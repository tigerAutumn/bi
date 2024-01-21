package com.pinting.gateway.business.in.facade;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalAgentPay_AgentPayQuery;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalAgentPay_AgentPaySubmit;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalAgentPay_AgentPayQuery;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalAgentPay_AgentPaySubmit;
import com.pinting.gateway.hessian.message.reapal.model.AgentPayQueryDetail;
import com.pinting.gateway.reapal.out.model.req.AgentPayQueryReq;
import com.pinting.gateway.reapal.out.model.req.AgentPayReq;
import com.pinting.gateway.reapal.out.model.req.BatchContent;
import com.pinting.gateway.reapal.out.model.resp.AgentPayDetail;
import com.pinting.gateway.reapal.out.model.resp.AgentPayQueryResp;
import com.pinting.gateway.reapal.out.service.AgentPayServiceClient;

/**
 * 银行代付下单
 * 
 * @Project: gateway
 * @Title: ReapalAgentPayFacade.java
 * @author dingpf
 * @date 2015-11-17 下午6:17:43
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("ReapalAgentPay")
public class ReapalAgentPayFacade {
	@Autowired
	private AgentPayServiceClient agentPayServiceClient;

	/**
	 * 出金提交
	 * @param req
	 * @param resp
	 */
	public void agentPaySubmit(B2GReqMsg_ReapalAgentPay_AgentPaySubmit req,
			B2GResMsg_ReapalAgentPay_AgentPaySubmit resp) {
		
		DecimalFormat df = new DecimalFormat("0.00");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String batchDate = sdf.format(new Date());
		String batchCurrnum = req.getBatchCurrnum();
		String batchCount = String.valueOf(req.getBatchCount());
		String batchAmount = df.format(req.getBatchAmount());
		BatchContent batchContent = new BatchContent();
		batchContent.setAccountName(req.getAccountName());
		batchContent.setAccountNo(req.getAccountNo());
		batchContent.setAccountType(req.getAccountType());
		batchContent.setAmount(df.format(req.getAmount()));
		batchContent.setBankName(req.getBankName());
		batchContent.setBranchBankName("分行");
		batchContent.setCity(req.getCity());
		batchContent.setCurrency("CNY");
		batchContent.setIdNo("");
		batchContent.setIdType("");
		batchContent.setMerchantOrderNo("");
		batchContent.setMobile(StringUtil.isEmpty(req.getMobile())?"":req.getMobile());
		batchContent.setNote(StringUtil.isEmpty(req.getNote())?"":req.getNote());
		batchContent.setProvince(req.getProvince());
		batchContent.setSerialNo(req.getSerialNo());
		batchContent.setSubBranchBankName(req.getSubBranchBankName());
		batchContent.setUserAgreementNo("");
		List<BatchContent> batchContents = new ArrayList<BatchContent>();
		batchContents.add(batchContent);
		AgentPayReq agentPayReq = new AgentPayReq();
		agentPayReq.setBatchAmount(batchAmount);
		agentPayReq.setBatchCount(batchCount);
		agentPayReq.setBatchCurrnum(batchCurrnum);
		agentPayReq.setBatchDate(batchDate);
		
		agentPayServiceClient.agentPaySubmit(agentPayReq, batchContents);

	}
	
	public void agentPayQuery(B2GReqMsg_ReapalAgentPay_AgentPayQuery req,
			B2GResMsg_ReapalAgentPay_AgentPayQuery resp){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String batchDate = sdf.format(req.getBatchDate());
		String batchCurrnum = req.getBatchCurrnum();
		
		AgentPayQueryReq queryReq = new AgentPayQueryReq();
		queryReq.setBatchCurrnum(batchCurrnum);
		queryReq.setBatchDate(batchDate);
		
		AgentPayQueryResp queryResp = agentPayServiceClient.agentPayQuery(queryReq);
		//组织响应的数据
		List<AgentPayQueryDetail> agentPayQueryDetails = new ArrayList<AgentPayQueryDetail>();
		List<AgentPayDetail> agentPayDetails = queryResp.getDetails();
		if(agentPayDetails!=null && agentPayDetails.size()>0){
			for (AgentPayDetail detail : agentPayDetails) {
				AgentPayQueryDetail agentPayQueryDetail = new AgentPayQueryDetail();
				agentPayQueryDetail.setTradeNum(detail.getTradeNum());
				agentPayQueryDetail.setTradeCardnum(detail.getTradeCardnum());
				agentPayQueryDetail.setTradeCardname(detail.getTradeCardname());
				agentPayQueryDetail.setTradeBranchbank(detail.getTradeBranchbank());
				agentPayQueryDetail.setTradeSubbranchbank(detail.getTradeSubbranchbank());
				agentPayQueryDetail.setTradeAccountname(detail.getTradeAccountname());
				agentPayQueryDetail.setTradeAccounttype(detail.getTradeAccounttype());
				agentPayQueryDetail.setTradeAmount(detail.getTradeAmount());
				agentPayQueryDetail.setTradeAmounttype(detail.getTradeAmounttype());
				agentPayQueryDetail.setTradeRemark(detail.getTradeRemark());
				agentPayQueryDetail.setContractUsercode(detail.getContractUsercode());
				agentPayQueryDetail.setTradeFeedbackcode(detail.getTradeFeedbackcode());
				agentPayQueryDetail.setTradeReason(detail.getTradeReason());
				agentPayQueryDetails.add(agentPayQueryDetail);
			}
		}
		resp.setBatchCurrnum(batchCurrnum);
		try {
			resp.setBatchDate(sdf.parse(batchDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		resp.setDetails(agentPayQueryDetails);
		
	}

}
