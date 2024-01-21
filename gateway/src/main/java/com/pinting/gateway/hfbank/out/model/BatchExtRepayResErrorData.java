package com.pinting.gateway.hfbank.out.model;

import java.io.Serializable;

/**
 * 
 * @project gateway
 * @title BatchExtRepayResErrorData.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管，借款人批量还款返回--失败明细
 */
public class BatchExtRepayResErrorData  implements  Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2106577285589097490L;
	/**明细编号*/
	private 	String 		detail_no;
	/**错误编码*/
	private 	String 		error_no;
	/**错误描述*/
	private 	String 		error_info;
	public String getDetail_no() {
		return detail_no;
	}
	public void setDetail_no(String detail_no) {
		this.detail_no = detail_no;
	}
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
	
	
}
