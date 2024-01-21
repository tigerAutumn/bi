package com.pinting.gateway.baofoo.out.model.req;


/**
 * Created by 剑钊 on 2016/7/21.
 */
public class Pay4AnotherReq<T> {

    private Pay4AnotherTransContent<T> trans_content;

    public Pay4AnotherTransContent getTrans_content() {
        return trans_content;
    }

    public void setTrans_content(Pay4AnotherTransContent trans_content) {
        this.trans_content = trans_content;
    }
}
