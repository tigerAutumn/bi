package com.pinting.business.accounting.service.impl;

import com.pinting.business.accounting.service.ProductPlanStatutsScheduleService;
import com.pinting.business.dayend.task.ProductPlanStatutsChangeSchedule;
import com.pinting.business.model.BsProduct;
import com.pinting.business.service.manage.ProductReleaseService;
import com.pinting.business.service.site.ProductService;
import com.pinting.business.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service("productPlanStatutsScheduleService")
@SuppressWarnings("rawtypes")
public class ProductPlanStatutsScheduleServiceImpl implements
		ProductPlanStatutsScheduleService {
	private static ScheduledExecutorService executor;
	static {
		executor = Executors.newScheduledThreadPool(20);
	}
	@Autowired
	private ProductReleaseService productReleaseService;
	@Autowired
	private ProductService productService;
	
	@Override
	public ScheduledFuture autoPublishSchedule(BsProduct product) {
		final Integer key = product.getId();
		//计算需延迟执行时间ms
		Date startTime = product.getStartTime();
		if(startTime == null){
			return null;
		}
		long delay = startTime.getTime() - 5*60*1000 - System.currentTimeMillis();
		//5分钟内
		if(delay < 0){
			productReleaseService.releaseProduct(key, null, null, null);
			return null;
		}
		return executor.schedule(new Runnable() {
			
			@Override
			public void run() {
				//自动发布
				productReleaseService.releaseProduct(key, null, null, null);
			}
			
		}, delay, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public ScheduledFuture autoOpeningSchedule(BsProduct product) {
		final Integer key = product.getId();
		//计算需延迟执行时间ms
		Date startTime = product.getStartTime();
		if(startTime == null){
			return null;
		}
		long delay = startTime.getTime() - System.currentTimeMillis();
		if(delay < 0){
			BsProduct productModify = new BsProduct();
			productModify.setId(key);
			productModify.setStatus(Constants.PRODUCT_STATUS_OPENING);
			productService.openProductById(productModify);
			return null;
		}
		return executor.schedule(new Runnable() {
			
			@Override
			public void run() {
				//自动开始
				BsProduct productModify = new BsProduct();
				productModify.setId(key);
				productModify.setStatus(Constants.PRODUCT_STATUS_OPENING);
				productService.openProductById(productModify);
			}
			
		}, delay, TimeUnit.MILLISECONDS);
	}

	@Override
	public ScheduledFuture autoFinishSchedule(BsProduct product) {
		final Integer key = product.getId();
		//计算需延迟执行时间ms
		Date endTime = product.getEndTime();
		if(endTime == null){
			return null;
		}
		long delay = endTime.getTime() - System.currentTimeMillis();
		if(delay < 0){
			productReleaseService.finishProduct(key, null);
			return null;
		}
		return executor.schedule(new Runnable() {
			
			@Override
			public void run() {
				//自动结束
				productReleaseService.finishProduct(key, null);
			}
			
		}, delay, TimeUnit.MILLISECONDS);
	}

	@Override
	public boolean cancelProductSchedule(ScheduledFuture schedule) {
		return schedule.cancel(false);
	}

	@Override
	public List<ScheduledFuture> scheduleRegist4Status(BsProduct product) {
		List<ScheduledFuture> scheduleList = new ArrayList<ScheduledFuture>();
		if(Constants.PRODUCT_STATUS_PUBLISH_NO.equals(product.getStatus())){
			//自动发布定时
			ScheduledFuture autoPublishSchedule = autoPublishSchedule(product);
			if(autoPublishSchedule != null){
				scheduleList.add(autoPublishSchedule);
			}
			//自动开始定时
			ScheduledFuture autoOpeningSchedule = autoOpeningSchedule(product);
			if(autoOpeningSchedule != null){
				scheduleList.add(autoOpeningSchedule);
			}
			//自动结束定时
			Date startTime = product.getStartTime();
			Date endTime = product.getEndTime();
			if(startTime != null && endTime != null && endTime.compareTo(new Date()) > 0){
				ScheduledFuture autoFinishSchedule = autoFinishSchedule(product);
				if(autoFinishSchedule != null){
					scheduleList.add(autoFinishSchedule);
				}
			}
		}else if(Constants.PRODUCT_STATUS_PUBLISH_YES.equals(product.getStatus())){
			//自动开始定时
			ScheduledFuture autoOpeningSchedule = autoOpeningSchedule(product);
			if(autoOpeningSchedule != null){
				scheduleList.add(autoOpeningSchedule);
			}
			//自动结束定时
			ScheduledFuture autoFinishSchedule = autoFinishSchedule(product);
			if(autoFinishSchedule != null){
				scheduleList.add(autoFinishSchedule);
			}
		}else if(Constants.PRODUCT_STATUS_OPENING.equals(product.getStatus())){
			//自动结束定时
			ScheduledFuture autoFinishSchedule = autoFinishSchedule(product);
			if(autoFinishSchedule != null){
				scheduleList.add(autoFinishSchedule);
			}
		}
		
		return scheduleList;
	}
	
	@Override
	public void scheduleRegistDelete(BsProduct product){
		List<ScheduledFuture> scheduleList = ProductPlanStatutsChangeSchedule.getProductPlanScheduleMap().get(product.getId());
		if(!CollectionUtils.isEmpty(scheduleList)){
			for (ScheduledFuture scheduledFuture : scheduleList) {
				cancelProductSchedule(scheduledFuture);
			}
			ProductPlanStatutsChangeSchedule.getProductPlanScheduleMap().remove(product.getId());
		}
	}

}
