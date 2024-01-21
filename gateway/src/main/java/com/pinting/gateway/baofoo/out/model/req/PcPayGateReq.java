package com.pinting.gateway.baofoo.out.model.req;

/**
 * Created by 剑钊 on 2016/7/22.
 */
public class PcPayGateReq extends BaoFooOutBaseReq{

    /**
     * 功能 ID
     * 若选择全部银行则为空字符串，选择全部银行即跳转宝付收银台选择银行
     * 招商银行（借） 3001
     * 中国工商银行（借） 3002
     * 中国建设银行（借） 3003
     */
    private String payId;

    /**
     * 订单日期
     * 格式：年年年年月月日日时时分分秒秒
     */
    private String tradeDate;

    /**
     * 订单号
     * 唯一订单号，8-20 位字母和数字宝付将以此作为结算的唯一凭证
     */
    private String transId;

    /**
     * 订单金额
     * 单位：分
     */
    private String orderMoney;

    /**
     * 商品名称（非必填）
     */
    private String productName;

    /**
     * 用户名称（非必填）
     */
    private String userName;

    /**
     * 附加字段（长度不能超过128位）
     */
    private String additionalInfo;

    /**
     * 页面返回地址
     */
    private String pageUrl;

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }
}
