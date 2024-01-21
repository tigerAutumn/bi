package com.dafy.core.util.encrypt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dafy.core.util.StringUtil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class BASE64Util {
	private static final Logger LOG = LoggerFactory.getLogger(BASE64Util.class);

	public static byte[] decryptBASE64(String key) {
		if (StringUtil.isBlank(key))
			return null;
		try {
			return new BASE64Decoder().decodeBuffer(key);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}

		return null;
	}

	public static String encryptBASE64(byte[] key) {
		try {
			return new BASE64Encoder().encodeBuffer(key);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return null;
	}

	public static void main(String[] args) {
		String str = "中华人民共和国";
		String eb = encryptBASE64(str.getBytes());
		System.out.println("原文：" + str);
		System.out.println("通过Base64加密后：" + eb);
		String db = new String(decryptBASE64(eb));
		System.out.println("通过Base64解密后：" + db);
	}
}