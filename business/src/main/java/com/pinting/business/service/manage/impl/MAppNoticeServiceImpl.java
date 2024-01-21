package com.pinting.business.service.manage.impl;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pinting.business.dao.BsAppMessageMapper;
import com.pinting.business.model.BsAppMessage;
import com.pinting.business.service.manage.MAppNoticeService;
import com.pinting.core.util.StringUtil;

/**
 * app通知管理Service
 * @author yanwl
 * @date 2016-02-23
 */
@Service
public class MAppNoticeServiceImpl implements MAppNoticeService {

	@Autowired
	private BsAppMessageMapper appMessageMapper;

	@Override
	public List<BsAppMessage> findAppMessage(Integer pageNum,
			Integer numPerPage, String name, String title, Integer releasePart,
			Integer isSend) {
		BsAppMessage message = new BsAppMessage();
		message.setNumPerPage(numPerPage);
		message.setPageNum(pageNum);
		if(StringUtil.isNotEmpty(name)) {
			message.setName(name);
		}
		if(StringUtil.isNotEmpty(title)) {
			message.setTitle(title);
		}
		message.setReleasePart(releasePart);
		message.setIsSend(isSend);
		List<BsAppMessage> messages = appMessageMapper.selectAppMessage(message);
		return messages.size() > 0 ? messages : null;
	}

	@Override
	public int findAllAppNoticeCount(String name, String title, Integer releasePart,Integer isSend) {
		BsAppMessage message = new BsAppMessage();
		if(StringUtil.isNotEmpty(name)) {
			message.setName(name);
		}
		if(StringUtil.isNotEmpty(title)) {
			message.setTitle(title);
		}
		message.setReleasePart(releasePart);
		message.setIsSend(isSend);
		return appMessageMapper.selectAllAppNoticeCount(message);
	}

	@Override
	public int saveAppNotice(BsAppMessage bsAppMessage) {
		return appMessageMapper.insertSelective(bsAppMessage);
	}

	@Override
	public List<BsAppMessage> findNoticeByMap(Map<String, Object> map) {
		List<BsAppMessage> messages = appMessageMapper.selectNoticeByMap(map);
		return messages.size() > 0 ? messages : null;
	}

	@Override
	public int deleteAppNotice(Integer id) {
		return appMessageMapper.deleteByPrimaryKey(id);
	}

	@Override
	public BsAppMessage findAppNoticeById(Integer id) {
		return appMessageMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateAppNotice(BsAppMessage bsAppMessage) {
		return appMessageMapper.updateByPrimaryKeySelective(bsAppMessage);
	}
}
