package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Created by cyb on 2017/11/6.
 */
public class ResMsg_Activity_ExchangeGift extends ResMsg {

    private static final long serialVersionUID = 44316472811623847L;

    private Integer luckyDrawId;

    private boolean haveAddress;

    public boolean isHaveAddress() {
        return haveAddress;
    }

    public void setHaveAddress(boolean haveAddress) {
        this.haveAddress = haveAddress;
    }

    public Integer getLuckyDrawId() {
        return luckyDrawId;
    }

    public void setLuckyDrawId(Integer luckyDrawId) {
        this.luckyDrawId = luckyDrawId;
    }
}
