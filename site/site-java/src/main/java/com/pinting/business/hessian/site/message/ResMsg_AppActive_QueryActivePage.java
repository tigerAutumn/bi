package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Author:      shh
 * Date:        2017/7/7
 * Description: H5活动中心 出参
 */
public class ResMsg_AppActive_QueryActivePage extends ResMsg {

    private static final long serialVersionUID = -1678703057402842133L;

    /* 总条数 */
    private Integer count;

    /* 对应活动列表 */
    private ArrayList<HashMap<String, Object>> dataGrid;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ArrayList<HashMap<String, Object>> getDataGrid() {
        return dataGrid;
    }

    public void setDataGrid(ArrayList<HashMap<String, Object>> dataGrid) {
        this.dataGrid = dataGrid;
    }
}
