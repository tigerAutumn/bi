package com.pinting.manage.service;

import com.pinting.manage.model.SysReturnMoneyNoticeDo;

/**
 * Created by babyshark on 2017/7/14.
 */
public interface MSysReturnMoneyService {

    /**
     * 生成系统回款通知报文
     * @param productOrderNo
     * @return
     */
    SysReturnMoneyNoticeDo generateSysReturnMoneyPlan(String productOrderNo);

    /**
     * 人工触发系统回款
     * @param productOrderNo
     */
    void sysReturnMoneySucc(String productOrderNo);
}
