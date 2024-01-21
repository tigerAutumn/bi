package com.pinting.gateway.hessian.message.hfbank;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/4/3
 * Description:
 */
public class G2BReqMsg_HFBank_UserBatchWithdrawNotice extends ReqMsg {
    private static final long serialVersionUID = -5893277304506691321L;

    /* 平台客户号 */
    @NotEmpty(message="platcust为空")
    private String platcust;
    /* 订单金额 */
    @NotNull(message="order_amt为空")
    private Double order_amt;
    /* 订单日期 */
    @NotEmpty(message="trans_date为空")
    private String trans_date;
    /* 订单时间 */
    @NotEmpty(message="trans_time为空")
    private String trans_time;
    /* 提现订单号 */
    @NotEmpty(message="pay_order_no为空")
    private String pay_order_no;
    /* 支付完成日期 YYYYMMDD */
    @NotEmpty(message="pay_finish_date为空")
    private String pay_finish_date;
    /* 支付完成时间 HHmmss*/
    @NotEmpty(message="pay_finish_time为空")
    private String pay_finish_time;
    /* 交易状态 0-申请成功 1-提现成功 2-失败 */
    @NotEmpty(message="order_status为空")
    private String order_status;
    /* 支付金额 */
    @NotNull(message="pay_amt为空")
    private Double pay_amt;
    /* 失败原因 */
    private String error_info;
    /* 失败编码 */
    private String error_no;
    /* 平台编号 */
    @NotEmpty(message="plat_no为空")
    private String plat_no;
    /* 订单号 */
    @NotEmpty(message="order_no为空")
    private String order_no;
    /* 支付通道流水号 */
    private String host_req_serial_no;
    /* 行内支付才有（03小额 06超网 09大额） */
    private String pay_path;
    
    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public Double getOrder_amt() {
        return order_amt;
    }

    public void setOrder_amt(Double order_amt) {
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

    public Double getPay_amt() {
        return pay_amt;
    }

    public void setPay_amt(Double pay_amt) {
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
    
}
