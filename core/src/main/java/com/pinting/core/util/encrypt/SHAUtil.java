package com.pinting.core.util.encrypt;

import java.security.MessageDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHAUtil {
	private static final Logger LOG = LoggerFactory.getLogger(SHAUtil.class);

	public static byte[] encryptSHA(byte[] data) {
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA");
			sha.update(data);

			return sha.digest();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return null;
	}
}
