package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;


/**
 * 用户交易明细请求
 * @author yanwl
 * @date 2015-11-13
 */
public class ReqMsg_MAccount_TradeDetailListQuery extends ReqMsg {

	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = 4828765622722864507L;
	
	/**
	 * 查询手机号
	 */
	private String sMobile;
	
	/**
	 * 查询用户名
	 */
	private String sUserName;
	
	/**
	 * 查询交易类型
	 */
	private String sTransType;
	
	/**
	 * 页码
	 */
	private int pageNum;
	
	/**
	 * 每页显示的记录数(默认为20条,可以通过set改变其数量)
	 */
	private int numPerPage = 20;

	public int getPageNum() {
		if (pageNum < 1) {

			this.pageNum = 1;
		}

		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public String getsMobile() {
		return sMobile;
	}

	public void setsMobile(String sMobile) {
		this.sMobile = sMobile;
	}

	public String getsUserName() {
		return sUserName;
	}

	public void setsUserName(String sUserName) {
		this.sUserName = sUserName;
	}

	public String getsTransType() {
		return sTransType;
	}

	public void setsTransType(String sTransType) {
		this.sTransType = sTransType;
	}
}
