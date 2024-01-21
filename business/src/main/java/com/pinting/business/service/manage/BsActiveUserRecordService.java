package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.hessian.manage.message.ReqMsg_BsActiveUserRecord_GetList;
import com.pinting.business.hessian.manage.message.ResMsg_BsActiveUserRecord_GetList;
import com.pinting.business.model.BsActiveUserRecord;

public interface BsActiveUserRecordService {
	
	void selectList(ReqMsg_BsActiveUserRecord_GetList req, ResMsg_BsActiveUserRecord_GetList res);
 
}
