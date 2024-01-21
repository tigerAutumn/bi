/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.model.vo;

import com.pinting.business.model.BsChannelBankcard;

/**
 * 
 * @author HuanXiong
 * @version $Id: BsChannelBankcardVO.java, v 0.1 2016-1-8 下午2:33:52 HuanXiong Exp $
 */
public class BsChannelBankcardVO extends BsChannelBankcard {

    private Double dayTop;
    
    private Double oneTop;
    
    private String dayTopStr;
    
    private String oneTopStr;
    
    public String getDayTopStr() {
        return dayTopStr;
    }

    public void setDayTopStr(String dayTopStr) {
        this.dayTopStr = dayTopStr;
    }

    public String getOneTopStr() {
        return oneTopStr;
    }

    public void setOneTopStr(String oneTopStr) {
        this.oneTopStr = oneTopStr;
    }

    public Double getDayTop() {
        return dayTop;
    }

    public void setDayTop(Double dayTop) {
        this.dayTop = dayTop;
    }

    public Double getOneTop() {
        return oneTop;
    }

    public void setOneTop(Double oneTop) {
        this.oneTop = oneTop;
    }
    
}
