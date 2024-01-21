package com.pinting.gateway.hfbank.out.model;

import java.util.Date;
import java.util.List;

import com.pinting.gateway.hessian.message.hfbank.model.CompensationInfo;
/**
 * 
 * @project gateway
 * @title PublishReqModel.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管 ，标的发布请求
 */
public class PublishReqModel extends BaseReqModel {
	/*产品编号*/
	private		String 		prod_id;
	/*产品名称*/
	private		String 		prod_name;
	/*产品类型（0周期性产品、1不定期出账产品）*/
	private		String 		prod_type;
	/*发行总额度*/
	private		String 		total_limit;
	/*产品起息方式 0满额起息1成立起息2投标起息 3 成立审核后起息*/
	private		String 		value_type;
	/*成立方式0满额成立 1成立日成立 2活期方式*/
	private		String 		create_type;
	/*产品发行日(YYYY-MM-DD HH:mm:ss)*/
	private		String 		sell_date;
	/*起息日，如起息方式为成立起息，为必选项
	(YYYY-MM-DD HH:mm:ss)*/
	private		String 		value_date;
	/*到期日同上(YYYY-MM-DD HH:mm:ss)*/
	private		String 		expire_date;
	/*周期，例如：3个月的标的传 3*/
	private		Integer 	cycle;
	/*周期单位  1日 2周 3月 4季 5年*/
	private		String 		cycle_unit;
	/*年化收益率例如：8.9% 传 0.089 小数点后5位*/
	private		String 		ist_year;
	/*还款方式  0-一次性还款  1-分期还款*/
	private		String 		repay_type;
	/*融资信息列表*/
	private     List<PublishFinancingInfo>  financing_info_list;
	/*风险等级*/
	private		String 		risk_lvl;
	/*产品介绍*/
	private		String 		prod_info;
	/*限制购买人数*/
	private		Integer 	buyer_num_limit;
	/*出账标示：
	0、成标后自动出账至借款人融资账户
	1、调用成标出账接口，出账至标的对应收款账户*/
	private		String 		chargeOff_auto;
	/*超额限制 0-不限制  1-限制*/
	private		String 		overLimit;
	/*超额限制总额度，限制该标的允许超额投标的总额度*/
	private		String 		over_total_limit;
	/*标的报备字段（如果做受托支付，需要在该字段说明）*/
	private		String 		remark;
	
	private 	List<CompensationInfo> compensation;		
	
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public String getProd_type() {
		return prod_type;
	}
	public void setProd_type(String prod_type) {
		this.prod_type = prod_type;
	}
	public String getTotal_limit() {
		return total_limit;
	}
	public void setTotal_limit(String total_limit) {
		this.total_limit = total_limit;
	}
	public String getValue_type() {
		return value_type;
	}
	public void setValue_type(String value_type) {
		this.value_type = value_type;
	}
	public String getCreate_type() {
		return create_type;
	}
	public void setCreate_type(String create_type) {
		this.create_type = create_type;
	}

	public Integer getCycle() {
		return cycle;
	}
	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}
	public String getCycle_unit() {
		return cycle_unit;
	}
	public void setCycle_unit(String cycle_unit) {
		this.cycle_unit = cycle_unit;
	}
	public String getIst_year() {
		return ist_year;
	}
	public void setIst_year(String ist_year) {
		this.ist_year = ist_year;
	}
	public String getRepay_type() {
		return repay_type;
	}
	public void setRepay_type(String repay_type) {
		this.repay_type = repay_type;
	}
	public List<PublishFinancingInfo> getFinancing_info_list() {
		return financing_info_list;
	}
	public void setFinancing_info_list(
			List<PublishFinancingInfo> financing_info_list) {
		this.financing_info_list = financing_info_list;
	}
	public String getRisk_lvl() {
		return risk_lvl;
	}
	public void setRisk_lvl(String risk_lvl) {
		this.risk_lvl = risk_lvl;
	}
	public String getProd_info() {
		return prod_info;
	}
	public void setProd_info(String prod_info) {
		this.prod_info = prod_info;
	}
	public Integer getBuyer_num_limit() {
		return buyer_num_limit;
	}
	public void setBuyer_num_limit(Integer buyer_num_limit) {
		this.buyer_num_limit = buyer_num_limit;
	}
	public String getChargeOff_auto() {
		return chargeOff_auto;
	}
	public void setChargeOff_auto(String chargeOff_auto) {
		this.chargeOff_auto = chargeOff_auto;
	}
	public String getOverLimit() {
		return overLimit;
	}
	public void setOverLimit(String overLimit) {
		this.overLimit = overLimit;
	}
	public String getOver_total_limit() {
		return over_total_limit;
	}
	public void setOver_total_limit(String over_total_limit) {
		this.over_total_limit = over_total_limit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getSell_date() {
		return sell_date;
	}

	public void setSell_date(String sell_date) {
		this.sell_date = sell_date;
	}

	public String getValue_date() {
		return value_date;
	}

	public void setValue_date(String value_date) {
		this.value_date = value_date;
	}

	public String getExpire_date() {
		return expire_date;
	}

	public void setExpire_date(String expire_date) {
		this.expire_date = expire_date;
	}
	public List<CompensationInfo> getCompensation() {
		return compensation;
	}
	public void setCompensation(
			List<CompensationInfo> compensation) {
		this.compensation = compensation;
	}
	
}
