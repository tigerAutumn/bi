package com.pinting.gateway.loan7.out.util;

import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.loan7.out.model.BaseReqModel;
import com.pinting.gateway.loan7.out.model.BaseResModel;
import com.pinting.gateway.loan7.out.model.LoginReqModel;
import com.pinting.gateway.loan7.out.model.LoginResModel;
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
public class DepLoan7CommunicateUtil {
	
	private static Logger log = LoggerFactory.getLogger(DepLoan7CommunicateUtil.class);
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_GATEWAY);
	//7贷
	private static String depLoan7OutUrl = "http://localhost:8098/simulator/loan7/business";//达飞自主放款
	public static String depLoan7OutClientKey = "channel7daikey1230";
	public static String depLoan7OutClientSecret = "7dai7987!&Ke6!3";
	
	static{
		//7贷
		depLoan7OutUrl = GlobEnvUtil.get("7dai.dep.out.url");
		depLoan7OutClientKey = GlobEnvUtil.get("7dai.dep.out.client.key");
		depLoan7OutClientSecret = GlobEnvUtil.get("7dai.dep.out.client.secret");
	}
	
	/**
	 * 普通的通讯过程(7贷)
	 * @param req 通讯请求模型
 	 * @return BaseResModel 模型
	 */
	public static BaseResModel doCommunicate2DepLoan7(BaseReqModel req){
		//确保已经登录过
		//判断是否已经登录
		String token = getDepLoan7Token();
		
		if (StringUtil.isEmpty(token)) {
			token = assureDepLoan7Login();
		}
		//开始发起交易
		req.setToken(token);
		String retStr = doSingleCommunicateDepLoan7(req);
		//解密报文，并返回模型对象
		BaseResModel resMsg = DepLoan7OutMsgUtil.parseMsg(retStr, req.getTransCode());
		
		if (resMsg == null) {
			throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR);
		}
		//判断是否超时，如果超时，重新登录再发起此交易
		if(DepLoan7OutConstant.RETURN_CODE_EXPIRE.equals(resMsg.getRespCode())){
			//超时了，再次登录
			token = assureDepLoan7Login();
			//开始发起交易
			req.setToken(token);
			retStr = doSingleCommunicateDepLoan7(req);
			//解密报文，并返回模型对象
			resMsg = DepLoan7OutMsgUtil.parseMsg(retStr, req.getTransCode());
			if (resMsg == null) {
				throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR);
			}
		}
		
		return resMsg;
	}
	
	
	

	
	
	//登录
	private static String assureDepLoan7Login(){
		
		String transCode = DepLoan7OutConstant.DEP_LOAN7_LOGIN;
		LoginReqModel reqModel = new LoginReqModel();
		reqModel.setClientKey(depLoan7OutClientKey);
		reqModel.setClientSecret(depLoan7OutClientSecret);
		reqModel.setRequestTime(new Date());
		reqModel.setTransCode(transCode);
		String requestSeq = String.valueOf(System.currentTimeMillis());   
		reqModel.setRequestSeq(requestSeq);
		String retStr = doSingleCommunicateDepLoan7(reqModel);
		//解密报文，并返回模型对象
		LoginResModel resMsg = (LoginResModel)DepLoan7OutMsgUtil.parseMsg(retStr, transCode);
		String token = "";
		if(resMsg != null){
			try {
				jsClientDaoSupport.setString(resMsg.getToken(), "dep_7dai_out_access_token");
				token = resMsg.getToken();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			throw new PTMessageException(PTMessageEnum.DAFY_LOGIN_FAIL);
		}
		return token;
	}
	
	
	
	
	//发起通讯，返回原始报文
	private static String doSingleCommunicateDepLoan7(BaseReqModel req){
		if(StringUtil.isEmpty(req.getToken())){
			req.setToken(getDepLoan7Token());
		}
		req.setClientKey(depLoan7OutClientKey);
		//获取加密以后的参数
		String msg = DepLoan7OutMsgUtil.packageMsg(req);
		//组装HTTP参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("DATA", msg);
		params.put("clientKey", depLoan7OutClientKey);
		params.put("transCode", req.getTransCode());
		
		//发送http请求，获得返回报文
		String retStr = null;
		retStr = HttpClientUtil.sendRequest(depLoan7OutUrl, params);
		return retStr;
	}
	
	public static String getDepLoan7Token(){
		String token = "";
		try {
			 token = jsClientDaoSupport.getString("dep_7dai_out_access_token");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

}
