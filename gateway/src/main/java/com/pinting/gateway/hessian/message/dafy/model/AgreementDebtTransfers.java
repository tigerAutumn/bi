package com.pinting.gateway.hessian.message.dafy.model;

import java.io.Serializable;

/**
 * 协议下载通知-二级循环对象
 * @author bianyatian
 * @2016-12-15 上午10:43:16
 */
public class AgreementDebtTransfers implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9082253418192688094L;
	
	private String debtTransferUrl;	//债权转让协议下载地址

	public String getDebtTransferUrl() {
		return debtTransferUrl;
	}

	public void setDebtTransferUrl(String debtTransferUrl) {
		this.debtTransferUrl = debtTransferUrl;
	}
}
