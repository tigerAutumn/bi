package com.pinting.cfcatransfer.bigangwan.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.cfcatransfer.bigangwan.model.BaseReqModel;
import com.pinting.cfcatransfer.bigangwan.model.BaseResModel;
import com.pinting.cfcatransfer.util.DESUtil;
import com.pinting.cfcatransfer.util.JsonLibUtil;
import com.pinting.core.json.JsonValueProcessorImpl;


/**
 * 报文处理类
 * 
 * @Project: gateway
 * @Title: DafyInMsgUtil.java
 * @author dingpf
 * @date 2015-2-11 下午2:22:29
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class BGWInMsgUtil {
	private final static String BGWINDESKEY = "bigangwan1343464564352134Oopinting.com1,.*";
	private static Logger log = LoggerFactory.getLogger(BGWInMsgUtil.class);
	
	/**
	 * 解析达飞请求报文：DES解密，获得明文，并转换成对应bean
	 * 
	 * @param ciphertext
	 *            密文（转换后为json格式）
	 * @return 对应报文bean
	 */
	public static BaseReqModel parseMsg(String ciphertext) {
		BaseReqModel model = null;
		try {
			// DES解密，获得明文
			String decryptData = new DESUtil(BGWINDESKEY).decryptStr(ciphertext);
			log.debug("============解密成功，解密获得明文：【" + decryptData + "】============");
			// String 转 json
			// 获得 报文bean类型名称
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(Date.class,
					new JsonValueProcessorImpl());
			decryptData = decryptData.replace("\\", "").replace("\"[{", "[{").replace("}]\"", "}]");
			//获得json数组字符串
			String jsonList = "";
			if(decryptData.contains("[{")){
				jsonList = decryptData.substring(decryptData.indexOf("[{"), decryptData.indexOf("}]")+2);
			}
			JSONObject jsonObject = JSONObject.fromObject(decryptData, config);
			String transCode = jsonObject.getString("transCode");
			StringBuffer modelClassName = new StringBuffer(
					BGWInConstant.MESSAGE_MODEL_PRE)
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
			
			
//			JsonConfig config = new JsonConfig();
//			config.registerJsonValueProcessor(Date.class,
//					new JsonValueProcessorImpl());
//			JSONObject jsonObject = JSONObject.fromObject(decryptData, config);
//			// 获得 报文bean类型名称
//			String transCode = jsonObject.getString("transCode");
//			StringBuffer modelClassName = new StringBuffer(
//					Constants.DAFY2GATEWAY_MESSAGE_MODEL_PRE)
//					.append(transCode.substring(0, 1).toUpperCase())
//					.append(transCode.substring(1)).append("ReqModel");
//			// json 转 javabean
//			String[] dateFmts = new String[] { "yyy-MM-dd HH:mm:ss",
//					"yyyy-MM-dd" };
//			JSONUtils.getMorpherRegistry().registerMorpher(
//					new DateMorpher(dateFmts));
//			model = (BaseReqModel) JSONObject.toBean(jsonObject,
//					Class.forName(modelClassName.toString()));
		} catch (Exception e) {
			log.error("parse dafy request message error! cause by : ", e);
		}

		return model;
	}

	/**
	 * 组装响应报文：组装hash，转换成json格式，并进行DES解密
	 * 
	 * @param resModel
	 *            待响应bean对象
	 * @return
	 */
	public static String packageMsg(BaseResModel resModel) {
		String ciphertext = null;
		try {
			// bean 转换成 json
			String message = JsonLibUtil.bean2Json(resModel);
			log.debug("============发送报文：【" + message + "】============");
			// 对json进行DES加密
			ciphertext = new DESUtil(BGWINDESKEY).encryptStr(message);
			log.debug("============报文转换密文：【" + ciphertext + "】============");
		} catch (Exception e) {
			log.error("package gateway response message error! cause by : ", e);
		}
		return ciphertext;
	}
	
	/**
	 * 组装成功响应报文
	 * @param resModel
	 * @return
	 */
	public static String packageSuccMsg(BaseResModel resModel){
		resModel.setRespCode(BGWInConstant.RETURN_CODE_SUCCESS);
		resModel.setResponseTime(new Date());
		return packageMsg(resModel);
	}


	public static void main(String[] args) throws UnsupportedEncodingException {

	}
	
}
