package com.pinting.gateway.hfbank.out.model;

/**
 * Description: 充值订单状态查询请求信息
 */
public class QueryFundOrderReqModel extends BaseReqModel {

    //原充值订单号
    private String original_serial_no;

    //发生金额
    private Double occur_balance;

    public String getOriginal_serial_no() {
        return original_serial_no;
    }

    public void setOriginal_serial_no(String original_serial_no) {
        this.original_serial_no = original_serial_no;
    }

    public Double getOccur_balance() {
        return occur_balance;
    }

    public void setOccur_balance(Double occur_balance) {
        this.occur_balance = occur_balance;
    }
}
