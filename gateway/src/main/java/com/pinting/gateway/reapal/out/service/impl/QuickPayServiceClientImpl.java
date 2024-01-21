package com.pinting.gateway.reapal.out.service.impl;

import org.springframework.stereotype.Service;

import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.reapal.out.enums.ReapalUrl;
import com.pinting.gateway.reapal.out.model.req.BindCardSignReq;
import com.pinting.gateway.reapal.out.model.req.CancelCardReq;
import com.pinting.gateway.reapal.out.model.req.MemoryCardSignReq;
import com.pinting.gateway.reapal.out.model.req.QueryOrderResultReq;
import com.pinting.gateway.reapal.out.model.req.ResendCodeReq;
import com.pinting.gateway.reapal.out.model.req.SubmitPayReq;
import com.pinting.gateway.reapal.out.model.resp.BindCardSignResp;
import com.pinting.gateway.reapal.out.model.resp.CancelCardResp;
import com.pinting.gateway.reapal.out.model.resp.MemoryCardSignResp;
import com.pinting.gateway.reapal.out.model.resp.QueryOrderResultResp;
import com.pinting.gateway.reapal.out.model.resp.ResendCodeResp;
import com.pinting.gateway.reapal.out.model.resp.SubmitPayResp;
import com.pinting.gateway.reapal.out.service.QuickPayServiceClient;
import com.pinting.gateway.reapal.out.util.Decipher;
import com.pinting.gateway.reapal.out.util.ReapalMessageUtil;
import com.pinting.gateway.reapal.out.util.ReapalSubmit;

@Service("reapalQuickPayServiceClient")
public class QuickPayServiceClientImpl implements QuickPayServiceClient {

	@Override
	public MemoryCardSignResp memoryCardSignPreOrder(MemoryCardSignReq req){
		try {
			//发送请求
			String post = ReapalSubmit.buildSubmit(req, ReapalUrl.NO_BIND_PRE_ORDER);
			//解密返回的数据
		    String res = Decipher.decryptData(post);
		    //将json串变成对象
		    MemoryCardSignResp resp = (MemoryCardSignResp)ReapalMessageUtil.parseResp(res, "MemoryCardSignResp");
			return resp;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, "预下单通讯异常");
		}
	}

	@Override
	public BindCardSignResp bindCardSignPreOrder(BindCardSignReq req) {
		try {
			//发送请求
			String post = ReapalSubmit.buildSubmit(req, ReapalUrl.BIND_PRE_ORDER);
			//解密返回的数据
		    String res = Decipher.decryptData(post);
		    //将json串变成对象
		    BindCardSignResp resp = (BindCardSignResp)ReapalMessageUtil.parseResp(res, "BindCardSignResp");
			return resp;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, "绑卡签约通讯异常");
		}
	}

	@Override
	public SubmitPayResp submitPay(SubmitPayReq req) {
		try {
			//发送请求
			String post = ReapalSubmit.buildSubmit(req, ReapalUrl.CONFIRM_ORDER);
			//解密返回的数据
		    String res = Decipher.decryptData(post);
		    //将json串变成对象
		    SubmitPayResp resp = (SubmitPayResp)ReapalMessageUtil.parseResp(res, "SubmitPayResp");
			return resp;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, "确认下单支付通讯异常");
		}
	}

	@Override
	public CancelCardResp cancelCard(CancelCardReq req) {
		try {
			//发送请求
			String post = ReapalSubmit.buildSubmit(req, ReapalUrl.CANCEL_CARD);
			//解密返回的数据
		    String res = Decipher.decryptData(post);
		    //将json串变成对象
		    CancelCardResp resp = (CancelCardResp)ReapalMessageUtil.parseResp(res, "CancelCardResp");
			return resp;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, "解绑卡通讯异常");
		}
	}

	@Override
	public QueryOrderResultResp queryOrderRusult(QueryOrderResultReq req) {
		try {
			//发送请求
			String post = ReapalSubmit.buildSubmit(req, ReapalUrl.QUERY_ORDER_RESULT);
			//解密返回的数据
		    String res = Decipher.decryptData(post);
		    //将json串变成对象
		    QueryOrderResultResp resp = (QueryOrderResultResp)ReapalMessageUtil.parseResp(res, "QueryOrderResultResp");
		    return resp;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, "支付结果查询通讯异常");
		}
	}

	@Override
	public ResendCodeResp resendCode(ResendCodeReq req) {
		try {
			//发送请求
			String post = ReapalSubmit.buildSubmit(req, ReapalUrl.RESEND_CODE);
			//解密返回的数据
		    String res = Decipher.decryptData(post);
		    //将json串变成对象
		    ResendCodeResp resp = (ResendCodeResp)ReapalMessageUtil.parseResp(res, "ResendCodeResp");
			return resp;
		}catch(Exception e) {
			e.printStackTrace();
			throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, "重发验证码异常");
		}
	}

}
