package com.pinting.business.service.manage.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pinting.business.dao.*;
import com.pinting.business.model.vo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.model.BsPaymentChannel;
import com.pinting.business.model.BsPaymentChannelExample;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.LnCompensateDetail;
import com.pinting.business.model.LnPayOrders;
import com.pinting.business.model.LnPayOrdersExample;
import com.pinting.business.model.LnRepaySchedule;
import com.pinting.business.dao.BsRedPacketInfoMapper;
import com.pinting.business.service.manage.MFinanceStatisticsService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;

@Service
public class MFinanceStatisticsServiceImpl implements MFinanceStatisticsService {
	
	@Autowired
	private BsPayOrdersMapper orderMapper;
	@Autowired
	private BsRedPacketInfoMapper bsRedPacketInfoMapper;
	@Autowired
	private LnRepayScheduleMapper lnRepayScheduleMapper;
	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
	@Autowired
	private LnLoanMapper lnLoanMapper;
	@Autowired
	private BsDepositionReturnMapper bsDepositionReturnMapper;
	@Autowired
	private Bs19payCheckErrorMapper bs19payCheckErrorMapper;
	@Autowired
	private BsSysDailyCheckGachaMapper bsSysDailyCheckGachaMapper;
	@Autowired
	private LnPayOrdersMapper lnPayOrdersMapper;
	@Autowired
	private LnCompensateDetailMapper lnCompensateDetailMapper;
	@Autowired
    private BsPaymentChannelMapper paymentChannelMapper;

	@Override
	public List<UserRechanageStatisticsVO> userRechangeStatistics(String userName, String mobile, String startTime,
			String endTime, Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<UserRechanageStatisticsVO> result = orderMapper.userRechangeStatistics(mobile, userName, startTime,
				endTime,position,offset);
		return result;
	}

	@Override
	public Integer userRechangeStatisticsCount(String userName, String mobile, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Integer count = orderMapper.userRechangeStatisticsCount(mobile, userName, startTime, endTime);
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
		Double totalAmount = orderMapper.userTotalRechangeAmountStatistics(mobile, userName, startTime, endTime);
		return totalAmount;
	}


	@Override
	public List<CorpusBuyStatisticsVO> corpusBuyStatistics(String userType,String userName,
			String mobile, String startTime, String endTime, Integer page,
			Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		
		BsSysConfig bsSysConfig =   bsSysConfigMapper.selectByPrimaryKey("SUPER_FINANCE_USER_ID");
		String userIdString  = bsSysConfig.getConfValue();
		String[] userIds = userIdString.split(",");
		
		List<CorpusBuyStatisticsVO> result = orderMapper.corpusBuyStatistics(userType,mobile, userName, startTime,
				endTime,userIds,position,offset);
		return result;
	}

	@Override
	public Integer corpusBuyStatisticsCount(String userType,String userName, String mobile,
			String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		
		BsSysConfig bsSysConfig =   bsSysConfigMapper.selectByPrimaryKey("SUPER_FINANCE_USER_ID");
		String userIdString  = bsSysConfig.getConfValue();
		String[] userIds = userIdString.split(",");
		
		Integer count = orderMapper.corpusBuyStatisticsCount(userType,mobile, userName, startTime, endTime,userIds);
		return count;
	}


	@Override
	public CorpusBuyStatisticsVO corpusBuyTotalAmount(String userType,String userName,
			String mobile, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		
		BsSysConfig bsSysConfig =   bsSysConfigMapper.selectByPrimaryKey("SUPER_FINANCE_USER_ID");
		String userIdString  = bsSysConfig.getConfValue();
		String[] userIds = userIdString.split(",");
		
		CorpusBuyStatisticsVO totalAmount = orderMapper.corpusBuyTotalAmount(userType,mobile, userName, startTime, endTime,userIds);
		return totalAmount;
	}


    
	@Override
	public List<PayFinanceStatisticsVO> payFinanceStatistics(String userName,
			String mobile, String startTime, String endTime, Integer page,
			Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<PayFinanceStatisticsVO> result = orderMapper.payFinanceStatistics(mobile, userName, startTime,
				endTime,position,offset);
		return result;
	}

	@Override
	public Integer payFinanceStatisticsCount(String userName, String mobile,
			String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Integer count = orderMapper.payFinanceStatisticsCount(mobile, userName, startTime, endTime);
		return count;
	}
	
	@Override
	public PayFinanceStatisticsVO payFinanceStatisticsTotalAmount(
			String userName, String mobile, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		PayFinanceStatisticsVO vo = orderMapper.payFinanceStatisticsTotalAmount(mobile, userName, startTime, endTime);
		return vo;
	}

	@Override
	public List<CalculateRewardsStatisticsVO> calculateRewardsStatistics(
			String userName, String mobile, String startTime, String endTime,
			Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<CalculateRewardsStatisticsVO> result = orderMapper.calculateRewardsStatistics(mobile, userName, startTime,
				endTime,position,offset);
		return result;
	}

	@Override
	public Integer calculateRewardsStatisticsCount(String userName,
			String mobile, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Integer count = orderMapper.calculateRewardsStatisticsCount(mobile, userName, startTime, endTime);
		return count;
	}
	
	
	@Override
	public Double calculateRewardsStatisticsTotalAmount(String userName,
			String mobile, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Double totalAmount = orderMapper.calculateRewardsStatisticsTotalAmount(mobile, userName, startTime, endTime);
		return totalAmount;
	}

	
	
