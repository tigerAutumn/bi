package com.pinting.business.model.vo;

public class AddressDetailInfo {
	
	private String sex;
	
	private String birthday;
	
	private String address;

	private String telString; //手机号码
	
	private String province; //省份
	
	private String city;//城市
	
	private String prov;//省份
	
	public String getTelString() {
		return telString;
	}

	public void setTelString(String telString) {
		this.telString = telString;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

}
