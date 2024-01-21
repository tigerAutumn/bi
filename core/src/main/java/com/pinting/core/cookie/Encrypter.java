package com.pinting.core.cookie;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Encrypter {
	private static final Logger LOG = LoggerFactory.getLogger(Encrypter.class);

	private static byte[] salt = { -87, -101, -56, 50, 86, 53, -29, 3 };

	private static int iterationCount = 19;
	private Cipher ecipher;
	private Cipher dcipher;

	public Encrypter() {
		String passPhrase = "!@#$%^&*()_&^$%^$%$#@KJOIJ*&W&^T$%$#W@%*&(U)(JUOIJJIieohfiuehgtru";
		try {
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt,
					iterationCount);
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
					.generateSecret(keySpec);
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt,
					iterationCount);

			this.ecipher = Cipher.getInstance(key.getAlgorithm());
			this.dcipher = Cipher.getInstance(key.getAlgorithm());
			this.ecipher.init(1, key, paramSpec);
			this.dcipher.init(2, key, paramSpec);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);

			throw new RuntimeException("Failed to initialize cookie encoder", e);
		}
	}

	public byte[] encrypt(byte[] plaintext) {
		try {
			return this.ecipher.doFinal(plaintext);
		} catch (Exception e) {
			LOG.error(
					"Failed to encrypt object" + ArrayUtils.toString(plaintext),
					e);
		}

		return null;
	}

	public byte[] decrypt(byte[] cryptotext) {
		try {
			return this.dcipher.doFinal(cryptotext);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return null;
	}
}
