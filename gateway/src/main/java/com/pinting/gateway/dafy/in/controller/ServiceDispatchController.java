package com.pinting.gateway.dafy.in.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.business.out.service.SendBusinessService;
import com.pinting.gateway.dafy.in.model.BaseReqModel;
import com.pinting.gateway.dafy.in.model.BindBankcardResultReqModel;
import com.pinting.gateway.dafy.in.model.BindBankcardResultResModel;
import com.pinting.gateway.dafy.in.model.BuyProductResultReqModel;
import com.pinting.gateway.dafy.in.model.BuyProductResultResModel;
import com.pinting.gateway.dafy.in.model.CustomerWithdrawCheckReqModel;
import com.pinting.gateway.dafy.in.model.CustomerWithdrawCheckResModel;
import com.pinting.gateway.dafy.in.model.CustomerWithdrawResultReqModel;
import com.pinting.gateway.dafy.in.model.CustomerWithdrawResultResModel;
import com.pinting.gateway.dafy.in.model.LoginReqModel;
import com.pinting.gateway.dafy.in.model.LoginResModel;
import com.pinting.gateway.dafy.in.model.RealCertificateResultReqModel;
import com.pinting.gateway.dafy.in.model.RealCertificateResultResModel;
import com.pinting.gateway.dafy.in.model.ReceiveMoneyNoticeReqModel;
import com.pinting.gateway.dafy.in.model.ReceiveMoneyNoticeResModel;
import com.pinting.gateway.dafy.in.model.SysBuyProductNoticeReqModel;
import com.pinting.gateway.dafy.in.model.SysBuyProductNoticeResModel;
import com.pinting.gateway.dafy.in.model.SysReturnMoneyNoticeReqModel;
import com.pinting.gateway.dafy.in.model.SysReturnMoneyNoticeResModel;
import com.pinting.gateway.dafy.in.model.SysWithdrawResultReqModel;
import com.pinting.gateway.dafy.in.model.SysWithdrawResultResModel;
import com.pinting.gateway.dafy.in.util.DafyInConstant;
import com.pinting.gateway.dafy.in.util.DafyInMsgUtil;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Customer_ReceiveMoney;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyBankCard_CardBindResult;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyPayment_BuyProductResult;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyPayment_SysBuyProductNotice;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyPayment_SysReturnMoneyNotice;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyRegister_RealCertificateResult;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Withdraw_CustomerWithdrawCheck;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Withdraw_CustomerWithdrawResult;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_Withdraw_SysWithdrawResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_Customer_ReceiveMoney;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyBankCard_CardBindResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyPayment_BuyProductResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyPayment_SysBuyProductNotice;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyPayment_SysReturnMoneyNotice;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyRegister_RealCertificateResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_Withdraw_CustomerWithdrawCheck;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_Withdraw_CustomerWithdrawResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_Withdraw_SysWithdrawResult;
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
public class ServiceDispatchController {
	private static final String dafyClientKey = "channeldafykey1230";
	private static final String dafyClientSecret = "dafy7987!&Ke6!3";

	private Logger log = LoggerFactory
			.getLogger(ServiceDispatchController.class);
	@Autowired
	private SendBusinessService sendBusinessService;

