package com.pinting.business.facade.site;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_Fund_CheckIn;
import com.pinting.business.hessian.site.message.ReqMsg_Fund_ListQuery;
import com.pinting.business.hessian.site.message.ReqMsg_Fund_NetValueListQuery;
import com.pinting.business.hessian.site.message.ResMsg_Fund_CheckIn;
import com.pinting.business.hessian.site.message.ResMsg_Fund_ListQuery;
import com.pinting.business.hessian.site.message.ResMsg_Fund_NetValueListQuery;
import com.pinting.business.model.FdAppoint;
import com.pinting.business.model.FdInfo;
import com.pinting.business.model.FdNet;
import com.pinting.business.service.site.FundService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.business.util.SMSUtils;
import com.pinting.business.util.EMaySmsUtils.EMaySmsUtil;

@Component("Fund")
public class FundFacade {

	@Autowired
	private FundService fundService;
	
	public void checkIn(ReqMsg_Fund_CheckIn regMsg, ResMsg_Fund_CheckIn resMsg){
		
		FdAppoint appoint = new FdAppoint();
		appoint.setCity(regMsg.getCity());
		appoint.setMobile(regMsg.getMobile());
		appoint.setName(regMsg.getName());
		appoint.setTime(new Date());
		fundService.addFund(appoint);
		
		//梦网：SMSUtils.sendSMS(Constants.SEND_MOBILE, "有一笔客户投资已经预约，请即时查看，客户"+regMsg.getName().substring(0, 1)+"**");
		//亿美
		EMaySmsUtil.sendSMS(Constants.SEND_MOBILE, "【币港湾】有一笔客户投资已经预约，请即时查看，客户"+regMsg.getName().substring(0, 1)+"**",null,"1");
	}
	
	public void listQuery(ReqMsg_Fund_ListQuery reqMsg,ResMsg_Fund_ListQuery resMsg){
		
		ArrayList<FdInfo> valueList = (ArrayList<FdInfo>) fundService.findFdInfoListByPage(reqMsg.getPageIndex(),reqMsg.getPageSize());
		
		ArrayList<HashMap<String,Object>> hashList = BeanUtils.classToArrayList(valueList); 
		
		resMsg.setValueList(hashList);
		
		int pageSize = reqMsg.getPageSize();
		
		int total = fundService.countNetValueList();
		resMsg.setTotal((int)Math.ceil(total/pageSize));
		resMsg.setPageIndex((Integer)reqMsg.getPageIndex());
	}
	
	public void netValueListQuery(ReqMsg_Fund_NetValueListQuery reqMsg,ResMsg_Fund_NetValueListQuery resMsg){
		
		ArrayList<FdNet> valueList = (ArrayList<FdNet>) fundService.findFdNetByFundId(reqMsg.getFundId());
		
		ArrayList<HashMap<String,Object>> value = BeanUtils.classToArrayList(valueList);
		resMsg.setValue(value);
	}
}
