package com.pinting.business.service.manage.impl;

import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_BalanceBuy;
import com.pinting.business.model.BsProduct;

/**
 * 使用rabbitMQ发送微信消息模板
 * @project business
 * @author Gemma
 * @2018-8-10 18:27:23
 */
public interface ActivityCollectByRabbitService {

	
	
	/**
     * 运营基础数据采集入rabbitMQ
     * @author Gemma
     * @param ReqMsg_RegularBuy_BalanceBuy 购买请求信息 
     * @param BsProduct 产品信息
     * @param subAccountId 子账户编号
     * @param bizOrderNo 订单号
     * @return
     */
    void salaryPlanCollect(ReqMsg_RegularBuy_BalanceBuy req, BsProduct product, Integer subAccountId, String bizOrderNo);
}
