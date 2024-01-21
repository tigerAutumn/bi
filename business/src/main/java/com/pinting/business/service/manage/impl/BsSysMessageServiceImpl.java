package com.pinting.business.service.manage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsErrorCodeMapper;
import com.pinting.business.dao.BsSysMessageMapper;
import com.pinting.business.model.BsSysMessage;
import com.pinting.business.model.vo.BsErrorCodeVO;
import com.pinting.business.service.manage.BsSysMessageService;
import com.pinting.core.util.DateUtil;

@Service
public class BsSysMessageServiceImpl implements BsSysMessageService {

	@Autowired
	private BsSysMessageMapper sysMessageMapper;
	
	@Override
	public ArrayList<BsSysMessage> findSysMessageList(Integer id, String title,
			String content, String receiverType, String status,
			String createTime, String updateTime, int pageNum, int numPerPage,
			String orderDirection, String orderField) {

		BsSysMessage  bsSysMessage = new BsSysMessage();
		if(id != null ) {
			bsSysMessage.setId(id);
		}
		if(title != null && !"".equals(title)) {
			bsSysMessage.setTitle(title.trim());
		}
		if(content != null && !"".equals(content)) {
			bsSysMessage.setContent(content.trim());
		}
		if(receiverType != null && !"".equals(receiverType)) {
			bsSysMessage.setReceiverType(receiverType);
		}
		if(status != null && !"".equals(status)) {
			bsSysMessage.setStatus(status);
		}
		if(createTime != null && !"".equals(createTime)) {
			bsSysMessage.setCreateTime(DateUtil.parseDateTime(createTime));
		}
		if(updateTime != null && !"".equals(updateTime)) {
			bsSysMessage.setUpdateTime(DateUtil.parseDateTime(updateTime));
		}
		if(orderDirection != null && (!"".equals(orderDirection)) && orderField != null && (!"".equals(orderField))) {
			bsSysMessage.setOrderDirection(orderDirection);
			bsSysMessage.setOrderField(orderField);
		}
		bsSysMessage.setPageNum(pageNum);
		bsSysMessage.setNumPerPage(numPerPage);
		return sysMessageMapper.selectSysMessageListPageInfo(bsSysMessage);
	
	}

	@Override
	public int findCountSysMessage(Integer id, String title, String content,
			String receiverType, String status, String createTime,
			String updateTime) {
		BsSysMessage  bsSysMessage = new BsSysMessage();
		if(id != null ) {
			bsSysMessage.setId(id);
		}
		if(title != null && !"".equals(title)) {
			bsSysMessage.setTitle(title.trim());
		}
		if(content != null && !"".equals(content)) {
			bsSysMessage.setContent(content.trim());
		}
		if(receiverType != null && !"".equals(receiverType)) {
			bsSysMessage.setReceiverType(receiverType);
		}
		if(status != null && !"".equals(status)) {
			bsSysMessage.setStatus(status);
		}
		if(createTime != null && !"".equals(createTime)) {
			bsSysMessage.setCreateTime(DateUtil.parseDateTime(createTime));
		}
		if(updateTime != null && !"".equals(updateTime)) {
			bsSysMessage.setUpdateTime(DateUtil.parseDateTime(updateTime));
		}
		return sysMessageMapper.selectCountSysMessage(bsSysMessage);
	}

	@Override
	public int updateSysMessage(BsSysMessage record) {
		return sysMessageMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int addSysMessage(BsSysMessage record) {
		return sysMessageMapper.insertSelective(record);
	}

	@Override
	public BsSysMessage sysMessagePrimaryKey(Integer id) {
		return sysMessageMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteSysMessageById(Integer id) {
		return sysMessageMapper.deleteByPrimaryKey(id);
	}

}
