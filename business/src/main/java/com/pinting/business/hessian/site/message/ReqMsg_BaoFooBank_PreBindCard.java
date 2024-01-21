package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2016/8/23
 * Description: 预绑卡（宝付）请求信息
 */
public class ReqMsg_BaoFooBank_PreBindCard extends ReqMsg {

    private static final long serialVersionUID = -3532471791521153900L;

    /* 用户姓名（持卡人姓名） */
    private String userName;

    /* 持卡人身份证号 */
    private String idCard;

    /* 银行卡号 */
    private String cardNo;

    /* 银行预留手机号 */
    private String mobile;

    /* 银行ID */
    private String bankId;

    /* 用户编号 */
    private String userId;

    /* 渠道类型 */
    private String terminalType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }
}
