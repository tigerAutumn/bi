package com.pinting.gateway.hessian.message.hfbank;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 平台充值通知
 * @author SHENGP
 * @date  2017年4月21日 下午3:18:00
 */
public class G2BReqMsg_HFBank_PlatTopUpNotice extends ReqMsg {

	private static final long serialVersionUID = -1445163509855248312L;

	/* 平台编号 */
	@NotEmpty(message="plat_no为空")
    private String plat_no;
    /* 订单号 */
	@NotEmpty(message="order_no为空")
    private String order_no;
    /* 金额 */
	@NotNull(message="amt为空")
    private Double amt;
    /* 1-入账成功  2-入账失败 */
	@NotEmpty(message="code为空")
    private String code;

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

	public Double getAmt() {
		return amt;
	}

	public void setAmt(Double amt) {
		this.amt = amt;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
