package com.pinting.gateway.hessian.message.zsd;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.loan.model.BankLimit;

import java.util.List;

/**
 * Author:      shh
 * Date:        2017/8/30
 * Description: 查询银行卡限额 响应信息
 */
public class G2BResMsg_ZsdBankLimit_QueryBankLimit extends ResMsg {

    private static final long serialVersionUID = 7440458549861554105L;

    /* 各银行限额 */
    private List<BankLimit> limits;

    public List<BankLimit> getLimits() {
        return limits;
    }

    public void setLimits(List<BankLimit> limits) {
        this.limits = limits;
    }
}
