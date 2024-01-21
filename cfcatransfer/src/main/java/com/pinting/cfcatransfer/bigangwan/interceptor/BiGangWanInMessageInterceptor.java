package com.pinting.cfcatransfer.bigangwan.interceptor;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.cfcatransfer.bigangwan.model.BaseReqModel;
import com.pinting.cfcatransfer.bigangwan.model.BaseResModel;
import com.pinting.cfcatransfer.bigangwan.util.BGWInConstant;
import com.pinting.cfcatransfer.bigangwan.util.BGWInMsgUtil;

/**
 * 请求拦截，并解析报文
 * 
 * @Project: gateway
 * @Title: BiGangWanInMessageInterceptor.java
 * @author dingpf
 * @date 2015-2-10 下午8:46:42
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class BiGangWanInMessageInterceptor extends HandlerInterceptorAdapter {
	private Logger log = LoggerFactory
			.getLogger(BiGangWanInMessageInterceptor.class);

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		// 获得报文密文
		String encryptData = request.getParameter("DATA");
		log.debug("============获得报文密文：【" + encryptData + "】============");
		//解密报文
		BaseReqModel model = BGWInMsgUtil.parseMsg(encryptData);
		if(model == null){
			log.error("============请求报文解密失败，请检查！============");
			PrintWriter out = response.getWriter();
			// 组装报错报文返回给达飞
			BaseResModel resModel = new BaseResModel();
			resModel.setRespCode(BGWInConstant.RETURN_CODE_REFUSE);
			resModel.setRespMsg(BGWInConstant.RETURN_MSG_DECODE_FAIL);
			resModel.setResponseTime(new Date());
			String resp = BGWInMsgUtil.packageMsg(resModel);
			log.error("返回报文：" + resp);
			out.print(resp);
			out.flush();
			out.close();
			return false;
		}
		
		log.info("model=" + model);
		
		//合法且在有效期内，进入具体业务逻辑
		request.setAttribute("reqModel", model);
		return true;
	}
}
