/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.manage;

import com.pinting.business.hessian.manage.message.ReqMsg_Data_DataStatistics;
import com.pinting.business.hessian.manage.message.ReqMsg_Data_HistoryData;
import com.pinting.business.hessian.manage.message.ResMsg_Data_DataStatistics;
import com.pinting.business.hessian.manage.message.ResMsg_Data_HistoryData;

/**
 * 
 * @author HuanXiong
 * @version $Id: DataStatisticsService.java, v 0.1 2015-12-14 下午7:19:20 HuanXiong Exp $
 */
public interface DataStatisticsService {
    public void dataStatistics(ReqMsg_Data_DataStatistics reqMsg, ResMsg_Data_DataStatistics resMsg);

    /**
     * 
     * @param req
     * @param res
     */
    public void historyData(ReqMsg_Data_HistoryData req, ResMsg_Data_HistoryData res);
}
