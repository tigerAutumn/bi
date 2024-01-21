package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ReqMsg;

import java.util.Date;

/**
 * Author	:	yed
 * Date		:	2017/6/12
 * Description	:	绑卡请求信息
 */
public class B2GReqMsg_HFBank_UserAddCard extends ReqMsg {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6120328197410972874L;
	
	/* 商户平台在资金账户管理平台注册的平台  */
    private String plat_no;
    /* 订单号 */
    private String order_no;
    /* 商户交易日期 YYYYMMdd */
    private Date partner_trans_date;
    /* 商户交易时间 HHmmss */
    private Date partner_trans_time;
    /* 用户在资金账户管理平台的电子账户 */
    private String platcust;
    /* 绑卡类型:1个人客户  2:对公客户  */
    private String type;
    /* 证件类型(1:身份证),个人客户必填  */
    private String id_type;
    /* 证件号码  */
    private String id_code;
    /* 姓名,个人客户必填  */
    private String name;
    /* 卡号，个人客户必填 */
    private String card_no;
    /* 卡类型(1:借记卡，2:信用卡) */
    private String card_type;
    /* 预留手机号(个人客户必填) */
    private String pre_mobile;
    /* 组织机构代码，对公客户必填  */
    private String org_no;
    /* 账户名称，对公客户必填  */
    private String acct_name;
    /* 账号，对公客户必填  */
    private String acct_no;
    /* 开户银行（精确到分行），对公客户必填  */
    private String open_branch;
    /* 支付通道  */
    private String pay_code;
    /* 异步通知地址,对公客户必填  */
    private String notify_url;
    /* 备注 */
    private String remark;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId_type() {
		return id_type;
	}
	public void setId_type(String id_type) {
		this.id_type = id_type;
	}
	public String getId_code() {
		return id_code;
	}
	public void setId_code(String id_code) {
		this.id_code = id_code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public String getPre_mobile() {
		return pre_mobile;
	}
	public void setPre_mobile(String pre_mobile) {
		this.pre_mobile = pre_mobile;
	}
	public String getOrg_no() {
		return org_no;
	}
	public void setOrg_no(String org_no) {
		this.org_no = org_no;
	}
	public String getAcct_name() {
		return acct_name;
	}
	public void setAcct_name(String acct_name) {
		this.acct_name = acct_name;
	}
	public String getAcct_no() {
		return acct_no;
	}
	public void setAcct_no(String acct_no) {
		this.acct_no = acct_no;
	}
	public String getOpen_branch() {
		return open_branch;
	}
	public void setOpen_branch(String open_branch) {
		this.open_branch = open_branch;
	}
	public String getPay_code() {
		return pay_code;
	}
	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
    
}