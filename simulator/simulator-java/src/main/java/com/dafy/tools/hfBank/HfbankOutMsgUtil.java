package com.dafy.tools.hfBank;

import com.dafy.core.util.BeanUtils;
import com.dafy.core.util.DateUtil;
import com.dafy.core.util.encrypt.MD5Util;
import com.dafy.model.vo.BaseReqModel;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 报文处理类（达飞作为服务端）
 * 
 * @Project:gateway
 * @Title:DafyOutMsgUtil.java
 * @author dingpf
 * @date 2015-2-11 下午5:06:32
 * @Copyright:2015 BiGangWan.com Inc. All rights reserved.
 */
public class HfbankOutMsgUtil {
	
	
	private static Logger log = LoggerFactory.getLogger(HfbankOutMsgUtil.class);

	
	//恒丰测试环境证书
	//public static String certificate = "nqv1hfi7ukd8ar0kv4pcsyim8r986junj4vxo1jkvcxld3mbjsj4xo3cubha6d-dg+sl0xs79k63z48t8dl55g0mnyeqd9gk17suq47/gde76woko-t9vybt39y1v+7wx1-qdhzbm3nipa31anw+vl5-no2-vt5kzp913k0o14jl+9tze60act4vjwtr3j+avyvnmgs00w+p1s9tjjs28+y+3ow2tpr90hjad0mrn4r1y3fy53s03fcu5zp/ev82bjvz2w-eb/osxl8yk2nznepvk2yhya09wtax+zgxk2h/ca2k-4uorfbweeoe77n1nnud0hpcg30hwy581sjwo2m16ztwtqtsbfbfnertoe026h85/spcfnh0km+98al0jwuccl4hq+6m+a04jxfgw6vulvdye4p6-ayg7z2tmd-prkbkiab4939-ggugt/n22kogrkru7st7clfmrybdjbc7y6x+/s05jx-7mbd-ly8bjd9d6/20qbetp5+209ou";
	
	//恒丰模拟环境证书
	public static String certificate = "kl94bifcdmil6ash3wwpsp2z/3usx71o9amphpv5f22b8x-u8fb5b-77-1sf9q6329+hh/vigiiqsf/p3i1yhp6krbi4vr/oj6iplju+dsyckivarq5wd9ta/l9xjjk5q4np584l66165j-snpzi2chec1drii2awh25nz55fme679dyhb8tsdktz12kg6bhuy1knpxcqpjhxw4g7m/mi-sc4x1xn1dqsllcw5cpfpp6pbqcmbwa0exoj71s+//sdnpcc0ou8z6zmyp-3zo4u9adht/yy2y/i7jxtv81qorl8-aft14y2/36ol7r17in6hwel945zuhvkt2pyxtq231op56m4hbs+qtl5/9p2pe4jy6/0y23xxvpy/-uc+ncyxx/e/y6v8qatxwrj0u9egy3-u10kxfyalth5phzajbf6pixx+aik6jc7ykt27yij0+6wefx8+uej0rjjcdvlgswrt++nk8kao1b2zh8fsxudydglupx3mobr853h5+p";
	




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
			if(obj instanceof Double){
				obj = new DecimalFormat("0.00").format((obj));
			}
			if(obj instanceof ArrayList){
				Gson gson = new Gson();
				obj = gson.toJson(obj);
			}
			if (obj instanceof LinkedHashMap) {
				Gson gson = new Gson();
				obj =gson.toJson(obj);
			} 
			if (obj != null && !"".equals(obj)) {
				sign.append(obj).append("|");
			}
		  
		}
		//去除|并返回a
		sign.append(certificate);
		
		return sign.toString();
	}
	

}
