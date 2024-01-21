package com.pinting.mall.service;

import com.pinting.mall.model.MallBsSysConfig;

/**
 * 
 * @Project: Mall
 * @Title: MallSysConfigService.java
 * @author Gemma
 * @date 2018-5-10 13:43:54 
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface MallSysConfigService {

	/**
	 * 根据key查询系统配置表
	 * @param conf_key
	 * @return 返回系统配置信息, 没有找到，返回null
	 */
	public MallBsSysConfig findConfigValueByKey(String key);
	
}
