package com.pinting.business.service.loan;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.model.LnCompensateDetail;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_AgreementNotice;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_AgreementNotice;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_AgreementNotice;
import com.pinting.gateway.hessian.message.loan7.G2BResMsg_DepLoan7_AgreementNotice;

import java.util.List;

/**
 * Created by shh on 2016/12/23 11:36.
 * 代偿协议相关服务（云贷、7贷）
 * 代偿协议生成、通知、查询
 */
public interface LateRepayAgreementService {

    /**
     * 云贷查询代偿协议地址（云贷）
     * @param res 
     * @param req
     */
    void findAgreementUrls(G2BReqMsg_DafyLoan_AgreementNotice req, G2BResMsg_DafyLoan_AgreementNotice res);

    /**
     * 7贷查询代偿协议地址（7贷）
     * @param res
     * @param req
     */
    void findSevenAgreementUrls(G2BReqMsg_DepLoan7_AgreementNotice req, G2BResMsg_DepLoan7_AgreementNotice res);

    //==========================代偿协议重新生成合规前的版本 start==========================

    /**
     * 生成代偿协议-收款确认函服务费（云贷、7贷代偿协议重新生成）
     * @param lnCompensateDetail
     * @param orderNo
     * @param partnerEnum
     */
    void renewReceiptConfirmServiceFee2Pdf(LnCompensateDetail lnCompensateDetail, String orderNo, PartnerEnum partnerEnum);

    /**
     * 生成代偿协议-收款确认函债转（云贷、7贷代偿协议重新生成）
     * @param lnCompensateDetail
     * @param orderNo
     * @param partnerEnum
     */
    void renewCreditorNotice2Pdf(LnCompensateDetail lnCompensateDetail, String orderNo, PartnerEnum partnerEnum);

    /**
     * 生成代偿协议-债权转让通知书（云贷、7贷代偿协议重新生成）
     * @param lnCompensateDetail
     * @param orderNo
     * @param partnerEnum
     */
    void renewReceiptConfirmLate2Pdf(LnCompensateDetail lnCompensateDetail, String orderNo, PartnerEnum partnerEnum);

    /**
     * 生成代偿协议-债权转让协议（云贷、7贷代偿协议重新生成）
     * @param lnCompensateDetail
     * @param orderNo
     * @param partnerEnum
     */
    void renewReditor2Pdf(LnCompensateDetail lnCompensateDetail, String orderNo, PartnerEnum partnerEnum);

    /**
     * 代偿协议地址通知给云贷（云贷、7贷）代偿协议重新生成调用
     * @param orderNo 代偿编号
     * @param detailList 代偿通知明细
     * @param partnerEnum
     */
    void renewNotifyAgreementUrls(String orderNo, List<LnCompensateDetail> detailList, PartnerEnum partnerEnum);

    //==========================代偿协议重新生成合规前的版本 end==========================

}