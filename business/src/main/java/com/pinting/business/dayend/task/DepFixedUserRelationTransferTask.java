package com.pinting.business.dayend.task;

import com.pinting.business.accounting.finance.model.ProductType;
import com.pinting.business.accounting.finance.model.SubAccountCode;
import com.pinting.business.accounting.finance.service.UserDepFixedQuitService;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.enums.VIPId4PartnerEnum;
import com.pinting.business.accounting.loan.model.LoanRelation4TransferVO;
import com.pinting.business.accounting.loan.service.DepFixedLoanRelationshipService;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DispatcherService;
import com.pinting.business.coreflow.core.enums.BusinessTypeEnum;
import com.pinting.business.coreflow.transfer.util.ConstantsForTransfer;
import com.pinting.business.dao.BsDepositionQuitApplyMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.BsSubAccountPairMapper;
import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.model.BsDepositionQuitApply;
import com.pinting.business.model.BsDepositionQuitApplyExample;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.LnFinanceRepaySchedule;
import com.pinting.business.model.vo.BsDepositionQuitApplySubVO;
import com.pinting.business.model.vo.BsSubAccountVO4DepFixedMatch;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * @project business
 * @title DepFixedUserRelationTransferTask.java
 * @author Dragon & cat
 * @date 2017-4-5
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 1、固定期限产品到期退出进行债权转让（存管系统云贷自主放款、赞时贷、7贷）
 * 				2、VIP转普通
 *
 */
@Service
public class DepFixedUserRelationTransferTask {
	private Logger log = LoggerFactory.getLogger(DepFixedUserRelationTransferTask.class);
	private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	@Autowired
	private BsDepositionQuitApplyMapper bsDepositionQuitApplyMapper;
	@Autowired
	private LnLoanRelationMapper lnLoanRelationMapper;
	@Autowired
	private DepFixedLoanRelationshipService depFixedLoanRelationshipService;
	@Autowired
	private	UserDepFixedQuitService userDepFixedQuitService;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsSubAccountPairMapper bsSubAccountPairMapper;
	@Autowired
	private DispatcherService dispatchService;
	@Autowired
	private SysConfigService sysConfigService;

