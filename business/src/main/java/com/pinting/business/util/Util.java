package com.pinting.business.util;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.ThirdSysChannelEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.encrypt.DESUtil;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Util {

	/**
	 * 代付 订单号（20位）生成（需传入某唯一的序号）
	 * @param keyId 订单唯一序号传入（一般可为一张表的id编号）
	 * @return
	 */
	public static String generateOrderNo4Pay19(Integer keyId) {
		//String startVal = DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss");
		String startVal = Long.toHexString(Long.parseLong(DateUtil.formatDateTime(new Date(), "yyMMddHHmmssSSS")));
		String key = String.format("%08d", keyId);
		return fixStringByZero(startVal, 12) + key;
	}
	
	/**
	 * 代付 订单号（20位）生成
	 * @param userId 用户编号
	 * @return
	 */
	public static synchronized String generateUserOrderNo4Pay19(Integer userId) {
		//预估到251231235959999，转成16进制后12位
		String dateStr = Long.toHexString(Long.parseLong(DateUtil.formatDateTime(new Date(), "yyMMddHHmmssSSS")));
		String userIdStr = Integer.toHexString(userId);//6位
		String randomStr = String.valueOf(new Random().nextInt(100));//2位
		
		return fixStringByZero(dateStr, 12) + fixStringByZero(userIdStr, 6) + fixStringByZero(randomStr, 2);
	}

	/**
	 * 宝付 查询卡bin、没有userId的 流水号（20位）生成
	 * @return
	 */
	public static String generateUserTransNo4BaoFoo() {
		//预估到251231235959999，转成16进制后12位
		String dateStr = Long.toHexString(Long.parseLong(DateUtil.formatDateTime(new Date(), "yyMMddHHmmssSSS")));
		String randomStr = String.valueOf(new Random().nextInt(100000000));//8位

		return fixStringByZero(dateStr, 12) + fixStringByZero(randomStr, 8);
	}
	
	//根据位数要求，给字符串前面补零。如果已经足位或者超出，则返回原字符串
	private static String fixStringByZero(String str, int len){
		int strLen = str.length();
		for (int i = 0; i < len - strLen; i++) {
			str = "0" + str;
		}
		return str;
	}
	
	/**
	 * 生成流水号，只是目前暂用 Long时间+userId+9位随机
	 * 
	 * @param userId
	 * @return
	 */
	public static String generateJnlNo(Integer userId) {
		String noStr = String.valueOf(new Date().getTime());
		int randomNum = (int) (Math.random() * 100000000);
		DecimalFormat decimalFormat = new DecimalFormat("000000000");
		noStr += userId + decimalFormat.format(randomNum);
		return noStr;
	}

	/**
	 * 生成指定长度的数字字符串，数字位数不足前面补零
	 * @param length	需要转换成的长度
	 * @return
	 */
	public static String generateAssignLengthNo(int length) {

		int randomNum = (int) (Math.random() * Math.pow(10, length));
		String tmpString = String.valueOf(randomNum);
		for (int i = tmpString.length(); i < length; i++) {
			tmpString = "0" + tmpString;
		}
		return tmpString;
	}

	/**
	 * 生成宝付订单号
	 * @param length
	 * @return
     */
	public static String generateOrderNo4BaoFoo(int length){

		String dateStr = Long.toHexString(Long.parseLong(DateUtil.formatDateTime(new Date(), "yyMMddHHmmssSSS")));
		String randomStr = generateAssignLengthNo(length);

		return fixStringByZero(dateStr, 12) + randomStr;

	}

	/**
	 * 生成 达飞订单号（用于发给达飞）
	 * @param subAccountId 子账户编号（唯一）
	 * @return
	 */
	public static String generateOrderNo4Dafy(String subAccountId){
		int length = 9;
		for (int i = subAccountId.length(); i < length; i++) {
			subAccountId = "0" + subAccountId;
		}
		String orderNo = DateUtil.formatDateTime(new Date(), "yyMMdd") + subAccountId;
		return orderNo;
	}
	
	/**
	 * 生成 提现交易唯一标识码（用于发给达飞）
	 * @param withdrawId 提现表账户编号
	 */
	public static String generateApplyNo(String withdrawId, String type){
		int length = 9;
		for (int i = withdrawId.length(); i < length; i++) {
			withdrawId = "0" + withdrawId;
		}
		String applyNo =  type + DateUtil.formatDateTime(new Date(), "yyMMdd") + withdrawId;
		return applyNo;
	}
	
	
	/**
	 * 生成 用户邀请码
	 * 生成规则：用户编号 + 1137
	 * @param userId
	 * @return
	 */
	public static String generateInvitationCode(Integer userId){
		Integer invitationCode = userId + 1137;
		return String.valueOf(invitationCode);
	}
	/**
	 * 根据邀请码获得用户编号
	 * 获得规则：邀请码 - 1137
	 * @param invitationCode
	 * @return 成功则返回用户编号，否则返回-1
	 */
	public static Integer getUserIdByInvitationCode(String invitationCode){
		Integer userId = -1;
		try {
			userId = Integer.valueOf(invitationCode) - 1137;
			if(userId <= 0){
				return -1;
			}
		} catch (NumberFormatException e) {
			return -1;
		}
		return userId;
	}
	
	/**
	 * 用户渠道码加密
	 * @param userChannel
	 * @return
	 */
	public static String encryptUserChannelCode(String userChannel){
		try {
			String inputData = userChannel;
			DESUtil des = new DESUtil("CHANNEL4USER");
			inputData = des.encryptStr(inputData);
			return URLEncoder.encode(inputData,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			throw new PTMessageException(PTMessageEnum.USER_CHANNEL_ERROR);
		}
		
	}
	
	/**
	 * 解密获得用户渠道码
	 * @param encryptCode
	 * @return
	 */
	public static String decryptUserChannelCode(String encryptCode){
		try {
			DESUtil des = new DESUtil("CHANNEL4USER");
			String outputStr = des.decryptStr(encryptCode);
			return outputStr;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PTMessageException(PTMessageEnum.USER_CHANNEL_ERROR);
		}
	}
	
	/**
	 * RETURN_2_BALANCE  转换成   Bonus2Balance
	 * @param transCode
	 * @return
	 */
	public static String changeTransCode(String transCode){
		String[] ary = transCode.split("_");
		String newCode = "";
		for(int i=0;i<ary.length;i++){
			ary[i]=ary[i].toLowerCase();
			char[] chars=new char[1];
			chars[0]=ary[i].charAt(0);
			String temp=new String(chars);
			
			newCode = newCode + ary[i].replaceFirst(temp,temp.toUpperCase());
		}
		return newCode;
	}
	/**
	 * 根据三方渠道号获取系统回款子账户码
	 * @param thirdSysChannelEnum
	 * @return
	 */
	public static String getSysReturnSubActCode(ThirdSysChannelEnum thirdSysChannelEnum){
		String actCode = "";
		switch (thirdSysChannelEnum) {
		case YUN_DAI:
			actCode = Constants.SYS_ACCOUNT_RETURN_YUN;
			break;
		case SEVEN_DAI:
			actCode = Constants.SYS_ACCOUNT_RETURN_7;
			break;
		}
		return actCode;
	}

	/**
	 * 获得三方响应编码
	 * @param res
	 * @return
	 */
	public static String getThirdReturnCode(ResMsg res){
		String thirdReturnCode = "";
		if (res.getExtendMap() != null) {
			Object code = res.getExtendMap().get("thirdReturnCode");
			thirdReturnCode = code != null ? String.valueOf(code) : "";
		}else {
			thirdReturnCode = res.getResCode();
		}
		return thirdReturnCode;
	}
	/**
	 * 
	 * @Title: generateOrderNo 
	 * @Description: 生成用户订单号，用于发起19付（须小于50位）
	 * @param userId
	 * @return
	 * @throws
	 */
	public static String generateOrderNo(Integer userId) {
		String startVal = DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss");
		int randomNum = (int) (Math.random() * 100000000);
		DecimalFormat decimalFormat = new DecimalFormat("000000000");
		startVal += userId + decimalFormat.format(randomNum);
		return startVal;
	}
	/**
	 * 
	 * @Title: generateOrderNo 
	 * @Description: 生成钱报用户订单（20位）
	 * @param userId
	 * @return
	 * @throws
	 */
	public static String generateQianbaoOrderNo(Integer userId) {
		String dateStr = Long.toHexString(Long.parseLong(DateUtil.formatDateTime(new Date(), "yyMMddHHmmssSSS")));
		String userIdStr = Integer.toHexString(userId);//6位
		String randomStr = String.valueOf(new Random().nextInt(100));//2位
		return fixStringByZero(dateStr, 12) + fixStringByZero(userIdStr, 6) + fixStringByZero(randomStr, 2);
	}
	/**
	 * 生成 系统 订单号，用于系统向19付发起（须小于50位）
	 * @param type 类型（如系统转账：TS）
	 * @return
	 */
	public static String generateSysOrderNo(String type) {
		String startVal = type + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS");
		int randomNum = (int) (Math.random() * 100000000);
		DecimalFormat decimalFormat = new DecimalFormat("000000000");
		startVal += decimalFormat.format(randomNum);
		return startVal;
	}


	/**
	 * 生成 用户支付绑定id，用于宝付成功绑卡之后返回给蜂鸟（须小于50位）
	 * @param type 类型（如系统转账：TS）
	 * @return
	 */
	public static String generateBindIdNo(String type,Integer userId) {

		String bgwBindId=type+DateUtil.formatDateTime(new Date(),"yyyyMMddHHmmss")+String.valueOf(userId);

		return bgwBindId;
	}
	
	private static String toHex(long time){    
		String str = Integer.toHexString((int)time);
		int len = str.length();
		if(len < 8) {
			for (int i = 0; i < 8-len; i++) {
				str = "0" + str;
			}
		}else {
			str = str.substring(len-8);
		}
        return str;
	}
	
	/**
	 * 生成红包申请单号
	 * @return
	 */
	public static String getApplyNo() {
    	String str = "A"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    	return str;
    }
    
	/**
	 * 生产红包发放批次号
	 * @return
	 */
    public static String getSerialNo() {
    	String str = "S"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    	return str;
    }
    
    /**
     * 过滤掉非UTF-8字符方法
     * 
     * @param text
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String filterOffUtf8Mb4(String text) throws UnsupportedEncodingException {  
        byte[] bytes = text.getBytes("UTF-8");  
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);  
        int i = 0;  
        while (i < bytes.length) {  
            short b = bytes[i];  
            if (b > 0) {  
                buffer.put(bytes[i++]);  
                continue;  
            }  
            b += 256;  
            if ((b ^ 0xC0) >> 4 == 0) {  
                buffer.put(bytes, i, 2);  
                i += 2;  
            }  
            else if ((b ^ 0xE0) >> 4 == 0) {  
                buffer.put(bytes, i, 3);  
                i += 3;  
            }  
            else if ((b ^ 0xF0) >> 4 == 0) {  
                i += 4;  
            }else if (((b >> 2) ^ 0x3E) == 0) { 
                i += 5; 
            } else if (((b >> 1) ^ 0x7E) == 0) { 
                i += 6; 
            } 
            //添加处理如b的指为-48等情况出现的死循环 
            else 
            { 
                buffer.put(bytes[i++]); 
                continue; 
            } 
        }  
        buffer.flip();  
        return new String(buffer.array(), "utf-8"); 
    }
    
    /**
     * 短信订单号 (19位)
     * @return
        */
    public static String generateSmsOrderNo() {
       String startVal = DateUtil.formatDateTime(new Date(), "yyMMddHHmmssSSS");//15位
       String random = generateAssignLengthNo(4);
       return fixStringByZero(startVal, 15) + random;
    }

	/**
	 * 元转分
	 * @param yuan
	 * @return 单位：分；类型LONG
     */
	public static Long yuan2Fen(Double yuan) {
		BigDecimal amount = MoneyUtil.multiply(yuan == null ? 0:yuan, 100);
		Long fen = amount.longValue();
		return fen;
	}

	/**
	 * 分转元
	 * @param fen
	 * @return 单位：元；类型DOUBLE
     */
	public static Double fen2Yuan(Long fen) {
		BigDecimal amount = MoneyUtil.divide(fen, 100);
		Double yuan = amount.doubleValue();
		return yuan;
	}
    
    
	public static void main(String[] args) {
//		System.out.println(encryptUserChannelCode("tjsd"));
//		try {
//			System.out.println(decryptUserChannelCode(URLDecoder.decode("sZl%2ByYuOKhenTZBZXiVQrQ%3D%3D", "UTF-8")));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		String str=Constants.SYS_USER_BONUS_2_BALANCE;
//		String[] ary = str.split("_");
//		String newCode = "";
//		for(int i=0;i<ary.length;i++){
//			ary[i]=ary[i].toLowerCase();
//			char[] chars=new char[1];
//			chars[0]=ary[i].charAt(0);
//			String temp=new String(chars);
//			
//			newCode = newCode + ary[i].replaceFirst(temp,temp.toUpperCase());
//		}
//		System.out.println(Util.generateUserOrderNo4Pay19(1));
		System.out.println(yuan2Fen(2.3399999999)+"|"+fen2Yuan(243523L));
	}
	

}
