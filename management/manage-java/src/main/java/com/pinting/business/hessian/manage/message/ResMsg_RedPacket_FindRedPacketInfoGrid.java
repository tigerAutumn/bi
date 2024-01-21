/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_RedPacket_FindRedPacketInfoGrid.java, v 0.1 2016-2-29 下午5:50:21 HuanXiong Exp $
 */
public class ResMsg_RedPacket_FindRedPacketInfoGrid extends ResMsg {

    /**  */
    private static final long serialVersionUID = -987874686089873377L;

    private ArrayList<HashMap<String, Object>> dataGrid;
    
    private ArrayList<HashMap<String, Object>> agents;
    
    private Integer count;

    public ArrayList<HashMap<String, Object>> getDataGrid() {
        return dataGrid;
    }

    public void setDataGrid(ArrayList<HashMap<String, Object>> dataGrid) {
        this.dataGrid = dataGrid;
    }

    public ArrayList<HashMap<String, Object>> getAgents() {
        return agents;
    }

    public void setAgents(ArrayList<HashMap<String, Object>> agents) {
        this.agents = agents;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    
}
