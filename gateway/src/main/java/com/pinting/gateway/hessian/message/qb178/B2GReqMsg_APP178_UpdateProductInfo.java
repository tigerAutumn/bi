package com.pinting.gateway.hessian.message.qb178;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_APP178_UpdateProductInfo extends ReqMsg {


    /**
	 * 
	 */
	private static final long serialVersionUID = -8640986084409880452L;

	/* 产品编号 */
    private String product_code;

    /* 产品状态, 申购中（buying），已确权（confirmed）， 赎回完成（redeemed），发行失败（failure）*/
    private String product_status;
    
	/* 合作商流水号，全局唯一，建议时间戳 */
	private String serialNo;

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getProduct_status() {
		return product_status;
	}

	public void setProduct_status(String product_status) {
		this.product_status = product_status;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	

}
