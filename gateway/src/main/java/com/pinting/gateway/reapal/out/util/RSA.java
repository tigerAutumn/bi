package com.pinting.gateway.reapal.out.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/*
 --------------------------------------------**********--------------------------------------------

 该算法于1977年由美国麻省理工学院MIT(Massachusetts Institute of Technology)的Ronal Rivest，Adi Shamir和Len Adleman三位年轻教授提出，并以三人的姓氏Rivest，Shamir和Adlernan命名为RSA算法，是一个支持变长密钥的公共密钥算法，需要加密的文件快的长度也是可变的!

 所谓RSA加密算法，是世界上第一个非对称加密算法，也是数论的第一个实际应用。它的算法如下：

 1.找两个非常大的质数p和q（通常p和q都有155十进制位或都有512十进制位）并计算n=pq，k=(p-1)(q-1)。

 2.将明文编码成整数M，保证M不小于0但是小于n。

 3.任取一个整数e，保证e和k互质，而且e不小于0但是小于k。加密钥匙（称作公钥）是(e, n)。

 4.找到一个整数d，使得ed除以k的余数是1（只要e和n满足上面条件，d肯定存在）。解密钥匙（称作密钥）是(d, n)。

 加密过程： 加密后的编码C等于M的e次方除以n所得的余数。

 解密过程： 解密后的编码N等于C的d次方除以n所得的余数。

 只要e、d和n满足上面给定的条件。M等于N。

 --------------------------------------------**********--------------------------------------------
 */


import javax.crypto.Cipher;

public class RSA {
	/** 指定key的大小 */
	private static int KEYSIZE = 1024;
    public static final String CHAR_ENCODING = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";
	/**
	 * 生成密钥对
	 */
	public static Map<String, String> generateKeyPair() throws Exception {
		/** RSA算法要求有一个可信任的随机数源 */
		SecureRandom sr = new SecureRandom();
		/** 为RSA算法创建一个KeyPairGenerator对象 */
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		/** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
		kpg.initialize(KEYSIZE, sr);
		/** 生成密匙对 */
		KeyPair kp = kpg.generateKeyPair();
		/** 得到公钥 */
		Key publicKey = kp.getPublic();
		byte[] publicKeyBytes = publicKey.getEncoded();
		String pub = new String(Base64.encodeBase64(publicKeyBytes),
				CHAR_ENCODING);
		/** 得到私钥 */
		Key privateKey = kp.getPrivate();
		byte[] privateKeyBytes = privateKey.getEncoded();
		String pri = new String(Base64.encodeBase64(privateKeyBytes),
				CHAR_ENCODING);

		Map<String, String> map = new HashMap<String, String>();
		map.put("publicKey", pub);
		map.put("privateKey", pri);
		RSAPublicKey rsp = (RSAPublicKey) kp.getPublic();
		BigInteger bint = rsp.getModulus();
		byte[] b = bint.toByteArray();
		byte[] deBase64Value = Base64.encodeBase64(b);
		String retValue = new String(deBase64Value);
		map.put("modulus", retValue);
		return map;
	}

    /**
     * 通过PFX文件获得私钥
     *
     * @param //文件路径
     * @param //PFX密码
     * @return PrivateKey
     */
    public static PrivateKey getPvkformPfx(String strPfx, String strPassword)
            throws Exception {
        PrivateKey prikey = null;
        char[] nPassword = null;
        if ((strPassword == null) || strPassword.trim().equals("")) {
            nPassword = null;
        } else {
            nPassword = strPassword.toCharArray();
        }
        KeyStore ks = getKsformPfx(strPfx, strPassword);
        String keyAlias = getAlsformPfx(strPfx, strPassword);
        prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
        return prikey;
    }

    /**
     * 通过PFX文件获得KEYSTORE
     *
     * @param //文件路径
     * @param //PFX密码
     * @return KeyStore
     */
    public static KeyStore getKsformPfx(String strPfx, String strPassword)
            throws Exception {
        FileInputStream fis = null;
        Security
                .addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        KeyStore ks = KeyStore.getInstance("PKCS12", "BC");
        fis = new FileInputStream(strPfx);
        // If the keystore password is empty(""), then we have to set
        // to null, otherwise it won't work!!!
        char[] nPassword = null;
        if ((strPassword == null) || strPassword.trim().equals("")) {
            nPassword = null;
        } else {
            nPassword = strPassword.toCharArray();
        }
        ks.load(fis, nPassword);
        if (null != fis) {

            fis.close();

        }
        return ks;

    }

    /**
     * 通过PFX文件获得别名
     *
     * @param  //文件路径
     * @param //PFX密码
     * @return 别名
     */
    public static String getAlsformPfx(String strPfx, String strPassword)
            throws Exception {
        String keyAlias = null;
        KeyStore ks = getKsformPfx(strPfx, strPassword);
        Enumeration<String> enumas = ks.aliases();
        keyAlias = null;
        // we are readin just one certificate.
        if (enumas.hasMoreElements()) {
            keyAlias = (String) enumas.nextElement();
        }
        return keyAlias;
    }

    public static PublicKey getPubKeyFromCRT(String crtFileName) throws Exception {
        InputStream is = new FileInputStream(crtFileName);
        CertificateFactory cf = CertificateFactory.getInstance("x509");
        Certificate cerCert = cf.generateCertificate(is);
        return cerCert.getPublicKey();
    }

	/**
	 * 加密方法 source： 源数据
	 */
	public static String encrypt(String source, String publicKey)
			throws Exception {
		Key key = getPublicKey(publicKey);
		/** 得到Cipher对象来实现对源数据的RSA加密 */
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] b = source.getBytes();
		/** 执行加密操作 */
		byte[] b1 = cipher.doFinal(b);
		return new String(Base64.encodeBase64(b1),
				CHAR_ENCODING);
	}
    /**
     * 加密方法 source： 源数据
     */
    public static String encrypt(String source, PublicKey pubKeyFromCrt)
            throws Exception {
        Key key = pubKeyFromCrt;
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] b = source.getBytes();
        /** 执行加密操作 */
        byte[] b1 = cipher.doFinal(b);
        return new String(Base64.encodeBase64(b1),
                CHAR_ENCODING);
    }
	/**
	 * 解密算法 cryptograph:密文
	 */
	public static String decrypt(String cryptograph, PrivateKey privateKey)
			throws Exception {
		Key key = privateKey;
		/** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] b1 = Base64.decodeBase64(cryptograph.getBytes());
		/** 执行解密操作 */
		byte[] b = cipher.doFinal(b1);
		return new String(b);
	}

	/**
	 * 得到公钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String key) throws Exception {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
				Base64.decodeBase64(key.getBytes()));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 得到私钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
				Base64.decodeBase64(key.getBytes()));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public static String sign(String content, String privateKey) {
		String charset = CHAR_ENCODING;
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					Base64.decodeBase64(privateKey.getBytes()));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			Signature signature = Signature.getInstance("SHA1WithRSA");

			signature.initSign(priKey);
			signature.update(content.getBytes(charset));

			byte[] signed = signature.sign();

			return new String(Base64.encodeBase64(signed));
		} catch (Exception e) {

		}

		return null;
	}
	
	public static boolean checkSign(String content, String sign, String publicKey)
	{
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode2(publicKey);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		
			Signature signature = Signature
			.getInstance("SHA1WithRSA");
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes("utf-8") );
		
			boolean bverify = signature.verify( Base64.decode2(sign) );
			return bverify;
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}	

}