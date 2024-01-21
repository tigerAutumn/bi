package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 根据显示端查询显示的banner信息 出参
 * @author dengpengfeng
 * @2016-3-17 上午11:05:50
 */
public class ResMsg_BannerConfig_getList extends ResMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1789377944462713329L;
	/*banner列表*/
	private ArrayList<HashMap<String, Object>> bannerList;
	
	public ArrayList<HashMap<String, Object>> getBannerList() {
		return bannerList;
	}
	public void setBannerList(ArrayList<HashMap<String, Object>> bannerList) {
		this.bannerList = bannerList;
	}

}
