package com.pinting.gateway.reapal.out.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Decoder;

import com.pinting.core.util.StringUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.reapal.out.config.ReapalConfig;
import com.pinting.gateway.reapal.out.enums.ReapalDsfRespStatus;
import com.pinting.gateway.reapal.out.enums.ReapalDsfUrl;
import com.pinting.gateway.reapal.out.model.req.AgentPayQueryReq;
import com.pinting.gateway.reapal.out.model.req.AgentPayReq;
import com.pinting.gateway.reapal.out.model.req.BatchContent;
import com.pinting.gateway.reapal.out.model.resp.AgentPayDetail;
import com.pinting.gateway.reapal.out.model.resp.AgentPayQueryResp;
import com.pinting.gateway.reapal.out.model.resp.AgentPayResp;
import com.pinting.gateway.reapal.out.service.AgentPayServiceClient;
import com.pinting.gateway.reapal.out.util.DsfFunction;
import com.pinting.gateway.reapal.out.util.ReapalSubmit;

@Service("agentPayServiceClient")
public class AgentPayServiceClientImpl implements AgentPayServiceClient {

	@Override
	public AgentPayResp agentPaySubmit(AgentPayReq req, List<BatchContent> batchContent) {
		//协议参数赋值
		String _input_charset = ReapalConfig.input_charset;
		String batchBizid = ReapalConfig.merchant_id;
		String batchVersion = ReapalConfig.batchVersion;
		String batchBiztype = ReapalConfig.bizType;
		req.set_input_charset(_input_charset);
		req.setBatchBizid(batchBizid);
		req.setBatchBiztype(batchBiztype);
		req.setBatchVersion(batchVersion);
		//batchContent转换成字符串后放入req请求对象
		if(batchContent!=null && batchContent.size()>0){
			StringBuffer contentStr = new StringBuffer();
			for (BatchContent content : batchContent) {
				contentStr.append(content.getSerialNo()).append(",")
					.append(content.getAccountNo()).append(",").append(content.getAccountName()).append(",")
					.append(content.getBankName()).append(",").append(content.getBranchBankName()).append(",")
					.append(content.getSubBranchBankName()).append(",").append(content.getAccountType()).append(",")
					.append(content.getAmount()).append(",").append(content.getCurrency()).append(",")
					.append(content.getProvince()).append(",").append(content.getCity()).append(",")
					.append(content.getMobile()).append(",").append(content.getIdType()).append(",")
					.append(content.getIdNo()).append(",").append(content.getUserAgreementNo()).append(",")
					.append(content.getMerchantOrderNo()).append(",").append(content.getNote()).append(",|");
			}
			req.setBatchContent(contentStr.substring(0, contentStr.length()-2));
		}else{
			throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, "代付明细不存在");
		}
		//发起请求
		AgentPayResp resp  = new AgentPayResp();
		String respStr = ReapalSubmit.buildDsfSubmit(req, ReapalDsfUrl.AGENT_PAY);
		if(StringUtil.isEmpty(respStr)){
			throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, "支付通讯异常");
		}
		//解析xml报文
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(respStr);
		} catch (Exception e) {
			throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, "代付结果解析失败");
		}
		Element root = doc.getRootElement();
		String status = root.elementText("status");
		//提交成功
		if(!StringUtil.isEmpty(status) && ReapalDsfRespStatus.SUCC.getCode().equals(status)){
			resp.setStatus(status);
		}
		//提交失败
		else{
			String reason = root.elementText("reason");
			throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, !StringUtil.isEmpty(reason)?reason:"代付失败");
		}	
		return resp;
	}

	@Override
	public AgentPayQueryResp agentPayQuery(AgentPayQueryReq req) {
		//协议参数赋值
		String _input_charset = ReapalConfig.input_charset;
		String batchBizid = ReapalConfig.merchant_id;
		String batchVersion = ReapalConfig.batchVersion;
		req.set_input_charset(_input_charset);
		req.setBatchBizid(batchBizid);
		req.setBatchVersion(batchVersion);
		//发起查询请求
		AgentPayQueryResp resp  = new AgentPayQueryResp();
		String respStr = ReapalSubmit.buildDsfSubmit(req, ReapalDsfUrl.AGENT_PAY_QUERY);
		if(StringUtil.isEmpty(respStr)){
			throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, "代付查询通讯异常");
		}
		//解析报文
		Document doc = null;
		try {
			//解密响应报文
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				String respStrJim = DsfFunction.jim(decoder.decodeBuffer(respStr), "");
				//解析xml报文
				doc = DocumentHelper.parseText(respStrJim);
			} catch (Exception e) {
				//解密失败，考虑代付查询请求失败
				e.printStackTrace();
				//解析xml报文
				doc = DocumentHelper.parseText(respStr);
			}
		} catch (Exception e) {
			throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, "代付查询结果解析失败");
		}
		Element root = doc.getRootElement();
		String status = root.elementText("status");
		//查询失败
		if(!StringUtil.isEmpty(status)){
			String reason = root.elementText("reason");
			throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, !StringUtil.isEmpty(reason)?reason:"代付查询失败");
		}
		//查询成功
		else{
			String batchDate = root.elementText("batchDate");
			String batchCurrnum =  root.elementText("batchCurrnum");
			Element batchContent = root.element("batchContent");
			//组织出金明细信息
			@SuppressWarnings("unchecked")
			List<Element> contentElements = batchContent.elements("detailInfo");
			List<AgentPayDetail> agentPayDetails = new ArrayList<>();
			if(contentElements!=null && contentElements.size()>0){
				for (Element contentElement : contentElements) {
					String content = contentElement.getTextTrim();
					String[] details = content.split(",", 13);
					AgentPayDetail agentPayDetail = new AgentPayDetail();
					agentPayDetail.setTradeNum(details[0]);
					agentPayDetail.setTradeCardnum(details[1]);
					agentPayDetail.setTradeCardname(details[2]);
					agentPayDetail.setTradeBranchbank(details[3]);
					agentPayDetail.setTradeSubbranchbank(details[4]);
					agentPayDetail.setTradeAccountname(details[5]);
					agentPayDetail.setTradeAccounttype(details[6]);
					agentPayDetail.setTradeAmount(details[7]);
					agentPayDetail.setTradeAmounttype(details[8]);
					agentPayDetail.setTradeRemark(details[9]);
					agentPayDetail.setContractUsercode(details[10]);
					agentPayDetail.setTradeFeedbackcode(details[11]);
					agentPayDetail.setTradeReason(details[12]);
					agentPayDetails.add(agentPayDetail);
				}
			}
			//组织响应的数据
			resp.setBatchCurrnum(batchCurrnum);
			resp.setBatchDate(batchDate);
			resp.setDetails(agentPayDetails);
		}
		
		return resp;
	}
	
	

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String batchDate = sdf.format(new Date());
		String batchCurrnum = "DSF111111111111111112";
		String batchCount = "1";
		String batchAmount = "0.01";
		//String batchContent = "1,622315555555555555,张三,中国工商银行,北京分行,朝阳支行,私,0.01,CNY,北京,北京,18805810700,身份证,123456789012345678,111111111111111,10000001,工资";
		BatchContent batchContent = new BatchContent();
		batchContent.setAccountName("丁鹏风");
		batchContent.setAccountNo("6212261202006836705");
		batchContent.setAccountType("私");
		batchContent.setAmount("0.01");
		batchContent.setBankName("中国工商银行");
		batchContent.setBranchBankName("分行");
		batchContent.setCity("杭州");
		batchContent.setCurrency("CNY");
		batchContent.setIdNo("");
		batchContent.setIdType("");
		batchContent.setMerchantOrderNo("");
		batchContent.setMobile("18805810700");
		batchContent.setNote("代付");
		batchContent.setProvince("浙江");
		batchContent.setSerialNo("1");
		batchContent.setSubBranchBankName("杭州临平支行");
		batchContent.setUserAgreementNo("");
		List<BatchContent> batchContents = new ArrayList<BatchContent>();
		batchContents.add(batchContent);
		/*AgentPayReq req = new AgentPayReq();
		req.setBatchAmount(batchAmount);
		req.setBatchCount(batchCount);
		req.setBatchCurrnum(batchCurrnum);
		req.setBatchDate(batchDate);
		new AgentPayServiceClientImpl().agentPaySubmit(req, batchContents);*/
		AgentPayQueryReq queryReq = new AgentPayQueryReq();
		queryReq.setBatchCurrnum("DSF111111111111111112");
		queryReq.setBatchDate(batchDate);
		AgentPayQueryResp resp = new AgentPayServiceClientImpl().agentPayQuery(queryReq);
		System.out.println(resp.getDetails());
	}

}
