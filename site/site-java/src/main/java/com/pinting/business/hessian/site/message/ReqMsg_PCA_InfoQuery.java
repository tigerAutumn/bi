package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 根据上级id号查询城市 入参
 * @author Huang MengJian
 * @2015-2-12 上午10:56:43
 */
public class ReqMsg_PCA_InfoQuery extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1349963900389776860L;
	/*上级id*/
	private Integer parentId;

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	
}
