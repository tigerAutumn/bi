package com.pinting.business.model.vo;

import java.util.List;

public class AddressInfos {
	
	private Integer errNum;
	
	private String retMsg;
	
	private AddressDetailInfo showapi_res_body;
	
	private AddressDetailInfo retData;

	public Integer getErrNum() {
		return errNum;
	}

	public void setErrNum(Integer errNum) {
		this.errNum = errNum;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public AddressDetailInfo getRetData() {
		return retData;
	}

	public void setRetData(AddressDetailInfo retData) {
		this.retData = retData;
	}

	public AddressDetailInfo getShowapi_res_body() {
		return showapi_res_body;
	}

	public void setShowapi_res_body(AddressDetailInfo showapi_res_body) {
		this.showapi_res_body = showapi_res_body;
	}

}
