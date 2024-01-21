package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      cyb
 * Date:        2016/9/20
 * Description: 是否绑卡响应信息
 */
public class ResMsg_User_IsBindCard extends ResMsg {

    private static final long serialVersionUID = -4750711189324586817L;

    private boolean isBindCard;

    public boolean isBindCard() {
        return isBindCard;
    }

    public void setBindCard(boolean bindCard) {
        isBindCard = bindCard;
    }
}
