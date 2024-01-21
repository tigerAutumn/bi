/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.model.vo;

import com.pinting.business.model.BsAutoRedPacketRule;

/**
 * 
 * @author HuanXiong
 * @version $Id: AutoRedPacketTotalAmountVO.java, v 0.1 2016-4-6 下午12:14:52 HuanXiong Exp $
 */
public class AutoRedPacketTotalAmountVO extends BsAutoRedPacketRule {
    
    public Double subtract;

    public Double getSubtract() {
        return subtract;
    }

    public void setSubtract(Double subtract) {
        this.subtract = subtract;
    }
    
}
