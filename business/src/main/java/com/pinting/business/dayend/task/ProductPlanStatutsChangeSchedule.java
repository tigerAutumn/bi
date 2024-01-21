package com.pinting.business.dayend.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.springframework.util.CollectionUtils;

import com.pinting.business.accounting.service.ProductPlanStatutsScheduleService;
import com.pinting.business.model.BsProduct;
import com.pinting.business.service.site.ProductService;
import com.pinting.core.util.SpringBeanUtil;

/**
 * 理财计划状态定时任务初始化
 * @Project: business
 * @Title: ProductPlanStatutsChangeSchedule.java
 * @author dingpf
 * @date 2016-4-21 上午11:15:05
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
@SuppressWarnings("rawtypes")
public class ProductPlanStatutsChangeSchedule{
	
	//理财计划定时任务池
	private static Map<Integer, List<ScheduledFuture>> productPlanScheduleMap = new HashMap<Integer, List<ScheduledFuture>>();
	static {
		//首次初始化理财计划定时任务
		if(productPlanScheduleMap.size() == 0){
			//查询需定时的理财计划
			ProductService productService = (ProductService) SpringBeanUtil.getBean("productService");
			List<BsProduct> products = productService.findAuthPassProducts();
			if(!CollectionUtils.isEmpty(products)){
				ProductPlanStatutsScheduleService productPlanStatutsScheduleService = (ProductPlanStatutsScheduleService) SpringBeanUtil.getBean("productPlanStatutsScheduleService");
				for (BsProduct product : products) {
					List<ScheduledFuture> scheduleList = productPlanStatutsScheduleService.scheduleRegist4Status(product);
					if(!CollectionUtils.isEmpty(scheduleList)){
						productPlanScheduleMap.put(product.getId(), scheduleList);
					}
				}
				System.out.println(">>>>>>["+productPlanScheduleMap.size()+"]条理财计划定时任务初始化完成<<<<<<");
			}
		}
	}

	public static Map<Integer, List<ScheduledFuture>> getProductPlanScheduleMap() {
		return productPlanScheduleMap;
	}
	
}
