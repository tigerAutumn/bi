package com.pinting.business.accounting.loan.service.impl;

import com.pinting.business.accounting.finance.service.DepUserBonusGrantService;
import com.pinting.business.accounting.finance.service.impl.process.DepUserBonusGrant4BuyProcess;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.enums.VIPId4PartnerEnum;
import com.pinting.business.accounting.loan.model.SuperTransferInfo;
import com.pinting.business.accounting.loan.service.LoanAccountService;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.service.loan.AuthBalanaceQueryService;
import com.pinting.business.service.site.BsAgentViewConfigService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_TransferDebt;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_TransferDebt;
import com.pinting.gateway.hessian.message.hfbank.model.TransferDebtReqCommission;
import com.pinting.gateway.out.service.HfbankTransportService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class LoanRelationshipServiceImpl implements LoanRelationshipService {

    private final Logger logger = LoggerFactory.getLogger(LoanRelationshipServiceImpl.class);
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private LoanAccountService loanAccountService;
    @Autowired
    private LnLoanRelationMapper lnLoanRelationMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private LnFinanceRepayScheduleMapper lnFinanceRepayScheduleMapper;
    @Autowired
    private LnRepayScheduleMapper lnRepayScheduleMapper;
    @Autowired
    private BsSubAccountPairMapper bsSubAccountPairMapper;
    @Autowired
    private LnCreditTransferMapper lnCreditTransferMapper;
    @Autowired
    private SubAccountService subAccountService;
    @Autowired
    private AuthBalanaceQueryService authBalanaceQueryService;
    @Autowired
    private AlgorithmService algorithmService;
    @Autowired
    private DepUserBonusGrantService depUserBonusGrantService;
    @Autowired
    private BsUserMapper userMapper;
    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnLoanAmountChangeMapper lnLoanAmountChangeMapper;
    @Autowired
    private BsAgentViewConfigService bsAgentViewConfigService;
    @Autowired
    private LnDepositionTargetMapper lnDepositionTargetMapper;
    @Autowired
    private HfbankTransportService hfbankTransportService;
    @Autowired
	private SpecialJnlService specialJnlService;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;

    @Override
    public List<LnLoanRelation> confirmLoanRelation4Loan(final Integer loanId,
                                                         final Integer lnUserId, final Integer lnSubAccountId, final Double amount, final Integer loanTerm) {
        if (loanId == null || lnUserId == null || lnSubAccountId == null || amount == null || loanTerm == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        /**
         * 1、查询某日可借款的总额，判断该笔借款是否能够放款成功；
         * （通过对应的期限查询AUTH的可用余额总额和VIP理财人账户当前的可用余额总额）
         * 2、根据借款期限查询可匹配债权的投资AUTH户（分日期，按金额大到小排序）
         * 3、匹配债权，AUTH转REG_D（调借款申请授权金额冻结接口）,保存债权关系记录表
         * 4、返回债权记录列表
         */
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_LOAN_MATCH.getKey());

            return transactionTemplate.execute(new TransactionCallback<List<LnLoanRelation>>() {
                @Override
                public List<LnLoanRelation> doInTransaction(TransactionStatus status) {
                    List<LnLoanRelation> list = new ArrayList<LnLoanRelation>(); //返回值
                    LnLoanRelation initLoanRelation = new LnLoanRelation();
                    initLoanRelation.setLnSubAccountId(lnSubAccountId);
                    initLoanRelation.setLnUserId(lnUserId);
                    initLoanRelation.setLoanId(loanId);

                    logger.info("=========借款债权匹配开始：loanId=" + loanId + ",lnUserId=" + lnUserId
                            + ",lnSubAccountId=" + lnSubAccountId + ",amount=" + amount + ",loanTerm=" + loanTerm + "==============");
                    int day = 5;//站岗资金保留天数默认天数
                    BsSysConfig configDay = sysConfigService.findConfigByKey(Constants.DAY_4_WAIT_LOAN); //站岗资金保留天数
                    if (configDay != null) {
                        day = Integer.parseInt(configDay.getConfValue());
                    }
                    //获取VIP理财用户列表
                    List<Integer> superList = getSuperUserList();
                    //获取当前可匹配的总金额
                    Double normalAUTHAmount = authBalanaceQueryService.getNormalAuthBalance(loanTerm, day, superList,null);
                    Double superAUTHAmount = authBalanaceQueryService.getSuperAuthBalance(superList);
                    Double canLoanAmount = MoneyUtil.add(normalAUTHAmount, superAUTHAmount).doubleValue();

                    if (canLoanAmount < amount) {
                        logger.info("=========借款债权匹配：可借金额=" + canLoanAmount + ",需借金额=" + amount + "。不进行匹配，直接借款失败");
                        throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
                        //return list;
                    }
                    Double borrowAmount = amount;//借款人借的金额

                    if (normalAUTHAmount > 0) {
                        BsSysConfig matchTimeConfig = sysConfigService.findConfigByKey(Constants.LOAN_RELATION_MATCH_TIME); //同一日投资的债权匹配次数
                        Integer matchTime = 1;//默认同一日投资的债权匹配次数
                        if (matchTimeConfig != null) {
                            matchTime = Integer.parseInt(matchTimeConfig.getConfValue());
                        }

                        //普通理财人债权匹配
                        LoanRelationMatchReturnVO noramlMatch = normalMatch(borrowAmount, normalAUTHAmount, day, matchTime, initLoanRelation, superList, loanTerm, list);
                        //需要返回的借贷关系表
                        list = noramlMatch.getList();
                        borrowAmount = noramlMatch.getBorrowAmount();
                        normalAUTHAmount = noramlMatch.getNormalAUTHAmount();
                        if (borrowAmount == 0) {
                            return list;
                        }
                    }

                    logger.info("=========借款债权匹配：普通理财账户可借金额=" + normalAUTHAmount + ",还需借金额=" + borrowAmount + ",VIP理财账户可借金额=" + superAUTHAmount);


                    //VIP理财人债权匹配
                    if (borrowAmount > 0 && superAUTHAmount > 0) {
                        List<Integer> superUserList = getSuperUserList();
                        if (!CollectionUtils.isEmpty(superUserList)) {
                            Double limitAmount = getMatchLimitAmount();//获取 债权匹配时低于该金额的不进行债权承接

                            List<BsCanMatch4ZanSubAccountVO> canMatchList = bsSubAccountMapper.canMatch4ZanList(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN,
                                    Constants.PRODUCT_TYPE_AUTH, superUserList, null, null, "yes", limitAmount);
                            if (!CollectionUtils.isEmpty(canMatchList)) {
                                LoanRelationMatchReturnVO superMatchResult = superMatchDetails(canMatchList, borrowAmount, initLoanRelation, superAUTHAmount, list);
                                list = superMatchResult.getList();
                                borrowAmount = superMatchResult.getBorrowAmount();
                                superAUTHAmount = superMatchResult.getSuperAUTHAmount();
                            }
                        }
                    }

                    if (borrowAmount != 0) {
                        logger.info("========撮合结束，当前需借金额=" + borrowAmount + ",撮合失败==========");
                        throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
                    }

                    return list;
                }
            });
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_LOAN_MATCH.getKey());
        }
    }

    @Override
    public List<LnLoanRelation> confirmLoanRelation4LoanNew(final Integer loanId,
                                                            final Integer lnUserId, final Integer lnSubAccountId, final Double amount, final Integer loanTerm) {
        if (loanId == null || lnUserId == null || lnSubAccountId == null || amount == null || loanTerm == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        /**
         * 1、查询某日可借款的总额，判断该笔借款是否能够放款成功；
         * （通过对应的期限查询AUTH的可用余额总额和VIP理财人账户当前的可用余额总额）
         * 2、根据借款期限查询可匹配债权的投资VIP户（分日期，按金额大到小排序）
         * 3、匹配债权，AUTH转REG_D（调借款申请授权金额冻结接口）,保存债权关系记录表
         * 4、返回债权记录列表
         */
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_LOAN_MATCH.getKey());

            return transactionTemplate.execute(new TransactionCallback<List<LnLoanRelation>>() {
                @Override
                public List<LnLoanRelation> doInTransaction(TransactionStatus status) {
                    List<LnLoanRelation> list = new ArrayList<LnLoanRelation>(); //返回值
                    LnLoanRelation initLoanRelation = new LnLoanRelation();
                    initLoanRelation.setLnSubAccountId(lnSubAccountId);
                    initLoanRelation.setLnUserId(lnUserId);
                    initLoanRelation.setLoanId(loanId);

                    logger.info("=========借款债权匹配开始：loanId=" + loanId + ",lnUserId=" + lnUserId
                            + ",lnSubAccountId=" + lnSubAccountId + ",amount=" + amount + ",loanTerm=" + loanTerm + "==============");
                    int day = 5;//站岗资金保留天数默认天数
                    BsSysConfig configDay = sysConfigService.findConfigByKey(Constants.DAY_4_WAIT_LOAN); //站岗资金保留天数
                    if (configDay != null) {
                        day = Integer.parseInt(configDay.getConfValue());
                    }

                    //获取VIP理财用户列表
                    List<Integer> superList = getSuperUserList();
                    //获取当前可匹配的总金额
                    Double normalAUTHAmount = authBalanaceQueryService.getNormalAuthBalance(loanTerm, day, superList,null);
                    Double normalSmallAuthAmount = authBalanaceQueryService.getSmallNormalAuthBalanceNew(loanTerm, day, superList);
                    Double superAUTHAmount = authBalanaceQueryService.getSuperAuthBalance(superList);
                    Double canLoanAmount = MoneyUtil.add(MoneyUtil.add(normalAUTHAmount, superAUTHAmount).doubleValue(), normalSmallAuthAmount).doubleValue();

                    if (canLoanAmount < amount) {
                        logger.info("=========借款债权匹配：可借金额=" + canLoanAmount + ",需借金额=" + amount + "。不进行匹配，直接借款失败");
                        throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
                        //return list;
                    }

                    Double borrowAmount = amount;//借款人借的金额
                    //优先匹配普通用户小于1000的剩余投资金额
                    logger.info("=========借款债权匹配：理财账户剩余小额可借金额=" + normalSmallAuthAmount + ",还需借金额=" + borrowAmount);

                    if (normalSmallAuthAmount > 0) {
                        logger.info("小额匹配开始=============================================================");
                        //普通理财人小额债权匹配
                        LoanRelationMatchReturnVO smallNormalMatch = normalSmallMatch(borrowAmount, normalSmallAuthAmount, day, initLoanRelation, superList, loanTerm, list);
                        //需要返回的借贷关系表
                        list = smallNormalMatch.getList();
                        borrowAmount = smallNormalMatch.getBorrowAmount();
                        normalSmallAuthAmount = smallNormalMatch.getNormalAUTHAmount();
                        if (borrowAmount == 0) {
                            return list;
                        }

                    }

                    logger.info("=========借款债权匹配：理财账户剩余小额可借金额=" + normalSmallAuthAmount + ",还需借金额=" + borrowAmount + ",VIP理财账户可借金额=" + superAUTHAmount);

                    //VIP理财人债权匹配
                    if (borrowAmount > 0 && superAUTHAmount > 0) {
                        List<Integer> superUserList = getSuperUserList();
                        if (!CollectionUtils.isEmpty(superUserList)) {
                            Double limitAmount = getMatchLimitAmount();//获取 债权匹配时低于该金额的不进行债权承接

                            List<BsCanMatch4ZanSubAccountVO> canMatchList = bsSubAccountMapper.canMatch4ZanList(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN,
                                    Constants.PRODUCT_TYPE_AUTH, superUserList, null, null, "yes", limitAmount);
                            if (!CollectionUtils.isEmpty(canMatchList)) {
                                LoanRelationMatchReturnVO superMatchResult = superMatchDetailsNew(canMatchList, borrowAmount, initLoanRelation, superAUTHAmount, list, normalSmallAuthAmount);
                                list = superMatchResult.getList();
                                borrowAmount = superMatchResult.getBorrowAmount();
                                superAUTHAmount = superMatchResult.getSuperAUTHAmount();
                            }
                        }
                    }


                    logger.info("=========借款债权匹配：VIP理财账户可借金额=" + superAUTHAmount + ",还需借金额=" + borrowAmount + ",普通理财账户可借金额=" + normalAUTHAmount);
                    if (borrowAmount > 0 && normalAUTHAmount > 0) {

                        //普通理财人债权匹配
                        LoanRelationMatchReturnVO noramlMatch = normalMatchNew(borrowAmount, normalAUTHAmount, day, initLoanRelation, superList, loanTerm, list);
                        //需要返回的借贷关系表
                        list = noramlMatch.getList();
                        borrowAmount = noramlMatch.getBorrowAmount();
                        normalAUTHAmount = noramlMatch.getNormalAUTHAmount();
                        if (borrowAmount == 0) {
                            return list;
                        }
                    }


                    if (borrowAmount != 0) {
                        logger.info("========撮合结束，当前需借金额=" + borrowAmount + ",撮合失败==========");
                        throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
                    }

                    return list;
                }
            });
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_LOAN_MATCH.getKey());
        }
    }


    //获取 债权匹配时低于该金额的不进行债权承接
    protected Double getMatchLimitAmount() {
        BsSysConfig limit = sysConfigService.findConfigByKey(Constants.MATCH_LIMIT_AMOUNT);//债权匹配时低于该金额的不进行债权承接
//		Double limitAmount = 20d;//债权匹配时低于该金额的不进行债权承接
        Double limitAmount = 1000d;//债权匹配时低于该金额的不进行债权承接
        if (limit != null) {
            limitAmount = Double.valueOf(limit.getConfValue());
        }
        return limitAmount;
    }

    /**
     * 债权匹配时，VIP理财人匹配详情
     *
     * @param canMatchList
     * @param borrowAmount
     * @param initLoanRelation
     * @param superAUTHAmount
     * @param list
     * @return
     */
    protected LoanRelationMatchReturnVO superMatchDetails(
            List<BsCanMatch4ZanSubAccountVO> canMatchList, Double borrowAmount,
            LnLoanRelation initLoanRelation, Double superAUTHAmount,
            List<LnLoanRelation> list) {
        LoanRelationMatchReturnVO returnVo = new LoanRelationMatchReturnVO();
        BsSysConfig configMax = sysConfigService.findConfigByKey(Constants.MATCH_VIPUSER_MATCH_MAX_AMOUNT);//VIP理财人匹配金额的最大值
        BsSysConfig configMin = sysConfigService.findConfigByKey(Constants.MATCH_VIPUSER_MATCH_MIN_AMOUNT);//VIP理财人匹配金额的最小值
        Integer vipMax = 500;//VIP理财人匹配金额的最大值
        Integer vipMin = 20;//VIP理财人匹配金额的最小值
        if (configMax != null) {
            vipMax = Integer.valueOf(configMax.getConfValue());
        }
        if (configMin != null) {
            vipMin = Integer.valueOf(configMin.getConfValue());
        }
        for (BsCanMatch4ZanSubAccountVO record : canMatchList) {
            Double VIPAmount = record.getAvailableBalance();
            while (VIPAmount != 0 && borrowAmount != 0) {

                //债权匹配时低于该金额的不进行债权承接
                Double limitAmount = getMatchLimitAmount();
                //匹配金额
                Double thisAmount = getVIPMatchAmount(vipMax, vipMin, VIPAmount, borrowAmount, limitAmount);

                if (thisAmount == 0) break;

                //冻结相应AUTH户的金额，减少borrowAmount，返回的list中新增数据
                loanAccountService.chargeLoanFreeze(thisAmount, record.getId());

                //查询对应REG_D编号（目前auth和reg_d为一对一关系）
                BsSubAccountPair pair = getREG_D(record.getId());
                //新增借贷关系数据
                LnLoanRelation lnLoanRelationRecord = new LnLoanRelation();
                lnLoanRelationRecord.setCreateTime(new Date());
                lnLoanRelationRecord.setLnSubAccountId(initLoanRelation.getLnSubAccountId());
                lnLoanRelationRecord.setLnUserId(initLoanRelation.getLnUserId());
                lnLoanRelationRecord.setLoanId(initLoanRelation.getLoanId());
                lnLoanRelationRecord.setStatus(Constants.LOAN_RELATION_STATUS_PAYING);
                lnLoanRelationRecord.setUpdateTime(new Date());
                lnLoanRelationRecord.setFirstTerm(1);
                lnLoanRelationRecord.setBsSubAccountId(pair.getRegDAccountId());
                lnLoanRelationRecord.setBsUserId(record.getUserId());
                lnLoanRelationRecord.setInitAmount(thisAmount);
                lnLoanRelationRecord.setTotalAmount(thisAmount);
                lnLoanRelationRecord.setLeftAmount(thisAmount);
                lnLoanRelationMapper.insertSelective(lnLoanRelationRecord);

                //修改需借金额和VIP理财用户可借金额
                borrowAmount = MoneyUtil.subtract(borrowAmount, thisAmount).doubleValue();
                VIPAmount = MoneyUtil.subtract(VIPAmount, thisAmount).doubleValue();
                superAUTHAmount = MoneyUtil.subtract(superAUTHAmount, thisAmount).doubleValue();
                logger.info("==============借款-VIP用户匹配信息：loanId:" + initLoanRelation.getLoanId() + ",matchAmount:" + thisAmount + ",leftBorrowAmount:" + borrowAmount + "==================");
                list.add(lnLoanRelationRecord);
                //判断借款金额为0或VIP理财用户可借金额为0，则无需继续匹配
                if (borrowAmount == 0 || VIPAmount == 0 || superAUTHAmount == 0) {
                    break;
                }
            }
        }
        returnVo.setList(list);
        returnVo.setBorrowAmount(borrowAmount);
        returnVo.setSuperAUTHAmount(superAUTHAmount);
        return returnVo;
    }


    /**
     * 债权匹配时，VIP理财人匹配详情
     *
     * @param canMatchList
     * @param borrowAmount
     * @param initLoanRelation
     * @param superAUTHAmount
     * @param list
     * @return
     */
    protected LoanRelationMatchReturnVO superMatchDetailsNew(
            List<BsCanMatch4ZanSubAccountVO> canMatchList, Double borrowAmount,
            LnLoanRelation initLoanRelation, Double superAUTHAmount,
            List<LnLoanRelation> list, Double smallAuthAmount) {
        LoanRelationMatchReturnVO returnVo = new LoanRelationMatchReturnVO();
        BsSysConfig configMax = sysConfigService.findConfigByKey(Constants.MATCH_VIPUSER_MATCH_MAX_AMOUNT);//VIP理财人匹配金额的最大值
        BsSysConfig configMin = sysConfigService.findConfigByKey(Constants.MATCH_VIPUSER_MATCH_MIN_AMOUNT);//VIP理财人匹配金额的最小值

        Integer vipMax = 3000;//VIP理财人匹配金额的最大值
        Integer vipMin = 1000;//VIP理财人匹配金额的最小值
        if (configMax != null) {
            vipMax = Integer.valueOf(configMax.getConfValue());
        }
        if (configMin != null) {
            vipMin = Integer.valueOf(configMin.getConfValue());
        }
        for (BsCanMatch4ZanSubAccountVO record : canMatchList) {
            Double VIPAmount = record.getAvailableBalance();
            //债权匹配时低于该金额的不进行债权承接
            Double limitAmount = getMatchLimitAmount();
            while (VIPAmount != 0 && borrowAmount != 0) {

                //如果VIP可借金额小于最小可接债权或者剩余借款小于最小可接债权，则不匹配
                if (VIPAmount < limitAmount || borrowAmount < limitAmount) {
                    break;
                }

                Double thisAmount;

                //如果小额金额列表已无可匹配金额
                if (smallAuthAmount == 0) {

                    if (borrowAmount <= 3000 && VIPAmount >= borrowAmount) {
                        //剩余借款金额不大于3000,vip可借金额不小于剩余借款金额,直接匹配
                        thisAmount = borrowAmount;

                    } else if (borrowAmount > 3000) {
                        //随机匹配金额且剩余借款不小于limitAmount,否则一直随机
                        thisAmount = getVIPMatchAmountNew(vipMax, vipMin, VIPAmount, borrowAmount, limitAmount);

                    } else {
                        //剩余借款金额不大于3000,vip可借金额小于剩余借款金额,且（剩余借款金额-vip可借金额）不小于最小可借债权，直接匹配金额为VIP可借金额
                        if (borrowAmount - VIPAmount >= limitAmount) {
                            thisAmount = VIPAmount;
                        } else {
                            break;
                        }
                    }
                }
                //如果小额金额列表有可用金额，说明剩余借款金额小于2000，
                else {
                    if (VIPAmount >= borrowAmount) {
                        //VIP可借金额不小于借款金额，直接匹配
                        thisAmount = borrowAmount;
                    } else {
                        //vip可借金额小于剩余借款金额,且（剩余借款金额-vip可借金额）不小于最小可借债权，直接匹配金额为VIP可借金额
                        if (borrowAmount - VIPAmount >= limitAmount) {
                            thisAmount = VIPAmount;
                        } else {
                            break;
                        }
                    }
                }
                if (thisAmount == 0) break;

                //冻结相应AUTH户的金额，减少borrowAmount，返回的list中新增数据
                loanAccountService.chargeLoanFreeze(thisAmount, record.getId());

                //查询对应REG_D编号（目前auth和reg_d为一对一关系）
                BsSubAccountPair pair = getREG_D(record.getId());
                //新增借贷关系数据
                LnLoanRelation lnLoanRelationRecord = new LnLoanRelation();
                lnLoanRelationRecord.setCreateTime(new Date());
                lnLoanRelationRecord.setLnSubAccountId(initLoanRelation.getLnSubAccountId());
                lnLoanRelationRecord.setLnUserId(initLoanRelation.getLnUserId());
                lnLoanRelationRecord.setLoanId(initLoanRelation.getLoanId());
                lnLoanRelationRecord.setStatus(Constants.LOAN_RELATION_STATUS_PAYING);
                lnLoanRelationRecord.setUpdateTime(new Date());
                lnLoanRelationRecord.setFirstTerm(1);
                lnLoanRelationRecord.setBsSubAccountId(pair.getRegDAccountId());
                lnLoanRelationRecord.setBsUserId(record.getUserId());
                lnLoanRelationRecord.setInitAmount(thisAmount);
                lnLoanRelationRecord.setTotalAmount(thisAmount);
                lnLoanRelationRecord.setLeftAmount(thisAmount);
                lnLoanRelationMapper.insertSelective(lnLoanRelationRecord);

                //修改需借金额和VIP理财用户可借金额
                borrowAmount = MoneyUtil.subtract(borrowAmount, thisAmount).doubleValue();
                VIPAmount = MoneyUtil.subtract(VIPAmount, thisAmount).doubleValue();
                superAUTHAmount = MoneyUtil.subtract(superAUTHAmount, thisAmount).doubleValue();
                logger.info("==============借款-VIP用户匹配信息：loanId:" + initLoanRelation.getLoanId() + ",matchAmount:" + thisAmount + ",leftBorrowAmount:" + borrowAmount + "==================");
                list.add(lnLoanRelationRecord);
                //判断借款金额为0或VIP理财用户可借金额为0，则无需继续匹配
                if (borrowAmount == 0 || VIPAmount == 0 || superAUTHAmount == 0) {
                    break;
                }
            }
        }
        returnVo.setList(list);
        returnVo.setBorrowAmount(borrowAmount);
        returnVo.setSuperAUTHAmount(superAUTHAmount);
        return returnVo;
    }

    /**
     * 获取VIP理财人撮合时的匹配金额
     *
     * @param vipMax
     * @param vipMin
     * @param availableBalance
     * @param borrowAmount
     * @return
     */

    private Double getVIPMatchAmount(Integer vipMax,
                                     Integer vipMin, Double availableBalance, Double borrowAmount, Double limitAmont) {
        /**
         * 1、对比VIP理财人账户金额和需借款金额，小的设为基准数；
         * 2、对比基准数和最小值，若基准数<最小值,则基准数为匹配金额,直接返回
         * 3、根据vipMax、vipMin（最大值、最小值）取得整百随机数；
         * 4、对比基准数是和随机数，小的为匹配金额
         */
        Double returnAmount = 0d;
        Double baseAmount = borrowAmount; //基准数
        //1、对比VIP理财人账户金额和需借款金额，小的设为基准数；
        if (availableBalance < borrowAmount) {
            baseAmount = availableBalance;
        }

        if (baseAmount <= vipMin) {
            //2、对比基准数和最小值，若基准数<最小值,则基准数为匹配金额,直接返回
            returnAmount = baseAmount;
        } else {
            Random rand = new Random();
            Integer intNum = (vipMax - vipMin) / 10;
            //(double)((rand.nextInt(150)+1)*100+5000); //(5000,20000]
            Double randAmount = (double) ((rand.nextInt(intNum) + 1) * 10 + vipMin);
            if (baseAmount < randAmount) {
                returnAmount = baseAmount;
            } else {
                returnAmount = randAmount;
            }
        }
        //剩余金额 = 借款金额-匹配金额
        Double leftAmount = MoneyUtil.subtract(borrowAmount, returnAmount).doubleValue();

        if (leftAmount > 0 && leftAmount < limitAmont) {
            if (returnAmount == baseAmount) {
                returnAmount = 0d;
            } else {
                returnAmount = getVIPMatchAmount(vipMax, vipMin, availableBalance, borrowAmount, limitAmont);
            }
        }
        return returnAmount;
    }

    private Double getVIPMatchAmountNew(Integer vipMax,
                                        Integer vipMin, Double availableBalance, Double borrowAmount, Double limitAmount) {
        /**
         * 1、对比VIP理财人账户金额和需借款金额，小的设为基准数；
         * 2、根据vipMax、vipMin（最大值、最小值）取得整百随机数；
         * 3、对比基准数是和随机数，小的为匹配金额
         */
        Double returnAmount;
        Double baseAmount = borrowAmount; //基准数
        //1、对比VIP理财人账户金额和需借款金额，小的设为基准数；
        if (availableBalance < borrowAmount) {
            baseAmount = availableBalance;
        }

        Random rand = new Random();
        //（1000，3000]  80%   1000 20%
        Integer luckyNumber = rand.nextInt(5); // [0,5)
        Double randAmount;
        if (luckyNumber > 0) {
            //匹配金额(1000，3000]，且为100的倍数
            randAmount = (double) ((rand.nextInt((vipMax - vipMin) / 100) + 1) * 100 + vipMin);
        } else {
            //匹配金额为1000
            randAmount = (double) vipMin;
        }

        logger.info("VIP 随机值："+randAmount);
        if (baseAmount < randAmount) {
            returnAmount = baseAmount;
        } else {
            returnAmount = randAmount;
        }
        //剩余金额 = 借款金额-匹配金额
        Double leftAmount = MoneyUtil.subtract(borrowAmount, returnAmount).doubleValue();

        if (leftAmount > 0 && leftAmount < limitAmount) {
            if (returnAmount == baseAmount) {
                returnAmount = 0d;
            } else {
                returnAmount = getVIPMatchAmountNew(vipMax, vipMin, availableBalance, borrowAmount, limitAmount);
            }
        }
        return returnAmount;
    }


    /**
     * 债权匹配时，普通理财账户匹配
     *
     * @param borrowAmount
     * @param normalAUTHAmount
     * @param day
     * @param matchTime
     * @param initLoanRelation
     * @param superList
     * @param loanTerm
     * @param list
     * @return
     */
    protected LoanRelationMatchReturnVO normalMatch(Double borrowAmount,
                                                    Double normalAUTHAmount, int day, Integer matchTime, LnLoanRelation initLoanRelation,
                                                    List<Integer> superList, Integer loanTerm, List<LnLoanRelation> list) {
        Date now = new Date();
        LoanRelationMatchReturnVO returnVo = new LoanRelationMatchReturnVO();
        //判断借款金额和普通用户可借金额 是否大于0，当其中一个值等于0 时，结束普通用户的债权匹配
        Double limitAmount = getMatchLimitAmount();//债权匹配时低于该金额的不进行债权承接
        while (borrowAmount > 0 && normalAUTHAmount > 0) {

            Double beforMatchBorrowAmount = borrowAmount; // 循环前需借金额
            //循环查询匹配在站岗期内的普通用户的投资（AUTH）列表
            for (int beforeDay = day - 1; beforeDay >= 0; beforeDay--) {
                //同一日投资的债权匹配次数
                for (int time = 0; time < matchTime; time++) {
                    //确定可以匹配的普通理财用户站岗户列表的日期
                    String interestBeginDate = DateUtil.formatYYYYMMDD(DateUtil.addDays(now, -beforeDay));
                    //某一日普通理财用户站岗户列表
                    List<BsCanMatch4ZanSubAccountVO> canMatchList = bsSubAccountMapper.canMatch4ZanList(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN,
                            Constants.PRODUCT_TYPE_AUTH, superList, interestBeginDate, loanTerm, "no", limitAmount);
                    if (!CollectionUtils.isEmpty(canMatchList)) {
                        //进行匹配普通理财用户站岗户列表
                        LoanRelationMatchReturnVO normalMatchResult = normalMatchDetails(canMatchList, borrowAmount, normalAUTHAmount, list, initLoanRelation);
                        list = normalMatchResult.getList();
                        borrowAmount = normalMatchResult.getBorrowAmount();
                        normalAUTHAmount = normalMatchResult.getNormalAUTHAmount();
                        if (normalAUTHAmount == 0) break;
                    }
                    if (normalAUTHAmount == 0) break;
                }
            }

            //判断匹配前后金额是否发生变化，若无变化，跳出while
            if (beforMatchBorrowAmount == borrowAmount) break;
        }
        returnVo.setList(list);
        returnVo.setBorrowAmount(borrowAmount);
        returnVo.setNormalAUTHAmount(normalAUTHAmount);
        return returnVo;
    }

    /**
     * 债权匹配时，普通理财账户匹配
     *
     * @param borrowAmount
     * @param normalAUTHAmount
     * @param day
     * @param initLoanRelation
     * @param superList
     * @param loanTerm
     * @param list
     * @return
     */
    protected LoanRelationMatchReturnVO normalMatchNew(Double borrowAmount,
                                                       Double normalAUTHAmount, int day, LnLoanRelation initLoanRelation,
                                                       List<Integer> superList, Integer loanTerm, List<LnLoanRelation> list) {
        Date now = new Date();
        LoanRelationMatchReturnVO returnVo = new LoanRelationMatchReturnVO();
        //判断借款金额和普通用户可借金额 是否大于0，当其中一个值等于0 时，结束普通用户的债权匹配
        Double limitAmount = getMatchLimitAmount();//债权匹配时低于该金额的不进行债权承接
        while (borrowAmount > 0 && normalAUTHAmount > 0) {

            Double beforMatchBorrowAmount = borrowAmount; // 循环前需借金额
            //循环查询匹配在站岗期内的普通用户的投资（AUTH）列表
            for (int beforeDay = day - 1; beforeDay >= 0; beforeDay--) {
                //确定可以匹配的普通理财用户站岗户列表的日期
                String interestBeginDate = DateUtil.formatYYYYMMDD(DateUtil.addDays(now, -beforeDay));
                //某一日普通理财用户站岗户列表
                List<BsCanMatch4ZanSubAccountVO> canMatchList = bsSubAccountMapper.canMatch4ZanList(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN,
                        Constants.PRODUCT_TYPE_AUTH, superList, interestBeginDate, loanTerm, "no", limitAmount);
                if (!CollectionUtils.isEmpty(canMatchList)) {
                    //进行匹配普通理财用户站岗户列表
                    LoanRelationMatchReturnVO normalMatchResult = normalMatchDetails(canMatchList, borrowAmount, normalAUTHAmount, list, initLoanRelation);
                    list = normalMatchResult.getList();
                    borrowAmount = normalMatchResult.getBorrowAmount();
                    normalAUTHAmount = normalMatchResult.getNormalAUTHAmount();
                }
                if (normalAUTHAmount == 0) break;
            }

            //判断匹配前后金额是否发生变化，若无变化，跳出while
            if (beforMatchBorrowAmount == borrowAmount) break;
        }
        returnVo.setList(list);
        returnVo.setBorrowAmount(borrowAmount);
        returnVo.setNormalAUTHAmount(normalAUTHAmount);
        return returnVo;
    }


    /**
     * 债权匹配时，小额普通理财账户匹配
     *
     * @param borrowAmount
     * @param normalAUTHAmount
     * @param day
     * @param initLoanRelation
     * @param superList
     * @param loanTerm
     * @param list
     * @return
     */
    protected LoanRelationMatchReturnVO normalSmallMatch(Double borrowAmount,
                                                         Double normalAUTHAmount, int day, LnLoanRelation initLoanRelation,
                                                         List<Integer> superList, Integer loanTerm, List<LnLoanRelation> list) {
        Date now = new Date();
        LoanRelationMatchReturnVO returnVo = new LoanRelationMatchReturnVO();
        //判断借款金额和普通用户可借金额 是否大于0，当其中一个值等于0 时，结束普通用户的债权匹配
        Double limitAmount = getMatchLimitAmount();//债权匹配时低于该金额的不进行债权承接
        while (borrowAmount > 0 && normalAUTHAmount > 0) {

            Double beforMatchBorrowAmount = borrowAmount; // 循环前需借金额
            //循环查询匹配在站岗期内的普通用户的投资（AUTH）列表
            for (int beforeDay = day - 1; beforeDay >= 0; beforeDay--) {
                //确定可以匹配的普通理财用户站岗户列表的日期
                String interestBeginDate = DateUtil.formatYYYYMMDD(DateUtil.addDays(now, -beforeDay));
                //某一日普通理财用户站岗户列表
                List<BsCanMatch4ZanSubAccountVO> canMatchList = bsSubAccountMapper.canSmallMatch4ZanList(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN,
                        Constants.PRODUCT_TYPE_AUTH, superList, interestBeginDate, loanTerm, "no", limitAmount);
                if (!CollectionUtils.isEmpty(canMatchList)) {
                    //进行匹配普通理财用户站岗户列表
                    LoanRelationMatchReturnVO normalMatchResult = normalSmallMatchDetails(canMatchList, borrowAmount, normalAUTHAmount, list, initLoanRelation);
                    list = normalMatchResult.getList();
                    borrowAmount = normalMatchResult.getBorrowAmount();
                    normalAUTHAmount = normalMatchResult.getNormalAUTHAmount();
                    if (borrowAmount == 0) break;
                }
            }

            //判断匹配前后金额是否发生变化，若无变化，跳出while
            if (beforMatchBorrowAmount == borrowAmount) break;
        }
        returnVo.setList(list);
        returnVo.setBorrowAmount(borrowAmount);
        returnVo.setNormalAUTHAmount(normalAUTHAmount);
        return returnVo;
    }

    /**
     * 债权匹配时，普通理财账户匹配详情
     *
     * @param canMatchList
     * @param borrowAmount
     * @param normalAUTHAmount
     * @param list
     * @param initLoanRelation
     * @return
     */
    protected LoanRelationMatchReturnVO normalMatchDetails(
            List<BsCanMatch4ZanSubAccountVO> canMatchList, Double borrowAmount,
            Double normalAUTHAmount, List<LnLoanRelation> list, LnLoanRelation initLoanRelation) {
        LoanRelationMatchReturnVO returnVo = new LoanRelationMatchReturnVO();
        Double limitAmount = getMatchLimitAmount();//普通理财人债权匹配时低于该金额的不进行债权承接
        for (BsCanMatch4ZanSubAccountVO subAccountRecord : canMatchList) {

            logger.info("当前用户sub_account_id:"+subAccountRecord.getId());
            Double thisAmount=1d;
            Double availableBalance=subAccountRecord.getAvailableBalance();

            while (availableBalance>=limitAmount && thisAmount!=0 && borrowAmount>0) {
                thisAmount = 0d; //此次撮合金额
                Double baseAmount = borrowAmount; //基础数确认
                if (availableBalance < borrowAmount) {
                    baseAmount = availableBalance;
                }
                //判断基础数是否小于最低匹配值，若小于，则跳过该subAccount数据的匹配
                if (baseAmount < limitAmount) {
                    continue;
                }
                //获得匹配金额
                thisAmount = getThisAmountNew(baseAmount, subAccountRecord.getAgentId(), limitAmount, borrowAmount);
                //判断匹配金额是否等于0，0则跳过该subAccount数据的匹配
                if (thisAmount == 0) {
                    continue;
                }

                //冻结相应AUTH户的金额
                loanAccountService.chargeLoanFreeze(thisAmount, subAccountRecord.getId());

                //查询对应REG_D编号（目前auth和reg_d为一对一关系）
                BsSubAccountPair pair = getREG_D(subAccountRecord.getId());
                //新增借贷关系数据
                LnLoanRelation lnLoanRelationRecord = new LnLoanRelation();
                lnLoanRelationRecord.setCreateTime(new Date());
                lnLoanRelationRecord.setLnSubAccountId(initLoanRelation.getLnSubAccountId());
                lnLoanRelationRecord.setLnUserId(initLoanRelation.getLnUserId());
                lnLoanRelationRecord.setLoanId(initLoanRelation.getLoanId());
                lnLoanRelationRecord.setStatus(Constants.LOAN_RELATION_STATUS_PAYING);
                lnLoanRelationRecord.setUpdateTime(new Date());
                lnLoanRelationRecord.setFirstTerm(1);
                lnLoanRelationRecord.setBsSubAccountId(pair.getRegDAccountId());
                lnLoanRelationRecord.setBsUserId(subAccountRecord.getUserId());
                lnLoanRelationRecord.setInitAmount(thisAmount);
                lnLoanRelationRecord.setTotalAmount(thisAmount);
                lnLoanRelationRecord.setLeftAmount(thisAmount);
                lnLoanRelationMapper.insertSelective(lnLoanRelationRecord);

                //修改需借金额和普通用户可借金额
                borrowAmount = MoneyUtil.subtract(borrowAmount, thisAmount).doubleValue();

                normalAUTHAmount = MoneyUtil.subtract(normalAUTHAmount, thisAmount).doubleValue();

                logger.info("==============借款-普通用户匹配信息：loanId:" + initLoanRelation.getLoanId() + ",matchAmount:" + thisAmount + ",leftBorrowAmount:" + borrowAmount + "==================");
                //返回列表增加数据
                list.add(lnLoanRelationRecord);

                availableBalance = bsSubAccountMapper.selectSubAccountByIdForLock(subAccountRecord.getId()).getAvailableBalance();
            }
        }
        returnVo.setList(list);
        returnVo.setBorrowAmount(borrowAmount);
        returnVo.setNormalAUTHAmount(normalAUTHAmount);

        return returnVo;
    }

    /**
     * 债权匹配时，小额普通理财账户匹配详情
     *
     * @param canMatchList
     * @param borrowAmount
     * @param normalAUTHAmount
     * @param list
     * @param initLoanRelation
     * @return
     */
    protected LoanRelationMatchReturnVO normalSmallMatchDetails(
            List<BsCanMatch4ZanSubAccountVO> canMatchList, Double borrowAmount,
            Double normalAUTHAmount, List<LnLoanRelation> list, LnLoanRelation initLoanRelation) {

        LoanRelationMatchReturnVO returnVo = new LoanRelationMatchReturnVO();
        Double limitAmount = getMatchLimitAmount();
        for (BsCanMatch4ZanSubAccountVO subAccountRecord : canMatchList) {
            Double thisAmount = borrowAmount; //此次撮合金额
            logger.info("此次撮合账户id："+subAccountRecord.getId());
            if (subAccountRecord.getAvailableBalance() < borrowAmount && MoneyUtil.subtract(borrowAmount, subAccountRecord.getAvailableBalance()).doubleValue() >= limitAmount) {
                thisAmount = subAccountRecord.getAvailableBalance();
            } else if (subAccountRecord.getAvailableBalance() < borrowAmount && MoneyUtil.subtract(borrowAmount, subAccountRecord.getAvailableBalance()).doubleValue() < limitAmount) {
                break;
            }
            logger.info("此次撮合金额："+thisAmount);
            if (thisAmount == 0) {
                break;
            }

            //冻结相应AUTH户的金额
            loanAccountService.chargeLoanFreeze(thisAmount, subAccountRecord.getId());

            //查询对应REG_D编号（目前auth和reg_d为一对一关系）
            BsSubAccountPair pair = getREG_D(subAccountRecord.getId());
            //新增借贷关系数据
            LnLoanRelation lnLoanRelationRecord = new LnLoanRelation();
            lnLoanRelationRecord.setCreateTime(new Date());
            lnLoanRelationRecord.setLnSubAccountId(initLoanRelation.getLnSubAccountId());
            lnLoanRelationRecord.setLnUserId(initLoanRelation.getLnUserId());
            lnLoanRelationRecord.setLoanId(initLoanRelation.getLoanId());
            lnLoanRelationRecord.setStatus(Constants.LOAN_RELATION_STATUS_PAYING);
            lnLoanRelationRecord.setUpdateTime(new Date());
            lnLoanRelationRecord.setFirstTerm(1);
            lnLoanRelationRecord.setBsSubAccountId(pair.getRegDAccountId());
            lnLoanRelationRecord.setBsUserId(subAccountRecord.getUserId());
            lnLoanRelationRecord.setInitAmount(thisAmount);
            lnLoanRelationRecord.setTotalAmount(thisAmount);
            lnLoanRelationRecord.setLeftAmount(thisAmount);
            lnLoanRelationMapper.insertSelective(lnLoanRelationRecord);

            //修改需借金额和普通用户可借金额
            borrowAmount = MoneyUtil.subtract(borrowAmount, thisAmount).doubleValue();
            logger.info("此次剩余借款金额："+borrowAmount+"--------------------------------------------------");
            normalAUTHAmount = MoneyUtil.subtract(normalAUTHAmount, thisAmount).doubleValue();
            logger.info("此次小额剩余金额："+normalAUTHAmount+"--------------------------------------------------");

            logger.info("==============借款-普通用户匹配信息：loanId:" + initLoanRelation.getLoanId() + ",matchAmount:" + thisAmount + ",leftBorrowAmount:" + borrowAmount + "==================");
            //返回列表增加数据
            list.add(lnLoanRelationRecord);
        }
        returnVo.setList(list);
        returnVo.setBorrowAmount(borrowAmount);
        returnVo.setNormalAUTHAmount(normalAUTHAmount);

        return returnVo;
    }

    /**
     * 撮合规则
     *
     * @param baseAmount   基础数金额
     * @param agentId      渠道编号
     * @param borrowAmount 借款金额
     * @param limitAmount  债权匹配时低于该金额的不进行债权承接
     *                     当匹配金额确定后，若剩余金额<limitAmount 且 thisAmount+limitAmount <= baseAmount,则thisAmount = thisAmount+limitAmount
     *                     若剩余金额<limitAmount 且
     * @return thisAmount 撮合结果金额
     */
    private Double getThisAmount(Double baseAmount, Integer agentId, Double limitAmount, Double borrowAmount) {
        Double thisAmount = 0d;
        if (agentId != null && bsAgentViewConfigService.isQianbao(agentId)) {
            /**
             * 钱报用户
             * 3000以下的直接匹配，以基础数为准；
             * 取1-4随机数；取到4，取3000-4000的随机数；取到1-3，取4000-5000随机数；
             * 如果取到的数大于基础数，则以基础数为准
             */
            if (baseAmount > 3000) {

                Random rand = new Random();
                Double rand178Amount = 3000.0;
                Integer luckyNumber = rand.nextInt(4) + 1; // [1,4]
                if (luckyNumber <= 3) {
                    rand178Amount = (double) ((rand.nextInt(10) + 1) * 100 + 4000); //(4000,5000]
                } else {
                    rand178Amount = (double) ((rand.nextInt(10) + 1) * 100 + 3000); //(3000,4000]
                }
                if (rand178Amount.compareTo(baseAmount) < 0) {
                    thisAmount = rand178Amount;
                }else {
                    thisAmount=baseAmount;
                }
            } else {
                thisAmount = baseAmount;
            }
        } else {
            /**
             * 非钱报用户
             * 100以下的直接匹配，以基础数为准；
             * 100-5000，取100-5000的随机数；
             * 5000-2万，取5000-2万的随机数；
             * 2万到5万，取2万-5万的随机数；
             * 如果取到的数大于基础数，则以基础数为准
             */
            Random rand = new Random();
            Double randAmount = baseAmount;

            if (baseAmount > 100 && baseAmount <= 5000) {
                randAmount = (double) ((rand.nextInt(50) + 1) * 100); //[100,5000]
            } else if (baseAmount > 5000 && baseAmount <= 20000) {
                randAmount = (double) ((rand.nextInt(150) + 1) * 100 + 5000); //(5000,20000]
            } else if (baseAmount > 20000) {
                randAmount = (double) ((rand.nextInt(300) + 1) * 100 + 20000); //(20000,50000]
            }
            if (randAmount.compareTo(baseAmount) <= 0) {
                thisAmount = randAmount;
            } else {
                thisAmount = baseAmount;
            }
        }
        //剩余需借金额：borrowAmount - thisAmount
        Double leftAmount = MoneyUtil.subtract(borrowAmount, thisAmount).doubleValue();
        if (leftAmount > 0 && leftAmount.compareTo(limitAmount) < 0) {

            if (thisAmount.compareTo(baseAmount) == 0) {
                //判断匹配金额是否等于基础数，若相等，则说明重新计算匹配金额也将不会在发生变化，则返回thisAmount = 0
                return 0d;
            } else {
                //若不相等，则重新计算匹配金额
                thisAmount = getThisAmount(baseAmount, agentId, limitAmount, borrowAmount);
            }
        }
        return thisAmount;
    }

    /**
     * 撮合规则
     *
     * @param baseAmount   基础数金额
     * @param agentId      渠道编号
     * @param borrowAmount 借款金额
     * @param limitAmount  债权匹配时低于该金额的不进行债权承接
     *                     当匹配金额确定后，若剩余金额<limitAmount 且 thisAmount+limitAmount <= baseAmount,则thisAmount = thisAmount+limitAmount
     *                     若剩余金额<limitAmount 且
     * @return thisAmount 撮合结果金额
     */
    @Override
    public Double getThisAmountNew(Double baseAmount, Integer agentId, Double limitAmount, Double borrowAmount) {
        Double thisAmount;
        if (agentId != null && bsAgentViewConfigService.isQianbao(agentId)) {
            /**
             * 钱报用户
             * 3000以下的直接匹配，以基础数为准；
             * 取1-4随机数；取到4，取3000-4000的随机数；取到1-3，取4000-5000随机数；
             * 如果取到的数大于基础数，则以基础数为准
             */
            if (baseAmount > 3000) {

                Random rand = new Random();
                Double rand178Amount;
                Integer luckyNumber = rand.nextInt(4) + 1; // [1,4]
                if (luckyNumber <= 3) {
                    rand178Amount = (double) ((rand.nextInt(10) + 1) * 100 + 4000); //(4000,5000]
                } else {
                    rand178Amount = (double) ((rand.nextInt(10) + 1) * 100 + 3000); //(3000,4000]
                }

                logger.info("钱报随机值："+rand178Amount);
                if (rand178Amount < baseAmount) {
                    thisAmount = rand178Amount;
                }else {
                    thisAmount=baseAmount;
                }
            } else {
                thisAmount = baseAmount;
            }
        } else {
            /**
             * 非钱报用户
             * 3000以下的直接匹配，以基础数为准；
             * 1000-3000随机
             *
             */
            if(baseAmount > 3000) {
                Random rand = new Random();
                //（1000，3000]  80%   1000 20%
                Integer luckyNumber = rand.nextInt(5); // [0,5)
                if (luckyNumber > 0) {
                    //匹配金额大于1000，小于等于3000，且为100的倍数
                    Double random = (rand.nextInt(21) + 1) * 100 + limitAmount;
                    logger.info("非钱报随机值："+random);
                    thisAmount = random > baseAmount ? baseAmount : random;
                } else {
                    //匹配金额为1000
                    thisAmount = limitAmount;
                }
            }else {
                thisAmount = baseAmount;
            }



        }
        //剩余需借金额：borrowAmount - thisAmount
        Double leftAmount = MoneyUtil.subtract(borrowAmount, thisAmount).doubleValue();
        if (leftAmount > 0 && leftAmount.compareTo(limitAmount) < 0) {

            if (thisAmount.compareTo(baseAmount) == 0 && thisAmount<=3000) {
                //判断匹配金额是否等于基础数，若相等，则说明重新计算匹配金额也将不会在发生变化，则返回thisAmount = 0
                return 0d;
            } else {
                //若不相等，则重新计算匹配金额
                logger.info("因不符合规则，重新随机");
                thisAmount = getThisAmountNew(baseAmount, agentId, limitAmount, borrowAmount);
            }
        }
        return thisAmount;
    }


    /**
     * 获取VIP理财用户列表
     *
     * @return
     */

    @Override
    public List<Integer> getSuperUserList() {
        List<Integer> superUserList = null;
        BsSysConfig configUser = sysConfigService.findConfigByKey(Constants.SUPER_FINANCE_USER_ID);//VIP理财人账户
        if (configUser != null) {
            superUserList = new ArrayList<Integer>();
            String[] userStr = configUser.getConfValue().split(",");
            for (String string : userStr) {
                superUserList.add(Integer.parseInt(string));
            }
        }
        return superUserList;
    }


    /**
     * VIP理财人债权转让
     */
    @Override
    public boolean superTransferNormal() {
        /**
         * 1、查询可以借贷关系表理财用户是VIP理财用户且剩余金额大于0的列表A（借款时间正序，剩余金额倒序）
         * 2、循环列表A，判断还款计划表中是否有已还款的记录，若无，则安排债权转让
         * 3、根据借款人的借款期限查询未被分配债权的站岗期内的站岗户列表（2016-12-16修改为符合条件的可匹配金额最大的一条记录）
         * 5、根据匹配规则计算匹配金额B
         * 6、根据当前日期与借款信息表中的借款成功时间差和匹配金额B，计算应转让的本金C。
         * 7、确定转让本金，修改借贷关系，新增借贷关系，新增债权转让记录，新增理财人还款计划，删除超级用户原来的理财人还款计划，债权转让动账
         * 8、根据该笔借贷关系最后的剩余金额判断是否需要新增新的理财人还款计划
         */
        List<Integer> superList = getSuperUserList();
        if (CollectionUtils.isEmpty(superList)) {
            //超级用户列表为空，则不进行转让
            logger.info("========VIP理财用户债权转让：VIP理财人列表为空，转让不进行=========");
            return false;
        }
        //查询可以借贷关系表理财用户是VIP理财用户且剩余金额大于0的列表A（借款时间正序，剩余金额倒序）
        List<LoanRelationVO> superLoanRelationList = lnLoanRelationMapper.getSuperLnLoanRelationList(superList);
        int day = 5;//站岗资金保留天数默认天数
        BsSysConfig configDay = sysConfigService.findConfigByKey(Constants.DAY_4_WAIT_LOAN); //站岗资金保留天数
        if (configDay != null) {
            day = Integer.parseInt(configDay.getConfValue());
        }

        if (!CollectionUtils.isEmpty(superLoanRelationList)) {
            for (LoanRelationVO lnLoanRelation : superLoanRelationList) {
                //根据借款id 统计还款计划表中状态为非init的条数 若条数大于0，则不能将债权转让
                int countNOtInit = lnRepayScheduleMapper.countByLoanIdNotInit(lnLoanRelation.getLoanId());
                if (countNOtInit > 0) {
                    continue;
                }
                //安排债权转让
                //获取当前同周期和固定天数内可匹配的总金额
                Double normalAUTHAmount = authBalanaceQueryService.getNormalAuthBalance(lnLoanRelation.getPeriod(), day, superList, lnLoanRelation.getBsUserId());
                if (normalAUTHAmount == 0) {
                	logger.info("=============赞分期转让，lnLoanRelation.id"+lnLoanRelation.getId()+"，借款月数："+lnLoanRelation.getPeriod()+"无同周期的理财资金可接");
                    continue;
                }

                Double borrowAmount = lnLoanRelation.getLeftAmount();
                //VIP理财人单笔债权关系金额 --对-- 普通站岗户列表 进行转让
                LoanRelationMatchReturnVO loanRelationResult = superTransferDetailNew(day, lnLoanRelation,
                        superList, borrowAmount, normalAUTHAmount);
                borrowAmount = loanRelationResult.getBorrowAmount();
                normalAUTHAmount = loanRelationResult.getNormalAUTHAmount();
                //无可承接的理财，跳过转让
                if (borrowAmount == null || borrowAmount.compareTo(lnLoanRelation.getLeftAmount()) == 0) {
                    continue;
                }
                /*原可多次转出，现只能一对一转出
                 * if (borrowAmount > 0) {
                    //超级用户 新增理财人还款计划
                    BsSubAccount bsSubAccount = subAccountService.findSubAccountById(lnLoanRelation.getBsSubAccountId());
                    getFinanceRepayScheduleList(lnLoanRelation.getId(), borrowAmount, lnLoanRelation.getPeriod(),
                            bsSubAccount.getProductRate(), lnLoanRelation.getLoanTime());
                }*/
                //判断当前同周期和固定天数内可匹配的总金额 在 进行转让后的剩余金额是否等于0 ，若是，则不需要再金额匹配
                /*if (normalAUTHAmount == 0) {
                    break;
                }*/
            }
        } else {
            //VIP理财人债权关系列表为空，则不进行转让
            logger.info("========VIP理财用户债权转让：VIP理财人债权关系列表为空，转让不进行");
            return false;
        }

        return true;

    }


    /**
     * VIP理财人债权转让详细匹配
     *
     * @param day                  站岗资金保留天数默认天数
     * @param lnLoanRelation       可转让的VIP理财人的债权关系
     * @param superList            VIP理财人id列表
     * @param borrowAmountInit     某条债权关系中VIP理财人可转让金额--->nll表示未转让，0表示已转让
     * @param normalAUTHAmountInit 普通用户可接债权总金额
     * @return
     */
    private LoanRelationMatchReturnVO superTransferDetail(final Integer day,
                                                          final LoanRelationVO lnLoanRelation,
                                                          final List<Integer> superList,
                                                          final Double borrowAmountInit,
                                                          final Double normalAUTHAmountInit) {
        Date now = new Date();
        //借款成功当月的天数
        Integer monthDay = com.pinting.business.util.DateUtil.mothDays(lnLoanRelation.getLoanTime());
        //VIP理财人持有天数
        Date loanTime = DateUtil.parseDate(DateUtil.formatYYYYMMDD(lnLoanRelation.getLoanTime()));
        Integer superGetDay = DateUtil.getDiffeDay(now, loanTime);

        LoanRelationMatchReturnVO returnVO = new LoanRelationMatchReturnVO();
        Double borrowAmount = borrowAmountInit;
        Double normalAUTHAmount = normalAUTHAmountInit;
        while (borrowAmount > 0 && normalAUTHAmount > 0) {
            Double beforeAmount = borrowAmount;
            for (int beforeDay = day - 1; beforeDay >= 0; beforeDay--) {
                //判断可借金额是否已经为0
                if (borrowAmount == 0) {
                    break;
                }
                //获取某日可以匹配的站岗资金户
                String interestBeginDate = DateUtil.formatYYYYMMDD(DateUtil.addDays(now, -beforeDay));


                /**
                 * 根据匹配金额 = (1+月利率/当月天数(借款时间的当月)*持债权天数)* VIP可转金额
                 * 得到匹配金额最大值 = (1+最大月利率/30天*30天)* VIP可转金额                                1+最大月利率（配置表配置）
                 */
                Double maxMonthRate = 1.01; //最大月利率+1
                BsSysConfig configRate = sysConfigService.findConfigByKey(Constants.ZAN_PRODUCT_MAX_MONTH_RATE); //赞分期产品最大月利率
                if (configRate != null) {
                    maxMonthRate = MoneyUtil.divide(Double.valueOf(configRate.getConfValue()), 100, 3).doubleValue() + 1;
                }
                Double limitAmount = MoneyUtil.multiply(maxMonthRate, borrowAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                BsCanMatch4ZanSubAccountVO subAccountRecord = bsSubAccountMapper.getCanMatch4Zan2Transfer(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN,
                        Constants.PRODUCT_TYPE_AUTH, superList, interestBeginDate, lnLoanRelation.getPeriod(), limitAmount, "asc");
                if (subAccountRecord != null) {
                    //进行转让匹配
                    //获取可接该债权的金额
                    Double thisAmount = getCanTransfer4MatchAmount(subAccountRecord
                            , monthDay, superGetDay, borrowAmountInit);

                    /**
                     * 假设1000元VIP,对应承接理财金额1010，若subAccountRecord.getAvailableBalance()< 1010,
                     * 则 查询最大的跳过该笔，进行后一天的站岗户的匹配
                     */
                    if (subAccountRecord.getAvailableBalance() < thisAmount) {
                        subAccountRecord = bsSubAccountMapper.getCanMatch4Zan2Transfer(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN,
                                Constants.PRODUCT_TYPE_AUTH, superList, interestBeginDate, lnLoanRelation.getPeriod(), limitAmount, "desc");

                        if (subAccountRecord != null) {
                            thisAmount = getCanTransfer4MatchAmount(subAccountRecord, monthDay, superGetDay, borrowAmountInit);
                            if (subAccountRecord.getAvailableBalance() < thisAmount) {
                                continue;
                            }
                        }
                    }
                    //转让金额=VIP可转金额
                    final Double transferAmount = borrowAmount;
                    final BsCanMatch4ZanSubAccountVO subAccountRecordTemp = subAccountRecord;
                    final Double thisAmountTemp = thisAmount;
                    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                        @Override
                        public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                            //修改超级用户借贷关系，新增借贷关系，
                            //新增债权转让记录，新增金额变动表
                            //新增理财人还款计划，删除超级用户原来的理财人还款计划
                            doChange4LoanRelation(lnLoanRelation, subAccountRecordTemp, transferAmount, thisAmountTemp);

                            //SF转让记账
                            SuperTransferInfo sTransferInfo = new SuperTransferInfo();
                            sTransferInfo.setsInvestorRegActId(lnLoanRelation.getBsSubAccountId());
                            sTransferInfo.setSuperUserId(lnLoanRelation.getBsUserId());
                            sTransferInfo.setInvestorAuthActId(subAccountRecordTemp.getId());
                            sTransferInfo.setNormalUserId(subAccountRecordTemp.getUserId());
                            sTransferInfo.setMatchAmount(thisAmountTemp);
                            sTransferInfo.setAmount(transferAmount);

                            sTransferInfo.setPartner(PartnerEnum.ZAN);
                            loanAccountService.chargeSuperTransfer(sTransferInfo);
                        }
                    });
                    //用户的起息日在此时间前转让成功，发送奖励金，否则不发送奖励金
                    Date diffDate = DateUtil.parseDate(Constants.ZAN_BONUSGRANT_DIFFERENT_DATE);
                    if (subAccountRecord.getInterestBeginDate().compareTo(diffDate) < 0) {
                        //查询对应REG_D户
                        BsSubAccountPair pair = getREG_D(subAccountRecord.getId());

                        //查询用户信息，获取
                        BsUser bsUser = userMapper.selectByPrimaryKey(subAccountRecord.getUserId());
                        //奖励金处理
                        if (pair != null && pair.getId() != null) {
                            DepUserBonusGrant4BuyProcess process = new DepUserBonusGrant4BuyProcess();
                            process.setUserBonusGrantService(depUserBonusGrantService);
                            process.setAmount(transferAmount);
                            process.setBonusGrantType(depUserBonusGrantService.getBonusGrantTypeByUserId(subAccountRecord.getUserId()));
                            process.setReferrerUserId(bsUser.getRecommendId());
                            process.setSelfUserId(subAccountRecord.getUserId());
                            process.setSubAccountId(pair.getRegDAccountId());
                            process.setPropertySymbol(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN);
                            new Thread(process).start();
                        }
                    }

                    //修改可转让金额和理财人AUTH总金额
                    borrowAmount = MoneyUtil.subtract(borrowAmount, transferAmount).doubleValue();
                    normalAUTHAmount = MoneyUtil.subtract(normalAUTHAmount, thisAmount).doubleValue();
                    returnVO.setBorrowAmount(borrowAmount);
                    returnVO.setNormalAUTHAmount(normalAUTHAmount);
                    //判断是否是最后一笔出借，然后发送微信通知
                    try {
                        subAccountService.sendWechat4LastLoan(subAccountRecord.getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //判断可转让金额和理财人AUTH总金额是否为0，为0则无需继续转让
                    if (borrowAmount == 0 || normalAUTHAmount == 0) {
                        break;
                    }
                }
            }
            //比较转让前的可转让金额和转让后金额时候相同，相同则说明已无可承接的债权
            if (borrowAmount == beforeAmount) break;
        }
        return returnVO;

    }


    /**
     * VIP理财人债权转让详细匹配(2017-03-27 新版)
     *
     * @param day                  站岗资金保留天数默认天数
     * @param lnLoanRelation       可转让的VIP理财人的债权关系
     * @param superList            VIP理财人id列表
     * @param borrowAmountInit     某条债权关系中VIP理财人可转让金额--->nll表示未转让，0表示已转让
     * @param normalAUTHAmountInit 普通用户可接债权总金额
     * @return
     */
    private LoanRelationMatchReturnVO superTransferDetailNew(final Integer day,
                                                             final LoanRelationVO lnLoanRelation,
                                                             final List<Integer> superList,
                                                             final Double borrowAmountInit,
                                                             final Double normalAUTHAmountInit) {
        Date now = new Date();
        //借款成功当月的天数
        Integer monthDay = com.pinting.business.util.DateUtil.mothDays(lnLoanRelation.getLoanTime());
        //VIP理财人持有天数
        Date loanTime = DateUtil.parseDate(DateUtil.formatYYYYMMDD(lnLoanRelation.getLoanTime()));
        Integer superGetDay = DateUtil.getDiffeDay(now, loanTime);

        final LoanRelationMatchReturnVO returnVO = new LoanRelationMatchReturnVO();
        returnVO.setBorrowAmount(borrowAmountInit);
        returnVO.setNormalAUTHAmount(normalAUTHAmountInit);
        while (returnVO.getBorrowAmount() > 0 && returnVO.getNormalAUTHAmount() > 0) {
            Double beforeAmount = returnVO.getBorrowAmount();
            for (int beforeDay = day - 1; beforeDay >= 0; beforeDay--) {
                //判断可借金额是否已经为0
                if (returnVO.getBorrowAmount() == 0) {
                    break;
                }
                //获取某日可以匹配的站岗资金户（按照投资额的从大到小）
                String interestBeginDate = DateUtil.formatYYYYMMDD(DateUtil.addDays(now, -beforeDay));
                //债权转让时查询可以接收债权的AUTH户金额 按照投资金额从多到少排序
                BsCanMatch4ZanSubAccountVO subAccountRecord = bsSubAccountMapper.getCanMatch4Zan2TransferNew(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN,
                        Constants.PRODUCT_TYPE_AUTH, superList, interestBeginDate, lnLoanRelation.getPeriod(), borrowAmountInit, "desc",lnLoanRelation.getBsUserId());
                if (subAccountRecord != null) {
                	BsHfbankUserExtExample hfExample = new BsHfbankUserExtExample();
                	hfExample.createCriteria().andUserIdEqualTo(lnLoanRelation.getBsUserId());
                	List<BsHfbankUserExt> outHfUserList = bsHfbankUserExtMapper.selectByExample(hfExample);
                	final BsHfbankUserExt hfUser = outHfUserList.get(0);
                	//受让人信息
                	final BsCanMatch4ZanSubAccountVO subAccountRecordTemp = subAccountRecord;
                    //计算折让金额
                    final Double discountAmount = getCanTransfer4DiscountAmount(subAccountRecord, monthDay, superGetDay, borrowAmountInit);
                    //转让金额=VIP可转金额
                    final Double transferAmount = returnVO.getBorrowAmount();
                    
                    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                        @Override
                        public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                        	boolean transferFlag = false;
                        	 //冻结承接人AUTH户金额
                        	loanAccountService.chargeLoanFreeze(transferAmount, subAccountRecordTemp.getId());
                        	//查询标的信息
                        	LnDepositionTarget depositionTarget = lnDepositionTargetMapper.selectByLoanId(lnLoanRelation.getLoanId());
                        	
                        	//标的转让
                        	B2GReqMsg_HFBank_TransferDebt transReq = new B2GReqMsg_HFBank_TransferDebt();
                        	
                        	transReq.setPlatcust(hfUser.getHfUserId());
        					transReq.setProd_id(depositionTarget.getId().toString());
        					transReq.setDeal_amount(transferAmount);
        					//出让人手续费
        					List<TransferDebtReqCommission> commissionList = new ArrayList<TransferDebtReqCommission>();
        					TransferDebtReqCommission commission = new TransferDebtReqCommission();
        					commission.setPayout_amt("0.00");
        					commission.setPayout_plat_type(Constants.PAYOUT_PLAT_TYPE_FEE);
        					commissionList.add(commission);
        					transReq.setCommission(commissionList);
        					//转让收益
        					transReq.setTransfer_income(0d);
        					//交易金额=成交价格(债转本金)+出让人手续费0+受让人手续费0+转让收益0
        					transReq.setTrans_amt(transferAmount);
        					//受让人平台客户编号
        					transReq.setDeal_platcustprivate(subAccountRecordTemp.getHfUserId());
        					//收益出资方账户
        					transReq.setIncome_acct(hfUser.getHfUserId());
        					String orderNo = Util.generateOrderNo4BaoFoo(8);
        					transReq.setOrder_no(orderNo);
        					//转让份额
        					transReq.setTrans_share(transferAmount);
        					//抵用金
        					transReq.setCoupon_amt(0d);
        					
        					B2GResMsg_HFBank_TransferDebt transRes = hfbankTransportService.transferDebt(transReq);
        					if(!Constants.DEP_RECODE_SUCCESS.equals(transRes.getRecode())){
        						transRes = hfbankTransportService.transferDebt(transReq);
        						if(!Constants.DEP_RECODE_SUCCESS.equals(transRes.getRecode())){
        							// 再次转让失败
        							//解冻相应AUTH户的金额
        							loanAccountService.chargeLoanUnFreeze(transferAmount, subAccountRecordTemp.getId());
       							 	//发送告警短信
       							 	specialJnlService.warn4FailNoSMS(transferAmount, "zan，vip转让失败，relationId:"+lnLoanRelation.getId(), orderNo, "债权转让失败");
        						}else{
        							//修改超级用户借贷关系，新增借贷关系，
                                    //新增债权转让记录，新增金额变动表
                                    //新增理财人还款计划，删除超级用户原来的理财人还款计划
                                    doChange4LoanRelationNew(lnLoanRelation, subAccountRecordTemp, transferAmount, discountAmount);
                                    
                                    
                                    //SF转让记账
                                    SuperTransferInfo sTransferInfo = new SuperTransferInfo();
                                    sTransferInfo.setsInvestorRegActId(lnLoanRelation.getBsSubAccountId());
                                    sTransferInfo.setSuperUserId(lnLoanRelation.getBsUserId());
                                    sTransferInfo.setInvestorAuthActId(subAccountRecordTemp.getId());
                                    sTransferInfo.setNormalUserId(subAccountRecordTemp.getUserId());
                                    sTransferInfo.setMatchAmount(transferAmount);
                                    sTransferInfo.setAmount(transferAmount);

                                    sTransferInfo.setPartner(PartnerEnum.ZAN);
                                    loanAccountService.chargeSuperTransfer(sTransferInfo);
                                    
                                    transferFlag = true;
	       						}
       						 }else{
       							 //修改超级用户借贷关系，新增借贷关系，
                                 //新增债权转让记录，新增金额变动表
                                 //新增理财人还款计划，删除超级用户原来的理财人还款计划
                                 doChange4LoanRelationNew(lnLoanRelation, subAccountRecordTemp, transferAmount, discountAmount);
                                 
                                 
                                 //SF转让记账
                                 SuperTransferInfo sTransferInfo = new SuperTransferInfo();
                                 sTransferInfo.setsInvestorRegActId(lnLoanRelation.getBsSubAccountId());
                                 sTransferInfo.setSuperUserId(lnLoanRelation.getBsUserId());
                                 sTransferInfo.setInvestorAuthActId(subAccountRecordTemp.getId());
                                 sTransferInfo.setNormalUserId(subAccountRecordTemp.getUserId());
                                 sTransferInfo.setMatchAmount(transferAmount);
                                 sTransferInfo.setAmount(transferAmount);

                                 sTransferInfo.setPartner(PartnerEnum.ZAN);
                                 loanAccountService.chargeSuperTransfer(sTransferInfo);
                                 
                                 transferFlag = true;
       						 }
                        	if(transferFlag){
                        		//用户的起息日在此时间前转让成功，发送奖励金，否则不发送奖励金
                                Date diffDate = DateUtil.parseDate(Constants.ZAN_BONUSGRANT_DIFFERENT_DATE);
                                if (subAccountRecordTemp.getInterestBeginDate().compareTo(diffDate) < 0) {
                                    //查询对应REG_D户
                                    BsSubAccountPair pair = getREG_D(subAccountRecordTemp.getId());

                                    //查询用户信息，获取
                                    BsUser bsUser = userMapper.selectByPrimaryKey(subAccountRecordTemp.getUserId());
                                    //奖励金处理
                                    if (pair != null && pair.getId() != null) {
                                        DepUserBonusGrant4BuyProcess process = new DepUserBonusGrant4BuyProcess();
                                        process.setUserBonusGrantService(depUserBonusGrantService);
                                        process.setAmount(transferAmount);
                                        process.setBonusGrantType(depUserBonusGrantService.getBonusGrantTypeByUserId(subAccountRecordTemp.getUserId()));
                                        process.setReferrerUserId(bsUser.getRecommendId());
                                        process.setSelfUserId(subAccountRecordTemp.getUserId());
                                        process.setSubAccountId(pair.getRegDAccountId());
                                        process.setPropertySymbol(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN);
                                        new Thread(process).start();
                                    }
                                }

                                //修改可转让金额和理财人AUTH总金额
                                returnVO.setBorrowAmount(MoneyUtil.subtract(returnVO.getBorrowAmount(), transferAmount).doubleValue());
                                returnVO.setNormalAUTHAmount(MoneyUtil.subtract(returnVO.getNormalAUTHAmount(), transferAmount).doubleValue());
                                //判断是否是最后一笔出借，然后发送微信通知
                                try {
                                    subAccountService.sendWechat4LastLoan(subAccountRecordTemp.getId());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }}
                        }
                    });
                    
                    
                }
            }
            //比较转让前的可转让金额和转让后金额时候相同，相同则说明已无可承接的债权
            if (returnVO.getBorrowAmount() == beforeAmount) break;
        }
        return returnVO;

    }


    protected Double getCanTransfer4DiscountAmount(BsCanMatch4ZanSubAccountVO subAccountRecord,
                                                   Integer monthDay, Integer superGetDay, Double borrowAmount) {

        //等额本息
        AverageCapitalPlusInterestVO vo=algorithmService.calAverageCapitalPlusInterestPlan(borrowAmount,subAccountRecord.getTerm(),subAccountRecord.getProductRate()).get(0);

        //VIP理财折让金额=当期利息*VIP持有债权时间/当期时长；
        Double canTransfer4MatchAmount = CalculatorUtil.calculate("a*a/a", vo.getPlanInterest(), superGetDay.doubleValue(), monthDay.doubleValue());
        		//vo.getPlanInterest()*superGetDay/monthDay;

        return canTransfer4MatchAmount;
    }

    protected Double getCanTransfer4MatchAmount(BsCanMatch4ZanSubAccountVO subAccountRecord,
                                                Integer monthDay, Integer superGetDay, Double borrowAmount) {
        //月利率
        Double monthRate = MoneyUtil.divide(subAccountRecord.getProductRate(), 1200, 10).doubleValue();
        //(月利率/当月天数(借款时间的当月)*持债权天数)
        Double a = MoneyUtil.multiply(MoneyUtil.divide(monthRate, monthDay, 10).doubleValue(), superGetDay).doubleValue();

        //根据VIP理财 可转让金额，计算最多可承接的理财金额，匹配金额 = (1+月利率/当月天数(借款时间的当月)*持债权天数)* VIP可转金额；
        Double canTransfer4MatchAmount = MoneyUtil.multiply(a + 1, borrowAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        return canTransfer4MatchAmount;
    }

    /**
     * 根据AUTH id查询REG_D和AUTH成对信息
     *
     * @param id
     * @return
     */
    private BsSubAccountPair getREG_D(Integer id) {

        BsSubAccountPairExample pairExample = new BsSubAccountPairExample();
        pairExample.createCriteria().andAuthAccountIdEqualTo(id);
        List<BsSubAccountPair> pairs = bsSubAccountPairMapper.selectByExample(pairExample);
        if (CollectionUtils.isEmpty(pairs)) {
            throw new PTMessageException(PTMessageEnum.ACCOUNT_NOT_FOUND);
        }

        BsSubAccountPair pair = pairs.get(0);

        return pair;
    }

    /**
     * 修改超级用户借贷关系，新增借贷关系，新增债权转让记录，新增理财人还款计划，删除超级用户原来的理财人还款计划
     *
     * @param lnLoanRelation
     * @param subAccountRecord inSub
     * @param transferAmount   转让金额
     * @param inAmount         受让人出资金额
     */
    private void doChange4LoanRelation(LoanRelationVO lnLoanRelation,
                                       BsCanMatch4ZanSubAccountVO subAccountRecord, Double transferAmount, Double inAmount) {
        //修改超级用户借贷关系
        LnLoanRelation initRelation = lnLoanRelationMapper.selectByPrimaryKey(lnLoanRelation.getId());
        LnLoanRelation superLoanRelation = new LnLoanRelation();
        superLoanRelation.setId(lnLoanRelation.getId());
        superLoanRelation.setTotalAmount(MoneyUtil.subtract(initRelation.getTotalAmount(), transferAmount).doubleValue());
        superLoanRelation.setLeftAmount(MoneyUtil.subtract(initRelation.getLeftAmount(), transferAmount).doubleValue());
        superLoanRelation.setUpdateTime(new Date());
        lnLoanRelationMapper.updateByPrimaryKeySelective(superLoanRelation);

        //新增借贷关系
        LnLoanRelation normalLoanRelation = new LnLoanRelation();
        //查询对应REG_D编号（目前auth和reg_d为一对一关系）
        BsSubAccountPair pair = getREG_D(subAccountRecord.getId());

        normalLoanRelation.setBsSubAccountId(pair.getRegDAccountId());
        normalLoanRelation.setBsUserId(subAccountRecord.getUserId());
        normalLoanRelation.setCreateTime(new Date());
        normalLoanRelation.setFirstTerm(1);
        normalLoanRelation.setLeftAmount(transferAmount);
        normalLoanRelation.setLnSubAccountId(lnLoanRelation.getLnSubAccountId());
        normalLoanRelation.setLnUserId(lnLoanRelation.getLnUserId());
        normalLoanRelation.setLoanId(lnLoanRelation.getLoanId());
        normalLoanRelation.setStatus(Constants.LOAN_RELATION_STATUS_SUCCESS);
        normalLoanRelation.setInitAmount(transferAmount);
        normalLoanRelation.setTotalAmount(transferAmount);
        normalLoanRelation.setUpdateTime(new Date());
        lnLoanRelationMapper.insertSelective(normalLoanRelation);

        //新增债权金额变动记录表
        LnLoanAmountChange tempAmountChange = new LnLoanAmountChange();
        tempAmountChange.setUpdateTime(new Date());
        tempAmountChange.setAfterAmount(superLoanRelation.getLeftAmount());
        tempAmountChange.setBeforeAmount(initRelation.getLeftAmount());
        tempAmountChange.setChangeAmount(transferAmount);
        tempAmountChange.setCreateTime(new Date());
        tempAmountChange.setRelationId(lnLoanRelation.getId());
        lnLoanAmountChangeMapper.insertSelective(tempAmountChange);

        //新增债权转让记录
        LnCreditTransfer LnCreditTransfer = new LnCreditTransfer();
        LnCreditTransfer.setAmount(transferAmount);
        LnCreditTransfer.setInAmount(inAmount);
        LnCreditTransfer.setInLoanRelationId(normalLoanRelation.getId());
        LnCreditTransfer.setInSubAccountId(normalLoanRelation.getBsSubAccountId());
        LnCreditTransfer.setInUserId(subAccountRecord.getUserId());
        LnCreditTransfer.setOutLoanRelationId(lnLoanRelation.getId());
        LnCreditTransfer.setOutSubAccountId(lnLoanRelation.getBsSubAccountId());
        LnCreditTransfer.setOutUserId(lnLoanRelation.getBsUserId());
        LnCreditTransfer.setUpdateTime(new Date());
        LnCreditTransfer.setCreateTime(new Date());
        lnCreditTransferMapper.insertSelective(LnCreditTransfer);

        //新增理财人还款计划
        getFinanceRepayScheduleList(normalLoanRelation.getId(), transferAmount, lnLoanRelation.getPeriod(),
                subAccountRecord.getProductRate(), lnLoanRelation.getLoanTime());

        //删除超级用户原来的理财人还款计划
        LnFinanceRepayScheduleExample scheduleExample = new LnFinanceRepayScheduleExample();
        scheduleExample.createCriteria().andRelationIdEqualTo(lnLoanRelation.getId());
        List<LnFinanceRepaySchedule> financeList = lnFinanceRepayScheduleMapper.selectByExample(scheduleExample);
        if (financeList.size() > 0) {
            lnFinanceRepayScheduleMapper.deleteByExample(scheduleExample);
        }


    }

    /**
     * 修改超级用户借贷关系，新增借贷关系，新增债权转让记录，新增理财人还款计划，删除超级用户原来的理财人还款计划
     *
     * @param lnLoanRelation
     * @param subAccountRecord inSub
     * @param transferAmount   受让人出资金额
     * @param discountAmount   折让金额
     */
    private void doChange4LoanRelationNew(LoanRelationVO lnLoanRelation,
                                          BsCanMatch4ZanSubAccountVO subAccountRecord, Double transferAmount, Double discountAmount) {
        //修改超级用户借贷关系
        LnLoanRelation initRelation = lnLoanRelationMapper.selectByPrimaryKey(lnLoanRelation.getId());
        LnLoanRelation superLoanRelation = new LnLoanRelation();
        superLoanRelation.setId(lnLoanRelation.getId());
        superLoanRelation.setTotalAmount(MoneyUtil.subtract(initRelation.getTotalAmount(), transferAmount).doubleValue());
        superLoanRelation.setLeftAmount(MoneyUtil.subtract(initRelation.getLeftAmount(), transferAmount).doubleValue());
        superLoanRelation.setUpdateTime(new Date());
        lnLoanRelationMapper.updateByPrimaryKeySelective(superLoanRelation);

        //新增借贷关系
        LnLoanRelation normalLoanRelation = new LnLoanRelation();
        //查询对应REG_D编号（目前auth和reg_d为一对一关系）
        BsSubAccountPair pair = getREG_D(subAccountRecord.getId());

        normalLoanRelation.setBsSubAccountId(pair.getRegDAccountId());
        normalLoanRelation.setBsUserId(subAccountRecord.getUserId());
        normalLoanRelation.setCreateTime(new Date());
        normalLoanRelation.setFirstTerm(1);
        normalLoanRelation.setLeftAmount(transferAmount);
        normalLoanRelation.setLnSubAccountId(lnLoanRelation.getLnSubAccountId());
        normalLoanRelation.setLnUserId(lnLoanRelation.getLnUserId());
        normalLoanRelation.setLoanId(lnLoanRelation.getLoanId());
        normalLoanRelation.setStatus(Constants.LOAN_RELATION_STATUS_SUCCESS);
        normalLoanRelation.setInitAmount(transferAmount);
        normalLoanRelation.setTotalAmount(transferAmount);
        normalLoanRelation.setUpdateTime(new Date());
        lnLoanRelationMapper.insertSelective(normalLoanRelation);

        //新增债权金额变动记录表
        LnLoanAmountChange tempAmountChange = new LnLoanAmountChange();
        tempAmountChange.setUpdateTime(new Date());
        tempAmountChange.setAfterAmount(superLoanRelation.getLeftAmount());
        tempAmountChange.setBeforeAmount(initRelation.getLeftAmount());
        tempAmountChange.setChangeAmount(transferAmount);
        tempAmountChange.setCreateTime(new Date());
        tempAmountChange.setRelationId(lnLoanRelation.getId());
        lnLoanAmountChangeMapper.insertSelective(tempAmountChange);

        //新增债权转让记录
        LnCreditTransfer lnCreditTransfer = new LnCreditTransfer();
        lnCreditTransfer.setAmount(transferAmount);
        lnCreditTransfer.setInAmount(transferAmount);
        lnCreditTransfer.setInLoanRelationId(normalLoanRelation.getId());
        lnCreditTransfer.setInSubAccountId(normalLoanRelation.getBsSubAccountId());
        lnCreditTransfer.setInUserId(subAccountRecord.getUserId());
        lnCreditTransfer.setOutLoanRelationId(lnLoanRelation.getId());
        lnCreditTransfer.setOutSubAccountId(lnLoanRelation.getBsSubAccountId());
        lnCreditTransfer.setOutUserId(lnLoanRelation.getBsUserId());
        lnCreditTransfer.setUpdateTime(new Date());
        lnCreditTransfer.setCreateTime(new Date());
        lnCreditTransfer.setDiscountAmount(discountAmount);
        lnCreditTransferMapper.insertSelective(lnCreditTransfer);

        //新增理财人还款计划
        getFinanceRepayScheduleList(normalLoanRelation.getId(), transferAmount, lnLoanRelation.getPeriod(),
                subAccountRecord.getProductRate(), lnLoanRelation.getLoanTime());

        //删除超级用户原来的理财人还款计划
        LnFinanceRepayScheduleExample scheduleExample = new LnFinanceRepayScheduleExample();
        scheduleExample.createCriteria().andRelationIdEqualTo(lnLoanRelation.getId());
        List<LnFinanceRepaySchedule> financeList = lnFinanceRepayScheduleMapper.selectByExample(scheduleExample);
        if (financeList.size() > 0) {
            lnFinanceRepayScheduleMapper.deleteByExample(scheduleExample);
        }


    }

    /**
     * 生成理财人还款计划表
     */
    @Override
    public void getFinanceRepayScheduleList(
            Integer loanRelationId, Double amount, int term, Double rate, Date sucDate) {
        //等额本息方法获得列表
        List<AverageCapitalPlusInterestVO> avgList = algorithmService.calAverageCapitalPlusInterestPlan(amount, term, rate);
        if (!CollectionUtils.isEmpty(avgList))
            for (AverageCapitalPlusInterestVO avgRecord : avgList) {
                LnFinanceRepaySchedule record = new LnFinanceRepaySchedule();

                record.setPlanTotal(avgRecord.getPlanTotal());
                record.setPlanPrincipal(avgRecord.getPlanPrincipal());
                record.setPlanInterest(avgRecord.getPlanInterest());
                record.setRepaySerial(avgRecord.getRepaySerial());
                record.setCreateTime(new Date());
                record.setUpdateTime(new Date());
                record.setRelationId(loanRelationId);
                record.setPlanDate(DateUtil.addMonths(sucDate, avgRecord.getRepaySerial()));
                record.setStatus(Constants.FINANCE_REPAY_SATAE_INIT);
                lnFinanceRepayScheduleMapper.insertSelective(record);
            }
    }

    /**
     * 还款成功时修改债权相关信息
     */
    @Override
    public void changeLoanRelation4RepaySuccess(Integer loanId,
                                                Integer repaySerial, Double repayAmount) {
        if (loanId == null || repaySerial == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //判断是否是最后一期还款
        LnLoanExample loanExample = new LnLoanExample();
        loanExample.createCriteria().andIdEqualTo(loanId);
        List<LnLoan> loanList = lnLoanMapper.selectByExample(loanExample);
        if (CollectionUtils.isEmpty(loanList)) {
            logger.error("============ 还款时，根据借款编号查询借款信息列表：无  ============");
        }
        LnLoan loan = loanList.get(0);
        Integer period = loan.getPeriod();//借款总期数

        //根据借款编号查询所有借贷关系
        LnLoanRelationExample lrExample = new LnLoanRelationExample();
        lrExample.createCriteria().andLoanIdEqualTo(loanId);
        List<LnLoanRelation> lrList = lnLoanRelationMapper.selectByExample(lrExample);
        if (CollectionUtils.isEmpty(lrList)) {
            logger.error("============ 还款时，根据借款编号查询借贷关系列表：无  ============");
        }

        for (LnLoanRelation lnLoanRelation : lrList) {
            //根据期次和借贷关系编号查询理财人还款记录
            LnFinanceRepayScheduleExample frsExample = new LnFinanceRepayScheduleExample();
            frsExample.createCriteria().andRelationIdEqualTo(lnLoanRelation.getId()).andRepaySerialEqualTo(repaySerial);
            List<LnFinanceRepaySchedule> list = lnFinanceRepayScheduleMapper.selectByExample(frsExample);
            if (CollectionUtils.isEmpty(list)) {
                logger.error("============ 还款时，根据期次和借贷关系编号查询理财人还款记录列表：无  ============");
            }
            for (LnFinanceRepaySchedule lnFinanceRepaySchedule : list) {
                //修改理财人还款计划表
                LnFinanceRepaySchedule frsRecord = new LnFinanceRepaySchedule();
                frsRecord.setId(lnFinanceRepaySchedule.getId());
                frsRecord.setDoneTime(new Date());
                frsRecord.setStatus(Constants.FINANCE_REPAY_SATAE_REPAIED);
                frsRecord.setUpdateTime(new Date());
                lnFinanceRepayScheduleMapper.updateByPrimaryKeySelective(frsRecord);
                //修改借贷关系表
                LnLoanRelation lrRecord = new LnLoanRelation();
                lrRecord.setId(lnLoanRelation.getId());
                lrRecord.setLeftAmount(MoneyUtil.subtract(lnLoanRelation.getLeftAmount(), lnFinanceRepaySchedule.getPlanPrincipal()).doubleValue());
                if (lrRecord.getLeftAmount() == 0 && repaySerial == period) {
                    lrRecord.setStatus(Constants.LOAN_RELATION_STATUS_REPAID);
                }
                lnLoanRelationMapper.updateByPrimaryKeySelective(lrRecord);

                //新增借贷变动表
                LnLoanAmountChange loanAmountChange = new LnLoanAmountChange();
                loanAmountChange.setBeforeAmount(lnLoanRelation.getLeftAmount());
                loanAmountChange.setAfterAmount(lrRecord.getLeftAmount());
                loanAmountChange.setChangeAmount(lnFinanceRepaySchedule.getPlanPrincipal());
                loanAmountChange.setRelationId(lnLoanRelation.getId());
                loanAmountChange.setCreateTime(new Date());
                loanAmountChange.setUpdateTime(new Date());

            }
        }

    }

    @Override
    public List<Integer> getSuperUserSubAccountIdList() {
        List<Integer> subAccountIdList = bsSubAccountMapper.getSuperUserSubAccountIdList();
        return subAccountIdList;
    }

	@Override
	public List<LoanRelation4DepVO> confirmLoanRelation4LoanNewDep(
			final Integer loanId,final Integer lnUserId, final Integer lnSubAccountId, 
			final Double amount, final Integer loanTerm) {
		if (loanId == null || lnUserId == null || lnSubAccountId == null || amount == null || loanTerm == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        /**
         * 1、查询某日可借款的总额，判断该笔借款是否能够放款成功；
         * （通过对应的期限查询AUTH的可用余额总额和VIP理财人账户当前的可用余额总额）
         * 2、根据借款期限查询可匹配债权的投资VIP户（分日期，按金额大到小排序）
         * 3、匹配债权，AUTH转REG_D（调借款申请授权金额冻结接口）,保存债权关系记录表
         * 4、返回债权记录列表
         */
        long begin = System.currentTimeMillis();
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_LOAN_MATCH.getKey());

            return transactionTemplate.execute(new TransactionCallback<List<LoanRelation4DepVO>>() {
                @Override
                public List<LoanRelation4DepVO> doInTransaction(TransactionStatus status) {
                    List<LoanRelation4DepVO> list = new ArrayList<LoanRelation4DepVO>(); //返回值
                    LnLoanRelation initLoanRelation = new LnLoanRelation();
                    initLoanRelation.setLnSubAccountId(lnSubAccountId);
                    initLoanRelation.setLnUserId(lnUserId);
                    initLoanRelation.setLoanId(loanId);

                    logger.info("=========借款债权匹配开始：loanId=" + loanId + ",lnUserId=" + lnUserId
                            + ",lnSubAccountId=" + lnSubAccountId + ",amount=" + amount + ",loanTerm=" + loanTerm + "==============");
                    int day = 5;//站岗资金保留天数默认天数
                    BsSysConfig configDay = sysConfigService.findConfigByKey(Constants.DAY_4_WAIT_LOAN); //站岗资金保留天数
                    if (configDay != null) {
                        day = Integer.parseInt(configDay.getConfValue());
                    }

                    //获取VIP理财用户列表
                    List<Integer> superList = getSuperUserList();
                    //获取当前可匹配的总金额
                    Double normalAUTHAmount = authBalanaceQueryService.getNormalAuthBalance(loanTerm, day, superList,null);
                    Double normalSmallAuthAmount = authBalanaceQueryService.getSmallNormalAuthBalanceNew(loanTerm, day, superList);
                    Double superAUTHAmount = authBalanaceQueryService.getSuperAuthBalance(superList);
                    Double canLoanAmount = MoneyUtil.add(MoneyUtil.add(normalAUTHAmount, superAUTHAmount).doubleValue(), normalSmallAuthAmount).doubleValue();

                    if (canLoanAmount < amount) {
                        logger.info("=========【赞分期】借款债权匹配：可借金额=" + canLoanAmount + ",需借金额=" + amount + "。不进行匹配，直接借款失败");
                        throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
                        //return list;
                    }

                    Double borrowAmount = amount;//借款人借的金额
                    //优先匹配普通用户小于1000的剩余投资金额
                    logger.info("=========【赞分期】借款债权匹配：理财账户剩余小额可借金额=" + normalSmallAuthAmount + ",还需借金额=" + borrowAmount);

                    if (normalSmallAuthAmount > 0) {
                        logger.info("【赞分期】小额匹配开始=============================================================");
                        //普通理财人小额债权匹配
                        LoanRelationMatchReturnVO smallNormalMatch = normalSmallMatch4Dep(borrowAmount, normalSmallAuthAmount, day, initLoanRelation, superList, loanTerm, list);
                        //需要返回的借贷关系表
                        list = smallNormalMatch.getDepRelationList();
                        borrowAmount = smallNormalMatch.getBorrowAmount();
                        normalSmallAuthAmount = smallNormalMatch.getNormalAUTHAmount();
                        if (borrowAmount == 0) {
                            return list;
                        }

                    }
                    logger.info("=========借款债权匹配：理财账户剩余小额可借金额=" + normalSmallAuthAmount + ",还需借金额=" + borrowAmount + ",大额理财账户可借金额=" + normalAUTHAmount);
                    if (borrowAmount > 0 && normalAUTHAmount > 0) {
                    	logger.info("【赞分期】普通大额匹配开始=============================================================");
                        //普通理财人债权匹配
                        LoanRelationMatchReturnVO noramlMatch = normalMatchNew4Dep(borrowAmount, normalAUTHAmount, day, initLoanRelation, superList, loanTerm, list);
                        //需要返回的借贷关系表
                        list = noramlMatch.getDepRelationList();
                        borrowAmount = noramlMatch.getBorrowAmount();
                        normalAUTHAmount = noramlMatch.getNormalAUTHAmount();
                        if (borrowAmount == 0) {
                            return list;
                        }
                    }
                    logger.info("=========借款债权匹配：理财账户剩余大额可借金额=" + normalAUTHAmount + ",还需借金额=" + borrowAmount + ",VIP理财账户可借金额=" + superAUTHAmount);
                    //VIP理财人债权匹配
                    if (borrowAmount > 0 && superAUTHAmount > 0) {
                        List<Integer> superUserList = getSuperUserList();
                        if (!CollectionUtils.isEmpty(superUserList)) {
                        	logger.info("【赞分期】VIP匹配开始=============================================================");
                            Double limitAmount = getMatchLimitAmount();//获取 债权匹配时低于该金额的不进行债权承接

                            List<BsCanMatch4ZanSubAccountVO> canMatchList = bsSubAccountMapper.canMatch4ZanListDepStage(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN,
                                    Constants.PRODUCT_TYPE_AUTH, superUserList, null, null, "yes", limitAmount);
                            if (!CollectionUtils.isEmpty(canMatchList)) {
                                LoanRelationMatchReturnVO superMatchResult = superMatchDetailsNew4Dep(canMatchList, borrowAmount, initLoanRelation, superAUTHAmount, list, normalSmallAuthAmount);
                                list = superMatchResult.getDepRelationList();
                                borrowAmount = superMatchResult.getBorrowAmount();
                                superAUTHAmount = superMatchResult.getSuperAUTHAmount();
                            }
                        }
                    }


                    logger.info("=========借款债权匹配：VIP理财账户可借金额=" + superAUTHAmount + ",还需借金额=" + borrowAmount + ",普通理财账户可借金额=" + normalAUTHAmount);
                    


                    if (borrowAmount != 0) {
                        logger.info("========撮合结束，当前需借金额=" + borrowAmount + ",撮合失败==========");
                        throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
                    }

                    return list;
                }
            });
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_LOAN_MATCH.getKey());
            long end = System.currentTimeMillis();
            logger.info("======ZAN放款债权匹配[耗时：" + (end - begin) + "毫秒]======");
        }
	}
	
	
	/**
     * 存管--债权匹配时，小额普通理财账户匹配
     *
     * @param borrowAmount
     * @param normalAUTHAmount
     * @param day
     * @param initLoanRelation
     * @param superList
     * @param loanTerm
     * @param list
     * @return
     */
    protected LoanRelationMatchReturnVO normalSmallMatch4Dep(Double borrowAmount,
                                                         Double normalAUTHAmount, int day, LnLoanRelation initLoanRelation,
                                                         List<Integer> superList, Integer loanTerm, List<LoanRelation4DepVO> list) {
        Date now = new Date();
        LoanRelationMatchReturnVO returnVo = new LoanRelationMatchReturnVO();
        //判断借款金额和普通用户可借金额 是否大于0，当其中一个值等于0 时，结束普通用户的债权匹配
        Double limitAmount = getMatchLimitAmount();//债权匹配时低于该金额的不进行债权承接
        while (borrowAmount > 0 && normalAUTHAmount > 0) {

            Double beforMatchBorrowAmount = borrowAmount; // 循环前需借金额
            //循环查询匹配在站岗期内的普通用户的投资（AUTH）列表
            for (int beforeDay = day - 1; beforeDay >= 0; beforeDay--) {
                //确定可以匹配的普通理财用户站岗户列表的日期
                String interestBeginDate = DateUtil.formatYYYYMMDD(DateUtil.addDays(now, -beforeDay));
                //某一日普通理财用户站岗户列表
                List<BsCanMatch4ZanSubAccountVO> canMatchList = bsSubAccountMapper.canSmallMatch4ZanListDepStage(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN,
                        Constants.PRODUCT_TYPE_AUTH, superList, interestBeginDate, loanTerm, "no", limitAmount);
                if (!CollectionUtils.isEmpty(canMatchList)) {
                    //进行匹配普通理财用户站岗户列表
                    LoanRelationMatchReturnVO normalMatchResult = normalSmallMatchDetails4Dep(canMatchList, borrowAmount, normalAUTHAmount, list, initLoanRelation);
                    list = normalMatchResult.getDepRelationList();
                    borrowAmount = normalMatchResult.getBorrowAmount();
                    normalAUTHAmount = normalMatchResult.getNormalAUTHAmount();
                    if (borrowAmount == 0) break;
                }
            }

            //判断匹配前后金额是否发生变化，若无变化，跳出while
            if (beforMatchBorrowAmount == borrowAmount) break;
        }
        returnVo.setDepRelationList(list);
        returnVo.setBorrowAmount(borrowAmount);
        returnVo.setNormalAUTHAmount(normalAUTHAmount);
        return returnVo;
    }
    
    
    /**
     * 存管--债权匹配时，小额普通理财账户匹配详情
     *
     * @param canMatchList
     * @param borrowAmount
     * @param normalAUTHAmount
     * @param list
     * @param initLoanRelation
     * @return
     */
    protected LoanRelationMatchReturnVO normalSmallMatchDetails4Dep(
            List<BsCanMatch4ZanSubAccountVO> canMatchList, Double borrowAmount,
            Double normalAUTHAmount, List<LoanRelation4DepVO> list, LnLoanRelation initLoanRelation) {

        LoanRelationMatchReturnVO returnVo = new LoanRelationMatchReturnVO();
        Double limitAmount = getMatchLimitAmount();
        for (BsCanMatch4ZanSubAccountVO subAccountRecord : canMatchList) {
            Double thisAmount = borrowAmount; //此次撮合金额
            logger.info("此次撮合账户id："+subAccountRecord.getId());
            if (subAccountRecord.getAvailableBalance() < borrowAmount && MoneyUtil.subtract(borrowAmount,subAccountRecord.getAvailableBalance()).doubleValue() >= limitAmount) {
                thisAmount = subAccountRecord.getAvailableBalance();
            } else if (subAccountRecord.getAvailableBalance() < borrowAmount && MoneyUtil.subtract(borrowAmount,subAccountRecord.getAvailableBalance()).doubleValue() < limitAmount) {
                break;
            }
            logger.info("此次撮合金额："+thisAmount);
            if (thisAmount == 0) {
                break;
            }

            //冻结相应AUTH户的金额
            loanAccountService.chargeLoanFreeze(thisAmount, subAccountRecord.getId());

            //查询对应REG_D编号（目前auth和reg_d为一对一关系）
            BsSubAccountPair pair = getREG_D(subAccountRecord.getId());
            //新增借贷关系数据
            LnLoanRelation lnLoanRelationRecord = new LnLoanRelation();
            lnLoanRelationRecord.setCreateTime(new Date());
            lnLoanRelationRecord.setLnSubAccountId(initLoanRelation.getLnSubAccountId());
            lnLoanRelationRecord.setLnUserId(initLoanRelation.getLnUserId());
            lnLoanRelationRecord.setLoanId(initLoanRelation.getLoanId());
            lnLoanRelationRecord.setStatus(Constants.LOAN_RELATION_STATUS_PAYING);
            lnLoanRelationRecord.setUpdateTime(new Date());
            lnLoanRelationRecord.setFirstTerm(1);
            lnLoanRelationRecord.setBsSubAccountId(pair.getRegDAccountId());
            lnLoanRelationRecord.setBsUserId(subAccountRecord.getUserId());
            lnLoanRelationRecord.setInitAmount(thisAmount);
            lnLoanRelationRecord.setTotalAmount(thisAmount);
            lnLoanRelationRecord.setLeftAmount(thisAmount);
            lnLoanRelationMapper.insertSelective(lnLoanRelationRecord);
            
            LoanRelation4DepVO relation4DepRecord = new LoanRelation4DepVO();
            relation4DepRecord.setLnLoanRelation(lnLoanRelationRecord);
            relation4DepRecord.setHfUserId(subAccountRecord.getHfUserId());//恒丰用户id
            relation4DepRecord.setSelfAmount(thisAmount);
            //修改需借金额和普通用户可借金额
            borrowAmount = MoneyUtil.subtract(borrowAmount, thisAmount).doubleValue();
            logger.info("此次剩余借款金额："+borrowAmount+"--------------------------------------------------");
            normalAUTHAmount = MoneyUtil.subtract(normalAUTHAmount, thisAmount).doubleValue();
            logger.info("此次小额剩余金额："+normalAUTHAmount+"--------------------------------------------------");

            logger.info("==============借款-普通用户匹配信息：loanId:" + initLoanRelation.getLoanId() + ",matchAmount:" + thisAmount + ",leftBorrowAmount:" + borrowAmount + "==================");
            //返回列表增加数据
            list.add(relation4DepRecord);
        }
        returnVo.setDepRelationList(list);
        returnVo.setBorrowAmount(borrowAmount);
        returnVo.setNormalAUTHAmount(normalAUTHAmount);

        return returnVo;
    }
    
    
    /**
     * 存管--债权匹配时，VIP理财人匹配详情
     *
     * @param canMatchList
     * @param borrowAmount
     * @param initLoanRelation
     * @param superAUTHAmount
     * @param list
     * @return
     */
    protected LoanRelationMatchReturnVO superMatchDetailsNew4Dep(
            List<BsCanMatch4ZanSubAccountVO> canMatchList, Double borrowAmount,
            LnLoanRelation initLoanRelation, Double superAUTHAmount,
            List<LoanRelation4DepVO> list, Double smallAuthAmount) {
        LoanRelationMatchReturnVO returnVo = new LoanRelationMatchReturnVO();
        BsSysConfig configMax = sysConfigService.findConfigByKey(Constants.MATCH_VIPUSER_MATCH_MAX_AMOUNT);//VIP理财人匹配金额的最大值
        BsSysConfig configMin = sysConfigService.findConfigByKey(Constants.MATCH_VIPUSER_MATCH_MIN_AMOUNT);//VIP理财人匹配金额的最小值

        Integer vipMax = 3000;//VIP理财人匹配金额的最大值
        Integer vipMin = 1000;//VIP理财人匹配金额的最小值
        if (configMax != null) {
            vipMax = Integer.valueOf(configMax.getConfValue());
        }
        if (configMin != null) {
            vipMin = Integer.valueOf(configMin.getConfValue());
        }
        for (BsCanMatch4ZanSubAccountVO record : canMatchList) {
            Double VIPAmount = record.getAvailableBalance();
            //债权匹配时低于该金额的不进行债权承接
            Double limitAmount = getMatchLimitAmount();
            while (VIPAmount != 0 && borrowAmount != 0) {

                //如果VIP可借金额小于最小可接债权或者剩余借款小于最小可接债权，则不匹配
                if (VIPAmount < limitAmount || borrowAmount < limitAmount) {
                    break;
                }

                Double thisAmount;

                //如果小额金额列表已无可匹配金额
                if (smallAuthAmount == 0) {

                    if (borrowAmount <= 3000 && VIPAmount >= borrowAmount) {
                        //剩余借款金额不大于3000,vip可借金额不小于剩余借款金额,直接匹配
                        thisAmount = borrowAmount;

                    } else if (borrowAmount > 3000) {
                        //随机匹配金额且剩余借款不小于limitAmount,否则一直随机
                        thisAmount = getVIPMatchAmountNew(vipMax, vipMin, VIPAmount, borrowAmount, limitAmount);

                    } else {
                        //剩余借款金额不大于3000,vip可借金额小于剩余借款金额,且（剩余借款金额-vip可借金额）不小于最小可借债权，直接匹配金额为VIP可借金额
                        if (borrowAmount - VIPAmount >= limitAmount) {
                            thisAmount = VIPAmount;
                        } else {
                            break;
                        }
                    }
                }
                //如果小额金额列表有可用金额，说明剩余借款金额小于2000，
                else {
                    if (VIPAmount >= borrowAmount) {
                        //VIP可借金额不小于借款金额，直接匹配
                        thisAmount = borrowAmount;
                    } else {
                        //vip可借金额小于剩余借款金额,且（剩余借款金额-vip可借金额）不小于最小可借债权，直接匹配金额为VIP可借金额
                        if (borrowAmount - VIPAmount >= limitAmount) {
                            thisAmount = VIPAmount;
                        } else {
                            break;
                        }
                    }
                }
                if (thisAmount == 0) break;

                //冻结相应AUTH户的金额，减少borrowAmount，返回的list中新增数据
                loanAccountService.chargeLoanFreeze(thisAmount, record.getId());

                //查询对应REG_D编号（目前auth和reg_d为一对一关系）
                BsSubAccountPair pair = getREG_D(record.getId());
                //新增借贷关系数据
                LnLoanRelation lnLoanRelationRecord = new LnLoanRelation();
                lnLoanRelationRecord.setCreateTime(new Date());
                lnLoanRelationRecord.setLnSubAccountId(initLoanRelation.getLnSubAccountId());
                lnLoanRelationRecord.setLnUserId(initLoanRelation.getLnUserId());
                lnLoanRelationRecord.setLoanId(initLoanRelation.getLoanId());
                lnLoanRelationRecord.setStatus(Constants.LOAN_RELATION_STATUS_PAYING);
                lnLoanRelationRecord.setUpdateTime(new Date());
                lnLoanRelationRecord.setFirstTerm(1);
                lnLoanRelationRecord.setBsSubAccountId(pair.getRegDAccountId());
                lnLoanRelationRecord.setBsUserId(record.getUserId());
                lnLoanRelationRecord.setInitAmount(thisAmount);
                lnLoanRelationRecord.setTotalAmount(thisAmount);
                lnLoanRelationRecord.setLeftAmount(thisAmount);
                lnLoanRelationMapper.insertSelective(lnLoanRelationRecord);

                LoanRelation4DepVO relation4DepVO = new LoanRelation4DepVO();
                relation4DepVO.setLnLoanRelation(lnLoanRelationRecord);
                relation4DepVO.setHfUserId(record.getHfUserId());
                relation4DepVO.setSelfAmount(thisAmount);
                
                //修改需借金额和VIP理财用户可借金额
                borrowAmount = MoneyUtil.subtract(borrowAmount, thisAmount).doubleValue();
                VIPAmount = MoneyUtil.subtract(VIPAmount, thisAmount).doubleValue();
                superAUTHAmount = MoneyUtil.subtract(superAUTHAmount, thisAmount).doubleValue();
                logger.info("==============借款-VIP用户匹配信息：loanId:" + initLoanRelation.getLoanId() + ",matchAmount:" + thisAmount + ",leftBorrowAmount:" + borrowAmount + "==================");
                list.add(relation4DepVO);
                //判断借款金额为0或VIP理财用户可借金额为0，则无需继续匹配
                if (borrowAmount == 0 || VIPAmount == 0 || superAUTHAmount == 0) {
                    break;
                }
            }
        }
        returnVo.setDepRelationList(list);
        returnVo.setBorrowAmount(borrowAmount);
        returnVo.setSuperAUTHAmount(superAUTHAmount);
        return returnVo;
    }
    
    
    /**
     * 存管--债权匹配时，普通理财账户匹配
     *
     * @param borrowAmount
     * @param normalAUTHAmount
     * @param day
     * @param initLoanRelation
     * @param superList
     * @param loanTerm
     * @param list
     * @return
     */
    protected LoanRelationMatchReturnVO normalMatchNew4Dep(Double borrowAmount,
                                                       Double normalAUTHAmount, int day, LnLoanRelation initLoanRelation,
                                                       List<Integer> superList, Integer loanTerm, List<LoanRelation4DepVO> list) {
        Date now = new Date();
        LoanRelationMatchReturnVO returnVo = new LoanRelationMatchReturnVO();
        //判断借款金额和普通用户可借金额 是否大于0，当其中一个值等于0 时，结束普通用户的债权匹配
        Double limitAmount = getMatchLimitAmount();//债权匹配时低于该金额的不进行债权承接
        while (borrowAmount > 0 && normalAUTHAmount > 0) {

            Double beforMatchBorrowAmount = borrowAmount; // 循环前需借金额
            //循环查询匹配在站岗期内的普通用户的投资（AUTH）列表
            for (int beforeDay = day - 1; beforeDay >= 0; beforeDay--) {
                //确定可以匹配的普通理财用户站岗户列表的日期
                String interestBeginDate = DateUtil.formatYYYYMMDD(DateUtil.addDays(now, -beforeDay));
                //某一日普通理财用户站岗户列表
                List<BsCanMatch4ZanSubAccountVO> canMatchList = bsSubAccountMapper.canMatch4ZanListDepStage(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN,
                        Constants.PRODUCT_TYPE_AUTH, superList, interestBeginDate, loanTerm, "no", limitAmount);
                if (!CollectionUtils.isEmpty(canMatchList)) {
                    //进行匹配普通理财用户站岗户列表
                    LoanRelationMatchReturnVO normalMatchResult = normalMatchDetails4Dep(canMatchList, borrowAmount, normalAUTHAmount, list, initLoanRelation);
                    list = normalMatchResult.getDepRelationList();
                    borrowAmount = normalMatchResult.getBorrowAmount();
                    normalAUTHAmount = normalMatchResult.getNormalAUTHAmount();
                }
                if (normalAUTHAmount == 0) break;
            }

            //判断匹配前后金额是否发生变化，若无变化，跳出while
            if (beforMatchBorrowAmount == borrowAmount) break;
        }
        returnVo.setDepRelationList(list);
        returnVo.setBorrowAmount(borrowAmount);
        returnVo.setNormalAUTHAmount(normalAUTHAmount);
        return returnVo;
    }
    
    /**
     *  存管--债权匹配时，普通理财账户匹配详情
     *
     * @param canMatchList
     * @param borrowAmount
     * @param normalAUTHAmount
     * @param list
     * @param initLoanRelation
     * @return
     */
    protected LoanRelationMatchReturnVO normalMatchDetails4Dep(
            List<BsCanMatch4ZanSubAccountVO> canMatchList, Double borrowAmount,
            Double normalAUTHAmount, List<LoanRelation4DepVO> list, LnLoanRelation initLoanRelation) {
        LoanRelationMatchReturnVO returnVo = new LoanRelationMatchReturnVO();
        Double limitAmount = getMatchLimitAmount();//普通理财人债权匹配时低于该金额的不进行债权承接
        for (BsCanMatch4ZanSubAccountVO subAccountRecord : canMatchList) {

            logger.info("当前用户sub_account_id:"+subAccountRecord.getId());
            Double thisAmount=1d;
            Double availableBalance=subAccountRecord.getAvailableBalance();

            while (availableBalance>=limitAmount && thisAmount!=0 && borrowAmount>0) {
                thisAmount = 0d; //此次撮合金额
                Double baseAmount = borrowAmount; //基础数确认
                if (availableBalance < borrowAmount) {
                    baseAmount = availableBalance;
                }
                //判断基础数是否小于最低匹配值，若小于，则跳过该subAccount数据的匹配
                if (baseAmount < limitAmount) {
                    continue;
                }
                //获得匹配金额
                thisAmount = getThisAmountNew(baseAmount, subAccountRecord.getAgentId(), limitAmount, borrowAmount);
                //判断匹配金额是否等于0，0则跳过该subAccount数据的匹配
                if (thisAmount == 0) {
                    continue;
                }

                //冻结相应AUTH户的金额
                loanAccountService.chargeLoanFreeze(thisAmount, subAccountRecord.getId());

                //查询对应REG_D编号（目前auth和reg_d为一对一关系）
                BsSubAccountPair pair = getREG_D(subAccountRecord.getId());
                //新增借贷关系数据
                LnLoanRelation lnLoanRelationRecord = new LnLoanRelation();
                lnLoanRelationRecord.setCreateTime(new Date());
                lnLoanRelationRecord.setLnSubAccountId(initLoanRelation.getLnSubAccountId());
                lnLoanRelationRecord.setLnUserId(initLoanRelation.getLnUserId());
                lnLoanRelationRecord.setLoanId(initLoanRelation.getLoanId());
                lnLoanRelationRecord.setStatus(Constants.LOAN_RELATION_STATUS_PAYING);
                lnLoanRelationRecord.setUpdateTime(new Date());
                lnLoanRelationRecord.setFirstTerm(1);
                lnLoanRelationRecord.setBsSubAccountId(pair.getRegDAccountId());
                lnLoanRelationRecord.setBsUserId(subAccountRecord.getUserId());
                lnLoanRelationRecord.setInitAmount(thisAmount);
                lnLoanRelationRecord.setTotalAmount(thisAmount);
                lnLoanRelationRecord.setLeftAmount(thisAmount);
                lnLoanRelationMapper.insertSelective(lnLoanRelationRecord);

                LoanRelation4DepVO relation4DepVO = new LoanRelation4DepVO();
                relation4DepVO.setLnLoanRelation(lnLoanRelationRecord);
                relation4DepVO.setHfUserId(subAccountRecord.getHfUserId());
                relation4DepVO.setSelfAmount(thisAmount);
                
                //修改需借金额和普通用户可借金额
                borrowAmount = MoneyUtil.subtract(borrowAmount, thisAmount).doubleValue();

                normalAUTHAmount = MoneyUtil.subtract(normalAUTHAmount, thisAmount).doubleValue();

                logger.info("==============借款-普通用户匹配信息：loanId:" + initLoanRelation.getLoanId() + ",matchAmount:" + thisAmount + ",leftBorrowAmount:" + borrowAmount + "==================");
                //返回列表增加数据
                list.add(relation4DepVO);

                availableBalance = bsSubAccountMapper.selectSubAccountByIdForLock(subAccountRecord.getId()).getAvailableBalance();
            }
        }
        returnVo.setDepRelationList(list);
        returnVo.setBorrowAmount(borrowAmount);
        returnVo.setNormalAUTHAmount(normalAUTHAmount);

        return returnVo;
    }
    
	@Override
	public List<Integer> getSuperUserListBySymbol(String propertySymbol) {
		List<Integer> superUserList = null;
        BsSysConfig configUser = null;
        if (Constants.PROPERTY_SYMBOL_ZAN.equals(propertySymbol)) { 
        	configUser = sysConfigService.findConfigByKey(Constants.SUPER_FINANCE_USER_ID);//VIP理财人账户
        } else if (Constants.PROPERTY_SYMBOL_ZSD.equals(propertySymbol)) {
        	configUser = sysConfigService.findConfigByKey(VIPId4PartnerEnum.ZSD.getVipIdKey());//赞时贷VIP理财人用户编号
        }
        if (configUser != null) {
            superUserList = new ArrayList<Integer>();
            String[] userStr = configUser.getConfValue().split(",");
            for (String string : userStr) {
                superUserList.add(Integer.parseInt(string));
            }
        }
        return superUserList;
	}

}
