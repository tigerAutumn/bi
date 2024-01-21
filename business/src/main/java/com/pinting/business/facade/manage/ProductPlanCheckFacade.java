package com.pinting.business.facade.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanCheck_PlanCheck;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanCheck_PlanDetail;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanCheck_PlanListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_ProductPlanCheck_PlanCheck;
import com.pinting.business.hessian.manage.message.ResMsg_ProductPlanCheck_PlanDetail;
import com.pinting.business.hessian.manage.message.ResMsg_ProductPlanCheck_PlanListQuery;
import com.pinting.business.service.manage.MWxMenuService;
import com.pinting.business.service.manage.ProductPlanCheckService;

@Component("ProductPlanCheck")
public class ProductPlanCheckFacade {
	@Autowired
	private ProductPlanCheckService productPlanCheckService;
	
	public void planListQuery(ReqMsg_ProductPlanCheck_PlanListQuery reqMsg,ResMsg_ProductPlanCheck_PlanListQuery resMsg) {
		productPlanCheckService.planListQuery(reqMsg,resMsg);
	}
	
	public void planCheck(ReqMsg_ProductPlanCheck_PlanCheck reqMsg,ResMsg_ProductPlanCheck_PlanCheck resMsg) {
		productPlanCheckService.planCheck(reqMsg,resMsg);
	}
	
	
	public void planDetail(ReqMsg_ProductPlanCheck_PlanDetail reqMsg,ResMsg_ProductPlanCheck_PlanDetail resMsg) {
		productPlanCheckService.planDetail(reqMsg,resMsg);
	}
	
}
