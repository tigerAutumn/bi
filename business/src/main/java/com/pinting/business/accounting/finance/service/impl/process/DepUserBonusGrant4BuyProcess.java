package com.pinting.business.accounting.finance.service.impl.process;

import com.pinting.business.accounting.finance.service.DepUserBonusGrantService;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author:      cyb
 * Date:        2017/4/10
 * Description: 用户奖励金发放处理
 */
public class DepUserBonusGrant4BuyProcess implements Runnable {

    private Logger log = LoggerFactory.getLogger(DepUserBonusGrant4BuyProcess.class);

    private DepUserBonusGrantService depUserBonusGrantService;
    private Integer selfUserId;// 理财人编号
    private Integer referrerUserId;// 推荐人编号
    private Integer subAccountId;// 理财产品户编号
    private Double amount;// 理财金额
    private String bonusGrantType;//奖励金发放类型
    private String propertySymbol; //资金接收方标志

    @Override
    public void run() {
        // 校验活动时间
        // 2018年1月1日（包含）之后
        // 1. 利率下降为原先的一半
        // 2. 老用户不再享有推荐人全拿奖励金，和其他用户保持一致
        String isStart = depUserBonusGrantService.duringActivity(Constants.ACTIVITY_ID_2017_RECOMMEND);
        if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
            // 2018年1月1日之前的奖励金发放处理
            oldBonusProcess();
        } else if(Constants.ACTIVITY_IS_START.equals(isStart)) {
            // 2018年1月1日之后的奖励金发放处理
            newBonusProcess();
        } else if(Constants.ACTIVITY_IS_END.equals(isStart)) {
            // 2019年以及之后的奖励金发放处理
            depUserBonusGrantService.newBonus2019Process(selfUserId, referrerUserId, subAccountId, amount, bonusGrantType, propertySymbol);
        }
    }

    private void newBonusProcess() {
        log.info("【2018年1月1日之后的用户奖励金发放处理】开始");
        if(PartnerEnum.ZAN.getCode().equals(propertySymbol)){
            if(Constants.BONUS_GRANT_TYPE_PART.equals(bonusGrantType) || Constants.BONUS_GRANT_TYPE_ALL.equals(bonusGrantType)) {
                //推荐人、购买人各拿部分奖励金
                if(referrerUserId == null)
                    return;
                log.info("=========【用户奖励金发放处理】REG_D推荐人、购买人各拿部分奖励金=========");
                depUserBonusGrantService.entrustEachTakePart(selfUserId, referrerUserId, subAccountId, amount, true);
            } else if(Constants.BONUS_GRANT_TYPE_BUYER_PART.equals(bonusGrantType)) {
                //购买人拿部分奖励金
                log.info("=========【用户奖励金发放处理】REG_D购买人拿部分奖励金=========");
                depUserBonusGrantService.entrustSelfTakePart(selfUserId, subAccountId, amount, true);
            }
        }else{
            if(Constants.BONUS_GRANT_TYPE_PART.equals(bonusGrantType) || Constants.BONUS_GRANT_TYPE_ALL.equals(bonusGrantType)){
                //推荐人、购买人各拿部分奖励金
                if(referrerUserId == null)
                    return;
                log.info("=========【用户奖励金发放处理】REG推荐人、购买人各拿部分奖励金=========");
                depUserBonusGrantService.eachTakePart(selfUserId, referrerUserId, subAccountId, amount, true);
            } else if(Constants.BONUS_GRANT_TYPE_BUYER_PART.equals(bonusGrantType)) {
                //购买人拿部分奖励金
                log.info("=========【用户奖励金发放处理】REG购买人拿部分奖励金=========");
                depUserBonusGrantService.selfTakePart(selfUserId, subAccountId, amount, true);
            }
        }
    }

    private void oldBonusProcess() {
        log.info("【2018年1月1日之前的用户奖励金发放处理】开始");
        if(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN.equals(propertySymbol)){
            if(Constants.BONUS_GRANT_TYPE_ALL.equals(bonusGrantType)){
                //推荐人拿所有奖励金
                log.info("=========【用户奖励金发放处理】REG_D推荐人拿所有奖励金=========");
                depUserBonusGrantService.entrustReferrerTakeAll(selfUserId, referrerUserId, subAccountId, amount);
            } else if(Constants.BONUS_GRANT_TYPE_PART.equals(bonusGrantType)){
                //推荐人、购买人各拿部分奖励金
                if(referrerUserId == null)
                    return;
                log.info("=========【用户奖励金发放处理】REG_D推荐人、购买人各拿部分奖励金=========");
                depUserBonusGrantService.entrustEachTakePart(selfUserId, referrerUserId, subAccountId, amount, false);
            } else if(Constants.BONUS_GRANT_TYPE_BUYER_PART.equals(bonusGrantType)) {
                //购买人拿部分奖励金
                log.info("=========【用户奖励金发放处理】REG_D购买人拿部分奖励金=========");
                depUserBonusGrantService.entrustSelfTakePart(selfUserId, subAccountId, amount, false);
            }
        }else{
            if(Constants.BONUS_GRANT_TYPE_ALL.equals(bonusGrantType)){
                //推荐人拿所有奖励金
                log.info("=========【用户奖励金发放处理】REG推荐人拿所有奖励金=========");
                depUserBonusGrantService.referrerTakeAll(selfUserId, referrerUserId, subAccountId, amount);
            } else if(Constants.BONUS_GRANT_TYPE_PART.equals(bonusGrantType)){
                //推荐人、购买人各拿部分奖励金
                if(referrerUserId == null)
                    return;
                log.info("=========【用户奖励金发放处理】REG推荐人、购买人各拿部分奖励金=========");
                depUserBonusGrantService.eachTakePart(selfUserId, referrerUserId, subAccountId, amount, false);
            } else if(Constants.BONUS_GRANT_TYPE_BUYER_PART.equals(bonusGrantType)) {
                //购买人拿部分奖励金
                log.info("=========【用户奖励金发放处理】REG购买人拿部分奖励金=========");
                depUserBonusGrantService.selfTakePart(selfUserId, subAccountId, amount, false);
            }
        }
    }

    public void setUserBonusGrantService(DepUserBonusGrantService userBonusGrantService) {
        this.depUserBonusGrantService = userBonusGrantService;
    }

    public DepUserBonusGrantService getDepUserBonusGrantService() {
        return depUserBonusGrantService;
    }

    public void setDepUserBonusGrantService(DepUserBonusGrantService depUserBonusGrantService) {
        this.depUserBonusGrantService = depUserBonusGrantService;
    }

    public Integer getSelfUserId() {
        return selfUserId;
    }

    public void setSelfUserId(Integer selfUserId) {
        this.selfUserId = selfUserId;
    }

    public Integer getReferrerUserId() {
        return referrerUserId;
    }

    public void setReferrerUserId(Integer referrerUserId) {
        this.referrerUserId = referrerUserId;
    }

    public Integer getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Integer subAccountId) {
        this.subAccountId = subAccountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBonusGrantType() {
        return bonusGrantType;
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
