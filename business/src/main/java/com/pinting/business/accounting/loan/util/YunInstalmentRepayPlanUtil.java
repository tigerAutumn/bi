package com.pinting.business.accounting.loan.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.business.model.vo.RepayPlan;
import com.pinting.business.model.vo.YunRepayPlanVO;
import com.pinting.core.util.DateUtil;

/**
 * 云贷分期账单工具类
 * @author bianyatian
 * @2018-7-5 上午10:47:50
 */
public class YunInstalmentRepayPlanUtil {
	
	 private static Logger logger = LoggerFactory.getLogger(YunInstalmentRepayPlanUtil.class);
	/**
	 * 返回操作时间所在的期次和当期的账单日
	 * @param loanDate 借款成功日
	 * @param repayDate 操作时间
	 * @param period 借款期数
	 * @return
	 */
	public static YunRepayPlanVO calRepayPlan(Date loanDate, Date repayDate, Integer period){
		YunRepayPlanVO yunRepayPlan = new YunRepayPlanVO();
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(loanDate);
        toDay(calendar);
        long loanStartDay = calendar.getTimeInMillis();

        int repayDay = calendar.get(Calendar.DATE);
        if (repayDay >= 29) {
        	repayDay = 28;
		}
        
        RepayPlan repayPlan = getRepayPlan(repayDay, loanStartDay,
        		loanDate.getTime());
        // 第一期要计头计尾
        if (repayPlan != null) {
        	yunRepayPlan.setSerialId(repayPlan.getPeriod());
        	yunRepayPlan.setDays((int)repayPlan.getDays() + 1);
        	yunRepayPlan.setPlanDate(
        		DateUtil.parseDate(new SimpleDateFormat("yyyy-MM-dd").format(repayPlan.getDate()))
        	);
           if(repayDate.compareTo(yunRepayPlan.getPlanDate()) <= 0){
        	   logger.info("云贷分期无账单时计算，当期时间所在账单日；借款日期："+DateUtil.formatYYYYMMDD(loanDate)+";当期日期："+DateUtil.formatYYYYMMDD(repayDate)
        			   +"总期数："+period+">>>>>>>>计算的，所在期数"+yunRepayPlan.getSerialId()+"；所在期数的账单日"+DateUtil.formatYYYYMMDD(yunRepayPlan.getPlanDate()));
        	   return yunRepayPlan;
           }
        }

        while (repayPlan != null && period >= repayPlan.getPeriod() ) {
            repayPlan = getRepayPlan(repayDay, loanStartDay, repayPlan.getDate());
            yunRepayPlan.setSerialId(repayPlan.getPeriod());
        	yunRepayPlan.setDays((int)repayPlan.getDays());
        	yunRepayPlan.setPlanDate(
        		DateUtil.parseDate(new SimpleDateFormat("yyyy-MM-dd").format(repayPlan.getDate()))
        	);
           if(repayDate.compareTo(yunRepayPlan.getPlanDate()) <= 0){
        	   logger.info("云贷分期无账单时计算，当期时间所在账单日；借款日期："+DateUtil.formatYYYYMMDD(loanDate)+";当期日期："+DateUtil.formatYYYYMMDD(repayDate)
        			   +"总期数："+period+">>>>>>>>计算的，所在期数"+yunRepayPlan.getSerialId()+"；所在期数的账单日"+DateUtil.formatYYYYMMDD(yunRepayPlan.getPlanDate()));
        	   return yunRepayPlan;
           }
            
        }
		return null;
	};

	/**
     * 注意时间要统一，全部到天，时分秒毫秒全部置零
     *
     * @param repayDay     还款日
     * @param loanStartDay 借款成功时间
     * @param nowDay   借款到期时间
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
	
	public static void main(String []args){
		Date loanDate = DateUtil.parseDate("2018-1-29");
		Date repayDate = DateUtil.parseDate("2018-6-31");
		YunRepayPlanVO a =calRepayPlan(loanDate, repayDate, 6);
		System.out.println(a.getSerialId()+">>>>"+DateUtil.formatYYYYMMDD(a.getPlanDate())+">>>>"+a.getDays()+">>>");
	}
}
