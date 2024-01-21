package com.pinting.business.facade.site;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_PayOrders_GetSuccessPayOrders;
import com.pinting.business.hessian.site.message.ResMsg_PayOrders_GetSuccessPayOrders;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.service.manage.BsPayOdersService;

/**
 * 订单相关
 * @author bianyatian
 * @2016-5-19 上午10:36:48
 */
@Component("PayOrders")
public class PayOrdersFacade {
	@Autowired
	private BsPayOdersService bsPayOdersService;
	
	/**
	 * 查询处理中和成功的购买订单，有则获取第一条的状态
	 * @param req
	 * @param res
	 */
	public void getSuccessPayOrders(ReqMsg_PayOrders_GetSuccessPayOrders req,ResMsg_PayOrders_GetSuccessPayOrders res){
		List<BsPayOrders> list = bsPayOdersService.selectBuySuccessPayOrders(req.getUserId());
		if(list != null && list.size() > 0){
			res.setBuyType(list.get(0).getStatus());
		}
	}

}
