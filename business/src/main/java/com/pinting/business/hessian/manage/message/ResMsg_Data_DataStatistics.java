/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.manage.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_Data_DataStatistics.java, v 0.1 2015-12-14 下午2:22:32 HuanXiong Exp $
 */
public class ResMsg_Data_DataStatistics extends ResMsg {

    /**  */
    private static final long serialVersionUID = 4235670205597409941L;
    
    // 1、总的统计数据
    private Map<String, Object> totalDatas;
    // 2、图表显示数据
    private List<HashMap<String, Object>>  chartDatas;
    // 3、所有用户 表格数据
    private List<HashMap<String, Object>> dataTables;
    // 4、所有用户 总数
    private Integer count;
    // 5、渠道来源
    private List<HashMap<String, Object>> agentList;
    
    public List<HashMap<String, Object>> getAgentList() {
        return agentList;
    }
    public void setAgentList(List<HashMap<String, Object>> arrayList) {
        this.agentList = arrayList;
    }
    public Map<String, Object> getTotalDatas() {
        return totalDatas;
    }
    public void setTotalDatas(Map<String, Object> totalDatas) {
        this.totalDatas = totalDatas;
    }
    public List<HashMap<String, Object>> getChartDatas() {
        return chartDatas;
    }
    public void setChartDatas(List<HashMap<String, Object>> chartDatas) {
        this.chartDatas = chartDatas;
    }
    public List<HashMap<String, Object>> getDataTables() {
        return dataTables;
    }
    public void setDataTables(List<HashMap<String, Object>> dataTables) {
        this.dataTables = dataTables;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
}
