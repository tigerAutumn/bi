package com.pinting.schedule.facade;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.pinting.business.accounting.service.ProductPlanStatutsScheduleService;
import com.pinting.business.dayend.task.ProductPlanStatutsChangeSchedule;
import com.pinting.business.model.BsProduct;
import com.pinting.schedule.hessian.message.B2SReqMsg_ProductPlanSchedule_Regist4AuthPass;
import com.pinting.schedule.hessian.message.B2SReqMsg_ProductPlanSchedule_Regist4Publish;
import com.pinting.schedule.hessian.message.B2SReqMsg_ProductPlanSchedule_RegistDelete;
import com.pinting.schedule.hessian.message.B2SResMsg_ProductPlanSchedule_Regist4AuthPass;
import com.pinting.schedule.hessian.message.B2SResMsg_ProductPlanSchedule_Regist4Publish;
import com.pinting.schedule.hessian.message.B2SResMsg_ProductPlanSchedule_RegistDelete;

/**
 * 产品发布计划
 * @author dingpengfeng
 * @2016-6-24 下午2:34:41
 */
@Component("ProductPlanSchedule")
@SuppressWarnings("rawtypes")
public class ProductPlanScheduleFacade {
	
	@Autowired
	@Qualifier("productPlanStatutsScheduleService")
	private ProductPlanStatutsScheduleService productPlanStatutsScheduleService;
	private Logger logger = LoggerFactory.getLogger(ProductPlanScheduleFacade.class);

	/**
	 * 理财审核通过，注册定时任务，传出参数
	 * @param req
	 * @param res
	 */
	public void regist4AuthPass(B2SReqMsg_ProductPlanSchedule_Regist4AuthPass req, 
			B2SResMsg_ProductPlanSchedule_Regist4AuthPass res){
		BsProduct product = req.getProduct();
		logger.info("理财审核通过，注册定时任务请求数据：{}", JSONObject.fromObject(req).toString());
		List<ScheduledFuture> scheduleList = productPlanStatutsScheduleService.scheduleRegist4Status(product);
		if(!CollectionUtils.isEmpty(scheduleList)){
			ProductPlanStatutsChangeSchedule.getProductPlanScheduleMap().put(product.getId(), scheduleList);
		}
	}
	
	/**
	 * 手动发布理财，重置定时任务
	 * @param req
	 * @param res
	 */
	public void regist4Publish(B2SReqMsg_ProductPlanSchedule_Regist4Publish req, 
			B2SResMsg_ProductPlanSchedule_Regist4Publish res){
		logger.info("手动发布理财，重置定时任务：{}", JSONObject.fromObject(req).toString());
		BsProduct product = req.getProduct();
		productPlanStatutsScheduleService.scheduleRegistDelete(product);
		List<ScheduledFuture> scheduleList = productPlanStatutsScheduleService.scheduleRegist4Status(product);
		if(!CollectionUtils.isEmpty(scheduleList)){
			ProductPlanStatutsChangeSchedule.getProductPlanScheduleMap().put(product.getId(), scheduleList);
		}
	}
	
	/**
	 * 删除理财定时任务
	 * @param req
	 * @param res
	 */
	public void registDelete(B2SReqMsg_ProductPlanSchedule_RegistDelete req, 
			B2SResMsg_ProductPlanSchedule_RegistDelete res){
		BsProduct product = req.getProduct();
		productPlanStatutsScheduleService.scheduleRegistDelete(product);
	}

}