	public void execute() {
		log.info("==================【存管系统固定期限产品到期退出业务】定时开始 =================");


		try {
			log.info("==================【普通用户退出转让】开始 =================");
			//普通用户退出转让
			userQuitTransfer();
			log.info("==================【普通用户退出转让】开始 =================");
		} catch (Exception e) {
			log.error("==================异常：【普通用户退出转让】 普通债权转让异常 =================");
			e.printStackTrace();
		}

		try {
			log.info("==================【VIP退出转让】开始 =================");
			//VIP债权转让给普通用户
			vip2UserTransfer();
			log.info("==================【VIP退出转让】开始 =================");
		} catch (Exception e) {
			log.error("==================异常：【VIP退出转让】VIP退出转让异常 =================");
			e.printStackTrace();
		}


		log.info("==================【存管系统固定期限产品到期退出业务】定时结束 =================");

	}
	private void userQuitTransfer() {
		Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
		ProductType firstProType = SubAccountCode.productTypeMap.get(PartnerEnum.FREE);
		//查询预期执行日期在当日，状态为通过的 理财退出申请列表，自由资金优先
		List<BsDepositionQuitApplySubVO> applyList = bsDepositionQuitApplyMapper.select4Transfer(today, Constants.DEP_QUIT_APPLY_PASS, firstProType.getAuthCode());
		if(CollectionUtils.isEmpty(applyList)){
			log.info("==================【普通转让】理财退出申请列表为空=================");
			return;
		}
		//查询债权转让每次查询待转列表条数
		BsSysConfig config = sysConfigService.findConfigByKey(Constants.TRANSFER_RELATION_LIMIT_PAGE_SIZE);
		Integer pageSize = config != null ? Integer.valueOf(config.getConfValue()) : 50;
		
		for (BsDepositionQuitApplySubVO bsDepositionQuitApply : applyList) {
			//查询该笔投资待退出总条数
			Integer count = lnLoanRelationMapper.countRelationListWait2Transfer(bsDepositionQuitApply.getSubAccountId());
			//需多少次查询债转笔数
			Integer transferTime = count%pageSize == 0 ? count/pageSize : count/pageSize+1;
			
			log.info("==================【普通转让】待退出subAccountId："+bsDepositionQuitApply.getSubAccountId()+"开始，" +
					"待退出总条数:"+count+"，每次查询"+pageSize+"条，分"+transferTime+"次执行债转列表查询=================");
			for(int time = 1; time <= transferTime; time++ ){
				log.info("==================【普通转让】待退出subAccountId："+bsDepositionQuitApply.getSubAccountId()
						+"，第"+time+"次查询数据，进行债转");
				try {
					jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey(),300);
					//查询有债权关系的债权数据
					List<LoanRelation4TransferVO> relationList = lnLoanRelationMapper.getRelationListWait2Transfer(bsDepositionQuitApply.getSubAccountId(),pageSize);
					if(CollectionUtils.isNotEmpty(relationList)){
						for (LoanRelation4TransferVO record : relationList) {
							//前置判断
							if(record.getRelationBeginDate() == null || record.getBaseRate() == null){
								log.info("==================【普通转让】待转数据relationId："+record.getId()+"，债权起始日期或利率为空=================");
								continue;
							}
							if(record.getLeftAmount() <= 0){
								log.info("==================【普通转让】待转数据relationId：可转本金为空=================");
								continue;
							}
							//计算应还到出让人的金额数据
							LnFinanceRepaySchedule scheduleTemp = new LnFinanceRepaySchedule();
							FlowContext flowContext = new FlowContext();
							flowContext.setRes(scheduleTemp);
							flowContext.setTransCode(ConstantsForTransfer.TRANSFER_GET_NEED_PAY);
							flowContext.setBusinessType(record.getPartnerBusinessFlag());
							flowContext.setPartnerEnum(PartnerEnum.getEnumByCode(record.getPartnerCode()));
							flowContext.getExtendMap().put("loanRelation4TransferRecord", record);
	
							dispatchService.dispatcherService(flowContext);
							scheduleTemp = (LnFinanceRepaySchedule)flowContext.getRes();
							//承接人应付出让人利息
							Double planInterest = scheduleTemp.getPlanInterest() != null ? scheduleTemp.getPlanInterest() : 0d;
							//承接人应付出让人债转付息
							Double panTransInterest = scheduleTemp.getPlanTransInterest() != null ? scheduleTemp.getPlanTransInterest() : 0d;
							//承接人应付平台营收
							Double initPlanFee = scheduleTemp.getPlanFee() != null ? scheduleTemp.getPlanFee() : 0d;
	
							//承接人应付总利息= 承接人应付出让人利息+承接人应付出让人债转付息+承接人应付平台营收
							Double transInAmount = MoneyUtil.add(initPlanFee,
									MoneyUtil.add(planInterest, panTransInterest).doubleValue()).doubleValue();
							//承接人应付总本息
							Double  transInSumAmount= MoneyUtil.add(record.getLeftAmount(),transInAmount).doubleValue();
	
	
							//根据承接人应付本息获取承接人数据
							BsSubAccountVO4DepFixedMatch inSubAccountTemp = new BsSubAccountVO4DepFixedMatch();
							ProductType freeProductType = SubAccountCode.productTypeMap.get(PartnerEnum.FREE);
							//获取自由可承接
							inSubAccountTemp = getInSubAccount(transInSumAmount, record.getLeftAmount(), record.getBsUserId(), freeProductType, false);
							if(inSubAccountTemp == null){
								//对应资产端
								ProductType productType = SubAccountCode.productTypeMap.get(PartnerEnum.getEnumByCode(record.getPartnerCode()));
								inSubAccountTemp = getInSubAccount(transInSumAmount, record.getLeftAmount(), record.getBsUserId(), productType, false);
							}
							if(inSubAccountTemp == null){
								//自由VIP
								inSubAccountTemp = getInSubAccount(transInSumAmount, record.getLeftAmount(), record.getBsUserId(), freeProductType, true);
							}
							if(inSubAccountTemp == null){
								continue;
							}
							Double matchRedAmount=0d; //红包金额匹配初始化
							Double matchAuthAmount=transInSumAmount; //站岗户金额匹配初始化
							//判断是否用到红包户
							if(inSubAccountTemp.getRedAvailableBalance() > 0 ){
								//红包只能用于本金部分，若红包小于本金，则收取红包为本金部分
								matchRedAmount = inSubAccountTemp.getRedAvailableBalance().compareTo(record.getLeftAmount()) <=0
										? inSubAccountTemp.getRedAvailableBalance() : record.getLeftAmount();
								matchAuthAmount =  MoneyUtil.subtract(transInSumAmount,matchRedAmount).doubleValue();
								log.info("=============【债权转让】应冻结承接人RED户金额："+matchRedAmount+"，站岗户金额："+matchAuthAmount+"============================");
	
							}
							log.info("=============【债权转让】冻结承接人AUTH户："+inSubAccountTemp.getId()+"，金额："+matchAuthAmount+"============================");
							Double diffAmount = 0d;//实际补差B，暂时设为0
							Double realReturnInter = 0d;//实际还款利息X，暂时设为0
							Double planFee = 0d;//平台实际营收S，暂时设为0
							BsSubAccount diffAct = null;//补差户，暂时设为null
							//查询对应站岗户，计算实际需补差金额，实际应回理财人利息，实际平台营收
							BsSubAccount authAct = bsSubAccountMapper.selectByPrimaryKey(record.getBsSubAccountId());
							if(firstProType.getAuthCode().equals(authAct.getProductType())){
								//为自由站岗户
								Double leftPlanInterest = authAct.getLeftPlanInterest();//剩余应付利息Y
								log.info("出让人转让相关初始金额[relationId="+record.getId()+"]："
										+"应计利息="+scheduleTemp.getPlanInterest()+"/上次债转付息="+scheduleTemp.getPlanTransInterest()
										+"/实际受让人应付总本息="+transInSumAmount+"/实际还款本金="+record.getLeftAmount()
										+"/初始平台营收="+scheduleTemp.getPlanFee()+"/剩余应付利息="+leftPlanInterest);
	
								if(leftPlanInterest <= 0){
									//if 剩余应付利息<=0 实际回到理财人利息X=0，平台营收=承接人应付总利息= 承接人应付出让人利息+承接人应付出让人债转付息+承接人应付平台营收，补差B=0
									realReturnInter = 0d;
									diffAmount = 0d;
									planFee = transInAmount;
								}else{
									//承接人应付出让人利息+承接人应付出让人债转付息
									Double interestTemp = CalculatorUtil.calculate("a+a", planInterest, panTransInterest);
									if(leftPlanInterest.compareTo(interestTemp) == 0){
										//if 剩余应付利息=承接人应付出让人利息+承接人应付出让人债转付息 则X=Y,S=承接人应付平台营收，补差B=0
										realReturnInter = leftPlanInterest;
										diffAmount = 0d;
										planFee = initPlanFee;
									}else if(leftPlanInterest.compareTo(interestTemp) < 0){
										//else if 剩余应付利息<(承接人应付出让人利息+承接人应付出让人债转付息) 则X=Y,S=承接人应付平台营收+((承接人应付出让人利息+承接人应付出让人债转付息) -剩余应付利息)，补差B=0
										realReturnInter = leftPlanInterest;
										diffAmount = 0d;
										planFee = CalculatorUtil.calculate("a+a-a", initPlanFee, interestTemp, leftPlanInterest);
									}else if(leftPlanInterest.compareTo(interestTemp) > 0){
										//查询对应补差户，获得补差金额B = B>0 ? B : 0
										diffAct = bsSubAccountPairMapper.selectDiffActByLoanRelationId(record.getId());
										diffAmount = diffAct != null && diffAct.getBalance().compareTo(0d)>0 ? diffAct.getBalance() : 0d;
										//平台营收、补差再次确认
										//S=(IS-B)<0 ? 0 : (IS-B)
										planFee = (initPlanFee.compareTo(diffAmount) < 0) ? 0d : CalculatorUtil.calculate("a-a", initPlanFee, diffAmount);
										//B`=B= (IS-B)<0 ? IS : B
										diffAmount = (initPlanFee.compareTo(diffAmount) < 0) ? initPlanFee : diffAmount;
										Double diffAmountTemp = diffAmount; // B`=B
										//B= (Y-(I+T)) > B ? B : (Y-(I+T))
										diffAmount = (CalculatorUtil.calculate("a-a-a", leftPlanInterest, interestTemp, diffAmount) > 0 )
												? diffAmount : CalculatorUtil.calculate("a-a", leftPlanInterest, interestTemp);
										//S= S + (B`- B)
										planFee = CalculatorUtil.calculate("a+a-a", planFee, diffAmountTemp, diffAmount);
										//X= (I+T)+B
										realReturnInter = CalculatorUtil.calculate("a+a", interestTemp, diffAmount);
	
									}
								}
	
							}else{
								//非自由站岗户，无剩余利息
								//获取补差金额
								diffAct = bsSubAccountPairMapper.selectDiffActByLoanRelationId(record.getId());
								diffAmount = diffAct != null && diffAct.getBalance().compareTo(0d)>0 ? diffAct.getBalance() : 0d;//补差金额
								if(scheduleTemp.getPlanFee() < 0){
									log.info("=============【债权转让】转让人应付手续费<0,错误；relationId:"+record.getId()
											+"============================");
									continue;
								}
								log.info("=============【债权转让】转让人应付手续费"+scheduleTemp.getPlanFee()+"减去补差金额："+diffAmount
										+"============================");
								//实收手续费
								Double feeAmount = MoneyUtil.subtract(scheduleTemp.getPlanFee(), diffAmount).doubleValue();
								//手续费不够补差时：补差金额为手续费金额，实收手续费为0
								if(feeAmount < 0){
									feeAmount = 0d;
									diffAmount = scheduleTemp.getPlanFee();
								}
								planFee = feeAmount;
								realReturnInter = MoneyUtil.subtract(transInAmount, planFee).doubleValue();
							}
							log.info("=============【债权转让】最终应回出让人利息="+realReturnInter+"，平台营收="+planFee+"，补差户金额="+diffAmount+"============================");
							depFixedLoanRelationshipService.doTransfer4Free(record, inSubAccountTemp, matchAuthAmount, matchRedAmount,planFee, transInAmount, diffAmount, diffAct!=null ? diffAct.getId() : null);
	
							//切面使用参数
							parameter4Aspect(record, PartnerEnum.getEnumByCode(record.getPartnerCode()));
	
						}
					}
	
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey());
				}
			}
			//转让后的  查询债权关系中，subAccountId相同， leftAmount的总值；大于0 ，则不退出
			Double sumLeftAmount = lnLoanRelationMapper.sumLeftAmount(bsDepositionQuitApply.getSubAccountId());

