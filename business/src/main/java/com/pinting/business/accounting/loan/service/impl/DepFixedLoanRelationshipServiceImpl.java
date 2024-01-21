package com.pinting.business.accounting.loan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.pinting.business.accounting.finance.model.ProductType;
import com.pinting.business.accounting.finance.model.SubAccountCode;
import com.pinting.business.accounting.finance.model.TransferAccountInfo;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.enums.VIPId4PartnerEnum;
import com.pinting.business.accounting.loan.model.LoanRelation4TransferVO;
import com.pinting.business.accounting.loan.service.DepFixedLoanAccountService;
import com.pinting.business.accounting.loan.service.DepFixedLoanRelationshipService;
import com.pinting.business.accounting.loan.service.DepFixedRepayAccountService;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.accounting.loan.util.YunInstalmentRepayPlanUtil;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.AverageCapitalPlusInterestVO;
import com.pinting.business.model.vo.BsSubAccountVO4DepFixedMatch;
import com.pinting.business.model.vo.LoanRelation4DepVO;
import com.pinting.business.model.vo.LoanRelationMatchReturnVO;
import com.pinting.business.model.vo.YunRepayPlanVO;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.service.site.BsAgentViewConfigService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_TransferDebt;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_TransferDebt;
import com.pinting.gateway.hessian.message.hfbank.model.TransferDebtReqCommission;
import com.pinting.gateway.out.service.HfbankTransportService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DepFixedLoanRelationshipServiceImpl implements
		DepFixedLoanRelationshipService {

	private final Logger logger = LoggerFactory.getLogger(DepFixedLoanRelationshipServiceImpl.class);

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
    private HfbankTransportService hfbankTransportService;
	@Autowired
	private LnFinanceRepayScheduleMapper lnFinanceRepayScheduleMapper;
	@Autowired
	private BsSubAccountPairMapper bsSubAccountPairMapper;
	@Autowired
	private LnDepositionTargetMapper lnDepositionTargetMapper;
	@Autowired
	private LnLoanAmountChangeMapper lnLoanAmountChangeMapper;
	@Autowired
	private LnCreditTransferMapper lnCreditTransferMapper;
	@Autowired
	private LnRepayMapper lnRepayMapper;
	@Autowired
	private SpecialJnlService specialJnlService;
	@Autowired
	private DepFixedRepayAccountService depFixedRepayAccountService;
	@Autowired
	private LoanRelationshipService loanRelationshipService;
	@Autowired
	private LnBillBizInfoMapper billBizInfoMapper;
	@Autowired
	private AlgorithmService algorithmService;

	@Override
	public List<LoanRelation4DepVO> confirmLoanRelation4Loan(final Integer loanId,
			final Integer lnUserId, final Integer lnSubAccountId, final Double amount,
			final Integer loanTerm, final PartnerEnum partnerEnum) {


		/**
		 * 只对接普通理财人的债权
		 * 1、根据站岗可用金额查询可以匹配债权的列表：起息日在今日及之前，起息日在今日之前的优先，日期相同金额大的优先；
		 * 2、非钱报用户 0<剩余可借金额<=50000，随机值区域[0,5000]--其中80%(0,3000],20%(3000,5000]
		 * 		剩余可借金额>50000，随机值区域(5000,10000]--其中80%(5000,8000],20%(8000,10000]
		 * 3、钱报用户沿用钱报随机数机制
		 * 		剩余可借金额<=3000,随机值=剩余可借金额
		 * 		剩余可借金额>3000，随机值区域(3000,5000]--其中75%(3000,4000],25%(4000,5000]
		 * 4、调用用户账户冻结
		 */
		if(loanId ==null || lnUserId == null || amount ==null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		long begin = System.currentTimeMillis();
		try {
			jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEPFIXED_LOAN_MATCH.getKey()+partnerEnum.getCode());
			return transactionTemplate.execute(new TransactionCallback<List<LoanRelation4DepVO>>(){
				@Override
				public List<LoanRelation4DepVO> doInTransaction(TransactionStatus status) {
					ProductType productType = SubAccountCode.productTypeMap.get(partnerEnum);
					String auth_productType = productType.getAuthCode();
					String red_productType = productType.getRedCode();
					String interestDate =DateUtil.formatYYYYMMDD(new Date());

					List<LoanRelation4DepVO> retList = new ArrayList<LoanRelation4DepVO>();
					logger.info("日期:【"+DateUtil.parseDate( interestDate )+"】=========【"+partnerEnum.getName()+"】【存管固期，借款债权匹配】开始：loanId="+loanId+",lnUserId="+lnUserId
							+",lnSubAccountId="+lnSubAccountId+",amount="+amount+",loanTerm="+loanTerm+"==============");
					 //获取VIP理财用户列表
                    List<Integer> VIPList = getDepVIPUserList(VIPId4PartnerEnum.getEnumByCode(partnerEnum.getCode()).getVipIdKey());
                    //还需要匹配的总额初始化
                    Double needMatchAmount = amount;


                    //匹配最小金额，低于该金额匹配放弃，除借款额度剩余外
					Double minMatchBalance = 10d;
					BsSysConfig config = sysConfigService.findConfigByKey(Constants.YUN_DAI_SELF_MATH_MIN_BALANCE);
					if( config != null ) {
						minMatchBalance = Double.valueOf(config.getConfValue());
					}

					//查询待匹配总金额
					Double sum4MatchAmount = bsSubAccountMapper.depFixedNormalSumAmountWait4Match(VIPList, auth_productType, red_productType , minMatchBalance);
					if(sum4MatchAmount.compareTo(amount) < 0){
						logger.info("===========【"+partnerEnum.getName()+"】可匹配金额为："+sum4MatchAmount+"，需借金额为："+amount+"匹配未成功，返回false，借款失败================");
						throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
					}else{
						//int i = 1;
						while (needMatchAmount > 0 && sum4MatchAmount>0) {
							Double initNeedMatchAmount = needMatchAmount;
							//logger.info("===========第"+i+"次查询,需匹配金额"+initNeedMatchAmount+"=============");
							//i= i+1;
							//查询待匹配列表

							List<BsSubAccountVO4DepFixedMatch> depFixedWait4MatchList = bsSubAccountMapper.depFixedNormalWait4MatchList(VIPList, auth_productType, red_productType, minMatchBalance);
							if(CollectionUtils.isNotEmpty(depFixedWait4MatchList)){
								for (BsSubAccountVO4DepFixedMatch bsSubAccount : depFixedWait4MatchList) {
									//根据债权匹配规则，得到匹配金额
									Double matchAmount = getMatchAmount(MoneyUtil.add(
											bsSubAccount.getAvailableBalance(), bsSubAccount.getRedAvailableBalance()).doubleValue(),
											bsSubAccount.getAgentId(),needMatchAmount);
									//logger.info(bsSubAccount.getId()+"匹配到金额："+matchAmount+"最小匹配金额："+minMatchBalance);
									//当剩余需匹配金额(需借)>10 && 匹配金额（可借）<10,
									if(needMatchAmount.compareTo(minMatchBalance) >0 && matchAmount.compareTo(minMatchBalance) < 0){
										//logger.info(bsSubAccount.getId()+"匹配到金额："+matchAmount+"<最小匹配金额："+minMatchBalance);
										continue;
									}
									//对比要匹配金额和需借剩余金额的大小
									matchAmount = getSmallerAmount(matchAmount, needMatchAmount);
									if(matchAmount > 0){
										//返回list添加值
										LoanRelation4DepVO initLoanRelation = new LoanRelation4DepVO();
										initLoanRelation.setHfUserId(bsSubAccount.getHfUserId());

										Double matchRedAmount=0d; //红包金额匹配初始化
										Double matchAuthAmount=matchAmount; //站岗户金额匹配初始化
										//判断是否用到红包户
										if(bsSubAccount.getRedAvailableBalance() > 0 ){
											matchRedAmount = getSmallerAmount(bsSubAccount.getRedAvailableBalance(),matchAmount);

											matchAuthAmount = MoneyUtil.subtract(matchAmount,matchRedAmount).doubleValue()<=0 ? 0d : MoneyUtil.subtract(matchAmount,matchRedAmount).doubleValue();

											initLoanRelation.setBsSubAccountId_red(bsSubAccount.getRedSubAccountId());
										}

										//新增债权关系记录，状态为PAYING 借款付款中
										LnLoanRelation loanRelation =addNewRelation(loanId, lnUserId, lnSubAccountId, bsSubAccount.getId(), bsSubAccount.getUserId(),
												matchAmount, Constants.LOAN_RELATION_STATUS_PAYING, Constants.TRANS_MARK_NORMAL, matchRedAmount);

										initLoanRelation.setLnLoanRelation(loanRelation);
										initLoanRelation.setCouponAmount(matchRedAmount);//抵用金
										initLoanRelation.setSelfAmount(matchAuthAmount);//自费金额
										retList.add(initLoanRelation);

										//冻结相应AUTH户的金额和RED户金额
										depFixedLoanAccountService.chargeLoanFreeze(matchAuthAmount, bsSubAccount.getId(), matchRedAmount, bsSubAccount.getRedSubAccountId());

										//待匹配总金额 = 原待匹配总金额 - 该次匹配的金额
										sum4MatchAmount = MoneyUtil.subtract(sum4MatchAmount, matchAmount).doubleValue();
										//需匹配金额 = 原需匹配金额 - 该次匹配的金额
										needMatchAmount = MoneyUtil.subtract(needMatchAmount, matchAmount).doubleValue();


									}

									//每次匹配完成后判断还需匹配的金额以及待匹配的金额是否为0，即判断该笔是否匹配完成，是否该结束
									if(needMatchAmount == 0 || sum4MatchAmount == 0){
										break;
									}
								}

							}
							//列表循环，匹配后需要匹配金额未发生变化,跳出while循环
							if(initNeedMatchAmount.compareTo(needMatchAmount) == 0){
								break;
							}
						}

					}
					if(needMatchAmount == 0){
						return retList;
					}else{
						throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
					}

				}
			});

		} finally {
			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEPFIXED_LOAN_MATCH.getKey()+partnerEnum.getCode());
			long end = System.currentTimeMillis();
			logger.info("======放款债权匹配[耗时：" + (end - begin) + "毫秒]======");
		}
	}

	/**
	 * 根据可匹配金额和用户的渠道算可匹配金额
	 * 非钱报用户 0<剩余可借金额<=50000，随机值区域(0,5000]--其中80%(0,3000],20%(3000,5000]
	 * 		剩余可借金额>50000，随机值区域(5000,10000]--其中80%(5000,8000],20%(8000,10000]
	 * 钱报用户沿用钱报随机数机制
	 * 		剩余可借金额<=3000,随机值=剩余可借金额剩余
	 * 		可借金额>3000，随机值区域(3000,5000]--其中75%(3000,4000],25%(4000,5000]
	 * @param leftAmount ---》bsSubAccount表中的剩余可匹配金额(AUTH_YUN和RED)
	 * @param agentId ---》渠道id
	 * @param needMatchAmount ---》需要匹配的金额
	 * @return
	 */
	protected Double getMatchAmount(Double leftAmount, Integer agentId,
			Double needMatchAmount) {
		Double matchAmount = needMatchAmount;//初始化

		if(agentId != null && bsAgentViewConfigService.isQianbao(agentId)){
			/*钱报用户
			 * needMatchAmount<=3000，直接匹配
			 * needMatchAmount>3000，计算可以匹配的金额
			 */
			if(needMatchAmount <= 3000){
				matchAmount = getSmallerAmount(needMatchAmount,leftAmount);
			}else{
				//随机值
				Random rand = new Random();
				Double canMatchAmount = 3000.0;
		        Integer luckyNumber = rand.nextInt(4) + 1; // [1,4]
		        if(luckyNumber<=3){
		        	canMatchAmount = (double)((rand.nextInt(10)+1)*100+4000); //(4000,5000]
		        }else{
		        	canMatchAmount = (double)((rand.nextInt(10)+1)*100+3000); //(3000,4000]
		        }
				//判断计算出来的可匹配金额是否大于需要匹配的金额，若canMatchAmount>needMatchAmount,应该匹配needMatchAmount
				if(MoneyUtil.subtract(canMatchAmount,needMatchAmount).doubleValue() > 0){
					canMatchAmount = needMatchAmount;
				}
				matchAmount = getSmallerAmount(canMatchAmount,leftAmount);
			}

		}else{
			/*非钱报用户
			 * needMatchAmount：0~5万，其中80%(0,3000],20%(3000,5000]；
			 * 					>5万，其中80%(5000,8000],20%(8000,10000]
			 */

			Random rand = new Random();
			Double canMatchAmount = 0.0;
			if(needMatchAmount <= 50000){
		        Integer luckyNumber = rand.nextInt(100) + 1; // [1,100]
		        if(luckyNumber<=80){
		        	canMatchAmount = (double)((rand.nextInt(10)+1)*300); //(0,3000]
		        }else{
		        	canMatchAmount = (double)((rand.nextInt(10)+1)*200+3000); //(3000,5000]
		        }
			}else {
				Integer luckyNumber = rand.nextInt(100) + 1; // [1,100]
		        if(luckyNumber<=80){
		        	canMatchAmount = (double)((rand.nextInt(10)+1)*300+5000); //(5000,8000]
		        }else{
		        	canMatchAmount = (double)((rand.nextInt(10)+1)*200+8000); //(8000,10000]
		        }
			}
			matchAmount = getSmallerAmount(canMatchAmount,leftAmount);
		}
		return matchAmount;
	}

	/**
	 * 获取更小值
	 *	canMatchAmount<=leftAmount,返回canMatchAmount，否则，返回leftAmount
	 * @param canMatchAmount
	 * @param leftAmount
	 * @return
	 */
	private Double getSmallerAmount(Double canMatchAmount, Double leftAmount) {
		Double matchAmount = 0.0;
		if(canMatchAmount.compareTo(leftAmount)<=0 ){
			matchAmount = canMatchAmount;
		}else{
			matchAmount = leftAmount;
		}
		return matchAmount;
	}

	/**
	 * 新增借贷关系数据
	 * @param loanId
	 * @param lnUserId
	 * @param lnSubAccountId
	 * @param bsSubAccountId
	 * @param bsUserId
	 * @param matchAmount
	 * @param discountAmount
	 */
	protected LnLoanRelation addNewRelation(Integer loanId, Integer lnUserId,
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

	@Override
	public List<Integer> getDepVIPUserList(String configKey) {
		List<Integer> VIPUserList = null;
        BsSysConfig configUser = sysConfigService.findConfigByKey(configKey);//VIP理财人账户
        if (configUser != null) {
            VIPUserList = new ArrayList<Integer>();
            String[] userStr = configUser.getConfValue().split(",");
            for (String string : userStr) {
                VIPUserList.add(Integer.parseInt(string));
            }
        }
        return VIPUserList;
	}

	/**
	 * 计算利息=计息天数* 利率(%)* 计息金额/36500，保留2位小数
	 * @param days 计息天数
	 * @param rate 利率%
	 * @param amount 计息金额
	 * @return
	 */
	@Override
	public Double calInterest(Integer days, Double rate, Double amount,
			Integer roundNum) {
		Double temp = MoneyUtil.divide(MoneyUtil.multiply(days, rate).doubleValue(), 36500,10).doubleValue();
		Double interestAmount = MoneyUtil.multiply(amount,temp).setScale(roundNum,BigDecimal.ROUND_HALF_UP).doubleValue();
		return interestAmount;
	}

	/**
	 * 生成理财人还款计划数据并新增
	 * @param record
	 * @param calInterestPrincipal 计息本金
	 * @param lastDay 还款计划计息日后一天
	 * @param agreementAmount 协议利息 = 协议利率*计息本金*还款人计息天数/365(未包含本金)
	 * @param agreementRate 协议利率
	 * @param thisRepayAmount 此次还款本金
	 * @return LnFinanceRepaySchedule
	 */
	@Override
	public LnFinanceRepaySchedule generateFinanceRepaySchedule(
			LoanRelation4TransferVO record, Double calInterestPrincipal,
			Date lastDay, Double agreementAmount,Double agreementRate, Double thisRepayAmount) {
		boolean isReturnFlag = false; //出让人债权拥有期内是否存在利息回款
		Date finishDate = lastDay;//初始化最后计息时间的后一天，若自然回款日小于（最后一次计息日的后一天），finishDate=自然回款日后一日，否则为（最后一次计息日的后一天）
		Date lastFinishInterestDate = record.getLastFinishInterestDate();//理财人自然回款日（投资的最后一次计息日）
		if(lastFinishInterestDate!=null && lastFinishInterestDate.compareTo(finishDate) < 0){
			finishDate = lastFinishInterestDate;
					//DateUtil.addDays(lastFinishInterestDate, 1);
		}
		Date beginDate = record.getRelationBeginDate(); //出让人获取债权的时间
		if(record.getLastPayInterestDate() != null
				&& record.getLastPayInterestDate().compareTo(beginDate) > 0 ){
			/**
			 * 上次只还利息的时间 在  【受让人获取债权的时间】   之后
			 * 说明在出让人拥有债权时间内，有发生还利息
			 * 则需计息开始时间为发生利息还款的后一日，否则为出让人获取债权的时间
			 */
			beginDate = DateUtil.addDays(record.getLastPayInterestDate(), 1);
			isReturnFlag = true;
		}
		Integer interestDays = DateUtil.getDiffeDay(finishDate,beginDate); //起息日到最后一次计息日的所有计息天数（前包括，后不包括）
		logger.info("============【还款处理】此次开始计息的时间："+DateUtil.formatYYYYMMDD(beginDate)
				+"，结束计息时间："+DateUtil.formatYYYYMMDD(finishDate)+"，计息天数："+interestDays+"=============================");

		if(interestDays < 0){
			interestDays = 0;
			logger.info("============【还款处理】异常情况，计息天数为0============================");
		}
		//理财人应得利息 = 计息本金*产品利率*理财人持有天数/365;
		Double interestAmount =  calInterest(interestDays, record.getBaseRate(), calInterestPrincipal, 2);

		//承接债权后还款，应付理财人的债权垫付金额
		Double planTransInterest = 0d;
		if(Constants.TRANS_MARK_TRANS_IN.equals(record.getTransMark())){
			//债转标记为转入,计算上次垫付且未还的利息
			if(!isReturnFlag){
				//计算在需计算利息期内，是否有产生部分还款
				if(record.getInitAmount() == record.getLeftAmount()){
					//未发生还款
					if(calInterestPrincipal == record.getLeftAmount()){
						//若此次计息本金=剩余本金，应付理财人的债权垫付金额=垫付全金额
						planTransInterest = record.getLastPayInterest();
						logger.info("============【还款/转让处理】应付理财人的债权垫付金额："+planTransInterest+"=============================");
					}else{
						//此次计息本金   占    总债权初始金额的比例
						Double rate = MoneyUtil.divide(calInterestPrincipal, record.getInitAmount()).doubleValue();
						//应付理财人的债权垫付金额 = 承接债权时所付利息 * rate
						planTransInterest = MoneyUtil.multiply(record.getLastPayInterest(), rate).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
						logger.info("============【还款/转让处理】应付理财人的债权垫付金额："+planTransInterest+"=============================");
					}
				}else{
					/**
					 * A：initAmount=1000，leftAmount= 700，则payedAmount=300
					 * 	转让表数据record.getLastPayInterest() = inAmount - amount
					 * payedInterest
					 */
					//借款人已还金额
					Double payedAmount = MoneyUtil.subtract(record.getInitAmount(), record.getLeftAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					//借款人已还金额    占    总债权初始金额的比例
					Double rate = MoneyUtil.divide(payedAmount, record.getInitAmount()).doubleValue();
					//已归还理财人的利息 = 承接债权时所付利息 * 已还比例
					Double payedInterest = MoneyUtil.multiply(record.getLastPayInterest(), rate).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

					if(calInterestPrincipal == record.getLeftAmount()){
						//应付理财人的债权垫付金额
						planTransInterest = MoneyUtil.subtract(record.getLastPayInterest(),payedInterest).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
						logger.info("============【还款/转让处理】上次承接债权后应付理财人的债权垫付金额："+planTransInterest+"=============================");
					}else{
						//此次计息本金+之前已还金额
						Double needPaySumAmount = MoneyUtil.add(payedAmount, calInterestPrincipal).doubleValue();
						//（此次计息本金+之前已还金额）   占    总债权初始金额的比例
						Double rate1 = MoneyUtil.divide(needPaySumAmount, record.getInitAmount()).doubleValue();
						//应该归还理财人的总利息 = 承接债权时所付利息 * rate1
						Double needPaySumInterest = MoneyUtil.multiply(record.getLastPayInterest(), rate1).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();

						//此次应付理财人的债权垫付金额，承接上次债权时所付的利息 = needPaySumInterest - payedInterest
						planTransInterest = MoneyUtil.subtract(needPaySumInterest, payedInterest).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
						logger.info("============【还款/转让处理】上次承接债权后已理财人的债权垫付金额："+needPaySumAmount+"，未还理财人的债权垫付金额："+planTransInterest+"=============================");
					}
				}
			}
		}
		LnFinanceRepaySchedule scheduleTemp = new LnFinanceRepaySchedule();
		if(agreementAmount != null){
			//此时为还款，理财人分账 = 还款本金+计息本金*产品利率*承接人持有天数/365+计息本金/债权本金*债转付息
			Double financeAmount = MoneyUtil.add(thisRepayAmount,
					MoneyUtil.add(interestAmount, planTransInterest).doubleValue()).doubleValue();
			scheduleTemp.setPlanTotal(MoneyUtil.add(agreementAmount, thisRepayAmount).doubleValue());

			//手续费分账=计息本金*协议利率*借款人还款计息天数/365-理财人分账
			Double planFee = MoneyUtil.subtract(scheduleTemp.getPlanTotal(), financeAmount).doubleValue();

			scheduleTemp.setPlanFee(planFee);
		}else{
			//债权转让
			//承接人应付出利息 = 计息本金*【协议利率】*理财人持有天数/365
			Double B_out_amount = calInterest(interestDays, agreementRate, calInterestPrincipal, 2);

			/**
			 * 承接人应付出本息 = 计息本金*【协议利率】*理财人持有天数/365+计息本金/债权本金*债转付息+本金
			 * 转让人产品本息收益 = 计息本金*【产品利率】*理财人持有天数/365+计息本金/债权本金*债转付息+本金
			 * 转让人付出手续费=承接人付出本息-转让人产品本息收益-补差 ,
			 * 不包含补差部分则 = 计息本金*【协议利率】*理财人持有天数/365- 计息本金*【产品利率】*理财人持有天数/365
			 */

			Double planFee = MoneyUtil.subtract(B_out_amount, interestAmount).doubleValue();
			scheduleTemp.setPlanFee(planFee);
		}

		scheduleTemp.setPlanInterest(interestAmount);
		scheduleTemp.setPlanPrincipal(thisRepayAmount);
		scheduleTemp.setPlanTransInterest(planTransInterest);

		return scheduleTemp;
	}

	@Override
	public Map<String, Object> doDepFixedTransferDetail(Date today, final LoanRelation4TransferVO record, boolean vipTransFlag, final PartnerEnum partnerEnum) {
		//生成理财人还款计划数据
		LnFinanceRepaySchedule scheduleTemp = new LnFinanceRepaySchedule();
		if(PartnerEnum.YUN_DAI_SELF.getCode().equals(partnerEnum.getCode())){
			scheduleTemp = generateFinanceRepaySchedule(record,
					record.getLeftAmount(), today, null, record.getAgreementRate(), record.getLeftAmount());
		}else{
			scheduleTemp = getFinanceRepaySchedule4SevenTransfer(record, today);
		}


		//承接人债转付息=债转本金*转让人持有天数*转出人协议利率/365+债转本金/转让人受让时债权本金*转让人受让时+转让人付出手续费
		final Double transInAmount = MoneyUtil.add(scheduleTemp.getPlanFee(),
				MoneyUtil.add(scheduleTemp.getPlanInterest(), scheduleTemp.getPlanTransInterest()).doubleValue()).doubleValue();
		//承接人付出本息
		final Double  transInSumAmount= MoneyUtil.add(record.getLeftAmount(),transInAmount).doubleValue();
		//转让人产品收益 = 债转本金*转让人持有天数*产品利率/365+债转本金/转让人受让时债权本金*转让人受让时
		final Double transOutGetAmount =  MoneyUtil.add(scheduleTemp.getPlanInterest(), scheduleTemp.getPlanTransInterest()).doubleValue();
		//获取补差金额
		final BsSubAccount diffAct = bsSubAccountPairMapper.selectDiffActByLoanRelationId(record.getId());
		Double diffAmount = diffAct != null && diffAct.getBalance().compareTo(0d)>0 ? diffAct.getBalance() : 0d;//补差金额
		if(scheduleTemp.getPlanFee() < 0){
			logger.info("=============【"+partnerEnum.getName()+"-债权转让】转让人应付手续费<0,错误；relationId:"+record.getId()
					+"============================");
			return null;
		}
		logger.info("=============【"+partnerEnum.getName()+"-债权转让】转让人应付手续费"+scheduleTemp.getPlanFee()+"减去补差金额："+diffAmount
				+"============================");
		//实收手续费
		Double feeAmount = MoneyUtil.subtract(scheduleTemp.getPlanFee(), diffAmount).doubleValue();
		//手续费不够补差时：补差金额为手续费金额，实收手续费为0
        if(feeAmount < 0){
        	feeAmount = 0d;
            diffAmount = scheduleTemp.getPlanFee();
            diffAct.setBalance(diffAmount);
        }
        final Double fee = feeAmount;

		//VIP用户id列表
		List<Integer> vipIdList = getDepVIPUserList(VIPId4PartnerEnum.getEnumByCode(partnerEnum.getCode()).getVipIdKey());

		BsSubAccountVO4DepFixedMatch inSubAccountTemp = new BsSubAccountVO4DepFixedMatch();
		ProductType productType = SubAccountCode.productTypeMap.get(partnerEnum);
		if(record.getLeftAmount().compareTo(5000d) <= 0){
			//钱报系优先接
			inSubAccountTemp = bsSubAccountMapper.query4TransferCommon(transInSumAmount, "yes", "no", vipIdList, record.getLeftAmount(),record.getBsUserId(), productType.getAuthCode(), productType.getRedCode());
			if(inSubAccountTemp == null){
				inSubAccountTemp = bsSubAccountMapper.query4TransferCommon(transInSumAmount, "no", "no", vipIdList, record.getLeftAmount(),record.getBsUserId(), productType.getAuthCode(), productType.getRedCode());
				if(inSubAccountTemp == null && !vipTransFlag){
					inSubAccountTemp = bsSubAccountMapper.query4TransferCommon(transInSumAmount, "no", "yes", vipIdList,record.getLeftAmount(),record.getBsUserId(), productType.getAuthCode(), productType.getRedCode());
				}
			}
		}else{
			inSubAccountTemp = bsSubAccountMapper.query4TransferCommon(transInSumAmount, "no", "no", vipIdList, record.getLeftAmount(),record.getBsUserId(), productType.getAuthCode(), productType.getRedCode());
			if(inSubAccountTemp == null && !vipTransFlag){
				inSubAccountTemp = bsSubAccountMapper.query4TransferCommon(transInSumAmount, "no", "yes", vipIdList, record.getLeftAmount(),record.getBsUserId(), productType.getAuthCode(), productType.getRedCode());
			}
		}
		if(inSubAccountTemp == null){
			return null;
		}
		final BsSubAccountVO4DepFixedMatch inSubAccount = inSubAccountTemp;
		final Double diffAmountFinal = diffAmount;
		try {
			jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEPFIXED_LOAN_MATCH.getKey()+partnerEnum.getCode());
			transactionTemplate.execute(new TransactionCallbackWithoutResult(){
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					LoanRelation4DepVO initLoanRelation = new LoanRelation4DepVO();
					initLoanRelation.setHfUserId(inSubAccount.getHfUserId());

					Double matchRedAmount=0d; //红包金额匹配初始化
					Double matchAuthAmount=transInSumAmount; //站岗户金额匹配初始化
					//判断是否用到红包户
					if(inSubAccount.getRedAvailableBalance() > 0 ){
						//红包只能用于本金部分，若红包小于本金，则收取红包为本金部分
						matchRedAmount = inSubAccount.getRedAvailableBalance().compareTo(record.getLeftAmount()) <=0
								? inSubAccount.getRedAvailableBalance() : record.getLeftAmount();
						logger.info("=============【"+partnerEnum.getName()+"-债权转让】冻结承接人RED户金额："+matchRedAmount+"============================");
						matchAuthAmount =  MoneyUtil.subtract(transInSumAmount,matchRedAmount).doubleValue();

						initLoanRelation.setBsSubAccountId_red(inSubAccount.getRedSubAccountId());
					}
					logger.info("=============【"+partnerEnum.getName()+"-债权转让】冻结承接人AUTH户："+inSubAccount.getId()+"，金额："+matchAuthAmount+"============================");
					//冻结相应AUTH户的金额和RED户金额
					depFixedLoanAccountService.chargeLoanFreeze(matchAuthAmount, inSubAccount.getId(), matchRedAmount, inSubAccount.getRedSubAccountId());

					LnDepositionTarget depositionTarget = lnDepositionTargetMapper.selectByLoanId(record.getLoanId());
					/**
					 * 调用存管标的转让
					 * 交易金额=自费价格+出让人手续费+受让人手续费+转让收益
					 * 自费价格=债转本金-抵用券金额
					 * 抵用券金额=匹配的红包金额，如没有则为0
					 * 出让人手续费=转让人付出手续费
					 * 转让收益=承接人债转付息
					 * 收益出资方：受让人
					 */
					logger.info("=============【"+partnerEnum.getName()+"-债权转让】调用存管标的转让============================");
					B2GReqMsg_HFBank_TransferDebt transReq = new B2GReqMsg_HFBank_TransferDebt();
					transReq.setPlatcust(record.getHfUserId());
					transReq.setProd_id(depositionTarget.getId().toString());
					//自费价格=债转本金-抵用券金额
					Double deal_amount = MoneyUtil.subtract(record.getLeftAmount(),matchRedAmount).doubleValue();
					transReq.setDeal_amount(deal_amount);
					//抵用劵金额
					transReq.setCoupon_amt(matchRedAmount);
					//出让人手续费
					List<TransferDebtReqCommission> commissionList = new ArrayList<TransferDebtReqCommission>();
					TransferDebtReqCommission commission = new TransferDebtReqCommission();
					commission.setPayout_amt(MoneyUtil.defaultRound(new BigDecimal(fee)).toString());
					commission.setPayout_plat_type(Constants.PAYOUT_PLAT_TYPE_FEE);
					commissionList.add(commission);
					transReq.setCommission(commissionList);

					//转让收益
					transReq.setTransfer_income(transInAmount);

					//交易金额=自费+出让人手续费+受让人手续费+转让收益
					Double trans_amt = MoneyUtil.add(
							MoneyUtil.add(deal_amount, fee).doubleValue(),transInAmount).doubleValue();
					transReq.setTrans_amt(trans_amt);
					//受让人平台客户编号
					transReq.setDeal_platcustprivate(inSubAccount.getHfUserId());
					//收益出资方账户
					transReq.setIncome_acct(inSubAccount.getHfUserId());
					String orderNo = Util.generateOrderNo4BaoFoo(8);
					transReq.setOrder_no(orderNo);
					//转让份额
					transReq.setTrans_share(record.getLeftAmount());
					B2GResMsg_HFBank_TransferDebt transRes = hfbankTransportService.transferDebt(transReq);

					if(!ConstantUtil.BSRESCODE_SUCCESS.equals(transRes.getResCode())){
						logger.info("=============【"+partnerEnum.getName()+"-债权转让】调用存管标的转让,失败：ResCode:"+transRes.getResCode()+",Recode:"+transRes.getRecode()+"============================");
						String orderNoNew = Util.generateOrderNo4BaoFoo(8);
						transReq.setOrder_no(orderNoNew);
						transRes = hfbankTransportService.transferDebt(transReq);
						if(!ConstantUtil.BSRESCODE_SUCCESS.equals(transRes.getResCode())){
							logger.info("=============【"+partnerEnum.getName()+"-债权转让】再次调用存管标的转让,失败：ResCode:"+transRes.getResCode()+",Recode:"+transRes.getRecode()+"============================");
							// 再次转让失败
							//解冻相应AUTH户的金额和RED户金额
							depFixedLoanAccountService.chargeLoanUnFreeze(matchAuthAmount, inSubAccount.getId(), matchRedAmount, inSubAccount.getRedSubAccountId());
							//发送告警短信
							specialJnlService.warn4FailNoSMS(transInSumAmount, "理财人退出转让失败，relationId:"+record.getId(), orderNo, "债权转让失败");
						}else{
							logger.info("=============【"+partnerEnum.getName()+"-债权转让】调用存管标的转让,成功============================");
							//调用转让成功库表操作
							doChange4LoanRelation(record, inSubAccount, transInSumAmount, matchRedAmount);

							//债权转让记账
							TransferAccountInfo transferInfo = new TransferAccountInfo();
							transferInfo.setOutUser_investorAuthActId(record.getBsSubAccountId());
							transferInfo.setOutUser_principalAmount(record.getLeftAmount());
							transferInfo.setOutUser_interestAmount(transOutGetAmount);
							transferInfo.setOutUser_diffActId(diffAct.getId());
							transferInfo.setDiffFee(diffAmountFinal);
							transferInfo.setFee(fee);//币港湾营收=转让人付出手续费
							transferInfo.setInUser_authAmount(matchAuthAmount);
							transferInfo.setInUser_investorAuthActId(inSubAccount.getId());
							transferInfo.setInUser_redAmount(matchRedAmount);
							transferInfo.setInUser_investorRedActId(inSubAccount.getRedSubAccountId());
							transferInfo.setPartnerEnum(partnerEnum);
							transferInfo.setOut_relationId(record.getId());
							depFixedRepayAccountService.chargeRelationTransfer(transferInfo);
						 }
		    		}else{
		    			logger.info("=============【"+partnerEnum.getName()+"-债权转让】调用存管标的转让,成功============================");
		    			//调用转让成功库表操作
		    			doChange4LoanRelation(record, inSubAccount, transInSumAmount, matchRedAmount);
		    			//债权转让记账
		    			TransferAccountInfo transferInfo = new TransferAccountInfo();
		    			transferInfo.setOutUser_investorAuthActId(record.getBsSubAccountId());
		    			transferInfo.setInUser_authAmount(matchAuthAmount);
		    			transferInfo.setOutUser_principalAmount(record.getLeftAmount());
		    			transferInfo.setOutUser_interestAmount(transOutGetAmount);
		    			transferInfo.setOutUser_diffActId(diffAct.getId());
		    			transferInfo.setDiffFee(diffAmountFinal);
		    			transferInfo.setFee(fee);
		    			transferInfo.setInUser_investorAuthActId(inSubAccount.getId());
		    			transferInfo.setInUser_redAmount(matchRedAmount);
		    			transferInfo.setInUser_investorRedActId(inSubAccount.getRedSubAccountId());
		    			transferInfo.setPartnerEnum(partnerEnum);
		    			transferInfo.setOut_relationId(record.getId());
		    			depFixedRepayAccountService.chargeRelationTransfer(transferInfo);
		    		}
				}
			});
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEPFIXED_LOAN_MATCH.getKey()+partnerEnum.getCode());
		}

		// 切面使用参数
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("loanRelationId", record.getId());
        dataMap.put("partnerEnum", partnerEnum);
		// 生成债转让协议签章(云贷、七贷)
		return dataMap;
	}

	@Override
	public void doDepFixedZsdTransferDetail(Date today,
			final LoanRelation4TransferVO record, boolean vipTransFlag) {
		//生成理财人还款计划数据
		LnFinanceRepaySchedule scheduleTemp = generateFinanceRepaySchedule(record,
				record.getLeftAmount(), today, null, record.getAgreementRate(), record.getLeftAmount());

		//承接人债转付息=债转本金*转让人持有天数*转出人协议利率/365+债转本金/转让人受让时债权本金*转让人受让时+转让人付出手续费
		final Double transInAmount = MoneyUtil.add(scheduleTemp.getPlanFee(),
				MoneyUtil.add(scheduleTemp.getPlanInterest(), scheduleTemp.getPlanTransInterest()).doubleValue()).doubleValue();
		//承接人付出本息
		final Double  transInSumAmount= MoneyUtil.add(record.getLeftAmount(),transInAmount).doubleValue();
		//转让人产品收益 = 债转本金*转让人持有天数*产品利率/365+债转本金/转让人受让时债权本金*转让人受让时
		final Double transOutGetAmount =  MoneyUtil.add(scheduleTemp.getPlanInterest(), scheduleTemp.getPlanTransInterest()).doubleValue();
		//获取补差金额
		final BsSubAccount diffAct = bsSubAccountPairMapper.selectDiffActByLoanRelationId(record.getId());
		Double diffAmount = diffAct != null && diffAct.getBalance().compareTo(0d)>0 ? diffAct.getBalance() : 0d;//补差金额
		if(scheduleTemp.getPlanFee() < 0){
			logger.info("=============【赞时贷-债权转让】转让人应付手续费<0,错误；relationId:"+record.getId()
					+"============================");
			return;
		}
		//实收手续费
		Double feeAmount = MoneyUtil.subtract(scheduleTemp.getPlanFee(), diffAmount).doubleValue();
		//手续费不够补差时：补差金额为手续费金额，实收手续费为0
        if(feeAmount < 0){
        	feeAmount = 0d;
            diffAmount = scheduleTemp.getPlanFee();
            diffAct.setBalance(diffAmount);
        }
        final Double fee = feeAmount;

		//VIP用户id列表
		List<Integer> vipIdList = getDepVIPUserList(VIPId4PartnerEnum.ZSD.getVipIdKey());

		BsSubAccountVO4DepFixedMatch inSubAccountTemp = new BsSubAccountVO4DepFixedMatch();

		if(record.getLeftAmount().compareTo(5000d) <= 0){
			//钱报系优先接
			inSubAccountTemp = bsSubAccountMapper.query4TransferZsd(transInSumAmount, "yes", "no", vipIdList, record.getLeftAmount(),record.getBsUserId());
			if(inSubAccountTemp == null){
				inSubAccountTemp = bsSubAccountMapper.query4TransferZsd(transInSumAmount, "no", "no", vipIdList, record.getLeftAmount(),record.getBsUserId());
				if(inSubAccountTemp == null && !vipTransFlag){
					inSubAccountTemp = bsSubAccountMapper.query4TransferZsd(transInSumAmount, "no", "yes", vipIdList,record.getLeftAmount(),record.getBsUserId());
				}
			}
		}else{
			inSubAccountTemp = bsSubAccountMapper.query4TransferZsd(transInSumAmount, "no", "no", vipIdList, record.getLeftAmount(),record.getBsUserId());
			if(inSubAccountTemp == null && !vipTransFlag){
				inSubAccountTemp = bsSubAccountMapper.query4TransferZsd(transInSumAmount, "no", "yes", vipIdList, record.getLeftAmount(),record.getBsUserId());
			}
		}
		if(inSubAccountTemp == null){
			return;
		}
		final BsSubAccountVO4DepFixedMatch inSubAccount = inSubAccountTemp;
		final Double diffAmountFinal = diffAmount;
		try {
			jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEPFIXED_ZSD_LOAN_MATCH.getKey());
			transactionTemplate.execute(new TransactionCallbackWithoutResult(){
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					LoanRelation4DepVO initLoanRelation = new LoanRelation4DepVO();
					initLoanRelation.setHfUserId(inSubAccount.getHfUserId());

					Double matchRedAmount=0d; //红包金额匹配初始化
					Double matchAuthAmount=transInSumAmount; //站岗户金额匹配初始化
					//判断是否用到红包户
					if(inSubAccount.getRedAvailableBalance() > 0 ){
						//红包只能用于本金部分，若红包小于本金，则收取红包为本金部分
						matchRedAmount = inSubAccount.getRedAvailableBalance().compareTo(record.getLeftAmount()) <=0
								? inSubAccount.getRedAvailableBalance() : record.getLeftAmount();
						logger.info("=============【赞时贷-债权转让】冻结承接人RED户金额："+matchRedAmount+"============================");
						matchAuthAmount =  MoneyUtil.subtract(transInSumAmount,matchRedAmount).doubleValue();

						initLoanRelation.setBsSubAccountId_red(inSubAccount.getRedSubAccountId());
					}
					logger.info("=============【赞时贷-债权转让】冻结承接人AUTH户："+inSubAccount.getId()+"，金额："+matchAuthAmount+"============================");
					//冻结相应AUTH户的金额和RED户金额
					depFixedLoanAccountService.chargeLoanFreeze(matchAuthAmount, inSubAccount.getId(), matchRedAmount, inSubAccount.getRedSubAccountId());

					LnDepositionTarget depositionTarget = lnDepositionTargetMapper.selectByLoanId(record.getLoanId());
					/**
					 * 调用存管标的转让
					 * 交易金额=自费价格+出让人手续费+受让人手续费+转让收益
					 * 自费价格=债转本金-抵用券金额
					 * 抵用券金额=匹配的红包金额，如没有则为0
					 * 出让人手续费=转让人付出手续费
					 * 转让收益=承接人债转付息
					 * 收益出资方：受让人
					 */
					logger.info("=============【赞时贷-债权转让】调用存管标的转让============================");
					B2GReqMsg_HFBank_TransferDebt transReq = new B2GReqMsg_HFBank_TransferDebt();
					transReq.setPlatcust(record.getHfUserId());
					transReq.setProd_id(depositionTarget.getId().toString());
					//自费价格=债转本金-抵用券金额
					Double deal_amount = MoneyUtil.subtract(record.getLeftAmount(),matchRedAmount).doubleValue();
					transReq.setDeal_amount(deal_amount);
					//抵用劵金额
					transReq.setCoupon_amt(matchRedAmount);
					//出让人手续费
					List<TransferDebtReqCommission> commissionList = new ArrayList<TransferDebtReqCommission>();
					TransferDebtReqCommission commission = new TransferDebtReqCommission();
					commission.setPayout_amt(MoneyUtil.defaultRound(new BigDecimal(fee)).toString());
					commission.setPayout_plat_type(Constants.PAYOUT_PLAT_TYPE_FEE);
					commissionList.add(commission);
					transReq.setCommission(commissionList);

					//转让收益
					transReq.setTransfer_income(transInAmount);

					//交易金额=自费+出让人手续费+受让人手续费+转让收益
					Double trans_amt = MoneyUtil.add(
							MoneyUtil.add(deal_amount, fee).doubleValue(),transInAmount).doubleValue();
					transReq.setTrans_amt(trans_amt);
					//受让人平台客户编号
					transReq.setDeal_platcustprivate(inSubAccount.getHfUserId());
					//收益出资方账户
					transReq.setIncome_acct(inSubAccount.getHfUserId());
					String orderNo = Util.generateOrderNo4BaoFoo(8);
					transReq.setOrder_no(orderNo);
					//转让份额
					transReq.setTrans_share(record.getLeftAmount());
					B2GResMsg_HFBank_TransferDebt transRes = hfbankTransportService.transferDebt(transReq);

					if(!ConstantUtil.BSRESCODE_SUCCESS.equals(transRes.getResCode())){
						logger.info("=============【赞时贷-债权转让】调用存管标的转让,失败：ResCode:"+transRes.getResCode()+",Recode:"+transRes.getRecode()+"============================");
						String orderNoNew = Util.generateOrderNo4BaoFoo(8);
						transReq.setOrder_no(orderNoNew);
						transRes = hfbankTransportService.transferDebt(transReq);
						if(!ConstantUtil.BSRESCODE_SUCCESS.equals(transRes.getResCode())){
							logger.info("=============【赞时贷-债权转让】再次调用存管标的转让,失败：ResCode:"+transRes.getResCode()+",Recode:"+transRes.getRecode()+"============================");
							// 再次转让失败
							//解冻相应AUTH户的金额和RED户金额
							depFixedLoanAccountService.chargeLoanUnFreeze(matchAuthAmount, inSubAccount.getId(), matchRedAmount, inSubAccount.getRedSubAccountId());
							//发送告警短信
							specialJnlService.warn4FailNoSMS(transInSumAmount, "理财人退出转让失败，relationId:"+record.getId(), orderNo, "债权转让失败");
						}else{
							logger.info("=============【赞时贷-债权转让】调用存管标的转让,成功============================");
							//调用转让成功库表操作
							doChange4LoanRelation(record, inSubAccount, transInSumAmount, matchRedAmount);

							//债权转让记账
							TransferAccountInfo transferInfo = new TransferAccountInfo();
							transferInfo.setOutUser_investorAuthActId(record.getBsSubAccountId());
							transferInfo.setOutUser_principalAmount(record.getLeftAmount());
							transferInfo.setOutUser_interestAmount(transOutGetAmount);
							transferInfo.setOutUser_diffActId(diffAct.getId());
							transferInfo.setDiffFee(diffAmountFinal);
							transferInfo.setFee(fee);//币港湾营收=转让人付出手续费
							transferInfo.setInUser_authAmount(matchAuthAmount);
							transferInfo.setInUser_investorAuthActId(inSubAccount.getId());
							transferInfo.setInUser_redAmount(matchRedAmount);
							transferInfo.setInUser_investorRedActId(inSubAccount.getRedSubAccountId());
							transferInfo.setPartnerEnum(PartnerEnum.ZSD);
							transferInfo.setOut_relationId(record.getId());
							depFixedRepayAccountService.chargeRelationTransfer(transferInfo);
						 }
		    		}else{
		    			logger.info("=============【赞时贷-债权转让】调用存管标的转让,成功============================");
		    			//调用转让成功库表操作
		    			doChange4LoanRelation(record, inSubAccount, transInSumAmount, matchRedAmount);
		    			//债权转让记账
		    			TransferAccountInfo transferInfo = new TransferAccountInfo();
		    			transferInfo.setOutUser_investorAuthActId(record.getBsSubAccountId());
		    			transferInfo.setInUser_authAmount(matchAuthAmount);
		    			transferInfo.setOutUser_principalAmount(record.getLeftAmount());
		    			transferInfo.setOutUser_interestAmount(transOutGetAmount);
		    			transferInfo.setOutUser_diffActId(diffAct.getId());
		    			transferInfo.setDiffFee(diffAmountFinal);
		    			transferInfo.setFee(fee);
		    			transferInfo.setInUser_investorAuthActId(inSubAccount.getId());
		    			transferInfo.setInUser_redAmount(matchRedAmount);
		    			transferInfo.setInUser_investorRedActId(inSubAccount.getRedSubAccountId());
		    			transferInfo.setPartnerEnum(PartnerEnum.ZSD);
		    			transferInfo.setOut_relationId(record.getId());
		    			depFixedRepayAccountService.chargeRelationTransfer(transferInfo);
		    		}
				}
			});
		}catch (Exception e) {
			e.printStackTrace();
			return ;
		}finally {
			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEPFIXED_ZSD_LOAN_MATCH.getKey());
		}
	}


	/**
	 * 修改出让人借贷关系
	 * 新增借贷关系，新增债权转让记录
	 * @param outRecord
	 * @param inRecord
	 * @param needPayAmount
	 * @param matchRedAmount 匹配到红包的金额
	 */
	protected void doChange4LoanRelation(LoanRelation4TransferVO outRecord, BsSubAccountVO4DepFixedMatch inRecord,
			Double needPayAmount, Double matchRedAmount) {
		Double amount = outRecord.getLeftAmount();//转让债权的本金
		//修改出让人借贷关系
		LnLoanRelation initRelation = lnLoanRelationMapper.selectByPrimaryKey(outRecord.getId());
		LnLoanRelation outLoanRelation = new LnLoanRelation();
		outLoanRelation.setId(initRelation.getId());
		outLoanRelation.setTotalAmount(MoneyUtil.subtract(initRelation.getTotalAmount(), amount).doubleValue());
		outLoanRelation.setLeftAmount(MoneyUtil.subtract(initRelation.getLeftAmount(), amount).doubleValue());
		outLoanRelation.setStatus(Constants.LOAN_RELATION_STATUS_TRANSFERRED);//已转出
		outLoanRelation.setUpdateTime(new Date());
		lnLoanRelationMapper.updateByPrimaryKeySelective(outLoanRelation);
		//新增债权关系记录，状态为成功SUCCESS，转让标记为转入
		Integer firstTerm = outRecord.getThisRepayPlanSerialId()== null ? 1:outRecord.getThisRepayPlanSerialId();
		LnLoanRelation inRelationId = addNewRelation4Transfer(outRecord.getLoanId(), outRecord.getLnUserId(), outRecord.getLnSubAccountId(), inRecord.getId(),
				inRecord.getUserId(),amount,Constants.LOAN_RELATION_STATUS_SUCCESS,Constants.TRANS_MARK_TRANS_IN, matchRedAmount, firstTerm);
		//新增债权金额变动记录表
		LnLoanAmountChange tempAmountChange = new LnLoanAmountChange();
        tempAmountChange.setUpdateTime(new Date());
        tempAmountChange.setAfterAmount(outLoanRelation.getLeftAmount());
        tempAmountChange.setBeforeAmount(initRelation.getLeftAmount());
        tempAmountChange.setChangeAmount(amount);
        tempAmountChange.setCreateTime(new Date());
        tempAmountChange.setRelationId(outRecord.getId());
        lnLoanAmountChangeMapper.insertSelective(tempAmountChange);

        //新增债权转让记录
        LnCreditTransfer LnCreditTransfer = new LnCreditTransfer();
  		LnCreditTransfer.setAmount(amount);
  		LnCreditTransfer.setInAmount(needPayAmount);
  		LnCreditTransfer.setInLoanRelationId(inRelationId.getId());
  		LnCreditTransfer.setInSubAccountId(inRecord.getId());
  		LnCreditTransfer.setInUserId(inRecord.getUserId());
  		LnCreditTransfer.setOutLoanRelationId(outLoanRelation.getId());
  		LnCreditTransfer.setOutSubAccountId(initRelation.getBsSubAccountId());
  		LnCreditTransfer.setOutUserId(initRelation.getBsUserId());
  		LnCreditTransfer.setUpdateTime(new Date());
  		LnCreditTransfer.setCreateTime(new Date());
  		lnCreditTransferMapper.insertSelective(LnCreditTransfer);

	}

	private LnLoanRelation addNewRelation4Transfer(Integer loanId, Integer lnUserId,
			Integer lnSubAccountId, Integer bsSubAccountId,
			Integer bsUserId, Double matchAmount, String status,
			String transMarkTrans, Double discountAmount, Integer firstTerm) {
		LnLoanRelation lnLoanRelationRecord = new LnLoanRelation();
		lnLoanRelationRecord.setCreateTime(new Date());
		lnLoanRelationRecord.setLnSubAccountId(lnSubAccountId);
		lnLoanRelationRecord.setLnUserId(lnUserId);
		lnLoanRelationRecord.setLoanId(loanId);
		lnLoanRelationRecord.setStatus(status);
		lnLoanRelationRecord.setUpdateTime(new Date());
		lnLoanRelationRecord.setFirstTerm(firstTerm);
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

	@Override
	public List<LoanRelation4DepVO> confirmLoanRelation4LoanZSD(final Integer loanId,
			final Integer lnUserId, final Integer lnSubAccountId, final Double amount) {
		if(loanId ==null || lnUserId == null || amount ==null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		/**
		 * 1、查询待匹配总金额，校验是否满足出借金额
		 * 2、小额匹配-->vip匹配-->大额匹配
		 */
		long begin = System.currentTimeMillis();
		try {
			jsClientDaoSupport.lock(RedisLockEnum.LOCK_ZSD_LOAN_MATCH.getKey());
			return transactionTemplate.execute(new TransactionCallback<List<LoanRelation4DepVO>>(){
				@Override
				public List<LoanRelation4DepVO> doInTransaction(TransactionStatus status) {
					List<LoanRelation4DepVO> retList = new ArrayList<LoanRelation4DepVO>();
					logger.info("=========【赞时贷，借款债权匹配】开始：loanId="+loanId+",lnUserId="+lnUserId
							+",lnSubAccountId="+lnSubAccountId+",amount="+amount+"==============");
					 //获取VIP理财用户列表
                    List<Integer> VIPList = getDepVIPUserList(VIPId4PartnerEnum.ZSD.getVipIdKey());
                    //还需要匹配的总额初始化
                    Double needMatchAmount = amount;
                    //获取当前可匹配的总金额(包括vip)
                    Double sum4MatchAmount = bsSubAccountMapper.balanceWait4Match4ZSD(Constants.PRODUCT_TYPE_AUTH_ZSD);
                    if(sum4MatchAmount.compareTo(amount) < 0){
						logger.info("===========【赞时贷】可匹配金额为："+sum4MatchAmount+"，需借金额为："+amount+"匹配未成功，返回false，借款失败================");
						throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
					}else{
						logger.info("===============================【赞时贷】小额匹配开始==============================");
						//小额匹配
						LoanRelationMatchReturnVO smallNormalMatch = smallNormalZSDMatch(needMatchAmount, loanId, lnUserId, lnSubAccountId);
						List<LoanRelation4DepVO> smallList = smallNormalMatch.getDepRelationList();
						needMatchAmount = smallNormalMatch.getBorrowAmount();
						logger.info("===============================【赞时贷】小额匹配结束，剩余需借金额="+needMatchAmount+"==============================");
						retList.addAll(smallList);
						if(needMatchAmount == 0){
							return retList;
						}

						//大额匹配
						logger.info("===============================【赞时贷】大额匹配开始==============================");
						LoanRelationMatchReturnVO largeMatch = largeZSDMatch(needMatchAmount, loanId, lnUserId, lnSubAccountId);
						List<LoanRelation4DepVO> largeList = largeMatch.getDepRelationList();
						needMatchAmount = largeMatch.getBorrowAmount();
						logger.info("===============================【赞时贷】大额匹配结束，剩余需借金额="+needMatchAmount+"==============================");
						retList.addAll(largeList);
						if(needMatchAmount == 0){
							return retList;
						}

						//VIP匹配
						logger.info("===============================【赞时贷】VIP匹配开始==============================");
						LoanRelationMatchReturnVO vipMatch = vipZSDMatch(needMatchAmount, loanId, lnUserId, lnSubAccountId);
						List<LoanRelation4DepVO> vipList = vipMatch.getDepRelationList();
						needMatchAmount = vipMatch.getBorrowAmount();
						logger.info("===============================【赞时贷】VIP匹配结束，剩余需借金额="+needMatchAmount+"==============================");
						retList.addAll(vipList);
						if(needMatchAmount == 0){
							return retList;
						}
					}

                    if(needMatchAmount == 0){
						return retList;
					}else{
						logger.info("========撮合结束，当前需借金额=" + needMatchAmount + ",撮合失败==========");
						throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
					}
				}
			});

		} finally {
			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_ZSD_LOAN_MATCH.getKey());
			long end = System.currentTimeMillis();
			logger.info("======ZSD放款债权匹配[耗时：" + (end - begin) + "毫秒]======");
		}
	}

	/**
	 * 赞时贷普通大额匹配
	 * @param needMatchAmount 目前需匹配金额
	 * @param loanId
	 * @param lnUserId
	 * @param lnSubAccountId
	 * @return
	 */
	protected LoanRelationMatchReturnVO largeZSDMatch(Double needMatchAmount,
			Integer loanId, Integer lnUserId, Integer lnSubAccountId) {
		LoanRelationMatchReturnVO returnVo = new LoanRelationMatchReturnVO();
		List<LoanRelation4DepVO> retList = new ArrayList<LoanRelation4DepVO>();
		Double limitAmount = getMatchLimitAmount();
		List<BsSubAccountVO4DepFixedMatch> list = bsSubAccountMapper.zsdNormalWait4MatchList(Constants.PRODUCT_TYPE_AUTH_ZSD,getDepVIPUserList(VIPId4PartnerEnum.ZSD.getVipIdKey()), limitAmount, null);
		while(CollectionUtils.isNotEmpty(list) && needMatchAmount>0){
			Double initNeedMatchAmount = needMatchAmount;
			for (BsSubAccountVO4DepFixedMatch bsSubAccount : list) {
				 Double thisAmount=1d;
				//该笔投资可被匹配金额
				Double can2matchAmount = MoneyUtil.add(bsSubAccount.getAvailableBalance(), bsSubAccount.getRedAvailableBalance()).doubleValue();
				if(can2matchAmount.compareTo(limitAmount)>= 0 && thisAmount!=0 && needMatchAmount>0) {
					//logger.info("==============借款id:"+loanId+"匹配站岗户id:"+bsSubAccount.getId()+"=========================");
					Double baseAmount = needMatchAmount; //基础数确认
					if (can2matchAmount.compareTo(needMatchAmount) < 0) {
						baseAmount = can2matchAmount;
					}
					//判断基础数是否小于最低匹配值，若小于，则跳过该subAccount数据的匹配
					//logger.info("==============baseAmount:"+baseAmount+"limitAmount:"+limitAmount+"=========================");
					if (baseAmount.compareTo(limitAmount) < 0) {
					    continue;
					}
					//获得匹配金额
					thisAmount = loanRelationshipService.getThisAmountNew(baseAmount, bsSubAccount.getAgentId(), limitAmount, needMatchAmount);
					if(thisAmount.compareTo(0d) <= 0){
						continue;
					}
					//返回list添加值
					LoanRelation4DepVO initLoanRelation = new LoanRelation4DepVO();
					initLoanRelation.setHfUserId(bsSubAccount.getHfUserId());

					Double matchRedAmount=0d; //红包金额匹配初始化
					Double matchAuthAmount=thisAmount; //站岗户金额匹配初始化
					//判断是否用到红包户
					if(bsSubAccount.getRedAvailableBalance() > 0 ){
						matchRedAmount = getSmallerAmount(bsSubAccount.getRedAvailableBalance(),thisAmount);

						matchAuthAmount = MoneyUtil.subtract(thisAmount,matchRedAmount).doubleValue()<=0 ? 0d : MoneyUtil.subtract(thisAmount,matchRedAmount).doubleValue();

						initLoanRelation.setBsSubAccountId_red(bsSubAccount.getRedSubAccountId());
					}

					//新增债权关系记录，状态为PAYING 借款付款中
					LnLoanRelation loanRelation =addNewRelation(loanId, lnUserId, lnSubAccountId, bsSubAccount.getId(), bsSubAccount.getUserId(),
							thisAmount, Constants.LOAN_RELATION_STATUS_PAYING, Constants.TRANS_MARK_NORMAL, matchRedAmount);

					initLoanRelation.setLnLoanRelation(loanRelation);
					initLoanRelation.setCouponAmount(matchRedAmount);//抵用金
					initLoanRelation.setSelfAmount(matchAuthAmount);//自费金额
					retList.add(initLoanRelation);

					//冻结相应AUTH户的金额和RED户金额
					depFixedLoanAccountService.chargeLoanFreeze(matchAuthAmount, bsSubAccount.getId(), matchRedAmount, bsSubAccount.getRedSubAccountId());
					//需匹配金额 = 原需匹配金额 - 该次匹配的金额
					needMatchAmount = MoneyUtil.subtract(needMatchAmount, thisAmount).doubleValue();
					can2matchAmount = MoneyUtil.subtract(can2matchAmount, thisAmount).doubleValue();

					if(needMatchAmount.compareTo(0d) == 0){
						break;
					}
				 }

			}
			if(initNeedMatchAmount.compareTo(needMatchAmount) == 0){
				//一轮匹配后，未发生改变，则跳出
				break;
			}
			if(needMatchAmount.compareTo(0d) > 0){
				list = bsSubAccountMapper.zsdNormalWait4MatchList(Constants.PRODUCT_TYPE_AUTH_ZSD,getDepVIPUserList(VIPId4PartnerEnum.ZSD.getVipIdKey()), limitAmount, null);
			}
		}
		returnVo.setDepRelationList(retList);
        returnVo.setBorrowAmount(needMatchAmount);

        return returnVo;
	}

	/**
	 * 赞时贷vip匹配
	 * @param needMatchAmount 目前需匹配金额
	 * @param loanId
	 * @param lnUserId
	 * @param lnSubAccountId
	 * @return
	 */
	protected LoanRelationMatchReturnVO vipZSDMatch(Double needMatchAmount,
			Integer loanId, Integer lnUserId, Integer lnSubAccountId) {
		LoanRelationMatchReturnVO returnVo = new LoanRelationMatchReturnVO();
		List<LoanRelation4DepVO> retList = new ArrayList<LoanRelation4DepVO>();
		List<BsSubAccountVO4DepFixedMatch> list = bsSubAccountMapper.zsdVIPWait4MatchList(Constants.PRODUCT_TYPE_AUTH_ZSD,getDepVIPUserList(VIPId4PartnerEnum.ZSD.getVipIdKey()));
		if(CollectionUtils.isNotEmpty(list)){
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
			for (BsSubAccountVO4DepFixedMatch bsSubAccount : list) {
				//此次需要撮合金额
				Double matchAmount = needMatchAmount;
			}
			//债权匹配时低于该金额的不进行债权承接
			Double limitAmount = getMatchLimitAmount();
			for (BsSubAccountVO4DepFixedMatch bsSubAccount : list) {
				Double VIPAmount = bsSubAccount.getAvailableBalance();
				while (VIPAmount != 0 && needMatchAmount != 0) {
					//如果VIP可借金额小于最小可接债权或者剩余借款小于最小可接债权，则不匹配
	                if (VIPAmount < limitAmount || needMatchAmount < limitAmount) {
	                    break;
	                }
	                Double thisAmount = 0d;//此次撮合金额
	                if(needMatchAmount <= 3000){
	                	if(needMatchAmount<=VIPAmount){
	                		//needMatchAmount<=VIP余额，则VIP直接出借金额needMatchAmount
	                		thisAmount = needMatchAmount;
	                	}else{
		                	/**
							 * needMatchAmount>VIP余额，则判断：
							 * needMatchAmount-VIP余额>=1000,出借VIP余额，剩余借款余额c直接走理财人出借判断
							 * needMatchAmount-VIP余额<1000,不匹配，needMatchAmount直接给理财人（站岗余额>1000的用户）
							 */
		                	if(MoneyUtil.subtract(needMatchAmount, VIPAmount).doubleValue() >= limitAmount){
		                		thisAmount = VIPAmount;
		                	}else{
		                		break;
		                	}
	                	}

	                }else{//需撮合金额>3000
	                	if(needMatchAmount<=VIPAmount){
	                	/**
	                	 * needMatchAmount<=VIP余额，摇随机值[1000~3000]：
	                	 * needMatchAmount-随机值>=1000,则出借随机值
	                	 * needMatchAmount-随机值<1000，则重新摇随机值
	                	 */
	                	while(thisAmount == 0d){
	                		Double randAmount = getRandom(vipMin,vipMax);
							if(MoneyUtil.subtract(needMatchAmount, randAmount).doubleValue() >= vipMin){
								thisAmount = randAmount;
							}
	                	}

	                	}else{
	                	/**
	                	 * needMatchAmount>VIP余额，摇随机值[1000~3000]：
	                	 * VIP余额-随机值>=0,则判断
	                	 * b-随机值>=1000，则出借随机值
	                	 * b-随机值<1000,则重新摇随机值
	                	 * VIP余额-随机值<0，则判断
	                	 * b-VIP余额>=1000,则出借VIP余额
	                	 * b-VIP余额<1000,则重新摇随机值
	                	 */
	                	while(thisAmount == 0d){
	                		Double randAmount = getRandom(vipMin,vipMax);
	                		if(MoneyUtil.subtract(VIPAmount,randAmount).doubleValue() >= 0){
	                			if(MoneyUtil.subtract(needMatchAmount, randAmount).doubleValue() >= vipMin)
	                				thisAmount = randAmount;
	                		}else{
	                			if(MoneyUtil.subtract(needMatchAmount, VIPAmount).doubleValue() >= vipMin)
	                				thisAmount = VIPAmount;
	                		}
	                	}
	                	}
	                }


	                //返回list添加值
	                LoanRelation4DepVO initLoanRelation = new LoanRelation4DepVO();
					initLoanRelation.setHfUserId(bsSubAccount.getHfUserId());
	                //新增债权关系记录，状态为PAYING 借款付款中
					LnLoanRelation loanRelation =addNewRelation(loanId, lnUserId, lnSubAccountId, bsSubAccount.getId(), bsSubAccount.getUserId(),
							thisAmount, Constants.LOAN_RELATION_STATUS_PAYING, Constants.TRANS_MARK_NORMAL, 0d);

					initLoanRelation.setLnLoanRelation(loanRelation);
					initLoanRelation.setCouponAmount(0d);//抵用金
					initLoanRelation.setSelfAmount(thisAmount);//自费金额
					retList.add(initLoanRelation);

					//冻结相应AUTH户的金额和RED户金额
					depFixedLoanAccountService.chargeLoanFreeze(thisAmount, bsSubAccount.getId(), 0d, bsSubAccount.getRedSubAccountId());
					//需匹配金额 = 原需匹配金额 - 该次匹配的金额
					needMatchAmount = MoneyUtil.subtract(needMatchAmount, thisAmount).doubleValue();
					//VIP剩余可匹配金额
					VIPAmount = MoneyUtil.subtract(VIPAmount, thisAmount).doubleValue();
					logger.info("===================【赞时贷】vip此次撮合金额："+thisAmount+"===================");

					if(needMatchAmount.compareTo(0d) == 0){
						break;
					}
				}
			}

		}
		returnVo.setDepRelationList(retList);
		returnVo.setBorrowAmount(needMatchAmount);

		return returnVo;
	}

	/**
	 * 取得[vipMin,vipMax]的整百随机数
	 * @param vipMin
	 * @param vipMax
	 * @return
	 */
	private Double getRandom(Integer vipMin, Integer vipMax) {
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
        return randAmount;
	}

	/**
	 * 赞时贷普通小额匹配
	 * @param needMatchAmount 目前需匹配金额
	 * @param loanId
	 * @param lnUserId
	 * @param lnSubAccountId
	 * @return
	 */
	protected LoanRelationMatchReturnVO smallNormalZSDMatch(Double needMatchAmount, Integer loanId,
		 Integer lnUserId, Integer lnSubAccountId) {
		LoanRelationMatchReturnVO returnVo = new LoanRelationMatchReturnVO();
		List<LoanRelation4DepVO> retList = new ArrayList<LoanRelation4DepVO>();
		Double limitAmount = getMatchLimitAmount();
		List<BsSubAccountVO4DepFixedMatch> list = bsSubAccountMapper.zsdNormalWait4MatchList(Constants.PRODUCT_TYPE_AUTH_ZSD,getDepVIPUserList(VIPId4PartnerEnum.ZSD.getVipIdKey()), null, limitAmount);
		if(CollectionUtils.isNotEmpty(list)){
			for (BsSubAccountVO4DepFixedMatch bsSubAccount : list) {
				//此次需要撮合金额
				Double matchAmount = needMatchAmount;
				//该笔投资可被匹配金额
				Double can2matchAmount = MoneyUtil.add(bsSubAccount.getAvailableBalance(), bsSubAccount.getRedAvailableBalance()).doubleValue();

				if (can2matchAmount.compareTo(needMatchAmount) <= 0 && MoneyUtil.subtract(needMatchAmount, can2matchAmount).doubleValue() >= limitAmount) {
					matchAmount = can2matchAmount;
	            } else if (can2matchAmount < needMatchAmount && MoneyUtil.subtract(needMatchAmount, can2matchAmount).doubleValue() < limitAmount) {
	                continue;
	            }
				logger.info("===================【赞时贷】小额普通此次撮合金额："+matchAmount+"===================");

				//返回list添加值
				LoanRelation4DepVO initLoanRelation = new LoanRelation4DepVO();
				initLoanRelation.setHfUserId(bsSubAccount.getHfUserId());

				Double matchRedAmount=0d; //红包金额匹配初始化
				Double matchAuthAmount=matchAmount; //站岗户金额匹配初始化
				//判断是否用到红包户
				if(bsSubAccount.getRedAvailableBalance() > 0 ){
					matchRedAmount = getSmallerAmount(bsSubAccount.getRedAvailableBalance(),matchAmount);

					matchAuthAmount = MoneyUtil.subtract(matchAmount,matchRedAmount).doubleValue()<=0 ? 0d : MoneyUtil.subtract(matchAmount,matchRedAmount).doubleValue();

					initLoanRelation.setBsSubAccountId_red(bsSubAccount.getRedSubAccountId());
				}

				//新增债权关系记录，状态为PAYING 借款付款中
				LnLoanRelation loanRelation =addNewRelation(loanId, lnUserId, lnSubAccountId, bsSubAccount.getId(), bsSubAccount.getUserId(),
						matchAmount, Constants.LOAN_RELATION_STATUS_PAYING, Constants.TRANS_MARK_NORMAL, matchRedAmount);

				initLoanRelation.setLnLoanRelation(loanRelation);
				initLoanRelation.setCouponAmount(matchRedAmount);//抵用金
				initLoanRelation.setSelfAmount(matchAuthAmount);//自费金额
				retList.add(initLoanRelation);

				//冻结相应AUTH户的金额和RED户金额
				depFixedLoanAccountService.chargeLoanFreeze(matchAuthAmount, bsSubAccount.getId(), matchRedAmount, bsSubAccount.getRedSubAccountId());
				//需匹配金额 = 原需匹配金额 - 该次匹配的金额
				needMatchAmount = MoneyUtil.subtract(needMatchAmount, matchAmount).doubleValue();
				if(needMatchAmount.compareTo(0d) == 0){
					break;
				}
			}
		}
		returnVo.setDepRelationList(retList);
		returnVo.setBorrowAmount(needMatchAmount);

		return returnVo;
	}


	/**
	 * 普通小额匹配的最大额
	 * 且小额匹配时，剩余借款人未匹配金额低于该金额时，之前匹配有效，直接进入vip匹配
	 * @return
	 */
	protected Double getMatchLimitAmount() {
        BsSysConfig limit = sysConfigService.findConfigByKey(Constants.ZSD_MATCH_LIMIT_AMOUNT);//债权匹配时低于该金额的不进行债权承接
        Double limitAmount = 500d;//债权匹配时低于该金额的不进行债权承接，此额度一下为小额
        if (limit != null) {
            limitAmount = Double.valueOf(limit.getConfValue());
        }
        return limitAmount;
    }

	@Override
	public LnFinanceRepaySchedule getFinanceRepaySchedule4SevenRepay(
			LoanRelation4TransferVO record, Integer calDays, Date lastDay,
			Double agreementAmount, Date lastSettleTime) {
		/**
		 * 七贷利息还款时， 生成理财人还款计划数据
		 * 1、理财人计息天数从relation表创建时间到最后计息日（计息）止，为n
		 * 2、借款计息天数为N，若N<= n，则表示上次还款后未发生债权转让，则理财人计息天数为N;
		 * 	否则，理财人计息天数为n，并查询债转付息
		 * 3、币港湾的服务费为 agreementAmount-理财人分账
		 */
		LnFinanceRepaySchedule scheduleTemp = new LnFinanceRepaySchedule();
		scheduleTemp.setPlanTransInterest(0d);
		Date beginDate = record.getRelationBeginDate(); //出让人获取债权的时间或借款出账时间
		//判断上次还款时间和起息日，（上次还款时间+1）>起息日,则起息时间为上次还款时间+1
		if(lastSettleTime.compareTo(beginDate) > 0 ){
			beginDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(lastSettleTime));
			logger.info("============七贷【还款处理】出让人/理财人获取债权的时间或借款出账时间："+beginDate+",上次还款后的起息时间为："+lastSettleTime+"================");
		}

		Date lastFinishInterestDate = record.getLastFinishInterestDate();
		if(lastFinishInterestDate != null && DateUtil.addDays(lastFinishInterestDate, -1).compareTo(lastDay) <0){
			logger.info("============七贷【还款处理】理财人最后计息日："+DateUtil.formatYYYYMMDD(DateUtil.addDays(lastFinishInterestDate, -1))
					+"，借款人该次借款最后计息日："+DateUtil.formatYYYYMMDD(lastDay)+"=============================");
			lastDay = DateUtil.addDays(lastFinishInterestDate, -1);
		}

		Integer financeInteretDays = DateUtil.getDiffeDay(lastDay,beginDate)+1; //起息日到最后一次计息日的所有计息天数（前包括，后为还款日包括）
		if(financeInteretDays < 0 ) financeInteretDays=0;
		if(calDays - financeInteretDays <= 0){
			//上次还款后未发生债权转让，理财人计息天数以借款计息天数为准
			financeInteretDays = calDays;
			scheduleTemp.setPlanTransInterest(0d);
			logger.info("============七贷【还款处理】还款计息天数："+calDays+"，理财人债权起息日："+DateUtil.formatYYYYMMDD(beginDate)
					+"结束计息时间："+DateUtil.formatYYYYMMDD(lastDay)+"，计息天数："+financeInteretDays+"，所以上次还款后未发生债权转让，理财人计息天数以借款计息天数为准=============================");
		}else{
			if(Constants.TRANS_MARK_TRANS_IN.equals(record.getTransMark())){
				//上次还款后发生债权转让，理财人计息天数以债权债转后计息天数为准，并记上次债转付息
				scheduleTemp.setPlanTransInterest(record.getLastPayInterest());
				logger.info("============七贷【还款处理】还款计息天数："+calDays+"，理财人债权起息日："+DateUtil.formatYYYYMMDD(beginDate)
						+"结束计息时间："+DateUtil.formatYYYYMMDD(lastDay)+"，计息天数："+financeInteretDays+"，所以上次还款后发生债权转让，理财人计息天数以债权债转后计息天数为准，且为转入债权记上次债转付息=============================");
			}else{
				scheduleTemp.setPlanTransInterest(0d);
				logger.info("============七贷【还款处理】还款计息天数："+calDays+"，理财人债权起息日："+DateUtil.formatYYYYMMDD(beginDate)
						+"结束计息时间："+DateUtil.formatYYYYMMDD(lastDay)+"，计息天数："+financeInteretDays+"，所以上次还款后未发生债权转让=============================");
			}

		}
		//理财人应得利息 = 计息本金*产品利率*理财人持有天数/365;
		Double interestAmount =  calInterest(financeInteretDays, record.getBaseRate(), record.getLeftAmount(), 2);
		logger.info("============七贷【还款处理】还款理财人应得利息 = 计息本金*产品利率*理财人持有天数/365="
				+record.getLeftAmount()+"*"+record.getBaseRate()+"*"+financeInteretDays+"/365="+interestAmount+"======================");
		scheduleTemp.setPlanInterest(interestAmount);
		//手续费（币港湾营收） = 协议利率利息 - 理财人应得利息 - 上次债转付息
		Double planFee = MoneyUtil.subtract(MoneyUtil.subtract(agreementAmount, interestAmount).doubleValue()
				,scheduleTemp.getPlanTransInterest()).doubleValue();
		scheduleTemp.setPlanFee(planFee);
		return scheduleTemp;
	}


	/**
	 * 七贷债权转让时， 生成理财人还款计划数据
	 * @param record
	 * @param lastDay 最后计息日，传入当天
	 * @return
	 */
	@Override
	public LnFinanceRepaySchedule getFinanceRepaySchedule4SevenTransfer(
			LoanRelation4TransferVO record, Date lastDay) {
		/**
		 * 1、初始起息日beginDate=出让人获取债权的时间或借款出账时间
		 * 2、根据loanId查询账单业务处理信息表ln_bill_biz_info，查询上次还款时间repayTime
		 * 		若无数据，则起息日不变，否则，判断repayTime是否在beginDate之后，是，则beginDate=repayTime，否则不变
		 *
		 */
		//承接债权后还款，应付理财人的债权垫付金额
		Double planTransInterest = 0d;

		//出让人获取债权的时间或借款出账时间
		Date beginDate = record.getRelationBeginDate();
		logger.info("============七贷【债转】relation.id="+record.getId()+"，出让人获取债权的时间或借款出账时间="+DateUtil.formatYYYYMMDD(beginDate)+"============================");
		//查询上次还款时间
    	LnBillBizInfo billBizInfo = billBizInfoMapper.selectLastByLoanId(record.getLoanId());
    	if(billBizInfo != null && billBizInfo.getRepayTime() != null){
    		Date lastRepayDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(billBizInfo.getRepayTime()));
    		logger.info("============七贷【债转】relation.loan_id="+record.getLoanId()+"，上次还款时间="+DateUtil.formatYYYYMMDD(billBizInfo.getRepayTime())+"============================");
    		if(lastRepayDate.compareTo(beginDate) >= 0){
    			//还款发生在债权成立之后，则起息日为上次还款日+1
    			beginDate = DateUtil.addDays(lastRepayDate,1);
    		}
    	}
    	logger.info("============七贷【债转】relation.id="+record.getId()+"，出让人债权起息日="+DateUtil.formatYYYYMMDD(beginDate)+"============================");

    	Date finishDate = lastDay;//初始化最后计息时间的后一天，若自然回款日小于（最后一次计息日的后一天），finishDate=自然回款日，否则为（最后一次计息日的后一天）
		Date lastFinishInterestDate = record.getLastFinishInterestDate();//理财人自然回款日（回款日不计息）
		if(lastFinishInterestDate!=null && lastFinishInterestDate.compareTo(finishDate) < 0){
			finishDate = lastFinishInterestDate;
		}
		Integer interestDays = DateUtil.getDiffeDay(finishDate,beginDate); //起息日到最后一次计息日的所有计息天数（前包括，后不包括）
		logger.info("============七贷【债转】relation.id="+record.getId()+"，出让人债权最后计息日="+DateUtil.formatYYYYMMDD(finishDate)+"，计息天数："+interestDays+"=============================");

		if(interestDays < 0){
			interestDays = 0;
			logger.info("============【还款处理】异常情况，计息天数为0============================");
		}
		//理财人应得利息 = 计息本金*产品利率*理财人持有天数/365;
		Double interestAmount =  calInterest(interestDays, record.getBaseRate(), record.getLeftAmount(), 2);

		//承接人应付出利息 = 计息本金*【协议利率】*理财人持有天数/365
		Double B_out_amount = calInterest(interestDays, record.getAgreementRate(), record.getLeftAmount(), 2);

		/**
		 * 承接人应付出本息 = 计息本金*【协议利率】*理财人持有天数/365+计息本金/债权本金*债转付息+本金
		 * 转让人产品本息收益 = 计息本金*【产品利率】*理财人持有天数/365+计息本金/债权本金*债转付息+本金
		 * 转让人付出手续费=承接人付出本息-转让人产品本息收益-补差 ,
		 * 不包含补差部分则 = 计息本金*【协议利率】*理财人持有天数/365- 计息本金*【产品利率】*理财人持有天数/365
		 */

		Double planFee = MoneyUtil.subtract(B_out_amount, interestAmount).doubleValue();

		LnFinanceRepaySchedule scheduleTemp = new LnFinanceRepaySchedule();
		scheduleTemp.setPlanFee(planFee);
		scheduleTemp.setPlanInterest(interestAmount);
		scheduleTemp.setPlanPrincipal(record.getLeftAmount());
		scheduleTemp.setPlanTransInterest(planTransInterest);


		return scheduleTemp;
	}

	/**
	 * 包含自由资金债权转让的具体转让，包括冻结站岗户红包户，调用恒丰标的转让，转让成功和失败的相关操作
	 * @author bianyatian
	 * @param record 转出的对象数据
	 * @param inSubAccount 受让站岗户信息
	 * @param matchAuthAmount 受让人站岗户付出金额
	 * @param matchRedAmount 受让人红包户付出金额
	 * @param planFee 平台营收
	 * @param transInAmount 受让人付出金额除本金部分
	 * @param diffAmount 补差户应减金额
	 * @param diffActId 补差户id
	 */
	@Override
	public Map<String, Object> doTransfer4Free(final LoanRelation4TransferVO record, final BsSubAccountVO4DepFixedMatch inSubAccount,
			final Double matchAuthAmount, final Double matchRedAmount, final Double planFee,
			final Double transInAmount, final Double diffAmount, final Integer diffActId) {
		try {
			final Double principal = record.getLeftAmount();
			final Integer inAuthSubId = inSubAccount.getId();
			final Integer inRedSubId = inSubAccount.getRedSubAccountId();
			final String inHfUserId = inSubAccount.getHfUserId();
			
			jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEPFIXED_LOAN_MATCH.getKey());
			transactionTemplate.execute(new TransactionCallbackWithoutResult(){
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					//冻结相应AUTH户的金额和RED户金额
					depFixedLoanAccountService.chargeLoanFreeze(matchAuthAmount, inAuthSubId, matchRedAmount, inRedSubId);
					
					LnDepositionTarget depositionTarget = lnDepositionTargetMapper.selectByLoanId(record.getLoanId());
					/**
					 * 调用存管标的转让
					 * 交易金额=自费价格出让人手续费+受让人手续费+转让收益
					 * 自费价格=债转本金-抵用券金额
					 * 抵用券金额=匹配的红包金额，如没有则为0
					 * 出让人手续费=转让人付出手续费
					 * 转让收益=承接人债转付息
					 * 收益出资方：受让人
					 */
					B2GReqMsg_HFBank_TransferDebt transReq = new B2GReqMsg_HFBank_TransferDebt();
					transReq.setPlatcust(record.getHfUserId());
					transReq.setProd_id(depositionTarget.getId().toString());
					//自费价格=债转本金-抵用券金额
					Double deal_amount = MoneyUtil.subtract(principal,matchRedAmount).doubleValue();
					transReq.setDeal_amount(deal_amount);
					//抵用劵金额
					transReq.setCoupon_amt(matchRedAmount);
					//出让人手续费
					List<TransferDebtReqCommission> commissionList = new ArrayList<TransferDebtReqCommission>();
					TransferDebtReqCommission commission = new TransferDebtReqCommission();
					commission.setPayout_amt(MoneyUtil.defaultRound(new BigDecimal(planFee)).toString());
					commission.setPayout_plat_type(Constants.PAYOUT_PLAT_TYPE_FEE);
					commissionList.add(commission);
					transReq.setCommission(commissionList);

					//转让收益
					transReq.setTransfer_income(transInAmount);

					//交易金额=自费价格+出让人手续费+受让人手续费+转让收益
					Double trans_amt = MoneyUtil.add(
							MoneyUtil.add(deal_amount, planFee).doubleValue(),transInAmount).doubleValue();
					transReq.setTrans_amt(trans_amt);
					//受让人平台客户编号
					transReq.setDeal_platcustprivate(inHfUserId);
					//收益出资方账户
					transReq.setIncome_acct(inHfUserId);
					String orderNo = Util.generateOrderNo4BaoFoo(8);
					transReq.setOrder_no(orderNo);
					//转让份额
					transReq.setTrans_share(principal);
					logger.info("=============【债权转让】调用存管标的转让============================");

					B2GResMsg_HFBank_TransferDebt transRes = hfbankTransportService.transferDebt(transReq);

					if(!ConstantUtil.BSRESCODE_SUCCESS.equals(transRes.getResCode())){
						logger.info("=============【债权转让】调用存管标的转让,失败：ResCode:"+transRes.getResCode()+",Recode:"+transRes.getRecode()+"============================");
						String orderNoNew = Util.generateOrderNo4BaoFoo(8);
						transReq.setOrder_no(orderNoNew);
						transRes = hfbankTransportService.transferDebt(transReq);
						if(!ConstantUtil.BSRESCODE_SUCCESS.equals(transRes.getResCode())){
							logger.info("=============【债权转让】再次调用存管标的转让,失败：ResCode:"+transRes.getResCode()+",Recode:"+transRes.getRecode()+"============================");
							// 再次转让失败
							//解冻相应AUTH户的金额和RED户金额
							depFixedLoanAccountService.chargeLoanUnFreeze(matchAuthAmount, inAuthSubId, matchRedAmount, inRedSubId);
							//发送告警短信
							//specialJnlService.warn4FailNoSMS(transInSumAmount, "理财人退出转让失败，relationId:"+record.getId(), orderNo, "债权转让失败");
						}else{
							logger.info("=============【债权转让】调用存管标的转让,成功============================");
							//调用转让成功库表操作
							doChange4LoanRelation(record, inSubAccount, MoneyUtil.add(transInAmount, principal).doubleValue(), matchRedAmount);

							//债权转让记账
							TransferAccountInfo transferInfo = new TransferAccountInfo();
							transferInfo.setOutUser_investorAuthActId(record.getBsSubAccountId());
							transferInfo.setOutUser_principalAmount(record.getLeftAmount());
							transferInfo.setOutUser_interestAmount(CalculatorUtil.calculate("a-a-a",transInAmount,diffAmount,planFee));
							transferInfo.setOutUser_diffActId(diffActId);
							transferInfo.setDiffFee(diffAmount);
							transferInfo.setFee(planFee);//币港湾营收=转让人付出手续费
							transferInfo.setInUser_authAmount(matchAuthAmount);
							transferInfo.setInUser_investorAuthActId(inSubAccount.getId());
							transferInfo.setInUser_redAmount(matchRedAmount);
							transferInfo.setInUser_investorRedActId(inSubAccount.getRedSubAccountId());
							transferInfo.setPartnerEnum(PartnerEnum.getEnumByCode(record.getPartnerCode()));
							transferInfo.setOut_relationId(record.getId());
							depFixedRepayAccountService.chargeRelationTransfer(transferInfo);
						 }
		    		}else{
		    			logger.info("=============【债权转让】调用存管标的转让,成功============================");
		    			//调用转让成功库表操作
						doChange4LoanRelation(record, inSubAccount, MoneyUtil.add(transInAmount, principal).doubleValue(), matchRedAmount);

						//债权转让记账
						TransferAccountInfo transferInfo = new TransferAccountInfo();
						transferInfo.setOutUser_investorAuthActId(record.getBsSubAccountId());
						transferInfo.setOutUser_principalAmount(record.getLeftAmount());
						transferInfo.setOutUser_interestAmount(CalculatorUtil.calculate("a-a-a",transInAmount,diffAmount,planFee));
						transferInfo.setOutUser_diffActId(diffActId);
						transferInfo.setDiffFee(diffAmount);
						transferInfo.setFee(planFee);//币港湾营收=转让人付出手续费
						transferInfo.setInUser_authAmount(matchAuthAmount);
						transferInfo.setInUser_investorAuthActId(inSubAccount.getId());
						transferInfo.setInUser_redAmount(matchRedAmount);
						transferInfo.setInUser_investorRedActId(inSubAccount.getRedSubAccountId());
						transferInfo.setPartnerEnum(PartnerEnum.getEnumByCode(record.getPartnerCode()));
						transferInfo.setOut_relationId(record.getId());
						depFixedRepayAccountService.chargeRelationTransfer(transferInfo);
		    		}
				}

			});
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEPFIXED_LOAN_MATCH.getKey());
		}

		// 切面使用参数
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("loanRelationId", record.getId());
		dataMap.put("partnerEnum", PartnerEnum.getEnumByCode(record.getPartnerCode()));
		// 生成债转让协议签章(云贷、七贷)
		return dataMap;
	}

	/**
	 * 云贷等额本息-债权转让 生成理财人还款计划数据，仅生成数据返回不插入
	 */
	@Override
	public LnFinanceRepaySchedule getFinanceRepaySchedule4FixedInstallmentTransfer(
			LoanRelation4TransferVO record) {
		LnFinanceRepaySchedule scheduleTemp = new LnFinanceRepaySchedule();
		//当天日期
        Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
		//当期账单日为空时，调用方法，获取当期账单日
		if(record.getThisRepayPlanDate() == null){
			YunRepayPlanVO repayPlan = YunInstalmentRepayPlanUtil.calRepayPlan(record.getLoanDate(),
					today, record.getLoanPeriod());
			record.setThisRepayPlanDate(repayPlan.getPlanDate());
			record.setThisRepayPlanSerialId(repayPlan.getSerialId());
		}
		//当期天数 = 上次还款计划还款日期 != null ? 上次还款计划还款日期 -当期账单日期 (前不包括，后包括): 借款日期- 当期账单日期 (前包括，后包括)
		Integer termDays = record.getLastRepayPlanDate() != null ? DateUtil.getDiffeDay(record.getThisRepayPlanDate(), record.getLastRepayPlanDate()) 
				: DateUtil.getDiffeDay(record.getThisRepayPlanDate(), record.getLoanDate())+1;
		
		//最后计息日（该日不计息）= 回款日<当日 ? 回款日:当日
		Date lastCalDate = record.getLastFinishInterestDate() != null && record.getLastFinishInterestDate().compareTo(today) < 0 ? record.getLastFinishInterestDate() : today;
		//最近一次还款的计划还款日
		Date lastRepayPlanDate = record.getLastRepayPlanDate();
		
		if(lastRepayPlanDate != null && lastCalDate.compareTo(lastRepayPlanDate) <= 0){
			/**
			 * 若本次执行债转日（理财人投资的最后计息日）=<最近一次还款日（账单日）
			 * 说明最后计息日 所在 借款期次已经还款，承接人只需支付本金
			 * 承接人应付利息=0，币港湾应收手续费=0，出让人应收利息=0，承接人应付本金=当前剩余本金
			 */
            scheduleTemp.setPlanTotal(record.getLeftAmount());
            scheduleTemp.setPlanPrincipal(record.getLeftAmount());
            scheduleTemp.setPlanFee(0d);
            scheduleTemp.setPlanInterest(0d);
            scheduleTemp.setPlanTransInterest(0d);
			
		}else{
			//债转付息初始化
			Double planTransInterest = 0d;
			//债权起息日初始化=债权成立日
			Date relationBeginDate = record.getRelationBeginDate();
			if(Constants.TRANS_MARK_TRANS_IN.equals(record.getTransMark())){
				/**
				 * B=出让债权的获得债权成立日，
				 * I=最近一次还款日（计划还款日期）-lastRepayPlanDate
				 * 1）若C=“TRANS_IN”，I=null，则从未发生过还款，则从起息日J=债权成立日 有债转付息
				 * 2）若C=“TRANS_IN”，且从获得债权成立日起未发生还款（B>=I），则从债权成立日开始计息（包括债权成立日）,起息日J=债权成立日；有债转付息
				 * 1）和2）合并判断处理
				 * 3）若C=“TRANS_IN”，（B>I）且从获得债权成立日起已发生还款，则从上次还款日后一日开始计息,起息日J=上次还款后一日；无债转付息
				 */
				if( lastRepayPlanDate == null || 
						(lastRepayPlanDate != null  && record.getRelationBeginDate().compareTo(lastRepayPlanDate) >= 0)){
					planTransInterest = record.getLastPayInterest();
				}else {
					relationBeginDate = DateUtil.addDays(lastRepayPlanDate, 1);
				}
			}else{
				/**
				 * 4）若C!=“TRANS_IN”，且未发生还款，则从债权成立日开始计息（包括债权成立日）,起息日J=债权成立日；G=0；
				 * 5）若C!=“TRANS_IN”，且已发生还款，则从上次还款日后一日开始计息,起息日J=上次还款后一日；G=0；
				 */
				if( lastRepayPlanDate != null){
					relationBeginDate = DateUtil.addDays(lastRepayPlanDate, 1);
				}
			}
			//债权占有日期 = 债权起息日-最后计息日(最后计息日为债权转让当日或理财人回款日，不计息)
			Integer userDays = DateUtil.getDiffeDay(lastCalDate, relationBeginDate);
			/**
			 * 借款协议利息=【调用等额本息方法获得利息，等额本息方式计算某期利息；
			 * 入参本金为债权初始本金（承接后）；入参利率为借款协议利率13%；入参总期数债转后期数（借款期数-匹配表的first_term+1）；
			 * 为入参当前期数为债转后的还款期数（借款人还款期数-匹配表的first_term+1）】*计息天数（持有天数）/当期总计息天数
			 */
			AverageCapitalPlusInterestVO agreementVo = algorithmService.calAverageCapitalPlusInterestPlan4Serial(
					record.getInitAmount(),  (record.getLoanPeriod()-record.getFirstTerm()+1), 
					MoneyUtil.divide(record.getAgreementRate(),100,4).doubleValue() , (record.getThisRepayPlanSerialId()-record.getFirstTerm()+1));
			//借款协议利息
			Double repayAgreementInterest = CalculatorUtil.calculate("a*a/a",agreementVo.getPlanInterest(),userDays.doubleValue(),termDays.doubleValue());
			
			/**
			 * 产品利息（出让人应收利息）=【调用等额本息方法，等额本息方式计算某期利息；
			 * 入参本金为债权初始本金（承接后）；入参利率为产品利率8%；入参总期数债转后期数（借款期数-匹配表的first_term+1）；
			 * 为入参当前期数为债转后的还款期数（借款人还款期数-匹配表的first_term+1）】*计息天数（持有天数）/当期总天数  +G
			 */
			AverageCapitalPlusInterestVO interestVo = algorithmService.calAverageCapitalPlusInterestPlan4Serial(
					record.getInitAmount(), (record.getLoanPeriod()-record.getFirstTerm()+1), 
					MoneyUtil.divide(record.getBaseRate(), 100, 4).doubleValue(), 
					(record.getThisRepayPlanSerialId()-record.getFirstTerm()+1));
			
			//应回理财人利息
			Double repay2UserInterest = CalculatorUtil.calculate("a*a/a",interestVo.getPlanInterest(),userDays.doubleValue(),termDays.doubleValue());
		
			//币港湾服务费 = 协议利息-利息
			Double fee = MoneyUtil.subtract(repayAgreementInterest, repay2UserInterest).doubleValue();
			
			//承接人应付 = 借款协议利息+债转本金+债转付息G
			Double planTotal = CalculatorUtil.calculate("a+a+a",record.getLeftAmount(),repayAgreementInterest,planTransInterest);
			scheduleTemp.setPlanTotal(planTotal);
            scheduleTemp.setPlanPrincipal(record.getLeftAmount());
            scheduleTemp.setPlanFee(fee);
            scheduleTemp.setPlanInterest(repay2UserInterest);
            scheduleTemp.setPlanTransInterest(planTransInterest);
		}
		logger.info("等额本息承接人应付金额，planTotal="+scheduleTemp.getPlanTotal()+",PlanPrincipal="+scheduleTemp.getPlanPrincipal()+",PlanFee="+scheduleTemp.getPlanFee()
				+",PlanInterest="+scheduleTemp.getPlanInterest()+",PlanTransInterest="+scheduleTemp.getPlanTransInterest());
		return scheduleTemp;
	}
	
	/**
	 * 云贷等本等息-债权转让 生成理财人还款计划数据，仅生成数据返回不插入
	 * 生成plan_total,plan_principal,plan_interest,plan_fee,plan_trans_interest
	 * 转让人A1收息 (repay2UserInterest)
	 * 承接人A2付息  (repayAgreementInterest)
	 * 币港湾营收(恒丰) = 承接人A2付息 - 转让人A1应收本息 ( 补差在上层计算 , 本方法未做补差减法 ) 
	 */
	@Override
	public LnFinanceRepaySchedule getFinanceRepaySchedule4FixedPrincipalInterestTransfer(
			LoanRelation4TransferVO record) {
		LnFinanceRepaySchedule scheduleTemp = new LnFinanceRepaySchedule();
		//当天日期
        Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
		//当期账单日为空时，调用方法，获取当期账单日
		if(record.getThisRepayPlanDate() == null){
			YunRepayPlanVO repayPlan = YunInstalmentRepayPlanUtil.calRepayPlan(record.getLoanDate(),
					today, record.getLoanPeriod());
			record.setThisRepayPlanDate(repayPlan.getPlanDate());
			record.setThisRepayPlanSerialId(repayPlan.getSerialId());
		}
		logger.info("LoanRelation4TransferVO>>>>>>>{"+JSONObject.toJSONString(record)+"}");
		//当期天数 =上次还款计划还款日期 !=null?上次还款计划还款日期 -当期账单日期 (前不包括,后包括):借款日期-当期账单日期 (前包括,后包括)
		Integer termDays = record.getLastRepayPlanDate() != null ? DateUtil.getDiffeDay(record.getThisRepayPlanDate(), record.getLastRepayPlanDate()) 
				: DateUtil.getDiffeDay(record.getThisRepayPlanDate(), record.getLoanDate())+1;
		
		//最后计息日(该日不计息)=回款日<当日?回款日:当日
		Date lastCalDate = record.getLastFinishInterestDate() != null && record.getLastFinishInterestDate().compareTo(today) < 0 ? record.getLastFinishInterestDate() : today;
		//最近一次还款的计划还款日
		Date lastRepayPlanDate = record.getLastRepayPlanDate();
		logger.info("当期天数{termDays}="+termDays+"当天日期{today}="+today+"最后一次还款的计划还款日{lastRepayPlanDate}="
					+lastRepayPlanDate+"最后计息日{lastCalDate}="+lastCalDate);
		if( lastRepayPlanDate != null && lastCalDate.compareTo(lastRepayPlanDate) <= 0 ) {
			/**
			 * 若本次执行债转日(理财人投资的最后计息日)=<最近一次还款日(账单日);
			 * 说明最后计息日 所在 借款期次已经还款,承接人只需支付本金;
			 * 承接人应付利息=0,币港湾应收手续费=0,出让人应收利息=0,承接人应付本金 =当前剩余本金;
			 */
            scheduleTemp.setPlanTotal(record.getLeftAmount());
            scheduleTemp.setPlanPrincipal(record.getLeftAmount());
            scheduleTemp.setPlanFee(0d);
            scheduleTemp.setPlanInterest(0d);
            scheduleTemp.setPlanTransInterest(0d);
			
		} else {
			//债转付息G初始化
			Double planTransInterest = 0d;
			//债权起息日初始化=债权成立日
			Date relationBeginDate = record.getRelationBeginDate();
			
			if( Constants.TRANS_MARK_TRANS_IN.equals(record.getTransMark()) ) {
				/**
				 * B=出让债权的获得债权成立日,
				 * I=最近一次还款日(计划还款日期)-lastRepayPlanDate
				 * 1)若C="TRANS_IN",I=null,则从未发生过还款,则从起息日J=债权成立日 有债转付息
				 * 2)若C="TRANS_IN",且从获得债权成立日起未发生还款(B>=I),则从债权成立日开始计息(包括债权成立日),起息日J=债权成立日;有债转付息
				 * 1)和2)合并判断处理
				 * 3)若C="TRANS_IN",(B>I)且从获得债权成立日起已发生还款,则从上次还款日后一日开始计息,起息日J=上次还款后一日;无债转付息
				 */
				if( lastRepayPlanDate == null || 
						(lastRepayPlanDate != null  && record.getRelationBeginDate().compareTo(lastRepayPlanDate) >= 0)){
					planTransInterest = record.getLastPayInterest();
				} else {
					relationBeginDate = DateUtil.addDays(lastRepayPlanDate, 1);
				}
			} else {
				/**
				 * 4)若C!="TRANS_IN",且未发生还款,则从债权成立日开始计息(包括债权成立日),起息日J=债权成立日;G=0;
				 * 5)若C!="TRANS_IN",且已发生还款,则从上次还款日后一日开始计息,起息日J=上次还款后一日;G=0;
				 */
				if( lastRepayPlanDate != null){
					relationBeginDate = DateUtil.addDays(lastRepayPlanDate, 1);
				}
			}
			//债权占有日期 =债权起息日-最后计息日(最后计息日为当日或理财人回款日,不计息)
			Integer userDays = DateUtil.getDiffeDay(lastCalDate, relationBeginDate);
			
			/**
			 * 出借人借款协议本息=(出借人剩余本金*借款协议利率)/(剩余总本金*结算利率)*结算利息;
			 */
			AverageCapitalPlusInterestVO settleVO = algorithmService.calEqualPrincipalInterestPlan4Serial(record.getApproveAmount(), 
					record.getLoanPeriod(), MoneyUtil.divide(record.getBgwSettleRate(), 100, 2).doubleValue(), 
					record.getThisRepayPlanSerialId());
			
			logger.info("转让人债权持有天数{userDays}="+userDays+"还款的结算信息集合{还款期次,结算本息,应还本金,结算利息}="+JSONObject.toJSONString(settleVO));
			
			Double leftTotalPrincipal = lnLoanRelationMapper.sumLeftAmountByLoanId(record.getLoanId());
			
			Double repayAgreementInterest = CalculatorUtil.calculate("a*a*a*a/(a*a*a)",
					record.getLeftAmount(), record.getAgreementRate(), settleVO.getPlanInterest(),userDays.doubleValue(),
					leftTotalPrincipal, record.getBgwSettleRate(), termDays.doubleValue());
			
			logger.info("(承接人付息)={"+record.getLeftAmount()+"*"+record.getAgreementRate()+"*"+settleVO.getPlanInterest()+"*"+userDays.doubleValue()
					+"/("+leftTotalPrincipal+"*"+record.getBgwSettleRate()+"*"+termDays.doubleValue()+")="+repayAgreementInterest+"}");
			/**
			 * 产品利息(出让人应收利息)=(计息天数*出借人剩余本金*产品利率*结算利息)/(账单天数*剩余总本金*结算利率);
			 */
			Double repay2UserInterest = CalculatorUtil.calculate("a*a*a*a/(a*a*a)",
					userDays.doubleValue(), record.getLeftAmount(), record.getBaseRate(), settleVO.getPlanInterest(),
					termDays.doubleValue(), leftTotalPrincipal, record.getBgwSettleRate());
			
			logger.info("(转让人收息)={"+userDays.doubleValue()+"*"+record.getLeftAmount()+"*"+record.getBaseRate()+"*"+settleVO.getPlanInterest()
					+"/("+termDays.doubleValue()+"*"+leftTotalPrincipal+"*"+record.getBgwSettleRate()+")="+repay2UserInterest+"}");
		
			//币港湾服务费 =协议利息-利息;
			Double fee = MoneyUtil.subtract(repayAgreementInterest, repay2UserInterest).doubleValue();
			logger.info("币港湾服务费 ={"+repayAgreementInterest+"-"+repay2UserInterest+"="+fee+"}");
			//承接人应付=借款协议利息+债转本金+上一次债转付息G;
			Double planTotal = CalculatorUtil.calculate("a+a+a", record.getLeftAmount(),repayAgreementInterest,planTransInterest);
			logger.info("承接人应付金额={"+record.getLeftAmount()+"+"+repayAgreementInterest+"+"+planTransInterest+"="+planTotal+"}");
			scheduleTemp.setPlanTotal(planTotal);
            scheduleTemp.setPlanPrincipal(record.getLeftAmount());
            scheduleTemp.setPlanFee(fee);
            scheduleTemp.setPlanInterest(repay2UserInterest);
            scheduleTemp.setPlanTransInterest(planTransInterest);
		}
		
		return scheduleTemp;
	}
}
