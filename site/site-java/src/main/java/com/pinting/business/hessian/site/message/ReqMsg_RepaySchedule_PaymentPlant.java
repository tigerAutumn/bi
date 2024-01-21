package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by houjf on 2017/3/30.
 */
public class ReqMsg_RepaySchedule_PaymentPlant extends ReqMsg {

    private static final long serialVersionUID = -5893934640956320909L;

    /*用户ID*/
    private Integer userId;
	private Integer pageIndex;
	private Integer pageSize;

    /*往期回款查询时间*/
    private String dateTime;

    /*请求状态*/
    private String status;

    /*回款详情查询时间*/
    private String detailsTime;

    private Integer page;
    
    private String reqStatus;
    
    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getDetailsTime() {
        return detailsTime;
    }

    public void setDetailsTime(String detailsTime) {
        this.detailsTime = detailsTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

	public String getReqStatus() {
		return reqStatus;
	}

	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}
    
    
}
