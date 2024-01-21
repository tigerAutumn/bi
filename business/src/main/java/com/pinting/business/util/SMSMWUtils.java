package com.pinting.business.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.core.util.StringUtil;


/**
 * 梦网短信接口
 * 用户名:J02791,密码:581026
 * 为了您的账户安全,请及时修改您的密码,如有任何问题,请随时联系您的客服人员．
 * 密码接口修改地址:http://61.145.229.29:9003/MWGate/wmgw.asmx/MongateCsSpSendSmsNew

 * 技术支持QQ:25660050 1015770875

 * @author linkin
 *
 */
public class SMSMWUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(SMSMWUtils.class);
	
	private static CloseableHttpClient client = HttpClients.createDefault();
	
	
	/**
	 * 发送短信
	 * @param mobile 目标手机号
	 * @param message 手机信息内容
	 * @return
	 */
	public static boolean sendSMS(String mobile, String message) {
		
		try {
			HttpGet get = new HttpGet(getURL(mobile, message));
			CloseableHttpResponse reponse = client.execute(get);
			HttpEntity entity = reponse.getEntity();
			//String resultMsg = EntityUtils.toString(entity).split("");
			return XMLToTextCheck(EntityUtils.toString(entity));
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		
		return false;
	}
	/**
	 * 提取xml里的值判断是否发送成功
	 * @param XMLStr
	 * @return
	 */
	public static  boolean XMLToTextCheck(String XMLStr){

		boolean isSuccess = false;
		if(XMLStr!=null&&(!"".equals(XMLStr.trim()))&&(!"error".equals(XMLStr.trim())))
		{
			try {
				Document document = DocumentHelper.parseText(XMLStr);//将XML串进行解析
				String result = document.getRootElement().getText();//取得根结点
				if(result.length()>10&&result.length()<25)
				{
					isSuccess=true;
				}
			}
			catch (DocumentException e) {
			e.printStackTrace();
			}
		}
		
		return isSuccess;
		}
	private static String getURL(String phone, String content) throws UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://61.145.229.29:9003/MWGate/wmgw.asmx/MongateCsSpSendSmsNew?");
		buffer.append("userId=");
		buffer.append("J02791");
		buffer.append("&password=");
		buffer.append("581026");
		buffer.append("&pszMobis=");
		buffer.append(phone);
		buffer.append("&pszMsg=");
		buffer.append(URLEncoder.encode(content, "UTF-8"));
		buffer.append("&iMobiCount=");
		buffer.append("1");
		buffer.append("&pszSubPort=");
		buffer.append("*");
		
		return buffer.toString();
	}
	/**
	 * 
	 * @param 目标手机号
	 * @return  验证码
	 * @throws Exception
	 */
	public static String sendToMobiles(String mobile) {
		//生成验证码
		Random random = new Random();
		StringBuffer sRand = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand.append(rand);
		}
		String content = "您的验证码为" + sRand.toString() +"，有效期120秒，客服热线400-806-1230";
		if(sendSMS(mobile, content)){
			return sRand.toString();
		}else{
			return Constants.SEND_CODE_ERROR;
		}
	}
	
	
	
	
	public static void main(String[] args) {
		//System.out.print(sendSMS("15355814371","您的验证码为0865，有效期120秒，客服热线400-806-1230"))	;
		
	}
}