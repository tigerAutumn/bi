package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 币港湾日终账务查询
 * @author SHENGP
 * @date  2017年6月15日 下午2:29:09
 */
public class ReqMsg_Statistics_BGWDailySnapQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 757707146851471412L;
	
	private String propertySymbol;

	private String startDate;
	
    private String endDate;
    
    private Integer pageNum = 1;
    
    private Integer numPerPage = 50;
    
    private Integer start = 1;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	public Integer getStart() {
		start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public String getPropertySymbol() {
		return propertySymbol;
	}

	public void setPropertySymbol(String propertySymbol) {
		this.propertySymbol = propertySymbol;
	}
	
}
