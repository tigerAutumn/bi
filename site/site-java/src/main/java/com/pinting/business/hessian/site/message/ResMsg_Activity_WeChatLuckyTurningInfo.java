package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author zhangpeng
 * @date 2018/05/27
 * @Description 微信小程序财运大转盘用户抽奖数据
 */
public class ResMsg_Activity_WeChatLuckyTurningInfo extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1971796061824448247L;

	/* 活动是否开始-start;not_start;end */
    private String isStart;

    /* YYYY-mm-dd HH:mm:ss */
    private String startTime;

    private String endTime;

    /* 是否登录:yes;no */
    private String isLogin;

	public String getIsStart() {
		return isStart;
	}

	public void setIsStart(String isStart) {
		this.isStart = isStart;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}

}
