/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_Finance_WithdrawIndex.java, v 0.1 2016-1-7 下午3:17:41 HuanXiong Exp $
 */
public class ResMsg_ChannelWithdraw_WithdrawIndex extends ResMsg {

    /**  */
    private static final long serialVersionUID = 4099236767657996702L;

    private ArrayList<HashMap<String, Object>> channelBankCardList;
    
    private List<Map<String, Object>> channelList;
    
    private Double amount;
    
    private String withdrawStatus;  // 1、取现：WITHDRAW；2、已完成：OVER；3、PROCESS：处理中
    
    private String oneTop;
    
    private String dayTop;
    
    public String getOneTop() {
        return oneTop;
    }

    public void setOneTop(String oneTop) {
        this.oneTop = oneTop;
    }

    public String getDayTop() {
        return dayTop;
    }

    public void setDayTop(String dayTop) {
        this.dayTop = dayTop;
    }

    public ArrayList<HashMap<String, Object>> getChannelBankCardList() {
        return channelBankCardList;
    }

    public void setChannelBankCardList(ArrayList<HashMap<String, Object>> channelBankCardList) {
        this.channelBankCardList = channelBankCardList;
    }

    public List<Map<String, Object>> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<Map<String, Object>> channelList) {
        this.channelList = channelList;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(String withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }
    
}
