package com.pinting.business.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import com.pinting.business.model.BsSmsRecordJnl;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.out.service.SMSServiceClient;

/**
 * 包括梦网短信
 * 
 * 欢迎您使用56短信接口,您的账号已经成功开通 用户名:bgwaqlc,密码:174lgts9,企业ID:2204,平台:10690
 * 为了您的账户安全,请及时修改您的密码,如有任何问题,请随时联系您的客服人员．
 * 密码接口修改地址:http://www.56dxw.com/company-dynamic/guanyuduanxinjiekouyewu.html
 * 公司网址:http://www.56dxw.com 台地址:http://sms.56dxw.com 技术支持QQ:852754126 826585910
 * 登陆sms.56dxw.com 可以查看余额 修改密码
 * 
 * @author Flouny.linkin
 * 
 */

public class SMSUtils {

	private static final Logger LOG = LoggerFactory.getLogger(SMSUtils.class);

	private static CloseableHttpClient client = HttpClients.createDefault();
	
	
	public SMSServiceClient smsServiceClient;

	/**
	 * 发送短信
	 * 
	 * @param mobile
	 *            目标手机号
	 * @param message
	 *            手机信息内容
	 * @return
	 */
	public static String sendSMS(String mobile, String message) {

		try {
			//判断手机号长度和是否为1开头
			if(StringUtil.isNotBlank(mobile) && mobile.length()== 11 && mobile.startsWith("1")){
				
				HttpGet get = new HttpGet(getURL(mobile, message));
				RequestConfig requestConfig = RequestConfig.custom()
						.setConnectionRequestTimeout(10000).setSocketTimeout(10000)
						.setConnectTimeout(10000).build();// 设置请求和传输超时时间
				get.setConfig(requestConfig);
				CloseableHttpResponse reponse = client.execute(get);
				HttpEntity entity = reponse.getEntity();
				// String resultMsg = EntityUtils.toString(entity).split("");
				String str = XMLToTextCheck(EntityUtils.toString(entity));
				if (StringUtils.isNotBlank(str)) {
					return str;
				} else {
					try {
						get = new HttpGet(getURL2(mobile, message));
						CloseableHttpResponse reponse3 = client.execute(get);
						HttpEntity entity3 = reponse3.getEntity();
						return (entity3 != null && "1".equalsIgnoreCase(EntityUtils
								.toString(entity3))) ? "56DX-succ" : "56DX-fail";
					} catch (Exception e2) {
						LOG.error(e2.getMessage(), e2);
					}
				}
			}
			
		} catch (Exception e) {
			try {
				HttpGet get = new HttpGet(getURL2(mobile, message));
				get = new HttpGet(getURL2(mobile, message));
				CloseableHttpResponse reponse3 = client.execute(get);
				HttpEntity entity3 = reponse3.getEntity();
				return (entity3 != null && "1".equalsIgnoreCase(EntityUtils
						.toString(entity3))) ? "56DX-succ" : "56DX-fail";
			} catch (Exception e3) {
				LOG.error(e3.getMessage(), e3);
			}
		}

		return "";
	}

	/**
	 * 提取xml里的值判断是否发送成功
	 * 成功则返回XMLStr,否则为空
	 * @param XMLStr
	 * @return
	 */
	public static String XMLToTextCheck(String XMLStr) {

		String isSuccess = "";
		if (XMLStr != null && (!"".equals(XMLStr.trim()))
				&& (!"error".equals(XMLStr.trim()))) {
			try {
				Document document = DocumentHelper.parseText(XMLStr);// 将XML串进行解析
				String result = document.getRootElement().getText();// 取得根结点
				if (result.length() > 10 && result.length() < 25) {
					isSuccess = result;
				}
			} catch (DocumentException e) {
				LOG.error("短信发送失败，返回结果："+XMLStr,e);
				return isSuccess;
			}
		}

		return isSuccess;
	}

