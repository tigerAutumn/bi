package com.pinting.gateway.hessian.message.zsd;

import java.util.ArrayList;
import java.util.HashMap;


import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_ZsdRepay_LateRepayNotice extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5579442925001189451L;

	/**
     * 代偿单号
     */
    private String orderNo;
    /**
     * 支付单号
     * */
    private String payOrderNo;
    /**
     * 划拨申请时间
     */
    private String applyTime;

    /**
     * 划拨完成时间
     */
    private String finishTime;
    
    /**
     * 代偿总金额
     */
    private String totalAmount;
    
    /**
     * 代偿循环列表
     */
    private  ArrayList<HashMap<String, Object>>  repayments; //账单信息
    
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public ArrayList<HashMap<String, Object>> getRepayments() {
		return repayments;
	}

	public void setRepayments(ArrayList<HashMap<String, Object>> repayments) {
		this.repayments = repayments;
	}
}