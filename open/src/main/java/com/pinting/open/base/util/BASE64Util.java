package com.pinting.open.base.util;

public class BASE64Util {

//	public static byte[] decryptBASE64(String key) {
//		if (StringUtil.isBlank(key))
//			return null;
//		try {
//			return new BASE64Decoder().decodeBuffer(key);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}

	public static String encryptBASE64(byte[] key) {
		try {
			return new BASE64Encoder().encode(key);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

//	public static void main(String[] args) {
//		String str = "中华人民共和国";
//		String eb = encryptBASE64(str.getBytes());
//		System.out.println("原文：" + str);
//		System.out.println("通过Base64加密后：" + eb);
//		String db = new String(decryptBASE64(eb));
//		System.out.println("通过Base64解密后：" + db);
//	}
}