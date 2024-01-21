package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;

/**
 * 
 * @author SHENGP
 * @date  2017年5月11日 下午4:55:12
 */
public class QueryLiquidationData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5175422514438847063L;
	
	/* 0:未完成，1:完成 */
	private String liquidation_flag;

	public String getLiquidation_flag() {
		return liquidation_flag;
	}

	public void setLiquidation_flag(String liquidation_flag) {
		this.liquidation_flag = liquidation_flag;
	}
	
}
