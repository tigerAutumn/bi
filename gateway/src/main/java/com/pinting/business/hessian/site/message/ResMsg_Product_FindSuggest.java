package com.pinting.business.hessian.site.message;

import java.util.List;
import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Product_FindSuggest extends ResMsg {

	private static final long serialVersionUID = -2392982177345454832L;

	private String isExistRedPacket; // 是否存在可用红包（yes 是、no 否）

	private List<Map<String,Object>> ProductLst;

	public String getIsExistRedPacket() {
		return isExistRedPacket;
	}

	public void setIsExistRedPacket(String isExistRedPacket) {
		this.isExistRedPacket = isExistRedPacket;
	}

	public List<Map<String,Object>> getProductLst() {
		return ProductLst;
	}

	public void setProductLst(List<Map<String,Object>> productLst) {
		ProductLst = productLst;
	}
}
