package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 查询支持快捷支付的优先级最高银行，入参
 * @author shencheng
 * @2015-11-16 下午7:48:46
 */
public class ReqMsg_Bank_Pay19BankList extends ReqMsg{

	private static final long serialVersionUID = -8579095780577211319L;

	private Integer userId;  //用户ID     选填参数，有用户ID则查询用户配置优先级数据进行替换

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
