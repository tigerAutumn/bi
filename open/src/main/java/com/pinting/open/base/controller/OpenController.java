package com.pinting.open.base.controller;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.pinting.open.base.exception.OpenException;
import com.pinting.open.base.request.Request;
import com.pinting.open.base.response.Response;
import com.pinting.open.base.util.MD5Util;
import com.pinting.open.base.util.ParamUtil;
import com.pinting.open.base.util.ResponseUtil;

public class OpenController {

	private Request obtainRequest(HttpServletRequest req, Class<?> c) throws OpenException {
		Gson gson = new Gson();
		JsonParser paser = new JsonParser();
		String request_json_str_ = req.getParameter("request_json_str_");
		if (request_json_str_ == null) {
			return null;
		}
		JsonObject jsonObj = (JsonObject) paser.parse(request_json_str_);
		Request request = (Request) gson.fromJson(request_json_str_, c);
		// MD5验证
		String sign = request.getSignOpenApi();
		jsonObj.remove("signOpenApi");
		String text = ParamUtil.createLinkString(jsonObj);
		String _sign = MD5Util.encryptMD5(text);
		if (!sign.equals(_sign)) {
			throw new OpenException("500002", "MD5校验失败");
		}
		return request;
	}

	public String execute(HttpServletRequest req, Class<?> c, ControllerCallBack action) {
		Response response = null;
		String result = null;
		try {
			String reqestClassName = c.getCanonicalName();
			String responseClassName = reqestClassName.replace("Request", "Response").replace(".request.", ".response.");
			Class<?> responseClass = Class.forName(responseClassName);
			response = (Response) responseClass.newInstance();
			response.setSuccess(true);
			Request request = obtainRequest(req, c);
			if(request == null) {
				throw new OpenException("500004", "请求参数不合法");
			}
			executeAction(request, response, action);
		} catch (OpenException e) {
			response = new Response();
			response.setSuccess(false);
			response.setException(e);
		} catch (Exception e) {
			OpenException oe = new OpenException("500003", "系统运行出现异常，请稍候再试");
			response = new Response();
			response.setSuccess(false);
			response.setException(oe);
		}
		result = ResponseUtil.dealResponse(response);
		return result;
	}

	private void executeAction(Request request, Response response, ControllerCallBack action){
		action.doCheck(request, response);
		action.doOperation(request, response);
	}
}
