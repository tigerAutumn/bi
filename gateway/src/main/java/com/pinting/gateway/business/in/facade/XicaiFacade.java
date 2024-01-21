package com.pinting.gateway.business.in.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.pinting.core.exception.PTException;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.gateway.hessian.message.xicai.B2GReqMsg_Xicai_BindUserCallBack;
import com.pinting.gateway.hessian.message.xicai.B2GReqMsg_Xicai_InvestCallBack;
import com.pinting.gateway.hessian.message.xicai.B2GReqMsg_Xicai_PushP2P;
import com.pinting.gateway.hessian.message.xicai.B2GResMsg_Xicai_BindUserCallBack;
import com.pinting.gateway.hessian.message.xicai.B2GResMsg_Xicai_InvestCallBack;
import com.pinting.gateway.hessian.message.xicai.B2GResMsg_Xicai_PushP2P;
import com.pinting.gateway.xicai.out.enums.XicaiRespCode;
import com.pinting.gateway.xicai.out.helper.Constants;
import com.pinting.gateway.xicai.out.model.BindUserCallBackReq;
import com.pinting.gateway.xicai.out.model.InvestCallBackReq;
import com.pinting.gateway.xicai.out.model.PushP2PReq;
import com.pinting.gateway.xicai.out.service.XicaiServiceClient;

import net.sf.json.JSONObject;

@Component("Xicai")
public class XicaiFacade {

	@Autowired
	@Qualifier("xicaiServiceClient")
	private XicaiServiceClient xicaiServiceClient;
	
	/**
	 * 
	 * @Title: pushP2P 
	 * @Description: 推送产品ID接口
	 * @param req
	 * @param res
	 * @throws
	 */
	public void pushP2P(B2GReqMsg_Xicai_PushP2P req, B2GResMsg_Xicai_PushP2P res) {
		PushP2PReq pushReq = new PushP2PReq();
		pushReq.setPid(req.getPid());
		JSONObject jsonObject = xicaiServiceClient.pushP2P(pushReq);
		//System.out.println(jsonObject);
		if(!XicaiRespCode.SUCCESS_CODE_0.getCode().equals(jsonObject.get("code").toString())) {
			new PTException(jsonObject.get("code").toString(),(String)jsonObject.get("msg"));
		}
	}
	
	public void investCallBack(B2GReqMsg_Xicai_InvestCallBack req, B2GResMsg_Xicai_InvestCallBack res) {
		InvestCallBackReq investReq = new InvestCallBackReq();
		investReq.setId(String.valueOf(req.getSubAccountId()));
		investReq.setCommision(String.valueOf(req.getCommision()));
		investReq.setEarnings(String.valueOf(req.getEarnings()));
		investReq.setInvestamount(String.valueOf(req.getInvestamount()));
		investReq.setPhone(String.valueOf(req.getPhone()));
		investReq.setPid(String.valueOf(req.getPid()));
		//investReq.setUrl(Constants.PRODUCT_PC_BASE_URL+"?id="+req.getPid()+"&agent=xicai");
		investReq.setUrl(GlobEnvUtil.get("product.pc.bsae.url")+"?id="+req.getPid()+"&agent=xicai");
		JSONObject jsonObject = xicaiServiceClient.investCallBack(investReq);
		if(!XicaiRespCode.SUCCESS_CODE_0.getCode().equals(jsonObject.get("code").toString())) {
			new PTException(jsonObject.get("code").toString(),(String)jsonObject.get("ErrorMessage"));
		}
	}
	
	
	public void bindUserCallBack(B2GReqMsg_Xicai_BindUserCallBack req, B2GResMsg_Xicai_BindUserCallBack res) {
		BindUserCallBackReq bindUserCallBackReq = new BindUserCallBackReq();
		/**
		 * 	private String phone;
			
			private String name;
			
			private String result;
			
			private String display;
		 */
		bindUserCallBackReq.setPhone(req.getPhone());
		bindUserCallBackReq.setName(req.getPhone());
		bindUserCallBackReq.setResult(String.valueOf(req.getResult()));
		bindUserCallBackReq.setDisplay(req.getDisplay());
		JSONObject jsonObject = xicaiServiceClient.bindUserCallBack(bindUserCallBackReq);
		if(!XicaiRespCode.SUCCESS_CODE_0.getCode().equals(jsonObject.get("code").toString())) {
			new PTException(jsonObject.get("code").toString(),(String)jsonObject.get("ErrorMessage"));
		}
	}
}
