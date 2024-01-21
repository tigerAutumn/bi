package com.pinting.gateway.pay19.out.model.req;

public class NewCounterBaseReq extends BaseReq {

	private static final long serialVersionUID = -6581396541868274461L;
	
	private String version = "2.00";
	
	private String merchantId;
	
	private String verifyString;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getVerifyString() {
		return verifyString;
	}

	public void setVerifyString(String verifyString) {
		this.verifyString = verifyString;
	}

}
