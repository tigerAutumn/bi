package com.pinting.gateway.youbei.out.service;

import com.pinting.gateway.youbei.out.model.RealNameModel;

public interface SendYouBeiService {

	/**
	 * 用户四要素验证
	 * @param name 姓名
	 * @param idCard 身份证号
	 * @param cardNo 卡号
	 * @param mobile 手机号
	 * @return
	 */
	public RealNameModel checkRealName(String name, String idCard, String cardNo, String mobile);
}
