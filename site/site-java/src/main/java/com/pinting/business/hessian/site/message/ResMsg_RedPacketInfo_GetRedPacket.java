/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_RedPacketInfo_GetRedPacket.java, v 0.1 2016-3-3 上午10:51:40 HuanXiong Exp $
 */
public class ResMsg_RedPacketInfo_GetRedPacket extends ResMsg {

    /**  */
    private static final long serialVersionUID = -7792257604681948314L;

    private ArrayList<HashMap<String, Object>> dataGrid;

    public ArrayList<HashMap<String, Object>> getDataGrid() {
        return dataGrid;
    }

    public void setDataGrid(ArrayList<HashMap<String, Object>> dataGrid) {
        this.dataGrid = dataGrid;
    }
    
}
