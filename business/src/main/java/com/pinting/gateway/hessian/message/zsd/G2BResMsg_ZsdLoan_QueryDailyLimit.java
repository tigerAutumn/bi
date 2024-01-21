package com.pinting.gateway.hessian.message.zsd;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      shh
 * Date:        2017/8/30
 * Description: 赞时贷查询币港湾当日可用额度 响应信息
 */
public class G2BResMsg_ZsdLoan_QueryDailyLimit extends ResMsg {

    private static final long serialVersionUID = -2200425197739310738L;

    /* 额度情况 以分为单位 */
    private String amountNoLimit;

	public String getAmountNoLimit() {
		return amountNoLimit;
	}

	public void setAmountNoLimit(String amountNoLimit) {
		this.amountNoLimit = amountNoLimit;
	}
}
