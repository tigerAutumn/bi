package com.pinting.gateway.out.service;

import com.pinting.gateway.hessian.message.hfbank.*;

public interface HfbankTransportService {

	/* ============================== 龙猫负责接口开始 =============================== */
    /**
     * 1、标的发布
     * @param req
     * @return
     */
    B2GResMsg_HFBank_Publish publish(B2GReqMsg_HFBank_Publish req);
    
    /**
     * 2、标的成废
     * @param req
     * @return
     */
	B2GResMsg_HFBank_EstablishOrAbandon establishOrAbandon(B2GReqMsg_HFBank_EstablishOrAbandon req);

    /**
     * 3、批量投标
     * @param req
     * @return
     */
    B2GResMsg_HFBank_BatchExtBuy batchExtBuy(B2GReqMsg_HFBank_BatchExtBuy req);

    /**
     * 4、标的出账
     * @param req
     * @return
     */
    B2GResMsg_HFBank_ChargeOff chargeOff(B2GReqMsg_HFBank_ChargeOff req);

    /**
     * 5、标的出账信息修改
     * @param req
     * @return
     */
    B2GResMsg_HFBank_ModifyPayOutAcct modifyPayOutAcct(B2GReqMsg_HFBank_ModifyPayOutAcct req);

    /**
     *  7、标的转让
     * @param req
     * @return
     */
    B2GResMsg_HFBank_TransferDebt transferDebt(B2GReqMsg_HFBank_TransferDebt req);

    /**
     * 8、借款人批量还款
     * @param req
     * @return
     */
    B2GResMsg_HFBank_BatchExtRepay batchExtRepay(B2GReqMsg_HFBank_BatchExtRepay req);

    /**
     * 9、标的还款
     * @param req
     * @return
     */
    B2GResMsg_HFBank_ProdRepay prodRepay(B2GReqMsg_HFBank_ProdRepay req);

    /**
     * 10、平台转账个人
     * @param req
     * @return
     */
    B2GResMsg_HFBank_PlatformTransfer platformTransfer(B2GReqMsg_HFBank_PlatformTransfer req);

    /**
     * 11、平台不同账户转账
     * @param req
     * @return
     */
    B2GResMsg_HFBank_PlatformAccountConverse platformAccountConverse(B2GReqMsg_HFBank_PlatformAccountConverse req);



    /* ============================== 龙猫负责接口结束 =============================== */








    /* ============================== 浣熊负责接口开始 =============================== */
    /**
     * 1. 批量开户（四要素绑卡）
     * 2. 批量开户（实名认证），针对借款人
     * 3. 批量换卡
     * 4. 短验绑卡申请
     * 5. 短验绑卡确认
     * 6. 网关充值请求
     * 7. 充值回调通知				异步通知
     * 8. 快捷充值请求
     * 9. 快捷充值确认
     * 10. 借款人扣款还款（代扣）
     * 11. 借款人扣款还款（代扣）通知	异步通知
     * 12. 批量提现
     * 13. 提现通知					异步通知
     * 14. 订单状态查询
     * 15. 资金余额查询
     * 16. 账户余额明细查询
     * 17. 标的账户余额查询
     * 18. 标的代偿(委托)还款 
     * 19. 借款人还款代偿(委托)
     * 20. 平台提现
     * 21. 平台充值
     */

    /**
     * 1. 批量开户（四要素绑卡）
     * @param req
     */
    B2GResMsg_HFBank_BatchBindCard4Elements batchBindCard4Elements(B2GReqMsg_HFBank_BatchBindCard4Elements req);

    /**
     * 2. 批量开户（实名认证），针对借款人
     * @param req
     */
    B2GResMsg_HFBank_BatchBindCardRealNameAuth batchBindCardRealNameAuth(B2GReqMsg_HFBank_BatchBindCardRealNameAuth req);

    /**
     * 3. 批量换卡
     * @param req
     */
    B2GResMsg_HFBank_BatchChangeCard batchChangeCard(B2GReqMsg_HFBank_BatchChangeCard req);

    /**
     * 4. 短验绑卡申请
     * @param req
     */
    B2GResMsg_HFBank_UserPreBindCard userPreBindCard(B2GReqMsg_HFBank_UserPreBindCard req);

    /**
     * 5. 短验绑卡确认
     * @param req
     */
    B2GResMsg_HFBank_UserBindCard userBindCard(B2GReqMsg_HFBank_UserBindCard req);