	@RequestMapping("/dafy/business")
	public @ResponseBody
	String serviceDispatch(HttpServletRequest request,
			HttpServletResponse response) {
		BaseReqModel reqModel = (BaseReqModel) request.getAttribute("reqModel");
		// 待返回报文密文
		String resp = null;
		switch (reqModel.getTransCode()) {
		case DafyInConstant.LOGIN:// 达飞登录 处理
			resp = (String) login((LoginReqModel) reqModel);
			break;
		case DafyInConstant.REAL_CERTIFICATE_RESULT:// 实名认证结果通知 处理
			resp = (String) realCertificateResult((RealCertificateResultReqModel) reqModel);
			break;
		case DafyInConstant.BIND_CARD_RESULT:// 卡绑定结果通知 处理
			resp = (String) cardBindResult((BindBankcardResultReqModel) reqModel);
			break;
		case DafyInConstant.BUY_PRODUCT_RESULT:// 购买产品结果通知 处理
			resp = (String) buyProductResult((BuyProductResultReqModel) reqModel);
			break;
		case DafyInConstant.RECEIVE_MONEY_NOTICE:
			resp = (String) receiveMoneyNotice((ReceiveMoneyNoticeReqModel) reqModel);
			break;
		case DafyInConstant.SYS_WITHDRAW_RESULT: // 系统提现结果通知处理
			resp = (String) sysWithdrawResult((SysWithdrawResultReqModel) reqModel);
			break;
		case DafyInConstant.CUSTOMER_WITHDRAW_RESULT: // 用户提现结果通知处理
			resp = (String) customerWithdrawResult((CustomerWithdrawResultReqModel) reqModel);
			break;
		case DafyInConstant.CUSTOMER_WITHDRAW_CHECK: // 用户提现申请验证
			resp = (String) customerWithdrawCheck((CustomerWithdrawCheckReqModel) reqModel);
			break;
		case DafyInConstant.SYS_BUY_PRODUCT_NOTICE: // 系统购买结果通知处理
			resp = (String) sysBuyProductNotice((SysBuyProductNoticeReqModel) reqModel);
			break;
		case DafyInConstant.SYS_RETURN_MONEY_NOTICE: // 系统回款通知处理
			resp = (String) sysReturnMoneyNotice((SysReturnMoneyNoticeReqModel) reqModel);
			break;
		default:
			break;
		}
		// 返回响应报文（密文）
		return resp;
	}

	private String customerWithdrawCheck(CustomerWithdrawCheckReqModel reqModel) {
		// 组装报文，向business发送
		G2BReqMsg_Withdraw_CustomerWithdrawCheck req = new G2BReqMsg_Withdraw_CustomerWithdrawCheck();
		req.setApplyNo(reqModel.getApplyNo());
		req.setApplyTime(reqModel.getApplyTime());
		req.setCustomerId(reqModel.getCustomerId());
		req.setBankcard(reqModel.getBankcard());
		req.setAmount(reqModel.getAmount());
		req.setTransType(reqModel.getTransType());
		// 向business发起请求通知，告知结果
		G2BResMsg_Withdraw_CustomerWithdrawCheck res = sendBusinessService
				.sendBsCustomerWithdrawCheck(req);
		// 获取business响应吗，并装成返回达飞的响应码
		String respCode = ConstantUtil.BSRESCODE_SUCCESS.equals(res
				.getResCode()) ? DafyInConstant.RETURN_CODE_SUCCESS
				: DafyInConstant.RETURN_CODE_FAIL;
		CustomerWithdrawCheckResModel resModel = new CustomerWithdrawCheckResModel();
		resModel.setCheckResult(res.getCheckResult());
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
		resModel.setResponseTime(new Date());
		return DafyInMsgUtil.packageMsg(resModel);
	}

	private String customerWithdrawResult(
			CustomerWithdrawResultReqModel reqModel) {
		// 组装报文，向business发送
		G2BReqMsg_Withdraw_CustomerWithdrawResult req = new G2BReqMsg_Withdraw_CustomerWithdrawResult();
		req.setApplyNo(reqModel.getApplyNo());
		req.setResult(reqModel.getResult());
		req.setSuccessTime(reqModel.getSuccessTime());
		req.setCustomerId(reqModel.getCustomerId());
		req.setBankcard(reqModel.getBankcard());
		req.setAmount(reqModel.getAmount());
		req.setMoneyType(reqModel.getMoneyType());
		req.setTransType(reqModel.getTransType());
		req.setFailReason(reqModel.getFailReason());
		// 向business发起请求通知，告知结果
		G2BResMsg_Withdraw_CustomerWithdrawResult res = sendBusinessService
				.sendBsCustomerWithdraw(req);
		// 获取business响应吗，并装成返回达飞的响应码
		String respCode = ConstantUtil.BSRESCODE_SUCCESS.equals(res
				.getResCode()) ? DafyInConstant.RETURN_CODE_SUCCESS
				: DafyInConstant.RETURN_CODE_FAIL;
		CustomerWithdrawResultResModel resModel = new CustomerWithdrawResultResModel();
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
		resModel.setResponseTime(new Date());
		return DafyInMsgUtil.packageMsg(resModel);
	}

