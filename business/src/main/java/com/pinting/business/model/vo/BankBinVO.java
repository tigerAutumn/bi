/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.model.vo;

import com.pinting.business.model.BsCardBin;

/**
 * 
 * @author HuanXiong
 * @version $Id: BankBinVO.java, v 0.1 2015-11-14 下午2:15:26 HuanXiong Exp $
 */
public class BankBinVO extends BsCardBin {

    private String bankName;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

}
