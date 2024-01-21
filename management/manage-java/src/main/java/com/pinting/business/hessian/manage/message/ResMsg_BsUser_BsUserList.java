package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsUser_BsUserList extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2372060988618171182L;
	
	private ArrayList<HashMap<String, Object>> userList;
	private String totalRows;
	private String pageNum;
	private String numPerPage;
	/**渠道名称**/
	private String agentName;
	public ArrayList<HashMap<String, Object>> getUserList() {
		return userList;
	}
	public void setUserList(ArrayList<HashMap<String, Object>> userList) {
		this.userList = userList;
	}
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
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	

}
