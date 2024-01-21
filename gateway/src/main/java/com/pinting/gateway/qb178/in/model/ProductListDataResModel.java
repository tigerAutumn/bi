package com.pinting.gateway.qb178.in.model;

import java.io.Serializable;

public class ProductListDataResModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3525899638891062615L;
	/**产品编号*/
	private 	String 		product_code;
	/**产品名称*/
	private 	String 		product_name;
	/**产品发行总额（金额单位：分）*/
	private 	Long 		issuance_amount;
	/**募集开始时间*/
	private 	String 		purchase_starttime;
	/**募集结束时间*/
	private 	String 		purchase_endtime;
	/**还款方式：
	 * 利随本清（onetimePrincipalAndInterest）  
	 * 每期付息到期还本（monthlyInterest）  
	 * 等额本息（matchingPrincipalAndInterest）
	 * */
	private 	String 		repayment_method;
	/**年化收益率*/
	private 	String 		product_rate;
	/**发行商*/
	private 	String 		publisher;
	/**发行下限（金额单位：分）*/
	private 	Long 		down_limit;
	/**产品最大持有人数*/
	private 	Integer		max_peoples;
	/**认购起点金额（金额单位：分）*/
	private 	Long 		sale_amount;
	/**认购递增金额（金额单位：分）*/
	private 	Long 		min_transaction;
	/**单次最高认购金额（金额单位：分）*/
	private 	Long 		up_limit;
	/**计息日（起息日）*/
	private 	String 		value_date;
	/**结息日（到期日） 利随本清还款方式必填*/
	private 	String 		expiry_date;
	/**还款期数，每期付息到期还本方式必填*/
	private 	Integer 	repayment_periods;
	/**单期单位，每期付息到期还本方式必填*/
	private 	String 		period_unit;
	/**单期时间，每期付息到期还本方式必填*/
	private 	String 		single_period;
	/**自然天数*/
	private 	String 		year_days;
	/**图片路径*/
	private 	String 		small_pic;
	/**图片路径*/
	private 	String 		big_pic;
	/**产品存续期（与 period_unit 组合成为投资期限）*/
	private 	String 		deadline;
	/**产品状态*/
	private		String		product_status;
	
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Long getIssuance_amount() {
		return issuance_amount;
	}
	public void setIssuance_amount(Long issuance_amount) {
		this.issuance_amount = issuance_amount;
	}
	public String getPurchase_starttime() {
		return purchase_starttime;
	}
	public void setPurchase_starttime(String purchase_starttime) {
		this.purchase_starttime = purchase_starttime;
	}
	public String getPurchase_endtime() {
		return purchase_endtime;
	}
	public void setPurchase_endtime(String purchase_endtime) {
		this.purchase_endtime = purchase_endtime;
	}
	public String getRepayment_method() {
		return repayment_method;
	}
	public void setRepayment_method(String repayment_method) {
		this.repayment_method = repayment_method;
	}
	public String getProduct_rate() {
		return product_rate;
	}
	public void setProduct_rate(String product_rate) {
		this.product_rate = product_rate;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public Long getDown_limit() {
		return down_limit;
	}
	public void setDown_limit(Long down_limit) {
		this.down_limit = down_limit;
	}
	public Integer getMax_peoples() {
		return max_peoples;
	}
	public void setMax_peoples(Integer max_peoples) {
		this.max_peoples = max_peoples;
	}
	public Long getSale_amount() {
		return sale_amount;
	}
	public void setSale_amount(Long sale_amount) {
		this.sale_amount = sale_amount;
	}
	public Long getMin_transaction() {
		return min_transaction;
	}
	public void setMin_transaction(Long min_transaction) {
		this.min_transaction = min_transaction;
	}
	public Long getUp_limit() {
		return up_limit;
	}
	public void setUp_limit(Long up_limit) {
		this.up_limit = up_limit;
	}
	public String getValue_date() {
		return value_date;
	}
	public void setValue_date(String value_date) {
		this.value_date = value_date;
	}
	public String getExpiry_date() {
		return expiry_date;
	}
	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}
	public Integer getRepayment_periods() {
		return repayment_periods;
	}
	public void setRepayment_periods(Integer repayment_periods) {
		this.repayment_periods = repayment_periods;
	}
	public String getPeriod_unit() {
		return period_unit;
	}
	public void setPeriod_unit(String period_unit) {
		this.period_unit = period_unit;
	}
	public String getSingle_period() {
		return single_period;
	}
	public void setSingle_period(String single_period) {
		this.single_period = single_period;
	}
	public String getYear_days() {
		return year_days;
	}
	public void setYear_days(String year_days) {
		this.year_days = year_days;
	}
	public String getSmall_pic() {
		return small_pic;
	}
	public void setSmall_pic(String small_pic) {
		this.small_pic = small_pic;
	}
	public String getBig_pic() {
		return big_pic;
	}
	public void setBig_pic(String big_pic) {
		this.big_pic = big_pic;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getProduct_status() {
		return product_status;
	}
	public void setProduct_status(String product_status) {
		this.product_status = product_status;
	}
	
}
