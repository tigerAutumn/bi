package com.pinting.business.hessian.site.message;

import java.util.List;
import java.util.Map;

import com.pinting.business.model.Bs19payBank;
import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * 查询支持网银支付的19付银行 出参
 * @author shiyulong
 * @2015-12-15 上午9:56:36
 */
public class ResMsg_Bank_Pay19NetBankList extends ResMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1612794409941324265L;
	/*银行列表*/
	private List<Map<String, Object>> bankList;
	public List<Map<String, Object>> getBankList() {
		return bankList;
	}
	public void setBankList(List<Map<String, Object>> bankList) {
		this.bankList = bankList;
	}
	
}
