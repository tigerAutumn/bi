package com.pinting.gateway.hessian.message.youbei;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_YouBei_CheckRealName extends ReqMsg {

	private static final long serialVersionUID = -7726865400612700730L;

	private String idCard;
	
	private String cardNo;
	
	private String name;
	
	private String mobile;

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
