package com.pinting.gateway.hfbank.out.model;

/**
 * Author	:	yed
 * Date	:	2017/5/25
 * Description: 资金变动明细查询
 */
public class GetFundListReqModel extends BaseReqModel {

    /* 平台客户编号 */
    private String platcust;
    /* 查询起始日期 */
    private String start_date;
    /* 查询结束日期 */
    private String end_date;
    /* 交易名称 */
    private String trans_name;
    /* 分页大小 */
    private String pagesize;
    /* 页码：从1开始  */
    private String pagenum;
	public String getPlatcust() {
		return platcust;
	}
	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
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