package com.pinting.gateway.baofoo.out.service.impl;

import com.pinting.gateway.baofoo.out.model.req.BalanceQueryReq;
import com.pinting.gateway.baofoo.out.model.req.CheckAccountFileReq;
import com.pinting.gateway.baofoo.out.model.resp.BalanceQueryResp;
import com.pinting.gateway.baofoo.out.model.resp.BalanceResp;
import com.pinting.gateway.baofoo.out.model.resp.Pay4AnotherResp;
import com.pinting.gateway.baofoo.out.service.AccountService;
import com.pinting.gateway.baofoo.out.util.BaoFooHttpUtil;
import com.pinting.gateway.baofoo.out.util.PartnerBaoFooEnv;
import com.pinting.gateway.baofoo.out.util.PartnerBaoFooEnvUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.util.Constants;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by 剑钊 on 2016/7/25.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Override
    public void downloadCheckAccountFile(CheckAccountFileReq req) throws Exception {

        if(StringUtils.isBlank(req.getPartner())) {
            //币港湾与宝付对账
            BaoFooHttpUtil.requestForm(req);
        }else if (Constants.BGW_BAOFOO_ASSIST.equals(req.getPartner())) {
        	BaoFooHttpUtil.requestFormBaoFooAssist(req);
		}else {
            //其他对账方与宝付对账
            PartnerBaoFooEnv transferEnv= PartnerBaoFooEnvUtil.transferEnvMap.get(req.getPartner());
            BaoFooHttpUtil.requestForm(req,transferEnv);
        }
    }

    @Override
    public BalanceResp<BalanceQueryResp> queryBalance(BalanceQueryReq req) throws Exception {

        PartnerBaoFooEnv transferEnv= PartnerBaoFooEnvUtil.transferEnvMap.get(req.getPartner());
        String resp=BaoFooHttpUtil.requestForm(req.getAccountType(),transferEnv);

        BalanceResp<BalanceQueryResp> balanceQueryResp = new BalanceResp<>();
        balanceQueryResp = (BalanceResp<BalanceQueryResp>) balanceQueryResp.str2Obj(resp);

        if (balanceQueryResp.getTrans_content().getTrans_head().getReturn_code().equals("0000")) {
            return balanceQueryResp;
        } else{
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
        }
    }
}
