package com.pinting.gateway.baofoo.out.model.resp;

import java.util.List;

/**
 * Created by 剑钊
 *
 * @2016/10/19 17:30.
 */
public class BalanceQueryResp {


    private List<BaoFooBalance> trans_reqData;

    public List<BaoFooBalance> getTrans_reqData() {
        return trans_reqData;
    }

    public void setTrans_reqData(List<BaoFooBalance> trans_reqData) {
        this.trans_reqData = trans_reqData;
    }
}
