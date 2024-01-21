package com.pinting.gateway.in.facade;


import com.alibaba.fastjson.JSON;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BillInfo;
import com.pinting.business.accounting.loan.service.DepFixedActFillService;
import com.pinting.business.accounting.loan.service.DepFixedBillSyncService;
import com.pinting.business.accounting.loan.service.DepFixedLoanPaymentService;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.dao.LnDailyAmountMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnUserMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.service.loan.LateRepayAgreementService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_ApplyLoan;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_QueryLoanResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_QueryLoanResult;
import com.pinting.gateway.hessian.message.dafy.model.QueryBillRepayment;
import com.pinting.gateway.hessian.message.loan7.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
/**
 * 
 * @project business
 * @title DepLoan7Facade.java
 * @author Dragon & cat
 * @date 2017-12-12
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Component("DepLoan7")
public class DepLoan7Facade {

	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	private Logger log = LoggerFactory.getLogger(DepLoan7Facade.class);
	
	@Autowired
	private DepFixedLoanPaymentService depFixedLoanPaymentService;
	@Autowired
	private DepFixedBillSyncService	depFixedBillSyncService;
	@Autowired
	private LnDailyAmountMapper lnDailyAmountMapper;
	@Autowired
	private LnUserMapper lnUserMapper;
	@Autowired
	private LnLoanMapper lnLoanMapper;
	@Autowired
	private DepFixedRepayPaymentService depFixedRepayPaymentService;
	@Autowired
	private SignSealService signSealService;
	@Autowired
	private LateRepayAgreementService repayAgreementService;
	@Autowired
	private DepFixedActFillService depFixedActFillService;
	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
	
	//查询频率优化 TODO
	static private int queryLoanResultCount = 0 ; //借款状态查询次数
	static private int queryRepayResultCount = 0 ; //还款状态查询次数
	static private int agreementNoticeCount = 0; //代偿协议查询次数
	
	static private long queryLoanResultStartMills;  //每个周期的第一次借款状态查询时间
	static private long queryRepayResultStartMills;  //每个周期的第一次还款状态查询时间
	static private long agreementNoticeStartMills;  //每个周期的第一次代偿协议查询时间
	
	static private Integer maxCount;
	static private Integer periodOfTime;
	
	public void dailyAvailableAmountLimit(
			G2BReqMsg_DepLoan7_DailyAvailableAmountLimit req,
			G2BResMsg_DepLoan7_DailyAvailableAmountLimit res) {
		log.info("====================>Business平台已收到7贷自主放款每日可借额度查询请求：" + JSON.toJSONString(req));
		res.setLoanDate(req.getQueryDate());
		try {
			String lastCheckDate = jsClientDaoSupport.getString(Constants.LIQUIDATION_FLAG); //最后一次清算的日期
			String queryDateStr = DateUtil.formatYYYYMMDD(req.getQueryDate());
			log.info("====================>Business平台已收到7贷自主放款每日可借额度查询请求：最后一次清算时间："+ queryDateStr);
			//如果清算时间小于【查询日期】，则返回0
			if(StringUtils.isBlank(lastCheckDate) || DateUtil.parseDate(lastCheckDate).compareTo(DateUtil.parseDate(queryDateStr)) < 0){
				res.setAmount(null);
				log.info("====================>Business平台已收到7贷自主放款每日可借额度查询请求：未清算，返回null" );
				throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,",未找到7贷自主放款" + DateUtil.formatYYYYMMDD(req.getQueryDate()) + "的放款限额信息");
			}else{
				log.info("====================>Business平台已收到7贷自主放款每日可借额度查询请求：已清算，查询数据库" );
				LnDailyAmountExample example = new LnDailyAmountExample();
				example.createCriteria().andUseDateEqualTo(req.getQueryDate()).andPartnerCodeEqualTo(PartnerEnum.SEVEN_DAI_SELF.getCode())
				.andStatusEqualTo(Constants.LN_DAILY_AMOUNT_STATUS_AVALIABLE);
				List<LnDailyAmount> lnDailyAmountList = lnDailyAmountMapper.selectByExample(example);
				if(CollectionUtils.isEmpty(lnDailyAmountList)){
					res.setAmount(null);
					throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,",未找到7贷自主放款" + DateUtil.formatYYYYMMDD(req.getQueryDate()) + "的放款限额信息");
				}
				LnDailyAmount lnDailyAmount = lnDailyAmountList.get(0);
				Double dailyAmount = MoneyUtil.add(lnDailyAmount.getFreeAmount(),lnDailyAmount.getPartnerAmount()).doubleValue();
				res.setAmount(MoneyUtil.formatHundred(dailyAmount));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void applyLoan(
			G2BReqMsg_DepLoan7_ApplyLoan req,
			G2BResMsg_DepLoan7_ApplyLoan res) throws Exception {
		log.info("====================>Business平台已收到放款请求：" + JSON.toJSONString(req));
		G2BReqMsg_DafyLoan_ApplyLoan appReq = new G2BReqMsg_DafyLoan_ApplyLoan();
		appReq.setLoanId(req.getLoanId());
		appReq.setMobile(req.getMobile());
		appReq.setApplyTime(req.getApplyTime());
		appReq.setBankCard(req.getBankCard());
		appReq.setBankCode(req.getBankCode());
		appReq.setBreakMaxDays(req.getBreakMaxDays());
		appReq.setBreakTimes(req.getBreakTimes());
		appReq.setBusinessType(req.getBusinessType());
		appReq.setCreditAmount(req.getCreditAmount());
		appReq.setCreditLevel(req.getCreditLevel());
		appReq.setCreditScore(req.getCreditScore());
		appReq.setEducation(req.getEducation());
		appReq.setIdCard(req.getIdCard());
		appReq.setLoanAmount(req.getLoanAmount());
		appReq.setLoanedAmount(req.getLoanedAmount());
		appReq.setLoanFee(req.getLoanFee());
		appReq.setLoanRate(req.getLoanRate());
		appReq.setLoanTerm(req.getLoanTerm());
		appReq.setLoanTimes(req.getLoanTimes());
		appReq.setMarriage(req.getMarriage());
		appReq.setMonthlyIncome(req.getMonthlyIncome());
		appReq.setName(req.getName());
		appReq.setOrderNo(req.getOrderNo());
		appReq.setPurpose(req.getPurpose());
		appReq.setSubjectName(req.getSubjectName());
		appReq.setUserId(req.getUserId());
		appReq.setWorkUnit(req.getWorkUnit());
		depFixedLoanPaymentService.loanApply(appReq, PartnerEnum.SEVEN_DAI_SELF);
	}
	
	/**
	 * 补账完成通知
	 * @param req
	 * @param res
	 */
	public void fillFinishNotice(G2BReqMsg_DepLoan7_FillFinishNotice req,
			G2BResMsg_DepLoan7_FillFinishNotice res){
		
		log.info("====================>Business平台已收到补账完成通知：" + req);
		depFixedActFillService.depFixedActFillFinishHandle(req);
	}

	/**
	 * 放款结果查询
	 * @param req
	 * @param res
	 */
	public void queryLoanResult(
			G2BReqMsg_DepLoan7_QueryLoanResult req,
			G2BResMsg_DepLoan7_QueryLoanResult res) {
		log.info("====================>Business平台已收到7贷放款结果查询请求：" + JSON.toJSONString(req));
		
		long nowMills = System.currentTimeMillis();
		if (maxCount == null) {
			log.info("====================>累计次数不存在，查询数据库配置表：" + req);
			BsSysConfig maxCountConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.CHECK_MAX_COUNT);
			BsSysConfig periodOfTimeCofig = bsSysConfigMapper.selectByPrimaryKey(Constants.CHECK_PERIOD_OF_TIME);
			maxCount = Integer.parseInt(maxCountConfig.getConfValue());
			periodOfTime = Integer.parseInt(periodOfTimeCofig.getConfValue());
		} else {
			log.info("====================>累计次数存在：");
			int count = queryLoanResultCount + 1;
			log.info("====================>7贷放款结果查询当前查询次数为：" + count );
		}
		if (queryLoanResultCount == 0) {
			log.info("====================>7贷放款结果第一次查询，初始化第一次查询时间：" + req);
			queryLoanResultStartMills = System.currentTimeMillis();
		}
		if ((nowMills-queryLoanResultStartMills) > periodOfTime*1000) {
			log.info("====================>7贷放款结果查询超过规定时间，查询次数变为1，重新初始化第一次查询时间：" + req);
			queryLoanResultCount = 1;
			queryLoanResultStartMills = System.currentTimeMillis();
			G2BReqMsg_DafyLoan_QueryLoanResult loanReq = new G2BReqMsg_DafyLoan_QueryLoanResult();
			loanReq.setOrderNo(req.getOrderNo());
			loanReq.setApplyDate(req.getApplyDate());
			G2BResMsg_DafyLoan_QueryLoanResult loanRes = new G2BResMsg_DafyLoan_QueryLoanResult();
			depFixedLoanPaymentService.loanResultQuery(loanReq, loanRes, PartnerEnum.SEVEN_DAI_SELF);
			res.setLoanId(loanRes.getLoanId());
			res.setOrderNo(loanRes.getOrderNo());
			res.setBgwOrderNo(loanRes.getBgwOrderNo());
			res.setAgreementNo(loanRes.getAgreementNo());
			res.setLoanServiceRate(loanRes.getLoanServiceRate());
			res.setFinishTime(loanRes.getFinishTime());
			res.setChannel(loanRes.getChannel());
			res.setLenders(loanRes.getLenders());
			res.setResultCode(loanRes.getResultCode());
			res.setResultMsg(loanRes.getResultMsg());
			res.setResCode(loanRes.getResCode());
			res.setResMsg(loanRes.getResMsg());
		} else {
			if (queryLoanResultCount < maxCount) {
				log.info("====================>7贷放款结果查询次数加1：" + req);
				queryLoanResultCount++;
				G2BReqMsg_DafyLoan_QueryLoanResult loanReq = new G2BReqMsg_DafyLoan_QueryLoanResult();
				loanReq.setOrderNo(req.getOrderNo());
				loanReq.setApplyDate(req.getApplyDate());
				G2BResMsg_DafyLoan_QueryLoanResult loanRes = new G2BResMsg_DafyLoan_QueryLoanResult();
				depFixedLoanPaymentService.loanResultQuery(loanReq, loanRes, PartnerEnum.SEVEN_DAI_SELF);
				res.setLoanId(loanRes.getLoanId());
				res.setOrderNo(loanRes.getOrderNo());
				res.setBgwOrderNo(loanRes.getBgwOrderNo());
				res.setAgreementNo(loanRes.getAgreementNo());
				res.setLoanServiceRate(loanRes.getLoanServiceRate());
				res.setFinishTime(loanRes.getFinishTime());
				res.setChannel(loanRes.getChannel());
				res.setLenders(loanRes.getLenders());
				res.setResultCode(loanRes.getResultCode());
				res.setResultMsg(loanRes.getResultMsg());
				res.setResCode(loanRes.getResCode());
				res.setResMsg(loanRes.getResMsg());
			} else {
				log.info("====================>超过查询次数，7贷放款结果查询次数加1：" + req);
				queryLoanResultCount++;
				throw new PTMessageException(PTMessageEnum.CHECK_COUNT_OUT);
			}
		}
	}
	
	
	/**
	 * 账单（还款计划）推送
	 * @param req
	 * @param res
	 */
	public void pushBill(G2BReqMsg_DepLoan7_PushBill req,
			G2BResMsg_DepLoan7_PushBill res){
		log.info("====================>Business平台已收到账单（还款计划）推送：" + JSON.toJSONString(req));
		
		try {
			jsClientDaoSupport.lock(RedisLockEnum.LOCK_BILL_SYNC.getKey() + PartnerEnum.SEVEN_DAI_SELF.getCode() + req.getLoanId());
			
			LnUserExample lnUserExample = new LnUserExample();
			lnUserExample.createCriteria().andPartnerUserIdEqualTo(req.getUserId()).andPartnerCodeEqualTo(PartnerEnum.SEVEN_DAI_SELF.getCode());
			List<LnUser> lnUserlist = lnUserMapper.selectByExample(lnUserExample);
			if (CollectionUtils.isEmpty(lnUserlist)) {
				throw new PTMessageException(PTMessageEnum.USER_INFO_NOT_FOUND);
			}

			LnLoanExample lnLoanExample = new LnLoanExample();
			lnLoanExample.createCriteria().andPartnerLoanIdEqualTo(req.getLoanId()).andStatusEqualTo("PAIED").andLnUserIdEqualTo(lnUserlist.get(0).getId());
			List<LnLoan> lnLoanlist = lnLoanMapper.selectByExample(lnLoanExample);
			if (CollectionUtils.isEmpty(lnLoanlist)) {
				throw new PTMessageException(PTMessageEnum.LOAN_INFO_NOT_FOUND);
			}
			
			BillInfo billInfo = new BillInfo();
			billInfo.setPartnerUserId(lnUserlist.get(0).getPartnerUserId());
			billInfo.setPartnerLoanId(lnLoanlist.get(0).getPartnerLoanId());
			billInfo.setAgreementNo(req.getAgreementNo());
			billInfo.setAgreementUrl(req.getAgreementUrl());
			billInfo.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());

			List<QueryBillRepayment>  repaymentList = new ArrayList<QueryBillRepayment>();
			for (HashMap<String, Object> repayMent : req.getRepayments()) {
				QueryBillRepayment queryBillRepayment = new QueryBillRepayment();
				queryBillRepayment.setRepayId((String)repayMent.get("repayId"));
				queryBillRepayment.setStatus((String)repayMent.get("status"));
				queryBillRepayment.setRepayDate((Date)repayMent.get("repayDate"));
				queryBillRepayment.setRepaySerial((Integer)repayMent.get("repaySerial"));
				queryBillRepayment.setTotal((Double)repayMent.get("total"));
				queryBillRepayment.setPrincipal((Double)repayMent.get("principal"));
				queryBillRepayment.setInterest((Double)repayMent.get("interest"));
				queryBillRepayment.setPrincipalOverdue((Double)repayMent.get("principalOverdue"));
				queryBillRepayment.setInterestOverdue((Double)repayMent.get("interestOverdue"));
				queryBillRepayment.setReservedField1((String)repayMent.get("reservedField1"));
				queryBillRepayment.setBgwOrderNo((String)repayMent.get("bgwOrderNo"));
				repaymentList.add(queryBillRepayment);
			}
			billInfo.setRepayments(repaymentList);
			depFixedBillSyncService.loanSyncBill(billInfo, lnLoanlist.get(0).getId());
		} finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_BILL_SYNC.getKey() + PartnerEnum.SEVEN_DAI_SELF.getCode() + req.getLoanId());
        }
		

	}
	
	
	public void querySignResult(
			G2BReqMsg_DepLoan7_QuerySignResult req,
			G2BResMsg_DepLoan7_QuerySignResult res) {
		log.info("====================>Business平台已收到签章结果查询请求：" + JSON.toJSONString(req));
		
		BsUserSealFile  bsUserSealFile = signSealService.querySealFileInfoByAgreementNo(req.getAgreementNo());
		if (bsUserSealFile == null || StringUtil.isBlank(bsUserSealFile.getRelativeInfo()) ) {
			throw new PTMessageException(PTMessageEnum.SEAL_FILE_NOT_FOUND);
		}
		LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(Integer.parseInt(bsUserSealFile.getRelativeInfo()));
		if (lnLoan == null ) {
			throw new PTMessageException(PTMessageEnum.LOAN_INFO_NOT_FOUND);
		}
		
		res.setSignResult(bsUserSealFile.getSealStatus());
		res.setAgreementUrl(bsUserSealFile.getFileAddress());
		res.setLoanId(lnLoan.getPartnerLoanId());
	}
	
	
	public void activeRepayPre(
			G2BReqMsg_DepLoan7_ActiveRepayPre req,
			G2BResMsg_DepLoan7_ActiveRepayPre res) {
		log.info("====================>Business平台已收到主动还款预下单请求：" + req);
		
	}
	
	
	public void activeRepayConfirm(
			G2BReqMsg_DepLoan7_ActiveRepayConfirm req,
			G2BResMsg_DepLoan7_ActiveRepayConfirm res) throws Exception {
		log.info("====================>Business平台已收到主动还款正式下单请求：" + req);
	}
	
	/**
	 * 代偿通知
	 * @param req
	 * @param res
	 */
	public void lateRepayNotice(G2BReqMsg_DepLoan7_LateRepayNotice req,
			G2BResMsg_DepLoan7_LateRepayNotice res){
		log.info("====================>Business平台已收到七贷代偿通知请求：" + JSON.toJSONString(req));
		
		List<LnCompensateDetail> detailList = new ArrayList<LnCompensateDetail>();
        LnCompensate lnCompensate = new LnCompensate();
        lnCompensate.setApplyTime(req.getApplyTime());
        lnCompensate.setCreateTime(new Date());
        lnCompensate.setFinishTime(req.getFinishTime());
        lnCompensate.setOrderNo(req.getOrderNo());
        lnCompensate.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
        lnCompensate.setPayOrderNo(req.getPayOrderNo());
        lnCompensate.setTotalMount(req.getTotalAmount());
        lnCompensate.setUpdateTime(new Date());
        ArrayList<HashMap<String, Object>> detailMapList = req.getRepayments();
        for (HashMap<String, Object> detailMap : detailMapList) {
            LnCompensateDetail detailRecord = new LnCompensateDetail();
            detailRecord.setInterest((Double)detailMap.get("interest"));
            detailRecord.setInterestOverdue((Double)detailMap.get("interestOverdue"));
            detailRecord.setPartnerLoanId((String)detailMap.get("loanId"));
            detailRecord.setPartnerRepayId((String)detailMap.get("repayId"));
            detailRecord.setPartnerUserId((String)detailMap.get("userId"));
            detailRecord.setPrincipal((Double)detailMap.get("principal"));
            detailRecord.setPrincipalOverdue((Double)detailMap.get("principalOverdue"));
            detailRecord.setRepaySerial((Integer)detailMap.get("repaySerial"));
            detailRecord.setRepayType((String)detailMap.get("repayType"));
            detailRecord.setTotal((Double)detailMap.get("total"));

            detailList.add(detailRecord);
        }
        
		depFixedRepayPaymentService.lateRepayNotice(lnCompensate, detailList);
	}
	
	/**
	 * 还款结果查询
	 * @param req
	 * @param res
	 */
	public void queryRepayResult(G2BReqMsg_DepLoan7_QueryRepayResult req, 
			G2BResMsg_DepLoan7_QueryRepayResult res){
		log.info("====================>Business平台已收到7贷还款结果查询：" + JSON.toJSONString(req));
		long nowMills = System.currentTimeMillis();
		if (maxCount == null) {
			log.info("====================>累计次数不存在，查询数据库配置表：" + req);
			BsSysConfig maxCountConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.CHECK_MAX_COUNT);
			BsSysConfig periodOfTimeCofig = bsSysConfigMapper.selectByPrimaryKey(Constants.CHECK_PERIOD_OF_TIME);
			maxCount = Integer.parseInt(maxCountConfig.getConfValue());
			periodOfTime = Integer.parseInt(periodOfTimeCofig.getConfValue());
		} else {
			log.info("====================>累计次数存在：");
			int count = queryRepayResultCount + 1;
			log.info("====================>7贷还款结果查询当前查询次数为：" + count );
		}
		if (queryRepayResultCount == 0) {
			log.info("====================>7贷还款结果第一次查询，初始化第一次查询时间：" + req);
			queryRepayResultStartMills = System.currentTimeMillis();
		}
		if ((nowMills-queryRepayResultStartMills) > periodOfTime*1000) {
			log.info("====================>7贷还款结果查询超过规定时间，查询次数变为1，重新初始化第一次查询时间：" + req);
			queryRepayResultCount = 1;
			queryRepayResultStartMills = System.currentTimeMillis();
			depFixedRepayPaymentService.sevenRepayResultQuery(req, res);
		} else {
			if (queryRepayResultCount < maxCount) {
				log.info("====================>7贷还款结果查询次数加1：" + req);
				queryRepayResultCount++;
				depFixedRepayPaymentService.sevenRepayResultQuery(req, res);
			} else {
				log.info("====================>超过查询次数，7贷还款结果查询次数加1：" + req);
				queryRepayResultCount++;
				throw new PTMessageException(PTMessageEnum.CHECK_COUNT_OUT);
			}
		}
	}
	
	/**
	 * 代扣还款
	 * @param req
	 * @param res
	 * @throws Exception 
	 */
	public void cutRepayConfirm(G2BReqMsg_DepLoan7_CutRepayConfirm req, G2BResMsg_DepLoan7_CutRepayConfirm res) throws Exception{
		log.info("====================>Business平台已收到代扣还款请求：" + JSON.toJSONString(req));
		if(Constants.REQ_IS_OFFLINE_Y.equals(req.getIsOffline())){
			depFixedRepayPaymentService.repayOfflineDepSeven(req);
		}else{
			depFixedRepayPaymentService.depSevenRepayApply(req);
		}
	}
	
	/**
	 * 协议下载地址查询
	 * @param req
	 * @param res
	 */
	public void agreementNotice(G2BReqMsg_DepLoan7_AgreementNotice req, G2BResMsg_DepLoan7_AgreementNotice res){
		// 合规化，7贷协议不再生成，协议下载地址查询接口不再传输内容
		log.info("====================>Business平台已收到7贷协议下载地址查询请求：" + JSON.toJSONString(req));
		if(StringUtil.isEmpty(req.getLoanId())){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,"7贷协议下载地址查询时传入合作方借款编号为空");
		}
		long nowMills = System.currentTimeMillis();
		if (maxCount == null) {
			log.info("====================>累计次数不存在，查询数据库配置表：" + req);
			BsSysConfig maxCountConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.CHECK_MAX_COUNT);
			BsSysConfig periodOfTimeCofig = bsSysConfigMapper.selectByPrimaryKey(Constants.CHECK_PERIOD_OF_TIME);
			maxCount = Integer.parseInt(maxCountConfig.getConfValue());
			periodOfTime = Integer.parseInt(periodOfTimeCofig.getConfValue());
		} else {
			log.info("====================>累计次数存在：");
			int count = agreementNoticeCount + 1;
			log.info("====================>7贷协议下载地址查询当前查询次数为：" + count );
		}
		if (agreementNoticeCount == 0) {
			log.info("====================>7贷协议下载地址第一次查询，初始化第一次查询时间：" + req);
			agreementNoticeStartMills = System.currentTimeMillis();
		}
		if ((nowMills-agreementNoticeStartMills) > periodOfTime*1000) {
			log.info("====================>7贷协议下载地址查询超过规定时间，查询次数变为1，重新初始化第一次查询时间：" + req);
			agreementNoticeCount = 1;
			agreementNoticeStartMills = System.currentTimeMillis();
			repayAgreementService.findSevenAgreementUrls(req,res);
		} else {
			if (agreementNoticeCount < maxCount) {
				log.info("====================>7贷协议下载地址查询次数加1：" + req);
				agreementNoticeCount++;
				repayAgreementService.findSevenAgreementUrls(req,res);
				
			} else {
				log.info("====================>超过查询次数，7贷协议下载地址查询次数加1：" + req);
				agreementNoticeCount++;
				throw new PTMessageException(PTMessageEnum.CHECK_COUNT_OUT);
			}
		}
		
	}
	
	/**
	 * 还款预下单重发验证码短信
	 * @param req
	 * @param res
	 */
	public void activeRepaySmsCodeRepeat(G2BReqMsg_DepLoan7_ActiveRepaySmsCodeRepeat req, G2BResMsg_DepLoan7_ActiveRepaySmsCodeRepeat res){
		log.info("====================>Business平台已收到还款预下单重发验证码短信：" + req);
	}

}
