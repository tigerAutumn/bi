package com.pinting.gateway.hfbank.out.model;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 快捷充值确认请求信息
 */
public class DirectQuickConfirmReqModel extends BaseReqModel {

    /* 短信验证码 */
    private String identifying_code;
    /* 原快捷支付申请订单号 */
    private String origin_order_no;
    /* 签名数据 */
    private String pay_code;
    /* 备注 */
    private String remark;

    public String getIdentifying_code() {
        return identifying_code;
    }

    public void setIdentifying_code(String identifying_code) {
        this.identifying_code = identifying_code;
    }

    public String getOrigin_order_no() {
        return origin_order_no;
    }

    public void setOrigin_order_no(String origin_order_no) {
        this.origin_order_no = origin_order_no;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
