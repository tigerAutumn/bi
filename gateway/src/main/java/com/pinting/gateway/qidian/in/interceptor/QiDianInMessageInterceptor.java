package com.pinting.gateway.qidian.in.interceptor;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.gateway.qidian.in.model.BaseReqModel;
import com.pinting.gateway.qidian.in.model.BaseResModel;
import com.pinting.gateway.qidian.in.util.QiDianInConstant;
import com.pinting.gateway.qidian.in.util.QiDianInMsgUtil;
import com.pinting.gateway.util.Constants;

public class QiDianInMessageInterceptor extends HandlerInterceptorAdapter{
	private Logger log = LoggerFactory
			.getLogger(QiDianInMessageInterceptor.class);
	
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_GATEWAY);

	private static String MODE_CHECK_TRUE = "true";
	private static String qidianMode = GlobEnvUtil.get("qidian.check.hash.token");

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		// 测试数据
		// ========================以上测试数据=======================================================
		// 获得报文密文
		String encryptData = request.getParameter("DATA");
		log.info("============七店通知币港湾，获得报文密文：【" + encryptData + "】============");
		//1.解密报文
		BaseReqModel model = QiDianInMsgUtil.parseMsg(encryptData);
		if(model == null){
			log.error("============七店请求报文解密失败，请检查！============");
			PrintWriter out = response.getWriter();
			// 组装报错报文返回给七店
			BaseResModel resModel = new BaseResModel();
			resModel.setRespCode(QiDianInConstant.RETURN_CODE_REFUSE);
			resModel.setRespMsg(QiDianInConstant.RETURN_MSG_DECODE_FAIL);
			resModel.setResponseTime(new Date());
			String resp = QiDianInMsgUtil.packageMsg(resModel);
			log.error("返回报文：" + resp);
			out.print(resp);
			out.flush();
			out.close();
			return false;
		}
		log.info("model=" + model);

		//2.校验hash
		if(MODE_CHECK_TRUE.equals(qidianMode)) {
			String validHash = QiDianInMsgUtil.qiDianPackageMsgHash(model, model.getTransCode());
			log.info("============hash密文【" +MD5Util.encryptMD5(validHash)+ "】============");
			if(StringUtil.isEmpty(model.getHash()) || !model.getHash().equals(MD5Util.encryptMD5(validHash))){
				log.error("============七店请求报文校验失败，请检查！============");
				PrintWriter out = response.getWriter();
				// 组装报错报文返回给七店
				BaseResModel resModel = new BaseResModel();
				resModel.setRespCode(QiDianInConstant.RETURN_CODE_REFUSE);
				resModel.setRespMsg(QiDianInConstant.RETURN_MSG_HASH_ERROR);
				resModel.setResponseTime(new Date());
				String resp = QiDianInMsgUtil.packageMsg(resModel);
				out.print(resp);
				out.flush();
				out.close();
				return false;
			}
		}

		//校验requestTime
		if(null == model.getRequestTime()){
			log.error("============七店请求报文requestTime校验失败，请检查！============");
			PrintWriter out = response.getWriter();
			// 组装报错报文返回给七店
			BaseResModel resModel = new BaseResModel();
			resModel.setRespCode(QiDianInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(QiDianInConstant.RETURN_MSG_RESULT_PARAMS_ERROR+":requestTime为空");
			resModel.setResponseTime(new Date());
			String resp = QiDianInMsgUtil.packageMsg(resModel);
			out.print(resp);
			out.flush();
			out.close();
			return false;
		}
		
		
		//3.合法请求，判断是否超时
		if(MODE_CHECK_TRUE.equals(qidianMode)) {
			if(!QiDianInConstant.LOGIN.equals(model.getTransCode())){//非登录交易的时候才需要判断
				String token = model.getToken();
				if(StringUtil.isEmpty(token)){//token错误
					//token错误逻辑
					log.error("============自主放款七店请求token为空，已拒绝！============");
					PrintWriter out = response.getWriter();
					// 组装报错报文返回给七店
					BaseResModel resModel = new BaseResModel();
					resModel.setRespCode(QiDianInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(QiDianInConstant.RETURN_MSG_TOKEN_EMPTY_ERROR);
					resModel.setResponseTime(new Date());
					String resp = QiDianInMsgUtil.packageMsg(resModel);
					out.print(resp);
					out.flush();
					out.close();
					return false;
				}
				String dafyLoanAccessToken = jsClientDaoSupport.getString("qidian_access_token");
				if(!token.equals(dafyLoanAccessToken)){//超时或者token出错
					//超时逻辑
					log.error("============七店请求超时，已拒绝！============");
					PrintWriter out = response.getWriter();
					// 组装报错报文返回给七店
					BaseResModel resModel = new BaseResModel();
					resModel.setRespCode(QiDianInConstant.RETURN_CODE_EXPIRE);
					resModel.setRespMsg(QiDianInConstant.RETURN_MSG_LOGIN_EXPIRED);
					resModel.setResponseTime(new Date());
					String resp = QiDianInMsgUtil.packageMsg(resModel);
					out.print(resp);
					out.flush();
					out.close();
					return false;
				}
			}
		}

		//4.合法且在有效期内，进入具体业务逻辑
		request.setAttribute("reqModel", model);
		QiDianInMsgUtil.lastAccessTime = System.currentTimeMillis();//正常操作，设置最后时间
		return true;
	}


}
