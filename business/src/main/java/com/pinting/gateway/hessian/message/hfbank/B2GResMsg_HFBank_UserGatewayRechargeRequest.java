package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      cyb
 * Date:        2017/4/3
 * Description: 网关充值响应信息
 */
public class B2GResMsg_HFBank_UserGatewayRechargeRequest extends ResMsg {

    private static final long serialVersionUID = 3811987904671252797L;

    /* 页面元素 */
    private String html;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
