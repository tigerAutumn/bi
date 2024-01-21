package com.pinting.business.facade.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.accounting.service.ProductPlanStatutsChangeService;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanStatutsChange_ScheduleRegist4AuthPass;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanStatutsChange_ScheduleRegistDelete4AuthReturn;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanStatutsChange_ScheduleRegistDelete4Finish;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanStatutsChange_ScheduleRegistReset4Publish;
import com.pinting.business.hessian.manage.message.ResMsg_ProductPlanStatutsChange_ScheduleRegist4AuthPass;
import com.pinting.business.hessian.manage.message.ResMsg_ProductPlanStatutsChange_ScheduleRegistDelete4AuthReturn;
import com.pinting.business.hessian.manage.message.ResMsg_ProductPlanStatutsChange_ScheduleRegistDelete4Finish;
import com.pinting.business.hessian.manage.message.ResMsg_ProductPlanStatutsChange_ScheduleRegistReset4Publish;


@Component("ProductPlanStatutsChange")
public class ProductPlanStatutsChangeFacade {
	private Logger log = LoggerFactory.getLogger(ProductPlanStatutsChangeFacade.class);
	@Autowired
	private ProductPlanStatutsChangeService productPlanStatutsChangeService;
	
	public void scheduleRegist4AuthPass(ReqMsg_ProductPlanStatutsChange_ScheduleRegist4AuthPass req, ResMsg_ProductPlanStatutsChange_ScheduleRegist4AuthPass res) {
		productPlanStatutsChangeService.scheduleRegist4AuthPass(req.getBsProduct());
	}
	
	public void scheduleRegistDelete4AuthReturn(ReqMsg_ProductPlanStatutsChange_ScheduleRegistDelete4AuthReturn req, ResMsg_ProductPlanStatutsChange_ScheduleRegistDelete4AuthReturn res) {
		productPlanStatutsChangeService.scheduleRegistDelete4AuthReturn(req.getBsProduct());
	}

	/**
	 * 手动发布成功
	 * @param req
	 * @param res
	 */
	public void scheduleRegistReset4Publish(ReqMsg_ProductPlanStatutsChange_ScheduleRegistReset4Publish req, ResMsg_ProductPlanStatutsChange_ScheduleRegistReset4Publish res) {
	    productPlanStatutsChangeService.scheduleRegistReset4Publish(req.getBsProduct());
	}
	
	/**
     * 结束计划
     * @param req
     * @param res
     */
    public void scheduleRegistDelete4Finish(ReqMsg_ProductPlanStatutsChange_ScheduleRegistDelete4Finish req, ResMsg_ProductPlanStatutsChange_ScheduleRegistDelete4Finish res) {
        productPlanStatutsChangeService.scheduleRegistDelete4Finish(req.getBsProduct());
    }
}
