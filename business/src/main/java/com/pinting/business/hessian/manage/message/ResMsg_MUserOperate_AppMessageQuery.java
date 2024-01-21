package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MUserOperate_AppMessageQuery extends ResMsg {

	private static final long serialVersionUID = -4623479475625277680L;
	
    private ArrayList<HashMap<String, Object>> appMessageList;
	
	private String pushChar;

	public ArrayList<HashMap<String, Object>> getAppMessageList() {
		return appMessageList;
	}

	public void setAppMessageList(ArrayList<HashMap<String, Object>> appMessageList) {
		this.appMessageList = appMessageList;
	}

	public String getPushChar() {
		return pushChar;
	}

	public void setPushChar(String pushChar) {
		this.pushChar = pushChar;
	}
}
