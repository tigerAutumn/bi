package com.pinting.business.util.loan7;
/**
 * Project: loancenterrpc-provider
 * File Created at 2015年9月9日
 * chiukong lee
 * Copyright 2015 Dafy Finance Corporation Limited.
 * All rights reserved.
 * This software is the confidential and proprietary information of
 * Dafy Finance Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with http://www.dafy.com.
 */


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.pinting.business.model.vo.RepayPlan;

/**
 * 还款工具类
 *
 * @author chiukong lee
 * @since 2015年9月9日 下午4:05:08
 */
public class RepayPlanUtil {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 借款成功日期
        Date successDate = format.parse("2017-12-18");
        // 还款日
        int repayDay = 14;
        // 借款期数，单位: 天
        int period = 90;
        // 借款金额，单位: 分
        long loanMoney = 500000;
        // 借款年利率
        double yearInterestRate = 0.437;

        System.out.println(
                calculateRepayPlan(repayDay, successDate, period, loanMoney, yearInterestRate));
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
    public static List<RepayPlan> calculateRepayPlan(int repayDay, Date successTime, int period,
                                                     long loanMoney, double yearInterestRate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(successTime);
        toDay(calendar);
        long loanStartDay = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_MONTH, period - 1);
        long loanEndDay = calendar.getTimeInMillis();

        List<RepayPlan> repayPlans = new ArrayList<RepayPlan>();
        RepayPlan repayPlan = getRepayPlan(repayDay, loanStartDay, loanEndDay,
                successTime.getTime());

        // 第一期要计头计尾
        if (repayPlan != null) {
            repayPlan.setDays(repayPlan.getDays() + 1);
        }

        while (repayPlan != null) {
            repayPlans.add(repayPlan);
            repayPlan = getRepayPlan(repayDay, loanStartDay, loanEndDay, repayPlan.getDate());
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

    /**
     * 注意时间要统一，全部到天，时分秒毫秒全部置零
     *
     * @param repayDay     还款日
     * @param loanStartDay 借款成功时间
     * @param loanEndDay   借款到期时间
     * @return
     */
    private static RepayPlan getRepayPlan(int repayDay, long loanStartDay, long loanEndDay,
                                          long nowDay) {
        if (nowDay >= loanEndDay) {
            return null;
        }
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
        return getNextRepayPlan(repayDay, loanStartDay, loanEndDay, nowCalendar,
                originMillionSeconds);
    }

    private static RepayPlan getNextRepayPlan(int repayDay, long loanStartDate, long loanEndDate,
                                              Calendar nowCalendar, long originMillionSeconds) {
        // 现在的天
        int monthDays = nowCalendar.getActualMaximum(Calendar.DATE);
        // 取下个月的对应天
        int realRepayDay = Math.min(repayDay, monthDays);
        nowCalendar.set(Calendar.DAY_OF_MONTH, realRepayDay);
        long nextRepayDate = nowCalendar.getTimeInMillis();
        if (nextRepayDate > loanEndDate) {
            nextRepayDate = loanEndDate;
        }
        int period = getRepayPeriod(repayDay, loanStartDate, nextRepayDate);
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

    private static void toDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }


}
