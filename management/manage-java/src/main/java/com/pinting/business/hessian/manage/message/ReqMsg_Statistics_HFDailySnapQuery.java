package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 恒丰日终账务查询
 * @author SHENGP
 * @date  2017年6月14日 下午5:45:47
 */
public class ReqMsg_Statistics_HFDailySnapQuery extends ReqMsg {

	private static final long serialVersionUID = -8200912236573828594L;
	
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
