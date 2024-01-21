package com.pinting.business.service.manage.impl;

import java.util.List;

import com.pinting.business.model.vo.CorpusBuyStatisticsVO;
import com.pinting.business.model.vo.HfbankCustomerWithdrawalVO;
import com.pinting.business.model.vo.LnLoanServiceFeeVO;
import com.pinting.business.model.vo.PayFinanceStatisticsVO;
import com.pinting.business.model.vo.UserRechanageStatisticsVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.dao.LnDepositionRepayScheduleDetailMapper;
import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.service.manage.DepMFinanceStatisticsService;
import com.pinting.core.util.StringUtil;
@Service
public class DepMFinanceStatisticsServiceImpl implements
		DepMFinanceStatisticsService {
	@Autowired
	private LnLoanRelationMapper lnLoanRelationMapper;
	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
	@Autowired
	private BsPayOrdersMapper bsPayOrdersMapper;
	@Autowired
	private LnDepositionRepayScheduleDetailMapper lnDepositionRepayScheduleDetailMapper;
	
	
	@Override
	public List<UserRechanageStatisticsVO> userRechangeStatistics(
			String userName, String mobile, String startTime, String endTime,
			Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<UserRechanageStatisticsVO> result = lnLoanRelationMapper.depUserRechangeStatistics(mobile, userName, startTime,
				endTime,position,offset);
		return result;
	}

	@Override
	public Integer userRechangeStatisticsCount(String userName, String mobile,
			String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Integer count = lnLoanRelationMapper.depUserRechangeStatisticsCount(mobile, userName, startTime, endTime);
		return count;
	}

	@Override
	public Double userTotalRechangeAmountStatistics(String userName,
			String mobile, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Double totalAmount = lnLoanRelationMapper.depUserTotalRechangeAmountStatistics(mobile, userName, startTime, endTime);
		return totalAmount;
	}

	@Override
	public List<CorpusBuyStatisticsVO> yunZSDCorpusBuyStatistics(String userType,
			String userName, String mobile, String startTime, String endTime, String partnerCode,
			Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<CorpusBuyStatisticsVO> list = bsPayOrdersMapper.yunZSDCorpusBuyStatistics(userType, mobile, userName, startTime, endTime, partnerCode, position, offset);
		return list;
	}

	@Override
	public Integer yunZSDCorpusBuyStatisticsCount(String userType,
			String userName, String mobile, String startTime, String endTime, String partnerCode) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Integer count = bsPayOrdersMapper.yunZSDCorpusBuyStatisticsCount(userType, mobile, userName, startTime, endTime, partnerCode);
		return count;
	}

	@Override
	public Double yunZSDCorpusBuyStatisticsSumBalance(String userType,
			String userName, String mobile, String startTime, String endTime, String partnerCode) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Double sumBalance = bsPayOrdersMapper.yunZSDCorpusBuyStatisticsSumBalance(userType, mobile, userName, startTime, endTime, partnerCode);
		return sumBalance;
	}

	@Override	public List<HfbankCustomerWithdrawalVO> queryHfbankWithdrawalList(String userName, String startTime, String endTime, Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<HfbankCustomerWithdrawalVO> resultList = bsPayOrdersMapper.selectHfbankWithdrawalList(userName, startTime,
				endTime, position, offset);
		return resultList.size() > 0 ? resultList : null;
	}

	@Override
	public Integer queryHfbankWithdrawalCount(String userName, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Integer count = bsPayOrdersMapper.selectHfbankWithdrawalCount(userName, startTime, endTime);
		return count;
	}

	@Override
	public Double queryHfbankWithdrawalSumAmount(String userName, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Double totalAmount = bsPayOrdersMapper.selectHfbankWithdrawalSumAmount( userName, startTime, endTime);
		return totalAmount;
	}

	@Override
	public Double queryHfbankWithdrawalSumFee(String userName, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Double totalAmount = bsPayOrdersMapper.selectHfbankWithdrawalSumFee( userName, startTime, endTime);
		return totalAmount;
	}

	@Override
	public List<HfbankCustomerWithdrawalVO> queryBaofooWithdrawalList(String userName, String startTime, String endTime,String transType, Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<HfbankCustomerWithdrawalVO> resultList = bsPayOrdersMapper.selectBaofooWithdrawalList(userName, startTime,
				endTime,transType, position, offset);
		return resultList.size() > 0 ? resultList : null;
	}

	@Override
	public Integer queryBaofooWithdrawalCount(String userName, String startTime, String endTime,String transType) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Integer count = bsPayOrdersMapper.selectBaofooWithdrawalCount(userName, startTime, endTime,transType);
		return count;
	}

	@Override
	public Double queryBaofooWithdrawalSumAmount(String userName, String startTime, String endTime,String transType) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Double totalAmount = bsPayOrdersMapper.selectBaofooWithdrawalSumAmount( userName, startTime, endTime,transType);
		return totalAmount;
	}

	@Override
	public Double queryBaofooWithdrawalSumFee(String userName, String startTime, String endTime,String transType) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Double totalAmount = bsPayOrdersMapper.selectBaofooWithdrawalSumFee( userName, startTime, endTime,transType);
		return totalAmount;
	}
	@Override
	public List<PayFinanceStatisticsVO> payFinanceYunZSDStatistics(String userType, String userName,
																String mobile, String startTime, String endTime,
																String partnerCode, String partnerBusinessFlag,
																Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<PayFinanceStatisticsVO> result = bsPayOrdersMapper.payFinanceYunZSDStatistics(userType, mobile, userName, startTime,
				endTime, partnerCode, partnerBusinessFlag, position, offset);
		return result;
	}


	@Override
	public Integer payFinanceYunZSDStatisticsCount(String userType, String userName, String mobile,
												String startTime, String endTime, String partnerCode, String partnerBusinessFlag) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Integer count = bsPayOrdersMapper.payFinanceYunZSDStatisticsCount(userType, mobile, userName, startTime, endTime, partnerCode, partnerBusinessFlag);
		return count;
	}

	@Override
	public Double payFinanceYunZSDTotalAmount(String userType, String userName, String mobile, String startTime,
											  String endTime, String partnerCode, String partnerBusinessFlag) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Double totalAmount = bsPayOrdersMapper.payFinanceYunZSDTotalAmount(userType, mobile, userName, startTime, endTime, partnerCode, partnerBusinessFlag);
		return totalAmount;
	}

	@Override
	public Integer countLoanServiceFee(String userName, String mobile, String partnerCode, String startTime,
			String endTime, String partnerBusinessFlag) {
		Integer count = lnDepositionRepayScheduleDetailMapper.countLoanServiceFee(userName, mobile, partnerCode,
				startTime, endTime, partnerBusinessFlag);
		return count;
	}

	@Override
	public List<LnLoanServiceFeeVO> selectLoanServiceFeeList(String userName, String mobile, String partnerCode,
			String startTime, String endTime, String partnerBusinessFlag, Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		// 校验参数	
		return lnDepositionRepayScheduleDetailMapper.selectLoanServiceFeeList(userName, mobile, partnerCode, 
				startTime, endTime, partnerBusinessFlag, position, offset);
	}

	@Override
	public LnLoanServiceFeeVO sumLnLoanServiceFee(String userName, String mobile, String partnerCode, String startTime,
			String endTime, String partnerBusinessFlag) {
		return lnDepositionRepayScheduleDetailMapper.sumLoanServiceFee(userName, mobile, partnerCode,
				startTime, endTime, partnerBusinessFlag);
	}
	
}
