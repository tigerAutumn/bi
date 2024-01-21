package com.pinting.business.service.manage.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsAppPushedMessageMapper;
import com.pinting.business.model.BsAppPushedMessage;
import com.pinting.business.service.manage.MAppPushMessageService;

/**
 * app版本管理Service
 * @author yanwl
 * @date 2016-02-18
 */
@Service
public class MAppPushMessageServiceImpl implements MAppPushMessageService {

	@Autowired
	private BsAppPushedMessageMapper appPushedMessageMapper;

	@Override
	public int saveAppPushedMessage(BsAppPushedMessage bsPushedMessage) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", bsPushedMessage.getUserId());
		map.put("messageId", bsPushedMessage.getMessageId());
		List<BsAppPushedMessage> list = appPushedMessageMapper.selectPushedMessageByMap(map);
		if(list == null || list.isEmpty()) {
			return appPushedMessageMapper.insertSelective(bsPushedMessage);
		}else {
			return 0;
		}
	}

	@Override
	public List<BsAppPushedMessage> findPushedMessageByMap(
			Map<String, Object> map) {
		List<BsAppPushedMessage> list = appPushedMessageMapper.selectPushedMessageByMap(map);
		return list.size() > 0 ? list : null;
	}

	@Override
	public int saveAppPushedMessage(String sql) {
		return appPushedMessageMapper.insertBsAppPushedMessage(sql);
	}
}
