package com.pinting.business.util.chanzorSms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.business.model.BsSmsRecordJnl;
import com.pinting.business.util.DateUtil;
import com.pinting.core.util.StringUtil;


/**
 * 畅卓短信接口
 * @author bianyatian
 * @2016-7-4 下午2:42:07
 */
public class ChanzorSMSUtils {
    private static final Logger log = LoggerFactory.getLogger(ChanzorSMSUtils.class);
    
    private static String chanzorUrl = "http://sms.chanzor.com:8001"; //发送地址
    //private static String utf8URL = "http://sms.chanzor.com:8001/sms.aspx"; //短信发送地址
    //private static String gbkURL = "http://sms.chanzor.com:8001/smsGBK.aspx";
    
    private static String userid = ""; //企业ID，为空不需要
    private static String account = "ccs00820"; //用户帐号，由系统管理员
    private static String password = "013978"; //用户账号对应的密码
    
    //http://sms.chanzor.com:8001/sms.aspx?action=send&account=账号&password=密码&mobile=手机号&content=内容&sendTime=
	
    /**
     * 
     * @param mobile 发信发送的目的号码.多个号码之间用英文逗号隔开 
     * @param message 短信内容
     * @return 成功-true,失败-false
     */
    public static boolean utfSendReturnFlag(String mobile, String message){
    	boolean flag = false;
    	try {
			String PostData = "userid="+userid+"&account="+account+"&password="+password
					+"&mobile="+mobile+"&sendTime=&content="+URLEncoder.encode(message,"UTF-8");
			String result = SMS(PostData, chanzorUrl+"/sms.aspx?action=send");
			try {
				Document document = DocumentHelper.parseText(result);
	            Element rootElm = document.getRootElement();
	            Element memberElm = rootElm.element("returnstatus");
				String returnstatus = memberElm.getText();
				if("Success".equals(returnstatus)){
					flag = true;
				}
			} catch (DocumentException e) {
                e.printStackTrace();
                return flag;
            }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return flag;
		};
    	return flag;		
    }
    
    /**
     * 
     * @param mobile 发信发送的目的号码.多个号码之间用英文逗号隔开 
     * @param message 短信内容
     * @return 返回taskId，群发短信只返回一个taskId，该次发送所有短信共享同一个taskId
     */
    public static String utfSendReturnTaskId(String mobile, String message){
    	String taskId = "";
    	try {
			String PostData = "userid="+userid+"&account="+account+"&password="+password
					+"&mobile="+mobile+"&sendTime=&content="+URLEncoder.encode(message,"utf-8");
			String result = SMS(PostData, chanzorUrl+"/sms.aspx?action=send");
			try {
				Document document = DocumentHelper.parseText(result);
	            Element rootElm = document.getRootElement();
	            Element memberElm = rootElm.element("returnstatus");
				String returnstatus = memberElm.getText();
				String taskID = rootElm.element("taskID").getText();
				if("Success".equals(returnstatus)){
					taskId = taskID;
				}
			} catch (DocumentException e) {
                e.printStackTrace();
                return taskId;
            }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return taskId;
		};
    	return taskId;		
    }
    
    /**
     * 发送状态查询，taskId为空时，只能查询一次，每次最多获取1000条，获取后不能再次获取
 	 * taskId不为空时，按指定批次查询可以多次查询
     * @param taskId
     * @return
     */
    public static List<BsSmsRecordJnl> checkSmsSend(String taskId) {

		List<BsSmsRecordJnl> list = new ArrayList<BsSmsRecordJnl>();
		try {
			
			String PostData = "userid="+userid+"&account="+account+"&password="+password;
			if(StringUtil.isNotBlank(taskId)){
				PostData = PostData + "&taskid="+taskId;
			}
			String result = SMS(PostData, chanzorUrl+"/statusApi.aspx?action=query");
			Document document = DocumentHelper.parseText(result);
			List<Element> elementList= document.getRootElement().selectNodes("statusbox");
			for (Element element : elementList) {
				String mobile = element.element("mobile").getText();
				String taskid = element.element("taskid").getText();
				String status = element.element("status").getText();
				String receivetime = element.element("receivetime").getText();
				String errorcode = element.element("errorcode").getText();
				BsSmsRecordJnl record = new BsSmsRecordJnl();
				record.setArriveTime(StringUtil.isNotBlank(receivetime)? DateUtil.parse(receivetime,"yyyy-MM-dd HH:mm:ss") : null);
				record.setSerialNo(taskid);
				record.setMobile(mobile);
				record.setStatusCode(Integer.parseInt(status==""?"-1":status));
				record.setStatusMsg(errorcode);
				list.add(record);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
    
    
    public static String SMS(String postData, String postUrl) {
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return "";
    }
    
    public static void main(String[] args) {
        //System.out.println(utfSendReturnTaskId("15990147161,15868150199", "您的验证码是12991，请继续完成操作，请于30分钟内完成操作。【币港湾】"));
    	checkSmsSend("1607066406061704");
    	//checkSmsSend("");
    }
}
