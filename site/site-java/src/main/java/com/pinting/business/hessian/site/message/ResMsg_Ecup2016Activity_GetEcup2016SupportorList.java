package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 好友助力列表查询 入参
 * @author bianyatian
 * @2016-6-23 上午11:10:55
 */
public class ResMsg_Ecup2016Activity_GetEcup2016SupportorList extends ResMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6505096501981142296L;
	/*总页数*/
	private Integer totalCount;
	/*当前页*/
	private Integer pageIndex;
	/*总条数*/
	private Integer count;
	/*好友助力列表*/
	private ArrayList<HashMap<String, Object>> list;

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public ArrayList<HashMap<String, Object>> getList() {
		return list;
	}

	public void setList(ArrayList<HashMap<String, Object>> list) {
		this.list = list;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
