package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;

public class ReturnBankCardResponse extends Response {
		
    private String returnPath;

    private String bankName;
    
    private String cardNo;
    
    private Integer cardId;
    
    private String smallLogo;

    private String largeLogo;

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
    
    
}
