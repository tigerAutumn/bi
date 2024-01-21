package com.pinting.gateway.pay19.out.model.req;

public class HoldingOrderBaseReq extends BaseReq {

	private static final long serialVersionUID = 1488790010384644111L;

	private String version = "3.00";
	
	private String verifyString;
	
	private String merchantId;
	
	private String mxUserId;
	
	private String mxOrderId;
	
	private String mxOrderDate;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVerifyString() {
		return verifyString;
	}

	public void setVerifyString(String verifyString) {
		this.verifyString = verifyString;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMxUserId() {
		return mxUserId;
	}

	public void setMxUserId(String mxUserId) {
		this.mxUserId = mxUserId;
	}

	public String getMxOrderId() {
		return mxOrderId;
	}

	public void setMxOrderId(String mxOrderId) {
		this.mxOrderId = mxOrderId;
	}

	public String getMxOrderDate() {
		return mxOrderDate;
	}

	public void setMxOrderDate(String mxOrderDate) {
		this.mxOrderDate = mxOrderDate;
	}
}
