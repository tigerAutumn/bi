package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 还款结果查询
 * @author bianyatian
 * @2016-11-28 下午6:28:35
 */
public class G2BReqMsg_DafyLoan_QueryRepayResult extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4467700841361523934L;

	@NotEmpty(message="orderNo为空")
	private String orderNo; //还款单号
	@NotNull(message="applyDate为空")
	private Date applyDate; //提交放款日期

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
}
