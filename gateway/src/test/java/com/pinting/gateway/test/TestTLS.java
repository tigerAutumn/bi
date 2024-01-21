/**
 * Company: www.baofu.com
 * @author dasheng(大圣)
 * @date 2018年1月26日
 */
package com.pinting.gateway.test;


import com.pinting.gateway.baofoo.out.model.req.BaoFooOutBaseReq;
import com.pinting.gateway.baofoo.out.util.BaoFooHttpUtil;
import com.pinting.gateway.util.HttpClientUtil;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.util.HashMap;


/**
 * 
 *  升级后我司只支持TLSv1.1，TLSv1.2
 *  运行后有返回值（biz_resp_code=BF00436&biz_resp_msg=交易类型不存在&resp_code=F）说明请求正常
 *  
 *  SSLContext ctx = SSLContext.getInstance("TLSv1.2");或SSLContext ctx = SSLContext.getInstance("TLSv1.1");
 *  在SimpleHttpClient类中的enableSSL方法内
 */ 

public class TestTLS{
	public static void main(String[] args) throws Exception {		
		
		HashMap<String,String> DateArry = new HashMap<String,String>();
		DateArry.put("terminal_id", "100000917");
		DateArry.put("member_id", "100000178");
		BaoFooOutBaseReq req = new BaoFooOutBaseReq();
		String postString  = BaoFooHttpUtil.requestForm("https://vgw.baofoo.com/cutpayment/protocol/backTransRequest", req,DateArry);
		System.out.println(postString);


		String s = HttpClientUtil.sendRequest("http://xxx", null);
		System.out.println(s);

		//查询支持的TLS协议
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, null, null);

		SSLSocketFactory factory = (SSLSocketFactory) context.getSocketFactory();
		SSLSocket socket = (SSLSocket) factory.createSocket();

		String[] protocols = socket.getSupportedProtocols();

		System.out.println("Supported Protocols: " + protocols.length);
		for (int i = 0; i < protocols.length; i++) {
			System.out.println(" " + protocols[i]);
		}

		protocols = socket.getEnabledProtocols();

		System.out.println("Enabled Protocols: " + protocols.length);
		for (int i = 0; i < protocols.length; i++) {
			System.out.println(" " + protocols[i]);
		}
	}
}