package com.pinting.open.pojo.request.product;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class BalanceBuyRequest extends Request {
	
	private Integer userId; // 用户ID
	
	private Double amount; // 购买金额
	
	private Integer productId; // 购买产品ID

	private String verifyCode;  // 短信验证码
	
	private Integer terminalType; // 来源(3:android,4:ios)
	
	private Integer redPacketId; // 优惠券ID（红包、加息券）

	private String type; // 类型：RED_PACKET-红包; INTEREST_TICKET-加息券；其他-未用优惠券

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getRedPacketId() {
		return redPacketId;
	}

	public void setRedPacketId(Integer redPacketId) {
		this.redPacketId = redPacketId;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/product/balanceBuy";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/product/balanceBuy";
	}

}
