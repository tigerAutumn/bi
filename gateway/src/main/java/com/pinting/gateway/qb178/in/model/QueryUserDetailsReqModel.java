package com.pinting.gateway.qb178.in.model;


/**
 * Author:      shh
 * Date:        2017/7/28
 * Description: 查询会员详情请求信息
 */
public class QueryUserDetailsReqModel extends BaseReqModel {

    /* 会员账号(手机号) */
    private String user_account;

    /* 创建时间开始 */
    private String create_time_begin;

    /* 创建时间结束 */
    private String create_time_end;

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getCreate_time_begin() {
        return create_time_begin;
    }

    public void setCreate_time_begin(String create_time_begin) {
        this.create_time_begin = create_time_begin;
    }

    public String getCreate_time_end() {
        return create_time_end;
    }

    public void setCreate_time_end(String create_time_end) {
        this.create_time_end = create_time_end;
    }
}
