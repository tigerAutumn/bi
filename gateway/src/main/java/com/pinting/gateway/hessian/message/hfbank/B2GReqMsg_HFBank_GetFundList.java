package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ReqMsg;

import java.util.Date;

/**
 * 资金变动明细查询请求信息
 * Created by yed on 2017/5/25.
 */
public class B2GReqMsg_HFBank_GetFundList extends ReqMsg {


    /**
	 * 
	 */
	private static final long serialVersionUID = 3222658658559394489L;
	/* 订单号 */
	private String order_no;
	/* 平台客户编号 */
    private String platcust;
    /* 查询起始时间 */
    private Date start_date;
    /* 查询结束时间 */
    private Date end_date;
    /* 交易名称 */
    private String trans_name;
    /* 分页大小 */
    private String pagesize;
    /* 页码 */
    private String pagenum;
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getPlatcust() {
		return platcust;
	}
	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public String getTrans_name() {
		return trans_name;
	}
	public void setTrans_name(String trans_name) {
		this.trans_name = trans_name;
	}
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public String getPagenum() {
		return pagenum;
	}
	public void setPagenum(String pagenum) {
		this.pagenum = pagenum;
	}
}