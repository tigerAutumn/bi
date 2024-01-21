package com.pinting.business.hessian.manage.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MWxAutoReply_GetReplyList extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 840786778936389310L;

	private List<HashMap<String,Object>> replyList;
	
	private int totalRows;

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public List<HashMap<String,Object>> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<HashMap<String,Object>> replyList) {
		this.replyList = replyList;
	}
}
