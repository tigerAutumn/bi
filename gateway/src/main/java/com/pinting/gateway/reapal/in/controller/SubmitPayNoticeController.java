package com.pinting.gateway.reapal.in.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.business.out.service.SendBusinessService;
import com.pinting.gateway.hessian.message.reapal.G2BReqMsg_ReapalQuickPay_ReapalNotify;
import com.pinting.gateway.hessian.message.reapal.G2BResMsg_ReapalQuickPay_ReapalNotify;
import com.pinting.gateway.reapal.in.enums.NoticeOrderStatus;
import com.pinting.gateway.reapal.in.enums.OrderStatus;
import com.pinting.gateway.reapal.out.config.ReapalConfig;
import com.pinting.gateway.reapal.out.enums.ReapalQuickPayRespCode;
import com.pinting.gateway.reapal.out.util.Decipher;
import com.pinting.gateway.reapal.out.util.Md5Utils;

@Controller
@RequestMapping("reapal/submitPay")
public class SubmitPayNoticeController {

	private static Logger logger = LoggerFactory.getLogger(SubmitPayNoticeController.class);
	
	@Autowired
	private SendBusinessService sendBusinessService;
	
	@RequestMapping("notice")
	@ResponseBody
	public String notice(HttpServletRequest request, HttpServletResponse response) {
		String data = request.getParameter("data");
		String encryptkey = request.getParameter("encryptkey");
		
		try {
			//解析密文数据
			String decryData = Decipher.decryptData(encryptkey,data);
			logger.info("收到Reapal异步通知>>>>>>" + decryData);
			JSONObject jsonObject = JSON.parseObject(decryData);
			String merchant_id = jsonObject.getString("merchant_id");
			String trade_no = jsonObject.getString("trade_no");
			String order_no = jsonObject.getString("order_no");
			String total_fee = jsonObject.getString("total_fee");
			String status = jsonObject.getString("status");
			String result_code = jsonObject.getString("result_code");
			String result_msg = jsonObject.getString("result_msg");
			String sign = jsonObject.getString("sign");
			String notify_id = jsonObject.getString("notify_id");
			//将返回的参数进行验签
			Map<String, String> map = new HashMap<String, String>();
			map.put("merchant_id", merchant_id);
			map.put("trade_no", trade_no);
			map.put("order_no", order_no);
			map.put("total_fee", total_fee);
			map.put("status", status);
			map.put("result_code", result_code);
			map.put("result_msg", result_msg);
			map.put("notify_id", notify_id);
			String mysign = Md5Utils.BuildMysign(map, ReapalConfig.key);
			if(sign.equals(mysign)) {
				G2BReqMsg_ReapalQuickPay_ReapalNotify req = new G2BReqMsg_ReapalQuickPay_ReapalNotify();
				req.setAmount(MoneyUtil.divide(Double.valueOf(total_fee), 100.0d, 2).doubleValue());
				req.setMerchantId(merchant_id);
				req.setOrderNo(order_no);
				req.setTradeNo(trade_no);
				req.setStatus(NoticeOrderStatus.YES.getCode().equalsIgnoreCase(status)?OrderStatus.SUCCESS.getCode():OrderStatus.FAIL.getCode());
				req.setResultCode(result_code);
				if(StringUtil.isNotBlank(result_msg)) {
					req.setResultMsg(result_msg);
				}
				else {
					ReapalQuickPayRespCode code = ReapalQuickPayRespCode.find(result_code);
					req.setResultMsg(code!=null?code.getDescription():"");
				}
				req.setNotifyId(notify_id);
				G2BResMsg_ReapalQuickPay_ReapalNotify res = sendBusinessService.sendReapalQuickPayNotify(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					logger.info("异步通知处理结果返回给融宝:【币港湾订单号:"+order_no+",融宝订单号:"+trade_no+",处理结果:success】");
					return "success";
				}
				else {
					logger.info("异步通知处理结果返回给融宝:【币港湾订单号:"+order_no+",融宝订单号:"+trade_no+",处理结果:fail】");
				}
			}
			else {
				logger.error("异步通知处理结果返回给融宝:【处理结果:fail,异常信息:MD5验证不通过】");
			}
		}catch(Exception e) {
			logger.error("异步通知处理结果返回给融宝:【处理结果:fail,异常信息:"+e.getMessage()+"】");
		}
		return "fail";
	}
}