    /**
     * 6. 网关充值请求
     * @param req
     */
    B2GResMsg_HFBank_UserGatewayRechargeRequest userGatewayRechargeRequest(B2GReqMsg_HFBank_UserGatewayRechargeRequest req);

    /**
     * 8. 快捷充值请求
     * @param req
     */
    B2GResMsg_HFBank_UserQuickPayPreRecharge userQuickPayPreRecharge(B2GReqMsg_HFBank_UserQuickPayPreRecharge req);

    /**
     * 9. 快捷充值确认
     * @param req
     */
    B2GResMsg_HFBank_UserQuickPayConfirmRecharge userQuickPayConfirmRecharge(B2GReqMsg_HFBank_UserQuickPayConfirmRecharge req);

    /**
     * 10. 借款人扣款还款（代扣）
     * @param req
     */
    B2GResMsg_HFBank_BorrowCutRepayDK borrowCutRepayDK(B2GReqMsg_HFBank_BorrowCutRepayDK req);

    /**
     * 12. 批量提现
     * @param req
     */
    B2GResMsg_HFBank_UserBatchWithdraw userBatchWithdraw(B2GReqMsg_HFBank_UserBatchWithdraw req);

    /**
     * 14. 订单状态查询
     * @param req
     */
    B2GResMsg_HFBank_QueryOrder queryOrder(B2GReqMsg_HFBank_QueryOrder req);

    /**
     * 15. 资金余额查询
     * @param req
     */
    B2GResMsg_HFBank_QueryAccountInfo queryAccountInfo(B2GReqMsg_HFBank_QueryAccountInfo req);

    /**
     * 16. 账户余额明细查询
     * @param req
     */
    B2GResMsg_HFBank_QueryAccountLeftAmountInfo queryAccountLeftAmountInfo(B2GReqMsg_HFBank_QueryAccountLeftAmountInfo req);

    /**
     * 17. 标的账户余额查询
     * @param req
     */
    B2GResMsg_HFBank_QueryProductBalance queryProductBalance(B2GReqMsg_HFBank_QueryProductBalance req);
    
    
    /**
     * 18. 标的代偿（委托）还款
     * @param req
     */
    B2GResMsg_HFBank_CompensateRepay compensateRepay(B2GReqMsg_HFBank_CompensateRepay req);
    
    
    /**
     * 19. 借款人还款代偿（委托）
     * @param req
     */
    B2GResMsg_HFBank_RepayCompensate repayCompensate(B2GReqMsg_HFBank_RepayCompensate req);
    
    /**
     * 20. 平台提现
     * @param req
     */
    B2GResMsg_HFBank_PlatWithDraw platWithDraw(B2GReqMsg_HFBank_PlatWithDraw req);
    /**
     * 21. 平台充值
     * */
    B2GResMsg_HFBank_PlatCharge platCharge(B2GReqMsg_HFBank_PlatCharge req);
    /* ============================== 浣熊负责接口结束 =============================== */
    
    /**
     * 对账文件资金进出数据 
     * @param req
     */
    B2GResMsg_HFBank_FundDataCheck fundDataCheck(B2GReqMsg_HFBank_FundDataCheck req);

    /**
     * 对账文件账户余额数据
     * @param req
     * @return
     */
    B2GResMsg_HFBank_BalanceInfo balanceInfo(B2GReqMsg_HFBank_BalanceInfo req);
    
    /**
     * 清算状态查询
     * @param req
     */
    B2GResMsg_HFBank_QueryLiquidationInfo queryLiquidationInfo(B2GReqMsg_HFBank_QueryLiquidationInfo req);
    
    /**
     * 资金变动明细查询
     * @param req
     */
    B2GResMsg_HFBank_GetFundList getFundList(B2GReqMsg_HFBank_GetFundList req);
    
    /**
     * 绑卡
     * @param req
     * */
    B2GResMsg_HFBank_UserAddCard userAddCard(B2GReqMsg_HFBank_UserAddCard req);

    /**
     * 更新用户信息
     * @param req
     * @return
     */
    B2GResMsg_HFBank_UpdatePlatAcct updatePlatAcct(B2GReqMsg_HFBank_UpdatePlatAcct req);


    /**
     * 充值订单状态查询
     * @param req
     * @return
     */
    B2GResMsg_HFBank_GetFundOrderInfo getFundOrderInfo(B2GReqMsg_HFBank_GetFundOrderInfo req);

}