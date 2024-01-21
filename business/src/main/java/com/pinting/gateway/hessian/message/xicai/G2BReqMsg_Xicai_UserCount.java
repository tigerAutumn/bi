package com.pinting.gateway.hessian.message.xicai;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_Xicai_UserCount extends ReqMsg {

	private static final long serialVersionUID = -2345249276554422522L;

	private String startdate;
	
	private String enddate;
	
	private Integer page;
	
	private Integer pagesize;
	
	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}
}
