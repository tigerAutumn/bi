package com.pinting.business.hessian.site.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * @Project: site-java
 * @Title: ResMsg_Dict_ListQuery.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:42:10
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ResMsg_Dict_ListQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2608139681127799358L;
	private List<HashMap<String,Object>> itemList;//	用户编号	必填		
	public List<HashMap<String,Object>> getItemList() {
		return itemList;
	}
	public void setItemList(List<HashMap<String,Object>> itemList) {
		this.itemList = itemList;
	}
	
}
