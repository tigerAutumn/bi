package com.pinting.business.accounting.service.impl;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinting.business.accounting.service.RecordAccountingService;
import com.pinting.business.accounting.service.UserWithdrawService;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.dao.BsUserWithdrawMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsAccountJnl;
import com.pinting.business.model.BsDailyBonus;
import com.pinting.business.model.BsUserWithdraw;
import com.pinting.business.model.BsUserWithdrawExample;
import com.pinting.business.model.DafyUserExt;
import com.pinting.business.model.vo.BsUserAssetVO;
import com.pinting.business.model.vo.BsUserVO;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.service.site.BonusService;
import com.pinting.business.service.site.SendWechatService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.gateway.out.service.DafyTransportService;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Withdraw_BonusWithdraw;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Withdraw_BonusWithdraw;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Withdraw_CustomerWithdrawCheck;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Withdraw_CustomerWithdrawResult;

@Service
public class UserWithdrawServiceImpl implements UserWithdrawService {

	@Autowired
	private BsUserWithdrawMapper bsUserWithdrawMapper;
	@Autowired
	private BsSpecialJnlService specialJnlService;
	@Autowired
	private BsUserMapper bsUserMapper;
	@Autowired
	private BonusService bonusService;
	@Autowired
	private RecordAccountingService recordAccountingService;
	/*@Autowired
	private SMSService smsService;*/
	@Autowired
	private UserService userService;
	@Autowired
	private DafyTransportService dafyTransportService;
	@Autowired
	private SMSServiceClient smsServiceClient;
	@Autowired
	private SendWechatService sendWechatService;
	
	@Override
	public Integer addUserWithdraw(BsUserWithdraw bsUserWithdraw) {
		return bsUserWithdrawMapper.insertSelective(bsUserWithdraw);
	}
	
	@Override
	public void modifyUserWithdrawById(BsUserWithdraw bsUserWithdraw){
		bsUserWithdrawMapper.updateByPrimaryKeySelective(bsUserWithdraw);
	}
	
	@Override
	public void modifyUserWithdrawByApplyNo(BsUserWithdraw bsUserWithdraw) {
		BsUserWithdrawExample example = new BsUserWithdrawExample();
		example.createCriteria().andApplyNoEqualTo(bsUserWithdraw.getApplyNo());
		bsUserWithdrawMapper.updateByExampleSelective(bsUserWithdraw, example);
	}

	@Override
	public BsUserWithdraw findUserWithdrawByApplyNo(String applyNo) {
		BsUserWithdrawExample example = new BsUserWithdrawExample();
		example.createCriteria().andApplyNoEqualTo(applyNo);
		ArrayList<BsUserWithdraw> bsUserWithdrawList = (ArrayList<BsUserWithdraw>) bsUserWithdrawMapper
				.selectByExample(example);
		return bsUserWithdrawList.size() == 1 ? bsUserWithdrawList.get(0)
				: null;
	}

