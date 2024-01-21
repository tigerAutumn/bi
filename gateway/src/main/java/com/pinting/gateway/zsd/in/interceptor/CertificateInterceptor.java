package com.pinting.gateway.zsd.in.interceptor;

import com.pinting.core.util.GlobEnvUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * 证书校验及拦截
 * @project gateway
 * @title CertificateInterceptor.java
 * @author Dragon & cat
 * @date 2017-9-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class CertificateInterceptor extends HandlerInterceptorAdapter {
    private Logger log = LoggerFactory.getLogger(CertificateInterceptor.class);

    private static String PUBLIC_KEY_PATH = "D:\\workproject\\zanfenqi\\bigangwan_client.cer";
    private static PublicKey publicKey = null;

    static {
        PUBLIC_KEY_PATH = GlobEnvUtil.get("zsd.client.publicKey.path");
        FileInputStream fis = null;
        try {
            CertificateFactory certificatefactory=CertificateFactory.getInstance("X.509");
            fis = new FileInputStream(PUBLIC_KEY_PATH);
            X509Certificate Cert = (X509Certificate)certificatefactory.generateCertificate(fis);
            publicKey = Cert.getPublicKey();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fis != null ){
                try {
                    fis.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
        if(certs != null && certs.length > 0){
            for (X509Certificate cert : certs) {
                if(publicKey != null && cert.getPublicKey().equals(publicKey)){
                    return true;
                }
            }
        }
        String url = request.getScheme() + "://" + request.getServerName() + ":" +
                request.getServerPort() + request.getRequestURI();
        log.error(">>>非法的请求[" + url + "]<<<");
        throw new PTMessageException(PTMessageEnum.BAD_REQUEST);
        // return false;
    }

}
