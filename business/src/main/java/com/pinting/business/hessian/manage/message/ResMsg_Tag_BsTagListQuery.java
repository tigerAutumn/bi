package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Tag_BsTagListQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3017887677756799480L;
	
	private ArrayList<HashMap<String, Object>> tagList;

	public ArrayList<HashMap<String, Object>> getTagList() {
		return tagList;
	}

	public void setTagList(ArrayList<HashMap<String, Object>> tagList) {
		this.tagList = tagList;
	}

}
