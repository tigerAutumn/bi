package com.pinting.business.model.vo;

import java.text.DecimalFormat;

import com.pinting.business.model.Bs19payBank;

public class Bs19payBankVO extends Bs19payBank{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5055941535839401816L;
	/** 银行名称**/
	private String name;
	/**单笔限额**/
    private String oneTopStr;
    /**单日限额**/
    private String dayTopStr;
    /**每月限额**/
    private String monthTopStr;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOneTopStr() {
		if(getOneTop() != null){
			oneTopStr = new DecimalFormat("#.##").format(Double.parseDouble(getOneTop().toString()));
    	}
		return oneTopStr;
	}
	public void setOneTopStr(String oneTopStr) {
		this.oneTopStr = oneTopStr;
	}
	public String getDayTopStr() {
		if(getDayTop() != null){
			dayTopStr = new DecimalFormat("#.##").format(Double.parseDouble(getDayTop().toString()));
    	}
		return dayTopStr;
	}
	public void setDayTopStr(String dayTopStr) {
		this.dayTopStr = dayTopStr;
	}
	public String getMonthTopStr() {
		if(getMonthTop() != null){
			monthTopStr = new DecimalFormat("#.##").format(Double.parseDouble(getMonthTop().toString()));
    	}
		return monthTopStr;
	}
	public void setMonthTopStr(String monthTopStr) {
		this.monthTopStr = monthTopStr;
	}
	

}
