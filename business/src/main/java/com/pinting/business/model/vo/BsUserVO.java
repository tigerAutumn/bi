package com.pinting.business.model.vo;

import com.pinting.business.model.BsUser;
import com.pinting.core.util.StringUtil;

public class BsUserVO extends BsUser {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4528429634913407301L;
	private String bankCard;
	private String dafyUserId;
	/**渠道名称**/
	private String agentName;
	/**推荐数**/
	private Integer recommendCount;
	/**隐藏中间4位的手机号码**/
	private String mobileCustom;
	/**隐藏中间8位的身份证号码**/
	private String idCardCustom;
	
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getDafyUserId() {
		return dafyUserId;
	}
	public void setDafyUserId(String dafyUserId) {
		this.dafyUserId = dafyUserId;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public Integer getRecommendCount() {
		return recommendCount;
	}
	public void setRecommendCount(Integer recommendCount) {
		this.recommendCount = recommendCount;
	}
	public String getMobileCustom() {
		if(StringUtil.isNotEmpty(getMobile())){
			mobileCustom = custom(getMobile());
		}
		return mobileCustom;
	}
	public void setMobileCustom(String mobileCustom) {
		this.mobileCustom = mobileCustom;
	}
	public String getIdCardCustom() {
		if(StringUtil.isNotEmpty(getIdCard())){
			idCardCustom = custom(getIdCard());
		}
		return idCardCustom;
	}
	public void setIdCardCustom(String idCardCustom) {
		this.idCardCustom = idCardCustom;
	}

	/**
	 * 处理手机号码或者身份证号码
	 * @param number手机号码或身份证
	 * @return
	 */
	private static String custom(String number){
		number = number.trim();
		if(number.length() == 11){//手机号码
			String mobile = number.substring(3, 7);
			number = number.replaceAll(mobile, "****");
		}else if(number.length() == 18){//18位身份证
			String idCard = number.substring(6, 14);
			number = number.replaceAll(idCard, "********");
		}else if(number.length() == 15){//15位身份证
			String idCard = number.substring(6, 12);
			number = number.replaceAll(idCard, "******");
		}
		return number;
	}
	  
}
