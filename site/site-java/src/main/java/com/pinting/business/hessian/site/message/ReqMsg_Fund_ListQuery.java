package com.pinting.business.hessian.site.message;

import java.util.HashMap;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Fund_ListQuery extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5051971522976136198L;
	
	private int pageIndex;
	
	private int pageSize;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
