package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public class B2GResMsg_BaoFooQuickPay_PartnerPay4Trans extends ResMsg {

    /**
     * 宝付批次号
     */
    private String trans_batchid;

    /**
     * 商户订单号
     */
    private String trans_no;

    /**
     * 转账金额
     */
    private String trans_money;

    /**
     * 宝付代付状态
     */
    private String res_Code;

    /**
     * 宝付代付返回信息
     */
    private String res_Msg;


    public String getTrans_batchid() {
        return trans_batchid;
    }

    public void setTrans_batchid(String trans_batchid) {
        this.trans_batchid = trans_batchid;
    }

    public String getTrans_no() {
        return trans_no;
    }

    public void setTrans_no(String trans_no) {
        this.trans_no = trans_no;
    }

    public String getTrans_money() {
        return trans_money;
    }

    public void setTrans_money(String trans_money) {
        this.trans_money = trans_money;
    }

    public String getRes_Code() {
        return res_Code;
    }

    public void setRes_Code(String res_Code) {
        this.res_Code = res_Code;
    }

    public String getRes_Msg() {
        return res_Msg;
    }

    public void setRes_Msg(String res_Msg) {
        this.res_Msg = res_Msg;
    }
}
