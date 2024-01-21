package com.pinting.gateway.out.service;

import com.pinting.gateway.hessian.message.baofoo.*;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public interface BaoFooTransportService {

    /**
     * 发送短信
     * @param req
     * @return
     */
    @Deprecated
    B2GResMsg_BaoFooQuickPay_SendSms sendSms(B2GReqMsg_BaoFooQuickPay_SendSms req);

    /**
     * 预绑卡
     * @param req
     * @return
     */
    B2GResMsg_BaoFooQuickPay_BindCard bindCard(B2GReqMsg_BaoFooQuickPay_BindCard req);

    /**
     * 确认绑卡
     * @param req
     * @return
     */
    B2GResMsg_BaoFooQuickPay_BindCardConfirm bindCardConfirm(B2GReqMsg_BaoFooQuickPay_BindCardConfirm req);


    /**
     * 解绑卡
     * @param req
     * @return
     */
    B2GResMsg_BaoFooQuickPay_UnBindCard unBind(B2GReqMsg_BaoFooQuickPay_UnBindCard req);

    /**
     * 预充值
     * @param req
     * @return
     */
    B2GResMsg_BaoFooQuickPay_QuickPay quickPay(B2GReqMsg_BaoFooQuickPay_QuickPay req);

    /**
     * 确认充值
     * @param req
     * @return
     */
    B2GResMsg_BaoFooQuickPay_QuickPayConfirm quickPayConfirm(B2GReqMsg_BaoFooQuickPay_QuickPayConfirm req);


    /**
     * 代付转账
     * @param req
     * @return
     */
    B2GResMsg_BaoFooQuickPay_Pay4Trans pay4Trans(B2GReqMsg_BaoFooQuickPay_Pay4Trans req);

    /**
     * 合作方代付转账
     * @param req
     * @return
     */
    B2GResMsg_BaoFooQuickPay_PartnerPay4Trans partnerPay4Trans(B2GReqMsg_BaoFooQuickPay_PartnerPay4Trans req);

    /**
     * 同步快捷支付状态
     * @param req
     * @return
     */
    B2GResMsg_BaoFooQuickPay_QuickPayStatusQuery syncQuickPayStauts(B2GReqMsg_BaoFooQuickPay_QuickPayStatusQuery req);

    /**
     * 同步代付支付状态
     * @param req
     * @return
     */
    B2GResMsg_BaoFooQuickPay_Pay4StatusQuery syncPay4Status(B2GReqMsg_BaoFooQuickPay_Pay4StatusQuery req);

    /**
     * 网银
     * @param req
     * @return
     */
    B2GResMsg_BaoFooQuickPay_EBank eBank(B2GReqMsg_BaoFooQuickPay_EBank req);
    
    /**
     * 卡bin查询
     * @param req
     * @return
     */
    B2GResMsg_BaoFooQuickPay_CardBinQuery cardBinQuery(B2GReqMsg_BaoFooQuickPay_CardBinQuery req);
    
    /**
     * 钱包转账(系统)
     * @param req
     * @return
     */
    B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans pay4OnlineTrans(B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans req);

    /**
     * 对账文件下载
     * @param req
     * @return
     */
    B2GResMsg_BaoFooQuickPay_DownLoadFile downLoadFile(B2GReqMsg_BaoFooQuickPay_DownLoadFile req);

    /**
     * 查询宝付账户余额
     * @param req
     * @return
     */
    B2GResMsg_BaoFooQuickPay_BalanceQuery queryBalance(B2GReqMsg_BaoFooQuickPay_BalanceQuery req);

    /**
     * 代扣
     * @param req
     * @return
     */
    B2GResMsg_BaoFooCutpayment_Cutpayment withholding(B2GReqMsg_BaoFooCutpayment_Cutpayment req);

    /**
     * 查询代扣状态
     * @param req
     * @return
     */
    B2GResMsg_BaoFooCutpayment_CutpaymentStatusQuery queryWithholdingStatus(B2GReqMsg_BaoFooCutpayment_CutpaymentStatusQuery req);
    
    
    /**
     * 协议支付（直接支付）
     * @param req
     * @return
     */
    B2GResMsg_BaoFooAgreementPay_DirectAgreementPay directAgreementPay(B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay req);
    
    /**
     * 协议支付-订单结果查询
     * @param req
     * @return
     */
    B2GResMsg_BaoFooAgreementPay_QueryOrder queryOrder(B2GReqMsg_BaoFooAgreementPay_QueryOrder req);
}
