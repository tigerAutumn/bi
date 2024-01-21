package com.pinting.business.accounting.loan.service.impl;

import com.alibaba.fastjson.JSON;
import com.pinting.business.accounting.finance.service.DepUserBonusGrantService;
import com.pinting.business.accounting.finance.service.UserBonusGrantService;
import com.pinting.business.accounting.finance.service.impl.process.DepUserBonusGrant4BuyProcess;
import com.pinting.business.accounting.loan.enums.DepTargetEnum;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.LoanSubjects;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.model.DFResultInfo;
import com.pinting.business.accounting.loan.model.InvestorRegInfo;
import com.pinting.business.accounting.loan.service.LoanAccountService;
import com.pinting.business.accounting.loan.service.LoanPaymentService;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.accounting.service.impl.process.SignSeal4BorrowServicesZanProcess;
import com.pinting.business.accounting.service.impl.process.SignSeal4LoanAgreementProcess;
import com.pinting.business.dao.*;
import com.pinting.business.enums.*;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.dto.LoanQueueDTO;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.common.CommissionService;
import com.pinting.business.service.common.DepCommonService;
import com.pinting.business.service.loan.LoanUserMobileWhiteListCheckService;
import com.pinting.business.service.loan.LoanUserService;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.*;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.hessian.message.hfbank.model.*;
import com.pinting.gateway.hessian.message.loan.B2GReqMsg_LoanNotice_NoticeLoan;
import com.pinting.gateway.hessian.message.loan.B2GResMsg_LoanNotice_NoticeLoan;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Loan_Loan;
import com.pinting.gateway.hessian.message.loan.model.RepaySchedule;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.out.service.BaoFooTransportService;
import com.pinting.gateway.out.service.HfbankTransportService;
import com.pinting.gateway.out.service.loan.NoticeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by 剑钊 on 2016/8/18.
 */
@Service
public class LoanPaymentServiceImpl implements LoanPaymentService {
    private Logger log = LoggerFactory.getLogger(LoanPaymentServiceImpl.class);
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Autowired
    private BaoFooTransportService baoFooTransportService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private LoanUserService loanUserService;
    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnRepayScheduleMapper scheduleMapper;
    @Autowired
    private LnRepayScheduleDetailMapper scheduleDetailMapper;
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private LnPayOrdersMapper payOrdersMapper;
    @Autowired
    private LnPayOrdersJnlMapper payOrdersJnlMapper;
    @Autowired
    private LnPartnerChargeRuleMapper ruleMapper;
    @Autowired
    private BsPayResultQueueMapper queueMapper;
    @Autowired
    private LoanAccountService loanAccountService;
    @Autowired
    private LoanRelationshipService relationService;
    @Autowired
    private BsSubAccountPairMapper pairMapper;
    @Autowired
    private CommissionService commissionService;
    @Autowired
    private LnLoanRelationMapper loanRelationMapper;
    @Autowired
    private DepUserBonusGrantService depUserBonusGrantService;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private BsSubAccountMapper subAccountMapper;
    @Autowired
    private BsProductMapper productMapper;
    @Autowired
    private BsServiceFeeMapper serviceFeeMapper;
    @Autowired
    private LoanUserMobileWhiteListCheckService loanUserMobileWhiteListCheckService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private SignSealService signSealService;
    @Autowired
    private LnLoanBlackMapper lnLoanBlackMapper;
    @Autowired
    private SubAccountService subAccountService;
    @Autowired
    private PayOrdersService payOrdersService;
    @Autowired
    private HfbankTransportService hfbankTransportService;
    @Autowired
    private LnDepositionTargetMapper lnDepositionTargetMapper;
    @Autowired
    private LnDepositionTargetJnlMapper depositionTargetJnlMapper;
    @Autowired
    private DepCommonService depCommonService;
    @Autowired
    private LnBindCardMapper lnBindCardMapper;
    @Autowired
    private BsRevenueTransDetailMapper bsRevenueTransDetailMapper;
    @Autowired
    private LnSubAccountMapper lnSubAccountMapper;
    @Autowired
    private LnSubjectMapper lnSubjectMapper;
    @Autowired
    private LnAccountJnlMapper lnAccountJnlMapper;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;
    @Autowired
    private UserBonusGrantService userBonusGrantService;
    
    @Override
    @Transactional
    public void loan(G2BReqMsg_Loan_Loan req) throws Exception {
        if (StringUtils.isBlank(req.getOrderNo()) || StringUtil.isBlank(req.getChannel())
                || StringUtils.isBlank(req.getBusinessType()) || StringUtil.isBlank(req.getLoanTerm()) || StringUtils.isBlank(req.getUserId())
                || StringUtils.isBlank(req.getLoanId()) || StringUtils.isBlank(req.getLoanAmount())
                || StringUtils.isBlank(req.getBindId())) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        /*//判读是否超过阈值，查询当日申请的非失败的及当日成功的借款表总金额
        Double loanIngAmount = lnLoanMapper.selectTodayNotFillAmount();
        //阈值查询
        BsSysConfig configRate = sysConfigService.findConfigByKey(Constants.HF_THRESHOLD_RATE);
        Double thresholdAmount = 8000000d; //默认为800万
        BsSysConfig configRiskReserve = sysConfigService.findConfigByKey(Constants.HF_RISK_RESERVE_AMOUNT);
        if(configRate != null && configRiskReserve != null){
        	thresholdAmount = MoneyUtil.multiply(Double.parseDouble(configRate.getConfValue()), Double.parseDouble(configRiskReserve.getConfValue())).doubleValue();
        }
        Double diffAmount = MoneyUtil.subtract(thresholdAmount, loanIngAmount).doubleValue();
        if( MoneyUtil.subtract(diffAmount,MoneyUtil.divide(req.getLoanAmount(), "100").doubleValue()).doubleValue() <= 0){
        	throw new PTMessageException(PTMessageEnum.LOAN_OVER);
        }*/
        
        LnUser lnUser = loanUserService.queryLoanUserExist(req.getUserId(), req.getChannel());
        if (lnUser != null) {
        	//判断黑名单
        	LnLoanBlackExample example = new LnLoanBlackExample();
        	example.createCriteria().andMobileEqualTo(lnUser.getMobile());
        	List<LnLoanBlack> blackList = lnLoanBlackMapper.selectByExample(example);
        	if(CollectionUtils.isNotEmpty(blackList)){
        		throw new PTMessageException(PTMessageEnum.ZAN_LOAN_USER_FOUND_IN_BLACK);
        	}
        	//白名单
            if (!loanUserMobileWhiteListCheckService.lnMobileWhiteListCheck(lnUser.getMobile())) {
                throw new PTMessageException(PTMessageEnum.ZAN_LOAN_USER_NOT_FOUND_IN_WHITE);
            }
        } else {
            throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_USER_NOT_EXIST);
        }
        
