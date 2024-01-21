package com.pinting.gateway.hessian.message.qb178;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_Qb178_QueryProductList  extends ReqMsg{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 435604714955980273L;
	/**资产方产品编码*/
	private    String 		product_code;
	/**产品创建时间开始*/
	private    Date 		create_time_begin;
	/**产品创建时间结束*/
	private    Date 		create_time_end;
	/**查询资产方可推送产品类型
	默认为：资产标的 0000.0004.0004*/
	private    String 		product_type;
	/**查询资产方可推送产品状态：申购中 buying 已确权 confirmed 发行失败 failure 拟定 prepared*/
	private    String 		product_status;
	/**页数，起始值为1*/
	private		Integer		page_num;
	/**每页记录数*/
	private		Integer 	page_size;
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public Date getCreate_time_begin() {
		return create_time_begin;
	}
	public void setCreate_time_begin(Date create_time_begin) {
		this.create_time_begin = create_time_begin;
	}
	public Date getCreate_time_end() {
		return create_time_end;
	}
	public void setCreate_time_end(Date create_time_end) {
		this.create_time_end = create_time_end;
	}
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
	public String getProduct_status() {
		return product_status;
	}
	public void setProduct_status(String product_status) {
		this.product_status = product_status;
	}
	public Integer getPage_num() {
		return page_num;
	}
	public void setPage_num(Integer page_num) {
		this.page_num = page_num;
	}
	public Integer getPage_size() {
		return page_size;
	}
	public void setPage_size(Integer page_size) {
		this.page_size = page_size;
	}
}
