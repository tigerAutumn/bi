package com.pinting.gateway.baofoo.out.service.impl;

import com.pinting.gateway.baofoo.out.enums.BaoFooTxnType;
import com.pinting.gateway.baofoo.out.enums.QuickPayRespCode;
import com.pinting.gateway.baofoo.out.model.req.PrepareQuickPayReq;
import com.pinting.gateway.baofoo.out.model.req.QuickPayReq;
import com.pinting.gateway.baofoo.out.model.req.QuickPayStatusReq;
import com.pinting.gateway.baofoo.out.model.resp.PrepareQuickPayResp;
import com.pinting.gateway.baofoo.out.model.resp.QuickPayResp;
import com.pinting.gateway.baofoo.out.model.resp.QuickPayStatusResp;
import com.pinting.gateway.baofoo.out.service.AttestationPayService;
import com.pinting.gateway.baofoo.out.util.BaoFooHttpUtil;
import com.pinting.gateway.baofoo.out.util.BaoFooMessageUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by 剑钊 on 2016/7/19.
 */
@Service
public class AttestationPayServiceImpl implements AttestationPayService {


    @Override
    public PrepareQuickPayResp prepareQuickPay(PrepareQuickPayReq req) throws Exception {

        HashMap<String, String> reqParamMap = new HashMap<>();
        //私有参数
        reqParamMap.put("txn_sub_type", BaoFooTxnType.TXN_SUB_TYPE_PREPARED_QUICK_PAY);

        //向宝付发送请求
        String resp = BaoFooHttpUtil.requestForm(BaoFooHttpUtil.baofooQuickUrl, req, reqParamMap);

        return (PrepareQuickPayResp) BaoFooMessageUtil.parseResp(resp, "PrepareQuickPayResp");
    }

    @Override
    public QuickPayResp quickPay(QuickPayReq req) throws Exception {

        HashMap<String, String> reqParamMap = new HashMap<>();
        //私有参数
        reqParamMap.put("txn_sub_type", BaoFooTxnType.TXN_SUB_TYPE_QUICK_PAY);

        //向宝付发送请求
        String resp = BaoFooHttpUtil.requestForm(BaoFooHttpUtil.baofooQuickUrl, req, reqParamMap);

        if (StringUtils.isBlank(resp)) {
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
        }

        QuickPayResp res = (QuickPayResp) BaoFooMessageUtil.parseResp(resp, "QuickPayResp");

        //成功
        if (res.getResp_code().equals(QuickPayRespCode.SUCCESS_CODE_0000.getCode())) {
            return res;
        } else {
            //失败
            if (QuickPayRespCode.find(res.getResp_code())!=null && QuickPayRespCode.find(res.getResp_code()).getStatus().contains("FAIL")) {
                throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, res.getResp_code() + "," + res.getResp_msg());
            }
            //处理中
            else {
                throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
            }
        }
    }

    @Override
    public QuickPayStatusResp quickPayStatusQuery(QuickPayStatusReq req) throws Exception {
        HashMap<String, String> reqParamMap = new HashMap<>();
        //私有参数
        reqParamMap.put("txn_sub_type", BaoFooTxnType.TXN_SUB_TYPE_QUICK_PAY_STATUS_QUERY);

        //向宝付发送请求
        String resp = BaoFooHttpUtil.requestForm(BaoFooHttpUtil.baofooQuickUrl, req, reqParamMap);
        if (StringUtils.isBlank(resp)) {
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
        }

        QuickPayStatusResp res = (QuickPayStatusResp) BaoFooMessageUtil.parseResp(resp, "QuickPayStatusResp");

        //成功
        if (res.getResp_code().equals(QuickPayRespCode.SUCCESS_CODE_0000.getCode())) {
            return res;
        }else {
            //失败
            if(QuickPayRespCode.find(res.getResp_code())!=null && QuickPayRespCode.find(res.getResp_code()).getStatus().equals("BIZ_FAIL")){
                throw new PTMessageException(PTMessageEnum.BIZ_PAY_FAIL, ":" + res.getResp_code() + "," + res.getResp_msg());
            }else {
                throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
            }

        }

    }
}
