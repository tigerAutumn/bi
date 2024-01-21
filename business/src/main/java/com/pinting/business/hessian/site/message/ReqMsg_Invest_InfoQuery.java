package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 根据用户id和子产品id号查询我的单条投资记录  入参
 * @author huangmengjian
 * @2015-2-4 下午5:46:25
 */
public class ReqMsg_Invest_InfoQuery extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7654645820070531307L;
	
	/*产品id*/
	private Integer id;
	/*用户id*/
	private Integer userId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
