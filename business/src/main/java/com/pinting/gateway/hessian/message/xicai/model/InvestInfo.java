package com.pinting.gateway.hessian.message.xicai.model;

import java.io.Serializable;

public class InvestInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -417576940069532853L;

	private Integer id;
	
	private String pid;
	
	private String username;
	
	private String display;
	
	private String datetime;
	
	private Integer money;
	
	private Double commision;
	
	private String life_cycle;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Double getCommision() {
		return commision;
	}

	public void setCommision(Double commision) {
		this.commision = commision;
	}

	public String getLife_cycle() {
		return life_cycle;
	}

	public void setLife_cycle(String life_cycle) {
		this.life_cycle = life_cycle;
	}
	
	
}
