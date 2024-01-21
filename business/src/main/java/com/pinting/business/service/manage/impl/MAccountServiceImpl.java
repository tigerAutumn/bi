package com.pinting.business.service.manage.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pinting.business.dao.*;
import com.pinting.business.model.vo.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.model.BsAccountJnl;
import com.pinting.business.model.BsAccountJnlExample;
import com.pinting.business.model.BsAssetsList;
import com.pinting.business.model.BsAssetsListExample;
import com.pinting.business.model.BsCashSchedule30;
import com.pinting.business.model.BsCashSchedule30Example;
import com.pinting.business.model.BsCheckErrorJnl;
import com.pinting.business.model.BsCheckErrorJnlExample;
import com.pinting.business.model.BsCheckJnl;
import com.pinting.business.model.BsPayOrdersExample;
import com.pinting.business.model.BsPayOrdersExample.Criteria;
import com.pinting.business.model.BsProfitLoss;
import com.pinting.business.model.BsProfitLossExample;
import com.pinting.business.model.BsSysWithdraw;
import com.pinting.business.model.BsSysWithdrawExample;
import com.pinting.business.model.BsUserWithdraw;
import com.pinting.business.model.BsUserWithdrawExample;
import com.pinting.business.service.manage.MAccountService;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
@Service
public class MAccountServiceImpl implements MAccountService{
	
	@Autowired
	private BsProfitLossMapper bsProfitLossMapper;
	@Autowired
	private BsAccountJnlMapper accountJnlMapper;
	
	@Autowired
	private BsCheckErrorJnlMapper checkErrorJnlMapper;
	
	@Autowired
	private BsCheckJnlMapper checkJnlMapper;
	
	@Autowired
	private BsUserMapper userMapper;
	@Autowired
	private BsCashSchedule30Mapper bsCashSchedule30Mapper;
	@Autowired
	private BsPayOrdersMapper bsPayOrdersMapper;
	@Autowired
	private BsSysWithdrawMapper bsSysWithdrawMapper;
	@Autowired
	private BsUserWithdrawMapper bsBsUserWithdrawMapper;
	@Autowired
	private BsAssetsListMapper bsAssetsListMapper;
	@Autowired
	private BsSysAccountJnlMapper bsSysAccountJnlMapper;
	@Autowired
	private BsBatchBuyMapper bsBatchBuyMapper;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private LnPayOrdersMapper lnPayOrdersMapper;
	@Autowired
	private LnRepayMapper lnRepayMapper;
	@Autowired
	private LnDepositionRepayScheduleMapper lnDepositionRepayScheduleMapper;
	
	@Override
	public ArrayList<BsCheckErrorJnlVO> findCheckErrorJnlListByPage(Date beginTime,Date overTime,int pageNum,
			int numPerPage) {
		BsCheckErrorJnlVO checkErrorJnl = new BsCheckErrorJnlVO();
		checkErrorJnl.setBeginTime(beginTime);
		checkErrorJnl.setOverTime(overTime);
		checkErrorJnl.setPageNum(pageNum);
		checkErrorJnl.setNumPerPage(numPerPage);
		
		return checkErrorJnlMapper.selectCheckErrorJnlListPageInfo(checkErrorJnl);
	}
	

	@Override
	public int countCheckErrorList() {
		BsCheckErrorJnlExample example = new BsCheckErrorJnlExample();
		return checkErrorJnlMapper.countByExample(example);
	}
	

	@Override
	public boolean updateCheckErrorById(BsCheckErrorJnl checkError) {

		return checkErrorJnlMapper.updateByPrimaryKeySelective(checkError)>0;
	}

	@Override
	public ArrayList<BsAccountJnlVO> findAccountListQueryByPage(Integer status,Date beginTime,Date overTime,int pageNum,
			int numPerPage) {
		BsAccountJnlVO accountJnlVO = new BsAccountJnlVO();
		accountJnlVO.setPageNum(pageNum);
		accountJnlVO.setNumPerPage(numPerPage);
		accountJnlVO.setBeginTime(beginTime);
		accountJnlVO.setOverTime(overTime);
		accountJnlVO.setStatus(status);

		return accountJnlMapper.selectAccountJnlListPageInfo(accountJnlVO);
	}

	@Override
	public int countTransferList(Integer status,Date beginTime,Date overTime) {
		BsAccountJnlExample example = new BsAccountJnlExample();
		com.pinting.business.model.BsAccountJnlExample.Criteria criteria =example.createCriteria();
		
		if(status!=null&&status>0){
			criteria.andStatusEqualTo(status);
		}
		if(beginTime!=null&&overTime!=null){
			criteria.andTransTimeBetween(beginTime, overTime);
		}
		return accountJnlMapper.countByExample(example);
	}

	@Override
	public BsAccountJnl findAccountJnlById(Integer id) {
		return accountJnlMapper.selectByPrimaryKey(id);
	}

	@Override
	public BsCheckJnl findCheckJnlById(Integer id) {
		return checkJnlMapper.selectByPrimaryKey(id);
	}

	@Override
	public ArrayList<BsProfitLoss> findProfitLossListQueryByPage(
			Date beginTime, Date overTime, int pageNum, int numPerPage) {
		BsProfitLossVO profitLossVO = new BsProfitLossVO();
		profitLossVO.setPageNum(pageNum);
		profitLossVO.setNumPerPage(numPerPage);
		profitLossVO.setBeginTime(beginTime);
		profitLossVO.setOverTime(overTime);

		return bsProfitLossMapper.selectProfitLossListPageInfo(profitLossVO);
	}


	@Override
	public int countProfitLossList() {
		BsProfitLossExample example = new BsProfitLossExample();
		return bsProfitLossMapper.countByExample(example);
	}


	@Override
	public ArrayList<BsStatisticsVO> findInvestMatureWarmList(Date settleAccountsBeginTime,
			Date settleAccountsEndTime, String userName, int pageNum, int numPerPage) {
		BsStatisticsVO statisticsVo = new BsStatisticsVO();
		
		if(settleAccountsBeginTime != null){
			statisticsVo.setSettleAccountsBeginTime(settleAccountsBeginTime);
		}
		
		if(settleAccountsEndTime != null){
			statisticsVo.setSettleAccountsEndTime(DateUtil.addDays(settleAccountsEndTime,1));
		}
		
		if(userName != null){
			statisticsVo.setUserName(userName);
		}
		statisticsVo.setPageNum(pageNum);
		statisticsVo.setNumPerPage(numPerPage);
			
		return userMapper.selectInvestMatureWarmList(statisticsVo);
	}


	@Override
	public int countInvestMatureWarmList(Date settleAccountsBeginTime, Date settleAccountsEndTime,
			String userName) {
		BsStatisticsVO statisticsVo = new BsStatisticsVO();
		
		if(settleAccountsBeginTime != null){
			statisticsVo.setSettleAccountsBeginTime(settleAccountsBeginTime);
		}
		
		if(settleAccountsEndTime != null){
			statisticsVo.setSettleAccountsEndTime(DateUtil.addDays(settleAccountsEndTime,1));
		}
		
		if(userName != null){
			statisticsVo.setUserName(userName);
		}
		
		Integer count = userMapper.countInvestMatureWarmList(statisticsVo);
		return count==null?0:count;
	}


