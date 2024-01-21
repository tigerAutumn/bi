package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 欧洲杯活动-保存用户猜冠亚军记录（检查数据完整）出参
 * @author bianyatian
 * @2016-6-22 下午1:35:35
 */
public class ResMsg_Ecup2016Activity_SaveUserChampionSilver extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2378669983354216754L;
	/*保存是否成功*/
	private boolean flag;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
