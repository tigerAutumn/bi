package com.pinting.business.accounting.loan.service.impl.process;

import com.pinting.business.accounting.loan.service.LoanPaymentService;
import com.pinting.business.accounting.loan.service.PartnerTransService;
import com.pinting.business.model.LnBindCard;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnMarketGrantRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 营销代付通知线程
 * Created by 剑钊 on 2016/8/30.
 */
public class MarketingProcess implements Runnable{

    private Logger log = LoggerFactory.getLogger(MarketingProcess.class);

    private PartnerTransService partnerTransService;

    private LnMarketGrantRecord record;

    private String errMsg;

    public Logger getLog() {
        return log;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public PartnerTransService getPartnerTransService() {
        return partnerTransService;
    }

    public void setPartnerTransService(PartnerTransService partnerTransService) {
        this.partnerTransService = partnerTransService;
    }

    public LnMarketGrantRecord getRecord() {
        return record;
    }

    public void setRecord(LnMarketGrantRecord record) {
        this.record = record;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        partnerTransService.notify2Partner(record,errMsg);
    }
}
