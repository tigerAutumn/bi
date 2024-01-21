package com.pinting.business.model.vo;

/**
 * Created by cyb on 2017/11/5.
 */
public class ActivityGiftNumberVO extends ActivityBaseVO {

    /* 礼品剩余个数 */
    private Integer number;

    /* 是否填写过地址 */
    private boolean haveAddress;

    /* 兑奖状态 */
    private String status;

    /* 已兑奖的ID */
    private Integer luckyDrawId;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public boolean isHaveAddress() {
        return haveAddress;
    }

    public void setHaveAddress(boolean haveAddress) {
        this.haveAddress = haveAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getLuckyDrawId() {
        return luckyDrawId;
    }

    public void setLuckyDrawId(Integer luckyDrawId) {
        this.luckyDrawId = luckyDrawId;
    }
}
