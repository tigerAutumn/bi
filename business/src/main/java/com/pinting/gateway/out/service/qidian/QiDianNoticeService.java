package com.pinting.gateway.out.service.qidian;

import com.pinting.gateway.hessian.message.qidian.B2GReqMsg_QiDianNotice_CustomerInfoSync;
import com.pinting.gateway.hessian.message.qidian.B2GReqMsg_QiDianNotice_OrderInfoSync;
import com.pinting.gateway.hessian.message.qidian.B2GResMsg_QiDianNotice_CustomerInfoSync;
import com.pinting.gateway.hessian.message.qidian.B2GResMsg_QiDianNotice_OrderInfoSync;

/**
 * 
 * @project business
 * @title QiDianNoticeService.java
 * @author Dragon & cat
 * @date 2018-3-22
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public interface QiDianNoticeService {

    /**
     * 用户信息同步
     * @param req
     * @return
     */
    B2GResMsg_QiDianNotice_CustomerInfoSync customerInfoSync(B2GReqMsg_QiDianNotice_CustomerInfoSync req);
    
    /**
     * 用户信息同步
     * @param req
     * @return
     */
    B2GResMsg_QiDianNotice_OrderInfoSync orderInfoSync(B2GReqMsg_QiDianNotice_OrderInfoSync req);
    
}
