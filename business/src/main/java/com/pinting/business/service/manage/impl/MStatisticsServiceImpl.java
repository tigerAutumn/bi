package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.*;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_BuyMessageDepListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_BuyMessageListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_BuyMessageDepListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_BuyMessageListQuery;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.manage.MStatisticsService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class MStatisticsServiceImpl implements MStatisticsService{

	
	@Autowired
	private BsUserMapper bsUserMapper;
	@Autowired
	private BsPayOrdersMapper bsPayOrdersMapper;
	@Autowired
	private BsStatisticsMapper bsStatisticsMapper;
	@Autowired
	private BsCashSchedule30Mapper cashScheduleMapper;
	@Autowired
	private BsProductMapper productMapper;
	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
	@Autowired
	private BsPropertyInfoMapper bsPropertyInfoMapper;
	@Autowired
	private LnLoanMapper lnLoanMapper;
	@Autowired
	private BsDepCash30Mapper bsDepCash30Mapper;
	@Autowired
	private LnLoanRelationMapper lnLoanRelationMapper;
	
	@Override
	public int CountBuyMessage(String orderNo,Double beginAmount, Double endAmount,
			Date beginTime, Date endTime,Date settleAccountsBeginTime, Date settleAccountsEndTime, String bindBankCard,
			String buyBankCard, String idCard, Integer accountStatus,String mobile, String userName, Integer term,
			String productCode,String agentName,String buyBankType,Integer agentId,Double rate,Double startRate,Double endRate,
			String sAgentIds,String nonAgentId,String propertySymbol) {
		BsStatisticsVO statisticsVo = new BsStatisticsVO();

		checkParamsForBuyMessage(statisticsVo,orderNo,beginAmount, endAmount,
				 beginTime,  endTime, settleAccountsBeginTime,  settleAccountsEndTime,  bindBankCard,
				 buyBankCard,  idCard,  accountStatus, mobile,  userName,  term,
				 productCode, agentName, buyBankType, agentId, rate, startRate, endRate,
				 sAgentIds, nonAgentId, propertySymbol);
		return bsUserMapper.countUserBuyMessageList(statisticsVo);
	}

	@Override
	public int countBuyMessageForZan(String orderNo,Double beginAmount, Double endAmount,
							   Date beginTime, Date endTime,Date settleAccountsBeginTime, Date settleAccountsEndTime, String bindBankCard,
							   String buyBankCard, String idCard, Integer accountStatus,String mobile, String userName, Integer term,
							   String productCode,String agentName,String buyBankType,Integer agentId,Double rate,Double startRate,Double endRate,
							   String sAgentIds,String nonAgentId,String propertySymbol) {
		BsStatisticsVO statisticsVo = new BsStatisticsVO();

		checkParamsForBuyMessage(statisticsVo,orderNo,beginAmount, endAmount,
				beginTime,  endTime, settleAccountsBeginTime,  settleAccountsEndTime,  bindBankCard,
				buyBankCard,  idCard,  accountStatus, mobile,  userName,  term,
				productCode, agentName, buyBankType, agentId, rate, startRate, endRate,
				sAgentIds, nonAgentId, propertySymbol);
		return bsUserMapper.countUserBuyMessageListForZan(statisticsVo);
	}

	@Override
	public List<BsStatisticsVO> findUserBuyMessageList(String orderNo,Double beginAmount,
			Double endAmount, Date beginTime, Date endTime,Date settleAccountsBeginTime, Date settleAccountsEndTime,
			String bindBankCard, String buyBankCard, String idCard,String productCode,String agentName,
			Integer accountStatus, String mobile, String userName, Integer term,int pageNum,int numPerPage,
			String orderDirection,String orderField,String buyBankType,Integer agentId,Double rate,Double startRate,Double endRate,
			String sAgentIds,String nonAgentId,String propertySymbol){

		BsStatisticsVO statisticsVo = new BsStatisticsVO();
		checkBuyMessageParams(statisticsVo, orderNo, beginAmount,
				endAmount, beginTime, endTime,settleAccountsBeginTime, settleAccountsEndTime,
				bindBankCard, buyBankCard, idCard, productCode, agentName,
				accountStatus, mobile, userName, term,pageNum, numPerPage,
				orderDirection, orderField, buyBankType, agentId, rate, startRate, endRate,
				sAgentIds, nonAgentId, propertySymbol);
		return bsUserMapper.selectUserBuyMessageList(statisticsVo);
	}


	public List<BsStatisticsVO> findUserBuyMessageListForZan(String orderNo,Double beginAmount,
													   Double endAmount, Date beginTime, Date endTime,Date settleAccountsBeginTime, Date settleAccountsEndTime,
													   String bindBankCard, String buyBankCard, String idCard,String productCode,String agentName,
													   Integer accountStatus, String mobile, String userName, Integer term,int pageNum,int numPerPage,
													   String orderDirection,String orderField,String buyBankType,Integer agentId,Double rate,Double startRate,Double endRate,
													   String sAgentIds,String nonAgentId,String propertySymbol){
		BsStatisticsVO statisticsVo = new BsStatisticsVO();
		checkBuyMessageParams(statisticsVo, orderNo, beginAmount,
				 endAmount, beginTime, endTime,settleAccountsBeginTime, settleAccountsEndTime,
				bindBankCard, buyBankCard, idCard, productCode, agentName,
				accountStatus, mobile, userName, term,pageNum, numPerPage,
		 		orderDirection, orderField, buyBankType, agentId, rate, startRate, endRate,
				 sAgentIds, nonAgentId, propertySymbol);

		return bsUserMapper.selectUserBuyMessageListForZan(statisticsVo);
	}


	@Override
	public double findUserBuySumBalance(Double beginAmount,Double endAmount, Date beginTime, Date endTime,
			Date settleAccountsBeginTime, Date settleAccountsEndTime,String bindBankCard, String buyBankCard, 
			String idCard,Integer accountStatus, String mobile, String userName, Integer term,String ProductCode,
			String buyBankType,Integer agentId,Double rate,Double startRate,Double endRate,String sAgentIds,String nonAgentId
			,String propertySymbol) {
		
		BsStatisticsVO statisticsVo = new BsStatisticsVO();
		checkParamsForFindUserBuySumBalance(statisticsVo, beginAmount, endAmount, beginTime, endTime, settleAccountsBeginTime, settleAccountsEndTime, bindBankCard, buyBankCard, idCard, accountStatus, mobile, userName, term, ProductCode, buyBankType, agentId, rate, startRate, endRate, sAgentIds, nonAgentId, propertySymbol);
		return bsUserMapper.selectUserBuySumBalance(statisticsVo);
	}

	@Override
	public double findUserBuySumBalanceForZan(Double beginAmount, Double endAmount, Date beginTime, Date endTime, Date settleAccountsBeginTime, Date settleAccountsEndTime, String bindBankCard, String buyBankCard, String idCard, Integer accountStatus, String mobile, String userName, Integer term, String ProductCode, String buyBankType, Integer agentId, Double rate, Double startRate, Double endRate, String agentIds, String nonAgentId, String propertySymbol) {
		BsStatisticsVO statisticsVo = new BsStatisticsVO();
		checkParamsForFindUserBuySumBalance(statisticsVo, beginAmount, endAmount, beginTime, endTime, settleAccountsBeginTime, settleAccountsEndTime, bindBankCard, buyBankCard, idCard, accountStatus, mobile, userName, term, ProductCode, buyBankType, agentId, rate, startRate, endRate, agentIds, nonAgentId, propertySymbol);
		return bsUserMapper.selectUserBuySumBalanceForZan(statisticsVo);
	}

	@Override
	public void insertValueList(ArrayList<BsStatistics> valueList) {
		bsStatisticsMapper.insertValueList(valueList);
	}

	@Override
	public List<BsStatistics> findMStatisticsByType(String type) {
		BsStatisticsExample example = new BsStatisticsExample();
		example.createCriteria().andTypeEqualTo(type);
		example.setOrderByClause("time");
		return bsStatisticsMapper.selectByExample(example);
	}

	@Override
	public ArrayList<BsCashSchedule30> selectBsCashScheduleList(Date today,
			Date future30) {
		BsCashSchedule30VO cashSchedule30VO = new BsCashSchedule30VO();
		cashSchedule30VO.setBeginTime(today);
		cashSchedule30VO.setOverTime(future30);
		
		return cashScheduleMapper.selectBsCashScheduleList(cashSchedule30VO);
		
		
	}

	@Override
	public boolean deleteBsCashSchedule() {
		
		BsCashSchedule30Example example = new BsCashSchedule30Example();
		example.createCriteria().andCashDateGreaterThanOrEqualTo(new Date());
		
		int count = cashScheduleMapper.countByExample(example);
		return cashScheduleMapper.deleteByExample(example)!=count?false:true;
	}

	@Override
	public void insertCashScheduleList(ArrayList<BsCashSchedule30> valueList) {
		
		cashScheduleMapper.insertCashScheduleList(valueList);
	}

	@Override
	public List<BsProduct> findProductList() {
		return productMapper.selectByExample(null);
	}

	@Override
	public List<BsStatisticsVO> findUserInvestment(Double beginAmount,
			Double endAmount, Date beginTime, Date endTime,
			String bindBankCard, String bindBankName,
			Date settleAccountsBeginTime, Date settleAccountsEndTime,
			Integer accountStatus, String orderNo, String productId,
			String agentName, Double beginSettlementAmount,
			Double endSettlementAmount, String idCard, String mobile,
			String userName, int pageNum, int numPerPage, 
			String orderDirection, String orderField, Integer orderStatus, 
			Integer agentId, Integer term, String rate, String buyBankType) {
		BsStatisticsVO bsStatisticsVo = new BsStatisticsVO();
		if(beginAmount != null) {
			bsStatisticsVo.setBeginAmount(beginAmount);
		}
		if(endAmount != null) {
			bsStatisticsVo.setEndAmount(endAmount);
		}
		if(beginTime != null) {
			bsStatisticsVo.setBeginTime(beginTime);
		}
		if(endTime != null) {
			bsStatisticsVo.setEndTime(DateUtil.addDays(endTime, 1));
		}
		if(bindBankCard != null) {
			bsStatisticsVo.setBindBankCard(bindBankCard);
		}
		if(bindBankName != null) {
			bsStatisticsVo.setBindBankName(bindBankName);
		}
		if(settleAccountsBeginTime != null) {
			bsStatisticsVo.setSettleAccountsBeginTime(settleAccountsBeginTime);
		}
		if(settleAccountsEndTime != null) {
			bsStatisticsVo.setSettleAccountsEndTime(settleAccountsEndTime);
		}
		if(accountStatus != null) {
			bsStatisticsVo.setAccountStatus(accountStatus);
		}
		if(orderNo != null && !"".equals(orderNo)){
			bsStatisticsVo.setOrderNo(orderNo);
		}
		if(productId != null && !"".equals(productId)) {
			bsStatisticsVo.setProductId(Integer.parseInt(productId));
		}

		if(agentName != null) {
			bsStatisticsVo.setAgentName(agentName);
		}
		if(beginSettlementAmount != null) {
			bsStatisticsVo.setBeginSettlementAmount(beginSettlementAmount);
		}
		if(endSettlementAmount != null) {
			bsStatisticsVo.setEndSettlementAmount(endSettlementAmount);
		}
		if(idCard != null) {
			bsStatisticsVo.setIdCard(idCard);
		}
		if(mobile != null) {
			bsStatisticsVo.setMobile(mobile);
		}
		if(userName != null) {
			bsStatisticsVo.setUserName(userName);
		}
		if(orderStatus != null) {
			bsStatisticsVo.setOrderStatus(orderStatus);
		}
		if(agentId != null) {
			bsStatisticsVo.setAgentId(agentId);
		}
		if (orderDirection != null&& (!"".equals(orderDirection))&&orderField != null&& (!"".equals(orderField))) {
			bsStatisticsVo.setOrderDirection(orderDirection);
			bsStatisticsVo.setOrderField(orderField);
		}
		if(term != null) {
			bsStatisticsVo.setTerm(term);
		}
		if(rate != null && !"".equals(rate)) {
			bsStatisticsVo.setRate(Double.parseDouble(rate));
		}
		if (buyBankType != null && !"".equals(buyBankType)) {
			bsStatisticsVo.setBuyBankType(buyBankType);
		}
		bsStatisticsVo.setPageNum(pageNum);
		bsStatisticsVo.setNumPerPage(numPerPage);
		return bsUserMapper.selectUserInvestmentPaybackList(bsStatisticsVo);
	}

	@Override
	public double selectUserSumInvestment(Double beginAmount, Double endAmount,
			Date beginTime, Date endTime, Date settleAccountsBeginTime,
			Date settleAccountsEndTime, String bindBankCard, 
			String buyBankCard, String bindBankName , String idCard, Integer accountStatus,
			String mobile, String userName, Integer term, String productId, 
			Double beginSettlementAmount, Double endSettlementAmount, String orderNo, 
			Integer agentId, String rate, String buyBankType) {
		BsStatisticsVO statisticsVo = new BsStatisticsVO();
		if(beginAmount != null){
			statisticsVo.setBeginAmount(beginAmount);
		}
		if(endAmount != null){
			statisticsVo.setEndAmount(endAmount);
		}
		if(beginTime != null){
			statisticsVo.setBeginTime(beginTime);
		}
		if(endTime != null){
			statisticsVo.setEndTime(DateUtil.addDays(endTime, 1));
		}
		if(settleAccountsBeginTime != null){
			statisticsVo.setSettleAccountsBeginTime(settleAccountsBeginTime);
		}
		
		if(settleAccountsEndTime != null){
			statisticsVo.setSettleAccountsEndTime(settleAccountsEndTime);
		}
		if(bindBankCard != null){
			statisticsVo.setBindBankCard(bindBankCard);
		}
		if(buyBankCard != null){
			statisticsVo.setBuyBankCard(buyBankCard);
		}
		if(bindBankName != null){
			statisticsVo.setBindBankName(bindBankName);
		}
		if(idCard != null){
			statisticsVo.setIdCard(idCard);
		}
		if(accountStatus != null){
			statisticsVo.setAccountStatus(accountStatus);
		}
		if(mobile != null){
			statisticsVo.setMobile(mobile);
		}
		if(userName != null){
			statisticsVo.setUserName(userName);
		}
		if(term != null){
			statisticsVo.setTerm(term);
		}
		if(productId != null && !"".equals(productId)){
			statisticsVo.setProductId(Integer.parseInt(productId));
		}
		if(beginSettlementAmount != null){
			statisticsVo.setBeginSettlementAmount(beginSettlementAmount);
		}
		if(endSettlementAmount != null){
			statisticsVo.setEndSettlementAmount(endSettlementAmount);
		}
		if(orderNo != null && !"".equals(orderNo)){
			statisticsVo.setOrderNo(orderNo);
		}
		if(agentId != null) {
			statisticsVo.setAgentId(agentId);
		}
		if(rate != null && !"".equals(rate)) {
			statisticsVo.setRate(Double.parseDouble(rate));
		}
		if (buyBankType != null && !"".equals(buyBankType)) {
			statisticsVo.setBuyBankType(buyBankType);
		}
		return bsUserMapper.selectUserSumInvestment(statisticsVo);
	}

	@Override
	public int selectCountSumInvestment(Double beginAmount,
			Double endAmount, Date beginTime, Date endTime,
			Date settleAccountsBeginTime, Date settleAccountsEndTime,
			String bindBankCard, String buyBankCard, String bindBankName, String idCard,
			Integer accountStatus, String mobile, String userName,
			Integer term, String productId, 
			Double beginSettlementAmount, Double endSettlementAmount, String orderNo, 
			Integer agentId, String rate, String buyBankType) {
		BsStatisticsVO statisticsVo = new BsStatisticsVO();
		if(beginAmount != null){
			statisticsVo.setBeginAmount(beginAmount);
		}
		if(endAmount != null){
			statisticsVo.setEndAmount(endAmount);
		}
		if(beginTime != null){
			statisticsVo.setBeginTime(beginTime);
		}
		if(endTime != null){
			statisticsVo.setEndTime(DateUtil.addDays(endTime, 1));
		}
		if(settleAccountsBeginTime != null){
			statisticsVo.setSettleAccountsBeginTime(settleAccountsBeginTime);
		}
		
		if(settleAccountsEndTime != null){
			statisticsVo.setSettleAccountsEndTime(settleAccountsEndTime);
		}
		if(bindBankCard != null){
			statisticsVo.setBindBankCard(bindBankCard);
		}
		if(buyBankCard != null){
			statisticsVo.setBuyBankCard(buyBankCard);
		}
		if(bindBankName != null){
			statisticsVo.setBindBankName(bindBankName);
		}
		if(idCard != null){
			statisticsVo.setIdCard(idCard);
		}
		if(accountStatus != null){
			statisticsVo.setAccountStatus(accountStatus);
		}
		if(mobile != null){
			statisticsVo.setMobile(mobile);
		}
		if(userName != null){
			statisticsVo.setUserName(userName);
		}
		if(term != null){
			statisticsVo.setTerm(term);
		}
		if(productId != null && !"".equals(productId)){
			statisticsVo.setProductId(Integer.parseInt(productId));
		}
		if(beginSettlementAmount != null){
			statisticsVo.setBeginSettlementAmount(beginSettlementAmount);
		}
		if(endSettlementAmount != null){
			statisticsVo.setEndSettlementAmount(endSettlementAmount);
		}
		if(orderNo != null && !"".equals(orderNo)){
			statisticsVo.setOrderNo(orderNo);
		}
		if(agentId != null) {
			statisticsVo.setAgentId(agentId);
		}
		if(rate != null && !"".equals(rate)) {
			statisticsVo.setRate(Double.parseDouble(rate));
		}
		if (buyBankType != null && !"".equals(buyBankType)) {
			statisticsVo.setBuyBankType(buyBankType);
		}

		return bsUserMapper.countUserInvestmentList(statisticsVo);
	}
	
	@Override
	public List<BsStatisticsVO> findAgentUserMessageList(String orderNo,Double beginAmount,
			Double endAmount, Date beginTime, Date endTime,Date settleAccountsBeginTime, Date settleAccountsEndTime,
			String bindBankCard, String buyBankCard, String idCard,String productCode,
			Integer accountStatus, String mobile, String userName, Integer term,int pageNum,int numPerPage,
			String orderDirection,String orderField,Integer agentId,String productName, 
			Date interestBeginTime, Date interestEndTime, String distributionChannel, Integer terminalType){
			
		BsStatisticsVO statisticsVo = new BsStatisticsVO();
		
		if(orderNo != null && !"".equals(orderNo)){
			statisticsVo.setOrderNo(orderNo);
		}
		if(beginAmount != null){
			statisticsVo.setBeginAmount(beginAmount);
		}
		if(endAmount != null){
			statisticsVo.setEndAmount(endAmount);
		}
		if(beginTime != null){
			statisticsVo.setBeginTime(beginTime);
		}
		if(endTime != null){
			statisticsVo.setEndTime(DateUtil.addDays(endTime, 1));
		}
		if(settleAccountsBeginTime != null){
			statisticsVo.setSettleAccountsBeginTime(settleAccountsBeginTime);
		}
		
		if(settleAccountsEndTime != null){
			statisticsVo.setSettleAccountsEndTime(DateUtil.addDays(settleAccountsEndTime,1));
		}
		if(bindBankCard != null){
			statisticsVo.setBindBankCard(bindBankCard);
		}
		if(buyBankCard != null){
			statisticsVo.setBuyBankCard(buyBankCard);
		}
		if(idCard != null){
			statisticsVo.setIdCard(idCard);
		}
		if(accountStatus != null){
			statisticsVo.setAccountStatus(accountStatus);
		}
		if(mobile != null){
			statisticsVo.setMobile(mobile);
		}
		if(userName != null){
			statisticsVo.setUserName(userName);
		}
		if(term != null){
			statisticsVo.setTerm(term);
		}
		if(productCode != null && !"".equals(productCode)){
			statisticsVo.setProductCode(productCode);
		}

		if (orderDirection != null&& (!"".equals(orderDirection))&&orderField != null&& (!"".equals(orderField))) {
			statisticsVo.setOrderDirection(orderDirection);
			statisticsVo.setOrderField(orderField);
		}
		if(null != productName){
			statisticsVo.setProductName(productName);
		}
		if (null != interestBeginTime) {
			statisticsVo.setInterestBeginTime(interestBeginTime);
		}
		if (null != interestEndTime) {
			statisticsVo.setInterestEndTime(interestEndTime);
		}
		if(null != distributionChannel) {
			statisticsVo.setDistributionChannel(distributionChannel);
		}
		statisticsVo.setPageNum(pageNum);
		statisticsVo.setNumPerPage(numPerPage);
		statisticsVo.setAgentId(agentId);
		statisticsVo.setTerminalType(terminalType);
		return bsUserMapper.findAgentUserMessageList(statisticsVo);
	}
	
	public int countAgentBuyMessage(String orderNo,Double beginAmount, Double endAmount,
			Date beginTime, Date endTime,Date settleAccountsBeginTime, Date settleAccountsEndTime, String bindBankCard,
			String buyBankCard, String idCard, Integer accountStatus,
			String mobile, String userName, Integer term,String productCode,Integer agentId,String productName, 
			Date interestBeginTime, Date interestEndTime) {
		BsStatisticsVO statisticsVo = new BsStatisticsVO();
		
		if(orderNo != null && !"".equals(orderNo)){
			statisticsVo.setOrderNo(orderNo);
		}
		if(beginAmount != null){
			statisticsVo.setBeginAmount(beginAmount);
		}
		if(endAmount != null){
			statisticsVo.setEndAmount(endAmount);
		}
		if(beginTime != null){
			statisticsVo.setBeginTime(beginTime);
		}
		if(endTime != null){
			
			statisticsVo.setEndTime(DateUtil.addDays(endTime, 1));
		}
		
		if(settleAccountsBeginTime != null){
			statisticsVo.setSettleAccountsBeginTime(settleAccountsBeginTime);
		}
		
		if(settleAccountsEndTime != null){
			statisticsVo.setSettleAccountsEndTime(DateUtil.addDays(settleAccountsEndTime,1));
		}
		if(bindBankCard != null){
			statisticsVo.setBindBankCard(bindBankCard);
		}
		if(buyBankCard != null){
			statisticsVo.setBuyBankCard(buyBankCard);
		}
		if(idCard != null){
			statisticsVo.setIdCard(idCard);
		}
		if(accountStatus != null){
			statisticsVo.setAccountStatus(accountStatus);
		}
		if(mobile != null){
			statisticsVo.setMobile(mobile);
		}
		if(userName != null){
			statisticsVo.setUserName(userName);
		}
		if(term != null){
			statisticsVo.setTerm(term);
		}
		if(productCode != null && !"".equals(productCode)){
			statisticsVo.setProductCode(productCode);
		}
		if(null != productName){
			statisticsVo.setProductName(productName);
		}
		if (null != interestBeginTime) {
			statisticsVo.setInterestBeginTime(interestBeginTime);
		}
		if (null != interestEndTime) {
			statisticsVo.setInterestEndTime(interestEndTime);
		}
 		statisticsVo.setAgentId(agentId);
		return bsUserMapper.countAgentBuyMessage(statisticsVo);
	}
	
	@Override
	public double findAgentUserSumBalance(Double beginAmount,
	Double endAmount, Date beginTime, Date endTime,Date settleAccountsBeginTime, Date settleAccountsEndTime,
	String bindBankCard, String buyBankCard, String idCard,
	Integer accountStatus, String mobile, String userName, Integer term,
	String ProductCode,Integer agentId,String productName, 
	Date interestBeginTime, Date interestEndTime) {
		
		BsStatisticsVO statisticsVo = new BsStatisticsVO();
		if(beginAmount != null){
			statisticsVo.setBeginAmount(beginAmount);
		}
		if(endAmount != null){
			statisticsVo.setEndAmount(endAmount);
		}
		if(beginTime != null){
			statisticsVo.setBeginTime(beginTime);
		}
		if(endTime != null){
			statisticsVo.setEndTime(DateUtil.addDays(endTime, 1));
		}
		if(settleAccountsBeginTime != null){
			statisticsVo.setSettleAccountsBeginTime(settleAccountsBeginTime);
		}
		
		if(settleAccountsEndTime != null){
			statisticsVo.setSettleAccountsEndTime(DateUtil.addDays(settleAccountsEndTime,1));
		}
		if(bindBankCard != null){
			statisticsVo.setBindBankCard(bindBankCard);
		}
		if(buyBankCard != null){
			statisticsVo.setBuyBankCard(buyBankCard);
		}
		if(idCard != null){
			statisticsVo.setIdCard(idCard);
		}
		if(accountStatus != null){
			statisticsVo.setAccountStatus(accountStatus);
		}
		if(mobile != null){
			statisticsVo.setMobile(mobile);
		}
		if(userName != null){
			statisticsVo.setUserName(userName);
		}
		if(term != null){
			statisticsVo.setTerm(term);
		}
		if(ProductCode != null && !"".equals(ProductCode)){
			statisticsVo.setProductCode(ProductCode);
		}
		if(null != productName){
			statisticsVo.setProductName(productName);
		}
		if (null != interestBeginTime) {
			statisticsVo.setInterestBeginTime(interestBeginTime);
		}
		if (null != interestEndTime) {
			statisticsVo.setInterestEndTime(interestEndTime);
		}
		statisticsVo.setAgentId(agentId);
		return bsUserMapper.selectAgentSumBalance(statisticsVo);
	}

	@Override
	public void buyMessageListQuery(ReqMsg_Statistics_BuyMessageListQuery req, ResMsg_Statistics_BuyMessageListQuery res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		int totalRows = this.countBuyMessageForZan(req.getOrderNo(),
				req.getBeginBuyAmount(), req.getEndBuyAmount(),
				req.getBuyBeginTime(), req.getBuyEndTime(),
				req.getInvestBeginTime(), req.getInvestEndTime(),
				req.getBindBankCard(), req.getBuyBankCard(), req.getIdCard(),
				req.getAccountStatus(), req.getMobile(), req.getUserName(),
				req.getTerm(), req.getProductCode(),req.getAgentName(),
				req.getBuyBankType(),req.getAgentId(),req.getRate(),req.getStartRate(),req.getEndRate(),
				req.getAgentIds(),req.getNonAgentId(),req.getPropertySymbol());

		if (totalRows > 0) {
			List<BsStatisticsVO> valueList = this
					.findUserBuyMessageListForZan(req.getOrderNo(),
							req.getBeginBuyAmount(), req.getEndBuyAmount(),
							req.getBuyBeginTime(), req.getBuyEndTime(),
							req.getInvestBeginTime(), req.getInvestEndTime(),
							req.getBindBankCard(), req.getBuyBankCard(),
							req.getIdCard(), req.getProductCode(),req.getAgentName(),
							req.getAccountStatus(), req.getMobile(),
							req.getUserName(), req.getTerm(), req.getPageNum(),
							req.getNumPerPage(), req.getOrderDirection(),
							req.getOrderField(),req.getBuyBankType(),req.getAgentId(),
							req.getRate(),req.getStartRate(),req.getEndRate(),req.getAgentIds(),req.getNonAgentId(),req.getPropertySymbol());

			ArrayList<HashMap<String, Object>> buyMessageList = BeanUtils
					.classToArrayList(valueList);

			res.setUserBuyMessageList(buyMessageList);

			Double sumBalanceAmount = this.findUserBuySumBalanceForZan(
					req.getBeginBuyAmount(), req.getEndBuyAmount(),
					req.getBuyBeginTime(), req.getBuyEndTime(),
					req.getInvestBeginTime(), req.getInvestEndTime(),
					req.getBindBankCard(), req.getBuyBankCard(),
					req.getIdCard(), req.getAccountStatus(), req.getMobile(),
					req.getUserName(), req.getTerm(), req.getProductCode(),
					req.getBuyBankType(),req.getAgentId(),req.getRate(),
					req.getStartRate(),req.getEndRate(),req.getAgentIds(),req.getNonAgentId(),req.getPropertySymbol());
			res.setSumBalanceAmount(sumBalanceAmount);
		}
		res.setTotalRows(totalRows);
		res.setNumPerPage(numPerPage);
		res.setPageNum(pageNum);

		List<BsProduct> products = this.findProductListNoDep();
		List<BsPayOrders> buyBankTypeList = bsPayOrdersMapper.findBuyBankTypeList();
		List<BsProduct> rates = productMapper.selectProductRateList();
		res.setProductList(BeanUtils.classToArrayList(products));
		res.setBuyBankTypeList(BeanUtils.classToArrayList(buyBankTypeList));
		res.setRateList(BeanUtils.classToArrayList(rates));
		List<BsSysConfig> sysConfigList = bsSysConfigMapper.selectByExample(null);
		res.setSysConfigs(BeanUtils.classToArrayList(sysConfigList));
	}

	@Override
	public void depBuyMessageListQuery(ReqMsg_Statistics_BuyMessageDepListQuery req, ResMsg_Statistics_BuyMessageDepListQuery res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		//产品类型
		String productType = null;
		if(Constants.PROPERTY_SYMBOL_ZSD.equals(req.getPropertySymbol())) {
			productType = Constants.PRODUCT_TYPE_AUTH_ZSD;
		}else if(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF.equals(req.getPropertySymbol())) {
			productType = Constants.PRODUCT_TYPE_AUTH_7;
		}else if(Constants.PROPERTY_SYMBOL_ZAN.equals(req.getPropertySymbol())) {
			productType = Constants.PRODUCT_TYPE_AUTH;
		}else if(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF.equals(req.getPropertySymbol())) {
			productType = Constants.PRODUCT_TYPE_AUTH_YUN;
		}else if(Constants.PRODUCT_PROPERTY_SYMBOL_FREE.equals(req.getPropertySymbol())) {
			productType = Constants.PRODUCT_TYPE_AUTH_FREE;
		}
		
		int totalRows = 0;
		if (productType != null) {
			totalRows = this.countBuyMessageForDep(req.getOrderNo(),
					req.getBeginBuyAmount(), req.getEndBuyAmount(),
					req.getBuyBeginTime(), req.getBuyEndTime(),
					req.getInvestBeginTime(), req.getInvestEndTime(),
					req.getBindBankCard(), req.getBuyBankCard(), req.getIdCard(),
					req.getAccountStatus(), req.getMobile(), req.getUserName(),
					req.getTerm(), req.getProductCode(),req.getAgentName(),
					req.getBuyBankType(),req.getAgentId(),req.getRate(),req.getStartRate(),req.getEndRate(),
					req.getAgentIds(),req.getNonAgentId(),req.getPropertySymbol(), productType);
		}
		
		if (totalRows > 0) {
			List<BsStatisticsVO> valueList = this
					.findUserBuyMessageListForDep(req.getOrderNo(),
							req.getBeginBuyAmount(), req.getEndBuyAmount(),
							req.getBuyBeginTime(), req.getBuyEndTime(),
							req.getInvestBeginTime(), req.getInvestEndTime(),
							req.getBindBankCard(), req.getBuyBankCard(),
							req.getIdCard(), req.getProductCode(),req.getAgentName(),
							req.getAccountStatus(), req.getMobile(),
							req.getUserName(), req.getTerm(), req.getPageNum(),
							req.getNumPerPage(), req.getOrderDirection(),
							req.getOrderField(),req.getBuyBankType(),req.getAgentId(),
							req.getRate(),req.getStartRate(),req.getEndRate(),req.getAgentIds(),
							req.getNonAgentId(),req.getPropertySymbol(), productType);

			ArrayList<HashMap<String, Object>> buyMessageList = BeanUtils
					.classToArrayList(valueList);

			res.setUserBuyMessageList(buyMessageList);

		}
		res.setTotalRows(totalRows);
		res.setNumPerPage(numPerPage);
		res.setPageNum(pageNum);
	}

	private void checkBuyMessageParams(BsStatisticsVO statisticsVo, String orderNo, Double beginAmount, Double endAmount, Date beginTime, Date endTime, Date settleAccountsBeginTime, Date settleAccountsEndTime, String bindBankCard, String buyBankCard, String idCard, String productCode, String agentName, Integer accountStatus, String mobile, String userName, Integer term, int pageNum, int numPerPage, String orderDirection, String orderField, String buyBankType, Integer agentId, Double rate, Double startRate, Double endRate, String sAgentIds, String nonAgentId, String propertySymbol) {
		if(orderNo != null && !"".equals(orderNo)){
			statisticsVo.setOrderNo(orderNo);
		}
		if(beginAmount != null){
			statisticsVo.setBeginAmount(beginAmount);
		}
		if(endAmount != null){
			statisticsVo.setEndAmount(endAmount);
		}
		if(beginTime != null){
			statisticsVo.setBeginTime(beginTime);
		}
		if(endTime != null){
			statisticsVo.setEndTime(endTime);
		}
		if(settleAccountsBeginTime != null){
			statisticsVo.setSettleAccountsBeginTime(settleAccountsBeginTime);
		}
		if(settleAccountsEndTime != null){
			statisticsVo.setSettleAccountsEndTime(settleAccountsEndTime);
		}
		if(bindBankCard != null){
			statisticsVo.setBindBankCard(bindBankCard);
		}
		if(buyBankCard != null){
			statisticsVo.setBuyBankCard(buyBankCard);
		}
		if(idCard != null){
			statisticsVo.setIdCard(idCard);
		}
		if(accountStatus != null && !"".equals(accountStatus)){
			statisticsVo.setAccountStatus(accountStatus);
		}else {
			statisticsVo.setAccountStatus(0);
		}
		if(mobile != null){
			statisticsVo.setMobile(mobile);
		}
		if(userName != null){
			statisticsVo.setUserName(userName);
		}
		if(term != null){
			statisticsVo.setTerm(term);
		}
		if(productCode != null && !"".equals(productCode)){
			statisticsVo.setProductCode(productCode);
		}
		if (agentName != null && !"".equals(agentName)) {
			statisticsVo.setAgentName(agentName);
		}
		if (buyBankType != null && !"".equals(buyBankType)) {
			statisticsVo.setBuyBankType(buyBankType);
		}
		if(rate != null){
			statisticsVo.setRate(rate);
		}
		if(startRate  != null) {
			statisticsVo.setStartRate(startRate);
		}
		if(endRate != null) {
			statisticsVo.setEndRate(endRate);
		}
		if (orderDirection != null&& (!"".equals(orderDirection))&&orderField != null&& (!"".equals(orderField))) {
			statisticsVo.setOrderDirection(orderDirection);
			statisticsVo.setOrderField(orderField);
		}
		//statisticsVo.setAgentId(agentId);
		if(StringUtil.isNotEmpty(sAgentIds)) {
			String[] agentIds = sAgentIds.split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				statisticsVo.setAgentIds(ids);
			}
		}
		if(StringUtil.isNotEmpty(nonAgentId)) {
			statisticsVo.setNonAgentId(nonAgentId);
		}
		statisticsVo.setPageNum(pageNum);
		statisticsVo.setNumPerPage(numPerPage);

		if(propertySymbol != null){
			statisticsVo.setPropertySymbol(propertySymbol);
		}
	}


	private void checkBuyMessageParamsDep(BsStatisticsDepVO statisticsVo, String orderNo, String beginAmount, String endAmount, Date beginTime, Date endTime, Date settleAccountsBeginTime, Date settleAccountsEndTime, String bindBankCard, String buyBankCard, String idCard, String productCode, String agentName, Integer accountStatus, String mobile, String userName, Integer term, int pageNum, int numPerPage, String orderDirection, String orderField, String buyBankType, Integer agentId, Double rate, Double startRate, Double endRate, String sAgentIds, String nonAgentId, String propertySymbol, String productType) {
		if(orderNo != null && !"".equals(orderNo)){
			statisticsVo.setOrderNo(orderNo);
		}
		if(beginAmount != null){
			statisticsVo.setBeginAmount(beginAmount);
		}
		if(endAmount != null){
			statisticsVo.setEndAmount(endAmount);
		}
		if(beginTime != null){
			statisticsVo.setBeginTime(beginTime);
		}
		if(endTime != null){
			statisticsVo.setEndTime(endTime);
		}
		if(settleAccountsBeginTime != null){
			statisticsVo.setSettleAccountsBeginTime(settleAccountsBeginTime);
		}
		if(settleAccountsEndTime != null){
			statisticsVo.setSettleAccountsEndTime(settleAccountsEndTime);
		}
		if(bindBankCard != null){
			statisticsVo.setBindBankCard(bindBankCard);
		}
		if(buyBankCard != null){
			statisticsVo.setBuyBankCard(buyBankCard);
		}
		if(idCard != null){
			statisticsVo.setIdCard(idCard);
		}
		if(accountStatus != null && !"".equals(accountStatus)){
			statisticsVo.setAccountStatus(accountStatus);
		}else {
			statisticsVo.setAccountStatus(0);
		}
		if(mobile != null){
			statisticsVo.setMobile(mobile);
		}
		if(userName != null){
			statisticsVo.setUserName(userName);
		}
		if(term != null){
			statisticsVo.setTerm(term);
		}
		if(productCode != null && !"".equals(productCode)){
			statisticsVo.setProductCode(productCode);
		}
		if (agentName != null && !"".equals(agentName)) {
			statisticsVo.setAgentName(agentName);
		}
		if (buyBankType != null && !"".equals(buyBankType)) {
			statisticsVo.setBuyBankType(buyBankType);
		}
		if(rate != null){
			statisticsVo.setRate(rate);
		}
		if(startRate  != null) {
			statisticsVo.setStartRate(startRate);
		}
		if(endRate != null) {
			statisticsVo.setEndRate(endRate);
		}
		if (orderDirection != null&& (!"".equals(orderDirection))&&orderField != null&& (!"".equals(orderField))) {
			statisticsVo.setOrderDirection(orderDirection);
			statisticsVo.setOrderField(orderField);
		}
		//statisticsVo.setAgentId(agentId);
		if(StringUtil.isNotEmpty(sAgentIds)) {
			String[] agentIds = sAgentIds.split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				statisticsVo.setAgentIds(ids);
			}
		}
		if(StringUtil.isNotEmpty(nonAgentId)) {
			statisticsVo.setNonAgentId(nonAgentId);
		}
		statisticsVo.setPageNum(pageNum);
		statisticsVo.setNumPerPage(numPerPage);

		if(propertySymbol != null){
			statisticsVo.setPropertySymbol(propertySymbol);
		}
		if(productType != null) {
			statisticsVo.setProductType(productType);
		}
	}

	private void checkParamsForBuyMessage(BsStatisticsVO statisticsVo, String orderNo, Double beginAmount, Double endAmount,
										  Date beginTime, Date endTime, Date settleAccountsBeginTime, Date settleAccountsEndTime, String bindBankCard,
										  String buyBankCard, String idCard, Integer accountStatus, String mobile, String userName, Integer term,
										  String productCode, String agentName, String buyBankType, Integer agentId, Double rate, Double startRate, Double endRate,
										  String sAgentIds, String nonAgentId, String propertySymbol) {
		if(orderNo != null && !"".equals(orderNo)){
			statisticsVo.setOrderNo(orderNo);
		}
		if(beginAmount != null){
			statisticsVo.setBeginAmount(beginAmount);
		}
		if(endAmount != null){
			statisticsVo.setEndAmount(endAmount);
		}
		if(beginTime != null){
			statisticsVo.setBeginTime(beginTime);
		}
		if(endTime != null){
			statisticsVo.setEndTime(endTime);
		}

		if(settleAccountsBeginTime != null){
			statisticsVo.setSettleAccountsBeginTime(settleAccountsBeginTime);
		}

		if(settleAccountsEndTime != null){
			statisticsVo.setSettleAccountsEndTime(settleAccountsEndTime);
		}
		if(bindBankCard != null){
			statisticsVo.setBindBankCard(bindBankCard);
		}
		if(buyBankCard != null){
			statisticsVo.setBuyBankCard(buyBankCard);
		}
		if(idCard != null){
			statisticsVo.setIdCard(idCard);
		}
		if(accountStatus != null && !"".equals(accountStatus)){
			statisticsVo.setAccountStatus(accountStatus);
		}else {
			statisticsVo.setAccountStatus(0);
		}
		if(mobile != null){
			statisticsVo.setMobile(mobile);
		}
		if(userName != null){
			statisticsVo.setUserName(userName);
		}
		if(term != null){
			statisticsVo.setTerm(term);
		}
		if(productCode != null && !"".equals(productCode)){
			statisticsVo.setProductCode(productCode);
		}
		if (agentName != null && !"".equals(agentName)) {
			statisticsVo.setAgentName(agentName);
		}
		if (buyBankType != null && !"".equals(buyBankType)) {
			statisticsVo.setBuyBankType(buyBankType);
		}
		if (rate != null) {
			statisticsVo.setRate(rate);
		}
		if (startRate != null) {
			statisticsVo.setStartRate(startRate);
		}
		if (endRate != null) {
			statisticsVo.setEndRate(endRate);
		}
		//statisticsVo.setAgentId(agentId);
		if(StringUtil.isNotEmpty(sAgentIds)) {
			String[] agentIds = sAgentIds.split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				statisticsVo.setAgentIds(ids);
			}
		}
		if(StringUtil.isNotEmpty(nonAgentId)) {
			statisticsVo.setNonAgentId(nonAgentId);
		}

		if(propertySymbol != null){
			statisticsVo.setPropertySymbol(propertySymbol);
		}
	}

	private void checkParamsForBuyMessageDep(BsStatisticsDepVO statisticsVo, String orderNo, String beginAmount, String endAmount,
										  Date beginTime, Date endTime, Date settleAccountsBeginTime, Date settleAccountsEndTime, String bindBankCard,
										  String buyBankCard, String idCard, Integer accountStatus, String mobile, String userName, Integer term,
										  String productCode, String agentName, String buyBankType, Integer agentId, Double rate, Double startRate, Double endRate,
										  String sAgentIds, String nonAgentId, String propertySymbol, String productType) {
		if(orderNo != null && !"".equals(orderNo)){
			statisticsVo.setOrderNo(orderNo);
		}
		if(beginAmount != null){
			statisticsVo.setBeginAmount(beginAmount);
		}
		if(endAmount != null){
			statisticsVo.setEndAmount(endAmount);
		}
		if(beginTime != null){
			statisticsVo.setBeginTime(beginTime);
		}
		if(endTime != null){
			statisticsVo.setEndTime(endTime);
		}

		if(settleAccountsBeginTime != null){
			statisticsVo.setSettleAccountsBeginTime(settleAccountsBeginTime);
		}

		if(settleAccountsEndTime != null){
			statisticsVo.setSettleAccountsEndTime(settleAccountsEndTime);
		}
		if(bindBankCard != null){
			statisticsVo.setBindBankCard(bindBankCard);
		}
		if(buyBankCard != null){
			statisticsVo.setBuyBankCard(buyBankCard);
		}
		if(idCard != null){
			statisticsVo.setIdCard(idCard);
		}
		if(accountStatus != null && !"".equals(accountStatus)){
			statisticsVo.setAccountStatus(accountStatus);
		}else {
			statisticsVo.setAccountStatus(0);
		}
		if(mobile != null){
			statisticsVo.setMobile(mobile);
		}
		if(userName != null){
			statisticsVo.setUserName(userName);
		}
		if(term != null){
			statisticsVo.setTerm(term);
		}
		if(productCode != null && !"".equals(productCode)){
			statisticsVo.setProductCode(productCode);
		}
		if (agentName != null && !"".equals(agentName)) {
			statisticsVo.setAgentName(agentName);
		}
		if (buyBankType != null && !"".equals(buyBankType)) {
			statisticsVo.setBuyBankType(buyBankType);
		}
		if (rate != null) {
			statisticsVo.setRate(rate);
		}
		if (startRate != null) {
			statisticsVo.setStartRate(startRate);
		}
		if (endRate != null) {
			statisticsVo.setEndRate(endRate);
		}
		//statisticsVo.setAgentId(agentId);
		if(StringUtil.isNotEmpty(sAgentIds)) {
			String[] agentIds = sAgentIds.split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				statisticsVo.setAgentIds(ids);
			}
		}
		if(StringUtil.isNotEmpty(nonAgentId)) {
			statisticsVo.setNonAgentId(nonAgentId);
		}

		if(propertySymbol != null){
			statisticsVo.setPropertySymbol(propertySymbol);
		}
		if(productType != null) {
			statisticsVo.setProductType(productType);
		}
	}

	private void checkParamsForFindUserBuySumBalance(BsStatisticsVO statisticsVo, Double beginAmount, Double endAmount, Date beginTime, Date endTime, Date settleAccountsBeginTime, Date settleAccountsEndTime, String bindBankCard, String buyBankCard, String idCard, Integer accountStatus, String mobile, String userName, Integer term, String ProductCode, String buyBankType, Integer agentId, Double rate, Double startRate, Double endRate, String agentIds, String nonAgentId, String propertySymbol) {
		if(beginAmount != null){
			statisticsVo.setBeginAmount(beginAmount);
		}
		if(endAmount != null){
			statisticsVo.setEndAmount(endAmount);
		}
		if(beginTime != null){
			statisticsVo.setBeginTime(beginTime);
		}
		if(endTime != null){
			statisticsVo.setEndTime(endTime);
		}
		if(settleAccountsBeginTime != null){
			statisticsVo.setSettleAccountsBeginTime(settleAccountsBeginTime);
		}
		if(settleAccountsEndTime != null){
			statisticsVo.setSettleAccountsEndTime(settleAccountsEndTime);
		}
		if(bindBankCard != null){
			statisticsVo.setBindBankCard(bindBankCard);
		}
		if(buyBankCard != null){
			statisticsVo.setBuyBankCard(buyBankCard);
		}
		if(idCard != null){
			statisticsVo.setIdCard(idCard);
		}
		if(accountStatus != null){
			statisticsVo.setAccountStatus(accountStatus);
		}
		if(mobile != null){
			statisticsVo.setMobile(mobile);
		}
		if(userName != null){
			statisticsVo.setUserName(userName);
		}
		if(term != null){
			statisticsVo.setTerm(term);
		}
		if(ProductCode != null && !"".equals(ProductCode)){
			statisticsVo.setProductCode(ProductCode);
		}
		/*if(agentId != null){
			statisticsVo.setAgentId(agentId);
		}*/
		if (buyBankType != null && !"".equals(buyBankType)) {
			statisticsVo.setBuyBankType(buyBankType);
		}
		if(rate != null){
			statisticsVo.setRate(rate);
		}
		if(startRate  != null) {
			statisticsVo.setStartRate(startRate);
		}
		if(endRate != null) {
			statisticsVo.setEndRate(endRate);
		}
		if(StringUtil.isNotEmpty(agentIds)) {
			String[] sagentIds = agentIds.split(",");
			if(sagentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : sagentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				statisticsVo.setAgentIds(ids);
			}
		}
		if(StringUtil.isNotEmpty(nonAgentId)) {
			statisticsVo.setNonAgentId(nonAgentId);
		}

		if(propertySymbol != null){
			statisticsVo.setPropertySymbol(propertySymbol);
		}
	}

	private void checkParamsForFindUserBuySumBalanceDep(BsStatisticsDepVO statisticsVo, String beginAmount, String endAmount, Date beginTime, Date endTime, Date settleAccountsBeginTime, Date settleAccountsEndTime, String bindBankCard, String buyBankCard, String idCard, Integer accountStatus, String mobile, String userName, Integer term, String ProductCode, String buyBankType, Integer agentId, Double rate, Double startRate, Double endRate, String agentIds, String nonAgentId, String propertySymbol, String productType) {
		if(beginAmount != null){
			statisticsVo.setBeginAmount(beginAmount);
		}
		if(endAmount != null){
			statisticsVo.setEndAmount(endAmount);
		}
		if(beginTime != null){
			statisticsVo.setBeginTime(beginTime);
		}
		if(endTime != null){
			statisticsVo.setEndTime(endTime);
		}
		if(settleAccountsBeginTime != null){
			statisticsVo.setSettleAccountsBeginTime(settleAccountsBeginTime);
		}
		if(settleAccountsEndTime != null){
			statisticsVo.setSettleAccountsEndTime(settleAccountsEndTime);
		}
		if(bindBankCard != null){
			statisticsVo.setBindBankCard(bindBankCard);
		}
		if(buyBankCard != null){
			statisticsVo.setBuyBankCard(buyBankCard);
		}
		if(idCard != null){
			statisticsVo.setIdCard(idCard);
		}
		if(accountStatus != null){
			statisticsVo.setAccountStatus(accountStatus);
		}
		if(mobile != null){
			statisticsVo.setMobile(mobile);
		}
		if(userName != null){
			statisticsVo.setUserName(userName);
		}
		if(term != null){
			statisticsVo.setTerm(term);
		}
		if(ProductCode != null && !"".equals(ProductCode)){
			statisticsVo.setProductCode(ProductCode);
		}
		/*if(agentId != null){
			statisticsVo.setAgentId(agentId);
		}*/
		if (buyBankType != null && !"".equals(buyBankType)) {
			statisticsVo.setBuyBankType(buyBankType);
		}
		if(rate != null){
			statisticsVo.setRate(rate);
		}
		if(startRate  != null) {
			statisticsVo.setStartRate(startRate);
		}
		if(endRate != null) {
			statisticsVo.setEndRate(endRate);
		}
		if(StringUtil.isNotEmpty(agentIds)) {
			String[] sagentIds = agentIds.split(",");
			if(sagentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : sagentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				statisticsVo.setAgentIds(ids);
			}
		}
		if(StringUtil.isNotEmpty(nonAgentId)) {
			statisticsVo.setNonAgentId(nonAgentId);
		}

		if(propertySymbol != null){
			statisticsVo.setPropertySymbol(propertySymbol);
		}
		if(productType != null) {
			statisticsVo.setProductType(productType);
		}
	}

	@Override
	public List<BsStatisticsVO> findAgentUserMessageListAgent(String orderNo, Double beginAmount, Double endAmount, Date beginTime, Date endTime,
															  Date settleAccountsBeginTime, Date settleAccountsEndTime, String bindBankCard,
															  String buyBankCard, String idCard, String productCode,
															  Integer accountStatus, String mobile, String userName,
															  Integer term, int pageNum, int numPerPage, String orderDirection,
															  String orderField, String distributionChannel, String productName,
															  Date interestBeginTime, Date interestEndTime, Integer terminalType) {
		BsStatisticsVO statisticsVo = new BsStatisticsVO();

		if(orderNo != null && !"".equals(orderNo)){
			statisticsVo.setOrderNo(orderNo);
		}
		if(beginAmount != null){
			statisticsVo.setBeginAmount(beginAmount);
		}
		if(endAmount != null){
			statisticsVo.setEndAmount(endAmount);
		}
		if(beginTime != null){
			statisticsVo.setBeginTime(beginTime);
		}
		if(endTime != null){
			statisticsVo.setEndTime(DateUtil.addDays(endTime, 1));
		}
		if(settleAccountsBeginTime != null){
			statisticsVo.setSettleAccountsBeginTime(settleAccountsBeginTime);
		}

		if(settleAccountsEndTime != null){
			statisticsVo.setSettleAccountsEndTime(DateUtil.addDays(settleAccountsEndTime,1));
		}
		if(bindBankCard != null){
			statisticsVo.setBindBankCard(bindBankCard);
		}
		if(buyBankCard != null){
			statisticsVo.setBuyBankCard(buyBankCard);
		}
		if(idCard != null){
			statisticsVo.setIdCard(idCard);
		}
		if(accountStatus != null){
			statisticsVo.setAccountStatus(accountStatus);
		}
		if(mobile != null){
			statisticsVo.setMobile(mobile);
		}
		if(userName != null){
			statisticsVo.setUserName(userName);
		}
		if(term != null){
			statisticsVo.setTerm(term);
		}
		if(productCode != null && !"".equals(productCode)){
			statisticsVo.setProductCode(productCode);
		}

		if (orderDirection != null&& (!"".equals(orderDirection))&&orderField != null&& (!"".equals(orderField))) {
			statisticsVo.setOrderDirection(orderDirection);
			statisticsVo.setOrderField(orderField);
		}
		if(null != productName){
			statisticsVo.setProductName(productName);
		}
		if (null != interestBeginTime) {
			statisticsVo.setInterestBeginTime(interestBeginTime);
		}
		if (null != interestEndTime) {
			statisticsVo.setInterestEndTime(interestEndTime);
		}
		if (null != distributionChannel) {
			statisticsVo.setDistributionChannel(distributionChannel);
		}
		statisticsVo.setPageNum(pageNum);
		statisticsVo.setNumPerPage(numPerPage);
		statisticsVo.setTerminalType(terminalType);
		return bsUserMapper.findAgentUserMessageList(statisticsVo);
	}

	@Override
	public int countAgentBuyMessageAgent(String orderNo, Double beginAmount, Double endAmount, Date beginTime, Date endTime,
										 Date settleAccountsBeginTime, Date settleAccountsEndTime, String bindBankCard, String buyBankCard,
										 String idCard, Integer accountStatus, String mobile, String userName,
										 Integer term, String productCode, String distributionChannel,
										 String productName, Date interestBeginTime, Date interestEndTime, Integer terminalType) {
		BsStatisticsVO statisticsVo = new BsStatisticsVO();

		if(orderNo != null && !"".equals(orderNo)){
			statisticsVo.setOrderNo(orderNo);
		}
		if(beginAmount != null){
			statisticsVo.setBeginAmount(beginAmount);
		}
		if(endAmount != null){
			statisticsVo.setEndAmount(endAmount);
		}
		if(beginTime != null){
			statisticsVo.setBeginTime(beginTime);
		}
		if(endTime != null){

			statisticsVo.setEndTime(DateUtil.addDays(endTime, 1));
		}

		if(settleAccountsBeginTime != null){
			statisticsVo.setSettleAccountsBeginTime(settleAccountsBeginTime);
		}

		if(settleAccountsEndTime != null){
			statisticsVo.setSettleAccountsEndTime(DateUtil.addDays(settleAccountsEndTime,1));
		}
		if(bindBankCard != null){
			statisticsVo.setBindBankCard(bindBankCard);
		}
		if(buyBankCard != null){
			statisticsVo.setBuyBankCard(buyBankCard);
		}
		if(idCard != null){
			statisticsVo.setIdCard(idCard);
		}
		if(accountStatus != null){
			statisticsVo.setAccountStatus(accountStatus);
		}
		if(mobile != null){
			statisticsVo.setMobile(mobile);
		}
		if(userName != null){
			statisticsVo.setUserName(userName);
		}
		if(term != null){
			statisticsVo.setTerm(term);
		}
		if(productCode != null && !"".equals(productCode)){
			statisticsVo.setProductCode(productCode);
		}
		if(null != productName){
			statisticsVo.setProductName(productName);
		}
		if (null != interestBeginTime) {
			statisticsVo.setInterestBeginTime(interestBeginTime);
		}
		if (null != interestEndTime) {
			statisticsVo.setInterestEndTime(interestEndTime);
		}
		if (null != distributionChannel) {
			statisticsVo.setDistributionChannel(distributionChannel);
		}
		statisticsVo.setTerminalType(terminalType);
		return bsUserMapper.countAgentBuyMessage(statisticsVo);
	}

	@Override
	public double findAgentUserSumBalanceAgent(Double beginAmount, Double endAmount, Date beginTime, Date endTime, Date settleAccountsBeginTime,
											   Date settleAccountsEndTime, String bindBankCard, String buyBankCard, String idCard,
											   Integer accountStatus, String mobile, String userName, Integer term,
											   String ProductCode, String distributionChannel, String productName,
											   Date interestBeginTime, Date interestEndTime, Integer terminalType) {
		BsStatisticsVO statisticsVo = new BsStatisticsVO();
		if(beginAmount != null){
			statisticsVo.setBeginAmount(beginAmount);
		}
		if(endAmount != null){
			statisticsVo.setEndAmount(endAmount);
		}
		if(beginTime != null){
			statisticsVo.setBeginTime(beginTime);
		}
		if(endTime != null){
			statisticsVo.setEndTime(DateUtil.addDays(endTime, 1));
		}
		if(settleAccountsBeginTime != null){
			statisticsVo.setSettleAccountsBeginTime(settleAccountsBeginTime);
		}

		if(settleAccountsEndTime != null){
			statisticsVo.setSettleAccountsEndTime(DateUtil.addDays(settleAccountsEndTime,1));
		}
		if(bindBankCard != null){
			statisticsVo.setBindBankCard(bindBankCard);
		}
		if(buyBankCard != null){
			statisticsVo.setBuyBankCard(buyBankCard);
		}
		if(idCard != null){
			statisticsVo.setIdCard(idCard);
		}
		if(accountStatus != null){
			statisticsVo.setAccountStatus(accountStatus);
		}
		if(mobile != null){
			statisticsVo.setMobile(mobile);
		}
		if(userName != null){
			statisticsVo.setUserName(userName);
		}
		if(term != null){
			statisticsVo.setTerm(term);
		}
		if(ProductCode != null && !"".equals(ProductCode)){
			statisticsVo.setProductCode(ProductCode);
		}
		if(null != productName){
			statisticsVo.setProductName(productName);
		}
		if (null != interestBeginTime) {
			statisticsVo.setInterestBeginTime(interestBeginTime);
		}
		if (null != interestEndTime) {
			statisticsVo.setInterestEndTime(interestEndTime);
		}
		if (null != distributionChannel) {
			statisticsVo.setDistributionChannel(distributionChannel);
		}
		statisticsVo.setTerminalType(terminalType);
		return bsUserMapper.selectAgentSumBalance(statisticsVo);
	}

	@Override
	public int countBuyMessageForDep(String orderNo,String beginAmount, String endAmount,
									 Date beginTime, Date endTime,Date settleAccountsBeginTime, Date settleAccountsEndTime, String bindBankCard,
									 String buyBankCard, String idCard, Integer accountStatus,String mobile, String userName, Integer term,
									 String productCode,String agentName,String buyBankType,Integer agentId,Double rate,Double startRate,Double endRate,
									 String sAgentIds,String nonAgentId,String propertySymbol, String productType) {
		BsStatisticsDepVO statisticsVo = new BsStatisticsDepVO();

		checkParamsForBuyMessageDep(statisticsVo,orderNo,beginAmount, endAmount,
				beginTime,  endTime, settleAccountsBeginTime,  settleAccountsEndTime,  bindBankCard,
				buyBankCard,  idCard,  accountStatus, mobile,  userName,  term,
				productCode, agentName, buyBankType, agentId, rate, startRate, endRate,
				sAgentIds, nonAgentId, propertySymbol, productType);
		return bsUserMapper.countUserBuyMessageListForDep(statisticsVo);
	}

	@Override
	public double findUserBuySumBalanceForDep(String beginAmount, String endAmount, Date beginTime, Date endTime,
											  Date settleAccountsBeginTime, Date settleAccountsEndTime, String bindBankCard,
											  String buyBankCard, String idCard, Integer accountStatus, String mobile,
											  String userName, Integer term, String ProductCode, String buyBankType,
											  Integer agentId, Double rate, Double startRate, Double endRate, String agentIds,
											  String nonAgentId, String propertySymbol, String productType) {
		BsStatisticsDepVO statisticsVo = new BsStatisticsDepVO();
		checkParamsForFindUserBuySumBalanceDep(statisticsVo, beginAmount, endAmount, beginTime, endTime, settleAccountsBeginTime, settleAccountsEndTime, bindBankCard, buyBankCard, idCard, accountStatus, mobile, userName, term, ProductCode, buyBankType, agentId, rate, startRate, endRate, agentIds, nonAgentId, propertySymbol, productType);
		Double sumBalanceForDep = bsUserMapper.selectUserBuySumBalanceForDep(statisticsVo);
		return sumBalanceForDep == null ? 0d : sumBalanceForDep;
	}

	@Override
	public List<BsStatisticsVO> findUserBuyMessageListForDep(String orderNo,String beginAmount,
															 String endAmount, Date beginTime, Date endTime,Date settleAccountsBeginTime, Date settleAccountsEndTime,
															 String bindBankCard, String buyBankCard, String idCard,String productCode,String agentName,
															 Integer accountStatus, String mobile, String userName, Integer term,int pageNum,int numPerPage,
															 String orderDirection,String orderField,String buyBankType,Integer agentId,Double rate,Double startRate,Double endRate,
															 String sAgentIds,String nonAgentId,String propertySymbol, String productType) {
		BsStatisticsDepVO statisticsVo = new BsStatisticsDepVO();
		checkBuyMessageParamsDep(statisticsVo, orderNo, beginAmount,
				endAmount, beginTime, endTime,settleAccountsBeginTime, settleAccountsEndTime,
				bindBankCard, buyBankCard, idCard, productCode, agentName,
				accountStatus, mobile, userName, term,pageNum, numPerPage,
				orderDirection, orderField, buyBankType, agentId, rate, startRate, endRate,
				sAgentIds, nonAgentId, propertySymbol, productType);

		return bsUserMapper.selectUserBuyMessageListForDep(statisticsVo);
	}

	@Override
	public List<BsProduct> findProductListDep() {
		BsPropertyInfoExample example = new BsPropertyInfoExample();
		example.createCriteria().andPropertySymbolEqualTo(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
		List<BsPropertyInfo> propertyInfos = bsPropertyInfoMapper.selectByExample(example);
		
		BsProductExample productExample = new BsProductExample();
		productExample.createCriteria().andPropertyIdEqualTo(propertyInfos.get(0).getId());
		return productMapper.selectByExample(productExample);
	}

	@Override
	public List<BsProduct> findProductListNoDep() {
		BsPropertyInfoExample example = new BsPropertyInfoExample();
		example.createCriteria().andPropertySymbolEqualTo(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
		List<BsPropertyInfo> propertyInfos = bsPropertyInfoMapper.selectByExample(example);
		
		BsProductExample productExample = new BsProductExample();
		productExample.createCriteria().andPropertyIdNotEqualTo(propertyInfos.get(0).getId());
		return productMapper.selectByExample(productExample);
	}
	
	@Override
	public boolean deleteBsDepCash30(String partnerCode) {
		BsDepCash30Example example = new BsDepCash30Example();
		example.createCriteria().andCashDateGreaterThan(new Date()).andPartnerCodeEqualTo(partnerCode);
		
		int count = bsDepCash30Mapper.countByExample(example);
		return bsDepCash30Mapper.deleteByExample(example)!=count?false:true;
	}

	@Override
	public void insertDepCashScheduleList(List<DepCash30VO> valueList) {
		bsDepCash30Mapper.insertDepCashList(valueList);
	}
	
	@Override
	public void depInvestmentBuyMessageListQuery(ReqMsg_Statistics_BuyMessageDepListQuery req,
			ResMsg_Statistics_BuyMessageDepListQuery res) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		int totalRows = 0;
		totalRows = this.countInvestmentBuyMessageForDep(req.getMobile(), req.getUserName(), req.getOrderNo(),
					req.getInvestBuyStartTime(), req.getInvestBuyEndTime(), req.getInvestFinishStartTime(), req.getInvestFinishEndTime(),
					req.getAccountStatus(), req.getAgentName(), req.getAgentId(), req.getAgentIds(), req.getNonAgentId(), req.getPropertySymbol());
		
		if (totalRows > 0) {
			List<BsStatisticsVO> valueList = this
					.findInvestmentBuyMessageListForDep(req.getMobile(), req.getUserName(), req.getOrderNo(),
					req.getInvestBuyStartTime(), req.getInvestBuyEndTime(), req.getInvestFinishStartTime(), req.getInvestFinishEndTime(),
					req.getAccountStatus(), req.getAgentName(), req.getAgentId(), req.getAgentIds(), 
					req.getNonAgentId(), req.getPropertySymbol(), req.getPageNum(), req.getNumPerPage());

			ArrayList<HashMap<String, Object>> buyMessageList = BeanUtils
					.classToArrayList(valueList);
			res.setUserBuyMessageList(buyMessageList);
			
			res.setSumBuyMessageAmount(this.findInvestmentBuySumBalanceForDep(req.getMobile(), req.getUserName(), req.getOrderNo(),
					req.getInvestBuyStartTime(), req.getInvestBuyEndTime(), req.getInvestFinishStartTime(), req.getInvestFinishEndTime(),
					req.getAccountStatus(), req.getAgentName(), req.getAgentId(), req.getAgentIds(), 
					req.getNonAgentId(), req.getPropertySymbol()));
		}
		res.setTotalRows(totalRows);
		res.setNumPerPage(numPerPage);
		res.setPageNum(pageNum);
	}

	@Override
	public int countInvestmentBuyMessageForDep(String mobile, String userName, String orderNo, String beginTime,
			String endTime, String settleAccountsBeginTime, String settleAccountsEndTime, Integer accountStatus,
			String agentName, Integer agentId, String sAgentIds, String nonAgentId, String propertySymbol) {
		BsStatisticsDepVO statisticsVo = new BsStatisticsDepVO();
		checkInvestmentBuyMessageParamsDep(statisticsVo, orderNo, beginTime, endTime, settleAccountsBeginTime, settleAccountsEndTime, agentName,
				accountStatus, mobile, userName, 0, 0,
				agentId, sAgentIds, nonAgentId, propertySymbol, null);
		return bsUserMapper.countInvestmentBuyMessageListForDep(statisticsVo);
	}

	@Override
	public List<BsStatisticsVO> findInvestmentBuyMessageListForDep(String mobile, String userName, String orderNo,
			String beginTime, String endTime, String settleAccountsBeginTime, String settleAccountsEndTime,
			Integer accountStatus, String agentName, Integer agentId, String sAgentIds, String nonAgentId,
			String propertySymbol, int pageNum, int numPerPage) {
		BsStatisticsDepVO statisticsVo = new BsStatisticsDepVO();
		checkInvestmentBuyMessageParamsDep(statisticsVo, orderNo, beginTime, endTime, settleAccountsBeginTime, settleAccountsEndTime, agentName,
				accountStatus, mobile, userName, pageNum, numPerPage,
			    agentId, sAgentIds, nonAgentId, propertySymbol, null);
		return bsUserMapper.selectInvestmentBuyMessageListForDep(statisticsVo);
	}
	
	private void checkInvestmentBuyMessageParamsDep(BsStatisticsDepVO statisticsVo, String orderNo, String beginTime, String endTime, String settleAccountsBeginTime, String settleAccountsEndTime, String agentName,
			Integer accountStatus, String mobile, String userName, int pageNum, int numPerPage, Integer agentId, String sAgentIds, String nonAgentId, String propertySymbol, String productType) {
		if(orderNo != null && !"".equals(orderNo)){
			statisticsVo.setOrderNo(orderNo);
		}
		if(StringUtil.isNotBlank(beginTime)){
			statisticsVo.setInvestStartTime(beginTime);
		}
		if(StringUtil.isNotBlank(endTime)){
			statisticsVo.setInvestFinishTime(endTime);
		}
		if(StringUtil.isNotBlank(settleAccountsBeginTime)){
			statisticsVo.setLastFinishInterestStartTime(settleAccountsBeginTime);
		}
		if(StringUtil.isNotBlank(settleAccountsEndTime)){
			statisticsVo.setLastFinishInterestEndTime(settleAccountsEndTime);
		}
		if(accountStatus != null && !"".equals(accountStatus)){
			statisticsVo.setAccountStatus(accountStatus);
		}else {
			statisticsVo.setAccountStatus(0);
		}
		if(mobile != null){
			statisticsVo.setMobile(mobile);
		}
		if(userName != null){
			statisticsVo.setUserName(userName);
		}
		if (agentName != null && !"".equals(agentName)) {
			statisticsVo.setAgentName(agentName);
		}
		//statisticsVo.setAgentId(agentId);
		if(StringUtil.isNotEmpty(sAgentIds)) {
			String[] agentIds = sAgentIds.split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				statisticsVo.setAgentIds(ids);
			}
		}
		if(StringUtil.isNotEmpty(nonAgentId)) {
			statisticsVo.setNonAgentId(nonAgentId);
		}
		if (pageNum > 0) {			
			statisticsVo.setPageNum(pageNum);
		}
		if (numPerPage > 0) {			
			statisticsVo.setNumPerPage(numPerPage);
		}

		if(propertySymbol != null){
			statisticsVo.setPropertySymbol(propertySymbol);
		}
		if(productType != null) {
			statisticsVo.setProductType(productType);
		}
	}

	@Override
	public double findInvestmentBuySumBalanceForDep(String mobile, String userName, String orderNo, String beginTime,
			String endTime, String settleAccountsBeginTime, String settleAccountsEndTime, Integer accountStatus,
			String agentName, Integer agentId, String sAgentIds, String nonAgentId, String propertySymbol) {
		BsStatisticsDepVO statisticsVo = new BsStatisticsDepVO();
		checkInvestmentBuyMessageParamsDep(statisticsVo, orderNo, beginTime, endTime, settleAccountsBeginTime, settleAccountsEndTime, agentName,
				accountStatus, mobile, userName, 0, 0,
			    agentId, sAgentIds, nonAgentId, propertySymbol, null);
		Double sumBalanceForDep = bsUserMapper.selectInvestmentBuySumBalanceForDep(statisticsVo);
		return sumBalanceForDep == null ? 0d : sumBalanceForDep;
	}

	@Override
	public List<AgentUserVo> queryRealAmountTransList() {
		List<AgentUserVo> list = lnLoanRelationMapper.selectRealAmountTransList();
		return list;
	}

	@Override
	public List<AgentUserVo> queryRepayedPeriodCountList() {
		List<AgentUserVo> list = lnLoanRelationMapper.selectrepayedPeriodCountList();
		return null;
	}

	@Override
	public int countQhdAgentBuyMessage(String userName, String mobile, Integer term, Integer accountStatus,
			String distributionChannel, String terminalType, Double beginBuyAmount, Double endBuyAmount,
			Date investFinishBeginTime, Date investFinsishEndTime, Date buyBeginTime, Date buyEndTime) {
		BsQhdStatisticsInvestVO statisticsVo = new BsQhdStatisticsInvestVO();
		
		if(!StringUtil.isEmpty(userName)) {
			statisticsVo.setUserName(userName);
		}
		if(!StringUtil.isEmpty(mobile)) {
			statisticsVo.setMobile(mobile);
		}
		if(term != null) {
			statisticsVo.setTerm(term);
		}
		if(accountStatus != null) {
			statisticsVo.setAccountStatus(accountStatus);
		}
		if (null != beginBuyAmount) {
			statisticsVo.setBeginBuyAmount(beginBuyAmount);
		}
		if (null != endBuyAmount) {
			statisticsVo.setEndBuyAmount(endBuyAmount);
		}
		if(investFinishBeginTime != null) {
			statisticsVo.setSettleAccountsBeginTime(investFinishBeginTime);
		}
		if(investFinsishEndTime != null) {
			statisticsVo.setSettleAccountsEndTime(investFinsishEndTime);
		}
		if (null != buyBeginTime) {
			statisticsVo.setBuyBeginTime(buyBeginTime);
		}
		if (null != buyEndTime) {
			statisticsVo.setBuyEndTime(buyEndTime);
		}
		statisticsVo.setDistributionChannel(distributionChannel);
		statisticsVo.setTerminalType(terminalType);
		return bsUserMapper.countQhdAgentBuyMessage(statisticsVo);
	}

	@Override
	public List<BsQhdStatisticsInvestVO> findQhdAgentUserMessageList(String userName, String mobile, Integer term,
			Integer accountStatus, String distributionChannel, String terminalType, Double beginBuyAmount,
			Double endBuyAmount, Date investFinishBeginTime, Date investFinsishEndTime, Date buyBeginTime,
			Date buyEndTime, int pageNum, int numPerPage) {
		BsQhdStatisticsInvestVO statisticsVo = new BsQhdStatisticsInvestVO();
		
		if(!StringUtil.isEmpty(userName)) {
			statisticsVo.setUserName(userName);
		}
		if(!StringUtil.isEmpty(mobile)) {
			statisticsVo.setMobile(mobile);
		}
		if(term != null) {
			statisticsVo.setTerm(term);
		}
		if(accountStatus != null) {
			statisticsVo.setAccountStatus(accountStatus);
		}
		if (null != beginBuyAmount) {
			statisticsVo.setBeginBuyAmount(beginBuyAmount);
		}
		if (null != endBuyAmount) {
			statisticsVo.setEndBuyAmount(endBuyAmount);
		}
		if(investFinishBeginTime != null) {
			statisticsVo.setSettleAccountsBeginTime(investFinishBeginTime);
		}
		if(investFinsishEndTime != null) {
			statisticsVo.setSettleAccountsEndTime(investFinsishEndTime);
		}
		if (null != buyBeginTime) {
			statisticsVo.setBuyBeginTime(buyBeginTime);
		}
		if (null != buyEndTime) {
			statisticsVo.setBuyEndTime(buyEndTime);
		}
		statisticsVo.setPageNum(pageNum);
		statisticsVo.setNumPerPage(numPerPage);
		statisticsVo.setDistributionChannel(distributionChannel);
		statisticsVo.setTerminalType(terminalType);
		return bsUserMapper.findQhdAgentUserMessageList(statisticsVo);
	}

	@Override
	public Double findQhdAgentUserSumBalance(String userName, String mobile, Integer term, Integer accountStatus,
			String distributionChannel, String terminalType, Double beginBuyAmount, Double endBuyAmount,
			Date investFinishBeginTime, Date investFinsishEndTime, Date buyBeginTime, Date buyEndTime) {
		BsQhdStatisticsInvestVO statisticsVo = new BsQhdStatisticsInvestVO();
		
		if(!StringUtil.isEmpty(userName)) {
			statisticsVo.setUserName(userName);
		}
		if(!StringUtil.isEmpty(mobile)) {
			statisticsVo.setMobile(mobile);
		}
		if(term != null) {
			statisticsVo.setTerm(term);
		}
		if(accountStatus != null) {
			statisticsVo.setAccountStatus(accountStatus);
		}
		if (null != beginBuyAmount) {
			statisticsVo.setBeginBuyAmount(beginBuyAmount);
		}
		if (null != endBuyAmount) {
			statisticsVo.setEndBuyAmount(endBuyAmount);
		}
		if(investFinishBeginTime != null) {
			statisticsVo.setSettleAccountsBeginTime(investFinishBeginTime);
		}
		if(investFinsishEndTime != null) {
			statisticsVo.setSettleAccountsEndTime(investFinsishEndTime);
		}
		if (null != buyBeginTime) {
			statisticsVo.setBuyBeginTime(buyBeginTime);
		}
		if (null != buyEndTime) {
			statisticsVo.setBuyEndTime(buyEndTime);
		}
		statisticsVo.setDistributionChannel(distributionChannel);
		statisticsVo.setTerminalType(terminalType);
		return bsUserMapper.findQhdAgentUserSumBalance(statisticsVo);
	}
	
}
