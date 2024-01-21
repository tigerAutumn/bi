package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      shh
 * Date:        2017/6/26
 * Description: 微信点亮存管图标瓜分百万红包点亮操作 出参
 */
public class ResMsg_Activity_ActivityLightUpOperate extends ResMsg {

    private static final long serialVersionUID = -3345261840990626104L;

    /* 是否已参加点亮活动 */
    private String isLightUp;

    /* 点亮人数 */
    private Integer num;

    public String getIsLightUp() {
        return isLightUp;
    }

    public void setIsLightUp(String isLightUp) {
        this.isLightUp = isLightUp;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
