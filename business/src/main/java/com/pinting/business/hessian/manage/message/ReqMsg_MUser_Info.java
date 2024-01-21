package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MUser_Info extends ReqMsg {


	private static final long serialVersionUID = -4623479475625277680L;
	/**
	 * 
	 */

	
	private Integer id;//	用户编号	必填		
	private String nick;//	用户名	必填		
	private String name;//	用户真实姓名	可选		
	private String mobile;//	手机号	可选		
	private String email;//	邮箱	可选		
	private String password;
	private Integer roleId;
	private Integer status;//	状态	必填		1：有效 2：注销 3：加锁
	private Date createTime;
	private Date loginTime;
	private Date logoutTime;
	private String note;
	private String flag;
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
}
