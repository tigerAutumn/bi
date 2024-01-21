package com.pinting.gateway.hfbank.out.model;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 标的账户余额查询响应信息
 */
public class GetProductNBalanceResModel extends BaseResModel {

    /* 标的账户余额 */
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
