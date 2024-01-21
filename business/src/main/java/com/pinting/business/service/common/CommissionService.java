package com.pinting.business.service.common;

import com.pinting.business.enums.PayPlatformEnum;
import com.pinting.business.enums.TransTypeEnum;
import com.pinting.business.model.vo.CommissionVO;

/**
 * 手续费相关服务接口
 * @author bianyatian
 * @2016-8-31 下午3:53:15
 */
public interface CommissionService {
	
	/**
	 * 币港湾手续费计算
	 * @param amount 交易金额，还款时必传，其他可传null
	 * @param transTypeEnum 交易类型，必传
	 * @return
	 */
	CommissionVO calServiceFee(Double amount, TransTypeEnum transTypeEnum, PayPlatformEnum payPlatformEnum);

}
