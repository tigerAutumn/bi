package com.pinting.gateway.dafy.out.util;

import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.gateway.dafy.out.model.BaseReqModel;
import com.pinting.gateway.dafy.out.model.BaseResModel;
import com.pinting.gateway.util.BeanUtils;
import com.pinting.gateway.util.Constants;
import com.pinting.gateway.util.DESUtil;
import com.pinting.gateway.util.JsonLibUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 报文处理类（达飞作为服务端）
 * 
 * @Project:gateway
 * @Title:DafyOutMsgUtil.java
 * @author dingpf
 * @date 2015-2-11 下午5:06:32
 * @Copyright:2015 BiGangWan.com Inc. All rights reserved.
 */
public class DafyOutMsgUtil {
	private static String dafyQueryInfoDESKey = "9id@eFY#.B86U,H!";
	private static String dafyOutDESKey = "CCMu*X.,Yd5Fffy.cAm1,.*1";
	public static String sysCustomerId = "1230735";
	//交易码数据用来作为组装报文的依据，key为交易码，value为需要组装的参数的key
	private static Map<String, List<String>> transUnitMap = new HashMap<String, List<String>>();
	private static String[] transUnits = new String[]{
		"login,transCode,clientKey,clientSecret,requestTime",
		"registerCustomer,token,transCode,requestTime,name,mobile,cardNo",
		"bindBankcard,token,transCode,requestTime,customerId,bankName,openAccountProvince,subbranchName,bankcardNo,openAccountCity",
		"buyProduct,token,transCode,requestTime,customerId,productCode,amount,orderId,urlJsp,transferType,bankCode",
		"checkAccount,token,transCode,requestTime,queryDate",
		"getLoanRelationUrl,token,transCode,requestTime",
		"basicInformation,token,transCode,requestTime,status,customerId,name,mobile,cardNo,bankName,openAccountProvince,subbranchName,bankcardNo,openAccountCity",
		"customerWithdraw,token,transCode,requestTime,applyTime,applyNo,customerId,customerName,bankcard,amount,transType",
		"queryWXAccountDetail,token,transCode,requestTime,startDate,endDate,transType,pageIndex,pageNum,nStatu",
		"sysWithdraw,token,transCode,requestTime,applyNo,amount,transType",
		"sysBatchBuyProduct,token,transCode,requestTime,clientKey,customerId,payPlatform,merchantId,payOrderNo,payReqTime,payFinshTime,amount,products",
		"queryLoanRelationship,token,transCode,requestTime,clientKey,customerId,orderNo,pageIndex,pageNum",
		"queryRepayJnl,token,transCode,requestTime,clientKey,customerId,borrowNo",
		"token,transCode,requestTime,clientKey,hash,orderNo,bgwOrderNo,loanId,channel,resultCode,resultMsg,finishTim",
		"queryRepayJnl,token,transCode,requestTime,clientKey,customerId,borrowNo",
		//云贷自主放款相关
		"dafyLoanLogin,transCode,clientKey,clientSecret,requestTime,requestSeq",
		"queryBill,token,transCode,requestTime,requestSeq,clientKey,userId,loanId",
		"waitFill,token,transCode,requestTime,requestSeq,clientKey,orderNo,fillDate,fillType,amount,fileUrl",
		"revenueSettle,token,transCode,requestTime,requestSeq,clientKey,orderNo,applyTime,finishTime,settleType,amount,fileUrl",
		"loanResultNotice,token,transCode,requestTime,requestSeq,clientKey,orderNo,bgwOrderNo,agreementNo,loanServiceRate,loanId,channel,resultCode,resultMsg,finishTime,lenders",
		"signResultNotice,token,transCode,requestTime,requestSeq,clientKey,agreementNo,loanId,signResult,agreementUrl",
		
		"repayResultNotice,token,transCode,requestTime,requestSeq,clientKey,orderNo,bgwOrderNo,channel,resultCode,resultMsg,finishTime",
		"agreementNotice,token,transCode,requestTime,requestSeq,clientKey,orderNo,agreements",

	};
	
	static{
		//包装交易码数据
		for (String units : transUnits) {
			String[] unitArr = units.split(",");
			ArrayList<String> unitList = new ArrayList<String>();
			for (int i = 1; i < unitArr.length; i++) {
				unitList.add(unitArr[i]);
			}
			transUnitMap.put(unitArr[0], unitList);
		}
	}
	
