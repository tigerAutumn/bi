package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;

public class InvestBuyAgreeMentResponse extends Response {
	
    private String            mobile;                                  //	手机号		
    private String            idCard;                                  //	身份证号码	
    private String            userName;                                //	用户真实姓名
    private Integer     	  dayNum;									//	投资期限
    private Double            rate;										//	利率
    private String 			  name;										//	产品名称
    private Double 			  amount;  									//	投资金额
    private String 			  times;									//	当前日期
    private String 			  openTime;             					//	协议签署日期
    private String 			  beginTime;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getDayNum() {
		return dayNum;
	}
	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
    
    
}
