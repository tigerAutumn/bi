package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_RegularBuy_InfoQuery extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7199278161567799109L;
	
	private int id;  //产品id号
	
	private int userId;

	private String nick;
	
	private Double money; //购买金额
	
	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
