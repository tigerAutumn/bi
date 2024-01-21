package com.pinting.gateway.hessian.message.dafy;

import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.dafy.model.Data;
import com.pinting.gateway.hessian.message.dafy.model.LoanRelationData;

public class B2GResMsg_Customer_GetLoanRelationNew extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4369295051489834833L;
	private List<Data> data;
	private String count; //总条数
	private String pageIndex; //当前第几页
	private String pageNum; //每页条数
	public List<Data> getData() {
		return data;
	}
	public void setData(List<Data> data) {
		this.data = data;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
}
