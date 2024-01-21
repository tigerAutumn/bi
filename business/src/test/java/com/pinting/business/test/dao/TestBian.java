package com.pinting.business.test.dao;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.pinting.business.accounting.loan.enums.LoanSubjects;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.accounting.loan.service.impl.LoanRelationshipServiceImpl;
import com.pinting.business.dao.BsMatchMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.LnCompensateAgreementAddressMapper;
import com.pinting.business.dao.LnCompensateRelationMapper;
import com.pinting.business.dao.LnDepositionRepayScheduleMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.dao.LnRepayScheduleDetailMapper;
import com.pinting.business.dao.LnRepayScheduleMapper;
import com.pinting.business.dao.LnUserMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsLoanRelativeRecord;
import com.pinting.business.model.BsMatch;
import com.pinting.business.model.BsMatchExample;
import com.pinting.business.model.BsMatchRepayDetail;
import com.pinting.business.model.BsProductInform;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.LnDepositionRepaySchedule;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnLoanExample;
import com.pinting.business.model.LnRepayScheduleDetail;
import com.pinting.business.model.LnRepayScheduleDetailExample;
import com.pinting.business.model.LnUser;
import com.pinting.business.model.vo.BsCanMatch4ZanSubAccountVO;
import com.pinting.business.model.vo.BsMatchVO;
import com.pinting.business.model.vo.BsMatchWarnVO;
import com.pinting.business.model.vo.BsProductUserVO;
import com.pinting.business.model.vo.HFBalanceDetailVO;
import com.pinting.business.service.loan.impl.LateRepayAgreementServiceImpl;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.site.ActivityLuckyDrawService;
import com.pinting.business.service.site.BsLoanRelationShipService;
import com.pinting.business.service.site.BsMatchService;
import com.pinting.business.service.site.BsProductInformService;
import com.pinting.business.service.site.ProductService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.test.TestBase;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.model.DebtTransferInfo;
import com.pinting.gateway.hessian.message.dafy.model.DebtTransfers;
import com.pinting.gateway.out.service.loan.DafyNoticeService;

public class TestBian extends TestBase {
	private Logger log = LoggerFactory.getLogger(TestBian.class);
	@Autowired
	private BsProductInformService bsProductInformService;
	@Autowired
	private ProductService productService;
	@Autowired
	private BsMatchService bsMatchService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private BsMatchMapper bsMatchMapper;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private LnLoanRelationMapper lnLoanRelationMapper;
	@Autowired
	private ActivityLuckyDrawService activityLuckyDrawService;
	@Autowired
	private LnDepositionRepayScheduleMapper lnDepositionRepayScheduleMapper;
	@Autowired
	private LnRepayScheduleDetailMapper lnRepayScheduleDetailMapper;
	@Autowired
	private LnLoanMapper lnLoanMapper;
	@Autowired
	private LnUserMapper lnUserMapper;
	@Autowired
	private BsLoanRelationShipService bsLoanRelationShipService;
	@Autowired
	private DafyNoticeService dafyNoticeService;
    @Autowired
    private LnCompensateAgreementAddressMapper lnCompensateAgreementAddressMapper;
    @Autowired
    private	DepFixedRepayPaymentService depFixedRepayPaymentService;
    @Autowired
    private LnRepayScheduleMapper lnRepayScheduleMapper;
    @Autowired
    private LnCompensateRelationMapper lnCompensateRelationMapper;
	@Autowired
	private LoanRelationshipService loanRelationshipService;
    
