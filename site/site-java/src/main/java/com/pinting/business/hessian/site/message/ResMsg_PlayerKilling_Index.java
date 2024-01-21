package com.pinting.business.hessian.site.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_PlayerKilling_Index extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1700499967893468762L;
	/*现实派投资金额*/
	private			Double		investAmountReal;
	/*知足派投资金额*/
	private			Double		investAmountContentment;
	
	/*现实派投资产品ID*/
	private			Integer		productIdReal;
	/*知足派投资产品ID*/
	private			Integer		productIdContentment;
	/*胜利方*/
	private			String		winner;
	
	/*知足派排行榜*/
	private			List<HashMap<String, Object>> rankingContentment;
	/*现实派排行榜*/
	private			List<HashMap<String, Object>> rankingReal;
	
	public Double getInvestAmountReal() {
		return investAmountReal;
	}
	public void setInvestAmountReal(Double investAmountReal) {
		this.investAmountReal = investAmountReal;
	}
	public Double getInvestAmountContentment() {
		return investAmountContentment;
	}
	public void setInvestAmountContentment(Double investAmountContentment) {
		this.investAmountContentment = investAmountContentment;
	}
	public Integer getProductIdReal() {
		return productIdReal;
	}
	public void setProductIdReal(Integer productIdReal) {
		this.productIdReal = productIdReal;
	}
	public Integer getProductIdContentment() {
		return productIdContentment;
	}
	public void setProductIdContentment(Integer productIdContentment) {
		this.productIdContentment = productIdContentment;
	}
	public String getWinner() {
		return winner;
	}
	public void setWinner(String winner) {
		this.winner = winner;
	}
	public List<HashMap<String, Object>> getRankingContentment() {
		return rankingContentment;
	}
	public void setRankingContentment(
			List<HashMap<String, Object>> rankingContentment) {
		this.rankingContentment = rankingContentment;
	}
	public List<HashMap<String, Object>> getRankingReal() {
		return rankingReal;
	}
	public void setRankingReal(List<HashMap<String, Object>> rankingReal) {
		this.rankingReal = rankingReal;
	}
	
	
}