	@Override
	@Deprecated
	@Transactional(noRollbackFor = PTMessageException.class)
	public synchronized void finishCustomerWithdrawResult(
			G2BReqMsg_Withdraw_CustomerWithdrawResult req) {

		/**
		 * 1、判断是否重复通知
		 * 2、当提现失败:
		 * 	解冻记账,
		 * 	更新用户提现表记录为失败,
		 * 	短信通知
		 * 3、提现成功：
		 * 	判断当前提现金额是否与系统中一致,
		 * 	判断当前提现银行是否与系统中一致,
		 * 	记账
		 * 	更新用户提现表记录为成功
		 *  插入一条提现奖励
		 * 	短信通知
		 */

		//判断是否重复通知
		BsUserWithdraw bsUserWithdraw = findUserWithdrawByApplyNo(req.getApplyNo());
		if(bsUserWithdraw.getStatus() != Constants.CUSTOMER_WITHDRAW_APPLY){
			throw new PTMessageException(PTMessageEnum.DAFY_RESULT_NOTICE_REPEAT);
		}
		
		BsUserVO userVo = bsUserMapper.selectByDafyUserId(req.getCustomerId());
		// 当提现失败
		if (Integer.parseInt(req.getResult()) == G2BReqMsg_Withdraw_CustomerWithdrawResult.WITHDRAW_RESULT_FAIL) {

			// 解冻记账(结算户冻结余额减少,可用余额增加;用户表可提现金额和当前奖励金金额增加;流水记账)
			Date transTime = new Date();
			BsAccountJnl jnl = new BsAccountJnl();
			jnl.setTransTime(transTime);
			jnl.setTransCode(Constants.USER_UNFREEZE);
			jnl.setTransAmount(req.getAmount());
			jnl.setUserId1(userVo.getId());
			recordAccountingService.postfRecordAccounting(jnl);
			Integer jnlId = jnl.getId();
			// 更新用户提现表记录为失败
			BsUserWithdraw userWithdraw4Fail = new BsUserWithdraw();
			userWithdraw4Fail.setStatus(Constants.CUSTOMER_WITHDRAW_FAIL);
			userWithdraw4Fail.setFailReason(req.getFailReason());
			userWithdraw4Fail.setEndJnlNo(jnlId);
			userWithdraw4Fail.setApplyNo(req.getApplyNo());
			userWithdraw4Fail.setFinishTime(req.getSuccessTime());
			modifyUserWithdrawByApplyNo(userWithdraw4Fail);
			// 发送短信给客户，提示体现是失败
			/*smsService.sendMessage(
					userVo.getMobile(),
					"您有一笔金额为：￥" + req.getAmount() + "元的提现交易失败，失败原因："
							+ req.getFailReason() + "，如有疑问请拨打400-806-1230。");*/
			smsServiceClient.sendTemplateMessage(userVo.getMobile(),TemplateKey.WITHDRAW_FALL, req.getAmount().toString(), req.getFailReason());
			//微信模板消息
    		String bankCard = req.getFailReason();
        	sendWechatService.withdrawMgs(userVo.getId(),"", bankCard.substring(bankCard.length()-4,bankCard.length()), req.getAmount().toString(),"fall",req.getFailReason(),null,null);
			return;
		}
		
		

		if (!userVo.getBankCard().equals(req.getBankcard())) { // 判断当前提现的银行卡是否与系统登记的银行卡相同
			specialJnlService.addSpecialJnl("【用户提现结果通知】",
					"【提现银行卡不一致，提现银行卡：" + userVo.getBankCard() + "，实际提现银行卡："
							+ req.getBankcard() + "】");
			throw new PTMessageException(
					PTMessageEnum.PAYMENT_CUSTOMERWITHDRAW_BANKCADE_ERROR);
		}

		if (bsUserWithdraw.getAmount().doubleValue() != req.getAmount()
				.doubleValue()) { // 判断当前提现的金额与系统登记的金额不相同时
			specialJnlService.addSpecialJnl("【用户提现结果通知】", "【提现金额不一致，提现金额："
					+ bsUserWithdraw.getAmount() + "，实际体现金额：" + req.getAmount()
					+ "】");
			throw new PTMessageException(
					PTMessageEnum.PAYMENT_SYSWITHDRAW_AMOUNT_NOT_SAME);
		}

		// 记账
		Date transTime = new Date();
		BsAccountJnl jnl = new BsAccountJnl();
		jnl.setTransTime(transTime);
		jnl.setTransCode(Constants.USER_BALANCE_WITHDRAW);
		jnl.setTransAmount(req.getAmount());
		jnl.setUserId1(userVo.getId());
		recordAccountingService.postfRecordAccounting(jnl);
		Integer jnlId = jnl.getId();

		// 更新用户提现表记录为成功
		BsUserWithdraw userWithdraw4Succ = new BsUserWithdraw();
		userWithdraw4Succ.setStatus(Constants.CUSTOMER_WITHDRAW_SUCCESS);
		userWithdraw4Succ.setFinishTime(req.getSuccessTime());
		userWithdraw4Succ.setEndJnlNo(jnlId);
		userWithdraw4Succ.setApplyNo(req.getApplyNo());
		modifyUserWithdrawByApplyNo(userWithdraw4Succ);

		// 插入一条提现奖励
		BsDailyBonus bsDailyBonus = new BsDailyBonus();
		bsDailyBonus.setBeRecommendUserId(userVo.getId());
		bsDailyBonus.setBonus(-req.getAmount());
		bsDailyBonus.setTime(new Date());
		bsDailyBonus.setUserId(userVo.getId());
		bonusService.addDailyBonus(bsDailyBonus);

		/*smsService.sendMessage(userVo.getMobile(), "您有一笔金额为：￥" + req.getAmount()
				+ "元的提现已完成，如有疑问请拨打400-806-1230。");*/
		smsServiceClient.sendTemplateMessage(userVo.getMobile(),TemplateKey.WITHDRAW_SUC, req.getAmount().toString());
		//微信模板消息
		/*String bankCard = req.getBankcard();
		sendWechatService.withdrawMgs(userVo.getId(),"", bankCard.substring(bankCard.length()-4,bankCard.length()), req.getAmount().toString(), "suc",null);*/
	}

