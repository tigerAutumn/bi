package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 平台数据新增内容
 * Created by cyb on 2018/1/24.
 */
public class ReqMsg_Invest_PlatformData extends ReqMsg {

    private static final long serialVersionUID = -7403520234974619796L;

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
