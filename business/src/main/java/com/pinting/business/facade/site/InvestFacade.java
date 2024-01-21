package com.pinting.business.facade.site;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.BsAgreementVersion;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.site.*;
import com.pinting.core.redis.JedisClientDaoSupport;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.model.BsDailyInterest;
import com.pinting.business.model.BsProduct;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import org.springframework.util.CollectionUtils;

/**
 * @Project: business
 * @Title: InvestFacade.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:44:15
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("Invest")
public class InvestFacade{
	@Autowired
	private InvestService investService;
	@Autowired
	private SubAccountService subAccountService;
	@Autowired
	private UserService userService;
	@Autowired
    private ProductService productService;
	@Autowired
	private AssetsService assetsService;
	@Autowired
	private AgreementVersionService agreementVersionService;

	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

	/**
	 * 根据userId查询投资收益明细
	 * @param req
	 * @param resp
	 */
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
	
	
	
	public void investListQuery(ReqMsg_Invest_InvestListQuery req, ResMsg_Invest_InvestListQuery resp) {
//	    List<BsSubAccountVO> myInvest = subAccountService.selectMyInvestment(req.getUserId(), req.getStartPage(), req.getPageSize(), null);
		List<BsSubAccountVO> myInvest = subAccountService.findMyInvestByUserIdAndType(Constants.PRODUCT_TYPE_REG,req.getUserId(),req.getStartPage(), req.getPageSize(),0);
		int pageSize=req.getPageSize();
		resp.setTotal((int)Math.ceil((double) subAccountService.countMyInvestBGWByUserId(req.getUserId())/pageSize));

		//查询云贷授权委托书生效时间
		BsAgreementVersion bsAgreementVersionYun =
				agreementVersionService.queryAgreementVersionDate(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_POWERATTORNEY, Constants.AGREEMENT_VERSION_NUMBER_1_1);
		Date agreementEffectiveStartTimeYun = bsAgreementVersionYun.getAgreementEffectiveStartTime();
		//查询7贷授权委托书生效时间
		BsAgreementVersion bsAgreementVersionSeven =
				agreementVersionService.queryAgreementVersionDate(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_POWERATTORNEY, Constants.AGREEMENT_VERSION_NUMBER_1_1);
		Date agreementEffectiveStartTimeSeven = bsAgreementVersionSeven.getAgreementEffectiveStartTime();
		
		ArrayList<HashMap<String,Object>> bean = BeanUtils.classToArrayList(myInvest);
		if (bean != null) {
			for (HashMap<String, Object> hashMap : bean) {
			    Date startTime = (Date) hashMap.get("startTime");
			    Date loadMatchShowTime = null;
			    try {
			        loadMatchShowTime = DateUtil.parse(Constants.LOAD_MATCH_SHOW_TIME, "yyyy-MM-dd");
	            } catch (ParseException e) {
	                e.printStackTrace();
	            }
			    if(DateUtil.compareTo(startTime, loadMatchShowTime) < 0) {
			        hashMap.put("loan", "no");
	            } else {
	                hashMap.put("loan", "yes");
	            }

				//授权委托书目前只针对存管云贷、存管7贷、自由计划的产品
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
		
		//resp.setPageIndex(req.getStartPage());//传递有误
		
		
	}

	/**
	 *
	 * 投资列表-委托计划
	 * @param req
	 * @param resp
     */
	public void investEntrustListQuery(ReqMsg_Invest_InvestEntrustListQuery req, ResMsg_Invest_InvestEntrustListQuery resp) {
		//委托计划
		ResMsg_Invest_InvestListQuery myInvestEntrust = assetsService.commissionPlanList(req.getUserId(),req.getStartPageEntrust(), req.getPageSizeEntrust());
		resp.setInvestAccountsEntrust(myInvestEntrust.getValueList());
		resp.setTotalPageEntrust(myInvestEntrust.getTotal());
	}

	/**
	 * 根据用户id和子产品id号查询我的单条投资记录 
	 * @param req
	 * @param res
	 */
	public void infoQuery(ReqMsg_Invest_InfoQuery req,ResMsg_Invest_InfoQuery res){
		
		BsSubAccountVO myInvest = subAccountService.findMyInvestByUserIdAndId(req.getUserId(),req.getId());
		res.setInvestAmount(myInvest.getBalance());
		res.setInvestDay(myInvest.getInvestDay());
		res.setInvestBeginDate(myInvest.getOpenTime());
		res.setInvestOverDate(myInvest.getInvestEndTime());
		res.setInvestRate(myInvest.getProductRate());
		res.setSubAccountId(myInvest.getId());
	}
	
	public void platformStatistics(ReqMsg_Invest_PlatformStatistics req,ResMsg_Invest_PlatformStatistics res) {
		List<String> platformOverviewList = jsClientDaoSupport.lrange(Constants.PLATFORM_DATA_REDIS_KEY_PLATFORMOVERVIEW);
		List<String> transactionLendDataStatisticsList = jsClientDaoSupport.lrange(Constants.PLATFORM_DATA_REDIS_KEY_TRANSACTIONLENDDATASTATISTICS);
		List<String> userDataList = jsClientDaoSupport.lrange(Constants.PLATFORM_DATA_REDIS_KEY_USERDATA);
		List<String> overdueInfoList = jsClientDaoSupport.lrange(Constants.PLATFORM_DATA_REDIS_KEY_OVERDUEINFO);
		if(CollectionUtils.isEmpty(platformOverviewList) || CollectionUtils.isEmpty(transactionLendDataStatisticsList)
				|| CollectionUtils.isEmpty(userDataList) || CollectionUtils.isEmpty(overdueInfoList)) {
			NumberTool tool = new NumberTool();
			// 累计投资金额
			Double totalInvestAmount = 0d;
			totalInvestAmount = productService.queryAccumulatedInvestment();
			res.setTotalInvestAmount(tool.format("0.00", totalInvestAmount));
			// 累计注册人数
			res.setTotalRegUser(tool.format("0", userService.countRegNum()));
			// 累计赚取收益
			res.setTotalInterestAmount(tool.format("0.00", userService.countUserIncome()));
			// 查询平台数据调用新的方法
			subAccountService.platformStatisticsNews(req,res);
		} else {
			subAccountService.platformData(req, res);
		}
	}
	
	/**
	 * PC端账户概述-投资分布列表
	 * @param req
	 * @param res
	 */
	public void investProportion(ReqMsg_Invest_InvestProportion req, ResMsg_Invest_InvestProportion res){
		ResMsg_Invest_InvestProportion investRes = assetsService.investProportionList(req.getUserId());
		res.setInvestProportionList(investRes.getInvestProportionList());
	}


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