			if(sumLeftAmount == 0){
				log.info("==================【存管系统固定期限产品到期退出业务】产品子账户编号"+bsDepositionQuitApply.getSubAccountId()+"调用退出记账服务=================");
				//修改退出申请表bs_increase_quit_apply状态为处理中
				BsDepositionQuitApply apply = new BsDepositionQuitApply();
				apply.setId(bsDepositionQuitApply.getId());
				apply.setStatus(Constants.DEP_QUIT_APPLY_PROCESS);
				apply.setUpdateTime(new Date());
				bsDepositionQuitApplyMapper.updateByPrimaryKeySelective(apply);

				try {
					//退出
					userDepFixedQuitService.quit(bsDepositionQuitApply.getSubAccountId());
				} catch (Exception e) {
					e.printStackTrace();
				}

			}else{
				log.info("==================【退出债权转让】产品子账户编号"+bsDepositionQuitApply.getSubAccountId()+"未完全转让=================");
				//未完全转让，修改退出申请表,预计执行时间+1天
				BsDepositionQuitApply apply = new BsDepositionQuitApply();
				apply.setId(bsDepositionQuitApply.getId());
				apply.setPlanDate(DateUtil.addDays(bsDepositionQuitApply.getPlanDate(), 1));
				apply.setUpdateTime(new Date());
				bsDepositionQuitApplyMapper.updateByPrimaryKeySelective(apply);
			}