	private String sysWithdrawResult(SysWithdrawResultReqModel reqModel) {
		// 组装报文，向business发起
		G2BReqMsg_Withdraw_SysWithdrawResult req = new G2BReqMsg_Withdraw_SysWithdrawResult();
		req.setApplyNo(reqModel.getApplyNo());
		req.setMoneyType(reqModel.getMoneyType());
		req.setResult(reqModel.getResult());
		req.setSuccessTime(reqModel.getSuccessTime());
		req.setTransType(reqModel.getTransType());
		req.setFailReason(reqModel.getFailReason());
		req.setAmount(reqModel.getAmount());
		// 向business发起请求通知，告知结果
		G2BResMsg_Withdraw_SysWithdrawResult res = sendBusinessService
				.sendBsSysWithdraw(req);

		// 获得business响应码，并转成返回达飞的响应码
		String respCode = ConstantUtil.BSRESCODE_SUCCESS.equals(res
				.getResCode()) ? DafyInConstant.RETURN_CODE_SUCCESS
				: DafyInConstant.RETURN_CODE_FAIL;
		SysWithdrawResultResModel resModel = new SysWithdrawResultResModel();
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
		resModel.setResponseTime(new Date());
		return DafyInMsgUtil.packageMsg(resModel);
	}

	/**
	 * 达飞登录 处理
	 * 
	 * @param reqModel
	 *            达飞请求
	 * @return 返回响应报文（密文）
	 */
	private String login(LoginReqModel reqModel) {
		log.info("=============达飞登录 处理："+reqModel.toString()+"==============");
		String clientKey = reqModel.getClientKey();
		String clientSecret = reqModel.getClientSecret();

		// 登录数据校验
		LoginResModel resModel = new LoginResModel();
		resModel.setResponseTime(new Date());

		if (StringUtil.isEmpty(clientKey) || StringUtil.isEmpty(clientSecret)
				|| !clientKey.equals(dafyClientKey)
				|| !clientSecret.equals(dafyClientSecret)) {
			// 登录失败
			resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(DafyInConstant.REUTRN_MSG_LOGIN_FAIL);
			log.info("=============达飞登录 处理：登录失败,clientKey:"+clientKey+",clientSecret:"+clientSecret+"==============");
		} else {// 登录成功
			DafyInMsgUtil.genToken();
			resModel.setToken(DafyInMsgUtil.token);
			resModel.setRespCode(DafyInConstant.RETURN_CODE_SUCCESS);
			resModel.setRespMsg(DafyInConstant.RETURN_MSG_LOGIN_SUCCESS);
			log.info("=============达飞登录 处理：登录成功==============");
		}

		return DafyInMsgUtil.packageMsg(resModel);
	}

	/**
	 * 实名认证结果通知 处理
	 * 
	 * @param reqModel
	 *            达飞请求
	 * @return 返回响应报文（密文）
	 */
	private String realCertificateResult(RealCertificateResultReqModel reqModel) {
		G2BReqMsg_DafyRegister_RealCertificateResult req = new G2BReqMsg_DafyRegister_RealCertificateResult();
		String customerIds = reqModel.getCustomerIds();
		String[] idArr = customerIds.split(",");
		List<String> customerIdList = new ArrayList<String>();
		Collections.addAll(customerIdList, idArr);

		req.setCustomerIdList(customerIdList);
		req.setResult(reqModel.getResult());
		// 向business发起请求通知，告知结果
		G2BResMsg_DafyRegister_RealCertificateResult res = sendBusinessService
				.sendBsRegisterResult(req);
		// 获得business响应码，并转成返回达飞的响应码
		String respCode = ConstantUtil.BSRESCODE_SUCCESS.equals(res
				.getResCode()) ? DafyInConstant.RETURN_CODE_SUCCESS
				: DafyInConstant.RETURN_CODE_FAIL;
		RealCertificateResultResModel resModel = new RealCertificateResultResModel();
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
		resModel.setResponseTime(new Date());

		return DafyInMsgUtil.packageMsg(resModel);
	}

