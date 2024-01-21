package com.pinting.business.service.site.impl;

import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.ResMsg_Invest_AccountBalance;
import com.pinting.business.hessian.site.message.ResMsg_Invest_InvestListQuery;
import com.pinting.business.hessian.site.message.ResMsg_Invest_InvestProportion;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsInvestProportionVO;
import com.pinting.business.model.vo.BsSubAccountVO;
import com.pinting.business.model.vo.WithdrawForecastVO;
import com.pinting.business.service.site.AssetsService;
import com.pinting.business.service.site.OrderService;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Author:      cyb
 * Date:        2016/8/27
 * Description: 我的资产以及相关内页服务
 */
@Service
public class AssetsServiceImpl implements AssetsService {

    private Logger logger = LoggerFactory.getLogger(AssetsServiceImpl.class);
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private BsPayOrdersMapper bsPayOrdersMapper;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private BsSysConfigMapper bsSysConfigMapper;
    @Autowired
    private BsWithdrawCheckMapper bsWithdrawCheckMapper;
    @Autowired
    private SubAccountService subAccountService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private BsHolidayMapper bsHolidayMapper;
    @Autowired
    private BsQuestionnaireMapper bsQuestionnaireMapper;

    @Override
    public ResMsg_Invest_AccountBalance accountBalance(String userId) {
        // 1、查询JSH可用余额
        // 2、查询提现处理中金额
        // 3、查询用户绑卡状态
        // 4、是否设置了交易密码
        // 5、存管户可用余额

        ResMsg_Invest_AccountBalance res = new ResMsg_Invest_AccountBalance();
        // 1、查询JSH可用余额
        BsSubAccount jshAccount = bsSubAccountMapper.selectJSHSubAccountByUserId(Integer.parseInt(userId));
        res.setAvailableBalance(MoneyUtil.multiply(jshAccount.getAvailableBalance(), 1).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        // 2、查询提现处理中金额
        BsPayOrdersExample bsPayOrdersExample = new BsPayOrdersExample();
        bsPayOrdersExample.createCriteria().andUserIdEqualTo(Integer.parseInt(userId)).andStatusEqualTo(Constants.ORDER_STATUS_PAYING);
        List<BsPayOrders> bsPayOrdersList = bsPayOrdersMapper.selectByExample(bsPayOrdersExample);
        if(!CollectionUtils.isEmpty(bsPayOrdersList)) {
            Double amount = 0d;
            for (BsPayOrders order : bsPayOrdersList) {
                amount = MoneyUtil.add(order.getAmount(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            res.setPayingWithdrawBalance(MoneyUtil.multiply(amount, 1).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        } else {
            res.setPayingWithdrawBalance("0");
        }
        // 3、查询用户绑卡状态
        BsUser bsUser = bsUserMapper.selectByPrimaryKey(Integer.parseInt(userId));
        res.setStatus(bsUser.getIsBindBank().equals(Constants.IS_BIND_BANK_YES) ? "YES" : "NO");
        // 4、是否设置了交易密码
        res.setHavePayPassword(StringUtils.isBlank(bsUser.getPayPassword())? "NO":"YES");
        // 5、存管户可用余额
        BsSubAccount depJsh = subAccountService.findDEPJSHSubAccountByUserId(Integer.parseInt(userId));
        if(null != depJsh) {
            depJsh.setAvailableBalance(depJsh.getAvailableBalance() == null ? 0d : depJsh.getAvailableBalance());
            res.setDepAvailableBalance(MoneyUtil.multiply(depJsh.getAvailableBalance(), 1).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        }
        return res;
    }

    @Override
    public ResMsg_Invest_InvestListQuery commissionPlanList(int userId, int pageNum, int numPerPage) {

        int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        // 查询赞分期产品
        List<BsSubAccountVO> voList = bsSubAccountMapper.selectCommissionPlanList(userId, start, numPerPage);
        // 统计
        int count = bsSubAccountMapper.countCommissionPlanList(userId);
        ResMsg_Invest_InvestListQuery res = new ResMsg_Invest_InvestListQuery();
        res.setValueList(BeanUtils.classToArrayList(voList));
        res.setTotal((int) Math.ceil((double) count / numPerPage));
       
        return res;
    }

    @Override
    public Map<String, Object> queryTopUpInstruction() {
        BsSysConfig rechangeLimitConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.RECHANGE_LIMIT);
        String rechangeLimit = rechangeLimitConfig.getConfValue();
        Map<String, Object> result = new HashMap<>();
        result.put("rechangeLimit", rechangeLimit);
        return result;
    }

    @Override
    public Map<String, Object> queryWithdrawInstrction() {
        BsSysConfig eachMonthWithdrawTimesConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.EACH_MONTH_WITHDRAW_TIMES);
        BsSysConfig withdrawCounterFeeConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.FINANCE_WITHDRAW_COUNTER_FEE);
        BsSysConfig withdrawLimitAmountConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.WITHDRAW_APPLY_LIMIT);
        String eachMonthWithdrawTimes = eachMonthWithdrawTimesConfig.getConfValue();
        String withdrawCounterFee = withdrawCounterFeeConfig.getConfValue();
        String withdrawLimitAmount = withdrawLimitAmountConfig.getConfValue();
        Map<String, Object> result = new HashMap<>();
        result.put("eachMonthWithdrawTimes", eachMonthWithdrawTimes);
        result.put("withdrawCounterFee", withdrawCounterFee);
        result.put("withdrawLimitAmount", withdrawLimitAmount);
        return result;
    }

	@Override
	public Integer queryFreeWithdrawTimesByUserId(Integer userId) {
        BsSysConfig eachMonthWithdrawTimesConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.EACH_MONTH_WITHDRAW_TIMES);
        Integer eachMonthWithdrawTimes = Integer.parseInt(eachMonthWithdrawTimesConfig.getConfValue());
        // 查询本月成功和处理中的提现订单的次数
        String applyTimeYYYYMM = DateUtil.formatDateTime(new Date(), "yyyy-MM");
        String startTime = applyTimeYYYYMM + "-01 00:00:00";
        Date startDate = DateUtil.parse(startTime, "yyyy-MM-dd HH:mm:ss");
        Date endDate = DateUtil.addMonths(startDate, 1);

        BsPayOrdersExample bsPayOrdersExample = new BsPayOrdersExample();
        bsPayOrdersExample.createCriteria().andUserIdEqualTo(userId)
                .andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS)
                .andTransTypeEqualTo(Constants.TRANS_BALANCE_WITHDRAW)
                .andUpdateTimeGreaterThanOrEqualTo(startDate).andUpdateTimeLessThan(endDate);

        BsPayOrdersExample payingExample = new BsPayOrdersExample();
        payingExample.createCriteria().andUserIdEqualTo(userId)
                .andStatusEqualTo(Constants.ORDER_STATUS_PAYING)
                .andTransTypeEqualTo(Constants.TRANS_BALANCE_WITHDRAW)
                .andUpdateTimeGreaterThanOrEqualTo(startDate).andUpdateTimeLessThan(endDate);

        List<String> statuses = new ArrayList<>();
        statuses.add(Constants.WITHDRAW_TO_CHECK);
        statuses.add(Constants.WITHDRAW_PASS_QUE);
        statuses.add(Constants.WITHDRAW_PASS_PROCESS);
        BsWithdrawCheckExample bsWithdrawCheckExample = new BsWithdrawCheckExample();
        bsWithdrawCheckExample.createCriteria()
                .andUserIdEqualTo(userId)
                .andStatusIn(statuses)
                .andCreateTimeGreaterThanOrEqualTo(startDate).andCreateTimeLessThan(endDate);
        int checkCount = bsWithdrawCheckMapper.countByExample(bsWithdrawCheckExample);
        int succCount = bsPayOrdersMapper.countByExample(bsPayOrdersExample);
        int payingCount = bsPayOrdersMapper.countByExample(payingExample);

        int historyWithdrawOrderCount = payingCount + succCount + checkCount;
        // 计算剩余可免费提现次数
        int leftTimes = (eachMonthWithdrawTimes - historyWithdrawOrderCount) >= 0 ? (eachMonthWithdrawTimes - historyWithdrawOrderCount) : 0;
		return leftTimes;
	}

