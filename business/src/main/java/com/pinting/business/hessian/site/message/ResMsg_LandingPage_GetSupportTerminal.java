package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * FLEXIBLE 推广型代理自动生产pc,h5页面，通过agent_code获取support_terminal的出参
 * @author zhangbao
 *
 */
public class ResMsg_LandingPage_GetSupportTerminal extends ResMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2458280058242678104L;

	private String support_terminal; //支持的终端(pc,h5)
	
	public String getSupport_terminal() {
		return support_terminal;
	}

	public void setSupport_terminal(String support_terminal) {
		this.support_terminal = support_terminal;
	}
	
}
