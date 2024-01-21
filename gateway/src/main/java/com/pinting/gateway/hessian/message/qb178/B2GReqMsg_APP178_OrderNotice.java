package com.pinting.gateway.hessian.message.qb178;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_APP178_OrderNotice extends ReqMsg {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1181775756209407964L;

	/* 订单编号 */
    private String delegate_code;

    /* 产品编码 */
    private String product_code;

    /* 产品名称 */
    private String product_name;

    /* 会员账号 */
    private String user_account;

    /* 订单金额（金额单位：分） */
    private Long order_balance;

    /* 订单类型 认购（applyTrade）*/
    private String delegate_type;

    /* 订单状态：委托中(delegating)，成功(success)，失败(failed) */
    private String delegate_status;

    /* 下单时间 yyyyMMddHHmmss */
    private Date order_time;
    
	/* 合作商流水号，全局唯一，建议时间戳 */
	private String serialNo;

	/* 子渠道标识 */
	private String subChannel;

	public String getDelegate_code() {
		return delegate_code;
	}

	public void setDelegate_code(String delegate_code) {
		this.delegate_code = delegate_code;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getUser_account() {
		return user_account;
	}

	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}

	
	public Long getOrder_balance() {
		return order_balance;
	}

	public void setOrder_balance(Long order_balance) {
		this.order_balance = order_balance;
	}

	public String getDelegate_type() {
		return delegate_type;
	}

	public void setDelegate_type(String delegate_type) {
		this.delegate_type = delegate_type;
	}

	public String getDelegate_status() {
		return delegate_status;
	}

	public void setDelegate_status(String delegate_status) {
		this.delegate_status = delegate_status;
	}

	public Date getOrder_time() {
		return order_time;
	}

	public void setOrder_time(Date order_time) {
		this.order_time = order_time;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getSubChannel() {
		return subChannel;
	}

	public void setSubChannel(String subChannel) {
		this.subChannel = subChannel;
	}
	
	
}
