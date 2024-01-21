package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 七贷三方借款协议 出参
 * Created by zhangpeng on 2018/3/5
 * @author zhangpeng
 */

public class ResMsg_Match_GetSevenUserLoanInfo extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5614174437744840623L;

	/* 出借人列表 */
    private  ArrayList<HashMap<String, Object>> dataUserInfo;

    /* 协议编号 */
    private String partnerLoanId;

    /* 借款人姓名 */
    private String lnUserName;

    /* 借款人身份证号 */
    private String lnUserIdCard;

    /* 借款人币港湾账户 */
    private String lnUserBGWAccount;

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
    
    /*借款服务费率*/
    private Double loanServiceRate;

    /*出借天数*/
    private Integer dayCount;
    
    public ArrayList<HashMap<String, Object>> getDataUserInfo() {
        return dataUserInfo;
    }

    public void setDataUserInfo(ArrayList<HashMap<String, Object>> dataUserInfo) {
        this.dataUserInfo = dataUserInfo;
    }

	public String getPartnerLoanId() {
		return partnerLoanId;
	}

	public void setPartnerLoanId(String partnerLoanId) {
		this.partnerLoanId = partnerLoanId;
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

    public String getLnUserBGWAccount() {
        return lnUserBGWAccount;
    }

    public void setLnUserBGWAccount(String lnUserBGWAccount) {
        this.lnUserBGWAccount = lnUserBGWAccount;
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

	public Double getLoanServiceRate() {
		return loanServiceRate;
	}

	public void setLoanServiceRate(Double loanServiceRate) {
		this.loanServiceRate = loanServiceRate;
	}

	public Integer getDayCount() {
		return dayCount;
	}

	public void setDayCount(Integer dayCount) {
		this.dayCount = dayCount;
	}
	
}