	/**
	 * 梦网拼接url
	 * 
	 * @param phone
	 * @param content
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String getURL(String phone, String content)
			throws UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://61.145.229.29:9006/MWGate/wmgw.asmx/MongateCsSpSendSmsNew?");
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

	private static String getURL2(String phone, String content)
			throws UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://jiekou.56dxw.com/sms/HttpInterface.aspx?");
		buffer.append("comid=");
		buffer.append("2204");
		buffer.append("&username=");
		buffer.append("bgwaqlc");
		buffer.append("&userpwd=");
		buffer.append("174lgts9");
		buffer.append("&handtel=");
		buffer.append(phone);
		buffer.append("&sendcontent=");
		buffer.append(URLEncoder.encode(content.concat("【币港湾安心理财】"), "gbk"));
		buffer.append("&sendtime=&smsnumber=");
		buffer.append("10690");

		return buffer.toString();
	}
	
	
	public static List<BsSmsRecordJnl> checkSmsSend() {

		List<BsSmsRecordJnl> list = new ArrayList<BsSmsRecordJnl>();
		try {
			HttpGet get = new HttpGet(getServiceCheckURL());
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectionRequestTimeout(10000).setSocketTimeout(10000)
					.setConnectTimeout(10000).build();// 设置请求和传输超时时间
			get.setConfig(requestConfig);
			CloseableHttpResponse reponse = client.execute(get);
			HttpEntity entity = reponse.getEntity();
			
			Document document = DocumentHelper.parseText(EntityUtils.toString(entity).trim());// 将XML串进行解析
			List<Element> result= document.getRootElement().elements();
			
			HttpGet get2 = new HttpGet(getMarketingCheckURL());
			RequestConfig requestConfig2 = RequestConfig.custom()
					.setConnectionRequestTimeout(10000).setSocketTimeout(10000)
					.setConnectTimeout(10000).build();// 设置请求和传输超时时间
			get2.setConfig(requestConfig2);
			CloseableHttpResponse reponse2 = client.execute(get2);
			HttpEntity entity2 = reponse2.getEntity();
			
			Document document2 = DocumentHelper.parseText(EntityUtils.toString(entity2).trim());// 将XML串进行解析
			List<Element> result2= document2.getRootElement().elements();
			//List<Object> result = document.getRootElement().content();
			for (Element element : result) {
				BsSmsRecordJnl record = new BsSmsRecordJnl();
				String text = element.getText();
				text = text.replaceAll("，", ",");
				String[] textArr = text.split(",");
				record.setArriveTime(StringUtil.isNotBlank(textArr[1])? DateUtil.parse(textArr[1],"yyyy-MM-dd HH:mm:ss") : null);
				record.setSerialNo(textArr[2]);
				record.setMobile(textArr[4]);
				record.setStatusCode(Integer.parseInt(textArr[7]==""?"-1":textArr[7]));
				record.setStatusMsg(textArr[8]);
				list.add(record);
			}
			
			for (Element element2 : result2) {
				BsSmsRecordJnl record = new BsSmsRecordJnl();
				String text = element2.getText();
				text = text.replaceAll("，", ",");
				String[] textArr = text.split(",");
				record.setArriveTime(StringUtil.isNotBlank(textArr[1])? DateUtil.parse(textArr[1],"yyyy-MM-dd HH:mm:ss") : null);
				record.setSerialNo(textArr[2]);
				record.setMobile(textArr[4]);
				record.setStatusCode(Integer.parseInt(textArr[7]==""?"-1":textArr[7]));
				record.setStatusMsg(textArr[8]);
				list.add(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	/**
	 * 查询是否发送成功接口--服务短信
	 *   http://61.145.229.29:9006/MWGate/wmgw.asmx/MongateGetDeliver?userId=J02791&password=581026&iReqType=2
	 * @param phone
	 * @param content
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String getServiceCheckURL(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://61.145.229.29:9006/MWGate/wmgw.asmx/MongateGetDeliver?");
		buffer.append("userId=");
		buffer.append("J02791");
		buffer.append("&password=");
		buffer.append("581026");
		buffer.append("&iReqType=");
		buffer.append("2");

		return buffer.toString();
	}
	
	/**
	 * 查询是否发送成功接口--营销短信
	 *   http://61.145.229.28:8803/MWGate/wmgw.asmx/MongateGetDeliver?userId=J90760&password=589671&iReqType=2
	 * @param phone
	 * @param content
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String getMarketingCheckURL(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://61.145.229.28:8803/MWGate/wmgw.asmx/MongateGetDeliver?");
		buffer.append("userId=");
		buffer.append("J90760");
		buffer.append("&password=");
		buffer.append("589671");
		buffer.append("&iReqType=");
		buffer.append("2");

		return buffer.toString();
	}
	
	/**
	 * http://61.145.229.29:9006/MWGate/wmgw.asmx/MongateGetDeliver?userId=J02791&password=581026&iReqType=2
	 * 查询短信发送的状态
	 * http://61.145.229.29:9006/MWGate/wmgw.asmx/MongateQueryBalance?userId=J02791&password=581026
	 * 查询余额
	 */

