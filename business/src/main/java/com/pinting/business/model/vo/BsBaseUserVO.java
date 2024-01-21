package com.pinting.business.model.vo;

import com.pinting.business.model.BsUser;

public class BsBaseUserVO extends BsUser {

	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = 8041115299388992484L;
	
	private Integer age;
	
	private String sex;
	
	private String agentName;
	
	private String firstInvestDevice;
	
	private String area;
	
	private double weightInvestTrem;

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getFirstInvestDevice() {
		return firstInvestDevice;
	}

	public void setFirstInvestDevice(String firstInvestDevice) {
		this.firstInvestDevice = firstInvestDevice;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public double getWeightInvestTrem() {
		return weightInvestTrem;
	}

	public void setWeightInvestTrem(double weightInvestTrem) {
		this.weightInvestTrem = weightInvestTrem;
	}
}
