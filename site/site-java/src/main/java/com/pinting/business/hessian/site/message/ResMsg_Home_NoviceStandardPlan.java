package com.pinting.business.hessian.site.message;

import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 新手引导页 新手专享计划,只显示新手标产品
 * @author shuhuanghui
 * 2016-6-13 下午7:30:10
 */
public class ResMsg_Home_NoviceStandardPlan extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6272255604393987690L;
	/*新手标*/
	private HashMap<String, Object> noviceStandardProduct;
	
	/** 红包金额 */
	private double totalRedPacketSubtract;

	public HashMap<String, Object> getNoviceStandardProduct() {
		return noviceStandardProduct;
	}

	public void setNoviceStandardProduct(
			HashMap<String, Object> noviceStandardProduct) {
		this.noviceStandardProduct = noviceStandardProduct;
	}

	public double getTotalRedPacketSubtract() {
		return totalRedPacketSubtract;
	}

	public void setTotalRedPacketSubtract(double totalRedPacketSubtract) {
		this.totalRedPacketSubtract = totalRedPacketSubtract;
	}

}
