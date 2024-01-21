package com.pinting.open.base.controller;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.response.Response;

public abstract class ControllerCallBack {

	/**
	 * 参数合法性和权限校验
	 * @Title: doCheck 
	 * @Description: TODO
	 * @param request
	 * @throws
	 */
	public void doCheck(Request request, Response response){
		
	}
	
	/**
	 * 执行业务逻辑
	 * @Title: doOperation 
	 * @Description: TODO
	 * @param request
	 * @throws
	 */
	public abstract void doOperation(Request request, Response response);
}