	static{
		if(Constants.GATEWAY_SERVER_MODE_PROD.equals(GlobEnvUtil.get("server.mode"))){
			dafyOutDESKey = GlobEnvUtil.get("dafy.des.key");
			sysCustomerId = GlobEnvUtil.get("dafy.sys.customerId");
		}
	}
	
	private static Logger log = LoggerFactory.getLogger(DafyOutMsgUtil.class);

	/**
	 * 解析达飞响应报文：DES解密，获得明文，并转换成对应bean
	 * 
	 * @param ciphertext
	 *            密文（转换后为json格式）
	 * @param transCode
	 *            交易码
	 * @return 对应报文bean
	 */
	public static BaseResModel parseMsg(String ciphertext, String transCode) {
		BaseResModel model = null;
		try {
			if(!DafyOutConstant.GET_LOAN_RELATION_NEW.equals(transCode)){
				log.info("============达飞返回的报文密文：【" + ciphertext + "】============");
			}else{
				log.info("============达飞返回的报文密文（债权关系前200字符）：【" + StringUtil.left(ciphertext, 200) + "】============");
			}
			// DES解密，获得明文
			String decryptData = new DESUtil(dafyOutDESKey).decryptStr(ciphertext);
			if(!DafyOutConstant.GET_LOAN_RELATION_NEW.equals(transCode)){
				log.info("============解密获得明文：【" + decryptData + "】============");
			}else{
				log.info("============解密获得明文（债权关系前200字符）：【" + StringUtil.left(decryptData, 200) + "】============");
			}

			// 获得 报文bean类型名称
			StringBuffer modelClassName = new StringBuffer(
					Constants.GATEWAY2DAFY_MESSAGE_MODEL_PRE)
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
						Constants.HESSIAN_MESSAGE_MODEL_PRE).append(
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
			log.error("parse dafy response message error! cause by:", e);
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
			//String hash = packageMsgHash(reqModel);
			String hash = packageMsgHash(reqModel, reqModel.getTransCode());
			log.info("============组装报文，hash字段明文：【" + hash + "】============");
			String encryptHash = MD5Util.encryptMD5(hash);
			log.info("============组装报文，hash字段密文：【" + encryptHash + "】============");
			reqModel.setHash(encryptHash);
			// bean 转换成 json
			String message = JsonLibUtil.bean2Json(reqModel);

			log.info("============发送报文明文：【" + message + "】加密dafyOutDESKey:"+dafyOutDESKey+"============");
			// 对json进行DES加密
			ciphertext = new DESUtil(dafyOutDESKey).encryptStr(message);
			log.info("============发送报文密文：【" + ciphertext + "】============");
		} catch (Exception e) {
			log.error("package gateway request message error! cause by:", e);
		}
		return ciphertext;
	}

	/**
	 * 根据交易码,组装md5加密前的报文
	 * @param t model类型
	 * @param transCode 交易码
	 * @return
	 */
	private static <T> String packageMsgHash(T t, String transCode) {
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
			if(obj instanceof List){
				obj = JSONArray.fromObject(obj);
			}
			obj = obj == null? "" : obj;//null的时候，改成“”
			hash.append(unit).append("=").append(obj).append("&");
		}
		//去除&并返回
		System.out.println("hash=" +hash.substring(0, hash.length() - 1));
		return hash.substring(0, hash.length() - 1);
	}
	
	public static String createDafyQueryKey() {
		return dafyQueryInfoDESKey;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String reqData = "{\"respCode\":\"someCode\",\"respMsg\":\"someMsg\",\"responseTime\":\"2015-01-16 11:11:11\"}";
		JSONObject jsonObject = JSONObject.fromObject(reqData);
		System.out.println(new DESUtil(dafyOutDESKey).encryptStr(reqData));
		
		String s = "5X42fdRD0fh9Dg+9snaGnECKlLhKiFc3EFy4x2XNxFgKsvbEjWj0AIuhyr9ilxVFAYe99z73x3McqSlxSRG8rCDXUnpt3KBn9KLiw8fSapa89K5YTxjcHE9T13nsftnY70E/lvCjaYkDzznqDieKJAv7X6Rc0k6/RUmMbtwutiNT3ITmUHK302LN/SOLXMGl7objN59eRNmuKpJtwSQp9dnIs2DF93Ym51138STo2RHxTbGbid8Zi6i0U1D6EOAG5/nnw5OLpCU=";
		System.out.println(new DESUtil(dafyOutDESKey).decryptStr(s));
	}
}
