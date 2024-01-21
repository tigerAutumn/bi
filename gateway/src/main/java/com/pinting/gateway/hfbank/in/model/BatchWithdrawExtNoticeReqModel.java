package com.pinting.gateway.hfbank.in.model;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 提现通知请求信息
 */
public class BatchWithdrawExtNoticeReqModel extends BaseReqModel {

    /* 平台客户号 */
    private String platcust;
    /* 订单金额 */
    private String order_amt;
    /* 订单日期 */
    private String trans_date;
    /* 订单时间 */
    private String trans_time;
    /* 提现订单号 */
    private String pay_order_no;
    /* 支付完成日期 YYYYMMDD */
    private String pay_finish_date;
    /* 支付完成时间 HHmmss*/
    private String pay_finish_time;
    /* 交易状态 0-申请成功 1-提现成功 2-失败 */
    private String order_status;
    /* 支付金额 */
    private String pay_amt;
    /* 失败原因 */
    private String error_info;
    /* 失败编码 */
    private String error_no;
    /* 支付通道流水号 */
    private String host_req_serial_no;
    /* 行内支付才有（03小额 06超网 09大额） */
    private String pay_path;
    
    private String advance_amt;
    
    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getOrder_amt() {
        return order_amt;
    }

    public void setOrder_amt(String order_amt) {
        this.order_amt = order_amt;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public String getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(String trans_time) {
        this.trans_time = trans_time;
    }

    public String getPay_order_no() {
        return pay_order_no;
    }

    public void setPay_order_no(String pay_order_no) {
        this.pay_order_no = pay_order_no;
    }

    public String getPay_finish_date() {
        return pay_finish_date;
    }

    public void setPay_finish_date(String pay_finish_date) {
        this.pay_finish_date = pay_finish_date;
    }

    public String getPay_finish_time() {
        return pay_finish_time;
    }

    public void setPay_finish_time(String pay_finish_time) {
        this.pay_finish_time = pay_finish_time;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getPay_amt() {
        return pay_amt;
    }

    public void setPay_amt(String pay_amt) {
        this.pay_amt = pay_amt;
    }

    public String getError_info() {
        return error_info;
    }

    public void setError_info(String error_info) {
        this.error_info = error_info;
    }

    public String getError_no() {
        return error_no;
    }

    public void setError_no(String error_no) {
        this.error_no = error_no;
    }

	public String getHost_req_serial_no() {
		return host_req_serial_no;
	}

	public void setHost_req_serial_no(String host_req_serial_no) {
		this.host_req_serial_no = host_req_serial_no;
	}

	public String getPay_path() {
		return pay_path;
	}

	public void setPay_path(String pay_path) {
		this.pay_path = pay_path;
	}

	public String getAdvance_amt() {
		return advance_amt;
	}

	public void setAdvance_amt(String advance_amt) {
		this.advance_amt = advance_amt;
	}
    
}
