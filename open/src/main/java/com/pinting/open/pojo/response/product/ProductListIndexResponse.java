package com.pinting.open.pojo.response.product;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.product.Product;

public class ProductListIndexResponse extends Response {
	/*限时活动--没有时隐藏，有活动时显示（显示：show  隐藏：hide）*/
	private   	String    			timeLimitActivityStatus; 
	/*限时活动*/
	private		Product				timeLimitActivity;
	/*新手专享--没有时隐藏，有活动时显示（显示：show  隐藏：hide）*/
	private   	String   			noviceActivityStatus;
	/*新手专享*/
	private		Product				noviceActivity;
	/*到期还本付息计划卡片状态--没有则隐藏，有一个则显示计划，多个则显示区间（隐藏：hide 一个：one  多个：multi）*/
	private   	String   			finishReturnAllProductStatus;
	/*到期还本付息计划*/
	private		Product				finishReturnAllProduct;

	/*等额本息计划卡片状态--没有则隐藏，有一个则显示计划，多个则显示区间（隐藏：hide 一个：one  多个：multi）*/
	private   	String    			averageCapitalPlusInterestProductStatus;
	/*等额本息计划*/
	private		Product				averageCapitalPlusInterestProduct;

	// 是否新手
	private Integer isNovice;
	
	public String getTimeLimitActivityStatus() {
		return timeLimitActivityStatus;
	}
	public void setTimeLimitActivityStatus(String timeLimitActivityStatus) {
		this.timeLimitActivityStatus = timeLimitActivityStatus;
	}
	public Product getTimeLimitActivity() {
		return timeLimitActivity;
	}
	public void setTimeLimitActivity(Product timeLimitActivity) {
		this.timeLimitActivity = timeLimitActivity;
	}
	public String getNoviceActivityStatus() {
		return noviceActivityStatus;
	}
	public void setNoviceActivityStatus(String noviceActivityStatus) {
		this.noviceActivityStatus = noviceActivityStatus;
	}
	public Product getNoviceActivity() {
		return noviceActivity;
	}
	public void setNoviceActivity(Product noviceActivity) {
		this.noviceActivity = noviceActivity;
	}
	public String getFinishReturnAllProductStatus() {
		return finishReturnAllProductStatus;
	}
	public void setFinishReturnAllProductStatus(String finishReturnAllProductStatus) {
		this.finishReturnAllProductStatus = finishReturnAllProductStatus;
	}
	public Product getFinishReturnAllProduct() {
		return finishReturnAllProduct;
	}
	public void setFinishReturnAllProduct(Product finishReturnAllProduct) {
		this.finishReturnAllProduct = finishReturnAllProduct;
	}

	public String getAverageCapitalPlusInterestProductStatus() {
		return averageCapitalPlusInterestProductStatus;
	}
	public void setAverageCapitalPlusInterestProductStatus(
			String averageCapitalPlusInterestProductStatus) {
		this.averageCapitalPlusInterestProductStatus = averageCapitalPlusInterestProductStatus;
	}
	public Product getAverageCapitalPlusInterestProduct() {
		return averageCapitalPlusInterestProduct;
	}
	public void setAverageCapitalPlusInterestProduct(
			Product averageCapitalPlusInterestProduct) {
		this.averageCapitalPlusInterestProduct = averageCapitalPlusInterestProduct;
	}
	public Integer getIsNovice() {
		return isNovice;
	}
	public void setIsNovice(Integer isNovice) {
		this.isNovice = isNovice;
	}

}
