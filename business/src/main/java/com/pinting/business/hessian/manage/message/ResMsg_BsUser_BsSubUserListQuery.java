package com.pinting.business.hessian.manage.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsUser_BsSubUserListQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6015750581580821114L;
	/**
	 * 以下循环：
	 * id					用户Id
	 * nick					用户昵称
 	 * userName 		          用户名
	 * mobile				手机号
	 * idCard				身份证
	 * status				状态
	 * sumBalance           总资产
	 * canWithdraw			可提现金额
	 * currentInterest		投资收益
	 * currentBanlace		当前投资本金
	 * totalBonus			累计推荐奖励
	 * totalInterest		累计投资收益
	 * cardNo				绑定的银行卡号
	 * bankStatus			绑定银行卡号的状态 （1-正常  2-禁用 3-绑定中 4.绑定失败）
	 * bindTime 			绑卡时间
	 * cardOwner			银行卡所属人
	 */
	private List<HashMap<String, Object>> bsUserList;
	private Integer numPerPage;
	private Integer totalRows;
	private Integer pageNum;
	private String search;
	
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public List<HashMap<String, Object>> getBsUserList() {
		return bsUserList;
	}
	public void setBsUserList(List<HashMap<String, Object>> bsUserList) {
		this.bsUserList = bsUserList;
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
	


	
}
