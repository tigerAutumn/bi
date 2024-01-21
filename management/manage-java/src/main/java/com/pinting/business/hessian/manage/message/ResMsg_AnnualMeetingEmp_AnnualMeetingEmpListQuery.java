package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 2016公司年会抽奖   出参
 * Created by shh on 2017/01/13 15:08.
 */
public class ResMsg_AnnualMeetingEmp_AnnualMeetingEmpListQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8461572782473456757L;
	
	private ArrayList<HashMap<String,Object>> valueList;
	
	private Integer totalRows;
	
	private Integer pageNum;
	
	private Integer numPerPage;

	public ArrayList<HashMap<String, Object>> getValueList() {
		return valueList;
	}

	public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
		this.valueList = valueList;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}
	
}
