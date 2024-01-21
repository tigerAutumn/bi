package com.pinting.mall.service;

import com.pinting.mall.model.MallSendCommodity;

/**
 *
 * @author SHENGUOPING
 * @date  2018年5月16日 下午4:31:55
 */
public interface MallSendCommodityService {

	
	MallSendCommodity findByOrderId(Integer orderId);
	
	
}
