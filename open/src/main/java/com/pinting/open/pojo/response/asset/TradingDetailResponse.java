package com.pinting.open.pojo.response.asset;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.asset.TradingDetail;

public class TradingDetailResponse extends Response {

	private int totalPage;  //总页数
	
	private List<TradingDetail> tradingDetailList;  //交易明细列表
	
	
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<TradingDetail> getTradingDetailList() {
		return tradingDetailList;
	}

	public void setTradingDetailList(List<TradingDetail> tradingDetailList) {
		this.tradingDetailList = tradingDetailList;
	}
	
}
