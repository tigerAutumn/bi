package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsSysConfig;

public interface BsSysConfigService {
	
	/**
	 * 根据key查询
	 * @param confKey
	 * @return
	 */
	public BsSysConfig findKey(String confKey);
	
	/**
	 * 查询 全部
	 * @param confKey
	 * @return
	 */
	public List<BsSysConfig> findList();

}
