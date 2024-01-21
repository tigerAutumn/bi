package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Author:      cyb
 * Date:        2017/3/16
 * Description: 红包列表
 */
public class ResMsg_RedPacketInfo_QueryRedPacketList extends ResMsg {

    private static final long serialVersionUID = 8944103916550088886L;

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
