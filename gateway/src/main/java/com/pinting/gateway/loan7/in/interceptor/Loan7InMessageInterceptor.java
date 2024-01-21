package com.pinting.gateway.loan7.in.interceptor;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.core.util.StringUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.gateway.loan7.in.util.Loan7InConstant;
import com.pinting.gateway.loan7.in.util.Loan7InMsgUtil;
import com.pinting.gateway.loan7.in.model.BaseReqModel;
import com.pinting.gateway.loan7.in.model.BaseResModel;

/**
 * 达飞请求拦截，并解析报文
 * 
 * @Project: gateway
 * @Title: DafyMessageInterceptor.java
 * @author dingpf
 * @date 2015-2-10 下午8:46:42
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class Loan7InMessageInterceptor extends HandlerInterceptorAdapter {
	private Logger log = LoggerFactory
			.getLogger(Loan7InMessageInterceptor.class);

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
		log.info("============7贷通知币港湾，获得报文密文：【" + encryptData + "】============");
		//1.解密报文
		BaseReqModel model = Loan7InMsgUtil.parseMsg(encryptData);
		if(model == null){
			log.error("============7贷请求报文解密失败，请检查！============");
			PrintWriter out = response.getWriter();
			// 组装报错报文返回给达飞
			BaseResModel resModel = new BaseResModel();
			resModel.setRespCode(Loan7InConstant.RETURN_CODE_REFUSE);
			resModel.setRespMsg(Loan7InConstant.RETURN_MSG_DECODE_FAIL);
			resModel.setResponseTime(new Date());
			String resp = Loan7InMsgUtil.packageMsg(resModel);
			log.error("返回报文：" + resp);
			out.flush();
			out.close();
			return false;
		}
		
		log.info("model=" + model);
		//2.校验hash
//		String validHash = Loan7InMsgUtil.packageMsgHash(model, model.getTransCode());
//		log.info("============hash密文【" +MD5Util.encryptMD5(validHash)+ "】============");
//		if(StringUtil.isEmpty(model.getHash()) || !model.getHash().equals(MD5Util.encryptMD5(validHash))){
//			log.error("============7贷请求报文校验失败，请检查！============");
//			PrintWriter out = response.getWriter();
//			// 组装报错报文返回给达飞
//			BaseResModel resModel = new BaseResModel();
//			resModel.setRespCode(Loan7InConstant.RETURN_CODE_REFUSE);
//			resModel.setRespMsg(Loan7InConstant.RETURN_MSG_HASH_ERROR);
//			resModel.setResponseTime(new Date());
//			String resp = Loan7InMsgUtil.packageMsg(resModel);
//			out.print(resp);
//			out.flush();
//			out.close();
//			return false;
//		}
		
		//3.合法请求，判断是否超时
//		if(!Loan7InConstant.LOGIN.equals(model.getTransCode())){//非登录交易的时候才需要判断
//			String token = model.getToken();
//			if(System.currentTimeMillis() - Loan7InMsgUtil.lastAccessTime > Loan7InMsgUtil.expiredTime){//超时
//				//超时逻辑
//				log.error("============7贷请求超时，已拒绝！============");
//				PrintWriter out = response.getWriter();
//				// 组装报错报文返回给达飞
//				BaseResModel resModel = new BaseResModel();
//				resModel.setRespCode(Loan7InConstant.RETURN_CODE_EXPIRE);
//				resModel.setRespMsg(Loan7InConstant.RETURN_MSG_LOGIN_EXPIRED);
//				resModel.setResponseTime(new Date());
//				String resp = Loan7InMsgUtil.packageMsg(resModel);
//				out.print(resp);
//				out.flush();
//				out.close();
//				return false;
//			}
//			if(StringUtil.isEmpty(token) || !token.equals(Loan7InMsgUtil.token)){//token错误
//				//token错误逻辑
//				log.error("============7贷请求token错误，已拒绝！============");
//				PrintWriter out = response.getWriter();
//				// 组装报错报文返回给达飞
//				BaseResModel resModel = new BaseResModel();
//				resModel.setRespCode(Loan7InConstant.RETURN_CODE_REFUSE);
//				resModel.setRespMsg(Loan7InConstant.RETURN_MSG_TOKEN_ERROR);
//				resModel.setResponseTime(new Date());
//				String resp = Loan7InMsgUtil.packageMsg(resModel);
//				out.print(resp);
//				out.flush();
//				out.close();
//				return false;
//			}
//		}
		
		//4.合法且在有效期内，进入具体业务逻辑
		request.setAttribute("reqModel", model);
		Loan7InMsgUtil.lastAccessTime = System.currentTimeMillis();//正常操作，设置最后时间
		return true;
	}
}
