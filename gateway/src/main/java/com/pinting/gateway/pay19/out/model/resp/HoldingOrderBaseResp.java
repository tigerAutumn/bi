package com.pinting.gateway.pay19.out.model.resp;

public class HoldingOrderBaseResp extends BaseResp {

	private static final long serialVersionUID = 7711500258207064289L;

	private String version;
	
	private String verifyString;
	
	private String status;
	
	private String retCode;
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
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
