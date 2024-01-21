package com.pinting.business.accounting.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.service.SysWithdrawService;
import com.pinting.business.dao.BsSysWithdrawMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsSysWithdraw;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Withdraw_SysWithdrawResult;

@Service
public class SysWithdrawServiceImpl implements SysWithdrawService {

	@Autowired
	private BsSysWithdrawMapper bsSysWithdrawMapper;
	@Autowired
	private BsSpecialJnlService specialJnlService;
	@Override
	public boolean addSysWithdraw(BsSysWithdraw sysWithdraw) {
		
		int flag = bsSysWithdrawMapper.insertSelective(sysWithdraw);
		
		return flag>0?true:false;
	}
	@Override
	public boolean finishSysWithdraw(G2BReqMsg_Withdraw_SysWithdrawResult req) {

		/**
		 * 系统提现步骤：
		 * 1.判断达飞返回提现金额与提交金额做比较，如不相同返回错误结果
		 * 2.开始更新当前提现结果
		 */
		
		BsSysWithdraw sysWithdraw = bsSysWithdrawMapper.selectSysWithdrawByAppleNo(req.getApplyNo());
		
		if(sysWithdraw == null){ //查无此提现记录
			throw new PTMessageException(PTMessageEnum.PAYMENT_SYSWITHDRAW_NOT_EXIST);			
		}
		
		//达飞返回提现失败
		if(Integer.parseInt(req.getResult()) == G2BReqMsg_Withdraw_SysWithdrawResult.WITHDRAW_RESULT_FAIL){
			BsSysWithdraw sysWithdrawFail = new BsSysWithdraw();
			sysWithdrawFail.setStatus(Constants.WX_SYS_WITHDRAW_FAIL);
			sysWithdrawFail.setId(sysWithdraw.getId());
			sysWithdrawFail.setFailReason(req.getFailReason());
			sysWithdrawFail.setFinishTime(req.getSuccessTime());
			bsSysWithdrawMapper.updateByPrimaryKeySelective(sysWithdrawFail);
			return true;
		}
		
		//达飞返回提现成功
		
		if(sysWithdraw.getAmount().doubleValue() != req.getAmount().doubleValue()){
			specialJnlService.addSpecialJnl("【网新提现结果通知】", "【提现金额不一致，提现金额：" + sysWithdraw.getAmount() + "，实际提现金额：" + req.getAmount() + "】");
			throw new PTMessageException(PTMessageEnum.PAYMENT_SYSWITHDRAW_AMOUNT_NOT_SAME);
		}
		
		
		BsSysWithdraw sysWithdrawSuccess = new BsSysWithdraw();
		sysWithdrawSuccess.setId(sysWithdraw.getId());
		sysWithdrawSuccess.setFinishTime(req.getSuccessTime());
		sysWithdrawSuccess.setStatus(Constants.WX_SYS_WITHDRAW_SUCCESS);
		
		bsSysWithdrawMapper.updateByPrimaryKeySelective(sysWithdrawSuccess);
		
		return true;
	}
	@Override
	public Integer addSysWithdrawReturnId(BsSysWithdraw sysWithdraw) {
		return bsSysWithdrawMapper.insertSelective(sysWithdraw);
	}
	@Override
	public boolean modifySysWithdrawById(BsSysWithdraw sysWithdraw) {
		
		return bsSysWithdrawMapper.updateByPrimaryKeySelective(sysWithdraw) == 1 ? true : false;
	}
	
}
