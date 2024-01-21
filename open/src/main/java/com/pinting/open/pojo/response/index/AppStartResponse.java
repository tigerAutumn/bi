package com.pinting.open.pojo.response.index;

import com.pinting.open.base.response.Response;
/**
 * APP启动页Response
 * @author Dragon & cat
 * @date 2017-3-16
 */
public class AppStartResponse extends Response {
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
