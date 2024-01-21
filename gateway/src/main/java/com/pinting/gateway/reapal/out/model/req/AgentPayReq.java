package com.pinting.gateway.reapal.out.model.req;

public class AgentPayReq extends ReapalBaseOutReq{
	//协议参数
	private String sign;			//签名字符串
	private String signType="MD5";	//固定值：MD5
	private String batchBizid;		//合作伙伴在融宝的用户ID
	private String _input_charset;	//gbk, utf8
	private String batchBiztype="00000";	//5位，默认00000
	private String batchDate;		//系统当前日期（yyyyMMdd）
	private String batchVersion="00";//固定值:00
	//业务参数
	private String batchCurrnum;	//50位长度，当日不能重复
	private String batchCount;		//不必输
	private String batchAmount;		//不必输
	private String batchContent;	//代付明细，支持多条，详见BatchContent类
	
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getBatchBizid() {
		return batchBizid;
	}
	public void setBatchBizid(String batchBizid) {
		this.batchBizid = batchBizid;
	}
	public String get_input_charset() {
		return _input_charset;
	}
	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}
	public String getBatchBiztype() {
		return batchBiztype;
	}
	public void setBatchBiztype(String batchBiztype) {
		this.batchBiztype = batchBiztype;
	}
	public String getBatchDate() {
		return batchDate;
	}
	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
	}
	public String getBatchVersion() {
		return batchVersion;
	}
	public void setBatchVersion(String batchVersion) {
		this.batchVersion = batchVersion;
	}
	public String getBatchCurrnum() {
		return batchCurrnum;
	}
	public void setBatchCurrnum(String batchCurrnum) {
		this.batchCurrnum = batchCurrnum;
	}
	public String getBatchCount() {
		return batchCount;
	}
	public void setBatchCount(String batchCount) {
		this.batchCount = batchCount;
	}
	public String getBatchAmount() {
		return batchAmount;
	}
	public void setBatchAmount(String batchAmount) {
		this.batchAmount = batchAmount;
	}
	public String getBatchContent() {
		return batchContent;
	}
	public void setBatchContent(String batchContent) {
		this.batchContent = batchContent;
	}
	
}
