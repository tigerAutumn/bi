package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.dafy.model.Agreements;

import java.util.List;


public class G2BResMsg_DafyLoan_AgreementNotice extends ResMsg {

    /**
     *
     */
    private static final long serialVersionUID = 4600446602051408622L;

    private List<Agreements> agreements; //（以下一级循环）

    public List<Agreements> getAgreements() {
        return agreements;
    }

    public void setAgreements(List<Agreements> agreements) {
        this.agreements = agreements;
    }
}