	@Override
	public ResMsg_Invest_InvestProportion investProportionList(Integer userId) {
		ResMsg_Invest_InvestProportion res = new ResMsg_Invest_InvestProportion();
		List<BsInvestProportionVO> list = bsSubAccountMapper.selectInvestProportionList(userId);
		if(!CollectionUtils.isEmpty(list)) {
            if(list.size() <= 1) {
                BsInvestProportionVO vo1 = new BsInvestProportionVO();
                vo1.setInvestAmount(0d);
                vo1.setInvestNum(0);
                vo1.setProportionRate(0d);
                list.add(vo1);
            }
        } else {
            list = new ArrayList<>();
            BsInvestProportionVO vo1 = new BsInvestProportionVO();
            vo1.setInvestAmount(0d);
            vo1.setInvestNum(0);
            vo1.setProportionRate(0d);
            list.add(vo1);
            BsInvestProportionVO vo2 = new BsInvestProportionVO();
            vo2.setInvestAmount(0d);
            vo2.setInvestNum(0);
            vo2.setProportionRate(0d);
            list.add(vo2);
        }
		res.setInvestProportionList(BeanUtils.classToArrayList(list));
		return res;
	}
	
	@Override
	public List<BsSubAccountVO> zanMyInvestByUserId(Integer userId, Integer pageIndex,
			Integer pageSize, String investStatus){
		int start = (pageIndex <= 1) ? 0 : ((pageIndex - 1) * pageSize);
		 // 查询赞分期产品
        List<BsSubAccountVO> voList = bsSubAccountMapper.zanMyInvestByUserId(userId, start, pageSize,investStatus);
		
		return voList;
	}

