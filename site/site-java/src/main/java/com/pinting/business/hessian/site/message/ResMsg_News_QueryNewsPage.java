/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 分页查询所有新闻 出参
 * @author HuanXiong
 * @version $Id: ResMsg_News_QueryNewsPage.java, v 0.1 2016-2-23 上午10:28:18 HuanXiong Exp $
 */
public class ResMsg_News_QueryNewsPage extends ResMsg {

    /**  */
    private static final long serialVersionUID = -7698484437865711130L;

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
