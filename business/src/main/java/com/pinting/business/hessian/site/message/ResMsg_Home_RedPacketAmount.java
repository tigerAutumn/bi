package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Created by cyb on 2017/12/4.
 */
public class ResMsg_Home_RedPacketAmount extends ResMsg {

    private static final long serialVersionUID = -309094897887009274L;

    private Double totalRedPacketSubtract;

    public Double getTotalRedPacketSubtract() {
        return totalRedPacketSubtract;
    }

    public void setTotalRedPacketSubtract(Double totalRedPacketSubtract) {
        this.totalRedPacketSubtract = totalRedPacketSubtract;
    }
}