	@Override
	public int countCashScheduleList(Date beginTime, Date overTime) {
		BsCashSchedule30VO cashSchedule30VO = new BsCashSchedule30VO();
		cashSchedule30VO.setBeginTime(beginTime);
		cashSchedule30VO.setOverTime(overTime);
		return bsCashSchedule30Mapper.countCashSchedule30List(cashSchedule30VO);
				
	}


	@Override
	public ArrayList<BsCashSchedule30> findCashScheduleListQueryByPage(
			Date beginTime, Date overTime, int pageNum, int numPerPage) {
		BsCashSchedule30VO CashSchedule30VO = new BsCashSchedule30VO();
		CashSchedule30VO.setPageNum(pageNum);
		CashSchedule30VO.setNumPerPage(numPerPage);
		CashSchedule30VO.setBeginTime(beginTime);
		CashSchedule30VO.setOverTime(overTime);
		ArrayList<BsCashSchedule30> list = bsCashSchedule30Mapper.selectCashSchedule30ListPageInfo(CashSchedule30VO);
//		if(CollectionUtils.isNotEmpty(list)){
//			for (BsCashSchedule30 bsCashSchedule30 : list) {
//				Date after30Days = DateUtil.addDays(new Date(), 365);
//				if(bsCashSchedule30.getCashDate().compareTo(after30Days) >0){
//					resList.add(bsCashSchedule30);
//				}else{
//					Map<String, Object> paramMap = new HashMap<String, Object>();
//					paramMap.put("currTime", bsCashSchedule30.getCashDate());
//					Double yun = bsBatchBuyMapper.selectOneDaySysAwaitingReturnYunDai(paramMap);
//					bsCashSchedule30.setYunDaiAmount(yun == null ? 0d : yun);
//					Double qi = bsBatchBuyMapper.selectOneDaySysAwaitingReturn7Dai(paramMap);
//					bsCashSchedule30.setQiDaiAmount(qi == null ? 0d : qi);
//					resList.add(bsCashSchedule30);
//				}
//				
//			}
//		}
		return list;
	}


	@Override
	public double sumCashBaseAmount(Date beginTime, Date overTime) {
		BsCashSchedule30VO cashSchedule = new BsCashSchedule30VO();
		cashSchedule.setBeginTime(beginTime);
		cashSchedule.setOverTime(overTime);
		Double amount = bsCashSchedule30Mapper.totalCashBaseAmount(cashSchedule) ;
		amount = amount ==  null? 0d : amount;
		return amount;
	}

	
	@Override
	public double sumBashInterestAmount(Date beginTime, Date overTime) {
		BsCashSchedule30VO cashSchedule = new BsCashSchedule30VO();
		cashSchedule.setBeginTime(beginTime);
		cashSchedule.setOverTime(overTime);
		Double amount = bsCashSchedule30Mapper.totalBashInterestAmount(cashSchedule);
		amount = amount ==  null? 0d : amount;
		return amount; 
	}


	@Override
	public ArrayList<BsPayOrdersVO> findOrdersListQueryByPage(String userMobile, String userName, String orderNo, 
			Integer accountType, String transType, String channelTransType, String bankName, String bankCardNo, 
			Date beginTime, Date overTime, int pageNum, int numPerPage, String orderDirection, String orderField, 
			Integer status, String buyBankType, Integer agentId, String mobile, String idCard, String payChannel, String returnMsg,
			String sAgentIds,String nonAgentId, Integer userId,String sShowTerminal) {
		BsPayOrdersVO payOrdersVO = new BsPayOrdersVO();
		if(userMobile != null && !"".equals(userMobile)){
			payOrdersVO.setUserMobile(userMobile);
		}
		if(userName != null && !"".equals(userName)){
			payOrdersVO.setUserName(userName);
		}
		if(orderNo != null && !"".equals(orderNo)){
			payOrdersVO.setOrderNo(orderNo);
		}
		if(accountType != null && !"".equals(accountType)){
			payOrdersVO.setAccountType(accountType);
		}
		if(transType != null && !"".equals(transType)){
			payOrdersVO.setTransType(transType);
		}
		if(channelTransType != null && !"".equals(channelTransType)){
			payOrdersVO.setChannelTransType(channelTransType);
		}
		if(bankName != null && !"".equals(bankName)){
			payOrdersVO.setBankName(bankName);
		}
		if(bankCardNo != null && !"".equals(bankCardNo)){
			payOrdersVO.setBankCardNo(bankCardNo);
		}
		if(beginTime != null) {
			payOrdersVO.setBeginTime(beginTime);
		}
		if(overTime != null) {
			payOrdersVO.setOverTime(DateUtil.addDays(overTime, 1));
		}
		if(status != null && !"".equals(status)) {
			payOrdersVO.setStatus(status);
		}
		if(buyBankType != null && !"".equals(buyBankType)) {
			payOrdersVO.setBuyBankType(buyBankType);
		}
		/*if(agentId != null && !"".equals(agentId)) {
			payOrdersVO.setAgentId(agentId);
		}*/
		if(mobile != null && !"".equals(mobile)) {
			payOrdersVO.setMobile(mobile);
		}
		if(idCard != null && !"".equals(idCard)) {
			payOrdersVO.setIdCard(idCard);
		}
		if(payChannel != null && !"".equals(payChannel)) {
			payOrdersVO.setPayChannel(payChannel);
		}
		if (orderDirection != null&& (!"".equals(orderDirection))&&orderField != null&& (!"".equals(orderField))) {
			payOrdersVO.setOrderDirection(orderDirection);
			payOrdersVO.setOrderField(orderField);
		}
		if(StringUtil.isNotEmpty(sAgentIds)) {
			String[] agentIds = sAgentIds.split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				payOrdersVO.setAgentIds(ids);
			}
		}
		if(StringUtil.isNotEmpty(nonAgentId)) {
			payOrdersVO.setNonAgentId(nonAgentId);
		}
		if(userId != null && !"".equals(userId)) {
			payOrdersVO.setUserId(userId);
		}