        //统计借款人待还本金，加上此次申请本金，和最高借款本金比较，超过返回借款失败
        Double notRepayAmount = scheduleMapper.sumNotRepayByLnUserId(lnUser.getId());
        Double maxLoanAmount = 20d;
		BsSysConfig config = sysConfigService.findConfigByKey(Constants.LOAN_MAX_SUM_AMOUNT);
		if(config != null){
			maxLoanAmount = MoneyUtil.multiply(Double.valueOf(config.getConfValue()), 10000).doubleValue();
		}
        if(maxLoanAmount.compareTo(MoneyUtil.add(notRepayAmount, MoneyUtil.divide(req.getLoanAmount(), "100").doubleValue()).doubleValue()) < 0 ){
        	throw new PTMessageException(PTMessageEnum.LOAN_MAX_OUT);
        }
        
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_LOAN.getKey() + lnUser.getIdCard());

            LnLoanExample loanExample = new LnLoanExample();
            loanExample.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo());
            List<LnLoan> loanList = lnLoanMapper.selectByExample(loanExample);
            if (CollectionUtils.isNotEmpty(loanList) && loanList.size() > 0) {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号重复");
            }

            //检查绑卡表，如果客户对应的卡未绑定，则返回相应错误
            LnBindCard lnBindCard = loanUserService.queryLoanBindCardExist(req.getUserId(), req.getBindId(), req.getChannel());

            if (lnBindCard != null) {

                //记录借款信息表ln_loan,状态为审核通过
                LnLoan lnLoan = new LnLoan();
                lnLoan.setApplyAmount(MoneyUtil.divide(req.getLoanAmount(), "100").doubleValue());
                lnLoan.setApplyTime(DateUtil.parseDateTime(req.getLoanApplyTime()));
                lnLoan.setApproveAmount(MoneyUtil.divide(req.getLoanAmount(), "100").doubleValue());
                lnLoan.setBgwBindId(req.getBindId());
                lnLoan.setBreakMaxDays(StringUtils.isNotBlank(req.getBreakMaxDays()) ? Integer.parseInt(req.getBreakMaxDays()) : null);
                lnLoan.setBreakTimes(StringUtils.isNotBlank(req.getBreakTimes()) ? Integer.parseInt(req.getBreakTimes()) : null);

                LnPartnerChargeRuleExample example = new LnPartnerChargeRuleExample();
                // 筛选可用的计费规则配置 .andIsCurrentEqualTo("YES")
                example.createCriteria().andPartnerCodeEqualTo(req.getChannel()).andTermEqualTo(Integer.valueOf(req.getLoanTerm())).andIsCurrentEqualTo(Constants.YES);
                List<LnPartnerChargeRule> list = ruleMapper.selectByExample(example);

                if (CollectionUtils.isEmpty(list) || list.size() == 0) {
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "找不到规则");
                }

                lnLoan.setChargeRuleId(list.get(0).getId());
                lnLoan.setPartnerLoanId(req.getLoanId());
                lnLoan.setCreateTime(new Date());
                lnLoan.setCreditAmount(MoneyUtil.divide(req.getLoanAmount(), "100").doubleValue());
                lnLoan.setCreditLevel(req.getCreditLevel());
                lnLoan.setCreditScore(StringUtils.isNotBlank(req.getCreditScore()) ? Integer.parseInt(req.getCreditScore()) : null);
                lnLoan.setLnUserId(lnBindCard.getLnUserId());
                lnLoan.setLoanedAmount(StringUtils.isNotBlank(req.getLoanAmount()) ? MoneyUtil.divide(req.getLoanAmount(), "100").doubleValue() : 0);
                lnLoan.setLoanTimes(StringUtils.isNotBlank(req.getLoanTimes()) ? Integer.parseInt(req.getLoanTimes()) : null);
                lnLoan.setPartnerBusinessFlag(req.getBusinessType());
                lnLoan.setPartnerOrderNo(req.getOrderNo());
                lnLoan.setPayOrderNo(Util.generateOrderNo4BaoFoo(8));
                lnLoan.setPeriod(Integer.parseInt(req.getLoanTerm()));
                lnLoan.setPurpose(req.getPurpose());
                lnLoan.setStatus(LoanStatus.LOAN_STATUS_CHECKED.getCode());
                lnLoan.setSubjectName(req.getSubjectName());
                lnLoan.setUpdateTime(new Date());
                lnLoan.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                LnSubjectVO subjectVo = lnSubjectMapper.selectByLoanTerm(lnLoan.getPeriod(), PartnerEnum.ZAN.getCode(),
                        LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                lnLoan.setAgreementRate(MoneyUtil.multiply(MoneyUtil.divide(subjectVo.getNumValue(),100,10).doubleValue(),12).doubleValue());
                lnLoanMapper.insertSelective(lnLoan);

                //根据还款日  对还款计划列表排序
                List<RepaySchedule> repaySchedules = req.getRepaySchedule();
                log.info("借款编号：" + req.getLoanId() + "对应的>>>还款计划列表：" + repaySchedules + "<<<");
                Collections.sort(repaySchedules, new Comparator<RepaySchedule>() {
                    @Override
                    public int compare(RepaySchedule o1, RepaySchedule o2) {
                        return o1.getRepayDate().compareTo(o2.getRepayDate());
                    }
                });

                //调用各类型费用计算公式 判断费用是否正确
                for (int i = 0; i < repaySchedules.size(); i++) {
                    //保费的计算
                    if (repaySchedules.get(i).getPremium() != MoneyUtil.multiply(loanUserService.calPremium(lnLoan.getId(), i + 1), 100).longValue()
                            //信息服务费
                            || repaySchedules.get(i).getInfoServiceFee() != MoneyUtil.multiply(loanUserService.calInfoServiceFee(lnLoan.getId(), i + 1), 100).longValue()
                            //本金
                            || repaySchedules.get(i).getPrincipal() != MoneyUtil.multiply(loanUserService.calPrincipal(lnLoan.getId(), i + 1), 100).longValue()
                            //利息
                            || repaySchedules.get(i).getInterest() != MoneyUtil.multiply(loanUserService.calInterest(lnLoan.getId(), i + 1), 100).longValue()
                            //监管费
                            || repaySchedules.get(i).getSuperviseFee() != MoneyUtil.multiply(loanUserService.calSuperviseFee(lnLoan.getId(), i + 1), 100).longValue()
                            //账户管理费
                            || repaySchedules.get(i).getAccountManageFee() != MoneyUtil.multiply(loanUserService.calAccountManageFee(lnLoan.getId(), i + 1), 100).longValue()
                            ) {

                        throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "费用计算错误");
                    }
                }
                
                
                //新增一条ln_deposition_target的INIT记录
                LnDepositionTarget target = new LnDepositionTarget();
                target.setProdName(Constants.PARTNER_LOAN_ZAN + lnLoan.getId().toString());
                target.setProdType(Constants.TARGET_PRODUCT_CYCLE);
                target.setTotalLimit(MoneyUtil.divide(req.getLoanAmount(), "100").doubleValue());
                target.setSetupType(Constants.TARGET_PRODUCT_ESTABLISH_DAY);
                target.setSellDate(new Date());
                target.setInterestType(Constants.TARGET_PRODUCT_INTEREST_CHECK);
                target.setCycle(lnLoan.getPeriod());
                target.setCycleUnit(Constants.TARGET_PRODUCT_UNIT_MONTH);
                target.setIstYear(lnLoan.getAgreementRate());
                target.setRepayType(Constants.TARGET_REPAY_TYPE_PERIOD);
                target.setLoanId(lnLoan.getId());
                target.setChargeOffAuto(Constants.TARGET_OUT_ACCOUNT_ACTIVE);
                target.setOverLimit(Constants.TARGET_OVER_LIMIT);
                target.setOverTotalLimit(MoneyUtil.divide(req.getLoanAmount(), "100").doubleValue());
                target.setStatus(Constants.DEP_TARGET_INIT);
                target.setCreateTime(new Date());
                target.setUpdateTime(new Date());
                lnDepositionTargetMapper.insertSelective(target);

                String orderNo = Util.generateOrderNo4BaoFoo(8);
                B2GReqMsg_HFBank_Publish publish = new B2GReqMsg_HFBank_Publish();
                publish.setProd_id(target.getId().toString());
                publish.setProd_name(target.getProdName());
                publish.setProd_type(target.getProdType());
                publish.setTotal_limit(MoneyUtil.divide(req.getLoanAmount(), "100").doubleValue());
                publish.setValue_type(Constants.TARGET_PRODUCT_INTEREST_CHECK);
                publish.setCreate_type(Constants.TARGET_PRODUCT_ESTABLISH_DAY);
                publish.setSell_date(com.pinting.business.util.DateUtil.getDate(new Date()));
                publish.setCycle(lnLoan.getPeriod());
                publish.setCycle_unit(target.getCycleUnit());
                publish.setIst_year(target.getIstYear());
                publish.setRepay_type(target.getRepayType());
                publish.setChargeOff_auto(target.getChargeOffAuto());
                publish.setOverLimit(target.getOverLimit());
                publish.setOver_total_limit(MoneyUtil.divide(req.getLoanAmount(), "100").doubleValue());
