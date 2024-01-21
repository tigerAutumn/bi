package com.pinting.business.service.site;

import com.pinting.business.model.BsActiveUserRecord;

public interface ActiveUserRecordService {
	
	/**
	 * 根据用户id，终端，登录时间查询记录
	 * @param record
	 * @return
	 */
	public BsActiveUserRecord selectByRecord(BsActiveUserRecord record);
	

	/**
	 * 新增
	 * @param record
	 */
	public boolean addRecord(BsActiveUserRecord record);
}
