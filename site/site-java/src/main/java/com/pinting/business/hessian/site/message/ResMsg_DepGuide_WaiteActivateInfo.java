package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      cyb
 * Date:        2017/4/13
 * Description:
 */
public class ResMsg_DepGuide_WaiteActivateInfo extends ResMsg {

    private static final long serialVersionUID = -687333190981882550L;

    /* 手机号 */
    private String mobile;
    /* 用户姓名 */
    private String userName;
    /* 身份证号 */
    private String idCard;
    /* 银行卡号 */
    private String cardNo;
    /* 所属银行 */
    private String bankName;
    /* 个人存管账户 */
    private String hfUserId;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getHfUserId() {
        return hfUserId;
    }

    public void setHfUserId(String hfUserId) {
        this.hfUserId = hfUserId;
    }

}
