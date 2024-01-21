package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * 回款计划详情
 * Created by shh on 2017/3/30.
 */
public class RepayScheduleDetailsRequest extends Request {

	/* 用户id */
	private Integer userId;

	/* 日期格式为2017-01 */
	private String planDate;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/asset/repayScheduleDetails";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/asset/repayScheduleDetails";
	}

}
