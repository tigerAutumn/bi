package com.pinting.gateway.hessian.message.loan;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 还款结果查询
 * @author bianyatian
 * @2016-9-9 下午2:19:48
 */
public class G2BResMsg_Repay_QueryRepayResult extends ResMsg {

	private String loanId;	//借款编号
	private String channel;	//资金通道
	private String repayResultCode;	//还款结果编码
	private String repayResultMsg;	//还款结果信息
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
