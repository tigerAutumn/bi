package com.pinting.business.accounting.finance.service.impl;

import com.pinting.business.accounting.finance.service.UserBonusExtractService;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.service.site.BonusService;
import com.pinting.business.service.site.SMSService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.in.util.MethodRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by babyshark on 2016/9/10.
 */
@Service
public class UserBonusExtractServiceImpl implements UserBonusExtractService {
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    private Logger log = LoggerFactory.getLogger(UserBonusExtractServiceImpl.class);
    @Autowired
    private BsSysSubAccountService bsSysSubAccountService;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private BsSpecialJnlMapper bsSpecialJnlMapper;
    @Autowired
    private BsSysSubAccountMapper bsSysSubAccountMapper;
    @Autowired
    private BsAccountJnlMapper bsAccountJnlMapper;
    @Autowired
    private BonusService bonusService;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private BsUserTransDetailMapper bsUserTransDetailMapper;
    @Autowired
    private BsSysAccountJnlMapper bsSysAccountJnlMapper;
    @Autowired
    private BsSpecialJnlService bsSpecialJnlService;
    @Autowired
    private SMSService smsService;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;


    /**
     * 奖励金转余额
     * 用户子账户JLJ转JSH，系统子账户JSH转USER
     *
     * @param userId
     * @param amount
     * @return
     */
    /**
     * 用户奖励金转余额
     *
     * 1.判断系统子账户JSH的AvailableBalance金额是否足够
     *
     * 2.不足够-bs_special_jnl中新增一条记录
     * 3.不足够-bs_sub_account的JLJ余额减少
     * 4.不足够-记录bs_user_trans_detail(状态为处理中)
     * 5.不足够-修改bs_user减去current_bonus的值
     * 6.不足够-向管理运营者发布告警短信，提醒运营管理者及时充值到19付账户中
     *
     *
     * 7.足够-bs_daily_bonus插入一条奖励金转余额的奖励记录
     * 8.足够-用户子账户表，用户奖励金JLJ转到JSH结算户,新增用户记账流水表
     * 9.足够-系统子账户-结算户JSH、用户USER 修改
     * 10.足够-记录系统往来记账流水表
     * 11.足够-修改bs_user减去current_bonus的值
     * 12.足够-记录bs_user_trans_detail(状态为成功)
     *
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @MethodRole("APP")
    public boolean transBonusToJSH(Integer userId, Double amount) {
        // ==================================== 数据校验开始 ==============================================
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_BONUS2JSH.getKey());

            // 是否开通存管
            BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
            extExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(Constants.HFBANK_USER_EXT_STATUS_OPEN);
            List<BsHfbankUserExt> ext = bsHfbankUserExtMapper.selectByExample(extExample);
            if (CollectionUtils.isEmpty(ext)) {
                throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
            }

            // 校验奖励金是否足够用于转余额
            BsSubAccount jljSubAccount = bsSubAccountMapper.selectJLJSubAccountByUserId(userId);
            Double jljCanWithdraw = jljSubAccount.getCanWithdraw() == null ? 0d : jljSubAccount.getCanWithdraw();
            if(amount.compareTo(jljCanWithdraw) > 0) {
                log.info("======【奖励金转账户余额】：用户奖励金子账户中可提现余额不足，对应操作开始=====");
                // 1.用户奖励金子账户中可提现余额不足
                log.info("======【奖励金转账户余额】：bs_special_jnl中新增一条记录======");
                BsSpecialJnl bsSpecialJnl = new BsSpecialJnl();
                bsSpecialJnl.setUserId(userId);
                bsSpecialJnl.setType("【奖励金转提失败】：金额");
                bsSpecialJnl.setDetail("用户奖励金子账户中可提现余额不足");
                bsSpecialJnl.setOpTime(new Date());
                bsSpecialJnl.setAmount(amount);
                bsSpecialJnl.setStatus(Constants.SPECIAL_JNL_STATUS_FALL);
                bsSpecialJnlMapper.insertSelective(bsSpecialJnl);

                log.info("======【奖励金转账户余额】：记录bs_user_trans_detail(状态为失败)=====");
                // 4.记录bs_user_trans_detail(状态为处理中)
                BsUserTransDetail userTransDetail = new BsUserTransDetail();
                userTransDetail.setCreateTime(new Date());
                userTransDetail.setUpdateTime(new Date());
                userTransDetail.setUserId(userId);
                userTransDetail.setTransType(Constants.Trans_TYPE_BONUS_2_BALANCE);
                userTransDetail.setTransStatus(Constants.Trans_STATUS_FAIL);
                userTransDetail.setAmount(amount);
                userTransDetail.setTransDesc("【奖励金转余额】用户奖励金子账户中可提现余额不足");
                bsUserTransDetailMapper.insertSelective(userTransDetail);
                throw new PTMessageException(PTMessageEnum.USER_JLJ_NOT_ENOUGH);
            }
            // ==================================== 数据校验结束 ==============================================

            // 1.判断系统子账户JSH的AvailableBalance金额是否足够
            boolean isEnough = doSys(userId, amount);
            if (!isEnough) {
                log.info("======【奖励金转账户余额】：系统子账户JSH可用资金不足，对应操作开始=====");
                // 2.系统子账户JSH可用资金不足,bs_special_jnl中新增一条记录，用于后台操作
                log.info("======【奖励金转账户余额】：bs_special_jnl中新增一条记录======");
                BsSpecialJnl bsSpecialJnl = new BsSpecialJnl();
                bsSpecialJnl.setUserId(userId);
                bsSpecialJnl.setType("【奖励金转提失败】：金额");
                bsSpecialJnl.setDetail("系统子账户中JSH类型的可用余额不足");
                bsSpecialJnl.setOpTime(new Date());
                bsSpecialJnl.setAmount(amount);
                bsSpecialJnl.setStatus(Constants.SPECIAL_JNL_STATUS_DEAL);
                bsSpecialJnlMapper.insertSelective(bsSpecialJnl);

                // 3.bs_sub_account的JLJ余额减少
                log.info("======【奖励金转账户余额】：bs_sub_account的JLJ余额减少======");
                BsSubAccount userJLJ = bsSubAccountMapper
                        .selectJLJSubAccountByUserId(userId);
                BsSubAccount JLJ = new BsSubAccount();
                JLJ.setId(userJLJ.getId());
                JLJ.setBalance(-amount);
                JLJ.setAvailableBalance(-amount);
                JLJ.setCanWithdraw(-amount);
                JLJ.setLastTransDate(new Date());
                bsSubAccountMapper.updateBalancesByIncrement(JLJ);

                log.info("======【奖励金转账户余额】：记录bs_user_trans_detail(状态为处理中)=====");
                // 4.记录bs_user_trans_detail(状态为处理中)
                BsUserTransDetail userTransDetail = new BsUserTransDetail();
                userTransDetail.setCreateTime(new Date());
                userTransDetail.setUpdateTime(new Date());
                userTransDetail.setUserId(userId);
                userTransDetail.setTransType(Constants.Trans_TYPE_BONUS_2_BALANCE);
                userTransDetail.setTransStatus(Constants.Trans_STATUS_DEAL);
                userTransDetail.setAmount(amount);
                userTransDetail.setTransDesc("【奖励金转余额】系统子账户JSH金额不足");
                bsUserTransDetailMapper.insertSelective(userTransDetail);

                log.info("======【奖励金转账户余额】：修改bs_user减去current_bonus的值======");
                // 5.修改bs_user减去current_bonus的值
                BsUser bsUser = new BsUser();
                bsUser.setId(userId);
                bsUser.setCanWithdraw(0.00);
                bsUser.setCurrentBonus(-amount);
                bsUser.setTotalBonus(0.00);
                bsUserMapper.updateBonusById(bsUser);
                // 6.向管理运营者发布告警短信，提醒运营管理者及时充值到19付账户中
                smsService.sendSysManagerMobiles("奖励转余额失败充值提醒",false);
                log.info("======【奖励金转账户余额】：系统子账户JSH可用资金不足，对应操作结束=====");
                return isEnough;
            }
            return isEnough;
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_BONUS2JSH.getKey());
        }
    }

    /**
     * 针对奖励金转余额处理中交易，管理台执行通过操作
     *
     * @param special
     * @return
     */
    /**
     * 后台管理操作奖励金转余额未转成功的特殊交易
     * 1.判断系统子账户JSH的AvailableBalance金额是否足够
     *
     * 2.足够-bs_daily_bonus插入一条奖励金转余额的奖励记录
     * 3.用户子账户表，用户奖励金JLJ转到JSH结算户（仅结算户增加）,新增用户记账流水表
     * 4.系统子账户-结算户JSH、用户USER 修改
     * 5.记录系统往来记账流水表
     * 6.修改bs_user增加can_withdraw的值
     * 7.修改bs_special_jnl状态为成功
     * 8.修改bs_user_trans_detail(状态为成功)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean passBonus2JSH(BsSpecialJnl special) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_BONUS2JSH.getKey());
            BsSysSubAccount jshAccount = bsSysSubAccountMapper
                    .selectByCode(Constants.SYS_ACCOUNT_JSH);
            BsSysSubAccount userAccount = bsSysSubAccountMapper
                    .selectByCode(Constants.SYS_ACCOUNT_USER);
            if (jshAccount.getBalance() < special.getAmount()) {
                return false;
            } else {
                log.info("======【奖励金转账户余额】：开始======");
                // 2.bs_daily_bonus插入一条奖励金转余额的奖励记录
                BsDailyBonus bsDailyBonus = new BsDailyBonus();
                bsDailyBonus.setBeRecommendUserId(special.getUserId());
                bsDailyBonus.setBonus(-special.getAmount());
                bsDailyBonus.setTime(new Date());
                bsDailyBonus.setUserId(special.getUserId());
                bonusService.addDailyBonus(bsDailyBonus);

                // 3.用户子账户表，用户奖励金JLJ转到JSH结算户,新增用户记账流水表
                BsAccountJnl jnl = new BsAccountJnl();
                jnl = new BsAccountJnl();
                jnl.setTransTime(new Date());
                jnl.setTransCode(Constants.USER_BONUS_2_BALANCE);
                jnl.setTransAmount(special.getAmount());
                jnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
                jnl.setUserId1(special.getUserId());
                jnl.setUserId2(special.getUserId());
                userJLJ2JSH(jnl,"manage");
                log.debug("奖励金转到结算户，用户子账户及用户记账流水操作：" + jnl.toString());

                // 4.系统子账户-结算户JSH、用户USER 修改
                log.info("======【奖励金转账户余额】：系统子账户-结算户JSH、用户USER 修改=====");
                bsSysSubAccountService.updateJSHToUser(special.getAmount());

                // 5.记录系统往来记账流水表
                setSysAccountJnl(jshAccount, userAccount, special.getAmount());

                // 6.修改bs_user增加can_withdraw的值
                BsUser bsUser = new BsUser();
                bsUser.setId(special.getUserId());
                bsUser.setCanWithdraw(special.getAmount());
                bsUser.setCurrentBonus(0.00);
                bsUser.setTotalBonus(0.00);
                bsUserMapper.updateBonusById(bsUser);

                //7.修改bs_special_jnl状态为成功
                BsSpecialJnl specialJnl = new BsSpecialJnl();
                specialJnl.setId(special.getId());
                specialJnl.setmUserId(special.getmUserId());
                specialJnl.setStatus(Constants.SPECIAL_JNL_STATUS_SUCCESS);
                bsSpecialJnlService.update(specialJnl);

                // 8.修改bs_user_trans_detail(状态为成功)
                log.info("======【奖励金转账户余额】：修改bs_user_trans_detail(状态为成功)=====");
                BsUserTransDetail selectTransDetail = new BsUserTransDetail();

                selectTransDetail.setUserId(special.getUserId());
                selectTransDetail.setTransType(Constants.Trans_TYPE_BONUS_2_BALANCE);
                selectTransDetail.setTransStatus(Constants.Trans_STATUS_DEAL);

                BsUserTransDetail transDetail = bsUserTransDetailMapper.selectByUserTrans(selectTransDetail);
                transDetail.setUpdateTime(new Date());
                transDetail.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                transDetail.setTransDesc("【奖励金转余额】后台手动转余额成功");
                bsUserTransDetailMapper.updateByPrimaryKeySelective(transDetail);

                log.info("======【奖励金转账户余额】：结束======");
                return true;
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_BONUS2JSH.getKey());
        }
    }

    // 1.【奖励金转余额】 时，判断系统子账户JSH的AvailableBalance金额是否足够
    private boolean doSys(Integer userId, Double amount) {
        BsSysSubAccount jshAccount = bsSysSubAccountMapper
                .selectByCode(Constants.SYS_ACCOUNT_JSH);
        BsSysSubAccount userAccount = bsSysSubAccountMapper
                .selectByCode(Constants.SYS_ACCOUNT_USER);
        if (jshAccount.getBalance() < amount) {
            return false;
        } else {
            log.info("======【奖励金转账户余额】：开始======");
            // 7.bs_daily_bonus插入一条奖励金转余额的奖励记录
            BsDailyBonus bsDailyBonus = new BsDailyBonus();
            bsDailyBonus.setBeRecommendUserId(userId);
            bsDailyBonus.setBonus(-amount);
            bsDailyBonus.setTime(new Date());
            bsDailyBonus.setUserId(userId);
            bonusService.addDailyBonus(bsDailyBonus);

            // 8.用户子账户表，用户奖励金JLJ转到JSH结算户,新增用户记账流水表
            BsAccountJnl jnl = new BsAccountJnl();
            jnl = new BsAccountJnl();
            jnl.setTransTime(new Date());
            jnl.setTransCode(Constants.USER_BONUS_2_BALANCE);
            jnl.setTransAmount(amount);
            jnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
            jnl.setUserId1(userId);
            jnl.setUserId2(userId);
            userJLJ2JSH(jnl,"user");
            log.debug("奖励金转到结算户，用户子账户及用户记账流水操作：" + jnl.toString());

            // 9.系统子账户-结算户JSH、用户USER 修改
            log.info("======【奖励金转账户余额】：系统子账户-结算户JSH、用户USER 修改=====");
            bsSysSubAccountService.updateJSHToUser(amount);

            // 10.记录系统往来记账流水表
            setSysAccountJnl(jshAccount, userAccount, amount);

            // 11.修改bs_user减去current_bonus的值,增加can_withdraw的值
            BsUser bsUser = new BsUser();
            bsUser.setId(userId);
            bsUser.setCanWithdraw(amount);
            bsUser.setCurrentBonus(-amount);
            bsUser.setTotalBonus(0.00);
            bsUserMapper.updateBonusById(bsUser);

            // 12.记录bs_user_trans_detail(状态为成功)
            log.info("======【奖励金转账户余额】：记录bs_user_trans_detail(状态为成功)=====");
            BsUserTransDetail userTransDetail = new BsUserTransDetail();
            userTransDetail.setUpdateTime(new Date());
            userTransDetail.setCreateTime(new Date());
            userTransDetail.setUserId(userId);
            userTransDetail.setTransType(Constants.Trans_TYPE_BONUS_2_BALANCE);
            userTransDetail.setTransStatus(Constants.Trans_STATUS_SUCCESS);
            userTransDetail.setAmount(amount);
            userTransDetail.setTransDesc("【奖励金转余额】成功");
            bsUserTransDetailMapper.insertSelective(userTransDetail);

            log.info("======【奖励金转账户余额】：结束======");
            return true;
        }
    }

    //8.用户子账户表，用户奖励金JLJ转到JSH结算户,新增用户记账流水表
    private void userJLJ2JSH(BsAccountJnl accountJnl,String userOrManage) {
        // 用户子账户-结算户、奖励金 查询
        BsSubAccount userJSH = bsSubAccountMapper
                .selectJSHSubAccountByUserId(accountJnl.getUserId1());
        BsSubAccount userJLJ = bsSubAccountMapper
                .selectJLJSubAccountByUserId(accountJnl.getUserId1());
        // 用户记账流水表-记账
        BsAccountJnl jnl = new BsAccountJnl();
        jnl.setTransTime(accountJnl.getTransTime());
        jnl.setTransCode(accountJnl.getTransCode());
        jnl.setTransAmount(accountJnl.getTransAmount());
        jnl.setStatus(accountJnl.getStatus());
        jnl.setUserId1(accountJnl.getUserId1());
        jnl.setUserId2(accountJnl.getUserId2());
		/*jnl.setTransType(Constants.TRANS_TYPE_BONUS2BALANCE);*/
        jnl.setTransName("奖励金转余额");
        jnl.setSysTime(new Date());
        jnl.setCdFlag1(Constants.CD_FLAG_C); // 对JLJ而言是转出，为贷
        jnl.setAccountId1(userJLJ.getAccountId());
        jnl.setSubAccountId1(userJLJ.getId());
        jnl.setSubAccountCode1(userJLJ.getCode());
        jnl.setBeforeBalance1(userJLJ.getBalance());
        jnl.setBeforeAvialableBalance1(userJLJ.getAvailableBalance());
        jnl.setBeforeFreezeBalance1(userJLJ.getFreezeBalance());
        jnl.setAfterBalance1(userJLJ.getBalance() - jnl.getTransAmount());
        jnl.setAfterAvialableBalance1(userJLJ.getAvailableBalance()
                - jnl.getTransAmount());
        jnl.setAfterFreezeBalance1(userJLJ.getFreezeBalance());

