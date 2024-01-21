package com.pinting.business.facade.site;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_ActiveUserRecord_AddRecord;
import com.pinting.business.hessian.site.message.ResMsg_ActiveUserRecord_AddRecord;
import com.pinting.business.model.BsActiveUserRecord;
import com.pinting.business.service.site.ActiveUserRecordService;
import com.pinting.core.util.DateUtil;

/**
 * 活跃用户记录
 * @author bianyatian
 * @2016-6-2 下午1:44:20
 */
@Component("ActiveUserRecord")
public class ActiveUserRecordFacade {
	
	@Autowired
	private ActiveUserRecordService activeUserRecordService;
	
	/**
	 * 新增
	 * @param req
	 * @param res
	 */
	public void addRecord(ReqMsg_ActiveUserRecord_AddRecord req, ResMsg_ActiveUserRecord_AddRecord res){
		
		BsActiveUserRecord sRecord = new BsActiveUserRecord();
		sRecord.setUserId(req.getUserId());
		sRecord.setLoginDate(DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date())));
		sRecord.setTerminalType(req.getTerminalType());
		BsActiveUserRecord rRecord = activeUserRecordService.selectByRecord(sRecord);
		if(rRecord == null && StringUtils.isNotBlank(sRecord.getTerminalType())){
			sRecord.setSrcUrl(req.getSrcUrl());
			sRecord.setCreateTime(new Date());
			sRecord.setUpdateTime(new Date());
			activeUserRecordService.addRecord(sRecord);
		}
	}
}
