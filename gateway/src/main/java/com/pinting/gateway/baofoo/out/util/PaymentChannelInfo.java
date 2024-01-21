package com.pinting.gateway.baofoo.out.util;

/**
 * 支付渠道信息类
 * @author bianyatian
 * @2017-12-7 上午10:30:07
 */
public class PaymentChannelInfo {
	
	/**
	 * 商户号
	 */
	private String merchantNo;
	
	/**
	 * 代扣支付终端号
	 */
	private String cutPaymentterminalId;
	
	/**
	 * 代付终端号
	 */
	private String pay4terminalId;
	
	/**
	 * 宝付代扣公钥
	 */
	private String cutPaymentcerPath;
	
	/**
	 * 宝付代付公钥
	 */
	private String pay4cerpath;
	
	/**
	 * 代扣版本号
	 */
	private String cutVersion;
	
	/**
	 * 宝付私钥
	 */
	private String pfxpath;
	
	/**
	 * 私钥密码
	 */
	private String pfxpwd;
	
    /**
     * 宝付协议支付终端号
     */
	private String singlePayTerminalId;
	
	/**
	 * 协议支付（直接支付）deskey
	 */
	private String  singlePayDesKey;
	
    /**
     * 宝付协议支付公钥
     */
	private  String singlePayCerPath;
    
    /**
     * 宝付协议支付私钥
     */
	private  String singlePayPfxPath;
    
    /**
     * 宝付协议支付私钥密码
     */
	private  String singlePayPfxPwd;
    
    /**
     * 宝付协议支付报文版本号
     */
	private  String singlePayVersion;
    
	

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getCutPaymentcerPath() {
		return cutPaymentcerPath;
	}

	public void setCutPaymentcerPath(String cutPaymentcerPath) {
		this.cutPaymentcerPath = cutPaymentcerPath;
	}

	public String getCutVersion() {
		return cutVersion;
	}

	public void setCutVersion(String cutVersion) {
		this.cutVersion = cutVersion;
	}

	public String getPfxpath() {
		return pfxpath;
	}

	public void setPfxpath(String pfxpath) {
		this.pfxpath = pfxpath;
	}

	public String getPfxpwd() {
		return pfxpwd;
	}

	public void setPfxpwd(String pfxpwd) {
		this.pfxpwd = pfxpwd;
	}

	public String getCutPaymentterminalId() {
		return cutPaymentterminalId;
	}

	public void setCutPaymentterminalId(String cutPaymentterminalId) {
		this.cutPaymentterminalId = cutPaymentterminalId;
	}

	public String getPay4cerpath() {
		return pay4cerpath;
	}

	public void setPay4cerpath(String pay4cerpath) {
		this.pay4cerpath = pay4cerpath;
	}

	public String getPay4terminalId() {
		return pay4terminalId;
	}

	public void setPay4terminalId(String pay4terminalId) {
		this.pay4terminalId = pay4terminalId;
	}

	
	public String getSinglePayTerminalId() {
		return singlePayTerminalId;
	}

	public void setSinglePayTerminalId(String singlePayTerminalId) {
		this.singlePayTerminalId = singlePayTerminalId;
	}

	public String getSinglePayDesKey() {
		return singlePayDesKey;
	}

	public String getSinglePayCerPath() {
		return singlePayCerPath;
	}

	public String getSinglePayPfxPath() {
		return singlePayPfxPath;
	}

	public String getSinglePayPfxPwd() {
		return singlePayPfxPwd;
	}

	public String getSinglePayVersion() {
		return singlePayVersion;
	}

	public void setSinglePayDesKey(String singlePayDesKey) {
		this.singlePayDesKey = singlePayDesKey;
	}

	public void setSinglePayCerPath(String singlePayCerPath) {
		this.singlePayCerPath = singlePayCerPath;
	}

	public void setSinglePayPfxPath(String singlePayPfxPath) {
		this.singlePayPfxPath = singlePayPfxPath;
	}

	public void setSinglePayPfxPwd(String singlePayPfxPwd) {
		this.singlePayPfxPwd = singlePayPfxPwd;
	}

	public void setSinglePayVersion(String singlePayVersion) {
		this.singlePayVersion = singlePayVersion;
	}
	
}
