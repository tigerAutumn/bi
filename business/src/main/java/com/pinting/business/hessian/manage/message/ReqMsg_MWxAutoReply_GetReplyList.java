package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MWxAutoReply_GetReplyList extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6476462529076319669L;

	private String sName;
	
	private String sKeywords;
	
	private String sTitle;
	
	private String sContent;
	
	private String sReplyType;
	
	private String sMsgType;

	private Date startTime;
	
	private Date endTime;
	
	/**
	 * 每页显示的记录数(默认为20条,可以通过set改变其数量)
	 */
	private int numPerPage = 20;
	/**
	 * 当前页码
	 */
	private int pageNum;
	
	

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getsKeywords() {
		return sKeywords;
	}

	public void setsKeywords(String sKeywords) {
		this.sKeywords = sKeywords;
	}

	public String getsTitle() {
		return sTitle;
	}

	public void setsTitle(String sTitle) {
		this.sTitle = sTitle;
	}

	public String getsContent() {
		return sContent;
	}

	public void setsContent(String sContent) {
		this.sContent = sContent;
	}

	public String getsReplyType() {
		return sReplyType;
	}

	public void setsReplyType(String sReplyType) {
		this.sReplyType = sReplyType;
	}

	public String getsMsgType() {
		return sMsgType;
	}

	public void setsMsgType(String sMsgType) {
		this.sMsgType = sMsgType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

}
