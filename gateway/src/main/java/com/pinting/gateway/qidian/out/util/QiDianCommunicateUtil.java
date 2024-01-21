package com.pinting.gateway.qidian.out.util;

import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.qidian.out.enums.NoticeUrl;
import com.pinting.gateway.qidian.out.model.BaseReqModel;
import com.pinting.gateway.qidian.out.model.BaseResModel;
import com.pinting.gateway.qidian.out.model.LoginReqModel;
import com.pinting.gateway.qidian.out.model.LoginResModel;
import com.pinting.gateway.util.Constants;
import com.pinting.gateway.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 外部通信类，用于和外部系统底层通信
 * @Project: gateway
 * @Title: ComminuteUtil.java
 * @author Zhou Changzai
 * @date 2015-2-12 下午12:01:03
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class QiDianCommunicateUtil {
	
	private static Logger log = LoggerFactory.getLogger(QiDianCommunicateUtil.class);
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_GATEWAY);
	//七店
	private static String qiDianOutUrl = "http://113.105.125.178:9678/bigangwan/business";//七店
	public static String qiDianOutClientKey = "channelBigangForQidian";
	public static String qiDianOutClientSecret = "123fs1DXSFF";
	
	static{
		//七店
		qiDianOutUrl = GlobEnvUtil.get("qidian.out.url");
		qiDianOutClientKey = GlobEnvUtil.get("qidian.out.client.key");
		qiDianOutClientSecret = GlobEnvUtil.get("qidian.out.client.secret");
	}
	
	/**
	 * 普通的通讯过程(七店)
	 * @param req 通讯请求模型
 	 * @return BaseResModel 模型
	 */
	public static BaseResModel doCommunicate2QiDian(BaseReqModel req){
		//确保已经登录过
		//判断是否已经登录
		String token = getQiDianToken();
		
		if (StringUtil.isEmpty(token)) {
			token = assureQiDianLogin();
		}
		//开始发起交易
		req.setToken(token);
		String retStr = doSingleCommunicateQiDian(req);
		//解密报文，并返回模型对象
		BaseResModel resMsg = QiDianOutMsgUtil.parseMsg(retStr, req.getTransCode());
		
		if (resMsg == null) {
			throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR);
		}
		//判断是否超时，如果超时，重新登录再发起此交易
		if(QiDianOutConstant.RETURN_CODE_EXPIRE.equals(resMsg.getRespCode())){
			//超时了，再次登录
			token = assureQiDianLogin();
			//开始发起交易
			req.setToken(token);
			retStr = doSingleCommunicateQiDian(req);
			//解密报文，并返回模型对象
			resMsg = QiDianOutMsgUtil.parseMsg(retStr, req.getTransCode());
			if (resMsg == null) {
				throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR);
			}
		}
		
		return resMsg;
	}
	
	
	

	
	
	//登录
	private static String assureQiDianLogin(){
		
		String transCode = QiDianOutConstant.QIDIAN_LOGIN;
		LoginReqModel reqModel = new LoginReqModel();
		reqModel.setClientKey(qiDianOutClientKey);
		reqModel.setClientSecret(qiDianOutClientSecret);
		reqModel.setRequestTime(new Date());
		reqModel.setTransCode(transCode);
		String requestSeq = String.valueOf(System.currentTimeMillis());   
		reqModel.setRequestSeq(requestSeq);
		String retStr = doSingleCommunicateQiDian(reqModel);
		//解密报文，并返回模型对象
		LoginResModel resMsg = (LoginResModel)QiDianOutMsgUtil.parseMsg(retStr, transCode);
		String token = "";
		if(resMsg != null){
			try {
				jsClientDaoSupport.setString(resMsg.getToken(), "qidian_out_access_token");
				token = resMsg.getToken();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			throw new PTMessageException(PTMessageEnum.QIDIAN_LOGIN_FAIL);
		}
		return token;
	}
	
	
	
	
	//发起通讯，返回原始报文
	private static String doSingleCommunicateQiDian(BaseReqModel req){
		if(StringUtil.isEmpty(req.getToken())){
			req.setToken(getQiDianToken());
		}
		req.setClientKey(qiDianOutClientKey);
		//获取加密以后的参数
		String msg = QiDianOutMsgUtil.packageMsg(req);
		//组装HTTP参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("DATA", msg);
		params.put("clientKey", qiDianOutClientKey);
		params.put("transCode", req.getTransCode());
		
		//发送http请求，获得返回报文
		String retStr = null;
		retStr = HttpClientUtil.sendRequest(qiDianOutUrl+NoticeUrl.getEnumByTransCode(req.getTransCode()).getCode(), params);
		return retStr;
	}
	
	public static String getQiDianToken(){
		String token = "";
		try {
			 token = jsClientDaoSupport.getString("qidian_out_access_token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

}
