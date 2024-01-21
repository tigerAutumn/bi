package com.pinting.gateway.business.in;

import java.util.HashMap;

import com.pinting.gateway.dafy.in.model.LoginReqModel;
import com.pinting.gateway.dafy.in.model.LoginResModel;
import com.pinting.gateway.dafy.out.util.DafyOutMsgUtil;
import com.pinting.gateway.util.HttpClientUtil;

/**
 * @Project: gateway
 * @Title: Test.java
 * @author Zhou Changzai
 * @date 2015-2-13 下午2:42:57
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class Test {
	public static void main(String[] args) {
		String url = "http://115.236.48.172/remote/dafy/business";
		LoginReqModel req = new LoginReqModel();
		req.setClientKey("abc");
		req.setClientSecret("xyz");
		req.setTransCode("login");
		
		String encryptStr = "aaa";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("DATA", encryptStr);
		String retJson = HttpClientUtil.sendRequest(url, map);
		System.out.println("返回的数据密文：" + retJson);
		
//		LoginResModel res = (LoginResModel)DafyOutMsgUtil.parseMsg(retJson, "login");
//		System.out.println("返回结果" + res.getRespMsg());
		
//		String str = "pf1I33atL7sC6sIncQNJNPgn2bm2ArkUUc9ZQp0W8scrYkVsBNw66Fj7rv2Ba1vSoWgqzpa3uIBnx/FoTRphohBfdhy3hA6wF2f0I8LEh+YlmmK78YMyzwoygSiUrFxhgfiAw4ccCWQWUpgaL3Z6QFCPJBi+1D1eBER/X09h56X5FTX+QKvu5gfDLnCnYtr/OaFwQB6pNRTN7Gvf7z3ldKBaiHlbEV5jkQ5W4RBSp1MKrH/YW2yoAw==";
//		
//		BindBankcardResultReqModel req = (BindBankcardResultReqModel)DafyInMsgUtil.parseMsg(str); 
//		
//		String validHash = DafyInMsgUtil.packageMsgHash(req, req.getTransCode());
//		if(StringUtil.isEmpty(req.getHash()) || !req.getHash().equals(MD5Util.encryptMD5(validHash))){
//			System.out.println("============达飞请求报文校验失败，请检查！============");
//		}
//		System.out.println(req.getCustomerIds());
	}
}
