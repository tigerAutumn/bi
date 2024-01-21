package com.pinting.gateway.in.facade;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BillInfo;
import com.pinting.business.accounting.loan.service.DepFixedActFillService;
import com.pinting.business.accounting.loan.service.DepFixedBillSyncService;
import com.pinting.business.accounting.loan.service.DepFixedLoanPaymentService;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.coreflow.core.enums.TransCodeEnum;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DispatcherService;
import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.dao.LnDailyAmountMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnUserMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsUser4LoanVO;
import com.pinting.business.service.loan.LateRepayAgreementService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.RegexUtil;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.*;
import com.pinting.gateway.hessian.message.dafy.model.QueryBillRepayment;
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
 * @Project: business
 * @Title: DafyLoanFacade.java
 * @author Dragon & cat
 * @date 2016-11-25
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("DafyLoan")
public class DafyLoanFacade {
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	private Logger log = LoggerFactory.getLogger(DafyLoanFacade.class);
//	@Autowired
//	private IncrRepayPaymentService incrRepayPaymentService;
	@Autowired
	private LnLoanMapper lnLoanMapper;
//	@Autowired
//	private LnLoanRelationMapper loanRelationMapper;
//	@Autowired
//	private BsUserMapper bsUserMapper;
	@Autowired
	private SignSealService signSealService;
	@Autowired
	private DepFixedBillSyncService	depFixedBillSyncService;
//	@Autowired
//	private IncrActFillService	incrActFillService;
	@Autowired
	private LateRepayAgreementService repayAgreementService;
	@Autowired
	private LnUserMapper lnUserMapper;
	@Autowired
	private LnDailyAmountMapper lnDailyAmountMapper;
	@Autowired
	private DepFixedActFillService depFixedActFillService;
	@Autowired
	private DepFixedRepayPaymentService  depFixedRepayPaymentService;
	@Autowired
	private DepFixedLoanPaymentService depFixedLoanPaymentService;
	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
	@Autowired
	private DispatcherService dispatcherService;

	//查询频率优化
	static private int queryLoanResultCount = 0; //借款状态查询次数
	static private int queryRepayResultCount = 0; //还款状态查询次数
	static private int agreementNoticeCount = 0; //代偿协议查询次数
	
	static private long queryLoanResultStartMills;  //每个周期的第一次借款状态查询时间
	static private long queryRepayResultStartMills;  //每个周期的第一次还款状态查询时间
	static private long agreementNoticeStartMills;  //每个周期的第一次代偿协议查询时间
	
	static private Integer maxCount;
	static private Integer periodOfTime;
	
	public void dailyAvailableAmountLimit(
			G2BReqMsg_DafyLoan_DailyAvailableAmountLimit req,
			G2BResMsg_DafyLoan_DailyAvailableAmountLimit res) {
		log.info("====================>Business平台已收到每日可借额度查询请求：" + req);
		res.setLoanDate(req.getQueryDate());
		try {
			String lastCheckDate = jsClientDaoSupport.getString(Constants.LIQUIDATION_FLAG); //最后一次清算的日期
			String queryDateStr = DateUtil.formatYYYYMMDD(req.getQueryDate());
			log.info("====================>Business平台已收到每日可借额度查询请求：最后一次清算时间："+ queryDateStr);
			//如果清算时间小于【查询日期】，则返回0
			if(StringUtils.isBlank(lastCheckDate) || DateUtil.parseDate(lastCheckDate).compareTo(DateUtil.parseDate(queryDateStr)) < 0){
				res.setAmount(null);
				log.info("====================>Business平台已收到每日可借额度查询请求：未清算，返回null" );
				throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,",未找到云贷自主放款" + DateUtil.formatYYYYMMDD(req.getQueryDate()) + "的放款限额信息");
			}else{
				log.info("====================>Business平台已收到每日可借额度查询请求：已清算，查询数据库" );
				LnDailyAmountExample example = new LnDailyAmountExample();
				example.createCriteria().andUseDateEqualTo(req.getQueryDate()).andPartnerCodeEqualTo(PartnerEnum.YUN_DAI_SELF.getCode())
					.andStatusEqualTo(Constants.LN_DAILY_AMOUNT_STATUS_AVALIABLE);
				List<LnDailyAmount> lnDailyAmountList = lnDailyAmountMapper.selectByExample(example);
				if(CollectionUtils.isEmpty(lnDailyAmountList)){
					res.setAmount(null);
					throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,",未找到云贷自主放款" + DateUtil.formatYYYYMMDD(req.getQueryDate()) + "的放款限额信息");
				}
				LnDailyAmount lnDailyAmount = lnDailyAmountList.get(0);
				Double dailyAmount = MoneyUtil.add(lnDailyAmount.getPartnerAmount(),lnDailyAmount.getFreeAmount()).doubleValue();
				res.setAmount( MoneyUtil.formatHundred(dailyAmount));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}


