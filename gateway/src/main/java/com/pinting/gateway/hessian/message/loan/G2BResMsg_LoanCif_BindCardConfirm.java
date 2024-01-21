package com.pinting.gateway.hessian.message.loan;

import com.pinting.core.hessian.msg.ResMsg;

public class G2BResMsg_LoanCif_BindCardConfirm extends ResMsg {

    /**
     *  绑卡编号
     */
    private String bindId;

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }
}
