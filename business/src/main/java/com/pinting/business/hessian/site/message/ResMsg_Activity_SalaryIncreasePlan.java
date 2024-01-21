package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Activity_SalaryIncreasePlan extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4117831224414844158L;

	private ArrayList<HashMap<String, Object>> moreThan10000List;
	
	private Integer moreThan10000Quota;
	
	private ArrayList<HashMap<String, Object>> moreThan50000List;
	
	private Integer moreThan50000Quota;
	
	private ArrayList<HashMap<String, Object>> moreThan100000List;
	
	private Integer moreThan100000Quota;
	
	private ArrayList<HashMap<String, Object>> moreThan500000List;
	
	private Integer moreThan500000Quota;

	public ArrayList<HashMap<String, Object>> getMoreThan10000List() {
		return moreThan10000List;
	}

	public void setMoreThan10000List(
			ArrayList<HashMap<String, Object>> moreThan10000List) {
		this.moreThan10000List = moreThan10000List;
	}

	public Integer getMoreThan10000Quota() {
		return moreThan10000Quota;
	}

	public void setMoreThan10000Quota(Integer moreThan10000Quota) {
		this.moreThan10000Quota = moreThan10000Quota;
	}

	public ArrayList<HashMap<String, Object>> getMoreThan50000List() {
		return moreThan50000List;
	}

	public void setMoreThan50000List(
			ArrayList<HashMap<String, Object>> moreThan50000List) {
		this.moreThan50000List = moreThan50000List;
	}

	public Integer getMoreThan50000Quota() {
		return moreThan50000Quota;
	}

	public void setMoreThan50000Quota(Integer moreThan50000Quota) {
		this.moreThan50000Quota = moreThan50000Quota;
	}

	public ArrayList<HashMap<String, Object>> getMoreThan100000List() {
		return moreThan100000List;
	}

	public void setMoreThan100000List(
			ArrayList<HashMap<String, Object>> moreThan100000List) {
		this.moreThan100000List = moreThan100000List;
	}

	public Integer getMoreThan100000Quota() {
		return moreThan100000Quota;
	}

	public void setMoreThan100000Quota(Integer moreThan100000Quota) {
		this.moreThan100000Quota = moreThan100000Quota;
	}

	public ArrayList<HashMap<String, Object>> getMoreThan500000List() {
		return moreThan500000List;
	}

	public void setMoreThan500000List(
			ArrayList<HashMap<String, Object>> moreThan500000List) {
		this.moreThan500000List = moreThan500000List;
	}

	public Integer getMoreThan500000Quota() {
		return moreThan500000Quota;
	}

	public void setMoreThan500000Quota(Integer moreThan500000Quota) {
		this.moreThan500000Quota = moreThan500000Quota;
	}
	
}
