package com.pinting.gateway.hfbank.out.model;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 账户余额明细查询响应信息
 */
public class GetAccountNBalaceResModel extends BaseResModel {

    /* 返回账户余额明细 */
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
