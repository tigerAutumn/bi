package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 根据产品ID查询用户类型产品权限 入参
 * @author shiyulong
 * @2016-2-18 下午12:40:17
 */
public class ReqMsg_Product_FindProductUserTypeAuthById extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2339552188278549443L;
	/*产品id*/
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
