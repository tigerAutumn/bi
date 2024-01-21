package com.pinting.gateway.hfbank.out.model;


/**
 * 对账文件资金进出数据
 * @author SHENGP
 * @date  2017年5月9日 下午3:05:34
 */
public class FundDataCheckReqModel extends BaseReqModel {

	/* 对账日期(yyyyMMdd) */
	private String paycheck_date;
	
	/* 是否预对账0-不是，1-是 */
	private String precheck_flag;
	
	/* 开始时间(预对账必输：yyyyMMddHHmmss) */
	private String begin_time;
	
	/* 结束时间(预对账必输：yyyyMMddHHmmss) */
	private String end_time;
	
	public String getPrecheck_flag() {
		return precheck_flag;
	}
	public void setPrecheck_flag(String precheck_flag) {
		this.precheck_flag = precheck_flag;
	}
	public String getPaycheck_date() {
		return paycheck_date;
	}
	public void setPaycheck_date(String paycheck_date) {
		this.paycheck_date = paycheck_date;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
}
