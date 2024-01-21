package com.pinting.gateway.reapal.out.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.pinting.gateway.reapal.out.config.ReapalConfig;
import com.pinting.gateway.reapal.out.enums.ReapalUrl;

public class ReapalForH5 {

	public static String BuildFormH5(String merchant_id, String order_no, String bind_id, String member_id, String terminal_type, String returnUrl) throws Exception {

		Map<String, String> sPara = new HashMap<String, String>();
		sPara.put("merchant_id", merchant_id);
		sPara.put("terminal_type", terminal_type);
		sPara.put("order_no", order_no);
		sPara.put("bind_id", bind_id);
		sPara.put("member_id", member_id);
		sPara.put("return_url", returnUrl);
		sPara.put("notify_url", ReapalConfig.certify_notify_url);
		sPara.put("version", "3.1.3");

		String mysign = Md5Utils.BuildMysign(sPara, ReapalConfig.key);// 生成签名结果

		sPara.put("sign", mysign);
		
		String json = JSON.toJSONString(sPara);

		Map<String, String> maps = Decipher.encryptData(json);
		
		StringBuffer sbHtml = new StringBuffer();

		// post方式传递
		sbHtml.append(
				"<form id=\"rongpaysubmit\" name=\"rongpaysubmit\" action=\"")
				.append(ReapalConfig.rongpay_api+ReapalUrl.CERTIFY_INTERFACE.getCode()).append("\" method=\"post\">");

		sbHtml.append("<input type=\"hidden\" name=\"merchant_id\" value=\"")
				.append(ReapalConfig.merchant_id).append("\"/>");
		sbHtml.append("<input type=\"hidden\" name=\"data\" value=\"")
				.append(maps.get("data")).append("\"/>");
		sbHtml.append("<input type=\"hidden\" name=\"encryptkey\" value=\"")
				.append(maps.get("encryptkey")).append("\"/></form>");

		sbHtml.append("<script type=\"text/javascript\">document.getElementById(\"rongpaysubmit\").submit()</script>");
		
		return sbHtml.toString();

	}
	
	
	public static Map<String,String> parseParam(HttpServletRequest request) {
        Map<String,String> paramsMap = new HashMap<String,String>();
        Enumeration paramsEnum = request.getParameterNames();
        while (paramsEnum.hasMoreElements()) {
            String paramName = (String) paramsEnum.nextElement();
            String paramValue = request.getParameter(paramName);
            paramsMap.put(paramName, paramValue);
        }
        return paramsMap;
    }
}
