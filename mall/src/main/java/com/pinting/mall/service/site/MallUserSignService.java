package com.pinting.mall.service.site;

/**
 * 用户签到相关
 * @project mall
 * @author bianyatian
 * @2018-5-15 上午11:33:40
 */
public interface MallUserSignService {
	
	/**
	 * 用户当日是否签到
	 * @author bianyatian
	 * @param userId
	 * @return
	 */
	boolean userIsSign(Integer userId);
}
