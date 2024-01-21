package com.pinting.business.accounting.finance.service.impl;

import com.pinting.business.accounting.finance.service.DepUserBonusGrantService;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.dao.*;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.AverageCapitalPlusInterestVO;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.service.site.ActivityService;
import com.pinting.business.service.site.SendWechatByRabbitService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/10
 * Description: 存管体系用户奖励金发放
 */
@Service
public class DepUserBonusGrantServiceImpl implements DepUserBonusGrantService {

    Logger logger = LoggerFactory.getLogger(DepUserBonusGrantServiceImpl.class);

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private BsUserMapper userMapper;
    @Autowired
    private BsSysConfigMapper sysConfigMapper;
    @Autowired
    private BsSubAccountMapper subAccountMapper;
    @Autowired
    private BsAccountJnlMapper accountJnlMapper;
    @Autowired
    private BsDailyBonusMapper bonusMapper;
    @Autowired
    private BsProductMapper productMapper;
    @Autowired
    private BsBonusGrantPlanMapper bonusGrantPlanMapper;
    @Autowired
    private AlgorithmService algorithmService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private SendWechatByRabbitService sendWechatByRabbitService;
    

    @Override
    public void referrerTakeAll(Integer selfUserId, Integer referrerUserId, Integer subAccountId, Double amount) {
        try {
            //判断推荐人是否存在
            BsUser recommend = userMapper.selectByPrimaryKey(referrerUserId);
            if(recommend != null && recommend.getStatus() == Constants.USER_STATUS_1) {
                //查询产品子账户
                BsSubAccount productAccount = subAccountMapper.selectByPrimaryKey(subAccountId);
                //查询理财产品信息
                BsProduct product = productMapper.selectByPrimaryKey(productAccount.getProductId());

                String key = "";
                switch(product.getTerm()) {
                    case -7:
                        key = Constants.PUSH_MONEY_RATE_7DAY;
                        break;
                    case 1:
                        key = Constants.PUSH_MONEY_RATE_1;
                        break;
                    case 3:
                        key = Constants.PUSH_MONEY_RATE_3;
                        break;
                    case 6:
                        key = Constants.PUSH_MONEY_RATE_6;
                        break;
                    case 12:
                        key = Constants.PUSH_MONEY_RATE_12;
                        break;
                }
                //获得奖励金利率
                BsSysConfig config = sysConfigMapper.selectByPrimaryKey(key);
                if(config != null) {
                    //获得总奖励金
                    BigDecimal rateValue = MoneyUtil.divide(config.getConfValue(), "100", 5);
                    BigDecimal totalBonus = MoneyUtil.multiply(amount, rateValue.doubleValue());
                    //计算每个月（最后一月除外）平均奖励金,1月期的奖励金即为总奖励金，默认赋值为一月期
                    BigDecimal avgBonus = totalBonus.setScale(2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal lastMonth = totalBonus.setScale(2, BigDecimal.ROUND_HALF_UP);
                    Integer term = product.getTerm();
                    //负数期限暂定表示小于30天，故此处期数设为1期
                    if(term < 0){
                        term = 1;
                    }
                    if(term > 1){
                        avgBonus = totalBonus.divide(new BigDecimal(term), 2, BigDecimal.ROUND_HALF_UP);
                        lastMonth = MoneyUtil.subtract(totalBonus.doubleValue(), avgBonus.multiply(new BigDecimal(term-1)).doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                    //插入奖励金发放计划表，第一个月记录直接为发放成功状态
                    for(int i=1; i<=term; i++){
                        BsBonusGrantPlan plan = new BsBonusGrantPlan();
                        plan.setAmount(i==term?lastMonth.doubleValue():avgBonus.doubleValue());
                        plan.setBeRecommendUserId(selfUserId);
                        plan.setCreateTime(new Date());
                        plan.setGrantDate(DateUtil.addDays(new Date(), 30*(i-1)));
                        plan.setGrantType(Constants.BONUS_GRANT_TYPE_ALL);//推荐人拿所有奖励金
                        plan.setSerialNo(i);
                        plan.setStatus(Constants.BONUS_GRANT_STATUS_INIT);
                        plan.setSubAccountId(subAccountId);
                        plan.setUpdateTime(new Date());
                        plan.setUserId(referrerUserId);
                        bonusGrantPlanMapper.insertSelective(plan);
                        //首期发放即时到账
                        if(i==1){
                            grantBonus(plan);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eachTakePart(Integer selfUserId, Integer referrerUserId, Integer subAccountId, Double amount, boolean newRules) {
        try {
            //判断推荐人是否存在
            BsUser recommend = userMapper.selectByPrimaryKey(referrerUserId);
            if(recommend != null && recommend.getStatus() == Constants.USER_STATUS_1) {
                //查询产品子账户
                BsSubAccount productAccount = subAccountMapper.selectByPrimaryKey(subAccountId);
                //查询理财产品信息
                BsProduct product = productMapper.selectByPrimaryKey(productAccount.getProductId());

                String referrerKey = "";
                String selfKey = "";
                if(newRules) {
                    switch(product.getTerm()) {
                        case -7:
                            referrerKey = Constants.BONUS_RATE_4_REFERRER_7DAY_NEW;
                            selfKey = Constants.BONUS_RATE_4_SELF_7DAY_NEW;
                            break;
                        case 1:
                            referrerKey = Constants.BONUS_RATE_4_REFERRER_1MONTH_NEW;
                            selfKey = Constants.BONUS_RATE_4_SELF_1MONTH_NEW;
                            break;
                        case 3:
                            referrerKey = Constants.BONUS_RATE_4_REFERRER_3MONTH_NEW;
                            selfKey = Constants.BONUS_RATE_4_SELF_3MONTH_NEW;
                            break;
                        case 6:
                            referrerKey = Constants.BONUS_RATE_4_REFERRER_6MONTH_NEW;
                            selfKey = Constants.BONUS_RATE_4_SELF_6MONTH_NEW;
                            break;
                        case 12:
                            referrerKey = Constants.BONUS_RATE_4_REFERRER_1YEAR_NEW;
                            selfKey = Constants.BONUS_RATE_4_SELF_1YEAR_NEW;
                            break;
                    }
                } else {
                    switch(product.getTerm()) {
                        case -7:
                            referrerKey = Constants.BONUS_RATE_4_REFERRER_7DAY;
                            selfKey = Constants.BONUS_RATE_4_SELF_7DAY;
                            break;
                        case 1:
                            referrerKey = Constants.BONUS_RATE_4_REFERRER_1MONTH;
                            selfKey = Constants.BONUS_RATE_4_SELF_1MONTH;
                            break;
                        case 3:
                            referrerKey = Constants.BONUS_RATE_4_REFERRER_3MONTH;
                            selfKey = Constants.BONUS_RATE_4_SELF_3MONTH;
                            break;
                        case 6:
                            referrerKey = Constants.BONUS_RATE_4_REFERRER_6MONTH;
                            selfKey = Constants.BONUS_RATE_4_SELF_6MONTH;
                            break;
                        case 12:
                            referrerKey = Constants.BONUS_RATE_4_REFERRER_1YEAR;
                            selfKey = Constants.BONUS_RATE_4_SELF_1YEAR;
                            break;
                    }
                }
                //获得奖励金利率
                BsSysConfig referrerConfig = sysConfigMapper.selectByPrimaryKey(referrerKey);
                BsSysConfig selfConfig = sysConfigMapper.selectByPrimaryKey(selfKey);
                if(referrerConfig != null && selfConfig != null) {
                    //获得总奖励金
                    BigDecimal referrerRateValue = MoneyUtil.divide(referrerConfig.getConfValue(), "100", 5);
                    BigDecimal referrerTotalBonus = MoneyUtil.multiply(amount, referrerRateValue.doubleValue());
                    BigDecimal selfRateValue = MoneyUtil.divide(selfConfig.getConfValue(), "100", 5);
                    BigDecimal selfTotalBonus = MoneyUtil.multiply(amount, selfRateValue.doubleValue());
                    //计算每个月（最后一月除外）平均奖励金,1月期（及以下）的奖励金即为总奖励金，默认赋值为一月期
                    BigDecimal referrerAvgBonus = referrerTotalBonus.setScale(2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal referrerLastMonth = referrerTotalBonus.setScale(2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal selfAvgBonus = selfTotalBonus.setScale(2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal selfLastMonth = selfTotalBonus.setScale(2, BigDecimal.ROUND_HALF_UP);
                    Integer term = product.getTerm();
                    //负数期限暂定表示小于30天，故此处期数设为1期
                    if(term < 0){
                        term = 1;
                    }
                    if(term > 1){
                        referrerAvgBonus = referrerTotalBonus.divide(new BigDecimal(term), 2, BigDecimal.ROUND_HALF_UP);
                        referrerLastMonth = MoneyUtil.subtract(referrerTotalBonus.doubleValue(), referrerAvgBonus.multiply(new BigDecimal(term-1)).doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        selfAvgBonus = selfTotalBonus.divide(new BigDecimal(term), 2, BigDecimal.ROUND_HALF_UP);
                        selfLastMonth = MoneyUtil.subtract(selfTotalBonus.doubleValue(), selfAvgBonus.multiply(new BigDecimal(term-1)).doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                    //插入奖励金发放计划表，第一个月记录直接为发放成功状态
                    for(int i=1; i<=term; i++){
                        //推荐人相关
                        BsBonusGrantPlan plan = new BsBonusGrantPlan();
                        plan.setAmount(i==term?referrerLastMonth.doubleValue():referrerAvgBonus.doubleValue());
                        plan.setBeRecommendUserId(selfUserId);
                        plan.setCreateTime(new Date());
                        plan.setGrantDate(DateUtil.addDays(new Date(), 30*(i-1)));
                        plan.setGrantType(Constants.BONUS_GRANT_TYPE_PART);//推荐人、理财人各自拿部分奖励金
                        plan.setSerialNo(i);
                        plan.setStatus(Constants.BONUS_GRANT_STATUS_INIT);
                        plan.setSubAccountId(subAccountId);
                        plan.setUpdateTime(new Date());
                        plan.setUserId(referrerUserId);
                        bonusGrantPlanMapper.insertSelective(plan);
                        //理财人相关
                        BsBonusGrantPlan selfPlan = new BsBonusGrantPlan();
                        selfPlan.setAmount(i==term?selfLastMonth.doubleValue():selfAvgBonus.doubleValue());
                        selfPlan.setBeRecommendUserId(selfUserId);
                        selfPlan.setCreateTime(new Date());
                        selfPlan.setGrantDate(DateUtil.addDays(new Date(), 30*(i-1)));
                        selfPlan.setGrantType(Constants.BONUS_GRANT_TYPE_PART);//推荐人、理财人各自拿部分奖励金
                        selfPlan.setSerialNo(i);
                        selfPlan.setStatus(Constants.BONUS_GRANT_STATUS_INIT);
                        selfPlan.setSubAccountId(subAccountId);
                        selfPlan.setUpdateTime(new Date());
                        selfPlan.setUserId(selfUserId);
                        bonusGrantPlanMapper.insertSelective(selfPlan);

                        //首期发放即时到账
                        if(i==1){
                            grantBonus(plan);
                            grantBonus(selfPlan);
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selfTakePart(Integer selfUserId, Integer subAccountId, Double amount, boolean newRules) {
        try {
            //查询产品子账户
            BsSubAccount productAccount = subAccountMapper.selectByPrimaryKey(subAccountId);
            //查询理财产品信息
            BsProduct product = productMapper.selectByPrimaryKey(productAccount.getProductId());

            String selfKey = "";
            if(newRules) {
                switch(product.getTerm()) {
                    case -7:
                        selfKey = Constants.BONUS_RATE_4_SELF_7DAY_NEW;
                        break;
                    case 1:
                        selfKey = Constants.BONUS_RATE_4_SELF_1MONTH_NEW;
                        break;
                    case 3:
                        selfKey = Constants.BONUS_RATE_4_SELF_3MONTH_NEW;
                        break;
                    case 6:
                        selfKey = Constants.BONUS_RATE_4_SELF_6MONTH_NEW;
                        break;
                    case 12:
                        selfKey = Constants.BONUS_RATE_4_SELF_1YEAR_NEW;
                        break;
                }
            } else {
                switch(product.getTerm()) {
                    case -7:
                        selfKey = Constants.BONUS_RATE_4_SELF_7DAY;
                        break;
                    case 1:
                        selfKey = Constants.BONUS_RATE_4_SELF_1MONTH;
                        break;
                    case 3:
                        selfKey = Constants.BONUS_RATE_4_SELF_3MONTH;
                        break;
                    case 6:
                        selfKey = Constants.BONUS_RATE_4_SELF_6MONTH;
                        break;
                    case 12:
                        selfKey = Constants.BONUS_RATE_4_SELF_1YEAR;
                        break;
                }
            }
            //获得奖励金利率
            BsSysConfig selfConfig = sysConfigMapper.selectByPrimaryKey(selfKey);
            if(selfConfig != null) {
                //获得总奖励金
                BigDecimal selfRateValue = MoneyUtil.divide(selfConfig.getConfValue(), "100", 5);
                BigDecimal selfTotalBonus = MoneyUtil.multiply(amount, selfRateValue.doubleValue());
                //计算每个月（最后一月除外）平均奖励金,1月期的奖励金即为总奖励金，默认赋值为一月期
                BigDecimal selfAvgBonus = selfTotalBonus.setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal selfLastMonth = selfTotalBonus.setScale(2, BigDecimal.ROUND_HALF_UP);
                Integer term = product.getTerm();
                //负数期限暂定表示小于30天，故此处期数设为1期
                if(term < 0){
                    term = 1;
                }
                if(term > 1){
                    selfAvgBonus = selfTotalBonus.divide(new BigDecimal(term), 2, BigDecimal.ROUND_HALF_UP);
                    selfLastMonth = MoneyUtil.subtract(selfTotalBonus.doubleValue(), selfAvgBonus.multiply(new BigDecimal(term-1)).doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                //插入奖励金发放计划表，第一个月记录直接为发放成功状态
                for(int i=1; i<=term; i++){
                    //理财人相关
                    BsBonusGrantPlan selfPlan = new BsBonusGrantPlan();
                    selfPlan.setAmount(i==term?selfLastMonth.doubleValue():selfAvgBonus.doubleValue());
                    selfPlan.setBeRecommendUserId(selfUserId);
                    selfPlan.setCreateTime(new Date());
                    selfPlan.setGrantDate(DateUtil.addDays(new Date(), 30*(i-1)));
                    selfPlan.setGrantType(Constants.BONUS_GRANT_TYPE_BUYER_PART);//理财人拿部分奖励金
                    selfPlan.setSerialNo(i);
                    selfPlan.setStatus(Constants.BONUS_GRANT_STATUS_INIT);
                    selfPlan.setSubAccountId(subAccountId);
                    selfPlan.setUpdateTime(new Date());
                    selfPlan.setUserId(selfUserId);
                    bonusGrantPlanMapper.insertSelective(selfPlan);

                    //首期发放即时到账
                    if(i==1){
                        grantBonus(selfPlan);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean grantBonus(final BsBonusGrantPlan plan) {
        // 1. 生成奖励金发放计划
        // 2. 修改用户信息表-当前奖励金。奖励金发放总额
        // 3. 修改用户奖励金子账户 - JLJ
        // 4. 新增用户记账流水表
        // 5. 新增用户奖励金明细表 - bs_daily_bonus
        // 6. 修改发放计划表发放状态

        try {
            if(plan == null){
                return false;
            }
            final BsUser user = userMapper.selectByPKForLock(plan.getUserId());
            transactionTemplate.execute(new TransactionCallbackWithoutResult(){

                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {

                    BigDecimal bonus = new BigDecimal(plan.getAmount());
                    //修改用户信息表
                    BsUser updateUser = new BsUser();
                    updateUser.setId(user.getId());
                    updateUser.setCurrentBonus(MoneyUtil.add(user.getCurrentBonus()==null?0.0:user.getCurrentBonus(), bonus.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    updateUser.setTotalBonus(MoneyUtil.add(user.getTotalBonus()==null?0.0:user.getTotalBonus(), bonus.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    userMapper.updateByPrimaryKeySelective(updateUser);
                    //修改用户子账户表
                    BsSubAccount tempJlj = subAccountMapper.selectSingleSubActByUserAndType(user.getId(),Constants.PRODUCT_TYPE_JLJ);
                    BsSubAccount subJljAccountLock = subAccountMapper.selectSubAccountByIdForLock(tempJlj.getId());
                    BsSubAccount readySubJljAccount = new BsSubAccount();
                    readySubJljAccount.setId(subJljAccountLock.getId());
                    readySubJljAccount.setBalance(MoneyUtil.add(subJljAccountLock.getBalance(), bonus.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readySubJljAccount.setAvailableBalance(MoneyUtil.add(subJljAccountLock.getAvailableBalance(), bonus.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readySubJljAccount.setCanWithdraw(MoneyUtil.add(subJljAccountLock.getCanWithdraw(), bonus.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    subAccountMapper.updateByPrimaryKeySelective(readySubJljAccount);
                    //新增用户记账流水表
                    BsAccountJnl accountJljJnl = new BsAccountJnl();
                    accountJljJnl.setTransTime(new Date());
                    accountJljJnl.setTransCode(Constants.USER_RECOMMEND_BONUS);
                    accountJljJnl.setTransName("获得推荐奖励");
                    accountJljJnl.setTransAmount(bonus.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    accountJljJnl.setSysTime(new Date());
                    accountJljJnl.setCdFlag1(Constants.CD_FLAG_C);
                    accountJljJnl.setUserId1(user.getId());
                    accountJljJnl.setCdFlag2(Constants.CD_FLAG_D);
                    accountJljJnl.setUserId2(user.getId());
                    accountJljJnl.setAccountId2(subJljAccountLock.getAccountId());
                    accountJljJnl.setSubAccountId2(subJljAccountLock.getId());
                    accountJljJnl.setSubAccountCode2(subJljAccountLock.getCode());
                    accountJljJnl.setBeforeBalance2(subJljAccountLock.getBalance());
                    accountJljJnl.setAfterBalance2(readySubJljAccount.getBalance());
                    accountJljJnl.setBeforeAvialableBalance2(subJljAccountLock.getAvailableBalance());
                    accountJljJnl.setAfterAvialableBalance2(readySubJljAccount.getAvailableBalance());
                    accountJljJnl.setBeforeFreezeBalance2(subJljAccountLock.getFreezeBalance());
                    accountJljJnl.setAfterFreezeBalance2(subJljAccountLock.getFreezeBalance());
                    accountJljJnl.setFee(0.0);
                    accountJnlMapper.insertSelective(accountJljJnl);
                    //新增用户奖励金明细表
                    BsDailyBonus daily = new BsDailyBonus();
                    daily.setUserId(user.getId());
                    daily.setBeRecommendUserId(plan.getBeRecommendUserId());
                    daily.setSubAccountId(plan.getSubAccountId());
                    daily.setBonus(bonus.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    daily.setTime(new Date());
                    if(Constants.BONUS_GRANT_TYPE_BONUS_4_ACTIVITY.equals(plan.getGrantType())){
                        daily.setType(Constants.Daily_BONUS_TYPE_ACTIVITY);
                    }else{
                        daily.setType(Constants.Daily_BONUS_TYPE_RECOMMEND);
                    }
                    daily.setNote(plan.getNote());
                    bonusMapper.insertSelective(daily);
                    //修改计划表发放状态
                    BsBonusGrantPlan planTemp = new BsBonusGrantPlan();
                    planTemp.setId(plan.getId());
                    planTemp.setStatus(Constants.BONUS_GRANT_STATUS_SUCC);
                    planTemp.setFinishDate(new Date());
                    planTemp.setUpdateTime(new Date());
                    bonusGrantPlanMapper.updateByPrimaryKeySelective(planTemp);
                }

            });
            //判断：若奖励金发放类型不为活动奖励且发放期数为第一期（grant_type ！= BONUS_4_ACTIVITY  and  serial_no=1 ），则立即发放微信通知
            logger.info("=============奖励金计划plan.getGrantType()"+plan.getGrantType()+"plan.getSerialNo()"+plan.getSerialNo()+"plan.getAmount()"+plan.getAmount());
            if(!Constants.BONUS_GRANT_TYPE_BONUS_4_ACTIVITY.equals(plan.getGrantType()) && plan.getSerialNo() == 1 && plan.getAmount()>0){
            	sendWechatByRabbitService.bonusArrive(user.getId(), plan.getAmount().toString(), DateUtil.format(new Date()));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public String getBonusGrantTypeByUserId(Integer userId) {
        BsUser user = userMapper.selectByPrimaryKey(userId);
        BsSysConfig config = sysConfigMapper.selectByPrimaryKey(Constants.REGISTDATE_SEPARATE_4_BONUS);
        //分隔时间节点
        Date separateDate = DateUtil.parseDate(config.getConfValue());
        //用户注册时间
        Date registDate = user.getRegisterTime();
        //之前的用户
        if(registDate.compareTo(separateDate) < 0){
            return Constants.BONUS_GRANT_TYPE_ALL;
        }
        //之后的用户
        else{
            if(null != user.getAgentId() && (31 == user.getAgentId() || 34 == user.getAgentId() || 49 == user.getAgentId()))
                return Constants.BONUS_GRANT_TYPE_BUYER_PART;
            else
                return Constants.BONUS_GRANT_TYPE_PART;
        }
    }

    @Override
    public void entrustReferrerTakeAll(Integer selfUserId, Integer referrerUserId, Integer subAccountId, Double amount) {
        try {
            logger.info("委托计划奖励金发放，推荐人拿所有奖励金请求信息：{}", "selfUserId = " + selfUserId + "; referrerUserId = " + referrerUserId + "; subAccountId = " +subAccountId + "; amount = " + amount);
            //判断推荐人是否存在
            BsUser recommend = userMapper.selectByPrimaryKey(referrerUserId);
            if(recommend != null && recommend.getStatus() == Constants.USER_STATUS_1) {
                //查询产品子账户
                BsSubAccount productAccount = subAccountMapper.selectByPrimaryKey(subAccountId);
                //查询理财产品信息
                BsProduct product = productMapper.selectByPrimaryKey(productAccount.getProductId());

                //获得奖励金年化利率
                BsSysConfig config = sysConfigMapper.selectByPrimaryKey(Constants.PUSH_MONEY_RATE_YEAR_INSTALLMENT);

                List<AverageCapitalPlusInterestVO> interestPlanList = algorithmService.calAverageCapitalPlusInterestPlan(amount, product.getTerm(), Double.valueOf(config.getConfValue()));
                //插入奖励金发放计划表，第一个月记录直接为发放成功状态
                for (AverageCapitalPlusInterestVO vo : interestPlanList) {
                    BsBonusGrantPlan plan = new BsBonusGrantPlan();
                    plan.setAmount(vo.getPlanInterest());
                    plan.setBeRecommendUserId(selfUserId);
                    plan.setCreateTime(new Date());
                    plan.setGrantDate(DateUtil.addDays(new Date(), 30*(vo.getRepaySerial()-1)));
                    plan.setGrantType(Constants.BONUS_GRANT_TYPE_ALL);//推荐人拿所有奖励金
                    plan.setSerialNo(vo.getRepaySerial());
                    plan.setStatus(Constants.BONUS_GRANT_STATUS_INIT);
                    plan.setSubAccountId(subAccountId);
                    plan.setUpdateTime(new Date());
                    plan.setUserId(referrerUserId);
                    bonusGrantPlanMapper.insertSelective(plan);
                    //首期发放即时到账
                    if(vo.getRepaySerial().equals(1)){
                        grantBonus(plan);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void entrustEachTakePart(Integer selfUserId, Integer referrerUserId, Integer subAccountId, Double amount, boolean newRules) {
        try {
            logger.info("委托计划奖励金发放，推荐人和理财人各拿部分奖励金请求信息：{}", "selfUserId = " + selfUserId + "; referrerUserId = " + referrerUserId + "; subAccountId = " +subAccountId + "; amount = " + amount);
            //判断推荐人是否存在
            BsUser recommend = userMapper.selectByPrimaryKey(referrerUserId);
            if(recommend != null && recommend.getStatus() == Constants.USER_STATUS_1) {
                //查询产品子账户
                BsSubAccount productAccount = subAccountMapper.selectByPrimaryKey(subAccountId);
                //查询理财产品信息
                BsProduct product = productMapper.selectByPrimaryKey(productAccount.getProductId());
                String referrerKey;
                String selfKey;
                if(newRules) {
                    referrerKey = Constants.BONUS_RATE_4_REFERRER_INSTALLMENT_NEW;
                    selfKey = Constants.BONUS_RATE_4_SELF_INSTALLMENT_NEW;
                } else {
                    referrerKey = Constants.BONUS_RATE_4_REFERRER_INSTALLMENT;
                    selfKey = Constants.BONUS_RATE_4_SELF_INSTALLMENT;
                }

                //获得奖励金利率
                BsSysConfig referrerConfig = sysConfigMapper.selectByPrimaryKey(referrerKey);
                BsSysConfig selfConfig = sysConfigMapper.selectByPrimaryKey(selfKey);

                List<AverageCapitalPlusInterestVO> referrerInterestPlanList = algorithmService.calAverageCapitalPlusInterestPlan(amount, product.getTerm(), Double.valueOf(referrerConfig.getConfValue()));
                List<AverageCapitalPlusInterestVO> selfInterestPlanList = algorithmService.calAverageCapitalPlusInterestPlan(amount, product.getTerm(), Double.valueOf(referrerConfig.getConfValue()));
                //插入奖励金发放计划表，第一个月记录直接为发放成功状态
                for (AverageCapitalPlusInterestVO vo : referrerInterestPlanList) {
                    // 推荐人拿部分奖励金
                    BsBonusGrantPlan plan = new BsBonusGrantPlan();
                    plan.setAmount(vo.getPlanInterest());
                    plan.setBeRecommendUserId(selfUserId);
                    plan.setCreateTime(new Date());
                    plan.setGrantDate(DateUtil.addDays(new Date(), 30*(vo.getRepaySerial()-1)));
                    plan.setGrantType(Constants.BONUS_GRANT_TYPE_PART);
                    plan.setSerialNo(vo.getRepaySerial());
                    plan.setStatus(Constants.BONUS_GRANT_STATUS_INIT);
                    plan.setSubAccountId(subAccountId);
                    plan.setUpdateTime(new Date());
                    plan.setUserId(referrerUserId);
                    bonusGrantPlanMapper.insertSelective(plan);
                    //首期发放即时到账
                    if(vo.getRepaySerial().equals(1)){
                        grantBonus(plan);
                    }
                }
                for (AverageCapitalPlusInterestVO vo : selfInterestPlanList) {
                    // 理财人拿部分奖励金
                    BsBonusGrantPlan plan = new BsBonusGrantPlan();
                    plan.setAmount(vo.getPlanInterest());
                    plan.setBeRecommendUserId(selfUserId);
                    plan.setCreateTime(new Date());
                    plan.setGrantDate(DateUtil.addDays(new Date(), 30*(vo.getRepaySerial()-1)));
                    plan.setGrantType(Constants.BONUS_GRANT_TYPE_PART);
                    plan.setSerialNo(vo.getRepaySerial());
                    plan.setStatus(Constants.BONUS_GRANT_STATUS_INIT);
                    plan.setSubAccountId(subAccountId);
                    plan.setUpdateTime(new Date());
                    plan.setUserId(selfUserId);
                    bonusGrantPlanMapper.insertSelective(plan);
                    //首期发放即时到账
                    if(vo.getRepaySerial().equals(1)){
                        grantBonus(plan);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void entrustSelfTakePart(Integer selfUserId, Integer subAccountId, Double amount, boolean newRules) {
        try {
            logger.info("委托计划奖励金发放，理财人拿部分奖励金请求信息：{}", "selfUserId = " + selfUserId + "; subAccountId = " +subAccountId + "; amount = " + amount);
            //查询产品子账户
            BsSubAccount productAccount = subAccountMapper.selectByPrimaryKey(subAccountId);
            //查询理财产品信息
            BsProduct product = productMapper.selectByPrimaryKey(productAccount.getProductId());
            String selfKey;
            if(newRules) {
                selfKey = Constants.BONUS_RATE_4_SELF_INSTALLMENT_NEW;
            } else {
                selfKey = Constants.BONUS_RATE_4_SELF_INSTALLMENT;
            }

            //获得奖励金利率
            BsSysConfig selfConfig = sysConfigMapper.selectByPrimaryKey(selfKey);

            List<AverageCapitalPlusInterestVO> selfInterestPlanList = algorithmService.calAverageCapitalPlusInterestPlan(amount, product.getTerm(), Double.valueOf(selfConfig.getConfValue()));
            //插入奖励金发放计划表，第一个月记录直接为发放成功状态
            for (AverageCapitalPlusInterestVO vo : selfInterestPlanList) {
                // 理财人拿部分奖励金
                BsBonusGrantPlan plan = new BsBonusGrantPlan();
                plan.setAmount(vo.getPlanInterest());
                plan.setBeRecommendUserId(selfUserId);
                plan.setCreateTime(new Date());
                plan.setGrantDate(DateUtil.addDays(new Date(), 30*(vo.getRepaySerial()-1)));
                plan.setGrantType(Constants.BONUS_GRANT_TYPE_PART);
                plan.setSerialNo(vo.getRepaySerial());
                plan.setStatus(Constants.BONUS_GRANT_STATUS_INIT);
                plan.setSubAccountId(subAccountId);
                plan.setUpdateTime(new Date());
                plan.setUserId(selfUserId);
                bonusGrantPlanMapper.insertSelective(plan);
                //首期发放即时到账
                if(vo.getRepaySerial().equals(1)){
                    grantBonus(plan);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String duringActivity(Integer activityId) {
        return activityService.duringActivity(activityId);
    }

    @Override
    public void newBonus2019Process(Integer selfUserId, Integer referrerUserId, Integer subAccountId, Double amount, String bonusGrantType, String propertySymbol) {
        logger.info("【2019年1月1日之后的用户奖励金发放处理】开始");
        BsSubAccount subAccount = subAccountMapper.selectByPrimaryKey(subAccountId);
        BsUser user = userMapper.selectByPrimaryKey(selfUserId);
        String isStart = activityService.duringActivity(user.getRegisterTime(), Constants.ACTIVITY_ID_2017_RECOMMEND);
        if(Constants.ACTIVITY_IS_START.equals(isStart)) {
            // 活动期间注册||活动未开始就已经注册了（为了确保2017年12月31日注册，2018年12月28号购买，2019年1月1号站岗到期的情况）
            BsSysConfig config = sysConfigMapper.selectByPrimaryKey(Constants.CONFIG_KEYBONUS_RATE_EFFECTIVE_DAYS);
            Integer days = StringUtil.isBlank(config.getConfValue()) ? 365 : Integer.valueOf(config.getConfValue());
            int betweenDays = 0;
            String endDate = "";
            try {
                betweenDays = com.pinting.business.util.DateUtil.getBetweenDays(user.getRegisterTime(), subAccount.getOpenTime());
                endDate = DateUtil.formatYYYYMMDD(DateUtil.addDays(user.getRegisterTime(), days - 1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            logger.info("【2019年1月1日之后的用户奖励金发放处理】用户 {} 注册时间：{}，结束时间：{}，投资时间：{}，相差天数：{}", selfUserId, DateUtil.formatYYYYMMDD(user.getRegisterTime()), endDate, DateUtil.formatYYYYMMDD(subAccount.getOpenTime()), betweenDays);
            if(betweenDays <= days && betweenDays > 0) {
                if(PartnerEnum.ZAN.getCode().equals(propertySymbol)){
                    if(Constants.BONUS_GRANT_TYPE_PART.equals(bonusGrantType) || Constants.BONUS_GRANT_TYPE_ALL.equals(bonusGrantType)) {
                        //推荐人、购买人各拿部分奖励金
                        if(referrerUserId == null)
                            return;
                        logger.info("=========【用户奖励金发放处理】REG_D推荐人、购买人各拿部分奖励金=========");
                        this.entrustEachTakePart(selfUserId, referrerUserId, subAccountId, amount, true);
                    } else if(Constants.BONUS_GRANT_TYPE_BUYER_PART.equals(bonusGrantType)) {
                        //购买人拿部分奖励金
                        logger.info("=========【用户奖励金发放处理】REG_D购买人拿部分奖励金=========");
                        this.entrustSelfTakePart(selfUserId, subAccountId, amount, true);
                    }
                }else{
                    if(Constants.BONUS_GRANT_TYPE_PART.equals(bonusGrantType) || Constants.BONUS_GRANT_TYPE_ALL.equals(bonusGrantType)){
                        //推荐人、购买人各拿部分奖励金
                        if(referrerUserId == null)
                            return;
                        logger.info("=========【用户奖励金发放处理】REG推荐人、购买人各拿部分奖励金=========");
                        this.eachTakePart(selfUserId, referrerUserId, subAccountId, amount, true);
                    } else if(Constants.BONUS_GRANT_TYPE_BUYER_PART.equals(bonusGrantType)) {
                        //购买人拿部分奖励金
                        logger.info("=========【用户奖励金发放处理】REG购买人拿部分奖励金=========");
                        this.selfTakePart(selfUserId, subAccountId, amount, true);
                    }
                }
            }
        } else if(Constants.ACTIVITY_IS_NOT_START.equals(isStart)) {
            String buyInActivity = activityService.duringActivity(subAccount.getOpenTime(), Constants.ACTIVITY_ID_2017_RECOMMEND);
            // 购买时间在活动期间内
            if(Constants.ACTIVITY_IS_START.equals(buyInActivity)) {
                if(PartnerEnum.ZAN.getCode().equals(propertySymbol)) {
                    logger.info("【2019年1月1日之后的用户奖励金发放处理】购买时间在活动期间内，站岗到期时间在活动结束之后，用户注册时间在活动开始之前");
                    if(Constants.BONUS_GRANT_TYPE_PART.equals(bonusGrantType) || Constants.BONUS_GRANT_TYPE_ALL.equals(bonusGrantType)) {
                        //推荐人、购买人各拿部分奖励金
                        if(referrerUserId == null)
                            return;
                        logger.info("=========【用户奖励金发放处理】REG_D推荐人、购买人各拿部分奖励金=========");
                        this.entrustEachTakePart(selfUserId, referrerUserId, subAccountId, amount, true);
                    } else if(Constants.BONUS_GRANT_TYPE_BUYER_PART.equals(bonusGrantType)) {
                        //购买人拿部分奖励金
                        logger.info("=========【用户奖励金发放处理】REG_D购买人拿部分奖励金=========");
                        this.entrustSelfTakePart(selfUserId, subAccountId, amount, true);
                    }
                }
            }
        }

        logger.info("【2019年1月1日之后的用户奖励金发放处理】结束");
    }
}
