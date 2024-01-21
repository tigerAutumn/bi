package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      cyb
 * Date:        2016/8/23
 * Description: 正式绑卡（宝付）响应信息
 */
public class ResMsg_BaoFooBank_BindCard extends ResMsg {

    private static final long serialVersionUID = 338309335843671358L;

    /* 绑定ID */
    private String bindId;

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

}
