package com.pinting.business.model.vo;

import com.pinting.business.model.BsRedPacketCheck;

public class RedPacketStatisticsVO extends BsRedPacketCheck {
	   private Double totalAmount;   //已发红包总金额
	   private Integer usedRedPocket; //已使用红包
	   private Double  usedRedPocketAmount;//已使用红包总金额
	   private Integer initRedPocket; //使用中红包
	   private Double  initRedPocketAmount;//使用中红包总金额
	   private Integer overRedPocket; //已过期红包
	   private Double  overRedPocketAmount;//已过期红包总金额
	   private String  rpName;
	   private Double  buyAmount;//投资金额
	   private Double  buyYearAmount; //年化投资金额
	   
		public Double getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(Double totalAmount) {
			this.totalAmount = totalAmount;
		}
		public Integer getUsedRedPocket() {
			return usedRedPocket;
		}
		public void setUsedRedPocket(Integer usedRedPocket) {
			this.usedRedPocket = usedRedPocket;
		}
		public Double getUsedRedPocketAmount() {
			return usedRedPocketAmount;
		}
		public void setUsedRedPocketAmount(Double usedRedPocketAmount) {
			this.usedRedPocketAmount = usedRedPocketAmount;
		}
		public Integer getInitRedPocket() {
			return initRedPocket;
		}
		public void setInitRedPocket(Integer initRedPocket) {
			this.initRedPocket = initRedPocket;
		}
		public Double getInitRedPocketAmount() {
			return initRedPocketAmount;
		}
		public void setInitRedPocketAmount(Double initRedPocketAmount) {
			this.initRedPocketAmount = initRedPocketAmount;
		}
		public Integer getOverRedPocket() {
			return overRedPocket;
		}
		public void setOverRedPocket(Integer overRedPocket) {
			this.overRedPocket = overRedPocket;
		}
		public Double getOverRedPocketAmount() {
			return overRedPocketAmount;
		}
		public void setOverRedPocketAmount(Double overRedPocketAmount) {
			this.overRedPocketAmount = overRedPocketAmount;
		}
		public String getRpName() {
			return rpName;
		}
		public void setRpName(String rpName) {
			this.rpName = rpName;
		}
		public Double getBuyAmount() {
			return buyAmount;
		}
		public void setBuyAmount(Double buyAmount) {
			this.buyAmount = buyAmount;
		}
		public Double getBuyYearAmount() {
			return buyYearAmount;
		}
		public void setBuyYearAmount(Double buyYearAmount) {
			this.buyYearAmount = buyYearAmount;
		}
		   
}
