package com.pinting.open.base.client;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pinting.open.base.exception.OpenException;
import com.pinting.open.base.request.Request;
import com.pinting.open.base.response.Response;
import com.pinting.open.base.util.MD5Util;
import com.pinting.open.base.util.ParamUtil;
import com.pinting.open.base.util.WebUtil;

public class AppOpenClient implements OpenClient {
    
	private String serverType;

	private Integer timeout;

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public AppOpenClient(String serverType) {
		this.serverType = serverType;
	}

	public Response execute(Request req) {
		String time = String.valueOf(new Date().getTime());
		req.setTimeOpenApi(time);
		Response res = calResponse(req);
		return res;
	}

	private Response calResponse(Request req) {
		Class<?> resC = null;
		OpenException ex = null;
		Gson gson = new Gson();
		JsonParser paser = new JsonParser();
		try {
			Class<?> c = req.getClass();
			String simString = c.getCanonicalName();
			simString = simString.replace("Request", "Response").replace(".request.", ".response.");
			resC = Class.forName(simString);
			String url = null;
			if ("rest".equals(this.serverType)) {
				if(req.getVersion() != null && !"".equals(req.getVersion())) {
					url = req.restApiUrl() + "/" + req.getVersion();
				}
				else {
					url = req.restApiUrl();
				}
			}
			else {
				if(req.getVersion() != null && !"".equals(req.getVersion())) {
					url = req.testApiUrl() + "/" + req.getVersion();
				}
				else {
					url = req.testApiUrl();
				}
			}
			JsonObject jsonObj = (JsonObject)paser.parse(gson.toJson(req));
			jsonObj.remove("signOpenApi");
			
			//判断是否传了APP版本号，如果没传APP版本号证明是老版本APP请求，直接抛出异常
			JsonElement jsonObject = jsonObj.get("appVersion");
			if (jsonObject == null) {
				Response res = null;
				try {
					res = (Response) resC.newInstance();
				} catch (Exception e1) {
					res = new Response();
				} 
				res.setSuccess(false);
				ex = new OpenException("500002","请下载最新的APP进行操作！");
				res.setException(ex);
				return res;
			}
			
			String text = ParamUtil.createLinkString(jsonObj);
			String signStr = MD5Util.encryptMD5(text); //MD5加密
			jsonObj.addProperty("signOpenApi", signStr);
			Map<String,String> reqMap = new HashMap<String,String>();
			reqMap.put("request_json_str_", jsonObj.toString());
			String result = WebUtil.postHttp(url, reqMap);
			//判断response中是否有list类型
			//Map<String,Class<?>> resMap = ParamUtil.objectFieldListConvert(resC);
			return (Response)gson.fromJson(result, resC);
		} catch (Exception e) {
			Response res = null;
			try {
				res = (Response) resC.newInstance();
			} catch (Exception e1) {
				res = new Response();
			} 
			res.setSuccess(false);
			//ex = calException(req, null, "500001", "客户端发送消息出现异常："+e.getMessage());
			ex = new OpenException("500001","币港湾网络堵塞，请稍候再试！");
			res.setException(ex);
			return res;
		}
	}

	private OpenException calException(Request req, Response res, String code, String message) {
		OpenException ex = null;
		try {
			Class<?> ecs = calExceptionClass(req);
			ex = (OpenException) ecs.newInstance();
		} catch (InstantiationException e) {
			ex = new OpenException();
		} catch (IllegalAccessException e) {
			ex = new OpenException();
		}
		ex.setCode(code);
		ex.setMessage(message);

		return ex;
	}

	private Class<?> calExceptionClass(Request req) {
		Class<?> cs = req.getClass();
		String simpleName = cs.getCanonicalName();
		String exceptionName = simpleName.replace("Request", "Exception").replace(".request.", ".exception.");
		try {
			Class<?> ecs = Class.forName(exceptionName);
			return ecs;
		} catch (ClassNotFoundException e) {
		}
		return OpenException.class;
	}

}
