package com.pinting.open.base.util;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pinting.open.base.exception.OpenException;
import com.pinting.open.base.response.Response;

public class ResponseUtil {
	
	public static String dealResponse(Response res) {
		JsonParser paser = new JsonParser();
		Gson gson = new Gson();
		OpenException oe = res.getException();
		res.setException(null);
		JsonObject resObj = (JsonObject) paser.parse(gson.toJson(res));
		if ((oe != null) && (!res.isSuccess())) {
			Map<String,Object> exMap = null;
			if (oe.getClass().equals(OpenException.class)) {
				try {
					exMap = ParamUtil.calParamsObject(oe);
					resObj.add("exception", paser.parse(gson.toJson(exMap)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				try {
					exMap = ParamUtil.calParamsObject(oe);
					Map<String,Object> rootExMap = ParamUtil.calParamsObject(oe, OpenException.class);
					exMap.putAll(rootExMap);
					resObj.add("exception", paser.parse(gson.toJson(exMap)));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		String result = resObj.toString();
		//DES加密
		result = new DESUtil(Constants.OPENDESKEY).encryptStr(result);
		return result;
	}
}
