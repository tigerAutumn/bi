package com.pinting.core.util.encrypt;

import java.security.MessageDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Util {
	private static final String ENCRYPTKEY = "inskeyforuser0123456789";
	private static final Logger LOG = LoggerFactory.getLogger(MD5Util.class);

	public static String encryptMD5(String s) {
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;

			for (int i = 0; i < j; i++) {
				byte b = md[i];
				str[(k++)] = hexDigits[(b >> 4 & 0xF)];
				str[(k++)] = hexDigits[(b & 0xF)];
			}

			return new String(str);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return null;
	}

	public static String encryptMD5(byte[] data) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(data);

			return BASE64Util.encryptBASE64(md5.digest());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return null;
	}

	public static String getEncryptkey() {
		return ENCRYPTKEY;
	}
	
	public static void main(String[] args) {
		System.out.println(MD5Util.encryptMD5("111111"+MD5Util.getEncryptkey()));
	}
}
