package com.pinting.gateway.hfbank.in.model;

/**
 * 平台充值回调通知请求信息
 * @author SHENGP
 * @date  2017年4月24日 下午4:52:50
 */
public class PlatTopUpNoticeReqModel extends BaseReqModel {

	/* 平台编号 */
    private String plat_no;
    /* 订单号 */
    private String order_no;
    
    /* 金额 */
    private String amt;
    
    /* 1-入账成功  2-入账失败 */
    private String code;

	public String getPlat_no() {
		return plat_no;
	}

	public void setPlat_no(String plat_no) {
		this.plat_no = plat_no;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
}
