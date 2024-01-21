package com.pinting.gateway.baofoo.out.model.resp;

/**
 * Created by 剑钊 on 2016/7/23.
 */
public class PcStatusQueryResp {

    /**
     * 商户订单号
     */
    private String transID;

    /**
     * 支付结果
     * Y成功 F失败 P处理中 N没有订单
     */
    private String checkResult;

    /**
     * 实际成功金额
     * 单位（分）
     */
    private String succMoney;

    /**
     * 支付完成时间
     * 格式：年年年年月月日日时时分分秒秒
     */
    private String succTime;

    public String getTransID() {
        return transID;
    }

    public void setTransID(String transID) {
        this.transID = transID;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getSuccMoney() {
        return succMoney;
    }

    public void setSuccMoney(String succMoney) {
        this.succMoney = succMoney;
    }

    public String getSuccTime() {
        return succTime;
    }

    public void setSuccTime(String succTime) {
        this.succTime = succTime;
    }
}
