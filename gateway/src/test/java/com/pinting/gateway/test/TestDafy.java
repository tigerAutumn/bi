package com.pinting.gateway.test;

import com.alibaba.fastjson.JSON;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.baofoo.out.model.req.SendSmsReq;
import com.pinting.gateway.baofoo.out.service.*;
import com.pinting.gateway.bird.in.model.LoanReq;
import com.pinting.gateway.business.in.facade.CustomerFacade;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByIdReqModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByIdResModel;
import com.pinting.gateway.dafy.out.service.SendDafyService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.util.Date;

/**
 * @author Zhou Changzai
 * @Project: gateway
 * @Title: TestDafy.java
 * @date 2015-2-12 下午3:31:50
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class TestDafy extends TestBase {
    @Autowired
    private CustomerFacade customerFacade;
    @Autowired
    BindCardService bindCardService;
    @Autowired
    AttestationPayService attestationPayService;
    @Autowired
    Pay4AnotherService pay4AnotherService;
    @Autowired
    private NewCounterService newCounterService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private SendDafyService sendDafyService;


    @Test
    public void testLogin2Dafy() throws Exception {

        //CloseableHttpClient httpclient = HttpClients.createDefault();

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream keyInstream = new FileInputStream(new File("C:\\Users\\dingpengfeng\\Desktop\\client.jks"));
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream instream = new FileInputStream(new File("C:\\Users\\dingpengfeng\\Desktop\\trust.jks"));
        try {
            keyStore.load(keyInstream, "011342".toCharArray());
            trustStore.load(instream, "011318".toCharArray());
        } catch (CertificateException e) {
            e.printStackTrace();
        } finally {
            try {
                keyInstream.close();
                instream.close();
            } catch (Exception ignore) {
            }
        }
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, "011342".toCharArray())
                .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
        // 只允许使用TLSv1协议
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        HttpPost httpPost = new HttpPost("https://zan.bigangwan.com:8443/gateway/remote/rest/loan");
        httpPost.setHeader("Content-Type", "application/json");
        LoanReq loan = new LoanReq();
        loan.setOrderNo("123");
        loan.setLoanId("233");
        loan.setBindId("123131312313");
        String result = JSON.toJSONString(loan);

        StringEntity entity = new StringEntity(result, HTTP.UTF_8);
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        try {
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpclient.execute(httpPost);

            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);//取出应答字符串

                System.out.println(result);
            } else {
                System.out.println(httpResponse.getStatusLine());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
