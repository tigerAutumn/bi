package com.pinting.gateway.zsd.out.model;

import java.io.Serializable;

/**
 * Author:      shh
 * Date:        2017/8/30
 * Description: 推送银行卡限额请求信息 银行限额对象
 */
public class Limit implements Serializable {

    /* 日限额 */
    private String day;

    /* 单笔限额 */
    private String single;

    /* 银行名称 */
    private String bankName;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
