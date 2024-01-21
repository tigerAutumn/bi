package com.pinting.gateway.hessian.message.xicai;

import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.xicai.model.InvestInfo;

public class G2BResMsg_Xicai_InvestCount extends ResMsg {

	private static final long serialVersionUID = 5678137915432681247L;

	private Integer code;
	
	private Integer total;
	
	private List<InvestInfo> list;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<InvestInfo> getList() {
		return list;
	}

	public void setList(List<InvestInfo> list) {
		this.list = list;
	}
	
}