	@Override
	@Deprecated
	public synchronized void userWithdraw(Integer userId, Double amount,
			String pass) {
		DafyUserExt dafyUser = userService.findDafyUserByUserId(userId);
		// 判断是否含达飞客户信息和绑卡信息
		if (dafyUser == null
				|| (dafyUser != null && dafyUser.getStatus() != Constants.DAFY_BINDING_STAUTS_NORMAL)) {
			throw new PTMessageException(PTMessageEnum.BANKCARD_NOT_BIND);
		}
		BsUserAssetVO user = userService.findUserAssetByUserId(userId);
		// 判断是否买过产品
		if (user.getInvestNum() <= 0) {
			throw new PTMessageException(PTMessageEnum.USER_NOT_BUY_PRODUCT);
		}
		// 判断可提现金额是否超过100
		if (user.getCanWithdraw() < 100) {
			throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_LIMIT_100);
		}
		// 判断金额是否超限（余额不足、超过最低或最高限额）
		if (amount <= 0 || amount > user.getCanWithdraw()) {
			throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
		}
		// 判断支付密码是否正确
		if (user.getPayPassword() == null) {
			throw new PTMessageException(PTMessageEnum.USER_PAY_PASS_IS_NULL);
		}
		if (!MD5Util.encryptMD5(pass + MD5Util.getEncryptkey()).equals(
				user.getPayPassword())) {
			throw new PTMessageException(PTMessageEnum.USER_PAY_PASS_ERROR);
		}
		
		Date applyTime = new Date();
		
		// 记录提现信息
		BsUserWithdraw bsUserWithdraw = new BsUserWithdraw();
		bsUserWithdraw.setAmount(amount);
		bsUserWithdraw.setUserId(userId);
		bsUserWithdraw.setStatus(Constants.CUSTOMER_WITHDRAW_APPLY);
		bsUserWithdraw.setWithdrawTime(applyTime);
		addUserWithdraw(bsUserWithdraw);
		Integer id = bsUserWithdraw.getId();
		String applyNo = Util.generateApplyNo(String.valueOf(id), Constants.CUSTOMER_WITHDRAW_APPLY_NO);
		
		// 更新提现信息表 申请编号
		BsUserWithdraw modifyWithdraw4ApplyNo = new BsUserWithdraw();
		modifyWithdraw4ApplyNo.setId(id);
		modifyWithdraw4ApplyNo.setApplyNo(applyNo);
		modifyUserWithdrawById(modifyWithdraw4ApplyNo);
		
