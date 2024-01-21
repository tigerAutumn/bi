package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 管理台保证金代收代付返回对象
 * @author bianyatian
 * @2016-12-7 上午11:20:34
 */
public class MDepositDsDfResVO {
	
	private Integer rowno; //查询结果序号
	
	private String lnUserName; //融资客户姓名
	
	private String lnUserMobile; //用户手机号
	
	private String dsdfType; //代收代付类型
	
	private String lnUserCode; //融资客户代码

	private Double dsAmount; //代收金额
	
	private Double dfAmount; //代付金额
	
	private Date doneTime; //产生日期
	
	private Double sumDsAmount; //代收总金额
	
	private Double sumDfAmount; //代付总金额

	public String getLnUserName() {
		return lnUserName;
	}

	public void setLnUserName(String lnUserName) {
		this.lnUserName = lnUserName;
	}

	public String getLnUserMobile() {
		return lnUserMobile;
	}

	public void setLnUserMobile(String lnUserMobile) {
		this.lnUserMobile = lnUserMobile;
	}

	public String getDsdfType() {
		return dsdfType;
	}

	public void setDsdfType(String dsdfType) {
		this.dsdfType = dsdfType;
	}

	public Double getDsAmount() {
		return dsAmount;
	}

	public void setDsAmount(Double dsAmount) {
		this.dsAmount = dsAmount;
	}

	public Double getDfAmount() {
		return dfAmount;
	}

	public void setDfAmount(Double dfAmount) {
		this.dfAmount = dfAmount;
	}

	public Date getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}

	public String getLnUserCode() {
		return lnUserCode;
	}

	public void setLnUserCode(String lnUserCode) {
		this.lnUserCode = lnUserCode;
	}

	public Integer getRowno() {
		return rowno;
	}

	public void setRowno(Integer rowno) {
		this.rowno = rowno;
	}

	public Double getSumDsAmount() {
		return sumDsAmount;
	}

	public void setSumDsAmount(Double sumDsAmount) {
		this.sumDsAmount = sumDsAmount;
	}

	public Double getSumDfAmount() {
		return sumDfAmount;
	}

	public void setSumDfAmount(Double sumDfAmount) {
		this.sumDfAmount = sumDfAmount;
	}
	
	
}
