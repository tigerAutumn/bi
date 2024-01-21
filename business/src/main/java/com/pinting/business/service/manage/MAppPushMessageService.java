package com.pinting.business.service.manage;

import java.util.List;
import java.util.Map;

import com.pinting.business.model.BsAppPushedMessage;

public interface MAppPushMessageService {
	/**
	 * 存储app推送信息
	 * @param bsPushedMessage
	 */
	public int saveAppPushedMessage(BsAppPushedMessage bsPushedMessage);
	
	/**
	 * 根据参数查询app推送信息列表
	 * @return
	 */
	public List<BsAppPushedMessage> findPushedMessageByMap(Map<String,Object> map);
	
	/**
	 * 存储app推送信息
	 * @param bsPushedMessage
	 */
	public int saveAppPushedMessage(String sql);
}
