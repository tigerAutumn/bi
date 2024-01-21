package com.pinting.business.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

public class CASignature {
	private static Logger log = LoggerFactory.getLogger(CASignature.class);
	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	public static String ENCODING = "UTF-8";

	/**
	 * 加载证书库，并获得证书库KeyStore对象
	 * 
	 * @param ksPath
	 *            证书库路径
	 * @param ksPassword
	 *            证书库密码
	 * @return KeyStore对象
	 */
	public static KeyStore getKeyStore(String ksPath, String ksPassword) {
		FileInputStream in = null;
		KeyStore ks = null;
		try {
			in = new FileInputStream(ksPath);
			ks = KeyStore.getInstance("JKS");
			ks.load(in, ksPassword.toCharArray());
		} catch (IOException e) {
			log.error("找不到证书库", e);
		} catch (KeyStoreException e) {
			log.error("创建KeyStore实例失败", e);
		} catch (NoSuchAlgorithmException e) {
			log.error("无此算法", e);
		} catch (CertificateException e) {
			log.error("加载证书失败", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ks;
	}

	/**
	 * 获得私钥
	 * 
	 * @param ks
	 *            证书库
	 * @param keyAlias
	 *            私钥名称
	 * @param keyPass
	 *            私钥密码
	 * @return 私钥对象
	 */
	public static PrivateKey getPrivateKey(KeyStore ks, String keyAlias,
			String keyPass) {
		PrivateKey privateKey = null;
		try {
			privateKey = (PrivateKey) ks
					.getKey(keyAlias, keyPass.toCharArray());
		} catch (UnrecoverableKeyException e) {
			log.error("证书库中的证书已 损坏", e);
		} catch (KeyStoreException e) {
			log.error("证书库异常", e);
		} catch (NoSuchAlgorithmException e) {
			log.error("无此算法", e);
		}
		return privateKey;
	}

	/**
	 * 获得公钥
	 * 
	 * @param ks
	 *            证书库
	 * @param publicKeyAlias
	 *            公钥名称
	 * @return
	 */
	public static PublicKey getPublicKey(KeyStore ks, String publicKeyAlias) {
		Certificate cert = null;
		PublicKey publicKey = null;
		try {
			cert = ks.getCertificate(publicKeyAlias);
			publicKey = cert.getPublicKey();// 公钥
		} catch (KeyStoreException e) {
			log.error("证书库异常", e);
		}
		return publicKey;
	}

	/**
	 * 消息签名
	 * 
	 * @param message
	 *            消息字符串
	 * @param privateKey
	 *            私钥
	 * @return 签名后byte数组
	 */
	public static byte[] sign(String message, PrivateKey privateKey) {
		byte[] signMessage = null;
		try {
			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(privateKey);
			signature.update(message.getBytes(ENCODING));
			signMessage = signature.sign();
		} catch (NoSuchAlgorithmException e) {
			log.error("无此算法", e);
		} catch (InvalidKeyException e) {
			log.error("无效的私钥", e);
		} catch (SignatureException e) {
			log.error("签名异常", e);
		} catch (UnsupportedEncodingException e) {
			log.error("不支持的编码格式", e);
		}
		return signMessage;
	}

	/**
	 * 文件签名
	 * 
	 * @param file
	 *            文件
	 * @param privateKey
	 *            私钥
	 * @return 签名后byte数组
	 */
	public static byte[] sign(File file, PrivateKey privateKey) {
		byte[] sign = null;
		if (file.exists()) {
			FileInputStream in = null;
			try {
				Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
				signature.initSign(privateKey);
				in = new FileInputStream(file);
				byte[] temp = new byte[2048];
				int length = 0;
				while ((length = in.read(temp)) != -1) {
					signature.update(temp, 0, length);
				}
				sign = signature.sign();
			} catch (NoSuchAlgorithmException e) {
				log.error("无此算法", e);
			} catch (InvalidKeyException e) {
				log.error("无效的私钥", e);
			} catch (FileNotFoundException e) {
				log.error("无法找到文件", e);
			} catch (IOException e) {
				log.error("文件读取失败", e);
			} catch (SignatureException e) {
				log.error("签名异常", e);
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sign;
	}

	/**
	 * 签名后byte数组 转 签名字符串
	 * 
	 * @param sign
	 *            签名后byte数组
	 * @return 签名字符串
	 */
	public static String sign2Base64(byte[] sign) {
		return Base64Utils.encodeToString(sign);
	}

	/**
	 * 签名后byte数组 转 签名字符串，并写入到指定文件
	 * 
	 * @param sign
	 *            签名后byte数组
	 * @param out
	 *            指定文件（用于写入签名字符串）
	 * @return 签名字符串
	 */
	public static String sign2Base64(byte[] sign, File out) {
		String signEncoding = Base64Utils.encodeToString(sign);
		log.info("======>签名字符串：" + signEncoding);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(out);
			pw.println(signEncoding);
		} catch (FileNotFoundException e) {
			log.error("无法找到文件", e);
		} finally {
			if (pw != null) {
				pw.flush();
				pw.close();
			}
		}
		return signEncoding;
	}

	/**
	 * 消息签名验签
	 * 
	 * @param message
	 *            消息
	 * @param sign
	 *            签名串
	 * @param publicKey
	 *            公钥
	 * @return
	 */
	public static boolean verify(String message, String sign,
			PublicKey publicKey) {
		boolean verifyResult = false;
		try {
			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(publicKey);
			signature.update(message.getBytes(ENCODING));
			verifyResult = signature.verify(Base64Utils.decodeFromString(sign));
		} catch (NoSuchAlgorithmException e) {
			log.error("无此算法", e);
		} catch (InvalidKeyException e) {
			log.error("无效的公钥", e);
		} catch (SignatureException e) {
			log.error("签名异常", e);
		} catch (UnsupportedEncodingException e) {
			log.error("未支持的编码格式", e);
		}
		return verifyResult;
	}

	/**
	 * 文件签名验签
	 * 
	 * @param messageFile
	 *            消息文件
	 * @param signFile
	 *            签名文件
	 * @param publicKey
	 *            公钥
	 * @return
	 */
	public static boolean verify(File messageFile, File signFile,
			PublicKey publicKey) {
		boolean verifyResult = false;
		FileInputStream messageIn = null;
		BufferedReader signIn = null;
		try {
			Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
			signature.initVerify(publicKey);
			messageIn = new FileInputStream(messageFile);
			byte[] temp = new byte[2048];
			int length = 0;
			while ((length = messageIn.read(temp)) != -1) {
				signature.update(temp, 0, length);
			}
			signIn = new BufferedReader(new FileReader(signFile));
			String sign = "";
			String line = "";
			while( (line = signIn.readLine()) != null){
				sign += line;
			}
			verifyResult = signature.verify(Base64Utils.decodeFromString(sign));
		} catch (NoSuchAlgorithmException e) {
			log.error("无此算法", e);
		} catch (InvalidKeyException e) {
			log.error("无效的公钥", e);
		} catch (FileNotFoundException e) {
			log.error("无法找到文件", e);
		} catch (SignatureException e) {
			log.error("签名异常", e);
		} catch (IOException e) {
			log.error("文件读取失败", e);
		} finally {
			try {
				if (messageIn != null) {
					messageIn.close();
				}
				if (signIn != null) {
					signIn.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return verifyResult;
	}

	/**
	 * 根据别名从证书库获得证书
	 * 
	 * @param ks
	 *            证书库
	 * @param alias
	 *            证书别名
	 * @return
	 */
	public static Certificate getCer(KeyStore ks, String alias) {
		Certificate cer = null;
		try {
			cer = ks.getCertificate(alias);
		} catch (KeyStoreException e) {
			log.error("获取证书失败", e);
		}
		return cer;
	}

	/**
	 * 验证证书是否即将（三个月内）到期
	 * 
	 * @param cer
	 *            证书
	 * @param date
	 *            时间
	 * @return
	 */
	public static boolean isWillValidity(X509Certificate cer, Date date) {
		Date endDate = cer.getNotAfter();
		if (endDate.compareTo(DateUtils.addDays(date, 90)) <= 0) {
			log.warn("证书将于【"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(endDate) + "】过期，请及时申请更换！");
			return true;
		}
		return false;
	}

	/**
	 * 验证证书是否已过期或者是否无效
	 * 
	 * @param cer
	 *            证书
	 * @param date
	 *            时间
	 * @return
	 */
	public static boolean isValidity(X509Certificate cer, Date date) {

		try {
			cer.checkValidity(date);
		} catch (CertificateExpiredException e) {
			log.error("证书已过期", e);
			return true;
		} catch (CertificateNotYetValidException e) {
			log.error("证书无效", e);
			return true;
		}
		return false;
	}
	//私钥配置
	public static String KEY_PASSWORD = "111111";
	public static String KEY_NAME = "041@800000000@徽商银行hsepay@00000002";
	public static String KEY_FILE_PATH = "F:/ca_key/kserver.keystore";
	public static String SOURCE_FILE = "F:/20150404.xls";
	public static String SIGN_FILE = "F:/20150404.sgn";
	//公钥配置
	public static String KEY_STORE_PASSWORD = "abc123";
	public static String PUBLIC_KEY_NAME = "041@8110105015336539@达飞普惠财富投资管理(北京)有限公司@00000002";
	public static String PUBLIC_KEY_FILE_PATH = "C:/Users/Administrator/Desktop/ca_key_正式证书/insigma.keystore";
	public static String SOURCE_FILE_COPY = "C:/Users/Administrator/Desktop/ca_key_正式证书/20150429.zip";
	public static String SIGN_FILE_COPY = "C:/Users/Administrator/Desktop/ca_key_正式证书/20150429zip.sgn";

	public static void main(String[] args) {
		// 签名 模拟
//		dafySign();

		// 验签 模拟
		wangxinVerify();
	}
	
	public static void dafySign(){
		KeyStore ks = getKeyStore(KEY_FILE_PATH, KEY_STORE_PASSWORD);
		
		boolean isValidity = isValidity((X509Certificate) getCer(ks, PUBLIC_KEY_NAME), new Date());
		log.info("======>证书有效性校验结果：" + isValidity);
		if(!isValidity){
			boolean isWillValidity = isWillValidity((X509Certificate) getCer(ks, PUBLIC_KEY_NAME), new Date());
			log.info("======>证书是否即将到期结果：" + isWillValidity);
			if(isWillValidity){
				//通知 即将到期！！！需要更新证书
				log.warn("即将到期！！！需要更新证书");
			}else{
				byte[] signB = sign(new File(SOURCE_FILE),
						getPrivateKey(ks, KEY_NAME, KEY_PASSWORD));
				sign2Base64(signB, new File(SIGN_FILE));
			}
		}else{
			//无效证书！！！
			log.error("无效证书！！！");
		}
	}
	
	public static boolean wangxinVerify(){
		boolean verifyFlag = false;
		KeyStore publicKs = getKeyStore(PUBLIC_KEY_FILE_PATH, KEY_STORE_PASSWORD);
		boolean isValidity = isValidity((X509Certificate) getCer(publicKs, PUBLIC_KEY_NAME), new Date());
		log.info("======>证书有效性校验结果：" + isValidity);
		if(!isValidity){
			boolean isWillValidity = isWillValidity((X509Certificate) getCer(publicKs, PUBLIC_KEY_NAME), new Date());
			log.info("======>证书是否即将到期结果：" + isWillValidity);
			if(isWillValidity){
				//通知 即将到期！！！需要更新证书
				log.warn("即将到期！！！需要更新证书");
			}
			verifyFlag = verify(new File(SOURCE_FILE_COPY), new File(
					SIGN_FILE_COPY), getPublicKey(publicKs, PUBLIC_KEY_NAME));
			log.info("======>验签结果：" + verifyFlag);
		}else{
			//无效证书！！！
			log.error("无效证书！！！");
		}
		return verifyFlag;
	}

}