    @Override
    public List<UserDrawBonusStatisticsVO> userDrawBonusStatistics(String customerName,
                                                                   String mobile, String startTime,
                                                                   String endTime, Integer page,
                                                                   Integer offset) {
        Integer position = (page - 1) * offset;
        if(StringUtil.isNotBlank(startTime)) {
            startTime += " 00:00:00";
        }
        if(StringUtil.isNotBlank(endTime)) {
            endTime += " 23:59:59";
        }
        List<UserDrawBonusStatisticsVO> vos = orderMapper.userDrawBonusStatistics(customerName, startTime, endTime, position, offset);
        return vos;
    }

    @Override
    public Integer userDrawBonusStatisticsCount(String customerName, String mobile,
                                                String startTime, String endTime) {
        if(StringUtil.isNotBlank(startTime)) {
            startTime += " 00:00:00";
        }
        if(StringUtil.isNotBlank(endTime)) {
            endTime += " 23:59:59";
        }
        int count = orderMapper.userDrawBonusStatisticsCount(customerName, startTime, endTime);
        return count;
    }
    
    
	@Override
	public Double userDrawBonusStatisticsTotalAmount(String customerName,
			String mobile, String startTime, String endTime) {
        if(StringUtil.isNotBlank(startTime)) {
            startTime += " 00:00:00";
        }
        if(StringUtil.isNotBlank(endTime)) {
            endTime += " 23:59:59";
        }
        Double totalAmount = orderMapper.userDrawBonusStatisticsTotalAmount(customerName, startTime, endTime);
        return totalAmount;
	}

	
    @Override
    public List<UserRedPublishStatisticsVO> userRedPacketPublishStatistics(String customerName,
                                                                           String mobile,
                                                                           String startTime,
                                                                           String endTime,
                                                                           Integer page,
                                                                           Integer offset) {
        Integer position = (page - 1) * offset;
        if(StringUtil.isNotBlank(startTime)) {
            startTime += " 00:00:00";
        }
        if(StringUtil.isNotBlank(endTime)) {
            endTime += " 23:59:59";
        }
        List<UserRedPublishStatisticsVO> result = bsRedPacketInfoMapper.userRedPacketPublishStatistics(customerName, mobile, startTime, endTime, position, offset);
        return result;
    }

    @Override
    public Integer userRedPacketPublishStatisticsCount(String customerName, String mobile,
                                                       String startTime, String endTime) {
        if(StringUtil.isNotBlank(startTime)) {
            startTime += " 00:00:00";
        }
        if(StringUtil.isNotBlank(endTime)) {
            endTime += " 23:59:59";
        }
        Integer count = bsRedPacketInfoMapper.userRedPacketPublishStatisticsCount(customerName, mobile, startTime, endTime);
        return count;
    }


	@Override
	public Double userRedPacketPublishStatisticsTotalAmount(
			String customerName, String mobile, String startTime, String endTime) {
        if(StringUtil.isNotBlank(startTime)) {
            startTime += " 00:00:00";
        }
        if(StringUtil.isNotBlank(endTime)) {
            endTime += " 23:59:59";
        }
        Double  totalAmount = bsRedPacketInfoMapper.userRedPacketPublishStatisticsTotalAmount(customerName, mobile, startTime, endTime);
        return totalAmount;
	}
    
	@Override
	public List<GrantRewardsStatisticsVO> grantRewardsStatistics(
			String userName, String mobile, String startTime, String endTime, String type,
			Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<GrantRewardsStatisticsVO> result = orderMapper.grantRewardsStatistics(mobile, userName, startTime,
				endTime,type,position,offset);
		return result;
	}

