package com.pinting.business.accounting.loan.service.impl;

import com.alibaba.fastjson.JSON;
import com.pinting.business.accounting.finance.model.DFResultInfo;
import com.pinting.business.accounting.loan.enums.DepTargetEnum;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.LoanSubjects;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.*;
import com.pinting.business.accounting.loan.service.*;
import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.coreflow.core.enums.BusinessTypeEnum;
import com.pinting.business.dao.*;
import com.pinting.business.enums.*;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.dto.LoanQueueDTO;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.service.common.CommissionService;
import com.pinting.business.service.common.DepCommonService;
import com.pinting.business.service.loan.LoanUserService;
import com.pinting.business.service.protocol.LoanAgreementSignSealService;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.business.util.Util;
import com.pinting.business.util.loan7.CalBillPeriod;
import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.*;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.hessian.message.hfbank.model.*;
import com.pinting.gateway.hessian.message.loan.model.RepaySchedule;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_LoanResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_LoanResultNotice;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.hessian.message.zsd.*;
import com.pinting.gateway.hessian.message.zsd.model.OpenAccountReq;
import com.pinting.gateway.out.service.HfbankTransportService;
import com.pinting.gateway.out.service.loan.DafyNoticeService;
import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;
import com.pinting.gateway.out.service.zsd.ZsdNoticeService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
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

import static com.pinting.core.util.MoneyUtil.defaultRound;

/**
 * Created by zhangbao on 2017/4/1.
 */
@Service
public class DepFixedLoanPaymentServiceImpl implements DepFixedLoanPaymentService {

    private Logger log = LoggerFactory.getLogger(DepFixedLoanPaymentServiceImpl.class);
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    private static Map<String,Double> settleRate4YunMap = new HashMap<String, Double>();;

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnPayOrdersMapper payOrdersMapper;
    @Autowired
    private LnPayOrdersJnlMapper payOrdersJnlMapper;
    @Autowired
    private LnLoanRelationMapper loanRelationMapper;
    @Autowired
    private CommissionService commissionService;
    @Autowired
    private BsServiceFeeMapper serviceFeeMapper;
    @Autowired
    private BsRevenueTransDetailMapper bsRevenueTransDetailMapper;
    @Autowired
    private LoanUserService loanUserService;
    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private LnBindCardMapper lnBindCardMapper;
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private DafyNoticeService dafyNoticeService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private HfbankTransportService hfbankTransportService;
    @Autowired
    private LnDepositionTargetMapper lnDepositionTargetMapper;
    @Autowired
    private DepFixedLoanRelationshipService depFixedLoanRelationshipService;
    @Autowired
    private LnPayOrdersMapper lnPayOrdersMapper;
    @Autowired
    private LnDepositionTargetJnlMapper depositionTargetJnlMapper;
    @Autowired
    private BsSubAccountPairMapper bsSubAccountPairMapper;
    @Autowired
    private LnSubAccountMapper lnSubAccountMapper;
    @Autowired
    private LnAccountJnlMapper lnAccountJnlMapper;
    @Autowired
    private DepFixedLoanAccountService depFixedLoanAccountService;
    @Autowired
    private DepCommonService depCommonService;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;
    @Autowired
    private BsBankMapper bsBankMapper;
    @Autowired
    private LoanPaymentService loanPaymentService;
    @Autowired
    private LnRepayScheduleMapper lnRepayScheduleMapper;
    @Autowired
    private LnRepayScheduleDetailMapper lnRepayScheduleDetailMapper;
    @Autowired
    private ZsdNoticeService zsdNoticeService;
    @Autowired
    private UcUserMapper ucUserMapper;
    @Autowired
    private UcUserExtMapper ucUserExtMapper;
    @Autowired
    private UcBankCardMapper ucBankCardMapper;
    @Autowired
    private Bs19payBankMapper bs19payBankMapper;
    @Autowired
    private LnAccountMapper lnAccountMapper;
    @Autowired
    private LnLoanBlackMapper lnLoanBlackMapper;
    @Autowired
    private SignSealService signSealService;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private DepLoan7NoticeService depLoan7NoticeService;
    @Autowired
    private LnDepositionRepayScheduleDetailMapper lnDepositionRepayScheduleDetailMapper;
    @Autowired
    private LnLoanRelationMapper lnLoanRelationMapper;
    @Autowired
    private LnFinanceRepayScheduleMapper lnFinanceRepayScheduleMapper;
    @Autowired
    private LnDepositionRepayScheduleMapper lnDepositionRepayScheduleMapper;
    @Autowired
    private BsUserSealFileMapper bsUserSealFileMapper;
    @Autowired
    private LoanAgreementSignSealService loanAgreementSignSealService;
    @Autowired
    private BsSysConfigMapper bsSysConfigMapper;
    @Autowired
    private DepFixedRepayPaymentService depFixedRepayPaymentService;
    @Autowired
    private LnLoanApplyRecordMapper lnLoanApplyRecordMapper;
    @Autowired
    private LnDailyAmountService lnDailyAmountService;
    @Autowired
    private DepFixedMatchLoanerInvestorService depFixedMatchLoanerInvestorService;
    @Autowired
    private AlgorithmService algorithmService;

    public void loanApply(G2BReqMsg_DafyLoan_ApplyLoan req, PartnerEnum partnerEnum) {
        req.setBankCard(StringUtil.isBlank(req.getBankCard()) ? req.getBankCard() : StringUtil.trimStr(req.getBankCard()));
        req.setName(StringUtil.isBlank(req.getName()) ? req.getName() : StringUtil.trimStr(req.getName()));
        req.setIdCard(StringUtil.isBlank(req.getIdCard()) ? req.getIdCard() : StringUtil.trimStr(req.getIdCard()));
        req.setMobile(StringUtil.isBlank(req.getMobile()) ? req.getMobile() : StringUtil.trimStr(req.getMobile()));
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_LOAN_SELF.getKey() + partnerEnum.getCode() + req.getIdCard());

            // 借款申请记录
            this.insertLoanApplyRecord(req, partnerEnum);

            Integer lnUserId = null;
            List<LnUser> list = null;
            try {
                //黑名单校验
                LnLoanBlackExample example = new LnLoanBlackExample();
                example.createCriteria().andMobileEqualTo(req.getMobile());
                List<LnLoanBlack> blackList = lnLoanBlackMapper.selectByExample(example);
                if(CollectionUtils.isNotEmpty(blackList)){
                	String bankCardNo = lnBindCardMapper.getBankCardByMobile(blackList.get(0).getMobile());
                	if(StringUtil.isNotBlank(bankCardNo) && req.getBankCard().equals(bankCardNo)){
                		//根据黑名单手机号查询最近一次绑卡卡号，相等异常
                		throw new PTMessageException(PTMessageEnum.YUN_SELF_LOAN_USER_FOUND_IN_BLACK);
                	}else{
                		for (LnLoanBlack lnLoanBlack : blackList) {
                			//空或者不相等，删除黑名单中的信息，继续其他判断
                    		lnLoanBlackMapper.deleteByPrimaryKey(lnLoanBlack.getId());
						}

                	}

                }
                //借款订单号和借款编号重复申请校验
                Integer count = lnLoanMapper.selectByOrderNoAndPartnerCode(req.getOrderNo(), partnerEnum.getCode());
                if (count > 0) {
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, partnerEnum.getName()+"订单号重复");
                }

                if(isExists4PartnerLoanId(req.getLoanId())){
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, partnerEnum.getName()+"借款编号重复");
                }
                //判断bankCode是否存在
                if(!BaoFooEnum.bankCodeNameMap.containsKey(req.getBankCode())){
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,",银行编码没有找到");
                }
                //根据银行卡号查询卡bin表，然后根据银行id和通道类型查询开户行，并判断是否和三方传过来的一致，不一致则返回相应错误
                BankBinVO bankBinVO = bankCardService.queryBankBin(req.getBankCard());
                if(bankBinVO == null){
                	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,",银行编码没有找到");
                }
                Bs19payBankExample payBankExample = new Bs19payBankExample();
                payBankExample.createCriteria().andBankIdEqualTo(bankBinVO.getBankId()).andChannelEqualTo(Constants.ORDER_CHANNEL_BAOFOO)
                	.andPayTypeEqualTo(1);
                List<Bs19payBank> bs19payBank = bs19payBankMapper.selectByExample(payBankExample);
                if(CollectionUtils.isEmpty(bs19payBank) || !req.getBankCode().equals(bs19payBank.get(0).getPay19BankCode())){
                	throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,",银行编码与实际银行账号信息不一致");
                }

                // 开户登记
                OpenAccountReq openAccountReq = new OpenAccountReq();
                openAccountReq.setPartnerCode(partnerEnum.getCode());
                openAccountReq.setIdCard(req.getIdCard());
                openAccountReq.setBankCode(req.getBankCode());
                openAccountReq.setBankCard(req.getBankCard());
                openAccountReq.setAnnualIncome(req.getMonthlyIncome() == null ? null : MoneyUtil.multiply(req.getMonthlyIncome(), 12).toString());
                openAccountReq.setCardHolder(req.getName());
                openAccountReq.setMobile(req.getMobile());
                openAccountReq.setUserId(req.getUserId());
                openAccountReq.setUcUserType(partnerEnum.getCode());
                openAccountReq.setEducation(req.getEducation());
                openAccountReq.setWorkUnit(req.getWorkUnit());
                openAccountReq.setMarriage(req.getMarriage());
                openAccountReq.setAddress(req.getAddress());
                openAccountReq.setEmail(req.getEmail());
                lnUserId = this.openAccount(openAccountReq);

                //统计借款人待还本金，加上此次申请本金，和最高借款本金比较，超过返回借款失败
                Double notRepayAmount = lnRepayScheduleMapper.sumNotRepayByLnUserId(lnUserId);
                Double maxLoanAmount = 20d;
				BsSysConfig config = sysConfigService.findConfigByKey(Constants.LOAN_MAX_SUM_AMOUNT);
				if(config != null){
					maxLoanAmount = MoneyUtil.multiply(Double.valueOf(config.getConfValue()), 10000).doubleValue();
				}
                if(maxLoanAmount.compareTo(MoneyUtil.add(notRepayAmount, req.getLoanAmount()).doubleValue()) < 0 ){
                	throw new PTMessageException(PTMessageEnum.LOAN_MAX_OUT);
                }

                // 云贷用户恒丰开户绑卡
                BindCardInfoReq cardInfoReq = new BindCardInfoReq();
                cardInfoReq.setBankCard(req.getBankCard());
                cardInfoReq.setBankCode(req.getBankCode());
                cardInfoReq.setIdCard(req.getIdCard());
                cardInfoReq.setLnUserId(lnUserId);
                cardInfoReq.setMobile(req.getMobile());
                cardInfoReq.setName(req.getName());
                cardInfoReq.setPartnerCode(partnerEnum.getCode());
                cardInfoReq.setUcUserType(partnerEnum.getCode());
                this.bindCard(cardInfoReq);

                LnUserExample lnUserExample = new LnUserExample();
                lnUserExample.createCriteria().andPartnerCodeEqualTo(partnerEnum.getCode()).andPartnerUserIdEqualTo(req.getUserId());
                list = lnUserMapper.selectByExample(lnUserExample);
                if (CollectionUtils.isEmpty(list)) {
                    throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_USER_NOT_EXIST);
                }

            } catch (PTMessageException e){
                LnUser lnUser = loanUserService.queryLoanUserExist(req.getUserId(), partnerEnum.getCode());
                if(lnUser != null) {
                    lnUserId = lnUser.getId();
                }

                if (!isExists4PartnerLoanId(req.getLoanId())) {
                    LnLoan lnLoan = new LnLoan();
                    lnLoan.setApplyAmount(req.getLoanAmount());
                    lnLoan.setApplyTime(req.getApplyTime());
                    lnLoan.setApproveAmount(req.getLoanAmount());
                    lnLoan.setBreakMaxDays(req.getBreakMaxDays());
                    lnLoan.setBreakTimes(req.getBreakTimes());
                    lnLoan.setHeadFee(req.getLoanFee());
                    lnLoan.setPartnerLoanId(req.getLoanId());
                    lnLoan.setCreateTime(new Date());
                    lnLoan.setUpdateTime(new Date());
                    lnLoan.setCreditAmount(req.getCreditAmount());
                    lnLoan.setLoanedAmount(req.getLoanAmount());
                    lnLoan.setCreditLevel(req.getCreditLevel());
                    lnLoan.setCreditScore(req.getCreditScore());
                    lnLoan.setLnUserId(lnUserId);
                    lnLoan.setLoanTimes(req.getLoanTimes());
                    lnLoan.setPartnerBusinessFlag(req.getBusinessType());
                    lnLoan.setPartnerOrderNo(req.getOrderNo());
                    lnLoan.setPayOrderNo(Util.generateOrderNo4BaoFoo(8));
                    lnLoan.setAddress(req.getAddress());
                    lnLoan.setEmail(req.getEmail());
                    //Double period = Math.floor(req.getLoanTerm()/30);
                    if(partnerEnum.equals(PartnerEnum.YUN_DAI_SELF)) {
                        Double period = Math.floor(req.getLoanTerm()/30);
                        lnLoan.setPeriod(period.intValue());
                        lnLoan.setPeriodUnit(Integer.valueOf(Constants.TARGET_PRODUCT_UNIT_MONTH));
                    }else{
                        lnLoan.setPeriod(req.getLoanTerm());
                        if (req.getLoanTerm() <= 12) {
                            lnLoan.setPeriodUnit(Integer.valueOf(Constants.TARGET_PRODUCT_UNIT_MONTH));
                        } else {
                            lnLoan.setPeriodUnit(Integer.valueOf(Constants.TARGET_PRODUCT_UNIT_DAY));
                        }
                    }
                    lnLoan.setPurpose(req.getPurpose());
                    lnLoan.setStatus(LoanStatus.LOAN_STATUS_FAIL.getCode());
                    lnLoan.setSubjectName(req.getSubjectName());
                    lnLoan.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                    //借款协议利率
                    Double agreementRate = MoneyUtil.divide(req.getLoanRate(), 100, 2).doubleValue();
                    lnLoan.setAgreementRate(agreementRate);
                    //借款服务费利率
                    if(partnerEnum.equals(PartnerEnum.YUN_DAI_SELF)) {
                         BsSysConfig loanServiceRateConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.LOAN_SERVICE_RATE_YUN_DAI);
                         lnLoan.setLoanServiceRate(loanServiceRateConfig != null?Double.valueOf(loanServiceRateConfig.getConfValue()) : null);
                    }else{
                         BsSysConfig loanServiceRateConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.LOAN_SERVICE_RATE_SEVEN_DAI);
                         lnLoan.setLoanServiceRate(loanServiceRateConfig != null?Double.valueOf(loanServiceRateConfig.getConfValue()) : null);
                    }

                    if(partnerEnum.equals(PartnerEnum.YUN_DAI_SELF)) {
                        //云贷借款---币港湾服务费利率
                        String settleRate4YunMapKey = DateUtil.format(new Date(), DateUtil.SIMPLE_DATE);//作为map的key
                        Double bgwSettleRate;
                        if(settleRate4YunMap.get(settleRate4YunMapKey) == null){
                            //map取不到当日的值，则先清空map,然后查询配置表，保存该值到map中
                            settleRate4YunMap.clear();
                            Double initRate = sysConfigService.findRatePercentByKey(Constants.YUN_DAI_SELF_SYS_SETTLE_RATE);
                            if(initRate != null){
                                settleRate4YunMap.put(settleRate4YunMapKey, initRate);
                            }
                        }
                        bgwSettleRate = settleRate4YunMap.get(settleRate4YunMapKey);
                        lnLoan.setBgwSettleRate(bgwSettleRate);
                    }

                    lnLoanMapper.insertSelective(lnLoan);
                }
                throw e;
            }

            //记录借款信息表ln_loan,状态为审核通过
            LnLoan lnLoan = new LnLoan();
            lnLoan.setApplyAmount(req.getLoanAmount());
            lnLoan.setApplyTime(req.getApplyTime());
            lnLoan.setApproveAmount(req.getLoanAmount());
            lnLoan.setBreakMaxDays(req.getBreakMaxDays());
            lnLoan.setBreakTimes(req.getBreakTimes());
            lnLoan.setHeadFee(req.getLoanFee());
            lnLoan.setPartnerLoanId(req.getLoanId());
            lnLoan.setCreateTime(new Date());
            lnLoan.setUpdateTime(new Date());
            lnLoan.setCreditAmount(req.getCreditAmount());
            lnLoan.setLoanedAmount(req.getLoanAmount());
            lnLoan.setCreditLevel(req.getCreditLevel());
            lnLoan.setCreditScore(req.getCreditScore());
            lnLoan.setLnUserId(lnUserId);
            lnLoan.setLoanTimes(req.getLoanTimes());
            lnLoan.setPartnerBusinessFlag(req.getBusinessType());
            lnLoan.setPartnerOrderNo(req.getOrderNo());
            lnLoan.setAddress(req.getAddress());
            lnLoan.setEmail(req.getEmail());
            lnLoan.setPayOrderNo(Util.generateOrderNo4BaoFoo(8));
            //Double period = Math.floor(req.getLoanTerm()/30);
            if(partnerEnum.equals(PartnerEnum.YUN_DAI_SELF)) {
                Double period = Math.floor(req.getLoanTerm()/30);
                lnLoan.setPeriod(period.intValue());
                lnLoan.setPeriodUnit(Integer.valueOf(Constants.TARGET_PRODUCT_UNIT_MONTH));
            }else{
                lnLoan.setPeriod(req.getLoanTerm());
                if (req.getLoanTerm() <= 12) {
                    lnLoan.setPeriodUnit(Integer.valueOf(Constants.TARGET_PRODUCT_UNIT_MONTH));
                } else {
                    lnLoan.setPeriodUnit(Integer.valueOf(Constants.TARGET_PRODUCT_UNIT_DAY));
                }
            }
            lnLoan.setPurpose(req.getPurpose());
            lnLoan.setStatus(LoanStatus.LOAN_STATUS_CHECKED.getCode());
            lnLoan.setSubjectName(req.getSubjectName());
            lnLoan.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
            //借款协议利率
            Double agreementRate = MoneyUtil.divide(req.getLoanRate(), 100, 2).doubleValue();
            lnLoan.setAgreementRate(agreementRate);
            //借款服务费利率
            if(partnerEnum.equals(PartnerEnum.YUN_DAI_SELF)) {
            	 BsSysConfig loanServiceRateConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.LOAN_SERVICE_RATE_YUN_DAI);
                 lnLoan.setLoanServiceRate(loanServiceRateConfig != null?Double.valueOf(loanServiceRateConfig.getConfValue()) : null);
            }else{
            	 BsSysConfig loanServiceRateConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.LOAN_SERVICE_RATE_SEVEN_DAI);
                 lnLoan.setLoanServiceRate(loanServiceRateConfig != null?Double.valueOf(loanServiceRateConfig.getConfValue()) : null);
            }

            if(partnerEnum.equals(PartnerEnum.YUN_DAI_SELF)) {
				//云贷借款---币港湾服务费利率
                String settleRate4YunMapKey = DateUtil.format(new Date(), DateUtil.SIMPLE_DATE);//作为map的key
                Double bgwSettleRate;
                if(settleRate4YunMap.get(settleRate4YunMapKey) == null){
					//map取不到当日的值，则先清空map,然后查询配置表，保存该值到map中
                	settleRate4YunMap.clear();
                	Double initRate = sysConfigService.findRatePercentByKey(Constants.YUN_DAI_SELF_SYS_SETTLE_RATE);
                	if(initRate != null){
                		settleRate4YunMap.put(settleRate4YunMapKey, initRate);
                	}
				}
				bgwSettleRate = settleRate4YunMap.get(settleRate4YunMapKey);
				lnLoan.setBgwSettleRate(bgwSettleRate);
			}

            lnLoanMapper.insertSelective(lnLoan);

            //新增一条ln_deposition_target的INIT记录
            LnDepositionTarget target = new LnDepositionTarget();
            target.setProdName(partnerEnum.getName() + lnLoan.getId().toString());
            target.setProdType(Constants.TARGET_PRODUCT_CYCLE);
            target.setTotalLimit(req.getLoanAmount());
            target.setSetupType(Constants.TARGET_PRODUCT_ESTABLISH_DAY);
            target.setSellDate(new Date());
            target.setInterestType(Constants.TARGET_PRODUCT_INTEREST_CHECK);
            Integer period=CalBillPeriod.calBillDays(req.getApplyTime());
            if(partnerEnum.equals(PartnerEnum.YUN_DAI_SELF)){
            	target.setCycle(req.getLoanTerm());//云贷产品周期为日,月数*30
            } else {
            	if (Constants.PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME.equals(req.getBusinessType())) {
            		target.setCycle(req.getLoanTerm());//七贷随借随还产品，直接存储
				} else {
					target.setCycle(period);//七贷先息后本产品周期为日,月数*30
				}
			}
            target.setCycleUnit(Constants.TARGET_PRODUCT_UNIT_DAY);
            target.setIstYear(lnLoan.getAgreementRate());
            target.setRepayType(Constants.TARGET_REPAY_TYPE_PERIOD);
            target.setLoanId(lnLoan.getId());
            target.setChargeOffAuto(Constants.TARGET_OUT_ACCOUNT_ACTIVE);
            target.setOverLimit(Constants.TARGET_OVER_LIMIT);
            target.setOverTotalLimit(req.getLoanAmount());
            target.setStatus(Constants.DEP_TARGET_INIT);
            target.setCreateTime(new Date());
            target.setUpdateTime(new Date());
            lnDepositionTargetMapper.insertSelective(target);
            String publishOrderNo = Util.generateOrderNo4BaoFoo(8);
            B2GReqMsg_HFBank_Publish publish = new B2GReqMsg_HFBank_Publish();
            log.info("标的发布的订单号=["+publishOrderNo+"]产品Id=["+target.getId().toString()+"]");
            publish.setProd_id(target.getId().toString());
            publish.setProd_name(target.getProdName());
            publish.setProd_type(target.getProdType());
            publish.setTotal_limit(req.getLoanAmount());
            publish.setValue_type(Constants.TARGET_PRODUCT_INTEREST_CHECK);
            publish.setCreate_type(Constants.TARGET_PRODUCT_ESTABLISH_DAY);
            publish.setSell_date(DateUtil.getDate(new Date()));
            publish.setCycle(target.getCycle());
            publish.setCycle_unit(target.getCycleUnit());
            publish.setIst_year(target.getIstYear());
            publish.setRepay_type(target.getRepayType());
            publish.setChargeOff_auto(target.getChargeOffAuto());
            publish.setOverLimit(target.getOverLimit());
            publish.setOver_total_limit(target.getOverTotalLimit());
            publish.setPartner_trans_date(new Date());
            publish.setPartner_trans_time(new Date());
            publish.setOrder_no(publishOrderNo);
            //融资信息列表赋值
            List<PublishFinancingInfo> infoList = new ArrayList<PublishFinancingInfo>();
            PublishFinancingInfo info = new PublishFinancingInfo();
            info.setCust_no(list.get(0).getHfUserId());
            info.setReg_date(new Date());
            info.setReg_time(new Date());
            //此处是否存在精度问题
            Double financInterest =  0.0d;
            if(partnerEnum.equals(PartnerEnum.YUN_DAI_SELF)){
            	financInterest = MoneyUtil.divide(MoneyUtil.multiply(req.getLoanTerm(),
                		MoneyUtil.multiply(target.getIstYear(),target.getTotalLimit()).doubleValue()
                		).doubleValue(),36500).doubleValue();
            }else{
            	financInterest = MoneyUtil.divide(MoneyUtil.multiply(period,
                		MoneyUtil.multiply(target.getIstYear(),target.getTotalLimit()).doubleValue()
                		).doubleValue(),36500).doubleValue();
            }
            Double financInterestRate = MoneyUtil.divide(financInterest,target.getTotalLimit()).doubleValue();
            info.setFinanc_int(String.valueOf(financInterestRate));
            info.setFee_int(0d);//恒丰增加校验-暂定0.00
            info.setOpen_branch(req.getBankCode());
            info.setWithdraw_account(req.getBankCard());
            info.setAccount_type(Constants.HF_LOAN_CARD_TYPE_PERSONAL);
            info.setPayee_name(req.getName());
            info.setFinanc_amt(target.getTotalLimit());
            infoList.add(info);
            publish.setFinancing_info_list(infoList);

            List<CompensationInfo> compensationInfos = new ArrayList<>();
