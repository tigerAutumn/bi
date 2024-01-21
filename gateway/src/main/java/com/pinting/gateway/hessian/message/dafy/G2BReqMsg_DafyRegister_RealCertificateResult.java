package com.pinting.gateway.hessian.message.dafy;

import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_DafyRegister_RealCertificateResult extends ReqMsg {
	private static final long serialVersionUID = -369992634388444901L;
	private List<String> customerIdList;
	private String result;
	public List<String> getCustomerIdList() {
		return customerIdList;
	}
	public void setCustomerIdList(List<String> customerIdList) {
		this.customerIdList = customerIdList;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

}
