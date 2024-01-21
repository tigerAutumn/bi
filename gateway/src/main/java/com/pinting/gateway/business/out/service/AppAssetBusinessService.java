package com.pinting.gateway.business.out.service;

import com.pinting.business.hessian.site.message.*;
import com.pinting.open.pojo.request.asset.MyBonusWithdrawInfoRequest;
import com.pinting.open.pojo.request.asset.WithdrawalIndexRequest;
import com.pinting.open.pojo.response.asset.MyBonusWithdrawInfoResponse;
import com.pinting.open.pojo.response.asset.WithdrawalIndexResponse;

public interface AppAssetBusinessService {
	/**
	 * 查询我的资产页面信息
	 * @param req  ReqMsg_User_AssetInfoQuery对象
	 * @return  
	 */
	public ResMsg_User_AssetInfoQuery assetInfoQuery(ReqMsg_User_AssetInfoQuery req);
	/**
	 * 投资收益列表
	 * @param req  ReqMsg_Invest_EarningsListQuery对象
	 * @return 
	 */
	public ResMsg_Invest_EarningsListQuery dayInvestEarnings(ReqMsg_Invest_EarningsListQuery req);
	/**
	 * 奖励金收益列表
	 * @param req  ReqMsg_Bonus_RecommendBonusListQuery对象
	 * @return 
	 */
	public ResMsg_Bonus_RecommendBonusListQuery myBonus(ReqMsg_Bonus_RecommendBonusListQuery req);
	/**
	 * 奖励金转出到余额
	 * @param req   ReqMsg_Bonus_BonusToJSH对象
	 * @return
	 */
	public ResMsg_Bonus_BonusToJSH myBonusToJSH(ReqMsg_Bonus_BonusToJSH req);
	
	
	/**
	 * 我的投资明细
	 * @param req   ReqMsg_Invest_InvestListQuery对象
	 * @return
	 */
	public ResMsg_Invest_InvestListQuery myInvestment(ReqMsg_Invest_InvestListQuery req);
	
	/**
	 * 我的投资明细
	 * @param req   ReqMsg_TransDetail_QueryByUserId对象
	 * @return
	 */
	public ResMsg_TransDetail_QueryByUserId tradingDetail(ReqMsg_TransDetail_QueryByUserId req);
	
	/**
	 * 我的银行卡
	 * @param req   ReqMsg_TransDetail_QueryByUserId对象
	 * @return
	 */
	public ResMsg_User_BankListQuery bankCard(ReqMsg_User_BankListQuery req);
	
	/**
	 * 安全中心页面信息
	 * @param req  ReqMsg_User_InfoQuery对象
	 * @return
	 */
	public ResMsg_User_InfoQuery safeCenter(ReqMsg_User_InfoQuery req);
	
	/**
	 * 充值预下单
	 * 
	 * @param req  ReqMsg_RegularBuy_Order对象
	 * @return
	 */
	public ResMsg_RegularBuy_Order rechargePreOrder(ReqMsg_RegularBuy_Order req);
	
	/**
	 * 充值正式下单
	 * 
	 * @param req  ReqMsg_RegularBuy_Order
	 * @return
	 */
	public ResMsg_RegularBuy_Order rechargeOrder(ReqMsg_RegularBuy_Order req);
	
    /**
     * 投资购买协议
     * @param req
     * @return
     */
    public ResMsg_Invest_BuyAgreeMent buyAgreeMent(ReqMsg_Invest_BuyAgreeMent req);

    /**
     * 查询账户余额信息
     * @param reqMsg
     * @return
     */
    public ResMsg_User_InfoQuery queryAccountBalance(ReqMsg_User_InfoQuery reqMsg);
    
    /**
     * 银行列表
     * @param req
     * @return
     */
    public ResMsg_Bank_bindBankList queryBindBank(ReqMsg_Bank_bindBankList req);

