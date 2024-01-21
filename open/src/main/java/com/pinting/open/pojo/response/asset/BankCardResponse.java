package com.pinting.open.pojo.response.asset;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.asset.BankCard;

public class BankCardResponse extends Response {
	
	private Integer size;
	
	private List<BankCard> bankCards;
	

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public List<BankCard> getBankCards() {
		return bankCards;
	}

	public void setBankCards(List<BankCard> bankCards) {
		this.bankCards = bankCards;
	}
	
	
}
