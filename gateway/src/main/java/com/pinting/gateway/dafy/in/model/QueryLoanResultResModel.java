package com.pinting.gateway.dafy.in.model;

import java.util.Date;
import java.util.List;

import com.pinting.gateway.hessian.message.dafy.model.Lenders;

/**
 * 自主放款-放款结果查询
 * @author Dragon & cat
 * @date 2016-11-28
 */
public class QueryLoanResultResModel extends BaseResModel {
	/*借款单号*/
	private			String		orderNo;
	/*支付单号*/
	private			String		bgwOrderNo;
	/*借款协议编号*/
	private			String		agreementNo;
	/*借款服务费率*/
	private			Integer		loanServiceRate;
	/*借款编号*/
	private			String		loanId;
	/*支付渠道*/
	private			String		channel;
	/*支付结果码*/
	private			String		resultCode;
	/*支付结果信息*/
	private			String		resultMsg;
	/*放款成功时间*/
	private			Date		finishTime;
	/*出借人信息*/
	private			List<Lenders> 	lenders;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBgwOrderNo() {
		return bgwOrderNo;
	}

	public void setBgwOrderNo(String bgwOrderNo) {
		this.bgwOrderNo = bgwOrderNo;
	}

	public String getAgreementNo() {
		return agreementNo;
	}

	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	public Integer getLoanServiceRate() {
		return loanServiceRate;
	}

	public void setLoanServiceRate(Integer loanServiceRate) {
		this.loanServiceRate = loanServiceRate;
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

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public List<Lenders> getLenders() {
		return lenders;
	}

	public void setLenders(List<Lenders> lenders) {
		this.lenders = lenders;
	}
	
	
}
