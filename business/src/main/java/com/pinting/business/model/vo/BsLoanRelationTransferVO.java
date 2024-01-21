package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 债权转让协议中需要的数据
 * @author bianyatian
 * @2016-9-21 上午11:05:54
 */
public class BsLoanRelationTransferVO {

	private Integer id; //ln_credit_transfer借款债权转让记录表 id

    private Integer outLoanRelationId; //出让人借贷关系编号

    private Integer inLoanRelationId; //受让人借贷关系编号

    private Integer outUserId; //出让人编号

    private Integer inUserId; //受让人编号

    private Integer outSubAccountId; //出让人子账户编号

    private Integer inSubAccountId; //受让人子账户编号

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

	private Double repayAmount4All; //应收本息总额
    
    private Integer term; //还款期限（月）
    
    private Integer leftTerm; //剩余还款月数
    
    private Integer firstTerm; //首次还款期次
    
    private Date transferTime; //转让时间
    
    private String productName; //计划名称

	private  Double discountAmount; //转让折让金额

	private Double productRate; //产品利率

	private Double expectProfit; //预期收益

	private Double approveAmount; //借款批准金额

	private Date interestBeginDate; //起息时间
    
	private String partnerBusinessFlag; //借款表业务标识
	
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOutLoanRelationId() {
		return outLoanRelationId;
	}

	public void setOutLoanRelationId(Integer outLoanRelationId) {
		this.outLoanRelationId = outLoanRelationId;
	}

	public Integer getInLoanRelationId() {
		return inLoanRelationId;
	}

	public void setInLoanRelationId(Integer inLoanRelationId) {
		this.inLoanRelationId = inLoanRelationId;
	}

	public Integer getOutUserId() {
		return outUserId;
	}

	public void setOutUserId(Integer outUserId) {
		this.outUserId = outUserId;
	}

	public Integer getInUserId() {
		return inUserId;
	}

	public void setInUserId(Integer inUserId) {
		this.inUserId = inUserId;
	}

	public Integer getOutSubAccountId() {
		return outSubAccountId;
	}

	public void setOutSubAccountId(Integer outSubAccountId) {
		this.outSubAccountId = outSubAccountId;
	}

	public Integer getInSubAccountId() {
		return inSubAccountId;
	}

	public void setInSubAccountId(Integer inSubAccountId) {
		this.inSubAccountId = inSubAccountId;
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

	public Date getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}

	public Integer getFirstTerm() {
		return firstTerm;
	}

	public void setFirstTerm(Integer firstTerm) {
		this.firstTerm = firstTerm;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getProductRate() {
		return productRate;
	}

	public void setProductRate(Double productRate) {
		this.productRate = productRate;
	}

	public Double getExpectProfit() {
		return expectProfit;
	}

	public void setExpectProfit(Double expectProfit) {
		this.expectProfit = expectProfit;
	}

	public Double getApproveAmount() {
		return approveAmount;
	}

	public void setApproveAmount(Double approveAmount) {
		this.approveAmount = approveAmount;
	}

	public Date getInterestBeginDate() {
		return interestBeginDate;
	}

	public void setInterestBeginDate(Date interestBeginDate) {
		this.interestBeginDate = interestBeginDate;
	}

	public String getPartnerBusinessFlag() {
		return partnerBusinessFlag;
	}

	public void setPartnerBusinessFlag(String partnerBusinessFlag) {
		this.partnerBusinessFlag = partnerBusinessFlag;
	}
	
}
