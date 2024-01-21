package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/2/14
 * Description:
 */
public class ResMsg_TransDetail_QueryZanReturnDetail extends ResMsg {

	private static final long serialVersionUID = 4394240180248322159L;

	private List<HashMap<String, Object>> list;

	public List<HashMap<String, Object>> getList() {
		return list;
	}

	public void setList(List<HashMap<String, Object>> list) {
		this.list = list;
	}
}
