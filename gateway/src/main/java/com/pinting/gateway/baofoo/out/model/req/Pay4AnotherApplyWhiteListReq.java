package com.pinting.gateway.baofoo.out.model.req;

/**
 * Created by 剑钊 on 2016/7/21.
 */
public class Pay4AnotherApplyWhiteListReq extends BaoFooOutBaseReq{

    /**
     * 收款人姓名
     */
    private String to_acc_name;

    /**
     * 收款人银行帐号
     */
    private String to_acc_no;

    public String getTo_acc_name() {
        return to_acc_name;
    }

    public void setTo_acc_name(String to_acc_name) {
        this.to_acc_name = to_acc_name;
    }

    public String getTo_acc_no() {
        return to_acc_no;
    }

    public void setTo_acc_no(String to_acc_no) {
        this.to_acc_no = to_acc_no;
    }
}
