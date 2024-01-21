package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MallCommodity_GetDetail extends ReqMsg {

    private static final long serialVersionUID = 1L;

    private Integer id; // 商品信息表ID

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
