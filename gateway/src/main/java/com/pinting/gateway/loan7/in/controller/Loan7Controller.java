package com.pinting.gateway.loan7.in.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.core.util.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.core.util.StringUtil;
import com.pinting.gateway.business.out.service.SendBusinessService;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyPayment_SysReturnMoneyNotice;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyPayment_SysReturnMoneyNotice;
import com.pinting.gateway.loan7.in.model.BaseReqModel;
import com.pinting.gateway.loan7.in.model.LoginReqModel;
import com.pinting.gateway.loan7.in.model.LoginResModel;
import com.pinting.gateway.loan7.in.model.SysReturnMoneyNoticeReqModel;
import com.pinting.gateway.loan7.in.model.SysReturnMoneyNoticeResModel;
import com.pinting.gateway.loan7.in.util.DepLoan7InMsgUtil;
import com.pinting.gateway.loan7.in.util.Loan7InConstant;
import com.pinting.gateway.loan7.in.util.Loan7InMsgUtil;
import com.pinting.gateway.util.Constants;

/**
 * 根据报文交易码，进行服务分发
 * 
 * @Project: gateway
 * @Title: ServiceDispatchController.java
 * @author dingpf
 * @date 2015-2-10 下午6:45:28
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Controller
public class Loan7Controller {
	private static final String dafyClientKey = "channeldafykey1230";
	private static final String dafyClientSecret = "dafy7987!&Ke6!3";

	private Logger log = LoggerFactory.getLogger(Loan7Controller.class);
	@Autowired
	private SendBusinessService sendBusinessService;

	@RequestMapping("/loan7/business")
	public @ResponseBody
	String serviceDispatch(HttpServletRequest request,
			HttpServletResponse response) {
		BaseReqModel reqModel = (BaseReqModel) request.getAttribute("reqModel");
		// 待返回报文密文
		String resp = null;
		switch (reqModel.getTransCode()) {
		case Loan7InConstant.LOGIN:// 7贷登录 处理
			resp = (String) login((LoginReqModel) reqModel);
			break;
		case Loan7InConstant.SYS_RETURN_MONEY_NOTICE: // 7贷系统回款通知处理
			resp = (String) sysReturnMoneyNotice((SysReturnMoneyNoticeReqModel) reqModel);
			break;
		default:
			break;
		}
		// 返回响应报文（密文）
		return resp;
	}

	/**
	 * 7贷登录 处理
	 * 
	 * @param reqModel
	 * @return 返回响应报文（密文）
	 */
	private String login(LoginReqModel reqModel) {
		String clientKey = reqModel.getClientKey();
		String clientSecret = reqModel.getClientSecret();

		// 登录数据校验
		LoginResModel resModel = new LoginResModel();
		resModel.setResponseTime(new Date());

		if (StringUtil.isEmpty(clientKey) || StringUtil.isEmpty(clientSecret)
				|| !clientKey.equals(dafyClientKey)
				|| !clientSecret.equals(dafyClientSecret)) {
			// 登录失败
			resModel.setRespCode(Loan7InConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(Loan7InConstant.REUTRN_MSG_LOGIN_FAIL);
		} else {// 登录成功
			Loan7InMsgUtil.genToken();
			resModel.setToken(Loan7InMsgUtil.token);
			resModel.setRespCode(Loan7InConstant.RETURN_CODE_SUCCESS);
			resModel.setRespMsg(Loan7InConstant.RETURN_MSG_LOGIN_SUCCESS);
		}

		return Loan7InMsgUtil.packageMsg(resModel);
	}
		
	/**
	 * 系统回款通知
	 * @param reqModel
	 * @return 返回响应报文（密文）
	 */
	private String sysReturnMoneyNotice(SysReturnMoneyNoticeReqModel reqModel) {
		log.info("7贷系统回款通知：" + reqModel.toString());

		// 组装报文，发给business
		G2BReqMsg_DafyPayment_SysReturnMoneyNotice req = new G2BReqMsg_DafyPayment_SysReturnMoneyNotice();
		req.setChannel(Constants.THIRD_SYS_CHANNEL_7DAI);//渠道设置
		req.setAmount(reqModel.getAmount());
		req.setMerchantId(reqModel.getMerchantId());
		req.setPayFinshTime(reqModel.getPayFinshTime());
		req.setPayReqTime(reqModel.getPayReqTime());
		req.setPayPlatform(reqModel.getPayPlatform());
		req.setPayOrderNo(reqModel.getPayOrderNo());
		req.setProductAmount(reqModel.getProductAmount());
		req.setProductCode(reqModel.getProductCode());
		req.setProductOrderNo(reqModel.getProductOrderNo());
		req.setProductInterest(reqModel.getProductInterest());
		req.setProductReturnTerm(reqModel.getProductReturnTerm());
		// 发给business处理，并返回
		G2BResMsg_DafyPayment_SysReturnMoneyNotice resBusiness = sendBusinessService
				.sendSysReturnMoneyNotice(req);
		// 组装报文密文，发给达飞
		SysReturnMoneyNoticeResModel resModel = new SysReturnMoneyNoticeResModel();
		// 获得business响应码，并转成返回达飞的响应码
		String respCode = ConstantUtil.BSRESCODE_SUCCESS.equals(resBusiness
				.getResCode()) ? Loan7InConstant.RETURN_CODE_SUCCESS
				: Loan7InConstant.RETURN_CODE_FAIL;
		String respMsg = ConstantUtil.BSRESCODE_SUCCESS.equals(resBusiness
				.getResCode()) ? Loan7InConstant.RETURN_MSG_RESULT_SUCCESS
				: Loan7InConstant.RETURN_MSG_RESULT_FAIL;
		//重复通知设为成功
		if("920006".equals(resBusiness.getResCode())){
			respCode = Loan7InConstant.RETURN_CODE_SUCCESS;
			respMsg = Loan7InConstant.RETURN_MSG_RESULT_REPEAT;
		}
		resModel.setRespCode(respCode);
		resModel.setRespMsg(respMsg);
		resModel.setResponseTime(new Date());
		return Loan7InMsgUtil.packageMsg(resModel);
	}
	
	
	@RequestMapping("/loan7/getString")
	public @ResponseBody
	String getString(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String encryptData = request.getParameter("DATA");
		String returnCode = Loan7InMsgUtil.getString(encryptData);
		return returnCode;
	}
	
}
