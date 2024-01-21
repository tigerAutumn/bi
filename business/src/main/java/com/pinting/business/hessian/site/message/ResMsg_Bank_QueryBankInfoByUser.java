package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 查询用户绑定的单个银行信息 出参
 * @author shencheng
 * @2015-11-24 下午7:13:11
 */
public class ResMsg_Bank_QueryBankInfoByUser extends ResMsg {

	private static final long serialVersionUID = 3941400540646816043L;

	/*银行id*/
	private Integer bankId;
	/*银行卡号*/
	private String cardNo;
	/*银行名称*/
	private String bankName;
	/*用户手机号*/
	private String mobile;
	/*用户姓名*/
	private String userName;
	/*用户身份证号*/
	private String idCard;
	/*日常提示*/
	private String dailyNotice;
	public String getDailyNotice() {
        return dailyNotice;
    }

    public void setDailyNotice(String dailyNotice) {
        this.dailyNotice = dailyNotice;
    }

    public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
}
