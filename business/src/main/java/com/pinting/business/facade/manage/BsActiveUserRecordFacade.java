package com.pinting.business.facade.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_BsActiveUserRecord_GetList;
import com.pinting.business.hessian.manage.message.ResMsg_BsActiveUserRecord_GetList;
import com.pinting.business.service.manage.BsActiveUserRecordService;

@Component("BsActiveUserRecord")
public class BsActiveUserRecordFacade {
	 @Autowired
	 private BsActiveUserRecordService bsActiveUserRecordService;
	
	public void getList(ReqMsg_BsActiveUserRecord_GetList req,
			ResMsg_BsActiveUserRecord_GetList res){
		bsActiveUserRecordService.selectList(req, res);
	}

}
