/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: MerchantDfQueryReq.java, v 0.1 2015-8-11 下午4:36:53 BabyShark Exp $
 */
public class MerchantDfQueryReq extends Pay4AnotherBaseReq {

    /**  */
    private static final long serialVersionUID = -5689275591798920468L;

    private String            tradeDate;
    private String            mxOrderId;
    private String            extend1;
    private String            extend2;
    private String            extend3;

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getMxOrderId() {
        return mxOrderId;
    }

    public void setMxOrderId(String mxOrderId) {
        this.mxOrderId = mxOrderId;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3;
    }

}
