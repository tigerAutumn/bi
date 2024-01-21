package com.pinting.gateway.baofoo.out.model.req;

import java.util.List;

/**
 * Created by 剑钊 on 2016/7/21.
 */
public class Pay4AnotherTransContent<T> {

    private List<Pay4AnotherTransReqData<T>> trans_reqDatas;

    public List<Pay4AnotherTransReqData<T>> getTrans_reqDatas() {
        return trans_reqDatas;
    }

    public void setTrans_reqDatas(List<Pay4AnotherTransReqData<T>> trans_reqDatas) {
        this.trans_reqDatas = trans_reqDatas;
    }
}
