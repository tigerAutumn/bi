package com.pinting.business.accounting.finance.model;

/**
 * Author:      cyb
 * Date:        2017/8/30
 * Description:
 */
public class ProductType {

    // 站岗户编码
    private String authCode;

    // 红包户编码
    private String redCode;

    // 补差户编码
    private String diffCode;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getRedCode() {
        return redCode;
    }

    public void setRedCode(String redCode) {
        this.redCode = redCode;
    }

    public String getDiffCode() {
        return diffCode;
    }

    public void setDiffCode(String diffCode) {
        this.diffCode = diffCode;
    }
}
