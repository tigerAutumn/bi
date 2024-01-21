package com.dafy.core.util;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dafy.core.util.encrypt.MD5Util;

/**
 * 报文处理类（达飞作为客户端）
 * 
 * @Project: simulator-core
 * @Title: DafyInMsgUtil.java
 * @author yanwl
 * @date 2015-11-21 
 */
public class DafyInMsgUtil {
	private static final Logger log = LoggerFactory.getLogger(DafyInMsgUtil.class);
	private static final String TOKEN_SEED = "z1j5p2t7b9g0w";
	public static String token;//Token
	public static long lastAccessTime;//达飞客户端最后访问时间
	public static final long expiredTime = 30 * 60 * 1000;//达飞客户端超时时间，30分钟
	
	//生成token,用于给客户端
	public static void genToken(){
		token = MD5Util.encryptMD5(TOKEN_SEED + UUID.randomUUID() + System.currentTimeMillis());
		lastAccessTime = System.currentTimeMillis();
		log.debug("============生成token：【" + token + "】============");
	}
	
	//获取订单号（payOrderNo）
	public static String getPayOrderNo(){
		return MD5Util.encryptMD5(TOKEN_SEED + UUID.randomUUID() + System.currentTimeMillis());
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID() +""+ System.currentTimeMillis());
		System.out.println(MD5Util.encryptMD5(UUID.randomUUID() +""+ System.currentTimeMillis()));
	}
}

