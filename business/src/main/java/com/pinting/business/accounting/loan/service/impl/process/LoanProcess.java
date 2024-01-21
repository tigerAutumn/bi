package com.pinting.business.accounting.loan.service.impl.process;

import com.pinting.business.accounting.loan.service.LoanPaymentService;
import com.pinting.business.model.LnBindCard;
import com.pinting.business.model.LnLoan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 债权匹配并发起支付
 * Created by 剑钊 on 2016/8/30.
 */
public class LoanProcess implements Runnable{

    private Logger log = LoggerFactory.getLogger(LoanProcess.class);

    private LoanPaymentService loanPaymentService;

    private LnLoan lnLoan;

    private LnBindCard lnBindCard;

    private String channel;

    @Override
    public void run() {
        loanPaymentService.matchAndLoanPay(lnLoan,lnBindCard,channel);
    }

    public LnLoan getLnLoan() {
        return lnLoan;
    }

    public void setLnLoan(LnLoan lnLoan) {
        this.lnLoan = lnLoan;
    }

    public LnBindCard getLnBindCard() {
        return lnBindCard;
    }

    public void setLnBindCard(LnBindCard lnBindCard) {
        this.lnBindCard = lnBindCard;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setLoanPaymentService(LoanPaymentService loanPaymentService) {
        this.loanPaymentService = loanPaymentService;
    }
}
