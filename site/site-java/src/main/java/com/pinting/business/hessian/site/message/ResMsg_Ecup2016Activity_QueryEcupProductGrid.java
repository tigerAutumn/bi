package com.pinting.business.hessian.site.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 查询欧洲杯活动标 出参 
 * @author HuanXiong
 * @version 2016-6-22 上午11:47:15
 */
public class ResMsg_Ecup2016Activity_QueryEcupProductGrid extends ResMsg {

    /**  */
    private static final long serialVersionUID = -7338792795826466142L;
    private List<HashMap<String, Object>> dataGrid;

    public List<HashMap<String, Object>> getDataGrid() {
        return dataGrid;
    }

    public void setDataGrid(List<HashMap<String, Object>> dataGrid) {
        this.dataGrid = dataGrid;
    }
    
}
