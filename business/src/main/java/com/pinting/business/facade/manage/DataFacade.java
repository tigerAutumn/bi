/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.facade.manage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_Data_DataStatistics;
import com.pinting.business.hessian.manage.message.ReqMsg_Data_HistoryData;
import com.pinting.business.hessian.manage.message.ResMsg_Data_DataStatistics;
import com.pinting.business.hessian.manage.message.ResMsg_Data_HistoryData;
import com.pinting.business.service.manage.DataStatisticsService;

/**
 * 
 * @author HuanXiong
 * @version $Id: DataFacade.java, v 0.1 2015-12-14 下午7:14:58 HuanXiong Exp $
 */
@Component("Data")
public class DataFacade {
    
    @Autowired
    private DataStatisticsService dataStatisticsService;
    
    public void dataStatistics(ReqMsg_Data_DataStatistics reqMsg, ResMsg_Data_DataStatistics resMsg) {
        dataStatisticsService.dataStatistics(reqMsg, resMsg);
    }
    
    public void historyData(ReqMsg_Data_HistoryData req, ResMsg_Data_HistoryData res) {
        dataStatisticsService.historyData(req, res);
    }
}
