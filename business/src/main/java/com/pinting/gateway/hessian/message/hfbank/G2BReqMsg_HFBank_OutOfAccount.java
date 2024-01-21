package com.pinting.gateway.hessian.message.hfbank;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_HFBank_OutOfAccount extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8061341301417278897L;
	
	
	/**出账金额*/
	@NotNull(message="out_amt为空")
	private  	Double 		out_amt;
	/**融资人平台客户编号*/
	@NotEmpty(message="platucst为空")
	private  	String 		platucst;
	/**开户行*/
	@NotEmpty(message="open_branch为空")
	private  	String 		open_branch;
	/**收款人银行卡号*/
	@NotEmpty(message="withdraw_account为空")
	private  	String 		withdraw_account;
	/**收款人姓名*/
	@NotEmpty(message="payee_name为空")
	private  	String 		payee_name;
	/**支付完成日期*/
	@NotNull(message="pay_finish_date为空")
	private  	Date 		pay_finish_date;
	/**支付完成时间*/
	@NotNull(message="pay_finish_time为空")
	private  	Date 		pay_finish_time;
	/**交易状态 1-出账成功 2-失败*/
	@NotEmpty(message="order_status为空")
	private  	String 		order_status;
	/**失败原因*/
	private  	String 		error_info;
	/**失败编码*/
	private  	String 		error_no;
	/** 支付通道流水号 */
	private     String  	host_req_serial_no;
	/** 行内支付才有（03小额 06超网 09大额） */
	private     String  	pay_path;
	
    /* 平台编号 */
    @NotEmpty(message="plat_no为空")
    private String plat_no;
    /* 订单号 */
    @NotEmpty(message="order_no为空")
    private String order_no;
    
    
    
	
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
	public Double getOut_amt() {
		return out_amt;
	}
	public void setOut_amt(Double out_amt) {
		this.out_amt = out_amt;
	}
	public String getPlatucst() {
		return platucst;
	}
	public void setPlatucst(String platucst) {
		this.platucst = platucst;
	}
	public String getOpen_branch() {
		return open_branch;
	}
	public void setOpen_branch(String open_branch) {
		this.open_branch = open_branch;
	}
	public String getWithdraw_account() {
		return withdraw_account;
	}
	public void setWithdraw_account(String withdraw_account) {
		this.withdraw_account = withdraw_account;
	}
	public String getPayee_name() {
		return payee_name;
	}
	public void setPayee_name(String payee_name) {
		this.payee_name = payee_name;
	}
	public Date getPay_finish_date() {
		return pay_finish_date;
	}
	public void setPay_finish_date(Date pay_finish_date) {
		this.pay_finish_date = pay_finish_date;
	}
	public Date getPay_finish_time() {
		return pay_finish_time;
	}
	public void setPay_finish_time(Date pay_finish_time) {
		this.pay_finish_time = pay_finish_time;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
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

}
