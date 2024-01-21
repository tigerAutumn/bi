package com.pinting.gateway.hessian.message.loan;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_LoanCif_PreBindCard extends ReqMsg {

    /**
     * 预绑卡订单号
     */
    private String orderNo;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 银行卡号
     */
    private String bankCard;

    /**
     * 用户姓名
     */
    private String cardHolder;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 银行预留手机号
     */
    private String mobile;

    /**
     * 银行编码
     */
    private String bankCode;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
