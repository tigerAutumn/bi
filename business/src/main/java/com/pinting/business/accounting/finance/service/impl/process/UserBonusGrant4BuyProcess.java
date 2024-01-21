package com.pinting.business.accounting.finance.service.impl.process;

import com.pinting.business.accounting.finance.service.UserBonusGrantService;
import com.pinting.business.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserBonusGrant4BuyProcess implements Runnable {
	private Logger log = LoggerFactory.getLogger(UserBonusGrant4BuyProcess.class);
	private UserBonusGrantService userBonusGrantService;
	private Integer selfUserId;// 理财人编号
	private Integer referrerUserId;// 推荐人编号
	private Integer subAccountId;// 理财产品户编号
	private Double amount;// 理财金额
	private String bonusGrantType;//奖励金发放类型
	private String propertySymbol; //资金接收方标志

	@Override
	public void run() {
		if(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN.equals(propertySymbol)){
			if(Constants.BONUS_GRANT_TYPE_ALL.equals(bonusGrantType)){
				//推荐人拿所有奖励金
				log.info("=========【用户奖励金发放处理】REG_D推荐人拿所有奖励金=========");
				userBonusGrantService.entrustReferrerTakeAll(selfUserId, referrerUserId, subAccountId, amount);
			} else if(Constants.BONUS_GRANT_TYPE_PART.equals(bonusGrantType)){
				//推荐人、购买人各拿部分奖励金
				if(referrerUserId == null)
					return;
				log.info("=========【用户奖励金发放处理】REG_D推荐人、购买人各拿部分奖励金=========");
				userBonusGrantService.entrustEachTakePart(selfUserId, referrerUserId, subAccountId, amount);
			} else if(Constants.BONUS_GRANT_TYPE_BUYER_PART.equals(bonusGrantType)) {
			    //购买人拿部分奖励金
	            log.info("=========【用户奖励金发放处理】REG_D购买人拿部分奖励金=========");
	            userBonusGrantService.entrustSelfTakePart(selfUserId, subAccountId, amount);
			}
		}else{
			if(Constants.BONUS_GRANT_TYPE_ALL.equals(bonusGrantType)){
				//推荐人拿所有奖励金
				log.info("=========【用户奖励金发放处理】REG推荐人拿所有奖励金=========");
				userBonusGrantService.referrerTakeAll(selfUserId, referrerUserId, subAccountId, amount);
			} else if(Constants.BONUS_GRANT_TYPE_PART.equals(bonusGrantType)){
				//推荐人、购买人各拿部分奖励金
				if(referrerUserId == null)
					return;
				log.info("=========【用户奖励金发放处理】REG推荐人、购买人各拿部分奖励金=========");
				userBonusGrantService.eachTakePart(selfUserId, referrerUserId, subAccountId, amount);
			} else if(Constants.BONUS_GRANT_TYPE_BUYER_PART.equals(bonusGrantType)) {
			    //购买人拿部分奖励金
	            log.info("=========【用户奖励金发放处理】REG购买人拿部分奖励金=========");
	            userBonusGrantService.selfTakePart(selfUserId, subAccountId, amount);
			}
		}
		
	}
	public void setUserBonusGrantService(UserBonusGrantService userBonusGrantService) {
		this.userBonusGrantService = userBonusGrantService;
	}
	public void setSelfUserId(Integer selfUserId) {
		this.selfUserId = selfUserId;
	}
	public void setReferrerUserId(Integer referrerUserId) {
		this.referrerUserId = referrerUserId;
	}
	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public void setBonusGrantType(String bonusGrantType) {
		this.bonusGrantType = bonusGrantType;
	}
	public String getPropertySymbol() {
		return propertySymbol;
	}
	public void setPropertySymbol(String propertySymbol) {
		this.propertySymbol = propertySymbol;
	}

}
