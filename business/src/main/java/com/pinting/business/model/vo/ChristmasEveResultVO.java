package com.pinting.business.model.vo;

/**
 * Created by cyb on 2017/12/11.
 */
public class ChristmasEveResultVO extends ActivityBaseVO {

    /* 中奖者手机号 */
    private String mobile;

    /* 奖品类型：1-一等奖；2-二等奖；3-三等奖；0-幸运奖 */
    private String type;

    private Double amount;

    private Integer userId;

    private Integer leftTimes;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLeftTimes() {
        return leftTimes;
    }

    public void setLeftTimes(Integer leftTimes) {
        this.leftTimes = leftTimes;
    }
}
