package com.pinting.business.accounting.finance.service.impl;

import com.pinting.business.accounting.finance.service.UserBalanceBuyService;
import com.pinting.business.accounting.finance.service.UserBonusGrantService;
import com.pinting.business.accounting.finance.service.impl.process.UserBonusGrant4BuyProcess;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.service.LoanAccountService;
import com.pinting.business.accounting.service.AccountHandleService;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_BalanceBuy;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_BalanceBuy;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsProductVO;
import com.pinting.business.service.site.Ecup2016ActivityService;
import com.pinting.business.service.site.RedPacketService;
import com.pinting.business.service.site.SendWechatService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.business.util.IdcardUtils;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.in.util.MethodRole;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by babyshark on 2016/9/10.
 */
@Service
public class UserBalanceBuyServiceImpl implements UserBalanceBuyService {
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    private final Logger logger = LoggerFactory.getLogger(UserBalanceBuyServiceImpl.class);
    @Autowired
    private AccountHandleService accountHandleService;
    @Autowired
    private UserBonusGrantService userBonusGrantService;
    @Autowired
    private BsPayOrdersMapper payOrderMapper;
    @Autowired
    private BsSysConfigMapper sysMapper;
    @Autowired
    private BsProductMapper proMapper;
    @Autowired
    private BsSubAccountMapper subAccountMapper;
    @Autowired
    private BsPayOrdersJnlMapper payOrderJnlMapper;
    @Autowired
    private BsAccountMapper accountMapper;
    @Autowired
    private BsUserTransDetailMapper userTransMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private BsUserMapper userMapper;
    @Autowired
    private BsAccountJnlMapper accountJnlMapperl;
    @Autowired
    private BsSysSubAccountMapper sysAccountMapper;
    @Autowired
    private BsSysAccountJnlMapper sysAccountJnlMapper;
    @Autowired
    private SMSServiceClient smsServiceClient;
    @Autowired
    private SendWechatService sendWechatService;
    @Autowired
    private BsRedPacketInfoMapper redPacketInfoMapper;
    @Autowired
    private BsSysSubAccountService subAccountService;
    @Autowired
    private BsRedPacketCheckMapper redPacketCheckMapper;
    @Autowired
    private RedPacketService redPacketService;
    @Autowired
    private Ecup2016ActivityService ecup2016ActivityService;
    @Autowired
    private BsPropertyInfoMapper bsPropertyInfoMapper;
    @Autowired
    private LoanAccountService loanAccountService;
    @Autowired
    private BsSysSubAccountService bsSysSubAccountService;

