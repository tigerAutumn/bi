package com.pinting.business.hessian.manage.message;


import java.util.HashMap;
import java.util.List;
import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MWxMenu_WxParentMenusListQuery extends ResMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -7813606948860401315L;
	
	/**
	 * 每页显示的记录数(默认为20条,可以通过set改变其数量)
	 */
	private int numPerPage = 20;
	/**
	 * 当前页码
	 */
	private int pageNum;
	
	private List<HashMap<String, Object>> wxParentMenuList;
	
	private Integer totalRows;

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public List<HashMap<String, Object>> getWxParentMenuList() {
		return wxParentMenuList;
	}

	public void setWxParentMenuList(List<HashMap<String, Object>> wxParentMenuList) {
		this.wxParentMenuList = wxParentMenuList;
	}
}
