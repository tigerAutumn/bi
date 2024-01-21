package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsUser_FeedbacksQuery extends ResMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8695122713887863320L;
	//id,userId,userName,userPhone,info
	private ArrayList<HashMap<String, Object>> feedbacks;
	private String totalRows;
	private String pageNum;
	private String numPerPage;
	public ArrayList<HashMap<String, Object>> getFeedbacks() {
		return feedbacks;
	}
	public void setFeedbacks(ArrayList<HashMap<String, Object>> feedbacks) {
		this.feedbacks = feedbacks;
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
	
}
