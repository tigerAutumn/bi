package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Created by cyb on 2017/12/12.
 */
public class ResMsg_Activity_ChrismasEveMakeupDraw extends ResMsg {

    private static final long serialVersionUID = -5830998128700135270L;

    /* 中奖者手机号 */
    private String mobile;

    /* 奖品类型：1-一等奖；2-二等奖；3-三等奖；0-幸运奖 */
    private String type;

    private Double amount;

    private Integer userId;

    private Integer leftTimes;

    public Integer getLeftTimes() {
        return leftTimes;
    }

    public void setLeftTimes(Integer leftTimes) {
        this.leftTimes = leftTimes;
    }

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
}
