package com.dafy.core.constant;

import org.apache.commons.lang.StringUtils;

import java.util.ResourceBundle;

/**
 * 系统常量配置
 * @author zousheng
 * @date 2015-11-19
 */
public class Globals {

	protected static ResourceBundle res;

	public static String getProperty(String key, String defaultStr) {
		return StringUtils.isBlank(res.getString(key)) ? defaultStr : res.getString(key).trim();
	}

	static {
		if (res == null)
			res = ResourceBundle.getBundle("dafy");
	}
	
	public static final String DAFY_OUT_DES_KEY = Globals.getProperty("dafy.out.des.key","");
	public static final String DAFY_IN_DES_KEY = Globals.getProperty("dafy.in.des.key","");
	public static final String DAFY_CLIENT_KEY = Globals.getProperty("dafy.client.key","");
	public static final String DAFY_CLIENT_SECRET = Globals.getProperty("dafy.client.secret","");
	public static final String DAFY_BUY_PRODUCT_NOTICE_URL = Globals.getProperty("dafy.buy.product.notice.url","");
	public static final String DAFY_RETURN_MONEY_NOTICE_URL = Globals.getProperty("dafy.return.money.notice.url","");
	public static final String DAFY_PAY_PLATFORM = Globals.getProperty("dafy.pay.platform","");
	public static final String DAFY_MERCHANT_ID = Globals.getProperty("dafy.merchant.id","");
	
	public static final String LOAN7_OUT_DES_KEY = Globals.getProperty("7dai.out.des.key","");
	public static final String LOAN7_IN_DES_KEY = Globals.getProperty("7dai.in.des.key","");
	public static final String LOAN7_CLIENT_KEY = Globals.getProperty("7dai.client.key","");
	public static final String LOAN7_CLIENT_SECRET = Globals.getProperty("7dai.client.secret","");
	

	public static final String QIDIAN_OUT_DES_KEY = Globals.getProperty("qidian.out.des.key","");
	public static final String QIDIAN_CLIENT_KEY = Globals.getProperty("qidian.client.key","");
	public static final String QIDIAN_CLIENT_SECRET = Globals.getProperty("qidian.client.secret","");
	public static final String QIDIAN_IN_DES_KEY = Globals.getProperty("qidian.in.des.key","");
}
