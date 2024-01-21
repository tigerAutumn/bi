package com.pinting.business.model.vo;

public class BsSubAccountVO4Match {

    private Integer subAccountId; // 站岗户subAccountid

    private Integer redSubAccountId; // 红包户subAccountid

    private Double redAvailableBalance; // 红包户可用金额

    private Double availableBalance; // 站岗户可用金额

    public Integer getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Integer subAccountId) {
        this.subAccountId = subAccountId;
    }

    public Integer getRedSubAccountId() {
        return redSubAccountId;
    }

    public void setRedSubAccountId(Integer redSubAccountId) {
        this.redSubAccountId = redSubAccountId;
    }

    public Double getRedAvailableBalance() {
        return redAvailableBalance;
    }

    public void setRedAvailableBalance(Double redAvailableBalance) {
        this.redAvailableBalance = redAvailableBalance;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }
}
