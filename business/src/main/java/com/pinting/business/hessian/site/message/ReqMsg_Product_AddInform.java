package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 添加产品提醒消息 入参
 * @author bianyatian
 * @2016-4-21 下午4:40:57
 */
public class ReqMsg_Product_AddInform extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5340738584309874563L;
	/*产品id*/
	private Integer productId;
	/*用户id*/
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

}
