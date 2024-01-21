package com.pinting.gateway.hfbank.out.model;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 资金余额查询请求信息
 */
public class GetAccountInfoReqModel extends BaseReqModel {

    /* 账户编号 */
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
