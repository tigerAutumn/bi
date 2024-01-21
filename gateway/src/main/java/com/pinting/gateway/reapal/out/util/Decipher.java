package com.pinting.gateway.reapal.out.util;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.pinting.gateway.reapal.out.config.ReapalConfig;

public class Decipher {

	private static Logger logger = LoggerFactory.getLogger(Decipher.class);
	
	/**
	 * 解密1
	 * 
	 * @param merchant_id
	 * @param data
	 * @param encryptkey
	 * @return
	 * @throws com.reapal.common.exception.ServiceException
	 */
	public static String decryptData(String post) throws Exception {

		// 将返回的json串转换为map

		TreeMap<String, String> map = JSON.parseObject(post,
				new TypeReference<TreeMap<String, String>>() {
				});
		String encryptkey = map.get("encryptkey");
		String data = map.get("data");

		// 获取自己私钥解密
		PrivateKey pvkformPfx = RSA.getPvkformPfx(ReapalConfig.privateKey,
				ReapalConfig.password);
		String decryptData = RSA.decrypt(encryptkey, pvkformPfx);

		post = AES.decryptFromBase64(data, decryptData);

		return post;
	}

	/**
	 * 解密2
	 * 
	 * @param merchant_id
	 * @param data
	 * @param encryptkey
	 * @return
	 * @throws com.reapal.common.exception.ServiceException
	 */
	public static String decryptData(String encryptkey, String data)
			throws Exception {

		// 获取自己私钥解密
		PrivateKey pvkformPfx = RSA.getPvkformPfx(ReapalConfig.privateKey, ReapalConfig.password);
		
		String decryptKey = RSA.decrypt(encryptkey, pvkformPfx);

		return AES.decryptFromBase64(data, decryptKey);

	}
	

	/**
	 * 加密
	 * 
	 * @param merchant_id
	 * @param data
	 * @param encryptkey
	 * @return
	 * @throws com.reapal.common.exception.ServiceException
	 */
	public static Map<String, String> encryptData(String json) throws Exception {
		logger.info("代证书加密字符串为：【" + json + "】");

		// 商户获取融宝公钥
		PublicKey pubKeyFromCrt = RSA.getPubKeyFromCRT(ReapalConfig.pubKeyUrl);
		// 随机生成16数字
		String key = getRandom(16);

		// 使用RSA算法将商户自己随机生成的AESkey加密
		String encryptKey = RSA.encrypt(key, pubKeyFromCrt);
		// 使用AES算法用随机生成的AESkey，对json串进行加密
		String encryData = AES.encryptToBase64(json, key);

		logger.info("密文key为：【" + encryptKey + "】");
		logger.info("密文数据为：【" + encryData + "】");

		Map<String, String> map = new HashMap<String, String>();
		map.put("data", encryData);
		map.put("encryptkey", encryptKey);

		return map;
	}

	public static Random random = new Random();

	public static String getRandom(int length) {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < length; i++) {
			boolean isChar = (random.nextInt(2) % 2 == 0);// 输出字母还是数字
			if (isChar) { // 字符串
				int choice = (random.nextInt(2) % 2 == 0) ? 65 : 97; // 取得大写字母还是小写字母
				ret.append((char) (choice + random.nextInt(26)));
			} else { // 数字
				ret.append(Integer.toString(random.nextInt(10)));
			}
		}
		return ret.toString();
	}

	public static void main(String[] args) {
		
		String encryptkey = "F3bi+tQydkwTlUO2XYMqXyq/zaDdXiiP0A5TvNdq23ADKietKxCuHfxVcD+r8MwsrptGFBlxOOC4KffTr5PXB1Fxj5GFpAJY6lo2ZpcRrirfKWd5NrX9D7jmgkqM/QVu7GkHloBJAgPV610Q+k9bFrQT+YklvftkoMFNInaeG10=";
		String data = "r8c5ZbRv+4KuWOhah4S9Muw0QKS38x36NGyfM0Ix/rGVYMVQU9k5x2x2amnKjy9w1fJcka9k0Ay1B4oNei4Dfv3lCW0Y++aLHeAyNSWDZBsny5np0+0vtEOup63QWkfx8aUKJT3EYgvHbQJFPp8B7X0QY0kFMRHhPp9pi4LBXq5oe8CjTON09M3Kuasb+u2FOWR4mDnCoGgTRA2hccFLWDIAmeMUAIfOZH2RAJAen9QljlGxk1ddJhQkdJPlbVr1/FedI8WvTHB7UGaZuFEvdCS2mEI9I0wsA2JCnP7e9bU=";
		
		try {
			System.out.println("解密内容为：" + decryptData(encryptkey, data));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("==================================");
		
		String ss ="{"
				+"\"merchant_id\""+":"+"\"100000000075980\""+","+"\"notify_id\""
				+":"+"\"8a0ace9092bc4abeaccf2cb5afc948a4\""+","+"\"order_no\""
				+":"+"\"IGWAMOYH6P88\""+","+"\"sign\""+":"+"\"a0fcb65af9f937c4355a3330d79f4197\""
				+","+"\"status\""+":"+"\"TRADE_FINISHED\""+","+"\"total_fee\""+":"+"1"
				+","+"\"trade_no\""+":"+"\"101511125564257\""+"}" ;
			 
       //System.out.println("*****************"+ss);
		
		try {
			//System.out.println("加密内容为：" + encryptData(decryptData(encryptkey, data)));
			System.out.println("加密内容为：" + encryptData(ss));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
