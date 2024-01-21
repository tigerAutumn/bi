package com.pinting.gateway.hfbank.out.service;

import com.pinting.gateway.hfbank.out.model.*;

/**
 * 
 * 自主放款相关业务，向达飞发起请求 接口服务
 * @Project: gateway
 * @Title: SendDafyLoanService.java
 * @author Dragon & cat
 * @date 2016-11-24
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface HfbankOutService {

	/**
	 * 账单同步
	 * @param req 请求数据 RepaySynchronizationReqModel
	 * @return RepaySynchronizationResModel
	 */
	public QueryBillResModel queryBill(QueryBillReqModel req);
	
	/* ============================== 浣熊写的接口开始 =============================== */
	/**
	 * 1. 批量开户（四要素绑卡）
	 * 2. 批量开户（实名认证），针对借款人
	 * 3. 批量换卡
	 * 4. 短验绑卡申请
	 * 5. 短验绑卡确认
	 * 6. 网关充值请求
	 * 7. 充值回调通知 异步通知，不在此类内
	 * 		@see com.pinting.gateway.hfbank.in.service.HfbankInService
	 * 8. 快捷充值请求
	 * 9. 快捷充值确认
	 * 10. 借款人扣款还款（代扣）
	 * 11. 借款人扣款还款（代扣）通知 异步通知，不在此类内
	 * 		@see com.pinting.gateway.hfbank.in.service.HfbankInService
	 * 12. 批量提现
	 * 13. 提现通知 异步通知，不在此类内
	 * 		@see com.pinting.gateway.hfbank.in.service.HfbankInService
	 * 14. 订单状态查询
	 * 15. 资金余额查询
	 * 16. 账户余额明细查询
	 * 17. 标的账户余额查询
	 * 18. 平台提现
	 * 19. 绑卡
	 * 20. 客户信息变更
	 */

	/**
	 * 1. 批量开户（四要素绑卡）
	 * @param req
	 * @return
     */
	BatchRegistExtResModel batchBindCard4Elements(BatchRegistExtReqModel req);

	/**
	 * 2. 批量开户（实名认证），针对借款人
	 * @param req
	 * @return
     */
	BatchRegistExt3ResModel batchBindCardRealNameAuth(BatchRegistExt3ReqModel req);

	/**
	 * 3. 批量换卡
	 * @param req
	 * @return
     */
	BatchUpdateCardExtResModel batchChangeCard(BatchUpdateCardExtReqModel req);

	/**
	 * 4. 短验绑卡申请
	 * @param req
	 * @return
     */
	GetBinkCardCode2ResModel userPreBindCard(GetBinkCardCode2ReqModel req);

	/**
	 * 5. 短验绑卡确认
	 */
	AddUser2ResModel userBindCard(AddUser2ReqModel req);

	/**
	 * 6. 网关充值请求
	 * @param req
	 * @return
     */
	GatewayRechargeRequestResModel userGatewayRechargeRequest(GatewayRechargeRequestReqModel req);

	/**
	 * 8. 快捷充值请求
	 * @param req
	 * @return
     */
	DirectQuickRequestResModel userQuickPayPreRecharge(DirectQuickRequestReqModel req);

	/**
	 * 9. 快捷充值确认
	 * @param req
	 * @return
     */
	DirectQuickConfirmResModel userQuickPayConfirmRecharge(DirectQuickConfirmReqModel req);

	/**
	 * 10. 借款人扣款还款（代扣）
	 * @param req
	 * @return
     */
	BorrowCutRepayResModel borrowCutRepayDK(BorrowCutRepayReqModel req);

	/**
	 * 12. 批量提现
	 * @param req
	 * @return
     */
	BatchWithdrawExtResModel userBatchWithdraw(BatchWithdrawExtReqModel req);

	/**
	 * 14. 订单状态查询
	 * @param req
	 * @return
	 */
	QueryOrderResModel queryOrder(QueryOrderReqModel req);

	/**
	 * 15. 资金余额查询
	 * @param req
	 * @return
	 */
	GetAccountInfoResModel queryAccountInfo(GetAccountInfoReqModel req);

	/**
	 * 16. 账户余额明细查询
	 * @param req
	 * @return
     */
	GetAccountNBalaceResModel queryAccountLeftAmountInfo(GetAccountNBalaceReqModel req);

	/**
	 * 17. 标的账户余额查询
	 * @param req
	 * @return
	 */
	GetProductNBalanceResModel queryProductBalance(GetProductNBalanceReqModel req);

	

	/* ============================== 浣熊写的接口结束 =============================== */
	
	/* ============================== 龙猫写的接口开始 =============================== */
	/**
	 * 1. 标的发布
	 * @param reqModel
	 * @return
	 */
	public PublishResModel publish(PublishReqModel reqModel);
	/**
	 * 2、标的成废
	 * @param reqModel
	 * @return
	 */
	public EstablishOrAbandonResModel establishOrAbandon(
			EstablishOrAbandonReqModel reqModel);
	/**
	 * 3、批量投标
	 * @param reqModel
	 * @return
	 */
	public BatchExtBuyResModel batchExtBuy(BatchExtBuyReqModel reqModel);
	/**
	 * 4、标的出账
	 * @param reqModel
	 * @return
	 */
	public ChargeOffResModel chargeOff(ChargeOffReqModel reqModel);
	/**
	 * 5、标的出账信息修改
	 * @param reqModel
	 * @return
	 */
	public ModifyPayOutAcctResModel modifyPayOutAcct(
			ModifyPayOutAcctReqModel reqModel);
	/**
	 * 7、标的转让
	 * @param reqModel
	 * @return
	 */
	public TransferDebtResModel transferDebt(TransferDebtReqModel reqModel);
	/**
	 * 8、借款人批量还款
	 * @param reqModel
	 * @return
	 */
	public BatchExtRepayResModel batchExtRepay(BatchExtRepayReqModel reqModel);
	/**
	 * 9、标的还款
	 * @param reqModel
	 * @return
	 */
	public ProdRepayResModel prodRepay(ProdRepayReqModel reqModel);
	/**
	 * 10、平台转账个人
	 * @param reqModel
	 * @return
	 */
	public PlatformTransferResModel platformTransfer(
			PlatformTransferReqModel reqModel);
	/**
	 * 11、平台不同账户转账
	 * @param reqModel
	 * @return
	 */
	public PlatformAccountConverseResModel platformAccountConverse(
			PlatformAccountConverseReqModel reqModel);
	
	
	/**
	 * 12、4.2.2.标的代偿（委托）还款
	 * @param reqModel
	 * @return
	 */
	public CompensateRepayResModel compensateRepay(
			CompensateRepayReqModel reqModel);
	
	
	/**
	 * 13、4.2.3.借款人还款代偿（委托）
	 * @param reqModel
	 * @return
	 */
	public RepayCompensateResModel repayCompensate(
			RepayCompensateReqModel reqModel);
	
	/** 
	 * 18、4.7.4平台提现 
	 * @param reqModel
	 * @return
	 * */
	public PlatWithDrawResModel platWithdraw(PlatWithDrawReqModel req);
	/** 
	 * 19、4.7.3平台提充值
	 * @param reqModel
	 * @return
	 * */
	public PlatChargeResModel platCharge(PlatChargeReqModel req);
	/** 
	 * 20、绑卡
	 * @param reqModel
	 * @return
	 * */
	public GetBinkCardInfoResModel addBankCard(GetBinkCardInfoReqModel req);

	/**
	 * 21、4.1.8 客户信息变更
	 * @param req
	 * @return
     */
	public UpdatePlatAcctResModel updatePlatAcct(UpdatePlatAcctReqModel req);
	
	/* ============================== 龙猫写的接口结束=============================== */

	/**
	 * 4.8.1.对账文件资金进出数据
	 */
	public FundDataCheckResModel fundDataCheck(FundDataCheckReqModel req);

	/**
	 * 4.8.2.对账文件账户余额数据
	 * @param req
	 * @return
     */
	public BalanceInfoResModel balanceInfo(BalanceInfoReqModel req);
	
	/**
	 * 4.8.6.清算状态查询
	 * @param req
	 * @return
	 */
	public LiquidationInfoResModel queryLiquidationInfo(LiquidationInfoReqModel req);
	
	/**
	 * 4.6.1 资金变动明细查询
	 * */
	public GetFundListResModel getFundList(GetFundListReqModel req);

	/**
	 * 4.6.9 充值订单状态查询
	 * @param reqModel
	 * @return
	 */
	QueryFundOrderResModel queryFundOrderInfo(QueryFundOrderReqModel reqModel);
}
