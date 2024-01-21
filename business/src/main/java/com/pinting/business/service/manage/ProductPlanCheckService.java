package com.pinting.business.service.manage;

import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanCheck_PlanCheck;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanCheck_PlanDetail;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanCheck_PlanListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_ProductPlanCheck_PlanCheck;
import com.pinting.business.hessian.manage.message.ResMsg_ProductPlanCheck_PlanDetail;
import com.pinting.business.hessian.manage.message.ResMsg_ProductPlanCheck_PlanListQuery;

public interface ProductPlanCheckService {
	
	/**
	 * 产品计划审核列表查询
	 * @param reqMsg
	 * @param resMsg
	 */
	void planListQuery(ReqMsg_ProductPlanCheck_PlanListQuery reqMsg,ResMsg_ProductPlanCheck_PlanListQuery resMsg);
	
	/**
	 * 产品计划审核操作
	 * @param reqMsg
	 * @param resMsg
	 */
	void planCheck(ReqMsg_ProductPlanCheck_PlanCheck reqMsg, ResMsg_ProductPlanCheck_PlanCheck resMsg);

	/**
	 * 产品计划详情
	 * @param reqMsg
	 * @param resMsg
	 */
	void planDetail(ReqMsg_ProductPlanCheck_PlanDetail reqMsg,ResMsg_ProductPlanCheck_PlanDetail resMsg);

}
