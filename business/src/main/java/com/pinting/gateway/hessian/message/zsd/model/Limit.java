package com.pinting.gateway.hessian.message.zsd.model;

import java.io.Serializable;

/**
 * Author:      shh
 * Date:        2017/8/30
 * Description: 查询银行卡限额响应信息 银行限额对象
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
