package com.pinting.gateway.baofoo.out.model.req;

/**
 * Created by 剑钊
 *
 * @2016/10/19 17:26.
 */
public class BalanceQueryReq extends BaoFooOutBaseReq{

    /**
     * 合作方编码
     */
    private String partner;

    /**
     * 账户类型
     * 0:全部；
     * 1:基本账户;
     * 2:未结算账户;
     * 3:冻结账户;
     * 4:保证金账户;
     * 5:资金托管账户
     */
    private String accountType;

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
