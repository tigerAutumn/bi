package com.dafy.tools.loan7;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.dafy.model.vo.loan7.RepayPlan;

public class Loan7RepayPlanUtil {

	
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 借款成功日期
        Date successDate = format.parse("2017-12-31");
        // 借款金额，单位: 分
        long loanMoney = 500000;
        // 借款年利率
        double yearInterestRate = 0.437;

        System.out.println(
                calculateRepayPlan(successDate, loanMoney, yearInterestRate));
    }
	
	
    /**
     * 注意时间要统一，全部到天，时分秒毫秒全部置零
     *
     * @param repayDay     还款日
     * @param loanStartDay 借款成功时间
     * @param loanEndDay   借款到期时间
     * @return
     */
    private static RepayPlan getRepayPlan(int repayDay, long loanStartDay,
                                          long nowDay) {
        
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTimeInMillis((nowDay));
        toDay(nowCalendar);
        int nowMonthDay = nowCalendar.get(Calendar.DAY_OF_MONTH);
        long originMillionSeconds = nowCalendar.getTimeInMillis();
        if (repayDay <= nowMonthDay || (repayDay > nowMonthDay
                && nowCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) == nowMonthDay)) {
            nowCalendar.add(Calendar.MONTH, 1);
            nowCalendar.set(Calendar.DAY_OF_MONTH,
                    Math.min(nowCalendar.getActualMaximum(Calendar.DAY_OF_MONTH), repayDay));
        }
        return getNextRepayPlan(repayDay, loanStartDay,  nowCalendar,
                originMillionSeconds);
    }
    
    private static RepayPlan getNextRepayPlan(int repayDay, long loanStartDate, 
            Calendar nowCalendar, long originMillionSeconds) {
		// 现在的天
		int monthDays = nowCalendar.getActualMaximum(Calendar.DATE);
		// 取下个月的对应天
		int realRepayDay = Math.min(repayDay, monthDays);
		nowCalendar.set(Calendar.DAY_OF_MONTH, realRepayDay);
		long nextRepayDate = nowCalendar.getTimeInMillis();
		
		int period = getRepayPeriod(repayDay, loanStartDate, nextRepayDate);
		if (period > 3) {
			return null;
		}
		int days = (int) ((nextRepayDate - originMillionSeconds) / 86400000);
		return new RepayPlan(period, nextRepayDate, days);
	}
    
    
    private static int getRepayPeriod(int repayDay, long loanDate, long repayDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(loanDate);
        calendar.set(Calendar.DAY_OF_MONTH,
                Math.min(calendar.getActualMaximum(Calendar.DAY_OF_MONTH), repayDay));

        // 获取到第一个扣款日
        if (calendar.getTimeInMillis() <= loanDate) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
            calendar.set(Calendar.DAY_OF_MONTH,
                    Math.min(calendar.getActualMaximum(Calendar.DAY_OF_MONTH), repayDay));
        }

        int period = 1;
        while (repayDate > calendar.getTimeInMillis()) {
            period++;
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
            calendar.set(Calendar.DAY_OF_MONTH,
                    Math.min(calendar.getActualMaximum(Calendar.DAY_OF_MONTH), repayDay));
        }
        return period;
    }
	
    /**
     * 计算还款计划. 从successTime开始, 一直到周期结束.
     *
     * @param repayDay         还款日
     * @param successTime      借款成功时间
     * @param period           借款期数，单位: 天
     * @param loanMoney        借款金额，单位: 分
     * @param yearInterestRate 借款年利率
     * @return
     */
    public static List<RepayPlan> calculateRepayPlan(Date successTime, long loanMoney, double yearInterestRate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(successTime);
        toDay(calendar);
        long loanStartDay = calendar.getTimeInMillis();

        int repayDay = calendar.get(Calendar.DATE);
        if (repayDay > 28) {
        	repayDay = 28;
		}
        
        List<RepayPlan> repayPlans = new ArrayList<RepayPlan>();
        RepayPlan repayPlan = getRepayPlan(repayDay, loanStartDay,
                successTime.getTime());

        // 第一期要计头计尾
        if (repayPlan != null) {
            repayPlan.setDays(repayPlan.getDays() + 1);
        }

        while (repayPlan != null) {
            repayPlans.add(repayPlan);
            repayPlan = getRepayPlan(repayDay, loanStartDay, repayPlan.getDate());
        }

        // 算利息和本金
        for (int i = 0; i < repayPlans.size(); i++) {
            RepayPlan plan = repayPlans.get(i);
            long interest = new Double(
                    Math.ceil(loanMoney * yearInterestRate / 365)).longValue() * plan.getDays();
            long principal = i == repayPlans.size() - 1 ? loanMoney : 0;
            plan.setInterest(interest);
            plan.setPrincipal(principal);
        }

        return repayPlans;
    }
    
    private static void toDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}
