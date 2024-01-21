package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MAppNotice_AppNoticeListQuery extends ResMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -3944473771131305243L;
	
	/**
	 * 每页显示的记录数(默认为20条,可以通过set改变其数量)
	 */
	private int numPerPage = 20;
	/**
	 * 当前页码
	 */
	private int pageNum;
	
	private Integer releasePart;

    private String name;

    private String title;

    private Integer isSend;
    
    private ArrayList<HashMap<String, Object>> appNoticeList;
    
    /**
	 * 总数
	 */
	private int totalRows;

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

	public Integer getReleasePart() {
		return releasePart;
	}

	public void setReleasePart(Integer releasePart) {
		this.releasePart = releasePart;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getIsSend() {
		return isSend;
	}

	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}

	public ArrayList<HashMap<String, Object>> getAppNoticeList() {
		return appNoticeList;
	}

	public void setAppNoticeList(ArrayList<HashMap<String, Object>> appNoticeList) {
		this.appNoticeList = appNoticeList;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
}