//            BsSysConfig bsSysConfig = sysConfigService.findConfigByKey(VIPId4PartnerEnum.getEnumByCode(partnerEnum.getCode()).getCompensateIdKey());
//            if( bsSysConfig != null ) {
//            	String userString[] = bsSysConfig.getConfValue().split(",");
//                for (String comUserId : userString) {
//                	BsHfbankUserExtExample hfbankUserExtExample = new BsHfbankUserExtExample();
//                	hfbankUserExtExample.createCriteria().andUserIdEqualTo(Integer.parseInt(comUserId)).andStatusEqualTo(Constants.HFBANK_USER_EXT_STATUS_OPEN);
//                	List<BsHfbankUserExt> hfbankUserExts = bsHfbankUserExtMapper.selectByExample(hfbankUserExtExample);
//                	if(CollectionUtils.isEmpty(hfbankUserExts)){
//                        specialJnlService.warn4FailNoSMS(lnLoan.getApproveAmount(),partnerEnum.getName()+"代偿人在BsHfbankUserExt表中不存在", lnLoan.getPayOrderNo(), "【"+partnerEnum.getName()+"借款申请异常】");
//                        throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, partnerEnum.getName()+"代偿人BsHfbankUserExt表中不存在");
//                    }
//                    BsHfbankUserExt hfbankUserExt = hfbankUserExts.get(0);
//                	CompensationInfo compInfos = new CompensationInfo();
//                	compInfos.setPlatcust(hfbankUserExt.getHfUserId());
//                	compInfos.setType(Constants.HF_COMPENSATE_REPAY_TYPE_COMPENSATE);
//                	compensationInfos.add(compInfos);
//    			}
//                publish.setCompensation_info_list(compensationInfos);
//            }

            BsUserCompensateVO vo = depFixedRepayPaymentService.compensaterInfo(lnLoan.getCreateTime(), partnerEnum.getCode());
            if (vo==null ) {
            	 specialJnlService.warn4FailNoSMS(lnLoan.getApproveAmount(),partnerEnum.getName()+"代偿人信息未找到", lnLoan.getPayOrderNo(), "【"+partnerEnum.getName()+"借款申请异常】");
                 throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, partnerEnum.getName()+"代偿人信息未找到");
			}
            CompensationInfo compInfos = new CompensationInfo();
        	compInfos.setPlatcust(vo.getHfUserId());
        	compInfos.setType(Constants.HF_COMPENSATE_REPAY_TYPE_COMPENSATE);
        	compensationInfos.add(compInfos);
        	publish.setCompensation_info_list(compensationInfos);
            //标的发布请求
            B2GResMsg_HFBank_Publish res = null;
            try {
                res = hfbankTransportService.publish(publish);
            }catch (Exception e){
                log.error(partnerEnum.getName()+"标的发布请求异常：{}", e);
                res = new B2GResMsg_HFBank_Publish();
                res.setResCode(ConstantUtil.BSRESCODE_FAIL);
                res.setResMsg("通讯失败，置为失败");
            }
            //标的发布请求，成功时入redis
            if(res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)){
                //修改ln_deposition_target为PUBLISH，新增一条ln_deposition_target_jnl的成功记录
                depCommonService.updateTargetStatus(true,Constants.DEP_TARGET_PUBLISH,DepTargetEnum.DEP_TARGET_OPERATE_PUBLISH,target,lnLoan,publishOrderNo);
                try{
                    LoanQueueDTO loanQueueDTO=new LoanQueueDTO();
                    loanQueueDTO.setChannel(partnerEnum.getCode());
                    loanQueueDTO.setLnLoan(lnLoan);
                    //再查一遍绑卡记录，因为有修改的可能
                    LnBindCard newCard = loanUserService.queryIncrBindCardExist(req.getUserId(), partnerEnum.getCode(), req.getBankCard());
                    loanQueueDTO.setLnBindCard(newCard);
                    if(PartnerEnum.SEVEN_DAI_SELF.getCode().equals(partnerEnum.getCode())){
                    	jsClientDaoSupport.rpush("loan_7dai_queue", JSON.toJSONString(loanQueueDTO));
                    	log.info(">>>"+partnerEnum.getName()+"入借款队列loan_7dai_queue数据:" + JSON.toJSONString(loanQueueDTO) + "<<<");
                    }else{
                    	jsClientDaoSupport.rpush("loan_queue", JSON.toJSONString(loanQueueDTO));
                    	log.info(">>>"+partnerEnum.getName()+"入借款队列数据:" + JSON.toJSONString(loanQueueDTO) + "<<<");
                    }

                }catch (Exception e){
                    log.error(partnerEnum.getName()+"借款申请放入队列异常:{}",e);
                }
            }else{
                //新增一条ln_deposition_target_jnl的失败记录
                depCommonService.updateTargetStatus(false,Constants.DEP_TARGET_PUBLISH,DepTargetEnum.DEP_TARGET_OPERATE_PUBLISH,target,lnLoan,publishOrderNo);
                LnLoan updateLoan = new LnLoan();
                updateLoan.setId(lnLoan.getId());
                updateLoan.setUpdateTime(new Date());
                updateLoan.setStatus(LoanStatus.LOAN_STATUS_FAIL.getCode());
                lnLoanMapper.updateByPrimaryKeySelective(updateLoan);
                LnLoan newLoan = lnLoanMapper.selectByPrimaryKey(lnLoan.getId());
                notifyLoan2Partner(partnerEnum, newLoan,"标的发布请求失败", null);
                specialJnlService.warn4FailNoSMS(lnLoan.getApproveAmount(), "标的发布请求失败", lnLoan.getPayOrderNo(), "【"+partnerEnum.getName()+"借款标的发布】");
                throw new PTMessageException(PTMessageEnum.DEP_TARGET_PUBLISH_ERROR, StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg());
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_LOAN_SELF.getKey() + partnerEnum.getCode() + req.getIdCard());
        }
    }

    @Override
    public void loanApply(G2BReqMsg_ZsdLoanApply_AddLoan req) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_LOAN_ZSD.getKey() + req.getIdCard());
            //黑名单校验
            LnLoanBlackExample example = new LnLoanBlackExample();
            example.createCriteria().andMobileEqualTo(req.getMobile());
            List<LnLoanBlack> blackList = lnLoanBlackMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(blackList)){
                throw new PTMessageException(PTMessageEnum.YUN_SELF_LOAN_USER_FOUND_IN_BLACK);
            }
            // 借款订单号和借款编号重复申请校验
            Integer count = lnLoanMapper.selectByOrderNoAndPartnerCode(req.getOrderNo(), PartnerEnum.ZSD.getCode());
            if (count > 0) {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR.getCode(), "赞时贷订单号重复");
            }
            List<LnLoan> lnLoans = lnLoanMapper.selectByLoadIdAndPartnerCode(req.getLoanId(),PartnerEnum.ZSD.getCode());
            if(CollectionUtils.isNotEmpty(lnLoans)) {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR.getCode(), "赞时贷借款编号重复");
            }
            //判断bankCode是否存在
            if(!BaoFooEnum.bankCodeNameMap.containsKey(req.getBankCode())) {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR.getCode(), "银行编码没有找到");
            }

            // 开户
            OpenAccountReq openAccountReq = new OpenAccountReq();
            openAccountReq.setPartnerCode(PartnerEnum.ZSD.getCode());
            openAccountReq.setIdCard(req.getIdCard());
            openAccountReq.setBankCode(req.getBankCode());
            openAccountReq.setBankCard(req.getBankCard());
            openAccountReq.setAnnualIncome(req.getAnnualIncome());
            openAccountReq.setCardHolder(req.getCardHolder());
            openAccountReq.setMobile(req.getMobile());
            openAccountReq.setUserId(req.getUserId());
            openAccountReq.setUcUserType(Constants.UC_USER_TYPE_ZSD);
            Integer lnUserId = this.openAccount(openAccountReq);

            Double applyLoanAmount = MoneyUtil.divide(Double.valueOf(req.getLoanAmount()), 100).doubleValue();

            //统计借款人待还本金，加上此次申请本金，和最高借款本金比较，超过返回借款失败
            Double notRepayAmount = lnRepayScheduleMapper.sumNotRepayByLnUserId(lnUserId);
            Double maxLoanAmount = 20d;
			BsSysConfig config = sysConfigService.findConfigByKey(Constants.LOAN_MAX_SUM_AMOUNT);
			if(config != null){
				maxLoanAmount = MoneyUtil.multiply(Double.valueOf(config.getConfValue()), 10000).doubleValue();
			}
            if(maxLoanAmount.compareTo(MoneyUtil.add(notRepayAmount, applyLoanAmount).doubleValue()) < 0 ){
            	throw new PTMessageException(PTMessageEnum.LOAN_MAX_OUT);
            }

            LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnUserId);

            // 绑卡
            BindCardInfoReq cardInfoReq = new BindCardInfoReq();
            cardInfoReq.setBankCard(req.getBankCard());
            cardInfoReq.setBankCode(req.getBankCode());
            cardInfoReq.setIdCard(req.getIdCard());
            cardInfoReq.setLnUserId(lnUserId);
            cardInfoReq.setMobile(req.getMobile());
            cardInfoReq.setName(req.getCardHolder());
            cardInfoReq.setPartnerCode(PartnerEnum.ZSD.getCode());
            cardInfoReq.setUcUserType(Constants.UC_USER_TYPE_ZSD);
            bindCard(cardInfoReq);

            // 借款申请
            //记录借款信息表ln_loan,状态为审核通过
            LnLoan lnLoan = new LnLoan();
            lnLoan.setApplyAmount(applyLoanAmount);
            lnLoan.setApplyTime(StringUtils.isNotBlank(req.getLoanApplyTime()) ? com.pinting.core.util.DateUtil.parseDateTime(req.getLoanApplyTime()) : new Date() );
            lnLoan.setApproveAmount(applyLoanAmount);
            lnLoan.setBreakMaxDays(StringUtils.isNotBlank(req.getBreakMaxDays()) ? Integer.valueOf(req.getBreakMaxDays()) : null);
            lnLoan.setBreakTimes(StringUtils.isNotBlank(req.getBreakTimes()) ? Integer.valueOf(req.getBreakTimes()) : null);
            lnLoan.setHeadFee(MoneyUtil.divide(req.getHeadFee(), 100).doubleValue());
            lnLoan.setPartnerLoanId(req.getLoanId());
            lnLoan.setCreateTime(new Date());
            lnLoan.setUpdateTime(new Date());
            lnLoan.setCreditAmount(StringUtils.isNotBlank(req.getCreditAmount()) ? MoneyUtil.divide(Double.valueOf(req.getCreditAmount()), 100).doubleValue(): null);
            lnLoan.setLoanedAmount(applyLoanAmount);
            lnLoan.setCreditLevel(req.getCreditLevel());
            lnLoan.setCreditScore(StringUtils.isNotBlank(req.getCreditScore()) ? Integer.valueOf(req.getCreditScore()) : null);
            lnLoan.setLnUserId(lnUserId);
            lnLoan.setLoanTimes(StringUtils.isNotBlank(req.getLoanTimes()) ? Integer.valueOf(req.getLoanTimes()) : null);
            lnLoan.setPartnerBusinessFlag(req.getBusinessType());
            lnLoan.setPartnerOrderNo(req.getOrderNo());
            lnLoan.setPayOrderNo(Util.generateOrderNo4BaoFoo(8));
            lnLoan.setPeriod(Integer.valueOf(req.getLoanTerm()));
            lnLoan.setPurpose(req.getPurpose());
            lnLoan.setStatus(LoanStatus.LOAN_STATUS_CHECKED.getCode());
            lnLoan.setSubjectName(req.getSubjectName());
            lnLoan.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
            Double agreementRate = MoneyUtil.divide(req.getLoanRate(), 100, 2).doubleValue();
            lnLoan.setAgreementRate(agreementRate); // 借款协议利率
            lnLoanMapper.insertSelective(lnLoan);

            //根据还款日对还款计划列表排序
            List<RepaySchedule> repaySchedules = req.getRepaySchedule();
            log.info("借款编号：" + req.getLoanId() + "对应的>>>还款计划列表：" + repaySchedules + "<<<");
            for(RepaySchedule schedule : repaySchedules) {
                log.info("借款编号：{}，计划还款编号：{}，计划还款日：{}", req.getLoanId(), schedule.getRepayId(), com.pinting.core.util.DateUtil.format(schedule.getRepayDate()));
            }

            Collections.sort(repaySchedules, new Comparator<RepaySchedule>() {
                @Override
                public int compare(RepaySchedule o1, RepaySchedule o2) {
                    return o1.getRepayDate().compareTo(o2.getRepayDate());
                }
            });

            //新增一条ln_deposition_target的INIT记录
            LnDepositionTarget target = new LnDepositionTarget();
            target.setProdName(Constants.PARTNER_LOAN_ZSD + lnLoan.getId().toString());
            target.setProdType(Constants.TARGET_PRODUCT_CYCLE);
            target.setTotalLimit(applyLoanAmount);
            target.setSetupType(Constants.TARGET_PRODUCT_ESTABLISH_DAY);
            target.setSellDate(new Date());
            target.setInterestType(Constants.TARGET_PRODUCT_INTEREST_CHECK);
            target.setCycle(Integer.valueOf(req.getLoanTerm()));
            target.setCycleUnit(Constants.TARGET_PRODUCT_UNIT_DAY);
            target.setIstYear(lnLoan.getAgreementRate());
            target.setRepayType(Constants.TARGET_REPAY_TYPE_PERIOD);
            target.setLoanId(lnLoan.getId());
            target.setChargeOffAuto(Constants.TARGET_OUT_ACCOUNT_ACTIVE);
            target.setOverLimit(Constants.TARGET_OVER_LIMIT);
            target.setOverTotalLimit(applyLoanAmount);
            target.setStatus(Constants.DEP_TARGET_INIT);
            target.setCreateTime(new Date());
            target.setUpdateTime(new Date());
            lnDepositionTargetMapper.insertSelective(target);
            String publishOrderNo = Util.generateOrderNo4BaoFoo(8);
            B2GReqMsg_HFBank_Publish publish = new B2GReqMsg_HFBank_Publish();
            log.info("标的发布的订单号=["+publishOrderNo+"]产品Id=["+target.getId().toString()+"]");
            publish.setProd_id(target.getId().toString());
            publish.setProd_name(target.getProdName());
            publish.setProd_type(target.getProdType());
            publish.setTotal_limit(applyLoanAmount);
            publish.setValue_type(Constants.TARGET_PRODUCT_INTEREST_CHECK);
            publish.setCreate_type(Constants.TARGET_PRODUCT_ESTABLISH_DAY);
            publish.setSell_date(DateUtil.getDate(new Date()));
            publish.setCycle(target.getCycle());
            publish.setCycle_unit(target.getCycleUnit());
            publish.setIst_year(target.getIstYear());
            publish.setRepay_type(target.getRepayType());
            publish.setChargeOff_auto(target.getChargeOffAuto());
            publish.setOverLimit(target.getOverLimit());
            publish.setOver_total_limit(target.getOverTotalLimit());
            publish.setPartner_trans_date(new Date());
            publish.setPartner_trans_time(new Date());
            publish.setOrder_no(publishOrderNo);
            //融资信息列表赋值
            List<PublishFinancingInfo> infoList = new ArrayList<PublishFinancingInfo>();
            PublishFinancingInfo info = new PublishFinancingInfo();
            LnUser newUser = lnUserMapper.selectByPrimaryKey(lnUserId);
            info.setCust_no(newUser.getHfUserId());
            info.setReg_date(new Date());
            info.setReg_time(new Date());
            //此处是否存在精度问题
            Double financInterest = MoneyUtil.divide(MoneyUtil.multiply(Integer.valueOf(req.getLoanTerm()),
                    MoneyUtil.multiply(target.getIstYear(),target.getTotalLimit()).doubleValue()
            ).doubleValue(),36500).doubleValue();
            Double financInterestRate = MoneyUtil.divide(financInterest,target.getTotalLimit()).doubleValue();
            info.setFinanc_int(String.valueOf(financInterestRate));
            info.setFee_int(0d);//恒丰增加校验-暂定0.00
            info.setOpen_branch(req.getBankCode());
            info.setWithdraw_account(req.getBankCard());
            info.setAccount_type(Constants.HF_LOAN_CARD_TYPE_PERSONAL);
            info.setPayee_name(req.getCardHolder());
            info.setFinanc_amt(target.getTotalLimit());
            infoList.add(info);
            publish.setFinancing_info_list(infoList);

            List<CompensationInfo> compensationInfos = new ArrayList<>();
            BsSysConfig bsSysConfig = sysConfigService.findConfigByKey(Constants.ZSD_COMPENSATE_USER_ID);
            if(bsSysConfig != null) {
                String userString[] = bsSysConfig.getConfValue().split(",");
                for (String comUserId : userString) {
                    UcUserExtExample extExample = new UcUserExtExample();
                    extExample.createCriteria().andUserIdEqualTo(Integer.parseInt(comUserId));
                    List<UcUserExt> userExtList = ucUserExtMapper.selectByExample(extExample);
                    if(CollectionUtils.isEmpty(userExtList)) {
                        specialJnlService.warn4FailNoSMS(lnLoan.getApproveAmount(),"赞时贷自主借款代偿人无恒丰开户信息", lnLoan.getPayOrderNo(), "【赞时贷自主借款借款申请异常】");
                        throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND.getCode(), "赞时贷代偿人无恒丰开户信息");
                    } else {
                        UcUser ucUser = ucUserMapper.selectByPrimaryKey(userExtList.get(0).getUcUserId());
                        CompensationInfo compensationInfo = new CompensationInfo();
                        compensationInfo.setPlatcust(ucUser.getHfUserId());
                        compensationInfo.setType(Constants.HF_COMPENSATE_REPAY_TYPE_COMPENSATE);
                        compensationInfos.add(compensationInfo);
                    }
                }
                publish.setCompensation_info_list(compensationInfos);
            }

            //标的发布请求
            B2GResMsg_HFBank_Publish res = null;
            try {
                res = hfbankTransportService.publish(publish);
            } catch (Exception e){
                log.error("赞时贷标的发布请求异常：{}", e);
                res = new B2GResMsg_HFBank_Publish();
                res.setResCode(ConstantUtil.BSRESCODE_FAIL);
                res.setResMsg("通讯失败，置为失败");
            }

            //标的发布请求，成功时入redis
            if(res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {
                //记录还款计划表ln_repay_schedule
                for (RepaySchedule repaySchedule : repaySchedules) {
                    LnRepaySchedule lnRepaySchedule = new LnRepaySchedule();
                    lnRepaySchedule.setUpdateTime(new Date());
                    lnRepaySchedule.setCreateTime(new Date());
                    lnRepaySchedule.setLoanId(lnLoan.getId());
                    lnRepaySchedule.setPartnerRepayId(repaySchedule.getRepayId());
                    lnRepaySchedule.setPlanDate(repaySchedule.getRepayDate());
                    lnRepaySchedule.setPlanTotal(MoneyUtil.divide(repaySchedule.getTotal(), 100).doubleValue());
                    lnRepaySchedule.setStatus(LoanStatus.REPAY_SCHEDULE_STATUS_INIT.getCode());
                    lnRepaySchedule.setSerialId(repaySchedules.indexOf(repaySchedule) + 1);
                    lnRepayScheduleMapper.insertSelective(lnRepaySchedule);

                    //记录还款计划明细表ln_repay_schedule_detail
                    LnRepayScheduleDetail lnRepayScheduleDetail = new LnRepayScheduleDetail();
                    lnRepayScheduleDetail.setUpdateTime(new Date());
                    lnRepayScheduleDetail.setCreateTime(new Date());
                    lnRepayScheduleDetail.setPlanId(lnRepaySchedule.getId());
                    lnRepayScheduleDetail.setPlanAmount(repaySchedule.getPrincipal() != null ? MoneyUtil.divide(repaySchedule.getPrincipal(), 100).doubleValue() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
                    //利息
                    lnRepayScheduleDetail.setPlanAmount(repaySchedule.getInterest() != null ? MoneyUtil.divide(repaySchedule.getInterest(), 100).doubleValue() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
                    //平台服务费 platformServiceFee
                    lnRepayScheduleDetail.setPlanAmount(repaySchedule.getPlatformServiceFee() != null ? MoneyUtil.divide(repaySchedule.getPlatformServiceFee(), 100).doubleValue() : 0);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PLATFORM_SERVICE_FEE.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
                    //信息认证费 infoCertifiedFee
                    lnRepayScheduleDetail.setPlanAmount(repaySchedule.getInfoCertifiedFee() != null ? MoneyUtil.divide(repaySchedule.getInfoCertifiedFee(), 100).doubleValue() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INFO_CERTIFIED_FEE.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
                    //风控服务费 riskManageServiceFee
                    lnRepayScheduleDetail.setPlanAmount(repaySchedule.getRiskManageServiceFee() != null ? MoneyUtil.divide(repaySchedule.getRiskManageServiceFee(), 100).doubleValue() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_RISK_MANAGE_SERVICE_FEE.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
                    //代收通道费 collectionChannelFee
                    lnRepayScheduleDetail.setPlanAmount(repaySchedule.getCollectionChannelFee() != null ? MoneyUtil.divide(repaySchedule.getCollectionChannelFee(), 100).doubleValue() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_COLLECTION_CHANNEL_FEE.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
                    //其他
                    lnRepayScheduleDetail.setPlanAmount(repaySchedule.getOther() != null ? MoneyUtil.divide(repaySchedule.getOther(), 100).doubleValue() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_OTHER.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
                    //优惠金额
                    lnRepayScheduleDetail.setPlanAmount(repaySchedule.getPromote() != null ? MoneyUtil.divide(repaySchedule.getPromote(), 100).doubleValue() : 0d);
                    lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PROMOTE.getCode());
                    lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
                }

                //修改ln_deposition_target为PUBLISH，新增一条ln_deposition_target_jnl的成功记录
                depCommonService.updateTargetStatus(true,Constants.DEP_TARGET_PUBLISH, DepTargetEnum.DEP_TARGET_OPERATE_PUBLISH,target,lnLoan,publishOrderNo);
                try {
                    LoanQueueDTO loanQueueDTO=new LoanQueueDTO();
                    loanQueueDTO.setChannel(PartnerEnum.ZSD.getCode());
                    loanQueueDTO.setLnLoan(lnLoan);
                    //再查一遍绑卡记录，因为有修改的可能
                    LnBindCard newCard = loanUserService.queryIncrBindCardExist(req.getUserId(), PartnerEnum.ZSD.getCode(), req.getBankCard());
                    loanQueueDTO.setLnBindCard(newCard);
                    jsClientDaoSupport.rpush("loan_queue", JSON.toJSONString(loanQueueDTO));
                    log.info(">>>赞时贷入借款队列数据:" + JSON.toJSONString(loanQueueDTO) + "<<<");
                } catch (Exception e) {
                    log.error("赞时贷借款申请放入队列异常:{}",e);
                }
            } else {
                //新增一条ln_deposition_target_jnl的失败记录
                depCommonService.updateTargetStatus(false,Constants.DEP_TARGET_PUBLISH,DepTargetEnum.DEP_TARGET_OPERATE_PUBLISH,target,lnLoan,publishOrderNo);
                LnLoan updateLoan = new LnLoan();
                updateLoan.setId(lnLoan.getId());
                updateLoan.setUpdateTime(new Date());
                updateLoan.setStatus(LoanStatus.LOAN_STATUS_FAIL.getCode());
                lnLoanMapper.updateByPrimaryKeySelective(updateLoan);
                LnLoan newLoan = lnLoanMapper.selectByPrimaryKey(lnLoan.getId());

                // 发布失败通知给赞时贷
                noticeLoan2Dsd(newLoan,"标的发布请求失败");
                specialJnlService.warn4FailNoSMS(lnLoan.getApproveAmount(), "标的发布请求失败", lnLoan.getPayOrderNo(), "【赞时贷自主借款标的发布】");
                throw new PTMessageException(PTMessageEnum.DEP_TARGET_PUBLISH_ERROR, StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg());
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_LOAN_ZSD.getKey() + req.getIdCard());
        }
    }

    @Override
    public Integer openAccount(OpenAccountReq req) {
        PartnerEnum partnerEnum = PartnerEnum.getEnumByCode(req.getPartnerCode());
        LnUser lnUser = loanUserService.queryLoanUserExist(req.getUserId(), req.getPartnerCode());
        Integer lnUserId;
        if(lnUser != null) {
            //更新借款用户信息，如工作单位，学历等
            LnUser user = new LnUser();
            user.setId(lnUser.getId());
            user.setMobile(req.getMobile());
            user.setUpdateTime(new Date());
            user.setWorkUnit(req.getWorkUnit());
            user.setEducation(req.getEducation());
            user.setMarriage(req.getMarriage());
            user.setAnnualIncome(req.getAnnualIncome() == null ? null : Double.valueOf(req.getAnnualIncome()).intValue());
            user.setUpdateTime(new Date());
            lnUserMapper.updateByPrimaryKeySelective(user);
            lnUserId = lnUser.getId();
        } else {
            //新增借款用户信息
            LnUser user = new LnUser();
            user.setPartnerCode(partnerEnum.getCode());
            user.setPartnerUserId(req.getUserId());
            user.setUserName(req.getCardHolder());
            user.setIdCard(req.getIdCard());
            user.setMobile(req.getMobile());
            user.setWorkUnit(req.getWorkUnit());
            user.setEducation(req.getEducation());
            user.setMarriage(req.getMarriage());
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setAnnualIncome(req.getAnnualIncome() == null ? null : Double.valueOf(req.getAnnualIncome()).intValue());
            lnUserMapper.insertSelective(user);
            lnUserId = user.getId();
            lnUser = user;

            //如果不存在则新增用户中心 uc_user记录
            UcUserExample ucUserExist = new UcUserExample();
            ucUserExist.createCriteria().andIdCardEqualTo(req.getIdCard()).andStatusEqualTo(Constants.UC_USER_OPEN);
            List<UcUser> ucUsers = ucUserMapper.selectByExample(ucUserExist);
            UcUser ucUser = new UcUser();
            if(CollectionUtils.isEmpty(ucUsers)) {
                ucUser.setCreateTime(new Date());
                ucUser.setUpdateTime(new Date());
                ucUser.setStatus(Constants.UC_USER_OPEN);
                ucUser.setUserName(req.getCardHolder());
                ucUser.setIdCard(req.getIdCard());
                ucUserMapper.insertSelective(ucUser);
            } else {
                ucUser = ucUsers.get(0);
            }
            //新增用户中心 uc_user_ext记录
            UcUserExtExample extExample = new UcUserExtExample();
            extExample.createCriteria().andUcUserIdEqualTo(ucUser.getId())
                    .andUserIdEqualTo(lnUserId).andUserTypeEqualTo(req.getUcUserType());
            List<UcUserExt> ucUserExtList = ucUserExtMapper.selectByExample(extExample);
            if(CollectionUtils.isEmpty(ucUserExtList)) {
                UcUserExt ucUserExt = new UcUserExt();
                ucUserExt.setUcUserId(ucUser.getId());
                ucUserExt.setCreateTime(new Date());
                ucUserExt.setUserType(req.getUcUserType());
                ucUserExt.setUserId(lnUserId);
                ucUserExtMapper.insertSelective(ucUserExt);
            }
        }
        //新增账户信息
        LnAccountExample example = new LnAccountExample();
        example.createCriteria().andLnUserIdEqualTo(lnUserId);
        List<LnAccount> lnAccountList = lnAccountMapper.selectByExample(example);
        LnAccount lnAccount = null;
        if(CollectionUtils.isEmpty(lnAccountList)) {
            lnAccount = new LnAccount();
            lnAccount.setLnUserId(lnUserId);
            lnAccount.setCreateTime(new Date());
            lnAccount.setUpdateTime(new Date());
            lnAccount.setOpenTime(new Date());
            lnAccount.setStatus(Constants.LN_ACCOUNT_STATUS_NORMAL);
            lnAccountMapper.insertSelective(lnAccount);
        } else {
            lnAccount = lnAccountList.get(0);
        }

        LnSubAccountExample exampleSub = new LnSubAccountExample();
        exampleSub.createCriteria().andLnUserIdEqualTo(lnUserId).andAccountTypeEqualTo(Constants.LOAN_ACT_TYPE_DEP_JSH);
        List<LnSubAccount> lnSubAccounts = lnSubAccountMapper.selectByExample(exampleSub);
        if(CollectionUtils.isEmpty(lnSubAccounts)){
            //借款人余额子账户开户
            LnSubAccount loanAct = new LnSubAccount();
            loanAct.setLnUserId(lnUserId);
            loanAct.setAccountId(lnAccount.getId());
            loanAct.setAccountType(Constants.LOAN_ACT_TYPE_DEP_JSH);
            loanAct.setOpenBalance(0d);
            loanAct.setBalance(0d);
            loanAct.setAvailableBalance(0d);
            loanAct.setCanWithdraw(0d);
            loanAct.setFreezeBalance(0d);
            loanAct.setStatus(Constants.LOAN_SUBACCOUNT_STATUS_OPEN);
            loanAct.setAccumulationInerest(0d);
            loanAct.setOpenTime(new Date());
            loanAct.setCreateTime(new Date());
            loanAct.setUpdateTime(new Date());
            lnSubAccountMapper.insertSelective(loanAct);
            //借款人开户记账
            LnAccountJnl accountJnl = new LnAccountJnl();
            accountJnl.setTransTime(new Date());
            accountJnl.setTransCode(Constants.LN_DEP_JSH_OPEN);
            accountJnl.setTransName(partnerEnum.getName() + "借款用户余额子账户开户");
            accountJnl.setTransAmount(0.0);
            accountJnl.setSysTime(new Date());
            accountJnl.setChannelTime(null);
            accountJnl.setChannelJnlNo(null);
            accountJnl.setCdFlag2(Constants.CD_FLAG_D);
            accountJnl.setUserId2(lnUserId);
            accountJnl.setSubAccountId1(loanAct.getId());
            accountJnl.setSubAccountId2(loanAct.getId());
            accountJnl.setBeforeBalance2(0d);
            accountJnl.setAfterBalance2(0d);
            accountJnl.setBeforeAvialableBalance2(0d);
            accountJnl.setAfterAvialableBalance2(0d);
            accountJnl.setBeforeFreezeBalance2(0d);
            accountJnl.setAfterFreezeBalance2(0d);
            accountJnl.setFee(0.0);
            lnAccountJnlMapper.insertSelective(accountJnl);
        }
        return lnUserId;
    }

    @Override
    public void bindCard(BindCardInfoReq req) {
        PartnerEnum partnerEnum = PartnerEnum.getEnumByCode(req.getPartnerCode());
        log.info(partnerEnum.getName() + "用户 {} 绑卡开始", req.getLnUserId());
        Integer lnUserId = req.getLnUserId();
        UcUserExtExample ucUserExtExample = new UcUserExtExample();
        ucUserExtExample.createCriteria().andUserIdEqualTo(lnUserId).andUserTypeEqualTo(req.getUcUserType());
        List<UcUserExt> extList = ucUserExtMapper.selectByExample(ucUserExtExample);
        if(CollectionUtils.isEmpty(extList)) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND.getCode(), "用户中心信息不存在");
        }
        UcUser ucUser = ucUserMapper.selectByPrimaryKey(extList.get(0).getUcUserId());

        Bs19payBankExample bs19payBankExample = new Bs19payBankExample();
        bs19payBankExample.createCriteria().andPay19BankCodeEqualTo(req.getBankCode());
        List<Bs19payBank> bs19payBanks = bs19payBankMapper.selectByExample(bs19payBankExample);

        // 1 uc_user.hf_user_id 存在记录，证明已经开户
        if(StringUtil.isNotEmpty(ucUser.getHfUserId())) {
            UcBankCard ucCard = new UcBankCard();
            UcBankCardExample bankExample = new UcBankCardExample();
            bankExample.createCriteria().andUcUserIdEqualTo(ucUser.getId()).andCardNoEqualTo(req.getBankCard())
                    .andStatusEqualTo(Constants.UC_BIND_CARD_STATUS_BINDED);
            bankExample.setOrderByClause("id desc");
            List<UcBankCard> ucBankCards = ucBankCardMapper.selectByExample(bankExample);

            // uc_bank_card 不存在同卡记录
            if(CollectionUtils.isEmpty(ucBankCards)) {
                // 插入记录 uc_bank_card
                UcBankCard ucBankCard = new UcBankCard();
                ucBankCard.setBankId(bs19payBanks.get(0).getBankId());
                ucBankCard.setBankName(BaoFooEnum.bankCodeNameMap.get(req.getBankCode()));
                ucBankCard.setBindTime(new Date());
                ucBankCard.setCardNo(req.getBankCard());
                ucBankCard.setCardOwner(req.getName());
                ucBankCard.setCreateTime(new Date());
                ucBankCard.setIdCard(req.getIdCard());
                ucBankCard.setIsBind(Constants.UC_BIND_CARD_NO);
                ucBankCard.setStatus(Constants.UC_BIND_CARD_STATUS_BINDED);
                ucBankCard.setUcUserId(ucUser.getId());
                ucBankCard.setUpdateTime(new Date());
                ucBankCard.setMobile(req.getMobile());
                ucBankCardMapper.insertSelective(ucBankCard);
                ucCard = ucBankCard;
            } else {
                ucCard = ucBankCards.get(0);
            }

            LnBindCardExample yunExample = new LnBindCardExample();
            yunExample.createCriteria().andLnUserIdEqualTo(lnUserId).andStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode()).andPartnerCodeEqualTo(req.getPartnerCode());
            yunExample.setOrderByClause("update_time desc");
            List<LnBindCard> yunList = lnBindCardMapper.selectByExample(yunExample);

            UcUserExtExample otherExtExample = new UcUserExtExample();
            otherExtExample.createCriteria().andUcUserIdEqualTo(ucUser.getId()).andUserTypeNotEqualTo(req.getUcUserType());
            List<UcUserExt> otherExtList = ucUserExtMapper.selectByExample(otherExtExample);

            String payBindOrderNo = Util.generateOrderNo4BaoFoo(8);
            if(CollectionUtils.isEmpty(otherExtList)) {
                // 其他端（币港湾、赞分期）无注册用户，先对恒丰绑卡记录进行解绑再对申请卡进行绑卡
                UcBankCardExample notApplyBankExample = new UcBankCardExample();
                notApplyBankExample.createCriteria().andUcUserIdEqualTo(ucUser.getId()).andCardNoNotEqualTo(req.getBankCard())
                        .andStatusEqualTo(Constants.UC_BIND_CARD_STATUS_BINDED).andIsBindEqualTo(Constants.UC_BIND_CARD_YES);
                List<UcBankCard> notApplyUcBankCards = ucBankCardMapper.selectByExample(notApplyBankExample);
                if (CollectionUtils.isNotEmpty(notApplyUcBankCards)) {
                    this.unBind(ucUser.getHfUserId(), ucUser.getId(), lnUserId, req);
                }
                if(CollectionUtils.isNotEmpty(yunList)) {
                    if(!yunList.get(0).getBankCard().equals(req.getBankCard())) {
                        payBindOrderNo = this.bind(ucUser.getHfUserId(), ucUser.getId(), lnUserId, req);
                        ucCard.setIsBind(Constants.UC_BIND_CARD_YES);
                    }
                }
            }

            boolean needInsert = false;
            if(CollectionUtils.isEmpty(yunList)) {
                needInsert = true;
            } else if(CollectionUtils.isNotEmpty(yunList) && !yunList.get(0).getBankCard().equals(req.getBankCard())) {
                needInsert = true;
                // ln_bind_card存在非最新记录的当前卡信息，更新旧的ln_bind_card
                LnBindCardExample yunCardOpenExample = new LnBindCardExample();
                yunCardOpenExample.createCriteria().andLnUserIdEqualTo(req.getLnUserId())
                        .andPartnerCodeEqualTo(partnerEnum.getCode()).andStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode())
                        .andBankCardEqualTo(req.getBankCard()).andIdCardEqualTo(req.getIdCard());
                LnBindCard yunCard = new LnBindCard();
                yunCard.setStatus(BaoFooEnum.UNBIND_SUCCESS.getCode());
                yunCard.setUpdateTime(new Date());
                lnBindCardMapper.updateByExampleSelective(yunCard, yunCardOpenExample);
            }

            if(needInsert) {
                // 复制绑卡信息（uc_bank_card - > ln_bind_card）
                LnBindCard newCard = new LnBindCard();
                newCard.setIsFirst(Constants.UC_BIND_CARD_NO.equals(ucCard.getIsBind()) ? Constants.IS_FIRST_BANK_NO: Constants.IS_FIRST_BANK_YES);
                newCard.setStatus(BaoFooEnum.BIND_SUCCESS.getCode());
                newCard.setCreateTime(new Date());
                newCard.setUpdateTime(com.pinting.core.util.DateUtil.addSeconds(new Date(), 1));
                newCard.setPartnerCode(partnerEnum.getCode());
                newCard.setLnUserId(lnUserId);
                newCard.setUserName(ucCard.getCardOwner());
                newCard.setMobile(req.getMobile());
                newCard.setIdCard(ucCard.getIdCard());
                newCard.setBankCard(ucCard.getCardNo());
                newCard.setBankCode(req.getBankCode());
                newCard.setBankName(ucCard.getBankName());
                newCard.setPayBindOrderNo(payBindOrderNo);
                lnBindCardMapper.insertSelective(newCard);
            }

            LnUser lnUser = new LnUser();
            lnUser.setId(lnUserId);
            if(StringUtil.isBlank(ucUser.getIdCard())) {
                // 更新uc_user用户信息（姓名，手机号，身份证）
                ucUser.setIdCard(req.getIdCard());
                ucUser.setMobile(req.getMobile());
                ucUser.setUserName(req.getName());

                // 更新ln_user用户信息（姓名，手机号，身份证）
                lnUser.setIdCard(req.getIdCard());
                lnUser.setMobile(req.getMobile());
                lnUser.setUserName(req.getName());
            }

            // 复制恒丰用户编号（uc_user - > ln_user.hf_user_id）
            lnUser.setHfUserId(ucUser.getHfUserId());
            lnUser.setUpdateTime(new Date());
            lnUserMapper.updateByPrimaryKeySelective(lnUser);
        } else {
            // uc_user.hf_user_id 不存在记录，调用（批量开户）四要素接口
            this.openAccountBind(partnerEnum.getCode(), ucUser.getId(), lnUserId, req);
        }
        log.info(partnerEnum.getName() + "用户 {} 开户结束", req.getLnUserId());
    }

    /**
     * 借款端调用恒丰四要素绑卡操作
     * @param partner
     * @param ucUserId
     * @param lnUserId
     * @param req
     */
    private void openAccountBind(String partner, Integer ucUserId, Integer lnUserId, BindCardInfoReq req) {
        B2GResMsg_HFBank_BatchBindCard4Elements res = new B2GResMsg_HFBank_BatchBindCard4Elements();

        Bs19payBankExample bs19payBankExample = new Bs19payBankExample();
        bs19payBankExample.createCriteria().andPay19BankCodeEqualTo(req.getBankCode());
        List<Bs19payBank> bs19payBanks = bs19payBankMapper.selectByExample(bs19payBankExample);

        // uc_user.hf_user_id 不存在记录，调用（批量开户）四要素接口
        String payBindOrderNo = Util.generateOrderNo4BaoFoo(8);
        //记录ln_pay_orders
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(1);
        lnPayOrders.setBankCardNo(req.getBankCard());
        lnPayOrders.setBankId(bs19payBanks.get(0).getBankId());
        lnPayOrders.setBankName(BaoFooEnum.bankCodeNameMap.get(req.getBankCode()));
        lnPayOrders.setAmount(0d);
        lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
        lnPayOrders.setLnUserId(lnUserId);
        lnPayOrders.setMobile(req.getMobile());
        lnPayOrders.setIdCard(req.getIdCard());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(payBindOrderNo);
        lnPayOrders.setPartnerCode(partner);
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_BIND_CARD.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrders.setUserName(req.getName());
        lnPayOrdersMapper.insertSelective(lnPayOrders);

        //记录ln_pay_orders_jnl表
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnl.setTransAmount(0d);
        payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

        log.info(PartnerEnum.getEnumByCode(partner).getName() + "用户 {} 未开户，调用恒丰开户四要素绑卡接口。", lnUserId);
        B2GReqMsg_HFBank_BatchBindCard4Elements reqBankCard = new B2GReqMsg_HFBank_BatchBindCard4Elements();
        List<BatchRegistExtDetail> details = new ArrayList<>();
        BatchRegistExtDetail detail = new BatchRegistExtDetail();
        reqBankCard.setOrder_no(Util.generateOrderNo4BaoFoo(8));
        detail.setDetail_no(payBindOrderNo);
        detail.setName(req.getName());
        detail.setId_type(Constants.HF_ID_TYPE_ID_CARD);
        detail.setId_code(req.getIdCard());
        detail.setMobile(req.getMobile());
        detail.setEmail(null);
        detail.setSex(null);
        detail.setCus_type(null);
        detail.setBirthday(null);
        detail.setOpen_branch(null);
        detail.setCard_no(req.getBankCard());
        detail.setCard_type(Constants.HF_CARD_TYPE_DEBIT);
        detail.setPre_mobile(req.getMobile());
        detail.setPay_code(Constants.HF_PAY_CODE_BAOFOO);
        detail.setNotify_url(null);
        detail.setRemark("批量开户(四要素绑卡)");
        details.add(detail);
        reqBankCard.setData(details);
        reqBankCard.setTotal_num(details.size());
        reqBankCard.setPartner_trans_date(new Date());
        reqBankCard.setPartner_trans_time(new Date());
        log.info("批量绑卡请求：{}", JSONObject.fromObject(req));
        boolean communication = true;
        try {
            res = hfbankTransportService.batchBindCard4Elements(reqBankCard);
        } catch (Exception e) {
            communication = false;
            log.error("批量开户(四要素绑卡)请求异常：{}，卡号：{}", e.getMessage(), lnPayOrders.getBankCardNo(), e);
        }

        if(!communication) {
            LnPayOrders payOrderTemp = new LnPayOrders();
            payOrderTemp.setId(lnPayOrders.getId());
            payOrderTemp.setUpdateTime(new Date());
            payOrderTemp.setReturnMsg("通讯异常");
            payOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
            lnPayOrdersMapper.updateByPrimaryKeySelective(payOrderTemp);

            //记录ln_pay_orders_jnl表
            LnPayOrdersJnl lnPayOrdersJnl2 = new LnPayOrdersJnl();
            lnPayOrdersJnl2.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
            lnPayOrdersJnl2.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
            lnPayOrdersJnl2.setCreateTime(new Date());
            lnPayOrdersJnl2.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl2.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl2.setUserId(lnPayOrders.getLnUserId());
            lnPayOrdersJnl2.setSysTime(new Date());
            lnPayOrdersJnl2.setReturnMsg("通讯异常");
            //lnPayOrdersJnl2.setNote("");
            lnPayOrdersJnl2.setTransAmount(0d);
            payOrdersJnlMapper.insertSelective(lnPayOrdersJnl2);

            specialJnlService.warn4FailNoSMS(lnPayOrders.getAmount(), PartnerEnum.getEnumByCode(partner).getName() + "借款用户"+lnUserId+"调用恒丰(四要素)绑卡失败通讯异常", null, "【" + PartnerEnum.getEnumByCode(partner).getName() + "恒丰(四要素)绑卡通讯异常】");
            throw new PTMessageException(PTMessageEnum.FOUR_ELEMENTS_AUTH_ERROR.getCode(), PartnerEnum.getEnumByCode(partner).getName() + "存管开户失败");
        }

        //4要素开户成功
        if(res != null && !org.springframework.util.CollectionUtils.isEmpty(res.getSuccess_data())
                && StringUtil.isNotEmpty(res.getSuccess_data().get(0).getPlatcust())) {
            String hfUserId = res.getSuccess_data().get(0).getPlatcust();
            //更新ln_user
            LnUser updateLnUser = new LnUser();
            updateLnUser.setId(lnUserId);
            updateLnUser.setUpdateTime(new Date());
            updateLnUser.setHfUserId(hfUserId);
            updateLnUser.setUserName(req.getName());
            updateLnUser.setIdCard(req.getIdCard());
            lnUserMapper.updateByPrimaryKeySelective(updateLnUser);
            //更新uc_user
            UcUser ucUserUp = new UcUser();
            ucUserUp.setId(ucUserId);
            ucUserUp.setHfUserId(hfUserId);
            ucUserUp.setIdCard(req.getIdCard());
            ucUserUp.setUserName(req.getName());
            ucUserUp.setUpdateTime(new Date());
            ucUserMapper.updateByPrimaryKeySelective(ucUserUp);
            //新增uc_bank_card
            UcBankCard ucBankCard = new UcBankCard();
            ucBankCard.setUcUserId(ucUserId);
            ucBankCard.setCardNo(req.getBankCard());
            ucBankCard.setIsBind(Constants.UC_BIND_CARD_YES);
            ucBankCard.setStatus(Constants.UC_BIND_CARD_STATUS_BINDED);
            ucBankCard.setUpdateTime(new Date());
            ucBankCard.setBindTime(new Date());
            ucBankCard.setCreateTime(new Date());
            ucBankCard.setBankId(bs19payBanks.get(0).getBankId());
            ucBankCard.setBankName(BaoFooEnum.bankCodeNameMap.get(req.getBankCode()));
            ucBankCard.setCardOwner(req.getName());
            ucBankCard.setIdCard(req.getIdCard());
            ucBankCard.setMobile(req.getMobile());
            ucBankCardMapper.insertSelective(ucBankCard);

            // 新增ln_bind_card
            LnBindCard newCard = new LnBindCard();
            newCard.setIsFirst(Constants.IS_FIRST_BANK_YES);
            newCard.setPartnerCode(partner);
            newCard.setLnUserId(lnUserId);
            newCard.setUserName(req.getName());
            newCard.setMobile(req.getMobile());
            newCard.setIdCard(req.getIdCard());
            newCard.setBankCard(req.getBankCard());
            newCard.setBankCode(req.getBankCode());
            newCard.setBankName(BaoFooEnum.bankCodeNameMap.get(req.getBankCode()));
            newCard.setStatus(BaoFooEnum.BIND_SUCCESS.getCode());
            newCard.setPayBindOrderNo(payBindOrderNo);
            newCard.setCreateTime(new Date());
            newCard.setUpdateTime(new Date());
            lnBindCardMapper.insertSelective(newCard);

            LnPayOrders payOrderTemp = new LnPayOrders();
            payOrderTemp.setId(lnPayOrders.getId());
            payOrderTemp.setUpdateTime(new Date());
            payOrderTemp.setReturnMsg("绑卡成功");
            payOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
            lnPayOrdersMapper.updateByPrimaryKeySelective(payOrderTemp);

            //记录ln_pay_orders_jnl表
            LnPayOrdersJnl lnPayOrdersJnl2 = new LnPayOrdersJnl();
            lnPayOrdersJnl2.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
            lnPayOrdersJnl2.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
            lnPayOrdersJnl2.setCreateTime(new Date());
            lnPayOrdersJnl2.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl2.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl2.setUserId(lnPayOrders.getLnUserId());
            lnPayOrdersJnl2.setSysTime(new Date());
            lnPayOrdersJnl2.setReturnMsg("绑卡成功");
            //lnPayOrdersJnl2.setNote("");
            lnPayOrdersJnl2.setTransAmount(0d);
            payOrdersJnlMapper.insertSelective(lnPayOrdersJnl2);
        } else {
            // 四要素开户失败
            LnPayOrders payOrderTemp = new LnPayOrders();
            payOrderTemp.setId(lnPayOrders.getId());
            payOrderTemp.setUpdateTime(new Date());
            payOrderTemp.setReturnMsg(res.getResMsg());
            payOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
            lnPayOrdersMapper.updateByPrimaryKeySelective(payOrderTemp);

            //记录ln_pay_orders_jnl表
            LnPayOrdersJnl lnPayOrdersJnl2 = new LnPayOrdersJnl();
            lnPayOrdersJnl2.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
            lnPayOrdersJnl2.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
            lnPayOrdersJnl2.setCreateTime(new Date());
            lnPayOrdersJnl2.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl2.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl2.setUserId(lnPayOrders.getLnUserId());
            lnPayOrdersJnl2.setSysTime(new Date());
            lnPayOrdersJnl2.setReturnMsg(res.getResMsg());
            //lnPayOrdersJnl2.setNote("");
            lnPayOrdersJnl2.setTransAmount(0d);
            payOrdersJnlMapper.insertSelective(lnPayOrdersJnl2);

            specialJnlService.warn4FailNoSMS(lnPayOrders.getAmount(), PartnerEnum.getEnumByCode(partner).getName() + "借款用户"+lnUserId+"调用恒丰(四要素)绑卡失败："+res.getResMsg(), null, "【" + PartnerEnum.getEnumByCode(partner).getName() + "恒丰(四要素)绑卡失败】");
            throw new PTMessageException(PTMessageEnum.FOUR_ELEMENTS_AUTH_ERROR, res.getResMsg());
        }
    }

    /**
     * 单客户端绑卡
     * @param hfUserId
     * @param ucUserId
     * @param lnUserId
     * @param req
     * @return
     */
    private String bind(String hfUserId, Integer ucUserId, Integer lnUserId, BindCardInfoReq req) {
        String payBindOrderNo = null;
        Bs19payBankExample bs19payBankExample = new Bs19payBankExample();
        bs19payBankExample.createCriteria().andPay19BankCodeEqualTo(req.getBankCode());
        List<Bs19payBank> bs19payBanks = bs19payBankMapper.selectByExample(bs19payBankExample);

        //记录ln_pay_orders
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(1);
        lnPayOrders.setBankCardNo(req.getBankCard());
        lnPayOrders.setBankId(bs19payBanks.get(0).getBankId());
        lnPayOrders.setBankName(BaoFooEnum.bankCodeNameMap.get(req.getBankCode()));
        lnPayOrders.setAmount(0d);
        lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
        lnPayOrders.setLnUserId(lnUserId);
        lnPayOrders.setMobile(req.getMobile());
        lnPayOrders.setIdCard(req.getIdCard());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(Util.generateOrderNo4BaoFoo(8));
        lnPayOrders.setPartnerCode(req.getPartnerCode());
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_BIND_CARD.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrders.setUserName(req.getName());
        lnPayOrdersMapper.insertSelective(lnPayOrders);

        // 3、插入订单流水表，状态为创建-1
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnl.setTransAmount(0d);
        payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

        B2GReqMsg_HFBank_UserAddCard addCardReq = new B2GReqMsg_HFBank_UserAddCard();
        addCardReq.setOrder_no(lnPayOrders.getOrderNo());
        addCardReq.setPartner_trans_date(new Date());
        addCardReq.setPartner_trans_time(new Date());
        addCardReq.setType(Constants.HF_BIND_CARD_TYPE_PERSON);//绑卡类型：1个人客户 2 对公客户
        addCardReq.setPlatcust(hfUserId);
        addCardReq.setId_type(Constants.HF_ID_TYPE_ID_CARD);	//1:身份证
        addCardReq.setId_code(lnPayOrders.getIdCard());
        addCardReq.setName(lnPayOrders.getUserName());
        addCardReq.setCard_no(lnPayOrders.getBankCardNo());
        addCardReq.setCard_type(Constants.HF_CARD_TYPE_DEBIT);	//1:借记卡,2:信用卡
        addCardReq.setPre_mobile(lnPayOrders.getMobile());
        addCardReq.setPay_code(Constants.HF_PAY_CODE_BAOFOO);
        addCardReq.setRemark("个人绑卡");
        log.info("绑卡请求：{}", JSONObject.fromObject(addCardReq));

        B2GResMsg_HFBank_BatchBindCard4Elements res = new B2GResMsg_HFBank_BatchBindCard4Elements();
        boolean communication = true;
        try {
            B2GResMsg_HFBank_UserAddCard addCardRes = hfbankTransportService.userAddCard(addCardReq);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(addCardRes.getResCode())) {
                List<BatchRegistExtSuccess> success_data = new ArrayList<>();
                BatchRegistExtSuccess success = new BatchRegistExtSuccess();
                success.setDetail_no(lnPayOrders.getOrderNo());
                success.setMobile(lnPayOrders.getMobile());
                success.setPlatcust(hfUserId);
                success_data.add(success);
                res.setSuccess_data(success_data);
                res.setSuccess_num("1");
                res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
                res.setResMsg("已开户用户，绑卡成功");
            } else {
                List<BatchRegistExtError> error_data = new ArrayList<>();
                BatchRegistExtError error = new BatchRegistExtError();
                error.setError_no(addCardRes.getResCode());
                error.setError_info(StringUtil.isBlank(addCardRes.getRemsg()) ? addCardRes.getResMsg() : addCardRes.getRemsg());
                error_data.add(error);
                res.setError_data(error_data);
                res.setResCode(error.getError_no());
                res.setResMsg(error.getError_info());
            }
        } catch (Exception e) {
            communication = false;
            log.info("恒丰正式绑卡操作失败 {}，订单号：{}", e.getMessage(), lnPayOrders.getOrderNo());
        }

        if(!communication) {
            // 二、调用正式绑卡接口失败后续操作
            // 1、更新订单表。状态为正式下单失败-7；
            LnPayOrders failOrder = new LnPayOrders();
            failOrder.setId(lnPayOrders.getId());
            failOrder.setUpdateTime(new Date());
            failOrder.setStatus(Constants.ORDER_STATUS_FAIL);
            failOrder.setReturnCode(Constants.ORDER_TRANS_CODE_FAIL);
            failOrder.setReturnMsg("正式绑卡失败：网络通信异常。");
            lnPayOrdersMapper.updateByPrimaryKeySelective(failOrder);
            // 2、新增订单流水表。
            LnPayOrdersJnl failJnl = new LnPayOrdersJnl();
            failJnl.setOrderId(lnPayOrders.getId());
            failJnl.setOrderNo(lnPayOrders.getOrderNo());
            failJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
            failJnl.setChannelTransType(Constants.CHANNEL_TRS_BIND_CARD);
            failJnl.setTransAmount(lnPayOrders.getAmount());
            failJnl.setSysTime(new Date());
            failJnl.setUserId(lnUserId);
            failJnl.setCreateTime(new Date());
            failJnl.setReturnCode(Constants.ORDER_TRANS_CODE_FAIL);
            failJnl.setReturnMsg("正式绑卡失败：网络通信异常。");
            payOrdersJnlMapper.insertSelective(failJnl);
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "绑卡失败：网络通信异常");
        }

        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode()) && "1".equals(res.getSuccess_num())
                && !org.springframework.util.CollectionUtils.isEmpty(res.getSuccess_data())) {
            log.info("恒丰绑卡操作成功，订单号：{}，{}", lnPayOrders.getOrderNo(), JSONObject.fromObject(res));

            // 1. 新增uc_bank_card
            UcBankCard ucBankCard = new UcBankCard();
            ucBankCard.setIsBind(Constants.UC_BIND_CARD_YES);
            ucBankCard.setStatus(Constants.UC_BIND_CARD_STATUS_BINDED);
            ucBankCard.setUpdateTime(new Date());
            ucBankCard.setBindTime(new Date());
            UcBankCardExample ucBankCardExample = new UcBankCardExample();
            ucBankCardExample.createCriteria().andCardNoEqualTo(req.getBankCard()).andStatusEqualTo(Constants.UC_BIND_CARD_STATUS_BINDED);
            ucBankCardMapper.updateByExampleSelective(ucBankCard, ucBankCardExample);

            // 2. 更新订单状态
            LnPayOrders payOrderTemp = new LnPayOrders();
            payOrderTemp.setId(lnPayOrders.getId());
            payOrderTemp.setUpdateTime(new Date());
            payOrderTemp.setReturnMsg("绑卡成功");
            payOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
            lnPayOrdersMapper.updateByPrimaryKeySelective(payOrderTemp);

            // 3. 记录ln_pay_orders_jnl表
            LnPayOrdersJnl lnPayOrdersJnl2 = new LnPayOrdersJnl();
            lnPayOrdersJnl2.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
            lnPayOrdersJnl2.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
            lnPayOrdersJnl2.setCreateTime(new Date());
            lnPayOrdersJnl2.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl2.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl2.setUserId(lnPayOrders.getLnUserId());
            lnPayOrdersJnl2.setSysTime(new Date());
            lnPayOrdersJnl2.setReturnMsg("绑卡成功");
            //lnPayOrdersJnl2.setNote("");
            lnPayOrdersJnl2.setTransAmount(0d);
            payOrdersJnlMapper.insertSelective(lnPayOrdersJnl2);


            payBindOrderNo = lnPayOrders.getOrderNo();
            return payBindOrderNo;
        } else {
            // 1. 更新uc_bank_card为失败
            UcBankCard ucBankCard = new UcBankCard();
            ucBankCard.setIsBind(Constants.UC_BIND_CARD_NO);
            ucBankCard.setStatus(Constants.UC_BIND_CARD_FAIL);
            ucBankCard.setUpdateTime(new Date());
            UcBankCardExample ucBankCardExample = new UcBankCardExample();
            ucBankCardExample.createCriteria().andCardNoEqualTo(req.getBankCard()).andStatusEqualTo(Constants.UC_BIND_CARD_STATUS_BINDED);
            ucBankCardMapper.updateByExampleSelective(ucBankCard, ucBankCardExample);

            // 1. 更新订单状态
            LnPayOrders payOrderTemp = new LnPayOrders();
            payOrderTemp.setId(lnPayOrders.getId());
            payOrderTemp.setUpdateTime(new Date());
            payOrderTemp.setReturnMsg("绑卡失败：" + res.getResMsg());
            payOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
            lnPayOrdersMapper.updateByPrimaryKeySelective(payOrderTemp);

            // 2. 记录ln_pay_orders_jnl表
            LnPayOrdersJnl lnPayOrdersJnl2 = new LnPayOrdersJnl();
            lnPayOrdersJnl2.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
            lnPayOrdersJnl2.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
            lnPayOrdersJnl2.setCreateTime(new Date());
            lnPayOrdersJnl2.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl2.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl2.setUserId(lnPayOrders.getLnUserId());
            lnPayOrdersJnl2.setSysTime(new Date());
            lnPayOrdersJnl2.setReturnMsg("绑卡失败：" + res.getResMsg());
            //lnPayOrdersJnl2.setNote("");
            lnPayOrdersJnl2.setTransAmount(0d);
            payOrdersJnlMapper.insertSelective(lnPayOrdersJnl2);

            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "绑卡失败：" + res.getResMsg());
        }
    }

    /**
     * 单客户端解绑
     * @param hfUserId
     * @param ucUserId
     * @param lnUserId
     * @param req
     */
    private void unBind(String hfUserId, Integer ucUserId, Integer lnUserId, BindCardInfoReq req) {
        LnBindCard card = new LnBindCard();

        LnBindCardExample cardExample = new LnBindCardExample();
        cardExample.createCriteria().andLnUserIdEqualTo(lnUserId).andIdCardEqualTo(req.getIdCard()).andBankCardNotEqualTo(req.getBankCard())
                .andStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode()).andIsFirstEqualTo(Constants.IS_FIRST_BANK_YES);
        List<LnBindCard> cardList = lnBindCardMapper.selectByExample(cardExample);
        if(CollectionUtils.isNotEmpty(cardList)) {
            card = cardList.get(0);
            Bs19payBankExample bs19payBankExample = new Bs19payBankExample();
            bs19payBankExample.createCriteria().andPay19BankCodeEqualTo(req.getBankCode());
            List<Bs19payBank> bs19payBanks = bs19payBankMapper.selectByExample(bs19payBankExample);

            // 调用解绑接口
            String payBindOrderNo = Util.generateOrderNo4BaoFoo(8);
            //记录ln_pay_orders
            LnPayOrders lnPayOrders = new LnPayOrders();
            lnPayOrders.setCreateTime(new Date());
            lnPayOrders.setAccountType(1);
            lnPayOrders.setBankCardNo(card.getBankCard());
            lnPayOrders.setBankId(bs19payBanks.get(0).getBankId());
            lnPayOrders.setBankName(BaoFooEnum.bankCodeNameMap.get(req.getBankCode()));
            lnPayOrders.setAmount(0d);
            lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
            lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_UN_BIND_CARD.getCode());
            lnPayOrders.setLnUserId(lnUserId);
            lnPayOrders.setMobile(card.getMobile());
            lnPayOrders.setIdCard(card.getIdCard());
            lnPayOrders.setMoneyType(0);
            lnPayOrders.setOrderNo(payBindOrderNo);
            lnPayOrders.setPartnerCode(req.getPartnerCode());
            lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
            lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_UN_BIND_CARD.getCode());
            lnPayOrders.setUpdateTime(new Date());
            lnPayOrders.setUserName(req.getName());
            lnPayOrdersMapper.insertSelective(lnPayOrders);

            //记录ln_pay_orders_jnl表
            LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
            lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
            lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_UN_BIND_CARD.getCode());
            lnPayOrdersJnl.setCreateTime(new Date());
            lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
            lnPayOrdersJnl.setSysTime(new Date());
            lnPayOrdersJnl.setTransAmount(0d);
            payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

            B2GReqMsg_HFBank_BatchChangeCard unbindReq = new B2GReqMsg_HFBank_BatchChangeCard();
            unbindReq.setPartner_trans_time(lnPayOrders.getCreateTime());
            unbindReq.setPartner_trans_date(lnPayOrders.getCreateTime());
            unbindReq.setOrder_no(Util.generateOrderNo4BaoFoo(8));
            unbindReq.setTotal_num(1);
            List<BatchUpdateCardExtDetail> data = new ArrayList<>();
            BatchUpdateCardExtDetail detail = new BatchUpdateCardExtDetail();
            detail.setPlatcust(hfUserId);
            detail.setCard_no(card.getBankCard());
            detail.setCard_no_old(card.getBankCard());
            detail.setDetail_no(lnPayOrders.getOrderNo());
            detail.setMobile(card.getMobile());
            detail.setName(card.getUserName());
            detail.setRemark("用户解绑卡");
            data.add(detail);
            unbindReq.setData(data);
            B2GResMsg_HFBank_BatchChangeCard res = hfbankTransportService.batchChangeCard(unbindReq);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                if(!CollectionUtils.isEmpty(res.getSuccess_data())) {
                    log.info("用户 {} 解绑卡成功：{}", lnUserId, JSONObject.fromObject(res));
                    lnPayOrders.setStatus(Constants.ORDER_STATUS_SUCCESS);
                    lnPayOrders.setUpdateTime(new Date());
                    lnPayOrders.setNote(detail.getDetail_no());
                    lnPayOrdersMapper.updateByPrimaryKeySelective(lnPayOrders);

                    //记录ln_pay_orders_jnl表
                    LnPayOrdersJnl succJnl = new LnPayOrdersJnl();
                    succJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
                    succJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_UN_BIND_CARD.getCode());
                    succJnl.setCreateTime(new Date());
                    succJnl.setOrderId(lnPayOrders.getId());
                    succJnl.setOrderNo(lnPayOrders.getOrderNo());
                    succJnl.setUserId(lnPayOrders.getLnUserId());
                    succJnl.setSysTime(new Date());
                    succJnl.setTransAmount(0d);
                    payOrdersJnlMapper.insertSelective(succJnl);

                    // ln_bind_card 解绑
                    // card.setStatus(BaoFooEnum.UNBIND_SUCCESS.getCode());
                    card.setIsFirst(Constants.IS_FIRST_BANK_NO);
                    card.setUpdateTime(new Date());
                    lnBindCardMapper.updateByPrimaryKeySelective(card);

                    // uc_bind_card is_bind = N
                    UcBankCard ucBankCard = new UcBankCard();
                    // ucBankCard.setStatus(Constants.UC_BIND_CARD_STATUS_UNBINDED);
                    ucBankCard.setIsBind(Constants.UC_BIND_CARD_NO);
                    ucBankCard.setUpdateTime(new Date());
                    ucBankCard.setUnbindTime(new Date());
                    UcBankCardExample bankExample = new UcBankCardExample();
                    bankExample.createCriteria().andUcUserIdEqualTo(ucUserId).andCardNoEqualTo(card.getBankCard()).andIdCardEqualTo(card.getIdCard());
                    ucBankCardMapper.updateByExampleSelective(ucBankCard, bankExample);
                }
                if(CollectionUtils.isEmpty(res.getSuccess_data())) {
                    log.info("用户 {} 解绑卡失败：{}", lnUserId, JSONObject.fromObject(res));
                    String errorMsg = "";
                    if(!CollectionUtils.isEmpty(res.getError_data())) {
                        lnPayOrders.setStatus(Constants.ORDER_STATUS_FAIL);
                        lnPayOrders.setReturnCode(res.getError_data().get(0).getError_no());
                        lnPayOrders.setReturnMsg(res.getError_data().get(0).getError_info());
                        lnPayOrders.setUpdateTime(new Date());
                        lnPayOrdersMapper.updateByPrimaryKeySelective(lnPayOrders);
                        errorMsg = res.getError_data().get(0).getError_info();
                    } else {
                        lnPayOrders.setStatus(Constants.ORDER_STATUS_FAIL);
                        lnPayOrders.setReturnCode(res.getResCode());
                        lnPayOrders.setReturnMsg("恒丰返回数据为空");
                        lnPayOrders.setUpdateTime(new Date());
                        lnPayOrdersMapper.updateByPrimaryKeySelective(lnPayOrders);
                        errorMsg = "恒丰返回数据为空";
                    }
                    LnPayOrdersJnl failjnl = new LnPayOrdersJnl();
                    failjnl.setOrderId(lnPayOrders.getId());
                    failjnl.setOrderNo(lnPayOrders.getOrderNo());
                    failjnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                    failjnl.setChannelTransType(Constants.CHANNEL_TRS_UN_BIND_CARD);
                    failjnl.setTransAmount(0d);
                    failjnl.setSysTime(new Date());
                    failjnl.setUserId(lnPayOrders.getLnUserId());
                    failjnl.setCreateTime(new Date());
                    payOrdersJnlMapper.insertSelective(failjnl);
                    throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "解绑失败：" + errorMsg);
                }
            } else {
                log.info("用户 {} 解绑卡失败：{}", lnUserId, JSONObject.fromObject(res));
                lnPayOrders.setStatus(Constants.ORDER_STATUS_FAIL);
                lnPayOrders.setReturnCode(res.getResCode());
                lnPayOrders.setReturnMsg(res.getResMsg());
                lnPayOrders.setUpdateTime(new Date());
                lnPayOrdersMapper.updateByPrimaryKeySelective(lnPayOrders);
                LnPayOrdersJnl failjnl = new LnPayOrdersJnl();
                failjnl.setOrderId(lnPayOrders.getId());
                failjnl.setOrderNo(lnPayOrders.getOrderNo());
                failjnl.setTransAmount(0d);
                failjnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                failjnl.setChannelTransType(Constants.CHANNEL_TRS_UN_BIND_CARD);
                failjnl.setSysTime(new Date());
                failjnl.setUserId(lnPayOrders.getLnUserId());
                failjnl.setCreateTime(new Date());
                payOrdersJnlMapper.insertSelective(failjnl);
                throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "解绑失败");
            }
        }
    }

    @Override
    public void matchAndLoanPay(final LnLoan lnLoan, LnBindCard lnBindCard, String channel) {
        //合作方
        String partnerCode = lnBindCard.getPartnerCode();
        String partnerName = PartnerEnum.getEnumByCode(partnerCode).getName();
        LnDepositionTarget depositionTarget = lnDepositionTargetMapper.selectByLoanId(lnLoan.getId());
        List<LoanRelation4DepVO> loanRelationList = null;
        PartnerEnum partnerEnum = PartnerEnum.getEnumByCode(partnerCode);
        LnDailyAmount lnDailyAmount = null;
        try {
            lnDailyAmount = lnDailyAmountService.getLnDailyAmount4Avaliable(partnerCode);
            if (lnDailyAmount != null) {
                lnDailyAmountService.chargeLoannDailyAmountFreeze(lnLoan.getApproveAmount(), lnDailyAmount.getId());
            } else {
                // 告警
                throw new PTMessageException(PTMessageEnum.ACCOUNT_QUOTA_NOT_ENOUGH);
            }

        	if(PartnerEnum.ZSD.getCode().equals(partnerCode)) {
                //赞时贷 调用借款债权匹配
        		loanRelationList = depFixedLoanRelationshipService.confirmLoanRelation4LoanZSD(lnLoan.getId(), lnLoan.getLnUserId(), null, lnLoan.getApproveAmount());
        	} else {
                //7贷/云贷 调用借款债权匹配
//                loanRelationList = depFixedLoanRelationshipService.confirmLoanRelation4Loan(lnLoan.getId(), lnLoan.getLnUserId(), null, lnLoan.getApproveAmount(), lnLoan.getPeriod(),partnerEnum);
                loanRelationList = depFixedMatchLoanerInvestorService.confirmLoanRelation4Loan(lnLoan.getId(), lnLoan.getLnUserId(), null, lnLoan.getApproveAmount(), lnLoan.getPeriod(),partnerEnum);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //调用债权匹配失败处理
            DepFixedLoanFailReq matchFailReq = new DepFixedLoanFailReq();
        	matchFailReq.setDepositionTarget(depositionTarget);
        	matchFailReq.setLnLoan(lnLoan);
        	matchFailReq.setError(e);
        	matchFailReq.setPropertySymbol(partnerCode);
        	matchFail(matchFailReq);

            boolean isAccountQuotaNotEnough = false; // 标识是否额度不足
            if (e instanceof PTMessageException) {
                PTMessageException ex = (PTMessageException) e;
                if (PTMessageEnum.ACCOUNT_QUOTA_NOT_ENOUGH.getCode().equals(ex.getErrCode())) {
                    isAccountQuotaNotEnough = true;
                }
            }
        	// 每日可用额度回退
            if (!isAccountQuotaNotEnough && lnDailyAmount != null) {
                lnDailyAmountService.chargeLoannDailyAmountUnFreeze(lnLoan.getApproveAmount(), lnDailyAmount.getId());
            }
            return;
        }
        if(CollectionUtils.isNotEmpty(loanRelationList)) {
        	int uLength = loanRelationList.size();
			int uSize = 100;
			BsSysConfig config = sysConfigService.findConfigByKey(Constants.HF_BATCH_EXT_BUY_MAX_SIZE);
			if(config != null){
				uSize = Integer.parseInt(config.getConfValue());
			}
			int uMaxPage = uLength%uSize==0 ? uLength/uSize : uLength/uSize + 1;

			Integer succSize = 0;//初始化批量投标成功
			Integer failSize = 0;//初始化批量投标失败
			String lastOrderNo = "";//最后一笔批量投标的订单号
			log.info("===========借款id："+lnLoan.getId()+"批量投标分"+uMaxPage+"次===========");
			for (int i = 1; i <= uMaxPage; i++) {
				log.info("===========借款id："+lnLoan.getId()+"批量投标第"+i+"次===========");
				//批量投标
	            B2GReqMsg_HFBank_BatchExtBuy req = new B2GReqMsg_HFBank_BatchExtBuy();
	            List<BatchExtBuyReqData> dataList = getDataList(loanRelationList.subList(uSize*(i-1), uLength<(uSize*i)?uLength:(uSize*i)));
	            String orderNo = Util.generateOrderNo4BaoFoo(8);
	            req.setOrder_no(orderNo);
	            req.setProd_id(depositionTarget.getId().toString());
	            req.setData(dataList);
	            req.setTotal_num(dataList.size());
	            req.setPartner_trans_date(new Date());
	            req.setPartner_trans_time(new Date());
	            log.info("标的批量投标订单号=["+orderNo+"]产品Id=["+depositionTarget.getId().toString()+"]");
	            B2GResMsg_HFBank_BatchExtBuy res = hfbankTransportService.batchExtBuy(req);
	            if(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode().equals(res.getResCode())){
	                specialJnlService.warn4Fail(lnLoan.getApproveAmount(), "标的批量投标订单号=["+orderNo+"]产品Id=["+depositionTarget.getId().toString()+"]请求超时", lnLoan.getPayOrderNo(), "【"+partnerName+"标的批量投标】",true);
	                log.info("===========借款id："+lnLoan.getId()+"批量投标第"+i+"次，超时===========");
	                lastOrderNo = orderNo;
	                throw new PTMessageException(PTMessageEnum.DEP_TARGET_BATCH_BUY_TIME_OUT);
	            }
	            if(StringUtil.isEmpty(res.getSuccess_num())){
	            	//批量投标失败
	            	log.info("===========借款id："+lnLoan.getId()+"批量投标第"+i+"次，失败===========");
	            	failSize = failSize+1;
	            	lastOrderNo = orderNo;
	            	break;
	            }
	            if(Integer.parseInt(res.getSuccess_num()) == dataList.size()){
	            	////批量投标成功
	            	succSize = succSize+1;
	            	if(i<uMaxPage){
	            		//新增一条ln_deposition_target_jnl记录
		            	depCommonService.insertTargetJnl(true,Constants.DEP_TARGET_BID,DepTargetEnum.DEP_TARGET_OPERATE_BID,depositionTarget,lnLoan,orderNo);
	            	}
	            }else{
	            	//批量投标失败
	            	log.info("===========借款id："+lnLoan.getId()+"批量投标第"+i+"次，失败===========");
	            	failSize = failSize+1;
	            	lastOrderNo = orderNo;
	            	break;
	            }
	            lastOrderNo = orderNo;
			}
            if(succSize!=uMaxPage && failSize > 0) {
            	//调用批量投标失败处理
                DepFixedLoanFailReq matchFailReq = new DepFixedLoanFailReq();
                matchFailReq.setDepositionTarget(depositionTarget);
                matchFailReq.setLnLoan(lnLoan);
                matchFailReq.setLoanRelationList(loanRelationList);
                matchFailReq.setOrderNo(lastOrderNo);
                matchFailReq.setPropertySymbol(partnerCode);
                buyActionFail(matchFailReq);

                // 每日可用额度回退
                lnDailyAmountService.chargeLoannDailyAmountUnFreeze(lnLoan.getApproveAmount(), lnDailyAmount.getId());
                return;
            }
            if(succSize != 0 && succSize==uMaxPage){
                //批量投标全部成功,标的表置为BID,增加一条标的流水
                depCommonService.updateTargetStatus(true,Constants.DEP_TARGET_BID,DepTargetEnum.DEP_TARGET_OPERATE_BID,depositionTarget,lnLoan,lastOrderNo);

                //标的成立请求
                B2GReqMsg_HFBank_EstablishOrAbandon establish = new B2GReqMsg_HFBank_EstablishOrAbandon();
                establish.setProd_id(depositionTarget.getId().toString());
                establish.setFlag(Constants.ESTABLISH_OR_ABANDON_FLAG_ABLISH);
                String estabishOrderNo = Util.generateOrderNo4BaoFoo(8);
                establish.setOrder_no(estabishOrderNo);
                List<EstablishOrAbandonReqFundData> fundDatas = new ArrayList<>();
                EstablishOrAbandonReqFundData fundData = new EstablishOrAbandonReqFundData();
                fundData.setPayout_plat_type(Constants.PAYOUT_PLAT_TYPE_FEE);
                fundData.setPayout_amt(lnLoan.getHeadFee().toString());
                fundDatas.add(fundData);
                establish.setFunddata(fundDatas);
                List<EstablishOrAbandonReqRepayPlan> repayPlans = new ArrayList<>();
                EstablishOrAbandonReqRepayPlan repayPlan = new EstablishOrAbandonReqRepayPlan();

                Integer period = 0;
                if (PartnerEnum.ZSD.getCode().equals(partnerCode)) {
					period = Math.abs(lnLoan.getPeriod());
				}else if (PartnerEnum.SEVEN_DAI_SELF.getCode().equals(partnerCode)) {

					period = depositionTarget.getCycle();
							//CalBillPeriod.calBillDays(lnLoan.getApplyTime());
				}else {
					period = 90;
				}

                if (PartnerEnum.YUN_DAI_SELF.getCode().equals(partnerCode) && BusinessTypeEnum.AVERAGE_CAPITAL_PLUS_INTEREST.getCode().equals(lnLoan.getPartnerBusinessFlag())) {
                    List<AverageCapitalPlusInterestVO> agreementVoList = algorithmService.calAverageCapitalPlusInterestPlan(lnLoan.getApproveAmount(), lnLoan.getPeriod(), lnLoan.getAgreementRate());
                    Double repay_amt = 0d;
                    for (AverageCapitalPlusInterestVO agreementVo : agreementVoList) {
                        repay_amt = MoneyUtil.add(repay_amt, agreementVo.getPlanTotal()).doubleValue();
                    }
                    log.info("计算协议利率总本息{}", repay_amt);
                    repayPlan.setRepay_amt(MoneyUtil.defaultRound(repay_amt).toString());
                    repayPlan.setRepay_fee("0");
                    repayPlan.setRepay_num(lnLoan.getPeriod().toString());
                    repayPlan.setRepay_date(DateUtils.addMonths(lnLoan.getApplyTime(), lnLoan.getPeriod()));
                } else if (PartnerEnum.YUN_DAI_SELF.getCode().equals(partnerCode) && BusinessTypeEnum.EQUAL_PRINCIPAL_INTEREST.getCode().equals(lnLoan.getPartnerBusinessFlag())) {
                    List<AverageCapitalPlusInterestVO> settleVOList = algorithmService.calEqualPrincipalInterestPlan(lnLoan.getApproveAmount(), lnLoan.getPeriod(), lnLoan.getBgwSettleRate());
                    Double repaySettle = 0d;
                    for (AverageCapitalPlusInterestVO settleVO : settleVOList) {
                        repaySettle = MoneyUtil.add(repaySettle, settleVO.getPlanInterest()).doubleValue();
                    }
                    Double repay_amt = CalculatorUtil.calculate("a+(a*a/a)", lnLoan.getApproveAmount(), lnLoan.getAgreementRate(), repaySettle, lnLoan.getBgwSettleRate());
                    log.info("计算协议利率总本息=出借人剩余本金{}+(借款协议利率{}*结算利息{}/结算利率{})", lnLoan.getApproveAmount(), lnLoan.getAgreementRate(), repaySettle, lnLoan.getBgwSettleRate());
                    repayPlan.setRepay_amt(MoneyUtil.defaultRound(repay_amt).toString());
                    repayPlan.setRepay_fee("0");
                    repayPlan.setRepay_num(lnLoan.getPeriod().toString());
                    repayPlan.setRepay_date(DateUtils.addMonths(lnLoan.getApplyTime(), lnLoan.getPeriod()));
                } else {
                    Double repay_amt = MoneyUtil.add(lnLoan.getApproveAmount(),
                            MoneyUtil.divide(
                                    MoneyUtil.multiply(period,
                                            MoneyUtil.multiply(lnLoan.getApproveAmount(), lnLoan.getAgreementRate()).doubleValue()).doubleValue()
                                    ,36500).doubleValue()
                    ).doubleValue();
                    log.info("计算协议利率总本息{}", repay_amt);
                    repayPlan.setRepay_amt(MoneyUtil.defaultRound(repay_amt).toString());
                    repayPlan.setRepay_fee("0");
                    repayPlan.setRepay_num("1");
                    repayPlan.setRepay_date(DateUtils.addDays(lnLoan.getApplyTime(),PartnerEnum.ZSD.getCode().equals(partnerCode)?(Math.abs(lnLoan.getPeriod())-1):89));
                }

                repayPlans.add(repayPlan);
                establish.setRepay_plan_list(repayPlans);
                B2GResMsg_HFBank_EstablishOrAbandon establishRes = null;
                log.info("标的成立请求订单号=["+estabishOrderNo+"]产品Id=["+depositionTarget.getId().toString()+"]");
                try {
                	establishRes = hfbankTransportService.establishOrAbandon(establish);
				} catch (Exception e) {
                    //新增一条标的成立失败流水
                    depCommonService.updateTargetStatus(false,Constants.DEP_TARGET_SET_UP,DepTargetEnum.DEP_TARGET_OPERATE_SET_UP,depositionTarget,lnLoan, estabishOrderNo);
                    specialJnlService.warn4Fail(lnLoan.getApproveAmount(), partnerName+"标的成立请求失败"+establishRes.getResMsg(), lnLoan.getPayOrderNo(), "【"+partnerName+"放款标的成立】",true);
                    throw new PTMessageException(PTMessageEnum.DEP_TARGET_ESTABLISH_ERROR);

				}

                if(ConstantUtil.BSRESCODE_SUCCESS.equals(establishRes.getResCode())){
//                if(res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS) &&
//                        Constants.HF_ORDER_STATUS_SUCC.equals(establishRes.getData().getOrder_status())){
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
                    payProcess(lnLoan);
                }else{
                    //新增一条标的成立失败流水
                    depCommonService.updateTargetStatus(false,Constants.DEP_TARGET_SET_UP,DepTargetEnum.DEP_TARGET_OPERATE_SET_UP,depositionTarget,lnLoan, estabishOrderNo);
                    specialJnlService.warn4Fail(lnLoan.getApproveAmount(), partnerName+"标的成立请求失败"+establishRes.getResMsg(), lnLoan.getPayOrderNo(), "【"+partnerName+"放款标的成立】",true);
                    throw new PTMessageException(PTMessageEnum.DEP_TARGET_ESTABLISH_ERROR);
                }
            }
        }

    }

	/**
     * 支付处理
     * @param lnLoan
     */
    @Override
    public void payProcess(LnLoan lnLoan) {

    	LnUser lnUser  = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());

        //新增一条ln_pay_orders记录
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
        lnPayOrders.setAmount(MoneyUtil.subtract(lnLoan.getApproveAmount(),lnLoan.getHeadFee()).doubleValue());
        LnBindCardExample example = new LnBindCardExample();
        example.createCriteria().andLnUserIdEqualTo(lnLoan.getLnUserId()).andStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode());
        example.setOrderByClause("update_time desc");
        List<LnBindCard> lnBindCards = lnBindCardMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(lnBindCards)){
            throw new PTMessageException(PTMessageEnum.USER_NOT_BIND_CARD);
        }
        LnBindCard lnBindCard = lnBindCards.get(0);
        lnPayOrders.setBankCardNo(lnBindCard.getBankCard());
    	String partnerCode = lnBindCard.getPartnerCode();
    	String partnerName = PartnerEnum.getEnumByCode(partnerCode).getName();
        BsCardBin bsCardBin = bankCardService.queryBankBin(lnBindCard.getBankCard());
        BsBank bank = bsBankMapper.selectByPrimaryKey(bsCardBin.getBankId());
        lnPayOrders.setBankId(bsCardBin.getBankId());
        lnPayOrders.setBankName(lnBindCard.getBankName());
        lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
        lnPayOrders.setIdCard(lnBindCard.getIdCard());
        lnPayOrders.setLnUserId(lnBindCard.getLnUserId());
        lnPayOrders.setMobile(lnBindCard.getMobile());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(lnLoan.getPayOrderNo());
        lnPayOrders.setPartnerCode(partnerCode);
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
        lnPayOrdersJnl.setTransAmount(MoneyUtil.subtract(lnLoan.getApproveAmount(),lnLoan.getHeadFee()).doubleValue());
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
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
        chargeOffDetail.setOut_amt(MoneyUtil.subtract(lnLoan.getApproveAmount(), lnLoan.getHeadFee()).toString());//出账金额=标的金额-手续费
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
        log.info("标的出账请求订单号=["+lnLoan.getPayOrderNo()+"]产品Id=["+lnDepositionTarget.getId().toString()+"]联行行号=["+chargeOffDetail.getBank_code()+"]");
        try {
            res = hfbankTransportService.chargeOff(chargeOff);
        } catch (Exception e) {
            log.error(partnerCode+"标的出账通讯失败：{}", e);
            specialJnlService.warn4Fail(lnLoan.getApproveAmount(), partnerName+"标的出账请求异常", lnLoan.getPayOrderNo(), partnerName+"放款标的出账", false);
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
            outOfAccountResultAccept(failReq, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE, PartnerEnum.getEnumByCode(partnerCode));
        }

    }

    @Override
    public void outOfAccountResultAccept(final G2BReqMsg_HFBank_OutOfAccount req, final DepTargetEnum depTargetEnum, final PartnerEnum partner) {
        outOfAccountResultAccept(req, depTargetEnum, partner, true);
    }

    @Override
    public void outOfAccountResultAccept(final G2BReqMsg_HFBank_OutOfAccount req, final DepTargetEnum depTargetEnum, final PartnerEnum partner, final boolean isNotify) {
        LnPayOrdersExample example4IdCard = new LnPayOrdersExample();
        example4IdCard.createCriteria().andOrderNoEqualTo(req.getOrder_no());
        final List<LnPayOrders> orders4IdCard= payOrdersMapper.selectByExample(example4IdCard);
        if(CollectionUtils.isEmpty(orders4IdCard)){
            throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST,"订单号"+req.getOrder_no()+"对应订单不存在");
        }
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_LOAN_SELF.getKey() + partner.getCode() + orders4IdCard.get(0).getIdCard());
            Map<String, Object> mapInfo = transactionTemplate.execute(new TransactionCallback<Map<String, Object>>(){
                @Override
                public Map<String, Object> doInTransaction(TransactionStatus status) {
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
                    if(Constants.HF_OUT_OF_ACCOUNT_SUCCESS.equals(req.getOrder_status())){
                        orderUpdate.setReturnCode(ConstantUtil.BSRESCODE_SUCCESS);
                        orderUpdate.setReturnMsg(ConstantUtil.DEFAULT_SUCESSMSG);
                        orderUpdate.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
                    }else {
                        orderUpdate.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
                        //记录失败的原因
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

                    // 保存签章需要的信息
                    Map<String, Object> mapInfo = new HashMap<>();

                    if(req.getOrder_status().equals(Constants.HF_OUT_OF_ACCOUNT_SUCCESS)){
                        Double actFee = 0d;
                        Double needFee = 0d;
                        Double threePartyPaymentServiceFee = 0d;
                        CommissionVO commission = null;
                        if (PartnerEnum.YUN_DAI_SELF.equals(partner)) {
                            //手续费金额查询
                            commission = commissionService.calServiceFee(lnPayOrder.getAmount(), TransTypeEnum.YUN_DAI_SELF_LOAN, PayPlatformEnum.HF_BAOFOO);
                        } else if (PartnerEnum.SEVEN_DAI_SELF.equals(partner)) {
                            commission = commissionService.calServiceFee(lnPayOrder.getAmount(), TransTypeEnum.SEVEN_DAI_SELF_LOAN, PayPlatformEnum.HF_BAOFOO);
                        } else if (PartnerEnum.ZSD.equals(partner)) {
                            // 赞时贷借款（支付通道）手续费（和赞时贷保持一致）
                            commission = commissionService.calServiceFee(lnPayOrder.getAmount(), TransTypeEnum.ZSD_LOAN, PayPlatformEnum.HF_BAOFOO);
                            // 更新还款计划表
                            LnRepayScheduleExample repayScheduleExample = new LnRepayScheduleExample();
                            repayScheduleExample.createCriteria().andLoanIdEqualTo(lnLoan.getId());
                            List<LnRepaySchedule> repayScheduleList = lnRepayScheduleMapper.selectByExample(repayScheduleExample);
                            for (LnRepaySchedule lnRepaySchedule : repayScheduleList) {
                                LnRepaySchedule lnRepayScheduleTemp = new LnRepaySchedule();
                                lnRepayScheduleTemp.setId(lnRepaySchedule.getId());
                                lnRepayScheduleTemp.setUpdateTime(new Date());
                                Date now = new Date();
                                Date applyDate = com.pinting.core.util.DateUtil.parse(DateUtil.format(lnLoan.getApplyTime(), "yyyy-MM-dd"), "yyyy-MM-dd");
                                Date today = com.pinting.core.util.DateUtil.parse(DateUtil.format(now, "yyyy-MM-dd"), "yyyy-MM-dd");
                                int compareDays = com.pinting.core.util.DateUtil.getDiffeDay(today, applyDate);
                                Date planDate = com.pinting.core.util.DateUtil.addDays(lnRepaySchedule.getPlanDate(), compareDays);
                                lnRepayScheduleTemp.setPlanDate(planDate);
                                lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepayScheduleTemp);
                            }
                        }

                        //记录手续费
                        actFee = commission.getActPayAmount();
                        needFee = commission.getNeedPayAmount();
                        threePartyPaymentServiceFee = commission.getThreePartyPaymentServiceFee();
                        BsServiceFee bsServiceFee = new BsServiceFee();
                        bsServiceFee.setPlanFee(needFee);
                        bsServiceFee.setDoneFee(actFee);
                        bsServiceFee.setTransAmount(lnPayOrder.getAmount());
                        bsServiceFee.setFeeType(Constants.FEE_TYPE_LOAN);
                        bsServiceFee.setStatus(Constants.FEE_STATUS_COLLECT);
                        bsServiceFee.setCreateTime(new Date());
                        bsServiceFee.setOrderNo(lnPayOrder.getOrderNo());
                        bsServiceFee.setSubAccountId(lnPayOrder.getSubAccountId());
                        bsServiceFee.setUpdateTime(new Date());
                        bsServiceFee.setNote("应扣" + needFee + "，实扣" + actFee);
                        bsServiceFee.setPaymentPlatformFee(threePartyPaymentServiceFee);
                        serviceFeeMapper.insertSelective(bsServiceFee);

                        //修改ln_deposition_target为已出账,新增一条ln_deposition_target_jnl记录
                        depCommonService.updateTargetStatus(true,Constants.DEP_TARGET_CHARGE_OFF,DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF,lnDepositionTarget,lnLoan,req.getOrder_no());
                        //修改借款表为成功
                        LnLoan loanTemp = new LnLoan();
                        loanTemp.setId(lnLoan.getId());
                        loanTemp.setUpdateTime(new Date());
                        loanTemp.setLoanTime(new Date());
                        loanTemp.setStatus(LoanStatus.LOAN_STATUS_PAIED.getCode());
                        lnLoanMapper.updateByPrimaryKeySelective(loanTemp);

                        // 设置借款放款时间，后续生成协议签章时使用
                        lnLoan.setLoanTime(loanTemp.getLoanTime());
                        //借贷关系表置为成功
                        LnLoanRelationExample exampleRel = new LnLoanRelationExample();
                        exampleRel.createCriteria().andLoanIdEqualTo(lnLoan.getId());
                        LnLoanRelation lnLoanRelation = new LnLoanRelation();
                        lnLoanRelation.setStatus(Constants.LOAN_RELATION_STATUS_SUCCESS);
                        lnLoanRelation.setUpdateTime(new Date());
                        loanRelationMapper.updateByExampleSelective(lnLoanRelation,exampleRel);
                        // bs_revenue_trans_detail记录砍头息营收记录
                        Double headFee = lnLoan.getHeadFee();
                        BsRevenueTransDetail bsRevenueTransDetail = new BsRevenueTransDetail();
                        bsRevenueTransDetail.setPartnerCode(partner.getCode());
                        bsRevenueTransDetail.setTransType(Constants.REVENUE_TYPE_HEAD_FEE_INCOME);
                        bsRevenueTransDetail.setLoanId(lnLoan.getId());
                        bsRevenueTransDetail.setRevenueAmount(headFee);
                        bsRevenueTransDetail.setCreateTime(new Date());
                        bsRevenueTransDetail.setUpdateTime(new Date());
                        bsRevenueTransDetailMapper.insertSelective(bsRevenueTransDetail);

                        Double realAmount = 0d,redPac = 0d;
                        //查询借贷关系
                        LnLoanRelationExample relationExample = new LnLoanRelationExample();
                        relationExample.createCriteria().andLoanIdEqualTo(lnLoan.getId());
                        relationExample.setOrderByClause("bs_sub_account_id asc");
                        final List<LnLoanRelation> relationList = loanRelationMapper.selectByExample(relationExample);
                        List<InvestorAuthYunInfo> investorAuthYuns = new ArrayList<>();
                        for (LnLoanRelation relation : relationList) {
                            InvestorAuthYunInfo incrInfo = new InvestorAuthYunInfo();
                            incrInfo.setInvestorAuthYunActId(relation.getBsSubAccountId());
                            incrInfo.setAuthYunAmount(relation.getInitAmount());
                            incrInfo.setRedPacAmount(relation.getDiscountAmount());
                            investorAuthYuns.add(incrInfo);
                            Double discountAmount = relation.getDiscountAmount() == null ?0 : relation.getDiscountAmount();
                            realAmount = MoneyUtil.add(realAmount,
                                    MoneyUtil.subtract(relation.getTotalAmount(),discountAmount).doubleValue()).doubleValue();
                            redPac = MoneyUtil.add(redPac,discountAmount).doubleValue();
                            /*realAmount += (relation.getTotalAmount() - relation.getDiscountAmount());
                            redPac += relation.getDiscountAmount();*/
                        }
                        BaseAccount baseAccount = new BaseAccount();
                        baseAccount.setBorrowerUserId(lnLoan.getLnUserId());
                        baseAccount.setPartner(partner);
                        baseAccount.setRealAmount(realAmount);
                        baseAccount.setRedPacAmount(redPac);
                        //借款人余额子账户编号
                        LnSubAccountExample exampleAcc = new LnSubAccountExample();
                        exampleAcc.createCriteria().andAccountTypeEqualTo(Constants.LOAN_ACT_TYPE_DEP_JSH).andLnUserIdEqualTo(lnLoan.getLnUserId());
                        List<LnSubAccount> lnSubAccounts = lnSubAccountMapper.selectByExample(exampleAcc);
                        if(CollectionUtils.isEmpty(lnSubAccounts)){
                            throw new PTMessageException(PTMessageEnum.SUB_ACCOUNT_NO_EXIT,",融资人余额子账户不存在");
                        }
                        LnSubAccount lnSubAccount = lnSubAccounts.get(0);
                        // 出账成功记账
                        depFixedLoanAccountService.chargeUpLoan(baseAccount,investorAuthYuns, lnSubAccount.getId(), headFee);

                        String agreementNo = null;
                        //放款结果通知云贷和七贷才需要改成三方协议，赞时贷有自己的流程
                        if (PartnerEnum.YUN_DAI_SELF.equals(partner) || PartnerEnum.SEVEN_DAI_SELF.equals(partner)) {
                        	 //生成协议编号
                            BsUserSealFile bsUserSealFile = new BsUserSealFile();
                    		String loanId = String.valueOf(lnLoan.getId());
                    		String partnerLoanId = lnLoan.getPartnerLoanId();
                    		if (PartnerEnum.YUN_DAI_SELF.equals(partner)) {
                    			if( BusinessTypeEnum.AVERAGE_CAPITAL_PLUS_INTEREST.getCode().equals(lnLoan.getPartnerBusinessFlag())) {
                    				agreementNo = "bgwde-jkxy@" + partnerLoanId;
                    			} else if ( BusinessTypeEnum.EQUAL_PRINCIPAL_INTEREST.getCode().equals(lnLoan.getPartnerBusinessFlag())) {
            						agreementNo = "bgwdb-jkxy@" + partnerLoanId;
                				} else if( BusinessTypeEnum.REPAY_ANY_TIME.getCode().equals(lnLoan.getPartnerBusinessFlag()) ) {
                    				agreementNo = "bgw-jkxy@" + partnerLoanId;
            					} else {
                					agreementNo = "bgw-jkxy@" + partnerLoanId;
                				}
                    			
                    			bsUserSealFile.setUserSrc(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
    						} else {
    							agreementNo = partnerLoanId;
    							bsUserSealFile.setUserSrc(Constants.PROPERTY_SYMBOL_7_DAI_SELF);
    						}
                    		//插入用户签章文件记录表
                    		bsUserSealFile.setNote("协议待签章");
                    		bsUserSealFile.setSealStatus(SealStatus.INIT.getCode());
                    		bsUserSealFile.setSealType(SealBusiness.LOAN_AGREEMENT.getCode());
                    		bsUserSealFile.setAgreementNo(agreementNo);
                    		bsUserSealFile.setUserId(lnPayOrder.getLnUserId());
                    		bsUserSealFile.setRelativeInfo(loanId);
                    		bsUserSealFile.setCreateTime(new Date());
                    		bsUserSealFile.setUpdateTime(new Date());
                    		bsUserSealFileMapper.insert(bsUserSealFile);
						}

                        LnLoan loanNew = lnLoanMapper.selectByPrimaryKey(lnLoan.getId());
                        log.info("出账成功通知开始"+partner.getCode());

                        // 控制是否通知合作方
                        if (isNotify) {
                            notifyLoan2Partner(partner, loanNew, "标的出账成功", agreementNo);
                        }

                        mapInfo.put("lnLoan", lnLoan);
                        mapInfo.put("agreementNo", agreementNo);
                        return mapInfo;
                    }else{
                        //新增一条ln_deposition_target_jnl记录
                        depCommonService.updateTargetStatus(false,DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE.equals(depTargetEnum)?Constants.DEP_TARGET_SET_UP:Constants.DEP_TARGET_CHARGE_OFF,
                                DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE.equals(depTargetEnum)?DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE:DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF,
                                lnDepositionTarget,lnLoan,req.getOrder_no());
                        //标的出账申请或回调通知失败,增加告警信息
                        String detail = partner.getName() + depTargetEnum.getDescription() + "通知结果失败 ,出账订单号=[" + req.getOrder_no() + "]";
                        specialJnlService.warnDevelop4Fail(lnLoan.getApproveAmount(), detail, lnLoan.getPayOrderNo(), "【" + partner.getName() + "放款标的出账】",false);
                        return mapInfo;
                    }
                }
            });

            // 控制生成协议签章
            if(mapInfo != null && !mapInfo.isEmpty()) {
                LnLoan lnLoan = (LnLoan) mapInfo.get("lnLoan");
                String agreementNo = (String) mapInfo.get("agreementNo");
                if (lnLoan != null && StringUtils.isNotBlank(agreementNo)) {
                    // 协议签章
                    loanAgreementSignSealService.protocolSeal(partner, new ProtocolSealVO(agreementNo, lnLoan));
                }
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_LOAN_SELF.getKey() + partner.getCode() + orders4IdCard.get(0).getIdCard());
        }
    }


    /**
     * 放款通知合作方分发
     * @param partnerEnum
     * @param lnLoan
     * @param errorMsg
     */
    private void notifyLoan2Partner(PartnerEnum partnerEnum, LnLoan lnLoan, String errorMsg, String agreementNo){
        switch (partnerEnum){
            case YUN_DAI_SELF :
                notifyLoan2YunDai(lnLoan, errorMsg, agreementNo);
                break;
            case SEVEN_DAI_SELF :
                noticeLoan2Seven(lnLoan, errorMsg, agreementNo);
                break;
            case ZSD :
                noticeLoan2Dsd(lnLoan, errorMsg);
                break;
        }
    }

	/**
     * 放款通知云贷
     *
     * @param lnLoan
     */
    public void notifyLoan2YunDai(final LnLoan lnLoan, final String errorMsg, final String agreementNo) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                B2GReqMsg_DafyLoanNotice_LoanResultNotice noticeLoan = new B2GReqMsg_DafyLoanNotice_LoanResultNotice();
                noticeLoan.setOrderNo(lnLoan.getPartnerOrderNo());
                noticeLoan.setPayChannel(Constants.CHANNEL_HFBANK);
                noticeLoan.setChannel(Constants.CHANNEL_DAFY);
                noticeLoan.setBgwOrderNo(lnLoan.getPayOrderNo());
                noticeLoan.setAgreementNo(agreementNo);

                // 云贷获取借款服务费率（100=1%）
                BsSysConfig loanServiceRateConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.LOAN_SERVICE_RATE_YUN_DAI);
                noticeLoan.setLoanServiceRate(StringUtils.isNotBlank(loanServiceRateConfig.getConfValue()) ? MoneyUtil.formatEnlarge(loanServiceRateConfig.getConfValue()).intValue() : null);

                noticeLoan.setLoanId(lnLoan.getPartnerLoanId());
                noticeLoan.setResultCode(lnLoan.getStatus().equals(LoanStatus.LOAN_STATUS_PAIED.getCode()) ? "SUCCESS" : "FAIL");
                noticeLoan.setResultMsg(errorMsg);
                noticeLoan.setFinishTime(lnLoan.getLoanTime() != null ? lnLoan.getLoanTime() : null);
                //获取出借人列表
                List<LnLoanRelationVO> loanRelList = loanRelationMapper.selectLendersByLoanId(lnLoan.getId());

                ArrayList<HashMap<String, Object>> lenders = new ArrayList<HashMap<String, Object>>();

                for(LnLoanRelationVO lnLoanRelation: loanRelList) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("investAmount", lnLoanRelation.getApproveAmount());
                    map.put("lenderName", lnLoanRelation.getUserName());
                    map.put("lenderIdcard",lnLoanRelation.getIdCard());
                    map.put("mobile",lnLoanRelation.getMobile());
                    lenders.add(map);
                }
                noticeLoan.setLenders(lenders);
                B2GResMsg_DafyLoanNotice_LoanResultNotice res = null;
                LnLoan loanTemp = new LnLoan();
                loanTemp.setId(lnLoan.getId());
                try {
                    res = dafyNoticeService.noticeLoan(noticeLoan);
                    if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                        loanTemp.setUpdateTime(new Date());
                        loanTemp.setInformStatus(LoanStatus.NOTICE_STATUS_SUCCESS.getCode());
                    } else {
                        loanTemp.setInformStatus(LoanStatus.NOTICE_STATUS_FAIL.getCode());
                        loanTemp.setUpdateTime(new Date());
                        lnLoanMapper.updateByPrimaryKeySelective(loanTemp);
                        throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
                    }
                } catch (Exception e) {
                    loanTemp.setInformStatus(LoanStatus.NOTICE_STATUS_FAIL.getCode());
                    loanTemp.setUpdateTime(new Date());
                    e.printStackTrace();
                } finally{
                    log.info("更新通知状态为SUCCESS/FAIL");
                    lnLoanMapper.updateByPrimaryKeySelective(loanTemp);
                }
            }
        }).start();
    }

	@Override
	public void matchFail(DepFixedLoanFailReq matchFailReq) {
		LnDepositionTarget depositionTarget = matchFailReq.getDepositionTarget();
		LnLoan lnLoan = matchFailReq.getLnLoan();
		Double orderAmount = defaultRound(lnLoan.getApproveAmount() - lnLoan.getHeadFee()).doubleValue();
		/**
		 * 1、修改借款表信息，标的表信息
		 * 2、发送标的废除，根据废除返回succ/fail记录存管标的操作流水表
		 * 3、通知云贷
		 */

		//标的废除
    	String orderNo = Util.generateOrderNo4BaoFoo(8);
        B2GReqMsg_HFBank_EstablishOrAbandon abandonReq = new B2GReqMsg_HFBank_EstablishOrAbandon();
        abandonReq.setProd_id(depositionTarget.getId().toString());
        abandonReq.setOrder_no(orderNo);
        abandonReq.setRemark("标的废除");
        abandonReq.setFlag(Constants.ESTABLISH_OR_ABANDON_FLAG_ABANDON);
        B2GResMsg_HFBank_EstablishOrAbandon   abandonRes = new  B2GResMsg_HFBank_EstablishOrAbandon();
        try {
        	abandonRes = hfbankTransportService.establishOrAbandon(abandonReq);
		} catch (Exception e) {
            log.error("云贷放款标的废除请求异常：{}", e);
            abandonRes.setResCode(ConstantUtil.BSRESCODE_FAIL);
            abandonRes.setResMsg("通讯失败，置为失败");
		}
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(abandonRes.getResCode())){
//        if(ConstantUtil.BSRESCODE_SUCCESS.equals(abandonRes.getResCode())
//                && abandonRes.getData() != null && Constants.HF_ORDER_STATUS_SUCC.equals(abandonRes.getData().getOrder_status())){
            //修改借款表信息
            LnLoan loanTemp = new LnLoan();
            loanTemp.setId(lnLoan.getId());
            loanTemp.setUpdateTime(new Date());
            loanTemp.setStatus(LoanStatus.LOAN_STATUS_FAIL.getCode());
            lnLoanMapper.updateByPrimaryKeySelective(loanTemp);
            LnLoan loanNew = lnLoanMapper.selectByPrimaryKey(lnLoan.getId());
        	//修改标的为CANCELLED，新增一条标的废除成功流水
            depCommonService.updateTargetStatus(true,Constants.DEP_TARGET_CANCELLED,DepTargetEnum.DEP_TARGET_OPERATE_CANCELLED,depositionTarget,lnLoan,orderNo);
            Exception e =  matchFailReq.getError();
            PartnerEnum partnerEnum = PartnerEnum.getEnumByCode(matchFailReq.getPropertySymbol());
            if(e == null){
                notifyLoan2Partner(PartnerEnum.getEnumByCode(matchFailReq.getPropertySymbol()), loanNew,"债权匹配失败", null);
                specialJnlService.warn4FailNoSMS(orderAmount, "债权匹配失败", loanNew.getPayOrderNo(), "【"+partnerEnum.getName()+"债权匹配】");
                return;
            }else{
                if ( e instanceof PTMessageException) {
                    notifyLoan2Partner(PartnerEnum.getEnumByCode(matchFailReq.getPropertySymbol()), loanNew,((PTMessageException) e).getErrMessage(), null);
                    specialJnlService.warn4FailNoSMS(orderAmount, ((PTMessageException) e).getErrMessage(), loanNew.getPayOrderNo(), "【"+partnerEnum.getName()+"债权匹配】");
                    return;
                } else {
                    notifyLoan2Partner(PartnerEnum.getEnumByCode(matchFailReq.getPropertySymbol()), loanNew,PTMessageEnum.TRANS_ERROR.getMessage(), null);
                    specialJnlService.warn4FailNoSMS(orderAmount, PTMessageEnum.TRANS_ERROR.getMessage(), loanNew.getPayOrderNo(), "【"+partnerEnum.getName()+"债权匹配】");
                    return;
                }
            }
        }else{
        	//新增一条标的废除失败流水
            depCommonService.updateTargetStatus(false,Constants.DEP_TARGET_CANCELLED,DepTargetEnum.DEP_TARGET_OPERATE_CANCELLED,depositionTarget,lnLoan, orderNo);
        	//告警
        	specialJnlService.warn4Fail(lnLoan.getApproveAmount(), PartnerEnum.getEnumByCode(matchFailReq.getPropertySymbol()).getName()+"放款标的废除失败depositionTargetId："+depositionTarget.getId(),
        			depositionTarget.getId().toString(), "【"+PartnerEnum.getEnumByCode(matchFailReq.getPropertySymbol()).getName()+"放款标的废除】",false);
            throw new PTMessageException(PTMessageEnum.DEP_TARGET_ABANDON_ERROR);
        }
	}

	@Override
	public void buyActionFail(DepFixedLoanFailReq matchFailReq) {
		/**
         * 1、存管标的操作流水表，投标失败
		 * [2、修改借款表信息，标的表信息
		 * 3、发送标的废除，根据废除返回succ/fail记录存管标的操作流水表
		 * 4、通知云贷]
		 * 5、ln_loan_relation表状态修改
		 * 6、冻结解冻
		 */
		//存管标的操作流水表
        LnDepositionTarget depositionTarget = matchFailReq.getDepositionTarget();
        LnLoan lnLoan = matchFailReq.getLnLoan();
    	LnDepositionTargetJnl depositionTargetJnlBidFailTemp = new LnDepositionTargetJnl();
    	depositionTargetJnlBidFailTemp.setProdId(depositionTarget.getId());
    	depositionTargetJnlBidFailTemp.setTransTime(new Date());
    	depositionTargetJnlBidFailTemp.setTransType(Constants.TARGET_JNL_TRANS_TYPE_PROD_BID);
    	depositionTargetJnlBidFailTemp.setTransName("批量投标");
    	depositionTargetJnlBidFailTemp.setAmount(lnLoan.getApproveAmount());
    	depositionTargetJnlBidFailTemp.setOrderNo(matchFailReq.getOrderNo());
    	depositionTargetJnlBidFailTemp.setProdStatus(Constants.DEP_TARGET_PUBLISH);
    	depositionTargetJnlBidFailTemp.setCreateTime(new Date());
    	depositionTargetJnlBidFailTemp.setUpdateTime(new Date());
    	depositionTargetJnlBidFailTemp.setTransStatus(Constants.TARGET_JNL_TRANS_STATUS_FAIL);
    	depositionTargetJnlMapper.insertSelective(depositionTargetJnlBidFailTemp);

		matchFail(matchFailReq);

		List<LoanRelation4DepVO> loanRelationList = matchFailReq.getLoanRelationList();

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

			LnLoanRelation lnLoanRelation = loanRelationMapper.selectByPrimaryKey(loanRelation4DepVO.getLnLoanRelation().getId());

			LnLoanRelation lnLoanRelationTemp = new LnLoanRelation();
			lnLoanRelationTemp.setId(lnLoanRelation.getId());
			lnLoanRelationTemp.setStatus(Constants.LOAN_RELATION_STATUS_FAIL);
			lnLoanRelationTemp.setUpdateTime(new Date());
			loanRelationMapper.updateByPrimaryKeySelective(lnLoanRelationTemp);
		}
	}

    @Override
    public void loanResultQuery(G2BReqMsg_DafyLoan_QueryLoanResult req, G2BResMsg_DafyLoan_QueryLoanResult res, PartnerEnum partnerEnum) {
        LnLoan lnLoan = lnLoanMapper.selectByParOrdNoAndParCode(req.getOrderNo(),partnerEnum.getCode());
        if(lnLoan == null) {
            /**
             *  1、查询ln_loan不存在记录，查询表ln_loan_apply_record不存在记录，响应返回支付失败
             *  2、查询ln_loan不存在，查询表ln_loan_apply_record存在，ln_loan.PartnerOrderNo放款失败数据存在，响应返回支付失败
             *  3、查询ln_loan不存在，查询表ln_loan_apply_record存在，ln_loan.PartnerOrderNo放款失败数据不存在，响应返回支付处理中
             *  4、查询ln_loan存在，返回ln_loan状态对应信息
             */
            LnLoanApplyRecordExample lnLoanApplyRecordExample = new LnLoanApplyRecordExample();
            lnLoanApplyRecordExample.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo());
            List<LnLoanApplyRecord> lnLoanApplyRecordList = lnLoanApplyRecordMapper.selectByExample(lnLoanApplyRecordExample);
            if (CollectionUtils.isEmpty(lnLoanApplyRecordList)) {
                // 借款申请记录表中未找到订单号
                res.setResultCode(Constants.ORDER_TRANS_CODE_FAIL);
                res.setResultMsg("放款失败：未找到订单号:" + req.getOrderNo() + "对应借款信息");
            } else {
                LnLoanExample lnLoanExample = new LnLoanExample();
                lnLoanExample.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo())
                        .andStatusEqualTo(LoanStatus.LOAN_STATUS_FAIL.getCode()).andLnUserIdIsNull();
                List<LnLoan> failLoanList = lnLoanMapper.selectByExample(lnLoanExample);
                if(CollectionUtils.isNotEmpty(failLoanList)) {
                    res.setResultCode(Constants.ORDER_TRANS_CODE_FAIL);
                    res.setLoanId(failLoanList.get(0).getPartnerLoanId());
                    res.setResultMsg("放款失败：借款信息校验失败");
                } else {
                    res.setResultCode(Constants.ORDER_TRANS_CODE_PROCESS);
                    res.setResultMsg(LoanStatus.ORDERS_STATUS_PAYING.getDescription());
                }
            }
            res.setOrderNo(req.getOrderNo());
            res.setChannel(Constants.CHANNEL_HFBANK);
            return;
        }
        res.setOrderNo(req.getOrderNo());
        res.setBgwOrderNo(lnLoan.getPayOrderNo());

        if (PartnerEnum.YUN_DAI_SELF.getCode().equals(partnerEnum.getCode())) {
            // 云贷获取借款服务费率（100=1%）
            BsSysConfig loanServiceRateConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.LOAN_SERVICE_RATE_YUN_DAI);
            res.setLoanServiceRate(loanServiceRateConfig != null && StringUtils.isNotBlank(loanServiceRateConfig.getConfValue()) ? MoneyUtil.formatEnlarge(loanServiceRateConfig.getConfValue()).intValue() : null);
        } else if (PartnerEnum.SEVEN_DAI_SELF.getCode().equals(partnerEnum.getCode())) {
            // 7贷获取借款服务费率（100=1%）
            BsSysConfig loanServiceRateConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.LOAN_SERVICE_RATE_SEVEN_DAI);
            res.setLoanServiceRate(loanServiceRateConfig != null && StringUtils.isNotBlank(loanServiceRateConfig.getConfValue()) ? MoneyUtil.formatEnlarge(loanServiceRateConfig.getConfValue()).intValue() : null);
        }

        res.setLoanId(lnLoan.getPartnerLoanId());
        res.setChannel(Constants.CHANNEL_HFBANK);
        if(lnLoan.getStatus().equals(LoanStatus.LOAN_STATUS_PAIED.getCode())) {
            res.setResultCode(Constants.ORDER_TRANS_CODE_SUCCESS);
            res.setResultMsg(LoanStatus.LOAN_STATUS_PAIED.getDescription());
            res.setFinishTime(lnLoan.getLoanTime());

            //查询协议编号返回
            BsUserSealFileExample bsUserSealFileExample = new BsUserSealFileExample();
            bsUserSealFileExample.createCriteria().andSealTypeEqualTo(SealBusiness.LOAN_AGREEMENT.getCode()).andRelativeInfoEqualTo(String.valueOf(lnLoan.getId()));
            List<BsUserSealFile> list = bsUserSealFileMapper.selectByExample(bsUserSealFileExample);
            if (!CollectionUtils.isEmpty(list)) {
                res.setAgreementNo(list.get(0).getAgreementNo());
            }else {
                res.setOrderNo(req.getOrderNo());
                res.setChannel(Constants.CHANNEL_HFBANK);
                res.setResultCode(Constants.ORDER_TRANS_CODE_FAIL);
                res.setResultMsg("放款结果查询未找到订单号:"+ req.getOrderNo() +"对应的借款协议编号");
                return;
            }

        }else if(lnLoan.getStatus().equals(LoanStatus.LOAN_STATUS_FAIL.getCode())) {
            res.setResultCode(Constants.ORDER_TRANS_CODE_FAIL);
            LnPayOrdersExample payOrdersExample = new LnPayOrdersExample();
            payOrdersExample.createCriteria().andOrderNoEqualTo(lnLoan.getPayOrderNo());
            List<LnPayOrders> payOrdersList = payOrdersMapper.selectByExample(payOrdersExample);
            if(CollectionUtils.isEmpty(payOrdersList)){
                res.setOrderNo(req.getOrderNo());
                res.setChannel(Constants.CHANNEL_HFBANK);
                res.setResultCode(Constants.ORDER_TRANS_CODE_FAIL);
                res.setResultMsg("放款结果查询未找到订单号:"+ req.getOrderNo() +"对应订单信息");
                return;
            }
            res.setResultMsg(StringUtils.isBlank(payOrdersList.get(0).getReturnMsg()) ? LoanStatus.LOAN_STATUS_FAIL.getDescription() : payOrdersList.get(0).getReturnMsg());
        } else if (LoanStatus.LOAN_STATUS_CHECKED.getCode().equals(lnLoan.getStatus())
                || LoanStatus.LOAN_STATUS_PAYING.getCode().equals(lnLoan.getStatus())) {
            // 查询借款表处于ln_loan.status = CHECKED 或 ln_loan.status = PAYING时，查询订单表（ln_pay_orders）状态
            // 订单表无记录时，响应字段（resultMsg）：处理中
            // 订单有记录时且ln_pay_orders.status=5时，响应字段（resultMsg）：支付处理中
            // 订单有记录时且ln_pay_orders.status=7时，响应字段（resultMsg）：支付失败
            // 订单有记录时且ln_pay_orders.status=6时，响应字段（resultMsg）：支付成功
            LnPayOrdersExample payOrdersExample = new LnPayOrdersExample();
            payOrdersExample.createCriteria().andOrderNoEqualTo(lnLoan.getPayOrderNo());
            List<LnPayOrders> payOrdersList = payOrdersMapper.selectByExample(payOrdersExample);
            if(CollectionUtils.isEmpty(payOrdersList)) {
                res.setResultCode(Constants.ORDER_TRANS_CODE_PROCESS);
                res.setResultMsg("处理中");
            } else {
                if (LoanStatus.ORDERS_STATUS_PAYING.getCode().equals(payOrdersList.get(0).getStatus().toString())) {
                    res.setResultCode(Constants.ORDER_TRANS_CODE_PROCESS);
                    res.setResultMsg(LoanStatus.ORDERS_STATUS_PAYING.getDescription());
                } else if (LoanStatus.ORDERS_STATUS_SUCCESS.getCode().equals(payOrdersList.get(0).getStatus().toString())) {
                    res.setResultCode(Constants.ORDER_TRANS_CODE_PROCESS);
                    res.setResultMsg(LoanStatus.ORDERS_STATUS_SUCCESS.getDescription());
                } else {
                    res.setResultCode(Constants.ORDER_TRANS_CODE_PROCESS);
                    res.setResultMsg(LoanStatus.ORDERS_STATUS_FAIL.getDescription());
                }
            }
        } else {
            res.setResultCode(Constants.ORDER_TRANS_CODE_PROCESS);
            res.setResultMsg(LoanStatus.LOAN_STATUS_PAYING.getDescription());
        }
        res.setFinishTime(lnLoan.getLoanTime());

        //获取出借人列表
        List<LnLoanRelationVO> loanRelList = loanRelationMapper.selectLendersByLoanId(lnLoan.getId());
        ArrayList<HashMap<String, Object>> lenders = new ArrayList<>();
        for(LnLoanRelationVO lnLoanRelation: loanRelList){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("investAmount", lnLoanRelation.getApproveAmount());
            map.put("lenderName", lnLoanRelation.getUserName());
            map.put("lenderIdcard",lnLoanRelation.getIdCard());
            map.put("mobile",lnLoanRelation.getMobile());
            lenders.add(map);
        }
        res.setLenders(lenders);

    }

    @Override
    public void borrowerDFWithdraw(DFResultInfo req) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
            log.info("借款人代付提现通知开始：{}", JSONObject.fromObject(req));
            // 检查是否是否重复通知（根据订单信息是否是处理中，如果不是，则已经处理过，拒绝再次处理）
            String orderNo = req.getMxOrderId();

            //查询LnPayOrders表是否存在提现记录
            LnPayOrdersExample lnPayOrdersExample = new LnPayOrdersExample();
            lnPayOrdersExample.createCriteria().andOrderNoEqualTo(orderNo).andStatusEqualTo(Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()));
            List<LnPayOrders> lnPayOrders = payOrdersMapper.selectByExample(lnPayOrdersExample);
            if(CollectionUtils.isEmpty(lnPayOrders)){
                // 没有发生过这个交易，记录特殊流水表
                specialJnlService.warn4FailNoSMS(-1.0,
                        "【借款人提现通知异常】用户编号[未知]存管通知了一个未知的订单号：" + orderNo, orderNo,
                        "用户提现通知异常");
                return;
            }

            //发生过借款人代付提现，做处理
            PartnerEnum partnerEnum = PartnerEnum.getEnumByCode(lnPayOrders.get(0).getPartnerCode());
            if(partnerEnum == null) {
                throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "未找到合作方编码");
            }
            if (OrderStatus.SUCCESS.getCode().equals(req.getOrderStatus())) {
                G2BReqMsg_HFBank_OutOfAccount succReq = new G2BReqMsg_HFBank_OutOfAccount();
                succReq.setOrder_no(orderNo);
                succReq.setOrder_status(Constants.HF_OUT_OF_ACCOUNT_SUCCESS);
                if(Constants.PROPERTY_SYMBOL_ZAN.equals(lnPayOrders.get(0).getPartnerCode())) {//赞分期
                    log.info("赞分期标的出账回调重发订单号=["+orderNo+"]通知更新");
                    loanPaymentService.outOfAccountResultAcceptZan(succReq, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF);
                } else{//云贷/7贷==
                    log.info("标的出账回调重发订单号=["+orderNo+"]通知更新");
                    this.outOfAccountResultAccept(succReq, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF, partnerEnum);
                }
            } else {
                G2BReqMsg_HFBank_OutOfAccount failReq = new G2BReqMsg_HFBank_OutOfAccount();
                failReq.setOrder_no(orderNo);
                failReq.setOrder_status(Constants.HF_OUT_OF_ACCOUNT_FAIL);
                failReq.setError_info("代付提现失败");
                if(PartnerEnum.ZAN.getCode().equals(lnPayOrders.get(0).getPartnerCode())) {
                    loanPaymentService.outOfAccountResultAcceptZan(failReq, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE);
                } else {
                    this.outOfAccountResultAccept(failReq, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE, partnerEnum);
                }
            }
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_WITHDRAW_SERVICE.getKey());
        }
    }

    @Override
    public void noticeLoan2Dsd(final LnLoan lnLoan, final String errorMsg) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                B2GReqMsg_ZsdNotice_NoticeLoan noticeLoan = new B2GReqMsg_ZsdNotice_NoticeLoan();
                noticeLoan.setOrderNo(lnLoan.getPartnerOrderNo());
                noticeLoan.setLoanId(lnLoan.getPartnerLoanId());
                noticeLoan.setChannel(Constants.CHANNEL_BAOFOO); // 资金通道目前为宝付
                noticeLoan.setPayChannel(Constants.CHANNEL_HFBANK);
                noticeLoan.setLoanResultCode(lnLoan.getStatus().equals(LoanStatus.LOAN_STATUS_PAIED.getCode()) ? "SUCCESS" : "FAIL");
                noticeLoan.setLoanResultMsg(errorMsg);
                noticeLoan.setLoanTime(lnLoan.getLoanTime() != null ? DateUtil.format(lnLoan.getLoanTime(), "yyyy-MM-dd HH:mm:ss") : null);

                B2GResMsg_ZsdNotice_NoticeLoan res = null;
                LnLoan loanTemp = new LnLoan();
                loanTemp.setId(lnLoan.getId());
                try {
                    res = zsdNoticeService.noticeLoan(noticeLoan);
                    if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                        loanTemp.setUpdateTime(new Date());
                        loanTemp.setInformStatus(LoanStatus.NOTICE_STATUS_SUCCESS.getCode());
                    } else {
                        loanTemp.setInformStatus(LoanStatus.NOTICE_STATUS_FAIL.getCode());
                        loanTemp.setUpdateTime(new Date());
                        lnLoanMapper.updateByPrimaryKeySelective(loanTemp);
                        throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
                    }
                } catch (Exception e) {
                    loanTemp.setInformStatus(LoanStatus.NOTICE_STATUS_FAIL.getCode());
                    loanTemp.setUpdateTime(new Date());
                    e.printStackTrace();
                } finally{
                    log.info("更新通知状态为SUCCESS/FAIL");
                    lnLoanMapper.updateByPrimaryKeySelective(loanTemp);
                }

            }
        }).start();

    }

    @Override
    public void zsdLoanResultQuery(G2BReqMsg_ZsdLoanApply_QueryLoan req, G2BResMsg_ZsdLoanApply_QueryLoan res) {

        LnLoan lnLoan = lnLoanMapper.selectByParOrdNoAndParCode(req.getOrderNo(),PartnerEnum.ZSD.getCode());
        if(lnLoan == null){
            res.setChannel(Constants.CHANNEL_HFBANK);
            res.setLoanResultCode(Constants.ORDER_TRANS_CODE_PROCESS);
            res.setLoanResultMsg("赞时贷放款结果查询未找到订单号:" + req.getOrderNo() + "对应借款信息");
            return;
        }
        if(lnLoan.getStatus().equals(LoanStatus.LOAN_STATUS_PAIED.getCode()) ||
        	lnLoan.getStatus().equals(LoanStatus.LOAN_STATUS_LATE.getCode()) ||
        	lnLoan.getStatus().equals(LoanStatus.LOAN_STATUS_BAD.getCode())) {
            res.setLoanResultCode(Constants.ORDER_TRANS_CODE_SUCCESS);
        }else if(lnLoan.getStatus().equals(LoanStatus.LOAN_STATUS_FAIL.getCode())){
            res.setLoanResultCode(Constants.ORDER_TRANS_CODE_FAIL);
            LnPayOrdersExample payOrdersExample = new LnPayOrdersExample();
            payOrdersExample.createCriteria().andOrderNoEqualTo(lnLoan.getPayOrderNo());
            List<LnPayOrders> payOrdersList = payOrdersMapper.selectByExample(payOrdersExample);
            if(CollectionUtils.isEmpty(payOrdersList)){
                res.setChannel(Constants.CHANNEL_HFBANK);
                res.setLoanResultMsg("赞时贷放款结果查询未找到订单号:"+ req.getOrderNo() +"对应订单信息");
                return;
            }
            res.setLoanResultMsg(StringUtils.isBlank(payOrdersList.get(0).getReturnMsg()) ? LoanStatus.LOAN_STATUS_FAIL.getDescription() : payOrdersList.get(0).getReturnMsg());

        }else{
            res.setLoanResultCode(Constants.ORDER_TRANS_CODE_PROCESS);
        }

        res.setChannel(Constants.CHANNEL_HFBANK);
        res.setLoanId(lnLoan.getPartnerLoanId());
    }

    @Override
    public void noticeLoan2Seven(final LnLoan lnLoan, final String errorMsg, final String agreementNo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                B2GReqMsg_DepLoan7Notice_LoanResultNotice noticeLoan = new B2GReqMsg_DepLoan7Notice_LoanResultNotice();
                noticeLoan.setOrderNo(lnLoan.getPartnerOrderNo());
                noticeLoan.setBgwOrderNo(lnLoan.getPayOrderNo());
                noticeLoan.setAgreementNo(agreementNo);

                // 7贷获取借款服务费率（100=1%）
                BsSysConfig loanServiceRateConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.LOAN_SERVICE_RATE_SEVEN_DAI);
                noticeLoan.setLoanServiceRate(StringUtils.isNotBlank(loanServiceRateConfig.getConfValue()) ? MoneyUtil.formatEnlarge(loanServiceRateConfig.getConfValue()).intValue() : null);

                noticeLoan.setLoanId(lnLoan.getPartnerLoanId());
                noticeLoan.setPayChannel(Constants.CHANNEL_HFBANK);
                noticeLoan.setResultCode(lnLoan.getStatus().equals(LoanStatus.LOAN_STATUS_PAIED.getCode()) ? "SUCCESS" : "FAIL");
                noticeLoan.setResultMsg(errorMsg);
                noticeLoan.setFinishTime(lnLoan.getLoanTime() != null ? lnLoan.getLoanTime() : null);
                //获取出借人列表
                List<LnLoanRelationVO> loanRelList = loanRelationMapper.selectLendersByLoanId(lnLoan.getId());
                ArrayList<HashMap<String, Object>> lenders = new ArrayList<HashMap<String, Object>>();
                for(LnLoanRelationVO lnLoanRelation: loanRelList){
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("investAmount", lnLoanRelation.getApproveAmount());
                    map.put("lenderName", lnLoanRelation.getUserName());
                    map.put("lenderIdcard",lnLoanRelation.getIdCard());
                    map.put("mobile",lnLoanRelation.getMobile());
                    lenders.add(map);
                }
                noticeLoan.setLenders(lenders);
                B2GResMsg_DepLoan7Notice_LoanResultNotice res = null;
                LnLoan loanTemp = new LnLoan();
                loanTemp.setId(lnLoan.getId());
                try {
                    res = depLoan7NoticeService.noticeLoan(noticeLoan);
                    if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                        loanTemp.setUpdateTime(new Date());
                        loanTemp.setInformStatus(LoanStatus.NOTICE_STATUS_SUCCESS.getCode());
                    } else {
                        loanTemp.setInformStatus(LoanStatus.NOTICE_STATUS_FAIL.getCode());
                        loanTemp.setUpdateTime(new Date());
                        lnLoanMapper.updateByPrimaryKeySelective(loanTemp);
                        throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
                    }
                } catch (Exception e) {
                    loanTemp.setInformStatus(LoanStatus.NOTICE_STATUS_FAIL.getCode());
                    loanTemp.setUpdateTime(new Date());
                    e.printStackTrace();
                } finally{
                    log.info("更新通知状态为SUCCESS/FAIL");
                    lnLoanMapper.updateByPrimaryKeySelective(loanTemp);
                }
            }
        }).start();
    }

    /**
     * 修改订单表，新增订单流水表公共方法
     * @param isSuc
     * @param lnPayOrder
     * @param lnLoan
     * @param lnBindCard
     */
    public void updateOrderStatus(Boolean isSuc, LnPayOrders lnPayOrder, LnLoan lnLoan,LnBindCard lnBindCard){
        //修改订单状态为成功或失败
        LnPayOrders payOrderUpdate = new LnPayOrders();
        payOrderUpdate.setId(lnPayOrder.getId());
        if(isSuc){
            payOrderUpdate.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
        }else {
            payOrderUpdate.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
        }
        payOrderUpdate.setUpdateTime(new Date());
        lnPayOrdersMapper.updateByPrimaryKeySelective(payOrderUpdate);

        //新增一条ln_pay_orders_jnl的SUCCESS或FAIL记录
        LnPayOrdersJnl payOrdersJnlSuc = new LnPayOrdersJnl();
        payOrdersJnlSuc.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
        payOrdersJnlSuc.setCreateTime(new Date());
        payOrdersJnlSuc.setOrderId(lnPayOrder.getId());
        payOrdersJnlSuc.setOrderNo(lnPayOrder.getOrderNo());
        payOrdersJnlSuc.setTransAmount(lnLoan.getApproveAmount());
        if(isSuc){
            payOrdersJnlSuc.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
        }else {
            payOrdersJnlSuc.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
        }
        payOrdersJnlSuc.setUserId(lnBindCard.getLnUserId());
        payOrdersJnlSuc.setSysTime(new Date());
        payOrdersJnlMapper.insertSelective(payOrdersJnlSuc);
    }


    //list转换
    private List<BatchExtBuyReqData> getDataList(
            List<LoanRelation4DepVO> loanRelationList) {
        List<BatchExtBuyReqData> dataList = new ArrayList<BatchExtBuyReqData>();
        for (LoanRelation4DepVO loanRelation4DepVO : loanRelationList) {
            BatchExtBuyReqData data = new BatchExtBuyReqData();
            data.setDetail_no(Util.generateOrderNo4Pay19(loanRelation4DepVO.getLnLoanRelation().getId()));
            data.setPlatcust(loanRelation4DepVO.getHfUserId());
            data.setTrans_amt(loanRelation4DepVO.getLnLoanRelation().getInitAmount());
            data.setExperience_amt(0d);
            data.setCoupon_amt(loanRelation4DepVO.getCouponAmount());
            data.setSelf_amt(loanRelation4DepVO.getSelfAmount());
            data.setIn_interest(0d);
            data.setSubject_priority(Constants.SUBJECT_PRIORITY_0);
            dataList.add(data);
        }
        return dataList;
    }


	@Override
	public void doRefundTicket(final LnLoan lnLoan) {
		LnPayOrdersExample payOrdersExample = new LnPayOrdersExample();
		payOrdersExample.createCriteria().andOrderNoEqualTo(lnLoan.getPayOrderNo());
		final List<LnPayOrders> orderList = lnPayOrdersMapper.selectByExample(payOrdersExample);
		if(CollectionUtils.isEmpty(orderList)){
    	    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, ", 未找到order_no=" + lnLoan.getPayOrderNo() + "对应的标的出账订单信息");
        }
		LnUserExample lnUserExample = new LnUserExample();
    	lnUserExample.createCriteria().andIdEqualTo(lnLoan.getLnUserId());
    	final List<LnUser> lnUserList = lnUserMapper.selectByExample(lnUserExample);
    	if (CollectionUtils.isEmpty(lnUserList)) {
    	    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, ", 未找到ln_user_id=" + lnLoan.getLnUserId() + "对应的借款用户信息");
    	}
    	final PartnerEnum partnerEnum = PartnerEnum.getEnumByCode(orderList.get(0).getPartnerCode());
        if(partnerEnum == null) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "未找到合作方编码");
        }
    	LnLoanRelationExample relationExample = new LnLoanRelationExample();
		relationExample.createCriteria().andLoanIdEqualTo(lnLoan.getId());
		final List<LnLoanRelation> loanRelationList = lnLoanRelationMapper.selectByExample(relationExample);
        if (CollectionUtils.isEmpty(loanRelationList)) {
        	throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "未找到load_id=" + lnLoan.getId() + "对应的借贷关系信息");
        }

        // 通知资产方标的放款失败
        ResMsg resMsg = new ResMsg();

        try {
            resMsg = notifyLoan2Partner(partnerEnum, lnLoan, "标的放款失败");
        } catch (Exception e) {
            log.error("放款结果通知通讯失败：{}", e.getMessage());
            resMsg.setResCode(Constants.GATEWAY_PAYING_RES_CODE);
            resMsg.setResMsg("放款结果通知失败");
        }

        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
            // 通知资产方放款失败接口响应，资产方受理成功，执行退票功能
            log.info("通知资产方放款失败接口响应（成功）：{}", JSONObject.fromObject(resMsg));

            transactionTemplate.execute(new TransactionCallbackWithoutResult(){
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    log.info("管理台执行退票功能事务start，load_id：{}", lnLoan.getId());
                    LnPayOrders lnPayOrders = orderList.get(0);
                    LnUser lnUser = lnUserList.get(0);
                    // 订单更新为支付处理中
                    LnPayOrders orderUpdate = new LnPayOrders();
                    orderUpdate.setId(lnPayOrders.getId());
                    orderUpdate.setUpdateTime(new Date());
                    orderUpdate.setStatus(Constants.ORDER_STATUS_PAYING);
                    lnPayOrdersMapper.updateByPrimaryKeySelective(orderUpdate);
                    // 调用标的出账回调通知
                    G2BReqMsg_HFBank_OutOfAccount req = new G2BReqMsg_HFBank_OutOfAccount();
                    req.setOrder_no(lnPayOrders.getOrderNo());
                    req.setOrder_status(Constants.HF_OUT_OF_ACCOUNT_SUCCESS);
                    req.setPay_path(null);

                    if(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(lnPayOrders.getPartnerCode()) ||
                            Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(lnPayOrders.getPartnerCode())) {			//云贷/7贷
                        log.info(partnerEnum.getName() + "标的出账订单号=[" + lnPayOrders.getOrderNo() + "]通知更新");
                        outOfAccountResultAccept(req, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF, partnerEnum, false);
                    }

                    // 执行退票脚本
                    // 存管还款计划
                    Date planDate = null;
                    try {
                        planDate = DateUtil.strToDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
                    } catch (Exception e) {
                        log.error("执行退票时时间格式化异常：{}", e.getMessage());
                        return;
                    }
                    LnDepositionRepaySchedule depositionRepaySchedule = new LnDepositionRepaySchedule();
                    depositionRepaySchedule.setLnUserId(lnUser.getId());
                    depositionRepaySchedule.setLoanId(lnLoan.getId());
                    depositionRepaySchedule.setPartnerRepayId(Constants.REFUND_TICKET_REPAY_PREFIX + lnLoan.getId());
                    depositionRepaySchedule.setSerialId(1);
                    depositionRepaySchedule.setPlanDate(planDate);
                    depositionRepaySchedule.setPlanTotal(lnLoan.getApproveAmount());
                    depositionRepaySchedule.setStatus(Constants.LN_REPAY_STATUS_INIT);
                    depositionRepaySchedule.setReturnFlag("DF_SUCC");
                    depositionRepaySchedule.setRepayType(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_NORMAL_REPAY);
                    depositionRepaySchedule.setCreateTime(new Date());
                    depositionRepaySchedule.setUpdateTime(new Date());
                    lnDepositionRepayScheduleMapper.insertSelective(depositionRepaySchedule);

                    // 存管还款计划详细-本金
                    LnDepositionRepayScheduleDetail principalDepositionDetail = new LnDepositionRepayScheduleDetail();
                    principalDepositionDetail.setPlanId(depositionRepaySchedule.getId());
                    principalDepositionDetail.setSubjectCode(Constants.SUBJECT_PRINCIPAL);
                    principalDepositionDetail.setPlanAmount(lnLoan.getApproveAmount());
                    principalDepositionDetail.setCreateTime(new Date());
                    principalDepositionDetail.setUpdateTime(new Date());
                    lnDepositionRepayScheduleDetailMapper.insertSelective(principalDepositionDetail);
                    // 存管还款计划详细-利息
                    LnDepositionRepayScheduleDetail interestDepositionDetail = new LnDepositionRepayScheduleDetail();
                    interestDepositionDetail.setPlanId(depositionRepaySchedule.getId());
                    interestDepositionDetail.setSubjectCode(Constants.SUBJECT_INTEREST);
                    interestDepositionDetail.setPlanAmount(0d);
                    interestDepositionDetail.setCreateTime(new Date());
                    interestDepositionDetail.setUpdateTime(new Date());
                    lnDepositionRepayScheduleDetailMapper.insertSelective(interestDepositionDetail);

                    LnRepaySchedule repayScheduleUpd = new LnRepaySchedule();
                    repayScheduleUpd.setStatus(Constants.LN_REPAY_CANCELLED);
                    LnRepayScheduleExample repayScheduleExample = new LnRepayScheduleExample();
                    repayScheduleExample.createCriteria().andLoanIdEqualTo(lnLoan.getId());
                    lnRepayScheduleMapper.updateByExampleSelective(repayScheduleUpd, repayScheduleExample);

                    // 账单
                    LnRepaySchedule newRepaySchedule = new LnRepaySchedule();
                    newRepaySchedule.setLoanId(lnLoan.getId());
                    newRepaySchedule.setPartnerRepayId(Constants.REFUND_TICKET_REPAY_PREFIX + lnLoan.getId());
                    newRepaySchedule.setSerialId(1);
                    newRepaySchedule.setPlanDate(planDate);
                    newRepaySchedule.setFinishTime(null);
                    newRepaySchedule.setPlanTotal(lnLoan.getApproveAmount());
                    newRepaySchedule.setStatus(Constants.LN_REPAY_REPAIED);
                    newRepaySchedule.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_INIT);
                    newRepaySchedule.setPayOrderNo(null);
                    newRepaySchedule.setCreateTime(new Date());
                    newRepaySchedule.setUpdateTime(new Date());
                    lnRepayScheduleMapper.insertSelective(newRepaySchedule);

                    // 账单明细
                    // 账单明细-本金
                    LnRepayScheduleDetail principalRepayScheduleDetail = new LnRepayScheduleDetail();
                    principalRepayScheduleDetail.setPlanId(newRepaySchedule.getId());
                    principalRepayScheduleDetail.setPlanAmount(lnLoan.getApproveAmount());
                    principalRepayScheduleDetail.setSubjectCode(Constants.SUBJECT_PRINCIPAL);
                    principalRepayScheduleDetail.setCreateTime(new Date());
                    principalRepayScheduleDetail.setUpdateTime(new Date());
                    lnRepayScheduleDetailMapper.insertSelective(principalRepayScheduleDetail);

                    // 账单明细-利息
                    LnRepayScheduleDetail interestRepayScheduleDetail = new LnRepayScheduleDetail();
                    interestRepayScheduleDetail.setPlanId(newRepaySchedule.getId());
                    interestRepayScheduleDetail.setPlanAmount(0d);
                    interestRepayScheduleDetail.setSubjectCode(Constants.SUBJECT_INTEREST);
                    interestRepayScheduleDetail.setCreateTime(new Date());
                    interestRepayScheduleDetail.setUpdateTime(new Date());
                    lnRepayScheduleDetailMapper.insertSelective(interestRepayScheduleDetail);

                    // 账单明细-逾期本金
                    LnRepayScheduleDetail principalOverdueRepayScheduleDetail = new LnRepayScheduleDetail();
                    principalOverdueRepayScheduleDetail.setPlanId(newRepaySchedule.getId());
                    principalOverdueRepayScheduleDetail.setPlanAmount(0d);
                    principalOverdueRepayScheduleDetail.setSubjectCode(Constants.SUBJECT_PRINCIPAL_OVERDUE);
                    principalOverdueRepayScheduleDetail.setCreateTime(new Date());
                    principalOverdueRepayScheduleDetail.setUpdateTime(new Date());
                    lnRepayScheduleDetailMapper.insertSelective(principalOverdueRepayScheduleDetail);

                    // 账单明细-逾期利息
                    LnRepayScheduleDetail interestOverdueRepayScheduleDetail = new LnRepayScheduleDetail();
                    interestOverdueRepayScheduleDetail.setPlanId(newRepaySchedule.getId());
                    interestOverdueRepayScheduleDetail.setPlanAmount(0d);
                    interestOverdueRepayScheduleDetail.setSubjectCode(Constants.SUBJECT_INTEREST_OVERDUE);
                    interestOverdueRepayScheduleDetail.setCreateTime(new Date());
                    interestOverdueRepayScheduleDetail.setUpdateTime(new Date());
                    lnRepayScheduleDetailMapper.insertSelective(interestOverdueRepayScheduleDetail);

                    // 理财人还款计划
                    for (LnLoanRelation lnLoanRelation : loanRelationList) {
                        LnFinanceRepaySchedule newFinanceRepaySchedule = new LnFinanceRepaySchedule();
                        newFinanceRepaySchedule.setRelationId(lnLoanRelation.getId());
                        newFinanceRepaySchedule.setRepaySerial(1);
                        newFinanceRepaySchedule.setPlanDate(planDate);
                        newFinanceRepaySchedule.setPlanTotal(lnLoanRelation.getLeftAmount());
                        newFinanceRepaySchedule.setPlanPrincipal(lnLoanRelation.getLeftAmount());
                        newFinanceRepaySchedule.setPlanInterest(0d);
                        newFinanceRepaySchedule.setPlanFee(0d);
                        newFinanceRepaySchedule.setStatus(Constants.FINANCE_REPAY_SATAE_REPAYING);
                        newFinanceRepaySchedule.setCreateTime(new Date());
                        newFinanceRepaySchedule.setUpdateTime(new Date());
                        lnFinanceRepayScheduleMapper.insertSelective(newFinanceRepaySchedule);
                    }

                    // 退票的借款人信息入黑名单，根据手机号和合作方查询是否存在
                    LnLoanBlackExample lnLoanBlackExample = new LnLoanBlackExample();
                    lnLoanBlackExample.createCriteria().andMobileEqualTo(lnUser.getMobile())
                            .andUsernameLike(partnerEnum.getCode() + "%");
                    List<LnLoanBlack> lnLoanBlackList = lnLoanBlackMapper.selectByExample(lnLoanBlackExample);
                    if (CollectionUtils.isEmpty(lnLoanBlackList)) {
                        LnLoanBlack loanBlack = new LnLoanBlack();
                        loanBlack.setMobile(lnUser.getMobile());
                        loanBlack.setUsername(partnerEnum.getCode() + lnUser.getUserName());
                        lnLoanBlackMapper.insertSelective(loanBlack);
                    }
                    log.info("管理台执行退票功能事务end，load_id：{}", lnLoan.getId());
                }
            });
        } else if(Constants.GATEWAY_PAYING_RES_CODE.equals(resMsg.getResCode())) {
            // 通知资产方放款失败接口响应，此异常表示通讯超时。
            log.info("通知资产方放款失败接口响应（超时）：{}", JSONObject.fromObject(resMsg));

            // 记录告警流水，告警发送运营/产品
            String detail = "["+ partnerEnum.getName() +"]管理台退票功能，标的放款结果通知受理超时(订单号["+ lnLoan.getPayOrderNo() +"])";
            specialJnlService.warnProductOperator4Fail(0d, detail, lnLoan.getPayOrderNo(),"【" + partnerEnum.getName() + "管理台退票功能】", false);

            throw new PTMessageException(PTMessageEnum.CONNECTION_TIME_OUT, resMsg.getResMsg());
        } else {
            // 通知资产方放款失败接口响应失败，资产方受理失败，界面提示。
            log.info("通知资产方放款失败接口响应（失败）：{}", JSONObject.fromObject(resMsg));

            // 记录告警流水，告警发送运营/产品
            String detail = "["+ partnerEnum.getName() +"]管理台退票功能，标的放款结果通知受理失败(订单号["+ lnLoan.getPayOrderNo() +"])";
            specialJnlService.warnProductOperator4Fail(0d, detail, lnLoan.getPayOrderNo(),"【" + partnerEnum.getName() + "管理台退票功能】", false);

            throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR, resMsg.getResMsg());
        }
	}

    /**
     * 放款通知合作方分发——同步方法
     *
     * @param partnerEnum
     * @param lnLoan
     * @param errorMsg
     */
    private ResMsg notifyLoan2Partner(PartnerEnum partnerEnum, LnLoan lnLoan, String errorMsg) {
        switch (partnerEnum) {
            case YUN_DAI_SELF :
                return notifyLoan2YunDai(lnLoan, errorMsg);
            case SEVEN_DAI_SELF :
                return noticeLoan2Seven(lnLoan, errorMsg);
        }

        throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "资产合作方非云贷非7贷");
    }


    /**
     * 放款通知云贷——同步方法
     *
     * @param lnLoan
     * @param errorMsg
     */
    private ResMsg notifyLoan2YunDai(final LnLoan lnLoan, final String errorMsg) {

        B2GReqMsg_DafyLoanNotice_LoanResultNotice noticeLoan = new B2GReqMsg_DafyLoanNotice_LoanResultNotice();
        noticeLoan.setOrderNo(lnLoan.getPartnerOrderNo());
        noticeLoan.setPayChannel(Constants.CHANNEL_HFBANK);
        noticeLoan.setChannel(Constants.CHANNEL_DAFY);
        noticeLoan.setBgwOrderNo(lnLoan.getPayOrderNo());
        noticeLoan.setAgreementNo(null);

        // 云贷获取借款服务费率（100=1%）
        BsSysConfig loanServiceRateConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.LOAN_SERVICE_RATE_YUN_DAI);
        noticeLoan.setLoanServiceRate(StringUtils.isNotBlank(loanServiceRateConfig.getConfValue()) ? MoneyUtil.formatEnlarge(loanServiceRateConfig.getConfValue()).intValue() : null);

        noticeLoan.setLoanId(lnLoan.getPartnerLoanId());
        noticeLoan.setResultCode(lnLoan.getStatus().equals(LoanStatus.LOAN_STATUS_PAIED.getCode()) ? "SUCCESS" : "FAIL");
        noticeLoan.setResultMsg(errorMsg);
        noticeLoan.setFinishTime(lnLoan.getLoanTime() != null ? lnLoan.getLoanTime() : null);
        //获取出借人列表
        List<LnLoanRelationVO> loanRelList = loanRelationMapper.selectLendersByLoanId(lnLoan.getId());

        ArrayList<HashMap<String, Object>> lenders = new ArrayList<HashMap<String, Object>>();

        for(LnLoanRelationVO lnLoanRelation: loanRelList) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("investAmount", lnLoanRelation.getApproveAmount());
            map.put("lenderName", lnLoanRelation.getUserName());
            map.put("lenderIdcard",lnLoanRelation.getIdCard());
            map.put("mobile",lnLoanRelation.getMobile());
            lenders.add(map);
        }
        noticeLoan.setLenders(lenders);

        return dafyNoticeService.noticeLoan(noticeLoan);
    }

    /**
     * 放款通知7贷——同步方法
     *
     * @param lnLoan
     * @param errorMsg
     */
    private ResMsg noticeLoan2Seven(final LnLoan lnLoan, final String errorMsg) {

        B2GReqMsg_DepLoan7Notice_LoanResultNotice noticeLoan = new B2GReqMsg_DepLoan7Notice_LoanResultNotice();
        noticeLoan.setOrderNo(lnLoan.getPartnerOrderNo());
        noticeLoan.setBgwOrderNo(lnLoan.getPayOrderNo());
        noticeLoan.setAgreementNo(null);

        // 7贷获取借款服务费率（100=1%）
        BsSysConfig loanServiceRateConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.LOAN_SERVICE_RATE_SEVEN_DAI);
        noticeLoan.setLoanServiceRate(StringUtils.isNotBlank(loanServiceRateConfig.getConfValue()) ? MoneyUtil.formatEnlarge(loanServiceRateConfig.getConfValue()).intValue() : null);

        noticeLoan.setLoanId(lnLoan.getPartnerLoanId());
        noticeLoan.setPayChannel(Constants.CHANNEL_HFBANK);
        noticeLoan.setResultCode(lnLoan.getStatus().equals(LoanStatus.LOAN_STATUS_PAIED.getCode()) ? "SUCCESS" : "FAIL");
        noticeLoan.setResultMsg(errorMsg);
        noticeLoan.setFinishTime(lnLoan.getLoanTime() != null ? lnLoan.getLoanTime() : null);
        //获取出借人列表
        List<LnLoanRelationVO> loanRelList = loanRelationMapper.selectLendersByLoanId(lnLoan.getId());
        ArrayList<HashMap<String, Object>> lenders = new ArrayList<HashMap<String, Object>>();
        for(LnLoanRelationVO lnLoanRelation: loanRelList){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("investAmount", lnLoanRelation.getApproveAmount());
            map.put("lenderName", lnLoanRelation.getUserName());
            map.put("lenderIdcard",lnLoanRelation.getIdCard());
            map.put("mobile",lnLoanRelation.getMobile());
            lenders.add(map);
        }
        noticeLoan.setLenders(lenders);

        return depLoan7NoticeService.noticeLoan(noticeLoan);
    }

    @Override
    public void backLoanDebtFinancing(final LnLoan lnLoan) {

        LnUserExample lnUserExample = new LnUserExample();
        lnUserExample.createCriteria().andIdEqualTo(lnLoan.getLnUserId());
        final List<LnUser> lnUserList = lnUserMapper.selectByExample(lnUserExample);
        if (CollectionUtils.isEmpty(lnUserList)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR.getCode(), ", 未找到ln_user_id=" + lnLoan.getLnUserId() + "对应的借款用户信息");
        }

        // 非标的发布，非标的投标，非标的废除状态，不能执行债权回退
        LnDepositionTarget lnDepositionTarget = lnDepositionTargetMapper.selectByLoanId(lnLoan.getId());
        if (!(Constants.DEP_TARGET_PUBLISH.equals(lnDepositionTarget.getStatus())
                || Constants.DEP_TARGET_BID.equals(lnDepositionTarget.getStatus())
                || Constants.DEP_TARGET_CANCELLED.equals(lnDepositionTarget.getStatus()))) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "标的状态错误");
        }

        // 标的成功流水
        LnDepositionTargetJnlExample example = new LnDepositionTargetJnlExample();
        example.createCriteria().andProdIdEqualTo(lnDepositionTarget.getId());
        List<LnDepositionTargetJnl> list = depositionTargetJnlMapper.selectByExample(example);
        LnDepositionTargetJnl lnDepositionTargetJnlSucc = null;
        LnDepositionTargetJnl lnDepositionTargetJnlFail = null;
        for (LnDepositionTargetJnl info : list) {
            if (Constants.DEP_TARGET_OPERATE_SUCC.equals(info.getTransStatus())) {
                lnDepositionTargetJnlSucc = info;
            } else if (Constants.DEP_TARGET_OPERATE_FAIL.equals(info.getTransStatus())) {
                lnDepositionTargetJnlFail = info;
            }
        }

        if (lnDepositionTargetJnlSucc == null) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "标的缺失成功流水信息");
        }

        // 标的流水应该处在发布成功状态
        if (!((Constants.DEP_TARGET_PUBLISH.equals(lnDepositionTargetJnlSucc.getProdStatus())
                && DepTargetEnum.DEP_TARGET_OPERATE_PUBLISH.getCode().equals(lnDepositionTargetJnlSucc.getTransType())
                && Constants.DEP_TARGET_OPERATE_SUCC.equals(lnDepositionTargetJnlSucc.getTransStatus()))
                || (Constants.DEP_TARGET_BID.equals(lnDepositionTargetJnlSucc.getProdStatus())
                && DepTargetEnum.DEP_TARGET_OPERATE_BID.getCode().equals(lnDepositionTargetJnlSucc.getTransType())
                && Constants.DEP_TARGET_OPERATE_SUCC.equals(lnDepositionTargetJnlSucc.getTransStatus())))) {
            log.info("标的操作成功流水必须处于发布成功或投标成功状态，当前状态类型：" + lnDepositionTargetJnlSucc.getTransType() +"，结果：" + lnDepositionTargetJnlSucc.getTransStatus());
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "标的流水状态错误");
        }

        // 债权回退：标的发布成功状态停留时间（小时）
        int residenceTime = 6; // 默认处理滞留6小时数据
        BsSysConfig bsSysConfig = sysConfigService.findConfigByKey(Constants.LOAN_DEBT_FINANCING_BACK_RESIDENCE_TIME);

        if (bsSysConfig != null) {
            try {
                residenceTime = Integer.valueOf(bsSysConfig.getConfValue());
            } catch (Exception e) {
                e.printStackTrace();
                log.error("配置中LOAN_DEBT_FINANCING_BACK键值内容错误："+ com.alibaba.fastjson.JSONObject.toJSONString(bsSysConfig));
            }
        }

        if (com.pinting.core.util.DateUtil.getDiffeHour(null, lnDepositionTargetJnlSucc.getCreateTime()) < residenceTime) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "标的发布状态更新时间不超过"+ residenceTime +"小时");
        }

        // 调用恒丰接口（标的账户余额查询），查询恒丰标的账户信息
        String prod_id = lnDepositionTarget.getId().toString();
        B2GReqMsg_HFBank_QueryProductBalance req = new B2GReqMsg_HFBank_QueryProductBalance();
        req.setProd_id(prod_id);
        B2GResMsg_HFBank_QueryProductBalance resMsg = new B2GResMsg_HFBank_QueryProductBalance();
        try {
            resMsg = hfbankTransportService.queryProductBalance(req);
        } catch (Exception e) {
            log.error("标的账户余额查询结果通知通讯失败：{}", e.getMessage());
            resMsg.setResCode(Constants.GATEWAY_PAYING_RES_CODE);
            resMsg.setResMsg("标的账户余额查询结果通知失败");
        }

        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
            log.info("查询恒丰标的账户信息接口响应（成功）：{}", JSONObject.fromObject(resMsg));
            Double prodAccount = Double.valueOf(resMsg.getData()); // 恒丰标的账户余额
            // 恒丰标的账户不存在 or 恒丰标的账户存在，且标的账户余额为0
            LnDepositionTargetJnl lnDepositionTargetJnlTemp = lnDepositionTargetJnlFail != null ? lnDepositionTargetJnlFail : lnDepositionTargetJnlSucc;
            if (prodAccount.compareTo(0d) == 0) {
                // 执行债权回退
                backLoanDebtFinancing(lnLoan, lnDepositionTargetJnlTemp.getOrderNo(), lnDepositionTarget, DepTargetEnum.DEP_TARGET_OPERATE_CANCELLED);
            } else {
                // 恒丰标的余额与本地标的金额一致，则确保投标已满标成功
                if (lnDepositionTarget.getTotalLimit().compareTo(prodAccount) == 0) {
                    String errorMsg = "借款编号"+ lnLoan.getId() +",已投标满标成功,不允许债权回退处理";
                    log.info(errorMsg);
                    throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), errorMsg);
                }

                if (lnDepositionTargetJnlFail == null || !(Constants.DEP_TARGET_CANCELLED.equals(lnDepositionTargetJnlFail.getProdStatus())
                        && Constants.TARGET_JNL_TRANS_STATUS_FAIL.equals(lnDepositionTargetJnlFail.getTransStatus()))) {

                    //新增一条ln_deposition_target_jnl废标失败记录流水
                    LnDepositionTargetJnl depositionTargetJnl = new LnDepositionTargetJnl();
                    depositionTargetJnl.setProdId(lnDepositionTarget.getId());
                    depositionTargetJnl.setOrderNo(lnDepositionTargetJnlTemp.getOrderNo());
                    depositionTargetJnl.setTransTime(new Date());
                    depositionTargetJnl.setTransType(DepTargetEnum.DEP_TARGET_OPERATE_CANCELLED.getCode());
                    depositionTargetJnl.setTransName(DepTargetEnum.DEP_TARGET_OPERATE_CANCELLED.getDescription());
                    depositionTargetJnl.setAmount(lnLoan.getApplyAmount());
                    depositionTargetJnl.setProdStatus(Constants.DEP_TARGET_CANCELLED);
                    depositionTargetJnl.setTransStatus(Constants.TARGET_JNL_TRANS_STATUS_FAIL);
                    depositionTargetJnl.setCreateTime(new Date());
                    depositionTargetJnl.setUpdateTime(new Date());
                    depositionTargetJnlMapper.insertSelective(depositionTargetJnl);
                }

                String errorMsg = "借款编号"+ lnLoan.getId() +",请先联系技术进行人工废标,再进行债权回退操作";
                log.info(errorMsg);
                throw new PTMessageException(PTMessageEnum.TRANS_ERROR, errorMsg);
            }
        } else if(Constants.GATEWAY_PAYING_RES_CODE.equals(resMsg.getResCode())) {
            log.info("查询恒丰标的账户信息接口响应（超时）：{}", JSONObject.fromObject(resMsg));
            throw new PTMessageException(PTMessageEnum.CONNECTION_TIME_OUT, resMsg.getResMsg());
        } else {
            log.info("查询恒丰标的账户信息接口响应（失败）：{}", JSONObject.fromObject(resMsg));
            throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR, resMsg.getResMsg());
        }
    }

    @Override
    public void backLoanDebtFinancing(final LnLoan lnLoan, final String orderNo, final LnDepositionTarget depositionTarget, final DepTargetEnum depTargetEnum) {
        LnLoanRelationExample relationExample = new LnLoanRelationExample();
        relationExample.createCriteria().andLoanIdEqualTo(lnLoan.getId()).andStatusEqualTo(Constants.LOAN_RELATION_STATUS_PAYING);
        final List<LnLoanRelation> loanRelationList = lnLoanRelationMapper.selectByExample(relationExample);
        double loanTotalAmount = 0d;
        for (LnLoanRelation loanRelation : loanRelationList) {
            loanTotalAmount = MoneyUtil.add(loanTotalAmount, loanRelation.getTotalAmount()).doubleValue();
        }
        if (loanTotalAmount > 0 && depositionTarget.getTotalLimit().compareTo(loanTotalAmount) != 0) {
            log.info("借款编号"+ lnLoan.getId() +",标的金额与债权总金额不匹配");
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "标的金额与债权总金额不匹配");
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                log.info("管理台执行标的债权回退功能事务start，load_id：{}", lnLoan.getId());

                /**
                 * 1、修改借款表信息，放款失败
                 * 2、存管标的表，标的操作流水表，废标成功
                 * 3、冻结资金解冻
                 * 4、ln_loan_relation表状态修改，借贷关系置为失败
                 */
                // 标的改为终态PAY_FAIL
                LnLoan loanTemp = new LnLoan();
                loanTemp.setId(lnLoan.getId());
                loanTemp.setUpdateTime(new Date());
                loanTemp.setStatus(LoanStatus.LOAN_STATUS_FAIL.getCode());
                lnLoanMapper.updateByPrimaryKeySelective(loanTemp);
                // 修改标的为CANCELLED，新增一条标的废除成功流水
                depCommonService.updateTargetStatus(true, Constants.DEP_TARGET_CANCELLED, depTargetEnum, depositionTarget, lnLoan, orderNo);

                for (LnLoanRelation loanRelation : loanRelationList) {
                    // 借贷关系处于PAYING, 回退冻结资金，设置借贷关系失败
                    BsSubAccountPairExample pairExample = new BsSubAccountPairExample();
                    pairExample.createCriteria().andAuthAccountIdEqualTo(loanRelation.getBsSubAccountId());
                    List<BsSubAccountPair> pairs = bsSubAccountPairMapper.selectByExample(pairExample);
                    if (CollectionUtils.isEmpty(pairs)) {
                        throw new PTMessageException(PTMessageEnum.ACCOUNT_NOT_FOUND);
                    }
                    double selfAmount = MoneyUtil.subtract(loanRelation.getTotalAmount(), loanRelation.getDiscountAmount()).doubleValue() <= 0 ? 0d : MoneyUtil.subtract(loanRelation.getTotalAmount(), loanRelation.getDiscountAmount()).doubleValue();
                    depFixedLoanAccountService.chargeLoanUnFreeze(selfAmount,
                            loanRelation.getBsSubAccountId(),
                            loanRelation.getDiscountAmount(),
                            pairs.get(0).getRedAccountId());

                    LnLoanRelation lnLoanRelationTemp = new LnLoanRelation();
                    lnLoanRelationTemp.setId(loanRelation.getId());
                    lnLoanRelationTemp.setStatus(Constants.LOAN_RELATION_STATUS_FAIL);
                    lnLoanRelationTemp.setUpdateTime(new Date());
                    loanRelationMapper.updateByPrimaryKeySelective(lnLoanRelationTemp);
                }

                log.info("管理台执行标的债权回退功能事务end，load_id：{}", lnLoan.getId());
            }
        });
    }


    /**
     * 插入借款申请记录
     */
    private void insertLoanApplyRecord(G2BReqMsg_DafyLoan_ApplyLoan req, PartnerEnum partnerEnum) {
        try {
            LnLoanApplyRecord loanApply = new LnLoanApplyRecord();
            loanApply.setApplyAmount(req.getLoanAmount());
            loanApply.setApplyTime(req.getApplyTime());
            loanApply.setBreakMaxDays(req.getBreakMaxDays());
            loanApply.setBreakTimes(req.getBreakTimes());
            loanApply.setHeadFee(req.getLoanFee());
            loanApply.setPartnerLoanId(req.getLoanId());
            loanApply.setCreateTime(new Date());
            loanApply.setUpdateTime(new Date());
            loanApply.setCreditAmount(req.getCreditAmount());
            loanApply.setLoanedAmount(req.getLoanAmount());
            loanApply.setCreditLevel(req.getCreditLevel());
            loanApply.setCreditScore(req.getCreditScore());
            loanApply.setPartnerUserId(req.getUserId());
            loanApply.setLoanTimes(req.getLoanTimes());
            loanApply.setPartnerBusinessFlag(req.getBusinessType());
            loanApply.setPartnerOrderNo(req.getOrderNo());
            if(partnerEnum.equals(PartnerEnum.YUN_DAI_SELF)) {
                Double period = Math.floor(req.getLoanTerm()/30);
                loanApply.setPeriod(period.intValue());
            }else{
                loanApply.setPeriod(req.getLoanTerm());
            }
            loanApply.setPurpose(req.getPurpose());
            loanApply.setSubjectName(req.getSubjectName());
            //借款协议利率
            Double agreementRate = MoneyUtil.divide(req.getLoanRate(), 100, 2).doubleValue();
            loanApply.setAgreementRate(agreementRate);
            //借款服务费利率
            try {
                if(partnerEnum.equals(PartnerEnum.YUN_DAI_SELF)) {
                    BsSysConfig loanServiceRateConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.LOAN_SERVICE_RATE_YUN_DAI);
                    loanApply.setLoanServiceRate(loanServiceRateConfig != null?Double.valueOf(loanServiceRateConfig.getConfValue()) : null);
                }else{
                    BsSysConfig loanServiceRateConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.LOAN_SERVICE_RATE_SEVEN_DAI);
                    loanApply.setLoanServiceRate(loanServiceRateConfig != null?Double.valueOf(loanServiceRateConfig.getConfValue()) : null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if(partnerEnum.equals(PartnerEnum.YUN_DAI_SELF)) {
                    //云贷借款---币港湾服务费利率
                    String settleRate4YunMapKey = DateUtil.format(new Date(), DateUtil.SIMPLE_DATE);//作为map的key
                    Double bgwSettleRate;
                    if(settleRate4YunMap.get(settleRate4YunMapKey) == null){
                        //map取不到当日的值，则先清空map,然后查询配置表，保存该值到map中
                        settleRate4YunMap.clear();
                        Double initRate = sysConfigService.findRatePercentByKey(Constants.YUN_DAI_SELF_SYS_SETTLE_RATE);
                        if(initRate != null){
                            settleRate4YunMap.put(settleRate4YunMapKey, initRate);
                        }
                    }
                    bgwSettleRate = settleRate4YunMap.get(settleRate4YunMapKey);
                    loanApply.setBgwSettleRate(bgwSettleRate);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            lnLoanApplyRecordMapper.insertSelective(loanApply);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 合作方借款编号是否已存在
     *
     * @param partnerLoanId
     * @return true 已存在， false 不存在
     */
    private boolean isExists4PartnerLoanId(String partnerLoanId) {
        LnLoanExample lnLoanExample = new LnLoanExample();
        lnLoanExample.createCriteria().andPartnerLoanIdEqualTo(partnerLoanId);
        int lnLoanCount = lnLoanMapper.countByExample(lnLoanExample);
        if (lnLoanCount > 0) {
            return true;
        }
        return false;
    }
}
