package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      cyb
 * Date:        2017/6/30
 * Description:
 */
public class ResMsg_AppActive_IsRead extends ResMsg {

    private static final long serialVersionUID = 3199028384185839556L;

    /* YES-已读；NO-未读 */
    private String isRead;

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
