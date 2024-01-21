package com.pinting.gateway.loan7.in.util;

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
import com.pinting.gateway.loan7.in.model.BaseReqModel;
import com.pinting.gateway.loan7.in.model.BaseResModel;
import com.pinting.gateway.util.BeanUtils;
import com.pinting.gateway.util.Constants;
import com.pinting.gateway.util.DESUtil;
import com.pinting.gateway.util.JacksonUtil;
import com.pinting.gateway.util.JsonLibUtil;

/**
 * 报文处理类（7贷存管作为客户端）
 * 
 * @Project: gateway
 * @Title: DepLoan7InMsgUtil.java
 * @author dingpf
 * @date 2015-2-11 下午2:22:29
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class DepLoan7InMsgUtil {
	private static final String TOKEN_SEED = "z1j5p2t7b9g0w";
	public static String token;//Token
	public static long lastAccessTime;//7贷存管客户端最后访问时间
	public static final long expiredTime = 30 * 60 * 1000;//7贷存管客户端超时时间，30分钟
	private  static String depLoan7InDESKey = "pinting1234567890Oopinting.com1,.*";
	public  static String depLoan7InClientKey = "channeldafykey1230";
	public  static String depLoan7InClientSecret = "dafy7987!&Ke6!3";
	private static Logger log = LoggerFactory.getLogger(DepLoan7InMsgUtil.class);
	
	
	static{
		if(Constants.GATEWAY_SERVER_MODE_PROD.equals(GlobEnvUtil.get("server.mode"))||Constants.GATEWAY_SERVER_MODE_TEST.equals(GlobEnvUtil.get("server.mode"))){
			depLoan7InDESKey = GlobEnvUtil.get("7dai.dep.in.des.key");
			depLoan7InClientKey = GlobEnvUtil.get("7dai.dep.in.client.key");
			depLoan7InClientSecret = GlobEnvUtil.get("7dai.dep.in.client.secret");
		}
	}
	
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
		"sysBuyProductNotice,token,transCode,requestTime,clientKey,payPlatform,finshTime,productOrderNo,productCode,productAmount,result,errorMsg",//7贷存管通知网新系统购买成功
		"sysReturnMoneyNotice,token,transCode,requestTime,clientKey,payPlatform,merchantId,payOrderNo,payReqTime,payFinshTime,amount,productOrderNo,productCode,productAmount,productInterest,productReturnTerm",//7贷存管通知网新已回款
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
		"baseRes,respCode,respMsg,responseTime",
		
		//入参组装顺序-自主放款相关
		"queryDailyAmount,token,transCode,requestTime,clientKey,queryDate",//查询每日限额
		"applyLoan,token,transCode,requestTime,clientKey,userId,name,idCard,mobile,bankCard,bankCode,orderNo,applyTime,businessType,loanId,loanAmount,loanFee,loanTerm,loanRate,subjectName,purpose,creditAmount,loanedAmount,creditLevel,creditScore,loanTimes,breakTimes,breakMaxDays,workUnit,education,marriage,monthlyIncome",//放款
		"fillFinish,token,transCode,requestTime,clientKey,orderNo,payOrderNo,applyTime,finishTime,fillType,amount",//补账完成通知
		"queryLoanResult,token,transCode,requestTime,clientKey,orderNo,applyDate",//放款结果查询
		"lateRepay,token,transCode,requestTime,clientKey,orderNo,payOrderNo,applyTime,finishTime,totalAmount,repayments",//代偿通知
		"queryRepayResult,token,transCode,requestTime,clientKey,orderNo,applyDate",//还款结果查询
		"pushBill,token,transCode,requestTime,clientKey,userId,loanId,agreementNo,agreementUrl,repayments",//账单（还款计划）推送
		"querySignResult,token,transCode,requestTime,clientKey,agreementNo",//借款协议签章结果查询
		"activeRepayPre,token,transCode,requestTime,clientKey,orderNo,userId,name,idCard,mobile,bankCard,bankCode,loanId,totalAmount,repayments",//主动还款预下单
		"activeRepayConfirm,token,transCode,requestTime,clientKey,bgwOrderNo,smsCode",//主动还款正式下单
		"cutRepayConfirm,token,transCode,requestTime,clientKey,orderNo,overdueFlag,userId,name,idCard,mobile,bankCard,bankCode,totalAmount,isOffline,offPayOrderNo,userSignNo,payIP,loans",//代扣还款
		"agreementNotice,token,transCode,requestTime,clientKey,loanId",//协议下载地址查询
		"activeRepaySmsCodeRepeat,token,transCode,requestTime,clientKey,orderNo",//还款短信验证码重发
		
		
		//返回组装的顺序-自主放款相关
		"queryDailyAmountRes,respCode,respMsg,responseTime,loanDate,amount",//查询每日限额
		"applyLoanRes,respCode,respMsg,responseTime",//放款
		"fillFinishRes,respCode,respMsg,responseTime",//补账完成通知
		"queryLoanResultRes,respCode,respMsg,responseTime,orderNo,bgwOrderNo,loanId,channel,resultCode,resultMsg,finishTime,lenders",//放款结果查询
		"lateRepayRes,respCode,respMsg,responseTime",//代偿通知
		"queryRepayResultRes,respCode,respMsg,responseTime,orderNo,bgwOrderNo,channel,resultCode,resultMsg,finishTime",//放款结果查询
		"pushBillRes,respCode,respMsg,responseTime",//账单（还款计划）推送
		"querySignResultRes,respCode,respMsg,responseTime,signResult,loanId,agreementUrl",//借款协议签章结果查询
		"activeRepayPreRes,respCode,respMsg,responseTime,bgwOrderNo",//主动还款预下单
		"activeRepayConfirmRes,respCode,respMsg,responseTime",//主动还款正式下单
		"cutRepayConfirmRes,respCode,respMsg,responseTime",//代扣还款
		"agreementNoticeRes,respCode,respMsg,responseTime,loanId,serviceFeeConfirmUrl,debtTransConfirmUrl,debtTransNoticesUrl,borrowerName,borrowerIdCard,debtTransferInfo,debtTransfers",//协议下载地址查询
		"activeRepaySmsCodeRepeatRes,respCode,respMsg,responseTime,bgwOrderNo",//代扣还款
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
	 * 解析7贷存管请求报文：DES解密，获得明文，并转换成对应bean
	 * 
	 * @param ciphertext
	 *            密文（转换后为json格式）
	 * @return 对应报文bean
	 */
	public static BaseReqModel parseMsg(String ciphertext) {
		BaseReqModel model = null;
		try {
			// DES解密，获得明文
			String decryptData = new DESUtil(depLoan7InDESKey).decryptStr(ciphertext);
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
			log.error("parse 7dai request message error! cause by : ", e);
		}

		return model;
	}

	/**
	 * 组装响应报文（响应给7贷存管）：组装hash，转换成json格式，并进行DES解密
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
			ciphertext = new DESUtil(depLoan7InDESKey).encryptStr(message);
			log.info("============报文转换密文：【" + ciphertext + "】============");
		} catch (Exception e) {
			log.error("package gateway response message error! cause by : ", e);
		}
		return ciphertext;
	}

	/**
	 * 组装响应报文（响应给7贷存管）：组装hash，转换成json格式，并进行DES解密
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
			ciphertext = new DESUtil(depLoan7InDESKey).encryptStr(message);
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
	public static <T> String depLoan7PackageMsgHash(T t, String transCode) {
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
			string = URLEncoder.encode(new DESUtil(depLoan7InDESKey).encryptStr(message), "utf-8");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return string;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {

		/*//7贷存管登录串
		

		//回款通知明文hash
		String hash = "token=583c1484e8e1f8acff1b732dfb7e2ab3&transCode=receiveMoneyNotice&requestTime=2016-02-24 00:08:54&data=[{\"cardNo\":\"330124198803300023\",\"bankNo\":\"6228480329144156174\",\"orderNo\":\"151125000008334\",\"status\":\"0\",\"successTime\":\"2016-02-24 00:01:08\",\"name\":\"周涵\",\"amountLx\":\"493.15\",\"amountBj\":\"20000.00\",\"productId\":\"028\"},{\"cardNo\":\"410901199004130826\",\"bankNo\":\"6225880168532076\",\"orderNo\":\"151125000008301\",\"status\":\"0\",\"successTime\":\"2016-02-24 00:06:09\",\"name\":\"刘佳\",\"amountLx\":\"1232.88\",\"amountBj\":\"50000.00\",\"productId\":\"028\"}]";
		System.out.println(MD5Util.encryptMD5(hash));
		
		//回款报文
		String message = "{\"requestTime\":\"2016-02-24 00:08:54\",\"hash\":\"a7da627c700780483f991d91ee310207\",\"token\":\"583c1484e8e1f8acff1b732dfb7e2ab3\",\"data\":\"[{\"cardNo\":\"330124198803300023\",\"bankNo\":\"6228480329144156174\",\"orderNo\":\"151125000008334\",\"status\":\"0\",\"successTime\":\"2016-02-24 00:01:08\",\"name\":\"周涵\",\"amountLx\":\"493.15\",\"amountBj\":\"20000.00\",\"productId\":\"028\"},{\"cardNo\":\"410901199004130826\",\"bankNo\":\"6225880168532076\",\"orderNo\":\"151125000008301\",\"status\":\"0\",\"successTime\":\"2016-02-24 00:06:09\",\"name\":\"刘佳\",\"amountLx\":\"1232.88\",\"amountBj\":\"50000.00\",\"productId\":\"028\"}]\",\"transCode\":\"receiveMoneyNotice\"}";
		System.out.println(URLEncoder.encode(new DESUtil(depLoan7InDESKey).encryptStr(message), "utf-8"));
		
		//登录
		String loginMessage = "{transCode:\"login\",requestTime:\"2017-12-12 21:11:11\",clientKey:\"channeldafykey1230\",clientSecret:\"dafy7987!&Ke6!3\",hash:\"840479a661701ffcdb9edf1f9e490e48\"}";
		System.out.println("登录:"+URLEncoder.encode(new DESUtil(depLoan7InDESKey).encryptStr(loginMessage), "utf-8"));
		
		
		//每日可借额度查询回款
		String dailyAvailableAmountLimitMessage = "{\"requestTime\":\"2016-11-25 00:08:54\",\"hash\":\"a7da627c700780483f991d91ee310207\",\"token\":\"583c1484e8e1f8acff1b732dfb7e2ab3\",\"queryDate\":\"2016-11-25\",\"transCode\":\"queryDailyAmount\"}";
		System.out.println("每日可借额度查询回款:"+URLEncoder.encode(new DESUtil(depLoan7InDESKey).encryptStr(dailyAvailableAmountLimitMessage), "utf-8"));
		
		
		//放款
		String applyLoanMessage = "{\"requestTime\":\"2017-02-21 00:08:54\",\"hash\":\"a7da627c700780483f991d91ee310207\",\"token\":\"583c1484e8e1f8acff1b732dfb7e2ab3\",\"userId\":\"1081611170013678\",\"name\":\"龙猫\",\"mobile\":\"15868157902\",\"idCard\":\"520203199012161817\",\"bankCard\":\"6228480322397174614\",\"bankCode\":\"ABC\",\"orderNo\":\"12345611002\",\"applyTime\":\"2017-02-21 13:01:08\",\"businessType\":\"现金循环贷\",\"loanId\":\"0001011002\",\"loanAmount\":300000,\"loanFee\":5940,\"loanTerm\":3,\"loanRate\":12,\"subjectName\":\"云贷借款\",\"purpose\":\"消费\",\"creditAmount\":6000000,\"loanedAmount\":0,\"creditLevel\":\"极好\",\"creditScore\":654,\"loanTimes\":2,\"breakTimes\":0,\"breakMaxDays\":30,\"transCode\":\"applyLoan\"}";
		System.out.println("放款:"+URLEncoder.encode(new DESUtil(depLoan7InDESKey).encryptStr(applyLoanMessage), "utf-8"));
		
		
		//放款结果查询
		String queryLoanResultMessage = "{\"requestTime\":\"2016-11-25 00:08:54\",\"hash\":\"a7da627c700780483f991d91ee310207\",\"token\":\"583c1484e8e1f8acff1b732dfb7e2ab3\",\"orderNo\":\"P2P0_15a82b2947f000800125ac89cf0\",\"applyDate\":\"2017-02-28\",\"transCode\":\"queryLoanResult\"}";
		System.out.println("放款结果查询:"+URLEncoder.encode(new DESUtil(depLoan7InDESKey).encryptStr(queryLoanResultMessage), "utf-8"));
		
		
	    //账单（还款计划推送）
	    String pushBillMessage = "{\"requestTime\":\"2017-05-19 16:54:41\",\"hash\":\"6ad4c3584040ad6945b51cf600a7f6c5\",\"token\":\"c9c61005-83e2-4502-b37e-363956c32056\",\"userId\":\"4000316\",\"loanId\":\"40000001537\",\"agreementNo\":\"p2p_bgw-jkxy_40000001537\",\"agreementUrl\":\"http://file.dafy.com.cn/file/20170519/3ce370bc624d4037964e281a81b51d50.pdf\",\"repayments\":\"[{\"repayId\":\"40000004494\",\"status\":\"INIT\",\"repayDate\":\"2017-08-16\",\"repaySerial\":\"1\",\"total\":11020,\"principal\":10000,\"interest\":1020,\"principalOverdue\":0,\"interestOverdue\":0,\"reservedField1\":\"\"}]\",\"transCode\":\"pushBill\"}";
	    System.out.println("账单（还款计划推送）:"+URLEncoder.encode(new DESUtil(depLoan7InDESKey).encryptStr(pushBillMessage), "utf-8"));
	    
		
		
		//借款协议签章结果查询
		String querySignResultMessage = "{\"requestTime\":\"2016-11-25 00:08:54\",\"hash\":\"a7da627c700780483f991d91ee310207\",\"token\":\"583c1484e8e1f8acff1b732dfb7e2ab3\",\"agreementNo\":\"1022001\",\"transCode\":\"querySignResult\"}";
		System.out.println("借款协议签章结果查询:"+URLEncoder.encode(new DESUtil(depLoan7InDESKey).encryptStr(querySignResultMessage), "utf-8"));
		
		

		
		//主动还款预下单
	    String activeRepayPreMessage = "{\"requestTime\":\"2017-05-12 00:08:54\",\"hash\":\"a7da627c700780483f991d91ee310207\",\"token\":\"583c1484e8e1f8acff1b732dfb7e2ab3\",\"orderNo\":\"YUN_ACTIVEREPAY_ORDERNO_20170511001\",\"userId\":\"1081604290019550\",\"name\":\"翁怡辰\",\"idCard\":\"330124199312030326\",\"mobile\":\"13588733651\",\"bankCard\":\"622909357795279519\",\"bankCode\":\"CIB\",\"loanId\":\"YUNLOAN_LOANID_20170504004\",\"totalAmount\":770,\"repayments\":\"[{\"repayId\":\"compenstate003\",\"status\":\"REPAIED\",\"repayType\":\"NORMAL\",\"repaySerial\":\"2\",\"total\":770,\"principal\":0,\"interest\":720,\"principalOverdue\":0,\"interestOverdue\":50,\"reservedField1\":\"syl\"}]\",\"transCode\":\"activeRepayPre\"}";
	    System.out.println("主动还款预下单:"+URLEncoder.encode(new DESUtil(depLoan7InDESKey).encryptStr(activeRepayPreMessage), "utf-8"));
	    
	    
	    
		
		//主动还款确认下单
	    String activeRepayConfirmMessage = "{\"requestTime\":\"2016-03-22 00:08:54\",\"hash\":\"a7da627c700780483f991d91ee310207\",\"token\":\"583c1484e8e1f8acff1b732dfb7e2ab3\",\"bgwOrderNo\":\"BDK20170512113754371059985103\",\"smsCode\":\"6544\",\"transCode\":\"activeRepayConfirm\"}";
	    System.out.println("主动还款确认下单:"+URLEncoder.encode(new DESUtil(depLoan7InDESKey).encryptStr(activeRepayConfirmMessage), "utf-8"));
	    
	    
	    
		
		//补账完成通知
		String fillFinishMessage = "{\"requestTime\":\"2016-05-20 10:08:54\",\"hash\":\"a7da627c700780483f991d91ee310207\",\"token\":\"583c1484e8e1f8acff1b732dfb7e2ab3\",\"orderNo\":\"YWF20170520102156195078578496\",\"payOrderNo\":\"payNo00001\",\"transCode\":\"fillFinish\",\"applyTime\":\"2017-05-20\",\"finishTime\":\"2017-05-20\",\"fillType\":\"INTEREST\",\"amount\":\"1100\"}";
		System.out.println("补账完成通知:"+URLEncoder.encode(new DESUtil(depLoan7InDESKey).encryptStr(fillFinishMessage), "utf-8"));
	
	    //代偿通知
	    String lateRepayMessage = "{\"requestTime\":\"2017-06-04 00:08:54\",\"hash\":\"a7da627c700780483f991d91ee310207\",\"token\":\"583c1484e8e1f8acff1b732dfb7e2ab3\",\"orderNo\":\"YUN_LATEREPAY_ORDERNO_20170512001\",\"payOrderNo\":\"201705110110000856171833_02\",\"transCode\":\"lateRepay\",\"applyTime\":\"2017-05-12 19:07:24\",\"finishTime\":\"2017-05-12 19:07:24\",\"totalAmount\":\"1059\"," +
	            "\"repayments\":\"[{\"userId\":\"1081604290019550\",\"loanId\":\"YUNLOAN_LOANID_20170505001\",\"repayId\":\"LONG_REPAYID_20170512001\",\"repayType\":\"NORMAL\",\"repaySerial\":\"1\",\"total\":\"1059\",\"principal\":\"1000\",\"interest\":\"59\",\"principalOverdue\":\"0\"}]\"}";
	    System.out.println("代偿通知:"+URLEncoder.encode(new DESUtil(depLoan7InDESKey).encryptStr(lateRepayMessage), "utf-8"));
	    
	    
		
		//还款结果查询
		String queryRepayResultMessage = "{\"requestTime\":\"2016-11-25 00:08:54\",\"hash\":\"a7da627c700780483f991d91ee310207\",\"token\":\"583c1484e8e1f8acff1b732dfb7e2ab3\",\"orderNo\":\"P2P0_15a8e363668009900125ac3ca4a\",\"transCode\":\"queryRepayResult\",\"applyDate\":\"2017-03-02\"}";
		System.out.println("还款结果查询:"+URLEncoder.encode(new DESUtil(depLoan7InDESKey).encryptStr(queryRepayResultMessage), "utf-8"));
	
		
		//代扣还款
		String cutRepayConfirmMessage = "{\"requestTime\":\"2016-05-18 00:08:54\",\"hash\":\"a7da627c700780483f991d91ee310207\",\"token\":\"583c1484e8e1f8acff1b732dfb7e2ab3\",\"orderNo\":\"dk00001\",\"transCode\":\"cutRepayConfirm\",\"overdueFlag\":\"NORMAL\",\"userId\":\"1000566\",\"name\":\"朱媛\",\"idCard\":\"330781190000001128\",\"mobile\":\"15158191349\"," +
				"\"bankCard\":\"6217710800006741\",\"bankCode\":\"CITIC\",\"totalAmount\":\"3480\",\"loans\":\"[{\"loanId\":\"1022zy38\",\"singleTotalAmount\":\"3480\"," +
				"\"repayments\":\"[{\"repayId\":\"zy1214\",\"status\":\"INIT\",\"repayType\":\"NORMAL\",\"repaySerial\":\"1\",\"total\":\"3480\",\"principal\":\"0\",\"interest\":\"3480\",\"principalOverdue\":\"0\"}]\"}]\"}";
		System.out.println("代扣还款:"+URLEncoder.encode(new DESUtil(depLoan7InDESKey).encryptStr(cutRepayConfirmMessage), "utf-8"));
	
		//协议下载地址查询
		String agreementNoticeMessage = "{\"requestTime\":\"2016-12-15 15:52:54\",\"hash\":\"a7da627c700780483f991d91ee310207\",\"token\":\"583c1484e8e1f8acff1b732dfb7e2ab3\",\"loanId\":\"1122\",\"transCode\":\"agreementNotice\"}";
		System.out.println("协议下载地址查询:"+URLEncoder.encode(new DESUtil(depLoan7InDESKey).encryptStr(agreementNoticeMessage), "utf-8"));
		*/
		
		
		//每日可借额度查询回款
		String dailyAvailableAmountLimitMessage = "{\"requestTime\":\"2017-12-12 00:08:54\",\"hash\":\"a7da627c700780483f991d91ee310207\",\"token\":\"583c1484e8e1f8acff1b732dfb7e2ab3\",\"queryDate\":\"2016-11-25\",\"transCode\":\"queryDailyAmount\"}";
		System.out.println("每日可借额度查询回款:"+URLEncoder.encode(new DESUtil(depLoan7InDESKey).encryptStr(dailyAvailableAmountLimitMessage), "utf-8"));
		

		
	}
	
}
