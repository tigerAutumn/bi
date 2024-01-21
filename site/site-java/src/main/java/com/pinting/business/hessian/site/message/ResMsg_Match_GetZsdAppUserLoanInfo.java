package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * 赞时贷APP借款协议 出参
 * Created by shh on 2017/10/31 14:00.
 * @author shh
 */
public class ResMsg_Match_GetZsdAppUserLoanInfo extends ResMsg {

    private static final long serialVersionUID = 5077688271655565498L;

    /* 出借人列表 */
    private  ArrayList<HashMap<String, Object>> dataUserInfo;

    /* 借款信息表id */
    private Integer id;

    /* 借款人姓名 */
    private String lnUserName;

    /* 借款人身份证号 */
    private String lnUserIdCard;

    /* 借款人赞时贷账户 */
    private String lnUserZsdAccount;

    /* 借款时间（协议签署时间）*/
    private Date loanTime;

    /* 借款用途 */
    private String purpose;

    /* 借款本金数额 */
    private Double approveAmount;

    /* 借款期限 */
    private Integer period;

    /* 协议利率 */
    private Double agreementRate;

    public ArrayList<HashMap<String, Object>> getDataUserInfo() {
        return dataUserInfo;
    }

    public void setDataUserInfo(ArrayList<HashMap<String, Object>> dataUserInfo) {
        this.dataUserInfo = dataUserInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLnUserName() {
        return lnUserName;
    }

    public void setLnUserName(String lnUserName) {
        this.lnUserName = lnUserName;
    }

    public String getLnUserIdCard() {
        return lnUserIdCard;
    }

    public void setLnUserIdCard(String lnUserIdCard) {
        this.lnUserIdCard = lnUserIdCard;
    }

    public String getLnUserZsdAccount() {
        return lnUserZsdAccount;
    }

    public void setLnUserZsdAccount(String lnUserZsdAccount) {
        this.lnUserZsdAccount = lnUserZsdAccount;
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Double getApproveAmount() {
        return approveAmount;
    }

    public void setApproveAmount(Double approveAmount) {
        this.approveAmount = approveAmount;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Double getAgreementRate() {
        return agreementRate;
    }

    public void setAgreementRate(Double agreementRate) {
        this.agreementRate = agreementRate;
    }
}