	@Override
	public Integer grantRewardsStatisticsCount(String userName, String mobile,
			String startTime, String endTime, String type) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Integer count = orderMapper.grantRewardsStatisticsCount(mobile, userName, startTime, endTime,type);
		return count;
	}

	@Override
	public Double grantRewardsStatisticsTotalAmount(String userName,
			String mobile, String startTime, String endTime, String type) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Double totalAmount = orderMapper.grantRewardsStatisticsTotalAmount(mobile, userName, startTime, endTime,type);
		return totalAmount;
	}

	
    @Override
    public List<UserGrantInterestStatisticsVO> userGrantInterestStatistics(String customerName,
                                                                           String startTime,
                                                                           String endTime,
                                                                           String propertySymbol,
                                                                           Integer page,
                                                                           Integer offset) {
        Integer position = (page - 1) * offset;
        if(StringUtil.isNotBlank(startTime)) {
            startTime += " 00:00:00";
        }
        if(StringUtil.isNotBlank(endTime)) {
            endTime += " 23:59:59";
        }
        List<UserGrantInterestStatisticsVO> vos = orderMapper.userGrantInterestStatistics(customerName, startTime, endTime,propertySymbol, position, offset);
        return vos;
    }

    @Override
    public Integer userGrantInterestStatisticsCount(String customerName, String startTime,
                                                    String endTime,String propertySymbol) {
        if(StringUtil.isNotBlank(startTime)) {
            startTime += " 00:00:00";
        }
        if(StringUtil.isNotBlank(endTime)) {
            endTime += " 23:59:59";
        }
        Integer count = orderMapper.userGrantInterestStatisticsCount(customerName, startTime, endTime,propertySymbol);
        return count;
    }
    
	@Override
	public UserGrantInterestStatisticsVO userGrantInterestStatisticsTotalAmount(
			String customerName, String startTime, String endTime,String propertySymbol) {
        if(StringUtil.isNotBlank(startTime)) {
            startTime += " 00:00:00";
        }
        if(StringUtil.isNotBlank(endTime)) {
            endTime += " 23:59:59";
        }
        UserGrantInterestStatisticsVO vo = orderMapper.userGrantInterestStatisticsTotalAmount(customerName, startTime, endTime,propertySymbol);
        return vo;
	}


	@Override
	public List<BalanceFinanceStatisticsVO> balanceFinanceStatistics(String partnerCode,
			String userName, String mobile, String note, String startTime, String endTime,
			Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<BalanceFinanceStatisticsVO> result = new ArrayList<BalanceFinanceStatisticsVO>();
		if (Constants.PROPERTY_SYMBOL_YUN_DAI.equals(partnerCode)) {
			result = orderMapper.balanceFinanceStatistics(mobile, userName, note, startTime,
					endTime,position,offset);
		}else if (Constants.PROPERTY_SYMBOL_ZAN.equals(partnerCode)) {
			result = orderMapper.balanceFinanceStatisticsZan(mobile, userName, note, startTime,
					endTime,position,offset);
		}
		
		List<BalanceFinanceStatisticsVO> resultList = new ArrayList<BalanceFinanceStatisticsVO>();
		Double totalBalance4Zan = 0d; //初始化赞分期借款列表的总本金
		Double totalFinanceInterest4Zan = 0d; //初始化赞分期借款列表的应付利息
		HashMap<String, Double> financeInterestMap = new HashMap<String, Double>(); //存同笔借款，同期的利息和
		//循环列表，重新计算赞分期先关的本金，利息，本息和
		for (int i = 0; i < result.size(); i++) {
			BalanceFinanceStatisticsVO thisVo = result.get(i);
			/**
			 * 1、判断是否是赞分期
			 * 2、结算本金：每个人的误差计入最后一期，因为每个人每笔债权都有一个投资本金----->serialId期次  ==period借款期数
			 * 3、融资客户应付利息：每期的误差计入比例最小部分（每期应付总利息=借款总额*结算年利率*（投资月数+1）/（投资月数*24）；）
			 * 		3.1、每期计算差额
			 * 		3.2、最后一期的每期应付总利息由总利息减之前的和来获得
			 */
			
			if("1.3".equals(thisVo.getPropertyCode())){
				
				if(thisVo.getSerialId() == thisVo.getPeriod() && thisVo.getSerialId()>0){
					//结算本金：每个人的误差计入最后一期
					Double sumPrincipalAmount = thisVo.getTotalAmount();
					Double thisPrincipalAmount = MoneyUtil.subtract(sumPrincipalAmount, MoneyUtil.multiply(thisVo.getBalance(),(thisVo.getPeriod()-1)).doubleValue()).doubleValue();
					thisVo.setBalance(thisPrincipalAmount);
				}
				if(StringUtils.isBlank(userName) && StringUtils.isBlank(mobile)){
					String key = thisVo.getLoanId().toString()+"_"+thisVo.getSerialId().toString();
					Double beforeFinanceInterest = financeInterestMap.get(key)==null?0d: financeInterestMap.get(key); //之前的同笔借款，同期的利息和
					
					//融资客户应付利息：每期的误差计入比例最小部分,利息金额最小且非列表最后一期和列表最前一期
					if(thisVo.getIsMin() != null && thisVo.getIsMin() == 1){
						Double sumFinanceInterest = thisVo.getSumFinanceInterest(); // 每笔,每期总应付利息
						if(thisVo.getSerialId() == thisVo.getPeriod() && thisVo.getSerialId()>0){
							Double sumFinanceInterestAll = thisVo.getSumFinanceInterestAll(); //每笔借款总应付利息
							sumFinanceInterest = MoneyUtil.subtract(sumFinanceInterestAll, MoneyUtil.multiply(sumFinanceInterest,(thisVo.getPeriod()-1)).doubleValue()).doubleValue();
						}
						Double thisFinanceInterest = MoneyUtil.subtract(sumFinanceInterest,beforeFinanceInterest).doubleValue();
						thisVo.setFinanceInterest(thisFinanceInterest);
					}
					financeInterestMap.put(key, MoneyUtil.add(beforeFinanceInterest,thisVo.getFinanceInterest()).doubleValue());//同笔借款，同期的利息和存储
				}
				
				totalBalance4Zan = MoneyUtil.add(totalBalance4Zan, thisVo.getBalance()).doubleValue();
				totalFinanceInterest4Zan = MoneyUtil.add(totalFinanceInterest4Zan, thisVo.getFinanceInterest()).doubleValue();
			}
			
			//最后一笔，加入赞分期借款列表的总本金 和总利息
			if(i == result.size()-1){
				thisVo.setTotalBalance(totalBalance4Zan);
				thisVo.setTotalFinanceInterest(totalFinanceInterest4Zan);
			}
			thisVo.setFinanceTotalAmount(MoneyUtil.add(thisVo.getFinanceInterest(), thisVo.getBalance()).doubleValue());
			resultList.add(thisVo);
		}


		return result;
	}

	@Override
	public Integer balanceFinanceStatisticsCount(String partnerCode,String userName,
			String mobile, String note, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Integer count = 0;
		if (Constants.PROPERTY_SYMBOL_YUN_DAI.equals(partnerCode)) {
			count = orderMapper.balanceFinanceStatisticsCount(mobile, userName, note, startTime, endTime);
		}else if (Constants.PROPERTY_SYMBOL_ZAN.equals(partnerCode)) {
			count = orderMapper.balanceFinanceStatisticsZanCount(mobile, userName, note, startTime, endTime);
		}
		return count;
	}

	@Override
	public BalanceFinanceStatisticsVO balanceFinanceStatisticsTotalAmount(
			String partnerCode,String userName, String mobile, String note, String startTime, String endTime, Integer count) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		BalanceFinanceStatisticsVO vo = new BalanceFinanceStatisticsVO();
		if(StringUtil.isBlank(note)){
			if (Constants.PROPERTY_SYMBOL_YUN_DAI.equals(partnerCode)) {
				vo = orderMapper.balanceFinanceStatisticsTotalAmount(mobile, userName, startTime, endTime);
			}else if (Constants.PROPERTY_SYMBOL_ZAN.equals(partnerCode)) {
				vo = orderMapper.balanceFinanceStatisticsZanTotalAmount(mobile, userName, startTime, endTime);
			}
			
		}else{
			vo.setTotalBalance(0d);
			vo.setTotalFinanceInterest(0d);
		}
		//赞分期相关部分：
		List<BalanceFinanceStatisticsVO> list = new ArrayList<BalanceFinanceStatisticsVO>();
		list = balanceFinanceStatistics(partnerCode,userName, mobile, note, startTime, endTime,1, count);
		if(CollectionUtils.isNotEmpty(list)){
			vo.setTotalBalance(MoneyUtil.add(vo.getTotalBalance()==null?0d:vo.getTotalBalance(), list.get(list.size()-1).getTotalBalance()).doubleValue());
			vo.setTotalFinanceInterest(MoneyUtil.add(vo.getTotalFinanceInterest()==null?0d:vo.getTotalFinanceInterest(), list.get(list.size()-1).getTotalFinanceInterest()).doubleValue());
		}
		return vo;
	}

	@Override
	public List<FinanceInterestOffline7DaiVO> financeInterestOffline7Dai(
			String userName, String mobile, String startTime, String endTime,
			Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<FinanceInterestOffline7DaiVO> result = orderMapper.financeInterestOffline7Dai(mobile, userName, startTime,
				endTime,position,offset);
		return result;
	}

	@Override
	public Integer financeInterestOffline7DaiCount(String userName,
			String mobile, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Integer count = orderMapper.financeInterestOffline7DaiCount(mobile, userName, startTime, endTime);
		return count;
	}

	@Override
	public FinanceInterestOffline7DaiVO financeInterestOffline7DaiTotalAmount(
			String userName, String mobile, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		FinanceInterestOffline7DaiVO vo = orderMapper.financeInterestOffline7DaiTotalAmount(mobile, userName, startTime, endTime);
		return vo;
	}

	@Override
	public List<RevenueCollectionRepayVO> revenueCollectionRepayList(
			String userName, String mobile, String type, String startTime,
			String endTime, Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<RevenueCollectionRepayVO> result = orderMapper.revenueCollectionRepayList(userName,mobile,type,startTime,endTime,position,offset);
		return result;
	}
	@Override
	public MDepositDsDfResRecordVO mDepositDsDfRecord(
			MDepositDsDfQueryVO queryVo) {
		MDepositDsDfResRecordVO returnVo = new MDepositDsDfResRecordVO();
		int count = lnRepayScheduleMapper.countDepositDsDfList(queryVo);
		returnVo.setCount(count);
		if(count >0){
			List<MDepositDsDfResVO> list = lnRepayScheduleMapper.selectDepositDsDfList(queryVo);
			returnVo.setList(list);
		}
		returnVo.setSumAmount(lnRepayScheduleMapper.sumAmount4DsDf(queryVo));
		return returnVo;
	}

	@Override
	public Integer revenueCollectionRepayCount(String userName, String mobile,
			String type, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Integer count = orderMapper.revenueCollectionRepayCount(userName, mobile, type, startTime, endTime);
		return count;
	}

	@Override
	public Double revenueCollectionRepaySum(String userName, String mobile,
			String type, String startTime, String endTime,String revenueType) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Double amount = orderMapper.revenueCollectionRepaySum(userName, mobile, type, startTime, endTime, revenueType);
		return amount;
	}

	@Override
	public List<RevenueCollectionRepayVO> revenueCollectionRepayYunZSDList(
			String userName, String mobile, String type, String startTime,
			String endTime, String partnerCode, Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<RevenueCollectionRepayVO> result = orderMapper.revenueCollectionRepayYunZSDList(userName,mobile,type,startTime,endTime,partnerCode,position,offset);
		return result;
	}

	@Override
	public Integer revenueCollectionRepayYunZSDCount(String userName,
			String mobile, String type, String startTime, String endTime, String partnerCode) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Integer count = orderMapper.revenueCollectionRepayYunZSDCount(userName, mobile, type, startTime, endTime, partnerCode);
		return count;
	}

	@Override
	public Double revenueCollectionRepayYunZSDSum(String userName, String mobile,
			String type, String startTime, String endTime, String revenueType, String partnerCode) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Double amount = orderMapper.revenueCollectionRepayYunZSDSum(userName, mobile, type, startTime, endTime, revenueType, partnerCode);
		return amount;
	}

	@Override
	public Integer influxCollectionRepayCount(String userName, String mobile,
			String type, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Integer count = orderMapper.influxCollectionRepayCount(userName, mobile, type, startTime, endTime);
		return count;
	}

	@Override
	public Double influxCollectionRepaySum(String userName, String mobile,
			String type, String startTime, String endTime, String influxType) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Double amount = orderMapper.influxCollectionRepaySum(userName, mobile, type, startTime, endTime, influxType);
		return amount;
	}

	@Override
	public List<InfluxCollectionRepayVO> influxCollectionRepayList(
			String userName, String mobile, String type, String startTime,
			String endTime, Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<InfluxCollectionRepayVO> result = orderMapper.influxCollectionRepayList(userName, mobile, type, startTime, endTime, position, offset);
		return result;
	}

	@Override
	public List<Cash30YunVO> cash30Yun(Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		Date cashDate = DateUtil.addDays(new Date(), 30);
		String cashDateString = DateUtil.format(cashDate);
		List<Cash30YunVO> result = orderMapper.cash30Yun(cashDateString);
		return result;
	}

	@Override
	public Double debtVipYun() {
		return orderMapper.debtVipYun();
	}

	@Override
	public Double standVipYun() {
		return orderMapper.standVipYun();
	}

	@Override
	public MDepositDsDfResRecordVO depositDsDfRecord4ZSD(
			MDepositDsDfQueryVO queryVo) {
		MDepositDsDfResRecordVO returnVo = new MDepositDsDfResRecordVO();
		int count = lnRepayScheduleMapper.countDepositDsDfList4ZSD(queryVo);
		returnVo.setCount(count);
		if(count >0){
			List<MDepositDsDfResVO> list = lnRepayScheduleMapper.selectDepositDsDfList4ZSD(queryVo);
			returnVo.setList(list);
		}
		returnVo.setSumAmount(lnRepayScheduleMapper.sumAmount4DsDf4ZSD(queryVo));
		return returnVo;
	}

	@Override
	public List<HeadFeeCollectPayVO> headFeeCollectPayList(String partnerCode, String userName, String mobile, String type, String startTime, String endTime, Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<HeadFeeCollectPayVO> result = lnLoanMapper.headFeeCollectPayList(partnerCode, userName, mobile, type, startTime, endTime, position, offset);
		return result;
	}

    @Override
    public Integer headFeeCollectPayCount(String partnerCode, String userName, String mobile, String type, String startTime, String endTime) {
        if(StringUtil.isNotBlank(startTime)) {
            startTime += " 00:00:00";
        }
        if(StringUtil.isNotBlank(endTime)) {
            endTime += " 23:59:59";
        }
        Integer count = lnLoanMapper.headFeeCollectPayCount(partnerCode, userName, mobile, type, startTime, endTime);
        return count;
    }

	@Override
	public List<DepBalanceFinanceStatisticsVO> depBalanceFinanceStatisticsCount(String userName,
			String mobile, String note, String custType,String partner_code, String partnerBusinessFlag, String startTime,
			String endTime) {
		List<DepBalanceFinanceStatisticsVO> countList = orderMapper.depBalanceFinanceStatisticsCount(mobile, userName,
				note, custType, partner_code, partnerBusinessFlag, startTime, endTime);
		return countList;
	}

	@Override
	public List<DepBalanceFinanceStatisticsVO> depBalanceFinanceStatistics(
			String userName, String mobile, String note, String custType, String partner_code, String partnerBusinessFlag,
			String startTime, String endTime, Integer page, Integer offset) {
		
		Integer position = (page - 1) * offset;
		List<DepBalanceFinanceStatisticsVO> result = orderMapper.depBalanceFinanceStatistics(mobile, userName,
				note, custType, partner_code, partnerBusinessFlag, startTime,
				endTime,position,offset);
		return result;
	}

	@Override
	public List<CorpusBuyStatisticsVO> queryLendingMatchForZan(String userType, String userName, String mobile, String startTime, String endTime, Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}

		BsSysConfig bsSysConfig = bsSysConfigMapper.selectByPrimaryKey("SUPER_FINANCE_USER_ID");
		String userIdString  = bsSysConfig.getConfValue();
		String[] userIds = userIdString.split(",");

		List<CorpusBuyStatisticsVO> result = orderMapper.selectLendingMatchForZan(userType,mobile, userName, startTime,
				endTime,userIds,position,offset);
		return result;
	}

	@Override
	public Integer queryLendingMatchForZanCount(String userType, String userName, String mobile, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}

		BsSysConfig bsSysConfig = bsSysConfigMapper.selectByPrimaryKey("SUPER_FINANCE_USER_ID");
		String userIdString  = bsSysConfig.getConfValue();
		String[] userIds = userIdString.split(",");

		Integer count = orderMapper.selectLendingMatchForZanCount(userType,mobile, userName, startTime, endTime,userIds);
		return count;
	}

	@Override
	public CorpusBuyStatisticsVO queryLendingMatchForZanTotalAmount(String userType, String userName, String mobile, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}

		BsSysConfig bsSysConfig = bsSysConfigMapper.selectByPrimaryKey("SUPER_FINANCE_USER_ID");
		String userIdString  = bsSysConfig.getConfValue();
		String[] userIds = userIdString.split(",");

		CorpusBuyStatisticsVO totalAmount = orderMapper.selectLendingMatchForZanTotalAmount(userType,mobile, userName, startTime, endTime,userIds);
		return totalAmount;
	}

	@Override
	public List<ReturnFeeToInvestorVO> returnFeeToInvestor(String partnerCode, String userName, String mobile, String startTime, String endTime, Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<ReturnFeeToInvestorVO> result = orderMapper.returnFeeToInvestor(partnerCode, mobile, userName, startTime,
				endTime,position,offset);
		return result;
	}

	@Override
	public Integer returnFeeToInvestorCount(String partnerCode, String userName, String mobile, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Integer count = orderMapper.returnFeeToInvestorCount(partnerCode, mobile, userName, startTime,
				endTime);
		return count;
	}

	@Override
	public Double returnFeeToInvestorTotalAmount(String partnerCode, String userName, String mobile, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Double amount = orderMapper.returnFeeToInvestorTotalAmount(partnerCode, mobile, userName, startTime,
				endTime);
		return amount;
	}

	@Override
	public List<PayFinanceStatisticsVO> payFinanceStatisticsForZan(String userName, String mobile, String startTime, String endTime, Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<PayFinanceStatisticsVO> result = orderMapper.payFinanceStatisticsForZan(mobile, userName, startTime,
				endTime,position,offset);
		return result;
	}

	@Override
	public Integer payFinanceStatisticsCountForZan(String userName, String mobile, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Integer count = orderMapper.payFinanceStatisticsCountForZan(mobile, userName, startTime, endTime);
		return count;
	}

	@Override
	public PayFinanceStatisticsVO payFinanceStatisticsTotalAmountForZan(String userName, String mobile, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		PayFinanceStatisticsVO vo = orderMapper.payFinanceStatisticsTotalAmountForZan(mobile, userName, startTime, endTime);
		return vo;
	}




	@Override
	public List<BalanceFinanceStatisticsVO> depZanBalanceFinanceStatistics(
			String userName, String mobile, String note, String startTime, String endTime,
			Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<BalanceFinanceStatisticsVO> result = orderMapper.depZanBalanceFinanceStatistics(mobile, userName, note, startTime,
				endTime,position,offset);
		List<BalanceFinanceStatisticsVO> resultList = new ArrayList<BalanceFinanceStatisticsVO>();
		Double totalBalance4Zan = 0d; //初始化赞分期借款列表的总本金
		Double totalFinanceInterest4Zan = 0d; //初始化赞分期借款列表的应付利息
		HashMap<String, Double> financeInterestMap = new HashMap<String, Double>(); //存同笔借款，同期的利息和
		//循环列表，重新计算赞分期先关的本金，利息，本息和
		for (int i = 0; i < result.size(); i++) {
			BalanceFinanceStatisticsVO thisVo = result.get(i);
			
			
			/**
			 * 1、判断是否是赞分期
			 * 2、结算本金：每个人的误差计入最后一期，因为每个人每笔债权都有一个投资本金----->serialId期次  ==period借款期数
			 * 3、融资客户应付利息：每期的误差计入比例最小部分（每期应付总利息=借款总额*结算年利率*（投资月数+1）/（投资月数*24）；）
			 * 		3.1、每期计算差额
			 * 		3.2、最后一期的每期应付总利息由总利息减之前的和来获得
			 */

			if("1.3".equals(thisVo.getPropertyCode())){

				if(thisVo.getSerialId() == thisVo.getPeriod() && thisVo.getSerialId()>0){
					//结算本金：每个人的误差计入最后一期
					Double sumPrincipalAmount = thisVo.getTotalAmount();
					Double thisPrincipalAmount = MoneyUtil.subtract(sumPrincipalAmount, MoneyUtil.multiply(thisVo.getBalance(),(thisVo.getPeriod()-1)).doubleValue()).doubleValue();
					thisVo.setBalance(thisPrincipalAmount);
				}
				if(StringUtils.isBlank(userName) && StringUtils.isBlank(mobile)){
					String key = thisVo.getLoanId().toString()+"_"+thisVo.getSerialId().toString();
					Double beforeFinanceInterest = financeInterestMap.get(key)==null?0d: financeInterestMap.get(key); //之前的同笔借款，同期的利息和

					//融资客户应付利息：每期的误差计入比例最小部分,利息金额最小且非列表最后一期和列表最前一期
					if(thisVo.getIsMin() != null && thisVo.getIsMin() == 1){
						BalanceFinanceStatisticsVO nextVo = null;
						if (i < result.size()-1) {
							nextVo = result.get(i+1);
						}
						if (nextVo != null && nextVo.getIsMin()!= null && nextVo.getIsMin()== 1  && nextVo.getLoanId().equals(thisVo.getLoanId()) && nextVo.getSerialId().equals(thisVo.getSerialId())) {
							
						}else {
							Double sumFinanceInterest = thisVo.getSumFinanceInterest(); // 每笔,每期总应付利息
							if(thisVo.getSerialId() == thisVo.getPeriod() && thisVo.getSerialId()>0){
								Double sumFinanceInterestAll = thisVo.getSumFinanceInterestAll(); //每笔借款总应付利息
								sumFinanceInterest = MoneyUtil.subtract(sumFinanceInterestAll, MoneyUtil.multiply(sumFinanceInterest,(thisVo.getPeriod()-1)).doubleValue()).doubleValue();
							}
							Double thisFinanceInterest = MoneyUtil.subtract(sumFinanceInterest,beforeFinanceInterest).doubleValue();
							thisVo.setFinanceInterest(thisFinanceInterest);
						}

					}
					financeInterestMap.put(key, MoneyUtil.add(beforeFinanceInterest,thisVo.getFinanceInterest()).doubleValue());//同笔借款，同期的利息和存储
				}

				totalBalance4Zan = MoneyUtil.add(totalBalance4Zan, thisVo.getBalance()).doubleValue();
				totalFinanceInterest4Zan = MoneyUtil.add(totalFinanceInterest4Zan, thisVo.getFinanceInterest()).doubleValue();
			}

			//最后一笔，加入赞分期借款列表的总本金 和总利息
			if(i == result.size()-1){
				thisVo.setTotalBalance(totalBalance4Zan);
				thisVo.setTotalFinanceInterest(totalFinanceInterest4Zan);
			}
			thisVo.setFinanceTotalAmount(MoneyUtil.add(thisVo.getFinanceInterest(), thisVo.getBalance()).doubleValue());
			resultList.add(thisVo);
		}


		return result;
	}

	@Override
	public Integer depZanBalanceFinanceStatisticsCount(String userName,
			String mobile, String note, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Integer count = orderMapper.depZanBalanceFinanceStatisticsCount(mobile, userName, note, startTime, endTime);
		return count;
	}

	@Override
	public BalanceFinanceStatisticsVO depZanBalanceFinanceStatisticsTotalAmount(
			String userName, String mobile, String note, String startTime, String endTime, Integer count) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		BalanceFinanceStatisticsVO vo = new BalanceFinanceStatisticsVO();
		vo.setTotalBalance(0d);
		vo.setTotalFinanceInterest(0d);
		//赞分期相关部分：
		List<BalanceFinanceStatisticsVO> list = new ArrayList<BalanceFinanceStatisticsVO>();
		list = depZanBalanceFinanceStatistics(userName, mobile, note, startTime, endTime,1, count);
		if(CollectionUtils.isNotEmpty(list)){
			vo.setTotalBalance(MoneyUtil.add(vo.getTotalBalance()==null?0d:vo.getTotalBalance(), list.get(list.size()-1).getTotalBalance()).doubleValue());
			vo.setTotalFinanceInterest(MoneyUtil.add(vo.getTotalFinanceInterest()==null?0d:vo.getTotalFinanceInterest(), list.get(list.size()-1).getTotalFinanceInterest()).doubleValue());
		}

		return vo;
	}

	@Override
	public List<InvestExpireVO> investExpire(String partnerCode, String type, String userName, String mobile, String startTime, String endTime, Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<InvestExpireVO> result = bsDepositionReturnMapper.investExpire(partnerCode, type, mobile, userName, startTime,
				endTime,position,offset);
		return result;
	}

	@Override
	public Integer investExpireCount(String partnerCode, String type, String userName, String mobile, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Integer count = bsDepositionReturnMapper.investExpireCount(partnerCode, type, mobile, userName, startTime,
				endTime);
		return count;
	}

	@Override
	public Double investExpireTotalAmount(String partnerCode, String type, String userName, String mobile, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		Double amount = bsDepositionReturnMapper.investExpireTotalAmount(partnerCode, type, mobile, userName, startTime,
				endTime);
		return amount;
	}

	@Override
	public Integer depBaoFooBalanceStatisticsCount(String startTime,
			String endTime) {
//		if(StringUtil.isNotBlank(startTime)) {
//			startTime += " 00:00:00";
//		}
//		if(StringUtil.isNotBlank(endTime)) {
//			endTime += " 23:59:59";
//		}
		int count = orderMapper.depBaoFooBalanceStatisticsCount(startTime, endTime);
		return count;
	}

	@Override
	public List<DepBaoFooBalanceStatisticsVO> depBaoFooBalanceStatistics(
			String startTime, String endTime, Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
//		if(StringUtil.isNotBlank(startTime)) {
//			startTime += " 00:00:00";
//		}
//		if(StringUtil.isNotBlank(endTime)) {
//			endTime += " 23:59:59";
//		}
		List<DepBaoFooBalanceStatisticsVO> result = orderMapper.depBaoFooBalanceStatistics(startTime,
				endTime,position,offset);
		return result;
	}

	@Override
	public HeadFeeCollectPayVO headFeeCollectPayTotalAmount(String partnerCode, String userName, String mobile, String type, String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		HeadFeeCollectPayVO result = lnLoanMapper.headFeeCollectPayTotalAmount(partnerCode, userName, mobile, type, startTime, endTime);
		return result;
	}

	@Override
	public List<BsPayCheckErrorVO> findCheckErrorList(String orderNo, String partnerCode, String businessType, String paymentChannelId, String channel,
			String startTime, String endTime, Integer page, Integer offset) {
		Integer position = (page - 1) * offset;
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		List<BsPayCheckErrorVO> result = bs19payCheckErrorMapper.findPayCheckErrorList(orderNo, partnerCode, businessType, paymentChannelId, channel,
				startTime, endTime, position, offset);
		return result;
	}

	@Override
	public int countCheckError(String orderNo, String partnerCode, String businessType, String paymentChannelId, String channel,
			String startTime, String endTime) {
		if(StringUtil.isNotBlank(startTime)) {
			startTime += " 00:00:00";
		}
		if(StringUtil.isNotBlank(endTime)) {
			endTime += " 23:59:59";
		}
		int count = bs19payCheckErrorMapper.countPayCheckError(orderNo, partnerCode, businessType, paymentChannelId, channel,
				startTime, endTime);
		return count;
	}

	@Override
	public List<BsSysDailyCheckGachaVO> findSysDailyCheckGachaList(String merchantNo, String checkDate) {
		List<BsSysDailyCheckGachaVO> result = new ArrayList<>();
		//宝付主通道
		BsPaymentChannelExample bsPaymentChannelExample = new BsPaymentChannelExample();
    	bsPaymentChannelExample.createCriteria().andTransTypeEqualTo("DK").andIsMainEqualTo(1);
        List<BsPaymentChannel> bsPaymentChannels = paymentChannelMapper.selectByExample(bsPaymentChannelExample);
        if (!CollectionUtils.isEmpty(bsPaymentChannels)) {
        	 List<BsSysDailyCheckGachaVO> mainResult = bsSysDailyCheckGachaMapper.findSysDailyCheckGachaList(bsPaymentChannels.get(0).getMerchantNo(), checkDate);
    		 result.addAll(mainResult);
        }
        
        //宝付辅助通道
        BsPaymentChannelExample paymentChannelExample = new BsPaymentChannelExample();
        paymentChannelExample.createCriteria().andTransTypeEqualTo("DK").andIsMainEqualTo(2);
        List<BsPaymentChannel> paymentChannels = paymentChannelMapper.selectByExample(paymentChannelExample);
        if (!CollectionUtils.isEmpty(paymentChannels)) {
            for (BsPaymentChannel bsPaymentChannel : paymentChannels) {
            	List<BsSysDailyCheckGachaVO> assistResult = bsSysDailyCheckGachaMapper.findAssistSysDailyCheckGachaList(bsPaymentChannel.getMerchantNo(), checkDate);
            	result.addAll(assistResult);
            }
		}
		return result;
	}

	@Override
	public String checkGachaErrorHandle(String handleType, String orderNo) {
		int count = 0;
		if (Constants.HANDLE_TYPE_DK_REFUND.equals(handleType)) {
			List<LnPayOrders> lnPayOrdersList = lnPayOrdersMapper.selectLnPayOrdersWithNoOffline("REPAY",
					Constants.CHANNEL_TRS_DK, orderNo);
			// 代扣异常 本地状态预下单，宝付成功，财务宝付退款给用户后，执行本地订单状态落地为失败
			if (!CollectionUtils.isEmpty(lnPayOrdersList)) {
				LnPayOrders record = new LnPayOrders();
				record.setId(lnPayOrdersList.get(0).getId());
				record.setStatus(Constants.ORDER_STATUS_FAIL);
				lnPayOrdersMapper.updateByPrimaryKeySelective(record);
				return ConstantUtil.BSRESCODE_SUCCESS;
			} 
		} else if (Constants.HANDLE_TYPE_MULTI_COMPENSATE.equals(handleType)) {
			List<LnCompensateDetail> list = lnCompensateDetailMapper.selectLnCompensateDetailList(Constants.ORDER_TRANS_CODE_INIT, orderNo);
			if (!CollectionUtils.isEmpty(list)) {
				for (LnCompensateDetail lnCompensateDetail : list) {
					// 代偿异常 本地账单状态已废弃/已代偿，资产方代偿多账，财务宝付退款给资产方后，执行本地代偿明细订单状态落地为失败
					LnRepaySchedule lnRepaySchedule = lnRepayScheduleMapper.selectRepayScheduleWithCompensateAndOrderNo(lnCompensateDetail.getPartnerRepayId(), 
							orderNo);
					if (lnRepaySchedule != null && (Constants.LN_REPAY_LATE_NOT.equals(lnRepaySchedule.getStatus()) ||
							Constants.LN_REPAY_CANCELLED.equals(lnRepaySchedule.getStatus()))) {					
						LnCompensateDetail record = new LnCompensateDetail();
						record.setId(lnCompensateDetail.getId());
						record.setStatus(Constants.ORDER_TRANS_CODE_FAIL);
						lnCompensateDetailMapper.updateByPrimaryKeySelective(record);
						count = count + 1;
					}
				}
				if (count >= 1) {					
					return ConstantUtil.BSRESCODE_SUCCESS;
				}
			} 
		}
		return ConstantUtil.BSRESCODE_FAIL;
	}

	@Override
	public List<BsSysDailyCheckGachaVO> findHfbankDailyCheckGachaList(String checkDate) {

		return bsSysDailyCheckGachaMapper.findHfbankDailyCheckGachaList(checkDate);
	}

}
