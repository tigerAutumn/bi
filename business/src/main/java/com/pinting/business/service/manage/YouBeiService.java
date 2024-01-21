package com.pinting.business.service.manage;

import java.util.Map;

public interface YouBeiService {

	/**
	 * 实名认证
	 * @return
	 */
	public Map<String, Object> checkRealName(String idCard, String name, String cardNo, String mobile);
}
