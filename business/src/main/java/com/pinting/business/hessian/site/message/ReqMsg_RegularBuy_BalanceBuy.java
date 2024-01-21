package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_RegularBuy_BalanceBuy extends ReqMsg {

	private static final long serialVersionUID = 942721199137356092L;

	private Integer userId;
	
	private Double amount;
	
	private Integer productId;
	
	private Integer terminalType; //终端类型@1手机端,2PC端

	private String  ticketType;     // RED_PACKET-红包; INTEREST_TICKET-加息券

	private Integer ticketId;       // 优惠券ID（红包ID、加息券ID）

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public Integer getTicketId() {
		return ticketId;
	}

	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
	
}