    /**
     * 回款路径_查询默认回款卡
     * @param req
     * @return
     */
    public ResMsg_Bank_ReturnPath safeReturnBankCard(ReqMsg_Bank_ReturnPath req);
    
    
    /**
     *  账户安全中心_交易密码_设置 
     * @param req  ReqMsg_User_SetUpTraderPassword
     * @return
     */
    public ResMsg_User_SetUpTraderPassword safePayPasswordSet(ReqMsg_User_SetUpTraderPassword req);
    
    
    /**
     *  账户安全中心_交易密码_修改
     * @param req  ReqMsg_Profile_PayPasswordModify
     * @return
     */
    public ResMsg_Profile_PayPasswordModify safePayPasswordChange(ReqMsg_Profile_PayPasswordModify req);
    
    
    /**
     *  账户安全中心_交易密码_修改
     * @param req  ReqMsg_User_FindPayPassword
     * @return
     */
    public ResMsg_User_FindPayPassword safePayPasswordForget(ReqMsg_User_FindPayPassword req);
    
    
    /**
     *  账户安全中心_回款路径_校验交易密码
     * @param req  ReqMsg_User_CheckPayPassword
     * @return
     */
    public ResMsg_User_CheckPayPassword checkPayPassword(ReqMsg_User_CheckPayPassword req);
    
    /**
     * 账户余额_提现
     * @param req   ReqMsg_UserBalance_Withdraw
     * @return
     */
    public ResMsg_UserBalance_Withdraw balanceWithdraw(ReqMsg_UserBalance_Withdraw req);

    /**
     *  账户安全中心_回款路径_修改回款路径
     * @param req  ReqMsg_Bank_SetIsFirstBankCard
     * @return
     */
    public ResMsg_Bank_SetIsFirstBankCard safeReturnPathChange(ReqMsg_Bank_SetIsFirstBankCard req);
    
    /**
     * 账户余额_提现_首页信息
     * @param indexRequest
     * @param indexResponse
     */
    public void withdrawalIndexRequest(WithdrawalIndexRequest indexRequest,
                                       WithdrawalIndexResponse indexResponse);
    
    
    /**
     * 账户余额_提现_首页信息_v1.0.1
     * @param indexRequest
     * @param indexResponse
     */
    
	public void withdrawalIndexRequest_1(WithdrawalIndexRequest indexRequest,
			WithdrawalIndexResponse indexResponse);
	
    /**
     * 账户余额_提现_首页信息_v1.0.2
     * @param indexRequest
     * @param indexResponse
     */
    
	public void withdrawalIndexRequest_2(WithdrawalIndexRequest indexRequest,
			WithdrawalIndexResponse indexResponse);
	
    /**
     * 
     * @Title: messageList 
     * @Description: 消息中心_消息列表
     * @param listRequest
     * @return
     * @throws
     */
    public ResMsg_User_MessageList messageList(ReqMsg_User_MessageList listRequest);
    
    /**
     * 
     * @Title: messageInfo 
     * @Description: 消息中心_消息详情
     * @param infoRequest
     * @return
     * @throws
     */
    public ResMsg_User_MessageInfo messageInfo(ReqMsg_User_MessageInfo infoRequest);
    
    /**
     * 
     * @Title: redPacketList 
     * @Description: 我的红包_红包列表（多个地方都用）
     * @param redRequest
     * @return
     * @throws
     */
    public ResMsg_RedPacketInfo_RedPacketList redPacketList(ReqMsg_RedPacketInfo_RedPacketList redRequest);

	/**
	 * 我的红包_红包列表（单独是红包列表）
	 * @param redRequest
	 * @return
     */
	ResMsg_RedPacketInfo_QueryRedPacketList queryRedPacketList(ReqMsg_RedPacketInfo_QueryRedPacketList redRequest);
    
    /**
     * 债权明细
     * @param req
     * @return
     */
    public ResMsg_Invest_LoadMatch myloadMatch(ReqMsg_Invest_LoadMatch req);
    
    /**
     * 可以显示债权明细的时间,在此时间之前是没有债权明细的
     * @param req
     * @return
     */
    public ResMsg_Invest_SysLoadMatchTime loadMatchTime(ReqMsg_Invest_SysLoadMatchTime req);

