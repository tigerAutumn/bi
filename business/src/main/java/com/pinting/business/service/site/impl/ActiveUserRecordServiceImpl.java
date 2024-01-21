package com.pinting.business.service.site.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsActiveUserRecordMapper;
import com.pinting.business.model.BsActiveUserRecord;
import com.pinting.business.service.site.ActiveUserRecordService;

@Service
public class ActiveUserRecordServiceImpl implements ActiveUserRecordService {

	private final Logger logger = LoggerFactory.getLogger(ActiveUserRecordServiceImpl.class);
	
	@Autowired
	private BsActiveUserRecordMapper bsActiveUserRecordMapper;
	
	@Override
	public BsActiveUserRecord selectByRecord(BsActiveUserRecord record) {
		BsActiveUserRecord activeUserRecord = null;
		List<BsActiveUserRecord> list = bsActiveUserRecordMapper.selectByRecord(record);
		if(CollectionUtils.isNotEmpty(list)){
			activeUserRecord = list.get(0);
		}
		return activeUserRecord;
	}

	@Override
	public boolean addRecord(BsActiveUserRecord record) {
		Integer userId = record.getUserId();
		if(userId != null && userId != 0){
			bsActiveUserRecordMapper.insertSelective(record);
		}else if(userId != null && userId == 0){
			logger.info("==================userId："+userId+",端口："
					+record.getTerminalType()+",url："+record.getSrcUrl()+"==================");
		}
		return true;
	}

}
