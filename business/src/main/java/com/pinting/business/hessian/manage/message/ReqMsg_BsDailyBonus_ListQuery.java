package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsDailyBonus_ListQuery extends ReqMsg{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4764157371846742198L;
	private String pageNum;
	private String numPerPage;
	
	/**推荐人姓名**/
	private String name;
	/**推荐人手机号码**/
	private String mobile;
	/**投资人 被推荐人姓名**/
	private String byName;
	
	/**投资人 推荐人手机号码**/
	private String byMobile;
	
	/** 推荐人姓名  **/
	private String recommendName;
	
	/** 推荐人手机号码  **/
	private String recommendMobile;
	
	private String queryFlag;

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(String numPerPage) {
		this.numPerPage = numPerPage;
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

	public String getByName() {
		return byName;
	}

	public void setByName(String byName) {
		this.byName = byName;
	}

	public String getByMobile() {
		return byMobile;
	}

	public void setByMobile(String byMobile) {
		this.byMobile = byMobile;
	}

	public String getRecommendName() {
		return recommendName;
	}

	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}

	public String getRecommendMobile() {
		return recommendMobile;
	}

	public void setRecommendMobile(String recommendMobile) {
		this.recommendMobile = recommendMobile;
	}

	public String getQueryFlag() {
		return queryFlag;
	}

	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}

}
