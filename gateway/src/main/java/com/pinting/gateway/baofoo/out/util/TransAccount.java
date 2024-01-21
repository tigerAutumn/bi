package com.pinting.gateway.baofoo.out.util;

/**
 * @title 宝付转账账户信息
 * Created by 剑钊 on 2016/11/2.
 */
public class TransAccount {

    /**
     * 转入方账号（邮箱）
     */
    private String accountTo;

    /**
     * 转入方会员号（用于宝付转账）
     */
    private String memberIdTo;

    /**
     * 转入方公司名称
     */
    private String toAcctName;

    public String getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(String accountTo) {
        this.accountTo = accountTo;
    }

    public String getMemberIdTo() {
        return memberIdTo;
    }

    public void setMemberIdTo(String memberIdTo) {
        this.memberIdTo = memberIdTo;
    }

    public String getToAcctName() {
        return toAcctName;
    }

    public void setToAcctName(String toAcctName) {
        this.toAcctName = toAcctName;
    }
}
