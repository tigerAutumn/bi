package com.pinting.gateway.baofoo.out.service.impl;

import com.pinting.gateway.baofoo.out.enums.BaoFooTxnType;
import com.pinting.gateway.baofoo.out.enums.QuickPayRespCode;
import com.pinting.gateway.baofoo.out.model.req.SendSmsReq;
import com.pinting.gateway.baofoo.out.model.resp.SendSmsResp;
import com.pinting.gateway.baofoo.out.service.SmsService;
import com.pinting.gateway.baofoo.out.util.BaoFooHttpUtil;
import com.pinting.gateway.baofoo.out.util.BaoFooMessageUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by 剑钊 on 2016/8/5.
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Override
    public SendSmsResp sendSms(SendSmsReq req) throws Exception {

        HashMap<String, String> reqParamMap = new HashMap<>();
        //私有参数
        reqParamMap.put("txn_sub_type", BaoFooTxnType.TXN_SUB_TYPE_BIND_SEND_SMS);

        //向宝付发送请求
        String resp = BaoFooHttpUtil.requestForm(BaoFooHttpUtil.baofooQuickUrl, req,reqParamMap);

        if (StringUtils.isBlank(resp)) {
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
        }

        SendSmsResp res=(SendSmsResp) BaoFooMessageUtil.parseResp(resp, "SendSmsResp");

        if(StringUtils.isBlank(res.getResp_code())){
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
        }

        if(!res.getResp_code().equals(QuickPayRespCode.SUCCESS_CODE_0000.getCode())){
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION,res.getResp_code()+","+res.getResp_msg());
        }
        return res;
    }
}
