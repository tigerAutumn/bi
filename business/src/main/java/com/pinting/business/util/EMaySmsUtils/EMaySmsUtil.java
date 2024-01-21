package com.pinting.business.util.EMaySmsUtils;

import com.pinting.business.model.BsSmsRecordJnl;
import com.pinting.business.util.DateUtil;
import com.pinting.business.util.Util;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 亿美短信：http://hprpt2.eucp.b2m.cn:8080/sdkproxy
 * 测试账号：0SDK-EBB-6699-RDRMR
 * 账号密码：301625
 * @author bianyatian
 * @2016-10-26 下午5:00:39
 */
public class EMaySmsUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(EMaySmsUtil.class);
	private static String cdkey = GlobEnvUtil.get("emay.cdkey").trim();
	private static String password = GlobEnvUtil.get("emay.password").trim();
	
	private static String cdkey_marketing = GlobEnvUtil.get("emay.marketing.cdkey").trim();
	private static String password_marketing = GlobEnvUtil.get("emay.marketing.password").trim();
	
	private static CloseableHttpClient client = HttpClients.createDefault();
	
	/**
	 * 短信发送
	 * @param mobile 手机号，以英文逗号隔开
	 * @param message 发送内容
	 * @param sendTime -为空时，是即时发送，否则为定时发送
	 * @return 序列号
	 */
	public static String sendSMS(String mobile, String message, String sendTime, String addserial) {
		String str = "";
		try {
			//判断手机号长度和是否为1开头
			if(StringUtil.isNotBlank(mobile) && mobile.length()>= 11 && mobile.startsWith("1")){
				HttpGet get = new HttpGet();
				String seqid = Util.generateSmsOrderNo();
				if(StringUtil.isBlank(sendTime)){
					//即时发送
					get = new HttpGet(getURL(mobile, message,seqid,addserial));
				}else{
					//定时发送
					get = new HttpGet(getURL4Time(mobile, message,seqid,sendTime));
				}
				
				RequestConfig requestConfig = RequestConfig.custom()
						.setConnectionRequestTimeout(10000).setSocketTimeout(10000)
						.setConnectTimeout(10000).build();// 设置请求和传输超时时间
				get.setConfig(requestConfig);
				CloseableHttpResponse reponse = client.execute(get);
				HttpEntity entity = reponse.getEntity();
				String returnStr = XMLToTextCheckSend(EntityUtils.toString(entity).trim());
				if("0".equals(returnStr)){
					str = seqid;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}
	
	/**
	 * 短信发送
	 * @param mobile 手机号，以英文逗号隔开
	 * @param message 发送内容
	 * @param sendTime -为空时，是即时发送，否则为定时发送
	 * @return 序列号
	 */
	public static String sendSMSMarketing(String mobile, String message) {
		String str = "";
		try {
			//判断手机号长度和是否为1开头
			if(StringUtil.isNotBlank(mobile) && mobile.length()>= 11 && mobile.startsWith("1")){
				HttpGet get = new HttpGet();
				String seqid = Util.generateSmsOrderNo();
				//即时发送
				get = new HttpGet(getURLMarketing(mobile, message,seqid));
				
				RequestConfig requestConfig = RequestConfig.custom()
						.setConnectionRequestTimeout(10000).setSocketTimeout(10000)
						.setConnectTimeout(10000).build();// 设置请求和传输超时时间
				get.setConfig(requestConfig);
				CloseableHttpResponse reponse = client.execute(get);
				HttpEntity entity = reponse.getEntity();
				String returnStr = XMLToTextCheckSend(EntityUtils.toString(entity).trim());
				if("0".equals(returnStr)){
					str = seqid;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}
	
	public static String queryBalance(){
		String str = "";
		try {
			HttpGet get =new HttpGet(queryBalanceURL());
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectionRequestTimeout(10000).setSocketTimeout(10000)
					.setConnectTimeout(10000).build();// 设置请求和传输超时时间
			get.setConfig(requestConfig);
			CloseableHttpResponse reponse = client.execute(get);
			HttpEntity entity = reponse.getEntity();
			str = XMLToTextCheckQueryBalance(EntityUtils.toString(entity).trim());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}
	
	private static String XMLToTextCheckQueryBalance(String XMLStr) {
		String isSuccess = "";
		if (XMLStr != null && (!"".equals(XMLStr.trim()))
				&& (!"error".equals(XMLStr.trim()))) {
			try {
				Document document = DocumentHelper.parseText(XMLStr);// 将XML串进行解析
				Element rootElm = document.getRootElement();
                Element memberElm = rootElm.element("message");
                isSuccess = memberElm.getText();
			} catch (DocumentException e) {
				LOG.error("短信发送失败，返回结果："+XMLStr,e);
				return isSuccess;
			}
		}

		return isSuccess;
	}

	/**
	 * 发送即时短信-url-拼接
	 * @param mobile
	 * @param message
	 * @param seqid
	 * @param sendTime
	 * @return
	 */
	private static String getURL4Time(String mobile, String message,
			String seqid, String sendTime) throws UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://hprpt2.eucp.b2m.cn:8080/sdkproxy/sendtimesms.action?");
		buffer.append("cdkey=");
		buffer.append(cdkey);
		buffer.append("&password=");
		buffer.append(password);
		buffer.append("&phone=");
		buffer.append(mobile);
		buffer.append("&message=");
		buffer.append(URLEncoder.encode(message, "UTF-8"));
		buffer.append("&seqid="); //长整型值企业内部必须保持唯一，获取状态报告使用(19位以内数字)
		buffer.append(seqid);
		buffer.append("&sendtime=");
		buffer.append(sendTime);
		return buffer.toString();
	}

	/**
	 * 发送即时短信-url-拼接
	 * @param mobile
	 * @param message
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String getURL(String mobile, String message, String seqid, String addserial)
			throws UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://hprpt2.eucp.b2m.cn:8080/sdkproxy/sendsms.action?");
		buffer.append("cdkey=");
		buffer.append(cdkey);
		buffer.append("&password=");
		buffer.append(password);
		buffer.append("&phone=");
		buffer.append(mobile);
		buffer.append("&message=");
		buffer.append(URLEncoder.encode(message, "UTF-8"));
		buffer.append("&seqid="); //长整型值企业内部必须保持唯一，获取状态报告使用(19位以内数字)
		buffer.append(seqid);
		buffer.append("&addserial=");
		buffer.append(StringUtils.isBlank(addserial)?"1":addserial);//扩展码，默认为1
		return buffer.toString();
	}
	
	
	/**
	 * 发送即时短信-url-拼接
	 * @param mobile
	 * @param message
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String getURLMarketing(String mobile, String message, String seqid)
			throws UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://hprpt2.eucp.b2m.cn:8080/sdkproxy/sendsms.action?");
		buffer.append("cdkey=");
		buffer.append(cdkey_marketing);
		buffer.append("&password=");
		buffer.append(password_marketing);
		buffer.append("&phone=");
		buffer.append(mobile);
		buffer.append("&message=");
		buffer.append(URLEncoder.encode(message, "UTF-8"));
		buffer.append("&seqid="); //长整型值企业内部必须保持唯一，获取状态报告使用(19位以内数字)
		buffer.append(seqid);
		
		return buffer.toString();
	}
	
	/**
	 * 查询余额
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String queryBalanceURL()throws UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://hprpt2.eucp.b2m.cn:8080/sdkproxy/querybalance.action?");
		buffer.append("cdkey=");
		buffer.append(cdkey);
		buffer.append("&password=");
		buffer.append(password);
		return buffer.toString();
	}
	
	
	/**
	 * 提取xml里的值判断是否发送成功
	 * 成功则返回XMLStr,否则为空
	 * @param XMLStr
	 * @return
	 */
	public static String XMLToTextCheckSend(String XMLStr) {
		String isSuccess = "";
		if (XMLStr != null && (!"".equals(XMLStr.trim()))
				&& (!"error".equals(XMLStr.trim()))) {
			try {
				Document document = DocumentHelper.parseText(XMLStr);// 将XML串进行解析
				Element rootElm = document.getRootElement();
                Element memberElm = rootElm.element("error");
                isSuccess = memberElm.getText();
			} catch (DocumentException e) {
				LOG.error("短信发送失败，返回结果："+XMLStr,e);
				return isSuccess;
			}
		}

		return isSuccess;
	}
	
	/**
	 * 获取状态报告 -url-拼接
	 * @return
	 */
	private static String getServiceCheckURL(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://hprpt2.eucp.b2m.cn:8080/sdkproxy/getreport.action?");
		buffer.append("cdkey=");
		buffer.append(cdkey);
		buffer.append("&password=");
		buffer.append(password);
		return buffer.toString();
	}
	
	/**
	 * 查询是否发送成功接口
	 * @return
	 */
	public static List<BsSmsRecordJnl> checkSmsSend() {
		LOG.info("=============【亿美短信】查询状态============");
		List<BsSmsRecordJnl> list = new ArrayList<BsSmsRecordJnl>();
		try {
			HttpGet get = new HttpGet(getServiceCheckURL());
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectionRequestTimeout(10000).setSocketTimeout(10000)
					.setConnectTimeout(10000).build();// 设置请求和传输超时时间
			get.setConfig(requestConfig);
			CloseableHttpResponse reponse = client.execute(get);
			HttpEntity entity = reponse.getEntity();
			String XMLStr = EntityUtils.toString(entity).trim();
			Document document = DocumentHelper.parseText(XMLStr);// 将XML串进行解析
			List<Element> result= document.getRootElement().elements();
			for (Element element : result) {
				BsSmsRecordJnl record = new BsSmsRecordJnl();
				String text = element.getText();
				Element mobileElement = element.element("srctermid");
				if("0".equals(text) || mobileElement == null || StringUtil.isBlank(mobileElement.getText())){
					continue;
				}
				Element receiveDateElement = element.element("receiveDate");
				Element serialNoElement = element.element("seqid");
				Element statusMsgElement = element.element("state");
				//记录获取到的参数
				record.setArriveTime(StringUtil.isNotBlank(receiveDateElement.getText())? DateUtil.parse(receiveDateElement.getText(),"yyyyMMddHHmmss") : null);
				record.setSerialNo(serialNoElement.getText());
				record.setMobile(mobileElement.getText());
				record.setStatusMsg(statusMsgElement.getText());
				if("DELIVRD".equals(record.getStatusMsg())){
					record.setStatusCode(0);
				}else{
					record.setStatusCode(2);
				}
				list.add(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOG.info("=============【亿美短信】查询状态，查到数据条数："+list.size()+"============");
		return list;
	}
	
	public static void main(String []args){
		//System.out.println(EMaySmsUtil.queryBalance());
		/*List<BsSmsRecordJnl> list = EMaySmsUtil.checkSmsSend();
		for (BsSmsRecordJnl bsSmsRecordJnl : list) {
			System.out.println(bsSmsRecordJnl.getMobile()+">>>"+bsSmsRecordJnl.getStatusMsg()+">>>"+DateUtil.getDateFormatPatter(bsSmsRecordJnl.getArriveTime()));
		}*/
		/*EMaySmsUtil.sendSMS("13588733651", "{普通定时任务}失败或异常，详情请检查告警表",null);
		EMaySmsUtil.sendSMS("13588733651", "定时任务{对账处理}失败或异常，详情请检查告警表。",null);*/
		/*EMaySmsUtil.sendSMS("17348527545", "【币港湾】您新手机的验证码为：BGW625343。",null,"1");
		EMaySmsUtil.sendSMS("15990147161", "【币港湾】您新手机的验证码为：BGW625343。",null,"1");*/
		//EMaySmsUtil.sendSMS("15990147161", "【币港湾】您新手机的验证码是B827385。",null,null);
		//EMaySmsUtil.sendSMS("15990147161", "您有一笔充值已成功，充值金额：￥11元。如有疑问请拨打400-806-1230。",null);
	}
}
