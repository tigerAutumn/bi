package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.message.ReqMsg_Appoint_InfoQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Appoint_ListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Appoint_SaveAppoint;
import com.pinting.business.hessian.manage.message.ResMsg_Appoint_InfoQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Appoint_ListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Appoint_SaveAppoint;
import com.pinting.business.model.FdAppoint;
import com.pinting.business.model.vo.FdAppointVO;
import com.pinting.business.service.manage.AppointService;
import com.pinting.business.util.BeanUtils;

@Component("Appoint")
public class AppointFacade {

	@Autowired
	public AppointService appointService;
	
	public void listQuery(ReqMsg_Appoint_ListQuery reqMsg,ResMsg_Appoint_ListQuery resMsg){
		
		FdAppoint fdAppoint = new FdAppoint();
		fdAppoint.setPageNum(reqMsg.getPageNum());
		
		fdAppoint.setNumPerPage(reqMsg.getNumPerPage());
		
		ArrayList<FdAppointVO> valueList = (ArrayList<FdAppointVO>) appointService.findMFdAppointInfoList(fdAppoint);
		ArrayList<HashMap<String,Object>> hashList = BeanUtils.classToArrayList(valueList); 
		resMsg.setValueList(hashList);
		
		int total = appointService.countAppointList();
		
		fdAppoint.setTotalRows(total);
		
		resMsg.setPageNum(reqMsg.getPageNum());
		resMsg.setNumPerPage(reqMsg.getNumPerPage());
		resMsg.setTotalRows(total);
		
	}
	
	public void infoQuery(ReqMsg_Appoint_InfoQuery reqMsg,ResMsg_Appoint_InfoQuery resMsg){
		
		FdAppoint appoint = appointService.findFdAppointById(reqMsg.getId());
		
		resMsg.setCity(appoint.getCity());
		resMsg.setMobile(appoint.getMobile());
		resMsg.setName(appoint.getName());
		resMsg.setIsDeal(appoint.getIsDeal());
		resMsg.setId(appoint.getId());
		resMsg.setNote(appoint.getNote());
		
	}
	
	public void saveAppoint(ReqMsg_Appoint_SaveAppoint reqMsg,ResMsg_Appoint_SaveAppoint resMsg) throws PTMessageException{
		
		FdAppoint appoint = new FdAppoint();
		appoint.setId(reqMsg.getId());
		appoint.setName(reqMsg.getName());
		appoint.setMobile(reqMsg.getMobile());
		appoint.setmUserId(Integer.parseInt(reqMsg.getUserId()));
		appoint.setCity(reqMsg.getCity());
		appoint.setIsDeal(reqMsg.getIsDeal());
		appoint.setNote(reqMsg.getNote());
		boolean flag = appointService.updateAppointById(appoint);
		
		if(!flag){
			new PTMessageException(PTMessageEnum.TRANS_ERROR);
		}
		
	}

}
