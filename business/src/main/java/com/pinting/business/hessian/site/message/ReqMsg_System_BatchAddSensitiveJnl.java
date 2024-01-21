package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_System_BatchAddSensitiveJnl extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9046033865988136147L;
	
	/*
	 *  List元素HashMap中对应字段
		private Integer userId;
		private String opType;
		private String userName;
		private Date time;
		private String ip;
		private String terminal;
		private String info;
	*/
	private ArrayList<HashMap<String, Object>> sensitiveJnls;

	public ArrayList<HashMap<String, Object>> getSensitiveJnls() {
		return sensitiveJnls;
	}
	public void setSensitiveJnls(ArrayList<HashMap<String, Object>> sensitiveJnls) {
		this.sensitiveJnls = sensitiveJnls;
	}
}
