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
 * @version $Id: ResMsg_RedPacket_RedPacketList.java, v 0.1 2016-3-1 下午12:53:38 HuanXiong Exp $
 */
public class ResMsg_RedPacketInfo_RedPacketList extends ResMsg {

    /**  */
    private static final long serialVersionUID = 3536789372596052999L;
    
    private ArrayList<HashMap<String, Object>> dataGrid;
    
    private Integer count;

    public ArrayList<HashMap<String, Object>> getDataGrid() {
        return dataGrid;
    }

    public void setDataGrid(ArrayList<HashMap<String, Object>> dataGrid) {
        this.dataGrid = dataGrid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    
}
