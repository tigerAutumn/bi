package com.pinting.core.util.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSAUtil {
	private static final Logger LOG = LoggerFactory.getLogger(RSAUtil.class);

	public static String sign(byte[] data, String privateKey) {
		byte[] keyBytes = BASE64Util.decryptBASE64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initSign(priKey);
			signature.update(data);

			return BASE64Util.encryptBASE64(signature.sign());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return null;
	}

	public static boolean verify(byte[] data, String publicKey, String sign) {
		byte[] keyBytes = BASE64Util.decryptBASE64(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(keySpec);
			Signature signature = Signature.getInstance("MD5withRSA");

			signature.initVerify(pubKey);
			signature.update(data);

			return signature.verify(BASE64Util.decryptBASE64(sign));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return false;
	}

	public static byte[] decryptByPrivateKey(byte[] data, String key) {
		byte[] keyBytes = BASE64Util.decryptBASE64(key);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(2, privateKey);

			return cipher.doFinal(data);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return null;
	}

	public static byte[] decryptByPublicKey(byte[] data, String key) {
		byte[] keyBytes = BASE64Util.decryptBASE64(key);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key publicKey = keyFactory.generatePublic(x509KeySpec);

			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(2, publicKey);

			return cipher.doFinal(data);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return null;
	}

	public static byte[] encryptByPublicKey(byte[] data, String key) {
		byte[] keyBytes = BASE64Util.decryptBASE64(key);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key publicKey = keyFactory.generatePublic(x509KeySpec);

			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(1, publicKey);

			return cipher.doFinal(data);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return null;
	}

	public static byte[] encryptByPrivateKey(byte[] data, String key) {
		byte[] keyBytes = BASE64Util.decryptBASE64(key);

		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(1, privateKey);

			return cipher.doFinal(data);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		return null;
	}

	public static String getPrivateKey(RSAPrivateKey privateKey) {
		return BASE64Util.encryptBASE64(privateKey.getEncoded());
	}

	public static String getPublicKey(RSAPublicKey publicKey) {
		return BASE64Util.encryptBASE64(publicKey.getEncoded());
	}

	public static RSAKey initKey() {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			keyPairGen.initialize(1024);

			KeyPair keyPair = keyPairGen.generateKeyPair();

			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

			RSAKey rSAKey = new RSAKey();
			rSAKey.setPublicKey(publicKey);
			rSAKey.setPrivateKey(privateKey);

			return rSAKey;
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e.getMessage(), e);
		}

		return null;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		RSAKey key = initKey();
		System.out.println(getPrivateKey(key.getPrivateKey()));
		System.out.println(getPublicKey(key.getPublicKey()));
	}
}