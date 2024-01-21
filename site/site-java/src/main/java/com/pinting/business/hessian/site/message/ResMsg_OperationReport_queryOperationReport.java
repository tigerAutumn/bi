package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 查询运营报告列表信息 出参
 * @author shiyulong
 * @2016-7-6 下午5:21:44
 */
public class ResMsg_OperationReport_queryOperationReport extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 666443281311007321L;
	/*运营报告列表*/
	private ArrayList<HashMap<String, Object>> reportList;

	private Integer count;

	private int currYear;
	
	public ArrayList<HashMap<String, Object>> getReportList() {
		return reportList;
	}

	public void setReportList(ArrayList<HashMap<String, Object>> reportList) {
		this.reportList = reportList;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public int getCurrYear() {
		return currYear;
	}

	public void setCurrYear(int currYear) {
		this.currYear = currYear;
	}

}
