package com.pinting.business.service.site;

import java.util.List;

import com.pinting.business.model.BsSysMessage;

public interface SysMessageService {
	/**
	 * 查询普通用户系统公告
	 * @return
	 */
	public List<BsSysMessage> userNormalMessage();
	
	/**
	 * 查询钱报用户系统公告
	 * @return
	 */
	public List<BsSysMessage> user178Message(String type);
}
