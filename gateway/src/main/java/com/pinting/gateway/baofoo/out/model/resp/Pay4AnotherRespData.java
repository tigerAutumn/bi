package com.pinting.gateway.baofoo.out.model.resp;

import java.util.List;

/**
 * Created by 剑钊 on 2016/7/21.
 */
public class Pay4AnotherRespData<T> {

    private T trans_reqData;

    public T getTrans_reqData() {
        return trans_reqData;
    }

    public void setTrans_reqData(T trans_reqData) {
        this.trans_reqData = trans_reqData;
    }
}
