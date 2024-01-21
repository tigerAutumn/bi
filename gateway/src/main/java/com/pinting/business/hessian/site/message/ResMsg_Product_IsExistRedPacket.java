package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 是否存在红包
 * Created by cyb on 2018/5/4.
 */
public class ResMsg_Product_IsExistRedPacket extends ResMsg {

    private static final long serialVersionUID = 6082651395683810144L;

    private String isExistRedPacket; // 是否存在可用红包（yes 是、no 否）

    public String getIsExistRedPacket() {
        return isExistRedPacket;
    }

    public void setIsExistRedPacket(String isExistRedPacket) {
        this.isExistRedPacket = isExistRedPacket;
    }

}
