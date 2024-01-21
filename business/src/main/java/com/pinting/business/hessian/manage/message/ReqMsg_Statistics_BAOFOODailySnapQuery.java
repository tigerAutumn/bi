package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 宝付日终账务查询
 * @author SHENGP
 * @date  2017年6月15日 下午2:30:28
 */
public class ReqMsg_Statistics_BAOFOODailySnapQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7340868177707424422L;
	
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
