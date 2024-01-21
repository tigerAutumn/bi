package com.pinting.gateway.hessian.message.dafy;

import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_DafyBankCard_CardBindResult extends ReqMsg {

	private static final long serialVersionUID = -6138294839805185666L;
	private List<String> customerIdList;
	private List<String> resultList;
	private List<String> resultMsgList;
	public List<String> getCustomerIdList() {
		return customerIdList;
	}
	public void setCustomerIdList(List<String> customerIdList) {
		this.customerIdList = customerIdList;
	}
	public List<String> getResultList() {
		return resultList;
	}
	public void setResultList(List<String> resultList) {
		this.resultList = resultList;
	}
	public List<String> getResultMsgList() {
		return resultMsgList;
	}
	public void setResultMsgList(List<String> resultMsgList) {
		this.resultMsgList = resultMsgList;
	}

}
