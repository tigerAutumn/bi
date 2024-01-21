package com.pinting.open.pojo.response.index;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.product.Product;

public class IndexResponse extends Response {

	private String currDateTime;
	
	private Integer investNum;
	
	private Integer currTotalAmount;
	
	private String totalIncome;
	
	private String minRete;
	
	private String maxRate;
	
	private String publishTime; //活动最新的发布时间
	
	private List<Product> dataList; //标的列表

	private String isExistRedPacket; // 是否存在可用红包（yes 是、no 否）

	public String getIsExistRedPacket() {
		return isExistRedPacket;
	}

	public void setIsExistRedPacket(String isExistRedPacket) {
		this.isExistRedPacket = isExistRedPacket;
	}

	public List<Product> getDataList() {
		return dataList;
	}

	public void setDataList(List<Product> dataList) {
		this.dataList = dataList;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getMinRete() {
		return minRete;
	}

	public void setMinRete(String minRete) {
		this.minRete = minRete;
	}

	public String getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(String maxRate) {
		this.maxRate = maxRate;
	}

	public String getCurrDateTime() {
		return currDateTime;
	}

	public void setCurrDateTime(String currDateTime) {
		this.currDateTime = currDateTime;
	}

	public Integer getInvestNum() {
		return investNum;
	}

	public void setInvestNum(Integer investNum) {
		this.investNum = investNum;
	}

	public Integer getCurrTotalAmount() {
		return currTotalAmount;
	}

	public void setCurrTotalAmount(Integer currTotalAmount) {
		this.currTotalAmount = currTotalAmount;
	}

	public String getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(String totalIncome) {
		this.totalIncome = totalIncome;
	}
}
