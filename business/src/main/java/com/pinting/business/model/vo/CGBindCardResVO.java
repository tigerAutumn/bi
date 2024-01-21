package com.pinting.business.model.vo;

/**
 * Created by Administrator on 2017/9/20.
 */
public class CGBindCardResVO {

    private String resCode;

    private String resMsg;

    private String hfUserId;

    // 解绑||绑卡
    private String note;

    private String orderNo;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getHfUserId() {
        return hfUserId;
    }

    public void setHfUserId(String hfUserId) {
        this.hfUserId = hfUserId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
