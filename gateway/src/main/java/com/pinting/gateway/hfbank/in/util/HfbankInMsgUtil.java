package com.pinting.gateway.hfbank.in.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.gateway.hfbank.in.model.BaseReqModel;
import com.pinting.gateway.util.BeanUtils;
import com.pinting.gateway.util.Constants;

/**
 * 
 * @project gateway
 * @title HfbankInMsgUtil.java
 * @author Dragon & cat
 * @date 2017-4-3
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class HfbankInMsgUtil {

	private static Logger log = LoggerFactory.getLogger(HfbankInMsgUtil.class);
	//恒丰测试环境证书(回调验签)
	//public static String certificateIn = "kl94bifcdmil6ash3wwpsp2z/3usx71o9amphpv5f22b8x-u8fb5b-77-1sf9q6329+hh/vigiiqsf/p3i1yhp6krbi4vr/oj6iplju+dsyckivarq5wd9ta/l9xjjk5q4np584l66165j-snpzi2chec1drii2awh25nz55fme679dyhb8tsdktz12kg6bhuy1knpxcqpjhxw4g7m/mi-sc4x1xn1dqsllcw5cpfpp6pbqcmbwa0exoj71s+//sdnpcc0ou8z6zmyp-3zo4u9adht/yy2y/i7jxtv81qorl8-aft14y2/36ol7r17in6hwel945zuhvkt2pyxtq231op56m4hbs+qtl5/9p2pe4jy6/0y23xxvpy/-uc+ncyxx/e/y6v8qatxwrj0u9egy3-u10kxfyalth5phzajbf6pixx+aik6jc7ykt27yij0+6wefx8+uej0rjjcdvlgswrt++nk8kao1b2zh8fsxudydglupx3mobr853h5+p";
	//恒丰模拟环境证书(回调验签)
	public static String certificateIn = "kl94bifcdmil6ash3wwpsp2z/3usx71o9amphpv5f22b8x-u8fb5b-77-1sf9q6329+hh/vigiiqsf/p3i1yhp6krbi4vr/oj6iplju+dsyckivarq5wd9ta/l9xjjk5q4np584l66165j-snpzi2chec1drii2awh25nz55fme679dyhb8tsdktz12kg6bhuy1knpxcqpjhxw4g7m/mi-sc4x1xn1dqsllcw5cpfpp6pbqcmbwa0exoj71s+//sdnpcc0ou8z6zmyp-3zo4u9adht/yy2y/i7jxtv81qorl8-aft14y2/36ol7r17in6hwel945zuhvkt2pyxtq231op56m4hbs+qtl5/9p2pe4jy6/0y23xxvpy/-uc+ncyxx/e/y6v8qatxwrj0u9egy3-u10kxfyalth5phzajbf6pixx+aik6jc7ykt27yij0+6wefx8+uej0rjjcdvlgswrt++nk8kao1b2zh8fsxudydglupx3mobr853h5+p";

	public static long lastAccessTime;//恒丰系统最后访问时间
	
	static{
		if(Constants.GATEWAY_SERVER_MODE_PROD.equals(GlobEnvUtil.get("server.mode"))){
			certificateIn = GlobEnvUtil.get("hfbank.certificate.in");
		}
	}

	/**
	 * 组装签名数据md5加密前的报文
	 * @param t model类型
	 * @param transCode 交易码
	 * @return
	 */
	private static <T> String packageMsgSign(T t) {

		TreeMap<String, Object> treeMap = new TreeMap<String, Object>(); 
		HashMap<String, Object> tempMap = BeanUtils.transBeanMap(t);
		for (Entry<String, Object> entry : tempMap.entrySet()) {
			Object tmpEntry = entry.getValue();
			
			if( tmpEntry != null && !"".equals(tmpEntry) ) {
				treeMap.put(entry.getKey(), entry.getValue());
			}
		}
		
		StringBuffer sign = new StringBuffer();
		
		Iterator iterator = treeMap.keySet().iterator();
		while (iterator.hasNext()) {
		    String key = (String) iterator.next();
		    Object obj =  (Object) treeMap.get(key);
			if(obj instanceof Date){
				obj = DateUtil.format((Date) obj);
			}
			if(obj instanceof ArrayList){
				Gson gson = new Gson();
				obj = gson.toJson(obj);
			}
			if (obj instanceof LinkedHashMap) {
				Gson gson = new Gson();
				obj =gson.toJson(obj);
			} 
			if (obj != null && !key.equals("signdata") && !"".equals(obj)) {
				sign.append(obj).append("|");
			}
		  
		}
		//去除|并返回a
		sign.append(certificateIn);
		return sign.toString();
	}
	
	
	/**
	 * 取得签名数据字符串
	 * @param req 待响应bean对象
	 * @return
	 */
	public static String getSignData(BaseReqModel req) {
		String signData = null;
		try {
			
			String sign = packageMsgSign(req);
			log.info("============组装报文，signData明文：【" + sign + "】============");
			String encryptSign = MD5Util.encryptMD5(sign);
			log.info("============组装报文，signData字段密文：【" + encryptSign + "】============");
			signData = encryptSign;
			
		} catch (Exception e) {
			log.error("package gateway request message error! cause by:", e);
		}
		return signData;
	}
	
	
	
	
	public static boolean checkSignData(BaseReqModel req) {
		String signData = "";
		try {
			
			String sign = packageMsgSign(req);
			log.info("============组装报文，signData明文：【" + sign + "】============");
			String encryptSign = MD5Util.encryptMD5(sign);
			log.info("============组装报文，signData字段密文：【" + encryptSign + "】============");
			signData = encryptSign;
			
			if (!signData.equals(req.getSigndata()) ) {
				return false;
			}
			
		} catch (Exception e) {
			log.error("package gateway request message error! cause by:", e);
			return false;
		}
		return true;
	}
	
	

	public static void main(String[] args) throws UnsupportedEncodingException {
		 //成标出账通知
		//http://localhost:9083/gateway/remote/hfbank/business/outOfAccount?plat_no=bgw-21005&order_no=123456&out_amt=10000&platucst=11&open_branch=abc&withdraw_account=622848&payee_name=shiyulong&pay_finish_date=20170411&pay_finish_time=202833&order_ status=1&signdata=dadad&host_req_serial_no=111111
	}
	
}
