package com.pinting.gateway.dafy.in.interceptor;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.core.util.ConsMsgUtil;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.gateway.dafy.in.model.BaseReqModel;
import com.pinting.gateway.dafy.in.model.BaseResModel;
import com.pinting.gateway.dafy.in.util.DafyInConstant;
import com.pinting.gateway.dafy.in.util.DafyInMsgUtil;
import com.pinting.gateway.util.DESUtil;

/**
 * 达飞请求拦截，并解析报文
 * 
 * @Project: gateway
 * @Title: DafyMessageInterceptor.java
 * @author dingpf
 * @date 2015-2-10 下午8:46:42
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class DafyInMessageInterceptor extends HandlerInterceptorAdapter {
	private Logger log = LoggerFactory
			.getLogger(DafyInMessageInterceptor.class);

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		// 测试数据
		/*String data = "{transCode:\"login\",requestTime:\"2015-02-10 21:11:11\",clientKey:\"INSIGMA\",clientSecret:\"INSiGMADaFy0o1\",hash:\"840479a661701ffcdb9edf1f9e490e48\"}";
		String encryptData = new String(DESUtil.encryptStr(data,
				DESUtil.initKey()));
		log.debug("============获得报文密文：【" + URLEncoder.encode(encryptData, "UTF-8") + "】============");*/
		// ========================以上测试数据=======================================================
		// 获得报文密文
		String encryptData = request.getParameter("DATA");
		log.info("============达飞通知网新，获得报文密文：【" + encryptData + "】============");
		//1.解密报文
		BaseReqModel model = DafyInMsgUtil.parseMsg(encryptData);
		if(model == null){
			log.error("============达飞请求报文解密失败，请检查！============");
			PrintWriter out = response.getWriter();
			// 组装报错报文返回给达飞
			BaseResModel resModel = new BaseResModel();
			resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
			resModel.setRespMsg(DafyInConstant.RETURN_MSG_DECODE_FAIL);
			resModel.setResponseTime(new Date());
			String resp = DafyInMsgUtil.packageMsg(resModel);
			log.error("返回报文：" + resp);
			out.flush();
			out.close();
			return false;
		}
		
		log.info("model=" + model);
		/*//2.校验hash
		String validHash = DafyInMsgUtil.packageMsgHash(model, model.getTransCode());
		log.info("============hash密文【" +MD5Util.encryptMD5(validHash)+ "】============");
		if(StringUtil.isEmpty(model.getHash()) || !model.getHash().equals(MD5Util.encryptMD5(validHash))){
			log.error("============达飞请求报文校验失败，请检查！============");
			PrintWriter out = response.getWriter();
			// 组装报错报文返回给达飞
			BaseResModel resModel = new BaseResModel();
			resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
			resModel.setRespMsg(DafyInConstant.RETURN_MSG_HASH_ERROR);
			resModel.setResponseTime(new Date());
			String resp = DafyInMsgUtil.packageMsg(resModel);
			out.print(resp);
			out.flush();
			out.close();
			return false;
		}
		
		//3.合法请求，判断是否超时
		if(!DafyInConstant.LOGIN.equals(model.getTransCode())){//非登录交易的时候才需要判断
			String token = model.getToken();
			if(System.currentTimeMillis() - DafyInMsgUtil.lastAccessTime > DafyInMsgUtil.expiredTime){//超时
				//超时逻辑
				log.error("============达飞请求超时，已拒绝！============");
				PrintWriter out = response.getWriter();
				// 组装报错报文返回给达飞
				BaseResModel resModel = new BaseResModel();
				resModel.setRespCode(DafyInConstant.RETURN_CODE_EXPIRE);
				resModel.setRespMsg(DafyInConstant.RETURN_MSG_LOGIN_EXPIRED);
				resModel.setResponseTime(new Date());
				String resp = DafyInMsgUtil.packageMsg(resModel);
				out.print(resp);
				out.flush();
				out.close();
				return false;
			}
			if(StringUtil.isEmpty(token) || !token.equals(DafyInMsgUtil.token)){//token错误
				//token错误逻辑
				log.error("============达飞请求token错误，已拒绝！============");
				PrintWriter out = response.getWriter();
				// 组装报错报文返回给达飞
				BaseResModel resModel = new BaseResModel();
				resModel.setRespCode(DafyInConstant.RETURN_CODE_REFUSE);
				resModel.setRespMsg(DafyInConstant.RETURN_MSG_TOKEN_ERROR);
				resModel.setResponseTime(new Date());
				String resp = DafyInMsgUtil.packageMsg(resModel);
				out.print(resp);
				out.flush();
				out.close();
				return false;
			}
		}*/
		
		//4.合法且在有效期内，进入具体业务逻辑
		request.setAttribute("reqModel", model);
		DafyInMsgUtil.lastAccessTime = System.currentTimeMillis();//正常操作，设置最后时间
		return true;
	}
}
