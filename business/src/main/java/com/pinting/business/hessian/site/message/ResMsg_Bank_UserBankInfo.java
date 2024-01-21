package com.pinting.business.hessian.site.message;

import java.util.List;
import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 查询用户信息和支持快捷支付的19付银行 出参
 * @author shencheng
 * @2015-11-19 上午11:52:39
 */
public class ResMsg_Bank_UserBankInfo extends ResMsg {

	private static final long serialVersionUID = -3431798942420358645L;

	private Integer           userId;                                   //	用户编号	必填		
    private String            nick;                                     //	用户名	必填		
    private String            userName;                                 //	用户真实姓名	可选		
    private String            mobile;                                   //	手机号	可选		
    private String            email;                                    //	邮箱	可选		
    private String            idCard;                                   //	身份证号码	可选		
    private Integer           status;                                   //	状态	必填		1：有效 2：注销 3：加锁
    private Integer           isBindName;                               //	绑定真实姓名标志	必填		1：已绑定 2：未绑定
    private Integer           isBindBank;                               //	绑定银行卡标志	必填		1：已绑定 2：未绑定
    private List<Map<String, Object>> bankList;                         //  19付支持快捷支付的银行卡列表
    
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsBindName() {
		return isBindName;
	}
	public void setIsBindName(Integer isBindName) {
		this.isBindName = isBindName;
	}
	public Integer getIsBindBank() {
		return isBindBank;
	}
	public void setIsBindBank(Integer isBindBank) {
		this.isBindBank = isBindBank;
	}
	public List<Map<String, Object>> getBankList() {
		return bankList;
	}
	public void setBankList(List<Map<String, Object>> bankList) {
		this.bankList = bankList;
	}

}
