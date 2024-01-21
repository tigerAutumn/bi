package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 据userId查询我的投资记录 出参
 * @author shiyulong
 * @2015-12-21 下午12:43:43
 */
public class ResMsg_Invest_InvestListQuery extends ResMsg{


	/**
	 * 
	 */
	private static final long serialVersionUID = -4532752127955445988L;
	/*我的投资列表*/
	private ArrayList<HashMap<String,Object>> valueList;

	private int total;  //总页数
	
	private int pageIndex; //当前页码
	
	private Integer processingNum;//当前用户处理中订单的数量

	
	public ArrayList<HashMap<String, Object>> getValueList() {
		return valueList;
	}

	public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
		this.valueList = valueList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getProcessingNum() {
		return processingNum;
	}

	public void setProcessingNum(Integer processingNum) {
		this.processingNum = processingNum;
	}
	
	
	
}
