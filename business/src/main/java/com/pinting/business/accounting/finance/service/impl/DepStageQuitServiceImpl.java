package com.pinting.business.accounting.finance.service.impl;

import com.pinting.business.accounting.finance.service.DepStageQuitService;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:      cyb
 * Date:        2017/4/24
 * Description: 存管分期产品退出服务
 */
@Service
public class DepStageQuitServiceImpl implements DepStageQuitService {

    @Autowired
    private BsVipQuitMapper bsVipQuitMapper;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private SubAccountService subAccountService;
    @Autowired
    private BsSysSubAccountMapper bsSysSubAccountMapper;
    @Autowired
    private BsAccountJnlMapper bsAccountJnlMapper;
    @Autowired
    private BsSysAccountJnlMapper bsSysAccountJnlMapper;
    @Autowired
    private BsSysSubAccountService bsSysSubAccountService;
    @Autowired
    private BsUserMapper bsUserMapper;

    @Override
    public void vipQuitAccount(Integer quitId, Integer userId, Integer checkUserId) {
        /**
         * 1. 退出申请记录非审核成功状态，抛出异常
         * 2. 站岗户余额小于申请余额，退出失败
         * 3. 站岗户余额不小于申请金额，退出成功
         * 4. 开始记账
         *      4.0. 申请状态-退出成功
         *      4.1. 用户站岗户余额 -》 用户余额（ AUTH -， DEP_JSH +）
         *      4.2. 用户记账流水 +
         *      4.3. 系统站岗户余额 -》 系统用户余额（ BGW_AUTH_ZAN -， BGW_USER +）
         *      4.4. 系统记账流水 +
         */

        // 1. 退出申请记录非审核成功状态，抛出异常
        BsVipQuit quit =  bsVipQuitMapper.selectByPrimaryKey(quitId);
        if(null == quit) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "退出失败，申请记录不存在");
        }
        if(!Constants.VIP_QUIT_STATUS_PASS.equals(quit.getStatus())) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "退出失败，申请记录非审核通过状态");
        }
        // 2. 站岗户余额小于申请余额，退出失败
        List<BsSubAccount> auths = subAccountService.findAuthAccountByUserId(userId);
        if(CollectionUtils.isEmpty(auths)) {
            fail(quit, checkUserId);
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "退出失败，当前vip无站岗户");
        }
        BsSubAccount auth = auths.get(0);

        // 3. 站岗户余额不小于申请金额，退出成功
        if(quit.getAmount().compareTo(auth.getBalance()) > 0) {
            fail(quit, checkUserId);
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "退出失败，站岗户余额不足");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("auth", auth);
        params.put("quit", quit);
        params.put("checkUserId", checkUserId);
        params.put("userId", userId);

        // 4. 开始记账
        success(params);
    }

    /**
     * 退出失败
     * @param quit
     */
    private void fail(BsVipQuit quit, Integer checkUserId) {
        quit.setCkUserId(checkUserId);
        quit.setUpdateTime(new Date());
        quit.setCheckTime(new Date());
        quit.setStatus(Constants.VIP_QUIT_STATUS_QUIT_FAIL);
        bsVipQuitMapper.updateByPrimaryKeySelective(quit);
    }

    /**
     * 退出成功
     * @param params
     */
    @Transactional
    private void success(Map<String, Object> params) {

        // 4. 开始记账
        BsVipQuit quit = (BsVipQuit) params.get("quit");
        Integer userId = (Integer) params.get("userId");
        Integer checkUserId = (Integer) params.get("checkUserId");
        BsSubAccount authTemp = (BsSubAccount) params.get("auth");
        BsSubAccount authLock = bsSubAccountMapper.selectSubAccountByIdForLock(authTemp.getId());
        BsSubAccount depJshTemp = subAccountService.findDEPJSHSubAccountByUserId(userId);
        BsSubAccount depJshLock = bsSubAccountMapper.selectSubAccountByIdForLock(depJshTemp.getId());
        BsSubAccount auth = new BsSubAccount();
        BsSubAccount depJsh = new BsSubAccount();

        // 4.0. 申请状态-退出成功
        quit.setCkUserId(checkUserId);
        quit.setStatus(Constants.VIP_QUIT_STATUS_QUIT_SUCC);
        quit.setQuitTime(new Date());
        quit.setUpdateTime(new Date());
        bsVipQuitMapper.updateByPrimaryKeySelective(quit);

        // 4.1. 用户站岗户余额 -》 用户余额（ AUTH -，DEP_JSH +）
        // DEP_JSH +
        depJsh.setId(depJshLock.getId());
        depJsh.setBalance(MoneyUtil.add(depJshLock.getBalance(), quit.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        depJsh.setAvailableBalance(MoneyUtil.add(depJshLock.getAvailableBalance(), quit.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        depJsh.setCanWithdraw(MoneyUtil.add(depJshLock.getCanWithdraw(), quit.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        bsSubAccountMapper.updateByPrimaryKeySelective(depJsh);
        // AUTH -
        auth.setId(authLock.getId());
        auth.setBalance(MoneyUtil.subtract(authLock.getBalance(), quit.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        auth.setAvailableBalance(MoneyUtil.subtract(authLock.getAvailableBalance(), quit.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        auth.setCanWithdraw(MoneyUtil.subtract(authLock.getCanWithdraw(), quit.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        bsSubAccountMapper.updateByPrimaryKeySelective(auth);

        // 4.2. 用户记账流水 +
        BsAccountJnl accountJnl = new BsAccountJnl();
        accountJnl.setTransTime(new Date());
        accountJnl.setTransCode(Constants.USER_AUTH_QUIT);
        accountJnl.setTransName("赞分期VIP退出");
        accountJnl.setTransAmount(quit.getAmount());
        accountJnl.setSysTime(new Date());
        accountJnl.setChannelTime(null);
        accountJnl.setChannelJnlNo(null);
        accountJnl.setCdFlag1(Constants.CD_FLAG_C);
        accountJnl.setUserId1(userId);
        accountJnl.setAccountId1(authLock.getAccountId());
        accountJnl.setSubAccountId1(authLock.getId());
        accountJnl.setSubAccountCode1(authLock.getCode());
        accountJnl.setBeforeBalance1(authLock.getBalance());
        accountJnl.setAfterBalance1(auth.getBalance());
        accountJnl.setBeforeAvialableBalance1(authLock.getAvailableBalance());
        accountJnl.setAfterAvialableBalance1(auth.getAvailableBalance());
        accountJnl.setBeforeFreezeBalance1(authLock.getFreezeBalance());
        accountJnl.setAfterFreezeBalance1(auth.getFreezeBalance());
        accountJnl.setCdFlag2(Constants.CD_FLAG_D);
        accountJnl.setUserId2(userId);
        accountJnl.setAccountId2(depJshLock.getAccountId());
        accountJnl.setSubAccountId2(depJshLock.getId());
        accountJnl.setSubAccountCode2(depJshLock.getCode());
        accountJnl.setBeforeBalance2(depJshLock.getBalance());
        accountJnl.setAfterBalance2(depJsh.getBalance());
        accountJnl.setBeforeAvialableBalance2(depJshLock.getAvailableBalance());
        accountJnl.setAfterAvialableBalance2(depJsh.getAvailableBalance());
        accountJnl.setBeforeFreezeBalance2(depJshLock.getFreezeBalance());
        accountJnl.setAfterFreezeBalance2(depJsh.getFreezeBalance());
        accountJnl.setFee(0d);
        bsAccountJnlMapper.insertSelective(accountJnl);

        // 4.3. 系统站岗户余额 -》 系统用户余额（ BGW_AUTH_ZAN -， BGW_USER +）
        // BGW_USER +
        BsSysSubAccount sysUserLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_BGW_USER);
        BsSysSubAccount sysUser = new BsSysSubAccount();
        sysUser.setId(sysUserLock.getId());
        sysUser.setBalance(MoneyUtil.add(sysUserLock.getBalance(), quit.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        sysUser.setAvailableBalance(MoneyUtil.add(sysUserLock.getAvailableBalance(), quit.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        sysUser.setCanWithdraw(MoneyUtil.add(sysUserLock.getCanWithdraw(), quit.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        bsSysSubAccountMapper.updateByPrimaryKeySelective(sysUser);
        // BGW_AUTH_ZAN -
        BsSysSubAccount sysAuthLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_BGW_AUTH_ZAN);
        BsSysSubAccount sysAuth = new BsSysSubAccount();
        sysAuth.setId(sysAuthLock.getId());
        sysAuth.setBalance(MoneyUtil.subtract(sysAuthLock.getBalance(), quit.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        sysAuth.setAvailableBalance(MoneyUtil.subtract(sysAuthLock.getAvailableBalance(), quit.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        sysAuth.setCanWithdraw(MoneyUtil.subtract(sysAuthLock.getCanWithdraw(), quit.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        bsSysSubAccountMapper.updateByPrimaryKeySelective(sysAuth);

        // 4.4. 系统记账流水 +
        BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
        sysAccountJnl.setTransTime(new Date());
        sysAccountJnl.setTransCode(Constants.SYS_AUTH_QUIT);
        sysAccountJnl.setTransName("赞分期VIP退出");
        sysAccountJnl.setTransAmount(quit.getAmount());
        sysAccountJnl.setSysTime(new Date());
        sysAccountJnl.setChannelTime(null);
        sysAccountJnl.setChannelJnlNo(null);
        sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
        sysAccountJnl.setSubAccountCode1(sysAuthLock.getCode());
        sysAccountJnl.setBeforeBalance1(sysAuthLock.getBalance());
        sysAccountJnl.setAfterBalance1(sysAuth.getBalance());
        sysAccountJnl.setBeforeAvialableBalance1(sysAuthLock.getAvailableBalance());
        sysAccountJnl.setAfterAvialableBalance1(sysAuth.getAvailableBalance());
        sysAccountJnl.setBeforeFreezeBalance1(sysAuthLock.getFreezeBalance());
        sysAccountJnl.setAfterFreezeBalance1(sysAuth.getFreezeBalance());
        sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
        sysAccountJnl.setSubAccountCode2(sysUserLock.getCode());
        sysAccountJnl.setBeforeBalance2(sysUserLock.getBalance());
        sysAccountJnl.setAfterBalance2(sysUser.getBalance());
        sysAccountJnl.setBeforeAvialableBalance2(sysUserLock.getAvailableBalance());
        sysAccountJnl.setAfterAvialableBalance2(sysUser.getAvailableBalance());
        sysAccountJnl.setBeforeFreezeBalance2(sysUserLock.getFreezeBalance());
        sysAccountJnl.setAfterFreezeBalance2(sysUser.getFreezeBalance());
        sysAccountJnl.setFee(0d);
        bsSysAccountJnlMapper.insertSelective(sysAccountJnl);

        // 4.5. 用户修改
        BsUser userLock = bsUserMapper.selectByPKForLock(userId);
        BsUser user = new BsUser();
        user.setId(userLock.getId());
        user.setCanWithdraw(quit.getAmount());
        bsUserMapper.updateBonusById(user);
    }
}
