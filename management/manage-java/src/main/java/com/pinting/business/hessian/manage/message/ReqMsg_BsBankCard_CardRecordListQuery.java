package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsBankCard_CardRecordListQuery extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5467068557306616852L;
	private String pageNum;
	private String numPerPage;
	
	private String bankCardNo;//银行卡号
	private Integer status;//订单状态 1-创建\r\n 2-支付处理中\r\n 3-支付成功\r\n 4-支付失败

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(String numPerPage) {
		this.numPerPage = numPerPage;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