		if(StringUtils.isNotBlank(sShowTerminal)){
			String[] terminalTypes=sShowTerminal.split(",");
			if(terminalTypes.length>0){
				List<Object> types = new ArrayList<>();
				for(String terminalType:terminalTypes){
					types.add(Integer.valueOf(terminalType));
				}
				payOrdersVO.setTerminalTypes(types);
			}
		}
		payOrdersVO.setPageNum(pageNum);
		payOrdersVO.setNumPerPage(numPerPage);
		payOrdersVO.setReturnMsg(StringUtil.isNotBlank(returnMsg) ? returnMsg : null);
		return bsPayOrdersMapper.selectPayOrdersListPageInfo(payOrdersVO);
	}
	
	


	@Override
	public ArrayList<BsPayOrdersVO> findOrdersListQueryByPage4Normal(
			String userMobile, String userName, String transType,
			String channelTransType, Date beginTime, Date overTime,
			int pageNum, int numPerPage, Integer status, String buyBankType,
			Integer agentId, String payChannel, String returnMsg,
			String sAgentIds, String nonAgentId,String orderDirection, String orderField) {
		BsPayOrdersVO payOrdersVO = new BsPayOrdersVO();
		if(userMobile != null && !"".equals(userMobile)){
			payOrdersVO.setUserMobile(userMobile);
		}
		if(userName != null && !"".equals(userName)){
			payOrdersVO.setUserName(userName);
		}
		if(beginTime != null) {
			payOrdersVO.setBeginTime(beginTime);
		}else{
			payOrdersVO.setBeginTime(DateUtil.parseDateTime(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -89))+" 00:00:00"));
		}
		if(overTime != null) {
			payOrdersVO.setOverTime(DateUtil.addDays(overTime, 1));
		}else{
			payOrdersVO.setOverTime(DateUtil.parseDateTime(DateUtil.formatYYYYMMDD(new Date())+" 23:59:59"));
		}
		if(status != null && !"".equals(status)) {
			payOrdersVO.setStatus(status);
		}
		if(buyBankType != null && !"".equals(buyBankType)) {
			payOrdersVO.setBuyBankType(buyBankType);
		}
		if(transType != null && !"".equals(transType)){
			payOrdersVO.setTransType(transType);
		}
		if(channelTransType != null && !"".equals(channelTransType)){
			payOrdersVO.setChannelTransType(channelTransType);
		}
		if(payChannel != null && !"".equals(payChannel)) {
			payOrdersVO.setPayChannel(payChannel);
		}
		if (orderDirection != null&& (!"".equals(orderDirection))&&orderField != null&& (!"".equals(orderField))) {
			payOrdersVO.setOrderDirection(orderDirection);
			payOrdersVO.setOrderField(orderField);
		}
		if(StringUtil.isNotEmpty(sAgentIds)) {
			String[] agentIds = sAgentIds.split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				payOrdersVO.setAgentIds(ids);
			}
		}
		if(StringUtil.isNotEmpty(nonAgentId)) {
			payOrdersVO.setNonAgentId(nonAgentId);
		}
		payOrdersVO.setPageNum(pageNum);
		payOrdersVO.setNumPerPage(numPerPage);
		payOrdersVO.setReturnMsg(StringUtil.isNotBlank(returnMsg) ? returnMsg : null);
		return bsPayOrdersMapper.selectPayOrdersListPageInfo4Normal(payOrdersVO);
	}


	@Override
	public int countOrdersList(String orderNo,Integer Status,Date BeginTime,Date OverTime) {
		BsPayOrdersExample example = new BsPayOrdersExample();
		Criteria criteria = example.createCriteria();
		if(orderNo != null && !"".equals(orderNo)){
			criteria.andOrderNoEqualTo(orderNo);
		}
		if(Status>0){
			criteria.andStatusEqualTo(Status);
		}
		if(BeginTime!=null&&OverTime!=null){
			criteria.andCreateTimeBetween(BeginTime, OverTime);
		}
		return bsPayOrdersMapper.countByExample(example);
	}


	@Override
	public ArrayList<BsSysWithdraw> findSysWithdrawDetailListByPage(
			Date finishTimeBegin,Date finishTimeEnd,Date withdrawTimeBegin,Date withdrawTimeEnd,Integer status, int pageNum, int numPerPage) {
		
		
		BsSysWithdrawVO sysWithdrawVO = new BsSysWithdrawVO();
		
		
		if(finishTimeBegin != null && finishTimeEnd != null){
			sysWithdrawVO.setFinishTimeBegin(finishTimeBegin);
			sysWithdrawVO.setFinishTimeEnd(finishTimeEnd);
		}
		
		if(withdrawTimeBegin != null && withdrawTimeEnd != null){
			sysWithdrawVO.setWithdrawTimeBegin(withdrawTimeBegin);
			sysWithdrawVO.setWithdrawTimeEnd(withdrawTimeEnd);
		}
		
		if(status != null && status.intValue() != 0){
			sysWithdrawVO.setStatus(status);
		}
		sysWithdrawVO.setPageNum(pageNum);
		sysWithdrawVO.setNumPerPage(numPerPage);
		return bsSysWithdrawMapper.selectSysWithdrawDeteail(sysWithdrawVO);
	}


	@Override
	public int countSysWithdrawList(Date finishTimeBegin,Date finishTimeEnd,Date withdrawTimeBegin,Date withdrawTimeEnd,Integer status) {
		BsSysWithdrawExample example = new BsSysWithdrawExample();
		com.pinting.business.model.BsSysWithdrawExample.Criteria c = example.createCriteria();
		if(finishTimeBegin != null && finishTimeEnd != null){
			c.andFinishTimeBetween(finishTimeBegin, finishTimeEnd);
		}
		
		if(withdrawTimeBegin != null && withdrawTimeEnd != null){
			c.andWithdrawTimeBetween(withdrawTimeBegin, withdrawTimeEnd);
		}
		
		if(status != null && status.intValue() != 0){
			c.andStatusEqualTo(status);
		}
		
		return bsSysWithdrawMapper.countByExample(example);
	}


	@Override
	public ArrayList<BsUserWithdraw> findCustomerWithdrawByPage(String applyNo,
			Integer status, Date withdrawTime, Date finishTime, int pageNum,
			int numPerPage) {
	
		BsUserWithdraw model = new BsUserWithdraw();
		model.setApplyNo(applyNo);
		model.setFinishTime(finishTime);
		model.setWithdrawTime(withdrawTime);
		model.setStatus(status);
		model.setNumPerPage(numPerPage);
		model.setPageNum(pageNum);
		return  (ArrayList<BsUserWithdraw>) bsBsUserWithdrawMapper.findCustomerWithdrawByPage(model);
	}


	@Override
	public int countBsUserWithdraw(String applyNo, Integer status,
			Date withdrawTime, Date finishTime) {
		BsUserWithdrawExample example = new BsUserWithdrawExample();
		BsUserWithdrawExample.Criteria criteria = example.createCriteria();
		if(applyNo != null && !"".equals(applyNo)){
			criteria.andApplyNoEqualTo(applyNo);
		}
		if(status != null&&status>0){
			criteria.andStatusEqualTo(status);
		}
		if(withdrawTime!=null){
			criteria.andWithdrawTimeBetween(withdrawTime, DateUtil.addDays(withdrawTime, 1) );
		}
		if(finishTime!=null){
			criteria.andFinishTimeEqualTo(finishTime);
		}
		return bsBsUserWithdrawMapper.countByExample(example);
	}


	@Override
	public ArrayList<BsAssetsList> findAssetsListByPage(Integer status,
			Date createTime, int pageNum, int numPerPage) {
		BsAssetsList model = new BsAssetsList();
		model.setCreateTime(createTime);
		model.setStatus(status);
		model.setNumPerPage(numPerPage);
		model.setPageNum(pageNum);
		return  (ArrayList<BsAssetsList>) bsAssetsListMapper.findAssetsListByPage(model);
	}
	@Override
	public int countBsAssetsList(Integer status,
			Date createTime) {
		BsAssetsListExample example = new BsAssetsListExample();
		BsAssetsListExample.Criteria criteria = example.createCriteria();
		
		if(status != null&&status>0){
			criteria.andStatusEqualTo(status);
		}
		if(createTime!=null){
			criteria.andCreateTimeBetween(createTime, DateUtil.addDays(createTime, 1) );
		}
		
		return bsAssetsListMapper.countByExample(example);
	}


	@Override
	public void addBsAssetsPlan(BsAssetsList bsAssetsList) {
		bsAssetsListMapper.insertSelective(bsAssetsList);
	}


	@Override
	public int countInvestMatureWarm(Date time) {
		BsStatisticsVO statisticsVo = new BsStatisticsVO();
		statisticsVo.setEndTime(time);
		Integer count = userMapper.countInvestMatureWarm(statisticsVo);
		return count==null?0:count;
	}


	@Override
	public int selectCountPayOrder(String userMobile, String userName,
			String orderNo, Integer accountType, String transType,
			String channelTransType, String bankName, String bankCardNo,
			Date beginTime, Date overTime, Integer status, String buyBankType, 
			Integer agentId, String mobile, String idCard, String payChannel, String returnMsg,
			String sAgentIds, String nonAgentId, Integer userId,String sShowTerminal) {
		BsPayOrdersVO payOrdersVO = new BsPayOrdersVO();
		if(userMobile != null && !"".equals(userMobile)){
			payOrdersVO.setUserMobile(userMobile);
		}
		if(userName != null && !"".equals(userName)){
			payOrdersVO.setUserName(userName);
		}
		if(orderNo != null && !"".equals(orderNo)){
			payOrdersVO.setOrderNo(orderNo);
		}
		if(accountType != null && !"".equals(accountType)){
			payOrdersVO.setAccountType(accountType);
		}
		if(transType != null && !"".equals(transType)){
			payOrdersVO.setTransType(transType);
		}
		if(channelTransType != null && !"".equals(channelTransType)){
			payOrdersVO.setChannelTransType(channelTransType);
		}
		if(bankName != null && !"".equals(bankName)){
			payOrdersVO.setBankName(bankName);
		}
		if(bankCardNo != null && !"".equals(bankCardNo)){
			payOrdersVO.setBankCardNo(bankCardNo);
		}
		if(beginTime != null) {
			payOrdersVO.setBeginTime(beginTime);
		}
		if(overTime != null) {
			payOrdersVO.setOverTime(DateUtil.addDays(overTime, 1));
		}
		if(status != null && !"".equals(status)) {
			payOrdersVO.setStatus(status);
		}
		if (buyBankType != null && !"".equals(buyBankType)) {
			payOrdersVO.setBuyBankType(buyBankType);
		}
		/*if(agentId != null && !"".equals(agentId)) {
			payOrdersVO.setAgentId(agentId);
		}*/
		if(mobile != null && !"".equals(mobile)) {
			payOrdersVO.setMobile(mobile);
		}
		if(idCard != null && !"".equals(idCard)) {
			payOrdersVO.setIdCard(idCard);
		}
		if(payChannel != null && !"".equals(payChannel)) {
			payOrdersVO.setPayChannel(payChannel);
		}
		if(returnMsg != null && !"".equals(returnMsg)) {
            payOrdersVO.setReturnMsg(returnMsg);
        }
		if(StringUtil.isNotEmpty(sAgentIds)) {
			String[] agentIds = sAgentIds.split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				payOrdersVO.setAgentIds(ids);
			}
		}
		if(StringUtil.isNotEmpty(nonAgentId)) {
			payOrdersVO.setNonAgentId(nonAgentId);
		}
		if(userId != null && !"".equals(userId)) {
			payOrdersVO.setUserId(userId);
		}
		if(StringUtils.isNotBlank(sShowTerminal)){
			String[] terminalTypes=sShowTerminal.split(",");
			if(terminalTypes.length>0){
				List<Object> types = new ArrayList<>();
				for(String terminalType:terminalTypes){
					types.add(Integer.valueOf(terminalType));
				}
				payOrdersVO.setTerminalTypes(types);
			}
		}
		return bsPayOrdersMapper.selectCountPayOrders(payOrdersVO);
	}
	
	


	@Override
	public int selectCountPayOrder4Normal(String userMobile, String userName,
			String transType, String channelTransType, Date beginTime,
			Date overTime, int pageNum, int numPerPage, Integer status,
			String buyBankType, Integer agentId, String payChannel,
			String returnMsg, String sAgentIds, String nonAgentId) {
		BsPayOrdersVO payOrdersVO = new BsPayOrdersVO();
		if(userMobile != null && !"".equals(userMobile)){
			payOrdersVO.setUserMobile(userMobile);
		}
		if(userName != null && !"".equals(userName)){
			payOrdersVO.setUserName(userName);
		}
		if(transType != null && !"".equals(transType)){
			payOrdersVO.setTransType(transType);
		}
		if(channelTransType != null && !"".equals(channelTransType)){
			payOrdersVO.setChannelTransType(channelTransType);
		}
		if(beginTime != null) {
			payOrdersVO.setBeginTime(beginTime);
		}else{
			payOrdersVO.setBeginTime(DateUtil.parseDateTime(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -89))+" 00:00:00"));
		}
		if(overTime != null) {
			payOrdersVO.setOverTime(DateUtil.addDays(overTime, 1));
		}else{
			payOrdersVO.setOverTime(DateUtil.parseDateTime(DateUtil.formatYYYYMMDD(new Date())+" 23:59:59"));
		}
		if(status != null && !"".equals(status)) {
			payOrdersVO.setStatus(status);
		}
		if (buyBankType != null && !"".equals(buyBankType)) {
			payOrdersVO.setBuyBankType(buyBankType);
		}
		if(payChannel != null && !"".equals(payChannel)) {
			payOrdersVO.setPayChannel(payChannel);
		}
		if(returnMsg != null && !"".equals(returnMsg)) {
            payOrdersVO.setReturnMsg(returnMsg);
        }
		if(StringUtil.isNotEmpty(sAgentIds)) {
			String[] agentIds = sAgentIds.split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				payOrdersVO.setAgentIds(ids);
			}
		}
		if(StringUtil.isNotEmpty(nonAgentId)) {
			payOrdersVO.setNonAgentId(nonAgentId);
		}
		return bsPayOrdersMapper.selectCountPayOrders4Normal(payOrdersVO);
	}

	@Override
	public List<BsPayOrdersVO> findUserWithdrawal(String mobile,
			String userName, Integer status, Date beginTime, Date overTime, 
			int pageNum, int numPerPage, String orderDirection,
			String orderField) {
		BsPayOrdersVO bpoVO = new BsPayOrdersVO();
		if(mobile != null && !"".equals(mobile)){
			bpoVO.setMobile(mobile);
		}
		if(userName != null && !"".equals(userName)){
			bpoVO.setUserName(userName);
		}
		if(status != null && !"".equals(status)){
			bpoVO.setStatus(status);
		}
		if(beginTime != null) {
			bpoVO.setBeginTime(beginTime);
		}
		if(overTime != null) {
			bpoVO.setOverTime(overTime);
		}
		if (orderDirection != null&& (!"".equals(orderDirection))&&orderField != null&& (!"".equals(orderField))) {
			bpoVO.setOrderDirection(orderDirection);
			bpoVO.setOrderField(orderField);
		}
		bpoVO.setPageNum(pageNum);
		bpoVO.setNumPerPage(numPerPage);
		return bsPayOrdersMapper.selectUserWithdrawalInfo(bpoVO);
	}


	@Override
	public double sumUserWithdrawal(String mobile, String userName, 
			Integer status, Date beginTime, Date overTime) {
		BsPayOrdersVO bpoVO = new BsPayOrdersVO();
		if(mobile != null && !"".equals(mobile)){
			bpoVO.setMobile(mobile);
		}
		if(userName != null && !"".equals(userName)){
			bpoVO.setUserName(userName);
		}
		if(status != null && !"".equals(status)){
			bpoVO.setStatus(status);
		}
		if(beginTime != null) {
			bpoVO.setBeginTime(beginTime);
		}
		if(overTime != null) {
			bpoVO.setOverTime(overTime);
		}
		return bsPayOrdersMapper.selectSumUserWithdrawal(bpoVO);
	}


	@Override
	public int countUserWithdrawal(String mobile, String userName,
			Integer status, Date beginTime, Date overTime) {
		BsPayOrdersVO bpoVO = new BsPayOrdersVO();
		if(mobile != null && !"".equals(mobile)){
			bpoVO.setMobile(mobile);
		}
		if(userName != null && !"".equals(userName)){
			bpoVO.setUserName(userName);
		}
		if(status != null && !"".equals(status)){
			bpoVO.setStatus(status);
		}
		if(beginTime != null) {
			bpoVO.setBeginTime(beginTime);
		}
		if(overTime != null) {
			bpoVO.setOverTime(overTime);
		}
		return bsPayOrdersMapper.selectCountUserWithdrawal(bpoVO);
	}


	@Override
	public List<BsAccountJnlVO> findUserBonus(String mobile, String userName,
			String transCodes, Date beginTime, Date overTime, int pageNum,
			int numPerPage, String orderDirection, String orderField) {
		BsAccountJnlVO accountJnl = new BsAccountJnlVO();
		if(mobile != null && !"".equals(mobile)) {
			accountJnl.setMobile(mobile);
		}
		if(userName != null && !"".equals(userName)) {
			accountJnl.setUserName(userName);
		}
		if(transCodes != null && !"".equals(transCodes)) {
			accountJnl.setTransCodes(transCodes);
		}
		if(beginTime != null) {
			accountJnl.setBeginTime(beginTime);
		}
		if(overTime != null) {
			accountJnl.setOverTime(overTime);
		}
		if(orderDirection != null && !"".equals(orderDirection) && orderField != null && !"".equals(orderField)) {
			accountJnl.setOrderDirection(orderDirection);
			accountJnl.setOrderField(orderField);
		}
		accountJnl.setPageNum(pageNum);
		accountJnl.setNumPerPage(numPerPage);
		return accountJnlMapper.selectUserBonusList(accountJnl);
	}


	@Override
	public double sumUserBonus(String mobile, String userName,
			String transCodes, Date beginTime, Date overTime) {
		BsAccountJnlVO accountJnl = new BsAccountJnlVO();
		if(mobile != null && !"".equals(mobile)) {
			accountJnl.setMobile(mobile);
		}
		if(userName != null && !"".equals(userName)) {
			accountJnl.setUserName(userName);
		}
		if(transCodes != null && !"".equals(transCodes)) {
			accountJnl.setTransCodes(transCodes);
		}
		if(beginTime != null) {
			accountJnl.setBeginTime(beginTime);
		}
		if(overTime != null) {
			accountJnl.setOverTime(overTime);
		}
		return accountJnlMapper.selectSumUserBonus(accountJnl);
	}


	@Override
	public int countUserBonus(String mobile, String userName, String transCodes,
			Date beginTime, Date overTime) {
		BsAccountJnlVO accountJnl = new BsAccountJnlVO();
		if(mobile != null && !"".equals(mobile)) {
			accountJnl.setMobile(mobile);
		}
		if(userName != null && !"".equals(userName)) {
			accountJnl.setUserName(userName);
		}
		if(transCodes != null && !"".equals(transCodes)) {
			accountJnl.setTransCodes(transCodes);
		}
		if(beginTime != null) {
			accountJnl.setBeginTime(beginTime);
		}
		if(overTime != null) {
			accountJnl.setOverTime(overTime);
		}
		return accountJnlMapper.selectCoountUserBonus(accountJnl);
	}

	@Override
	public List<BsAccountJnlVO> userChargeAccount(String userName, String mobile, 
			Date beginTime, Date overTime, 
			Date startTransTime, Date endTransTime, 
			int pageNum, int numPerPage, 
			String orderDirection, String orderField) {
		BsAccountJnlVO accountJnl = new BsAccountJnlVO();
		if(userName != null && !"".equals(userName)) {
			accountJnl.setUserName(userName);
		}
		if(mobile != null && !"".equals(mobile)) {
			accountJnl.setMobile(mobile);
		}
		if(beginTime != null) {
			accountJnl.setBeginTime(beginTime);
		}
		if(overTime != null) {
			accountJnl.setOverTime(overTime);
		}
		if(startTransTime != null) {
			accountJnl.setStartTransTime(startTransTime);
		}
		if(endTransTime != null) {
			accountJnl.setEndTransTime(endTransTime);
		}
		if(orderDirection != null && !"".equals(orderDirection) && orderField != null && !"".equals(orderField)) {
			accountJnl.setOrderDirection(orderDirection);
			accountJnl.setOrderField(orderField);
		}
		accountJnl.setPageNum(pageNum);
		accountJnl.setNumPerPage(numPerPage);
		return accountJnlMapper.selectUserChargeAccountList(accountJnl);
	}


	@Override
	public int userCountChargeAccount(String userName, String mobile, 
			Date beginTime, Date overTime, 
			Date startTransTime, Date endTransTime) {
		BsAccountJnlVO accountJnl = new BsAccountJnlVO();
		if(userName != null && !"".equals(userName)) {
			accountJnl.setUserName(userName);
		}
		if(mobile != null && !"".equals(mobile)) {
			accountJnl.setMobile(mobile);
		}
		if(beginTime != null) {
			accountJnl.setBeginTime(beginTime);
		}
		if(overTime != null) {
			accountJnl.setOverTime(overTime);
		}
		if(startTransTime != null) {
			accountJnl.setStartTransTime(startTransTime);
		}
		if(endTransTime != null) {
			accountJnl.setEndTransTime(endTransTime);
		}
		return accountJnlMapper.selectCountUserChargeAccount(accountJnl);
	}


	@Override
	public List<BsSysAccountJnlVO> findSysAccountList(String transOtherCode,
			Integer status, Double startTransAmount, Double endTransAmount,
			Date beginTime, Date overTime, Date startTransTime,
			Date endTransTime, int pageNum, int numPerPage,
			String orderDirection, String orderField) {
		BsSysAccountJnlVO bsSysAccountJnl = new BsSysAccountJnlVO();
		if(transOtherCode != null && !"".equals(transOtherCode)) {
			bsSysAccountJnl.setTransOtherCode(transOtherCode);
		}
		if(status != null && !"".equals(status)) {
			bsSysAccountJnl.setStatus(status);
		}
		if(startTransAmount != null && !"".equals(startTransAmount)) {
			bsSysAccountJnl.setStartTransAmount(startTransAmount);
		}
		if(endTransAmount != null && !"".equals(endTransAmount)) {
			bsSysAccountJnl.setEndTransTime(endTransTime);
		}
		if(beginTime != null) {
			bsSysAccountJnl.setBeginTime(beginTime);
		}
		if(overTime != null) {
			bsSysAccountJnl.setOverTime(overTime);
		}
		if(startTransTime != null) {
			bsSysAccountJnl.setStartTransTime(startTransTime);
		}
		if(endTransTime != null) {
			bsSysAccountJnl.setEndTransTime(endTransTime);
		}
		if (orderDirection != null&& (!"".equals(orderDirection))&&orderField != null&& (!"".equals(orderField))) {
			bsSysAccountJnl.setOrderDirection(orderDirection);
			bsSysAccountJnl.setOrderField(orderField);
		}
		bsSysAccountJnl.setPageNum(pageNum);
		bsSysAccountJnl.setNumPerPage(numPerPage);
		
		return bsSysAccountJnlMapper.selectSysAccountList(bsSysAccountJnl);
	}


	@Override
	public int findCountSysAccount(String transOtherCode, Integer status,
			Double startTransAmount, Double endTransAmount, Date beginTime,
			Date overTime, Date startTransTime, Date endTransTime) {
		BsSysAccountJnlVO bsSysAccountJnl = new BsSysAccountJnlVO();
		if(transOtherCode != null && !"".equals(transOtherCode)) {
			bsSysAccountJnl.setTransOtherCode(transOtherCode);
		}
		if(status != null && !"".equals(status)) {
			bsSysAccountJnl.setStatus(status);
		}
		if(startTransAmount != null && !"".equals(startTransAmount)) {
			bsSysAccountJnl.setStartTransAmount(startTransAmount);
		}
		if(endTransAmount != null && !"".equals(endTransAmount)) {
			bsSysAccountJnl.setEndTransTime(endTransTime);
		}
		if(beginTime != null) {
			bsSysAccountJnl.setBeginTime(beginTime);
		}
		if(overTime != null) {
			bsSysAccountJnl.setOverTime(overTime);
		}
		if(startTransTime != null) {
			bsSysAccountJnl.setStartTransTime(startTransTime);
		}
		if(endTransTime != null) {
			bsSysAccountJnl.setEndTransTime(endTransTime);
		}
		return bsSysAccountJnlMapper.selectCountSysAccount(bsSysAccountJnl);
	}

	@Override
	public List<HFBankPayOrderVO> queryHFBankSysTopUp(HFBankPayOrderVO record) {
		List<HFBankPayOrderVO> list = bsPayOrdersMapper.selectHFBankSysTopUp(record);
		return (list != null && list.size() > 0) ? list : null;
	}

	@Override
	public List<HFBankPayOrderVO> queryHFBankSysWithdraw(HFBankPayOrderVO record) {
		List<HFBankPayOrderVO> list = bsPayOrdersMapper.selectHFBankSysWithdraw(record);
		return (list != null && list.size() > 0) ? list : null;
	}

	@Override
	public List<HFBankPayOrderVO> queryHFBankSysAccountTransfer(HFBankPayOrderVO record) {
		List<HFBankPayOrderVO> list = bsPayOrdersMapper.selectHFBankSysAccountTransfer(record);
		return (list != null && list.size() > 0) ? list : null;
	}

	@Override
	public int queryHFBankSysTopUpCount() {
		return bsPayOrdersMapper.selectHFBankSysTopUpCount();
	}

	@Override
	public int queryHFBankSysWithdrawCount() {
		return bsPayOrdersMapper.selectHFBankSysWithdrawCount();
	}

	@Override
	public int queryHFBankSysAccountTransferCount() {
		return bsPayOrdersMapper.selectHFBankSysAccountTransferCount();
	}

	@Override
	public List<HFBankPayOrderVO> queryZanCompensateList(HFBankPayOrderVO record) {
		List<HFBankPayOrderVO> list = bsPayOrdersMapper.selectZanCompensateList(record);
		return (list != null && list.size() > 0) ? list : null;
	}

	@Override
	public int queryZanCompensateCount() {
		return bsPayOrdersMapper.selectZanCompensateCount();
	}

	@Override
	public double queryZanAuthAmount(Integer userId) {
		double amount = 0d;
		amount = bsSubAccountMapper.selectZanAuthAmount(userId);
		return amount;
	}

	@Override
	public int queryHFBankAdvanceTransferCount() {
		return bsPayOrdersMapper.selectHFBankAdvanceTransferCount();
	}

	@Override
	public List<HFBankPayOrderVO> queryHFBankAdvanceTransfer(HFBankPayOrderVO record) {
		List<HFBankPayOrderVO> list = bsPayOrdersMapper.selectHFBankAdvanceTransfer(record);
		return (list != null && list.size() > 0) ? list : null;
	}

	@Override
	public List<HFBANKLoanWithdrawOrderVO> querySysLoanerWithdraw(
			HFBANKLoanWithdrawOrderVO record) {
		List<HFBANKLoanWithdrawOrderVO> list = lnPayOrdersMapper.selectLoanerList(record);
		return (list != null && list.size() > 0) ? list : null;
	}

	@Override
	public int querySysLoanerWithdrawCount() {
		return lnPayOrdersMapper.selectSysLoanerWithdrawCount();
	}

	@Override
	public int queryYunHeadFeeTransferCount() {
		return bsPayOrdersMapper.selectYunHeadFeeTransferCount();
	}

	@Override
	public List<HFBankHeadFeeTransferVO> queryYunHeadFeeTransfer(HFBankHeadFeeTransferVO record) {
		List<HFBankHeadFeeTransferVO> list = bsPayOrdersMapper.selectYunHeadFeeTransfer(record);
		return (list != null && list.size() > 0) ? list : null;
	}

	@Override
	public Map<String, Object> queryPartnerOfflineRepaymentInfo(String partnerCode, String startTime,
																String endTime, Integer pageNum, Integer numPerPage) {
		Integer start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
		int count = lnRepayMapper.selectPartnerOfflineRepaymentCount(partnerCode, startTime, endTime);
		Map<String, Object> result = new HashMap<>();
		if(count > 0) {
			List<PartnerOfflineRepaymentVO> partnerOfflineRepaymentList =
					lnRepayMapper.selectPartnerOfflineRepaymentInfo(partnerCode, startTime, endTime, start, numPerPage);
			result.put("list", partnerOfflineRepaymentList);
			result.put("count", count);
		} else {
			result.put("count", count);
			result.put("list", new ArrayList<PartnerOfflineRepaymentVO>());
		}
		return result;
	}
	
	@Override
	public int countQbDepServiceFee(String productName, Integer productTerm,
			Date buyBeginTime, Date buyEndTime) {
		DepServiceFeeSelectVO depServiceFeeSelectVO = new DepServiceFeeSelectVO();
		checkQbParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, buyBeginTime, buyEndTime);
		return userMapper.countQbDepServiceFee(depServiceFeeSelectVO);
	}

	@Override
	public List<DepServiceFeeVO> queryQbDepServiceFeeList(String productName,
			Integer productTerm, Date buyBeginTime, Date buyEndTime, int pageNum, int numPerPage) {
		DepServiceFeeSelectVO depServiceFeeSelectVO = new DepServiceFeeSelectVO();
		depServiceFeeSelectVO.setNumPerPage(numPerPage);
		depServiceFeeSelectVO.setPageNum(pageNum);
		checkQbParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, buyBeginTime, buyEndTime);
		return userMapper.queryQbDepServiceFeeList(depServiceFeeSelectVO);
	}

	@Override
	public int countHsDepServiceFee(String productName, Integer productTerm,
			Date buyBeginTime, Date buyEndTime, Integer accountStatus,
			String agentIds, String nonAgentId, Integer agentId) {
		DepServiceFeeSelectVO depServiceFeeSelectVO = new DepServiceFeeSelectVO();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		Date date1 = new Date();
		Date date2 = new Date();
		try {
			date1 = sdf.parse("2018-01-01");
			date2 = sdf.parse("2017-12-31");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//根据时间筛选有三种情况，分别为购买结束时间小于20180101，购买开始时间大于或等于20180101，与其他
		if((buyEndTime!=null && buyEndTime.before(date1))) {
			checkParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, buyBeginTime, buyEndTime, accountStatus, agentIds, nonAgentId);
			return userMapper.countHsDepServiceFee(depServiceFeeSelectVO);
		} else if((buyBeginTime!=null && !buyBeginTime.before(date1))) {
			checkParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, buyBeginTime, buyEndTime, accountStatus, agentIds, nonAgentId);
			return userMapper.countHsDep178ServiceFee(depServiceFeeSelectVO);
		} else {
			checkParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, date1, buyEndTime, accountStatus, agentIds, nonAgentId);
            int sum = userMapper.countHsDep178ServiceFee(depServiceFeeSelectVO);
			depServiceFeeSelectVO.setBuyBeginTime(buyBeginTime);
			depServiceFeeSelectVO.setBuyEndTime(date2);
			int sum178 = userMapper.countHsDepServiceFee(depServiceFeeSelectVO);
			return sum + sum178;
		}
	}

	@Override
	public List<DepServiceFeeVO> queryHsDepServiceFeeList(String productName,
			Integer productTerm, Date buyBeginTime, Date buyEndTime, Integer accountStatus,
			String agentIds, String nonAgentId, Integer agentId, int pageNum, int numPerPage) {
		DepServiceFeeSelectVO depServiceFeeSelectVO = new DepServiceFeeSelectVO();
		depServiceFeeSelectVO.setNumPerPage(numPerPage);
		depServiceFeeSelectVO.setPageNum(pageNum);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		Date date1 = new Date();
		Date date2 = new Date();
		try {
			date1=sdf.parse("2018-01-01");
			date2=sdf.parse("2017-12-31");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//根据时间筛选有三种情况，分别为购买结束时间小于20180101，购买开始时间大于或等于20180101，与其他
		if((buyEndTime!=null && buyEndTime.before(date1))) {
			checkParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, buyBeginTime, buyEndTime, accountStatus, agentIds, nonAgentId);
			return userMapper.queryHsDepServiceFeeList(depServiceFeeSelectVO);
		} else if((buyBeginTime!=null && !buyBeginTime.before(date1))) {
			checkParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, buyBeginTime, buyEndTime, accountStatus, agentIds, nonAgentId);
			return userMapper.queryHsDepServiceFee178List(depServiceFeeSelectVO);
		} else {
			checkParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, buyBeginTime, buyEndTime, accountStatus, agentIds, nonAgentId);
			return userMapper.queryHsDepServiceFeeAllList(depServiceFeeSelectVO);
		}
	}
	
	@Override
	public int countRebateOrder(Date beginTime, Date endTime) {
		return lnDepositionRepayScheduleMapper.countRebateOrder(beginTime, endTime);
	}

	@Override
	public List<LnDepositionRepayScheduleVO> queryRebateOrderList(Date beginTime, Date endTime, Integer pageNum, Integer numPerPage) {
		int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
		List<LnDepositionRepayScheduleVO> list = lnDepositionRepayScheduleMapper.queryRebateOrderList(beginTime, endTime, start, numPerPage);
		return (CollectionUtils.isNotEmpty(list)) ? list : null;
	}

	@Override
	public int countPtDepServiceFee(String productName, Integer productTerm,
			Date buyBeginTime, Date buyEndTime,
			String agentIds, String nonAgentId, Integer agentId) {
		DepServiceFeeSelectVO depServiceFeeSelectVO = new DepServiceFeeSelectVO();
		checkParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, buyBeginTime, buyEndTime, agentIds, nonAgentId);
		return userMapper.countPtDepServiceFee(depServiceFeeSelectVO);
	}

	@Override
	public List<DepServiceFeeVO> queryPtDepServiceFeeList(String productName,
			Integer productTerm, Date buyBeginTime, Date buyEndTime,
			String agentIds, String nonAgentId, Integer agentId, int pageNum, int numPerPage) {
		DepServiceFeeSelectVO depServiceFeeSelectVO = new DepServiceFeeSelectVO();
		depServiceFeeSelectVO.setNumPerPage(numPerPage);
		depServiceFeeSelectVO.setPageNum(pageNum);
		checkParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, buyBeginTime, buyEndTime, agentIds, nonAgentId);
		return userMapper.queryPtDepServiceFeeList(depServiceFeeSelectVO);
	}
	
	private void checkQbParamsForDepServiceFee(DepServiceFeeSelectVO depServiceFeeSelectVO, String productName,
			Integer productTerm, Date beginTime, Date endTime) {
		if (StringUtil.isNotBlank(productName)) {
			depServiceFeeSelectVO.setProductName(productName);
		}
		if (productTerm != null) {
			depServiceFeeSelectVO.setProductTerm(productTerm);
		}
		if (beginTime != null) {
			depServiceFeeSelectVO.setBuyBeginTime(beginTime);
		}
		if (endTime != null) {
			depServiceFeeSelectVO.setBuyEndTime(endTime);
		}
	}
	
	private void checkParamsForDepServiceFee(DepServiceFeeSelectVO depServiceFeeSelectVO, String productName,
			Integer productTerm, Date beginTime, Date endTime, Integer accountStatus,
			String sAgentIds, String nonAgentId) {
		if (StringUtils.isNotBlank(productName)) {
			depServiceFeeSelectVO.setProductName(productName);
		}
		if (productTerm != null) {
			depServiceFeeSelectVO.setProductTerm(productTerm);
		}
		if (beginTime != null) {
			depServiceFeeSelectVO.setBuyBeginTime(beginTime);
		}
		if (endTime != null) {
			depServiceFeeSelectVO.setBuyEndTime(endTime);
		}
		if (accountStatus != null) {
			depServiceFeeSelectVO.setAccountStatus(accountStatus);
		}
		if(StringUtils.isNotEmpty(sAgentIds)) {
			String[] agentIds = sAgentIds.split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtils.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				depServiceFeeSelectVO.setAgentIds(ids);
			}
		}
		if(StringUtils.isNotEmpty(nonAgentId)) {
			depServiceFeeSelectVO.setNonAgentId(nonAgentId);
		}
	}
	
	private void checkParamsForDepServiceFee(DepServiceFeeSelectVO depServiceFeeSelectVO, String productName,
			Integer productTerm, Date beginTime, Date endTime,
			String sAgentIds, String nonAgentId) {
		if (StringUtil.isNotBlank(productName)) {
			depServiceFeeSelectVO.setProductName(productName);
		}
		if (productTerm != null) {
			depServiceFeeSelectVO.setProductTerm(productTerm);
		}
		if (beginTime != null) {
			depServiceFeeSelectVO.setBuyBeginTime(beginTime);
		}
		if (endTime != null) {
			depServiceFeeSelectVO.setBuyEndTime(endTime);
		}
		if(StringUtil.isNotEmpty(sAgentIds)) {
			String[] agentIds = sAgentIds.split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				depServiceFeeSelectVO.setAgentIds(ids);
			}
		}
		if(StringUtil.isNotEmpty(nonAgentId)) {
			depServiceFeeSelectVO.setNonAgentId(nonAgentId);
		}
	}

	@Override
	public Double sumBuyAmount(String productName, Integer productTerm,
			Date buyBeginTime, Date buyEndTime,
			String agentIds, String nonAgentId, Integer agentId) {
		DepServiceFeeSelectVO depServiceFeeSelectVO = new DepServiceFeeSelectVO();
		checkParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, buyBeginTime, buyEndTime, agentIds, nonAgentId);
		return userMapper.sumPtBuyAmount(depServiceFeeSelectVO);
	}

	@Override
	public Double sumBuyAmount(String productName, Integer productTerm,
			Date buyBeginTime, Date buyEndTime, Integer accountStatus,
			String agentIds, String nonAgentId, Integer agentId) {
		DepServiceFeeSelectVO depServiceFeeSelectVO = new DepServiceFeeSelectVO();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		Date date1 = new Date();
		Date date2 = new Date();
		try {
			date1 = sdf.parse("2018-01-01");
			date2 = sdf.parse("2017-12-31");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//根据时间筛选有三种情况，分别为购买结束时间小于20180101，购买开始时间大于或等于20180101，与其他
		if((buyEndTime!=null && buyEndTime.before(date1))) {
			checkParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, buyBeginTime, buyEndTime, accountStatus, agentIds, nonAgentId);
			return userMapper.sumBuyAmount(depServiceFeeSelectVO);
		} else if((buyBeginTime!=null && !buyBeginTime.before(date1))) {
			checkParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, buyBeginTime, buyEndTime, accountStatus, agentIds, nonAgentId);
			return userMapper.sumBuy178Amount(depServiceFeeSelectVO);
		} else {
			checkParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, date1, buyEndTime, accountStatus, agentIds, nonAgentId);
            Double sum = userMapper.sumBuy178Amount(depServiceFeeSelectVO);
            depServiceFeeSelectVO.setBuyBeginTime(buyBeginTime);
			depServiceFeeSelectVO.setBuyEndTime(date2);
			Double sum178 = userMapper.sumBuyAmount(depServiceFeeSelectVO);
			return sum + sum178;
		}
	}

	@Override
	public Double sumDepServiceFee(String productName, Integer productTerm,
			Date buyBeginTime, Date buyEndTime,
			String agentIds, String nonAgentId, Integer agentId) {
		DepServiceFeeSelectVO depServiceFeeSelectVO = new DepServiceFeeSelectVO();
		checkParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, buyBeginTime, buyEndTime, agentIds, nonAgentId);
		return userMapper.sumDepServiceFee(depServiceFeeSelectVO);
	}


	@Override
	public Double sumQbBuyAmount(String productName, Integer productTerm,
			Date buyBeginTime, Date buyEndTime) {
		DepServiceFeeSelectVO depServiceFeeSelectVO = new DepServiceFeeSelectVO();
		checkQbParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, buyBeginTime, buyEndTime);
		return userMapper.sumQbBuyAmount(depServiceFeeSelectVO);
	}

	@Override
	public Double sumQbDepServiceFee(String productName, Integer productTerm,
			Date buyBeginTime, Date buyEndTime) {
		DepServiceFeeSelectVO depServiceFeeSelectVO = new DepServiceFeeSelectVO();
		checkQbParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, buyBeginTime, buyEndTime);
		return userMapper.sumQbDepServiceFee(depServiceFeeSelectVO);
	}

	@Override
	public Double sumHsDepServiceFee(String productName, Integer productTerm,
			Date buyBeginTime, Date buyEndTime, Integer accountStatus, String agentIds,
			String nonAgentId, Integer agentId) {
		DepServiceFeeSelectVO depServiceFeeSelectVO = new DepServiceFeeSelectVO();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		Date date1 = new Date();
		Date date2 = new Date();
		try {
			date1 = sdf.parse("2018-01-01");
			date2 = sdf.parse("2017-12-31");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//根据时间筛选有三种情况，分别为购买结束时间小于20180101，购买开始时间大于或等于20180101，与其他
		if((buyEndTime!=null && buyEndTime.before(date1))) {
			checkParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, buyBeginTime, buyEndTime, accountStatus, agentIds, nonAgentId);
			return userMapper.sumHsDepServiceFee(depServiceFeeSelectVO);
		} else if((buyBeginTime!=null && !buyBeginTime.before(date1))) {
			checkParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, buyBeginTime, buyEndTime, accountStatus, agentIds, nonAgentId);
			return userMapper.sumHsDep178ServiceFee(depServiceFeeSelectVO);
		} else {
			checkParamsForDepServiceFee(depServiceFeeSelectVO, productName, productTerm, date1, buyEndTime, accountStatus, agentIds, nonAgentId);
            Double sum = userMapper.sumHsDep178ServiceFee(depServiceFeeSelectVO);
            depServiceFeeSelectVO.setBuyBeginTime(buyBeginTime);
			depServiceFeeSelectVO.setBuyEndTime(date2);
			Double sum178 = userMapper.sumHsDepServiceFee(depServiceFeeSelectVO);
			if (sum == null) {
				sum = 0d;
			}
			if (sum178 == null) {
				sum178 = 0d;
			}
			return sum + sum178;
		}
	}
	
}
