package com.pinting.gateway.baofoo.out.model.resp;

import java.util.List;

/**
 * Created by 剑钊 on 2016/7/21.
 */
public class BalanceBaseResp<T> {

    private TransHead trans_head;

    private Pay4AnotherRespData<T> trans_reqDatas;

    public TransHead getTrans_head() {
        return trans_head;
    }

    public void setTrans_head(TransHead trans_head) {
        this.trans_head = trans_head;
    }

    public Pay4AnotherRespData<T> getTrans_reqDatas() {
        return trans_reqDatas;
    }

    public void setTrans_reqDatas(Pay4AnotherRespData<T> trans_reqDatas) {
        this.trans_reqDatas = trans_reqDatas;
    }
}
