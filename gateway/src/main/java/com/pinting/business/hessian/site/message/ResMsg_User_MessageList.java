package com.pinting.business.hessian.site.message;

import java.util.List;
import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_User_MessageList extends ResMsg {

	private static final long serialVersionUID = -1569825203139742408L;
	
	private Integer totalPage;

	private List<Map<String,Object>> dataLst;

	public List<Map<String, Object>> getDataLst() {
		return dataLst;
	}

	public void setDataLst(List<Map<String, Object>> dataLst) {
		this.dataLst = dataLst;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
}
