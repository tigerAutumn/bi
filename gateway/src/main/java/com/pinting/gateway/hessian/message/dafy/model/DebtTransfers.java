package com.pinting.gateway.hessian.message.dafy.model;

import java.io.Serializable;

public class DebtTransfers implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 6189612177356364278L;
	/*债权转让协议下载地址*/
	private	 String	 debtTransferUrl;

	public String getDebtTransferUrl() {
		return debtTransferUrl;
	}

	public void setDebtTransferUrl(String debtTransferUrl) {
		this.debtTransferUrl = debtTransferUrl;
	}
}
