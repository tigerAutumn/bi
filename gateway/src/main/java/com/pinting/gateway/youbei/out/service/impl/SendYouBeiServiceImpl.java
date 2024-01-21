package com.pinting.gateway.youbei.out.service.impl;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.util.HttpClientUtil;
import com.pinting.gateway.youbei.out.model.RealNameModel;
import com.pinting.gateway.youbei.out.service.SendYouBeiService;

import net.sf.json.JSONObject;

@Service
public class SendYouBeiServiceImpl implements SendYouBeiService {

	private static Logger logger = LoggerFactory.getLogger(SendYouBeiServiceImpl.class);
	
	@Override
	public RealNameModel checkRealName(String name, String idCard, String cardNo, String mobile) {
		logger.info("============调用有贝四要素验证接口============");
		RealNameModel result = null;
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("card_no", cardNo);
			params.put("id_no", idCard);
			params.put("id_name", URLEncoder.encode(name, "UTF-8"));
			params.put("mobile_no", mobile);
			params.put("auth_key", GlobEnvUtil.get("youbei.authkey"));
			String retStr = HttpClientUtil.sendRequestGet(GlobEnvUtil.get("youbei.checkrealname.url"), params);
			if(StringUtil.isNotBlank(retStr)) {
				JSONObject obj = JSONObject.fromObject(retStr);
				result = (RealNameModel) JSONObject.toBean(obj, RealNameModel.class);
				logger.info("============调用接口成功，返回数据："+retStr+"============");
			}
			else {
				logger.error("============调用接口失败，通讯失败============");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
