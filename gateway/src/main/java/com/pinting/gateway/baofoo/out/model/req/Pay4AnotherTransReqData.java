package com.pinting.gateway.baofoo.out.model.req;

import java.util.List;

/**
 * Created by 剑钊 on 2016/7/21.
 */
public class Pay4AnotherTransReqData<T> {

    private List<T> trans_reqData;

    public List<T> getTrans_reqData() {
        return trans_reqData;
    }

    public void setTrans_reqData(List<T> trans_reqData) {
        this.trans_reqData = trans_reqData;
    }
}
