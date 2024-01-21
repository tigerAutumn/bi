package com.pinting.business.model.vo;

/**
 * 渠道业绩查询-渠道用户查询优化 vo
 *
 * @author shh
 * @date 2018/5/10 17:27
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class AgentUserVo {

    private Integer userId;

    /* 理财人回款计划表记录数 */
    private Integer repayedPeriodCount;

    /* 债转金额 */
    private Double realAmountTrans;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRepayedPeriodCount() {
        return repayedPeriodCount;
    }

    public void setRepayedPeriodCount(Integer repayedPeriodCount) {
        this.repayedPeriodCount = repayedPeriodCount;
    }

    public Double getRealAmountTrans() {
        return realAmountTrans;
    }

    public void setRealAmountTrans(Double realAmountTrans) {
        this.realAmountTrans = realAmountTrans;
    }
}
