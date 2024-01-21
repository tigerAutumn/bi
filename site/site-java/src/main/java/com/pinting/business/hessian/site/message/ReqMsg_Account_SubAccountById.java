package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 根据主键id查询subAccount的入参
 * @author bianyatian
 * @2015-12-10 下午4:08:04
 */
public class ReqMsg_Account_SubAccountById extends ReqMsg{
	/**
	 * 
	 */
	private static final long serialVersionUID = -786596968165871285L;
	/*主键id*/
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
