package com.pinting.business.model.vo;

/**
 * Author:      cyb
 * Date:        2017/1/20
 * Description:
 */
public class RepayInfoVO {

    private Double planInterest;

    private Double planPrincipal;

    private Integer serialId;

    public Double getPlanInterest() {
        return planInterest;
    }

    public void setPlanInterest(Double planInterest) {
        this.planInterest = planInterest;
    }

    public Double getPlanPrincipal() {
        return planPrincipal;
    }

    public void setPlanPrincipal(Double planPrincipal) {
        this.planPrincipal = planPrincipal;
    }

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }
}
