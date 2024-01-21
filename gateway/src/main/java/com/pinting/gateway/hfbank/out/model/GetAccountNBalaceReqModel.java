package com.pinting.gateway.hfbank.out.model;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 账户余额明细查询请求信息
 */
public class GetAccountNBalaceReqModel extends BaseReqModel {

    /* 账户编号 */
    private String account;
    /* 平台（1--自有， 11--在途垫付）  个人（投资账户 12、融资 13） */
    private String acct_type;
    /* 针对个人（现金01、在途02） */
    private String fund_type;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAcct_type() {
        return acct_type;
    }

    public void setAcct_type(String acct_type) {
        this.acct_type = acct_type;
    }

    public String getFund_type() {
        return fund_type;
    }

    public void setFund_type(String fund_type) {
        this.fund_type = fund_type;
    }
}
