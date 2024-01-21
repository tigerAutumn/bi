package com.pinting.business.service.loan.impl;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.LoanSubjectRulesEnum;
import com.pinting.business.accounting.loan.enums.LoanSubjectRulesEnum.LeftRuleEnum;
import com.pinting.business.accounting.loan.enums.LoanSubjectRulesEnum.RateTypeEnum;
import com.pinting.business.accounting.loan.enums.LoanSubjects;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.dao.*;
import com.pinting.business.enums.BaoFooEnum;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.LnLoanVO;
import com.pinting.business.model.vo.LnSubjectVO;
import com.pinting.business.model.vo.ZANRevenueModelVO;
import com.pinting.business.service.loan.LoanUserService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_LoanCif_AddLoaner;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Loan_QueryLoan;
import com.pinting.gateway.hessian.message.loan.G2BResMsg_LoanCif_AddLoaner;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LoanUserServiceImpl implements LoanUserService {
	private final Logger logger = LoggerFactory.getLogger(LoanUserServiceImpl.class);
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private LnRepayScheduleMapper lnRepayScheduleMapper;
	@Autowired
	private LnSubjectMapper lnSubjectMapper;
	@Autowired
	private LnRepayMapper lnRepayMapper;
	@Autowired
	private LnUserMapper lnUserMapper;
	@Autowired
	private LnBindCardMapper lnBindCardMapper;
	@Autowired
	private LnAccountMapper lnAccountMapper;
	@Autowired
	private LnPayOrdersMapper payOrdersMapper;
	@Autowired
	private LnLoanMapper loanMapper;
	@Autowired
	private	LnSubAccountMapper lnSubAccountMapper;
	@Autowired
	private LnAccountJnlMapper lnAccountJnlMapper;
	@Autowired
	private BsUserMapper bsUserMapper;
	@Autowired
	private BsHfbankUserExtMapper bsHfbankUserExtMapper;
	@Autowired
	private LnRepayDetailMapper lnRepayDetailMapper;
	@Autowired
	private UcUserMapper ucUserMapper;
	@Autowired
	private UcUserExtMapper ucUserExtMapper;

	private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

	@Override
	public void addLoanCif(final G2BReqMsg_LoanCif_AddLoaner req,final G2BResMsg_LoanCif_AddLoaner res) {
		/**
		 * 1.判断必填字段是否必填；-姓名,注册手机号,身份证号
		 * 2.判断用户是否已存在；
		 */

		transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
			if(StringUtil.isBlank(req.getUserId()) || StringUtil.isBlank(req.getName())
					|| StringUtil.isBlank(req.getRegMobile()) || StringUtil.isBlank(req.getIdCard())){
				throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_NOT_ENOUGH);
			}

			if (queryLoanUserExist(req.getUserId(), req.getChannel()) != null) {
				logger.error("======" + req.getUserId() + "用户已存在======");
				throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_USER_EXIST);
			}

			LnUser record = new LnUser();
			record.setPartnerCode(StringUtils.trim(req.getChannel()));
			record.setPartnerUserId(StringUtils.trim(req.getUserId()));
			record.setUserName(StringUtils.trim(req.getName()));
			record.setMobile(StringUtils.trim(req.getRegMobile()));
			record.setIdCard(StringUtils.trim(req.getIdCard()));
			record.setProfession(StringUtils.trim(req.getProfession()));
			if(StringUtil.isNotBlank(req.getAnnualIncome())){
				record.setAnnualIncome(Integer.parseInt(req.getAnnualIncome()));
			}
			record.setProvinceCode(StringUtils.trim(req.getProvinceCode()));
			record.setCityCode(StringUtils.trim(req.getCityCode()));
			record.setAreaCode(StringUtils.trim(req.getAreaCode()));
			record.setCreateTime(new Date());
			record.setUpdateTime(new Date());
			lnUserMapper.insertSelective(record);

			//新增uc_user，uc_user_ext
			UcUserExample ucUserExist = new UcUserExample();
			ucUserExist.createCriteria().andIdCardEqualTo(req.getIdCard())
					.andStatusEqualTo(Constants.UC_USER_OPEN);
			List<UcUser> ucUsers = ucUserMapper.selectByExample(ucUserExist);

			if(CollectionUtils.isEmpty(ucUsers)){
				//新增用户中心 uc_user记录
				UcUser ucUser = new UcUser();
				ucUser.setCreateTime(new Date());
				ucUser.setUpdateTime(new Date());
				ucUser.setMobile(null);//借款端注册手机号不存入
				ucUser.setIdCard(req.getIdCard());
				ucUser.setUserName(req.getName());
				ucUser.setStatus(Constants.UC_USER_OPEN);
				ucUserMapper.insertSelective(ucUser);

				//新增用户中心uc_user_ext记录
				UcUserExt ucUserExt = new UcUserExt();
				ucUserExt.setUcUserId(ucUser.getId());
				ucUserExt.setCreateTime(new Date());
				ucUserExt.setUserType(Constants.UC_USER_TYPE_ZAN);
				ucUserExt.setUserId(record.getId());
				ucUserExtMapper.insertSelective(ucUserExt);
			}else{
				UcUser user = ucUsers.get(0);
				UcUserExtExample example = new UcUserExtExample();
				example.createCriteria().andUcUserIdEqualTo(user.getId()).andUserTypeEqualTo(Constants.UC_USER_TYPE_ZAN);
				List<UcUserExt> ucUserExts = ucUserExtMapper.selectByExample(example);
				if(CollectionUtils.isNotEmpty(ucUserExts)){
					throw new PTMessageException(PTMessageEnum.USER_EXIST);
				}
				//新增用户中心扩展表
				UcUserExt ucUserExt = new UcUserExt();
				ucUserExt.setUcUserId(user.getId());
				ucUserExt.setCreateTime(new Date());
				ucUserExt.setUserType(Constants.UC_USER_TYPE_ZAN);
				ucUserExt.setUserId(record.getId());
				ucUserExtMapper.insertSelective(ucUserExt);
			}

			LnAccount account = new LnAccount();
			account.setAccountCode(""); // 生成规则未定
			account.setLnUserId(record.getId());
			account.setOpenTime(new Date());
			account.setStatus(Constants.LN_ACCOUNT_STATUS_NORMAL);
			account.setCreateTime(new Date());
			account.setUpdateTime(new Date());
			lnAccountMapper.insertSelective(account);


			LnSubAccount loanAct = new LnSubAccount();
			loanAct.setLnUserId(record.getId());
			loanAct.setAccountId(account.getId());
			loanAct.setAccountType(Constants.LOAN_ACT_TYPE_DEP_JSH);
			loanAct.setOpenBalance(0.0);
			loanAct.setBalance(0.0);
			loanAct.setAvailableBalance(0.0);
			loanAct.setCanWithdraw(0.0);
			loanAct.setFreezeBalance(0.0);
			loanAct.setStatus(Constants.LOAN_SUBACCOUNT_STATUS_OPEN);
			loanAct.setAccumulationInerest(0.0);
			loanAct.setOpenTime(new Date());
			loanAct.setCreateTime(new Date());
			loanAct.setUpdateTime(new Date());
			lnSubAccountMapper.insertSelective(loanAct);
			Integer loanActId = loanAct.getId();//可返回

			LnAccountJnl accountJnl = new LnAccountJnl();
			accountJnl.setTransTime(new Date());
			accountJnl.setTransCode(Constants.LN_DEP_JSH_OPEN);
			accountJnl.setTransName("存管体系余额子账户开户");
			accountJnl.setTransAmount(0.0);
			accountJnl.setSysTime(new Date());
			accountJnl.setChannelTime(null);
			accountJnl.setChannelJnlNo(null);
			accountJnl.setCdFlag2(Constants.CD_FLAG_D);
			accountJnl.setUserId2(record.getId());
			accountJnl.setSubAccountId1(loanActId);
			accountJnl.setSubAccountId2(loanActId);
			accountJnl.setBeforeBalance2(0d);
			accountJnl.setAfterBalance2(0d);
			accountJnl.setBeforeAvialableBalance2(0d);
			accountJnl.setAfterAvialableBalance2(0d);
			accountJnl.setBeforeFreezeBalance2(0d);
			accountJnl.setAfterFreezeBalance2(0d);
			accountJnl.setFee(0.0);
			lnAccountJnlMapper.insertSelective(accountJnl);

			res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
			logger.info("======登记借款人信息成功======");
			}
		});
	}
	/**
	 * 查询借款用户信息是否存在
	 *
	 * @param userId  用户id
	 * @param channel 渠道
	 * @return
	 */
	@Override
	public LnUser queryLoanUserExist(String userId, String channel) {

		LnUserExample example = new LnUserExample();
		example.createCriteria().andPartnerCodeEqualTo(channel)
				.andPartnerUserIdEqualTo(userId);
		List<LnUser> list = lnUserMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
			return list.get(0);
		}

		return null;
	}

	@Override
	public LnBindCard queryLoanBindCardExist(String userId, String bindId, String channel) {
		//根据合作方用户id,查询用户信息
		LnUser lnUser = queryLoanUserExist(userId, channel);

		if (lnUser != null) {
			//根据币港湾借款用户人id和币港湾绑卡id查询用户是否绑卡成功
			LnBindCardExample example = new LnBindCardExample();
			example.createCriteria().andBgwBindIdEqualTo(bindId).andLnUserIdEqualTo(lnUser.getId()).andStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode());
			List<LnBindCard> list = lnBindCardMapper.selectByExample(example);
			if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
				return list.get(0);
			}

			return null;
		} else {
			throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_USER_NOT_EXIST);
		}
	}

	@Override
	public LnLoanVO queryLoanStatus(G2BReqMsg_Loan_QueryLoan req) {

		if(StringUtils.isBlank(req.getOrderNo())){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号为空");
		}

		LnLoanExample loanExample = new LnLoanExample();
		loanExample.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo());
		List<LnLoan> loanList = loanMapper.selectByExample(loanExample);

		if (CollectionUtils.isNotEmpty(loanList) && loanList.size() > 0) {
			LnPayOrdersExample ordersExample = new LnPayOrdersExample();
			ordersExample.createCriteria().andOrderNoEqualTo(loanList.get(0).getPayOrderNo());
			List<LnPayOrders> ordersList = payOrdersMapper.selectByExample(ordersExample);

			LnLoanVO vo = new LnLoanVO();
			vo.setChannel(CollectionUtils.isEmpty(ordersList)?"":ordersList.get(0).getChannel());
			vo.setPartnerLoanId(loanList.get(0).getPartnerLoanId());
			vo.setReturnMsg(CollectionUtils.isEmpty(ordersList)?"":ordersList.get(0).getReturnMsg());
			String status = loanList.get(0).getStatus();
			vo.setLoanTime(loanList.get(0).getLoanTime()!=null?DateUtil.format(loanList.get(0).getLoanTime()):null);
			if(LoanStatus.LOAN_STATUS_FAIL.getCode().equals(status)){
				vo.setStatus("FAIL");
			}else if(LoanStatus.LOAN_STATUS_PAIED.getCode().equals(status) ||
					LoanStatus.LOAN_STATUS_LATE.getCode().equals(status) ||
					LoanStatus.LOAN_STATUS_BAD.getCode().equals(status)){
				vo.setStatus("SUCCESS");
			}else{
				vo.setStatus("PROCESS");
			}
			return vo;
		} else {
			LnLoanVO vo = new LnLoanVO();
			vo.setStatus("PROCESS");
			vo.setReturnMsg("借款编号未找到");
			return vo;
		}
	}

	/**
	 * 此卡是否已绑定成功
	 *
	 * @param bankCard   银行卡号
	 * @param cardHolder 用户姓名
	 * @param idCard     身份证号
	 * @param mobile     银行预留手机号
	 * @return
	 */
	private LnBindCard queryLoanBindCardSuccessExist(String bankCard, String cardHolder, String idCard, String mobile) {

		LnBindCardExample example = new LnBindCardExample();
		example.createCriteria().andBankCardEqualTo(bankCard)
				.andStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode());
		List<LnBindCard> list = lnBindCardMapper.selectByExample(example);

		if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	@Override
	public boolean isLoanUserLate(Integer loanUserId) {
		boolean flag = false;
		if(loanUserId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		Integer count = lnRepayScheduleMapper.countLateNot(loanUserId, null, null);
		if(count > 0){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean isLoanUserLoanLate(Integer loanUserId, Integer loanId) {
		boolean flag = false;
		if(loanUserId == null || loanId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		Integer count = lnRepayScheduleMapper.countLateNot(loanUserId, loanId, null);
		if(count > 0){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean isLoanUserLoanTermLate(Integer loanUserId, Integer loanId,
			Integer serialId) {
		boolean flag = false;
		if(loanUserId == null || loanId == null ||serialId == null ){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		Integer count = lnRepayScheduleMapper.countLateNot(loanUserId, loanId, serialId);
		if(count > 0){
			flag = true;
		}
		return flag;
	}

	/**
	 * 计算某笔借款某期应还本金
	 */
	@Override
	public Double calPrincipal(Integer loanId, Integer serialId) {
		if(loanId == null || serialId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		LnSubjectVO subjectVo = lnSubjectMapper.selectByLoanId(loanId, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
		
		return calSubjectAmount(subjectVo,serialId);
	}

	

	/**
	 * 计算某笔借款某期应还币港湾利息
	 */
	@Override
	public Double calInterest(Integer loanId, Integer serialId) {
		if(loanId == null || serialId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		LnSubjectVO subjectVo = lnSubjectMapper.selectByLoanId(loanId, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
		
		return calSubjectAmount(subjectVo,serialId);
	}
	
	/**
	 * 计算某笔借款某期应还监管费
	 */
	@Override
	public Double calSuperviseFee(Integer loanId, Integer serialId) {
		if(loanId == null || serialId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		LnSubjectVO subjectVo = lnSubjectMapper.selectByLoanId(loanId, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_SUPERVISE_FEE.getCode());
		
		return calSubjectAmount(subjectVo,serialId);
	}

	/**
	 * 计算某笔借款某期应还信息服务费
	 */
	@Override
	public Double calInfoServiceFee(Integer loanId, Integer serialId) {
		if(loanId == null || serialId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		LnSubjectVO subjectVo = lnSubjectMapper.selectByLoanId(loanId, PartnerEnum.ZAN.getCode(),
				LoanSubjects.SUBJECT_CODE_INFO_SERVICE_FEE.getCode());
		
		return calSubjectAmount(subjectVo,serialId);
	}

	/**
	 * 计算某笔借款某期应还账户管理费
	 */
	@Override
	public Double calAccountManageFee(Integer loanId, Integer serialId) {
		if(loanId == null || serialId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		LnSubjectVO subjectVo = lnSubjectMapper.selectByLoanId(loanId, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_ACCOUNT_MANAGE_FEE.getCode());
		
		return calSubjectAmount(subjectVo,serialId);
	}

	/**
	 * 算某笔借款某期应提滞纳金
	 */
	@Override
	public Double calLateFee(Integer loanId, Integer serialId) {
		/**
		 * 月应还金额 = 本金+利息+监管费+信息服务费+账户管理费+保费
		 * 违约金 = （月应还金额–实际该期已还款金额）× 违约金系数 × 逾期天数
		 */
		if(loanId == null || serialId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		LnSubjectVO subjectVo = lnSubjectMapper.selectByLoanId(loanId, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode());
		
		LnRepayScheduleExample example = new LnRepayScheduleExample();
		example.createCriteria().andLoanIdEqualTo(loanId).andSerialIdEqualTo(serialId);
		List<LnRepaySchedule> repayScheduleList = lnRepayScheduleMapper.selectByExample(example);
		Double repayedAmount = 0d; //实际该期已还款金额
		if(CollectionUtils.isNotEmpty(repayScheduleList)){
			LnRepaySchedule repaySchedule = repayScheduleList.get(0);
			repayedAmount = lnRepayMapper.repayedAmountByLoanIdRepayPlanId(loanId, repaySchedule.getId());
		}
	
		Double rate = MoneyUtil.divide(subjectVo.getNumValue(),10000,10).doubleValue();  //违约金系数
		
		Date needRepayTime = DateUtil.addMonths(subjectVo.getLoanTime(), serialId);
		Date needRepayDate =DateUtil.parseDate(DateUtil.formatYYYYMMDD(needRepayTime));
		Date now =DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
		Integer lateDay = DateUtil.getDiffeDay(now,needRepayDate);
		Double amount = 0d;
		if(lateDay >=1){
			amount = calTotalRepayNoLate(loanId, serialId); //月应还金额
			amount = MoneyUtil.subtract(amount, repayedAmount == null ? 0d : repayedAmount).doubleValue(); //月应还本金+月应还利息实际该期已还款金额
			if(amount < 0){
				return Math.abs(amount);
			}
			//赞分期滞纳金规则自定义
			//递增规则：com.pinting.business.service.common.impl.ZanLateFeeIncreaceCalculateServiceImpl
			if (RateTypeEnum.CUSTOMIZED.getCode().equals(subjectVo.getRatetype())) {
				try {
					Class h;
					h = Class.forName(subjectVo.getCustomizedClass());
				    Object object = h.newInstance();  
				    Method m[]=h.getDeclaredMethods();  
			    	for(int i=0;i<m.length;i++){  
				          if(m[i].getName().equals("calLateFee")){  
				              amount = (Double)m[i].invoke(object,amount,lateDay,subjectVo.getReserveRule());
				              logger.info("滞纳金（单位：分）："+amount);
				          }  
			        }  
				} catch (Exception e) {
					e.printStackTrace();
					throw new PTMessageException(PTMessageEnum.ZAN_REPAY_CAl_LATE_FAIL);
				}

			}else {

				amount = MoneyUtil.multiply(amount, rate).doubleValue(); //（月应还本金+月应还利息–实际该期已还款金额）× 违约金系数
				
				//元-分
				amount = MoneyUtil.multiply(amount, 100).doubleValue();
				if(subjectVo.getReserveRule().equals(LoanSubjectRulesEnum.ReserveRuleEnum.ROUND.getCode())){
					//计数保留规则-四舍五入
					amount = (double) Math.round(amount);
				}else if(subjectVo.getReserveRule().equals(LoanSubjectRulesEnum.ReserveRuleEnum.FLOOR.getCode())){
					//计数保留规则-小数点后直接舍弃
					amount = (double) Math.floor(amount);
				}else if(subjectVo.getReserveRule().equals(LoanSubjectRulesEnum.ReserveRuleEnum.CEIL.getCode())){
					//计数保留规则-存在小数直接进位
					amount = (double) Math.ceil(amount);
				}
				
				amount = MoneyUtil.multiply(amount, lateDay).doubleValue();
			}
		}
		
		amount = MoneyUtil.divide(amount, 100, 2).doubleValue();
		
		return amount;
	}

	/**
	 * 计算某笔借款某期应还总费用
	 */
	@Override
	public Double calTotalRepay(Integer loanId, Integer serialId) {
		if(loanId == null || serialId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		
		Double principal =calPrincipal(loanId,serialId); //本金 
		Double interest =calInterest(loanId,serialId); //利息
		Double superviseFee =calSuperviseFee(loanId,serialId); //监管费
		Double infoServiceFee =calInfoServiceFee(loanId,serialId); //信息服务费
		Double accountManageFee =calAccountManageFee(loanId,serialId); //账户管理费
		Double premium = calPremium(loanId,serialId); //保费
		//Double lateFee = calLateFee(loanId,serialId); //滞纳金
		
		//滞纳金直接查询
		LnRepayScheduleExample example = new LnRepayScheduleExample();
		example.createCriteria().andLoanIdEqualTo(loanId).andSerialIdEqualTo(serialId);
		List<LnRepaySchedule> lnRepaySchedules = lnRepayScheduleMapper.selectByExample(example);
		
		List<String> repayStatus = new ArrayList<>();
		repayStatus.add(Constants.LN_REPAY_REPAIED);
		repayStatus.add(Constants.LN_REPAY_LATE_REPAIED);
		LnRepayExample lnRepayExample = new LnRepayExample();
		lnRepayExample.createCriteria().andRepayPlanIdEqualTo(lnRepaySchedules.get(0).getId()).andStatusIn(repayStatus);
		List<LnRepay> lnRepays = lnRepayMapper.selectByExample(lnRepayExample);
		
		
		LnRepayDetailExample lDetailExample = new LnRepayDetailExample();
		lDetailExample.createCriteria().andRepayIdEqualTo(lnRepays.get(0).getId()).andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode());
		List<LnRepayDetail> lnRepayDetails = lnRepayDetailMapper.selectByExample(lDetailExample);
		Double lateFee = lnRepayDetails.get(0).getDoneAmount();
		
		Double amount = MoneyUtil.add(principal, interest).doubleValue();
		amount = MoneyUtil.add(amount, superviseFee).doubleValue();
		amount = MoneyUtil.add(amount, infoServiceFee).doubleValue();
		amount = MoneyUtil.add(amount, accountManageFee).doubleValue();
		amount = MoneyUtil.add(amount, premium).doubleValue();
		amount = MoneyUtil.add(amount, lateFee).doubleValue();
		
		return amount;
	}

	/**
	 * rateType 为非CUSTOMIZED的科目金额计算
	 * @param subjectVo
	 * @param serialId
	 * @return
	 */
	private Double calSubjectAmount(LnSubjectVO subjectVo, Integer serialId) {
		if(subjectVo.getRatetype().equals(LoanSubjectRulesEnum.RateTypeEnum.CUSTOMIZED)){
			logger.error("============="+subjectVo.getSubjectCode()+"该科目rateType配置错误================");
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		//返回值
		Double subjectAmount = 0d;
		
		if(subjectVo.getRatetype().equals(LoanSubjectRulesEnum.RateTypeEnum.FIXED.getCode())){
			//比率类型-固定值
			subjectAmount = subjectVo.getNumValue();
		}else if(subjectVo.getRatetype().equals(LoanSubjectRulesEnum.RateTypeEnum.RATE.getCode())){
			//比率类型-按比率收取
			//总借款金额-分
			Double totalAmout = MoneyUtil.multiply(subjectVo.getApproveAmount(),100).doubleValue();
			if(StringUtil.isEmpty(subjectVo.getCalRule())){
				//分期计算规则-null-获取利率
				Double rate = MoneyUtil.divide(subjectVo.getNumValue(),10000,10).doubleValue();
				subjectAmount = MoneyUtil.multiply(totalAmout, rate).doubleValue();
			}else if(subjectVo.getCalRule().equals(LoanSubjectRulesEnum.CalRuleEnum.BEFORE.getCode())){
				//分期计算规则-先把本金分期，然后按照费率进行计算具体金额
				//每期平均借款金额
				Double termAvgAmount = MoneyUtil.divide(totalAmout, subjectVo.getTerm().doubleValue()).doubleValue();
				if(subjectVo.getReserveRule().equals(LoanSubjectRulesEnum.ReserveRuleEnum.ROUND.getCode())){
					//计数保留规则-四舍五入
					termAvgAmount = (double) Math.round(termAvgAmount);
				}else if(subjectVo.getReserveRule().equals(LoanSubjectRulesEnum.ReserveRuleEnum.FLOOR.getCode())){
					//计数保留规则-小数点后直接舍弃
					termAvgAmount = Math.floor(termAvgAmount);
				}else if(subjectVo.getReserveRule().equals(LoanSubjectRulesEnum.ReserveRuleEnum.CEIL.getCode())){
					//计数保留规则-存在小数直接进位
					termAvgAmount = Math.ceil(termAvgAmount);
				}
				Double rate = MoneyUtil.divide(subjectVo.getNumValue(),10000,10).doubleValue();
				//平均借款金额计算后的值
				Double subjectAvgAmount = MoneyUtil.multiply(termAvgAmount, rate).doubleValue();
				//总借款金额计算后的值
				Double subjectSumAmount = MoneyUtil.multiply(totalAmout, rate).doubleValue();
				if(subjectVo.getLeftRule().equals(LeftRuleEnum.FIRST.getCode())){
					//余数规则-本金分期后多余的金额加到第一期
					if(serialId == 1){
						//另term-1个周期的总和本金
						Double otherTermAmount = MoneyUtil.multiply(subjectAvgAmount, (subjectVo.getTerm()-1)).doubleValue();
						subjectAmount = MoneyUtil.subtract(subjectSumAmount,otherTermAmount).doubleValue();
					}else{
						subjectAmount = subjectAvgAmount;
					}
				}else if(subjectVo.getLeftRule().equals(LeftRuleEnum.LAST.getCode())){
					//余数规则-本金分期后多余的金额加到最后一期
					if(serialId == subjectVo.getTerm()){
						//另term-1个周期的总和本金
						Double otherTermAmount = MoneyUtil.multiply(subjectAvgAmount, (subjectVo.getTerm()-1)).doubleValue();
						subjectAmount = MoneyUtil.subtract(subjectSumAmount,otherTermAmount).doubleValue();
					}else{
						subjectAmount = subjectAvgAmount;
					}
				}
				
			}else if(subjectVo.getCalRule().equals(LoanSubjectRulesEnum.CalRuleEnum.AFTER.getCode())){
				//先按照费率进行计算具体金额然后再进行分期
				Double rate = MoneyUtil.divide(subjectVo.getNumValue(),10000,10).doubleValue();
				//总借款金额计算后的值
				Double subjectSumAmount = MoneyUtil.multiply(totalAmout, rate).doubleValue();
				//平均借款金额计算后的值
				Double subjectAvgAmount = MoneyUtil.divide(subjectSumAmount, subjectVo.getTerm().doubleValue()).doubleValue();
				
				if(subjectVo.getReserveRule().equals(LoanSubjectRulesEnum.ReserveRuleEnum.ROUND.getCode())){
					//计数保留规则-四舍五入
					subjectSumAmount = (double) Math.round(subjectSumAmount);
					subjectAvgAmount = (double) Math.round(subjectAvgAmount);
				}else if(subjectVo.getReserveRule().equals(LoanSubjectRulesEnum.ReserveRuleEnum.FLOOR.getCode())){
					//计数保留规则-小数点后直接舍弃
					subjectSumAmount = Math.floor(subjectSumAmount);
					subjectAvgAmount = Math.floor(subjectAvgAmount);
				}else if(subjectVo.getReserveRule().equals(LoanSubjectRulesEnum.ReserveRuleEnum.CEIL.getCode())){
					//计数保留规则-存在小数直接进位
					subjectSumAmount = Math.ceil(subjectSumAmount);
					subjectAvgAmount = Math.ceil(subjectAvgAmount);
				}
				
				if(subjectVo.getLeftRule().equals(LeftRuleEnum.FIRST.getCode())){
					//余数规则-本金分期后多余的金额加到第一期
					if(serialId == 1){
						//另term-1个周期的总和本金
						Double otherTermAmount = MoneyUtil.multiply(subjectAvgAmount, (subjectVo.getTerm()-1)).doubleValue();
						subjectAmount = MoneyUtil.subtract(subjectSumAmount,otherTermAmount).doubleValue();
					}else{
						subjectAmount = subjectAvgAmount;
					}
				}else if(subjectVo.getLeftRule().equals(LeftRuleEnum.LAST.getCode())){
					//余数规则-本金分期后多余的金额加到最后一期
					if(serialId == subjectVo.getTerm()){
						//另term-1个周期的总和本金
						Double otherTermAmount = MoneyUtil.multiply(subjectAvgAmount, (subjectVo.getTerm()-1)).doubleValue();
						subjectAmount = MoneyUtil.subtract(subjectSumAmount,otherTermAmount).doubleValue();
					}else{
						subjectAmount = subjectAvgAmount;
					}
				}
				
			}
			
		}
		
		subjectAmount = MoneyUtil.divide(subjectAmount, 100, 2).doubleValue();
		return subjectAmount;
	}


	/**
	 * 根据本金计算总的保费金额
	 * ［max(ceil(本金/10000)*25,30)］（向上取整）
	 * @param approveAmount
	 * @return
	 */
	private Double getTotalPremium(Double approveAmount) {
		Double amount = Math.ceil(MoneyUtil.divide(approveAmount, 10000).doubleValue());
		amount = Math.max(MoneyUtil.multiply(amount, 25).doubleValue(), 30);
		return amount;
	}

	/**
	 * 计算某笔借款某期应还保费
	 */
	@Override
	public Double calPremium(Integer loanId, Integer serialId) {
		LnSubjectVO subjectVo = lnSubjectMapper.selectByLoanId(loanId, PartnerEnum.ZAN.getCode(), 
				LoanSubjects.SUBJECT_CODE_PREMIUM.getCode());
		//总保费-分
		Double totalPremium = MoneyUtil.multiply(getTotalPremium(subjectVo.getApproveAmount()), 100).doubleValue();
		//每期平均保费
		Double avgPremium = MoneyUtil.divide(totalPremium, subjectVo.getTerm()).doubleValue();
		
		if(subjectVo.getReserveRule().equals(LoanSubjectRulesEnum.ReserveRuleEnum.ROUND.getCode())){
			//计数保留规则-四舍五入
			avgPremium = (double) Math.round(avgPremium);
		}else if(subjectVo.getReserveRule().equals(LoanSubjectRulesEnum.ReserveRuleEnum.FLOOR.getCode())){
			//计数保留规则-小数点后直接舍弃
			avgPremium = (double) Math.floor(avgPremium);
		}else if(subjectVo.getReserveRule().equals(LoanSubjectRulesEnum.ReserveRuleEnum.CEIL.getCode())){
			//计数保留规则-存在小数直接进位
			avgPremium = (double) Math.ceil(avgPremium);
		}
		Double premium = avgPremium;
		if(subjectVo.getLeftRule().equals(LeftRuleEnum.FIRST.getCode())){
			//余数规则-本金分期后多余的金额加到第一期
			if(serialId == 1){
				//另term-1个周期的总和本金
				Double otherTermAmount = MoneyUtil.multiply(avgPremium, (subjectVo.getTerm()-1)).doubleValue();
				premium = MoneyUtil.subtract(totalPremium,otherTermAmount).doubleValue();
			}
		}else if(subjectVo.getLeftRule().equals(LeftRuleEnum.LAST.getCode())){
			//余数规则-本金分期后多余的金额加到最后一期
			if(serialId == subjectVo.getTerm()){
				//另term-1个周期的总和本金
				Double otherTermAmount = MoneyUtil.multiply(avgPremium, (subjectVo.getTerm()-1)).doubleValue();
				premium = MoneyUtil.subtract(totalPremium,otherTermAmount).doubleValue();
			}
		}
		premium = MoneyUtil.divide(premium, 100, 2).doubleValue();
		return premium;
	}
	@Override
	public ZANRevenueModelVO calTotalRepay4ZANRevenue(Integer loanId,
			Integer serialId) {
		if(loanId == null || serialId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		
		ZANRevenueModelVO zANRevenueModelVO = new ZANRevenueModelVO();
		
		Double principal =calPrincipal(loanId,serialId); //本金 
		Double interest =calInterest(loanId,serialId); //利息
		Double superviseFee =calSuperviseFee(loanId,serialId); //监管费
		Double infoServiceFee =calInfoServiceFee(loanId,serialId); //信息服务费
		Double accountManageFee =calAccountManageFee(loanId,serialId); //账户管理费
		Double premium = calPremium(loanId,serialId); //保费
		//Double lateFee = calLateFee(loanId,serialId); //滞纳金
		//滞纳金直接查询
		LnRepayScheduleExample example = new LnRepayScheduleExample();
		example.createCriteria().andLoanIdEqualTo(loanId).andSerialIdEqualTo(serialId);
		List<LnRepaySchedule> lnRepaySchedules = lnRepayScheduleMapper.selectByExample(example);
		
		List<String> repayStatus = new ArrayList<>();
		repayStatus.add(Constants.LN_REPAY_REPAIED);
		repayStatus.add(Constants.LN_REPAY_LATE_REPAIED);
		
		LnRepayExample lnRepayExample = new LnRepayExample();
		lnRepayExample.createCriteria().andRepayPlanIdEqualTo(lnRepaySchedules.get(0).getId()).andStatusIn(repayStatus);
		List<LnRepay> lnRepays = lnRepayMapper.selectByExample(lnRepayExample);
		
		
		LnRepayDetailExample lDetailExample = new LnRepayDetailExample();
		lDetailExample.createCriteria().andRepayIdEqualTo(lnRepays.get(0).getId()).andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_LATE_FEE.getCode());
		List<LnRepayDetail> lnRepayDetails = lnRepayDetailMapper.selectByExample(lDetailExample);
		
		Double lateFee = lnRepayDetails.get(0).getDoneAmount();
		
		Double amount = MoneyUtil.add(principal, interest).doubleValue();
		amount = MoneyUtil.add(amount, superviseFee).doubleValue();
		amount = MoneyUtil.add(amount, infoServiceFee).doubleValue();
		amount = MoneyUtil.add(amount, accountManageFee).doubleValue();
		amount = MoneyUtil.add(amount, premium).doubleValue();
		amount = MoneyUtil.add(amount, lateFee).doubleValue();
		
		zANRevenueModelVO.setPrincipal(principal);
		zANRevenueModelVO.setInterest(interest);
		zANRevenueModelVO.setSuperviseFee(superviseFee);
		zANRevenueModelVO.setInfoServiceFee(infoServiceFee);
		zANRevenueModelVO.setAccountManageFee(accountManageFee);
		zANRevenueModelVO.setPremium(premium);
		zANRevenueModelVO.setLateFee(lateFee);
		zANRevenueModelVO.setTotalRepay(amount);
		
		return zANRevenueModelVO;
	}
	
	@Override
	public Double calTotalRepayNoLate(Integer loanId, Integer serialId) {
		if(loanId == null || serialId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		
		Double principal =calPrincipal(loanId,serialId); //本金 
		Double interest =calInterest(loanId,serialId); //利息
		Double superviseFee =calSuperviseFee(loanId,serialId); //监管费
		Double infoServiceFee =calInfoServiceFee(loanId,serialId); //信息服务费
		Double accountManageFee =calAccountManageFee(loanId,serialId); //账户管理费
		Double premium = calPremium(loanId,serialId); //保费
		
		Double amount = MoneyUtil.add(principal, interest).doubleValue();
		amount = MoneyUtil.add(amount, superviseFee).doubleValue();
		amount = MoneyUtil.add(amount, infoServiceFee).doubleValue();
		amount = MoneyUtil.add(amount, accountManageFee).doubleValue();
		amount = MoneyUtil.add(amount, premium).doubleValue();
		
		return amount;
	}

	@Override
	public LnBindCard queryIncrBindCardExist(String userId, String channel, String bankCard) {
		//根据合作方用户id,查询用户信息
		LnUser lnUser = queryLoanUserExist(userId, channel);

		if (lnUser != null) {
			//根据币港湾借款用户人id和币港湾绑卡id查询用户是否绑卡成功
			LnBindCardExample example = new LnBindCardExample();
			example.createCriteria().andLnUserIdEqualTo(lnUser.getId()).andBankCardEqualTo(bankCard).andStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode());
			example.setOrderByClause("update_time desc");
			List<LnBindCard> list = lnBindCardMapper.selectByExample(example);
			if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} else {
			throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_USER_NOT_EXIST);
		}
	}
	@Override
	public String queryBindCardExist(String idCard) {
		String  hfPlatCust = null;
		BsUserExample example = new BsUserExample();
		example.createCriteria().andIdCardEqualTo(idCard).andStatusEqualTo(Constants.USER_STATUS_1);
		List<BsUser> userList = bsUserMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(userList)) {
			BsHfbankUserExtExample  bsHfbankUserExtExample = new BsHfbankUserExtExample();
			bsHfbankUserExtExample.createCriteria().andUserIdEqualTo(userList.get(0).getId()).andStatusEqualTo(Constants.HFBANK_USER_EXT_STATUS_OPEN);
			List<BsHfbankUserExt> bsHfbankUserExts = bsHfbankUserExtMapper.selectByExample(bsHfbankUserExtExample);
			if (CollectionUtils.isNotEmpty(bsHfbankUserExts)) {
				hfPlatCust = bsHfbankUserExts.get(0).getHfUserId();
				logger.info("查询到用户已经作为投资人已经绑卡，获得平台客户号："+hfPlatCust);
				return hfPlatCust;
			}
		}
		
		LnUserExample lnUserExample = new LnUserExample();
		lnUserExample.createCriteria().andIdCardEqualTo(idCard).andPartnerCodeEqualTo(Constants.PROPERTY_SYMBOL_ZAN);
		List<LnUser> lnUsers = lnUserMapper.selectByExample(lnUserExample);
		if (CollectionUtils.isNotEmpty(lnUsers)) {
			hfPlatCust = lnUsers.get(0).getHfUserId();
			logger.info("查询到用户已经作为赞分期融资人已经绑卡，获得平台客户号："+hfPlatCust);
			return hfPlatCust;
		}
		return null;
	}

	@Override
	public LnUser queryLoanUserByHfUserId(String hfUserId) {
		LnUserExample example = new LnUserExample();
		example.createCriteria().andHfUserIdEqualTo(hfUserId);
		List<LnUser> list = lnUserMapper.selectByExample(example);
		if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public LnBindCard selectLoanBindCardExist(Integer userId) {
		//查询用户信息
		LnUser lnUser = lnUserMapper.selectByPrimaryKey(userId);
		if (lnUser != null) {
			//根据币港湾借款用户人id查询用户是否绑卡成功
			LnBindCardExample example = new LnBindCardExample();
			example.createCriteria().andLnUserIdEqualTo(lnUser.getId()).andStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode());
			List<LnBindCard> list = lnBindCardMapper.selectByExample(example);
			if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} else {
			throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_USER_NOT_EXIST);
		}
	}

}
