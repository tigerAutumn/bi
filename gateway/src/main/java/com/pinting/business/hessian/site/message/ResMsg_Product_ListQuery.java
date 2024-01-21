package com.pinting.business.hessian.site.message;

import java.util.List;
import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Product_ListQuery extends ResMsg {

	private static final long serialVersionUID = -2392982177345454832L;

	private List<Map<String,Object>> ProductLst;
	
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<Map<String,Object>> getProductLst() {
		return ProductLst;
	}

	public void setProductLst(List<Map<String,Object>> productLst) {
		ProductLst = productLst;
	}
}
