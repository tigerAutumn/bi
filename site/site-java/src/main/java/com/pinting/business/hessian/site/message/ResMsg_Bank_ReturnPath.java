/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 回款路径列表 出参
 * @author HuanXiong
 * @version $Id: ResMsg_Bank_ReturnPath.java, v 0.1 2015-11-18 下午6:41:26 HuanXiong Exp $
 */
public class ResMsg_Bank_ReturnPath extends ResMsg {

    /**  */
    private static final long serialVersionUID = 8612366739528976029L;
    /*回款路径*/
    private String returnPath;
    /*银行名称*/
    private String bankName;
    /*银行卡号*/
    private String cardNo;
    /*银行卡ID*/
    private Integer cardId;
    /*小图标*/
	private String smallLogo;
	/*大图标*/
    private String largeLogo;
    
    private String 		      isShowReturnPath;  //判断是否显示回款路径

    public String getReturnPath() {
        return returnPath;
    }

    public void setReturnPath(String returnPath) {
        this.returnPath = returnPath;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

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

	public String getIsShowReturnPath() {
		return isShowReturnPath;
	}

	public void setIsShowReturnPath(String isShowReturnPath) {
		this.isShowReturnPath = isShowReturnPath;
	}
    
    
}
