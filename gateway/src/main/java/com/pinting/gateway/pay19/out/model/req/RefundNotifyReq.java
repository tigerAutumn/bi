package com.pinting.gateway.pay19.out.model.req;

public class RefundNotifyReq extends BaseReq {

	private static final long serialVersionUID = -2883151487288769057L;
	
	private String version;
	
	private String mxResq;
	
	private String status;
	
	private String retCode;
	
	private String verifyString;
	
	private String refOrderId;
	
	private String oriPayOrderId;
	
	private String finishTime;
	
	private String amount;
	
	private String fee;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMxResq() {
		return mxResq;
	}

	public void setMxResq(String mxResq) {
		this.mxResq = mxResq;
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

	public String getVerifyString() {
		return verifyString;
	}

	public void setVerifyString(String verifyString) {
		this.verifyString = verifyString;
	}

	public String getRefOrderId() {
		return refOrderId;
	}

	public void setRefOrderId(String refOrderId) {
		this.refOrderId = refOrderId;
	}

	public String getOriPayOrderId() {
		return oriPayOrderId;
	}

	public void setOriPayOrderId(String oriPayOrderId) {
		this.oriPayOrderId = oriPayOrderId;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	@Override
	public String toString() {
		return "网银退款接口(异步推送数据)： 【version=" + version + ", mxResq=" + mxResq + ", status=" + status + ", retCode="
				+ retCode + ", verifyString=" + verifyString + ", refOrderId=" + refOrderId + ", oriPayOrderId="
				+ oriPayOrderId + ", finishTime=" + finishTime + ", amount=" + amount + ", fee=" + fee + "】";
	}

}
