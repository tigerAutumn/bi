package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MAppVersion_AppVersionListQuery extends ResMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -5768221239545912136L;
	
	
	private ArrayList<HashMap<String, Object>> appVersionList;
	
	/**
	 * 每页显示的记录数(默认为20条,可以通过set改变其数量)
	 */
	private int numPerPage = 20;
	/**
	 * 当前页码
	 */
	private int pageNum;
	
	/**
	 * 总数
	 */
	private int totalRows;


	public ArrayList<HashMap<String, Object>> getAppVersionList() {
		return appVersionList;
	}


	public void setAppVersionList(ArrayList<HashMap<String, Object>> appVersionList) {
		this.appVersionList = appVersionList;
	}


	public int getNumPerPage() {
		return numPerPage;
	}


	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}


	public int getPageNum() {
		return pageNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}


	public int getTotalRows() {
		return totalRows;
	}


	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
}
