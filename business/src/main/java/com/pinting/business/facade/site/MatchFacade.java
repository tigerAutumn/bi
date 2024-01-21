package com.pinting.business.facade.site;

import com.pinting.business.accounting.loan.service.DepFixedLoanRelationshipService;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.service.loan.BsUserSealFileService;
import com.pinting.business.service.manage.BsSubAccountService;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.site.*;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.business.util.loan7.CalBillPeriod;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 债权匹配相关
 * @author bianyatian
 * @2016-4-21 下午5:59:00
 */
@Component("Match")
public class MatchFacade {
	
	@Autowired
	private BsMatchService bsMatchService;
	@Autowired
	private RegularSiteService regularSiteService;
	@Autowired
	private ProductService productService;
	@Autowired
	private BsSubAccountService bsSubAccountService;
	@Autowired
	private SubAccountService subAccountService;
	@Autowired
	private BsLoanRelationShipService bsLoanRelationShipService;
	@Autowired
	private BsLoanFeeService bsLoanFeeService;
	@Autowired
	private AlgorithmService algorithmService;
	@Autowired
	private BsUserSealFileService bsUserSealFileService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private DepFixedLoanRelationshipService depFixedLoanRelationshipService;
	@Autowired
	private LnFinanceRepayScheduleMapper lnFinanceRepayScheduleMapper;
	@Autowired
	private LnLoanAmountChangeMapper lnLoanAmountChangeMapper;
	@Autowired
	private BsSysConfigService bsSysConfigService ;
	@Autowired
	private AgreementVersionService agreementVersionService;
	@Autowired
	private LnLoanMapper lnLoanMapper;
	@Autowired
	private LnUserMapper lnUserMapper;
	@Autowired
	private LnCompensateRelationMapper lnCompensateRelationMapper;
	@Autowired
	private LnLoanRelationMapper lnLoanRelationMapper;
	@Autowired
	private LnRepayScheduleMapper lnRepayScheduleMapper;
	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
	@Autowired
	private DepFixedRepayPaymentService depFixedRepayPaymentService;

	/**
	 * 根据userId和产品id分页查询债权明细 
	 * @param req
	 * @param res
	 */
	public void getUserMatchList(ReqMsg_Match_GetUserMatchList req, ResMsg_Match_GetUserMatchList res){
		BsPropertyInfo bsPropertyInfo = productService.queryPropertyInfoByProductId(req.getProductId());
		res.setPropertySymbol(bsPropertyInfo.getPropertySymbol());
		
		if (Constants.PRODUCT_PROPERTY_SYMBOL_ZAN.equals(bsPropertyInfo.getPropertySymbol())) {
			String entrustStatus =  bsSubAccountService.queryEntrustStatusBySubAccountId(req.getSubAccountId());
			res.setEntrustStatus(entrustStatus);
			//委托金额
			
			
//			BsSubAccount subAccount = subAccountService.findRegDSubAccountByAuthId(req.getSubAccountId());
			Double entrustAmount = regularSiteService.queryLeftAmount(req.getSubAccountId());
			res.setEntrustAmount(entrustAmount == null ? 0d : entrustAmount);
			
			int count = regularSiteService.countLnLoanRelation(req.getUserId(),req.getSubAccountId());
			List<LnLoanRelationVO> relationList =  new ArrayList<LnLoanRelationVO>();
			if(count > 0) {
				relationList = regularSiteService.queryLnLoanRelationList(req.getUserId(),req.getSubAccountId(), req.getStartPage(), req.getPageSize());
			}
			ArrayList<HashMap<String,Object>> bean = BeanUtils.classToArrayList(relationList);
			res.setDataGrid(bean);
			res.setTotal(count);
			
		}else if(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF.equals(bsPropertyInfo.getPropertySymbol()) ||
				Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF.equals(bsPropertyInfo.getPropertySymbol()) ||
				Constants.PRODUCT_PROPERTY_SYMBOL_FREE.equals(bsPropertyInfo.getPropertySymbol())) { // 存管云贷、7贷、自由产品的债权明细
			//统计增计划债权明细总数
			int count = regularSiteService.queryDepClaimsCountBySubAccountId(req.getSubAccountId());
			List<DetailsOfDebtVO> list =  new ArrayList<DetailsOfDebtVO>();
			if(count > 0) {
				list = regularSiteService.queryDepClaimsListBySubAccountIdNew(req.getSubAccountId(), req.getStartPage(), req.getPageSize());
			}
			List<BsMatchVO> matchList =  new ArrayList<BsMatchVO>();
			if(list != null && list.size() > 0) {
				for(DetailsOfDebtVO obj : list) {
					BsMatchVO vo = new BsMatchVO();
					vo.setBorrowerName(obj.getLoanName()); //借款人
					vo.setAmount(obj.getInitAmount()); //借款金额
					vo.setLeftPrincipal(obj.getLeftAmount());// 剩余本金
					if(!obj.getInitAmount().equals(obj.getLeftAmount())) { //金额发生变动，则有还款产生
						vo.setLastRepayDate(obj.getUpdateTime()); //最近还款时间
					}
					if(Constants.LOAN_RELATION_STATUS_REPAID.equals(obj.getStatus())) { //已还完
						vo.setRepayStatus(Constants.LOAN_RELATION_STATUS_REPAID); //还款状态
					}else if(Constants.LOAN_RELATION_STATUS_TRANSFERRED.equals(obj.getStatus()) && obj.getLeftAmount() == 0d) {
						vo.setRepayStatus(Constants.LOAN_RELATION_STATUS_TRANSFERRED); //债转 剩余本金为0 //已还完
					}else {
						vo.setRepayStatus(Constants.LOAN_RELATION_STATUS_PAYING); //还款中
					}
					//已还金额
					vo.setRepayAmount(MoneyUtil.subtract(obj.getInitAmount(), obj.getLeftAmount()).doubleValue()); //初始本金-剩余本金
					//备注-已还完的为历史借款，未还完的为借款中
					if(Constants.LOAN_RELATION_STATUS_REPAID.equals(obj.getStatus()) || 
							Constants.LOAN_RELATION_STATUS_TRANSFERRED.equals(obj.getStatus()) && obj.getLeftAmount() == 0d) {
						vo.setNote("历史借款");
					}else {
						vo.setNote("借款中");
					}
					vo.setTransMark(obj.getTransMark()); //债转标记

					//出借时间 转入TRANS_IN的取ln_loan_relation中的create_time，否则取借款成功的时间
					if(StringUtil.isNotBlank(obj.getTransMark()) && Constants.TRANS_MARK_TRANS_IN.equals(obj.getTransMark())) {
						vo.setMatchDate(obj.getCreateTime());
					}else {
						vo.setMatchDate(obj.getLoanTime());
					}

					// 7贷随借随还产品新增业务标识
					vo.setPartnerBusinessFlag(obj.getPartnerBusinessFlag());
					vo.setId(obj.getLnLoanRelationId());//借贷关系id
					vo.setLoanTime(obj.getLoanTime());//借款成功的时间
					vo.setTransferTime(obj.getTransferTime() == null ? new Date() : obj.getTransferTime());
					vo.setPartnerCode(obj.getPartnerCode());//借款人来源云贷或7贷
					matchList.add(vo);
				}
			}
			ArrayList<HashMap<String,Object>> bean = BeanUtils.classToArrayList(matchList);
			res.setDataGrid(bean);
			res.setTotal(count);
		}else if(Constants.PRODUCT_PROPERTY_SYMBOL_ZSD.equals(bsPropertyInfo.getPropertySymbol())) { // 赞时贷债权明细
			//统计赞时贷债权明细总数
			int count = regularSiteService.queryDepClaimsCountBySubAccountId(req.getSubAccountId());
			List<DetailsOfDebtVO> list =  new ArrayList<DetailsOfDebtVO>();
			if(count > 0) {
				list = regularSiteService.queryDepClaimsListBySubAccountIdNew(req.getSubAccountId(), req.getStartPage(), req.getPageSize());
			}
			List<BsMatchVO> matchList =  new ArrayList<BsMatchVO>();
			if(list != null && list.size() > 0) {
				for(DetailsOfDebtVO obj : list) {
					BsMatchVO vo = new BsMatchVO();
					vo.setBorrowerName(obj.getLoanName()); //借款人
					vo.setAmount(obj.getInitAmount()); //借款金额
					vo.setLeftPrincipal(obj.getLeftAmount());// 剩余本金
					if(!obj.getInitAmount().equals(obj.getLeftAmount())) { //金额发生变动，则有还款产生
						vo.setLastRepayDate(obj.getUpdateTime()); //最近还款时间
					}
					if(Constants.LOAN_RELATION_STATUS_REPAID.equals(obj.getStatus())) { //已还完
						vo.setRepayStatus(Constants.LOAN_RELATION_STATUS_REPAID); //还款状态
					}else if(Constants.LOAN_RELATION_STATUS_TRANSFERRED.equals(obj.getStatus()) && obj.getLeftAmount() == 0d) {
						vo.setRepayStatus(Constants.LOAN_RELATION_STATUS_TRANSFERRED); //债转 剩余本金为0 //已还完
					}else {
						vo.setRepayStatus(Constants.LOAN_RELATION_STATUS_PAYING); //还款中
					}
					//已还金额
					vo.setRepayAmount(MoneyUtil.subtract(obj.getInitAmount(), obj.getLeftAmount()).doubleValue()); //初始本金-剩余本金
					//备注-已还完的为历史借款，未还完的为借款中
					if(Constants.LOAN_RELATION_STATUS_REPAID.equals(obj.getStatus()) ||
							Constants.LOAN_RELATION_STATUS_TRANSFERRED.equals(obj.getStatus()) && obj.getLeftAmount() == 0d) {
						vo.setNote("历史借款");
					}else {
						vo.setNote("借款中");
					}

					//出借时间 转入TRANS_IN的取ln_loan_relation中的create_time，否则取借款成功的时间
					if(StringUtil.isNotBlank(obj.getTransMark()) && Constants.TRANS_MARK_TRANS_IN.equals(obj.getTransMark())) {
						vo.setMatchDate(obj.getCreateTime());
					}else {
						vo.setMatchDate(obj.getLoanTime());
					}

					vo.setTransMark(obj.getTransMark()); //债转标记
					vo.setId(obj.getLnLoanRelationId());//借贷关系id
					vo.setLoanTime(obj.getLoanTime());//借款成功的时间
					matchList.add(vo);
				}
			}
			ArrayList<HashMap<String,Object>> bean = BeanUtils.classToArrayList(matchList);
			res.setDataGrid(bean);
			res.setTotal(count);
		}else {
			//云贷、7贷老产品的债权明细
			BsSubAccount bsSubAccount = subAccountService.findSubAccountById(req.getSubAccountId());
			int count = 0;
			ArrayList<HashMap<String,Object>> bean = null;
			if(Constants.PRODUCT_TYPE_REG.equals(bsSubAccount.getProductType())) { // 未生成AUTH户时，债权明细查询bs_match表
				count = bsMatchService.countMatchListByUserIdProductId(req.getUserId(), req.getProductId(),req.getSubAccountId());
				List<BsMatchVO> list =  new ArrayList<BsMatchVO>();
				if(count > 0){
					list = bsMatchService.getMatchListByUserIdProductId(
							req.getUserId(), req.getProductId(),req.getSubAccountId(),req.getStartPage(), req.getPageSize());
					// REG户、已结算的bs_sub_account.status=5的产品，还款标志设置SUCC，债权明细页面还款“查询”时，直接从页面传值
					if(list != null && list.size() > 0) {
						for(BsMatchVO vo : list) {
							vo.setRepayFlag(Constants.REPAY_STATUS_SUCC_FLAG);
						}
					}
				}
				bean = BeanUtils.classToArrayList(list);
			}else if(Constants.PRODUCT_TYPE_AUTH_7.equals(bsSubAccount.getProductType()) ||
					Constants.PRODUCT_TYPE_AUTH_YUN.equals(bsSubAccount.getProductType()) ) { // 生成AUTH户后，债权明细查询ln_loan_relation表

				//3月10号后，云贷、7贷老产品重新匹配后 页面债权明细文案显示标志
				if(Constants.PRODUCT_TYPE_AUTH_7.equals(bsSubAccount.getProductType())) {
					res.setDebtDetailsFlag("AUTH_7_FLAG");
				}else if(Constants.PRODUCT_TYPE_AUTH_YUN.equals(bsSubAccount.getProductType())) {
					res.setDebtDetailsFlag("AUTH_YUN_FLAG");
				}

				int afterCount = regularSiteService.queryDepClaimsCountBySubAccountId(req.getSubAccountId());
				int beforeCount = bsMatchService.countMatchListIncludePostMigration(req.getUserId(), req.getProductId(),req.getSubAccountId());
				count = beforeCount + afterCount;
				
				List<BsMatchVO> matchList =  new ArrayList<BsMatchVO>();
				List<BsMatchVO> list =  new ArrayList<BsMatchVO>();
				if(count > 0) {
					list = bsMatchService.getMatchListIncludePostMigration(
							req.getUserId(), req.getProductId(),req.getSubAccountId(),req.getStartPage(), req.getPageSize());
				}
				
				if(list != null && list.size() > 0) {
					for(BsMatchVO vo : list) {
						if (Constants.LN_LOAN_RELATION.equals(vo.getTableFlag())) {
							if(Constants.LOAN_RELATION_STATUS_REPAID.equals(vo.getRepayStatus())) { //已还完
								vo.setRepayStatus(Constants.LOAN_RELATION_STATUS_REPAID); //还款状态
							}else if(Constants.LOAN_RELATION_STATUS_TRANSFERRED.equals(vo.getRepayStatus()) && vo.getLeftPrincipal() == 0d) {
								vo.setRepayStatus(Constants.LOAN_RELATION_STATUS_TRANSFERRED); //债转 剩余本金为0 //已还完
							}else {
								vo.setRepayStatus(Constants.LOAN_RELATION_STATUS_PAYING); //还款中
							}
							//备注-已还完的为历史借款，未还完的为借款中
							if(Constants.LOAN_RELATION_STATUS_REPAID.equals(vo.getRepayStatus()) ||
									Constants.LOAN_RELATION_STATUS_TRANSFERRED.equals(vo.getRepayStatus()) && vo.getLeftPrincipal() == 0d) {
								vo.setNote("历史借款");
							}else {
								vo.setNote("借款中");
							}
							//出借时间 转入TRANS_IN的取ln_loan_relation中的create_time，否则取借款成功的时间
							if(StringUtil.isNotBlank(vo.getTransMark()) && Constants.TRANS_MARK_TRANS_IN.equals(vo.getTransMark())) {
								vo.setMatchDate(vo.getCreateTime());
							}else {
								vo.setMatchDate(vo.getLoanTime());
							}
							vo.setTransferTime(vo.getTransferTime() == null ? new Date() : vo.getTransferTime());
						} else if (Constants.BS_MATCH.equals(vo.getTableFlag())) {
							if (Constants.REPAY_STATUS_LOANING.equals(vo.getRepayStatus()) || Constants.REPAY_STATUS_PARTLY_REPAID.equals(vo.getRepayStatus())) {
								vo.setRepayFlag(Constants.REPAY_STATUS_FAIL_FLAG);
							} else if (Constants.REPAY_STATUS_REPAID.equals(vo.getRepayStatus())) {
								vo.setRepayFlag(Constants.REPAY_STATUS_SUCC_FLAG);
							}
							//出借时间
							vo.setMatchDate(vo.getMatchDate());
							vo.setRepayStatus(Constants.LOAN_RELATION_STATUS_REPAID);
							vo.setNote("历史借款");
							//统一不显示协议
							vo.setStockGeneratedClaims("STOCKGENERATEDCLAIMS");
						}
						matchList.add(vo);
					}
				}
				bean = BeanUtils.classToArrayList(matchList);
			}
			res.setDataGrid(bean);
			res.setTotal(count);
		}
	}
	
