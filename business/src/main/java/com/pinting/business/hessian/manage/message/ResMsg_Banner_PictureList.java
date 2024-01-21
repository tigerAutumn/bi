package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 管理台-网站图片管理
 * @author SHENGP
 * @date  2017年7月14日 下午5:17:35
 */
public class ResMsg_Banner_PictureList extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7446670813662384204L;
	
	private ArrayList<HashMap<String,Object>> valueList;

	private Integer numPerPage;
	
	private Integer totalRows;
	
	private Integer pageNum;
	
	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public ArrayList<HashMap<String, Object>> getValueList() {
		return valueList;
	}

	public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
		this.valueList = valueList;
	}
	
}
