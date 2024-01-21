package com.pinting.business.accounting.service.impl;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.service.ProductPlanStatutsChangeService;
import com.pinting.business.model.BsProduct;
import com.pinting.schedule.hessian.message.B2SReqMsg_ProductPlanSchedule_Regist4AuthPass;
import com.pinting.schedule.hessian.message.B2SReqMsg_ProductPlanSchedule_Regist4Publish;
import com.pinting.schedule.hessian.message.B2SReqMsg_ProductPlanSchedule_RegistDelete;
import com.pinting.schedule.out.service.ScheduleTransportService;

@Service("productPlanStatutsChangeService")
public class ProductPlanStatutsChangeServiceImpl implements
		ProductPlanStatutsChangeService {
	@Autowired
	private ScheduleTransportService scheduleTransportService;
	private Logger logger = LoggerFactory.getLogger(ProductPlanStatutsChangeServiceImpl.class);
	
	@Override
	public void scheduleRegist4AuthPass(BsProduct product) {
		/*List<ScheduledFuture> scheduleList = scheduleRegist4Status(product);
		if(!CollectionUtils.isEmpty(scheduleList)){
			ProductPlanStatutsChangeSchedule.getProductPlanScheduleMap().put(product.getId(), scheduleList);
		}*/
		logger.info("理财审核通过，注册定时任务请求数据：{}", JSONObject.fromObject(product).toString());
		B2SReqMsg_ProductPlanSchedule_Regist4AuthPass req = new B2SReqMsg_ProductPlanSchedule_Regist4AuthPass();
		req.setProduct(product);
		scheduleTransportService.regist4AuthPass(req);
	}

	@Override
	public void scheduleRegistDelete4AuthReturn(BsProduct product) {
		//scheduleRegistDelete(product);
		logger.info("删除理财定时任务请求数据：{}", JSONObject.fromObject(product).toString());
		B2SReqMsg_ProductPlanSchedule_RegistDelete req = new B2SReqMsg_ProductPlanSchedule_RegistDelete();
		req.setProduct(product);
		scheduleTransportService.registDelete(req);
	}

	@Override
	public void scheduleRegistReset4Publish(BsProduct product) {
		/*scheduleRegistDelete(product);
		List<ScheduledFuture> scheduleList = scheduleRegist4Status(product);
		if(!CollectionUtils.isEmpty(scheduleList)){
			ProductPlanStatutsChangeSchedule.getProductPlanScheduleMap().put(product.getId(), scheduleList);
		}*/
		logger.info("手动发布理财，重置定时任务请求数据：{}", JSONObject.fromObject(product).toString());
		B2SReqMsg_ProductPlanSchedule_Regist4Publish req = new B2SReqMsg_ProductPlanSchedule_Regist4Publish();
		req.setProduct(product);
		scheduleTransportService.regist4Publish(req);
	}

	@Override
	public void scheduleRegistDelete4Finish(BsProduct product) {
		//scheduleRegistDelete(product);
		logger.info("理财计划手工结束时，删除定时任务注册请求数据：{}", JSONObject.fromObject(product).toString());
		B2SReqMsg_ProductPlanSchedule_RegistDelete req = new B2SReqMsg_ProductPlanSchedule_RegistDelete();
		req.setProduct(product);
		scheduleTransportService.registDelete(req);
	}

	@Override
	public void scheduleRegistDelete4AmountFull(BsProduct product) {
		//scheduleRegistDelete(product);
		logger.info("理财计划金额满额时，删除定时任务注册请求数据：{}", JSONObject.fromObject(product).toString());
		B2SReqMsg_ProductPlanSchedule_RegistDelete req = new B2SReqMsg_ProductPlanSchedule_RegistDelete();
		req.setProduct(product);
		scheduleTransportService.registDelete(req);
	}
	
}
