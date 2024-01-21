package com.pinting.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;

/**
 * <p>
 * </p>
 *
 * @author zhangc
 * @version $Id: EncryptUtil.java 2017-03-29 15:54 zhangc $
 */
public class AESEncryptUtil {

    public static final String KEY_ALGORITHM_RSA = "RSA";
    public static final String KEY_ALGORITHM_AES = "AES";

    public static final String PUBLIC_KEY = "RSAPublicKey";
    public static final String PRIVATE_KEY = "RSAPrivateKey";

    public static final String SIGNATURE_ALGORITHM_MD5_RSA = "MD5withRSA";
    
    public static void main(String[] args) {
    	String aesKey = "De8sM4FZvRCRia4V5wJudw==";
    	Map<String, String> sendMap = new HashMap<>();
    	sendMap.put("mobile", "15868157902");
    	sendMap.put("target", "fund_in");

    	JSONObject sendJSON = JSONObject.fromObject(sendMap);
//		String token = AESEncryptUtil.encryptByAESAndBase64(aesKey, sendJSON.toString());
    	
    	String token = null;
		try {
			token = URLEncoder.encode(AESEncryptUtil.encryptByAESAndBase64(aesKey, sendJSON.toString()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(token);
	}

    /**
     *
     * @Title: getCRC32Value
     * @Description: 获取字符串对应的重复概率较小的整形
     * @param str
     *            传入32位字符串
     * @return
     */
    public static String getCRC32Value(String str) {
        CRC32 crc32 = new CRC32();
        crc32.update(str.getBytes());
        return Long.toString(crc32.getValue());
    }

	/* AES相关------start */
    /**
     *
     * @Title: getAesRandomKeyString
     * @Description: 获取AES随机密钥字符串
     * @return
     */
    public static String getAESRandomKeyString() {
        // 随机生成密钥
        KeyGenerator keygen;
        try {
            keygen = KeyGenerator.getInstance(KEY_ALGORITHM_AES);
            SecureRandom random = new SecureRandom();
            keygen.init(random);
            Key key = keygen.generateKey();
            // 获取秘钥字符串
            String key64Str = Base64.encodeBase64String(key.getEncoded());
            return key64Str;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @Title: encryptByAESAndBase64
     * @Description: 使用AES加密，并返回经过BASE64处理后的密文
     * @param base64EncodedAESKey
     *            经过BASE64加密后的AES秘钥
     * @param dataStr
     * @return
     */
    public static String encryptByAESAndBase64(String base64EncodedAESKey,
                                               String dataStr) {
        SecretKey secretKey = restoreAESKey(base64EncodedAESKey);

        // 初始化加密组件
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(KEY_ALGORITHM_AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            // 加密后的数据，首先将字符串转为byte数组，然后加密，为便于保存先转为base64
            String encryptedDataStr = Base64
                    .encodeBase64String(cipher.doFinal(dataStr.getBytes()));
            return encryptedDataStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @Title: decryptByAESAndBase64
     * @Description: 使用AES解密，并返回经过BASE64处理后的密文
     * @param base64EncodedAESKey
     * @param encryptedDataStr
     * @return
     */
    public static String decryptByAESAndBase64(String base64EncodedAESKey,
                                               String encryptedDataStr) {
        SecretKey secretKey = restoreAESKey(base64EncodedAESKey);
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_AES);
            // 将加密组件的模式改为解密
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            // 和上面的加密相反，先解base64，再解密，最后将byte数组转为字符串
            String decryptedDataStr = new String(
                    cipher.doFinal(Base64.decodeBase64(encryptedDataStr)));
            return decryptedDataStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @Title: restoreAESKey
     * @Description: 还原BASE64加密后的AES密钥
     * @param base64EncodedAESKey
     * @return
     */
    public static SecretKey restoreAESKey(String base64EncodedAESKey) {
        // 还原秘钥字符串到秘钥byte数组
        byte[] keyByteArray = Base64.decodeBase64(base64EncodedAESKey);
        // 重新形成秘钥，SecretKey是Key的子类
        SecretKey secretKey = new SecretKeySpec(keyByteArray,
                KEY_ALGORITHM_AES);
        return secretKey;
    }

	/* AES相关------end */

	/* RSA相关------start */
    /**
     *
     * @Title: getRSARandomKeyPair
     * @Description: 生成RSA密钥对
     * @return
     */
    public static Map<String, String> getRSARandomKeyPair() {
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM_RSA);
            keyPairGen.initialize(512);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            String publicKeyStr = Base64
                    .encodeBase64String(publicKey.getEncoded());
            String privateKeyStr = Base64
                    .encodeBase64String(privateKey.getEncoded());

            Map<String, String> keyMap = new HashMap<String, String>(2);
            keyMap.put(PUBLIC_KEY, publicKeyStr);
            keyMap.put(PRIVATE_KEY, privateKeyStr);
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @Title: encryptByRSAPublicKeyAndBase64
     * @Description: RSA公钥加密：使用RSA公钥（BASE64加密后的字符串）对数据进行加密
     * @param publicRSAKey
     * @param dataStr
     * @return
     */
    public static String encryptByRSAPublicKeyAndBase64(String publicRSAKey,
                                                        String dataStr) {
        byte[] data = dataStr.getBytes();
        // 对公钥解密
        Key decodePublicKey = restoreRSAPublicKeyFromBase64KeyEncodeStr(
                publicRSAKey);

        // 对数据加密
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(KEY_ALGORITHM_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, decodePublicKey);
            byte[] encodedData = cipher.doFinal(data);
            String encodedDataStr = Base64.encodeBase64String(encodedData);
            return encodedDataStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     *
     * @Title: decryptByRSAPublicKeyAndBase64
     * @Description: RSA公钥解密：使用RSA公钥（BASE64加密后的字符串）对数据进行解密
     * @param publicRSAKey
     * @param encryptedDataStr
     * @return
     */
    public static String decryptByRSAPublicKeyAndBase64(String publicRSAKey,
                                                        String encryptedDataStr) {
        // 还原公钥
        Key decodePublicKey = restoreRSAPublicKeyFromBase64KeyEncodeStr(
                publicRSAKey);
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_RSA);
            cipher.init(Cipher.DECRYPT_MODE, decodePublicKey);
            byte[] decodedData = cipher
                    .doFinal(Base64.decodeBase64(encryptedDataStr));
            String decodedDataStr = new String(decodedData);
            return decodedDataStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     *
     * @Title: getSignFromEncryptedDataWithPrivateKey
     * @Description: 生成RSA签名：使用base64加密后的私钥和加密后的数据（byte数组形式），获取加密数据签名
     * @param privateRSAKey
     * @param encryotedDataStr
     * @return
     */
    public static String getSignFromEncryptedDataWithPrivateKey(
            String privateRSAKey, String encryotedDataStr) {
        byte[] data = Base64.decodeBase64(encryotedDataStr);
        // 加密后的数据+私钥，生成签名
        Key decodePrivateKey = restoreRSAPrivateKeyFromBase64KeyEncodeStr(
                privateRSAKey);
        Signature signature;
        try {
            signature = Signature.getInstance(SIGNATURE_ALGORITHM_MD5_RSA);
            signature.initSign((PrivateKey) decodePrivateKey); // 用的是私钥
            signature.update(data); // 用的是加密后的数据字节数组
            // 取得签名
            String sign = Base64.encodeBase64String((signature.sign()));
            return sign;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     *
     * @Title: verifySign
     * @Description: 验证RSA签名
     * @param publicRSAKey
     * @param sign
     * @param encryotedDataStr
     * @return
     */
    public static boolean verifySign(String publicRSAKey, String sign,
                                     String encryotedDataStr) {
        byte[] data = Base64.decodeBase64(encryotedDataStr);
        Key decodePublicKey = restoreRSAPublicKeyFromBase64KeyEncodeStr(
                publicRSAKey);
        // 初始化验证签名
        Signature signature;
        try {
            signature = Signature.getInstance(SIGNATURE_ALGORITHM_MD5_RSA);
            signature.initVerify((PublicKey) decodePublicKey); // 用的是公钥
            signature.update(data); // 用的是加密后的数据字节数组
            boolean ret = signature.verify(Base64.decodeBase64(sign));
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @Title: encryptByRSAPrivateKeyAndBase64
     * @Description: RSA私钥加密：使用RSA私钥（BASE64加密后的字符串）对数据进行加密
     * @param privateRSAKey
     * @param dataStr
     * @return
     */
    public static String encryptByRSAPrivateKeyAndBase64(String privateRSAKey,
                                                         String dataStr) {
        byte[] data = dataStr.getBytes();
        // 对私钥解密
        Key decodePrivateKey = restoreRSAPrivateKeyFromBase64KeyEncodeStr(
                privateRSAKey);

        // 对数据加密
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(KEY_ALGORITHM_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, decodePrivateKey);
            byte[] encodedData = cipher.doFinal(data);
            String encodedDataStr = Base64.encodeBase64String(encodedData);
            return encodedDataStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @Title: decryptByRSAPrivateKeyAndBase64
     * @Description: RSA私钥解密:使用RSA私钥（BASE64加密后的字符串）对数据进行解密
     * @param privateRSAKey
     * @param encryptedDataStr
     * @return
     */
    public static String decryptByRSAPrivateKeyAndBase64(String privateRSAKey,
                                                         String encryptedDataStr) {
        // 还原私钥
        Key decodePrivateKey = restoreRSAPrivateKeyFromBase64KeyEncodeStr(
                privateRSAKey);
        try {
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_RSA);
            cipher.init(Cipher.DECRYPT_MODE, decodePrivateKey);
            byte[] decodedData = cipher
                    .doFinal(Base64.decodeBase64(encryptedDataStr));
            String decodedDataStr = new String(decodedData);
            return decodedDataStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /*
     * 获取base64加密后的字符串的原始公钥
     */
    private static Key restoreRSAPublicKeyFromBase64KeyEncodeStr(
            String keyStr) {
        byte[] keyBytes = Base64.decodeBase64(keyStr);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        Key publicKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
            publicKey = keyFactory.generatePublic(x509KeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    /*
     * 获取base64加密后的字符串的原始私钥
     */
    private static Key restoreRSAPrivateKeyFromBase64KeyEncodeStr(
            String keyStr) {
        byte[] keyBytes = Base64.decodeBase64(keyStr);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        Key privateKey = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
            privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }
	/* RSA相关------end */

    /**
     * 去掉 + 和 /
     * @param content
     * @return
     */
    public static String escapeSymbol(String content) {
        if (content.indexOf("+") > 0 || content.indexOf("/") > 0) {
            content = content.replaceAll("\\+", ".");
            content = content.replaceAll("\\/", "_");
        }
        return content;
    }

    /**
     * 恢复 + 和 /
     * @param content
     * @return
     */
    public static String restoreSymbol(String content) {
        if (content.indexOf(".") > 0 || content.indexOf("_") > 0) {
            content = content.replaceAll("\\.", "+");
            content = content.replaceAll("\\_", "/");
        }
        return content;
    }
}