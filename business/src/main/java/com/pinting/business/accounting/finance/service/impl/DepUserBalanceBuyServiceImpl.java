package com.pinting.business.accounting.finance.service.impl;

import com.pinting.business.accounting.finance.model.ProductType;
import com.pinting.business.accounting.finance.model.SubAccountCode;
import com.pinting.business.accounting.finance.service.DepUserBalanceBuyService;
import com.pinting.business.accounting.finance.service.DepUserBonusGrantService;
import com.pinting.business.accounting.finance.service.impl.process.DepUserBonusGrant4BuyProcess;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.model.PartnerSysActCode;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.accounting.service.AccountHandleService;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.aspect.cache.ClearRedisCache;
import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.dao.*;
import com.pinting.business.enums.HFBankAccountType;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_BalanceBuy;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_BalanceBuy;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.TicketCheckVO;
import com.pinting.business.service.manage.impl.ActivityCollectByRabbitService;
import com.pinting.business.service.site.*;
import com.pinting.business.util.*;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_PlatformAccountConverse;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_PlatformAccountConverse;
import com.pinting.gateway.in.util.MethodRole;
import com.pinting.gateway.out.service.HfbankTransportService;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by babyshark on 2017/4/1.
 */
@Service
public class DepUserBalanceBuyServiceImpl implements DepUserBalanceBuyService {
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    private final Logger logger = LoggerFactory.getLogger(DepUserBalanceBuyServiceImpl.class);
    @Autowired
    private AccountHandleService accountHandleService;
    @Autowired
    private DepUserBonusGrantService depUserBonusGrantService;
    @Autowired
    private BsPayOrdersMapper payOrderMapper;
    @Autowired
    private BsSysConfigMapper sysMapper;
    @Autowired
    private BsProductMapper proMapper;
    @Autowired
    private BsSubAccountMapper subAccountMapper;
    @Autowired
    private BsSubAccountPairMapper bsSubAccountPairMapper;
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
    private BsAccountJnlMapper accountJnlMapper;
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
    private BsPropertyInfoMapper bsPropertyInfoMapper;
    @Autowired
    private BsSysSubAccountService bsSysSubAccountService;
    @Autowired
    private LoanRelationshipService loanRelationshipService;
    @Autowired
    private HfbankTransportService hfbankTransportService;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;
    @Autowired
    private AssetsService assetsService;
    @Autowired
    private ActivityCollectByRabbitService activityCollectRabbitService;
    @Autowired
    private BsInterestTicketInfoMapper bsInterestTicketInfoMapper;
    @Autowired
    private BsTicketGrantPlanCheckMapper bsTicketGrantPlanCheckMapper;
    @Autowired
    private BsProductSerialMapper bsProductSerialMapper;
    
