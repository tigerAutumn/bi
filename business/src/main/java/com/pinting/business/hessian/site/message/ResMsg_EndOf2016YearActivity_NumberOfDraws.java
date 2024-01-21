package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 2016客户年终抽奖活动其它奖(一、二、三等奖)项抽奖次数统计 出参
 * Created by shh on 2016/12/06 12:00.
 * @author shh
 */
public class ResMsg_EndOf2016YearActivity_NumberOfDraws extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3774090951298153718L;
	
	/** 最大抽奖次数*/
	private Integer maxNumberOfDraws;

	public Integer getMaxNumberOfDraws() {
		return maxNumberOfDraws;
	}

	public void setMaxNumberOfDraws(Integer maxNumberOfDraws) {
		this.maxNumberOfDraws = maxNumberOfDraws;
	}
	
}
