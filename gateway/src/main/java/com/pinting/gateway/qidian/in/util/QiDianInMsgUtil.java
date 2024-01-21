package com.pinting.gateway.qidian.in.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.core.json.JsonValueProcessorImpl;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.gateway.qidian.in.model.BaseReqModel;
import com.pinting.gateway.qidian.in.model.BaseResModel;
import com.pinting.gateway.util.BeanUtils;
import com.pinting.gateway.util.Constants;
import com.pinting.gateway.util.DESUtil;
import com.pinting.gateway.util.JacksonUtil;
import com.pinting.gateway.util.JsonLibUtil;

/**
 * 报文处理类（七店作为客户端）
 * @project gateway
 * @title QiDianInMsgUtil.java
 * @author Dragon & cat
 * @date 2018-3-21
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class QiDianInMsgUtil {
	private static final String TOKEN_SEED = "z1j5p2t7b9g0w";
	public static String token;//Token
	public static long lastAccessTime;//七店客户端最后访问时间
	public static final long expiredTime = 30 * 60 * 1000;//七店客户端超时时间，30分钟
	private  static String qiDianInDESKey = "pintingBigangwanQidian.deskey";
	public  static String qiDianInClientKey = "channelqidiankey001";
	public  static String qiDianInClientSecret = "qidianclientSecret&Ke6!3";
	private static Logger log = LoggerFactory.getLogger(QiDianInMsgUtil.class);
	
	
	static{
		if(Constants.GATEWAY_SERVER_MODE_PROD.equals(GlobEnvUtil.get("server.mode"))||Constants.GATEWAY_SERVER_MODE_TEST.equals(GlobEnvUtil.get("server.mode"))){
			qiDianInDESKey = GlobEnvUtil.get("qidian.in.des.key");
			qiDianInClientKey = GlobEnvUtil.get("qidian.in.client.key");
			qiDianInClientSecret = GlobEnvUtil.get("qidian.in.client.secret");
		}
	}
	
	//交易码数据用来作为组装报文的依据，key为交易码，value为需要组装的参数的key
	private static Map<String, List<String>> transUnitMap = new HashMap<String, List<String>>();
	private static String[] transUnits = new String[]{
		//入参组装顺序
		"login,transCode,clientKey,clientSecret,requestTime",//登录
		"franchiseeRegist,token,transCode,requestTime,clientKey,franchisees",//店主创建
		
		//返回组装的顺序
		"loginRes,respCode,respMsg,responseTime,token",
		"franchiseeRegistRes,respCode,respMsg,responseTime,franchiseeResult",//店主创建
		"baseRes,respCode,respMsg,responseTime"
	};
	
	//包装交易码数据
	static{
		for (String units : transUnits) {
			String[] unitArr = units.split(",");
			ArrayList<String> unitList = new ArrayList<String>();
			for (int i = 1; i < unitArr.length; i++) {
				unitList.add(unitArr[i]);
			}
			transUnitMap.put(unitArr[0], unitList);
		}
	}
	
	//生成token,用于给客户端
	public static void genToken(){
		token = MD5Util.encryptMD5(TOKEN_SEED + UUID.randomUUID() + System.currentTimeMillis());
		lastAccessTime = System.currentTimeMillis();
	}

	/**
	 * 解析七店请求报文：DES解密，获得明文，并转换成对应bean
	 * 
	 * @param ciphertext
	 *            密文（转换后为json格式）
	 * @return 对应报文bean
	 */
	public static BaseReqModel parseMsg(String ciphertext) {
		BaseReqModel model = null;
		try {
			// DES解密，获得明文
			String decryptData = new DESUtil(qiDianInDESKey).decryptStr(ciphertext);
			log.info("============解密成功，解密获得明文：【" + decryptData + "】============");
			// String 转 json
			// 获得 报文bean类型名称
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(Date.class,
					new JsonValueProcessorImpl());
			decryptData = decryptData.replace("\\", "").replace("\"[{", "[{").replace("}]\"", "}]");
			//获得json数组字符串
			String jsonList = "";
			if(decryptData.contains("[{")){
				jsonList = decryptData.substring(decryptData.indexOf("[{"), decryptData.lastIndexOf("}]")+2);
			}
			JSONObject jsonObject = JSONObject.fromObject(decryptData, config);
			String transCode = jsonObject.getString("transCode");
			StringBuffer modelClassName = new StringBuffer(
					Constants.QIDIAN2GATEWAY_MESSAGE_MODEL_PRE)
					.append(transCode.substring(0, 1).toUpperCase())
					.append(transCode.substring(1)).append("ReqModel");
			//json 2 bean
			Class modelClass = Class.forName(modelClassName.toString());
			//判断是否需要嵌套转换
			String listName = null;
			String listClassName = null;
			Field[] fields = modelClass.getDeclaredFields();
			for (Field field : fields) {
				String fieldClassName = field.getType().getName();
				if(List.class.getName().equals(fieldClassName)){
					listName = field.getName();//获取列表数据变量名
					Type type = field.getGenericType();//获取List列表变量类型
					if(type instanceof ParameterizedType){
						ParameterizedType pt = (ParameterizedType)type;
						Class<?> c = (Class<?>)pt.getActualTypeArguments()[0];
						listClassName = c.getName();
					}
					break;
				}
			}
			if (listName != null) {//如果有信息列表（嵌套转换）
				if(listClassName == null){//如果为null，是model定义有问题，没有把泛型加入进来，需要定义泛型类型
					throw new Exception("列表数据需定义泛型，请检查");
				}
				model = (BaseReqModel) JsonLibUtil.json2Bean(decryptData,
						Class.forName(modelClassName.toString()), listName,
						Class.forName(listClassName));
				model.setJsonList(jsonList);
			} else {
				model = (BaseReqModel) JsonLibUtil.json2Bean(decryptData,
						Class.forName(modelClassName.toString()));
			}
			
			
		} catch (Exception e) {
			log.error("parse qidian request message error! cause by : ", e);
		}

		return model;
	}

	/**
	 * 组装响应报文（响应给七店）：组装hash，转换成json格式，并进行DES解密
	 * 
	 * @param resModel
	 *            待响应bean对象
	 * @return
	 */
	public static String packageMsg(BaseResModel resModel) {
		String ciphertext = null;
		try {
			// String hash = packageMsgHash(resModel);
			String resModelClassName = resModel.getClass().getCanonicalName();
			String transCode = resModelClassName.substring(
					resModelClassName.lastIndexOf(".") + 1,
					resModelClassName.indexOf("ResModel"));

			String hash = packageMsgHash(resModel, transCode.substring(0, 1).toLowerCase().concat(transCode.substring(1)) + "Res");
			log.info("============hash字段明文：【" + hash + "】============");
			String encryptHash = MD5Util.encryptMD5(hash);
			log.info("============hash字段密文：【" + encryptHash + "】============");
			
			resModel.setHash(encryptHash);
			// bean 转换成 json
			String message = JsonLibUtil.bean2Json(resModel);
			log.info("============发送报文：【" + message + "】============");
			// 对json进行DES加密
			ciphertext = new DESUtil(qiDianInDESKey).encryptStr(message);
			log.info("============报文转换密文：【" + ciphertext + "】============");
		} catch (Exception e) {
			log.error("package gateway response message error! cause by : ", e);
		}
		return ciphertext;
	}

	/**
	 * 组装响应报文（响应给七店）：组装hash，转换成json格式，并进行DES解密
	 * 
	 * @param resModel
	 *            待响应bean对象
	 * @return
	 */
	public static String packageMsg4AmountIsNull(BaseResModel resModel) {
		String ciphertext = null;
		try {
			// String hash = packageMsgHash(resModel);
			String resModelClassName = resModel.getClass().getCanonicalName();
			String transCode = resModelClassName.substring(
					resModelClassName.lastIndexOf(".") + 1,
					resModelClassName.indexOf("ResModel"));

			String hash = packageMsgHash(resModel, transCode.substring(0, 1).toLowerCase().concat(transCode.substring(1)) + "Res");
			log.info("============hash字段明文：【" + hash + "】============");
			String encryptHash = MD5Util.encryptMD5(hash);
			log.info("============hash字段密文：【" + encryptHash + "】============");
			
			resModel.setHash(encryptHash);
			// bean 转换成 json
			String message = JacksonUtil.bean2Json(resModel);
			log.info("============发送报文：【" + message + "】============");
			// 对json进行DES加密
			ciphertext = new DESUtil(qiDianInDESKey).encryptStr(message);
			log.info("============报文转换密文：【" + ciphertext + "】============");
		} catch (Exception e) {
			log.error("package gateway response message error! cause by : ", e);
		}
		return ciphertext;
	}

	
	/**
	 * 根据交易码,组装md5加密前的报文
	 * @param t model类型
	 * @param transCode 交易码
	 * @return
	 */
	public static <T> String packageMsgHash(T t, String transCode) {
		// 组装hash字段
		List<String> unitList = transUnitMap.get(transCode);
		if(unitList == null){
			return null;
		}
		HashMap<String, Object> tempMap = BeanUtils.transBeanMap(t);
		StringBuffer hash = new StringBuffer();
		for (String unit : unitList) {
			Object obj = tempMap.get(unit);
			if(obj instanceof Date){
				obj = DateUtil.format((Date) obj);
			}
			if(obj instanceof Double){
				obj = new DecimalFormat("0.00").format((obj));
			}
			if("jsonList".equals(unit)){
				continue;
			}
			if(obj instanceof List){
				hash.append(unit).append("=").append(tempMap.get("jsonList")).append("&");
				continue;
			}
			hash.append(unit).append("=").append(obj).append("&");
		}
		//去除&并返回
		log.info("==========hash明文：【"+hash.substring(0, hash.length() - 1)+"】===========");
		return hash.substring(0, hash.length() - 1);
	}
	
	
	/**
	 * 根据交易码,组装md5加密前的报文
	 * @param t model类型
	 * @param transCode 交易码
	 * @return
	 */
	public static <T> String qiDianPackageMsgHash(T t, String transCode) {
		// 组装hash字段
		List<String> unitList = transUnitMap.get(transCode);
		if(unitList == null){
			return null;
		}
		HashMap<String, Object> tempMap = BeanUtils.transBeanMap(t);
		StringBuffer hash = new StringBuffer();
		for (String unit : unitList) {
			Object obj = tempMap.get(unit);
			if(obj instanceof Date){
				obj = DateUtil.format((Date) obj);
			}
			if(obj instanceof Double){
				obj = new DecimalFormat("0.00").format((obj));
			}
			if("jsonList".equals(unit)){
				continue;
			}
			if(obj instanceof List){
				hash.append(unit).append("=").append(tempMap.get("jsonList")).append("&");
				continue;
			}
			if (obj == null) {
				hash.append(unit).append("=").append("").append("&");
			}else {
				hash.append(unit).append("=").append(obj).append("&");
			}
			
		}
		//去除&并返回
		log.info("==========hash明文：【"+hash.substring(0, hash.length() - 1)+"】===========");
		return hash.substring(0, hash.length() - 1);
	}
	
	
	public static String getString(String message){
		String string = "";
		try {
			string = URLEncoder.encode(new DESUtil(qiDianInDESKey).encryptStr(message), "utf-8");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return string;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {

		//登录
		String loginMessage = "{transCode:\"login\",requestTime:\"2018-03-21 14:08:54\",clientKey:\"channelqidiankey001\",clientSecret:\"qidianclientSecret&Ke6!3\",hash:\"16c237c4258427f396b26f3f30a6015c\"}";
		System.out.println("登录:"+URLEncoder.encode(new DESUtil(qiDianInDESKey).encryptStr(loginMessage), "utf-8"));
		
		
		//店主创建
		String franchiseeRegistMessage = "{\"requestTime\":\"2018-03-21 14:08:54\",\"hash\":\"f47391ede15c8681a38c37b63a25f2db\",\"token\":\"c1f6edca-f446-4bfb-8cca-8e5ba0a6ff4d\",\"franchisees\":\"[{\"franchiseeId\":\"franchiseeId002\",\"franchiseeName\":\"七店店主名字2\",\"franchiseeMobile\":\"15868157903\",\"tradeName\":\"店名\",\"businessCode\":\"businessCode002\",\"area\":\"华东\",\"branchCompany\":\"杭州币港湾\",\"province\":\"浙江\",\"city\":\"杭州\",\"district\":\"江干区\",\"address\":\"西子国际\",\"note\":\"备注\"},{\"franchiseeId\":\"franchiseeId001\",\"franchiseeName\":\"七店店主名字\",\"franchiseeMobile\":\"15868157902\",\"tradeName\":\"店名\",\"businessCode\":\"businessCode001\",\"area\":\"华东\",\"branchCompany\":\"杭州币港湾\",\"province\":\"浙江\",\"city\":\"杭州\",\"district\":\"江干区\",\"address\":\"西子国际\",\"note\":\"备注\"}]\",\"transCode\":\"franchiseeRegist\"}";
		System.out.println("店主创建:"+URLEncoder.encode(new DESUtil(qiDianInDESKey).encryptStr(franchiseeRegistMessage), "utf-8"));
		
	}
	
}
