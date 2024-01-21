package com.pinting.gateway.reapal.out.model.req;

/**
 * 
 * @ClassName: MemoryCardSignReq 
 * @Description: 储蓄卡签约接口
 * @author lenovo
 * @date 2016年1月5日 下午2:19:20 
 *
 */
public class MemoryCardSignReq extends ReapalBaseOutReq{

	private String merchant_id; //商户在融宝的账户ID
	
	private String card_no; //借记卡的银行卡号
	
	private String owner; //用户姓名
	
	private String cert_type; //证件类型，暂时只支持身份证（01）
	
	private String cert_no; //用户填写的证件号码
	
	private String phone; //用户在银行预留手机号
	
	private String order_no; //商户生成的唯一订单号
	
	private String transtime; //时间戳，精确到秒，2015-03-06 12：24：59
	
	private String currency; //默认传156，目前仅支持人民币
	
	private Integer total_fee; //以“分”为单位的整数，必须大于零
	
	private String title; //商品名称
	
	private String body; //商品的具体描述
	
	private String member_id; //商户生成的用户ID
	
	private String terminal_type; //默认值:mobile web、wap、mobile

	private String terminal_info; //格式为IMEI_MAC/序列号_SIM
	
	private String member_ip; //用户真实的IP地址
	
	private String seller_email; //签约融宝支付账号或卖家收款融宝支付帐户
	
	private String notify_url; //融宝服务器主动通知商户网站里指定的页面http路径
	
	private String token_id; //融宝用于交易时风控进行安全的判断，100位以内，建议用UUID的生成方法
	
	private String version; //版本控制默认3.0，建议传最新版
	
	private String sign_type; //目前仅支持MD5，不参与验签
	
	private String sign; //按照sign_type参数指定的签名算法对待签名数据进行签名。目前仅支持MD5.详见数字签名

	public String getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCert_type() {
		return cert_type;
	}

	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	public String getCert_no() {
		return cert_no;
	}

	public void setCert_no(String cert_no) {
		this.cert_no = cert_no;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getTranstime() {
		return transtime;
	}

	public void setTranstime(String transtime) {
		this.transtime = transtime;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getTerminal_type() {
		return terminal_type;
	}

	public void setTerminal_type(String terminal_type) {
		this.terminal_type = terminal_type;
	}

	public String getTerminal_info() {
		return terminal_info;
	}

	public void setTerminal_info(String terminal_info) {
		this.terminal_info = terminal_info;
	}

	public String getMember_ip() {
		return member_ip;
	}

	public void setMember_ip(String member_ip) {
		this.member_ip = member_ip;
	}

	public String getSeller_email() {
		return seller_email;
	}

	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getToken_id() {
		return token_id;
	}

	public void setToken_id(String token_id) {
		this.token_id = token_id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
