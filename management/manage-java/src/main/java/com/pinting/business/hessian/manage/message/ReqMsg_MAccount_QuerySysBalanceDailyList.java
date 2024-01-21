package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 每日日终账务查询
 * @author SHENGUOPING
 * @date  2017年11月22日 下午10:14:44
 */
public class ReqMsg_MAccount_QuerySysBalanceDailyList extends ReqMsg {

	private static final long serialVersionUID = 9096097516635226755L;

	private Date snapBeginTime; // 起始时间

	private Date snapEndTime; // 结束时间
	
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

	public Date getSnapBeginTime() {
		return snapBeginTime;
	}

	public void setSnapBeginTime(Date snapBeginTime) {
		this.snapBeginTime = snapBeginTime;
	}

	public Date getSnapEndTime() {
		return snapEndTime;
	}

	public void setSnapEndTime(Date snapEndTime) {
		this.snapEndTime = snapEndTime;
	}
	
}
