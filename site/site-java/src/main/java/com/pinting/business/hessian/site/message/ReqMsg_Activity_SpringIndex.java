package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 踏春活动
 * @author bianyatian
 * @2017-3-24 下午7:08:32
 */
public class ReqMsg_Activity_SpringIndex extends ReqMsg {

	private static final long serialVersionUID = -5087343319139198111L;
	
	/* 显示端口 */
	private String terminal;

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	
}
