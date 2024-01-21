package com.pinting.business.hessian.site.message;

import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 理财计划列表分级--首页（引导页面）
 * @author SHENGP
 * @date  2017-7-4 下午2:15:56
 */
public class ResMsg_Product_ListIndexInfoQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4047675628204155190L;
	
	/*限时活动--没有时隐藏，有活动时显示（显示：show  隐藏：hide）*/
	private   	String    			timeLimitActivityStatus; 
	/*限时活动*/
	private		HashMap<String, Object>			timeLimitActivity;
	/*新手专享--没有时隐藏，有活动时显示（显示：show  隐藏：hide）*/
	private   	String   			noviceActivityStatus;
	/*新手专享*/
	private		HashMap<String, Object>				noviceActivity;
	/*到期还本付息计划卡片状态--没有则隐藏，有则显示（显示：show  隐藏：hide）*/
	private   	String   			finishReturnAllProductStatus;
	/*到期还本付息计划*/
	private		HashMap<String, Object>				finishReturnAllProduct;
	/*等额本息计划卡片状态--没有则隐藏，有则显示（显示：show  隐藏：hide）*/
	private   	String    			averageCapitalPlusInterestProductStatus;
	/*等额本息计划*/
	private		HashMap<String, Object>				averageCapitalPlusInterestProduct;
	
	public String getTimeLimitActivityStatus() {
		return timeLimitActivityStatus;
	}
	public void setTimeLimitActivityStatus(String timeLimitActivityStatus) {
		this.timeLimitActivityStatus = timeLimitActivityStatus;
	}
	public HashMap<String, Object> getTimeLimitActivity() {
		return timeLimitActivity;
	}
	public void setTimeLimitActivity(HashMap<String, Object> timeLimitActivity) {
		this.timeLimitActivity = timeLimitActivity;
	}
	public String getNoviceActivityStatus() {
		return noviceActivityStatus;
	}
	public void setNoviceActivityStatus(String noviceActivityStatus) {
		this.noviceActivityStatus = noviceActivityStatus;
	}
	public HashMap<String, Object> getNoviceActivity() {
		return noviceActivity;
	}
	public void setNoviceActivity(HashMap<String, Object> noviceActivity) {
		this.noviceActivity = noviceActivity;
	}
	public String getFinishReturnAllProductStatus() {
		return finishReturnAllProductStatus;
	}
	public void setFinishReturnAllProductStatus(String finishReturnAllProductStatus) {
		this.finishReturnAllProductStatus = finishReturnAllProductStatus;
	}
	public HashMap<String, Object> getFinishReturnAllProduct() {
		return finishReturnAllProduct;
	}
	public void setFinishReturnAllProduct(
			HashMap<String, Object> finishReturnAllProduct) {
		this.finishReturnAllProduct = finishReturnAllProduct;
	}

	public String getAverageCapitalPlusInterestProductStatus() {
		return averageCapitalPlusInterestProductStatus;
	}
	public void setAverageCapitalPlusInterestProductStatus(
			String averageCapitalPlusInterestProductStatus) {
		this.averageCapitalPlusInterestProductStatus = averageCapitalPlusInterestProductStatus;
	}
	public HashMap<String, Object> getAverageCapitalPlusInterestProduct() {
		return averageCapitalPlusInterestProduct;
	}
	public void setAverageCapitalPlusInterestProduct(
			HashMap<String, Object> averageCapitalPlusInterestProduct) {
		this.averageCapitalPlusInterestProduct = averageCapitalPlusInterestProduct;
	}
	
}
