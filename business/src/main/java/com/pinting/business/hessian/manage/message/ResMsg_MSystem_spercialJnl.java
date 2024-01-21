package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MSystem_spercialJnl extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2966449889123340985L;
	/**
	 * 
	 */
	
	private Integer id;
	private Integer mUserId;
	private Integer userId;
	private String type;
	private String detail;
	private Date opTime;
	private String ip;
	private ArrayList<HashMap<String, Object>> MSpercialList;
	
	
	public ArrayList<HashMap<String, Object>> getMSpercialList() {
		return MSpercialList;
	}
	public void setMSpercialList(ArrayList<HashMap<String, Object>> mSpercialList) {
		MSpercialList = mSpercialList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getmUserId() {
		return mUserId;
	}
	public void setmUserId(Integer mUserId) {
		this.mUserId = mUserId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Date getOpTime() {
		return opTime;
	}
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 每页显示的记录数(默认为20条,可以通过set改变其数量)
	 */
	private int numPerPage = 20;

	/**
	 * 总记录数
	 */
	private int totalRows;

	/**
	 * 当前页码
	 */
	private int pageNum;
	/**
	 * 当前开始下标
	 */
	private int start=1;

	/**
	 * 当前结束下标
	 */
	private int end;

	/**
	 * 排列方式
	 */
	private String sord;

	/**
	 * 总页数
	 */
	private int totalPages;

	/**
	 * 返回当前页面数
	 * 
	 * @return
	 */
	public int getPageNum() {

		if (pageNum < 1) {

			this.pageNum = 1;
		}

		return pageNum;
	}

	/**
	 * 返回总页数
	 * 
	 * @return
	 */
	public int getTotalPages() {

		totalPages = (int) Math.ceil((double) totalRows / numPerPage);
		return totalPages;
	}

	/**
	 * 返回总行数
	 * 
	 * @return
	 */
	public int getTotalRows() {

		return totalRows;
	}

	/**
	 * 第一页
	 * 
	 * @return
	 */
	public int firstPage() {

		return 1;
	}

	/**
	 * 最后一页
	 * 
	 * @return
	 */
	public int lastPage() {

		return getTotalPages();
	}

	/**
	 * 上一页
	 * 
	 * @return
	 */
	public int previousPage() {

		return (pageNum - 1 > getTotalPages()) ? getTotalPages() : pageNum - 1;
	}

	/**
	 * 下一页
	 * 
	 * @return
	 */
	public int nextPage() {

		return (pageNum + 1 > getTotalPages()) ? getTotalPages() : pageNum + 1;
	}

	/**
	 * 是否是第一页
	 * 
	 * @return
	 */
	public boolean isFirstPage() {

		return (pageNum == 1) ? true : false;
	}

	/**
	 * 是否是最后一页
	 * 
	 * @return
	 */
	public boolean isLastPage() {

		return (pageNum == getTotalPages()) ? true : false;
	}

	/**
	 * 当前开始下标
	 */
	public int getStart() {
		// start = (curPage <= 1) ? 0 : ((curPage - 1) * pageSize) + 1;
		// oracle的分页

		start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage); // mysql的分页

		return start;
	}

	/**
	 * 当前结束下标
	 */
	public int getEnd() {
		end = getStart() + numPerPage - 1;

		return end;
	}

	/**
	 * 每页显示的记录数(默认为10条,可以通过set改变其数量)
	 * 
	 * @return
	 */
	public int getNumPerPage() {

		return numPerPage;
	}

	public void setPageNum(int pageNum) {

		this.pageNum = pageNum;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}
}
