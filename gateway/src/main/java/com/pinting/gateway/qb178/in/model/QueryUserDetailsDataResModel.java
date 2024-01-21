package com.pinting.gateway.qb178.in.model;

import java.io.Serializable;

/**
 * Author:      shh
 * Date:        2017/7/28
 * Description: 查询会员详情响应data数据
 */
public class QueryUserDetailsDataResModel implements Serializable {

    private static final long serialVersionUID = -7497989207615240461L;

    /* 会员账号（手机号） */
    private String user_account;

    /* 证件号 */
    private String id_no;

    /* 证件类型 居民身份证（P01）*/
    private String id_kine;

    /* 银行卡号 */
    private String bank_account;

    /* 持卡人姓名 */
    private String holder_name;

    /* 认证时间 yyyyMmddHHmmss */
    private String create_time;

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getId_kine() {
        return id_kine;
    }

    public void setId_kine(String id_kine) {
        this.id_kine = id_kine;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public String getHolder_name() {
        return holder_name;
    }

    public void setHolder_name(String holder_name) {
        this.holder_name = holder_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