	@Override
	public Double zanMyInvestCurrentHoldByUserId(Integer userId) {
		return bsSubAccountMapper.zanMyInvestCurrentHoldByUserId(userId);
	}

    @Override
    public WithdrawForecastVO withdrawForecast(Integer userId, Double amount) {
        if(null == userId || null == amount) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
        WithdrawForecastVO result = new WithdrawForecastVO();
        String content = "";

        Double todayTopUpLeftBalance = orderService.topUpSubBuyBalanceToday(userId);
        // 用户可用余额
        Double availableBalance = bsSubAccountMapper.selectAvailableBalanceByUserId(userId);
        Double applyAmount = amount;
        BsSysConfig withdrawApplyLimitConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.WITHDRAW_APPLY_LIMIT);
        Double withdrawApplyLimit = withdrawApplyLimitConfig == null ? 50000d : Double.valueOf(withdrawApplyLimitConfig.getConfValue());
        if(applyAmount.compareTo(MoneyUtil.subtract(availableBalance, todayTopUpLeftBalance).doubleValue()) <= 0) {
            // 1.1 (D<=A) 申请金额 <= 用户可用余额 - 今日充值未购买剩余金额（提现金额不包含今日充值金额，T日清算完）
            if(applyAmount.compareTo(withdrawApplyLimit) > 0) {
                // 1.1.1 (D>5万) 申请金额 > 5万，工作日
                if(checkWorkDayTime()) {
                    content = "预计当日到账";
                } else {
                    content = "预计下个工作日到账";
                }
            } else {
                // 1.1.2 (D<=5万) 申请金额 > 5万
                content = "预计当日到账";
            }
        } else {
            // 1.2 (D>A) 申请金额 > 用户可用余额 - 今日充值未购买剩余金额（提现金额包含今日充值金额，T+1日清算完）
            if(applyAmount.compareTo(withdrawApplyLimit) > 0) {
                // 1.1.1 (D>5万) 申请金额 > 5万，工作日
                content = "预计下个工作日到账";
            } else {
                // 1.1.2 (D<=5万) 申请金额 > 5万
                content = "预计下个工作日到账";
            }
        }
        result.setContent(content);
        return result;
    }

    @Override
    public String riskStatus(Integer userId) {
        BsQuestionnaireExample example = new BsQuestionnaireExample();
        example.createCriteria().andUserIdEqualTo(userId);
        example.setOrderByClause("update_time desc");
        List<BsQuestionnaire> bsQuestionnaireList = bsQuestionnaireMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(bsQuestionnaireList)) {
            logger.info("用户{}未进行风险测评", userId);
            return Constants.IS_NO;
        } else {
            BsQuestionnaire questionnaire = bsQuestionnaireList.get(0);
            if(com.pinting.business.util.DateUtil.compareTo(questionnaire.getExpireTime(), new Date()) > 0) {
                logger.info("用户{}已进行风险测评", userId);
                return Constants.IS_YES;
            } else {
                logger.info("用户{}风险测评已过期", userId);
                return Constants.IS_EXPIRE;
            }
        }
    }

    //判断是否是工作日
    private boolean checkWorkDayTime() {
        BsHoliday holiday = bsHolidayMapper.todayIsHolidayOrNot();
        if(holiday == null) {
            Date now = new Date();
            String nowDateStr = DateUtil.formatYYYYMMDD(now);
            Date startTime = DateUtil.parseDateTime(nowDateStr + " 00:00:00");
            Date endTime = DateUtil.parseDateTime(nowDateStr + " 23:59:59");

            if(now.compareTo(startTime) >= 0 && now.compareTo(endTime) <= 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
