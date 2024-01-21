package com.pinting.gateway.business.in;

import java.util.HashMap;

import com.pinting.gateway.util.HttpClientUtil;

/**
 * @Project: gateway
 * @Title: TestHttp.java
 * @author Zhou Changzai
 * @date 2015-3-3 下午4:55:21
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class TestHttp {
	public static void main(String[] args) {
		String str = HttpClientUtil.sendRequest("http://118.186.255.63:10022/el_open/recharge/rechargeJump.jsp", new HashMap<String, String>());
		System.out.println(str);
	}
}
