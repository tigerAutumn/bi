package com.pinting.gateway.business.in.facade;

import com.pinting.gateway.bird.out.model.MarketTransReq;
import com.pinting.gateway.bird.out.service.NoticeService;
import com.pinting.gateway.hessian.message.loan.B2GReqMsg_MarketNotice_NoticeMarketTrans;
import com.pinting.gateway.hessian.message.loan.B2GResMsg_MarketNotice_NoticeMarketTrans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by 剑钊
 *
 * @2016/10/18 23:33.
 */
@Component("MarketNotice")
public class MarketNoticeFacade {

    @Autowired
    private NoticeService noticeService;

    public void  noticeMarketTrans(B2GReqMsg_MarketNotice_NoticeMarketTrans req, B2GResMsg_MarketNotice_NoticeMarketTrans res) throws Exception {

        MarketTransReq marketTransReq=new MarketTransReq();
        marketTransReq.setOrderNo(req.getOrderNo());
        marketTransReq.setChannel(req.getChannel());
        marketTransReq.setResultMsg(req.getResultMsg());
        marketTransReq.setResultCode(req.getResultCode());

        noticeService.noticeMarketTrans(marketTransReq);
    }
}
