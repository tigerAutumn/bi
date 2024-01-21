package com.pinting.gateway.zsd.out.model;

/**
 * 还款通知
 * @author SHENGUOPING
 * @date  2017年8月30日 下午8:05:28
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
     * 资金通道  
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
