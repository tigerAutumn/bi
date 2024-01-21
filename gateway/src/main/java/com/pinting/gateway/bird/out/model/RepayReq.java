package com.pinting.gateway.bird.out.model;


/**
 * Created by 剑钊 on 2016/8/10.
 * 还款下单通知
 */
public class RepayReq extends BaseReqModel {

    /**
     * 还款订单号
     */
    private String orderNo;


    /**
     * 借款编号
     */
    private String loanId;

    /**
     * 资金通道  目前为19Pay
     */
    private String channel;

    /**
     * 还款结果编码
     */
    private String repayResultCode;

    /**
     * 还款结果信息
     */
    private String repayResultMsg;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

	public String getRepayResultCode() {
		return repayResultCode;
	}

	public void setRepayResultCode(String repayResultCode) {
		this.repayResultCode = repayResultCode;
	}

	public String getRepayResultMsg() {
		return repayResultMsg;
	}

	public void setRepayResultMsg(String repayResultMsg) {
		this.repayResultMsg = repayResultMsg;
	}


}
