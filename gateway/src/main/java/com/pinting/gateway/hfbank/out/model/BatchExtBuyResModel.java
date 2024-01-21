package com.pinting.gateway.hfbank.out.model;
/**
 * 
 * @project gateway
 * @title BatchExtResModel.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管，批量投标请求
 */
public class BatchExtBuyResModel extends BaseResModel {
	/**平台编号(批量订单受理成功时，则必填)*/
	private 	String 		plat_no;
	/**订单编号(批量订单受理成功时，则必填)*/
	private 	String 		order_no;
	/**处理完成时间(批量订单受理成功时，则必填)*/
	private 	String 		finish_datetime;
	/**订单状态(批量订单受理成功时，则必填)*/
	private 	String 		order_status;
	/**订单处理信息(批量订单受理成功时，则必填)*/
	private 	String 		order_info;
	/**订单请求数量(批量订单受理成功时，则必填)*/
	private 	String 		total_num;
	/**成功数量(批量订单受理成功时，则必填)*/
	private 	String 		success_num;
	/**成功信息（有成功处理的数据时，则为必填参数；其中detail_no、platcust、prod_id、trans_amt为必填信息）*/
	private 	String 		success_data;
	/**失败信息（有失败处理的数据时，则为必填参数；其中detail_no、error_no、error_info为必填信息）*/
	private 	String 		error_data;
	public String getPlat_no() {
		return plat_no;
	}
	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getFinish_datetime() {
		return finish_datetime;
	}
	public void setFinish_datetime(String finish_datetime) {
		this.finish_datetime = finish_datetime;
	}

	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getOrder_info() {
		return order_info;
	}
	public void setOrder_info(String order_info) {
		this.order_info = order_info;
	}
	public String getTotal_num() {
		return total_num;
	}
	public void setTotal_num(String total_num) {
		this.total_num = total_num;
	}
	public String getSuccess_num() {
		return success_num;
	}
	public void setSuccess_num(String success_num) {
		this.success_num = success_num;
	}
	public String getSuccess_data() {
		return success_data;
	}
	public void setSuccess_data(String success_data) {
		this.success_data = success_data;
	}
	public String getError_data() {
		return error_data;
	}
	public void setError_data(String error_data) {
		this.error_data = error_data;
	}
	
	
}
