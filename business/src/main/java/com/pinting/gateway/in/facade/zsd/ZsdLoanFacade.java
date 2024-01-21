package com.pinting.gateway.in.facade.zsd;

import java.util.Date;

import com.pinting.business.service.loan.LoanQueryService;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdLoan_QueryDailyLimit;
import com.pinting.gateway.hessian.message.zsd.G2BResMsg_ZsdLoan_QueryDailyLimit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author:      shh
 * Date:        2017/8/30
 * Description: 赞时贷查询币港湾当日可用额度
 */
@Component("ZsdLoan")
public class ZsdLoanFacade {

    private Logger log = LoggerFactory.getLogger(ZsdLoanFacade.class);
    @Autowired
	private LoanQueryService loanQueryService;
    /**
     * 赞时贷查询币港湾当日可用额度
     * @param req
     * @param res
     * @throws Exception
     */
    public void queryDailyLimit(G2BReqMsg_ZsdLoan_QueryDailyLimit req, G2BResMsg_ZsdLoan_QueryDailyLimit res) throws Exception{
		Double zsdDailyAmount = loanQueryService.queryZsdDailyAmount(new Date());
		zsdDailyAmount = zsdDailyAmount == null ? 0d:zsdDailyAmount;
		log.info("收到赞时贷查询币港湾当日可用额度请求,可借额度为:"+String.valueOf(MoneyUtil.multiply(zsdDailyAmount, 100).longValue()));
		res.setAmountNoLimit(String.valueOf(MoneyUtil.multiply(zsdDailyAmount, 100).longValue()));
    }

}
