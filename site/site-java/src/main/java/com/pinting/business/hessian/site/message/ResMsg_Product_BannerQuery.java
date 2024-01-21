package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Product_BannerQuery extends ResMsg {

	private static final long serialVersionUID = -6423728369361836710L;

	/**
	 *  banner链接
	 */
	private String bannerUrl;
	/**
	 * 图片路径
	 */
	private String imgPath;
	public String getBannerUrl() {
		return bannerUrl;
	}
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
}
