package com.pinting.gateway.reapal.out.model.req;

public class SubmitPayReq extends ReapalBaseOutReq {

	private String merchant_id; //商户在融宝的用户ID
	
	private String order_no; //商户订单号（确认在合伙伙伴系统中唯一），譬如：HJKM2011051189234
	
	private String CVV2; //信用卡必传、储蓄卡可选
	
	private String check_code; //短信验证码
	
	private String version; //版本控制默认3.0
	
	private String sign_type; //目前仅支持MD5，不参与验签
	
	private String sign; //按照sign_type参数指定的签名算法对待签名数据进行签名。目前仅支持MD5.详见数字签名

	public String getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getCVV2() {
		return CVV2;
	}

	public void setCVV2(String cVV2) {
		CVV2 = cVV2;
	}

	public String getCheck_code() {
		return check_code;
	}

	public void setCheck_code(String check_code) {
		this.check_code = check_code;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
