package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MSystem_SysConfigsQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8945799617278752983L;
	//configKey,configValue,name,note
	private ArrayList<HashMap<String, Object>> configs;
	private String totalRows;
	private String pageNum;
	private String numPerPage;
	public String getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(String totalRows) {
		this.totalRows = totalRows;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(String numPerPage) {
		this.numPerPage = numPerPage;
	}
	public ArrayList<HashMap<String, Object>> getConfigs() {
		return configs;
	}
	public void setConfigs(ArrayList<HashMap<String, Object>> configs) {
		this.configs = configs;
	}

}