	/**
	 * 根据matchId查询债权关系还款明细
	 * @param req
	 * @param res
	 */
	public void getMatchRepayDetailList(ReqMsg_Match_GetMatchRepayDetailList req, ResMsg_Match_GetMatchRepayDetailList res){
		if((StringUtil.isNotBlank(req.getPropertySymbol()) && Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(req.getPropertySymbol()))
				|| (StringUtil.isNotBlank(req.getPropertySymbol()) && Constants.PROPERTY_SYMBOL_ZSD.equals(req.getPropertySymbol()))
				|| (StringUtil.isNotBlank(req.getPropertySymbol()) && Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(req.getPropertySymbol()))
				|| (StringUtil.isNotBlank(req.getPropertySymbol()) && Constants.PRODUCT_PROPERTY_SYMBOL_FREE.equals(req.getPropertySymbol()))) {
			List<BsMatchRepayDetail> list = new ArrayList<BsMatchRepayDetail>();
			// 借贷变动记录查询
			LnLoanAmountChangeExample loanAmountChangeExample = new LnLoanAmountChangeExample();
			loanAmountChangeExample.createCriteria().andRelationIdEqualTo(req.getMatchId()).andCreateTimeIsNotNull();
			loanAmountChangeExample.setOrderByClause("create_time desc");
            List<LnLoanAmountChange> loanAmountChangeList = lnLoanAmountChangeMapper.selectByExample(loanAmountChangeExample);
			if (!CollectionUtils.isEmpty(loanAmountChangeList)) {
				for (LnLoanAmountChange lnLoanAmountChange : loanAmountChangeList) {
					BsMatchRepayDetail vo = new BsMatchRepayDetail();
					vo.setRepayTime(lnLoanAmountChange.getCreateTime());
					vo.setRepayAmount(lnLoanAmountChange.getChangeAmount());
					list.add(vo);
				}
			}
			ArrayList<HashMap<String,Object>> bean = BeanUtils.classToArrayList(list);
			res.setList(bean);
		} else {
			BsSubAccount subAccount = subAccountService.findSubAccountById(Integer.parseInt(req.getSubAccountId()));
			if(subAccount == null) {
				throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
			}else {
				if(Constants.SUBACCOUNT_STATUS_SETTLE.equals(subAccount.getStatus())
						&& Constants.PRODUCT_TYPE_REG.equals(subAccount.getProductType())) {
					// 1、产品户为REG且已回款（status=5）的产品，还款查询bs_match_repay_detail
					List<BsMatchRepayDetail> list = bsMatchService.getListByMatchId(req.getMatchId());
					ArrayList<HashMap<String,Object>> bean = BeanUtils.classToArrayList(list);
					res.setList(bean);
				}else {
					// 2、存量迁移后老产品重新匹配后 还款查询
					List<BsMatchRepayDetail> list = new ArrayList<BsMatchRepayDetail>();
					// 借贷变动记录查询
					LnLoanAmountChangeExample loanAmountChangeExample = new LnLoanAmountChangeExample();
					loanAmountChangeExample.createCriteria().andRelationIdEqualTo(req.getMatchId()).andCreateTimeIsNotNull();
					loanAmountChangeExample.setOrderByClause("create_time desc");
					List<LnLoanAmountChange> loanAmountChangeList = lnLoanAmountChangeMapper.selectByExample(loanAmountChangeExample);
					if (!CollectionUtils.isEmpty(loanAmountChangeList)) {
						for (LnLoanAmountChange lnLoanAmountChange : loanAmountChangeList) {
							BsMatchRepayDetail vo = new BsMatchRepayDetail();
							vo.setRepayTime(lnLoanAmountChange.getCreateTime());
							vo.setRepayAmount(lnLoanAmountChange.getChangeAmount());
							list.add(vo);
						}
					}
					ArrayList<HashMap<String,Object>> bean = BeanUtils.classToArrayList(list);
					res.setList(bean);
				}
			}
		}

	}

	/**
	 * 查询委托计划债券明细
	 * @param req
	 * @param res
     */
	public void queryLnLoanRelationList(ReqMsg_Match_QueryLnLoanRelationList req, ResMsg_Match_QueryLnLoanRelationList res) {
		List<LnLoanRelationVO> vos = regularSiteService.queryLnLoanRelationList(req.getUserId(), req.getSubAccountId(), req.getPageNum(), req.getNumPerPage());
		int totalRows = regularSiteService.countLnLoanRelation(req.getUserId(), req.getSubAccountId());
		res.setGrid(BeanUtils.classToArrayList(vos));
		res.setTotalRows(totalRows);
		res.setTotalPages((int) Math.ceil((double) totalRows / req.getNumPerPage()));
	}
	
	/**
	 * 根据matchId查询债权关系还款明细(委托计划)
	 * @param req
	 * @param res
	 */
	public void getMatchRepayDetailZanList(ReqMsg_Match_GetMatchRepayDetailZanList req, ResMsg_Match_GetMatchRepayDetailZanList res){
		List<LnFinanceRepaySchedule> list = regularSiteService.queryRepayList(req.getMatchId());
		ArrayList<HashMap<String,Object>> bean = BeanUtils.classToArrayList(list);
		res.setList(bean);
	}

