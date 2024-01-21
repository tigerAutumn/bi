package com.pinting.business.model.vo;

import com.pinting.business.model.BsTicketGrantPlanCheck;

/**
 * 优惠券审核信息
 * Created by cyb on 2018/3/30.
 */
public class TicketCheckVO extends BsTicketGrantPlanCheck {

    private Double investLimit;

    private Double ticketApr;

    private String productLimit; // BIGANGWAN_SERIAL（港湾系列） YONGJIN_SERIAL（涌金系列） KUAHONG_SERIAL（跨虹系列） BAOXIN_SERIAL（保信系列） 多个产品限制用逗号隔开

    private String termLimit;   // 30,90,180,365（天）    多个产品期限限制用逗号隔开

    public Double getInvestLimit() {
        return investLimit;
    }

    public void setInvestLimit(Double investLimit) {
        this.investLimit = investLimit;
    }

    public Double getTicketApr() {
        return ticketApr;
    }

    public void setTicketApr(Double ticketApr) {
        this.ticketApr = ticketApr;
    }

    public String getProductLimit() {
        return productLimit;
    }

    public void setProductLimit(String productLimit) {
        this.productLimit = productLimit;
    }

    public String getTermLimit() {
        return termLimit;
    }

    public void setTermLimit(String termLimit) {
        this.termLimit = termLimit;
    }
}
