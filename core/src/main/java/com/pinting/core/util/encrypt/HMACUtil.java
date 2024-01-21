package com.pinting.core.util.encrypt;

import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HMACUtil {
	private static final Logger LOG = LoggerFactory.getLogger(HMACUtil.class);

	public static String initMacKey() {
		SecretKey secretKey = null;
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
			secretKey = keyGenerator.generateKey();
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e.getMessage(), e);

			return null;
		}

		return BASE64Util.encryptBASE64(secretKey.getEncoded());
	}

	public static byte[] encryptHMAC(byte[] data, String key) {
		SecretKey secretKey = new SecretKeySpec(BASE64Util.decryptBASE64(key),
				"HmacMD5");
		Mac mac = null;
		try {
			mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);

			return null;
		}

		return mac.doFinal(data);
	}
}
