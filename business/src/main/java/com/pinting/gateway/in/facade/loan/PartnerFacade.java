package com.pinting.gateway.in.facade.loan;

import com.pinting.business.accounting.loan.service.PartnerTransService;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.vo.LnLoanVO;
import com.pinting.gateway.hessian.message.loan.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by 剑钊
 *
 * @2016/10/17 19:44.
 */
@Component("Partner")
public class PartnerFacade {

    @Autowired
    private PartnerTransService partnerTransService;

    public void marketingTrans(G2BReqMsg_Partner_MarketingTrans req, G2BResMsg_Partner_MarketingTrans res){

        partnerTransService.marketingTrans(req);

    }

    public void queryMarketingTrans(G2BReqMsg_Partner_QueryMarketingTrans req, G2BResMsg_Partner_QueryMarketingTrans res){

        LnLoanVO lnLoanVO=partnerTransService.queryMarketingTransStatus(req);

        if(lnLoanVO!=null){

            res.setOrderNo(lnLoanVO.getPartnerOrderNo());
            res.setChannel(lnLoanVO.getChannel());
            res.setResultCode(lnLoanVO.getStatus());
            res.setResultMsg(lnLoanVO.getReturnMsg());
            res.setPayTime(lnLoanVO.getLoanTime());

        }else {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,"找不到营销代付信息");
        }
    }

    public void queryBalance(G2BReqMsg_Partner_QueryBalance req, G2BResMsg_Partner_QueryBalance res){

        res.setBalance(partnerTransService.queryBalance(req));
    }
}
