package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.Date;

/**
 * Created by shh on 2018/1/30 22:37.
 */
public class ResMsg_Match_GetUserClaimsTransferInfoForPdf extends ResMsg {

    private static final long serialVersionUID = -9148200762268010691L;

    private Integer id; //ln_credit_transfer借款债权转让记录表 id

    private Double amount; //转让金额

    private Double inAmount; //本次转让债权价值(受让人出资金额)

    private String outUserName; //出让人姓名

    private String inUserName; //受让人姓名

    private String outUserMobile; //出让人手机号（账户）

    private String inUserMobile; //受让人手机号（账户）

    private String outUserIdCardNo; //出让人身份证

    private String inUserIdCardNo; //受让人身份证

    private String borrowUserName; //借款人姓名

    private String borrowUserIdCardNo; //借款人身份证

    private Date firstRepayDate; //还款起始日

    private Double repayAmount4Month; //每月应收本息

    private Double repayAmount4All; //预计债权年化收益

    private Integer term; //还款期限（月）

    private Integer leftTerm; //剩余还款月数

    private Integer firstTerm; //首次还款期次

    private Date transferTime; //转让时间

    private String productName; //计划名称

    private Integer inSubAccountId; //受让人子账户编号

    private  Double discountAmount; //转让折让金额

    private Double expectProfit; //预期收益

    private boolean zanAgreementDate; //赞分期新旧协议时间区分标志

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getInAmount() {
        return inAmount;
    }

    public void setInAmount(Double inAmount) {
        this.inAmount = inAmount;
    }

    public String getOutUserName() {
        return outUserName;
    }

    public void setOutUserName(String outUserName) {
        this.outUserName = outUserName;
    }

    public String getInUserName() {
        return inUserName;
    }

    public void setInUserName(String inUserName) {
        this.inUserName = inUserName;
    }

    public String getOutUserMobile() {
        return outUserMobile;
    }

    public void setOutUserMobile(String outUserMobile) {
        this.outUserMobile = outUserMobile;
    }

    public String getInUserMobile() {
        return inUserMobile;
    }

    public void setInUserMobile(String inUserMobile) {
        this.inUserMobile = inUserMobile;
    }

    public String getOutUserIdCardNo() {
        return outUserIdCardNo;
    }

    public void setOutUserIdCardNo(String outUserIdCardNo) {
        this.outUserIdCardNo = outUserIdCardNo;
    }

    public String getInUserIdCardNo() {
        return inUserIdCardNo;
    }

    public void setInUserIdCardNo(String inUserIdCardNo) {
        this.inUserIdCardNo = inUserIdCardNo;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    public String getBorrowUserIdCardNo() {
        return borrowUserIdCardNo;
    }

    public void setBorrowUserIdCardNo(String borrowUserIdCardNo) {
        this.borrowUserIdCardNo = borrowUserIdCardNo;
    }

    public Date getFirstRepayDate() {
        return firstRepayDate;
    }

    public void setFirstRepayDate(Date firstRepayDate) {
        this.firstRepayDate = firstRepayDate;
    }

    public Double getRepayAmount4Month() {
        return repayAmount4Month;
    }

    public void setRepayAmount4Month(Double repayAmount4Month) {
        this.repayAmount4Month = repayAmount4Month;
    }

    public Double getRepayAmount4All() {
        return repayAmount4All;
    }

    public void setRepayAmount4All(Double repayAmount4All) {
        this.repayAmount4All = repayAmount4All;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getLeftTerm() {
        return leftTerm;
    }

    public void setLeftTerm(Integer leftTerm) {
        this.leftTerm = leftTerm;
    }

    public Integer getFirstTerm() {
        return firstTerm;
    }

    public void setFirstTerm(Integer firstTerm) {
        this.firstTerm = firstTerm;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getInSubAccountId() {
        return inSubAccountId;
    }

    public void setInSubAccountId(Integer inSubAccountId) {
        this.inSubAccountId = inSubAccountId;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getExpectProfit() {
        return expectProfit;
    }

    public void setExpectProfit(Double expectProfit) {
        this.expectProfit = expectProfit;
    }

    public boolean isZanAgreementDate() {
        return zanAgreementDate;
    }

    public void setZanAgreementDate(boolean zanAgreementDate) {
        this.zanAgreementDate = zanAgreementDate;
    }
}
