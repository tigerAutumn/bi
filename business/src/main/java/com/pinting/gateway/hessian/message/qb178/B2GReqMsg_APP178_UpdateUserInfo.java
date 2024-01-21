package com.pinting.gateway.hessian.message.qb178;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      shh
 * Date:        2017/7/31
 * Description: 更新会员信息入参
 */
public class B2GReqMsg_APP178_UpdateUserInfo extends ReqMsg {

    private static final long serialVersionUID = -4506624152428975872L;

    /* 会员账号 */
    private String user_account;

    /* 证件类型：居民身份证（P01）*/
    private String id_kind;

    /* 证件号 */
    private String id_no;

    /* 银行账号 */
    private String bank_account;

    /* 银行产品代码 */
    private String bank_pro_code;

    /* 合作商流水号，全局唯一，建议时间戳 */
    private String serialNo;

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getId_kind() {
        return id_kind;
    }

    public void setId_kind(String id_kind) {
        this.id_kind = id_kind;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    public String getBank_pro_code() {
        return bank_pro_code;
    }

    public void setBank_pro_code(String bank_pro_code) {
        this.bank_pro_code = bank_pro_code;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
