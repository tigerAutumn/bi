package com.pinting.business.accounting.loan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.pinting.business.accounting.finance.model.ProductType;
import com.pinting.business.accounting.finance.model.SubAccountCode;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.enums.PartnerLoanRangeEnum;
import com.pinting.business.accounting.loan.enums.VIPId4PartnerEnum;
import com.pinting.business.accounting.loan.model.LoanMatchInvestorRangeVO;
import com.pinting.business.accounting.loan.service.DepFixedLoanAccountService;
import com.pinting.business.accounting.loan.service.DepFixedMatchLoanerInvestorService;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.BsSubAccountPairMapper;
import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsSubAccountPair;
import com.pinting.business.model.BsSubAccountPairExample;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.LnLoanRelation;
import com.pinting.business.model.common.PagerModelRspDTO;
import com.pinting.business.model.vo.BsSubAccountVO4DepFixedMatch;
import com.pinting.business.model.vo.BsSubAccountVO4Match;
import com.pinting.business.model.vo.LoanRelation4DepVO;
import com.pinting.business.service.site.BsAgentViewConfigService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;

@Service
public class DepFixedMatchLoanerInvestorServiceImpl implements
        DepFixedMatchLoanerInvestorService {

    private final Logger logger = LoggerFactory.getLogger(DepFixedMatchLoanerInvestorServiceImpl.class);

    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private BsAgentViewConfigService bsAgentViewConfigService;
    @Autowired
    private LnLoanRelationMapper lnLoanRelationMapper;
    @Autowired
    private DepFixedLoanAccountService depFixedLoanAccountService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private BsSubAccountPairMapper bsSubAccountPairMapper;

    @Override
    public List<LoanRelation4DepVO> confirmLoanRelation4Loan(final Integer loanId, final Integer lnUserId, final Integer lnSubAccountId,
                                                             final Double amount, final Integer loanTerm, final PartnerEnum partnerEnum) {

        /**
         *
         *  1、获取VIP理财用户列表
         *  2、生成资产站岗户匹配范围配置和自由站岗户匹配范围配置（包含匹配范围区间值，匹配范围区间中是否存在数据标识）
         *  3、循环出借匹配开始
         *  4、根据匹配区间对可出借站岗户分三个redis队列（每个队列100条记录）：level_1[10000以上) level_2[1000，10000) level_3[20，1000)
         *  云贷站岗户队列，七贷站岗户队列，自由站岗户队列（优先匹配云贷/七贷站岗户，后匹配自由站岗户）
         *  借款金额三个对应区间loan_1[10000以上) loan_2[1000，10000) loan_3[0，1000)
         *  匹配规则：loan_1 匹配 level_1 》 level_2 》 level_3（匹配优先级）
         *  loan_2 匹配 level_2 》 level_1 》 level_3（匹配优先级）
         *  loan_3 匹配 level_3 》 level_2 》 level_1（匹配优先级）
         *  每笔借款，每个站岗户只能出借2笔资金，如果最终无匹配资金出借，则匹配已出借2笔以上的站岗户资金。
         *  5、匹配完成，判断是否为钱报用户，如果是钱报用户，最大出借金额为5000
         *  6、加业务锁（+ sub_account_id）
         *  7、一笔匹配结束，新增债权关系，冻结站岗户、红包户资金
         *  8、释放业务锁（+ sub_account_id）
         *  9、剩余借款金额减少实际匹配金额，如果剩余借款金额为0，本次借款匹配结束，如果剩余借款金额不为0，继续匹配。
         *  10、一笔债权匹配失败，回滚本次借款债权关系，解冻站岗户、红包户金额
         */
        if (loanId == null || lnUserId == null || amount == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        long begin = System.currentTimeMillis();

        List<LoanRelation4DepVO> retList = new ArrayList<>();

        try {
            String interestDate = DateUtil.formatYYYYMMDD(new Date());

            logger.info("日期:【" + DateUtil.parseDate(interestDate) + "】=========【" + partnerEnum.getName() + "】【存管固期，借款债权匹配】开始：loanId=" + loanId + ",lnUserId=" + lnUserId
                    + ",lnSubAccountId=" + lnSubAccountId + ",amount=" + amount + ",loanTerm=" + loanTerm + "==============");

            //还需要匹配的总额初始化
            Double needMatchAmount = amount;

            // 生成资产站岗户匹配范围配置和自由站岗户匹配范围配置（包含匹配范围区间值，匹配范围区间中是否存在数据标识）
            LoanMatchInvestorRangeVO loanMatchRange = new LoanMatchInvestorRangeVO(); // 自由站岗户匹配范围配置
            getLoanMatchRangeConfig(loanMatchRange);

            // 资产方匹配优先级排序
            List<PartnerEnum> partnerEnums = getPriorityPartnerEnums(partnerEnum, loanMatchRange.getPriorityUseFree());

            Set<Integer> loanOneSubAccountAuth = new HashSet<>(); // 出借过一次的站岗户ID列表
            Set<Integer> loanTwoSubAccountAuth = new HashSet<>(); // 出借过二次的站岗户ID列表

            List<BsSubAccountVO4DepFixedMatch> loanTwoSubAccountAuthPartner = new ArrayList<>(); // 出借过二次的资产端站岗户列表
            List<BsSubAccountVO4DepFixedMatch> loanTwoSubAccountAuthFree = new ArrayList<>(); // 出借过二次的自由站岗户列表

            boolean isNormalMatch = true; // 是否正常匹配规则

            // 循环出借匹配开始：开启债权循环匹配
            while (needMatchAmount > 0) {
                logger.info("开始匹配金额{}", needMatchAmount);

                BsSubAccountVO4DepFixedMatch bsSubAccount = null; // 匹配投资人站岗户

                if (needMatchAmount.compareTo(loanMatchRange.getOneLevelMatchAmount()) >= 0) {
                    // 借款金额在[10000, ****)区间, 匹配最优站岗户
                    // 老站岗户资金优先匹配
                    if (isNormalMatch) {
                        String[] levels = {PartnerLoanRangeEnum.ONE_LEVEL, PartnerLoanRangeEnum.TWO_LEVEL, PartnerLoanRangeEnum.THREE_LEVEL}; // 借款金额在[10000, ****)区间, 匹配站岗户顺序
                        for (PartnerEnum partner : partnerEnums) {
                            bsSubAccount = getMatchInvestorLoan(partner, loanMatchRange, loanTwoSubAccountAuth, levels);
                            if (bsSubAccount != null) {
                                break;
                            }
                        }
                    }
                } else if (needMatchAmount.compareTo(loanMatchRange.getTwoLevelMatchAmount()) >= 0 && needMatchAmount.compareTo(loanMatchRange.getOneLevelMatchAmount()) < 0) {
                    // 借款金额在[1000, 10000)区间, 匹配最优站岗户
                    // 老站岗户资金优先匹配
                    if (isNormalMatch) {
                        String[] levels = {PartnerLoanRangeEnum.TWO_LEVEL, PartnerLoanRangeEnum.ONE_LEVEL, PartnerLoanRangeEnum.THREE_LEVEL}; // 借款金额在[1000, 10000)区间, 匹配站岗户顺序
                        for (PartnerEnum partner : partnerEnums) {
                            bsSubAccount = getMatchInvestorLoan(partner, loanMatchRange, loanTwoSubAccountAuth, levels);
                            if (bsSubAccount != null) {
                                break;
                            }
                        }
                    }
                } else if (needMatchAmount > 0 && needMatchAmount.compareTo(loanMatchRange.getTwoLevelMatchAmount()) < 0) {
                    // 借款金额在[0, 1000)区间, 匹配最优站岗户
                    // 老站岗户资金优先匹配
                    if (isNormalMatch) {
                        String[] levels = {PartnerLoanRangeEnum.THREE_LEVEL, PartnerLoanRangeEnum.TWO_LEVEL, PartnerLoanRangeEnum.ONE_LEVEL}; // 借款金额在[20, 1000)区间, 匹配站岗户顺序
                        for (PartnerEnum partner : partnerEnums) {
                            bsSubAccount = getMatchInvestorLoan(partner, loanMatchRange, loanTwoSubAccountAuth, levels);
                            if (bsSubAccount != null) {
                                break;
                            }
                        }
                    }
                }
                if (bsSubAccount == null && CollectionUtils.isNotEmpty(loanTwoSubAccountAuth)) {
                    logger.info("正常匹配失败，执行最终匹配(匹配已出借过2笔以上的站岗户)");
                    isNormalMatch = false; // 标识进入最终匹配
                    if (CollectionUtils.isNotEmpty(loanTwoSubAccountAuthPartner)) {
                        bsSubAccount = getFinalMatchInvestorLoan(loanTwoSubAccountAuthPartner, loanMatchRange.getThreeLevelMatchAmount());
                    }
                    if (bsSubAccount == null && CollectionUtils.isNotEmpty(loanTwoSubAccountAuthFree)) {
                        bsSubAccount = getFinalMatchInvestorLoan(loanTwoSubAccountAuthFree, loanMatchRange.getThreeLevelMatchAmount());
                    }
                }
                if (bsSubAccount == null) {
                    throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
                }

                Double matchAmount = limitLoanMaxAmount(needMatchAmount, loanMatchRange, bsSubAccount); // 限制不同用户的放款匹配金额

                try {
                    jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEPFIXED_LOAN_MATCH_NEW.getKey() + bsSubAccount.getId());

                    BsSubAccountVO4Match bsSubAccountTemp = bsSubAccountMapper.depFixedAvailableBalance4MatchById(bsSubAccount.getId(), bsSubAccount.getRedSubAccountId());
                    if (bsSubAccountTemp == null) {
                        logger.info("站岗户状态已变更，站岗户：{},", bsSubAccount.getId());
                        continue;
                    }
                    bsSubAccount.setAvailableBalance(bsSubAccountTemp.getAvailableBalance());
                    bsSubAccount.setRedAvailableBalance(bsSubAccountTemp.getRedAvailableBalance());
                    Double availableBalance = MoneyUtil.add(bsSubAccountTemp.getAvailableBalance(), bsSubAccountTemp.getRedAvailableBalance()).doubleValue();
                    if (availableBalance.compareTo(loanMatchRange.getThreeLevelMatchAmount()) < 0) {
                        logger.info("站岗户余额不足，余额{},", availableBalance);
                        continue;
                    }
                    // 防并发控制，针对站岗户余额对比匹配金额，取更小的值.
                    matchAmount = getSmallerAmount(matchAmount, availableBalance).doubleValue();

                    final BsSubAccountVO4DepFixedMatch bsSubAccountMatch = bsSubAccount;
                    final Double bsSubMatchAmount = matchAmount;

                    LoanRelation4DepVO initLoanRelation = transactionTemplate.execute(new TransactionCallback<LoanRelation4DepVO>() {
                        @Override
                        public LoanRelation4DepVO doInTransaction(TransactionStatus status) {
                            logger.info("匹配成功，新增债权关系，冻结匹配金额{}，站岗户{}，站岗户金额{},红包户金额{}", bsSubMatchAmount, bsSubAccountMatch.getId(), bsSubAccountMatch.getAvailableBalance(), bsSubAccountMatch.getRedAvailableBalance());
                            //返回list添加值
                            LoanRelation4DepVO initLoanRelation = new LoanRelation4DepVO();
                            initLoanRelation.setHfUserId(bsSubAccountMatch.getHfUserId());

                            Double matchRedAmount = 0d; // 红包金额匹配初始化
                            Double matchAuthAmount = bsSubMatchAmount; // 实际的站岗户金额匹配初始化
                            //判断是否用到红包户
                            if (bsSubAccountMatch.getRedAvailableBalance() > 0) {
                                matchRedAmount = getSmallerAmount(bsSubAccountMatch.getRedAvailableBalance(), bsSubMatchAmount);
                                matchAuthAmount = MoneyUtil.subtract(bsSubMatchAmount, matchRedAmount).doubleValue() <= 0 ? 0d : MoneyUtil.subtract(bsSubMatchAmount, matchRedAmount).doubleValue();
                                initLoanRelation.setBsSubAccountId_red(bsSubAccountMatch.getRedSubAccountId());
                            }

                            //新增债权关系记录，状态为PAYING 借款付款中
                            LnLoanRelation loanRelation = addNewRelation(loanId, lnUserId, lnSubAccountId, bsSubAccountMatch.getId(), bsSubAccountMatch.getUserId(),
                                    bsSubMatchAmount, Constants.LOAN_RELATION_STATUS_PAYING, Constants.TRANS_MARK_NORMAL, matchRedAmount);

                            initLoanRelation.setLnLoanRelation(loanRelation);
                            initLoanRelation.setCouponAmount(matchRedAmount);//抵用金
                            initLoanRelation.setSelfAmount(matchAuthAmount);//自费金额

                            //冻结相应AUTH户的金额和RED户金额
                            depFixedLoanAccountService.chargeLoanFreeze(matchAuthAmount, bsSubAccountMatch.getId(), matchRedAmount, bsSubAccountMatch.getRedSubAccountId());
                            return initLoanRelation;
                        }
                    });

                    logger.info("结束剩余需匹配金额：{}，实际匹配金额：{}", needMatchAmount, matchAmount);
                    retList.add(initLoanRelation);

                    // 计算出借过2笔以上站岗户投资次数, 拆分出借过二次的资产端站岗户列表和出借过二次的自由站岗户列表
                    if (!loanOneSubAccountAuth.add(bsSubAccount.getId())) {
                        // 出借过2笔以上站岗户，在最终匹配(非正常匹配)中直接使用，对应的站岗余额和红包户余额 - 对应的匹配金额
                        bsSubAccount.setRedAvailableBalance(MoneyUtil.subtract(bsSubAccount.getRedAvailableBalance(), initLoanRelation.getCouponAmount()).doubleValue());
                        bsSubAccount.setAvailableBalance(MoneyUtil.subtract(bsSubAccount.getAvailableBalance(), initLoanRelation.getSelfAmount()).doubleValue());
                        if (loanTwoSubAccountAuth.add(bsSubAccount.getId())) {
                            if (Constants.PRODUCT_TYPE_AUTH_FREE.equals(bsSubAccount.getProductType())) {
                                loanTwoSubAccountAuthFree.add(bsSubAccount);
                            } else {
                                loanTwoSubAccountAuthPartner.add(bsSubAccount);
                            }
                        }
                    }
                } finally {
                    jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEPFIXED_LOAN_MATCH_NEW.getKey() + bsSubAccount.getId());
                }

                // 需匹配金额 = 原需匹配金额 - 该次匹配的金额
                needMatchAmount = MoneyUtil.subtract(needMatchAmount, matchAmount).doubleValue();
                // 每次匹配完成后判断还需匹配的金额以及待匹配的金额是否为0，即判断该笔是否匹配完成，是否该结束
                if (needMatchAmount == 0) {
                    break;
                }
            }

            if (needMatchAmount == 0) {
                return retList;
            } else {
                throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
            }
        } catch (Exception e) {
            logger.error("债权匹配失败", e);

            loanMatchAmountFail(retList); // 债权匹配失败处理，债权回退

            if (e instanceof PTMessageException) {
                PTMessageException ex = (PTMessageException) e;
                throw new PTMessageException(ex.getErrCode(), ex.getMessage());
            } else {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, e.getMessage());
            }
        } finally {
            long end = System.currentTimeMillis();
            logger.info("======放款债权匹配[耗时：" + (end - begin) + "毫秒]======");
        }
    }

    /**
     * 限制不同用户的放款匹配金额值
     * 1. 钱报系用户，最大放款额5000元
     * 2. 非钱报系用户，最大放款额10000元
     *
     * @param needMatchAmount
     * @param loanMatchRange
     * @param bsSubAccount
     * @return
     */
    private Double limitLoanMaxAmount(Double needMatchAmount, LoanMatchInvestorRangeVO loanMatchRange, BsSubAccountVO4DepFixedMatch bsSubAccount) {
        Double matchAmount = needMatchAmount; // 初始化待匹配金额
        if (bsSubAccount.getAgentId() != null && bsAgentViewConfigService.isQianbao(bsSubAccount.getAgentId())) {
            matchAmount = getSmallerAmount(matchAmount, loanMatchRange.getMatchMaxAmountQianBao()); // 钱报用户最大放款颗粒度
        } else {
            matchAmount = getSmallerAmount(matchAmount, loanMatchRange.getMatchMaxAmountNormal()); // 普通用户最大放款匹配颗粒度
        }
        return matchAmount;
    }

    /**
     * 执行最终匹配：
     * 1. 针对出借过2笔以上的站岗户拆分为资产端站岗户列表和自由站岗户列表，以可出借金额倒序排序
     * 2. 优先匹配资产站岗户，后匹配自由站岗户
     *
     * @param loanTwoSubAccountAuthPartner 出借站岗户列表
     * @param minMatchAmount               最小匹配金额
     * @return
     */
    private BsSubAccountVO4DepFixedMatch getFinalMatchInvestorLoan(List<BsSubAccountVO4DepFixedMatch> loanTwoSubAccountAuthPartner, Double minMatchAmount) {
        Collections.sort(loanTwoSubAccountAuthPartner, new Comparator<BsSubAccountVO4DepFixedMatch>() {
            @Override
            public int compare(BsSubAccountVO4DepFixedMatch o1, BsSubAccountVO4DepFixedMatch o2) {
                Double availableBalance1 = MoneyUtil.add(o1.getAvailableBalance(), o1.getRedAvailableBalance()).doubleValue();
                Double availableBalance2 = MoneyUtil.add(o2.getAvailableBalance(), o2.getRedAvailableBalance()).doubleValue();
                if (availableBalance1 > availableBalance2) {
                    return 1;
                }
                if (availableBalance1 == availableBalance2) {
                    return 0;
                }
                return -1;
            }
        });

        for (BsSubAccountVO4DepFixedMatch subAccountVO : loanTwoSubAccountAuthPartner) {
            Double availableBalance = MoneyUtil.add(subAccountVO.getAvailableBalance(), subAccountVO.getRedAvailableBalance()).doubleValue();
            if (availableBalance.compareTo(minMatchAmount) > 0) {
                return subAccountVO;
            }
        }
        return null;
    }

    /**
     * 匹配获取对应站岗户,根据借款区间不同，优先匹配不同区间的站岗户
     * 根据匹配区间进行匹配：level_1[10000以上) level_2[1000，10000) level_3[20，1000)
     * 借款金额区间loan_1[10000以上) loan_2[1000，10000) loan_3[20，1000)
     * 匹配规则：
     * loan_1 匹配 level_1 》 level_2 》 level_3（匹配优先级）
     * loan_2 匹配 level_2 》 level_1 》 level_3（匹配优先级）
     * loan_3 匹配 level_3 》 level_2 》 level_1（匹配优先级）
     *
     * @return
     */
    private BsSubAccountVO4DepFixedMatch getMatchInvestorLoan(PartnerEnum partnerEnum, LoanMatchInvestorRangeVO loanMatchRange, Set<Integer> loanTwoSubAccountAuth, String[] levels) {
        BsSubAccountVO4DepFixedMatch subAccountVO = null;
        try {
            PartnerLoanRangeEnum.RedisQueueEnum oneLevelAuthQueue = PartnerLoanRangeEnum.RedisQueueEnum.getEnumByCode(PartnerLoanRangeEnum.ONE_LEVEL, partnerEnum.getCode());
            PartnerLoanRangeEnum.RedisQueueEnum twoLevelAuthQueue = PartnerLoanRangeEnum.RedisQueueEnum.getEnumByCode(PartnerLoanRangeEnum.TWO_LEVEL, partnerEnum.getCode());
            PartnerLoanRangeEnum.RedisQueueEnum threeLevelAuthQueue = PartnerLoanRangeEnum.RedisQueueEnum.getEnumByCode(PartnerLoanRangeEnum.THREE_LEVEL, partnerEnum.getCode());
            String oneLevelAuthFlagStr = jsClientDaoSupport.getString(oneLevelAuthQueue.getFlag());
            Boolean oneLevelAuthFlag = StringUtils.isBlank(oneLevelAuthFlagStr) ? true : Boolean.valueOf(oneLevelAuthFlagStr);
            String twoLevelAuthFlagStr = jsClientDaoSupport.getString(twoLevelAuthQueue.getFlag());
            Boolean twoLevelAuthFlag = StringUtils.isBlank(twoLevelAuthFlagStr) ? true : Boolean.valueOf(twoLevelAuthFlagStr);
            String threeLevelAuthFlagStr = jsClientDaoSupport.getString(threeLevelAuthQueue.getFlag());
            Boolean threeLevelAuthFlag = StringUtils.isBlank(threeLevelAuthFlagStr) ? true : Boolean.valueOf(threeLevelAuthFlagStr);
            logger.info("资产方：{}，优先级别：{}，一级队列是否有数据标识：{}，二级队列是否有数据标识：{}，三级队列是否有数据标识：{}", partnerEnum.getCode(), levels, oneLevelAuthFlag, twoLevelAuthFlag, threeLevelAuthFlag);
            if (!oneLevelAuthFlag && !twoLevelAuthFlag && !threeLevelAuthFlag) {
                logger.info("三个级别的站岗户都无记录，退出资产端匹配");
                return null;
            }
            for (String level : levels) {
                subAccountVO = getMatchInvestorLoan(partnerEnum, loanTwoSubAccountAuth, level, oneLevelAuthFlag, twoLevelAuthFlag, threeLevelAuthFlag);
                if (subAccountVO != null) {
                    return subAccountVO;
                }
            }
            if (subAccountVO == null) {
                // 三个区间redis队列都已消耗完毕,初始化区间队列
                Map<String, Boolean> mapInfo = initAuthRedisQueue(partnerEnum, loanMatchRange, oneLevelAuthFlag, twoLevelAuthFlag, threeLevelAuthFlag);
                Boolean oneLevelAuthExist = mapInfo.get(oneLevelAuthQueue.getFlag());
                Boolean twoLevelAuthExist = mapInfo.get(twoLevelAuthQueue.getFlag());
                Boolean threeLevelAuthExist = mapInfo.get(threeLevelAuthQueue.getFlag());
                if (oneLevelAuthExist || twoLevelAuthExist || threeLevelAuthExist) {
                    // 初始化队列成功，继续匹配
                    for (String level : levels) {
                        subAccountVO = getMatchInvestorLoan(partnerEnum, loanTwoSubAccountAuth, level, oneLevelAuthExist, twoLevelAuthExist, threeLevelAuthExist);
                        if (subAccountVO != null) {
                            return subAccountVO;
                        }
                    }
                }
            }
            return subAccountVO;
        } catch (Exception e) {
            logger.error("匹配站岗户失败", e);
            return subAccountVO;
        }
    }


    /**
     * redis中获取匹配获取对应站岗户
     *
     * @param partnerEnum           资产方
     * @param loanTwoSubAccountAuth 已匹配出借过2次的站岗户
     * @param level                 匹配区间级别
     * @param oneLevelAuthFlag      一级队列标识，库中是否存在记录
     * @param twoLevelAuthFlag      二级队列标识，库中是否存在记录
     * @param threeLevelAuthFlag    三级队列标识，库中是否存在记录
     * @return
     */
    private BsSubAccountVO4DepFixedMatch getMatchInvestorLoan(PartnerEnum partnerEnum, Set<Integer> loanTwoSubAccountAuth, String level,
                                                              Boolean oneLevelAuthFlag, Boolean twoLevelAuthFlag, Boolean threeLevelAuthFlag) {
        switch (level) {
            case PartnerLoanRangeEnum.ONE_LEVEL:
                if (oneLevelAuthFlag) {
                    PartnerLoanRangeEnum.RedisQueueEnum oneLevelAuthQueue = PartnerLoanRangeEnum.RedisQueueEnum.getEnumByCode(PartnerLoanRangeEnum.ONE_LEVEL, partnerEnum.getCode());
                    BsSubAccountVO4DepFixedMatch subAccountOneLevel = getSubAccountAuthByRedisQueue(oneLevelAuthQueue.getKey());
                    if (subAccountOneLevel != null && loanTwoSubAccountAuth.contains(subAccountOneLevel.getId())) {
                        logger.info("本次借款，站岗户：{}已出借2笔", subAccountOneLevel.getId());
                        return getMatchInvestorLoan(partnerEnum, loanTwoSubAccountAuth, level, oneLevelAuthFlag, twoLevelAuthFlag, threeLevelAuthFlag);
                    } else {
                        return subAccountOneLevel;
                    }
                }
                break;
            case PartnerLoanRangeEnum.TWO_LEVEL:
                if (twoLevelAuthFlag) {
                    PartnerLoanRangeEnum.RedisQueueEnum twoLevelAuthQueue = PartnerLoanRangeEnum.RedisQueueEnum.getEnumByCode(PartnerLoanRangeEnum.TWO_LEVEL, partnerEnum.getCode());
                    BsSubAccountVO4DepFixedMatch subAccountTwoLevel = getSubAccountAuthByRedisQueue(twoLevelAuthQueue.getKey());
                    if (subAccountTwoLevel != null && loanTwoSubAccountAuth.contains(subAccountTwoLevel.getId())) {
                        logger.info("本次借款，站岗户：{}已出借2笔", subAccountTwoLevel.getId());
                        return getMatchInvestorLoan(partnerEnum, loanTwoSubAccountAuth, level, oneLevelAuthFlag, twoLevelAuthFlag, threeLevelAuthFlag);
                    } else {
                        return subAccountTwoLevel;
                    }
                }
                break;
            case PartnerLoanRangeEnum.THREE_LEVEL:
                if (threeLevelAuthFlag) {
                    PartnerLoanRangeEnum.RedisQueueEnum threeLevelAuthQueue = PartnerLoanRangeEnum.RedisQueueEnum.getEnumByCode(PartnerLoanRangeEnum.THREE_LEVEL, partnerEnum.getCode());
                    BsSubAccountVO4DepFixedMatch subAccountThreeLevel = getSubAccountAuthByRedisQueue(threeLevelAuthQueue.getKey());
                    if (subAccountThreeLevel != null && loanTwoSubAccountAuth.contains(subAccountThreeLevel.getId())) {
                        logger.info("本次借款，站岗户：{}已出借2笔", subAccountThreeLevel.getId());
                        return getMatchInvestorLoan(partnerEnum, loanTwoSubAccountAuth, level, oneLevelAuthFlag, twoLevelAuthFlag, threeLevelAuthFlag);
                    } else {
                        return subAccountThreeLevel;
                    }
                }
                break;
            default:
                return null;
        }
        return null;
    }


    /**
     * 获取更小值
     * canMatchAmount<=leftAmount,返回canMatchAmount，否则，返回leftAmount
     *
     * @param canMatchAmount
     * @param leftAmount
     * @return
     */
    private Double getSmallerAmount(Double canMatchAmount, Double leftAmount) {
        Double matchAmount = 0.0;
        if (canMatchAmount.compareTo(leftAmount) <= 0) {
            matchAmount = canMatchAmount;
        } else {
            matchAmount = leftAmount;
        }
        return matchAmount;
    }

    /**
     * 新增借贷关系数据
     *
     * @param loanId
     * @param lnUserId
     * @param lnSubAccountId
     * @param bsSubAccountId
     * @param bsUserId
     * @param matchAmount
     * @param discountAmount
     */
    private LnLoanRelation addNewRelation(Integer loanId, Integer lnUserId,
                                          Integer lnSubAccountId, Integer bsSubAccountId,
                                          Integer bsUserId, Double matchAmount, String status,
                                          String transMarkTrans, Double discountAmount) {
        LnLoanRelation lnLoanRelationRecord = new LnLoanRelation();
        lnLoanRelationRecord.setCreateTime(new Date());
        lnLoanRelationRecord.setLnSubAccountId(lnSubAccountId);
        lnLoanRelationRecord.setLnUserId(lnUserId);
        lnLoanRelationRecord.setLoanId(loanId);
        lnLoanRelationRecord.setStatus(status);
        lnLoanRelationRecord.setUpdateTime(new Date());
        lnLoanRelationRecord.setFirstTerm(1);
        lnLoanRelationRecord.setBsSubAccountId(bsSubAccountId);
        lnLoanRelationRecord.setBsUserId(bsUserId);
        lnLoanRelationRecord.setInitAmount(matchAmount);
        lnLoanRelationRecord.setTotalAmount(matchAmount);
        lnLoanRelationRecord.setLeftAmount(matchAmount);
        lnLoanRelationRecord.setDiscountAmount(discountAmount);
        lnLoanRelationRecord.setTransMark(transMarkTrans);
        lnLoanRelationMapper.insertSelective(lnLoanRelationRecord);
        return lnLoanRelationRecord;
    }

    /**
     * 查询VIP理财人账户
     *
     * @param configKeys
     * @return
     */
    public List<Integer> getDepVIPUserList(String... configKeys) {
        List<Integer> VIPUserList = new ArrayList<>();
        for (String configKey : configKeys) {
            BsSysConfig configUser = sysConfigService.findConfigByKey(configKey);//VIP理财人账户
            if (configUser != null) {
                String[] userStr = configUser.getConfValue().split(",");
                for (String string : userStr) {
                    VIPUserList.add(Integer.parseInt(string));
                }
            }
        }
        return VIPUserList;
    }

    /**
     * 债权匹配失败，解冻已匹配的资金
     *
     * @param loanRelationList
     */
    private void loanMatchAmountFail(List<LoanRelation4DepVO> loanRelationList) {
        /**
         * 1、ln_loan_relation表状态修改
         * 2、冻结解冻
         */
        for (LoanRelation4DepVO loanRelation4DepVO : loanRelationList) {
            BsSubAccountPairExample pairExample = new BsSubAccountPairExample();
            pairExample.createCriteria().andAuthAccountIdEqualTo(loanRelation4DepVO.getLnLoanRelation().getBsSubAccountId());
            List<BsSubAccountPair> pairs = bsSubAccountPairMapper.selectByExample(pairExample);
            if (CollectionUtils.isEmpty(pairs)) {
                throw new PTMessageException(PTMessageEnum.ACCOUNT_NOT_FOUND);
            }
            depFixedLoanAccountService.chargeLoanUnFreeze(loanRelation4DepVO.getSelfAmount(),
                    loanRelation4DepVO.getLnLoanRelation().getBsSubAccountId(),
                    loanRelation4DepVO.getCouponAmount(),
                    pairs.get(0).getRedAccountId());

            LnLoanRelation lnLoanRelation = lnLoanRelationMapper.selectByPrimaryKey(loanRelation4DepVO.getLnLoanRelation().getId());

            LnLoanRelation lnLoanRelationTemp = new LnLoanRelation();
            lnLoanRelationTemp.setId(lnLoanRelation.getId());
            lnLoanRelationTemp.setStatus(Constants.LOAN_RELATION_STATUS_FAIL);
            lnLoanRelationTemp.setUpdateTime(new Date());
            lnLoanRelationMapper.updateByPrimaryKeySelective(lnLoanRelationTemp);
        }
    }

    /**
     * 借款债权匹配金额范围，配置可控
     *
     * @param loanMatchRanges
     */
    private void getLoanMatchRangeConfig(LoanMatchInvestorRangeVO... loanMatchRanges) {
        Double oneLevelMatchAmount = 10000d;
        Double twoLevelMatchAmount = 1000d;
        Double threeLevelMatchAmount = 20d;
        Integer redisFlagExpTime = 5 * 60;
        Integer redisLevelQueueCount = 100;
        Double matchMaxAmountNormal = 10000d;
        Double matchMaxAmountQianBao = 5000d;
        String isPriorityUseFree = Boolean.FALSE.toString();

        List<BsSysConfig> configs = sysConfigService.findConfigByKeys(Constants.YUN_DAI_SELF_MATH_MIN_BALANCE, Constants.MATCH_MIDDLE_BALANCE,
                Constants.MATCH_MAX_BALANCE, Constants.MATCH_REDIS_FLAG_EXPTIME, Constants.MATCH_REDIS_LEVEL_QUEUE_COUNT,
                Constants.MATCH_MAX_AMOUNT_NORMAL, Constants.MATCH_MAX_AMOUNT_QIANBAO, Constants.MATCH_IS_PRIORITY_USE_FREE);
        for (BsSysConfig bsSysConfig : configs) {
            if (Constants.YUN_DAI_SELF_MATH_MIN_BALANCE.equals(bsSysConfig.getConfKey())) {
                threeLevelMatchAmount = Double.valueOf(bsSysConfig.getConfValue());
            }
            if (Constants.MATCH_MIDDLE_BALANCE.equals(bsSysConfig.getConfKey())) {
                twoLevelMatchAmount = Double.valueOf(bsSysConfig.getConfValue());
            }
            if (Constants.MATCH_MAX_BALANCE.equals(bsSysConfig.getConfKey())) {
                oneLevelMatchAmount = Double.valueOf(bsSysConfig.getConfValue());
            }
            if (Constants.MATCH_REDIS_FLAG_EXPTIME.equals(bsSysConfig.getConfKey())) {
                redisFlagExpTime = Integer.valueOf(bsSysConfig.getConfValue());
            }
            if (Constants.MATCH_REDIS_LEVEL_QUEUE_COUNT.equals(bsSysConfig.getConfKey())) {
                redisLevelQueueCount = Integer.valueOf(bsSysConfig.getConfValue());
            }
            if (Constants.MATCH_MAX_AMOUNT_NORMAL.equals(bsSysConfig.getConfKey())) {
                matchMaxAmountNormal = Double.valueOf(bsSysConfig.getConfValue());
            }
            if (Constants.MATCH_MAX_AMOUNT_QIANBAO.equals(bsSysConfig.getConfKey())) {
                matchMaxAmountQianBao = Double.valueOf(bsSysConfig.getConfValue());
            }
            if (Constants.MATCH_IS_PRIORITY_USE_FREE.equals(bsSysConfig.getConfKey())) {
                isPriorityUseFree = bsSysConfig.getConfValue();
            }
        }

        for (LoanMatchInvestorRangeVO loanMatchRange : loanMatchRanges) {
            loanMatchRange.setOneLevelMatchAmount(oneLevelMatchAmount);
            loanMatchRange.setTwoLevelMatchAmount(twoLevelMatchAmount);
            loanMatchRange.setThreeLevelMatchAmount(threeLevelMatchAmount);
            loanMatchRange.setRedisFlagExpTime(redisFlagExpTime);
            loanMatchRange.setRedisLevelQueueCount(redisLevelQueueCount);
            loanMatchRange.setMatchMaxAmountNormal(matchMaxAmountNormal);
            loanMatchRange.setMatchMaxAmountQianBao(matchMaxAmountQianBao);
            loanMatchRange.setPriorityUseFree(Boolean.valueOf(isPriorityUseFree));
        }
    }

    /**
     * 初始化站岗户队列，加业务锁，控制并发
     *
     * @param partnerEnum 资产方
     * @return
     */
    private Map<String, Boolean> initAuthRedisQueue(PartnerEnum partnerEnum, LoanMatchInvestorRangeVO loanMatchRange, Boolean oneLevelAuthFlag, Boolean twoLevelAuthFlag, Boolean threeLevelAuthFlag) {
        logger.info("区间redis队列初始化开始，资产端：{}", partnerEnum.getCode());
        Map<String, Boolean> mapInfo = new HashMap<>();
        PartnerLoanRangeEnum.RedisQueueEnum oneLevelAuthQueue = PartnerLoanRangeEnum.RedisQueueEnum.getEnumByCode(PartnerLoanRangeEnum.ONE_LEVEL, partnerEnum.getCode());
        PartnerLoanRangeEnum.RedisQueueEnum twoLevelAuthQueue = PartnerLoanRangeEnum.RedisQueueEnum.getEnumByCode(PartnerLoanRangeEnum.TWO_LEVEL, partnerEnum.getCode());
        PartnerLoanRangeEnum.RedisQueueEnum threeLevelAuthQueue = PartnerLoanRangeEnum.RedisQueueEnum.getEnumByCode(PartnerLoanRangeEnum.THREE_LEVEL, partnerEnum.getCode());
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEPFIXED_LOAN_MATCH_NEW.getKey() + partnerEnum.getCode());
            Long oneLevelAuthQueueLen = jsClientDaoSupport.llen(oneLevelAuthQueue.getKey());
            boolean oneLevelAuthExist = oneLevelAuthQueueLen != null && oneLevelAuthQueueLen > 0; // 一级队列标识，是否存在记录
            Long twoLevelAuthQueueLen = jsClientDaoSupport.llen(twoLevelAuthQueue.getKey());
            boolean twoLevelAuthExist = twoLevelAuthQueueLen != null && twoLevelAuthQueueLen > 0; // 二级队列标识，是否存在记录
            Long threeLevelAuthQueueLen = jsClientDaoSupport.llen(threeLevelAuthQueue.getKey());
            boolean threeLevelAuthExist = threeLevelAuthQueueLen != null && threeLevelAuthQueueLen > 0; // 三级队列标识，是否存在记录
            if (oneLevelAuthExist || twoLevelAuthExist || threeLevelAuthExist) {
                // 已初始化成功后，直接返回
                mapInfo.put(oneLevelAuthQueue.getFlag(), oneLevelAuthExist);
                mapInfo.put(twoLevelAuthQueue.getFlag(), twoLevelAuthExist);
                mapInfo.put(threeLevelAuthQueue.getFlag(), threeLevelAuthExist);
                logger.info("区间redis队列已初始化，支持继续匹配：{}，一级队列是否有数据标识：{}，二级队列是否有数据标识：{}，三级队列是否有数据标识：{}", oneLevelAuthExist || twoLevelAuthExist || threeLevelAuthExist, oneLevelAuthExist, twoLevelAuthExist, threeLevelAuthExist);
                return mapInfo;
            }

            // 资产方站岗户
            ProductType productTypeMap = SubAccountCode.productTypeMap.get(partnerEnum);
            String productType = productTypeMap.getAuthCode();
            String redProType = productTypeMap.getRedCode();

            // 获取VIP理财用户列表
            List<Integer> VIPList = getDepVIPUserList(VIPId4PartnerEnum.getEnumByCode(partnerEnum.getCode()).getVipIdKey());

            // 第一级区间金额使用后，会掉到第二区间或第三区间；因第一级区间有记录时，要查询第二级与第三级区间
            // 第二级区间金额使用后，会掉落到第三级区间；因第二级区间有记录时，要查询第三级区间
            PagerModelRspDTO<BsSubAccountVO4DepFixedMatch> modelRspDTO = getMatchInvestor(VIPList, productType, redProType, loanMatchRange,
                    oneLevelAuthFlag, oneLevelAuthFlag || twoLevelAuthFlag, oneLevelAuthFlag || twoLevelAuthFlag || threeLevelAuthFlag);
            if (CollectionUtils.isNotEmpty(modelRspDTO.getList())) {
                for (BsSubAccountVO4DepFixedMatch subAccountVO : modelRspDTO.getList()) {
                    Double availableBalance = MoneyUtil.add(subAccountVO.getAvailableBalance(), subAccountVO.getRedAvailableBalance()).doubleValue();
                    if (availableBalance.compareTo(loanMatchRange.getOneLevelMatchAmount()) >= 0) {
                        oneLevelAuthExist = true;
                        jsClientDaoSupport.rpush(oneLevelAuthQueue.getKey(), JSONObject.toJSONString(subAccountVO));
                    }
                    if (availableBalance.compareTo(loanMatchRange.getOneLevelMatchAmount()) < 0 && availableBalance.compareTo(loanMatchRange.getTwoLevelMatchAmount()) >= 0) {
                        twoLevelAuthExist = true;
                        jsClientDaoSupport.rpush(twoLevelAuthQueue.getKey(), JSONObject.toJSONString(subAccountVO));
                    }
                    if (availableBalance.compareTo(loanMatchRange.getTwoLevelMatchAmount()) < 0 && availableBalance.compareTo(loanMatchRange.getThreeLevelMatchAmount()) >= 0) {
                        threeLevelAuthExist = true;
                        jsClientDaoSupport.rpush(threeLevelAuthQueue.getKey(), JSONObject.toJSONString(subAccountVO));
                    }
                }
            }
            // 标识对应区间队列是否可继续初始化
            if (oneLevelAuthExist) {
                jsClientDaoSupport.setString(String.valueOf(oneLevelAuthExist), oneLevelAuthQueue.getFlag());
            } else {
                jsClientDaoSupport.setString(String.valueOf(oneLevelAuthExist), oneLevelAuthQueue.getFlag(), loanMatchRange.getRedisFlagExpTime());
            }
            if (twoLevelAuthExist) {
                jsClientDaoSupport.setString(String.valueOf(twoLevelAuthExist), twoLevelAuthQueue.getFlag());
            } else {
                jsClientDaoSupport.setString(String.valueOf(twoLevelAuthExist), twoLevelAuthQueue.getFlag(), loanMatchRange.getRedisFlagExpTime());
            }
            if (threeLevelAuthExist) {
                jsClientDaoSupport.setString(String.valueOf(threeLevelAuthExist), threeLevelAuthQueue.getFlag());
            } else {
                jsClientDaoSupport.setString(String.valueOf(threeLevelAuthExist), threeLevelAuthQueue.getFlag(), loanMatchRange.getRedisFlagExpTime());
            }

            mapInfo.put(oneLevelAuthQueue.getFlag(), oneLevelAuthExist);
            mapInfo.put(twoLevelAuthQueue.getFlag(), twoLevelAuthExist);
            mapInfo.put(threeLevelAuthQueue.getFlag(), threeLevelAuthExist);
            logger.info("区间redis队列初始化结束，支持继续匹配：{}，一级队列是否有数据标识：{}，二级队列是否有数据标识：{}，三级队列是否有数据标识：{}", oneLevelAuthExist || twoLevelAuthExist || threeLevelAuthExist, oneLevelAuthExist, twoLevelAuthExist, threeLevelAuthExist);
            return mapInfo;
        } catch (Exception e) {
            logger.error("区间redis队列初始化失败，不支持继续匹配", e);
            mapInfo.put(oneLevelAuthQueue.getFlag(), false);
            mapInfo.put(twoLevelAuthQueue.getFlag(), false);
            mapInfo.put(threeLevelAuthQueue.getFlag(), false);
            return mapInfo;
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEPFIXED_LOAN_MATCH_NEW.getKey() + partnerEnum.getCode());
        }
    }

    /**
     * 查询对应区间可出借站岗户用户数据
     *
     * @param vipUserIdList 过滤的VIP理财人
     * @param productType   站岗户
     * @param redProType    红包户
     * @return
     */
    private PagerModelRspDTO<BsSubAccountVO4DepFixedMatch> getMatchInvestor(List<Integer> vipUserIdList, String productType, String redProType, LoanMatchInvestorRangeVO loanMatchRange,
                                                                            Boolean oneLevelAuthFlag, Boolean twoLevelAuthFlag, Boolean threeLevelAuthFlag) {
        logger.info("区间redis队列初始化开始，资产端：{}，是否初始化一级队列；{}，是否初始化二级队列：{}，是否初始化三级队列：{}", productType, oneLevelAuthFlag, twoLevelAuthFlag, threeLevelAuthFlag);
        List<BsSubAccountVO4DepFixedMatch> depFixedWait4MatchList = bsSubAccountMapper.depFixedNormalWait4Match(vipUserIdList,
                productType, redProType, loanMatchRange, oneLevelAuthFlag, twoLevelAuthFlag, threeLevelAuthFlag);
        PagerModelRspDTO modelRspDTO = new PagerModelRspDTO(depFixedWait4MatchList);
        logger.info("查询站岗户数量：{}", modelRspDTO.getTotalRow());
        return modelRspDTO;
    }

    /**
     * 根据资产方区间队列key读取对应站岗户
     *
     * @param redisKey
     * @return
     */
    private BsSubAccountVO4DepFixedMatch getSubAccountAuthByRedisQueue(String redisKey) {
        Long len = jsClientDaoSupport.llen(redisKey);
        if (len != null && len > 0) {
            String queueString = jsClientDaoSupport.lpop(redisKey);
            BsSubAccountVO4DepFixedMatch subAccountVO = JSONObject.parseObject(queueString, BsSubAccountVO4DepFixedMatch.class);
            return subAccountVO;
        }
        return null;
    }

    /**
     * 债权匹配优先级设置
     * @param partnerEnum
     * @param isPriorityUseFree
     * @return
     */
    private List<PartnerEnum> getPriorityPartnerEnums(PartnerEnum partnerEnum, Boolean isPriorityUseFree) {
        List<PartnerEnum> partnerEnums = new ArrayList<>();
        if (isPriorityUseFree) {
            partnerEnums.add(PartnerEnum.FREE);
            partnerEnums.add(partnerEnum);
        } else {
            partnerEnums.add(partnerEnum);
            partnerEnums.add(PartnerEnum.FREE);
        }
        return partnerEnums;
    }
}
