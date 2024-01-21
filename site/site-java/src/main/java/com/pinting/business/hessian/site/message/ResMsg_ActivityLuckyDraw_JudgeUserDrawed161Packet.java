package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Created by Administrator on 2016/10/31.
 */
public class ResMsg_ActivityLuckyDraw_JudgeUserDrawed161Packet extends ResMsg {
    private static final long serialVersionUID = 5162173714798551903L;

    private boolean drawed161Packet;

    public boolean isDrawed161Packet() {
        return drawed161Packet;
    }

    public void setDrawed161Packet(boolean drawed161Packet) {
        this.drawed161Packet = drawed161Packet;
    }
}
