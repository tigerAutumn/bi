/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_RedPacket_ChooseRedPacket.java, v 0.1 2016-3-1 下午2:34:46 HuanXiong Exp $
 */
public class ResMsg_RedPacketInfo_ChooseRedPacket extends ResMsg {

    /**  */
    private static final long serialVersionUID = -1260420878223995384L;

    private HashMap<String, Object> data;

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

}