//                publish.setPlat_no(); 商户平台在资金账户管理平台注册的平台编号
                publish.setPartner_trans_date(new Date());
                publish.setPartner_trans_time(new Date());
                publish.setOrder_no(orderNo);
                //融资信息列表赋值
                List<PublishFinancingInfo> infoList = new ArrayList<PublishFinancingInfo>();
                PublishFinancingInfo info = new PublishFinancingInfo();
                info.setCust_no(lnUser.getHfUserId());
                info.setReg_date(new Date());
                info.setReg_time(new Date());
                Double financInterest = MoneyUtil.divide(MoneyUtil.multiply(req.getLoanTerm(),MoneyUtil.multiply(target.getIstYear(),target.getTotalLimit()).toString()).doubleValue(),
                        36500).doubleValue();
                Double financInterestRate = MoneyUtil.divide(financInterest,target.getTotalLimit()).doubleValue();

                info.setFinanc_int(String.valueOf(financInterestRate));
                info.setFee_int(0d);//恒丰增加必填项校验-暂定0.00
                info.setOpen_branch(lnBindCard.getBankCode());
                info.setWithdraw_account(lnBindCard.getBankCard());
                info.setAccount_type(Constants.HF_LOAN_CARD_TYPE_PERSONAL);
                info.setPayee_name(lnBindCard.getUserName());
                info.setFinanc_amt(target.getTotalLimit());
                infoList.add(info);
                publish.setFinancing_info_list(infoList);
                
                List<CompensationInfo> compensationInfos = new ArrayList<>();
                BsSysConfig bsSysConfig = sysConfigService.findConfigByKey(Constants.ZAN_COMPENSATE_USER_ID);
                if( bsSysConfig != null ) {
                	String userString[] = bsSysConfig.getConfValue().split(",");
                    for (String comUserId : userString) {
                    	BsHfbankUserExtExample hfbankUserExtExample = new BsHfbankUserExtExample();
                    	hfbankUserExtExample.createCriteria().andUserIdEqualTo(Integer.parseInt(comUserId)).andStatusEqualTo(Constants.HFBANK_USER_EXT_STATUS_OPEN);
                    	
                    	List<BsHfbankUserExt> hfbankUserExt = bsHfbankUserExtMapper.selectByExample(hfbankUserExtExample);
                    	CompensationInfo compInfos = new CompensationInfo();
                    	compInfos.setPlatcust(hfbankUserExt.get(0).getHfUserId());
                    	compInfos.setType(Constants.HF_COMPENSATE_REPAY_TYPE_COMPENSATE);//默认 0-代偿还款 1-委托还款
                    	compensationInfos.add(compInfos);
        			}
                    publish.setCompensation_info_list(compensationInfos);
                }
                
                //标的发布请求
                B2GResMsg_HFBank_Publish res = null;
                try {
                    res = hfbankTransportService.publish(publish);
                }catch (Exception e){
                    log.error("标的发布请求异常", e);
                    res = new B2GResMsg_HFBank_Publish();
                    res.setResCode(ConstantUtil.BSRESCODE_FAIL);
                    res.setResMsg("标的发布通讯失败，置为失败");
                }
                //标的发布请求，成功时入redis
                if(res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)){

                    //记录还款计划表ln_repay_schedule
                    for (RepaySchedule repaySchedule : repaySchedules) {

                        LnRepaySchedule lnRepaySchedule = new LnRepaySchedule();
                        lnRepaySchedule.setUpdateTime(new Date());
                        lnRepaySchedule.setCreateTime(new Date());
                        lnRepaySchedule.setLoanId(lnLoan.getId());
                        lnRepaySchedule.setPartnerRepayId(repaySchedule.getRepayId());
//                    lnRepaySchedule.setPlanDate(repaySchedule.getRepayDate());//还款计划以资金确认到账为准来进行计算，此处不做记录
                        lnRepaySchedule.setPlanTotal(MoneyUtil.divide(repaySchedule.getTotal(), 100).doubleValue());
                        lnRepaySchedule.setStatus(LoanStatus.REPAY_SCHEDULE_STATUS_INIT.getCode());
                        lnRepaySchedule.setSerialId(repaySchedules.indexOf(repaySchedule) + 1);

                        scheduleMapper.insertSelective(lnRepaySchedule);

                        //记录还款计划明细表ln_repay_schedule_detail
                        LnRepayScheduleDetail lnRepayScheduleDetail = new LnRepayScheduleDetail();
                        lnRepayScheduleDetail.setUpdateTime(new Date());
                        lnRepayScheduleDetail.setCreateTime(new Date());
                        lnRepayScheduleDetail.setPlanId(lnRepaySchedule.getId());
                        lnRepayScheduleDetail.setPlanAmount(repaySchedule.getPrincipal() != null ? MoneyUtil.divide(repaySchedule.getPrincipal(), 100).doubleValue() : 0d);
                        lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                        scheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
                        //利息
                        lnRepayScheduleDetail.setPlanAmount(repaySchedule.getInterest() != null ? MoneyUtil.divide(repaySchedule.getInterest(), 100).doubleValue() : 0d);
                        lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                        scheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
                        //监管费
                        lnRepayScheduleDetail.setPlanAmount(repaySchedule.getSuperviseFee() != null ? MoneyUtil.divide(repaySchedule.getSuperviseFee(), 100).doubleValue() : 0);
                        lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_SUPERVISE_FEE.getCode());
                        scheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
                        //信息服务费
                        lnRepayScheduleDetail.setPlanAmount(repaySchedule.getInfoServiceFee() != null ? MoneyUtil.divide(repaySchedule.getInfoServiceFee(), 100).doubleValue() : 0d);
                        lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INFO_SERVICE_FEE.getCode());
                        scheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
                        //账户管理费
                        lnRepayScheduleDetail.setPlanAmount(repaySchedule.getInfoServiceFee() != null ? MoneyUtil.divide(repaySchedule.getAccountManageFee(), 100).doubleValue() : 0d);
                        lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_ACCOUNT_MANAGE_FEE.getCode());
                        scheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
                        //保费
                        lnRepayScheduleDetail.setPlanAmount(repaySchedule.getPremium() != null ? MoneyUtil.divide(repaySchedule.getPremium(), 100).doubleValue() : 0d);
                        lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PREMIUM.getCode());
                        scheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
                        //其他
                        lnRepayScheduleDetail.setPlanAmount(repaySchedule.getOther() != null ? MoneyUtil.divide(repaySchedule.getOther(), 100).doubleValue() : 0d);
                        lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_OTHER.getCode());
                        scheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
                        //优惠金额
                        lnRepayScheduleDetail.setPlanAmount(repaySchedule.getPromote() != null ? MoneyUtil.divide(repaySchedule.getPromote(), 100).doubleValue() : 0d);
                        lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PROMOTE.getCode());
                        scheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
                    }

                    //修改ln_deposition_target为PUBLISH，新增一条ln_deposition_target_jnl的成功记录
                    depCommonService.updateTargetStatus(true,Constants.DEP_TARGET_PUBLISH,DepTargetEnum.DEP_TARGET_OPERATE_PUBLISH,target,lnLoan,orderNo);
                    try {
                        LoanQueueDTO loanQueueDTO=new LoanQueueDTO();
                        loanQueueDTO.setChannel(PartnerEnum.ZAN.getCode());
                        loanQueueDTO.setLnLoan(lnLoan);
                        loanQueueDTO.setLnBindCard(lnBindCard);
                        jsClientDaoSupport.rpush("loan_queue", JSON.toJSONString(loanQueueDTO));
                        log.info(">>>入借款队列数据:" + JSON.toJSONString(loanQueueDTO) + "<<<");
                    }catch (Exception e){
                        log.error("借款申请放入队列异常", e);
                    }

                }else{
                    //新增一条ln_deposition_target_jnl的失败记录
                    depCommonService.updateTargetStatus(false,Constants.DEP_TARGET_PUBLISH,DepTargetEnum.DEP_TARGET_OPERATE_PUBLISH,target,lnLoan,orderNo);
                    LnLoan updateLoan = new LnLoan();
                    updateLoan.setId(lnLoan.getId());
                    updateLoan.setUpdateTime(new Date());
                    updateLoan.setStatus(LoanStatus.LOAN_STATUS_FAIL.getCode());
                    lnLoanMapper.updateByPrimaryKeySelective(updateLoan);
                    LnLoan newLoan = lnLoanMapper.selectByPrimaryKey(lnLoan.getId());
                    notifyLoan2Partner(newLoan,"标的发布请求失败");
                    specialJnlService.warn4FailNoSMS(lnLoan.getApproveAmount(), "标的发布请求失败", lnLoan.getPayOrderNo(), "【赞分期自主借款标的发布】");
                    throw new PTMessageException(PTMessageEnum.DEP_TARGET_PUBLISH_ERROR, StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg());
                }

            } else {
                throw new PTMessageException(PTMessageEnum.ZAN_LOAN_USER_CARD_UNBIND);
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_LOAN.getKey() + lnUser.getIdCard());
        }
    }

    /**
     * 债权匹配
     */
    //@Transactional
    public void matchAndLoanPay(final LnLoan lnLoan, LnBindCard lnBindCard, String channel) {
        
        List<LoanRelation4DepVO> list = null;
        BaseAccount baseAccount = new BaseAccount();
        baseAccount.setAmount(0d);
        baseAccount.setBorrowerUserId(lnLoan.getLnUserId());
        baseAccount.setPartner(PartnerEnum.ZAN);
        Integer lnSubAccountId = loanAccountService.chargeLoanActOpen(baseAccount);
        try {
            //借款债权匹配
            list = relationService.confirmLoanRelation4LoanNewDep(lnLoan.getId(), lnLoan.getLnUserId(), lnSubAccountId, lnLoan.getApproveAmount(), lnLoan.getPeriod());
        } catch (Exception e) {
            //借款债权匹配失败
            e.printStackTrace();
            //匹配失败处理
            LnDepositionTarget depositionTarget = lnDepositionTargetMapper.selectByLoanId(lnLoan.getId());
            matchFail(depositionTarget,lnLoan,null,e);
            return;
        }

        if(CollectionUtils.isNotEmpty(list)){
        	LnDepositionTarget depositionTarget = lnDepositionTargetMapper.selectByLoanId(lnLoan.getId());
            //批量投标
            B2GReqMsg_HFBank_BatchExtBuy req = new B2GReqMsg_HFBank_BatchExtBuy();
            List<BatchExtBuyReqData>  dataList = getDataList(list);
            String orderNo = Util.generateOrderNo4BaoFoo(8);
            req.setOrder_no(orderNo);
            req.setProd_id(depositionTarget.getId().toString());
            req.setData(dataList);
            req.setTotal_num(list.size());
            req.setPartner_trans_date(new Date());
            req.setPartner_trans_time(new Date());
            B2GResMsg_HFBank_BatchExtBuy res = hfbankTransportService.batchExtBuy(req);
            if(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode().equals(res.getResCode())){
                specialJnlService.warn4Fail(lnLoan.getApproveAmount(), "标的批量投标订单号=["+orderNo+"]产品Id=["+depositionTarget.getId().toString()+"]请求超时", lnLoan.getPayOrderNo(), "【赞分期标的批量投标】",true);
                throw new PTMessageException(PTMessageEnum.DEP_TARGET_BATCH_BUY_TIME_OUT);
            }
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode()) && StringUtil.isNotEmpty(res.getSuccess_num()) &&
                    Integer.parseInt(res.getSuccess_num()) == list.size()){
                //批量投标全部成功,标的表置为BID,增加一条标的流水
                depCommonService.updateTargetStatus(true,Constants.DEP_TARGET_BID,DepTargetEnum.DEP_TARGET_OPERATE_BID,depositionTarget,lnLoan,orderNo);

                //标的成立
                B2GReqMsg_HFBank_EstablishOrAbandon establish = new B2GReqMsg_HFBank_EstablishOrAbandon();
                establish.setProd_id(depositionTarget.getId().toString());
                establish.setFlag(Constants.ESTABLISH_OR_ABANDON_FLAG_ABLISH);
                String estabishOrderNo = Util.generateOrderNo4BaoFoo(8);
                establish.setOrder_no(estabishOrderNo);
                List<EstablishOrAbandonReqFundData> fundDatas = new ArrayList<>();
                EstablishOrAbandonReqFundData fundData = new EstablishOrAbandonReqFundData();
                fundData.setPayout_plat_type(Constants.PAYOUT_PLAT_TYPE_FEE);
                if(lnLoan.getHeadFee() != null){
                    fundData.setPayout_amt(lnLoan.getHeadFee().toString());
                }else{
                    fundData.setPayout_amt("0");
                }
                fundDatas.add(fundData);
                establish.setFunddata(fundDatas);
                List<EstablishOrAbandonReqRepayPlan> repayPlans = new ArrayList<>();
                EstablishOrAbandonReqRepayPlan repayPlan = new EstablishOrAbandonReqRepayPlan();
                Double repay_amt = MoneyUtil.add(lnLoan.getApproveAmount(), 
                		MoneyUtil.divide(MoneyUtil.multiply(
                				MoneyUtil.multiply(lnLoan.getApproveAmount(), lnLoan.getAgreementRate()).doubleValue(),90).doubleValue()
                			, 365, 2).doubleValue()
                		).doubleValue();
                		/*lnLoan.getApproveAmount() + lnLoan.getApproveAmount() * lnLoan.getAgreementRate()/365 * 90;*/
                repayPlan.setRepay_amt(MoneyUtil.defaultRound(repay_amt).toString());
                repayPlan.setRepay_fee("0");
                repayPlan.setRepay_num("1");

                repayPlan.setRepay_date(DateUtils.addDays(lnLoan.getApplyTime(),89));
                repayPlans.add(repayPlan);
                establish.setRepay_plan_list(repayPlans);
                B2GResMsg_HFBank_EstablishOrAbandon establishRes = new  B2GResMsg_HFBank_EstablishOrAbandon();;
                try {
                	establishRes = hfbankTransportService.establishOrAbandon(establish);
				} catch (Exception e) {
		            log.error("赞分期标的成立请求异常：{}", e);
		            establishRes.setResCode(ConstantUtil.BSRESCODE_FAIL);
		            establishRes.setResMsg("通讯失败，置为失败");
				}
                
                if(establishRes.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)){
//                        && Constants.HF_ORDER_STATUS_SUCC.equals(establishRes.getData().getOrder_status())
                    //修改标的成立为SET_UP，新增一条标的成立成功流水
                    depCommonService.updateTargetStatus(true,Constants.DEP_TARGET_SET_UP,DepTargetEnum.DEP_TARGET_OPERATE_SET_UP,depositionTarget,lnLoan,estabishOrderNo);

                    //融资人余额子账户+
                    LnSubAccountExample example = new LnSubAccountExample();
                    example.createCriteria().andAccountTypeEqualTo(Constants.LOAN_ACT_TYPE_DEP_JSH).andLnUserIdEqualTo(lnLoan.getLnUserId());
                    List<LnSubAccount> lnSubAccounts = lnSubAccountMapper.selectByExample(example);
                    if(CollectionUtils.isEmpty(lnSubAccounts)){
                        throw new PTMessageException(PTMessageEnum.SUB_ACCOUNT_NO_EXIT,",融资人余额子账户不存在");
                    }
                    final LnSubAccount lnSubAccount = lnSubAccounts.get(0);
                    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                        @Override
                        protected void doInTransactionWithoutResult(TransactionStatus status) {
                            LnSubAccount lnActLock = lnSubAccountMapper.selectByPrimaryKey4Lock(lnSubAccount.getId());
                            LnSubAccount tempLnAct = new LnSubAccount();
                            tempLnAct.setId(lnActLock.getId());
                            tempLnAct.setBalance(MoneyUtil.add(lnActLock.getBalance(), lnLoan.getApproveAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                            tempLnAct.setAvailableBalance(MoneyUtil.add(lnActLock.getAvailableBalance(), lnLoan.getApproveAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                            tempLnAct.setCanWithdraw(MoneyUtil.add(lnActLock.getCanWithdraw(), lnLoan.getApproveAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                            tempLnAct.setUpdateTime(new Date());
                            lnSubAccountMapper.updateByPrimaryKeySelective(tempLnAct);
                            //借款人账户记账
                            LnAccountJnl lnAccountJnl = new LnAccountJnl();
                            lnAccountJnl.setTransTime(new Date());
                            lnAccountJnl.setTransCode(Constants.LN_DEP_JSH_ADD);
                            lnAccountJnl.setTransName("借款到余额");
                            lnAccountJnl.setTransAmount(lnLoan.getApproveAmount());
                            lnAccountJnl.setSysTime(new Date());
                            lnAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                            lnAccountJnl.setUserId1(lnActLock.getLnUserId());
                            lnAccountJnl.setSubAccountId1(lnActLock.getId());
                            lnAccountJnl.setBeforeBalance1(lnActLock.getBalance());
                            lnAccountJnl.setAfterBalance1(tempLnAct.getBalance());
                            lnAccountJnl.setBeforeAvialableBalance1(lnActLock.getAvailableBalance());
                            lnAccountJnl.setAfterAvialableBalance1(tempLnAct.getAvailableBalance());
                            lnAccountJnl.setBeforeFreezeBalance1(lnActLock.getFreezeBalance());
                            lnAccountJnl.setAfterFreezeBalance1(lnActLock.getFreezeBalance());
                            lnAccountJnl.setFee(0.0);
                            lnAccountJnlMapper.insertSelective(lnAccountJnl);
                        }
                    });

                    //标的成立成功调用标的出账
                    payProcessZan(lnLoan);
                }else{
                    //新增一条标的成立失败流水
                    depCommonService.updateTargetStatus(false,Constants.DEP_TARGET_SET_UP,DepTargetEnum.DEP_TARGET_OPERATE_SET_UP,depositionTarget,lnLoan, estabishOrderNo);
                    specialJnlService.warn4Fail(lnLoan.getApproveAmount(), "赞分期标的成立请求失败"+establishRes.getResMsg(), lnLoan.getPayOrderNo(), "【赞分期标的成立】",true);
                    throw new PTMessageException(PTMessageEnum.DEP_TARGET_ESTABLISH_ERROR);
                }
            }else{
                //批量投标失败处理
                buyActionFail(depositionTarget,lnLoan,list,orderNo,null);
            }
        }else{
            //匹配失败处理
        	LnDepositionTarget depositionTarget = lnDepositionTargetMapper.selectByLoanId(lnLoan.getId());
            matchFail(depositionTarget,lnLoan,null,null);
        }
            
            //relationService.confirmLoanRelation4LoanNew(lnLoan.getId(), lnLoan.getLnUserId(), lnSubAccountId, lnLoan.getApproveAmount(), lnLoan.getPeriod());

    }
    /**
     * 批量投标失败
     * @param depositionTarget
     * @param lnLoan
     * @param list
     * @param orderNo
     */
    private void buyActionFail(LnDepositionTarget depositionTarget,
			LnLoan lnLoan, List<LoanRelation4DepVO> list, String orderNo, Exception error) {
    	/**
		 * [1、修改借款表信息，标的表信息
		 * 2、发送标的废除，根据废除返回succ/fail记录存管标的操作流水表
		 * 3、通知赞分期]
		 * 4、存管标的操作流水表，投标失败
		 * 5、ln_loan_relation表状态修改
		 * 6、冻结解冻
		 */
    	//存管标的操作流水表
    	LnDepositionTargetJnl depositionTargetJnlBidFailTemp = new LnDepositionTargetJnl();
    	depositionTargetJnlBidFailTemp.setProdId(depositionTarget.getId());
    	depositionTargetJnlBidFailTemp.setTransTime(new Date());
    	depositionTargetJnlBidFailTemp.setTransType(Constants.TARGET_JNL_TRANS_TYPE_PROD_BID);
    	depositionTargetJnlBidFailTemp.setTransName("批量投标");
    	depositionTargetJnlBidFailTemp.setAmount(lnLoan.getApproveAmount());
    	depositionTargetJnlBidFailTemp.setOrderNo(orderNo);
    	depositionTargetJnlBidFailTemp.setProdStatus(Constants.DEP_TARGET_PUBLISH);
    	depositionTargetJnlBidFailTemp.setCreateTime(new Date());
    	depositionTargetJnlBidFailTemp.setUpdateTime(new Date());
    	depositionTargetJnlBidFailTemp.setTransStatus(Constants.TARGET_JNL_TRANS_STATUS_FAIL);
    	depositionTargetJnlMapper.insertSelective(depositionTargetJnlBidFailTemp);
    	
    	matchFail(depositionTarget,lnLoan,list,error);
    	 

    	for (LoanRelation4DepVO loanRelation4DepVO : list) {
    		//冻结解冻
    		loanAccountService.chargeLoanUnFreeze(loanRelation4DepVO.getLnLoanRelation().getInitAmount(), 
    				loanRelation4DepVO.getLnLoanRelation().getBsSubAccountId());
			
			LnLoanRelation lnLoanRelation = loanRelationMapper.selectByPrimaryKey(loanRelation4DepVO.getLnLoanRelation().getId());
			//ln_loan_relation表状态修改
			LnLoanRelation lnLoanRelationTemp = new LnLoanRelation();
			lnLoanRelationTemp.setId(lnLoanRelation.getId());
			lnLoanRelationTemp.setStatus(Constants.LOAN_RELATION_STATUS_FAIL);
			lnLoanRelationTemp.setUpdateTime(new Date());
			loanRelationMapper.updateByPrimaryKeySelective(lnLoanRelationTemp);
		}
	}

    /**
     * 债权匹配失败
     * @param depositionTarget
     * @param lnLoan
     * @param list
     * @param error
     */
	private void matchFail(LnDepositionTarget depositionTarget, LnLoan lnLoan,
			List<LoanRelation4DepVO> list, Exception error) {
		/**
		 * 1、修改借款表信息，标的表信息
		 * 2、发送标的废除，根据废除返回succ/fail记录存管标的操作流水表
		 * 3、通知赞分期
		 */

		//标的废除
    	String orderNo = Util.generateOrderNo4BaoFoo(8);
        B2GReqMsg_HFBank_EstablishOrAbandon abandonReq = new B2GReqMsg_HFBank_EstablishOrAbandon();
        abandonReq.setProd_id(depositionTarget.getId().toString());
        abandonReq.setOrder_no(orderNo);
        abandonReq.setRemark("标的废除");
        abandonReq.setFlag(Constants.ESTABLISH_OR_ABANDON_FLAG_ABANDON);
        B2GResMsg_HFBank_EstablishOrAbandon abandonRes = new B2GResMsg_HFBank_EstablishOrAbandon();
        try {
        	abandonRes = hfbankTransportService.establishOrAbandon(abandonReq);
		} catch (Exception e) {
            log.error("赞分期标的废除请求异常：{}", e);
            abandonRes.setResCode(ConstantUtil.BSRESCODE_FAIL);
            abandonRes.setResMsg("通讯失败，置为失败");
		}

        if(ConstantUtil.BSRESCODE_SUCCESS.equals(abandonRes.getResCode())){
//        if(ConstantUtil.BSRESCODE_SUCCESS.equals(abandonRes.getResCode()) &&　abandonRes.getData() != null
//        		&& Constants.HF_ORDER_STATUS_SUCC.equals(abandonRes.getData().getOrder_status())){
            //修改借款表信息
            LnLoan loanTemp = new LnLoan();
            loanTemp.setId(lnLoan.getId());
            loanTemp.setUpdateTime(new Date());
            loanTemp.setStatus(LoanStatus.LOAN_STATUS_FAIL.getCode());
            lnLoanMapper.updateByPrimaryKeySelective(loanTemp);
            LnLoan loanNew = lnLoanMapper.selectByPrimaryKey(lnLoan.getId());

            //修改标的表信息并新增流水
            LnDepositionTarget depositionTargetTemp = new LnDepositionTarget();
            depositionTargetTemp.setId(depositionTarget.getId());
            depositionTargetTemp.setStatus(Constants.DEP_TARGET_CANCELLED);
            depositionTargetTemp.setUpdateTime(new Date());
            lnDepositionTargetMapper.updateByPrimaryKeySelective(depositionTargetTemp);
        	//修改标的成立为CANCELLED，新增一条标的废除成功流水
            depCommonService.updateTargetStatus(true,Constants.DEP_TARGET_CANCELLED,DepTargetEnum.DEP_TARGET_OPERATE_CANCELLED,depositionTarget,lnLoan,orderNo);
            Exception e = error;
            if (e instanceof PTMessageException) {
                //通知赞分期
                notifyLoan2Partner(loanNew, ((PTMessageException) e).getErrMessage());
                //告警
                specialJnlService.warn4FailNoSMS(loanNew.getApproveAmount(), ((PTMessageException) e).getErrMessage(), lnLoan.getPayOrderNo(), "【赞分期借款】");
                return;
            } else {
                notifyLoan2Partner(loanNew, PTMessageEnum.TRANS_ERROR.getMessage());
                //告警
                specialJnlService.warn4FailNoSMS(loanNew.getApproveAmount(), PTMessageEnum.TRANS_ERROR.getMessage(), lnLoan.getPayOrderNo(), "【赞分期借款】");
                return;
            }
        }else if(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode().equals(abandonRes.getResCode())){
            //新增一条标的废除失败流水
            depCommonService.updateTargetStatus(false,Constants.DEP_TARGET_CANCELLED,DepTargetEnum.DEP_TARGET_OPERATE_CANCELLED,depositionTarget,lnLoan, orderNo);

            //告警
            specialJnlService.warn4Fail(lnLoan.getApproveAmount(), "赞分期标的废除超时depositionTargetId："+depositionTarget.getId(),
                    depositionTarget.getId().toString(), "【赞分期标的废除】", false);
            throw new PTMessageException(PTMessageEnum.DEP_TARGET_ABANDON_ERROR);
        } else{
        	//新增一条标的废除失败流水
            depCommonService.updateTargetStatus(false,Constants.DEP_TARGET_CANCELLED,DepTargetEnum.DEP_TARGET_OPERATE_CANCELLED,depositionTarget,lnLoan, orderNo);
        	
        	//告警
        	specialJnlService.warn4Fail(lnLoan.getApproveAmount(), "赞分期标的废除失败depositionTargetId："+depositionTarget.getId(), 
        			depositionTarget.getId().toString(), "【赞分期标的废除】", false);
            throw new PTMessageException(PTMessageEnum.DEP_TARGET_ABANDON_ERROR);
        }

        
	}

	//list转换
    private List<BatchExtBuyReqData> getDataList(List<LoanRelation4DepVO> list) {
    	List<BatchExtBuyReqData> dataList = new ArrayList<BatchExtBuyReqData>();
		for (LoanRelation4DepVO loanRelation4DepVO : list) {
			BatchExtBuyReqData data = new BatchExtBuyReqData();
			data.setDetail_no(Util.generateOrderNo4Pay19(loanRelation4DepVO.getLnLoanRelation().getId()));
			data.setPlatcust(loanRelation4DepVO.getHfUserId());
			data.setTrans_amt(loanRelation4DepVO.getLnLoanRelation().getInitAmount());
			data.setExperience_amt(0d);
			data.setCoupon_amt(0d);
			data.setSelf_amt(loanRelation4DepVO.getLnLoanRelation().getInitAmount());
			data.setIn_interest(0d);
			data.setSubject_priority(Constants.SUBJECT_PRIORITY_0);
			dataList.add(data);
		}
		return dataList;
	}

    /**
     * 赞分期支付处理
     * @param lnLoan
     */
    @Override
    public void payProcessZan(LnLoan lnLoan) {
    	
    	
    	LnUser lnUser  = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());
    	
        //新增一条ln_pay_orders记录
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(1);
        lnPayOrders.setAmount(lnLoan.getApproveAmount());
        //取时间最近一条
        LnBindCard lnBindCard = lnBindCardMapper.selectLatelyCard(lnLoan.getLnUserId());
        if(lnBindCard == null){
            throw new PTMessageException(PTMessageEnum.USER_NOT_BIND_CARD);
        }
        lnPayOrders.setBankCardNo(lnBindCard.getBankCard());
        BsCardBin bsCardBin = bankCardService.queryBankBin(lnBindCard.getBankCard());
        BsBank bank = bankCardService.findBankById(bsCardBin.getBankId());
        lnPayOrders.setBankId(bsCardBin.getBankId());
        lnPayOrders.setBankName(lnBindCard.getBankName());
        lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
        lnPayOrders.setIdCard(lnBindCard.getIdCard());
        lnPayOrders.setLnUserId(lnBindCard.getLnUserId());
        lnPayOrders.setMobile(lnBindCard.getMobile());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(lnLoan.getPayOrderNo());
        lnPayOrders.setPartnerCode(PartnerEnum.ZAN.getCode());
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()));
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_LOAN.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrders.setUserName(lnBindCard.getUserName());
        
        payOrdersMapper.insertSelective(lnPayOrders);

        //新增一条ln_pay_orders_jnl的INIT记录
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setTransAmount(lnLoan.getApproveAmount());
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_PAYING.getCode());
        lnPayOrdersJnl.setUserId(lnBindCard.getLnUserId());
        lnPayOrdersJnl.setSysTime(new Date());
        payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

        //发起标的出账请求
        LnDepositionTarget lnDepositionTarget = lnDepositionTargetMapper.selectByLoanId(lnLoan.getId());
        B2GReqMsg_HFBank_ChargeOff chargeOff = new B2GReqMsg_HFBank_ChargeOff();
        chargeOff.setProd_id(lnDepositionTarget.getId().toString());
        chargeOff.setPay_code(Constants.HF_PAY_CODE_HFBANK_OUT);    //行内通道出金编码
        //出账订单号和lnPayOrders订单号保持一致
        chargeOff.setOrder_no(lnLoan.getPayOrderNo());
        chargeOff.setPartner_trans_date(new Date());
        chargeOff.setPartner_trans_time(new Date());
        List<ChargeOffReqDetail> chargeOffDetails = new ArrayList<ChargeOffReqDetail>();
        ChargeOffReqDetail chargeOffDetail = new ChargeOffReqDetail();
        chargeOffDetail.setIs_advance(Constants.HF_WITHDRAW_IS_ADVANCE_YES); //垫付
        chargeOffDetail.setPlatcust(lnUser.getHfUserId());
        chargeOffDetail.setOut_amt(lnLoan.getApproveAmount().toString());
        chargeOffDetail.setOpen_branch(lnBindCard.getBankCode());
        chargeOffDetail.setWithdraw_account(lnBindCard.getBankCard());
        chargeOffDetail.setPayee_name(lnBindCard.getUserName());
        chargeOffDetail.setClient_property(Constants.HF_CLIENT_PROPERTY_PERSON);//公私标示(0-个人 1-公司 )
        chargeOffDetail.setTran_type(Constants.HF_WITHDRAW_TRAN_TYPE_PAY_REAL);
        chargeOffDetail.setBank_code(bank.getUnionBankId());
        chargeOffDetail.setBank_name(lnBindCard.getBankName());
        chargeOffDetails.add(chargeOffDetail);
        chargeOff.setCharge_off_list(chargeOffDetails);
        B2GResMsg_HFBank_ChargeOff res = null;
        try {
        	log.info("标的出账请求订单号=["+lnLoan.getPayOrderNo()+"]产品Id=["+lnDepositionTarget.getId().toString()+"]联行行号=["+chargeOffDetail.getBank_id()+"]");
            res = hfbankTransportService.chargeOff(chargeOff);
        } catch (Exception e) {
            log.error("赞分期标的出账通讯失败：{}", e);
            specialJnlService.warn4Fail(lnLoan.getApproveAmount(), "赞分期标的出账请求异常", lnLoan.getPayOrderNo(), "【赞分期标的出账】",true);
            return;
        }
        if(Constants.DEP_RECODE_SUCCESS.equals(res.getRecode()) && res.getData() != null &&
                Constants.HF_ORDER_STATUS_SUCC.equals(res.getData().getOrder_status())){
            //改借款状态为放款中
            LnLoan loanTemp = new LnLoan();
            loanTemp.setId(lnLoan.getId());
            loanTemp.setUpdateTime(new Date());
            loanTemp.setStatus(LoanStatus.LOAN_STATUS_PAYING.getCode());
            lnLoanMapper.updateByPrimaryKeySelective(loanTemp);
            depCommonService.updateTargetStatus(true,Constants.DEP_TARGET_SET_UP,DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE,lnDepositionTarget,lnLoan,lnLoan.getPayOrderNo());
        }else{
            G2BReqMsg_HFBank_OutOfAccount failReq = new G2BReqMsg_HFBank_OutOfAccount();
            failReq.setOrder_no(lnLoan.getPayOrderNo());
            failReq.setOrder_status(Constants.HF_OUT_OF_ACCOUNT_FAIL);
            failReq.setError_no(res.getRecode());
            failReq.setError_info(res.getRecode() + (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
            outOfAccountResultAcceptZan(failReq, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE);
        }
    }

    public void outOfAccountResultAcceptZan(final G2BReqMsg_HFBank_OutOfAccount req, final DepTargetEnum depTargetEnum) {
        LnPayOrdersExample example4IdCard = new LnPayOrdersExample();
        example4IdCard.createCriteria().andOrderNoEqualTo(req.getOrder_no());
        final List<LnPayOrders> orders4IdCard= payOrdersMapper.selectByExample(example4IdCard);
        if(CollectionUtils.isEmpty(orders4IdCard)){
            throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST,"订单号"+req.getOrder_no()+"对应订单不存在");
        }
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_LOAN.getKey() + orders4IdCard.get(0).getIdCard());
            transactionTemplate.execute(new TransactionCallbackWithoutResult(){
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    //修改ln_pay_orders为成功或失败
                    LnPayOrders lnPayOrder = orders4IdCard.get(0);
                    if(lnPayOrder.getStatus() != Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode())){
                        throw new PTMessageException(PTMessageEnum.ORDER_STATUS_UPDATE_ERROR,"订单号"+req.getOrder_no()+"非处理中");
                    }
                    LnPayOrders orderUpdate = new LnPayOrders();
                    orderUpdate.setId(lnPayOrder.getId());
                    orderUpdate.setUpdateTime(new Date());
                    orderUpdate.setPayPath(PayPathEnum.find(req.getPay_path())==null?
                    		null:PayPathEnum.find(req.getPay_path()).getPayPathVal());
                    if(req.getOrder_status().equals(Constants.HF_OUT_OF_ACCOUNT_SUCCESS)){
                    	orderUpdate.setReturnCode(ConstantUtil.BSRESCODE_SUCCESS);
                        orderUpdate.setReturnMsg(ConstantUtil.DEFAULT_SUCESSMSG);
                        orderUpdate.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
                    }else {
                        orderUpdate.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
                        //记录失败原因
                        orderUpdate.setReturnCode(req.getError_no());
                        orderUpdate.setReturnMsg(req.getError_info());
                    }
                    payOrdersMapper.updateByPrimaryKeySelective(orderUpdate);

                    //记录ln_pay_orders_jnl表
                    LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                    lnPayOrdersJnl.setSubAccountId(lnPayOrder.getSubAccountId());
                    lnPayOrdersJnl.setChannelTransType(lnPayOrder.getChannelTransType());
                    lnPayOrdersJnl.setCreateTime(new Date());
                    lnPayOrdersJnl.setOrderId(lnPayOrder.getId());
                    lnPayOrdersJnl.setOrderNo(lnPayOrder.getOrderNo());
                    lnPayOrdersJnl.setTransAmount(lnPayOrder.getAmount());
                    if(req.getOrder_status().equals(Constants.HF_OUT_OF_ACCOUNT_SUCCESS)){
                        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
                    }else {
                        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
                    }
                    lnPayOrdersJnl.setUserId(lnPayOrder.getLnUserId());
                    lnPayOrdersJnl.setSysTime(new Date());
                    payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

                    //修改ln_deposition_target状态
                    LnLoanExample exampleLoan = new LnLoanExample();
                    exampleLoan.createCriteria().andPayOrderNoEqualTo(req.getOrder_no());
                    List<LnLoan> lnLoans = lnLoanMapper.selectByExample(exampleLoan);
                    if(CollectionUtils.isEmpty(lnLoans)){
                        throw new PTMessageException(PTMessageEnum.LOAN_DATA_NOT_FOUND,",订单号"+req.getOrder_no()+"对应借款信息不存在");
                    }
                    LnLoan lnLoan = lnLoans.get(0);
                    LnDepositionTarget lnDepositionTarget = lnDepositionTargetMapper.selectByLoanId(lnLoan.getId());
                    if(req.getOrder_status().equals(Constants.HF_OUT_OF_ACCOUNT_SUCCESS)){
                        //修改ln_deposition_target为已出账,新增一条ln_deposition_target_jnl记录
                        depCommonService.updateTargetStatus(true,Constants.DEP_TARGET_CHARGE_OFF,DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF,lnDepositionTarget,lnLoan,req.getOrder_no());
                        //修改借款表为成功
                        LnLoan loanTemp = new LnLoan();
                        loanTemp.setId(lnLoan.getId());
                        loanTemp.setUpdateTime(new Date());
                        loanTemp.setLoanTime(new Date());
                        loanTemp.setStatus(LoanStatus.LOAN_STATUS_PAIED.getCode());
                        lnLoanMapper.updateByPrimaryKeySelective(loanTemp);

                        //更新还款计划表
                        LnRepayScheduleExample repayScheduleExample = new LnRepayScheduleExample();
                        repayScheduleExample.createCriteria().andLoanIdEqualTo(lnLoan.getId());
                        List<LnRepaySchedule> repayScheduleList = scheduleMapper.selectByExample(repayScheduleExample);

                        for (LnRepaySchedule lnRepaySchedule : repayScheduleList) {

                            LnRepaySchedule lnRepayScheduleTemp = new LnRepaySchedule();
                            lnRepayScheduleTemp.setId(lnRepaySchedule.getId());
                            lnRepayScheduleTemp.setUpdateTime(new Date());
                            lnRepayScheduleTemp.setPlanDate(DateUtil.addMonths(new Date(), lnRepaySchedule.getSerialId()));

                            scheduleMapper.updateByPrimaryKeySelective(lnRepayScheduleTemp);
                        }

                        //借贷关系表置为成功
                        LnLoanRelationExample exampleRel = new LnLoanRelationExample();
                        exampleRel.createCriteria().andLoanIdEqualTo(lnLoan.getId());
                        LnLoanRelation lnLoanRelation = new LnLoanRelation();
                        lnLoanRelation.setStatus(Constants.LOAN_RELATION_STATUS_SUCCESS);
                        lnLoanRelation.setUpdateTime(new Date());
                        loanRelationMapper.updateByExampleSelective(lnLoanRelation,exampleRel);

                        Double realAmount = 0d,redPac = 0d;
                        //查询借贷关系
                        LnLoanRelationExample relationExample = new LnLoanRelationExample();
                        relationExample.createCriteria().andLoanIdEqualTo(lnLoan.getId());
                        final List<LnLoanRelation> relationList = loanRelationMapper.selectByExample(relationExample);
                        List<InvestorRegInfo> infoList = new ArrayList<>();
                        for (LnLoanRelation relation : relationList) {
                            InvestorRegInfo info = new InvestorRegInfo();
                            info.setInvestorRegActId(relation.getBsSubAccountId());
                            info.setRegAmount(relation.getTotalAmount());
                            infoList.add(info);

                            BsSubAccountPairExample pairExample = new BsSubAccountPairExample();
                            pairExample.createCriteria().andRegDAccountIdEqualTo(relation.getBsSubAccountId());
                            List<BsSubAccountPair> pairList = pairMapper.selectByExample(pairExample);
                            if (CollectionUtils.isEmpty(pairList)) {
                                throw new PTMessageException(PTMessageEnum.ACCOUNT_NOT_FOUND);
                            }

                            //查询理财产品信息
                            BsSubAccount bsSubAccount = subAccountMapper.selectByPrimaryKey(relation.getBsSubAccountId());

                            //生成理财人回款计划
                            relationService.getFinanceRepayScheduleList(relation.getId(), relation.getLeftAmount(), lnLoan.getPeriod(), bsSubAccount.getProductRate(), new Date());
                            //用户的起息日在此时间前的，借款成功，发送奖励金，否则不发送奖励金
                            Date diffDate = DateUtil.parseDate(Constants.ZAN_BONUSGRANT_DIFFERENT_DATE);
                            if(bsSubAccount.getInterestBeginDate().compareTo(diffDate) < 0){
                                //奖励金
                                BsUser bsUser = bsUserMapper.selectByPrimaryKey(relation.getBsUserId());
                                DepUserBonusGrant4BuyProcess process = new DepUserBonusGrant4BuyProcess();
                                process.setUserBonusGrantService(depUserBonusGrantService);
                                process.setAmount(relation.getTotalAmount());
                                process.setBonusGrantType(depUserBonusGrantService.getBonusGrantTypeByUserId(relation.getBsUserId()));
                                process.setReferrerUserId(bsUser.getRecommendId());
                                process.setSelfUserId(relation.getBsUserId());
                                process.setSubAccountId(pairList.get(0).getRegDAccountId());
                                process.setPropertySymbol(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN);
                                new Thread(process).start();
                            }
                            //判断是否是最后一笔出借，然后发送微信通知
                            try {
                                subAccountService.sendWechat4LastLoan(pairList.get(0).getAuthAccountId());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        BaseAccount baseAccount = new BaseAccount();
                        baseAccount.setBorrowerUserId(lnLoan.getLnUserId());
                        baseAccount.setPartner(PartnerEnum.ZAN);
                        baseAccount.setAmount(lnLoan.getApproveAmount());

                        //计算手续费
                        CommissionVO commissionVO = commissionService.calServiceFee(lnLoan.getApproveAmount(), TransTypeEnum.LOAN_USER_LOAN, PayPlatformEnum.BAOFOO);
                        //记录手续费
                        BsServiceFee bsServiceFee = new BsServiceFee();
                        bsServiceFee.setPlanFee(commissionVO.getNeedPayAmount());
                        bsServiceFee.setDoneFee(commissionVO.getActPayAmount());
                        bsServiceFee.setTransAmount(lnLoan.getApproveAmount());
                        bsServiceFee.setFeeType(Constants.FEE_TYPE_LOAN);
                        bsServiceFee.setStatus(Constants.FEE_STATUS_COLLECT);
                        bsServiceFee.setCreateTime(new Date());
                        bsServiceFee.setOrderNo(lnLoan.getPayOrderNo());
                        bsServiceFee.setSubAccountId(relationList.get(0).getLnSubAccountId());
                        bsServiceFee.setUpdateTime(new Date());
                        bsServiceFee.setNote("应扣" + commissionVO.getNeedPayAmount() + "，实扣" + commissionVO.getActPayAmount());
                        bsServiceFee.setPaymentPlatformFee(commissionVO.getThreePartyPaymentServiceFee());
                        serviceFeeMapper.insertSelective(bsServiceFee);

                        //出账成功记账
                        loanAccountService.chargeLoan(baseAccount, infoList, relationList.get(0).getLnSubAccountId(), commissionVO.getActPayAmount());

                        //借款咨询与服务协议  签章
                        LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());
                        if (lnUser != null) {
                            SignSeal4BorrowServicesZanProcess process = new SignSeal4BorrowServicesZanProcess();
                            process.setSignSealService(signSealService);
                            SignSealUserInfoVO signSealUserInfo = new SignSealUserInfoVO();
                            signSealUserInfo.setIdCard(lnUser.getIdCard());
                            signSealUserInfo.setPdfPath(GlobEnvUtil.get("cfca.seal.pdfSrcPath"));
                            signSealUserInfo.setUserId(lnUser.getId());
                            signSealUserInfo.setUserName(lnUser.getUserName());
                            signSealUserInfo.setOrderNo(lnLoan.getPartnerLoanId());
                            signSealUserInfo.setMoney(String.valueOf(lnLoan.getApproveAmount()));
                            process.setSignSealUserInfo(signSealUserInfo);
                            process.setLnLoan(lnLoan);
                            new Thread(process).start();

                            //借款协议 签章
                            SignSeal4LoanAgreementProcess loanAgreementProcess = new SignSeal4LoanAgreementProcess();
                            List<BsUser4LoanVO> voList = new ArrayList<>();
                            for (LnLoanRelation relation : relationList) {
                                BsUser4LoanVO vo = new BsUser4LoanVO();
                                BsUser bsUser = bsUserMapper.selectByPrimaryKey(relation.getBsUserId());
                                vo.setUserId(bsUser.getId());
                                vo.setUserIdCardNo(bsUser.getIdCard());
                                vo.setUserName(bsUser.getUserName());
                                voList.add(vo);
                            }
                            SignSealUserInfoVO signSealUserInfo4LoanAgr = new SignSealUserInfoVO();
                            signSealUserInfo4LoanAgr.setIdCard(lnUser.getIdCard());
                            signSealUserInfo4LoanAgr.setPdfPath(GlobEnvUtil.get("cfca.seal.pdfSrcPath"));
                            signSealUserInfo4LoanAgr.setUserId(lnUser.getId());
                            signSealUserInfo4LoanAgr.setUserName(lnUser.getUserName());
                            signSealUserInfo4LoanAgr.setOrderNo(lnLoan.getPartnerLoanId());
                            signSealUserInfo4LoanAgr.setMoney(String.valueOf(lnLoan.getApproveAmount()));
                            loanAgreementProcess.setSignSealUserInfo(signSealUserInfo4LoanAgr);
                            loanAgreementProcess.setLnLoan(lnLoan);
                            loanAgreementProcess.setBsUserList(voList);
                            loanAgreementProcess.setSignSealService(signSealService);
                            new Thread(loanAgreementProcess).start();
                        }

                        LnLoan loanNew = lnLoanMapper.selectByPrimaryKey(lnLoan.getId());
                        notifyLoan2Partner(loanNew,"标的出账成功");
                    }else{
                        //新增一条ln_deposition_target_jnl记录
                        depCommonService.updateTargetStatus(false,DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE.equals(depTargetEnum)?Constants.DEP_TARGET_SET_UP:Constants.DEP_TARGET_CHARGE_OFF,
                                DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE.equals(depTargetEnum)?DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE:DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF,
                                lnDepositionTarget,lnLoan,req.getOrder_no());
                        //标的出账申请或回调通知失败,增加告警信息
                        specialJnlService.warn4Fail(lnLoan.getApproveAmount(), DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE.equals(depTargetEnum)?"赞分期标的出账申请,失败":"赞分期标的出账回调通知结果,失败" + req.getError_info()+",出账订单号=["+req.getOrder_no()+"]", lnLoan.getPayOrderNo(), "【赞分期标的出账】",false);
                    }
                }
            });
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_LOAN.getKey() + orders4IdCard.get(0).getIdCard());
        }
    }

    @Override
    public void notifyLoan(final DFResultInfo req) {
        LnPayOrdersExample payOrders4IdCardExample = new LnPayOrdersExample();
        payOrders4IdCardExample.createCriteria().andOrderNoEqualTo(req.getMxOrderId());
        final List<LnPayOrders> lnPayOrders4IdCardList = payOrdersMapper.selectByExample(payOrders4IdCardExample);
        if (CollectionUtils.isEmpty(lnPayOrders4IdCardList)) {
            throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
        }
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_LOAN.getKey() + lnPayOrders4IdCardList.get(0).getIdCard());
            //查询相关订单表
            LnPayOrdersExample payOrdersExample = new LnPayOrdersExample();
            payOrdersExample.createCriteria().andOrderNoEqualTo(req.getMxOrderId());
            final List<LnPayOrders> lnPayOrdersList = payOrdersMapper.selectByExample(payOrdersExample);
            if (CollectionUtils.isEmpty(lnPayOrdersList) ||
                    lnPayOrdersList.get(0).getStatus() != Constants.ORDER_STATUS_PAYING) {
                throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
            }
            //根据支付方订单号查询借款信息表
            LnLoanExample example = new LnLoanExample();
            example.createCriteria().andPayOrderNoEqualTo(req.getMxOrderId());
            List<LnLoan> loanList = lnLoanMapper.selectByExample(example);

            if (CollectionUtils.isEmpty(loanList)) {
                throw new PTMessageException(PTMessageEnum.LOAN_DATA_NOT_FOUND);
            }
            final LnLoan lnLoan = loanList.get(0);
            //查询借贷关系
            LnLoanRelationExample relationExample = new LnLoanRelationExample();
            relationExample.createCriteria().andLoanIdEqualTo(lnLoan.getId());
            final List<LnLoanRelation> relationList = loanRelationMapper.selectByExample(relationExample);
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    LnPayOrders order = payOrdersMapper.selectByPKForLock(lnPayOrdersList.get(0).getId());
                    //修改ln_pay_orders状态
                    LnPayOrders payOrdersTemp = new LnPayOrders();
                    payOrdersTemp.setId(order.getId());
                    payOrdersTemp.setUpdateTime(new Date());
                    payOrdersTemp.setReturnCode(req.getRetCode());
                    payOrdersTemp.setReturnMsg(req.getErrorMsg());
                    //记录ln_pay_orders_jnl表
                    LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();

                    if (OrderStatus.SUCCESS.getCode().equals(req.getOrderStatus())) {
                        payOrdersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
                        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
                    } else {
                        payOrdersTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
                        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
                    }

                    payOrdersMapper.updateByPrimaryKeySelective(payOrdersTemp);

                    lnPayOrdersJnl.setSubAccountId(order.getSubAccountId());
                    lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
                    lnPayOrdersJnl.setCreateTime(new Date());
                    lnPayOrdersJnl.setOrderId(order.getId());
                    lnPayOrdersJnl.setOrderNo(order.getOrderNo());
                    lnPayOrdersJnl.setTransAmount(order.getAmount());
                    lnPayOrdersJnl.setUserId(order.getLnUserId());
                    lnPayOrdersJnl.setSysTime(new Date());
                    lnPayOrdersJnl.setReturnCode(req.getRetCode());
                    lnPayOrdersJnl.setReturnMsg(req.getErrorMsg());

                    payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

                    //修改支付结果表状态
                    BsPayResultQueueExample queueExample = new BsPayResultQueueExample();
                    queueExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
                    List<BsPayResultQueue> queueList = queueMapper.selectByExample(queueExample);

                    if (CollectionUtils.isNotEmpty(queueList) && queueList.size() > 0) {
                        BsPayResultQueue queueTemp = new BsPayResultQueue();
                        queueTemp.setId(queueList.get(0).getId());
                        queueTemp.setUpdateTime(new Date());
                        if (OrderStatus.SUCCESS.getCode().equals(req.getOrderStatus())) {
                            queueTemp.setStatus("SUCC");
                        } else {
                            queueTemp.setStatus("FAIL");
                        }
                        queueMapper.updateByPrimaryKeySelective(queueTemp);
                    }
                }
            });
            //修改借款信息状态
            LnLoan temp = new LnLoan();
            temp.setId(lnLoan.getId());
            temp.setUpdateTime(new Date());
            if (OrderStatus.SUCCESS.getCode().equals(req.getOrderStatus())) {
                temp.setStatus(LoanStatus.LOAN_STATUS_PAIED.getCode());
                temp.setLoanTime(new Date());
                lnLoanMapper.updateByPrimaryKeySelective(temp);
                //更新还款计划表
                LnRepayScheduleExample repayScheduleExample = new LnRepayScheduleExample();
                repayScheduleExample.createCriteria().andLoanIdEqualTo(lnLoan.getId());
                List<LnRepaySchedule> repayScheduleList = scheduleMapper.selectByExample(repayScheduleExample);

                for (LnRepaySchedule lnRepaySchedule : repayScheduleList) {

                    LnRepaySchedule lnRepayScheduleTemp = new LnRepaySchedule();
                    lnRepayScheduleTemp.setId(lnRepaySchedule.getId());
                    lnRepayScheduleTemp.setUpdateTime(new Date());
                    lnRepayScheduleTemp.setPlanDate(DateUtil.addMonths(new Date(), lnRepaySchedule.getSerialId()));

                    scheduleMapper.updateByPrimaryKeySelective(lnRepayScheduleTemp);
                }

                BaseAccount baseAccount = new BaseAccount();
                baseAccount.setAmount(lnLoan.getApproveAmount());
                baseAccount.setBorrowerUserId(lnLoan.getLnUserId());
                baseAccount.setPartner(PartnerEnum.ZAN);
                //计算手续费
                CommissionVO commissionVO = commissionService.calServiceFee(lnLoan.getApproveAmount(), TransTypeEnum.LOAN_USER_LOAN, PayPlatformEnum.BAOFOO);

                //记录手续费
                BsServiceFee bsServiceFee = new BsServiceFee();
                bsServiceFee.setPlanFee(commissionVO.getNeedPayAmount());
                bsServiceFee.setDoneFee(commissionVO.getActPayAmount());
                bsServiceFee.setTransAmount(lnLoan.getApproveAmount());
                bsServiceFee.setFeeType(Constants.FEE_TYPE_LOAN);
                bsServiceFee.setStatus(Constants.FEE_STATUS_COLLECT);
                bsServiceFee.setCreateTime(new Date());
                bsServiceFee.setOrderNo(lnLoan.getPayOrderNo());
                bsServiceFee.setSubAccountId(relationList.get(0).getLnSubAccountId());
                bsServiceFee.setUpdateTime(new Date());
                bsServiceFee.setNote("应扣" + commissionVO.getNeedPayAmount() + "，实扣" + commissionVO.getActPayAmount());
                bsServiceFee.setPaymentPlatformFee(commissionVO.getThreePartyPaymentServiceFee());
                serviceFeeMapper.insertSelective(bsServiceFee);

                final List<InvestorRegInfo> infoList = new ArrayList<>();
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    public void doInTransactionWithoutResult(TransactionStatus status) {

                        for (LnLoanRelation relation : relationList) {

                            InvestorRegInfo info = new InvestorRegInfo();
                            info.setInvestorRegActId(relation.getBsSubAccountId());
                            info.setRegAmount(relation.getTotalAmount());
                            infoList.add(info);
                            //更新借贷关系状态
                            LnLoanRelation relationTemp = new LnLoanRelation();
                            relationTemp.setId(relation.getId());
                            relationTemp.setStatus(Constants.LOAN_RELATION_STATUS_SUCCESS);
                            relationTemp.setUpdateTime(new Date());
                            loanRelationMapper.updateByPrimaryKeySelective(relationTemp);

                            BsSubAccountPairExample pairExample = new BsSubAccountPairExample();
                            pairExample.createCriteria().andRegDAccountIdEqualTo(relation.getBsSubAccountId());
                            List<BsSubAccountPair> pairList = pairMapper.selectByExample(pairExample);
                            if (CollectionUtils.isEmpty(pairList)) {
                                throw new PTMessageException(PTMessageEnum.ACCOUNT_NOT_FOUND);
                            }

                            //查询理财产品信息
                            BsSubAccount bsSubAccount = subAccountMapper.selectByPrimaryKey(relation.getBsSubAccountId());

                            //生成理财人回款计划
                            relationService.getFinanceRepayScheduleList(relation.getId(), relation.getLeftAmount(), lnLoan.getPeriod(), bsSubAccount.getProductRate(), new Date());
                            //用户的起息日在此时间前的，借款成功，发送奖励金，否则不发送奖励金
                            Date diffDate = DateUtil.parseDate(Constants.ZAN_BONUSGRANT_DIFFERENT_DATE);
                            if(bsSubAccount.getInterestBeginDate().compareTo(diffDate) < 0){
                                //奖励金
                                BsUser bsUser = bsUserMapper.selectByPrimaryKey(relation.getBsUserId());
                                DepUserBonusGrant4BuyProcess process = new DepUserBonusGrant4BuyProcess();
                                process.setUserBonusGrantService(depUserBonusGrantService);
                                process.setAmount(relation.getTotalAmount());
                                process.setBonusGrantType(depUserBonusGrantService.getBonusGrantTypeByUserId(relation.getBsUserId()));
                                process.setReferrerUserId(bsUser.getRecommendId());
                                process.setSelfUserId(relation.getBsUserId());
                                process.setSubAccountId(pairList.get(0).getRegDAccountId());
                                process.setPropertySymbol(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN);
                                new Thread(process).start();
                            }
                            //判断是否是最后一笔出借，然后发送微信通知
                            try {
                                subAccountService.sendWechat4LastLoan(pairList.get(0).getAuthAccountId());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                //记账
                loanAccountService.chargeLoan(baseAccount, infoList, relationList.get(0).getLnSubAccountId(), commissionVO.getActPayAmount());
                //借款咨询与服务协议  签章
                LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());
                if (lnUser != null) {
                    SignSeal4BorrowServicesZanProcess process = new SignSeal4BorrowServicesZanProcess();
                    process.setSignSealService(signSealService);
                    SignSealUserInfoVO signSealUserInfo = new SignSealUserInfoVO();
                    signSealUserInfo.setIdCard(lnUser.getIdCard());
                    signSealUserInfo.setPdfPath(GlobEnvUtil.get("cfca.seal.pdfSrcPath"));
                    signSealUserInfo.setUserId(lnUser.getId());
                    signSealUserInfo.setUserName(lnUser.getUserName());
                    signSealUserInfo.setOrderNo(lnLoan.getPartnerLoanId());
                    signSealUserInfo.setMoney(String.valueOf(lnLoan.getApproveAmount()));
                    process.setSignSealUserInfo(signSealUserInfo);
                    process.setLnLoan(lnLoan);
                    new Thread(process).start();

                    //借款协议 签章
                    SignSeal4LoanAgreementProcess loanAgreementProcess = new SignSeal4LoanAgreementProcess();
                    List<BsUser4LoanVO> voList = new ArrayList<>();
                    for (LnLoanRelation relation : relationList) {
                        BsUser4LoanVO vo = new BsUser4LoanVO();
                        BsUser bsUser = bsUserMapper.selectByPrimaryKey(relation.getBsUserId());
                        vo.setUserId(bsUser.getId());
                        vo.setUserIdCardNo(bsUser.getIdCard());
                        vo.setUserName(bsUser.getUserName());
                        voList.add(vo);
                    }
                    SignSealUserInfoVO signSealUserInfo4LoanAgr = new SignSealUserInfoVO();
                    signSealUserInfo4LoanAgr.setIdCard(lnUser.getIdCard());
                    signSealUserInfo4LoanAgr.setPdfPath(GlobEnvUtil.get("cfca.seal.pdfSrcPath"));
                    signSealUserInfo4LoanAgr.setUserId(lnUser.getId());
                    signSealUserInfo4LoanAgr.setUserName(lnUser.getUserName());
                    signSealUserInfo4LoanAgr.setOrderNo(lnLoan.getPartnerLoanId());
                    signSealUserInfo4LoanAgr.setMoney(String.valueOf(lnLoan.getApproveAmount()));
                    loanAgreementProcess.setSignSealUserInfo(signSealUserInfo4LoanAgr);
                    loanAgreementProcess.setLnLoan(lnLoan);
                    loanAgreementProcess.setBsUserList(voList);
                    loanAgreementProcess.setSignSealService(signSealService);
                    new Thread(loanAgreementProcess).start();
                }


            } else {

                temp.setStatus(LoanStatus.LOAN_STATUS_FAIL.getCode());
                lnLoanMapper.updateByPrimaryKeySelective(temp);
                //从理财人的理财账户REG把资金转回AUTH/S_AUTH
                //记录bs_account_jnl
                for (LnLoanRelation relation : relationList) {

                    //更新借贷关系状态
                    LnLoanRelation relationTemp = new LnLoanRelation();
                    relationTemp.setId(relation.getId());
                    relationTemp.setStatus(Constants.LOAN_RELATION_STATUS_FAIL);
                    relationTemp.setUpdateTime(new Date());
                    loanRelationMapper.updateByPrimaryKeySelective(relationTemp);

                    BsSubAccountPairExample pairExample = new BsSubAccountPairExample();
                    pairExample.createCriteria().andRegDAccountIdEqualTo(relation.getBsSubAccountId());
                    List<BsSubAccountPair> pairList = pairMapper.selectByExample(pairExample);
                    if (CollectionUtils.isEmpty(pairList)) {
                        throw new PTMessageException(PTMessageEnum.ACCOUNT_NOT_FOUND);
                    }
                    loanAccountService.chargeLoanUnFreeze(relation.getTotalAmount(), pairList.get(0).getAuthAccountId());
                }
            }

            lnLoan.setStatus(temp.getStatus());
            notifyLoan2Partner(lnLoan, req.getErrorMsg());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_LOAN.getKey() + lnPayOrders4IdCardList.get(0).getIdCard());
        }
    }

    /**
     * 通知赞分期
     *
     * @param lnLoan
     */
    public void notifyLoan2Partner(final LnLoan lnLoan, final String errorMsg) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                B2GReqMsg_LoanNotice_NoticeLoan noticeLoan = new B2GReqMsg_LoanNotice_NoticeLoan();
                noticeLoan.setOrderNo(lnLoan.getPartnerOrderNo());
                LnPayOrdersExample ordersExample = new LnPayOrdersExample();
                ordersExample.createCriteria().andOrderNoEqualTo(lnLoan.getPayOrderNo());
                List<LnPayOrders> ordersList = payOrdersMapper.selectByExample(ordersExample);
                if (CollectionUtils.isNotEmpty(ordersList)) {
                    noticeLoan.setChannel(ordersList.get(0).getChannel());
                    noticeLoan.setPayChannel(ordersList.get(0).getChannel());
                }
                noticeLoan.setLoanId(lnLoan.getPartnerLoanId());
                noticeLoan.setLoanResultCode(lnLoan.getStatus().equals(LoanStatus.LOAN_STATUS_PAIED.getCode()) ? "SUCCESS" : "FAIL");
                noticeLoan.setLoanResultMsg(errorMsg);
                noticeLoan.setLoanTime(lnLoan.getLoanTime() != null ? DateUtil.format(lnLoan.getLoanTime()) : null);
                B2GResMsg_LoanNotice_NoticeLoan res = null;
                LnLoan loanTemp = new LnLoan();
                loanTemp.setId(lnLoan.getId());
                try {
                    res = noticeService.noticeLoan(noticeLoan);
                    if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                        loanTemp.setUpdateTime(new Date());
                        loanTemp.setInformStatus(LoanStatus.NOTICE_STATUS_SUCCESS.getCode());

                    } else {
                        throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
                    }
                } catch (Exception e) {

                    loanTemp.setInformStatus(LoanStatus.NOTICE_STATUS_FAIL.getCode());
                    loanTemp.setUpdateTime(new Date());
                    e.printStackTrace();
                }
                lnLoanMapper.updateByPrimaryKeySelective(loanTemp);
            }
        }).start();
    }
}