	@Test
	public void testInsert(){
		
		try {
			String aa = "com.pinting.business.service.common.impl.ZanLateFeeIncreaceCalculateServiceImpl";
			Class h;
			h = Class.forName(aa);
		    Object object = h.newInstance();  
		    Method m[]=h.getDeclaredMethods();  
	    	for(int i=0;i<m.length;i++){  
		          if(m[i].getName().equals("calLateFee")){  
		             Double amount = (Double)m[i].invoke(object,1000,3,"ROUND");
		             System.out.println("滞纳金（单位：分）："+amount);
		          }  
	        }  
			/*String aa = "{\"balance\":\"0.0000\",\"frozen_amount\":\"0.0000\"}";
			System.out.println(aa);
			HFBalanceDetailVO vo =  JSON.parseObject(aa,HFBalanceDetailVO.class);
			
			System.out.println(vo.getBalance());
			System.out.println(vo.getFrozen_amount());*/
			/*List<Integer> superList = loanRelationshipService.getSuperUserList();
			String interestBeginDate = DateUtil.formatYYYYMMDD(DateUtil.parseDate("2017-06-02"));
			
			List<BsCanMatch4ZanSubAccountVO> canMatchList = bsSubAccountMapper.canMatch4ZanListDepStage(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN,
                    Constants.PRODUCT_TYPE_AUTH, superList, interestBeginDate, 3, "no", 0d);
			
			
			
			BsCanMatch4ZanSubAccountVO subAccountRecord = bsSubAccountMapper.getCanMatch4Zan2TransferNew(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN,
                    Constants.PRODUCT_TYPE_AUTH, superList, interestBeginDate, 3, 1000d, "desc");
			System.out.println(subAccountRecord.getHfUserId());*/
			/*String parLoanId = "40000001464";
			//获取借款信息
	    	LnLoanExample example = new LnLoanExample();
	    	example.createCriteria().andPartnerLoanIdEqualTo(parLoanId);
	    	List<LnLoan> lnLoanList = lnLoanMapper.selectByExample(example);
	    	if(CollectionUtils.isEmpty(lnLoanList)){
	    		throw new PTMessageException(PTMessageEnum.LOAN_DATA_NOT_FOUND,"云贷协议下载地址查询时借款信息未找到");
	    	}
	    	LnLoan lnLoan = lnLoanList.get(0);
	    	//获取借款用户信息
	    	LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());
	    	
	    	System.out.println(parLoanId);
	    	System.out.println(lnUser.getUserName());
	    	System.out.println(lnUser.getIdCard());
	    	//收款确认函（服务费）下载地址赋值
	    	List<String>  file1AddrList = bsLoanRelationShipService.queryCompenAgreeAddressList(PartnerEnum.YUN_DAI_SELF.getCode(),
	    			SealBusiness.SERVICE_FEE_CONFIRM.getCode(), parLoanId);
	    	if(CollectionUtils.isEmpty(file1AddrList)){
	    		throw new PTMessageException(PTMessageEnum.DAFY_LATE_REPAY_AGREEMENT_NOT_EXIST,"代偿协议地址(服务费收款确认函)不存在");
	    	}
	    	System.out.println(file1AddrList.get(0));
	    	//收款确认函（债转）下载地址赋值
	    	List<String>  file2AddrList = bsLoanRelationShipService.queryCompenAgreeAddressList(PartnerEnum.YUN_DAI_SELF.getCode(),
	    			SealBusiness.DEBT_TRANS_CONFIRM.getCode(), parLoanId);
	    	if(CollectionUtils.isEmpty(file2AddrList)){
	    		throw new PTMessageException(PTMessageEnum.DAFY_LATE_REPAY_AGREEMENT_NOT_EXIST,"偿协议地址(债转收款确认函)不存在");
	    	}
	    	System.out.println(file2AddrList.get(0));
	    	//债权转让通知书下载地址赋值
	    	List<String>  file3AddrList = bsLoanRelationShipService.queryCompenAgreeAddressList(PartnerEnum.YUN_DAI_SELF.getCode(),
	    			SealBusiness.DEBT_TRANS_NOTICES.getCode(), parLoanId);
	    	if(CollectionUtils.isEmpty(file3AddrList)){
	    		throw new PTMessageException(PTMessageEnum.DAFY_LATE_REPAY_AGREEMENT_NOT_EXIST,"代偿协议地址(债权转让通知书)不存在");
	    	}
	    	System.out.println(file3AddrList.get(0));
	    	//债权转让信息赋值
	    	//获取计划还款最后一期合作方还款编号
	    	String partnerRepayId = lnRepayScheduleMapper.selectLastPeriodPartnerRepayId(lnLoan.getId());
	    	if(StringUtil.isEmpty(partnerRepayId)){
	    		throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,"获取计划还款最后一期合作方还款编号为空");
	    	}
	    	List<DebtTransferInfo> debtTransferInfoList = bsLoanRelationShipService.queryTransferInfo(parLoanId);
	    	System.out.println(debtTransferInfoList);
	    	//债权转让协议下载地址列表赋值
	    	List<String>  fileAddrList = bsLoanRelationShipService.queryCompenAgreeAddressList(PartnerEnum.YUN_DAI_SELF.getCode(),
	    			SealBusiness.DEBT_TRANSFER.getCode(), parLoanId);
	    	List<DebtTransfers> debtTransferList = new ArrayList<DebtTransfers>();
	        
	    	for(int i=0;i<fileAddrList.size();i++){
	    		DebtTransfers debtTransfers = new DebtTransfers();
	    		debtTransfers.setDebtTransferUrl(fileAddrList.get(i));
	            debtTransferList.add(debtTransfers);
	    	}
	    	System.out.println(debtTransferList);*/
/*
			//还款本金
			Double repayPrincipal = getRepayDetailAmount(14877, LoanSubjects.SUBJECT_CODE_PRINCIPAL);
			//计息本金
			Double P_amount = repayPrincipal == 0 ? 
			lnLoanRelationMapper.getRelationAmountNotRepayingByLoanId(1548):repayPrincipal;
			System.out.println("==================【YUN主动、代扣还款系统分账】计息本金："+P_amount+"=======================");*/
			/* List<LnDepositionRepaySchedule> repaySchedulesDFPending = lnDepositionRepayScheduleMapper.getLimitDesList(Constants.DEP_REPAY_RETURN_FLAG_DF_PENDING, Constants.DEP_REQUEST_ITEM_ACCOUNT);
			System.out.println(repaySchedulesDFPending.size());*/
			/*Date interestBeginDate = DateUtil.addDays(DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date())), -1); 
			List<Map<String, Object>> noMatchMaps = bsSubAccountMapper.getNoMatchList(interestBeginDate,Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI);
			if(!noMatchMaps.isEmpty()){
				log.info("==========定时任务【债权关系匹配-新】统计日债权匹配差额超100：共" + noMatchMaps.size() + "天==========");
				Double matchedPercent = 0.7;//匹配金额占投资金额的比例
				Double topBalance = 10000d;//已匹配金额的下线
				
				for(Map<String, Object> map : noMatchMaps){
					List<BsMatchVO> mList = new ArrayList<BsMatchVO>(); //记录可以分配的债权数据
					
					Date time = (Date) map.get("interestBeginDate"); //投资起息日
					
					//判断是否有需要再次分配的未完全匹配的债权列表
					List<Map<String, Object>> needMatch4DayMaps = bsSubAccountMapper.getNeedMatchList(time, matchedPercent,0.3,Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI);
					if(needMatch4DayMaps.isEmpty()){
						log.info("==========定时任务【债权关系匹配-新】" + DateUtil.formatYYYYMMDD(time) + "债权无需再次匹配==========");
						continue;
					}else{
						log.info("==========定时任务【债权关系匹配-新】" + DateUtil.formatYYYYMMDD(time) + "获得" +needMatch4DayMaps.size()+ "笔债权需再次匹配==========");
					}
					
					//Double sumBalance = (Double) map.get("sumBalance"); //未匹配对应的投资总额
					Double sumNoMacthAmount = (Double) map.get("sumAmount"); //未匹配的总金额
					Double sumInvestBalance = bsSubAccountMapper.sumInvestBalanceByDate(time,Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI); //某日当前投资总额
					
					String value = sysConfigService.findConfigByKey(Constants.MATCH_PATCH_ADD_PERCENT).getConfValue(); //固定比例，用于分配债权
					Double percent = MoneyUtil.divide(sumNoMacthAmount, sumInvestBalance).doubleValue();//未匹配金额和当日投资总额的比例
					if(percent < 1){
						//不是所有投资都未匹配
						percent = (double)(percent+Double.valueOf(value)/100); //未匹配金额和当日投资总额的比例+固定比例
						Integer amount4Day = (int)(percent*(sumInvestBalance-sumNoMacthAmount)); //某日待转让的匹配金额
						
						//根据起息时间查询已经完全匹配的和匹配金额超过一定比例的列表且已匹配金额超过一定值
						List<Map<String, Object>> matchedMaps = bsSubAccountMapper.getMatchedList(time, matchedPercent, topBalance, Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI);
						if(!matchedMaps.isEmpty()){
							log.info("==========定时任务【债权关系匹配-新】" + DateUtil.formatYYYYMMDD(time) + "查到"+matchedMaps.size()+"笔理财可转让债权匹配==========");
							
							for(Map<String, Object> matched : matchedMaps){
								
								Integer amount4Day4Sub  =(int) ((Double)matched.get("amount")*percent); //针对该笔投资所需要转让的债权金额
								if(amount4Day4Sub == 0){
									continue;
								}
								if(amount4Day4Sub < 10){
									amount4Day4Sub = 10;
								}
								if(amount4Day == 0){
									break;
								}else if(amount4Day < amount4Day4Sub){
									amount4Day4Sub = amount4Day;
								}
								if(amount4Day4Sub > (Double)matched.get("amount")){
									amount4Day4Sub = ((Double)matched.get("amount")).intValue();
								}
								
								
								//根据subAccountId查询已经匹配的匹配数据
								BsMatchExample example = new BsMatchExample();
								example.createCriteria().andSubAccountIdEqualTo((Integer)matched.get("sub_account_id"))
									.andRepayStatusNotEqualTo("3");
								example.setOrderByClause("left_principal desc");
								List<BsMatch> matchList = bsMatchMapper.selectByExample(example);
								if(matchList != null && matchList.size() > 0){
									for (BsMatch bsMatch : matchList) {
										Double mAmount = bsMatch.getAmount();
										BsMatchVO toOtherMatch = new BsMatchVO();
										toOtherMatch.setAmount(bsMatch.getAmount());
										toOtherMatch.setId(bsMatch.getId());
										toOtherMatch.setLastRepayDate(bsMatch.getLastRepayDate());
										toOtherMatch.setLeftPrincipal(bsMatch.getLeftPrincipal());
										toOtherMatch.setLoanRelativeId(bsMatch.getLoanRelativeId());
										toOtherMatch.setMatchDate(bsMatch.getMatchDate());
										toOtherMatch.setNote(bsMatch.getNote());
										toOtherMatch.setRepayAmount(bsMatch.getRepayAmount());
										toOtherMatch.setRepayStatus(bsMatch.getRepayStatus());
										toOtherMatch.setSubAccountId(bsMatch.getSubAccountId());
										toOtherMatch.setUpdateTime(bsMatch.getUpdateTime());
										toOtherMatch.setUserId(bsMatch.getUserId());
										if(mAmount>=amount4Day4Sub){
											//表示该笔匹配记录即可满足所需的转出金额
											toOtherMatch.setToOtherAmount(Double.valueOf(amount4Day4Sub));
											mList.add(toOtherMatch);
											amount4Day = amount4Day - amount4Day4Sub;
											break;
										}else{
											amount4Day4Sub = amount4Day4Sub-mAmount.intValue();
											toOtherMatch.setToOtherAmount(Double.valueOf(mAmount));
											mList.add(toOtherMatch);
											amount4Day = amount4Day - mAmount.intValue();
										}
									}
								}
							}
						}
					
					}
					if(mList != null && mList.size() > 0){
						log.info("==========定时任务【债权关系匹配-新】" + DateUtil.formatYYYYMMDD(time) + "获得"+mList.size()+"笔匹配明细 待执行关系转让==========");
						//有可以分配的债权
						//查询需要获得债权的列表；
						for (BsMatchVO match : mList) {
							Double beMatchAmount = 0d;//某日，补丁操作中已匹配金额
							//根据time查询某日未匹配的列表、投资金额小于5000的或（投资金额大于5000且匹配投资比例不超过30%）的列表，按投资金额从小到大
							List<Map<String, Object>> needMatchMaps = bsSubAccountMapper.getNeedMatchList(time, matchedPercent,0.3, Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI);
							if(!needMatchMaps.isEmpty()){
								Double canMatchAmount = match.getToOtherAmount(); //可以分配的债权金额
								for (Map<String, Object> map2 : needMatchMaps) {
									if(canMatchAmount>0){
										Double needMatchAmount = (Double) map2.get("amount"); //需要分配的债权金额
										Double balance = (Double) map2.get("balance");  //投资金额
										Integer amount4DayInit = (int)(percent*(sumInvestBalance-sumNoMacthAmount)); //某日待转让的匹配金额
										
										if(amount4DayInit < sumNoMacthAmount && balance > 5000){
											//可以分配的债权金额<未匹配金额且投资金额大于5000
											Double p = (MoneyUtil.subtract(amount4DayInit, beMatchAmount).doubleValue())/
													(MoneyUtil.subtract(sumNoMacthAmount, beMatchAmount).doubleValue());
											int needMatchAmountTemp =  (int) (p*needMatchAmount);
											if(needMatchAmountTemp == 0){
												break;
											}else{
												needMatchAmount = (double) needMatchAmountTemp;
											}
										}
										
										Integer subAccountId = (Integer) map2.get("id"); 
										Integer userId = (Integer) map2.get("user_id"); 
										//进行一系列的匹配操作
										if(canMatchAmount > needMatchAmount){
											toMatch(match,needMatchAmount,subAccountId,userId);
											canMatchAmount = MoneyUtil.subtract(canMatchAmount, needMatchAmount).doubleValue();
											beMatchAmount = MoneyUtil.add(beMatchAmount, needMatchAmount).doubleValue();
										}else{
											toMatch(match,canMatchAmount,subAccountId,userId);
											beMatchAmount = MoneyUtil.add(beMatchAmount, canMatchAmount).doubleValue();
											canMatchAmount = 0d;
										}
										
									}else{
										break;
									}
								}
							}else{
								break;
							}
						}
					}
				}
				
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private Double getRepayDetailAmount(int repayScheduleId, LoanSubjects subjectCode) {
		LnRepayScheduleDetailExample example = new LnRepayScheduleDetailExample();
		example.createCriteria().andPlanIdEqualTo(repayScheduleId).andSubjectCodeEqualTo(subjectCode.getCode());
		List<LnRepayScheduleDetail> list = lnRepayScheduleDetailMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0).getPlanAmount();
		}
		return 0d;
	}

	private void toMatch(BsMatchVO match, Double matchAmount,
			Integer subAccountId, Integer userId) {
		
		//还款
		bsMatchService.modifyMatch4Repay(match, matchAmount);
		//新增BsMatch表
		BsMatch bsMatch = new BsMatch();
		bsMatch.setAmount(matchAmount);
		bsMatch.setRepayAmount(0.0);
		bsMatch.setLeftPrincipal(matchAmount);
		bsMatch.setRepayStatus(Constants.BORROW_ING); //借款中
		bsMatch.setLoanRelativeId(match.getLoanRelativeId());
		bsMatch.setSubAccountId(subAccountId);
		bsMatch.setUserId(userId);
		bsMatch.setNote("借款中");
		bsMatchService.addBsMatch(bsMatch);	
			
	}
}