	/**
	 * 客户注册结果通知处理
	 * 
	 * @param reqModel
	 *            达飞请求
	 * @return 返回响应报文（密文）
	 */
	private String cardBindResult(BindBankcardResultReqModel reqModel) {
		String data = "";
		// 组装报文，发给business
		G2BReqMsg_DafyBankCard_CardBindResult req = new G2BReqMsg_DafyBankCard_CardBindResult();

		String customerIds = reqModel.getCustomerIds();
		String[] idArr = customerIds.split(",");
		String results = reqModel.getResults();
		String[] resultArr = results.split(",");
		String resultMsgs = reqModel.getResultMsgs();
		String[] msgArr = resultMsgs.split("#####");
		if (idArr.length == resultArr.length && idArr.length == msgArr.length) {
			List<String> customerIdList = new ArrayList<String>();
			Collections.addAll(customerIdList, idArr);
			req.setCustomerIdList(customerIdList);
			List<String> resultList = new ArrayList<String>();
			Collections.addAll(resultList, resultArr);
			req.setResultList(resultList);
			List<String> resultMsgList = new ArrayList<String>();
			Collections.addAll(resultMsgList, msgArr);
			req.setResultMsgList(resultMsgList);

			// 发给business处理，并返回
			G2BResMsg_DafyBankCard_CardBindResult resBusiness = sendBusinessService
					.sendBsCardBindResult(req);
			// 组装报文密文，发给达飞
			BindBankcardResultResModel resModel = new BindBankcardResultResModel();
			// 获得business响应码，并转成返回达飞的响应码
			String respCode = ConstantUtil.BSRESCODE_SUCCESS.equals(resBusiness
					.getResCode()) ? DafyInConstant.RETURN_CODE_SUCCESS
					: DafyInConstant.RETURN_CODE_FAIL;
			resModel.setRespCode(respCode);
			resModel.setRespMsg(ConstantUtil.BSRESCODE_SUCCESS
					.equals(resBusiness.getResCode()) ? DafyInConstant.RETURN_MSG_RESULT_SUCCESS
					: DafyInConstant.RETURN_MSG_RESULT_FAIL);
			resModel.setResponseTime(new Date());
			data = DafyInMsgUtil.packageMsg(resModel);
		} else {
			// 组装异常报文
			BindBankcardResultResModel resModel = new BindBankcardResultResModel();
			resModel.setRespCode(DafyInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(DafyInConstant.RETURN_MSG_RESULT_DATA_ERROR);
			resModel.setResponseTime(new Date());
			data = DafyInMsgUtil.packageMsg(resModel);
		}
		return data;
	}

	/**
	 * 购买产品通知处
	 * 
	 * @param reqModel
	 *            达飞请求
	 * @return 返回响应报文（密文）
	 */
	private String buyProductResult(BuyProductResultReqModel reqModel) {
		log.info("达飞购买后回调：" + reqModel.toString());

		// 组装报文，发给business
		G2BReqMsg_DafyPayment_BuyProductResult req = new G2BReqMsg_DafyPayment_BuyProductResult();
		req.setResult(reqModel.getResult());
		req.setOrderNo(reqModel.getApplyNo());
		req.setBankName(reqModel.getPayChannel());
		req.setAmount(reqModel.getActAmount());
		req.setMoneyType(reqModel.getMoneyType());
		req.setSuccessTime(reqModel.getRequestTime());
		// 发给business处理，并返回
		G2BResMsg_DafyPayment_BuyProductResult resBusiness = sendBusinessService
				.sendBsBuyProductResult(req);
		// 组装报文密文，发给达飞
		BuyProductResultResModel resModel = new BuyProductResultResModel();
		// 获得business响应码，并转成返回达飞的响应码
		String respCode = ConstantUtil.BSRESCODE_SUCCESS.equals(resBusiness
				.getResCode()) ? DafyInConstant.RETURN_CODE_SUCCESS
				: DafyInConstant.RETURN_CODE_FAIL;
		resModel.setRespCode(respCode);
		resModel.setRespMsg(ConstantUtil.BSRESCODE_SUCCESS.equals(resBusiness
				.getResCode()) ? DafyInConstant.RETURN_MSG_RESULT_SUCCESS
				: DafyInConstant.RETURN_MSG_RESULT_FAIL);
		resModel.setResponseTime(new Date());
		return DafyInMsgUtil.packageMsg(resModel);
	}

	/**
	 * 客户回款通知
	 * 
	 * @param reqModel
	 *            达飞请求
	 * @return 返回响应报文（密文）
	 */
	public String receiveMoneyNotice(ReceiveMoneyNoticeReqModel reqModel) {
		log.info("客户回款通知：" + reqModel.toString());

		// 组装报文，发给business
		G2BReqMsg_Customer_ReceiveMoney req = new G2BReqMsg_Customer_ReceiveMoney();
		// 通知列表压入
		req.setDataList(reqModel.getData());
		// 发给business处理，并返回
		G2BResMsg_Customer_ReceiveMoney resBusiness = sendBusinessService
				.sendBsReceiveMoneyResult(req);
		// 组装报文密文，发给达飞
		ReceiveMoneyNoticeResModel resModel = new ReceiveMoneyNoticeResModel();
		// 获得business响应码，并转成返回达飞的响应码
		String respCode = ConstantUtil.BSRESCODE_SUCCESS.equals(resBusiness
				.getResCode()) ? DafyInConstant.RETURN_CODE_SUCCESS
				: DafyInConstant.RETURN_CODE_FAIL;
		resModel.setRespCode(respCode);
		resModel.setRespMsg(ConstantUtil.BSRESCODE_SUCCESS.equals(resBusiness
				.getResCode()) ? DafyInConstant.RETURN_MSG_RESULT_SUCCESS
				: DafyInConstant.RETURN_MSG_RESULT_FAIL);
		resModel.setResponseTime(new Date());

		return DafyInMsgUtil.packageMsg(resModel);
	}
	
	/**
	 * 系统购买产品通知
	 * 
	 * @param reqModel
	 *            达飞请求
	 * @return 返回响应报文（密文）
	 */
	private String sysBuyProductNotice(SysBuyProductNoticeReqModel reqModel) {
		log.info("系统理财购买回调：" + reqModel.toString());

		// 组装报文，发给business
		G2BReqMsg_DafyPayment_SysBuyProductNotice req = new G2BReqMsg_DafyPayment_SysBuyProductNotice();
		req.setResult(reqModel.getResult());
		req.setFinishTime(reqModel.getFinshTime());
		req.setPayPlatform(reqModel.getPayPlatform());
		req.setProductAmount(reqModel.getProductAmount());
		req.setProductCode(reqModel.getProductCode());
		req.setProductOrderNo(reqModel.getProductOrderNo());
		req.setErrorMsg(reqModel.getErrorMsg());
		// 发给business处理，并返回
		G2BResMsg_DafyPayment_SysBuyProductNotice resBusiness = sendBusinessService
				.sendSysBuyProductNotice(req);
		// 组装报文密文，发给达飞
		SysBuyProductNoticeResModel resModel = new SysBuyProductNoticeResModel();
		// 获得business响应码，并转成返回达飞的响应码
		String respCode = ConstantUtil.BSRESCODE_SUCCESS.equals(resBusiness
				.getResCode()) ? DafyInConstant.RETURN_CODE_SUCCESS
				: DafyInConstant.RETURN_CODE_FAIL;
		resModel.setRespCode(respCode);
		resModel.setRespMsg(ConstantUtil.BSRESCODE_SUCCESS.equals(resBusiness
				.getResCode()) ? DafyInConstant.RETURN_MSG_RESULT_SUCCESS
				: DafyInConstant.RETURN_MSG_RESULT_FAIL);
		resModel.setResponseTime(new Date());
		return DafyInMsgUtil.packageMsg(resModel);
	}
	
	/**
	 * 系统回款通知
	 * @param reqModel 达飞请求
	 * @return 返回响应报文（密文）
	 */
	private String sysReturnMoneyNotice(SysReturnMoneyNoticeReqModel reqModel) {
		log.info("云贷系统回款通知：" + reqModel.toString());

		// 组装报文，发给business
		G2BReqMsg_DafyPayment_SysReturnMoneyNotice req = new G2BReqMsg_DafyPayment_SysReturnMoneyNotice();
		req.setChannel(Constants.THIRD_SYS_CHANNEL_YUNDAI);//渠道设置
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
				.getResCode()) ? DafyInConstant.RETURN_CODE_SUCCESS
				: DafyInConstant.RETURN_CODE_FAIL;
		resModel.setRespCode(respCode);
		resModel.setRespMsg(ConstantUtil.BSRESCODE_SUCCESS.equals(resBusiness
				.getResCode()) ? DafyInConstant.RETURN_MSG_RESULT_SUCCESS
				: DafyInConstant.RETURN_MSG_RESULT_FAIL);
		resModel.setResponseTime(new Date());
		return DafyInMsgUtil.packageMsg(resModel);
	}

	public static void main(String[] args) {

	}

	@RequestMapping("/dafy/getString")
	public @ResponseBody
	String getString(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String encryptData = request.getParameter("DATA");
		String returnCode = DafyInMsgUtil.getString(encryptData);
		return returnCode;
	}
}
