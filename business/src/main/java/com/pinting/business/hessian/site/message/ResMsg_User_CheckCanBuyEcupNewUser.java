package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version 2016-6-22 下午1:40:07
 */
public class ResMsg_User_CheckCanBuyEcupNewUser extends ResMsg {

    /**  */
    private static final long serialVersionUID = -5804007487805763965L;

    private Boolean newUser;

    public Boolean getNewUser() {
        return newUser;
    }

    public void setNewUser(Boolean newUser) {
        this.newUser = newUser;
    }
    
}
