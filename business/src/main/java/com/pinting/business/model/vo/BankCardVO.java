/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.model.vo;

import com.pinting.business.model.BsBankCard;

/**
 * 
 * @author HuanXiong
 * @version $Id: BankCardVO.java, v 0.1 2015-11-14 下午12:14:04 HuanXiong Exp $
 */
public class BankCardVO extends BsBankCard {

    /**  */
    private static final long serialVersionUID = -5211979159096736968L;

    private String            smallLogo;

    private String            largeLogo;

    public String getSmallLogo() {
        return smallLogo;
    }

    public void setSmallLogo(String smallLogo) {
        this.smallLogo = smallLogo;
    }

    public String getLargeLogo() {
        return largeLogo;
    }

    public void setLargeLogo(String largeLogo) {
        this.largeLogo = largeLogo;
    }

}
