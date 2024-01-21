package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      cyb
 * Date:        2017/2/27
 * Description: 六重礼抽奖
 */
public class ResMsg_Activity_AnniversaryLuckyDraw extends ResMsg {

    private static final long serialVersionUID = -3772968312864400705L;

    private Integer awardId;

    public Integer getAwardId() {
        return awardId;
    }

    public void setAwardId(Integer awardId) {
        this.awardId = awardId;
    }
}
