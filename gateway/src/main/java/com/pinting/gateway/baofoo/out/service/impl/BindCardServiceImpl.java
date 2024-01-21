package com.pinting.gateway.baofoo.out.service.impl;

import com.pinting.gateway.baofoo.out.enums.BaoFooTxnType;
import com.pinting.gateway.baofoo.out.enums.QuickPayRespCode;
import com.pinting.gateway.baofoo.out.model.req.BindCardReq;
import com.pinting.gateway.baofoo.out.model.req.BindRelationQueryReq;
import com.pinting.gateway.baofoo.out.model.req.PrepareBindCardReq;
import com.pinting.gateway.baofoo.out.model.req.UnbindCardReq;
import com.pinting.gateway.baofoo.out.model.resp.*;
import com.pinting.gateway.baofoo.out.service.BindCardService;
import com.pinting.gateway.baofoo.out.util.*;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by 剑钊 on 2016/7/18.
 */
@Service
public class BindCardServiceImpl implements BindCardService {

    /**
     * 预绑卡
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public PrepareBindCardResp prepareBindCard(PrepareBindCardReq req) throws Exception {

        HashMap<String, String> reqParamMap = new HashMap<>();
        //私有参数
        reqParamMap.put("txn_sub_type", BaoFooTxnType.TXN_SUB_TYPE_PREPARED_BIND_CARD);
        reqParamMap.put("id_card_type", BaoFooTxnType.ID_CARD_TYPE);

        //向宝付发送请求
        String resp = BaoFooHttpUtil.requestForm(BaoFooHttpUtil.baofooQuickUrl, req,reqParamMap);

        return (PrepareBindCardResp) BaoFooMessageUtil.parseResp(resp, "PrepareBindCardResp");
    }

    /**
     * 确认绑卡
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public BindCardResp bindCard(BindCardReq req) throws Exception{

        HashMap<String, String> reqParamMap = new HashMap<>();
        //私有参数
        reqParamMap.put("txn_sub_type", BaoFooTxnType.TXN_SUB_TYPE_BIND_CARD);

        //向宝付发送请求
        String resp = BaoFooHttpUtil.requestForm(BaoFooHttpUtil.baofooQuickUrl, req,reqParamMap);

        if (StringUtils.isBlank(resp)) {
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
        }

        BindCardResp res=(BindCardResp) BaoFooMessageUtil.parseResp(resp, "BindCardResp");
        if(!res.getResp_code().equals(QuickPayRespCode.SUCCESS_CODE_0000.getCode())){
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION,res.getResp_code()+","+res.getResp_msg());
        }
        return res;
    }

    /**
     * 解除绑定
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public UnbindCardResp unbindCard(UnbindCardReq req) throws Exception {

        HashMap<String, String> reqParamMap = new HashMap<>();
        //私有参数
        reqParamMap.put("txn_sub_type", BaoFooTxnType.TXN_SUB_TYPE_UNBIND_CARD);

        //向宝付发送请求
        String resp = BaoFooHttpUtil.requestForm(BaoFooHttpUtil.baofooQuickUrl, req,reqParamMap);

        if (StringUtils.isBlank(resp)) {
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
        }

        UnbindCardResp res= (UnbindCardResp)BaoFooMessageUtil.parseResp(resp, "UnbindCardResp");
        if(!res.getResp_code().equals(QuickPayRespCode.SUCCESS_CODE_0000.getCode())){
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION,res.getResp_code()+","+res.getResp_msg());
        }
        return res;
    }

    /**
     * 查询绑定关系类交易
     * @param req
     * @return
     */
    @Override
    public BindRelationQueryResp bindRelationQuery(BindRelationQueryReq req) throws Exception{

        HashMap<String, String> reqParamMap = new HashMap<>();
        //私有参数
        reqParamMap.put("txn_sub_type", BaoFooTxnType.TXN_SUB_TYPE_BIND_RELATION_QUERY);

        //向宝付发送请求
        String resp = BaoFooHttpUtil.requestForm(BaoFooHttpUtil.baofooQuickUrl, req,reqParamMap);

        if (StringUtils.isBlank(resp)) {
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
        }

        return (BindRelationQueryResp)BaoFooMessageUtil.parseResp(resp, "BindRelationQueryResp");
    }


}
