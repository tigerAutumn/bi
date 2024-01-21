package com.pinting.gateway.qidian.out.util;
/**
 * 
 * @project gateway
 * @title DepLoan7OutMsg.java
 * @author Dragon & cat
 * @date 2017-12-12
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class QiDianOutMsg {

    /**
     * 约定的大P客户ID
     */
    private String sysCustomerId;
    /**
     * 商户号
     */
    private String merchantId;

    /**
     * 客户端标识
     */
    private String clientKey;

    /**
     * 加解密key
     */
    private String outDesKey;

    private String clientSecret;

    /**
     * 购买商品的请求地址
     */
    private String url;

    public String getSysCustomerId() {
        return sysCustomerId;
    }

    public void setSysCustomerId(String sysCustomerId) {
        this.sysCustomerId = sysCustomerId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getOutDesKey() {
        return outDesKey;
    }

    public void setOutDesKey(String outDesKey) {
        this.outDesKey = outDesKey;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
