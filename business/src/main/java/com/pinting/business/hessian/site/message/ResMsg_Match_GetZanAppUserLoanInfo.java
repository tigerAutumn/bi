package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * 赞分期App借款协议  出参
 * Created by shh on 2016/9/24 11:34.
 */
public class ResMsg_Match_GetZanAppUserLoanInfo extends ResMsg {

    private static final long serialVersionUID = 3607155675301718943L;

    /* 出借人列表 */
    private  ArrayList<HashMap<String, Object>> dataUserInfo;

    /* 借款信息表id */
    private Integer id;

    /* 借款人姓名 */
    private String lnUserName;

    /* 借款人身份证号 */
    private String lnUserIdCard;

    /* 借款人赞分期账户 */
    private String lnUserZANAccount;

    /* 借款时间（协议签署时间）*/
    private Date loanTime;

    /* 借款用途 */
    private String purpose;

    /* 借款本金数额 */
    private Double approveAmount;

    /* 借款年化利率 */
    private Double lnRate;

    /* 借款期限 */
    private Integer period;

    /* 月偿还本息数额 */
    private Double needRepayMoney4Month;

    /* 第一期还款日 */
    private Date lnRepayStartDate;

    /* 最后一期还款日 */
    private Date lnRepayEndDate;

    /* 每月还款日 */
    private Integer day4Month;
    
    /*显示费率规则类型     OLD 老、NEW  新*/
    private String	showLateFeeType;	

    /* 赞分期新旧协议时间区分标志 */
    private boolean zanAgreementDate;

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

    public String getLnUserZANAccount() {
        return lnUserZANAccount;
    }

    public void setLnUserZANAccount(String lnUserZANAccount) {
        this.lnUserZANAccount = lnUserZANAccount;
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

    public Double getLnRate() {
        return lnRate;
    }

    public void setLnRate(Double lnRate) {
        this.lnRate = lnRate;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Double getNeedRepayMoney4Month() {
        return needRepayMoney4Month;
    }

    public void setNeedRepayMoney4Month(Double needRepayMoney4Month) {
        this.needRepayMoney4Month = needRepayMoney4Month;
    }

    public Date getLnRepayStartDate() {
        return lnRepayStartDate;
    }

    public void setLnRepayStartDate(Date lnRepayStartDate) {
        this.lnRepayStartDate = lnRepayStartDate;
    }

    public Date getLnRepayEndDate() {
        return lnRepayEndDate;
    }

    public void setLnRepayEndDate(Date lnRepayEndDate) {
        this.lnRepayEndDate = lnRepayEndDate;
    }

    public Integer getDay4Month() {
        return day4Month;
    }

    public void setDay4Month(Integer day4Month) {
        this.day4Month = day4Month;
    }

    public boolean isZanAgreementDate() {
        return zanAgreementDate;
    }

    public void setZanAgreementDate(boolean zanAgreementDate) {
        this.zanAgreementDate = zanAgreementDate;
    }

	public String getShowLateFeeType() {
		return showLateFeeType;
	}

	public void setShowLateFeeType(String showLateFeeType) {
		this.showLateFeeType = showLateFeeType;
	}
    
}