        jnl.setCdFlag2(Constants.CD_FLAG_D); // 对JSH而言是转入，为借
        jnl.setAccountId2(userJSH.getAccountId());
        jnl.setSubAccountId2(userJSH.getId());
        jnl.setSubAccountCode2(userJSH.getCode());
        jnl.setBeforeBalance2(userJSH.getBalance());
        jnl.setBeforeAvialableBalance2(userJSH.getAvailableBalance());
        jnl.setBeforeFreezeBalance2(userJSH.getFreezeBalance());
        jnl.setAfterBalance2(MoneyUtil.add(userJSH.getBalance(), jnl.getTransAmount()).doubleValue());
        jnl.setAfterAvialableBalance2(MoneyUtil.add(userJSH.getAvailableBalance(), jnl.getTransAmount()).doubleValue());
        jnl.setAfterFreezeBalance2(userJSH.getFreezeBalance());
        jnl.setCdFlag2(Constants.CD_FLAG_D);
        jnl.setFee(0d);

        // 用户子账户-结算户、奖励金 修改
        log.info("======【奖励金转账户余额】：用户子账户-结算户JSH余额修改=====");
        BsSubAccount JSH = new BsSubAccount();
        JSH.setId(userJSH.getId());
        JSH.setBalance(accountJnl.getTransAmount());
        JSH.setAvailableBalance(accountJnl.getTransAmount());
        JSH.setCanWithdraw(accountJnl.getTransAmount());
        JSH.setLastTransDate(new Date());
        bsSubAccountMapper.updateBalancesByIncrement(JSH);
        if("user".equals(userOrManage)){
            log.info("======【奖励金转账户余额】：用户子账户-奖励金JLJ余额修改=====");
            BsSubAccount JLJ = new BsSubAccount();
            JLJ.setId(userJLJ.getId());
            JLJ.setBalance(-accountJnl.getTransAmount());
            JLJ.setAvailableBalance(-accountJnl.getTransAmount());
            JLJ.setCanWithdraw(-accountJnl.getTransAmount());
            JLJ.setLastTransDate(new Date());
            bsSubAccountMapper.updateBalancesByIncrement(JLJ);
        }


