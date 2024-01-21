package com.pinting.gateway.baofoo.out.model.resp;

/**
 * Created by 剑钊 on 2016/7/19.
 */
public class BindCardResp extends BaoFooBaseResp {

    /**
     * 绑定标示号
     */
    private String bind_id;

    public String getBind_id() {
        return bind_id;
    }

    public void setBind_id(String bind_id) {
        this.bind_id = bind_id;
    }
}
