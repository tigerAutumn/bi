package com.pinting.gateway.cfca.out.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.gateway.cfca.out.model.BaseReqModel;
import com.pinting.gateway.cfca.out.model.BaseResModel;
import com.pinting.gateway.util.Constants;
import com.pinting.gateway.util.DESUtil;
import com.pinting.gateway.util.JsonLibUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 报文处理类（cfcatransfer作为服务端）
 * 
 * @Project:gateway
 * @Title:DafyOutMsgUtil.java
 * @author dingpf
 * @date 2015-2-11 下午5:06:32
 * @Copyright:2015 BiGangWan.com Inc. All rights reserved.
 */
public class CFCAOutMsgUtil {
	private static String CFCAOUTDESKEY = "bigangwan1343464564352134Oopinting.com1,.*";
	
	private static Logger log = LoggerFactory.getLogger(CFCAOutMsgUtil.class);

	/**
	 * 解析达飞响应报文：DES解密，获得明文，并转换成对应bean
	 * 
	 * @param ciphertext
	 *            密文（转换后为json格式）
	 * @param transCode
	 *            交易码
	 * @param listName
	 *            json报文内有列表的情况是，需告知列表名，没有则输入 null
	 * @return 对应报文bean
	 */
	public static BaseResModel parseMsg(String ciphertext, String transCode) {
		BaseResModel model = null;
		try {
			log.info("============CFCA返回的报文密文：【" + ciphertext + "】============");
			// DES解密，获得明文
			String decryptData = new DESUtil(CFCAOUTDESKEY).decryptStr(ciphertext);
			log.info("============解密获得明文：【" + decryptData + "】============");
			
			// 获得 报文bean类型名称
			StringBuffer modelClassName = new StringBuffer(
					Constants.GATEWAY2CFCA_MESSAGE_MODEL_PRE)
					.append(transCode.substring(0, 1).toUpperCase())
					.append(transCode.substring(1)).append("ResModel");
			//json 2 bean
			Class modelClass = Class.forName(modelClassName.toString());
			//判断是否需要嵌套转换
			String listName = null;
			Field[] fields = modelClass.getDeclaredFields();
			for (Field field : fields) {
				String fieldClassName = field.getType().getName();
				if(List.class.getName().equals(fieldClassName)){
					listName = field.getName();
					break;
				}
			}
			if (listName != null) {
				StringBuffer listClassName = new StringBuffer(
						Constants.CFCA_HESSIAN_MESSAGE_MODEL_PRE).append(
						listName.substring(0, 1).toUpperCase()).append(
						listName.substring(1));

				model = (BaseResModel) JsonLibUtil.json2Bean(decryptData,
						Class.forName(modelClassName.toString()), listName,
						Class.forName(listClassName.toString()));
			} else {
				model = (BaseResModel) JsonLibUtil.json2Bean(decryptData,
						Class.forName(modelClassName.toString()));
			}

		} catch (Exception e) {
			log.error("parse cfca response message error! cause by:", e);
		}

		return model;
	}

	/**
	 * 组装请求报文（发送给达飞）：组装hash，转换成json格式，并进行DES解密
	 * @param reqModel 待响应bean对象
	 * @return
	 */
	public static String packageMsg(BaseReqModel reqModel) {
		String ciphertext = null;
		try {
			// bean 转换成 json
			String message = JsonLibUtil.bean2Json(reqModel);

			log.info("============发送报文明文：【" + message + "】============");
			// 对json进行DES加密
			ciphertext = new DESUtil(CFCAOUTDESKEY).encryptStr(message);
			log.info("============发送报文密文：【" + ciphertext + "】============");
		} catch (Exception e) {
			log.error("package gateway request message error! cause by:", e);
		}
		return ciphertext;
	}


	public static void main(String[] args) throws UnsupportedEncodingException {
		String reqData = "{\"respCode\":\"someCode\",\"respMsg\":\"someMsg\",\"responseTime\":\"2015-01-16 11:11:11\"}";
		JSONObject jsonObject = JSONObject.fromObject(reqData);
		System.out.println(new DESUtil(CFCAOUTDESKEY).encryptStr(reqData));
		
		String s = "+YujmK/UczwyFv6x/bdLH51UdJMHFneqQYdgHPte1nGLI0mnaKJkg++gLCdEXMMzt/HrqvmS0lHQolBLoZzuDz0jNB8rf9RO00OYvNkytK1tNT15ax3tSgXZL+Tcu6l6VL7fFPUV70VVJzKC9R1q5KDi6OlHI8QhfzGhHjbJZR7czMQIJnbCitliLAxl9zBoFZrb/kiOOUU=";
		System.out.println(new DESUtil(CFCAOUTDESKEY).decryptStr(s));
	}
}
