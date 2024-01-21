package com.pinting.gateway.hessian.message.hfbank;

import java.util.Date;
import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.hfbank.model.TransferDebtReqCommission;
import com.pinting.gateway.hessian.message.hfbank.model.TransferDebtReqCommissionExt;

public class B2GReqMsg_HFBank_TransferDebt extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7767666621429143195L;
	/**转让人平台客户号*/
	private  	String 		platcust;
	/**转让份额*/
	private  	Double 		trans_share;
	/**产品编号*/
	private  	String 		prod_id;
	/**交易金额（成交价格+出让人手续费+受让人手续费+转让收益）*/
	private  	Double 		trans_amt;
	/**自费价格*/
	private  	Double 		deal_amount;
	/**抵用劵金额*/
	private  	Double 		coupon_amt;
	/**成交账号（受让人平台客户编号）*/
	private  	String 		deal_platcustprivate;
	/**出让人手续费说明*/
	private  	List<TransferDebtReqCommission> 		commission;
	/**受让人手续费说明*/
	private  	List<TransferDebtReqCommissionExt> 		commission_ext;
	/**发布时间(YYYY-MM-DD HH:mm:ss)*/
	private 	Date 		publish_date;
	/**成交时间(YYYY-MM-DD HH:mm:ss)*/
	private 	Date 		trans_date;
	/**转让收益*/
	private 	Double 		transfer_income;
	/**收益出资方账户    平台：01  ；个人：对应platcust*/
	private 	String 		income_acct;
	/**涉及的标的编号，不同标的之间用逗号分隔(eg:P0001,P0002)*/
	private 	String 		related_prod_ids;
	/**科目优先级0-可提优先1可投优先 新增*/
	private 	String		subject_priority;
	/**备注*/
	private 	String 		remark;
	
    /* 订单号 */
    private String order_no;

    /* 商户交易日期 YYYYMMdd */
    private Date partner_trans_date;

    /* 商户交易时间 HHmmss */
    private Date partner_trans_time;

	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public Date getPartner_trans_date() {
		return partner_trans_date;
	}

	public void setPartner_trans_date(Date partner_trans_date) {
		this.partner_trans_date = partner_trans_date;
	}

	public Date getPartner_trans_time() {
		return partner_trans_time;
	}

	public void setPartner_trans_time(Date partner_trans_time) {
		this.partner_trans_time = partner_trans_time;
	}

	public String getPlatcust() {
		return platcust;
	}
	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}
	public Double getTrans_share() {
		return trans_share;
	}
	public void setTrans_share(Double trans_share) {
		this.trans_share = trans_share;
	}
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public Double getTrans_amt() {
		return trans_amt;
	}
	public void setTrans_amt(Double trans_amt) {
		this.trans_amt = trans_amt;
	}
	public Double getDeal_amount() {
		return deal_amount;
	}
	public void setDeal_amount(Double deal_amount) {
		this.deal_amount = deal_amount;
	}
	public Double getCoupon_amt() {
		return coupon_amt;
	}
	public void setCoupon_amt(Double coupon_amt) {
		this.coupon_amt = coupon_amt;
	}
	public String getDeal_platcustprivate() {
		return deal_platcustprivate;
	}
	public void setDeal_platcustprivate(String deal_platcustprivate) {
		this.deal_platcustprivate = deal_platcustprivate;
	}
	public List<TransferDebtReqCommission> getCommission() {
		return commission;
	}
	public void setCommission(List<TransferDebtReqCommission> commission) {
		this.commission = commission;
	}
	public List<TransferDebtReqCommissionExt> getCommission_ext() {
		return commission_ext;
	}
	public void setCommission_ext(List<TransferDebtReqCommissionExt> commission_ext) {
		this.commission_ext = commission_ext;
	}

	public Date getPublish_date() {
		return publish_date;
	}

	public void setPublish_date(Date publish_date) {
		this.publish_date = publish_date;
	}

	public Date getTrans_date() {
		return trans_date;
	}

	public void setTrans_date(Date trans_date) {
		this.trans_date = trans_date;
	}

	public Double getTransfer_income() {
		return transfer_income;
	}
	public void setTransfer_income(Double transfer_income) {
		this.transfer_income = transfer_income;
	}
	public String getIncome_acct() {
		return income_acct;
	}
	public void setIncome_acct(String income_acct) {
		this.income_acct = income_acct;
	}
	public String getRelated_prod_ids() {
		return related_prod_ids;
	}
	public void setRelated_prod_ids(String related_prod_ids) {
		this.related_prod_ids = related_prod_ids;
	}
	
	
	public String getSubject_priority() {
		return subject_priority;
	}
	public void setSubject_priority(String subject_priority) {
		this.subject_priority = subject_priority;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