        log.info("======【奖励金转账户余额】：新增用户记账流水表=====");
        // 新增用户记账流水表
        log.debug("用户记账流水表记账，JLJ转JSH：" + jnl.toString());
        bsAccountJnlMapper.insertSelective(jnl);
        log.info("======交易【奖励金转账户余额】用户子账户修改及用户记账流水新增：执行结束======");

    }

    // 记录系统往来记账流水表
    //@Transactional(rollbackFor = Exception.class)
    private void setSysAccountJnl(BsSysSubAccount jshAccount,
                                  BsSysSubAccount userAccount, Double amount) {
        BsSysAccountJnl sysJnl = new BsSysAccountJnl();
        sysJnl.setTransTime(new Date());
        sysJnl.setTransCode(Constants.SYS_USER_BONUS_2_BALANCE);
        sysJnl.setTransAmount(amount);
        sysJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
        sysJnl.setTransName("奖励金转余额");
        sysJnl.setSysTime(new Date());
        sysJnl.setCdFlag1(Constants.CD_FLAG_C); // 对JLJ而言是转出，为贷
        sysJnl.setSubAccountCode1(jshAccount.getCode());
        sysJnl.setBeforeBalance1(jshAccount.getBalance());
        sysJnl.setBeforeAvialableBalance1(jshAccount.getAvailableBalance());
        sysJnl.setBeforeFreezeBalance1(jshAccount.getFreezeBalance());
        sysJnl.setAfterBalance1(MoneyUtil.subtract(jshAccount.getBalance(), amount).doubleValue());
        sysJnl.setAfterAvialableBalance1(MoneyUtil.subtract(jshAccount.getAvailableBalance(), amount).doubleValue());
        sysJnl.setAfterFreezeBalance1(jshAccount.getFreezeBalance());

        sysJnl.setCdFlag2(Constants.CD_FLAG_D); // 对JSH而言是转入，为借
        sysJnl.setSubAccountCode2(userAccount.getCode());
        sysJnl.setBeforeBalance2(userAccount.getBalance());
        sysJnl.setBeforeAvialableBalance2(userAccount.getAvailableBalance());
        sysJnl.setBeforeFreezeBalance2(userAccount.getFreezeBalance());
        sysJnl.setAfterBalance2(MoneyUtil.add(userAccount.getBalance(), amount).doubleValue());
        sysJnl.setAfterAvialableBalance2(MoneyUtil.add(userAccount.getAvailableBalance(), amount).doubleValue());
        sysJnl.setAfterFreezeBalance2(userAccount.getFreezeBalance());
        sysJnl.setCdFlag2(Constants.CD_FLAG_D);
        sysJnl.setFee(0d);

        bsSysAccountJnlMapper.insertSelective(sysJnl);
    }
}