    /**
     * 我的银行卡-预绑卡
     * @param req
     * @return
     */
    public ResMsg_Bank_PreBindCard preBindCard(ReqMsg_Bank_PreBindCard req);
    /**
     * 我的银行卡-正式绑卡
     * @param bindCardRequest ReqMsg_Bank_BindCard对象
     * @return
     */
	public ResMsg_Bank_BindCard bindCard(ReqMsg_Bank_BindCard bindCardRequest);
	
	/**
	 * 账户余额 
	 * 提现校验 是否设置交易密码，安全卡绑定
	 * @param reqMsg
	 * @return
	 */
	public ResMsg_Invest_AccountBalance accountBalance(ReqMsg_Invest_AccountBalance reqMsg);
	/**
	 * 我的投资_明细
	 * @param reqInvestListQuery
	 * @return
	 */
	public ResMsg_Invest_InvestEntrustListQuery myInvestmentEntrust(ReqMsg_Invest_InvestEntrustListQuery reqInvestListQuery);

	/**
	 * 交易明细-分期产品回款列表
	 * @param queryZanReturnDetail
	 * @return
     */
	ResMsg_TransDetail_QueryZanReturnDetail queryZanReturnDetail(ReqMsg_TransDetail_QueryZanReturnDetail queryZanReturnDetail);

	/**
	 * 回款计划-列表
	 * @param repaymentPlanList
	 * @return
     */
	public ResMsg_RepaySchedule_RepaymentPlanList repaymentPlanList(ReqMsg_RepaySchedule_RepaymentPlanList repaymentPlanList);

	/**
	 * 回款计划-明细
	 * @param repayScheduleDetails
	 * @return
     */
	public ResMsg_RepaySchedule_RepayScheduleDetails repayScheduleDetails(ReqMsg_RepaySchedule_RepayScheduleDetails repayScheduleDetails);
	/**
	 * 我的资产_获取激活页面数据_存管改造
	 * @param activatePageInfoRequest
	 * @return
	 */
	public ResMsg_User_ActivatePageInfo activatePageInfo(ReqMsg_User_ActivatePageInfo activatePageInfoRequest);
	/**
	 * 我的资产_激活存管账户
	 * @param activateDepAccountRequest
	 * @return
	 */
	public ResMsg_User_ActivateDepAccount activateDepAccount(ReqMsg_User_ActivateDepAccount activateDepAccountRequest);
	/**
	 * 我的奖励金收益_奖励金转出页面数据_存管改造
	 * @param withdrawInfoRequest
	 * @param withdrawInfoResponse
	 */
	public void myBonusWithdrawInfo(MyBonusWithdrawInfoRequest withdrawInfoRequest,MyBonusWithdrawInfoResponse withdrawInfoResponse);
	/**
	 * 我的奖励金收益_奖励金转出
	 * @param req
	 * @return
	 */
	public ResMsg_UserWithdraw_MyBonusWithdraw myBonusWithdraw(ReqMsg_UserWithdraw_MyBonusWithdraw req);

	/**
	 * 优惠券列表
	 * @param req
	 * @return
     */
	ResMsg_Ticket_TicketList ticketList(ReqMsg_Ticket_TicketList req);

	/**
	 * 解绑卡申请——人脸核身验证
	 * @param req
	 * @return
	 */
	ResMsg_Bank_UnbindApplyPoliceVerify unbindApplyPoliceVerify(ReqMsg_Bank_UnbindApplyPoliceVerify req);

	/**
	 * 人脸核身验证上传检测图片
	 * @param req
	 * @return
	 */
	ResMsg_Bank_UploadPicPoliceVerify uploadPicPoliceVerify(ReqMsg_Bank_UploadPicPoliceVerify req);
	
	/**
	 * 我的银行卡-解绑前校验
	 * @author bianyatian
	 * @param req
	 * @return
	 */
	ResMsg_Bank_UnbindCheck unBindCheck(ReqMsg_Bank_UnbindCheck req);
}
