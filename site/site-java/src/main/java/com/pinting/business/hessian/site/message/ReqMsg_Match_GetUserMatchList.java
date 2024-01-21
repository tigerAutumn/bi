package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 根据userId和产品id分页查询债权明细 入参
 * @author bianyatian
 * @2016-4-22 上午10:37:01
 */
public class ReqMsg_Match_GetUserMatchList extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5495521228576059404L;
	/*产品id*/
	private Integer productId;
	/*用户id*/
	private Integer userId;
	/*子账户id*/
	private Integer subAccountId;
	/*起始页*/
	private Integer startPage;
	/*每页条数*/
	private Integer pageSize;
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStartPage() {
		return startPage;
	}

	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public Integer getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}
	
}
