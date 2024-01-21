package com.pinting.gateway.qb178.out.model;

/**
 * @author bianyatian
 * @2017-7-28 下午3:06:42
 */
public class BaseResModel {
	private String error_no; //处理结果编码，返回 0000 时表示成功，其他则为错误代码
	private String error_info; //错误信息描述  
	private String cert_sign; //签名串  
	public String getError_no() {
		return error_no;
	}
	public void setError_no(String error_no) {
		this.error_no = error_no;
	}
	public String getError_info() {
		return error_info;
	}
	public void setError_info(String error_info) {
		this.error_info = error_info;
	}
	public String getCert_sign() {
		return cert_sign;
	}
	public void setCert_sign(String cert_sign) {
		this.cert_sign = cert_sign;
	}
	
	
}
