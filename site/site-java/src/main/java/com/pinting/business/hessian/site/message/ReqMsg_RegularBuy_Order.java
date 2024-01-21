package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_RegularBuy_Order extends ReqMsg {

	private static final long serialVersionUID = 2059027016433053591L;

	private Double amount;
	
	private String cardNo; //银行卡号
	
	private String userName;
	
	private String idCard; //身份证号
	
	private String mobile;
	
	private Integer productId; //理财产品ID
	
	private Integer bankId; //银行ID
	
	private String bankName; //银行名称
	
	private Integer userId;
	
	private Integer isBind; //用户是否绑定@1已绑定,2未绑定
	
	private Integer transType; //交易类型1卡购买,2充值
	
	private Integer terminalType; //终端类型@1手机端,2PC端
	
	private Integer placeOrder; //下单类型@1预下单,2正式下单
	
	private String orderNo; //币港湾订单号
	
	private String verifyCode; //正式下单时输入的短信
	
	private String userMacAddr; //用户MAC地址
	
	private String userIpAddr; //用户IP地址
	
	private Integer redPacketId;   // 红包ID

	public String getUserMacAddr() {
		return userMacAddr;
	}

	public void setUserMacAddr(String userMacAddr) {
		this.userMacAddr = userMacAddr;
	}

	public String getUserIpAddr() {
		return userIpAddr;
	}

	public void setUserIpAddr(String userIpAddr) {
		this.userIpAddr = userIpAddr;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Integer getPlaceOrder() {
		return placeOrder;
	}

	public void setPlaceOrder(Integer placeOrder) {
		this.placeOrder = placeOrder;
	}

	public Integer getTransType() {
		return transType;
	}

	public void setTransType(Integer transType) {
		this.transType = transType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getIsBind() {
		return isBind;
	}

	public void setIsBind(Integer isBind) {
		this.isBind = isBind;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

    public Integer getRedPacketId() {
        return redPacketId;
    }

    public void setRedPacketId(Integer redPacketId) {
        this.redPacketId = redPacketId;
    }
	
}
