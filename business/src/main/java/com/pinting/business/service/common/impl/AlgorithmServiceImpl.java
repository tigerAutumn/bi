package com.pinting.business.service.common.impl;

import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.dao.LnFinanceRepayScheduleMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.TransTypeEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.vo.AverageCapitalPlusInterestVO;
import com.pinting.business.model.vo.CommissionVO;
import com.pinting.business.model.vo.ZANRevenueModelVO;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.service.common.CommissionService;
import com.pinting.business.service.loan.LoanUserService;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlgorithmServiceImpl implements AlgorithmService {
    private final Logger logger = LoggerFactory.getLogger(AlgorithmServiceImpl.class);
    @Autowired
    private LnFinanceRepayScheduleMapper lnFinanceRepayScheduleMapper;
    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LoanUserService loanUserService;
    @Autowired
    private BsSysConfigMapper bsSysConfigMapper;
    @Autowired
    private CommissionService commissionService;

    @Override
    public List<AverageCapitalPlusInterestVO> calAverageCapitalPlusInterestPlan(
            Double amount, Integer term, Double rate) {
        if (amount == null || term == null || rate == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        //月利率
        Double monthRate = MoneyUtil.divide(rate, 1200, 4).doubleValue();
        //贷款本金×月利率          amount1
        Double amount1 = MoneyUtil.multiply(amount, monthRate).doubleValue(); //贷款本金×月利率
        //(1＋月利率)＾还款月数         term2
        Double term2 = Math.pow(monthRate + 1, term);

        Double amount4add = 0d;

        List<AverageCapitalPlusInterestVO> list = new ArrayList<>();
        for (int i = 0; i < term; i++) {

            //本息=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕
            Double totalAmount = MoneyUtil.divide(MoneyUtil.multiply(amount1, term2).doubleValue(), term2 - 1, 2).doubleValue();
            Double monthAmount;
            Double rateAmount;
            if (i == term - 1) {
                //每月本金-----总本金减之前的本金
                monthAmount = MoneyUtil.subtract(amount, amount4add).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            } else {
                //每月本金=贷款本金×月利率×(1+月利率)^(还款月序号-1)÷〔(1+月利率)^还款月数-1〕
                monthAmount = MoneyUtil.divide(MoneyUtil.multiply(amount1, Math.pow(monthRate + 1, i)).doubleValue(), term2 - 1, 2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            //每月利息-----本息-本金
            rateAmount = MoneyUtil.subtract(totalAmount, monthAmount).setScale(2, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            amount4add = MoneyUtil.add(amount4add, monthAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

            AverageCapitalPlusInterestVO record = new AverageCapitalPlusInterestVO();
            record.setPlanTotal(totalAmount);
            record.setPlanPrincipal(monthAmount);
            record.setPlanInterest(rateAmount);
            record.setRepaySerial(i + 1);
            list.add(record);
        }

        return list;
    }

    /**
     * 计算某笔借款某期应还理财人本息
     */
    @Override
    public Double calFinancerPrincipalInterest(Integer loanId, Integer serialId) {
        if (loanId == null || serialId == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        Double amount = lnFinanceRepayScheduleMapper.sumAmountByLoanIdSerialId(loanId, serialId);

        amount = MoneyUtil.multiply(amount, 1).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        return amount;
    }

    /**
     * 计算某笔借款某期应提保证金
     * 借款总额*3%/12
     */
    @Override
    public Double calZANDeposit(Integer loanId, Integer serialId) {
        if (loanId == null || serialId == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        LnLoan loan = lnLoanMapper.selectByPrimaryKey(loanId);
        Double amount = loan.getApproveAmount(); //借款总额
        amount = MoneyUtil.multiply(amount, 0.03).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        amount = MoneyUtil.divide(amount, 12).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        //amount = (double) Math.round(amount);
        return amount;
    }

    /**
     * 计算某笔借款某期应提币港湾服务费
     * 借款总额/期数+借款总额*名义月利率-投资人每期本息-VIP每期本息
     * <p>
     * 名义月利率 =  期数对应实际年化*(期数+1)/(期数*24)
     */
    @Override
    public Double calBGWServiceFee(Integer loanId, Integer serialId) {
        if (loanId == null || serialId == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        LnLoan loan = lnLoanMapper.selectByPrimaryKey(loanId);
        if (loan == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        Integer term = loan.getPeriod();
        BsSysConfig config = new BsSysConfig();
        switch (term) {
            case 1:
                config = bsSysConfigMapper.selectByPrimaryKey(Constants.ZAN_YEAR_RATE_1);
                break;
            case 2:
                config = bsSysConfigMapper.selectByPrimaryKey(Constants.ZAN_YEAR_RATE_2);
                break;
            case 3:
                config = bsSysConfigMapper.selectByPrimaryKey(Constants.ZAN_YEAR_RATE_3);
                break;
            case 4:
                config = bsSysConfigMapper.selectByPrimaryKey(Constants.ZAN_YEAR_RATE_4);
                break;
            case 5:
                config = bsSysConfigMapper.selectByPrimaryKey(Constants.ZAN_YEAR_RATE_5);
                break;
            case 6:
                config = bsSysConfigMapper.selectByPrimaryKey(Constants.ZAN_YEAR_RATE_6);
                break;
            case 9:
                config = bsSysConfigMapper.selectByPrimaryKey(Constants.ZAN_YEAR_RATE_9);
                break;
            case 12:
                config = bsSysConfigMapper.selectByPrimaryKey(Constants.ZAN_YEAR_RATE_12);
                break;

            default:
                break;
        }
        Double rateYear = MoneyUtil.divide(Double.valueOf(config.getConfValue()), 100).doubleValue();
        Double rateMonth = MoneyUtil.multiply(rateYear, term + 1).doubleValue();
        //名义月利率 =期数对应实际年化*(期数+1)/(期数*24)
        rateMonth = MoneyUtil.divide(rateMonth, MoneyUtil.multiply(term, 24).doubleValue(), 10).doubleValue();
        //投资人每期本息+VIP每期本息(A)
        Double sumInterest = lnFinanceRepayScheduleMapper.sumAmountByLoanIdSerialId(loanId, serialId);
        //借款总额/期数(B)
        Double amount1 = MoneyUtil.divide(loan.getApproveAmount(), term, 10).doubleValue();
        //借款总额*名义月利率(C)
        Double rateMonthAmount = MoneyUtil.multiply(loan.getApproveAmount(), rateMonth).doubleValue();
        logger.info("临时日志>>>amount1" + amount1 + "|rateMonthAmount=" + rateMonthAmount + "|sumInterest=" + sumInterest);
        //B+C-A = 借款总额/期数+借款总额*名义月利率-投资人每期本息-VIP每期本息
        Double amount = MoneyUtil.subtract(MoneyUtil.add(amount1, rateMonthAmount).doubleValue(), sumInterest).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        //amount = (double) Math.round(amount);
        return amount;
    }

    /**
     * 计算某笔借款某期赞分期营收
     * <p>
     * =还款金额-投资人本息-VIP本息-蜂鸟保证金-币港湾服务费-手续费
     */
    @Override
    public ZANRevenueModelVO calZANRevenue(Integer loanId, Integer serialId) {
        if (loanId == null || serialId == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //应还总费用(还款金额)
        Double totalAmount = loanUserService.calTotalRepay(loanId, serialId);
        //应还理财人本息(投资人本息-VIP本息)
        Double principalInterest = calFinancerPrincipalInterest(loanId, serialId);
        //保证金
        Double ZANDeposit = calZANDeposit(loanId, serialId);
        //币港湾服务费
        Double BGWServiceFee = calBGWServiceFee(loanId, serialId);
        //手续费
        CommissionVO commissionVO = commissionService.calServiceFee(totalAmount, TransTypeEnum.LOAN_USER_REPAY_QUICK_PAY, null);
        Double serviceFee = commissionVO.getActPayAmount();

        Double amount = MoneyUtil.subtract(totalAmount, principalInterest).doubleValue();
        amount = MoneyUtil.subtract(amount, ZANDeposit).doubleValue();
        amount = MoneyUtil.subtract(amount, BGWServiceFee).doubleValue();
        amount = MoneyUtil.subtract(amount, serviceFee).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        ZANRevenueModelVO zanRevenueModelVO = loanUserService.calTotalRepay4ZANRevenue(loanId, serialId);
        zanRevenueModelVO.setFinancierPrincipalInterest(principalInterest);
        zanRevenueModelVO.setZANDeposit(ZANDeposit);
        zanRevenueModelVO.setBGWServiceFee(BGWServiceFee);
        zanRevenueModelVO.setServiceFee(serviceFee);
        zanRevenueModelVO.setZANRevenue(amount);

        //amount = (double) Math.round(amount);
        return zanRevenueModelVO;
    }

    @Override
    public AverageCapitalPlusInterestVO calAverageCapitalPlusInterestPlan4Serial(
            Double amount, Integer term, Double rate, Integer serialId) {
        AverageCapitalPlusInterestVO record = new AverageCapitalPlusInterestVO();
        if (amount == null || term == null || rate == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        //月利率
        Double monthRate = MoneyUtil.divide(rate, 12, 4).doubleValue();
        //贷款本金×月利率          amount1
        Double amount1 = MoneyUtil.multiply(amount, monthRate).doubleValue(); //贷款本金×月利率
        //(1＋月利率)＾还款月数         term2
        Double term2 = Math.pow(monthRate + 1, term);

        Double amount4add = 0d;
        for (int i = 0; i < term; i++) {
            //本息=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕
            Double totalAmount = MoneyUtil.divide(MoneyUtil.multiply(amount1, term2).doubleValue(), term2 - 1, 2).doubleValue();
            Double monthAmount;
            Double rateAmount;
            if (i == term - 1) {
                //每月本金-----总本金减之前的本金
                monthAmount = MoneyUtil.subtract(amount, amount4add).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            } else {
                //每月本金=贷款本金×月利率×(1+月利率)^(还款月序号-1)÷〔(1+月利率)^还款月数-1〕
                monthAmount = MoneyUtil.divide(MoneyUtil.multiply(amount1, Math.pow(monthRate + 1, i)).doubleValue(), term2 - 1, 2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            //每月利息-----本息-本金
            rateAmount = MoneyUtil.subtract(totalAmount, monthAmount).setScale(2, BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            amount4add = MoneyUtil.add(amount4add, monthAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            if (i == serialId - 1) {
                record.setPlanTotal(totalAmount);
                record.setPlanPrincipal(monthAmount);
                record.setPlanInterest(rateAmount);
                record.setRepaySerial(i + 1);
                break;
            }
        }
        return record;
    }

    @Override
    public List<AverageCapitalPlusInterestVO> calEqualPrincipalInterestPlan(Double amount, Integer term, Double rate) {
        if (amount == null || term == null || rate == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        //monthRate月利率=结算利率/12;
        Double monthRate = MoneyUtil.divide(rate, 1200, 4).doubleValue();
        //amount1月利息=借款总本金*月利率 ;
        Double amount1 = MoneyUtil.multiply(amount, monthRate).doubleValue();
        //某期数term2=(1+月利率)^还款月数 ;
        Double term2 = Math.pow(monthRate + 1, term);
        //等额本息 月还款额 = 每期本息=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕;
        Double totalMonthAmount = MoneyUtil.divide(MoneyUtil.multiply(amount1, term2).doubleValue(), term2 - 1, 2).doubleValue();

        //每期结算利息
        Double rateAmount = MoneyUtil.divide(MoneyUtil.subtract(MoneyUtil.multiply(totalMonthAmount, term).doubleValue(), amount)
                .doubleValue(), term).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        Double amount4add = 0d;

        List<AverageCapitalPlusInterestVO> list = new ArrayList<AverageCapitalPlusInterestVO>();

        //计算结算利息
        for (int i = 0; i < term; i++) {
            Double monthAmount;
            if (i == term - 1) {
                //每月本金=总本金减之前的本金
                monthAmount = MoneyUtil.subtract(amount, amount4add).doubleValue();
            } else {
                //每月本金 = 总本金/总期数 向下舍入FLOOR
                monthAmount = CalculatorUtil.formatCash(amount, term, 2);
            }
            //serial-1 之前的本金和
            amount4add = MoneyUtil.add(amount4add, monthAmount).doubleValue();

            AverageCapitalPlusInterestVO record = new AverageCapitalPlusInterestVO();
            record.setPlanTotal(MoneyUtil.add(monthAmount, rateAmount).doubleValue());
            record.setPlanPrincipal(monthAmount);
            record.setPlanInterest(rateAmount);
            record.setRepaySerial(i + 1);
            list.add(record);
        }
        return list;
    }

    @Override
    public AverageCapitalPlusInterestVO calEqualPrincipalInterestPlan4Serial(
            Double amount, Integer term, Double rate, Integer serialId) {

        AverageCapitalPlusInterestVO record = new AverageCapitalPlusInterestVO();

        if (amount == null || term == null || rate == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //monthRate月利率=结算利率/12;
        Double monthRate = MoneyUtil.divide(rate, 12, 4).doubleValue();
        Double amount4add = 0d;

        //amount1月利息=借款总本金*月利率 ;
        Double amount1 = MoneyUtil.multiply(amount, monthRate).doubleValue();
        //某期数term2=(1+月利率)^还款月数 ;
        Double term2 = Math.pow(monthRate + 1, term);
        //等额本息 月还款额 = 每期本息=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕;
        Double monthAmount = MoneyUtil.divide(MoneyUtil.multiply(amount1, term2).doubleValue(), term2 - 1, 2).doubleValue();
        
        //计算结算利息
        for (int i = 0; i < term; i++) {
            Double monthPrincipal;
            if (i == term - 1) {
                //每月本金=总本金减之前的本金
            	monthPrincipal = MoneyUtil.subtract(amount, amount4add).doubleValue();
            } else {
                //每月本金 = 总本金/总期数 向下舍入FLOOR
            	monthPrincipal = CalculatorUtil.formatCash(amount, term, 2);
            }
            //serial-1 之前的本金和
            amount4add = MoneyUtil.add(amount4add, monthPrincipal).doubleValue();
            //每期结算利息
            Double rateAmount = MoneyUtil.subtract(monthAmount, monthPrincipal).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            if (i == serialId - 1) {
                record.setPlanTotal(MoneyUtil.add(monthPrincipal, rateAmount).doubleValue());
                record.setPlanPrincipal(monthPrincipal);
                record.setPlanInterest(rateAmount);
                record.setRepaySerial(i + 1);
                break;
            }
        }
        return record;
    }

    @Override
    public Double calBGWSettlementFee(Integer loanId, Integer serialId) {
        if (loanId == null || serialId == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        LnLoan loan = lnLoanMapper.selectByPrimaryKey(loanId);
        if (loan == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        Integer term = loan.getPeriod();
        BsSysConfig config = new BsSysConfig();
        switch (term) {
            case 1:
                config = bsSysConfigMapper.selectByPrimaryKey(Constants.ZAN_YEAR_RATE_1);
                break;
            case 2:
                config = bsSysConfigMapper.selectByPrimaryKey(Constants.ZAN_YEAR_RATE_2);
                break;
            case 3:
                config = bsSysConfigMapper.selectByPrimaryKey(Constants.ZAN_YEAR_RATE_3);
                break;
            case 4:
                config = bsSysConfigMapper.selectByPrimaryKey(Constants.ZAN_YEAR_RATE_4);
                break;
            case 5:
                config = bsSysConfigMapper.selectByPrimaryKey(Constants.ZAN_YEAR_RATE_3);
                break;
            case 6:
                config = bsSysConfigMapper.selectByPrimaryKey(Constants.ZAN_YEAR_RATE_6);
                break;
            case 9:
                config = bsSysConfigMapper.selectByPrimaryKey(Constants.ZAN_YEAR_RATE_9);
                break;
            case 12:
                config = bsSysConfigMapper.selectByPrimaryKey(Constants.ZAN_YEAR_RATE_12);
                break;

            default:
                break;
        }
        Double rateYear = MoneyUtil.divide(Double.valueOf(config.getConfValue()), 100).doubleValue();
        Double rateMonth = MoneyUtil.multiply(rateYear, term + 1).doubleValue();
        //名义月利率 =期数对应实际年化*(期数+1)/(期数*24)
        rateMonth = MoneyUtil.divide(rateMonth, MoneyUtil.multiply(term, 24).doubleValue(), 10).doubleValue();

        //借款总额/期数(B)
        Double amount1 = MoneyUtil.divide(loan.getApproveAmount(), term, 2).doubleValue();
        //借款总额*名义月利率(C)
        Double rateMonthAmount = MoneyUtil.multiply(loan.getApproveAmount(), rateMonth).doubleValue();
        //B+C= 借款总额/期数+借款总额*名义月利率
        Double amount = MoneyUtil.add(amount1, rateMonthAmount).doubleValue();
        return amount;
    }

}
