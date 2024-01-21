package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Created by cyb on 2017/10/30.
 */
public class ResMsg_News_IsRead extends ResMsg {

    private static final long serialVersionUID = 782207656022382811L;

    private String isRead;

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
