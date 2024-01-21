package com.pinting.gateway.hessian.message.zsd;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 借款结果查询服务
 * @author SHENGUOPING
 * @date  2017年8月30日 下午4:34:53
 */
public class G2BResMsg_ZsdLoanApply_QueryLoan extends ResMsg {

	private static final long serialVersionUID = -2083424416491286360L;

	/**
	 * 借款编号
	 */
	private String loanId;

	/**
	 * 资金通道
	 */
	private String channel;

	/**
	 * 借款结果编码
	 */
	private String loanResultCode;

	/**
	 * 借款结果信息
	 */
	private String loanResultMsg;

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

	public String getLoanResultCode() {
		return loanResultCode;
	}

	public void setLoanResultCode(String loanResultCode) {
		this.loanResultCode = loanResultCode;
	}

	public String getLoanResultMsg() {
		return loanResultMsg;
	}

	public void setLoanResultMsg(String loanResultMsg) {
		this.loanResultMsg = loanResultMsg;
	}
}
