/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_RedPacket_ChooseRedPacket.java, v 0.1 2016-3-1 下午2:31:15 HuanXiong Exp $
 */
public class ReqMsg_RedPacketInfo_ChooseRedPacket extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -3227628010548436443L;

    private Integer id; // 红包ID
    
    private Integer userId;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
}
