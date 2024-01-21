package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;
/**
 * 预绑卡Request
 * @author Dragon & cat
 * @date 2016-8-28
 */
public class BankCardBindPreRequest extends Request {
	/**
	 * 用户ID
	 */
	private Integer userId;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 身份证号
	 */
	private String idCard;
	/**
	 * 银行卡号
	 */
	private String cardNo;
	/**
	 * 银行ID
	 */
	private Integer bankId;
	/**
	 * 银行预留手机号
	 */
	private String mobile;
	/**
	 * 终端类型（1-H5端；2-PC端；3-Android端；4-iOS端）
	 */
	private String terminalType;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/asset/preBindCard";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/asset/preBindCard";
	}

}