		// 组装B2G消息，发送请求
		B2GResMsg_Withdraw_BonusWithdraw resMsg = null;
		try {
			
			B2GReqMsg_Withdraw_BonusWithdraw reqMsg = new B2GReqMsg_Withdraw_BonusWithdraw();
			reqMsg.setApplyNo(applyNo);
			reqMsg.setDafyUserId(dafyUser.getDafyUserId());
			reqMsg.setAmount(amount);
			reqMsg.setBankcard(dafyUser.getBankCard());
			reqMsg.setTransType(Constants.WXACCOUNT_TRS_TYPE_CUSTOMER_WITHDRAW);
			reqMsg.setUserName(user.getUserName());
			reqMsg.setApplyTime(applyTime);
			resMsg = dafyTransportService.bonusWithdraw(reqMsg);// 发送奖励金提现请求
		} catch (Exception e) {
			throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR,
					e.getMessage());
		}

		// 申请结果
		if (resMsg != null) {
			// 成功，记录提现信息(applyNo)，冻结提现金额
			if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
				
				// 冻结记账
				BsAccountJnl jnl = new BsAccountJnl();
				jnl.setTransTime(applyTime);
				jnl.setTransCode(Constants.USER_BALANCE_WITHDRAW);
				jnl.setTransAmount(amount);
				jnl.setUserId1(userId);
				Integer jnlId = recordAccountingService
						.preRecordAccounting(jnl);
				// 更新提现信息表开始流水
				BsUserWithdraw modifyWithdraw = new BsUserWithdraw();
				modifyWithdraw.setStartJnlNo(jnlId);
				modifyWithdraw.setStatus(Constants.CUSTOMER_WITHDRAW_APPLY);
				modifyWithdraw.setId(id);
				
				modifyUserWithdrawById(modifyWithdraw);
				/*
				 * 放到通知回来进行 //奖励金明细表增加记录 BsDailyBonus bsDailyBonus = new
				 * BsDailyBonus(); bsDailyBonus.setBeRecommendUserId(userId);
				 * bsDailyBonus.setBonus(-amount); bsDailyBonus.setTime(new
				 * Date()); bsDailyBonus.setUserId(userId);
				 * bonusService.addDailyBonus(bsDailyBonus);
				 */

			}
			// 失败，记录失败信息
			else {
				BsUserWithdraw modifyWithdraw = new BsUserWithdraw();
				modifyWithdraw.setStatus(Constants.CUSTOMER_WITHDRAW_FAIL);
				modifyWithdraw.setFailReason(resMsg.getResMsg());// 失败原因
				modifyWithdraw.setId(id);
				modifyWithdraw.setFinishTime(new Date());
				modifyUserWithdrawById(modifyWithdraw);
				throw new PTMessageException(PTMessageEnum.WITHDRAW_FAIL,
						resMsg.getResMsg());
			}
		}

	}

	@Override
	@Deprecated
	public boolean checkCustomerWithdraw(
			G2BReqMsg_Withdraw_CustomerWithdrawCheck req) {
		boolean flag = false;
		String applyNo = req.getApplyNo();
		Double amount = req.getAmount();
		Date applyTime = req.getApplyTime();
		String customerId = req.getCustomerId();
		String bankcard = req.getBankcard();
		BsUserWithdraw bsUserWithdraw = findUserWithdrawByApplyNo(applyNo);
		if(bsUserWithdraw != null){
			Double locAmount = bsUserWithdraw.getAmount();
			Date locApplyTime = bsUserWithdraw.getWithdrawTime();
			DafyUserExt dafyUser = userService.findDafyUserByUserId(bsUserWithdraw.getUserId());
			String locDafyUserId = dafyUser.getDafyUserId();
			String locBankcard = dafyUser.getBankCard();
			if(locAmount.compareTo(amount) == 0 && 
					locApplyTime.compareTo(applyTime) == 0 &&
					locDafyUserId.equals(customerId) && 
					locBankcard.equals(bankcard)){
				flag = true;
			}
		}
		return flag;
	}

}