    /**
     * 固定期限产品购买
     *
     * @param req
     * @param res
     */
    @Override
    @ClearRedisCache(clearKey = {ConstantsForCache.CacheKey.APPPRODUCTFACADE_LISTINDEXINFOQUERY, ConstantsForCache.CacheKey.APPPRODUCTFACADE_LISTRETURNTYPE, 
    		ConstantsForCache.CacheKey.PRODUCTFACADE_LISTQUERY, ConstantsForCache.CacheKey.HOME_INFOQUERY, 
    		ConstantsForCache.CacheKey.PRODUCT_FINDSUGGEST, ConstantsForCache.CacheKey.PRODUCT_SELECTPRODUCTDETAILLIST})
    @MethodRole("APP")
    public void buyFixed(final ReqMsg_RegularBuy_BalanceBuy req, final ResMsg_RegularBuy_BalanceBuy res) {
        try {
            logger.info("固定期限产品余额购买开始:{}", JSONObject.fromObject(req));

            jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_BUYPRODUCT.getKey());
            Map<String, Object> returnMap =  buyPreCheck(req);
            final BsUser bsUser = (BsUser) returnMap.get("bsUser");
            final BsProduct product = (BsProduct) returnMap.get("product");
            final BsRedPacketInfo redPacket = (BsRedPacketInfo) returnMap.get("redPacket");
            final BsInterestTicketInfo ticket = (BsInterestTicketInfo) returnMap.get("ticket");
            final Date now = new Date();

            // 非自主放款标识不允许购买
            BsPropertyInfo info = bsPropertyInfoMapper.selectByPrimaryKey(product.getPropertyId());
            if(!PartnerEnum.YUN_DAI_SELF.getCode().equals(info.getPropertySymbol())
                    && !PartnerEnum.ZSD.getCode().equals(info.getPropertySymbol())
                    && !PartnerEnum.SEVEN_DAI_SELF.getCode().equals(info.getPropertySymbol())
                    && !PartnerEnum.FREE.getCode().equals(info.getPropertySymbol())) {
                throw new PTMessageException(PTMessageEnum.FORBID_BUY);
            }
            //==========================================验证结束============================================

            BsSubAccountPair subActPair = null;
            final BsPropertyInfo bsPropertyInfo = bsPropertyInfoMapper.selectByPrimaryKey(product.getPropertyId());
            final String orderNo = Util.generateOrderNo(req.getUserId());
            final String qbOrderNo = Util.generateQianbaoOrderNo(req.getUserId());
            subActPair = transactionTemplate.execute(new TransactionCallback<BsSubAccountPair>(){
                @Override
                public BsSubAccountPair doInTransaction(TransactionStatus status) {
                    //购买子账户开户
                    BsSubAccountPair pair = chargeFixedActOpen(req, product, redPacket);
                    Integer authSubId = pair.getAuthAccountId();
                    //新增订单表
                    BsPayOrders order = new BsPayOrders();
                    if (bsUser.getCreateChannel() != 1) {
                    	order.setOrderNo(qbOrderNo + bsUser.getCreateChannel());
					} else {
						order.setOrderNo(orderNo);
					}
                    order.setAmount(redPacket==null?req.getAmount():MoneyUtil.defaultRound(MoneyUtil.subtract(req.getAmount(), redPacket.getAmount())).doubleValue());
                    order.setUserId(req.getUserId());
                    order.setSubAccountId(authSubId);
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
                    jnl.setSubAccountId(authSubId);
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
                    BsSubAccount tempJsh = subAccountMapper.selectSingleSubActByUserAndType(req.getUserId(),Constants.PRODUCT_TYPE_DEP_JSH);
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
                    BsSysSubAccount sysUserLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_BGW_USER);
                    if(req.getAmount() > sysUserLock.getAvailableBalance()) {
                        throw new PTMessageException(PTMessageEnum.SYS_BALANCE_NOT_ENOUGH);
                    }
                    BsSysSubAccount readyUserUpdate = new BsSysSubAccount();
                    readyUserUpdate.setId(sysUserLock.getId());
                    readyUserUpdate.setBalance(MoneyUtil.subtract(sysUserLock.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readyUserUpdate.setAvailableBalance(MoneyUtil.subtract(sysUserLock.getAvailableBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readyUserUpdate.setCanWithdraw(MoneyUtil.subtract(sysUserLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sysAccountMapper.updateByPrimaryKeySelective(readyUserUpdate);

                    //增加系统的AUTH_YUN || AUTH_ZSD余额
                    PartnerSysActCode sysCode = BaseAccount.partnerActCodeMap.get(PartnerEnum.getEnumByCode(bsPropertyInfo.getPropertySymbol()));
                    BsSysSubAccount sysAuthLock = bsSysSubAccountService.findSysSubAccount4Lock(sysCode.getAuthActCode());
                    BsSysSubAccount readyRegUpdate = new BsSysSubAccount();
                    readyRegUpdate.setId(sysAuthLock.getId());
                    readyRegUpdate.setBalance(MoneyUtil.add(sysAuthLock.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readyRegUpdate.setAvailableBalance(MoneyUtil.add(sysAuthLock.getAvailableBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readyRegUpdate.setCanWithdraw(MoneyUtil.add(sysAuthLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
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
                        HashMap<String, Object> extendMap = res.getExtendMap();
                        if(extendMap == null){
                            extendMap = new HashMap<String, Object>();
                        }
                        extendMap.put("product", extendPro);
                        extendMap.put("userId", bsUser.getId());
                        extendMap.put("investAmount", req.getAmount());
                        extendMap.put("subAccountId", authSubId);
                        extendMap.put("orderNo", order.getOrderNo());
                        if (bsUser.getCreateChannel() != 1) {
                        	extendMap.put("channel", bsUser.getCreateChannel());
						}
                        res.setExtendMap(extendMap);
                    }

                    //判读用户是否为首次购买
                    List<Integer> statusList = new ArrayList<Integer>();
                    statusList.add(1);
                    statusList.add(6);
                    List<String> prodTypeList = new ArrayList<String>();
                    prodTypeList.add(Constants.PRODUCT_TYPE_REG);
                    prodTypeList.add(Constants.PRODUCT_TYPE_AUTH);
                    prodTypeList.add(Constants.PRODUCT_TYPE_AUTH_YUN);
                    prodTypeList.add(Constants.PRODUCT_TYPE_AUTH_ZSD);
                    prodTypeList.add(Constants.PRODUCT_TYPE_AUTH_7);
                    prodTypeList.add(Constants.PRODUCT_TYPE_AUTH_FREE);
                    BsSubAccountExample subAccExample = new BsSubAccountExample();
                    subAccExample.createCriteria().andAccountIdEqualTo(tempJsh.getAccountId())
                            .andStatusNotIn(statusList).andProductTypeIn(prodTypeList);
                    List<BsSubAccount> temp = subAccountMapper.selectByExample(subAccExample);
                    if(temp.size() == 1) {
                        BsUser user = new BsUser();
                        user.setId(order.getUserId());
                        user.setFirstBuyTime(new Date());
                        userMapper.updateByPrimaryKeySelective(user);
                    }

                    //用户余额购买记账流水表
                    BsAccountJnl accountJnl = new BsAccountJnl();
                    accountJnl.setTransTime(new Date());
                    accountJnl.setTransCode(Constants.USER_BALANCE_BUY_PRODUCT);
                    accountJnl.setTransName("余额购买");
                    accountJnl.setTransAmount(order.getAmount());
                    accountJnl.setSysTime(new Date());
                    accountJnl.setCdFlag1(Constants.CD_FLAG_C);
                    accountJnl.setUserId1(order.getUserId());
                    accountJnl.setAccountId1(tempJsh.getAccountId());
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
                    accountJnl.setAccountId2(tempJsh.getAccountId());
                    accountJnl.setSubAccountId2(authSubId);
                    accountJnl.setBeforeBalance2(0.0);
                    accountJnl.setAfterBalance2(order.getAmount());
                    accountJnl.setBeforeAvialableBalance2(0.0);
                    accountJnl.setAfterAvialableBalance2(order.getAmount());
                    accountJnl.setBeforeFreezeBalance2(0.0);
                    accountJnl.setAfterFreezeBalance2(0.0);
                    accountJnl.setFee(0.0);
                    accountJnlMapper.insertSelective(accountJnl);

                    //系统用户购买记账流水表
                    BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                    sysAccountJnl.setTransTime(new Date());
                    sysAccountJnl.setTransCode(Constants.SYS_USER_BALANCE_BUY);
                    sysAccountJnl.setTransName("用户购买");
                    sysAccountJnl.setTransAmount(order.getAmount());
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
                    sysAccountJnl.setSubAccountCode2(sysAuthLock.getCode());
                    sysAccountJnl.setBeforeBalance2(sysAuthLock.getBalance());
                    sysAccountJnl.setAfterBalance2(readyRegUpdate.getBalance());
                    sysAccountJnl.setBeforeAvialableBalance2(sysAuthLock.getAvailableBalance());
                    sysAccountJnl.setAfterAvialableBalance2(readyRegUpdate.getAvailableBalance());
                    sysAccountJnl.setBeforeFreezeBalance2(sysAuthLock.getFreezeBalance());
                    sysAccountJnl.setAfterFreezeBalance2(sysAuthLock.getFreezeBalance());
                    sysAccountJnl.setFee(0.0);
                    sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                    sysAccountJnlMapper.insertSelective(sysAccountJnl);

                    // 判断是否支持红包
                    if(!Constants.IS_SUPPORT_RED_PACKET_FALSE.equals(product.getIsSupportRedPacket())) {
                        if(redPacket != null) {
                            //修改红包状态
                            redPacket.setOrderNo(order.getOrderNo());
                            redPacket.setStatus(Constants.RED_PACKET_STATUS_USED);
                            redPacket.setUpdateTime(new Date());
                            redPacketInfoMapper.updateByPrimaryKeySelective(redPacket);
                            //存管自有子账户转账至存管红包子账户
                            subAccountService.redPacketUsed(redPacket.getAmount());
                        }
                    }
                    logger.info("=========【使用账户余额购买产品成功】币港湾订单号："+order.getOrderNo()+"=========");

                    return pair;
                }

            });
            
            
            
            Integer subAccountId = subActPair.getAuthAccountId();
            //奖励金处理
            if(subAccountId != null){
                DepUserBonusGrant4BuyProcess process = new DepUserBonusGrant4BuyProcess();
                process.setUserBonusGrantService(depUserBonusGrantService);
                process.setAmount(req.getAmount());
                process.setBonusGrantType(depUserBonusGrantService.getBonusGrantTypeByUserId(req.getUserId()));
                process.setReferrerUserId(bsUser.getRecommendId());
                process.setSelfUserId(req.getUserId());
                process.setSubAccountId(subAccountId);
                process.setPropertySymbol(bsPropertyInfo.getPropertySymbol());
                new Thread(process).start();
            }

            //满额发自动红包
            AutoRedPacketParams params = new AutoRedPacketParams();
            params.setAmount(req.getAmount());
            params.setUserId(req.getUserId());
            params.setTriggerType(Constants.AUTO_RED_PACKET_TIGGER_TYPE_BUY_FULL);
            List<Integer> redPacketIds = redPacketService.autoRedPacketSend(params);
            if (redPacketIds != null && redPacketIds.size() >0) {
                res.setIsAutoRedPocket("yes");
            }

            //请求恒丰自有子账户转账至抵用金子账户
            if(!Constants.IS_SUPPORT_RED_PACKET_FALSE.equals(product.getIsSupportRedPacket())
                    && redPacket != null) {
                String trsOrderNo = Util.generateSysOrderNo("HTS");
                try {
                    Date transTime = new Date();
                    //订单表插入
                    BsPayOrders order = new BsPayOrders();
                    order.setOrderNo(trsOrderNo);
                    order.setAmount(redPacket.getAmount());
                    order.setUserId(req.getUserId());
                    order.setSubAccountId(subActPair.getRedAccountId());
                    order.setStatus(Constants.ORDER_STATUS_CREATE);
                    order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
                    order.setTerminalType(req.getTerminalType());
                    order.setCreateTime(transTime);
                    order.setUpdateTime(transTime);
                    order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
                    order.setTransType(Constants.TRANS_RED_PACKET_USED);
                    order.setMobile(bsUser.getMobile());
                    order.setIdCard(bsUser.getIdCard());
                    order.setUserName(bsUser.getUserName());
                    order.setChannel(Constants.CHANNEL_HFBANK);
                    order.setChannelTransType(Constants.CHANNEL_TRS_TRANSFER);
                    payOrderMapper.insertSelective(order);
                    //新增订单流水表
                    BsPayOrdersJnl jnl = new BsPayOrdersJnl();
                    jnl.setOrderId(order.getId());
                    jnl.setOrderNo(order.getOrderNo());
                    jnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
                    jnl.setTransAmount(order.getAmount());
                    jnl.setSysTime(transTime);
                    jnl.setUserId(req.getUserId());
                    jnl.setSubAccountId(subActPair.getRedAccountId());
                    jnl.setCreateTime(transTime);
                    payOrderJnlMapper.insertSelective(jnl);
                    //发起转账请求
                    B2GReqMsg_HFBank_PlatformAccountConverse actTrsReq = new B2GReqMsg_HFBank_PlatformAccountConverse();
                    actTrsReq.setAmt(redPacket.getAmount());
                    actTrsReq.setSource_account(HFBankAccountType.DEP_JSH.getCode());
                    actTrsReq.setDest_account(HFBankAccountType.DEP_RED.getCode());
                    actTrsReq.setOrder_no(trsOrderNo);
                    actTrsReq.setPartner_trans_date(transTime);
                    actTrsReq.setPartner_trans_time(transTime);
                    actTrsReq.setRemark("红包抵用");
                    B2GResMsg_HFBank_PlatformAccountConverse resMsg = hfbankTransportService.platformAccountConverse(actTrsReq);
                    //由于是内部账户间互转，此处不考虑处理中情况
                    //成功
                    if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())){
                        BsPayOrders ordersSucc = new BsPayOrders();
                        ordersSucc.setId(order.getId());
                        ordersSucc.setStatus(Constants.ORDER_STATUS_SUCCESS);
                        ordersSucc.setUpdateTime(new Date());
                        payOrderMapper.updateByPrimaryKeySelective(ordersSucc);
                        BsPayOrdersJnl jnlSucc = new BsPayOrdersJnl();
                        jnlSucc.setOrderId(order.getId());
                        jnlSucc.setOrderNo(order.getOrderNo());
                        jnlSucc.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
                        jnlSucc.setTransAmount(order.getAmount());
                        jnlSucc.setSysTime(new Date());
                        jnlSucc.setUserId(req.getUserId());
                        jnlSucc.setSubAccountId(order.getSubAccountId());
                        jnlSucc.setCreateTime(new Date());
                        payOrderJnlMapper.insertSelective(jnlSucc);
                    }
                    //失败
                    else{
                        BsPayOrders ordersFail = new BsPayOrders();
                        ordersFail.setId(order.getId());
                        ordersFail.setStatus(Constants.ORDER_STATUS_FAIL);
                        ordersFail.setReturnCode(resMsg.getRecode());
                        ordersFail.setReturnMsg(resMsg.getRemsg());
                        ordersFail.setUpdateTime(new Date());
                        payOrderMapper.updateByPrimaryKeySelective(ordersFail);
                        BsPayOrdersJnl jnlFail = new BsPayOrdersJnl();
                        jnlFail.setOrderId(order.getId());
                        jnlFail.setOrderNo(order.getOrderNo());
                        jnlFail.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                        jnlFail.setTransAmount(order.getAmount());
                        jnlFail.setSysTime(new Date());
                        jnlFail.setUserId(req.getUserId());
                        jnlFail.setSubAccountId(order.getSubAccountId());
                        jnlFail.setCreateTime(new Date());
                        payOrderJnlMapper.insertSelective(jnlFail);
                        throw new PTMessageException(PTMessageEnum.DEP_TRANS_ORDER_ERROR, resMsg.getRemsg());
                    }
                } catch (Exception e) {
                    logger.info("红包抵用平台账户转账失败：{}", e.getMessage());
                    specialJnlService.warn4FailNoSMS(redPacket.getAmount(),
                            "红包抵用平台账户转账[" + trsOrderNo + "]失败", trsOrderNo, "红包抵用平台账户转账失败");
                }
            }

            // 加息券
            if(ticket != null) {
                BsInterestTicketInfo ticketInfo = new BsInterestTicketInfo();
                // 投资本金 * 加息利率 * 投资期限 / 365
                ticketInfo.setInterestAmount(CalculatorUtil.calculate("a*a*a/a", req.getAmount(), ticket.getTicketApr(), Double.valueOf(product.getTerm4Day()), 36500d));
                ticketInfo.setAuthAccountId(subAccountId);
                ticketInfo.setOrderNo(orderNo);
                ticketInfo.setUseTime(new Date());
                ticketInfo.setUpdateTime(new Date());
                ticketInfo.setStatus(Constants.TICKET_INTEREST_STATUS_USED);
                ticketInfo.setId(ticket.getId());
                bsInterestTicketInfoMapper.updateByPrimaryKeySelective(ticketInfo);
            }
            
            String regularBuyBizOrderNo=null;
            if (bsUser.getCreateChannel() != 1) {
            	regularBuyBizOrderNo = qbOrderNo + bsUser.getCreateChannel();
			} else {
				regularBuyBizOrderNo = orderNo;
			}
            
            //数据入RabbitMQ
            activityCollectRabbitService.salaryPlanCollect(req, product, subAccountId, regularBuyBizOrderNo);
            
            try {
                // 短信模板、微信模板通知
                smsServiceClient.sendTemplateMessage(bsUser.getMobile(), TemplateKey.BUY_PRODUCT_SUC, req.getAmount().toString(),
                        product.getTerm4Day().toString(), product.getBaseRate().toString()+"%");
                String wxMsg = sendWechatService.buyProductMgs(req.getUserId(), "", product.getName(),  product.getBaseRate().toString()+"%", req.getAmount().toString(), product.getTerm4Day().toString(), "suc",null);
                logger.info("固定期限产品余额购买微信消息返回信息：{}", wxMsg);
            } catch (Exception e) {
                logger.info("固定期限产品余额购买发送消息失败{}", e.getMessage());
                e.printStackTrace();
            }

            HashMap<String, Object> extendMap = res.getExtendMap();
            if(extendMap == null){
                extendMap = new HashMap<String, Object>();
            }
            extendMap.put("subAccountId", subAccountId);
            res.setExtendMap(extendMap);

            logger.info("固定期限产品余额购买结束:{}", JSONObject.fromObject(res));
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_BUYPRODUCT.getKey());
        }
    }

    /**
     * 分期产品购买
     *
     * @param req
     * @param res
     */
    @Override
    @MethodRole("APP")
    public void buyStage(final ReqMsg_RegularBuy_BalanceBuy req, final ResMsg_RegularBuy_BalanceBuy res) {
        try {
            logger.info("委托计划余额购买开始:{}", JSONObject.fromObject(req));
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_BUYPRODUCT.getKey());
            Map<String, Object> returnMap =  buyPreCheck(req);
            final BsRedPacketInfo redPacket = (BsRedPacketInfo) returnMap.get("redPacket");
            //分期产品不可使用红包
            if(redPacket != null){
                throw new PTMessageException(PTMessageEnum.RED_PACKET_NO_ROLE_USE);
            }
            final BsUser bsUser = (BsUser) returnMap.get("bsUser");
            final BsProduct product = (BsProduct) returnMap.get("product");
            final Date now = new Date();

            // 非赞分期标识不允许购买
            BsPropertyInfo info = bsPropertyInfoMapper.selectByPrimaryKey(product.getPropertyId());
            if(!PartnerEnum.ZAN.getCode().equals(info.getPropertySymbol())) {
                throw new PTMessageException(PTMessageEnum.FORBID_BUY);
            }
            //==========================================验证结束============================================
            Integer subAccountId = transactionTemplate.execute(new TransactionCallback<Integer>(){
                @Override
                public Integer doInTransaction(TransactionStatus status) {
                    BaseAccount baseAccount = new BaseAccount();
                    baseAccount.setAmount(req.getAmount());
                    baseAccount.setPartner(PartnerEnum.ZAN);
                    baseAccount.setInvestorUserId(req.getUserId());
                    int subId = chargeStageActOpen(baseAccount, req.getProductId());
                    BsSubAccount sub = subAccountMapper.selectByPrimaryKey(subId);
                    //新增订单表
                    BsPayOrders order = new BsPayOrders();
                    order.setOrderNo(Util.generateOrderNo(req.getUserId()));
                    order.setAmount(req.getAmount());
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
                    List<String> prodTypeList = new ArrayList<String>();
                    prodTypeList.add(Constants.PRODUCT_TYPE_REG);
                    prodTypeList.add(Constants.PRODUCT_TYPE_AUTH);
                    prodTypeList.add(Constants.PRODUCT_TYPE_AUTH_YUN);
                    prodTypeList.add(Constants.PRODUCT_TYPE_AUTH_ZSD);
                    prodTypeList.add(Constants.PRODUCT_TYPE_AUTH_7);
                    BsSubAccountExample subAccExample = new BsSubAccountExample();
                    subAccExample.createCriteria().andAccountIdEqualTo(sub.getAccountId())
                            .andStatusNotIn(statusList).andProductTypeIn(prodTypeList);
                    List<BsSubAccount> bsSubList = subAccountMapper.selectByExample(subAccExample);
                    if(bsSubList.size() == 1) {
                        BsUser user = new BsUser();
                        user.setId(order.getUserId());
                        user.setFirstBuyTime(new Date());
                        userMapper.updateByPrimaryKeySelective(user);
                    }
                    return subId;
                }
            });

            //发送短信
            smsServiceClient.sendTemplateMessage(bsUser.getMobile(), TemplateKey.BUY_PRODUCT_SUC4ZAN,  req.getAmount().toString(), product.getTerm().toString(), product.getBaseRate().toString()+"%");
            //发送微信模板消息
            sendWechatService.buyProductMgs4Zan(req.getUserId(), "", product.getName(),  product.getBaseRate().toString()+"%", req.getAmount().toString(), product.getTerm().toString(), "suc",null);

            HashMap<String, Object> extendMap = res.getExtendMap();
            if(extendMap == null){
                extendMap = new HashMap<String, Object>();
            }
            res.setExtendMap(extendMap);
            logger.info("委托计划余额购买结束:{}", JSONObject.fromObject(res));
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_BUYPRODUCT.getKey());
        }
    }

    
    /**
     * VIP固定期限产品购买
     *
     * @param req
     */
    @Override
    @MethodRole("APP")
    public void buyFixed4Super(final ReqMsg_RegularBuy_BalanceBuy req) {
        try {
            logger.info("VIP定期限产品余额购买开始:{}", JSONObject.fromObject(req));
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_USER_BUYPRODUCT.getKey());
            transactionTemplate.execute(new TransactionCallback<Integer>(){
                @Override
                public Integer doInTransaction(TransactionStatus status) {
                    Date now = new Date();
                    BsUser bsUser = userMapper.selectByPrimaryKey(req.getUserId());
                    BsProduct pro = proMapper.selectByPrimaryKey(req.getProductId());
                    //查询VIP站岗户
                    final BsPropertyInfo bsPropertyInfo = bsPropertyInfoMapper.selectByPrimaryKey(pro.getPropertyId());

                    ProductType type = SubAccountCode.productTypeMap.get(PartnerEnum.getEnumByCode(bsPropertyInfo.getPropertySymbol()));
                    BsSubAccount tempSuperAuth = subAccountMapper.selectSingleSubActByUserAndType(req.getUserId(), type.getAuthCode());
                    Integer authSubId = null;
                    if(tempSuperAuth == null){
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(now);
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        Date interestBeginDate = calendar.getTime();
                        BsAccountExample accountExample = new BsAccountExample();
                        accountExample.createCriteria().andUserIdEqualTo(req.getUserId());
                        BsAccount account = accountMapper.selectByExample(accountExample).get(0);
                        //站岗户生成
                        BsSubAccount authSub = new BsSubAccount();
                        authSub.setAccountId(account.getId());
                        authSub.setCode(accountHandleService.generateProductAccount(req.getProductId(), req.getUserId()));
                        authSub.setProductId(req.getProductId());
                        authSub.setProductType(type.getAuthCode());
                        authSub.setProductCode(pro.getCode());
                        authSub.setProductName(pro.getName());
                        authSub.setProductRate(pro.getBaseRate());
                        authSub.setExtraRate(0d);
                        authSub.setOpenBalance(req.getAmount());
                        authSub.setBalance(0d);
                        authSub.setAvailableBalance(0d);
                        authSub.setCanWithdraw(0d);
                        authSub.setFreezeBalance(0d);
                        authSub.setTransStatus(Constants.USER_SUB_TRANS_STATUS_0);
                        authSub.setStatus(Constants.SUBACCOUNT_STATUS_FINANCING);
                        authSub.setInterestBeginDate(interestBeginDate); //起息日期
                        authSub.setLastFinishInterestDate(null); //结束日期
                        authSub.setAccumulationInerest(0d);
                        authSub.setOpenTime(now);
                        subAccountMapper.insertSelective(authSub);
                        tempSuperAuth = authSub;
                        //补差户生成
                        BsSubAccount diffSub = new BsSubAccount();
                        diffSub.setAccountId(account.getId());
                        diffSub.setCode(accountHandleService.generateProductAccount(req.getProductId(), req.getUserId()));
                        diffSub.setProductId(req.getProductId());
                        diffSub.setProductType(type.getDiffCode());
                        diffSub.setProductCode(pro.getCode());
                        diffSub.setProductName(pro.getName());
                        diffSub.setProductRate(pro.getBaseRate());
                        diffSub.setExtraRate(0d);
                        diffSub.setOpenBalance(0d);
                        diffSub.setBalance(0d);
                        diffSub.setAvailableBalance(0d);
                        diffSub.setCanWithdraw(0d);
                        diffSub.setFreezeBalance(0d);
                        diffSub.setTransStatus(Constants.USER_SUB_TRANS_STATUS_0);
                        diffSub.setStatus(Constants.SUBACCOUNT_STATUS_FINANCING);
                        diffSub.setInterestBeginDate(interestBeginDate); //起息日期
                        diffSub.setLastFinishInterestDate(null); //结束日期
                        diffSub.setAccumulationInerest(0d);
                        diffSub.setOpenTime(now);
                        subAccountMapper.insertSelective(diffSub);
                        //子账户对插入
                        BsSubAccountPair subPair = new BsSubAccountPair();
                        subPair.setAuthAccountId(authSub.getId());
                        subPair.setRegDAccountId(null);
                        subPair.setRedAccountId(null);
                        subPair.setDiffAccountId(diffSub.getId());
                        subPair.setCreateTime(now);
                        subPair.setUpdateTime(now);
                        bsSubAccountPairMapper.insertSelective(subPair);
                        authSubId = authSub.getId();
                    }else {
                        authSubId = tempSuperAuth.getId();
                    }
                    //新增订单表
                    BsPayOrders order = new BsPayOrders();
                    order.setOrderNo(Util.generateOrderNo(req.getUserId()));
                    order.setAmount(req.getAmount());
                    order.setUserId(req.getUserId());
                    order.setSubAccountId(authSubId);
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
                    jnl.setSubAccountId(authSubId);
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
                    BsSubAccount tempJsh = subAccountMapper.selectSingleSubActByUserAndType(req.getUserId(),Constants.PRODUCT_TYPE_DEP_JSH);
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
                    //站岗户金额增加
                    BsSubAccount superAuth = subAccountMapper.selectSubAccountByIdForLock(tempSuperAuth.getId());
                    BsSubAccount authActTemp = new BsSubAccount();
                    authActTemp.setId(superAuth.getId());
                    authActTemp.setBalance(MoneyUtil.add(superAuth.getBalance(), order.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    authActTemp.setAvailableBalance(MoneyUtil.add(superAuth.getAvailableBalance(), order.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    authActTemp.setCanWithdraw(MoneyUtil.add(superAuth.getCanWithdraw(), order.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    authActTemp.setLastTransDate(now);
                    subAccountMapper.updateByPrimaryKeySelective(authActTemp);

                    //减少系统的USER(用户余额户)余额
                    BsSysSubAccount sysUserLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_BGW_USER);
                    if(req.getAmount() > sysUserLock.getAvailableBalance()) {
                        throw new PTMessageException(PTMessageEnum.SYS_BALANCE_NOT_ENOUGH);
                    }
                    BsSysSubAccount readyUserUpdate = new BsSysSubAccount();
                    readyUserUpdate.setId(sysUserLock.getId());
                    readyUserUpdate.setBalance(MoneyUtil.subtract(sysUserLock.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readyUserUpdate.setAvailableBalance(MoneyUtil.subtract(sysUserLock.getAvailableBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readyUserUpdate.setCanWithdraw(MoneyUtil.subtract(sysUserLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sysAccountMapper.updateByPrimaryKeySelective(readyUserUpdate);

                    //增加系统的AUTH_YUN余额
                    PartnerSysActCode sysCode = BaseAccount.partnerActCodeMap.get(PartnerEnum.getEnumByCode(bsPropertyInfo.getPropertySymbol()));
                    BsSysSubAccount sysAuthLock = bsSysSubAccountService.findSysSubAccount4Lock(sysCode.getAuthActCode());
                    BsSysSubAccount readyRegUpdate = new BsSysSubAccount();
                    readyRegUpdate.setId(sysAuthLock.getId());
                    readyRegUpdate.setBalance(MoneyUtil.add(sysAuthLock.getBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readyRegUpdate.setAvailableBalance(MoneyUtil.add(sysAuthLock.getAvailableBalance(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    readyRegUpdate.setCanWithdraw(MoneyUtil.add(sysAuthLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    sysAccountMapper.updateByPrimaryKeySelective(readyRegUpdate);

                    //修改用户信息表
                    BsUser userLock = userMapper.selectByPKForLock(bsUser.getId());
                    BsUser updateUser = new BsUser();
                    updateUser.setId(userLock.getId());
                    updateUser.setCanWithdraw(MoneyUtil.subtract(userLock.getCanWithdraw(), order.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    userMapper.updateByPrimaryKeySelective(updateUser);

                    //修改产品表中的累计人次和累计金额
                    if(pro != null) {
                        pro.setInvestNum(pro.getInvestNum()+1);
                        pro.setCurrTotalAmount(MoneyUtil.add(pro.getCurrTotalAmount(), req.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                        proMapper.updateByPrimaryKeySelective(pro);
                    }

                    //判断用户是否为首次购买
                    List<Integer> statusList = new ArrayList<Integer>();
                    statusList.add(1);
                    statusList.add(6);
                    List<String> prodTypeList = new ArrayList<String>();
                    prodTypeList.add(Constants.PRODUCT_TYPE_REG);
                    prodTypeList.add(Constants.PRODUCT_TYPE_AUTH);
                    prodTypeList.add(Constants.PRODUCT_TYPE_AUTH_YUN);
                    prodTypeList.add(Constants.PRODUCT_TYPE_AUTH_ZSD);
                    prodTypeList.add(Constants.PRODUCT_TYPE_AUTH_7);
                    prodTypeList.add(Constants.PRODUCT_TYPE_AUTH_FREE);
                    BsSubAccountExample subAccExample = new BsSubAccountExample();
                    subAccExample.createCriteria().andAccountIdEqualTo(subAccountLock.getAccountId())
                            .andStatusNotIn(statusList).andProductTypeIn(prodTypeList);
                    List<BsSubAccount> bsSubList = subAccountMapper.selectByExample(subAccExample);
                    if(bsSubList.size() == 1) {
                        BsUser user = new BsUser();
                        user.setId(userLock.getId());
                        user.setFirstBuyTime(new Date());
                        userMapper.updateByPrimaryKeySelective(user);
                    }

                    //用户余额购买记账流水表
                    BsAccountJnl accountJnl = new BsAccountJnl();
                    accountJnl.setTransTime(new Date());
                    accountJnl.setTransCode(Constants.USER_BALANCE_BUY_PRODUCT);
                    accountJnl.setTransName("余额购买");
                    accountJnl.setTransAmount(order.getAmount());
                    accountJnl.setSysTime(new Date());
                    accountJnl.setCdFlag1(Constants.CD_FLAG_C);
                    accountJnl.setUserId1(order.getUserId());
                    accountJnl.setAccountId1(tempJsh.getAccountId());
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
                    accountJnl.setAccountId2(tempJsh.getAccountId());
                    accountJnl.setSubAccountId2(authSubId);
                    accountJnl.setBeforeBalance2(superAuth.getBalance());
                    accountJnl.setAfterBalance2(authActTemp.getBalance());
                    accountJnl.setBeforeAvialableBalance2(superAuth.getAvailableBalance());
                    accountJnl.setAfterAvialableBalance2(authActTemp.getAvailableBalance());
                    accountJnl.setBeforeFreezeBalance2(superAuth.getFreezeBalance());
                    accountJnl.setAfterFreezeBalance2(superAuth.getFreezeBalance());
                    accountJnl.setFee(0d);
                    accountJnlMapper.insertSelective(accountJnl);

                    //系统用户购买记账流水表
                    BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                    sysAccountJnl.setTransTime(new Date());
                    sysAccountJnl.setTransCode(Constants.SYS_USER_BALANCE_BUY);
                    sysAccountJnl.setTransName("用户购买");
                    sysAccountJnl.setTransAmount(order.getAmount());
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
                    sysAccountJnl.setSubAccountCode2(sysAuthLock.getCode());
                    sysAccountJnl.setBeforeBalance2(sysAuthLock.getBalance());
                    sysAccountJnl.setAfterBalance2(readyRegUpdate.getBalance());
                    sysAccountJnl.setBeforeAvialableBalance2(sysAuthLock.getAvailableBalance());
                    sysAccountJnl.setAfterAvialableBalance2(readyRegUpdate.getAvailableBalance());
                    sysAccountJnl.setBeforeFreezeBalance2(sysAuthLock.getFreezeBalance());
                    sysAccountJnl.setAfterFreezeBalance2(sysAuthLock.getFreezeBalance());
                    sysAccountJnl.setFee(0.0);
                    sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                    sysAccountJnlMapper.insertSelective(sysAccountJnl);

                    logger.info("=========【使用账户余额购买产品成功】币港湾订单号："+order.getOrderNo()+"=========");

                    return authSubId;
                }
            });

            logger.info("VIP固定期限产品余额购买结束");
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_BUYPRODUCT.getKey());
        }
    }

    /**
     * VIP分期产品购买
     *
     * @param req
     */
    @Override
    @MethodRole("APP")
    public void buyStage4Super(ReqMsg_RegularBuy_BalanceBuy req) {
        try {
            logger.info("VIP分期产品余额购买开始:{}", JSONObject.fromObject(req));
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
            int subId = chargeStageActOpen(account, productId);
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

            //判断用户是否为首次购买
            List<Integer> statusList = new ArrayList<Integer>();
            statusList.add(1);
            statusList.add(6);
            List<String> prodTypeList = new ArrayList<String>();
            prodTypeList.add(Constants.PRODUCT_TYPE_REG);
            prodTypeList.add(Constants.PRODUCT_TYPE_AUTH);
            prodTypeList.add(Constants.PRODUCT_TYPE_AUTH_YUN);
            prodTypeList.add(Constants.PRODUCT_TYPE_AUTH_ZSD);
            prodTypeList.add(Constants.PRODUCT_TYPE_AUTH_7);
            BsSubAccountExample subAccExample = new BsSubAccountExample();
            subAccExample.createCriteria().andAccountIdEqualTo(sub.getAccountId())
                    .andStatusNotIn(statusList).andProductTypeIn(prodTypeList);
            List<BsSubAccount> bsSubList = subAccountMapper.selectByExample(subAccExample);
            if(bsSubList.size() == 1) {
                BsUser user = new BsUser();
                user.setId(userLock.getId());
                user.setFirstBuyTime(new Date());
                userMapper.updateByPrimaryKeySelective(user);
            }
            logger.info("VIP分期产品余额购买结束");
        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_USER_BUYPRODUCT.getKey());
        }
    }

    @Transactional
    private Integer chargeStageActOpen(final BaseAccount baseAccount, final Integer productId){
        logger.info("[chargeStageActOpen]入参：" + baseAccount.toString() + ",productId=" + productId);
        final PartnerEnum partner = baseAccount.getPartner();
        final Integer investorUserId = baseAccount.getInvestorUserId();
        final Double amount = baseAccount.getAmount();
        if(partner == null || investorUserId == null || amount == null || productId == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //F:JSH >>> AUTH
        BsSubAccount tempUserJsh = subAccountMapper.selectSingleSubActByUserAndType(investorUserId, Constants.PRODUCT_TYPE_DEP_JSH);
        BsSubAccount jshAct = subAccountMapper.selectSubAccountByIdForLock(tempUserJsh.getId());
        if(MoneyUtil.subtract(jshAct.getAvailableBalance(), amount).doubleValue() < 0) {
            throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
        }
        BsSubAccount tempJshAct = new BsSubAccount();
        tempJshAct.setId(jshAct.getId());
        tempJshAct.setBalance(MoneyUtil.subtract(jshAct.getBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        tempJshAct.setAvailableBalance(MoneyUtil.subtract(jshAct.getAvailableBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        tempJshAct.setCanWithdraw(MoneyUtil.subtract(jshAct.getCanWithdraw(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        subAccountMapper.updateByPrimaryKeySelective(tempJshAct);
        //是否是超级用户
        boolean isSuperUser = false;
        List<Integer> superUsers = loanRelationshipService.getSuperUserList();
        if(!CollectionUtils.isEmpty(superUsers)){
            for (Integer superUser: superUsers) {
                if(investorUserId.equals(superUser)){
                    isSuperUser = true;
                    break;
                }
            }
        }

        BsProduct product = proMapper.selectByPrimaryKey(productId);
        BsSubAccount authAct = new BsSubAccount();
        Date now = new Date();
        Integer authActId = null;
        Double beforeAuthBalance = 0d;
        Double beforeAuthAvailableBalance = 0d;
        Double beforeAuthFreezeBalance = 0d;
        if(isSuperUser){
            //超级用户需先查询AUTH户是否存在
            BsSubAccount tempSuperAuth = subAccountMapper.selectSingleSubActByUserAndType(investorUserId, Constants.PRODUCT_TYPE_AUTH);
            if(tempSuperAuth != null) {
                BsSubAccount superAuth = subAccountMapper.selectSubAccountByIdForLock(tempSuperAuth.getId());
                authAct.setId(superAuth.getId());
                authAct.setBalance(MoneyUtil.add(superAuth.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                authAct.setAvailableBalance(MoneyUtil.add(superAuth.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                authAct.setCanWithdraw(MoneyUtil.add(superAuth.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                authAct.setLastTransDate(now);
                subAccountMapper.updateByPrimaryKeySelective(authAct);
                authActId = superAuth.getId();
                beforeAuthBalance = superAuth.getBalance();
                beforeAuthAvailableBalance = superAuth.getAvailableBalance();
                beforeAuthFreezeBalance = superAuth.getFreezeBalance();
            }
        }
        if(authActId == null){
            authAct.setAccountId(jshAct.getAccountId());
            authAct.setCode(accountHandleService.generateProductAccount(productId, investorUserId));
            authAct.setProductId(productId);
            authAct.setProductType(Constants.PRODUCT_TYPE_AUTH);
            authAct.setProductCode(product.getCode());
            authAct.setProductName(product.getName());
            authAct.setProductRate(product.getBaseRate());
            authAct.setExtraRate(0.0);
            if(isSuperUser){
                authAct.setOpenBalance(0d);
            } else {
                authAct.setOpenBalance(amount);
            }
            authAct.setBalance(amount);
            authAct.setAvailableBalance(amount);
            authAct.setCanWithdraw(amount);
            authAct.setFreezeBalance(0.0);
            authAct.setTransStatus(Constants.USER_SUB_TRANS_STATUS_2);
            authAct.setStatus(Constants.SUBACCOUNT_STATUS_FINANCING);
            authAct.setInterestBeginDate(com.pinting.core.util.DateUtil.addDays(now, 1)); //起息日期
            BsSysConfig authDaysConfig = sysMapper.selectByPrimaryKey(Constants.DAY_4_WAIT_LOAN);
            if(!isSuperUser){
                authAct.setLastFinishInterestDate(com.pinting.core.util.DateUtil.addDays(now,
                        1+Integer.valueOf(authDaysConfig.getConfValue()))); //结束日期
            }
            authAct.setAccumulationInerest(0.0);
            authAct.setOpenTime(now);
            subAccountMapper.insertSelective(authAct);
            authActId = authAct.getId();//可返回
            //REG_D户初始生成
            BsSubAccount regAct = new BsSubAccount();
            regAct.setAccountId(authAct.getAccountId());
            regAct.setCode(accountHandleService.generateProductAccount(authAct.getProductId(), investorUserId));
            regAct.setProductId(authAct.getProductId());
            regAct.setProductType(Constants.PRODUCT_TYPE_REG_D);
            regAct.setProductCode(authAct.getProductCode());
            regAct.setProductName(authAct.getProductName());
            regAct.setProductRate(authAct.getProductRate());
            regAct.setExtraRate(0.0);
            regAct.setOpenBalance(0.0);
            regAct.setBalance(0.0);
            regAct.setAvailableBalance(0.0);
            regAct.setCanWithdraw(0.0);
            regAct.setFreezeBalance(0.0);
            regAct.setTransStatus(Constants.USER_SUB_TRANS_STATUS_0);
            regAct.setStatus(Constants.SUBACCOUNT_STATUS_FINANCING);
            regAct.setInterestBeginDate(com.pinting.core.util.DateUtil.addDays(now, 1)); //起息日期
            regAct.setLastFinishInterestDate(null); //结束日期
            regAct.setAccumulationInerest(0.0);
            regAct.setOpenTime(now);
            subAccountMapper.insertSelective(regAct);
            Integer regActId = regAct.getId();//可返回
            //AUTH、REG_D关系表生成
            BsSubAccountPair pair = new BsSubAccountPair();
            pair.setAuthAccountId(authActId);
            pair.setRegDAccountId(regActId);
            pair.setTerm(product.getTerm());
            pair.setCreateTime(now);
            pair.setUpdateTime(now);
            bsSubAccountPairMapper.insertSelective(pair);
        }
        //用户账记账
        BsAccountJnl accountJnl = new BsAccountJnl();
        accountJnl.setTransTime(new Date());
        accountJnl.setTransCode(Constants.USER_AUTH_ADD);
        accountJnl.setTransName("站岗户资金授权");
        accountJnl.setTransAmount(amount);
        accountJnl.setSysTime(new Date());
        accountJnl.setChannelTime(null);
        accountJnl.setChannelJnlNo(null);
        accountJnl.setCdFlag1(Constants.CD_FLAG_C);
        accountJnl.setUserId1(investorUserId);
        accountJnl.setAccountId1(jshAct.getAccountId());
        accountJnl.setSubAccountId1(jshAct.getId());
        accountJnl.setSubAccountCode1(jshAct.getCode());
        accountJnl.setBeforeBalance1(jshAct.getBalance());
        accountJnl.setAfterBalance1(tempJshAct.getBalance());
        accountJnl.setBeforeAvialableBalance1(jshAct.getAvailableBalance());
        accountJnl.setAfterAvialableBalance1(tempJshAct.getAvailableBalance());
        accountJnl.setBeforeFreezeBalance1(jshAct.getFreezeBalance());
        accountJnl.setAfterFreezeBalance1(jshAct.getFreezeBalance());
        accountJnl.setCdFlag2(Constants.CD_FLAG_D);
        accountJnl.setUserId2(investorUserId);
        accountJnl.setAccountId2(authAct.getAccountId());
        accountJnl.setSubAccountId2(authAct.getId());
        accountJnl.setSubAccountCode2(authAct.getCode());
        accountJnl.setBeforeBalance2(beforeAuthBalance);
        accountJnl.setAfterBalance2(authAct.getBalance());
        accountJnl.setBeforeAvialableBalance2(beforeAuthAvailableBalance);
        accountJnl.setAfterAvialableBalance2(authAct.getAvailableBalance());
        accountJnl.setBeforeFreezeBalance2(beforeAuthFreezeBalance);
        accountJnl.setAfterFreezeBalance2(authAct.getFreezeBalance());
        accountJnl.setFee(0.0);
        accountJnlMapper.insertSelective(accountJnl);

        //S:USER >>> AUTH_ZAN
        BsSysSubAccount sysUserAct = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_BGW_USER);
        BsSysSubAccount tempSysUserAct = new BsSysSubAccount();
        tempSysUserAct.setId(sysUserAct.getId());
        tempSysUserAct.setBalance(MoneyUtil.subtract(sysUserAct.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        tempSysUserAct.setAvailableBalance(MoneyUtil.subtract(sysUserAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        tempSysUserAct.setCanWithdraw(MoneyUtil.subtract(sysUserAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        sysAccountMapper.updateByPrimaryKeySelective(tempSysUserAct);

        PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(baseAccount.getPartner());
        BsSysSubAccount patAuthAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getAuthActCode());
        BsSysSubAccount tempPatAuthAct = new BsSysSubAccount();
        tempPatAuthAct.setId(patAuthAct.getId());
        tempPatAuthAct.setBalance(MoneyUtil.add(patAuthAct.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        tempPatAuthAct.setAvailableBalance(MoneyUtil.add(patAuthAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        tempPatAuthAct.setCanWithdraw(MoneyUtil.add(patAuthAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        sysAccountMapper.updateByPrimaryKeySelective(tempPatAuthAct);

        //系统账户记账
        BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
        sysAccountJnl.setTransTime(new Date());
        sysAccountJnl.setTransCode(Constants.SYS_AUTH_ADD);
        sysAccountJnl.setTransName("系统站岗户资金授权");
        sysAccountJnl.setTransAmount(amount);
        sysAccountJnl.setSysTime(new Date());
        sysAccountJnl.setChannelTime(null);
        sysAccountJnl.setChannelJnlNo(null);
        sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
        sysAccountJnl.setSubAccountCode1(sysUserAct.getCode());
        sysAccountJnl.setBeforeBalance1(sysUserAct.getBalance());
        sysAccountJnl.setAfterBalance1(tempSysUserAct.getBalance());
        sysAccountJnl.setBeforeAvialableBalance1(sysUserAct.getAvailableBalance());
        sysAccountJnl.setAfterAvialableBalance1(tempSysUserAct.getAvailableBalance());
        sysAccountJnl.setBeforeFreezeBalance1(sysUserAct.getFreezeBalance());
        sysAccountJnl.setAfterFreezeBalance1(sysUserAct.getFreezeBalance());
        sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
        sysAccountJnl.setSubAccountCode2(patAuthAct.getCode());
        sysAccountJnl.setBeforeBalance2(patAuthAct.getBalance());
        sysAccountJnl.setAfterBalance2(tempPatAuthAct.getBalance());
        sysAccountJnl.setBeforeAvialableBalance2(patAuthAct.getAvailableBalance());
        sysAccountJnl.setAfterAvialableBalance2(tempPatAuthAct.getAvailableBalance());
        sysAccountJnl.setBeforeFreezeBalance2(patAuthAct.getFreezeBalance());
        sysAccountJnl.setAfterFreezeBalance2(patAuthAct.getFreezeBalance());
        sysAccountJnl.setFee(0.0);
        sysAccountJnlMapper.insertSelective(sysAccountJnl);

        return authActId;
    }

    /**
     * 固定期限产品购买子账户对开户
     * @param req
     * @param product
     * @param redPacket
     * @return
     */
    @Transactional
    private BsSubAccountPair chargeFixedActOpen(ReqMsg_RegularBuy_BalanceBuy req, BsProduct product, BsRedPacketInfo redPacket) {
        BsPropertyInfo bsPropertyInfo = bsPropertyInfoMapper.selectByPrimaryKey(product.getPropertyId());
        ProductType type = SubAccountCode.productTypeMap.get(PartnerEnum.getEnumByCode(bsPropertyInfo.getPropertySymbol()));
        //新增用户子账户信息表
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date interestBeginDate = calendar.getTime();
        int term4Day = product.getTerm4Day();
        calendar.add(Calendar.DAY_OF_MONTH, term4Day);
        Date returnDate = calendar.getTime();
        BsAccountExample accountExample = new BsAccountExample();
        accountExample.createCriteria().andUserIdEqualTo(req.getUserId());
        BsAccount account = accountMapper.selectByExample(accountExample).get(0);
        Double userAmount = redPacket==null?req.getAmount():MoneyUtil.defaultRound(MoneyUtil.subtract(req.getAmount(), redPacket.getAmount())).doubleValue();
        BsSubAccount authSub = new BsSubAccount();
        authSub.setAccountId(account.getId());
        authSub.setCode(accountHandleService.generateProductAccount(req.getProductId(), req.getUserId()));
        authSub.setProductId(req.getProductId());
        authSub.setProductType(type.getAuthCode());
        authSub.setProductCode(product.getCode());
        authSub.setProductName(product.getName());
        authSub.setProductRate(product.getBaseRate());
        authSub.setExtraRate(0.0);
        authSub.setOpenBalance(userAmount);
        authSub.setBalance(userAmount);
        authSub.setAvailableBalance(userAmount);
        authSub.setCanWithdraw(userAmount);
        authSub.setFreezeBalance(0.0);
        if (Constants.PRODUCT_TYPE_AUTH_FREE.equals(type.getAuthCode()) ) {
        	authSub.setLeftPlanInterest(CalculatorUtil.calculate("a*a*a/a", //本金*利息*天数/365
        			req.getAmount(), product.getBaseRate()/100, Double.valueOf(product.getTerm4Day()), 365d));
		}
        authSub.setTransStatus(Constants.USER_SUB_TRANS_STATUS_0);
        authSub.setStatus(Constants.SUBACCOUNT_STATUS_FINANCING);
        authSub.setInterestBeginDate(interestBeginDate); //起息日期
        authSub.setLastFinishInterestDate(returnDate); //结束日期
        authSub.setAccumulationInerest(0.0);
        authSub.setOpenTime(now);
        subAccountMapper.insertSelective(authSub);
        //红包户生成
        BsSubAccount redSub = null;
        if(!Constants.IS_SUPPORT_RED_PACKET_FALSE.equals(product.getIsSupportRedPacket())
                && redPacket != null) {
            redSub = new BsSubAccount();
            redSub.setAccountId(account.getId());
            redSub.setCode(accountHandleService.generateProductAccount(req.getProductId(), req.getUserId()));
            redSub.setProductId(req.getProductId());
            redSub.setProductType(type.getRedCode());
            redSub.setProductCode(product.getCode());
            redSub.setProductName(product.getName());
            redSub.setProductRate(product.getBaseRate());
            redSub.setExtraRate(0.0);
            redSub.setOpenBalance(redPacket.getAmount());
            redSub.setBalance(redPacket.getAmount());
            redSub.setAvailableBalance(redPacket.getAmount());
            redSub.setCanWithdraw(redPacket.getAmount());
            redSub.setFreezeBalance(0.0);
            redSub.setTransStatus(Constants.USER_SUB_TRANS_STATUS_0);
            redSub.setStatus(Constants.SUBACCOUNT_STATUS_FINANCING);
            redSub.setInterestBeginDate(interestBeginDate); //起息日期
            redSub.setLastFinishInterestDate(returnDate); //结束日期
            redSub.setAccumulationInerest(0.0);
            redSub.setOpenTime(now);
            subAccountMapper.insertSelective(redSub);
            BsAccountJnl redActJnl = new BsAccountJnl();
            redActJnl.setTransTime(new Date());
            redActJnl.setTransCode(Constants.USER_USE_REDPACKET);
            redActJnl.setTransName("红包抵用");
            redActJnl.setTransAmount(redPacket.getAmount());
            redActJnl.setSysTime(new Date());
            redActJnl.setCdFlag1(Constants.CD_FLAG_D);
            redActJnl.setUserId1(req.getUserId());
            redActJnl.setAccountId1(account.getId());
            redActJnl.setSubAccountId1(redSub.getId());
            redActJnl.setBeforeBalance1(0.0);
            redActJnl.setAfterBalance1(redPacket.getAmount());
            redActJnl.setBeforeAvialableBalance1(0.0);
            redActJnl.setAfterAvialableBalance1(redPacket.getAmount());
            redActJnl.setBeforeFreezeBalance1(0.0);
            redActJnl.setAfterFreezeBalance1(0.0);
            redActJnl.setFee(0.0);
            accountJnlMapper.insertSelective(redActJnl);
        }
        //补差户生成
        BsSubAccount diffSub = new BsSubAccount();
        diffSub.setAccountId(account.getId());
        diffSub.setCode(accountHandleService.generateProductAccount(req.getProductId(), req.getUserId()));
        diffSub.setProductId(req.getProductId());
        diffSub.setProductType(type.getDiffCode());
        diffSub.setProductCode(product.getCode());
        diffSub.setProductName(product.getName());
        diffSub.setProductRate(product.getBaseRate());
        diffSub.setExtraRate(0.0);
        diffSub.setOpenBalance(0.0);
        diffSub.setBalance(0.0);
        diffSub.setAvailableBalance(0.0);
        diffSub.setCanWithdraw(0.0);
        diffSub.setFreezeBalance(0.0);
        diffSub.setTransStatus(Constants.USER_SUB_TRANS_STATUS_0);
        diffSub.setStatus(Constants.SUBACCOUNT_STATUS_FINANCING);
        diffSub.setInterestBeginDate(interestBeginDate); //起息日期
        diffSub.setLastFinishInterestDate(returnDate); //结束日期
        diffSub.setAccumulationInerest(0.0);
        diffSub.setOpenTime(now);
        subAccountMapper.insertSelective(diffSub);
        //子账户对插入
        BsSubAccountPair subPair = new BsSubAccountPair();
        subPair.setAuthAccountId(authSub.getId());
        subPair.setRegDAccountId(null);
        subPair.setRedAccountId(redSub != null ? redSub.getId() : null);
        subPair.setDiffAccountId(diffSub.getId());
        subPair.setCreateTime(now);
        subPair.setUpdateTime(now);
        subPair.setTerm(product.getTerm());
        bsSubAccountPairMapper.insertSelective(subPair);
        return subPair;
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
        BsHfbankUserExtExample bsHfbankUserExtExample = new BsHfbankUserExtExample();
        bsHfbankUserExtExample.createCriteria().andUserIdEqualTo(req.getUserId()).andStatusEqualTo(Constants.HFBANK_USER_EXT_STATUS_OPEN);
        List<BsHfbankUserExt> extList = bsHfbankUserExtMapper.selectByExample(bsHfbankUserExtExample);
        if(CollectionUtils.isEmpty(extList)) {
            throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
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
            BsSubAccountExample subActExample = new BsSubAccountExample();
            List<Integer> statusList = new ArrayList<>();
            statusList.add(Constants.SUBACCOUNT_STATUS_OPEN);
            statusList.add(Constants.SUBACCOUNT_STATUS_SETTLE);
            statusList.add(Constants.SUBACCOUNT_STATUS_CLOSE);
            List<String> productTypeList = new ArrayList<>();
            productTypeList.add(Constants.PRODUCT_TYPE_REG);
            productTypeList.add(Constants.PRODUCT_TYPE_AUTH);
            productTypeList.add(Constants.PRODUCT_TYPE_AUTH_YUN);
            productTypeList.add(Constants.PRODUCT_TYPE_AUTH_ZSD);
            productTypeList.add(Constants.PRODUCT_TYPE_AUTH_7);
            subActExample.createCriteria().andProductTypeIn(productTypeList).andStatusNotIn(statusList).andAccountIdEqualTo(bsAccounts.get(0).getId());
            int buyCount = subAccountMapper.countByExample(subActExample);
            if(buyCount <= 0) {
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
        //8、判断当前用户是否有未完成的订单 >>> 废除
        //9、判断新手标
        List<String> transTypeList = new ArrayList<String>();
        transTypeList.add(Constants.TRANS_CARD_BUY_PRODUCT);
        transTypeList.add(Constants.TRANS_BALANCE_BUY_PRODUCT);
        if(Constants.PRODUCT_ACTIVITY_TYPE_NEW_BUYER.equals(product.getActivityType())) {
            //必须之前没有买过产品
            BsPayOrdersExample payOrderExample = new BsPayOrdersExample();
            payOrderExample.createCriteria().andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS).andTransTypeIn(transTypeList).andUserIdEqualTo(req.getUserId());
            Integer investCount = payOrderMapper.countByExample(payOrderExample);
            if(investCount > 0) {
                throw new PTMessageException(PTMessageEnum.USER_ALREADY_BUY_PRODUCT);
            }
        }
        //9.1、判断是新手用户且是欧洲杯新手用户标 >>> 废除

        BsProductSerial bsProductSerial = null;
        if(product.getSerialId() != null) {
            bsProductSerial = bsProductSerialMapper.selectByPrimaryKey(product.getSerialId());
        }

        // 判断是否支持优惠券
        BsRedPacketInfo temp = null;
        BsInterestTicketInfo ticket = null;
        boolean isRed = false;
        if(StringUtil.isNotBlank(req.getTicketType()) && req.getTicketId() != null) {
            logger.info("StringUtil.isNotBlank(req.getTicketType()) && req.getTicketId() != null");
            if(Constants.TICKET_INTEREST_TYPE_RED_PACKET.equals(req.getTicketType())) {
                // 红包
                isRed = true;
            } else if(Constants.TICKET_INTEREST_TYPE_INTEREST_TICKET.equals(req.getTicketType())) {
                logger.info("Constants.TICKET_INTEREST_TYPE_INTEREST_TICKET.equals(req.getTicketType())");
                // 加息券
                // 1. 产品是否支持使用加息券
                // 3. 排除新手专享产品
                logger.info("product: {}", JSONObject.fromObject(product));
                if(Constants.IS_SUPPORT_TICKET_INTEREST_TRUE.equals(product.getIsSupportIncrInterest()) && !Constants.PRODUCT_ACTIVITY_TYPE_NEW_BUYER.equals(product.getActivityType())) {
                    // 支持加息券
                    BsInterestTicketInfoExample example = new BsInterestTicketInfoExample();
                    example.createCriteria().andIdEqualTo(req.getTicketId()).andStatusEqualTo(Constants.TICKET_INTEREST_STATUS_INIT)
                            .andUseTimeStartLessThanOrEqualTo(now).andUseTimeEndGreaterThanOrEqualTo(now);
                    List<BsInterestTicketInfo> ticketList = bsInterestTicketInfoMapper.selectByExample(example);
                    // 6. 加息券是否过期是否未开始是否已使用
                    if(CollectionUtils.isEmpty(ticketList)) {
                        throw new PTMessageException(PTMessageEnum.TICKET_INTEREST_USED);
                    } else {
                        List<TicketCheckVO> checkList = bsTicketGrantPlanCheckMapper.selectCheckInfo(ticketList.get(0).getSerialNo(), req.getAmount());
                        // 5. 加入金额是否满足
                        if(!CollectionUtils.isEmpty(checkList)) {
                            logger.info("checkList.get(0):{}", JSONObject.fromObject(checkList.get(0)));
                            boolean isTrue = false;
                            // 4. 产品期数
                            String terms = checkList.get(0).getTermLimit();
                            if(StringUtil.isNotBlank(terms)) {
                                String[] termList = terms.split(",");
                                for(String term : termList) {
                                    if(term.equals(String.valueOf(product.getTerm4Day()))) {
                                        isTrue = true;
                                        break;
                                    }
                                }
                            }
                            logger.info("checkList.get(0)isTrue:{}", isTrue);
                            if(isTrue) {
                                isTrue = false;
                                // 2. 加息券是否支持该产品系列
                                String productLimitTemp = checkList.get(0).getProductLimit();
                                if(StringUtil.isNotBlank(productLimitTemp)) {
                                    String[] productLimit = productLimitTemp.split(",");
                                    for(String productName : productLimit) {
                                        if(Constants.PRODUCT_TYPE_BIGANGWAN_SERIAL.equals(productName)) {
                                            productName = "港湾";
                                        } else if(Constants.PRODUCT_TYPE_YONGJIN_SERIAL.equals(productName)) {
                                            productName = "涌金";
                                        } else if(Constants.PRODUCT_TYPE_KUAHONG_SERIAL.equals(productName)) {
                                            productName = "跨虹";
                                        } else if(Constants.PRODUCT_TYPE_BAOXIN_SERIAL.equals(productName)) {
                                            productName = "保信";
                                        }
                                        if(bsProductSerial != null) {
                                            if(bsProductSerial.getSerialName().contains(productName)) {
                                                isTrue = true;
                                                break;
                                            }
                                        } else if(product.getName().contains(productName)) {
                                            isTrue = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            if(isTrue) {
                                ticket = ticketList.get(0);
                            }
                        }
                        if(ticket == null) {
                            throw new PTMessageException(PTMessageEnum.TICKET_INTEREST_NO_ROLE_USE);
                        }
                    }
                }
            }
        } else {
            // 兼容老的APP接口
            isRed = true;
        }
        if(isRed && req.getTicketId() != null && !Constants.IS_SUPPORT_RED_PACKET_FALSE.equals(product.getIsSupportRedPacket())) {
            BsRedPacketInfoExample redPacketExample = new BsRedPacketInfoExample();
            redPacketExample.createCriteria().andIdEqualTo(req.getTicketId()).andStatusEqualTo(Constants.RED_PACKET_STATUS_INIT).andUseTimeStartLessThanOrEqualTo(now).andUseTimeEndGreaterThanOrEqualTo(now);
            List<BsRedPacketInfo> list = redPacketInfoMapper.selectByExample(redPacketExample);
            if(CollectionUtils.isEmpty(list)) {
                throw new PTMessageException(PTMessageEnum.RED_PACKET_USERD);
            } else {
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

        final BsRedPacketInfo redPacket = temp;
        final BsInterestTicketInfo ticketInfo = ticket;
        //11、判断余额是否够用
        List<BsSubAccount> subList = subAccountMapper.selectSubAccount(req.getUserId(), Constants.SYS_ACCOUNT_DEP_JSH, Constants.SUBACCOUNT_STATUS_OPEN);
        if(CollectionUtils.isEmpty(subList)) {
            throw new PTMessageException(PTMessageEnum.USER_JSH_NO_EXIT);
        }
        BsSubAccount subAccount = subList.get(0);
        Double balance = redPacket==null?subAccount.getAvailableBalance():MoneyUtil.add(redPacket.getAmount(), subAccount.getAvailableBalance()).doubleValue();
        if(req.getAmount() > balance) {
            throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
        }

        String riskStatus = assetsService.riskStatus(req.getUserId());
        if(Constants.IS_NO.equals(riskStatus)) {
            throw new PTMessageException(PTMessageEnum.NO_ASSESSMENT);
        } else if(Constants.IS_EXPIRE.equals(riskStatus)) {
            throw new PTMessageException(PTMessageEnum.ASSESSMENT_EXPIRE);
        }

        logger.info("余额购买前置校验结束:{}", JSONObject.fromObject(req));
        //组织返回数据
        returnMap.put("bsUser", bsUser);
        returnMap.put("product", product);
        returnMap.put("redPacket", redPacket);
        returnMap.put("ticket", ticketInfo);
        return returnMap;
    }
}