	public void applyLoan(
			G2BReqMsg_DafyLoan_ApplyLoan req,
			G2BResMsg_DafyLoan_ApplyLoan res) {
		log.info("====================>Business平台已收到放款请求：" + req);
//		depFixedLoanPaymentService.loanApply(req, PartnerEnum.YUN_DAI_SELF);

		// 校验邮箱格式
		if (StringUtils.isNotBlank(req.getEmail()) && !RegexUtil.isValidEmail(req.getEmail())) {
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,"email邮箱格式错误");
		}

		FlowContext flowContext = new FlowContext();
		flowContext.setReq(req);
		flowContext.setRes(res);
		flowContext.setTransCode(TransCodeEnum.APPLY_LOAN.getCode());
		flowContext.setPartnerEnum(PartnerEnum.YUN_DAI_SELF);
		flowContext.setBusinessType(req.getBusinessType());
		dispatcherService.dispatcherService(flowContext);
	}
	
	/**
	 * 补账完成通知
	 * @param req
	 * @param res
	 */
	public void fillFinishNotice(G2BReqMsg_DafyLoan_FillFinishNotice req,
			G2BResMsg_DafyLoan_FillFinishNotice res){
		
		log.info("====================>Business平台已收到补账完成通知：" + req);
		depFixedActFillService.depFixedActFillFinishHandle(req);
	}

	/**
	 * 放款结果查询
	 * @param req
	 * @param res
	 */
	public void queryLoanResult(
			G2BReqMsg_DafyLoan_QueryLoanResult req,
			G2BResMsg_DafyLoan_QueryLoanResult res) {
		log.info("====================>Business平台已收到放款结果查询请求：" + req);
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
			log.info("====================>云贷还款结果查询当前查询次数为：" + count );
		}
		if (queryLoanResultCount == 0) {
			log.info("====================>云贷放款结果第一次查询，初始化第一次查询时间：" + req);
			queryLoanResultStartMills = System.currentTimeMillis();
		}
		if ((nowMills-queryLoanResultStartMills) > periodOfTime*1000) {
			log.info("====================>云贷放款结果查询超过规定时间，查询次数变为1，重新初始化第一次查询时间：" + req);
			queryLoanResultCount = 1;
			queryLoanResultStartMills = System.currentTimeMillis();
			depFixedLoanPaymentService.loanResultQuery(req,res, PartnerEnum.YUN_DAI_SELF);
		} else {
			if (queryLoanResultCount < maxCount) {
				log.info("====================>云贷放款结果查询次数加1：" + req);
				queryLoanResultCount++;
				depFixedLoanPaymentService.loanResultQuery(req,res, PartnerEnum.YUN_DAI_SELF);
			} else {
				log.info("====================>超过查询次数，云贷放款结果查询次数加1：" + req);
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
	public void pushBill(G2BReqMsg_DafyLoan_PushBill req,
			G2BResMsg_DafyLoan_PushBill res){
		log.info("====================>Business平台已收到账单（还款计划）推送：" + req);

		LnUserExample lnUserExample = new LnUserExample();
		lnUserExample.createCriteria().andPartnerUserIdEqualTo(req.getUserId()).andPartnerCodeEqualTo(PartnerEnum.YUN_DAI_SELF.getCode());
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
		billInfo.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
		
		List<QueryBillRepayment>  repaymentList = new ArrayList<QueryBillRepayment>();
		for (HashMap<String, Object>  repayMent: req.getRepayments()) {
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
	}
	
	
	public void querySignResult(
			G2BReqMsg_DafyLoan_QuerySignResult req,
			G2BResMsg_DafyLoan_QuerySignResult res) {
		log.info("====================>Business平台已收到签章结果查询请求：" + req);
		
		BsUserSealFile  bsUserSealFile = signSealService.querySealFileInfoByAgreementNo(req.getAgreementNo());
		if (bsUserSealFile == null || StringUtil.isBlank(bsUserSealFile.getRelativeInfo()) ) {
			throw new PTMessageException(PTMessageEnum.SEAL_FILE_NOT_FOUND);
		}
		List<BsUser4LoanVO>  user4LoanVOs=  signSealService.selectUserListByLoanId(Integer.valueOf(bsUserSealFile.getRelativeInfo()));
		LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(Integer.parseInt(bsUserSealFile.getRelativeInfo()));
		if (lnLoan == null ) {
			throw new PTMessageException(PTMessageEnum.LOAN_INFO_NOT_FOUND);
		}
		
		res.setSignResult(bsUserSealFile.getSealStatus());
		res.setAgreementUrl(bsUserSealFile.getFileAddress());
		res.setLoanId(lnLoan.getPartnerLoanId());
		ArrayList<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		
		for (BsUser4LoanVO bsUser4LoanVO : user4LoanVOs) {
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("lenderName", bsUser4LoanVO.getUserName());
			map.put("lenderIdcard", bsUser4LoanVO.getUserIdCardNo());
			map.put("investAmount", bsUser4LoanVO.getOutAmount());
			list.add(map);
		}
		res.setLenders(list);
	}
	
	
	public void activeRepayPre(
			G2BReqMsg_DafyLoan_ActiveRepayPre req,
			G2BResMsg_DafyLoan_ActiveRepayPre res) {
		log.info("====================>Business平台已收到主动还款预下单请求：" + req);
		String bgwOrderNo = depFixedRepayPaymentService.doPreRepay(req);
		res.setBgwOrderNo(bgwOrderNo);
		
		
	}
	
	
	public void activeRepayConfirm(
			G2BReqMsg_DafyLoan_ActiveRepayConfirm req,
			G2BResMsg_DafyLoan_ActiveRepayConfirm res) throws Exception {
		log.info("====================>Business平台已收到主动还款正式下单请求：" + req);
		depFixedRepayPaymentService.doRepayConfirm(req);
	}
	
	/**
	 * 代偿通知
	 * @param req
	 * @param res
	 */
	public void lateRepayNotice(G2BReqMsg_DafyLoan_LateRepayNotice req,
			G2BResMsg_DafyLoan_LateRepayNotice res){
		log.info("====================>Business平台已收到代偿通知请求：" + req);

		FlowContext flowContext = new FlowContext();
		flowContext.setReq(req);
		flowContext.setRes(res);
		flowContext.setTransCode(TransCodeEnum.COMPENSATE_NOTICE.getCode());
		flowContext.setPartnerEnum(PartnerEnum.YUN_DAI_SELF);
		dispatcherService.dispatcherService(flowContext);
	}
	
	/**
	 * 还款结果查询
	 * @param req
	 * @param res
	 */
	public void queryRepayResult(G2BReqMsg_DafyLoan_QueryRepayResult req, 
			G2BResMsg_DafyLoan_QueryRepayResult res){
		log.info("====================>Business平台已收到还款结果查询：" + req);
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
			log.info("====================>云贷还款结果查询当前查询次数为：" + count );
		}
		if (queryRepayResultCount == 0) {
			log.info("====================>云贷还款结果第一次查询，初始化第一次查询时间：" + req);
			queryRepayResultStartMills = System.currentTimeMillis();
		}
		if ((nowMills-queryRepayResultStartMills) > periodOfTime*1000) {
			log.info("====================>云贷还款结果查询超过规定时间，查询次数变为1，重新初始化第一次查询时间：" + req);
			queryRepayResultCount = 1;
			queryRepayResultStartMills = System.currentTimeMillis();
			depFixedRepayPaymentService.repayResultQuery(req,res);
		} else {
			if (queryRepayResultCount < maxCount) {
				log.info("====================>云贷还款结果查询次数加1：" + req);
				queryRepayResultCount++;
				depFixedRepayPaymentService.repayResultQuery(req,res);
			} else {
				log.info("====================>超过查询次数，云贷还款结果查询次数加1：" + req);
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
	public void cutRepayConfirm(G2BReqMsg_DafyLoan_CutRepayConfirm req, G2BResMsg_DafyLoan_CutRepayConfirm res) throws Exception{
		log.info("====================>Business平台已收到代扣还款请求：" + req);
		depFixedRepayPaymentService.dispatcherRepayApply(req);
	}
	
	/**
	 * 协议下载地址查询
	 * @param req
	 * @param res
	 */
	
	public void agreementNotice(G2BReqMsg_DafyLoan_AgreementNotice req, G2BResMsg_DafyLoan_AgreementNotice res){
		// 合规化，云贷协议不再生成，协议下载地址查询接口不再传输内容
		log.info("====================>Business平台已收到协议下载地址查询：" + req);
		if(StringUtil.isEmpty(req.getLoanId())){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,"云贷协议下载地址查询时传入合作方借款编号为空");
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
			log.info("====================>云贷协议下载地址查询当前查询次数为：" + count );
		}
		if (agreementNoticeCount == 0) {
			log.info("====================>云贷协议下载地址第一次查询，初始化第一次查询时间：" + req);
			agreementNoticeStartMills = System.currentTimeMillis();
		}
		if ((nowMills-agreementNoticeStartMills) > periodOfTime*1000) {
			log.info("====================>云贷协议下载地址查询超过规定时间，查询次数变为1，重新初始化第一次查询时间：" + req);
			agreementNoticeCount = 1;
			agreementNoticeStartMills = System.currentTimeMillis();
			repayAgreementService.findAgreementUrls(req,res);
		} else {
			if (agreementNoticeCount < maxCount) {
				log.info("====================>云贷协议下载地址查询次数加1：" + req);
				agreementNoticeCount++;
				repayAgreementService.findAgreementUrls(req,res);
			} else {
				log.info("====================>超过查询次数，云贷协议下载地址查询次数加1：" + req);
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
	public void activeRepaySmsCodeRepeat(G2BReqMsg_DafyLoan_ActiveRepaySmsCodeRepeat req, G2BResMsg_DafyLoan_ActiveRepaySmsCodeRepeat res){
		log.info("====================>Business平台已收到还款预下单重发验证码短信：" + req);
		res.setBgwOrderNo(depFixedRepayPaymentService.doPreRepayRepeat(req.getOrderNo()));

	}
}