    /**
     * 余额购买
     *
     * @param req
     * @param res
     */
    @Override
    @MethodRole("APP")
    public void buy(final ReqMsg_RegularBuy_BalanceBuy req, final ResMsg_RegularBuy_BalanceBuy res) {
        try {
            logger.info("港湾计划余额购买开始:{}", JSONObject.fromObject(req));

            jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_BUYPRODUCT.getKey());
            Map<String, Object> returnMap =  buyPreCheck(req);
            final BsUser bsUser = (BsUser) returnMap.get("bsUser");
            final BsProduct product = (BsProduct) returnMap.get("product");
            final BsRedPacketInfo redPacket = (BsRedPacketInfo) returnMap.get("redPacket");
            final Date now = new Date();
            //==========================================验证结束============================================

            Integer subAccountId = null;
            BsPropertyInfo bsPropertyInfo = bsPropertyInfoMapper.selectByPrimaryKey(product.getPropertyId());
            subAccountId = transactionTemplate.execute(new TransactionCallback<Integer>(){
                @Override
                public Integer doInTransaction(TransactionStatus status) {
                    //新增用户子账户信息表
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(now);
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    BsAccountExample accountExample = new BsAccountExample();
                    accountExample.createCriteria().andUserIdEqualTo(req.getUserId());
                    BsAccount account = accountMapper.selectByExample(accountExample).get(0);
                    BsSubAccount sub = new BsSubAccount();
                    sub.setAccountId(account.getId());
                    sub.setCode(accountHandleService.generateProductAccount(req.getProductId(), req.getUserId()));
                    sub.setProductId(req.getProductId());
                    sub.setProductType(Constants.PRODUCT_TYPE_REG);
                    sub.setProductCode(product.getCode());
                    sub.setProductName(product.getName());
                    sub.setProductRate(product.getBaseRate());
                    sub.setExtraRate(0.0);
                    sub.setOpenBalance(req.getAmount());
                    sub.setBalance(req.getAmount());
                    sub.setAvailableBalance(0.0);
                    sub.setCanWithdraw(0.0);
                    sub.setFreezeBalance(req.getAmount());
                    sub.setTransStatus(Constants.USER_SUB_TRANS_STATUS_0);
                    sub.setStatus(Constants.SUBACCOUNT_STATUS_FINANCING);
                    //对账状态此处不再设置，迁移到对账时统一设置
                    //sub.setCheckStatus(Constants.CHECK_ACCOUNT_STATUS_SUCCESS);
                    sub.setInterestBeginDate(calendar.getTime()); //起息日期
                    int term4Day = product.getTerm4Day();
                    calendar.add(Calendar.DAY_OF_MONTH, term4Day);
                    sub.setLastFinishInterestDate(calendar.getTime()); //结束日期
                    sub.setAccumulationInerest(0.0);
                    sub.setOpenTime(now);
                    subAccountMapper.insertSelective(sub);

                    //新增订单表
                    BsPayOrders order = new BsPayOrders();
                    order.setOrderNo(Util.generateOrderNo(req.getUserId()));
                    order.setAmount(redPacket==null?req.getAmount():MoneyUtil.subtract(req.getAmount(), redPacket.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    order.setUserId(req.getUserId());
                    order.setSubAccountId(sub.getId());
                    order.setStatus(Constants.ORDER_STATUS_SUCCESS);
                    order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
                    order.setTerminalType(req.getTerminalType());
                    order.setCreateTime(now);
                    order.setUpdateTime(now);
                    order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
                    order.setTransType(Constants.TRANS_BALANCE_BUY_PRODUCT);
                    order.setMobile(bsUser.getMobile());
                    order.setIdCard(bsUser.getIdCard());
                    order.setUserName(bsUser.getUserName());
                    payOrderMapper.insertSelective(order);

                    //新增订单流水表
                    BsPayOrdersJnl jnl = new BsPayOrdersJnl();
                    jnl.setOrderId(order.getId());
                    jnl.setOrderNo(order.getOrderNo());
                    jnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
                    jnl.setTransAmount(order.getAmount());
                    jnl.setSysTime(now);
                    jnl.setUserId(req.getUserId());
                    jnl.setSubAccountId(sub.getId());
                    jnl.setSubAccountCode(sub.getCode());
                    jnl.setCreateTime(now);
                    payOrderJnlMapper.insertSelective(jnl);

                    //新增用户交易明细表
                    BsUserTransDetail detail = new BsUserTransDetail();
                    detail.setUserId(req.getUserId());
                    detail.setTransType(Constants.Trans_TYPE_BUY);
                    detail.setTransStatus(Constants.Trans_STATUS_SUCCESS);
                    detail.setOrderNo(order.getOrderNo());
                    detail.setCreateTime(now);
                    detail.setAmount(-req.getAmount());
                    detail.setUpdateTime(now);
                    userTransMapper.insertSelective(detail);

                    //减少用户的JSH(结算户)余额
                    BsSubAccount tempJsh = subAccountMapper.selectSingleSubActByUserAndType(req.getUserId(),Constants.PRODUCT_TYPE_JSH);
                    BsSubAccount subAccountLock = subAccountMapper.selectSubAccountByIdForLock(tempJsh.getId());
                    if(order.getAmount() > subAccountLock.getAvailableBalance()) {
                        throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
                    }
                    BsSubAccount readySubAccount = new BsSubAccount();
                    readySubAccount.setId(subAccountLock.getId());
                    readySubAccount.setBalance(MoneyUtil.subtract(subAccountLock.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readySubAccount.setAvailableBalance(MoneyUtil.subtract(subAccountLock.getAvailableBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readySubAccount.setCanWithdraw(MoneyUtil.subtract(subAccountLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    subAccountMapper.updateByPrimaryKeySelective(readySubAccount);

                    //减少系统的USER(用户余额户)余额
                    BsSysSubAccount sysUserLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_USER);
                    if(req.getAmount() > sysUserLock.getAvailableBalance()) {
                        throw new PTMessageException(PTMessageEnum.SYS_BALANCE_NOT_ENOUGH);
                    }
                    BsSysSubAccount readyUserUpdate = new BsSysSubAccount();
                    readyUserUpdate.setId(sysUserLock.getId());
                    readyUserUpdate.setBalance(MoneyUtil.subtract(sysUserLock.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readyUserUpdate.setAvailableBalance(MoneyUtil.subtract(sysUserLock.getAvailableBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readyUserUpdate.setCanWithdraw(MoneyUtil.subtract(sysUserLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sysAccountMapper.updateByPrimaryKeySelective(readyUserUpdate);

                    /**
                     * @author 浣熊 2016-7-13
                     */
                    //判断资产合作接收方是云贷还是7贷
                    BsSysSubAccount sysRegLock = null;
                    BsPropertyInfo bsPropertyInfo = bsPropertyInfoMapper.selectByPrimaryKey(product.getPropertyId());
                    if(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI.equals(bsPropertyInfo.getPropertySymbol())) {
                        sysRegLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_REG_7);
                    } else if(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI.equals(bsPropertyInfo.getPropertySymbol())) {
                        sysRegLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_REG_YUN);
                    }
                    //增加系统的REG((产品户)余额
                    BsSysSubAccount readyRegUpdate = new BsSysSubAccount();
                    readyRegUpdate.setId(sysRegLock.getId());
                    readyRegUpdate.setBalance(MoneyUtil.add(sysRegLock.getBalance(), req.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readyRegUpdate.setAvailableBalance(MoneyUtil.add(sysRegLock.getAvailableBalance(), req.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readyRegUpdate.setCanWithdraw(MoneyUtil.add(sysRegLock.getCanWithdraw(), req.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sysAccountMapper.updateByPrimaryKeySelective(readyRegUpdate);

                    //修改用户信息表
                    BsUser userLock = userMapper.selectByPKForLock(bsUser.getId());
                    BsUser updateUser = new BsUser();
                    updateUser.setId(userLock.getId());
                    updateUser.setCanWithdraw(MoneyUtil.subtract(userLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    userMapper.updateByPrimaryKeySelective(updateUser);

                    //修改产品表中的累计人次和累计金额
                    BsProduct pro = proMapper.selectByPrimaryKey(req.getProductId());
                    if(pro != null) {
                        pro.setInvestNum(pro.getInvestNum()+1);
                        pro.setCurrTotalAmount(MoneyUtil.add(pro.getCurrTotalAmount(), req.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        proMapper.updateByPrimaryKeySelective(pro);

                        BsProduct extendPro = new BsProduct();
                        extendPro.setId(pro.getId());
                        extendPro.setCurrTotalAmount(pro.getCurrTotalAmount());
                        extendPro.setMaxTotalAmount(pro.getMaxTotalAmount());
                        extendPro.setMinInvestAmount(pro.getMinInvestAmount());
                        extendPro.setName(pro.getName());
                        extendPro.setShowTerminal(pro.getShowTerminal());
                        HashMap<String, Object> extendMap = res.getExtendMap();
                        if(extendMap == null){
                            extendMap = new HashMap<String, Object>();
                        }
                        extendMap.put("product", extendPro);
                        extendMap.put("userId", bsUser.getId());
                        extendMap.put("investAmount", sub.getBalance());
                        extendMap.put("subAccountId", sub.getId());
                        res.setExtendMap(extendMap);
                    }

                    //判读用户是否为首次购买
                    List<Integer> statusList = new ArrayList<Integer>();
                    statusList.add(1);
                    statusList.add(6);
                    BsSubAccountExample subAccExample = new BsSubAccountExample();
                    subAccExample.createCriteria().andAccountIdEqualTo(account.getId()).andStatusNotIn(statusList).andProductTypeEqualTo(Constants.PRODUCT_TYPE_REG);
                    List<BsSubAccount> temp = subAccountMapper.selectByExample(subAccExample);
                    if(temp.size() == 1) {
                        BsUser user = new BsUser();
                        user.setId(order.getUserId());
                        user.setFirstBuyTime(new Date());
                        userMapper.updateByPrimaryKeySelective(user);
                    }

                    //新增用户记账流水表
                    BsAccountJnl accountJnl = new BsAccountJnl();
                    accountJnl.setTransTime(new Date());
                    accountJnl.setTransCode(Constants.USER_BALANCE_BUY_PRODUCT);
                    accountJnl.setTransName("余额购买");
                    accountJnl.setTransAmount(req.getAmount());
                    accountJnl.setSysTime(new Date());
                    accountJnl.setCdFlag1(Constants.CD_FLAG_C);
                    accountJnl.setUserId1(order.getUserId());
                    accountJnl.setAccountId1(account.getId());
                    accountJnl.setSubAccountId1(subAccountLock.getId());
                    accountJnl.setSubAccountCode1(subAccountLock.getCode());
                    accountJnl.setBeforeBalance1(subAccountLock.getBalance());
                    accountJnl.setAfterBalance1(readySubAccount.getBalance());
                    accountJnl.setBeforeAvialableBalance1(subAccountLock.getAvailableBalance());
                    accountJnl.setAfterAvialableBalance1(readySubAccount.getAvailableBalance());
                    accountJnl.setBeforeFreezeBalance1(subAccountLock.getFreezeBalance());
                    accountJnl.setAfterFreezeBalance1(subAccountLock.getFreezeBalance());
                    accountJnl.setCdFlag2(Constants.CD_FLAG_D);
                    accountJnl.setUserId2(order.getUserId());
                    accountJnl.setAccountId2(account.getId());
                    accountJnl.setSubAccountId2(sub.getId());
                    accountJnl.setSubAccountCode2(sub.getCode());
                    accountJnl.setBeforeBalance2(0.0);
                    accountJnl.setAfterBalance2(sub.getBalance());
                    accountJnl.setBeforeAvialableBalance2(0.0);
                    accountJnl.setAfterAvialableBalance2(0.0);
                    accountJnl.setBeforeFreezeBalance2(0.0);
                    accountJnl.setAfterFreezeBalance2(sub.getFreezeBalance());
                    accountJnl.setFee(0.0);
                    accountJnlMapperl.insertSelective(accountJnl);

                    //新增系统记账流水表
                    BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                    sysAccountJnl.setTransTime(new Date());
                    sysAccountJnl.setTransCode(Constants.SYS_USER_BALANCE_BUY);
                    sysAccountJnl.setTransName("用户购买");
                    sysAccountJnl.setTransAmount(req.getAmount());
                    sysAccountJnl.setSysTime(new Date());
                    sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                    sysAccountJnl.setSubAccountCode1(sysUserLock.getCode());
                    sysAccountJnl.setBeforeBalance1(sysUserLock.getBalance());
                    sysAccountJnl.setAfterBalance1(readyUserUpdate.getBalance());
                    sysAccountJnl.setBeforeAvialableBalance1(sysUserLock.getAvailableBalance());
                    sysAccountJnl.setAfterAvialableBalance1(readyUserUpdate.getAvailableBalance());
                    sysAccountJnl.setBeforeFreezeBalance1(sysUserLock.getFreezeBalance());
                    sysAccountJnl.setAfterFreezeBalance1(sysUserLock.getFreezeBalance());
                    sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
                    sysAccountJnl.setSubAccountCode2(sysRegLock.getCode());
                    sysAccountJnl.setBeforeBalance2(sysRegLock.getBalance());
                    sysAccountJnl.setAfterBalance2(readyRegUpdate.getBalance());
                    sysAccountJnl.setBeforeAvialableBalance2(sysRegLock.getAvailableBalance());
                    sysAccountJnl.setAfterAvialableBalance2(readyRegUpdate.getAvailableBalance());
                    sysAccountJnl.setBeforeFreezeBalance2(sysRegLock.getFreezeBalance());
                    sysAccountJnl.setAfterFreezeBalance2(sysRegLock.getFreezeBalance());
                    sysAccountJnl.setFee(0.0);
                    sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                    sysAccountJnlMapper.insertSelective(sysAccountJnl);

                    // 判断是否支持红包
                    if(!Constants.IS_SUPPORT_RED_PACKET_FALSE.equals(product.getIsSupportRedPacket())) {
                        // 支持红包
                        //修改红包状态
                        if(redPacket != null) {
                            //修改红包状态
                            redPacket.setOrderNo(order.getOrderNo());
                            redPacket.setStatus(Constants.RED_PACKET_STATUS_USED);
                            redPacket.setUpdateTime(new Date());
                            redPacketInfoMapper.updateByPrimaryKeySelective(redPacket);
                            //减少系统红包子账户金额
                            subAccountService.redPacketUsed(redPacket.getAmount());
                        }
                    }
                    logger.info("=========【使用账户余额购买产品成功】币港湾订单号："+order.getOrderNo()+"=========");

                    return sub.getId();
                }

            });

            //奖励金处理
            if(subAccountId != null){
                UserBonusGrant4BuyProcess process = new UserBonusGrant4BuyProcess();
                process.setUserBonusGrantService(userBonusGrantService);
                process.setAmount(req.getAmount());
                process.setBonusGrantType(userBonusGrantService.getBonusGrantTypeByUserId(req.getUserId()));
                process.setReferrerUserId(bsUser.getRecommendId());
                process.setSelfUserId(req.getUserId());
                process.setSubAccountId(subAccountId);
                process.setPropertySymbol(bsPropertyInfo.getPropertySymbol());
                new Thread(process).start();
            }

            //满额发自动红包
            //redPacketService.awardBonus(req.getAmount(), req.getUserId());
            AutoRedPacketParams params = new AutoRedPacketParams();
            params.setAmount(req.getAmount());
            params.setUserId(req.getUserId());
            params.setTriggerType(Constants.AUTO_RED_PACKET_TIGGER_TYPE_BUY_FULL);
            List<Integer> redPacketIds = redPacketService.autoRedPacketSend(params);
            if (redPacketIds != null && redPacketIds.size() >0) {
            	res.setIsAutoRedPocket("yes");
			}    
            //发送短信
			/*String message = "您有一笔投资已成功，本金："+order.getAmount()+"元, 投资周期："+product.getTerm()+"个月, 投资利率："+product.getBaseRate()+"%。如有疑问请拨打400-806-1230。";
			smsService.sendMessage(order.getMobile(), message);*/
            smsServiceClient.sendTemplateMessage(bsUser.getMobile(), TemplateKey.BUY_PRODUCT_SUC,  req.getAmount().toString(), product.getTerm4Day().toString(), product.getBaseRate().toString()+"%");
            //发送微信模板消息
            sendWechatService.buyProductMgs(req.getUserId(), "", product.getName(),  product.getBaseRate().toString()+"%", req.getAmount().toString(), product.getTerm4Day().toString(), "suc",null);


            HashMap<String, Object> extendMap = res.getExtendMap();
            if(extendMap == null){
                extendMap = new HashMap<String, Object>();
            }
            extendMap.put("subAccountId", subAccountId);
            res.setExtendMap(extendMap);

            logger.info("港湾计划余额购买结束:{}", JSONObject.fromObject(res));
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_BUYPRODUCT.getKey());
        }
    }

    /**
     * 余额授权
     *
     * @param req
     * @param res
     */
    @Override
    @MethodRole("APP")
    public void auth(ReqMsg_RegularBuy_BalanceBuy req, ResMsg_RegularBuy_BalanceBuy res) {
        try {
            logger.info("委托计划余额购买开始:{}", JSONObject.fromObject(req));
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_BUYPRODUCT.getKey());
            Map<String, Object> returnMap =  buyPreCheck(req);
            BsUser bsUser = (BsUser) returnMap.get("bsUser");
            BsProduct product = (BsProduct) returnMap.get("product");
            BsRedPacketInfo redPacket = (BsRedPacketInfo) returnMap.get("redPacket");
            Date now = new Date();
            //==========================================验证结束============================================

            Integer subAccountId = null;
            // 判断是否赞分期
            BsPropertyInfo bsPropertyInfo = bsPropertyInfoMapper.selectByPrimaryKey(product.getPropertyId());
            // 一、赞分期主要操作
            // 1、新增订单表
            // 2、新增订单流水表
            // 3、新增用户交易流水表
            // 4、修改用户子账户表JSH 	结算户 减少余额
            // 5、新增用户子账户表AUTH	站岗户 投资中状态、增加金额
            // 6、初始化用户子账户表REG_D	投资中状态、金额为0
            // 7、AUTH、REG_D关联表生成	BsSubAccountPair
            // 8、新增用户记账流水表	transcode AUTH_ADD
            // 9、修改系统子账户信息表（用户余额户）	USER	减少USER的余额
            // 10、修改系统子账户信息表（赞分期站岗户）AUTH_ZAN	增加金额
            // 11、新增系统记账流水表	BsSysAccountJnl
            // 12、修改用户信息表	扣除可用余额
            // 13、修改产品表中的累计人次和累计金额
            // 14、判断用户是否首次购买，修改首次购买时间
            // 15、判断红包是否可用
            // 16、奖励金处理
            BaseAccount baseAccount = new BaseAccount();
            baseAccount.setAmount(req.getAmount());
            baseAccount.setPartner(PartnerEnum.ZAN);
            baseAccount.setInvestorUserId(req.getUserId());
            int subId = loanAccountService.chargeAuthActAdd(baseAccount, req.getProductId());
            BsSubAccount sub = subAccountMapper.selectByPrimaryKey(subId);
            //新增订单表
            BsPayOrders order = new BsPayOrders();
            order.setOrderNo(Util.generateOrderNo(req.getUserId()));
            order.setAmount(redPacket==null?req.getAmount():MoneyUtil.subtract(req.getAmount(), redPacket.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            order.setUserId(req.getUserId());
            order.setSubAccountId(sub.getId());
            order.setStatus(Constants.ORDER_STATUS_SUCCESS);
            order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
            order.setTerminalType(req.getTerminalType());
            order.setCreateTime(now);
            order.setUpdateTime(now);
            order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
            order.setTransType(Constants.TRANS_BALANCE_BUY_PRODUCT);
            order.setMobile(bsUser.getMobile());
            order.setIdCard(bsUser.getIdCard());
            order.setUserName(bsUser.getUserName());
            payOrderMapper.insertSelective(order);

            //新增订单流水表
            BsPayOrdersJnl jnl = new BsPayOrdersJnl();
            jnl.setOrderId(order.getId());
            jnl.setOrderNo(order.getOrderNo());
            jnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
            jnl.setTransAmount(order.getAmount());
            jnl.setSysTime(now);
            jnl.setUserId(req.getUserId());
            jnl.setSubAccountId(sub.getId());
            jnl.setSubAccountCode(sub.getCode());
            jnl.setCreateTime(now);
            payOrderJnlMapper.insertSelective(jnl);

            //新增用户交易明细表
            BsUserTransDetail detail = new BsUserTransDetail();
            detail.setUserId(req.getUserId());
            detail.setTransType(Constants.Trans_TYPE_BUY);
            detail.setTransStatus(Constants.Trans_STATUS_SUCCESS);
            detail.setOrderNo(order.getOrderNo());
            detail.setCreateTime(now);
            detail.setAmount(-req.getAmount());
            detail.setUpdateTime(now);
            userTransMapper.insertSelective(detail);

            //修改用户信息表
            BsUser userLock = userMapper.selectByPKForLock(bsUser.getId());
            BsUser updateUser = new BsUser();
            updateUser.setId(userLock.getId());
            updateUser.setCanWithdraw(MoneyUtil.subtract(userLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            userMapper.updateByPrimaryKeySelective(updateUser);

            //修改产品表中的累计人次和累计金额
            BsProduct pro = proMapper.selectByPrimaryKey(req.getProductId());
            if(pro != null) {
                pro.setInvestNum(pro.getInvestNum()+1);
                pro.setCurrTotalAmount(MoneyUtil.add(pro.getCurrTotalAmount(), req.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                proMapper.updateByPrimaryKeySelective(pro);

                BsProduct extendPro = new BsProduct();
                extendPro.setId(pro.getId());
                extendPro.setCurrTotalAmount(pro.getCurrTotalAmount());
                extendPro.setMaxTotalAmount(pro.getMaxTotalAmount());
                extendPro.setMinInvestAmount(pro.getMinInvestAmount());
                extendPro.setShowTerminal(pro.getShowTerminal());
                extendPro.setName(pro.getName());
                HashMap<String, Object> extendMap = res.getExtendMap();
                if(extendMap == null){
                    extendMap = new HashMap<String, Object>();
                }
                extendMap.put("product", extendPro);
                extendMap.put("userId", bsUser.getId());
                extendMap.put("investAmount", sub.getBalance());
                extendMap.put("subAccountId", sub.getId());
                res.setExtendMap(extendMap);
            }

            //判断用户是否为首次购买
            List<Integer> statusList = new ArrayList<Integer>();
            statusList.add(1);
            statusList.add(6);
            BsSubAccountExample subAccExample = new BsSubAccountExample();
            subAccExample.createCriteria().andAccountIdEqualTo(sub.getAccountId()).andStatusNotIn(statusList).andProductTypeEqualTo(Constants.PRODUCT_TYPE_REG);
            List<BsSubAccount> bsSubList = subAccountMapper.selectByExample(subAccExample);
            if(bsSubList.size() == 1) {
                BsUser user = new BsUser();
                user.setId(order.getUserId());
                user.setFirstBuyTime(new Date());
                userMapper.updateByPrimaryKeySelective(user);
            }

            // 判断是否支持红包
            if(!Constants.IS_SUPPORT_RED_PACKET_FALSE.equals(product.getIsSupportRedPacket())) {
                // 支持红包
                if(redPacket != null) {
                    //修改红包状态
                    redPacket.setOrderNo(order.getOrderNo());
                    redPacket.setStatus(Constants.RED_PACKET_STATUS_BUYING);
                    redPacket.setUpdateTime(new Date());
                    redPacketInfoMapper.updateByPrimaryKeySelective(redPacket);
                    //减少系统红包子账户金额
                    subAccountService.redPacketUsed(redPacket.getAmount());
                }
            }
            //发送短信
			/*String message = "您有一笔投资已成功，本金："+order.getAmount()+"元, 投资周期："+product.getTerm()+"个月, 投资利率："+product.getBaseRate()+"%。如有疑问请拨打400-806-1230。";
			smsService.sendMessage(order.getMobile(), message);*/
            smsServiceClient.sendTemplateMessage(bsUser.getMobile(), TemplateKey.BUY_PRODUCT_SUC4ZAN,  req.getAmount().toString(), product.getTerm().toString(), product.getBaseRate().toString()+"%");
            //发送微信模板消息
            sendWechatService.buyProductMgs4Zan(req.getUserId(), "", product.getName(),  product.getBaseRate().toString()+"%", req.getAmount().toString(), pro.getTerm().toString(), "suc",null);

            HashMap<String, Object> extendMap = res.getExtendMap();
            if(extendMap == null){
                extendMap = new HashMap<String, Object>();
            }
//            extendMap.put("subAccountId", subId);
            res.setExtendMap(extendMap);
            logger.info("委托计划余额购买结束:{}", JSONObject.fromObject(res));
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_BUYPRODUCT.getKey());
        }
    }

    /**
     * 超级理财人余额授权
     *
     * @param req
     */
    @Override
    public void superAuth(ReqMsg_RegularBuy_BalanceBuy req) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_BUYPRODUCT.getKey());
            //获得合作方
            Integer productId = req.getProductId();
            BsProduct product = proMapper.selectByPrimaryKey(productId);
            BsPropertyInfo bsPropertyInfo = bsPropertyInfoMapper.selectByPrimaryKey(product.getPropertyId());
            PartnerEnum partner = PartnerEnum.getEnumByCode(bsPropertyInfo.getPropertySymbol());
            //组织记账数据
            BaseAccount account = new BaseAccount();
            account.setPartner(partner);
            account.setInvestorUserId(req.getUserId());
            account.setAmount(req.getAmount());
            int subId = loanAccountService.chargeAuthActAdd(account, productId);
            BsSubAccount sub = subAccountMapper.selectByPrimaryKey(subId);
            //新增订单表
            Date now = new Date();
            BsUser bsUser = userMapper.selectByPrimaryKey(req.getUserId());
            BsPayOrders order = new BsPayOrders();
            order.setOrderNo(Util.generateOrderNo(req.getUserId()));
            order.setAmount(req.getAmount());
            order.setUserId(req.getUserId());
            order.setSubAccountId(sub.getId());
            order.setStatus(Constants.ORDER_STATUS_SUCCESS);
            order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
            order.setTerminalType(null);
            order.setCreateTime(now);
            order.setUpdateTime(now);
            order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
            order.setTransType(Constants.TRANS_BALANCE_BUY_PRODUCT);
            order.setMobile(bsUser.getMobile());
            order.setIdCard(bsUser.getIdCard());
            order.setUserName(bsUser.getUserName());
            payOrderMapper.insertSelective(order);

            //新增订单流水表
            BsPayOrdersJnl jnl = new BsPayOrdersJnl();
            jnl.setOrderId(order.getId());
            jnl.setOrderNo(order.getOrderNo());
            jnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
            jnl.setTransAmount(order.getAmount());
            jnl.setSysTime(now);
            jnl.setUserId(req.getUserId());
            jnl.setSubAccountId(sub.getId());
            jnl.setSubAccountCode(sub.getCode());
            jnl.setCreateTime(now);
            payOrderJnlMapper.insertSelective(jnl);

            //新增用户交易明细表
            BsUserTransDetail detail = new BsUserTransDetail();
            detail.setUserId(req.getUserId());
            detail.setTransType(Constants.Trans_TYPE_BUY);
            detail.setTransStatus(Constants.Trans_STATUS_SUCCESS);
            detail.setOrderNo(order.getOrderNo());
            detail.setCreateTime(now);
            detail.setAmount(-req.getAmount());
            detail.setUpdateTime(now);
            userTransMapper.insertSelective(detail);

            //修改用户信息表
            BsUser userLock = userMapper.selectByPKForLock(bsUser.getId());
            BsUser updateUser = new BsUser();
            updateUser.setId(userLock.getId());
            updateUser.setCanWithdraw(MoneyUtil.subtract(userLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            userMapper.updateByPrimaryKeySelective(updateUser);

            //修改产品表中的累计人次和累计金额
            BsProduct pro = proMapper.selectByPrimaryKey(req.getProductId());
            if(pro != null) {
                pro.setInvestNum(pro.getInvestNum()+1);
                pro.setCurrTotalAmount(MoneyUtil.add(pro.getCurrTotalAmount(), req.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                proMapper.updateByPrimaryKeySelective(pro);
            }

        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_BUYPRODUCT.getKey());
        }
    }

    /**
     * 余额购买前置校验
     * @param req
     * @return 返回后续余额购买需要的数据对象，如果校验异常则throws异常
     */
    private Map<String, Object> buyPreCheck(ReqMsg_RegularBuy_BalanceBuy req){
        logger.info("余额购买前置校验开始:{}", JSONObject.fromObject(req));
        Map<String, Object> returnMap = new HashMap<>();
        final BsUser bsUser = userMapper.selectByPrimaryKey(req.getUserId());

        //0、判断是否已经绑卡
        if(null != bsUser.getIsBindBank() && bsUser.getIsBindBank().equals(Constants.IS_BIND_BANK_NO)) {
            throw new PTMessageException(PTMessageEnum.USER_NOT_BIND_CARD);
        }

        //1、判断用户是否存在，判断用户是否成年
        if(!(bsUser != null && bsUser.getStatus() == Constants.USER_STATUS_1)) {
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }
        if(!IdcardUtils.isAdultAge(bsUser.getIdCard())){
            throw new PTMessageException(PTMessageEnum.USER_NOT_ADULT);
        }

        // 1.1、不存在投资中的产品，则不再允许充值和购买
        if(IdcardUtils.isOldAge(bsUser.getIdCard(), Constants.OLD_MAN_AGE_LIMIT)) {
            BsAccountExample accountExample = new BsAccountExample();
            accountExample.createCriteria().andUserIdEqualTo(bsUser.getId());
            List<BsAccount> bsAccounts = accountMapper.selectByExample(accountExample);
            BsSubAccountExample regExample = new BsSubAccountExample();
            List<Integer> statusList = new ArrayList<>();
            statusList.add(Constants.SUBACCOUNT_STATUS_OPEN);
            statusList.add(Constants.SUBACCOUNT_STATUS_SETTLE);
            statusList.add(Constants.SUBACCOUNT_STATUS_CLOSE);
            regExample.createCriteria().andProductTypeEqualTo(Constants.PRODUCT_TYPE_REG).andStatusNotIn(statusList).andAccountIdEqualTo(bsAccounts.get(0).getId());
            int regCount = subAccountMapper.countByExample(regExample);
            BsSubAccountExample regdExample = new BsSubAccountExample();
            regdExample.createCriteria().andProductTypeEqualTo(Constants.PRODUCT_TYPE_AUTH).andStatusNotIn(statusList).andAccountIdEqualTo(bsAccounts.get(0).getId());
            int authCount = subAccountMapper.countByExample(regdExample);
            if(regCount + authCount <= 0) {
                throw new PTMessageException(PTMessageEnum.USER_IS_OLD_MAN);
            }
        }

        //2、判断系统当日配置限额
        final Date now = new Date();
        String startDay = DateUtil.format(now, "yyyy-MM-dd")+" 00:00:00";
        String endDay = DateUtil.format(now, "yyyy-MM-dd")+" 23:59:59";
        Double sysDay = payOrderMapper.calculateBuyProductTotal(startDay, endDay);
        if(sysDay == null) {
            sysDay = 0.0;
        }
        BsSysConfig sys = sysMapper.selectByPrimaryKey(Constants.PRICE_CEILING);
        if(MoneyUtil.add(req.getAmount(), sysDay).compareTo(new BigDecimal(sys.getConfValue())) == 1) {
            throw new PTMessageException(PTMessageEnum.PASS_SYSTEM_LIMIT);
        }
        //3、判断用户购买权限
        final BsProduct product = proMapper.selectProByUser(req.getProductId(), req.getUserId());
        if(product == null) {
            throw new PTMessageException(PTMessageEnum.USER_NO_AUTH_FOR_PRODUCT);
        }
        //4、判断是否达到最低购买金额和是否超过产品限额
        if(product.getMinInvestAmount() > req.getAmount()) {
            throw new PTMessageException(PTMessageEnum.BUY_AMOUNT_IS_NOT_PASS);
        }
        if(product.getMaxTotalAmount() < MoneyUtil.add(req.getAmount(), product.getCurrTotalAmount()).doubleValue()) {
            throw new PTMessageException(PTMessageEnum.PRODUCT_MAX_AMOUNT_OVER);
        }
        //5、判断产品是否已经过期
        if(product.getFinishTime() != null) {
            throw new PTMessageException(PTMessageEnum.PRODUCT_IS_FINISHED);
        }
        else {
            //当前时间早于产品开始时间
            if(now.compareTo(product.getStartTime()) < 0) {
                throw new PTMessageException(PTMessageEnum.PRODUCT_IS_NOT_OPENED);
            }
            //当前时间晚于产品结束时间
            if(product.getEndTime() != null && now.compareTo(product.getEndTime()) > 0) {
                throw new PTMessageException(PTMessageEnum.PRODUCT_IS_FINISHED);
            }
        }
        //6、判断产品状态
        if(!Constants.PRODUCT_STATUS_OPENING.equals(product.getStatus())) {
            throw new PTMessageException(PTMessageEnum.PRODUCT_STATUS_OPENING_ERROR);
        }
        //7、判断单次最高投资金额
        if(product.getMaxSingleInvestAmount() != null) {
            if(req.getAmount() > product.getMaxSingleInvestAmount()) {
                throw new PTMessageException(PTMessageEnum.SINGLE_PRODUCT_MAX_AMOUNT_OVER);
            }
        }
        //8、判断当前用户是否有未完成的订单
        BsPayOrdersExample ordExample = new BsPayOrdersExample();
        List<String> transTypeList = new ArrayList<String>();
        transTypeList.add(Constants.TRANS_CARD_BUY_PRODUCT);
        transTypeList.add(Constants.TRANS_BALANCE_BUY_PRODUCT);
        ordExample.createCriteria().andStatusEqualTo(Constants.ORDER_STATUS_PAYING).andTransTypeIn(transTypeList).andUserIdEqualTo(req.getUserId());
        List<BsPayOrders> ordList = payOrderMapper.selectByExample(ordExample);
        if(!CollectionUtils.isEmpty(ordList) && ordList.size() > 0) {
            throw new PTMessageException(PTMessageEnum.USER_ORDER_STATUS_PAYING);
        }
        //9、判断新手标
        if(Constants.PRODUCT_ACTIVITY_TYPE_NEW_BUYER.equals(product.getActivityType())) {
            //必须之前没有买过产品
            BsPayOrdersExample payOrderExample = new BsPayOrdersExample();
            payOrderExample.createCriteria().andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS).andTransTypeIn(transTypeList).andUserIdEqualTo(req.getUserId());
            Integer investCount = payOrderMapper.countByExample(payOrderExample);
            if(investCount > 0) {
                throw new PTMessageException(PTMessageEnum.USER_ALREADY_BUY_PRODUCT);
            }
        }
        //9.1、判断是新手用户且是欧洲杯新手用户标
        List<BsProductVO> newUserPros = ecup2016ActivityService.queryEcupProductGrid(null, "NEW_USER");
        logger.info("============================ NEW_USER 欧洲杯新手用户标 - START req.getProductId() " + req.getProductId() + " ================================");
        // 判断是否欧洲杯新手用户标
        if(!CollectionUtils.isEmpty(newUserPros)) {
            logger.info("============================ NEW_USER 欧洲杯新手用户标 newUserPros not null ================================");
            for (BsProductVO pro : newUserPros) {
                Integer proId = pro.getId();
                if(proId.equals(req.getProductId())) {
                    logger.info("============================ NEW_USER 欧洲杯新手用户标 proId " + proId + " ================================");
                    //必须之前没有买过产品
                    BsPayOrdersExample payOrderExample = new BsPayOrdersExample();
                    payOrderExample.createCriteria().andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS).andTransTypeIn(transTypeList).andUserIdEqualTo(req.getUserId());
                    Integer investCount = payOrderMapper.countByExample(payOrderExample);
                    if(investCount > 0) {
                        throw new PTMessageException(PTMessageEnum.USER_ALREADY_BUY_PRODUCT);
                    }
                    break;
                }
            }
        }
        logger.info("============================ NEW_USER 欧洲杯新手用户标 - END ================================");

        // 判断是否支持红包
        BsRedPacketInfo temp = null;
        if(!Constants.IS_SUPPORT_RED_PACKET_FALSE.equals(product.getIsSupportRedPacket())) {
            // 支持红包
            //10、判断红包是否可用
            if(req.getTicketId() != null) {
                BsRedPacketInfoExample redPacketExample = new BsRedPacketInfoExample();
                redPacketExample.createCriteria().andIdEqualTo(req.getTicketId()).andStatusEqualTo(Constants.RED_PACKET_STATUS_INIT).andUseTimeStartLessThanOrEqualTo(now).andUseTimeEndGreaterThanOrEqualTo(now);
                List<BsRedPacketInfo> list = redPacketInfoMapper.selectByExample(redPacketExample);
                if(CollectionUtils.isEmpty(list)) {
                    throw new PTMessageException(PTMessageEnum.RED_PACKET_USERD);
                }
                else {
                    BsRedPacketInfo info = list.get(0);
                    BsRedPacketCheckExample checkExample = new BsRedPacketCheckExample();
                    checkExample.createCriteria().andApplyNoEqualTo(info.getApplyNo()).andSerialNoEqualTo(info.getSerialNo()).andCheckStatusEqualTo(Constants.RED_PACKET_CHECK_STATUS_PASS).andFullLessThanOrEqualTo(req.getAmount());
                    List<BsRedPacketCheck> checkList = redPacketCheckMapper.selectByExample(checkExample);
                    if(!CollectionUtils.isEmpty(checkList)) {
                        String terms = checkList.get(0).getTermLimit();
                        if(StringUtil.isNotBlank(terms)) {
                            String[] array = terms.split(",");
                            for(String term : array) {
                                if(term.equals(String.valueOf(product.getTerm()))) {
                                    temp = list.get(0);
                                    break;
                                }
                            }
                        }
                    }
                    //不满足满减的条件
                    if(temp == null) {
                        throw new PTMessageException(PTMessageEnum.RED_PACKET_NO_ROLE_USE);
                    }
                }
            }
        }
        final BsRedPacketInfo redPacket = temp;
        //11、判断余额是否够用
        List<BsSubAccount> subList = subAccountMapper.selectSubAccount(req.getUserId(), Constants.SYS_ACCOUNT_JSH, Constants.SUBACCOUNT_STATUS_OPEN);
        if(CollectionUtils.isEmpty(subList)) {
            throw new PTMessageException(PTMessageEnum.USER_JSH_NO_EXIT);
        }
        BsSubAccount subAccount = subList.get(0);
        Double balance = redPacket==null?subAccount.getAvailableBalance():MoneyUtil.add(redPacket.getAmount(), subAccount.getAvailableBalance()).doubleValue();
        if(req.getAmount() > balance) {
            throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
        }
        // 12、判断单人此项目最多购买限额
//        if(null != product.getMaxInvestAmount()) {
//            // 查询单人此项目投资总金额
//            Double historyBalance = 0d;
//            BsAccountExample bsAccountExample = new BsAccountExample();
//            bsAccountExample.createCriteria().andUserIdEqualTo(bsUser.getId());
//            List<BsAccount> bsAccountList = accountMapper.selectByExample(bsAccountExample);
//
//            BsPropertyInfo bsPropertyInfo = bsPropertyInfoMapper.selectByPrimaryKey(product.getPropertyId());
//            if(!Constants.PROPERTY_SYMBOL_ZAN.equals(bsPropertyInfo.getPropertySymbol())) {
//                // REG户
//                List<Integer> regStatusList = new ArrayList<>();
//                regStatusList.add(Constants.SUBACCOUNT_STATUS_OPEN);
//                regStatusList.add(Constants.SUBACCOUNT_STATUS_CLOSE);
//                BsSubAccountExample regExample = new BsSubAccountExample();
//                regExample.createCriteria()
//                        .andProductIdEqualTo(product.getId())
//                        .andAccountIdEqualTo(bsAccountList.get(0).getId())
//                        .andProductTypeEqualTo(Constants.PRODUCT_TYPE_REG)
//                        .andStatusNotIn(regStatusList);
//                List<BsSubAccount> regSubAccount = subAccountMapper.selectByExample(regExample);
//                if(!CollectionUtils.isEmpty(regSubAccount)) {
//                    for (BsSubAccount reg : regSubAccount) {
//                        historyBalance = MoneyUtil.add(historyBalance, reg.getBalance()).doubleValue();
//                    }
//                }
//                logger.info("港湾计划：单人此项目历史购买金额", historyBalance);
//            } else {
//                // AUTH户
//                List<Integer> authStatusList = new ArrayList<>();
//                authStatusList.add(Constants.SUBACCOUNT_STATUS_OPEN);
//                authStatusList.add(Constants.SUBACCOUNT_STATUS_CLOSE);
//                BsSubAccountExample authExample = new BsSubAccountExample();
//                authExample.createCriteria()
//                        .andProductIdEqualTo(product.getId())
//                        .andAccountIdEqualTo(bsAccountList.get(0).getId())
//                        .andProductTypeEqualTo(Constants.PRODUCT_TYPE_AUTH)
//                        .andStatusNotIn(authStatusList);
//                List<BsSubAccount> authSubAccount = subAccountMapper.selectByExample(authExample);
//                if(!CollectionUtils.isEmpty(authSubAccount)) {
//                    for (BsSubAccount auth : authSubAccount) {
//                        historyBalance = MoneyUtil.add(historyBalance, auth.getOpenBalance()).doubleValue();
//                    }
//                }
//                logger.info("委托计划：单人此项目历史购买金额", historyBalance);
//            }
//            logger.info("单人此项目最多购买限额", product.getMaxInvestAmount());
//            logger.info("单人此项目购买总金额", MoneyUtil.add(req.getAmount(), historyBalance).doubleValue());
//            logger.info("是否超过单人此项目最多购买限额：", product.getMaxInvestAmount().compareTo(MoneyUtil.add(req.getAmount(), historyBalance).doubleValue()) < 0 ? "超限" : "未超限");
//            if(product.getMaxInvestAmount().compareTo(MoneyUtil.add(req.getAmount(), historyBalance).doubleValue()) < 0) {
//                // 超过单人此项目最多购买限额
//                throw new PTMessageException(PTMessageEnum.PRODUCT_MAX_INVEST_AMOUNT_IS_OUT.getCode(), PTMessageEnum.PRODUCT_MAX_INVEST_AMOUNT_IS_OUT.getMessage() + product.getMaxInvestAmount());
//            }
//        }

        logger.info("余额购买前置校验结束:{}", JSONObject.fromObject(req));
        //组织返回数据
        returnMap.put("bsUser", bsUser);
        returnMap.put("product", product);
        returnMap.put("redPacket", redPacket);
        return returnMap;
    }
}
