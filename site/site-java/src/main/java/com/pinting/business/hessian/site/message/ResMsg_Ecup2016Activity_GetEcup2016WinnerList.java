package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 助力值排行榜查询 出参
 * @author bianyatian
 * @2016-6-23 上午11:10:11
 */
public class ResMsg_Ecup2016Activity_GetEcup2016WinnerList extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9017613862536345028L;
	
	/*总页数*/
	private Integer totalCount;
	/*起始页*/
	private Integer pageIndex;
	/*总条数*/
	private Integer count;
	/*助力值排行榜列表*/
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
