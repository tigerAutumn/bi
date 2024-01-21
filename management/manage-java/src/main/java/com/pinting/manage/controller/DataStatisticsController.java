/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.manage.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_Data_DataStatistics;
import com.pinting.business.hessian.manage.message.ReqMsg_Data_HistoryData;
import com.pinting.business.hessian.manage.message.ResMsg_Data_DataStatistics;
import com.pinting.business.hessian.manage.message.ResMsg_Data_HistoryData;
import com.pinting.business.service.manage.AgentService;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;

/**
 * 
 * @author HuanXiong
 * @version $Id: DataStatisticsController.java, v 0.1 2015-12-14 下午2:08:02 HuanXiong Exp $
 */
@Controller
public class DataStatisticsController {

    @Autowired
    @Qualifier("dispatchService")
    private HessianService siteService;
    @Autowired
    private AgentService agentService;
    
    /**
     * 数据统计
     * 
     * @return
     */
    @RequestMapping("/data/data_index")
    public String dataIndex(ReqMsg_Data_DataStatistics reqMsg, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        // 请求过滤
        // timeType是空，则默认为 按日对比
        if(StringUtil.isBlank(reqMsg.getTimeType())) {
            reqMsg.setTimeType("day");
            reqMsg.setAgent("all");
        }
        // startTime 且 currentDays 为空，且 则默认查 最近30天
        if(StringUtil.isBlank(reqMsg.getStartTime()) && StringUtil.isBlank(reqMsg.getCurrentDays())) {
            reqMsg.setCurrentDays("30");
        }
        // startTime 不为空， endTime为空，则默认endTime是now
        if(!StringUtil.isBlank(reqMsg.getStartTime()) && StringUtil.isBlank(reqMsg.getEndTime()) && "day".equals(reqMsg.getTimeType())) {
            reqMsg.setEndTime(DateUtil.formatYYYYMMDD(new Date()));
        }
        
        // 数据统计
        ResMsg_Data_DataStatistics resMsg = (ResMsg_Data_DataStatistics)siteService.handleMsg(reqMsg);
        
        // 1、累计数据
        model.put("totalDatas", resMsg.getTotalDatas());
        // 2、图表数据
        if (null != resMsg.getChartDatas()) {
            JSONArray array = JSONArray.fromObject(resMsg.getChartDatas());
            model.put("chartDatas", array);
        }
        
        model.put("chartDatasTable", resMsg.getDataTables());
        // 3、表格数据
//        model.put("dataTables", resMsg.getDataTables());
        reqMsg.setTotalRows(resMsg.getCount()==null?0:resMsg.getCount());
        //渠道列表总数
        int agentTotal = agentService.findAgentsTotalRows();
        model.put("agentTotal", agentTotal);
        model.put("req", reqMsg);
        return "/statistics/data/data_index";
    }
    
    /**
     * 历史数据插入
     * 
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/data/history_data")
    public Map<String, Object> historyData(HttpServletRequest request, HttpServletResponse response, ReqMsg_Data_HistoryData reqMsg) {
        Map<String, Object> result = new HashMap<String, Object>();
        ResMsg_Data_HistoryData resMsg = (ResMsg_Data_HistoryData) siteService.handleMsg(reqMsg);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())){
            result.put("req", reqMsg);
            result.put("success", true);
            result.put("statusCode", "200");
            result.put("message", "插入成功");
            result.put("navTabId", "pagerForm");
        } else {
            result.put("success", false);
            result.put("req", reqMsg);
            result.put("statusCode", "300");
            result.put("message", resMsg.getResMsg());
        }
        return result;
    }
    
    /**
     * 历史数据插入
     * 
     * @param reqMsg
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/data/history_index")
    public String historyIndex(ReqMsg_Data_HistoryData reqMsg, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        model.put("req", reqMsg);
        return "/statistics/data/history_datas";
    }
}
