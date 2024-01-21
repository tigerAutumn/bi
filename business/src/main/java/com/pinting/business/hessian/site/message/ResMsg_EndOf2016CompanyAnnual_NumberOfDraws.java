package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 查询奖项 已抽奖的人次 出参
 * Created by shh on 2017/1/14 15:59.
 */
public class ResMsg_EndOf2016CompanyAnnual_NumberOfDraws extends ResMsg {

    private static final long serialVersionUID = -457140817853086880L;

    /** 最大抽奖次数*/
    private Integer maxNumberOfDraws;

    public Integer getMaxNumberOfDraws() {
        return maxNumberOfDraws;
    }

    public void setMaxNumberOfDraws(Integer maxNumberOfDraws) {
        this.maxNumberOfDraws = maxNumberOfDraws;
    }
}
