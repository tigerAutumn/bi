package com.pinting.business.service.site.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsSysMessageMapper;
import com.pinting.business.dao.BsSysNewsMapper;
import com.pinting.business.dao.BsSysSubAccountMapper;
import com.pinting.business.model.BsSysMessage;
import com.pinting.business.model.BsSysMessageExample;
import com.pinting.business.model.BsSysNews;
import com.pinting.business.model.BsSysNewsExample;
import com.pinting.business.service.site.SysMessageService;
import com.pinting.business.util.Constants;

@Service
public class SysMessageServiceImpl implements SysMessageService {

	@Autowired
	private BsSysMessageMapper bsSysMessageMapper;
	@Autowired
	private BsSysNewsMapper bsSysNewsMapper;
	
	@Override
	public List<BsSysMessage> userNormalMessage() {
		BsSysMessageExample example = new BsSysMessageExample();
		example.createCriteria().andStatusEqualTo(Constants.SMS_MESSAGE_STATUS_VISIBLE).andReceiverTypeEqualTo(Constants.SMS_MESSAGE_RECEIVER_TYPE_NORMAL);
		return bsSysMessageMapper.selectByExample(example);
		
/*		BsSysNewsExample bsSysNewsExample = new BsSysNewsExample();
		bsSysNewsExample.createCriteria().andTypeEqualTo(Constants.NEWS_TYPE_NOTICE).andReceiverTypeEqualTo(Constants.NEWS_RECEIVER_TYPE_BGW).andStatusEqualTo(Constants.NEWS_STATUS_PUBLISHED);
		List<BsSysNews> bsSysNewsList = bsSysNewsMapper.selectByExample(bsSysNewsExample);
		List<BsSysMessage> bsSysMessagesList = new ArrayList<BsSysMessage>() ;
		
		for (BsSysNews bsSysNews : bsSysNewsList) {
			BsSysMessage bsSysMessage = new BsSysMessage();
			bsSysMessage.setId(bsSysNews.getId());
			bsSysMessage.setContent(bsSysNews.getContent());
			bsSysMessagesList.add(bsSysMessage);
		}
		return bsSysMessagesList;*/
	}

	@Override
	public List<BsSysMessage> user178Message(String type) {
		BsSysMessageExample example = new BsSysMessageExample();
		example.createCriteria().andStatusEqualTo(Constants.SMS_MESSAGE_STATUS_VISIBLE).andReceiverTypeEqualTo(type);
		return bsSysMessageMapper.selectByExample(example);
		
/*		BsSysNewsExample bsSysNewsExample = new BsSysNewsExample();
		bsSysNewsExample.createCriteria().andTypeEqualTo(Constants.NEWS_TYPE_NOTICE).andReceiverTypeEqualTo(Constants.NEWS_RECEIVER_TYPE_BGW178).andStatusEqualTo(Constants.NEWS_STATUS_PUBLISHED);
		List<BsSysNews> bsSysNewsList = bsSysNewsMapper.selectByExample(bsSysNewsExample);
		List<BsSysMessage> bsSysMessagesList = new ArrayList<BsSysMessage>() ;
		
		for (BsSysNews bsSysNews : bsSysNewsList) {
			BsSysMessage bsSysMessage = new BsSysMessage();
			bsSysMessage.setId(bsSysNews.getId());
			bsSysMessage.setContent(bsSysNews.getContent());
			bsSysMessagesList.add(bsSysMessage);
		}
		return bsSysMessagesList;*/
	}

}
