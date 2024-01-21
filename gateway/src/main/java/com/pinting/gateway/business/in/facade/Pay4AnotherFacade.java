package com.pinting.gateway.business.in.facade;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_Pay4Another_MerchantDfQuery;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_Pay4Another_NewMerchantDf;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_Pay4Another_QueryCheckAccountFile;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_Pay4Another_MerchantDfQuery;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_Pay4Another_NewMerchantDf;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_Pay4Another_QueryCheckAccountFile;
import com.pinting.gateway.hessian.message.pay19.enums.DFOrderStatus;
import com.pinting.gateway.hessian.message.pay19.enums.DFStatus;
import com.pinting.gateway.pay19.out.model.req.CheckAccountFileReq;
import com.pinting.gateway.pay19.out.model.req.MerchantDfQueryReq;
import com.pinting.gateway.pay19.out.model.req.NewMerchantDfReq;
import com.pinting.gateway.pay19.out.model.resp.CheckAccountFileResp;
import com.pinting.gateway.pay19.out.model.resp.MerchantDfQueryResp;
import com.pinting.gateway.pay19.out.model.resp.NewMerchantDfResp;
import com.pinting.gateway.pay19.out.service.FTP4AccountsServiceClient;
import com.pinting.gateway.pay19.out.service.Pay4AnotherServiceClient;
import com.pinting.gateway.pay19.out.util.Pay19HttpUtil;
import com.pinting.gateway.util.Pay19CipherUtil;
import com.pinting.gateway.hessian.message.pay19.model.AccountDetail;
import com.pinting.gateway.hessian.message.pay19.model.TotalAccount;
/**
 * 银行代付下单
 * 
 * @Project: gateway
 * @Title: Pay4AnotherFacade.java
 * @author dingpf
 * @date 2015-11-17 下午6:17:43
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("Pay4Another")
public class Pay4AnotherFacade {
   @Autowired
   private Pay4AnotherServiceClient pay4AnotherServiceClient;
   @Autowired
   private FTP4AccountsServiceClient ftp4AccountsServiceClient;
   
	public void newMerchantDf(B2GReqMsg_Pay4Another_NewMerchantDf req,
			B2GResMsg_Pay4Another_NewMerchantDf res) {
		 NewMerchantDfReq newMerchantDfReq = new NewMerchantDfReq();
		 newMerchantDfReq.setReqTime(DateUtil.formatDateTime(req.getReqTime(), "yyyyMMddHHmmss"));
	        newMerchantDfReq.setMxOrderId(req.getMxOrderId());
	        newMerchantDfReq.setAmount(new DecimalFormat("0.00").format(req.getAmount()));//19付格式需要2位小数
	        newMerchantDfReq.setCardHolder(Pay19CipherUtil.encryptData(
	            req.getCardHolder(),
	            Pay19HttpUtil.merchant_key
	                    + DateUtil.formatDateTime(req.getReqTime(), "yyyyMMddHHmmss")));
	        newMerchantDfReq.setBankCardId(Pay19CipherUtil.encryptData(
	            req.getBankCardId(),
	            Pay19HttpUtil.merchant_key
	                    + DateUtil.formatDateTime(req.getReqTime(), "yyyyMMddHHmmss")));
	        newMerchantDfReq.setAccountType(req.getAccountType().getCode());
	        newMerchantDfReq.setCardType(req.getCardType().getCode());
	        newMerchantDfReq.setBankCode(req.getBankCode());
	        newMerchantDfReq.setSubBankName(req.getSubBankName());
	        newMerchantDfReq.setProvinceId(req.getProvinceId());
	        newMerchantDfReq.setCityId(req.getCityId());
	        newMerchantDfReq.setAlliedBankCode(req.getAlliedBankCode());
	        newMerchantDfReq.setPayType(req.getPayType().getCode());
	        newMerchantDfReq.setTradeDesc(req.getTradeDesc());
	        //newMerchantDfReq.setNotifyUrl(req.getNotifyUrl());
	        newMerchantDfReq.setNotifyUrl(GlobEnvUtil.get("19pay.notice.pay4another"));
	        newMerchantDfReq.setCurrency(req.getCurrency().getCode());
	        newMerchantDfReq.setTradeType(req.getTradeType().getCode());
	        newMerchantDfReq.setMxReserved(req.getMxReserved());
	        newMerchantDfReq.setPersistHandling(req.getPersistHandling().getCode());
	        newMerchantDfReq.setPersistTimeOut("4320");//72小时
	        newMerchantDfReq.setSecurityInfo(req.getSecurityInfo());

	        NewMerchantDfResp resp = pay4AnotherServiceClient.newMerchantDf(newMerchantDfReq);

	        res.setMxOrderId(resp.getMxOrderId());
	        res.setPreOrderTime(DateUtil.parse(resp.getPreOrderTime(), "yyyyMMddHHmmss"));
	        res.setStatus(DFStatus.find(resp.getStatus()));
	        res.setSysOrderId(resp.getSysOrderId());
	        res.setRetCode(resp.getRetCode());

	}
	
	public void merchantDfQuery(B2GReqMsg_Pay4Another_MerchantDfQuery req,
			B2GResMsg_Pay4Another_MerchantDfQuery res) {
        MerchantDfQueryReq reqModel = new MerchantDfQueryReq();
        reqModel.setReqTime(DateUtil.formatDateTime(req.getReqTime(), "yyyyMMddHHmmss"));
        reqModel.setMxOrderId(req.getMxOrderId());
        reqModel.setTradeDate(DateUtil.formatDateTime(req.getTradeDate(), "yyyyMMdd"));

        MerchantDfQueryResp resp = pay4AnotherServiceClient.queryMerchantDf(reqModel);

        res.setAmount(StringUtil.isEmpty(resp.getAmount()) ? 0d : Double.valueOf(resp
            .getAmount()));
        res.setFinishTime(StringUtil.isEmpty(resp.getFinishTime()) ? null : DateUtil.parse(
            resp.getFinishTime(), "yyyyMMddHHmmss"));
        res.setMxOrderId(resp.getMxOrderId());
        res.setOrderStatus(StringUtil.isEmpty(resp.getOrderStatus()) ? null : DFOrderStatus
            .find(resp.getOrderStatus()));
        res.setSysOrderId(resp.getSysOrderId());
	}
	
	public void queryCheckAccountFile(B2GReqMsg_Pay4Another_QueryCheckAccountFile req,
			B2GResMsg_Pay4Another_QueryCheckAccountFile res) {
		
        CheckAccountFileReq reqModel = new CheckAccountFileReq();
        reqModel.setCheckDate(req.getCheckDate());
        CheckAccountFileResp resp = ftp4AccountsServiceClient.downloadCheckAccountFile(reqModel);
        if (resp != null) {
            TotalAccount totalAccount = new TotalAccount();
            List<AccountDetail> accountDetails = new ArrayList<AccountDetail>();
            if (resp.getTotalAccount() != null) {
                totalAccount.setAccountDate(resp.getTotalAccount().getAccountDate());
                totalAccount.setOrderCount(resp.getTotalAccount().getOrderCount());
                totalAccount.setTotalIncome(resp.getTotalAccount().getTotalIncome());
                totalAccount.setTotalDebit(resp.getTotalAccount().getTotalDebit());
                totalAccount.setTotalRefund(resp.getTotalAccount().getTotalRefund());
                totalAccount.setTotalFee(resp.getTotalAccount().getTotalFee());
                totalAccount.setTotalClearing(resp.getTotalAccount().getTotalClearing());
                totalAccount.setTotalSettle(resp.getTotalAccount().getTotalSettle());
            }
            List<com.pinting.gateway.pay19.out.model.resp.AccountDetail> detals = resp.getAccountDetails();
            if (detals != null && detals.size() > 0) {
                for (com.pinting.gateway.pay19.out.model.resp.AccountDetail detail : detals) {
                    AccountDetail accountDetail = new AccountDetail();
                    accountDetail.setOrderNo(detail.getOrderNo());
                    accountDetail.setMpOrderNo(detail.getMpOrderNo());
                    accountDetail.setReqTime(detail.getReqTime());
                    accountDetail.setSubmitTime(detail.getSubmitTime());
                    accountDetail.setFinishTime(detail.getFinishTime());
                    accountDetail.setSettleTime(detail.getSettleTime());
                    accountDetail.setTradeType(detail.getTradeType());
                    accountDetail.setOrderSource(detail.getOrderSource());
                    accountDetail.setCurrency(detail.getCurrency());
                    accountDetail.setAmount(detail.getAmount());
                    accountDetail.setFee(detail.getFee());
                    accountDetail.setSettleAmount(detail.getSettleAmount());
                    accountDetail.setOrderRemark(detail.getOrderRemark());
                    accountDetails.add(accountDetail);
                }
            }
            res.setTotalAccount(totalAccount);
            res.setAccountDetails(accountDetails);
        }
		
	}
	
	
   
}
