package com.pinting.business.hessian.manage.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsSpecialJnl_ListQuery extends ResMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1635728357555163129L;
	
	private List<HashMap<String, Object>> specialJnls;
	private Integer numPerPage;
	private Integer totalRows;
	private Integer pageNum;
	/**总金额**/
	private Double amounts;
	
	public List<HashMap<String, Object>> getSpecialJnls() {
		return specialJnls;
	}
	public void setSpecialJnls(List<HashMap<String, Object>> specialJnls) {
		this.specialJnls = specialJnls;
	}
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
	public Double getAmounts() {
		return amounts;
	}
	public void setAmounts(Double amounts) {
		this.amounts = amounts;
	}

}
