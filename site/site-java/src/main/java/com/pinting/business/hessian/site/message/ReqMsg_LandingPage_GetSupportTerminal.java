package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * FLEXIBLE 推广型代理自动生产pc,h5页面，通过agent_code获取support_terminal的入参
 * @author zhangbao
 *
 */
public class ReqMsg_LandingPage_GetSupportTerminal extends ReqMsg{


	private static final long serialVersionUID = -1015309089669045592L;

	private Integer id;   // 推广型代理id

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
}
