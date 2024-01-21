package com.pinting.gateway.hfbank.out.model;

/**
 * Author:      shh
 * Date:        2017/5/9
 * Description: 对账文件账户余额数据请求信息
 */
public class BalanceInfoReqModel extends BaseReqModel {

    /* 对账日期(yyyyMMdd) */
    private String paycheck_date;

    /* 是否预对账0-不是，1-是 */
    private String precheck_flag;

    /* 开始时间(预对账必输：yyyyMMddHHmmss) */
    private String begin_time;

    /* 结束时间(预对账必输：yyyyMMddHHmmss) */
    private String end_time;

    public String getPaycheck_date() {
        return paycheck_date;
    }

    public void setPaycheck_date(String paycheck_date) {
        this.paycheck_date = paycheck_date;
    }

    public String getPrecheck_flag() {
        return precheck_flag;
    }

    public void setPrecheck_flag(String precheck_flag) {
        this.precheck_flag = precheck_flag;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

}
