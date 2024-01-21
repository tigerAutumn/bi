package com.pinting.gateway.hfbank.out.model;

import java.util.List;

/**
 * 平台非存管账户出金请求信息
 * Created by shh on 2017/4/1.
 */
public class PlattransReqModel extends BaseResModel {

    /* 账户类型(21-垫付账户) */
    private String account_type;
    /* 金额 */
    private Double amount;
    /* 附录5.3代发指令 */
    private List<PlattransDetailReqModel> data;
    /* 异步通知地址 */
    private String notify_url;
    /* 备注 */
    private String remark;

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public List<PlattransDetailReqModel> getData() {
        return data;
    }

    public void setData(List<PlattransDetailReqModel> data) {
        this.data = data;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
