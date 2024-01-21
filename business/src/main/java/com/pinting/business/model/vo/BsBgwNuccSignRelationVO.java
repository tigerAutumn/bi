package com.pinting.business.model.vo;

import com.pinting.business.model.BsBgwNuccSignRelation;

public class BsBgwNuccSignRelationVO extends BsBgwNuccSignRelation {

    private String userIdFlag; // 用户id校验标识，error表示格式校验不通过

    private String protocolNoFlag; // 网联协议号校验标识，error表示格式校验不通过

    private String cardNoFlag; //银行卡号校验标识，error表示格式校验不通过

    private String userIdStr; // 用户编号

    private String errorMsg; // 错误信息

    private int lineNo; // 行号

    public String getUserIdFlag() {
        return userIdFlag;
    }

    public void setUserIdFlag(String userIdFlag) {
        this.userIdFlag = userIdFlag;
    }

    public String getProtocolNoFlag() {
        return protocolNoFlag;
    }

    public void setProtocolNoFlag(String protocolNoFlag) {
        this.protocolNoFlag = protocolNoFlag;
    }

    public String getCardNoFlag() {
        return cardNoFlag;
    }

    public void setCardNoFlag(String cardNoFlag) {
        this.cardNoFlag = cardNoFlag;
    }

    public String getUserIdStr() {
        return userIdStr;
    }

    public void setUserIdStr(String userIdStr) {
        this.userIdStr = userIdStr;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getLineNo() {
        return lineNo;
    }

    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }
}
