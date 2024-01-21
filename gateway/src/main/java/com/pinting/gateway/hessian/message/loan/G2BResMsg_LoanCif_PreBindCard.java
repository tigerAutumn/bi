package com.pinting.gateway.hessian.message.loan;

import com.pinting.core.hessian.msg.ResMsg;

public class G2BResMsg_LoanCif_PreBindCard extends ResMsg {

    /**
     * 绑卡订单号
     */
    private String bgwOrderNo;

    public String getBgwOrderNo() {
        return bgwOrderNo;
    }

    public void setBgwOrderNo(String bgwOrderNo) {
        this.bgwOrderNo = bgwOrderNo;
    }
}