	/**
	 * 根据借贷关系loanRelationId查询借款人信息
	 * @param req
	 * @param res
     */
	public void getUserMatchLoanInfo(ReqMsg_Match_GetUserMatchLoanInfo req, ResMsg_Match_GetUserMatchLoanInfo res) {
        LnLoanRelationVO idVO = regularSiteService.queryAuthAccountId(req.getLoanRelationId());
        //根据借贷关系ID查询借款人信息
        LnLoanRelationVO loanVO = regularSiteService.queryLnLoanRelation(req.getLoanRelationId());
        if(idVO == null) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        } else {
            res.setAuthAccountId(idVO.getAuthAccountId());
            res.setProductId(idVO.getProductId());
            res.setBsUserId(idVO.getBsUserId());
        }
        if(loanVO == null) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        } else {
            res.setApproveAmount(loanVO.getApproveAmount());
            res.setPeriod(loanVO.getPeriod());
            res.setProductRate(loanVO.getProductRate());
            res.setInterestBeginDate(loanVO.getInterestBeginDate());
            res.setLastPlanDate(loanVO.getLastPlanDate());
            res.setPlanTotal(loanVO.getPlanTotal());
            res.setPlanDate(loanVO.getPlanDate());
            res.setRegdProductRate(loanVO.getRegdProductRate());
            res.setBorrowerName(loanVO.getBorrowerName());
            res.setMobile(loanVO.getMobile());
            res.setIdCard(loanVO.getIdCard());
            res.setPurpose(loanVO.getPurpose());
            res.setTotalAmount(loanVO.getTotalAmount());
            res.setLoanTime(loanVO.getLoanTime());
            res.setRegdId(loanVO.getRegdId());
            res.setBsUserId(loanVO.getBsUserId());
            res.setLnUserId(loanVO.getLnUserId());
            res.setLoanId(loanVO.getLoanId());

			//赞分期新旧协议时间区分标志
			BsSysConfig configUser = sysConfigService.findConfigByKey(Constants.ZAN_AGREEMENT_DATE_4_NEW);
			if(configUser != null) {
				String zanAgreementDate = configUser.getConfValue();
				boolean zanAgreementFlag = false;
				if(DateUtil.getDiffeDay(DateUtil.parseDate(DateUtil.formatYYYYMMDD(loanVO.getLoanTime())), DateUtil.parseDate(zanAgreementDate)) < 0) {
					zanAgreementFlag = false;
				}else {
					zanAgreementFlag = true;
				}
				res.setZanAgreementDate(zanAgreementFlag);

			}


        }
        
        //查询赞分期借款协议滞纳金修改时间 lateFeeChangeTime
        //与借款时间比较   如果借款时间(loanTime)在 lateFeeChangeTime 之前，显示老滞纳金费率，否则显示新的费率
        BsSysConfig bsSysConfig = bsSysConfigService.findKey(Constants.ZAN_LOAN_AGREEMENT_LATE_FEE);
        Date lateFeeChangeTime = DateUtil.parseDateTime(bsSysConfig.getConfValue());
		if (loanVO.getLoanTime().compareTo(lateFeeChangeTime) <= 0 ) {
			//显示老费率规则
			res.setShowLateFeeType("OLD");
		}else {
			//显示新费率规则
			res.setShowLateFeeType("NEW");
		}
		
        

	}

    /**
     * 根据借款表id查询月偿还本息数额、借款基本信息
     * @param req
     * @param res
     */
    public void getLoanBasicInfo(ReqMsg_Match_GetLoanBasicInfo req, ResMsg_Match_GetLoanBasicInfo res) {
        LnLoanRelationVO idVO = regularSiteService.queryAuthAccountId(req.getLoanRelationId());
        if(idVO == null) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        } else {
            //1、根据借款表id查询借款年化利率,返回值为万分之xx
            Double d = regularSiteService.queryLoanInterestRate(idVO.getLoanId());
            res.setLoanInterestRate(d/10000*100*12);
            //2、根据借款表id查询月偿还本息数额、借款基本信息
            LoanDetailInfoVO loanDetailInfoVO = regularSiteService.queryLoanInfoByLoanId(idVO.getLoanId());
            res.setNeedRepayMoney4Month(loanDetailInfoVO.getNeedRepayMoney4Month());
            res.setPeriod(loanDetailInfoVO.getPeriod());
            res.setLoanTime(loanDetailInfoVO.getLoanTime());
            res.setRegdId(idVO.getRegdId());
            if(loanDetailInfoVO.getLoanTime() != null) {
                res.setLnRepayStartDate(DateUtil.addMonths(loanDetailInfoVO.getLoanTime(), 1));
                res.setLnRepayEndDate(DateUtil.addMonths(loanDetailInfoVO.getLoanTime(), loanDetailInfoVO.getPeriod()));
                res.setDay4Month(Integer.parseInt(DateUtil.formatDateTime(loanDetailInfoVO.getLoanTime(), "dd")));
            }
        }

    }

	/**
	 * 根据借贷关系loanRelationId查询债权转让信息
	 * @param req
	 * @param res
	 */
	public void getUserClaimsTransferInfoOld(ReqMsg_Match_GetUserClaimsTransferInfo req, ResMsg_Match_GetUserClaimsTransferInfo res) {
		BsLoanRelationTransferVO loanRelationTransferVO = bsLoanRelationShipService.getLoanTransferInfos(req.getLoanRelationId());
		if(loanRelationTransferVO == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {
			res.setId(loanRelationTransferVO.getId());
			res.setAmount(loanRelationTransferVO.getAmount());
			res.setInAmount(loanRelationTransferVO.getInAmount());
			res.setOutUserName(loanRelationTransferVO.getOutUserName());
			res.setInUserName(loanRelationTransferVO.getInUserName());
			res.setOutUserMobile(loanRelationTransferVO.getOutUserMobile());
			res.setInUserMobile(loanRelationTransferVO.getInUserMobile());
			res.setOutUserIdCardNo(loanRelationTransferVO.getOutUserIdCardNo());
			res.setInUserIdCardNo(loanRelationTransferVO.getInUserIdCardNo());
			res.setBorrowUserName(loanRelationTransferVO.getBorrowUserName());
			res.setBorrowUserIdCardNo(loanRelationTransferVO.getBorrowUserIdCardNo());
			res.setFirstRepayDate(loanRelationTransferVO.getFirstRepayDate());
			res.setRepayAmount4Month(loanRelationTransferVO.getRepayAmount4Month());
			res.setRepayAmount4All(loanRelationTransferVO.getRepayAmount4All());
			res.setTerm(loanRelationTransferVO.getTerm());
			res.setLeftTerm(loanRelationTransferVO.getLeftTerm());
			res.setFirstTerm(loanRelationTransferVO.getFirstTerm());
			res.setTransferTime(loanRelationTransferVO.getTransferTime());
			res.setProductName(loanRelationTransferVO.getProductName());
			res.setInSubAccountId(loanRelationTransferVO.getInSubAccountId());
		}
	}


	/**
	 * 根据借贷关系loanRelationId查询债权转让信息
	 * @param req
	 * @param res
	 */
	public void getUserClaimsTransferInfo(ReqMsg_Match_GetUserClaimsTransferInfo req, ResMsg_Match_GetUserClaimsTransferInfo res) {
		BsLoanRelationTransferVO loanRelationTransferVO = bsLoanRelationShipService.getLoanTransferInfoNew(req.getLoanRelationId());
		if(loanRelationTransferVO == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {
			res.setId(loanRelationTransferVO.getId());
			res.setAmount(loanRelationTransferVO.getAmount());
			res.setInAmount(loanRelationTransferVO.getInAmount());
			res.setOutUserName(loanRelationTransferVO.getOutUserName());
			res.setInUserName(loanRelationTransferVO.getInUserName());
			res.setOutUserMobile(loanRelationTransferVO.getOutUserMobile());
			res.setInUserMobile(loanRelationTransferVO.getInUserMobile());
			res.setOutUserIdCardNo(loanRelationTransferVO.getOutUserIdCardNo());
			res.setInUserIdCardNo(loanRelationTransferVO.getInUserIdCardNo());
			res.setBorrowUserName(loanRelationTransferVO.getBorrowUserName());
			res.setBorrowUserIdCardNo(loanRelationTransferVO.getBorrowUserIdCardNo());
			res.setFirstRepayDate(loanRelationTransferVO.getFirstRepayDate());
			res.setRepayAmount4Month(loanRelationTransferVO.getRepayAmount4Month());
			res.setRepayAmount4All(loanRelationTransferVO.getRepayAmount4All());
			res.setTerm(loanRelationTransferVO.getTerm());
			res.setLeftTerm(loanRelationTransferVO.getLeftTerm());
			res.setFirstTerm(loanRelationTransferVO.getFirstTerm());
			res.setTransferTime(loanRelationTransferVO.getTransferTime());
			res.setProductName(loanRelationTransferVO.getProductName());
			res.setInSubAccountId(loanRelationTransferVO.getInSubAccountId());
			res.setDiscountAmount(loanRelationTransferVO.getDiscountAmount());
			res.setExpectProfit(loanRelationTransferVO.getExpectProfit());

			//赞分期债转协议-新旧协议时间区分标志
			BsSysConfig configUser = sysConfigService.findConfigByKey(Constants.ZAN_AGREEMENT_DATE_4_NEW);
			if(configUser != null) {

				String zanAgreementDate = configUser.getConfValue();
				boolean zanAgreementFlag = false;
				//债转时间跟配置表中时间比较
				if(DateUtil.getDiffeDay(DateUtil.parseDate(DateUtil.formatYYYYMMDD(loanRelationTransferVO.getTransferTime())), DateUtil.parseDate(zanAgreementDate)) < 0) {
					zanAgreementFlag = false;
				}else {
					zanAgreementFlag = true;
				}
				res.setZanAgreementDate(zanAgreementFlag);

			}
		}
	}

	/**
	 * 根据借款ID查询这一笔借款对应的理财人数据
	 * @param req
	 * @param res
     */
	public void getUserFinancialManagement(ReqMsg_Match_GetUserFinancialManagement req, ResMsg_Match_GetUserFinancialManagement res) {
		List<LnLoanRelationVO> list = regularSiteService.queryUserByLoanId(req.getLoanId());
		if(list == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {
			//根据借款ID查询借贷变动记录表ln_loan_amount_change，如果存在，取create_time最早的变动前金额before_amount
			List<LnLoanRelationVO> dataList = new ArrayList<LnLoanRelationVO>();
			for(int i=0; i<list.size(); i++) {
				LnLoanRelationVO vo = new LnLoanRelationVO();
				LnLoanRelationVO result = list.get(i);
				//借贷关系表ID
				LnLoanAmountChange loanAmountChange = regularSiteService.queryAmountByRelationId(result.getId());
				if(loanAmountChange == null) {
					vo.setUserMobile(result.getUserMobile());
					vo.setUserName(result.getUserName());
					vo.setIdCard(result.getIdCard());
					vo.setTotalAmount(result.getTotalAmount());
					vo.setPeriod(result.getPeriod());
					dataList.add(vo);
				} else {
					vo.setUserMobile(result.getUserMobile());
					vo.setUserName(result.getUserName());
					vo.setIdCard(result.getIdCard());
					vo.setTotalAmount(loanAmountChange.getBeforeAmount());
					vo.setPeriod(result.getPeriod());
					dataList.add(vo);
				}
			}
			//借款总本金数额
			if(dataList != null && dataList.size() > 0) {
				double sumTotalAmount = 0d;
				for(LnLoanRelationVO loan : dataList) {
					sumTotalAmount= MoneyUtil.add(sumTotalAmount,loan.getTotalAmount()).doubleValue();
				}
				res.setSumTotalAmount(sumTotalAmount);
			}
			ArrayList<HashMap<String,Object>> bean = BeanUtils.classToArrayList(dataList);
			res.setUserList(bean);
		}

	}

	//币港湾赞分期产品自动出借计划协议 等额本息方式计算期末预期收益数额
	public void getExpectedRevenueAmount(ReqMsg_Match_GetExpectedRevenueAmount req, ResMsg_Match_GetExpectedRevenueAmount res) {
		BsSubAccount subAccount = subAccountService.findSubAccountById(req.getAccountId());
		BsProduct product = productService.findRegularById(req.getProductId());
		if(subAccount == null || product == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}else{
			//等额本息计算
			List<AverageCapitalPlusInterestVO> list =
					algorithmService.calAverageCapitalPlusInterestPlan(subAccount.getOpenBalance(), product.getTerm(), subAccount.getProductRate());
			if(list != null && list.size() != 0) {
				double totalPlanInterest = 0d;
				for(int i=0; i<list.size(); i++) {
					totalPlanInterest = MoneyUtil.add(totalPlanInterest, list.get(i).getPlanInterest()).doubleValue();
					res.setIncomeAmount(totalPlanInterest);
				}
			}else {
				res.setIncomeAmount(0d);
			}
		}
	}


	/**
	 * 赞分期APP借款协议
	 * @param req
	 * @param res
     */
	public void getZanAppUserLoanInfo(ReqMsg_Match_GetZanAppUserLoanInfo req, ResMsg_Match_GetZanAppUserLoanInfo res) {
		LoanDetailInfoVO loanDetailInfoVO = bsLoanRelationShipService.getLoanDetailInfoVO(req.getPartnerLoanId());
		if(loanDetailInfoVO == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {
			ArrayList<HashMap<String, Object>> list = BeanUtils.classToArrayList(loanDetailInfoVO.getBsUserInfo());
			res.setDataUserInfo(list);
			res.setId(loanDetailInfoVO.getId());
			res.setLnUserName(loanDetailInfoVO.getLnUserName());
			res.setLnUserIdCard(loanDetailInfoVO.getLnUserIdCard());
			res.setLnUserZANAccount(loanDetailInfoVO.getLnUserZANAccount());
			res.setLoanTime(loanDetailInfoVO.getLoanTime());
			res.setPurpose(loanDetailInfoVO.getPurpose());
			res.setApproveAmount(loanDetailInfoVO.getApproveAmount());
			res.setPeriod(loanDetailInfoVO.getPeriod());
			res.setNeedRepayMoney4Month(loanDetailInfoVO.getNeedRepayMoney4Month());
			res.setLnRepayStartDate(loanDetailInfoVO.getLnRepayStartDate());
			res.setLnRepayEndDate(loanDetailInfoVO.getLnRepayEndDate());
			res.setDay4Month(loanDetailInfoVO.getDay4Month());
			res.setLnRate(loanDetailInfoVO.getLnRate());

			//赞分期新旧协议时间区分标志
			BsSysConfig configUser = sysConfigService.findConfigByKey(Constants.ZAN_AGREEMENT_DATE_4_NEW);
			if(configUser != null) {
				String zanAgreementDate = configUser.getConfValue();
				boolean zanAgreementFlag = false;
				if(DateUtil.getDiffeDay(loanDetailInfoVO.getLoanTime(), DateUtil.parseDate(zanAgreementDate)) < 0) {
					zanAgreementFlag = false;
				}else {
					zanAgreementFlag = true;
				}
				res.setZanAgreementDate(zanAgreementFlag);
			}
			
		}
		
		
        //查询赞分期借款协议滞纳金修改时间 lateFeeChangeTime
        //与借款时间比较   如果借款时间(loanTime)在 lateFeeChangeTime 之前，显示老滞纳金费率，否则显示新的费率
        BsSysConfig bsSysConfig = bsSysConfigService.findKey(Constants.ZAN_LOAN_AGREEMENT_LATE_FEE);
        Date lateFeeChangeTime = DateUtil.parseDateTime(bsSysConfig.getConfValue());
		if (loanDetailInfoVO.getLoanTime().compareTo(lateFeeChangeTime) <= 0 ) {
			//显示老费率规则
			res.setShowLateFeeType("OLD");
		}else {
			//显示新费率规则
			res.setShowLateFeeType("NEW");
		}
	}

	/**
	 * 赞分期APP借款咨询与服务协议 根据借款编号查询管费、信息服务费、账户管理费、保费
	 * @param req
	 * @param res
     */
	public void getUserLoanFeeList(ReqMsg_Match_GetUserLoanFeeList req, ResMsg_Match_GetUserLoanFeeList res) {
		LnLoan loan = bsLoanFeeService.queryLoanIdByPartnerLoanId(req.getPartnerLoanId());
		if(loan == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {
			List<RepayScheduleVO> feeList = bsLoanFeeService.queryFeeByLoanId(loan.getId());
			if(feeList == null || feeList.size() == 0) {
				throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
			}else {
				res.setUserName(feeList.get(0).getUserName());
				for(int i=0; i<feeList.size(); i++) {
					if(feeList.get(i).getSubjectCode() != null && Constants.SUBJECT_SUPERVISE_FEE.equals(feeList.get(i).getSubjectCode())) {
						res.setLoanServiceFee(feeList.get(i).getPlanAmount());
					}
					if(feeList.get(i).getSubjectCode() != null && Constants.SUBJECT_INFO_SERVICE_FEE.equals(feeList.get(i).getSubjectCode())) {
						res.setLoanInfoServiceFee(feeList.get(i).getPlanAmount());
					}
					if(feeList.get(i).getSubjectCode() != null && Constants.SUBJECT_ACCOUNT_MANAGE_FEE.equals(feeList.get(i).getSubjectCode())) {
						res.setLoanAccountManageFee(feeList.get(i).getPlanAmount());
					}
					if(feeList.get(i).getSubjectCode() != null && Constants.SUBJECT_PREMIUM.equals(feeList.get(i).getSubjectCode())) {
						res.setLoadPremium(feeList.get(i).getPlanAmount());
					}
				}
			}

		}

	}

	/**
	 * 判断借款协议、债权转让的协议loanRelationId是否属于当前登录的用户
	 * @param req
	 * @param res
     */
	public void checkUserIdLoanRelationId(ReqMsg_Match_CheckUserIdLoanRelationId req,ResMsg_Match_CheckUserIdLoanRelationId res){
		LnLoanRelation lnLoanRelation = regularSiteService.queryRecordByUserIdAndId(req.getUserId(), req.getLoanRelationId());
		if(lnLoanRelation == null){
			res.setLoanRelationId(-1);
		}else{
			res.setLoanRelationId(lnLoanRelation.getId());
		}
	}
	
	
	
	

	// =======================代偿协议相关 start =======================

	//(1) 收款确认函-服务费、收款确认函-债转 债权转让列表
	public void getUserClaimsTransferInfoList(ReqMsg_Match_GetUserClaimsTransferInfoList req, ResMsg_Match_GetUserClaimsTransferInfoList res) {
		//BsUser bsUser = new BsUser();
		List<CompensateTransfersVO> list = new ArrayList<CompensateTransfersVO>();

		//LnLoan lnLoan = lnLoanMapper.selectLoanByPartnerLoanId(req.getLoanId());
		
		BsUserCompensateVO compensateVO =  new BsUserCompensateVO();
		if(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF.equals(req.getPartnerEnum())) {
			//1、债权受让人-云贷的自然人
			//bsUser = regularSiteService.queryYunDaiSelfCreditor();
			//compensateVO = depFixedRepayPaymentService.compensaterInfo(lnLoan.getLoanTime(), Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF);
			list = lnRepayScheduleMapper.selectYunCollectionLettersByOrderNo(req.getOrderNo());
		}else if(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF.equals(req.getPartnerEnum())){
			//2、债权受让人-7贷的自然人
			//bsUser = regularSiteService.query7DaiSelfCreditor();
			//compensateVO = depFixedRepayPaymentService.compensaterInfo(lnLoan.getLoanTime(), Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF);
			list = lnRepayScheduleMapper.selectSevenCollectionLettersByOrderNo(req.getOrderNo());
		}
//		if(compensateVO == null) {
//			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
//		}else {
//			res.setYunDaiSelfUserName(getBlurName(compensateVO.getUserName()));
//			res.setYunDaiSelfIdCard(getBlurIdNo(compensateVO.getIdCard()));
//		}

		List<CompensateTransfersVO> compensateTransfersList = new ArrayList<CompensateTransfersVO>();
		if(list != null && list.size() > 0) {
			for(CompensateTransfersVO vo : list) {
				CompensateTransfersVO compensateTransfersVO = new CompensateTransfersVO();
				compensateTransfersVO.setApproveAmount(vo.getApproveAmount());
				compensateTransfersVO.setAgreementRate(vo.getAgreementRate());
				compensateTransfersVO.setPeriod(vo.getPeriod()*30);//云贷借款期限 天数
				compensateTransfersVO.setLoanUserName(getBlurName(vo.getLoanUserName()));
				compensateTransfersVO.setLoanIdCard(getBlurIdNo(vo.getLoanIdCard()));
				//7贷借款期限 期数
				compensateTransfersVO.setSevenPeriod(vo.getPeriod());
				compensateTransfersVO.setLoanAgreementNumber(vo.getLoanAgreementNumber());
				compensateTransfersVO.setTransferCreditorAmount(vo.getTransferCreditorAmount());
				//单笔债转的服务费 = 借款人剩余未还本金总额*代偿天数*（24%-借款协议利率）/365 (代偿天数/计息天数)
				compensateTransfersVO.setDebtServiceFee(vo.getDebtServiceFee());
				//代偿成功的时间
				compensateTransfersVO.setLateRepayDate(req.getLateRepayDate());
				compensateTransfersList.add(compensateTransfersVO);
			}
			ArrayList<HashMap<String,Object>> resultList = BeanUtils.classToArrayList(compensateTransfersList);
			res.setDataGrid(resultList);
		}

	}

	//(1) 收款确认函-债转 债权转让列表
	public void getReceiptConfirmInfoList(ReqMsg_Match_GetReceiptConfirmInfoList req, ResMsg_Match_GetReceiptConfirmInfoList res) {
		List<CompensateReceiptConfirmPdfVO> list = new ArrayList<>();

		CompensateReceiptConfirmPdfVO confirmPdfVO = new CompensateReceiptConfirmPdfVO();

		//1、根据合作方借款编号loan_id查询四方借款协议编号
		BsUserSealFile bsUserSealFile = bsUserSealFileService.queryAgreementNo(req.getLoanId(), req.getPartnerEnum());
		if(bsUserSealFile != null) {
			confirmPdfVO.setLoanAgreementNumber(bsUserSealFile.getAgreementNo());//四方借款协议编号
		}
		LnLoan lnLoan = regularSiteService.queryLoanByPartnerLoanId(req.getLoanId());
		if(lnLoan == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}
		confirmPdfVO.setLoanId(lnLoan.getId());
		confirmPdfVO.setApproveAmount(lnLoan.getApproveAmount());
		confirmPdfVO.setAgreementRate(lnLoan.getAgreementRate());
		confirmPdfVO.setPeriod(lnLoan.getPeriod());
		LnUser lnUser = regularSiteService.queryLnUserInfoById(lnLoan.getLnUserId());
		if(lnUser == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}
		confirmPdfVO.setLoanUserName(lnUser.getUserName());
		confirmPdfVO.setLoanIdCard(lnUser.getIdCard());

		List<CompensateTransfersPdfVO> transfersPdfVOList = regularSiteService.queryCompensateTransfer4StageList(lnLoan.getId());
		Double transferCreditorTotalAmount = 0d;
		for (CompensateTransfersPdfVO transfersPdfVO : transfersPdfVOList) {
			transferCreditorTotalAmount = MoneyUtil.add(transferCreditorTotalAmount, transfersPdfVO.getTransferCreditorAmount()).doubleValue();
		}
		confirmPdfVO.setTransferCreditorTotalAmount(transferCreditorTotalAmount);
		confirmPdfVO.setCompemsateInfos(BeanUtils.classToArrayList(transfersPdfVOList));
		list.add(confirmPdfVO);

		res.setDataGrid(BeanUtils.classToArrayList(list));
	}

	//(2) 债权转让通知书
	public void getCompensateUserTransferInfo(ReqMsg_Match_GetCompensateUserTransferInfo req, ResMsg_Match_GetCompensateUserTransferInfo res) {
		//1、根据合作方借款编号loan_id查询四方借款协议编号
		BsUserSealFile bsUserSealFile = bsUserSealFileService.queryAgreementNo(req.getLoanId(), req.getPartnerEnum());
		if(bsUserSealFile != null) {
			res.setLoanAgreementNumber(bsUserSealFile.getAgreementNo());//四方借款协议编号
		}
//		BsUser bsUser = new BsUser();
		LnLoan lnLoan = new LnLoan();
		LnUser lnUser = new LnUser();
//		if(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF.equals(req.getPartnerEnum())) {
//			//2、债权受让人-云贷的自然人
//			bsUser = regularSiteService.queryYunDaiSelfCreditor();
//		}else if(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF.equals(req.getPartnerEnum())){
//			//3、债权受让人-7贷的自然人
//			bsUser = regularSiteService.query7DaiSelfCreditor();
//		}
		lnLoan = regularSiteService.queryLoanByPartnerLoanId(req.getLoanId());
		if(lnLoan == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}
		lnUser = regularSiteService.queryLnUserInfoById(lnLoan.getLnUserId());
		
		BsUserCompensateVO bsUserCompensateVO = depFixedRepayPaymentService.compensaterInfo(lnLoan.getLoanTime(), req.getPartnerEnum());
        
		if(bsUserCompensateVO == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}else {
			res.setYunDaiSelfUserName(getBlurName(bsUserCompensateVO.getUserName()));
			res.setYunDaiSelfIdCard(getBlurIdNo(bsUserCompensateVO.getIdCard()));
		}

		List<CompensateTransfersVO> CompensateTransfersList = new ArrayList<CompensateTransfersVO>();

		//4、借款人姓名/身份证号码
		if(lnUser != null) {
			res.setLoanUserName(getBlurName(lnUser.getUserName()));
			res.setLoanIdCard(getBlurIdNo(lnUser.getIdCard()));
		}
		//5、债权转让列表
		List<CompensateTransfersVO> resultVOList = regularSiteService.queryCompensateTransferList(lnLoan.getId());
		if(resultVOList != null && resultVOList.size() > 0) {
			for(CompensateTransfersVO obj : resultVOList) {
				CompensateTransfersVO compensateVO = new CompensateTransfersVO();
				//统计7贷代偿人-代偿的笔数
				if(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF.equals(req.getPartnerEnum())){
					int sevenCompensateCount = lnCompensateRelationMapper.select7CompensationCount(lnLoan.getId());
					compensateVO.setSevenCompensateCount(sevenCompensateCount);
				}

				compensateVO.setUserName(getBlurName(obj.getUserName()));
				compensateVO.setIdCard(getBlurIdNo(obj.getIdCard()));
				//转让剩余期数/天数
				compensateVO.setNoBillingPeriod(obj.getNoBillingPeriod());
				//代偿成功的时间
				compensateVO.setLateRepayDate(req.getLateRepayDate());

				//债转通知书-债权转让金额 = 债权对应代偿本金的*未还账单天数*借款利率/365 + 债权对应代偿本金的
				compensateVO.setNoticeTransferCreditorAmount(obj.getAgreementAmount());
				
				//债权转让金额 = 未还本金*借款利率*未还账单期限/365+未还本金，同上
				compensateVO.setTransferCreditorAmount(compensateVO.getNoticeTransferCreditorAmount());
				//还款期次/代偿期数
				compensateVO.setSerialId(compensateVO.getSerialId());
				
				if(bsUserCompensateVO != null) {
					compensateVO.setYunDaiSelfUserName(getBlurName(bsUserCompensateVO.getUserName()));
					compensateVO.setYunDaiSelfIdCard(getBlurIdNo(bsUserCompensateVO.getIdCard()));
				}
				CompensateTransfersList.add(compensateVO);
			}
			ArrayList<HashMap<String,Object>> resultList = BeanUtils.classToArrayList(CompensateTransfersList);
			res.setDataGrid(resultList);
		}

	}

	//(2) 债权转让通知书
	public void getCreditorNoticeInfo(ReqMsg_Match_GetCreditorNoticeInfo req, ResMsg_Match_GetCreditorNoticeInfo res) {
		//1、根据合作方借款编号loan_id查询四方借款协议编号
		BsUserSealFile bsUserSealFile = bsUserSealFileService.queryAgreementNo(req.getLoanId(), req.getPartnerEnum());
		if(bsUserSealFile != null) {
			res.setLoanAgreementNumber(bsUserSealFile.getAgreementNo());//四方借款协议编号
		}
		LnLoan lnLoan = regularSiteService.queryLoanByPartnerLoanId(req.getLoanId());
		if(lnLoan == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}
		LnUser lnUser = regularSiteService.queryLnUserInfoById(lnLoan.getLnUserId());
		if(lnUser == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}
		res.setLoanUserName(lnUser.getUserName());
		res.setLoanIdCard(lnUser.getIdCard());

		BsUserCompensateVO bsUserCompensateVO = depFixedRepayPaymentService.compensaterInfo(lnLoan.getLoanTime(), req.getPartnerEnum());
		if(bsUserCompensateVO == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {
			res.setYunDaiSelfUserName(bsUserCompensateVO.getUserName());
			res.setYunDaiSelfIdCard(bsUserCompensateVO.getIdCard());
		}
		List<CompensateTransfersPdfVO> resultVOList = regularSiteService.queryCompensateTransfer4StageList(lnLoan.getId());
		if(!CollectionUtils.isEmpty(resultVOList)) {
			res.setDataGrid(BeanUtils.classToArrayList(resultVOList));
		}
	}

	//(3) 债权转让协议
	public void getCompensateTransferInfo(ReqMsg_Match_GetCompensateTransferInfo req, ResMsg_Match_GetCompensateTransferInfo res) {
//		BsUser bsUser = new BsUser();
//		if(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF.equals(req.getPartnerEnum())) {
//			//1、债权受让人-云贷的自然人
//			bsUser = regularSiteService.queryYunDaiSelfCreditor();
//		}else if(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF.equals(req.getPartnerEnum())){
//			//2、债权受让人-7贷的自然人
//			bsUser = regularSiteService.query7DaiSelfCreditor();
//		}
		LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(req.getLoanId());
		BsUserCompensateVO vo = depFixedRepayPaymentService.compensaterInfo(lnLoan.getLoanTime(), req.getPartnerEnum());

		if(vo == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}else {
			res.setYunDaiSelfUserName(getBlurName(vo.getUserName()));
			res.setYunDaiSelfIdCard(getBlurIdNo(vo.getIdCard()));
			res.setYunDaiSelfMobile(getBlurMobile(vo.getMobile()));
		}
		//3、债转数据
		CompensateTransfersVO compensateTransfersVO = regularSiteService.selectCompensateTransferInfo(req.getLoanId(), req.getLoanRelationId());
		if(compensateTransfersVO == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}else {
			res.setUserName(getBlurName(compensateTransfersVO.getUserName())); //币港湾实际出借人
			res.setIdCard(getBlurIdNo(compensateTransfersVO.getIdCard())); //出借人身份证号
			res.setLoanUserName(getBlurName(compensateTransfersVO.getLoanUserName()));//借款人姓名
			res.setLoanIdCard(getBlurIdNo(compensateTransfersVO.getLoanIdCard())); //借款人身份证号
			res.setPeriod(compensateTransfersVO.getPeriod()*30); //借款期限 天数
			res.setApproveAmount(compensateTransfersVO.getApproveAmount()); //借款本金
			//债权转让金额 = 出借人待收未还本金*借款利率*未还账单期限/365+出借人待收未还本金
			res.setTransferCreditorAmount(compensateTransfersVO.getAgreementAmount());
			res.setSevenPeriod(compensateTransfersVO.getPeriod()); //7贷借款期限 期数
			res.setUserMobile(getBlurMobile(compensateTransfersVO.getUserMobile())); //出借人手机号

		}

	}

	// =======================代偿协议相关 end =======================



	//=====================  存管新增协议 start =====================

	// (1) 存管港湾产品借款协议数据
	public void getCustodyLoanInfo(ReqMsg_Match_GetCustodyLoanInfo req, ResMsg_Match_GetCustodyLoanInfo res) {
		LnLoanRelation lnLoanRelation = regularSiteService.queryNotRepaidPrincipal(req.getLnLoanRelationId());
		if(lnLoanRelation == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {
			// 1、理财人信息 (不考虑债转) 根据借款id查询理财人信息
			List<LnLoanRelationVO> resultList = regularSiteService.queryCustodyFinancialManagement(lnLoanRelation.getLoanId());
			if(null != resultList && resultList.size() > 0) {
				res.setFinancialManagementList(BeanUtils.classToArrayList(resultList));
				//2、出借金额总计
				double sumTotalAmount = 0d;
				for(LnLoanRelationVO obj : resultList) {
					sumTotalAmount= MoneyUtil.add(sumTotalAmount,obj.getInitAmount()).doubleValue();
				}
				res.setSumTotalAmount(sumTotalAmount);
			}
			// 3、借款人信息
			LnUser lnUser = regularSiteService.queryLnUserInfoById(lnLoanRelation.getLnUserId());
			if(null != lnUser) {
				res.setLoanUserName(getBlurName(lnUser.getUserName()));
				res.setLoanIdCard(getBlurIdNo(lnUser.getIdCard()));
				res.setLoanMobile(getBlurMobile(lnUser.getMobile()));
			}
			// 4、借款基本信息
			LnLoan lnLoan = regularSiteService.queryLoanInfoById(lnLoanRelation.getLoanId());
			if(null != lnLoan) {
				res.setLoanId(lnLoan.getId());
				res.setLoanAmount(lnLoan.getApproveAmount());
				res.setLoanTerm(lnLoan.getPeriod()*30);
				res.setLoanDay(lnLoan.getLoanTime());
                res.setAddress(lnLoan.getAddress() == null ? null : getBlurAddress(lnLoan.getAddress()));
                res.setEmail(lnLoan.getEmail() == null ? null : getBlurEmail(lnLoan.getEmail()));
                res.setPartnerBusinessFlag(lnLoan.getPartnerBusinessFlag());
				if(Constants.AGREEMENT_VERSION_TYPE_HF_7DAI_LOAN.equals(req.getAgreementType()) ||
						Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_LOAN.equals(req.getAgreementType()) ||
						Constants.AGREEMENT_VERSION_TYPE_HF_7DAI_LOAN_ANYREPAY.equals(req.getAgreementType())){
					//7贷借款到期日以实际账单为准
					List<RepayPlan> repayPlanList = CalBillPeriod.calculateRepayPlan(lnLoan.getLoanTime(),
							Math.round(lnLoan.getApplyAmount()), lnLoan.getAgreementRate());
					RepayPlan sevenLoanDueDate = repayPlanList.get(repayPlanList.size() - 1);
					String sevenLoanDate = new SimpleDateFormat("yyyy年MM月dd日").format(sevenLoanDueDate.getDate());
					res.setSevenLoanDueDate(sevenLoanDate);
				}else if(Constants.AGREEMENT_VERSION_TYPE_HF_YUNDAI_INSTALLMENT.equals(req.getAgreementType()) ||
						Constants.AGREEMENT_VERSION_TYPE_HF_YUNDAI_PRINCIPAL_INTEREST.equals(req.getAgreementType())) {
					// 云贷分期产品借款到期日
					LnRepaySchedule lnRepaySchedule = lnRepayScheduleMapper.lastPlanByLoanId(lnLoanRelation.getLoanId());
					if(null != lnRepaySchedule) {
						String yunLoanDate = new SimpleDateFormat("yyyy年MM月dd日").format(lnRepaySchedule.getPlanDate());
						res.setSevenLoanDueDate(yunLoanDate);
					}else {
						res.setSevenLoanDueDate(getloanDueDate(lnLoan.getLoanTime(), lnLoan.getPeriod()));
					}
				}else {
					res.setLoanDueDate(DateUtil.addDays(lnLoan.getLoanTime(), 89)); //云贷产品固定90天(包含前后日期)
				}
				res.setAgreementRate(lnLoan.getAgreementRate());
				res.setPurpose(lnLoan.getPurpose() != null ? lnLoan.getPurpose() : ""); //借款用途
				res.setTheTerm(lnLoan.getPeriod());
				res.setPartnerLoanId(lnLoan.getPartnerLoanId());
				//出借天数
				Integer dayCount = CalBillPeriod.calBillDays(lnLoan.getLoanTime());
				res.setDayCount(dayCount);
				//分期产品借款服务费
				res.setFixLoanServiceRate(lnLoan.getLoanServiceRate());
			}
			// 5、债权转让记录 这一笔借款对应的所有债权转让记录
			if (StringUtil.isNotBlank(req.getAgreementType())) {
				if (Constants.AGREEMENT_VERSION_TYPE_HF_YUN_LOAN.equals(req.getAgreementType()) ||
						Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_LOAN.equals(req.getAgreementType())) {
					LnLoanRelation loanRelation = regularSiteService.queryLoanRelationById(req.getLnLoanRelationId());
					if(loanRelation != null) {
						List<DebtTransferRecordsVO> transferList = regularSiteService.selectCustodyLoanTransferClaims(loanRelation.getLoanId());
						if(null != transferList && transferList.size() > 0) {
							res.setTransferList(BeanUtils.classToArrayList(transferList));
						}
					}
				}
			}

			// 6、存管云贷借款协议新旧版本时间区分标志
			if (StringUtil.isNotBlank(req.getAgreementType())) {
				if(Constants.AGREEMENT_VERSION_TYPE_HF_YUN_LOAN.equals(req.getAgreementType()) ||
						Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_LOAN.equals(req.getAgreementType())) {
					boolean yundaiAgreementFlag = false;
					BsSubAccount subAccount = subAccountService.findSubAccountById(req.getSubAccountId());
					if(subAccount != null) {
						BsSysConfig configUser = sysConfigService.findConfigByKey(Constants.YUN_DAI_SELF_LOAN_AGREEMENT_DATE_4_NEW);
						if(configUser != null) {
							String yundaiAgreementDate = configUser.getConfValue();
							if(DateUtil.getDiffeSeconds(lnLoan.getLoanTime(), DateUtil.parseDateTime(yundaiAgreementDate)) > 0) {
								yundaiAgreementFlag = true;
							}else {
								yundaiAgreementFlag = false;
							}
							res.setYundaiLoanAgreementDateFlag(yundaiAgreementFlag);
						}
					}
				}

			}


		}

	}

	// (2) 存管港湾产品债权转让协议
	public void getCustodyClaimsTransferInfo(ReqMsg_Match_GetCustodyClaimsTransferInfo req, ResMsg_Match_GetCustodyClaimsTransferInfo res) {

		LnLoanRelation loanRelation = regularSiteService.queryLoanRelationById(req.getLoanRelationId());
		if(loanRelation == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {
			res.setTransStatus(loanRelation.getStatus());
			res.setTransMark(loanRelation.getTransMark() == null ? null : loanRelation.getTransMark());
		}

		//存管港湾产品-债权转让协议 根据借贷关系编号查询债权转让数据
		BsLoanRelationTransferVO loanRelationTransferVO = regularSiteService.queryCustodyTransferClaims(req.getLoanRelationId());
		if(loanRelationTransferVO == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {
			res.setInSubAccountId(loanRelationTransferVO.getInSubAccountId());
			res.setAmount(loanRelationTransferVO.getAmount());
			//债权金额=债转本金+债转付息=inAmount
			res.setInAmount(loanRelationTransferVO.getInAmount());
			res.setOutUserName(getBlurName(loanRelationTransferVO.getOutUserName()));
			res.setInUserName(getBlurName(loanRelationTransferVO.getInUserName()));
			res.setOutUserIdCardNo(getBlurIdNo(loanRelationTransferVO.getOutUserIdCardNo()));
			res.setInUserIdCardNo(getBlurIdNo(loanRelationTransferVO.getInUserIdCardNo()));
			res.setBorrowUserName(getBlurName(loanRelationTransferVO.getBorrowUserName()));
			res.setBorrowUserIdCardNo(getBlurIdNo(loanRelationTransferVO.getBorrowUserIdCardNo()));
			res.setTerm(loanRelationTransferVO.getTerm());
			res.setTransferTime(loanRelationTransferVO.getTransferTime());
			res.setApproveAmount(loanRelationTransferVO.getApproveAmount());
			res.setPartnerBusinessFlag(loanRelationTransferVO.getPartnerBusinessFlag());
		}
		//根据借贷关系id 查询未还本金left_amount(未还本金)
		LnLoanRelation lnLoanRelation = regularSiteService.queryNotRepaidPrincipal(req.getLoanRelationId());
		if(lnLoanRelation == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {
			res.setLeftAmount(lnLoanRelation.getLeftAmount());
		}
		//债权受让人-云贷的自然人
//		BsUser bsUser = regularSiteService.queryYunDaiSelfCreditor();
//		if(bsUser == null) {
//			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
//		} else {
//			res.setYunDaiSelfUserName(getBlurName(bsUser.getUserName()));
//			res.setYunDaiSelfIdCard(getBlurIdNo(bsUser.getIdCard()));
//		}
		//根据子账户编号查询转让债权金额
//		if(req.getSubAccountId() != null || req.getSubAccountId() != 0) {
//			List<LnCreditTransfer> list = regularSiteService.queryInAmountById(req.getSubAccountId());
//			if(list != null && list.size() > 0) {
//				//债权金额总计
//				double inAmount = 0d;
//				for(LnCreditTransfer obj : list) {
//					inAmount=MoneyUtil.add(inAmount,obj.getInAmount()).doubleValue();
//				}
//				res.setInAmount(inAmount);
//			}
//		}

	}

	// (2) 存管港湾产品债权转让协议
	public void getCustodyClaimsTransferInfoPdf(ReqMsg_Match_GetCustodyClaimsTransferInfoPdf req, ResMsg_Match_GetCustodyClaimsTransferInfoPdf res) {

		LnLoanRelation loanRelation = regularSiteService.queryLoanRelationById(req.getLoanRelationId());
		if(loanRelation == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {
			res.setTransStatus(loanRelation.getStatus());
			res.setTransMark(loanRelation.getTransMark() == null ? null : loanRelation.getTransMark());
		}

		//存管港湾产品-债权转让协议 根据借贷关系编号查询债权转让数据
		BsLoanRelationTransferVO loanRelationTransferVO = regularSiteService.queryCustodyTransferClaims(req.getLoanRelationId());
		if(loanRelationTransferVO == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {
			res.setInSubAccountId(loanRelationTransferVO.getInSubAccountId());
			res.setAmount(loanRelationTransferVO.getAmount());
			//债权金额=债转本金+债转付息=inAmount
			res.setInAmount(loanRelationTransferVO.getInAmount());
			res.setOutUserName(loanRelationTransferVO.getOutUserName());
			res.setInUserName(loanRelationTransferVO.getInUserName());
			res.setOutUserIdCardNo(loanRelationTransferVO.getOutUserIdCardNo());
			res.setInUserIdCardNo(loanRelationTransferVO.getInUserIdCardNo());
			res.setBorrowUserName(loanRelationTransferVO.getBorrowUserName());
			res.setBorrowUserIdCardNo(loanRelationTransferVO.getBorrowUserIdCardNo());
			res.setTerm(loanRelationTransferVO.getTerm());
			res.setTransferTime(loanRelationTransferVO.getTransferTime());
			res.setApproveAmount(loanRelationTransferVO.getApproveAmount());
			res.setPartnerBusinessFlag(loanRelationTransferVO.getPartnerBusinessFlag());
		}
		//根据借贷关系id 查询未还本金left_amount(未还本金)
		LnLoanRelation lnLoanRelation = regularSiteService.queryNotRepaidPrincipal(req.getLoanRelationId());
		if(lnLoanRelation == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {
			res.setLeftAmount(lnLoanRelation.getLeftAmount());
		}
	}


	//=====================  存管新增协议 end =====================


	//=====================   赞时贷APP新增协议 start =====================

	/**
	 * 赞时贷APP借款咨询与服务协议数据 根据借款编号查询 快速信审费、平台使用费、账户管理费、代收通道费
	 * @param req
	 * @param res
     */
	public void getZsdUserLoanFeeList(ReqMsg_Match_GetZsdUserLoanFeeList req, ResMsg_Match_GetZsdUserLoanFeeList res) {
		LnLoan loan = bsLoanFeeService.queryLoanIdByPartnerLoanId(req.getPartnerLoanId());
		if(loan == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {
			List<RepayScheduleVO> feeList = bsLoanFeeService.queryFeeByLoanId(loan.getId());
			if(feeList == null || feeList.size() == 0) {
				throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
			}else {
				res.setUserName(feeList.get(0).getUserName());
				res.setLoanTime(feeList.get(0).getLoanTime());
				res.setLoanId(feeList.get(0).getLoanId());
				for(int i=0; i<feeList.size(); i++) {
					if(feeList.get(i).getSubjectCode() != null && Constants.SUBJECT_CODE_PLATFORM_SERVICE_FEE.equals(feeList.get(i).getSubjectCode())) {
						res.setPlatformServiceFee(feeList.get(i).getPlanAmount());
					}
					if(feeList.get(i).getSubjectCode() != null && Constants.SUBJECT_CODE_INFO_CERTIFIED_FEE.equals(feeList.get(i).getSubjectCode())) {
						res.setAccountManageFee(feeList.get(i).getPlanAmount());
					}
					if(feeList.get(i).getSubjectCode() != null && Constants.SUBJECT_CODE_RISK_MANAGE_SERVICE_FEE.equals(feeList.get(i).getSubjectCode())) {
						res.setRiskManageServiceFee(feeList.get(i).getPlanAmount());
					}
					if(feeList.get(i).getSubjectCode() != null && Constants.SUBJECT_CODE_COLLECTION_CHANNEL_FEE.equals(feeList.get(i).getSubjectCode())) {
						res.setCollectionChannelFee(feeList.get(i).getPlanAmount());
					}
				}
			}
		}
	}

	/**
	 * 赞时贷APP借款协议
	 * @param req
	 * @param res
     */
	public void getZsdAppUserLoanInfo(ReqMsg_Match_GetZsdAppUserLoanInfo req, ResMsg_Match_GetZsdAppUserLoanInfo res) {
		LoanDetailInfoVO loanDetailInfoVO = bsLoanRelationShipService.getZsdLoanDetailInfoVO(req.getPartnerLoanId());
		if(loanDetailInfoVO == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {
			ArrayList<HashMap<String, Object>> list = BeanUtils.classToArrayList(loanDetailInfoVO.getBsUserInfo());
			res.setDataUserInfo(list);
			res.setId(loanDetailInfoVO.getId());
			res.setLnUserName(loanDetailInfoVO.getLnUserName());
			res.setLnUserIdCard(loanDetailInfoVO.getLnUserIdCard());
			res.setLnUserZsdAccount(loanDetailInfoVO.getLnUserZANAccount());
			res.setLoanTime(loanDetailInfoVO.getLoanTime());
			res.setPurpose(loanDetailInfoVO.getPurpose());
			res.setApproveAmount(loanDetailInfoVO.getApproveAmount());
			res.setPeriod(loanDetailInfoVO.getPeriod());
			res.setAgreementRate(loanDetailInfoVO.getAgreementRate());
		}
	}

	//=====================   赞时贷APP新增协议 end =====================

	/**
	 * 云贷三方借款协议
	 * @param req
	 * @param res
     */
	public void getYunUserLoanInfo(ReqMsg_Match_GetYunUserLoanInfo req, ResMsg_Match_GetYunUserLoanInfo res) {
		LoanDetailInfoVO loanDetailInfoVO = bsLoanRelationShipService.getYunLoanDetailInfoVO(req.getPartnerLoanId());
		if(loanDetailInfoVO == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {

			// 在退票处理时，标的出账后，异步生成借款协议。借款时间loanTime设置在事务处理中，导致借款协议生成时,无法查询到借款时间
			if (loanDetailInfoVO.getLoanTime() == null && req.getLoanTime() != null) {
				loanDetailInfoVO.setLoanTime(req.getLoanTime());
			}

			ArrayList<HashMap<String, Object>> list = BeanUtils.classToArrayList(loanDetailInfoVO.getBsUserInfo());
			res.setDataUserInfo(list);
			res.setPartnerLoanId(loanDetailInfoVO.getPartnerLoanId());
			res.setLnUserName(loanDetailInfoVO.getLnUserName());
			res.setLnUserIdCard(loanDetailInfoVO.getLnUserIdCard());
			res.setLnUserBGWAccount(loanDetailInfoVO.getLnUserZANAccount());
			res.setLoanTime(loanDetailInfoVO.getLoanTime());
			res.setPurpose(loanDetailInfoVO.getPurpose());
			res.setApproveAmount(loanDetailInfoVO.getApproveAmount());
			res.setPeriod(loanDetailInfoVO.getPeriod());
			res.setAgreementRate(loanDetailInfoVO.getAgreementRate());
			res.setAddress(loanDetailInfoVO.getAddress());
			res.setEmail(loanDetailInfoVO.getEmail());
			res.setPartnerBusinessFlag(loanDetailInfoVO.getPartnerBusinessFlag());

			//7贷借款到期日以实际账单为准
			List<RepayPlan> repayPlanList = CalBillPeriod.calculateRepayPlan(loanDetailInfoVO.getLoanTime(),
					Math.round(loanDetailInfoVO.getApproveAmount()), loanDetailInfoVO.getAgreementRate());
			RepayPlan sevenLoanDueDate = repayPlanList.get(repayPlanList.size() - 1);
			String loanExpireDate = new SimpleDateFormat("yyyy年MM月dd日").format(sevenLoanDueDate.getDate());
			res.setLoanExpireDate(loanExpireDate);

			// 云贷分期产品借款到期日查询
			if(Constants.LN_LOAN_PARTNER_BUSINESS_FLAG_FIXED_INSTALLMENT.equals(loanDetailInfoVO.getPartnerBusinessFlag()) ||
					Constants.LN_LOAN_PARTNER_BUSINESS_FLAG_FIXED_PRINCIPAL_INTEREST.equals(loanDetailInfoVO.getPartnerBusinessFlag()) ) {
				// 云贷分期产品借款到期日
				LnRepaySchedule lnRepaySchedule = lnRepayScheduleMapper.lastPlanByLoanId(loanDetailInfoVO.getId());
				if(null != lnRepaySchedule) {
					String loanDate = new SimpleDateFormat("yyyy年MM月dd日").format(lnRepaySchedule.getPlanDate());
					res.setLoanYunExpireDate(loanDate);
				}else {
					res.setLoanYunExpireDate(getloanDueDate(loanDetailInfoVO.getLoanTime(), loanDetailInfoVO.getPeriod()));
				}

			}

		}
		BsSysConfig loanServiceRateCofig = bsSysConfigMapper.selectByPrimaryKey(Constants.LOAN_SERVICE_RATE_YUN_DAI);
		Double loanServiceRate = Double.parseDouble(loanServiceRateCofig.getConfValue());
		res.setLoanServiceRate(loanServiceRate);
		// 云贷分期产品借款服务费
		res.setFixLoanServiceRate(loanDetailInfoVO.getLoanServiceRate());
	}
	
	/**
	 * 7贷三方借款协议
	 * @param req
	 * @param res
     */
	public void getSevenUserLoanInfo(ReqMsg_Match_GetSevenUserLoanInfo req, ResMsg_Match_GetSevenUserLoanInfo res) {
		LoanDetailInfoVO loanDetailInfoVO = bsLoanRelationShipService.getSevenLoanDetailInfoVO(req.getPartnerLoanId());
		if(loanDetailInfoVO == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {

			// 在退票处理时，标的出账后，异步生成借款协议。借款时间loanTime设置在事务处理中，导致借款协议生成时,无法查询到借款时间
			if (loanDetailInfoVO.getLoanTime() == null && req.getLoanTime() != null) {
				loanDetailInfoVO.setLoanTime(req.getLoanTime());
			}

			ArrayList<HashMap<String, Object>> list = BeanUtils.classToArrayList(loanDetailInfoVO.getBsUserInfo());
			res.setDataUserInfo(list);
			res.setPartnerLoanId(loanDetailInfoVO.getPartnerLoanId());
			res.setLnUserName(loanDetailInfoVO.getLnUserName());
			res.setLnUserIdCard(loanDetailInfoVO.getLnUserIdCard());
			res.setLnUserBGWAccount(loanDetailInfoVO.getLnUserZANAccount());
			res.setLoanTime(loanDetailInfoVO.getLoanTime());
			res.setPurpose(loanDetailInfoVO.getPurpose());
			res.setApproveAmount(loanDetailInfoVO.getApproveAmount());
			Integer dayCount = CalBillPeriod.calBillDays(loanDetailInfoVO.getLoanTime());
			res.setDayCount(dayCount);
			res.setPeriod(loanDetailInfoVO.getPeriod());
			res.setAgreementRate(loanDetailInfoVO.getAgreementRate());
		}
		BsSysConfig loanServiceRateCofig = bsSysConfigMapper.selectByPrimaryKey(Constants.LOAN_SERVICE_RATE_SEVEN_DAI);
		Double loanServiceRate = Double.parseDouble(loanServiceRateCofig.getConfValue());
		res.setLoanServiceRate(loanServiceRate);
	}
	
	//债权转让协议需要信息获取-赞分期债权转让协议签章 生成pdf数据准备
	public void getUserClaimsTransferInfoForPdf(ReqMsg_Match_GetUserClaimsTransferInfoForPdf req, ResMsg_Match_GetUserClaimsTransferInfoForPdf res) {
		BsLoanRelationTransferVO loanRelationTransferVO = bsLoanRelationShipService.getLoanTransferInfoNewForPdf(req.getLoanRelationId());
		if(loanRelationTransferVO == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		} else {
			res.setId(loanRelationTransferVO.getId());
			res.setAmount(loanRelationTransferVO.getAmount());
			res.setInAmount(loanRelationTransferVO.getInAmount());
			res.setOutUserName(loanRelationTransferVO.getOutUserName());
			res.setInUserName(loanRelationTransferVO.getInUserName());
			res.setOutUserMobile(loanRelationTransferVO.getOutUserMobile());
			res.setInUserMobile(loanRelationTransferVO.getInUserMobile());
			res.setOutUserIdCardNo(loanRelationTransferVO.getOutUserIdCardNo());
			res.setInUserIdCardNo(loanRelationTransferVO.getInUserIdCardNo());
			res.setBorrowUserName(loanRelationTransferVO.getBorrowUserName());
			res.setBorrowUserIdCardNo(loanRelationTransferVO.getBorrowUserIdCardNo());
			res.setFirstRepayDate(loanRelationTransferVO.getFirstRepayDate());
			res.setRepayAmount4Month(loanRelationTransferVO.getRepayAmount4Month());
			res.setRepayAmount4All(loanRelationTransferVO.getRepayAmount4All());
			res.setTerm(loanRelationTransferVO.getTerm());
			res.setLeftTerm(loanRelationTransferVO.getLeftTerm());
			res.setFirstTerm(loanRelationTransferVO.getFirstTerm());
			res.setTransferTime(loanRelationTransferVO.getTransferTime());
			res.setProductName(loanRelationTransferVO.getProductName());
			res.setInSubAccountId(loanRelationTransferVO.getInSubAccountId());
			res.setDiscountAmount(loanRelationTransferVO.getDiscountAmount());
			res.setExpectProfit(loanRelationTransferVO.getExpectProfit());

			//赞分期债转协议-新旧协议时间区分标志
			BsSysConfig configUser = sysConfigService.findConfigByKey(Constants.ZAN_AGREEMENT_DATE_4_NEW);
			if(configUser != null) {

				String zanAgreementDate = configUser.getConfValue();
				boolean zanAgreementFlag = false;
				//债转时间跟配置表中时间比较
				if(DateUtil.getDiffeDay(DateUtil.parseDate(DateUtil.formatYYYYMMDD(loanRelationTransferVO.getTransferTime())), DateUtil.parseDate(zanAgreementDate)) < 0) {
					zanAgreementFlag = false;
				}else {
					zanAgreementFlag = true;
				}
				res.setZanAgreementDate(zanAgreementFlag);

			}
		}
	}

	public void queryBorrowerInfo(ReqMsg_Match_QueryBorrowerInfo req, ResMsg_Match_QueryBorrowerInfo res) {

		BorrowerInfoVO info = regularSiteService.queryBorrowerInfo(req.getUserId(), req.getPartnerCode(), req.getLoanRelationId());
		res.setBorrowerInfo(BeanUtils.classToHashMap(info));
	}

	// 赞时贷资金迁移，协议根据loanRelationId查询借款人的资产方
	public void checkLnUserPartnerCode(ReqMsg_Match_CheckLnUserPartnerCode req, ResMsg_Match_CheckLnUserPartnerCode res) {
		CheckLnUserPartnerCodeVO vo = regularSiteService.queryCheckLnUserPartnerCode(req.getLoanRelationId());
		res.setPartnerCode(vo.getPartnerCode());
	}

	// =======================代偿协议重新生成合规前的版本 start =======================

	//收款确认函-服务费、收款确认函-债转 债权转让列表
	public void getUserClaimsTransferInfoListRenew(ReqMsg_Match_GetUserClaimsTransferInfoListRenew req,
												   ResMsg_Match_GetUserClaimsTransferInfoListRenew res) {

		List<CompensateTransfersVO> list = new ArrayList<CompensateTransfersVO>();

		LnLoan lnLoan = lnLoanMapper.selectLoanByPartnerLoanId(req.getLoanId());

		BsUserCompensateVO compensateVO =  new BsUserCompensateVO();
		if(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF.equals(req.getPartnerEnum())) {
			//1、债权受让人-云贷的自然人
			compensateVO = depFixedRepayPaymentService.compensaterInfo(lnLoan.getLoanTime(), Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF);
			list = lnRepayScheduleMapper.selectYunCollectionLettersByOrderNoRenew(req.getOrderNo());
		}else if(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF.equals(req.getPartnerEnum())){
			//2、债权受让人-7贷的自然人
			compensateVO = depFixedRepayPaymentService.compensaterInfo(lnLoan.getLoanTime(), Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF);
			list = lnRepayScheduleMapper.selectSevenCollectionLettersByOrderNo(req.getOrderNo());
		}
		if(compensateVO == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}else {
			res.setYunDaiSelfUserName(getBlurName(compensateVO.getUserName()));
			res.setYunDaiSelfIdCard(getBlurIdNo(compensateVO.getIdCard()));
		}

		List<CompensateTransfersVO> compensateTransfersList = new ArrayList<CompensateTransfersVO>();
		if(list != null && list.size() > 0) {
			for(CompensateTransfersVO vo : list) {
				CompensateTransfersVO compensateTransfersVO = new CompensateTransfersVO();
				compensateTransfersVO.setApproveAmount(vo.getApproveAmount());
				compensateTransfersVO.setAgreementRate(vo.getAgreementRate());
				compensateTransfersVO.setPeriod(vo.getPeriod()*30);//云贷借款期限 天数
				compensateTransfersVO.setLoanUserName(getBlurName(vo.getLoanUserName()));
				compensateTransfersVO.setLoanIdCard(getBlurIdNo(vo.getLoanIdCard()));
				//7贷借款期限 期数
				compensateTransfersVO.setSevenPeriod(vo.getPeriod());
				compensateTransfersVO.setLoanAgreementNumber(vo.getLoanAgreementNumber());
				compensateTransfersVO.setTransferCreditorAmount(vo.getTransferCreditorAmount());
				//单笔债转的服务费 = 借款人剩余未还本金总额*代偿天数*（24%-借款协议利率）/365 (代偿天数/计息天数)
				compensateTransfersVO.setDebtServiceFee(vo.getDebtServiceFee());
				//代偿成功的时间
				compensateTransfersVO.setLateRepayDate(req.getLateRepayDate());
				compensateTransfersList.add(compensateTransfersVO);
			}
			ArrayList<HashMap<String,Object>> resultList = BeanUtils.classToArrayList(compensateTransfersList);
			res.setDataGrid(resultList);
		}
	}

	//(2) 债权转让通知书
	public void getCompensateUserTransferInfoRenew(ReqMsg_Match_GetCompensateUserTransferInfoRenew req,
												   ResMsg_Match_GetCompensateUserTransferInfoRenew res) {

		//1、根据合作方借款编号loan_id查询四方借款协议编号
		BsUserSealFile bsUserSealFile = bsUserSealFileService.queryAgreementNo(req.getLoanId(), req.getPartnerEnum());
		if(bsUserSealFile != null) {
			res.setLoanAgreementNumber(bsUserSealFile.getAgreementNo());//四方借款协议编号
		}
		LnLoan lnLoan = new LnLoan();
		LnUser lnUser = new LnUser();
		lnLoan = regularSiteService.queryLoanByPartnerLoanId(req.getLoanId());
		if(lnLoan == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}
		lnUser = regularSiteService.queryLnUserInfoById(lnLoan.getLnUserId());

		BsUserCompensateVO vo =  new BsUserCompensateVO();
		if(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF.equals(req.getPartnerEnum())) {
			//1、债权受让人-云贷的自然人
			vo = depFixedRepayPaymentService.compensaterInfo(lnLoan.getLoanTime(), Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF);
		}else if(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF.equals(req.getPartnerEnum())){
			//2、债权受让人-7贷的自然人
			vo = depFixedRepayPaymentService.compensaterInfo(lnLoan.getLoanTime(), Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF);
		}
		if(vo == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}else {
			res.setYunDaiSelfUserName(getBlurName(vo.getUserName()));
			res.setYunDaiSelfIdCard(getBlurIdNo(vo.getIdCard()));
		}

		BsUserCompensateVO bsUserCompensateVO = depFixedRepayPaymentService.compensaterInfo(lnLoan.getLoanTime(), req.getPartnerEnum());

		if(bsUserCompensateVO == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}else {
			res.setYunDaiSelfUserName(getBlurName(bsUserCompensateVO.getUserName()));
			res.setYunDaiSelfIdCard(getBlurIdNo(bsUserCompensateVO.getIdCard()));
		}

		List<CompensateTransfersVO> CompensateTransfersList = new ArrayList<CompensateTransfersVO>();

		//4、借款人姓名/身份证号码
		if(lnUser != null) {
			res.setLoanUserName(getBlurName(lnUser.getUserName()));
			res.setLoanIdCard(getBlurIdNo(lnUser.getIdCard()));
		}
		//5、债权转让列表
		List<CompensateTransfersVO> resultVOList = regularSiteService.queryCompensateTransferListRenew(lnLoan.getId());
		if(resultVOList != null && resultVOList.size() > 0) {
			for(CompensateTransfersVO obj : resultVOList) {
				CompensateTransfersVO compensateVO = new CompensateTransfersVO();
				//统计7贷代偿人-代偿的笔数
				if(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF.equals(req.getPartnerEnum())){
					int sevenCompensateCount = lnCompensateRelationMapper.select7CompensationCount(lnLoan.getId());
					compensateVO.setSevenCompensateCount(sevenCompensateCount);
				}

				compensateVO.setUserName(getBlurName(obj.getUserName()));
				compensateVO.setIdCard(getBlurIdNo(obj.getIdCard()));
				//转让剩余期数/天数
				compensateVO.setNoBillingPeriod(obj.getNoBillingPeriod());
				//代偿成功的时间
				compensateVO.setLateRepayDate(req.getLateRepayDate());

				//债转通知书-债权转让金额 = 债权对应代偿本金的*未还账单天数*借款利率/365 + 债权对应代偿本金的
				compensateVO.setNoticeTransferCreditorAmount(obj.getAgreementAmount());

				//债权转让金额 = 未还本金*借款利率*未还账单期限/365+未还本金，同上
				compensateVO.setTransferCreditorAmount(compensateVO.getNoticeTransferCreditorAmount());
				//还款期次/代偿期数
				compensateVO.setSerialId(compensateVO.getSerialId());

				if(bsUserCompensateVO != null) {
					compensateVO.setYunDaiSelfUserName(getBlurName(bsUserCompensateVO.getUserName()));
					compensateVO.setYunDaiSelfIdCard(getBlurIdNo(bsUserCompensateVO.getIdCard()));
				}
				CompensateTransfersList.add(compensateVO);
			}
			ArrayList<HashMap<String,Object>> resultList = BeanUtils.classToArrayList(CompensateTransfersList);
			res.setDataGrid(resultList);
		}

	}

	//(3) 债权转让协议
	public void getCompensateTransferInfoRenew(ReqMsg_Match_GetCompensateTransferInfoRenew req,
											   ResMsg_Match_GetCompensateTransferInfoRenew res) {

		LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(req.getLoanId());
		BsUserCompensateVO vo = depFixedRepayPaymentService.compensaterInfo(lnLoan.getLoanTime(), req.getPartnerEnum());

		if(vo == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}else {
			res.setYunDaiSelfUserName(getBlurName(vo.getUserName()));
			res.setYunDaiSelfIdCard(getBlurIdNo(vo.getIdCard()));
			res.setYunDaiSelfMobile(getBlurMobile(vo.getMobile()));
		}
		//3、债转数据
		CompensateTransfersVO compensateTransfersVO = regularSiteService.selectCompensateTransferInfo(req.getLoanId(), req.getLoanRelationId());
		if(compensateTransfersVO == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}else {
			res.setUserName(getBlurName(compensateTransfersVO.getUserName())); //币港湾实际出借人
			res.setIdCard(getBlurIdNo(compensateTransfersVO.getIdCard())); //出借人身份证号
			res.setLoanUserName(getBlurName(compensateTransfersVO.getLoanUserName()));//借款人姓名
			res.setLoanIdCard(getBlurIdNo(compensateTransfersVO.getLoanIdCard())); //借款人身份证号
			res.setPeriod(compensateTransfersVO.getPeriod()*30); //借款期限 天数
			res.setApproveAmount(compensateTransfersVO.getApproveAmount()); //借款本金
			//债权转让金额 = 出借人待收未还本金*借款利率*未还账单期限/365+出借人待收未还本金
			res.setTransferCreditorAmount(compensateTransfersVO.getAgreementAmount());
			res.setSevenPeriod(compensateTransfersVO.getPeriod()); //7贷借款期限 期数
			res.setUserMobile(getBlurMobile(compensateTransfersVO.getUserMobile())); //出借人手机号

		}

	}

	// =======================代偿协议重新生成合规前的版本 end =======================

	
	/**
	 * 手机号模糊处理
	 * @param mobile
	 * @return
	 */
	private String getBlurMobile(String mobile) {
		String str = "";
		if(StringUtil.isNotBlank(mobile)){
			str = mobile.substring(0, 3);
			str = str + "****"+mobile.substring(mobile.length()-4);
		}
		return str;
	}

	/**
	 * 名字模糊
	 * @param name
	 * @return
	 */
	private String getBlurName(String name) {
		String str = "";
		if(StringUtil.isNotBlank(name)){
			str = name.substring(0, 1);
			if(name.length() > 3) {
				str = str+"**";
			}else {
				for (int i = 1; i < name.length(); i++) {
					str += "*";
				}
			}
		}
		return str;
	}

	/**
	 * 18位的隐藏掉中间10位，如123456**********12；15位隐藏中间8位，如123456********1
	 * 身份证
	 * @param idNo
	 * @return
	 */
	private String getBlurIdNo(String idNo) {
		String str = "";
		if(StringUtil.isNotBlank(idNo)){
			str = idNo.substring(0, 6);
			if(idNo.length() == 18){
				str += "**********" + idNo.substring(idNo.length()-2);
			}else{
				str += "********" + idNo.substring(idNo.length()-1);
			}
		}
		return str;
	}

	/**
	 * 借款协议中邮箱信息模糊处理，显示首个字符串后面模糊处理
	 * a********@sina.com
	 * @param email
	 * @return
	 */
	private String getBlurEmail(String email) {
		String str = "";
		String resultStr = "";
		if(StringUtil.isNotBlank(email)){
			if(email.contains("@")) {
				String[] emailArray = email.split("@");
				str = emailArray[0];
				resultStr = String.valueOf(emailArray[0].toString().charAt(0));

				for (int i = 1; i < str.length(); i++) {
					resultStr += "*";
				}
				resultStr = resultStr + "@" + emailArray[1].toString();
			}else {
				resultStr = email;
			}
		}
		return resultStr;
	}

	/**
	 * 借款协议中地址信息模糊处理
	 * 1、地址显示到区；
	 * 2、没有区就显示前8个字符；
	 * 3、地址少于8个字符全显示；
	 * 浙江省杭州市江干区**********
	 * @param address
	 * @return
	 */
	private String getBlurAddress(String address) {
		String str = "";
		String blurStr = "";
		String resultStr = "";
		if (StringUtil.isNotBlank(address)) {
			if (address.length() > 8) {
				if (address.contains("区")) {
					String[] addArray = address.split("区");
					str = addArray[1];
					resultStr = address.substring(0, 7);
					for (int i = 0; i < str.length(); i++) {
						blurStr += "*";
					}
					resultStr = addArray[0].toString() + "区" + blurStr;
				} else {
					resultStr = address.substring(0, 7);
				}
			} else {
				resultStr = address;
			}
		}
		return resultStr;
	}

	/**
	 * 计算借款到期日
	 * @param loanTime 借款成功时间
	 * @param period 借款期限
     * @return
     */
	private String getloanDueDate(Date loanTime, int period) {
		Calendar calendar = Calendar.getInstance();
		String day = new SimpleDateFormat("dd").format(loanTime);
		Integer repayDay = Integer.parseInt(day);
		if (repayDay >= 29) {
			repayDay = 28;
		}
		calendar.setTime(loanTime);
		calendar.add(Calendar.MONTH, period);
		calendar.set(Calendar.DAY_OF_MONTH,
				Math.min(calendar.getActualMaximum(Calendar.DAY_OF_MONTH), repayDay));
		Date date = calendar.getTime();
		return new SimpleDateFormat("yyyy年MM月dd日").format(date);
	}

}
