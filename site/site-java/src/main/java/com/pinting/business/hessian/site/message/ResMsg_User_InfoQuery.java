package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * @Project: site-java
 * @Title: ResMsg_User_InfoQuery.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:43:30
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ResMsg_User_InfoQuery extends ResMsg {

    /**
     * 
     */
    private static final long serialVersionUID = -7746102161067159526L;

    private Integer           userId;                                  //	用户编号	必填		
    private String            nick;                                    //	用户名	必填		
    private String            userName;                                //	用户真实姓名	可选		
    private String            mobile;                                  //	手机号	可选		
    private String            email;                                   //	邮箱	可选		
    private String            idCard;                                  //	身份证号码	可选		
    private String            urgentName;                              //	紧急联系人	可选		
    private String            urgentMobile;                            //	紧急联系人联系方式	可选		
    private Integer           relation;                                //	紧急联系关系	可选		
    private Integer           status;                                  //	状态	必填		1：有效 2：注销 3：加锁
    private Integer           isBindName;                              //	绑定真实姓名标志	必填		1：已绑定 2：未绑定
    private Integer           isBindBank;                              //	绑定银行卡标志	必填		1：已绑定 2：未绑定 
    private String            accountId;                               //	主账户号	必填		
    private Double            investEarnings;                          //	投资收益	必填		
    private Double            totalInvestEarnings;                     //	累计投资收益	必填		
    private Double            bonus;                                   //	推荐奖励	必填		
    private Double            totalBonus;                              //	累计推荐奖励	必填		
    private Double            assetAmount;                             //	账户资产总额	必填		
    private Double            regularAmount;                           //	固定理财总额	必填		
    private Integer           totalTransNum;                           //	累计交易次数	必填		
    private Integer           excitPaypassword;                        //	设置支付密码标志	必填		true 已有支付密码

    private Integer           dafyStatus;                              // 达飞绑定标志 1：已绑定 2：未绑定 3:绑定中

    private String            bankName;

    private String            cardNo;

    private Integer           cardId;
    
    private Integer returnPath;
    
    private String 		      isShowReturnPath;  //判断是否显示回款路径
	private String  		  h5ReturnPathHide; //H5隐藏回款路径
    
    
    public Integer getReturnPath() {
        return returnPath;
    }

    public void setReturnPath(Integer returnPath) {
        this.returnPath = returnPath;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getDafyStatus() {
        return dafyStatus;
    }

    public void setDafyStatus(Integer dafyStatus) {
        this.dafyStatus = dafyStatus;
    }

    public Integer getExcitPaypassword() {
        return excitPaypassword;
    }

    public void setExcitPaypassword(Integer excitPaypassword) {
        this.excitPaypassword = excitPaypassword;
    }

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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Double getInvestEarnings() {
        return investEarnings;
    }

    public void setInvestEarnings(Double investEarnings) {
        this.investEarnings = investEarnings;
    }

    public Double getTotalInvestEarnings() {
        return totalInvestEarnings;
    }

    public void setTotalInvestEarnings(Double totalInvestEarnings) {
        this.totalInvestEarnings = totalInvestEarnings;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public Double getTotalBonus() {
        return totalBonus;
    }

    public void setTotalBonus(Double totalBonus) {
        this.totalBonus = totalBonus;
    }

    public Double getAssetAmount() {
        return assetAmount;
    }

    public void setAssetAmount(Double assetAmount) {
        this.assetAmount = assetAmount;
    }

    public Double getRegularAmount() {
        return regularAmount;
    }

    public void setRegularAmount(Double regularAmount) {
        this.regularAmount = regularAmount;
    }

    public Integer getTotalTransNum() {
        return totalTransNum;
    }

    public void setTotalTransNum(Integer totalTransNum) {
        this.totalTransNum = totalTransNum;
    }

    public String getUrgentName() {
        return urgentName;
    }

    public void setUrgentName(String urgentName) {
        this.urgentName = urgentName;
    }

    public String getUrgentMobile() {
        return urgentMobile;
    }

    public void setUrgentMobile(String urgentMobile) {
        this.urgentMobile = urgentMobile;
    }

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

	public String getIsShowReturnPath() {
		return isShowReturnPath;
	}

	public void setIsShowReturnPath(String isShowReturnPath) {
		this.isShowReturnPath = isShowReturnPath;
	}

	public String getH5ReturnPathHide() {
		return h5ReturnPathHide;
	}

	public void setH5ReturnPathHide(String h5ReturnPathHide) {
		this.h5ReturnPathHide = h5ReturnPathHide;
	}
	
}
