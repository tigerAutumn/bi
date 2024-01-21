package com.pinting.business.accounting.finance.model;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author SHENGP
 * @date  2017年4月22日 下午5:38:34
 */
public class SysRechargeResultInfo extends ReqMsg {

	private static final long serialVersionUID = 8370809581213194546L;
	/* 平台编号 */
    private String plat_no;
    /* 订单号 */
    private String order_no;
    
    /* 金额 */
    private Double amount;
    
    /* 1-入账成功  2-入账失败 */
    private String code;

    private String status;
    
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}
