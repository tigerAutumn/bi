package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Author:      shh
 * Date:        2017/6/30
 * Description: 分页查询港湾资讯、平台公告 出参
 */
public class ResMsg_News_QueryNewsPageInfo extends ResMsg {

    private static final long serialVersionUID = -7421048267827129662L;

    /*对应新闻列表*/
    private ArrayList<HashMap<String, Object>> dataGrid;

    /*总条数*/
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
