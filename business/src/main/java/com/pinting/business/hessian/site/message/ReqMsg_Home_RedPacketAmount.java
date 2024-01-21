package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by cyb on 2017/12/4.
 */
public class ReqMsg_Home_RedPacketAmount extends ReqMsg {

    private static final long serialVersionUID = 3716273907284431857L;

    private String productShowTerminal;

    public String getProductShowTerminal() {
        return productShowTerminal;
    }

    public void setProductShowTerminal(String productShowTerminal) {
        this.productShowTerminal = productShowTerminal;
    }
}
