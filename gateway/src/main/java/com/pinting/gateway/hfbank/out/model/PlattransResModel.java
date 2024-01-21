package com.pinting.gateway.hfbank.out.model;

/**
 * 平台非存管账户出金响应信息
 * Created by shh on 2017/4/1.
 */
public class PlattransResModel extends BaseResModel {

    /* 返回业务数据 */
    private String data;
    /* 系统处理日期(yyyyMMddHHmmss) */
    private String process_date;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getProcess_date() {
        return process_date;
    }

    public void setProcess_date(String process_date) {
        this.process_date = process_date;
    }
}
