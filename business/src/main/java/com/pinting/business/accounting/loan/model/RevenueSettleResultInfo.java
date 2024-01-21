package com.pinting.business.accounting.loan.model;

import com.pinting.core.hessian.msg.ReqMsg;

import java.util.Date;
import java.util.List;

/**
 * Created by zhangbao on 2017/7/6.
 */
public class RevenueSettleResultInfo extends ReqMsg {

    private String orderNo;//我方订单号
    private Boolean isSuc;
    private List<Integer> repeatRepayIds;
    private String returnCode;//返回码
    private String returnMsg;//返回信息

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Boolean getSuc() {
        return isSuc;
    }

    public void setSuc(Boolean suc) {
        isSuc = suc;
    }

    public List<Integer> getRepeatRepayIds() {
        return repeatRepayIds;
    }

    public void setRepeatRepayIds(List<Integer> repeatRepayIds) {
        this.repeatRepayIds = repeatRepayIds;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }
}
