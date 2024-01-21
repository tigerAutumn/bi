package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 投资管理
 * 
 * @author Dragon & cat
 * @date 2017-3-17
 */
public class ReqMsg_User_InvestManage extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2770013558519609763L;
	/*用户ID*/
	private Integer userId;
	/*产品类型   （根据回款类型不同区分产品类型：港湾计划-FINISH_RETURN_ALL  、 
	委托计划   - AVERAGE_CAPITAL_PLUS_INTEREST 、  增计划 - EXIT_RETURN_ALL ）
	*/
	private	String	returnType;
	/*投资状态
	 * 港湾计划
	 *     HOLDING 持有中
	 *     FINISH  已完成
	 * 赞分期计划
	 * 	   HOLDING 持有中
	 *     ENTRUSTING  委托中
	 *     FINISH  已完成
	 * */
	private	String 	investStatus;
	/*开始页数*/
	private Integer pageNum;
	/*每页数量*/
	private Integer pageSize;
	/*总页数*/
	private Integer totalPages;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getInvestStatus() {
		return investStatus;
	}
	public void setInvestStatus(String investStatus) {
		this.investStatus = investStatus;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	
}
