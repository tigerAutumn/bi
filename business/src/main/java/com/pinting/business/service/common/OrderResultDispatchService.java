package com.pinting.business.service.common;

import com.pinting.business.model.dto.OrderResultInfo;

/**
 * 订单状态返回后的分发服务
 * @Project: business
 * @Title: OrderResultDispatcherService.java
 * @author Zhou Changzai
 * @date 2016-9-2下午2:40:45
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface OrderResultDispatchService {

	
	/**
	 * 业务分发，外部业务确定订单结果后，调用此分发服务
	 * 此服务判断订单所属交易，再调用  OrderBusinessService 中对应的方法
	 * @param orderResultInfo 结果对象
	 */
	public void dispatch(OrderResultInfo orderResultInfo);
}


