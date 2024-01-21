package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;

public class SafeCenterResponse extends Response {

	private Boolean    	      isBindPayPassword;  //是否有交易密码

    private String            bankName;  //银行卡所属名称

    private String            cardNo;  //银行卡卡号

    private Integer           cardId;  //银行卡ID
    
    private String           isShowReturnPath;  //是否显示回款路径
    
	public Boolean getIsBindPayPassword() {
		return isBindPayPassword;
	}

	public void setIsBindPayPassword(Boolean isBindPayPassword) {
		this.isBindPayPassword = isBindPayPassword;
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

	public String getIsShowReturnPath() {
		return isShowReturnPath;
	}

	public void setIsShowReturnPath(String isShowReturnPath) {
		this.isShowReturnPath = isShowReturnPath;
	}

    
}  
