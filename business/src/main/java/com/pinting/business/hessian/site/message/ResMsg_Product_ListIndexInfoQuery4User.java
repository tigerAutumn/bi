package com.pinting.business.hessian.site.message;

import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 理财计划列表分级--首页（引导页面）
 * @author SHENGP
 * @date  2017-7-4 下午2:15:56
 */
public class ResMsg_Product_ListIndexInfoQuery4User extends ResMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6548107085647097232L;
	// 是否新手
	private Integer isNovice;
	
	public Integer getIsNovice() {
		return isNovice;
	}
	public void setIsNovice(Integer isNovice) {
		this.isNovice = isNovice;
	}
	
}
