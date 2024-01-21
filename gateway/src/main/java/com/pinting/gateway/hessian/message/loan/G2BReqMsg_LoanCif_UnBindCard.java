package com.pinting.gateway.hessian.message.loan;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_LoanCif_UnBindCard extends ReqMsg {

    /**
     * 币港湾用户绑卡编号
     */
    private String bgwBindId;

    public String getBgwBindId() {
        return bgwBindId;
    }

    public void setBgwBindId(String bgwBindId) {
        this.bgwBindId = bgwBindId;
    }
}
