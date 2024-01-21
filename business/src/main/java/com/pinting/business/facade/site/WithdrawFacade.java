package com.pinting.business.facade.site;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.hessian.site.message.ReqMsg_UserWithdraw_CanWithdrawTime;
import com.pinting.business.hessian.site.message.ResMsg_UserWithdraw_CanWithdrawTime;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;

@Component("UserWithdraw")
public class WithdrawFacade {
	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
	
	public void canWithdrawTime(ReqMsg_UserWithdraw_CanWithdrawTime req,ResMsg_UserWithdraw_CanWithdrawTime res){
		BsSysConfig bsSysConfigB = bsSysConfigMapper.selectByPrimaryKey(Constants.WITHDRAW_TIME_BEGIN);
		BsSysConfig bsSysConfigE = bsSysConfigMapper.selectByPrimaryKey(Constants.WITHDRAW_TIME_END);
		res.setBegin(bsSysConfigB.getConfValue());
		res.setEnd(bsSysConfigE.getConfValue());
		String[] b = bsSysConfigB.getConfValue().split(":");
		String[] e = bsSysConfigE.getConfValue().split(":");
		String begin = DateUtil.formatYYYYMMDD(new Date())+" "+bsSysConfigB.getConfValue();
		String end = DateUtil.formatYYYYMMDD(new Date())+" "+bsSysConfigE.getConfValue();
		if(b.length == 2){
			begin = DateUtil.formatYYYYMMDD(new Date())+" "+bsSysConfigB.getConfValue()+":00";	
		}
		if(e.length == 2){
			end = DateUtil.formatYYYYMMDD(new Date())+" "+bsSysConfigE.getConfValue()+":00";
		}
		res.setBeginTime(begin);
		res.setEndTime(end);
		
	}

}
