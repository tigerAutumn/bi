package com.pinting.gateway.out.service;

import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Account_CheckAccount;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Account_QueryWXAccountDetail;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_GetLoanRelationNew;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_GetLoanRelationUrl;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_IncrementDataMission;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_IncrementDeptInfo;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_IncrementUserInfo;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryCurrDeptInfo;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryCurrDeptUserInfo;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryDeptInfo;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryDeptUserInfo;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryUserById;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryUserByPhone;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_Register;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_BasicInformation;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_BindCard;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_BuyProduct;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_QueryRepayJnl;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_SysBatchBuyProduct;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Withdraw_BonusWithdraw;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Withdraw_SysWithdraw;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Account_CheckAccount;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Account_QueryWXAccountDetail;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_GetLoanRelationNew;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_GetLoanRelationUrl;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_IncrementDataMission;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_IncrementDeptInfo;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_IncrementUserInfo;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryCurrDeptInfo;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryCurrDeptUserInfo;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryDeptInfo;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryDeptUserInfo;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryUserById;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryUserByPhone;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_Register;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_BasicInformation;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_BindCard;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_BuyProduct;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_QueryRepayJnl;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_SysBatchBuyProduct;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Withdraw_BonusWithdraw;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Withdraw_SysWithdraw;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_ServerUsableCheck;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_ServerUsableCheck;

public interface DafyTransportService {
	
	/**
	 * 注册用户，也就是给用户做实名认证
	 * @param req B2GReqMsg_Customer_Register
	 * @return B2GResMsg_Customer_Register 对象，如果注册成功，此对象中customerId字段应该有值
	 */
	public B2GResMsg_Customer_Register register(B2GReqMsg_Customer_Register req);
	
	/**
	 * 绑定银行卡，绑定的银行卡为理财到期三方支付回款的卡
	 * @param req B2GReqMsg_Payment_BindCard
	 * @return B2GResMsg_Payment_BindCard 对象
	 */
	public B2GResMsg_Payment_BindCard bindCard(B2GReqMsg_Payment_BindCard req);
	
	/**
	 * 用户购买产品
	 * @param req B2GReqMsg_Payment_BindCard（customerId，productCode，amount）
	 * @return B2GResMsg_Payment_BuyProduct 对象
	 */
	public B2GResMsg_Payment_BuyProduct buyProduct(B2GReqMsg_Payment_BuyProduct req);
	
	/**
	 * 资金对账
	 * @param req B2GReqMsg_Account_CheckAccount
	 * @return B2GResMsg_Account_CheckAccount 对象
	 */
	public B2GResMsg_Account_CheckAccount checkAccount(B2GReqMsg_Account_CheckAccount req);
	
	/**
	 * 债权关系查询 2015-12-01 之前
	 * @param req B2GReqMsg_Customer_QueryLoanRelation
	 * @return B2GReqMsg_Customer_QueryLoanRelation 对象
	 */
	public B2GResMsg_Customer_GetLoanRelationUrl queryLoanRelation(B2GReqMsg_Customer_GetLoanRelationUrl req);
	
	/**
	 * 基本信息接口（达飞注册+实名认证+绑卡）
	 * @param req B2GReqMsg_Payment_BasicInformation
	 * @return B2GResMsg_Payment_BasicInformation
	 */
	public B2GResMsg_Payment_BasicInformation basicInformation(B2GReqMsg_Payment_BasicInformation req);
	
	/**
	 * 奖励金提现接口
	 * @param req B2GReqMsg_Withdraw_BonusWithdraw
	 * @return B2GResMsg_Withdraw_BonusWithdraw
	 */
	public B2GResMsg_Withdraw_BonusWithdraw bonusWithdraw(B2GReqMsg_Withdraw_BonusWithdraw req);

	/**
	 * 品听提现接口
	 * @param req B2GReqMsg_Account_PinTingWithdraw
	 * @return B2GResMsg_Account_PinTingWithdraw
	 */
	public B2GResMsg_Withdraw_SysWithdraw sysWithdraw(
			B2GReqMsg_Withdraw_SysWithdraw req);
	/**
	 * 查询网新账户明细
	 * @param req
	 * @return
	 */
	public B2GResMsg_Account_QueryWXAccountDetail wXAccountDetailQuery(B2GReqMsg_Account_QueryWXAccountDetail req);
	
	/**
	 * 系统批量购买达飞理财
	 * @param req
	 * @return
	 */
	public B2GResMsg_Payment_SysBatchBuyProduct sysBatchBuyProduct(B2GReqMsg_Payment_SysBatchBuyProduct req);
	
	/**
	 * 债权关系查询  2015-12-01 之后
	 * @param req
	 * @return
	 */
	public B2GResMsg_Customer_GetLoanRelationNew queryLoanRelationNew(B2GReqMsg_Customer_GetLoanRelationNew req);
	
	/**
	 * 债权关系还款流水查询
	 * @param req
	 * @return
	 */
	public B2GResMsg_Payment_QueryRepayJnl queryRepayJnl(B2GReqMsg_Payment_QueryRepayJnl req);
	
	/**
	 * 根据电话查询达飞业务员信息
	 * @param req
	 * @return
	 */
	public B2GResMsg_Customer_QueryUserByPhone queryUserByPhone(B2GReqMsg_Customer_QueryUserByPhone req);
	
	/**
	 * 根据用户id查询达飞业务员信息
	 * @param req
	 * @return
	 */
	public B2GResMsg_Customer_QueryUserById queryUserById(B2GReqMsg_Customer_QueryUserById req);
	
	/**
	 * 根据部门编码查询当前部门及子部门下的达飞业务员
	 * @param req
	 * @return
	 */
	public B2GResMsg_Customer_QueryDeptUserInfo queryDeptUserInfo(B2GReqMsg_Customer_QueryDeptUserInfo req);
	
	/**
	 * 根据部门编码查询当前部门下的达飞业务员
	 * @param req
	 * @return
	 */
	public B2GResMsg_Customer_QueryCurrDeptUserInfo queryCurrDeptUserInfo(B2GReqMsg_Customer_QueryCurrDeptUserInfo req);
	
	/**
	 * 根据部门编码查询当前部门的下级部门
	 * @param req
	 * @return
	 */
	public B2GResMsg_Customer_QueryDeptInfo queryDeptInfo(B2GReqMsg_Customer_QueryDeptInfo req);
	
	/**
	 * 根据部门编码查询当前部门
	 * @param req
	 * @return
	 */
	public B2GResMsg_Customer_QueryCurrDeptInfo queryCurrDeptInfo(B2GReqMsg_Customer_QueryCurrDeptInfo req);
	
	/**
	 * 达飞用户信息增量同步
	 * @param req
	 * @return
	 */
	public B2GResMsg_Customer_IncrementUserInfo incrementUserInfo(B2GReqMsg_Customer_IncrementUserInfo req);
	
	/**
	 * 达飞部门信息增量同步
	 * @param req
	 * @return
	 */
	public B2GResMsg_Customer_IncrementDeptInfo incrementDeptInfo(B2GReqMsg_Customer_IncrementDeptInfo req);
	
	/**
	 * 达飞权限信息增量同步
	 * @param req
	 * @return
	 */
	public B2GResMsg_Customer_IncrementDataMission incrementDataMission(B2GReqMsg_Customer_IncrementDataMission req);
	
	/**
	 * 服务可用测试（用户ng调用url测试服务是否可用）
	 * @param req
	 * @return
	 */
	public B2GResMsg_Payment_ServerUsableCheck serverUsableCheck(B2GReqMsg_Payment_ServerUsableCheck req);
}
