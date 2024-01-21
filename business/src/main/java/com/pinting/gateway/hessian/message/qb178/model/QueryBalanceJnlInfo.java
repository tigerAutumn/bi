package com.pinting.gateway.hessian.message.qb178.model;

import java.io.Serializable;

/**
 * 查询会员资金流水列表数据
 * @author bianyatian
 * @2017-7-28 下午4:57:20
 */
public class QueryBalanceJnlInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4633648600380404313L;
	/* 会员账户 */
	private String user_account;
	/* 发生金额（金额单位：分） */
	private Long trans_amount;
	/* 后置金额（金额单位：分）=账户余额(加上或减去)发生金额 */
	private Long post_amount;
	/* 流水业务编码，如充值（101），提现（102）,
	交易收入（01），交易支出（02），冻结（03），
	解冻（04），其他（99），请按此编码转换 */
	private String trans_code;
	/* 资金流水描述 */
	private String trans_desc;
	/* 真实发生时间 yyyyMMddHHmmss */
	private String real_trans_dt;
	/*流水号*/
	private String log_id;
	
	public String getUser_account() {
		return user_account;
	}
	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}
	public Long getTrans_amount() {
		return trans_amount;
	}
	public void setTrans_amount(Long trans_amount) {
		this.trans_amount = trans_amount;
	}
	public Long getPost_amount() {
		return post_amount;
	}
	public void setPost_amount(Long post_amount) {
		this.post_amount = post_amount;
	}
	public String getTrans_code() {
		return trans_code;
	}
	public void setTrans_code(String trans_code) {
		this.trans_code = trans_code;
	}
	public String getTrans_desc() {
		return trans_desc;
	}
	public void setTrans_desc(String trans_desc) {
		this.trans_desc = trans_desc;
	}
	public String getReal_trans_dt() {
		return real_trans_dt;
	}
	public void setReal_trans_dt(String real_trans_dt) {
		this.real_trans_dt = real_trans_dt;
	}
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	
	
}