			log.info("==================【退出债权转让】待退出subAccountId："+bsDepositionQuitApply.getSubAccountId()+"结束=================");
		}

	}



	/**
	 * 根据承接人应付本息，查询符合承接的债权数据
	 * @author bianyatian
	 * @param transInSumAmount 承接人应付所有本息
	 * @param principal 承接人应付本金（出让本金）
	 * @param bsUserId 出让用户id
	 * @param productType
	 * @return
	 */
	private BsSubAccountVO4DepFixedMatch getInSubAccount(
			Double transInSumAmount, Double principal, Integer bsUserId,ProductType productType,boolean getVip) {
		/**
		 * 1、优先让自由资金承接，出让本金<=5000的钱报系优先承接；
		 * 2、无自有资金则由对应的借款合作方查找对应的老产品进行承接，同理出让本金<=5000的钱报系优先承接；
		 * 3、若无可承接，返回null
		 */
		List<Integer> vipIdList = depFixedLoanRelationshipService.getDepVIPUserList(VIPId4PartnerEnum.getEnumByCode(PartnerEnum.FREE.getCode()).getVipIdKey());
		BsSubAccountVO4DepFixedMatch inSubAccountTemp = new BsSubAccountVO4DepFixedMatch();

		if(getVip){
			inSubAccountTemp = bsSubAccountMapper.query4TransferCommon(transInSumAmount, "no", "yes", vipIdList,principal,bsUserId, productType.getAuthCode(), productType.getRedCode());
		}else{
			if(principal.compareTo(5000d) <= 0){
				//钱报系优先接
				inSubAccountTemp = bsSubAccountMapper.query4TransferCommon(transInSumAmount, "yes", "no", vipIdList, principal,bsUserId, productType.getAuthCode(), productType.getRedCode());
				if(inSubAccountTemp == null){
					//普通用户
					inSubAccountTemp = bsSubAccountMapper.query4TransferCommon(transInSumAmount, "no", "no", vipIdList, principal,bsUserId, productType.getAuthCode(), productType.getRedCode());
				}
			}else{
				//普通用户
				inSubAccountTemp = bsSubAccountMapper.query4TransferCommon(transInSumAmount, "no", "no", vipIdList, principal,bsUserId, productType.getAuthCode(), productType.getRedCode());
			}
		}
		return inSubAccountTemp;
	}

	private void userQuitTransferOld() {
		log.info("==================【存管系统固定期限产品到期退出业务】 债权转让开始 =================");
		try {
			//查询预期执行日期在当日，状态为通过的 理财退出申请列表
			BsDepositionQuitApplyExample example = new BsDepositionQuitApplyExample();
			Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
			example.createCriteria().andStatusEqualTo(Constants.DEP_QUIT_APPLY_PASS).andPlanDateEqualTo(today);
			List<BsDepositionQuitApply> applyList = bsDepositionQuitApplyMapper.selectByExample(example);

			if(CollectionUtils.isNotEmpty(applyList)){
				for (BsDepositionQuitApply bsDepositionQuitApply : applyList) {
					log.info("==================【退出债权转让】待退出subAccountId："+bsDepositionQuitApply.getSubAccountId()+"开始=================");
					try {
						jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey());

						BsSubAccount subAccount = bsSubAccountMapper.selectByPrimaryKey(bsDepositionQuitApply.getSubAccountId());
						if(Constants.PRODUCT_TYPE_AUTH_YUN.equals(subAccount.getProductType())){
							//查询有债权关系的债权数据
							List<LoanRelation4TransferVO> relationList = lnLoanRelationMapper.getRelationList(bsDepositionQuitApply.getSubAccountId(),null);
							if(CollectionUtils.isNotEmpty(relationList)){
								for (final LoanRelation4TransferVO record : relationList) {
									//前置判断
									if(record.getRelationBeginDate() == null || record.getBaseRate() == null){
										log.info("==================【云贷-退出债权转让】待转数据relationId："+record.getId()+"有误=================");
										continue;
									}
									if(record.getLeftAmount() <= 0){
										log.info("==================【云贷-退出债权转让】待转数据relationId：可转本金为空=================");
										continue;
									}
									//进行债权转让
									depFixedLoanRelationshipService.doDepFixedTransferDetail(today, record, false, SubAccountCode.getPartnerByAuthCode(subAccount.getProductType()));
								}
							}
						}else if(Constants.PRODUCT_TYPE_AUTH_7.equals(subAccount.getProductType())){
							//查询有债权关系的债权数据
							List<LoanRelation4TransferVO> relationList = lnLoanRelationMapper.getSevenRelationList(bsDepositionQuitApply.getSubAccountId(),null);
							if(CollectionUtils.isNotEmpty(relationList)){
								for (final LoanRelation4TransferVO record : relationList) {
									//前置判断
									if(record.getRelationBeginDate() == null || record.getBaseRate() == null){
										log.info("==================【七贷-退出债权转让】待转数据relationId："+record.getId()+"有误=================");
										continue;
									}
									if(record.getLeftAmount() <= 0){
										log.info("==================【七贷-退出债权转让】待转数据relationId：可转本金为空=================");
										continue;
									}
									//进行债权转让
									depFixedLoanRelationshipService.doDepFixedTransferDetail(today, record, false, SubAccountCode.getPartnerByAuthCode(subAccount.getProductType()));
								}
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey());
					}
					//转让后的  查询债权关系中，subAccountId相同， leftAmount的总值；大于0 ，则不退出
					Double sumLeftAmount = lnLoanRelationMapper.sumLeftAmount(bsDepositionQuitApply.getSubAccountId());

					if(sumLeftAmount == 0){
						log.info("==================【存管系统固定期限产品到期退出业务】产品子账户编号"+bsDepositionQuitApply.getSubAccountId()+"调用退出记账服务=================");
						//修改退出申请表bs_increase_quit_apply状态为处理中
						BsDepositionQuitApply apply = new BsDepositionQuitApply();
						apply.setId(bsDepositionQuitApply.getId());
						apply.setStatus(Constants.DEP_QUIT_APPLY_PROCESS);
						apply.setUpdateTime(new Date());
						bsDepositionQuitApplyMapper.updateByPrimaryKeySelective(apply);

						try {
							//退出
							userDepFixedQuitService.quit(bsDepositionQuitApply.getSubAccountId());
						} catch (Exception e) {
							e.printStackTrace();
						}

					}else{
						log.info("==================【退出债权转让】产品子账户编号"+bsDepositionQuitApply.getSubAccountId()+"未完全转让=================");
						//未完全转让，修改退出申请表,预计执行时间+1天
						BsDepositionQuitApply apply = new BsDepositionQuitApply();
						apply.setId(bsDepositionQuitApply.getId());
						apply.setPlanDate(DateUtil.addDays(bsDepositionQuitApply.getPlanDate(), 1));
						apply.setUpdateTime(new Date());
						bsDepositionQuitApplyMapper.updateByPrimaryKeySelective(apply);
					}

					log.info("==================【退出债权转让】待退出subAccountId："+bsDepositionQuitApply.getSubAccountId()+"结束=================");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("==================【存管系统固定期限产品到期退出业务】债权转让开始  结束 =================");
	}



	/**
	 * VIP转出
	 * @author bianyatian
	 */
	private void vip2UserTransfer() {

		Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
		//VIP用户id列表
		List<Integer> vipIdList = new ArrayList<Integer>();
		List<Integer> yunList = depFixedLoanRelationshipService.getDepVIPUserList(VIPId4PartnerEnum.YUN_DAI_SELF.getVipIdKey());
		List<Integer> sevenList = depFixedLoanRelationshipService.getDepVIPUserList(VIPId4PartnerEnum.SEVEN_DAI_SELF.getVipIdKey());
		List<Integer> freeList = depFixedLoanRelationshipService.getDepVIPUserList(VIPId4PartnerEnum.FREE.getVipIdKey());
		
		ProductType firstProType = SubAccountCode.productTypeMap.get(PartnerEnum.FREE);
		if(CollectionUtils.isNotEmpty(yunList)){
			vipIdList.addAll(yunList);
		}
		if(CollectionUtils.isNotEmpty(sevenList)){
			vipIdList.addAll(sevenList);
		}
		if(CollectionUtils.isNotEmpty(freeList)){
			vipIdList.addAll(freeList);
		}
		if(CollectionUtils.isEmpty(vipIdList)){return;}

		Integer count = lnLoanRelationMapper.countVIPRelationList(vipIdList);
		log.info("=====【VIP债权转让】可转总债权条数："+count+"=====");
		if(count >0){
			Integer pageSize = 100;
			Integer c = count/pageSize + (count%pageSize> 0?1:0);
			for(int i=0;i<c;i++){
				try {
					jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey(),300);
					//查询现有的vip的债权，且非代偿后接入的
					List<LoanRelation4TransferVO> relationList = lnLoanRelationMapper.getVIPRelationList4Page(vipIdList,0,pageSize);
					if(CollectionUtils.isNotEmpty(relationList)){
						for (LoanRelation4TransferVO record : relationList) {
							//前置判断
							if(record.getRelationBeginDate() == null || record.getBaseRate() == null){
								log.info("==================【VIP债权转让】待转数据relationId："+record.getId()+"，债权起始日期或利率为空=================");
								continue;
							}
							if(record.getLeftAmount() <= 0){
								log.info("==================【VIP债权转让】待转数据relationId：可转本金为空=================");
								continue;
							}
							//计算应还到出让人的金额数据
							LnFinanceRepaySchedule scheduleTemp = new LnFinanceRepaySchedule();
							FlowContext flowContext = new FlowContext();
							flowContext.setRes(scheduleTemp);
							flowContext.setTransCode(ConstantsForTransfer.TRANSFER_GET_NEED_PAY);
							flowContext.setBusinessType(record.getPartnerBusinessFlag());
							flowContext.setPartnerEnum(PartnerEnum.getEnumByCode(record.getPartnerCode()));
							flowContext.getExtendMap().put("loanRelation4TransferRecord", record);

							dispatchService.dispatcherService(flowContext);
							scheduleTemp = (LnFinanceRepaySchedule)flowContext.getRes();
							//承接人应付出让人利息
							Double planInterest = scheduleTemp.getPlanInterest() != null ? scheduleTemp.getPlanInterest() : 0d;
							//承接人应付出让人债转付息
							Double panTransInterest = scheduleTemp.getPlanTransInterest() != null ? scheduleTemp.getPlanTransInterest() : 0d;
							//承接人应付平台营收
							Double initPlanFee = scheduleTemp.getPlanFee() != null ? scheduleTemp.getPlanFee() : 0d;

							//承接人应付总利息= 承接人应付出让人利息+承接人应付出让人债转付息+承接人应付平台营收
							Double transInAmount = MoneyUtil.add(initPlanFee,
									MoneyUtil.add(planInterest, panTransInterest).doubleValue()).doubleValue();
							//承接人应付总本息
							Double  transInSumAmount= MoneyUtil.add(record.getLeftAmount(),transInAmount).doubleValue();


							//根据承接人应付本息获取承接人数据
							BsSubAccountVO4DepFixedMatch inSubAccountTemp = new BsSubAccountVO4DepFixedMatch();
							ProductType freeProductType = SubAccountCode.productTypeMap.get(PartnerEnum.FREE);
							//获取自由可承接
							inSubAccountTemp = getInSubAccount(transInSumAmount, record.getLeftAmount(), record.getBsUserId(), freeProductType, false);
							if(inSubAccountTemp == null){
								//对应资产端
								ProductType productType = SubAccountCode.productTypeMap.get(PartnerEnum.getEnumByCode(record.getPartnerCode()));
								inSubAccountTemp = getInSubAccount(transInSumAmount, record.getLeftAmount(), record.getBsUserId(), productType, false);
							}
							if(inSubAccountTemp == null){
								continue;
							}
							Double matchRedAmount=0d; //红包金额匹配初始化
							Double matchAuthAmount=transInSumAmount; //站岗户金额匹配初始化
							//判断是否用到红包户
							if(inSubAccountTemp.getRedAvailableBalance() > 0 ){
								//红包只能用于本金部分，若红包小于本金，则收取红包为本金部分
								matchRedAmount = inSubAccountTemp.getRedAvailableBalance().compareTo(record.getLeftAmount()) <=0
										? inSubAccountTemp.getRedAvailableBalance() : record.getLeftAmount();
								matchAuthAmount =  MoneyUtil.subtract(transInSumAmount,matchRedAmount).doubleValue();
								log.info("=============【债权转让】应冻结承接人RED户金额："+matchRedAmount+"，站岗户金额："+matchAuthAmount+"============================");

							}
							log.info("=============【债权转让】冻结承接人AUTH户："+inSubAccountTemp.getId()+"，金额："+matchAuthAmount+"============================");
							Double diffAmount = 0d;//实际补差B，暂时设为0
							Double realReturnInter = 0d;//实际还款利息X，暂时设为0
							Double planFee = 0d;//平台实际营收S，暂时设为0
							BsSubAccount diffAct = null;//补差户，暂时设为null
							//查询对应站岗户，计算实际需补差金额，实际应回理财人利息，实际平台营收
							BsSubAccount authAct = bsSubAccountMapper.selectByPrimaryKey(record.getBsSubAccountId());
							if(firstProType.getAuthCode().equals(authAct.getProductType())){
								//为自由站岗户,VIP不收利息
								Double leftPlanInterest = 0d;//剩余应付利息Y
								log.info("出让人转让相关初始金额[relationId="+record.getId()+"]："
										+"应计利息="+scheduleTemp.getPlanInterest()+"/上次债转付息="+scheduleTemp.getPlanTransInterest()
										+"/实际受让人应付总本息="+transInSumAmount+"/实际还款本金="+record.getLeftAmount()
										+"/初始平台营收="+scheduleTemp.getPlanFee()+"/剩余应付利息="+leftPlanInterest);
								realReturnInter = 0d;
								diffAmount = 0d;
								planFee = transInAmount;
								/*if(leftPlanInterest <= 0){
									//if 剩余应付利息<=0 实际回到理财人利息X=0，平台营收=承接人应付总利息= 承接人应付出让人利息+承接人应付出让人债转付息+承接人应付平台营收，补差B=0
									realReturnInter = 0d;
									diffAmount = 0d;
									planFee = transInAmount;
								}else{
									//承接人应付出让人利息+承接人应付出让人债转付息
									Double interestTemp = CalculatorUtil.calculate("a+a", planInterest, panTransInterest);
									if(leftPlanInterest.compareTo(interestTemp) == 0){
										//if 剩余应付利息=承接人应付出让人利息+承接人应付出让人债转付息 则X=Y,S=承接人应付平台营收，补差B=0
										realReturnInter = leftPlanInterest;
										diffAmount = 0d;
										planFee = initPlanFee;
									}else if(leftPlanInterest.compareTo(interestTemp) < 0){
										//else if 剩余应付利息<(承接人应付出让人利息+承接人应付出让人债转付息) 则X=Y,S=承接人应付平台营收+((承接人应付出让人利息+承接人应付出让人债转付息) -剩余应付利息)，补差B=0
										realReturnInter = leftPlanInterest;
										diffAmount = 0d;
										planFee = CalculatorUtil.calculate("a+a-a", initPlanFee, interestTemp, leftPlanInterest);
									}else if(leftPlanInterest.compareTo(interestTemp) > 0){
										//查询对应补差户，获得补差金额B = B>0 ? B : 0
										diffAct = bsSubAccountPairMapper.selectDiffActByLoanRelationId(record.getId());
										diffAmount = diffAct != null && diffAct.getBalance().compareTo(0d)>0 ? diffAct.getBalance() : 0d;
										//平台营收、补差再次确认
										//S=(IS-B)<0 ? 0 : (IS-B)
										planFee = (initPlanFee.compareTo(diffAmount) < 0) ? 0d : CalculatorUtil.calculate("a-a", initPlanFee, diffAmount);
										//B`=B= (IS-B)<0 ? IS : B
										diffAmount = (initPlanFee.compareTo(diffAmount) < 0) ? initPlanFee : diffAmount;
										Double diffAmountTemp = diffAmount; // B`=B
										//B= (Y-(I+T)) > B ? B : (Y-(I+T))
										diffAmount = (CalculatorUtil.calculate("a-a-a", leftPlanInterest, interestTemp, diffAmount) > 0 )
												? diffAmount : CalculatorUtil.calculate("a-a", leftPlanInterest, interestTemp);
										//S= S + (B`- B)
										planFee = CalculatorUtil.calculate("a+a-a", diffAmountTemp, diffAmount);
										//X= (I+T)+B
										realReturnInter = CalculatorUtil.calculate("a+a", interestTemp, diffAmount);

									}
								}*/

							}else{
								//非自由站岗户，无剩余利息
								//获取补差金额
								diffAct = bsSubAccountPairMapper.selectDiffActByLoanRelationId(record.getId());
								diffAmount = diffAct != null && diffAct.getBalance().compareTo(0d)>0 ? diffAct.getBalance() : 0d;//补差金额
								if(scheduleTemp.getPlanFee() < 0){
									log.info("=============【债权转让】转让人应付手续费<0,错误；relationId:"+record.getId()
											+"============================");
									continue;
								}
								log.info("=============【债权转让】转让人应付手续费"+scheduleTemp.getPlanFee()+"减去补差金额："+diffAmount
										+"============================");
								//实收手续费
								Double feeAmount = MoneyUtil.subtract(scheduleTemp.getPlanFee(), diffAmount).doubleValue();
								//手续费不够补差时：补差金额为手续费金额，实收手续费为0
								if(feeAmount < 0){
									feeAmount = 0d;
									diffAmount = scheduleTemp.getPlanFee();
								}
								planFee = feeAmount;
								realReturnInter = MoneyUtil.subtract(transInAmount, planFee).doubleValue();
							}
							log.info("=============【债权转让】最终应回出让人利息="+realReturnInter+"，平台营收="+planFee+"，补差户金额="+diffAmount+"============================");
							depFixedLoanRelationshipService.doTransfer4Free(record, inSubAccountTemp, matchAuthAmount, matchRedAmount,planFee, transInAmount, diffAmount, diffAct!=null ? diffAct.getId() : null);

						}
					}
				} finally {
					jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_LOAN_RELATION.getKey());
				}
			}
		}
	}



	private void vip2UserZsdTransfer() {
		log.info("=====赞时贷VIP债权转让开始=====");
		Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
		//VIP用户id列表
		List<Integer> vipIdList = depFixedLoanRelationshipService.getDepVIPUserList(VIPId4PartnerEnum.ZSD.getVipIdKey());
		Integer count = lnLoanRelationMapper.countVIPRelationList(vipIdList);
		if(count >0){
			Integer pageSize = 100;
			Integer c = count/pageSize + (count%pageSize> 0?1:0);
			for(int i=0;i<c;i++){
				try {
					jsClientDaoSupport.lock(RedisLockEnum.LOCK_ZSD_LOAN_RELATION.getKey());
					log.info("=====从0次开始，第"+i+"次查询赞时贷VIP可转债权=====");
					//查询现有的vip的债权，且非代偿后接入的
					List<LoanRelation4TransferVO> relationList = lnLoanRelationMapper.getVIPRelationList4Page(vipIdList,0,pageSize);
					if(CollectionUtils.isNotEmpty(relationList)){
						for (LoanRelation4TransferVO record : relationList) {
							if(record.getLeftAmount() <= 0){
								continue;
							}
							//进行债权转让
							depFixedLoanRelationshipService.doDepFixedZsdTransferDetail(today, record, true);
						}
					}
				} finally {
					jsClientDaoSupport.unlock(RedisLockEnum.LOCK_ZSD_LOAN_RELATION.getKey());
				}
			}
			Integer countAfter = lnLoanRelationMapper.countVIPRelationList(vipIdList);
			if(countAfter > 0){
				try {
					jsClientDaoSupport.lock(RedisLockEnum.LOCK_ZSD_LOAN_RELATION.getKey());
					//查询现有的vip的债权，且非代偿后接入的
					List<LoanRelation4TransferVO> relationList = lnLoanRelationMapper.getVIPRelationList4Page(vipIdList,0,countAfter);
					if(CollectionUtils.isNotEmpty(relationList)){
						for (LoanRelation4TransferVO record : relationList) {
							if(record.getLeftAmount() <= 0){
								continue;
							}
							//进行债权转让
							depFixedLoanRelationshipService.doDepFixedZsdTransferDetail(today, record, true);
						}
					}
				} finally {
					jsClientDaoSupport.unlock(RedisLockEnum.LOCK_ZSD_LOAN_RELATION.getKey());
				}
			}
		}
	}

	//切面使用参数
	private Map<String, Object> parameter4Aspect(LoanRelation4TransferVO record, PartnerEnum partnerEnum) {
		log.info("【债权协议签章切面参数准备】生成债权转让协议签章数据切面入参loanRelationId："+record.getId());
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("loanRelationId", record.getId());
		dataMap.put("partnerEnum", partnerEnum);
		// 生成债转让协议签章(云贷、七贷)
		return dataMap;
	}

}
