package com.pinting.gateway.reapal.in.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.reapal.out.config.ReapalConfig;
import com.pinting.gateway.reapal.out.enums.ReapalQuickPayRespCode;
import com.pinting.gateway.reapal.out.util.Decipher;
import com.pinting.gateway.reapal.out.util.Md5Utils;
import com.pinting.open.base.util.DESUtil;

@Controller
@RequestMapping("reapal")
public class CertifyController {
	
	private static Logger logger = LoggerFactory.getLogger(CertifyController.class);
	
//	@Autowired
//	private QuickPayServiceClient service;
//
//	@RequestMapping("certify")
//	@ResponseBody
//	public String certify(HttpServletRequest request, HttpServletResponse response) {
//		MemoryCardSignReq req = new MemoryCardSignReq();
//		req.setMerchant_id(ReapalConfig.merchant_id);
//		req.setCard_no("6225885863062477");
//		req.setOwner("舒煌辉");
////		req.setCard_no("6226095710997003");
////		req.setOwner("朱媛");
//		req.setCert_type("01");
//		req.setCert_no("330124198810304815");
//		req.setPhone("13575759159");
////		req.setCert_no("330781198910221128");
////		req.setPhone("15158191349");
//		req.setOrder_no(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
//		req.setTranstime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//		req.setCurrency("156");
//		req.setTitle("储蓄卡签约测试");
//		req.setBody("储蓄卡签约测试");
//		req.setMember_id("123456");
//		req.setTerminal_type("web");
//		req.setTerminal_info("null_B4-6D-83-C3-8F-C0_null");
//		req.setMember_ip("183.129.222.138");
//		req.setSeller_email(ReapalConfig.seller_email);
//		req.setToken_id(ReapalUtil.getUUID());
//		req.setTotal_fee(1);
//		req.setNotify_url(ReapalConfig.notify_url);
//		MemoryCardSignResp resp = service.memoryCardSignPreOrder(req);
//		if("0000".equals(resp.getResult_code())) {
//			String html = null;
//			try {
//				html = ReapalForH5.BuildFormH5(ReapalConfig.merchant_id, req.getOrder_no(), resp.getBind_id(), req.getMember_id(), "mobile", ReapalConfig.certify_android_return_url);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return html;
//		}
//		else {
//			return "储蓄卡签约接口调用失败";
//		}
//	}
	
	@RequestMapping("pc/certifyReturnUrl")
	public String pcCertifyReturnUrl(HttpServletRequest request, HttpServletResponse response) {
		String url = splitUrl(request, response, "pc");
		return "redirect:"+url;
	}
	
	@RequestMapping("h5/certifyReturnUrl")
	public String h5CertifyReturnUrl(HttpServletRequest request, HttpServletResponse response) {
		String url = splitUrl(request, response, "h5");
		return "redirect:"+url;
	}
	
	@RequestMapping("android/certifyReturnUrl")
	public String androidCertifyReturnUrl(HttpServletRequest request, HttpServletResponse response) {
		String url = splitUrl(request, response, "android");
		return "redirect:"+url;
	}
	
	@RequestMapping("ios/certifyReturnUrl")
	public String iosCertifyReturnUrl(HttpServletRequest request, HttpServletResponse response) {
		String url = splitUrl(request, response, "ios");
		return "redirect:"+url;
	}
	
	private String splitUrl(HttpServletRequest request, HttpServletResponse response, String type) {
		String data = request.getParameter("data");
		String encryptkey = request.getParameter("encryptkey");
		String pid = request.getParameter("pid");
		String url = "source="+type+"&flag=fail";
		try {
			//解析密文数据
			String decryData = Decipher.decryptData(encryptkey,data);
			logger.info("收到Reapal卡密接口同步返回>>>>>>" + decryData);
			JSONObject jsonObject = JSON.parseObject(decryData);
			String merchant_id = jsonObject.getString("merchant_id");
			String trade_no = jsonObject.getString("trade_no");
			String order_no = jsonObject.getString("order_no");
			String result_code = jsonObject.getString("result_code");
			String result_msg = jsonObject.getString("result_msg");
			String sign = jsonObject.getString("sign");
			String card_last = jsonObject.getString("card_last");
			String bind_id = jsonObject.getString("bind_id");
			String bank_code = jsonObject.getString("bank_code");
			String bank_name = jsonObject.getString("bank_name");
			String bank_card_type = jsonObject.getString("bank_card_type");
			String total_fee = jsonObject.getString("total_fee");
			String member_id = jsonObject.getString("member_id");
			String phone = jsonObject.getString("phone");
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("merchant_id", merchant_id);
			map.put("trade_no", trade_no);
			map.put("order_no", order_no);
			map.put("result_code", result_code);
			map.put("result_msg", result_msg);
			map.put("bind_id", bind_id);
			map.put("card_last", card_last);
			map.put("bank_code", bank_code);
			map.put("bank_name", bank_name);
			map.put("bank_card_type", bank_card_type);
			map.put("total_fee", total_fee);
			map.put("member_id", member_id);
			map.put("phone", phone);
			//将返回的参数进行验签
			String mysign = Md5Utils.BuildMysign(map, ReapalConfig.key);
			if(mysign.equals(sign)) {
				if(ReapalQuickPayRespCode.SUCCESS_CODE_0000.getCode().equals(result_code)) {
					url = "source="+type+"&flag=success&orderNo="+order_no;
					if(StringUtil.isNotBlank(pid)) {
						url += "&pid="+pid;
					}
				}
			}
			else {
				logger.error("Reapal卡密接口同步返回出现异常:【异常信息:MD5校验失败】");
			}
		}catch(Exception e) {
			logger.error("Reapal卡密接口同步返回出现异常:【异常信息:"+e.getMessage()+"】");
		}
		//对url进行DES加密
		DESUtil des = new DESUtil("cfgubijn");
		url = des.encryptStr(url);
		return ReapalConfig.certify_site_url + "?" + url;
	}
	
}
