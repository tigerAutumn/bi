package com.pinting.business.facade.manage;

import com.pinting.business.accounting.finance.service.UserBonusExtractService;
import com.pinting.business.dao.BsSysSubAccountMapper;
import com.pinting.business.hessian.manage.message.ReqMsg_BsUserBonus_Bonus2JSH;
import com.pinting.business.hessian.manage.message.ResMsg_BsUserBonus_Bonus2JSH;
import com.pinting.business.model.BsSpecialJnl;
import com.pinting.business.model.BsSysSubAccount;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("BsUserBonus")
public class BsUserBonusFacade {
	
	@Autowired
	private BsSpecialJnlService bsSpecialJnlService;
	@Autowired
	private UserBonusExtractService bonusToJSHService;
	@Autowired
	private BsSysSubAccountMapper bsSysSubAccountMapper;
	
	/**
	 * 后台处理奖励金转余额失败的用户
	 * 1.判断是否存在特殊交易信息
	 * 2.不存在-返回错误
	 * 
	 * 3.存在-处理交易
	 */
	public void bonus2JSH(ReqMsg_BsUserBonus_Bonus2JSH req,ResMsg_BsUserBonus_Bonus2JSH res){
		boolean flag = false;
		BsSpecialJnl special = bsSpecialJnlService.getByPrimaryKeyInDeal(req.getId());
		if(special == null){
			res.setFlag(flag);
			res.setResMsg("该特殊交易流水信息不在未处理中");
		}else{
			special.setmUserId(req.getmUserId());
			if(special.getUserId() == null){
				res.setFlag(flag);
				res.setResMsg("无法获取该特殊交易流水信息的用户id,该信息状态已被改为失败");
				special.setStatus(Constants.SPECIAL_JNL_STATUS_FALL);
				bsSpecialJnlService.update(special);
			}
			if(special.getAmount().compareTo(0d) <= 0){
				res.setFlag(flag);
				res.setResMsg("无法获取该特殊交易流水信息的交易额未大于0,该信息状态已被改为失败");
				special.setStatus(Constants.SPECIAL_JNL_STATUS_FALL);
				bsSpecialJnlService.update(special);
			}
			//查询系统子账户结算户
			BsSysSubAccount jshAccount = bsSysSubAccountMapper
					.selectByCode(Constants.SYS_ACCOUNT_JSH);
			if(jshAccount.getBalance().compareTo(special.getAmount()) < 0 ){
				res.setFlag(flag);
				res.setResMsg("系统子账户结算户余额不足，请先充值");
			}
			flag = bonusToJSHService.passBonus2JSH(special);
			if(!flag){
				res.setFlag(flag);
				res.setResMsg("发生未知错误，请重试");
			}else{
				res.setFlag(flag);
			}
		}
	}

}
