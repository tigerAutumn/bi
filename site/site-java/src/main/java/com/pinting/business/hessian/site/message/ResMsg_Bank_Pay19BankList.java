package com.pinting.business.hessian.site.message;

import java.util.List;
import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 查询支持快捷支付的优先级最高银行，出参
 * @author shencheng
 * @2015-11-16 下午7:48:46
 */
public class ResMsg_Bank_Pay19BankList extends ResMsg {

	private static final long serialVersionUID = -6490335945607255694L;
	/*银行列表*/
	private List<Map<String, Object>> bankList;

	public List<Map<String, Object>> getBankList() {
		return bankList;
	}

	public void setBankList(List<Map<String, Object>> bankList) {
		this.bankList = bankList;
	}
	
}
