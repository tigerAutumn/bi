package com.pinting.gateway.hessian.message.hfbank;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 
 * @project gateway
 * @title G2BReqMsg_HFBank_LiquidationNotice.java
 * @author Dragon & cat
 * @date 2017-5-15
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class G2BReqMsg_HFBank_LiquidationNotice extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -448699293072265147L;

	/*清算标识：1:完成*/
	@NotEmpty(message="liquidation_flag为空")
	private		String		liquidation_flag;
    /* 平台编号 */
    @NotEmpty(message="plat_no为空")
    private 	String 		plat_no;

	public String getLiquidation_flag() {
		return liquidation_flag;
	}

	public void setLiquidation_flag(String liquidation_flag) {
		this.liquidation_flag = liquidation_flag;
	}

	public String getPlat_no() {
		return plat_no;
	}

	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}
	
	
	
}
