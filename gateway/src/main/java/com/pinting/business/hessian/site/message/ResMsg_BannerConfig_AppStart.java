package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * APP启动页
 * @author Dragon & cat
 * @date 2017-3-16
 */
public class ResMsg_BannerConfig_AppStart extends ResMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*点击banner之后跳转的url*/
	private String url;
	/*图片地址*/
	private String image;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
}
