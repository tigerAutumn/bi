package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsBankCard_BsBankCardStatusModify extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2678737833306267159L;
	
	/**修改成功**/
	public static final int  SUCCESS = 1;
	
	/**存在余额**/
	public static final int  NOT_NULL_BALANCE = 2;
	
	/**投资中**/
	public static final int  NOT_NULL_BANKCARD_STATUS_2 = 3;
	
	/**转让中**/
	public static final int  NOT_NULL_BANKCARD_STATUS_3 = 5;
	
	/**结算中**/
	public static final int  NOT_NULL_BANKCARD_STATUS_7 = 6;
	
	/**有处理中的订单**/
	public static final int  NOT_NULL_PAYORDERS_STATUS = 4;
	/**其他数据错误**/
	public static final int  OTHER_DATA_ERROR_STATUS = 9;
	
	private String msg;
	
	private Integer signMsg;

	public Integer getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(Integer signMsg) {
		this.signMsg = signMsg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
