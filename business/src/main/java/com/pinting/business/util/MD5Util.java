package com.pinting.business.util;




import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	
	private static final String ENCODE ="utf-8";
	/**
	 * MD5小写加密
	 * @param src
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static final String md5LowerCase(String src) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(src.getBytes(ENCODE));
		byte b[] = md.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();// 32位的加密
	}

	/**
	 * MD5大写加密
	 * @param s
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public final static String md5UpperCase(String s) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		byte[] btInput = s.getBytes(ENCODE);
		// 获得MD5摘要算法的 MessageDigest 对象
		MessageDigest mdInst = MessageDigest.getInstance("MD5");
		// 使用指定的字节更新摘要
		mdInst.update(btInput);
		// 获得密文
		byte[] md = mdInst.digest();
		// 把密文转换成十六进制的字符串形式
		// byte[] data = { (byte) 0xfe, (byte) 0xff, 0x00, 0x61 };
		int j = md.length;
		char str[] = new char[j * 2];
		int k = 0;

		for (int i = 0; i < j; i++) {
			byte byte0 = md[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(str);
	}
	
	public static String getPwd( String arg) throws Exception {
		
		String str = md5LowerCase(arg);
		
		StringBuilder pwd = new StringBuilder();
		pwd.append(str.substring(5, 10));
		pwd.append(str.substring(0, 5) );
		pwd.append(str.substring(20, 32) );
		pwd.append(str.substring(10, 20) );
		
		return pwd.toString();
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(getPwd("156571"));
	}

}