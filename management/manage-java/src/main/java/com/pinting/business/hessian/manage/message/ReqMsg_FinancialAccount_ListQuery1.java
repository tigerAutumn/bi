package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_FinancialAccount_ListQuery1 extends ReqMsg {
    /**
	 * 
	 */
	private static final long serialVersionUID = 824369512985591072L;

    private String time;
    
    private Integer pageNum = 0;
    
    private Integer numPerPage = 20;
    
    private Integer start = 1;
    
    public Integer getStart() {
        start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
    
    public Integer getPageNum() {
        if (pageNum < 1) 
            this.pageNum = 1;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
