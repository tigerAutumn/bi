package com.pinting.business.model.dto;

import com.pinting.business.model.LnBindCard;
import com.pinting.business.model.LnLoan;

/**
 * @title 借款队列
 * Created by 剑钊 on 2016/11/22.
 */
public class LoanQueueDTO {

    private LnLoan lnLoan;

    private LnBindCard lnBindCard;

    private String channel;

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
}
