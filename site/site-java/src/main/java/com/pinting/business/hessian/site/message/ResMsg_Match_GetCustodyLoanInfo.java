package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * 存管港湾产品借款协议数据 出参
 * Created by shh on 2017/5/2 15:17.
 */
public class ResMsg_Match_GetCustodyLoanInfo extends ResMsg {

    private static final long serialVersionUID = 8931665859484284667L;

    /* 借款人姓名 */
    private String loanUserName;

    /* 借款人身份证号 */
    private String loanIdCard;

    /* 借款人达飞云贷账户 */
    private String loanMobile;

    /* 借款id */
    private Integer loanId;

    /* 借款金额 */
    private Double loanAmount;

    /* 期限：单位 天 */
    private Integer loanTerm;

    /* 借款出借日 */
    private Date loanDay;

    /* 借款到期日 */
    private Date loanDueDate;

    /* 借款年化利率 */
    private Double agreementRate;

    /* 出借金额总计 */
    private Double sumTotalAmount;

    /* 借款用途 */
    private String purpose;

    /* 到期偿还本息数额(赞时贷借款协议) */
    private Double principalInterestAmount;

    /* 理财人列表 */
    private ArrayList<HashMap<String,Object>> financialManagementList;

    /* 债权转让记录列表 */
    private ArrayList<HashMap<String,Object>> transferList;

    /* 存管云贷借款协议新旧版本时间区分标志 */
    private boolean yundaiLoanAgreementDateFlag;

    /* 期限 */
    private Integer theTerm;

    /* 7贷借款到期日 */
    private String sevenLoanDueDate;

    /* 合作方借款id */
    private String partnerLoanId;

    /*出借天数*/
    private Integer dayCount;

    /*借款人地址*/
    private String address;

    /*借款人邮箱*/
    private String email;
    
    /*业务合作标识*/
    private String partnerBusinessFlag;

    /* 分期产品借款服务费 */
    private Double fixLoanServiceRate;

    public String getLoanUserName() {
        return loanUserName;
    }

    public void setLoanUserName(String loanUserName) {
        this.loanUserName = loanUserName;
    }

    public String getLoanIdCard() {
        return loanIdCard;
    }

    public void setLoanIdCard(String loanIdCard) {
        this.loanIdCard = loanIdCard;
    }

    public String getLoanMobile() {
        return loanMobile;
    }

    public void setLoanMobile(String loanMobile) {
        this.loanMobile = loanMobile;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public Date getLoanDay() {
        return loanDay;
    }

    public void setLoanDay(Date loanDay) {
        this.loanDay = loanDay;
    }

    public Date getLoanDueDate() {
        return loanDueDate;
    }

    public void setLoanDueDate(Date loanDueDate) {
        this.loanDueDate = loanDueDate;
    }

    public Double getAgreementRate() {
        return agreementRate;
    }

    public void setAgreementRate(Double agreementRate) {
        this.agreementRate = agreementRate;
    }

    public Double getSumTotalAmount() {
        return sumTotalAmount;
    }

    public void setSumTotalAmount(Double sumTotalAmount) {
        this.sumTotalAmount = sumTotalAmount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Double getPrincipalInterestAmount() {
        return principalInterestAmount;
    }

    public void setPrincipalInterestAmount(Double principalInterestAmount) {
        this.principalInterestAmount = principalInterestAmount;
    }

    public ArrayList<HashMap<String, Object>> getFinancialManagementList() {
        return financialManagementList;
    }

    public void setFinancialManagementList(ArrayList<HashMap<String, Object>> financialManagementList) {
        this.financialManagementList = financialManagementList;
    }

    public ArrayList<HashMap<String, Object>> getTransferList() {
        return transferList;
    }

    public void setTransferList(ArrayList<HashMap<String, Object>> transferList) {
        this.transferList = transferList;
    }

    public boolean isYundaiLoanAgreementDateFlag() {
        return yundaiLoanAgreementDateFlag;
    }

    public void setYundaiLoanAgreementDateFlag(boolean yundaiLoanAgreementDateFlag) {
        this.yundaiLoanAgreementDateFlag = yundaiLoanAgreementDateFlag;
    }

    public Integer getTheTerm() {
        return theTerm;
    }

    public void setTheTerm(Integer theTerm) {
        this.theTerm = theTerm;
    }

    public String getSevenLoanDueDate() {
        return sevenLoanDueDate;
    }

    public void setSevenLoanDueDate(String sevenLoanDueDate) {
        this.sevenLoanDueDate = sevenLoanDueDate;
    }

    public String getPartnerLoanId() {
        return partnerLoanId;
    }

    public void setPartnerLoanId(String partnerLoanId) {
        this.partnerLoanId = partnerLoanId;
    }

    public Integer getDayCount() {
        return dayCount;
    }

    public void setDayCount(Integer dayCount) {
        this.dayCount = dayCount;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPartnerBusinessFlag() {
		return partnerBusinessFlag;
	}

	public void setPartnerBusinessFlag(String partnerBusinessFlag) {
		this.partnerBusinessFlag = partnerBusinessFlag;
	}

    public Double getFixLoanServiceRate() {
        return fixLoanServiceRate;
    }

    public void setFixLoanServiceRate(Double fixLoanServiceRate) {
        this.fixLoanServiceRate = fixLoanServiceRate;
    }
}
