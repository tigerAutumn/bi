package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.pinting.business.dayend.task.process.AgentUserListQueryCallable;
import com.pinting.core.util.MoneyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_Agent_BsUserListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Agent_BuyMessageExcelQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Agent_BuyMessageListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Agent_QhdBuyMessageListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Agent_QhdUserListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Agent_BsUserListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Agent_BuyMessageExcelQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Agent_BuyMessageListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Agent_QhdBuyMessageListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Agent_QhdUserListQuery;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.MUser;
import com.pinting.business.model.vo.BsQhdStatisticsInvestVO;
import com.pinting.business.model.vo.BsQhdUserAgentVO;
import com.pinting.business.model.vo.BsStatisticsVO;
import com.pinting.business.model.vo.BsUserAssetVO;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.service.manage.MStatisticsService;
import com.pinting.business.service.manage.MUserService;
import com.pinting.business.util.BeanUtils;
import org.springframework.util.CollectionUtils;

@Component("Agent")
public class AgentFacade {
	
	@Autowired
	private BsUserService bsUserService;
	
	@Autowired
	private MUserService mUserService;
	
	@Autowired
	private MStatisticsService mStatisticsService;

	private static ExecutorService executorService;
	static {
		try {
			executorService = Executors.newFixedThreadPool(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void bsUserListQuery(ReqMsg_Agent_BsUserListQuery req, ResMsg_Agent_BsUserListQuery resp) {
		if(null == executorService) {
			executorService = Executors.newFixedThreadPool(5);
		}


		MUser mUser = mUserService.findMUser(req.getmUserId());//查询当前登入管理员是那个渠道的
		if(null != mUser.getAgentId() && 0 != mUser.getAgentId()){
			resp.setmAgentId(mUser.getAgentId());//系统用户的渠道编号
			Integer pageNum = req.getPageNum();
			Integer numPerPage = req.getNumPerPage();
			int totalRows =  bsUserService.findAgentUserCount(req.getStatus(),req.getSearchMobile(),
					req.getsEmail(),req.getsIsBindBank(),req.getsIsBindName(),req.getsName(),
					req.getsNick(),req.getsRecommend(),req.geteRecommend(),req.getsReward(),req.geteReward(),
					req.getSregistTime(),req.getEregistTime(),mUser.getAgentId(), req.getRegTerminal(), req.getDistributionChannel());

			List<HashMap<String, Object>> amountTransGroup = new ArrayList<HashMap<String, Object>>();

			AgentUserListQueryCallable countCallable = new AgentUserListQueryCallable(mStatisticsService, "count");
			AgentUserListQueryCallable sumCallable = new AgentUserListQueryCallable(mStatisticsService, "sum");
			Future countFuture = executorService.submit(countCallable);
			Future sumFuture = executorService.submit(sumCallable);
			try {
				List<HashMap<String, Object>> countList = (List<HashMap<String, Object>>) countFuture.get();
				List<HashMap<String, Object>> sumList = (List<HashMap<String, Object>>) sumFuture.get();
					for(HashMap<String, Object> sum: sumList) {
					Integer userId = (Integer) sum.get("userId");
					Double realAmountTrans = (Double) sum.get("realAmountTrans");
					if(null != userId) {
						HashMap<String, Object> resultMap = new HashMap<>();
						resultMap.put("userId", userId);
						for (HashMap<String, Object> count : countList) {
							if(userId.equals((Integer) count.get("userId"))) {
								if((Integer) count.get("repayedPeriodCount") > 0) {
									realAmountTrans = 0d;
								}
								break;
							}
						}
						resultMap.put("realAmountTrans", realAmountTrans);
						amountTransGroup.add(resultMap);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}

			if (totalRows > 0) {
				List<HashMap<String, Object>> userList = new ArrayList<HashMap<String,Object>> ();
				List<BsUserAssetVO> bsUserList =  bsUserService.findAgentUser(req.getSearchMobile(), req.getStatus(), pageNum,
						numPerPage,req.getsEmail(),req.getsIsBindBank(),req.getsIsBindName(),req.getsName(),
						req.getsNick(),req.getsRecommend(),req.geteRecommend(),req.getsReward(),req.geteReward(),
						req.getSregistTime(),req.getEregistTime(),req.getOrderDirection(),req.getOrderField(),mUser.getAgentId(),
						req.getRegTerminal(), req.getDistributionChannel());
				for(int i=0;i<bsUserList.size();i++){
					BsUserAssetVO bsUserAssetVO = bsUserList.get(i);
					HashMap<String,Object> userAsset = new HashMap<String,Object>();
					userAsset.put("id",bsUserAssetVO.getId());
					userAsset.put("name", bsUserAssetVO.getUserName());
					userAsset.put("mobile", bsUserAssetVO.getMobile());
					userAsset.put("idCard",bsUserAssetVO.getIdCard());
					userAsset.put("recommendNum", bsUserAssetVO.getRecommendNum());
					userAsset.put("mUserAgentId", mUser.getAgentId());
					//总资产与前端页面保持一致，总资产=购买金额(包含红包户)+累计投资收益
					userAsset.put("sumBalance", MoneyUtil.add(bsUserAssetVO.getTotalBalance() ==null?0:bsUserAssetVO.getTotalBalance(),
							bsUserAssetVO.getCurrentInterest() ==null?0:bsUserAssetVO.getCurrentInterest()).doubleValue());

					//为总资产添加转转利息
					/*if(!CollectionUtils.isEmpty(amountTransGroup)) {
						for (Map<String, Object> amountTransMap: amountTransGroup) {
							if(null != amountTransMap) {
								if(null != amountTransMap.get("user_id")) {
									Integer userId = (Integer)amountTransMap.get("user_id");
									if(null != userId && bsUserAssetVO.getId().equals(userId)) {
										Double sumBalance = (Double) userAsset.get("sumBalance");
										sumBalance = MoneyUtil.defaultRound(sumBalance).doubleValue();
										Double transAmount = (Double) amountTransMap.get("real_amount_trans");
										if(null != transAmount) {
											sumBalance = MoneyUtil.add(sumBalance, transAmount).setScale(2).doubleValue();
											userAsset.put("sumBalance", sumBalance);
											break;
										}
									}
								}
							}
						}
					}*/
					userAsset.put("canWithdraw", bsUserAssetVO.getCanWithdraw()==null?0:bsUserAssetVO.getCanWithdraw()); //可提现金额
					userAsset.put("currentInterest", bsUserAssetVO.getCurrentInterest()==null?0:bsUserAssetVO.getCurrentInterest());//投资收益
					userAsset.put("currentBanlace", bsUserAssetVO.getRealBalance());//当前投资本金=总余额-结算户余额
					userAsset.put("totalBonus",bsUserAssetVO.getTotalBonus()==null?0:bsUserAssetVO.getTotalBonus());//累计推荐奖励
					userAsset.put("totalInterest", bsUserAssetVO.getTotalInterest()==null?0:bsUserAssetVO.getTotalInterest());//累计投资收益
					userAsset.put("registTime", bsUserAssetVO.getRegisterTime());
					userAsset.put("agentId", bsUserAssetVO.getAgentId());
					userAsset.put("regTerminal", bsUserAssetVO.getRegTerminal());
					userList.add(userAsset);
				}
				resp.setBsUserList(userList);
			}
			resp.setSearch(totalRows > 0?req.getSearchMobile():"");
			resp.setTotalRows(totalRows > 0?totalRows:0);
			resp.setNumPerPage(numPerPage);
			resp.setPageNum(pageNum);
			resp.setsName(totalRows > 0?req.getsName():"");
			resp.setEregistTime(totalRows > 0?req.getEregistTime():null);
			resp.setSregistTime(totalRows > 0?req.getSregistTime():null);
			resp.setsRecommend(totalRows > 0?req.getsRecommend():null);
			resp.seteRecommend(totalRows > 0?req.geteRecommend():null);
		}
	}
		
	public void buyMessageListQuery(ReqMsg_Agent_BuyMessageListQuery req,
			ResMsg_Agent_BuyMessageListQuery res) {
		MUser mUser = mUserService.findMUser(req.getmUserId());//查询当前登入管理员是那个渠道的
		if(null != mUser.getAgentId() && 0 != mUser.getAgentId()){

			Integer pageNum = req.getPageNum();
			Integer numPerPage = req.getNumPerPage();
			int totalRows = 0;
			if(mUser.getAgentId() == 15) {//钱报渠道的系统用户带分销渠道的查询条件
				totalRows = mStatisticsService.countAgentBuyMessageAgent(req.getOrderNo(),
						req.getBeginBuyAmount(), req.getEndBuyAmount(),
						req.getBuyBeginTime(), req.getBuyEndTime(),
						req.getInvestBeginTime(), req.getInvestEndTime(),
						req.getBindBankCard(), req.getBuyBankCard(), req.getIdCard(),
						req.getAccountStatus(), req.getMobile(), req.getUserName(),
						req.getTerm(), req.getProductCode(), req.getDistributionChannel(),req.getProductName(),
						req.getInterestBeginTime(), req.getInterestEndTime(), req.getTerminalType());
			}else {
				totalRows = mStatisticsService.countAgentBuyMessage(req.getOrderNo(),
						req.getBeginBuyAmount(), req.getEndBuyAmount(),
						req.getBuyBeginTime(), req.getBuyEndTime(),
						req.getInvestBeginTime(), req.getInvestEndTime(),
						req.getBindBankCard(), req.getBuyBankCard(), req.getIdCard(),
						req.getAccountStatus(), req.getMobile(), req.getUserName(),
						req.getTerm(), req.getProductCode(),mUser.getAgentId(),req.getProductName(),
						req.getInterestBeginTime(), req.getInterestEndTime());
			}

			if (totalRows > 0) {
				if(mUser.getAgentId() == 15) {//钱报渠道的系统用户带分销渠道的查询条件
					List<BsStatisticsVO> valueList = mStatisticsService
							.findAgentUserMessageListAgent(req.getOrderNo(),
									req.getBeginBuyAmount(), req.getEndBuyAmount(),
									req.getBuyBeginTime(), req.getBuyEndTime(),
									req.getInvestBeginTime(), req.getInvestEndTime(),
									req.getBindBankCard(), req.getBuyBankCard(),
									req.getIdCard(), req.getProductCode(),
									req.getAccountStatus(), req.getMobile(),
									req.getUserName(), req.getTerm(), req.getPageNum(),
									req.getNumPerPage(), req.getOrderDirection(),
									req.getOrderField(), req.getDistributionChannel(), req.getProductName(),
									req.getInterestBeginTime(), req.getInterestEndTime(), req.getTerminalType());
					ArrayList<HashMap<String, Object>> buyMessageList = BeanUtils
							.classToArrayList(valueList);
					res.setUserBuyMessageList(buyMessageList);
					double sumBalanceAmount = mStatisticsService.findAgentUserSumBalanceAgent(
							req.getBeginBuyAmount(), req.getEndBuyAmount(),
							req.getBuyBeginTime(), req.getBuyEndTime(),
							req.getInvestBeginTime(), req.getInvestEndTime(),
							req.getBindBankCard(), req.getBuyBankCard(),
							req.getIdCard(), req.getAccountStatus(), req.getMobile(),
							req.getUserName(), req.getTerm(), req.getProductCode(),
							req.getDistributionChannel(), req.getProductName(),
							req.getInterestBeginTime(), req.getInterestEndTime(), req.getTerminalType());
					res.setSumBalanceAmount(sumBalanceAmount);
				}else {
					List<BsStatisticsVO> valueList = mStatisticsService
							.findAgentUserMessageList(req.getOrderNo(),
									req.getBeginBuyAmount(), req.getEndBuyAmount(),
									req.getBuyBeginTime(), req.getBuyEndTime(),
									req.getInvestBeginTime(), req.getInvestEndTime(),
									req.getBindBankCard(), req.getBuyBankCard(),
									req.getIdCard(), req.getProductCode(),
									req.getAccountStatus(), req.getMobile(),
									req.getUserName(), req.getTerm(), req.getPageNum(),
									req.getNumPerPage(), req.getOrderDirection(),
									req.getOrderField(),mUser.getAgentId(),req.getProductName(),
									req.getInterestBeginTime(), req.getInterestEndTime(),
									req.getDistributionChannel(), req.getTerminalType());
					ArrayList<HashMap<String, Object>> buyMessageList = BeanUtils
							.classToArrayList(valueList);
					res.setUserBuyMessageList(buyMessageList);
					double sumBalanceAmount = mStatisticsService.findAgentUserSumBalance(
							req.getBeginBuyAmount(), req.getEndBuyAmount(),
							req.getBuyBeginTime(), req.getBuyEndTime(),
							req.getInvestBeginTime(), req.getInvestEndTime(),
							req.getBindBankCard(), req.getBuyBankCard(),
							req.getIdCard(), req.getAccountStatus(), req.getMobile(),
							req.getUserName(), req.getTerm(), req.getProductCode(),
							mUser.getAgentId(),req.getProductName(),
							req.getInterestBeginTime(), req.getInterestEndTime());
					res.setSumBalanceAmount(sumBalanceAmount);
				}

			}
			res.setTotalRows(totalRows);
			res.setNumPerPage(numPerPage);
			res.setPageNum(pageNum);
			List<BsProduct> products = mStatisticsService.findProductList();
			res.setProductList(BeanUtils.classToArrayList(products));
			res.setAgentId(mUser.getAgentId());
		}
	}
	
	
	public void buyMessageExcelQuery(ReqMsg_Agent_BuyMessageExcelQuery req,
			ResMsg_Agent_BuyMessageExcelQuery res) {
		MUser mUser = mUserService.findMUser(req.getmUserId());//查询当前登入管理员是那个渠道的
		if(null != mUser.getAgentId() && 0 != mUser.getAgentId()){
			List<BsStatisticsVO> valueList = mStatisticsService
					.findAgentUserMessageList("",
							req.getBeginBuyAmount(), req.getEndBuyAmount(),
							req.getBuyBeginTime(), req.getBuyEndTime(),
							req.getInvestBeginTime(), req.getInvestEndTime(),
							"", "","", "",
							req.getAccountStatus(), req.getMobile(),
							req.getUserName(), null, req.getPageNum(),
							req.getNumPerPage(), req.getOrderDirection(),
							req.getOrderField(),mUser.getAgentId(),req.getProductName(),
							req.getInterestBeginTime(), req.getInterestEndTime(), req.getDistributionChannel(), req.getTerminalType());
			ArrayList<HashMap<String, Object>> buyMessageList = BeanUtils
					.classToArrayList(valueList);
			res.setUserBuyMessageList(buyMessageList);
		}
	}
	
	public void qhdUserListQuery(ReqMsg_Agent_QhdUserListQuery req, ResMsg_Agent_QhdUserListQuery resp) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		int totalRows =  bsUserService.findQhdAgentUserCount(req.getUserName(), req.getMobile(),
				req.getStartRegisterTime(), req.getEndRegisterTime(), req.getRegTerminal(), req.getDistributionChannel());
		if (totalRows > 0) {
			List<BsQhdUserAgentVO> bsUserList =  bsUserService.findQhdAgentUserList(req.getUserName(), req.getMobile(),
					req.getStartRegisterTime(), req.getEndRegisterTime(), req.getRegTerminal(),
					req.getDistributionChannel(), pageNum, numPerPage);
			ArrayList<HashMap<String, Object>> userList = BeanUtils.classToArrayList(bsUserList);
			resp.setBsUserList(userList);
		}
		resp.setTotalRows(totalRows > 0?totalRows:0);
	}
		
	public void qhdBuyMessageListQuery(ReqMsg_Agent_QhdBuyMessageListQuery req, ResMsg_Agent_QhdBuyMessageListQuery res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		int totalRows = mStatisticsService.countQhdAgentBuyMessage(req.getUserName(), req.getMobile(), req.getTerm(),
				req.getAccountStatus(), req.getDistributionChannel(), req.getTerminalType(), req.getBeginBuyAmount(), req.getEndBuyAmount(), 
				req.getSettleAccountsBeginTime(), req.getSettleAccountsEndTime(), req.getBuyBeginTime(), req.getBuyEndTime());

		if (totalRows > 0) {
				List<BsQhdStatisticsInvestVO> valueList = mStatisticsService.findQhdAgentUserMessageList(req.getUserName(), req.getMobile(), req.getTerm(),
					req.getAccountStatus(), req.getDistributionChannel(), req.getTerminalType(), req.getBeginBuyAmount(), req.getEndBuyAmount(), 
					req.getSettleAccountsBeginTime(), req.getSettleAccountsEndTime(), req.getBuyBeginTime(), req.getBuyEndTime(), pageNum, numPerPage);
				ArrayList<HashMap<String, Object>> buyMessageList = BeanUtils.classToArrayList(valueList);
				res.setUserBuyMessageList(buyMessageList);
				double sumBalanceAmount = mStatisticsService.findQhdAgentUserSumBalance(req.getUserName(), req.getMobile(), req.getTerm(),
						req.getAccountStatus(), req.getDistributionChannel(), req.getTerminalType(), req.getBeginBuyAmount(), req.getEndBuyAmount(), 
						req.getSettleAccountsBeginTime(), req.getSettleAccountsEndTime(), req.getBuyBeginTime(), req.getBuyEndTime());
				res.setSumBalanceAmount(sumBalanceAmount);
		}
		res.setTotalRows(totalRows);
	}
	
}
