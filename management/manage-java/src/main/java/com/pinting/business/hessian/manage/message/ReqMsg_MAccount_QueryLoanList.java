package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 财务功能-借款人提现 入参
 * @author SHENGUOPING
 * @date  2017年9月26日 下午7:06:38
 */
public class ReqMsg_MAccount_QueryLoanList extends ReqMsg {

	private static final long serialVersionUID = 7648315140646514532L;
	
	private int pageNum;

    /** 每页显示的记录数(默认为20条,可以通过set改变其数量)*/
    private int numPerPage = 20;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }
    
}
