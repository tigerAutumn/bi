package com.pinting.gateway.baofoo.out.service;

import com.pinting.gateway.baofoo.out.model.req.SendSmsReq;
import com.pinting.gateway.baofoo.out.model.resp.SendSmsResp;

/**
 * Created by 剑钊 on 2016/8/5.
 */
public interface SmsService {

    /**
     * 发送短信
     * @param req
     * @return
     */
    SendSmsResp sendSms(SendSmsReq req) throws Exception;
}
