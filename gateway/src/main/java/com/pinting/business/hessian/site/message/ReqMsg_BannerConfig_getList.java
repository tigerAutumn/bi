package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 根据显示端查询显示的banner信息，入参
 * @author shencheng
 * @2016-3-17 下午2:29:30
 */
public class ReqMsg_BannerConfig_getList extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1274303527756988535L;
	/*展示渠道*/
	private String bChannel;

	public String getbChannel() {
		return bChannel;
	}

	public void setbChannel(String bChannel) {
		this.bChannel = bChannel;
	}

	
}
