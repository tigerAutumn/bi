package com.pinting.gateway.baofoo.out.service.impl;

import com.alibaba.fastjson.JSON;
import com.pinting.gateway.baofoo.out.enums.BaoFooTxnType;
import com.pinting.gateway.baofoo.out.model.req.CardBinQueryReq;
import com.pinting.gateway.baofoo.out.model.resp.CardBinQueryResp;
import com.pinting.gateway.baofoo.out.service.SecurityService;
import com.pinting.gateway.baofoo.out.util.BaoFooHttpUtil;
import com.pinting.gateway.baofoo.out.util.RsaCodingUtil;
import com.pinting.gateway.baofoo.out.util.SecurityUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by 剑钊 on 2016/8/24.
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public CardBinQueryResp queryCardBin(CardBinQueryReq req) throws Exception {

        HashMap<String, String> reqParamMap = new HashMap<>();
        //私有参数
        reqParamMap.put("txn_sub_type", BaoFooTxnType.TXN_SUB_TYPE_CARD_BIN_QUERY);

        //向宝付发送请求
        String respStr = BaoFooHttpUtil.securityServiceRequestForm(BaoFooHttpUtil.securityUrl, req, reqParamMap);
        //System.out.println(respStr);
        respStr = RsaCodingUtil.decryptByPubCerFile(respStr, BaoFooHttpUtil.securitycerpath);
        if(respStr.isEmpty()){//判断解密是否正确。如果为空则宝付公钥不正确
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,"解密公钥错误");
        }
        //解密返回值
        respStr = SecurityUtil.Base64Decode(respStr);
        CardBinQueryResp res = JSON.parseObject(respStr,CardBinQueryResp.class);

        if(!res.getResp_code().equals("0000")){
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION,  res.getResp_code() + "," + res.getResp_msg());
        }

        return res;
    }
}
