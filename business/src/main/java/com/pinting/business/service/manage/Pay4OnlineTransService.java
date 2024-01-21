package com.pinting.business.service.manage;

import com.pinting.business.model.dto.Pay4OnlineTransDTO;

/**
 * @title 宝付账户间转账 （管理台）
 * Created by 剑钊 on 2016/11/16.
 */
public interface Pay4OnlineTransService {

    void pay4OnlineTrans(Pay4OnlineTransDTO pay4OnlineTransDTO);

    /**
     * 生成营销token
     * @return
     */
    String createMarketingToken();
}
