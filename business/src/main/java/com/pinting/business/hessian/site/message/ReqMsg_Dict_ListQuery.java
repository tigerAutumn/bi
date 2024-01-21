package com.pinting.business.hessian.site.message;

import javax.validation.constraints.NotNull;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: business
 * @Title: ReqMsg_Dict_ListQuery.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:45:27
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_Dict_ListQuery extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 74640072686841894L;
	@NotNull(message="类型编号不能为空")
	private Integer dictId;//类型编号	必填
	public Integer getDictId() {
		return dictId;
	}
	public void setDictId(Integer dictId) {
		this.dictId = dictId;
	}
	
}
