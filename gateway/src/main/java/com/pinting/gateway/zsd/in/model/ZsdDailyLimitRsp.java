package com.pinting.gateway.zsd.in.model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Author:      shh
 * Date:        2017/8/30
 * Description: 赞时贷查询币港湾当日可用额度 响应信息
 */
public class ZsdDailyLimitRsp extends BaseResModel {

    /* 额度情况 以分为单位 */
    @NotEmpty(message = "额度情况不能为空")
    private Long amounts;

    public Long getAmounts() {
        return amounts;
    }

    public void setAmounts(Long amounts) {
        this.amounts = amounts;
    }
}
