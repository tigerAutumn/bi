package com.pinting.gateway.loan7.in.util;

import com.pinting.core.json.JsonValueProcessorImpl;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.gateway.loan7.in.model.BaseReqModel;
import com.pinting.gateway.loan7.in.model.BaseResModel;
import com.pinting.gateway.util.BeanUtils;
import com.pinting.gateway.util.Constants;
import com.pinting.gateway.util.DESUtil;
import com.pinting.gateway.util.JsonLibUtil;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 报文处理类（7贷作为客户端）
 * 
 * @Project: gateway
 * @Title: DafyInMsgUtil.java
 * @author dingpf
 * @date 2015-2-11 下午2:22:29
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class Loan7InMsgUtil {
	private static final String TOKEN_SEED = "z1j5p2t7b9g0w";
	public static String token;//Token
	public static long lastAccessTime;//达飞客户端最后访问时间
	public static final long expiredTime = 30 * 60 * 1000;//达飞客户端超时时间，30分钟
//	private final static String dafyInDESKey = "pinting1234567890Oopinting.com1,.*";
	private final static String dafyInDESKey = "CCMu*X.,Yd5Fffy.cAm1,.*";
	private static Logger log = LoggerFactory.getLogger(Loan7InMsgUtil.class);
	
	//交易码数据用来作为组装报文的依据，key为交易码，value为需要组装的参数的key
	private static Map<String, List<String>> transUnitMap = new HashMap<String, List<String>>();
	private static String[] transUnits = new String[]{
		//入参组装顺序
		"login,transCode,clientKey,clientSecret,requestTime",//登录
		"realCertificateResult,token,transCode,requestTime,customerIds,result",//注册结果通知
		"bindBankcardResult,token,transCode,requestTime,customerIds,results,resultMsgs",//绑卡通知
		"buyProductResult,token,transCode,requestTime,result,applyNo,payChannel,actAmount,moneyType",//购买产品通知
		"receiveMoneyNotice,token,transCode,requestTime,data",//客户回款通知
		"customerWithdrawResult,token,transCode,requestTime,applyNo,result,successTime,customerId,bankcard,amount,moneyType,transType,failReason",//用户提现通知
		"sysWithdrawResult,token,transCode,requestTime,applyNo,result,successTime,amount,moneyType,transType,failReason",//网新提现通知
		"customerWithdrawCheck,token,transCode,requestTime,applyNo,applyTime,customerId,bankcard,amount,transType",//用户提现申请验证回调
		"sysBuyProductNotice,token,transCode,requestTime,clientKey,payPlatform,finshTime,productOrderNo,productCode,productAmount,result,errorMsg",//达飞通知网新系统购买成功
		"sysReturnMoneyNotice,token,transCode,requestTime,clientKey,payPlatform,merchantId,payOrderNo,payReqTime,payFinshTime,amount,productOrderNo,productCode,productAmount,productInterest,productReturnTerm",//达飞通知网新已回款
		//返回组装的顺序
		"loginRes,respCode,respMsg,responseTime,token",
		"realCertificateResultRes,respCode,respMsg,responseTime",
		"bindBankcardResultRes,respCode,respMsg,responseTime",
		"buyProductResultRes,respCode,respMsg,responseTime",
		"receiveMoneyNoticeRes,respCode,respMsg,responseTime",
		"customerWithdrawResultRes,respCode,respMsg,responseTime",
		"sysWithdrawResultRes,respCode,respMsg,responseTime",
		"customerWithdrawCheckRes,respCode,respMsg,responseTime,checkResult",
		"sysBuyProductNoticeRes,respCode,respMsg,responseTime",
		"sysReturnMoneyNoticeRes,respCode,respMsg,responseTime",
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
	 * 解析达飞请求报文：DES解密，获得明文，并转换成对应bean
	 * 
	 * @param ciphertext
	 *            密文（转换后为json格式）
	 * @return 对应报文bean
	 */
	public static BaseReqModel parseMsg(String ciphertext) {
		BaseReqModel model = null;
		if(StringUtil.isNotBlank(ciphertext)) {
			try {
				// DES解密，获得明文
				String decryptData = new DESUtil(dafyInDESKey).decryptStr(ciphertext);
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
					jsonList = decryptData.substring(decryptData.indexOf("[{"), decryptData.indexOf("}]")+2);
				}
				JSONObject jsonObject = JSONObject.fromObject(decryptData, config);
				String transCode = jsonObject.getString("transCode");
				StringBuffer modelClassName = new StringBuffer(
						Constants.LOAN72GATEWAY_MESSAGE_MODEL_PRE)
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
				log.error("parse dafy request message error! cause by : ", e);
			}
		}

		return model;
	}

	/**
	 * 组装响应报文（响应给达飞）：组装hash，转换成json格式，并进行DES解密
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
			ciphertext = new DESUtil(dafyInDESKey).encryptStr(message);
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

	public static void main(String[] args) throws UnsupportedEncodingException {
//		String s = "{\"transCode\":\"registerResult\",\"customerId\":\"10000001\",\"result\":\"0\",\"requestTime\":\"2015-02-13 12:12:12\",\"hash\":\"737bcaa63ec667821cfd9af8c10fb1a2\"}";
//		String hash = "token=c33dd0bba92d2233600e104acc57f957&transCode=bindBankcardResult&requestTime=2015-02-26 11:29:55&customerIds=71886&result=0";
//		
//		System.out.println(new DESUtil(dafyInDESKey).encryptStr(s));
//		System.out.println(MD5Util.encryptMD5(hash));
		
		//BaseReqModel req = DafyInMsgUtil.parseMsg("YkRd2s0VZCaKTWyPHajpB56WE7jv7P+elGvDJvFCdTyq/07vaLS0Tg3sCvKiCB+vO8Aid/S7aspxZ6l8Gxs92zSfUUZeUshxe9kzp+J6sq3dhvyJR97r4ptJ1RHkHnDExtPtp86W/hDlFfvoj4Qjcr8Grk22YtYMmZwc+B1ocKd+oa7dI4OK0VQzku0m1aoLs4EdNdXYsAI/HbslyfsjTguEbMmdXU1xopi0blX0sTRHI1UZCvDSE/Ep04cVNEvwQUq3DtsmIMt2/Y8QvmGbQjwg/Anl3IstQjnBjMMj8NlLJRg8BcJFm/QFNRYBBaZlHkBMcA16S2onrtuKNOg9nW0qw0tjt1rQdYsYJ6sp6jY=");
		//System.out.println();
		
		/*String decryptData = "{transCode:\"login\",requestTime:\"2015-02-10 21:11:11\",\"data\":[{\"name\":\"111\"}, {\"name\":\"222\"}],\"clientKey:\"INSIGMA\",clientSecret:\"INSiGMADaFy0o1\",hash:\"840479a661701ffcdb9edf1f9e490e48\"}";
		
		String jsonList = "";
		if(decryptData.contains("[{")){
			jsonList = decryptData.substring(decryptData.indexOf("[{"), decryptData.indexOf("}]")+2);
		}
		
		System.out.println(jsonList);*/
		
		/*String hashMing = "token=7e6b50daaeeee4a2785102ae2ab01b08&transCode=buyProductResult&requestTime=2015-04-14 10:54:02&result=1&applyNo=150414000002416&payChannel=工商银行YZ&actAmount=5000.00&moneyType=0";
		String msg = "{\"token\":\"7e6b50daaeeee4a2785102ae2ab01b08\",\"transCode\":\"buyProductResult\",\"requestTime\":\"2015-04-14 10:54:02\",\"result\":\"1\",\"applyNo\":\"150414000002416\",\"payChannel\":\"工商银行YZ\",\"actAmount\":\"5000.00\",\"moneyType\":\"0\",\"hash\":\"56046196c9364ef5b591bca2ab54e33c\"}";
		System.out.println(MD5Util.encryptMD5(hashMing));
		System.out.println(new DESUtil(dafyInDESKey).encryptStr(msg));
		System.out.println(URLEncoder.encode(new DESUtil(dafyInDESKey).encryptStr(msg), "utf-8"));*/
		
		//达飞登录串
		System.out.println(URLEncoder.encode("J/qVmWoIferSQYRnl7cHC8Jw6ABdWa7o2FVZNRvmdtlCzgE+eh2U5VQkqaZEglLowAkCc76TNEB5il1A64em+DIVwtUxoC3Lu20J2IhRr46T2YV+3oy5nfj7KFcpkHAdbP/Qm0G7btWRzjpyy2Yh3s3AAdLr820q1kJ1do49ib6leATXM8IGvFigMO4M0u4be5BCyh+HMEwyKK48yu0u3Q==","UTF-8"));
		//回款通知明文hash
		String hash = "token=583c1484e8e1f8acff1b732dfb7e2ab3&transCode=receiveMoneyNotice&requestTime=2016-02-24 00:08:54&data=[{\"cardNo\":\"330124198803300023\",\"bankNo\":\"6228480329144156174\",\"orderNo\":\"151125000008334\",\"status\":\"0\",\"successTime\":\"2016-02-24 00:01:08\",\"name\":\"周涵\",\"amountLx\":\"493.15\",\"amountBj\":\"20000.00\",\"productId\":\"028\"},{\"cardNo\":\"410901199004130826\",\"bankNo\":\"6225880168532076\",\"orderNo\":\"151125000008301\",\"status\":\"0\",\"successTime\":\"2016-02-24 00:06:09\",\"name\":\"刘佳\",\"amountLx\":\"1232.88\",\"amountBj\":\"50000.00\",\"productId\":\"028\"}]";
		System.out.println(MD5Util.encryptMD5(hash));
		//回款报文
		String message = "{\"requestTime\":\"2016-02-24 00:08:54\",\"hash\":\"a7da627c700780483f991d91ee310207\",\"token\":\"583c1484e8e1f8acff1b732dfb7e2ab3\",\"data\":\"[{\"cardNo\":\"330124198803300023\",\"bankNo\":\"6228480329144156174\",\"orderNo\":\"151125000008334\",\"status\":\"0\",\"successTime\":\"2016-02-24 00:01:08\",\"name\":\"周涵\",\"amountLx\":\"493.15\",\"amountBj\":\"20000.00\",\"productId\":\"028\"},{\"cardNo\":\"410901199004130826\",\"bankNo\":\"6225880168532076\",\"orderNo\":\"151125000008301\",\"status\":\"0\",\"successTime\":\"2016-02-24 00:06:09\",\"name\":\"刘佳\",\"amountLx\":\"1232.88\",\"amountBj\":\"50000.00\",\"productId\":\"028\"}]\",\"transCode\":\"receiveMoneyNotice\"}";
		System.out.println(URLEncoder.encode(new DESUtil(dafyInDESKey).encryptStr(message), "utf-8"));
	}

	public static String getString(String message){
		String string = "";
		try {
			string = URLEncoder.encode(new DESUtil(dafyInDESKey).encryptStr(message), "utf-8");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return string;
	}
	
}
