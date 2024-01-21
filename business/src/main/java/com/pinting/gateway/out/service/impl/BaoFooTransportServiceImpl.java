package com.pinting.gateway.out.service.impl;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.hessian.message.baofoo.*;
import com.pinting.gateway.out.service.BaoFooTransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by 剑钊 on 2016/8/3.
 */
@Service
public class BaoFooTransportServiceImpl implements BaoFooTransportService{

    @Autowired
    @Qualifier("baoFooGatewayService")
    private HessianService baoFooGatewayService;


    @Override
    public B2GResMsg_BaoFooQuickPay_SendSms sendSms(B2GReqMsg_BaoFooQuickPay_SendSms req) {
        B2GResMsg_BaoFooQuickPay_SendSms res=(B2GResMsg_BaoFooQuickPay_SendSms)baoFooGatewayService.handleMsg(req);
        return res;
    }

    @Override
	public B2GResMsg_BaoFooQuickPay_BindCard bindCard(B2GReqMsg_BaoFooQuickPay_BindCard req) {
        B2GResMsg_BaoFooQuickPay_BindCard res = (B2GResMsg_BaoFooQuickPay_BindCard) baoFooGatewayService.handleMsg(req);
        return res;
    }

    @Override
	public B2GResMsg_BaoFooQuickPay_BindCardConfirm bindCardConfirm(B2GReqMsg_BaoFooQuickPay_BindCardConfirm req) {
        B2GResMsg_BaoFooQuickPay_BindCardConfirm res= (B2GResMsg_BaoFooQuickPay_BindCardConfirm) baoFooGatewayService.handleMsg(req);
        return res;
    }

    @Override
    public B2GResMsg_BaoFooQuickPay_UnBindCard unBind(B2GReqMsg_BaoFooQuickPay_UnBindCard req) {
        B2GResMsg_BaoFooQuickPay_UnBindCard res=(B2GResMsg_BaoFooQuickPay_UnBindCard)baoFooGatewayService.handleMsg(req);
        return res;
    }


    @Override
    public B2GResMsg_BaoFooQuickPay_QuickPay quickPay(B2GReqMsg_BaoFooQuickPay_QuickPay req) {
        B2GResMsg_BaoFooQuickPay_QuickPay res=(B2GResMsg_BaoFooQuickPay_QuickPay)baoFooGatewayService.handleMsg(req);
        return res;
    }

    @Override
    public B2GResMsg_BaoFooQuickPay_QuickPayConfirm quickPayConfirm(B2GReqMsg_BaoFooQuickPay_QuickPayConfirm req) {
        B2GResMsg_BaoFooQuickPay_QuickPayConfirm res=(B2GResMsg_BaoFooQuickPay_QuickPayConfirm) baoFooGatewayService.handleMsg(req);
        return res;
    }

    @Override
    public B2GResMsg_BaoFooQuickPay_Pay4Trans pay4Trans(B2GReqMsg_BaoFooQuickPay_Pay4Trans req) {
        B2GResMsg_BaoFooQuickPay_Pay4Trans res=(B2GResMsg_BaoFooQuickPay_Pay4Trans)baoFooGatewayService.handleMsg(req);
        return res;
    }

    @Override
    public B2GResMsg_BaoFooQuickPay_PartnerPay4Trans partnerPay4Trans(B2GReqMsg_BaoFooQuickPay_PartnerPay4Trans req) {
        return (B2GResMsg_BaoFooQuickPay_PartnerPay4Trans)baoFooGatewayService.handleMsg(req);
    }

    @Override
    public B2GResMsg_BaoFooQuickPay_QuickPayStatusQuery syncQuickPayStauts(B2GReqMsg_BaoFooQuickPay_QuickPayStatusQuery req) {
        B2GResMsg_BaoFooQuickPay_QuickPayStatusQuery res=(B2GResMsg_BaoFooQuickPay_QuickPayStatusQuery)baoFooGatewayService.handleMsg(req);
        return res;
    }

    @Override
    public B2GResMsg_BaoFooQuickPay_Pay4StatusQuery syncPay4Status(B2GReqMsg_BaoFooQuickPay_Pay4StatusQuery req) {
        B2GResMsg_BaoFooQuickPay_Pay4StatusQuery res=(B2GResMsg_BaoFooQuickPay_Pay4StatusQuery) baoFooGatewayService.handleMsg(req);
        return res;
    }

    @Override
    public B2GResMsg_BaoFooQuickPay_EBank eBank(B2GReqMsg_BaoFooQuickPay_EBank req) {
        B2GResMsg_BaoFooQuickPay_EBank res=(B2GResMsg_BaoFooQuickPay_EBank)baoFooGatewayService.handleMsg(req);
        return res;
    }

	@Override
	public B2GResMsg_BaoFooQuickPay_CardBinQuery cardBinQuery(
			B2GReqMsg_BaoFooQuickPay_CardBinQuery req) {
		B2GResMsg_BaoFooQuickPay_CardBinQuery res = (B2GResMsg_BaoFooQuickPay_CardBinQuery)baoFooGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans pay4OnlineTrans(
			B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans req) {
		B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans res = (B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans)baoFooGatewayService.handleMsg(req);
		return res;
	}

    @Override
    public B2GResMsg_BaoFooQuickPay_DownLoadFile downLoadFile(B2GReqMsg_BaoFooQuickPay_DownLoadFile req) {
        return (B2GResMsg_BaoFooQuickPay_DownLoadFile)baoFooGatewayService.handleMsg(req);
    }

    @Override
    public B2GResMsg_BaoFooQuickPay_BalanceQuery queryBalance(B2GReqMsg_BaoFooQuickPay_BalanceQuery req) {
        return (B2GResMsg_BaoFooQuickPay_BalanceQuery)baoFooGatewayService.handleMsg(req);
    }

    @Override
    public B2GResMsg_BaoFooCutpayment_Cutpayment withholding(B2GReqMsg_BaoFooCutpayment_Cutpayment req) {
        return (B2GResMsg_BaoFooCutpayment_Cutpayment)baoFooGatewayService.handleMsg(req);
    }

    @Override
    public B2GResMsg_BaoFooCutpayment_CutpaymentStatusQuery queryWithholdingStatus(B2GReqMsg_BaoFooCutpayment_CutpaymentStatusQuery req) {
        return (B2GResMsg_BaoFooCutpayment_CutpaymentStatusQuery)baoFooGatewayService.handleMsg(req);
    }

	@Override
	public B2GResMsg_BaoFooAgreementPay_DirectAgreementPay directAgreementPay(
			B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay req) {
		 return (B2GResMsg_BaoFooAgreementPay_DirectAgreementPay)baoFooGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_BaoFooAgreementPay_QueryOrder queryOrder(
			B2GReqMsg_BaoFooAgreementPay_QueryOrder req) {
		 return (B2GResMsg_BaoFooAgreementPay_QueryOrder)baoFooGatewayService.handleMsg(req);
	}


}
