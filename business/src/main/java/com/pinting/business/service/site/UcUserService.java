package com.pinting.business.service.site;



public interface UcUserService {
	/**
	 * 根据用户手机或昵称判断用户是否存在, 只要一个满足，即
	 * @param mobile 用户手机号或者昵称
	 * @return 如果找到信息，返回true，找不到则返回false
	 */
	public boolean isValidMobile(String mobile);

}
