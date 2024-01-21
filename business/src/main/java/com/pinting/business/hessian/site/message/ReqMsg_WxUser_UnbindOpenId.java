package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version 2016-5-31 下午1:46:08
 */
public class ReqMsg_WxUser_UnbindOpenId extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -2831886205866498899L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
}