	/**
	 * 
	 * @param 目标手机号
	 * @return 验证码
	 * @throws Exception
	 */
	public static String sendToMobiles(String mobile) {
		// 生成验证码
		Random random = new Random();
		StringBuffer sRand = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand.append(rand);
		}
		String content = "您的验证码为" + sRand.toString()
				+ "，有效期120秒，客服热线400-806-1230";
		if (StringUtils.isNotBlank(sendSMS(mobile, content))) {
			return sRand.toString();
		} else {
			return Constants.SEND_CODE_ERROR;
		}
	}
	
	/**
	 * 余额查询
	 * @return
	 */
	public static Integer queryBalance(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("http://61.145.229.29:9006/MWGate/wmgw.asmx/MongateQueryBalance?");
		buffer.append("userId=");
		buffer.append("J02791");
		buffer.append("&password=");
		buffer.append("581026");
		try {
			HttpGet get = new HttpGet(buffer.toString());
			CloseableHttpResponse reponse = client.execute(get);
			HttpEntity entity = reponse.getEntity();
			
			Document document = DocumentHelper.parseText(EntityUtils.toString(entity));//将XML串进行解析
			String result = document.getRootElement().getText();//取得根结点
			if(StringUtil.isNotEmpty(result)){
				return Integer.getInteger(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String []args){
//		SMSUtils.sendSMS("13588449807", "您有一笔投资已回款，总额：￥" + 23 + "元，其中本金：￥"+ 43 + "元，利息：￥"+ 12+ "元，如有疑问请拨打400-806-1230。");

//		SMSUtils.sendSMS("13588449807", "您有一笔金额为：￥" + 23+ "元的提现已完成，如有疑问请拨打400-806-1230。");
//		SMSUtils.sendSMS("13588449807", "您有一笔金额为：￥" + 344+ "元的提现已完成，如有疑问请拨打400-806-1230。");
//		SMSUtils.sendSMS("13588449807","您的验证码为" + 1234
//				+ "，有效期120秒，客服热线400-806-1230");
		//SMSUtils.sendSMS("13777588488","您有一笔投资已成功，本金：xxx元, 投资周期：xxx, 投资利率：xxx，次日起息。如有疑问请拨打400-806-1230。");
//		SMSUtils.sendSMS("13777588488","您有一笔投资已成功，本金：xxx元, 投资周期：xxx, 投资利率：xxx，次日起息。如有疑问请拨打400-806-1230。");
//		SMSUtils.sendSMS("13777588488","您有一笔投资已回款至您的币港湾账户余额，总额：xxx元，其中本金：xxx元，利息：xxx元，如有疑问请拨打400-806-1230。");
		/*String s = SMSUtils.sendSMS("15990147161111","信息提示：您预约的产品云贷循环贷A0001期大约5分钟后开始申购，请知晓。");
		System.out.println(s);
		String bankCardNo = "1234567890";
		bankCardNo = bankCardNo.substring(bankCardNo.length()-4,bankCardNo.length());*/
		
//		SMSUtils.sendSMS("13588449807","奖励转余额失败，请及时充值。");
//		SMSUtils.sendSMS("15658070761","您有一笔投资未成功，失败原因：dddd，请核实，如有疑问请拨打400-806-1230。");
//		SMSUtils.sendSMS("15658070761","您有一笔充值已成功，充值金额dddd。如有疑问请拨打400-806-1230。");
//		SMSUtils.sendSMS("15658070761","您有一笔充值未成功，失败原因：gggg，请核实，如有疑问请拨打400-806-1230。");
//		SMSUtils.sendSMS("13588449807","日终【到期未正常兑付告警】跑批失败。");
//		SMSUtils.sendSMS("13588449807","您有一笔投资已回款到账户余额，总额：￥xxx元，其中本金：￥xxx元，利息：￥xxx元，如有疑问请拨打400-806-1230。");
//		SMSUtils.sendSMS("13588449807","您有一笔投资已回款至尾号XXXX的银行卡，总额：xxx元，其中本金：xxx元，利息：xxx元，如有疑问请拨打400-806-1230。");
//		SMSUtils.sendSMS("13588449807","{人工发起单笔产品打款}转账申请异常：dsd");
//		SMSUtils.sendSMS("13588449807","{购买达飞理财发起}支付订单号[ddddd]购买达飞理财发起通讯异常");
//		SMSUtils.sendSMS("13588449807","{购买达飞理财结果通知}产品批次号[ggg]购买达飞理财失败");
//		SMSUtils.sendSMS("13588449807","{打款给达飞结果通知}订单号[ffff]打款给达飞失败");
//		SMSUtils.sendSMS("13588449807","{系统钱包转账-理财购买}订单号[gfd]转账申请通讯异常");
//		SMSUtils.sendSMS("13588449807","{达飞理财回款通知}产品批次号[hgf]确认转账划拨失败");
//		SMSUtils.sendSMS("13588449807","{批量客户回款}批次号[fgf]回款产生异常");
//		SMSUtils.sendSMS("13588449807","{单笔客户回款}产品户编号[gfd]回款产生异常");
//		SMSUtils.sendSMS("13588449807","{用户回款到卡通知}订单号[hjg]19付重复通知");
//		SMSUtils.sendSMS("13588449807","定时任务{19付对账}对账失败：ghjjj");
//		SMSUtils.sendSMS("13588449807","定时任务{系统钱包转账重发-理财购买}19订单号[ghj]理财购买发起异常：ffg");
//		SMSUtils.sendSMS("13588449807","定时任务{系统钱包转账重发-理财购买}理财购买发起异常：45666");
//		SMSUtils.sendSMS("13588449807","定时任务{系统钱包转账-理财购买}转账申请异常：4556");
//		SMSUtils.sendSMS("13588449807","定时任务{理财购买重发-购买失败}理财购买发起异常：5667");
//		SMSUtils.sendSMS("13588449807","定时任务{理财购买重发-打款确认失败}19订单号[fff]购买重发失败：fff");
//		SMSUtils.sendSMS("13588449807","定时任务{理财购买重发-打款确认失败}理财购买发起异常：fgg");
//		SMSUtils.sendSMS("13588449807","定时任务{用户回款失败重发}用户回款重发异常：fggg");
//		SMSUtils.sendSMS("13588449807","{sss|dds|交易成功}对账不一致：本地订单处理中，三方343");
//		SMSUtils.sendSMS("13588449807","{332|556|交易成功}对账不一致：本地订单失败或金额不匹配");
//		SMSUtils.sendSMS("13588449807","{34r|交易结果未知}对账不一致：本地订单成功，但三方未成功或无成功记录");
//		SMSUtils.sendSMS("13588449807","{455|455|交易成功}对账不一致：第三方成功，但本地未记录此账务");
//		SMSUtils.sendSMS("13588449807","{455|677|交易成功}对账不一致：第三方成功，但本地订单失败或金额不匹配");
//		SMSUtils.sendSMS("13588449807","{sdsd}失败或异常，详情请检查告警表");
		//SMSUtils.sendSMS("13588449807","定时任务{sasa}失败或异常，详情请检查告警表");
//		sendPathSMS("服务恢复通知：您的招商银行卡已经恢复手机购买");
		/*System.out.println("ddddd");
		String ss= "abcdef";
		ss = ss.substring(0,ss.length()-1);
		System.out.println(ss);*/
		
		queryBalance();
	}
	
	static void sendPathSMS(String s){
//		String mobiles = "13173641241|13735892391|15088212402|15257122147|13276710175|13588869818|13588449807|15988975757|18661877209|15967102506|18668218212|18057185110|13666663751|13588026412|13077890350|15622869669|13905719777|13913310188|18600157515|15990094163|18814868301|15336898157|13805730335|15011323970|13757167374|13758115449|13362742238|18668431513|18268322579|18969978199|13221838815|13758220278|13616503822|18905716160|13916061113|13958166898|13989814946|18868700601|13777846885|13867419534|13705716372|13816896765|18001583400|15958188107|13738098396|13917955179|18058123248|13811521225|18201196151|13906606719|13646852055|15268557112|18626862036|18625153064|18626866036|13910896089|15158191349|15818589833|15356185861|13022123828|13566569222|13605819145|18655150227|13600533256|15519940124|13940196633|15957109967|13923002563|15381169161|13296720908|18025338982|15167113757|13082818886|15967128524|13989811741|18758297611|18655168311|15958184437|18810815952|15558150816|18267919759|13611195315|18515217113";
		String mobiles = "13588449807,15267034367";
		String [] ms = mobiles.split(",");
		for(int i=0;i<ms.length;i++){
			SMSUtils.sendSMS(ms[i],s);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}