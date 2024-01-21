package com.pinting.gateway.in.facade.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.DetailsOfDebtVO;
import com.pinting.business.service.site.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.model.vo.BsMatchVO;
import com.pinting.business.model.vo.BsSubAccountVO;
import com.pinting.business.model.vo.LnLoanRelationVO;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.in.util.InterfaceVersion;
/**
 * @Project: business
 * @Title: InvestFacade.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:44:15
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("appInvest")
public class InvestFacade{
	@Autowired
	private InvestService investService;
	@Autowired
	private SubAccountService subAccountService;
	@Autowired
    private ProductService productService;
	@Autowired
	private UserService userService;
	@Autowired
	private BsMatchService matchService;
	@Autowired
	private AssetsService assetsService; 
	@Autowired
	private RegularSiteService regularSiteService;
	@Autowired
	private AgreementVersionService agreementVersionService;
	
	@InterfaceVersion("EarningsListQuery/1.0.0")
	public void earningsListQuery(ReqMsg_Invest_EarningsListQuery req, ResMsg_Invest_EarningsListQuery resp) {
		
		List<BsDailyInterest> BsDailyInterestList=investService.findDailyInvestByUserId(req.getUserId(), req.getPageIndex(), req.getPageSize());
		ArrayList<HashMap<String, Object>> investEarnings=null;
		if(BsDailyInterestList != null && BsDailyInterestList.size() > 0){
			investEarnings=new ArrayList<HashMap<String,Object>>();
			for (BsDailyInterest bsDailyInterest : BsDailyInterestList) {
				HashMap<String, Object> mapBonus=new HashMap<String, Object>();
				mapBonus.put("earningsDate", bsDailyInterest.getTime());
				mapBonus.put("amount", bsDailyInterest.getInterest());
				investEarnings.add(mapBonus);
			}
		}
		
		int pageSize=req.getPageSize();
		resp.setTotalCount((int)Math.ceil((double)investService.findDailyInvestCountByUserId(req.getUserId())/pageSize));
		resp.setInvestEarnings(investEarnings);
		resp.setPageIndex(req.getPageIndex());
	}
	
	
	@InterfaceVersion("InvestListQuery/1.0.0")
	public void investListQuery(ReqMsg_Invest_InvestListQuery req, ResMsg_Invest_InvestListQuery resp) {
		
		List<BsSubAccountVO> myInvest = subAccountService.findMyInvestByUserIdAndType(Constants.PRODUCT_TYPE_REG,req.getUserId(),req.getStartPage(), req.getPageSize(),0);
		int pageSize=req.getPageSize();
		resp.setTotal((int)Math.ceil((double)subAccountService.findMyInvestCountByUserId(req.getUserId())/pageSize));
		
		ArrayList<HashMap<String,Object>> bean = BeanUtils.classToArrayList(myInvest);
		
		resp.setValueList(bean);
		//resp.setPageIndex(req.getStartPage());//传递有误
		
		
	}
	
	@InterfaceVersion("InvestListQuery/1.0.1")
	public void investListQuery_1(ReqMsg_Invest_InvestListQuery req, ResMsg_Invest_InvestListQuery resp) {
		
		//List<BsSubAccountVO> myInvest = subAccountService.selectMyInvestment(req.getUserId(),req.getStartPage(), req.getPageSize(), null);
		List<BsSubAccountVO> myInvest = subAccountService.findMyInvestByUserIdAndType(Constants.PRODUCT_TYPE_REG,req.getUserId(),req.getStartPage(), req.getPageSize(), 0);
        
		int pageSize=req.getPageSize();
		resp.setTotal((int)Math.ceil((double)subAccountService.findMyInvestCountByUserId(req.getUserId())/pageSize));
		
		ArrayList<HashMap<String,Object>> bean = BeanUtils.classToArrayList(myInvest);


		//查询云贷授权委托书生效时间
		BsAgreementVersion bsAgreementVersionYun =
				agreementVersionService.queryAgreementVersionDate(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_POWERATTORNEY, Constants.AGREEMENT_VERSION_NUMBER_1_1);
		Date agreementEffectiveStartTimeYun = bsAgreementVersionYun.getAgreementEffectiveStartTime();
		//查询7贷授权委托书生效时间
		BsAgreementVersion bsAgreementVersionSeven =
				agreementVersionService.queryAgreementVersionDate(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_POWERATTORNEY, Constants.AGREEMENT_VERSION_NUMBER_1_1);
		Date agreementEffectiveStartTimeSeven = bsAgreementVersionSeven.getAgreementEffectiveStartTime();

		if (bean != null) {
			for (HashMap<String, Object> hashMap : bean) {
				//授权委托书只针对存管云贷、7贷产品
				if(Constants.BS_PRODUCT_PROPERTY_ID_4.equals((Integer) hashMap.get("propertyId"))) {
					//计划管理页面《出借服务协议》与《授权委托书》协议名字显示判断
					Date openTime = (Date) hashMap.get("openTime");
					if(openTime.after(agreementEffectiveStartTimeYun)) {
						hashMap.put("powerAttorneyFlag", "yes");
					}else {
						hashMap.put("powerAttorneyFlag", "no");
					}
				}else if(Constants.BS_PRODUCT_PROPERTY_ID_6.equals((Integer) hashMap.get("propertyId"))) {
					Date openTime = (Date) hashMap.get("openTime");
					if(openTime.after(agreementEffectiveStartTimeSeven)) {
						hashMap.put("powerAttorneyFlag", "yes");
					}else {
						hashMap.put("powerAttorneyFlag", "no");
					}
				}else if(Constants.BS_PRODUCT_PROPERTY_ID_7.equals((Integer) hashMap.get("propertyId"))) {
						hashMap.put("powerAttorneyFlag", "yes");
				} else {
					hashMap.put("powerAttorneyFlag", "null");
				}

			}

			resp.setValueList(bean);
		}else {
			resp.setValueList(null);
		}
		
	}
	
	@InterfaceVersion("InvestEntrustListQuery/1.0.0")
	public void investListQuery_2(ReqMsg_Invest_InvestEntrustListQuery req, ResMsg_Invest_InvestEntrustListQuery resp) {
		//委托计划
		ResMsg_Invest_InvestListQuery myInvestEntrust = assetsService.commissionPlanList(req.getUserId(),req.getStartPageEntrust(), req.getPageSizeEntrust());
		resp.setInvestAccountsEntrust(myInvestEntrust.getValueList());
		resp.setTotalPageEntrust(myInvestEntrust.getTotal());
	}
	
	@InterfaceVersion("PlatformStatistics/1.0.0")
	public void platformStatistics(ReqMsg_Invest_PlatformStatistics req,ResMsg_Invest_PlatformStatistics res){
	    NumberTool tool = new NumberTool();
	    // 累计投资金额
	    Double totalInvestAmount = 0d;
	    List<BsProduct> productList = productService.findProductList();
	    for (BsProduct bsProduct : productList) {
	        totalInvestAmount = MoneyUtil.add(totalInvestAmount, bsProduct.getCurrTotalAmount()).doubleValue();
        }
	    res.setTotalInvestAmount(tool.format("0.00", totalInvestAmount));
	    // 累计注册人数
	    res.setTotalRegUser(tool.format("0", userService.countRegNum()));
	    // 累计赚取收益
	    res.setTotalInterestAmount(tool.format("0.00", userService.countUserIncome()));
	    // 按月累计投资总额
		subAccountService.platformStatistics(req,res);
		List<HashMap<String, Object>> investsMonth = res.getInvestMentOverDateMonth();
		ArrayList<HashMap<String, Object>> newInvestsMonth = new ArrayList<>();
		if(!CollectionUtils.isEmpty(investsMonth)) {
			if(investsMonth.size() > 4) {
				for (int i = investsMonth.size() - 4; i < investsMonth.size(); i++) {
					newInvestsMonth.add(investsMonth.get(i));
				}
				res.setInvestMentOverDateMonth(newInvestsMonth);
			}
		}


		// 待赚取收益
		// 各产品投资情况
	}
	
	@InterfaceVersion("PlatformStatistics/1.0.1")
	public void platformStatistics_1(ReqMsg_Invest_PlatformStatistics req,ResMsg_Invest_PlatformStatistics res){
	    NumberTool tool = new NumberTool();
	    // 累计投资金额
	    Double totalInvestAmount = 0d;
	    /*List<BsProduct> productList = productService.findProductList();
	    for (BsProduct bsProduct : productList) {
	        totalInvestAmount = MoneyUtil.add(totalInvestAmount, bsProduct.getCurrTotalAmount()).doubleValue();
        }*/

		totalInvestAmount = productService.queryAccumulatedInvestment();

	    res.setTotalInvestAmount(tool.format("0.00", totalInvestAmount));
	    // 累计注册人数
	    res.setTotalRegUser(tool.format("0", userService.countRegNum()));
	    // 累计赚取收益
	    res.setTotalInterestAmount(tool.format("0.00", userService.countUserIncome()));
	    // 按月累计投资总额
		subAccountService.platformStatisticsNews(req,res);
		// 待赚取收益
		// 各产品投资情况

		List<HashMap<String, Object>> investsMonth = res.getInvestMentOverDateMonth();
		ArrayList<HashMap<String, Object>> newInvestsMonth = new ArrayList<>();
		if(!CollectionUtils.isEmpty(investsMonth)) {
			if(investsMonth.size() > 4) {
				for (int i = investsMonth.size() - 4; i < investsMonth.size(); i++) {
					newInvestsMonth.add(investsMonth.get(i));
	}
				res.setInvestMentOverDateMonth(newInvestsMonth);
			}
		}
	}
	
	@InterfaceVersion("LoadMatch/1.0.0")
	public void loadMatch(ReqMsg_Invest_LoadMatch req, ResMsg_Invest_LoadMatch res) {
		Integer userId = req.getUserId();
		Integer productId = req.getProductId();
		Integer start = req.getStart();
		Integer pageSize = req.getPageSize();
		Integer subAccountId = req.getSubAccountId();

		BsPropertyInfo bsPropertyInfo = productService.queryPropertyInfoByProductId(productId);
		res.setPropertySymbol(bsPropertyInfo.getPropertySymbol());
		if (Constants.PRODUCT_PROPERTY_SYMBOL_ZAN.equals(bsPropertyInfo.getPropertySymbol())) {
			List<LnLoanRelationVO> relationList = regularSiteService.queryLnLoanRelationList(userId,subAccountId, start, pageSize);
			int count = regularSiteService.countLnLoanRelation(userId, subAccountId);
			Integer totalPage = 1;
			if(count > 0) {
				totalPage = count%pageSize==0?count/pageSize:count/pageSize+1;
			}
			ArrayList<HashMap<String,Object>> bean = BeanUtils.classToArrayList(relationList);

			if (bean != null && bean.size() > 0) {
				for (HashMap<String, Object> hashMap : bean) {
					String name = (String)hashMap.get("borrowerName");
					String borrowerName = name.substring(0,1);
					if(name.length() > 3) {
						borrowerName = borrowerName+"**";
					}else {
						for(int i= 0;i<name.length()-1;i++){
							borrowerName = borrowerName+"*";
						}
					}
					hashMap.put("borrowerName", borrowerName);
				}
			}
			res.setDataList(bean);
			res.setTotal(totalPage);
		}else if(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF.equals(bsPropertyInfo.getPropertySymbol()) ||
				Constants.PRODUCT_PROPERTY_SYMBOL_ZSD.equals(bsPropertyInfo.getPropertySymbol()) ||
				Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF.equals(bsPropertyInfo.getPropertySymbol()) ||
				Constants.PRODUCT_PROPERTY_SYMBOL_FREE.equals(bsPropertyInfo.getPropertySymbol())) { //存管云贷、赞时贷、七贷、自由产品对应的债权明细
			int count = regularSiteService.queryDepClaimsCountBySubAccountId(subAccountId);
			List<DetailsOfDebtVO> list = regularSiteService.queryDepClaimsListBySubAccountIdNew(subAccountId, start, pageSize);

			Integer totalPage = 1;
			if(count > 0) {
				totalPage = count%pageSize==0?count/pageSize:count/pageSize+1;
			}
			List<BsMatchVO> matchList =  new ArrayList<BsMatchVO>();
			if(list != null && list.size() > 0) {
				for(DetailsOfDebtVO obj : list) {
					BsMatchVO vo = new BsMatchVO();
					String name = obj.getLoanName();
					String borrowerName = name.substring(0,1);
					if(name.length() > 3) {
						borrowerName = borrowerName+"**";
					}else {
						for(int i= 0;i<name.length()-1;i++){
							borrowerName = borrowerName+"*";
						}
					}
					vo.setBorrowerName(borrowerName); //借款人
					vo.setAmount(obj.getInitAmount()); //借款金额
					//1:借款中,2:部分还款,3:已还完
					if(Constants.LOAN_RELATION_STATUS_REPAID.equals(obj.getStatus())) {
						vo.setRepayStatus("3"); //已还完
					}else if(Constants.LOAN_RELATION_STATUS_TRANSFERRED.equals(obj.getStatus()) && obj.getLeftAmount() == 0d) {
						vo.setRepayStatus("3"); //债转 剩余本金为0 //已还完
					}else {
						vo.setRepayStatus("1"); //借款中
					}
					matchList.add(vo);
				}
			}
			ArrayList<HashMap<String,Object>> bean = BeanUtils.classToArrayList(matchList);
			res.setDataList(bean);
			res.setTotal(totalPage);
		}else {
			//云贷、7贷老产品的债权明细
			BsSubAccount bsSubAccount = subAccountService.findSubAccountById(subAccountId);
			int count = 0;
			ArrayList<HashMap<String,Object>> bean = null;
			if(Constants.PRODUCT_TYPE_REG.equals(bsSubAccount.getProductType())) { // 未生成AUTH户时，债权明细查询bs_match表
				List<BsMatchVO> matchList = matchService.getMatchListByUserIdProductId(userId, productId, subAccountId, start, pageSize);
				count = matchService.countMatchListByUserIdProductId(userId,productId,subAccountId);
				bean = BeanUtils.classToArrayList(matchList);
			}else if(Constants.PRODUCT_TYPE_AUTH_7.equals(bsSubAccount.getProductType()) ||
					Constants.PRODUCT_TYPE_AUTH_YUN.equals(bsSubAccount.getProductType()) ) { // 生成AUTH户后，债权明细查询ln_loan_relation表
				int afterCount = regularSiteService.queryDepClaimsCountBySubAccountId(subAccountId);
				int beforeCount = matchService.countMatchListByUserIdProductId(userId,productId,subAccountId);
				count = beforeCount + afterCount;
				
				List<BsMatchVO> matchList =  new ArrayList<BsMatchVO>();
				List<BsMatchVO> list =  new ArrayList<BsMatchVO>();
				
				if(count > 0) {
					list = matchService.getMatchListIncludePostMigration(userId, productId, subAccountId, start, pageSize);
				}
				if(list != null && list.size() > 0) {
					for(BsMatchVO vo : list) {
						if (Constants.BS_MATCH.equals(vo.getTableFlag())) {
							String name = vo.getBorrowerName();
							String borrowerName = name.substring(0,1);
							if(name.length() > 3) {
								borrowerName = borrowerName+"**";
							}else {
								for(int i= 0;i<name.length()-1;i++){
									borrowerName = borrowerName+"*";
								}
							}
							vo.setBorrowerName(borrowerName); //借款人
							if(Constants.LOAN_RELATION_STATUS_REPAID.equals(vo.getRepayStatus())) {
								vo.setRepayStatus("3"); //已还完
							}else if(Constants.LOAN_RELATION_STATUS_TRANSFERRED.equals(vo.getRepayStatus()) && vo.getLeftPrincipal() == 0d) {
								vo.setRepayStatus("3"); //债转 剩余本金为0 //已还完
							}else {
								vo.setRepayStatus("1"); //借款中
							}
						} else if (Constants.LN_LOAN_RELATION.equals(vo.getTableFlag())) {
							vo.setRepayStatus("3");
						}
						matchList.add(vo);
					}
				}
				bean = BeanUtils.classToArrayList(matchList);
			}
			Integer totalPage = 1;
			if(count > 0) {
				totalPage = count%pageSize==0?count/pageSize:count/pageSize+1;
			}
			res.setDataList(bean);
			res.setTotal(totalPage);
		}
	}
	
	@InterfaceVersion("SysLoadMatchTime/1.0.0")
	public void sysLoadMatchTime(ReqMsg_Invest_SysLoadMatchTime req, ResMsg_Invest_SysLoadMatchTime res) {
		res.setLoadMatchTime(Constants.LOAD_MATCH_SHOW_TIME);
	}
	
	
	@InterfaceVersion("AccountBalance/1.0.0")
	public void accountBalance(ReqMsg_Invest_AccountBalance req, ResMsg_Invest_AccountBalance res) {
		ResMsg_Invest_AccountBalance  resp = assetsService.accountBalance(req.getUserId());
		res.setAvailableBalance(resp.getAvailableBalance());
		res.setHavePayPassword(resp.getHavePayPassword());
		res.setAvailableBalance(resp.getAvailableBalance());
		res.setStatus(resp.getStatus());
		res.setDepAvailableBalance(resp.getDepAvailableBalance());
	}

	@InterfaceVersion("PlatformData/1.0.0")
	public void platformData(ReqMsg_Invest_PlatformData req, ResMsg_Invest_PlatformData res) {
		switch (req.getType()) {
			case 1:
				// 累计出借额（平台自成立以来出借的金额总和，仅统计本金）
				res.setAmount(subAccountService.sumLoanAmount());
				break;
			case 2:
				// 自成立以来累计借贷金额（平台成立以来出借的金额总和，仅统计本金）
				res.setAmount(subAccountService.sumBorrowerAmount());
				break;
			case 3:
				// 自成立以来累计借贷笔数（平台成立以来出借的总笔数）
				res.setTimes(subAccountService.countLoanTimes());
				break;
			case 4:
				// 当前待还借贷金额（借款人未还款的出借金额总和，仅统计本金）
				res.setAmount(subAccountService.sumLeftAmount());
				break;
			case 5:
				// 当前待还借贷笔数（借款人未还款的出借总笔数）
				res.setTimes(subAccountService.countLeftAmountTimes());
				break;
			case 6:
				// 累计出借人数（平台成立以来，累计的出借总人数，排除VIP理财人）
				res.setTimes(subAccountService.countLoanUserTimes());
				break;
			case 7:
				// 当期出借人数（目前有待回款的出借人数，排除VIP理财人）
				res.setTimes(subAccountService.countCurrentLoanUserTimes());
				break;
			case 8:
				// 前十大出借人出借余额占比（按待回款金额排序，前十大出借人出借余额总和/当前待还借贷金额，仅统计本金，排除VIP理财人）
				res.setAmount(subAccountService.sumTenLargestLeftAmount());
				break;
			case 9:
				// 最大单一出借人出借余额占比（按待回款金额排序，最大单一出借人出借余额总和/当前待还借贷金额，仅统计本金，排除VIP理财人）
				res.setAmount(subAccountService.sumLargestLeftAmount());
				break;
			case 10:
				// 累计借款人数（平台成立以来，累计的借款人数）
				res.setTimes(subAccountService.countBorrowerUserTimes());
				break;
			case 11:
				// 当期借款人数（目前处于借款状态的借款人数）
				res.setTimes(subAccountService.countCurrentBorrowerUserTimes());
				break;
			case 12:
				// 前十大借款人待还金额占比（按当前借款金额排序，前十大借款人待还金额总和/当前待还借贷金额，仅统计本金）
				res.setAmount(subAccountService.sumTenBorrowerLargestLeftAmount());
				break;
			case 13:
				// 最大单一借款人待还金额占比（按当前借款金额排序，最大单一借款人待还金额总和/当前待还借贷金额，仅统计本金）
				res.setAmount(subAccountService.sumBorrowerLargestLeftAmount());
				break;
			case 14:
				// 借款人逾期金额（当前对投资人已经处于逾期状态的所有借款的金额总和，仅统计本金）
				res.setAmount(subAccountService.sumLateAmount());
				break;
			case 15:
				// 借款人逾期笔数（当前对投资人处于逾期状态的所有借款的笔数之和）
				res.setTimes(subAccountService.countLateAmount());
				break;
			case 16:
				// 借款人逾期90天以上金额（当前对投资人逾期＞90天的借款金额总和，仅统计本金）
				res.setAmount(subAccountService.sum90LateAmount());
				break;
			case 17:
				// 借款人逾期90天以上笔数（当前对投资人逾期＞90天的借款笔数之和）
				res.setTimes(subAccountService.count90LateAmount());
				break;
			case 18:
				// 累计代偿金额（平台自成立以来，累计的代偿金额，包括本金和利息）
				res.setAmount(subAccountService.sumLateNotAmount());
				break;
			case 19:
				// 累计代偿笔数（平台自成立以来，累计代偿的笔数之和）
				res.setTimes(subAccountService.countLateNotAmount());
				break;
		}
	}
}
