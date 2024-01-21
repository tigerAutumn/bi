package com.pinting.business.dayend.task;

import com.pinting.business.dao.BsProductInvestViewMapper;
import com.pinting.business.model.BsProductInvestView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class ProductInvestViewTask {

	private Logger logger = LoggerFactory.getLogger(ProductInvestViewTask.class);
	
	@Autowired
	private BsProductInvestViewMapper viewMapper;
	
	public void execute() {
		logger.info("=================【产品投资概览】定时任务开始=====================");
		try {
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date startTime = calendar.getTime();
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			Date endTime = calendar.getTime();
			
			//起息日为今天(意味着购买是发生在昨天)
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String interestBeginDate = format.format(now);
			
			BsProductInvestView todayView = viewMapper.selectTodayProductInvest(startTime, endTime, interestBeginDate);
			BsProductInvestView totalView = viewMapper.selectTotalProductInvest(interestBeginDate);
			
			BsProductInvestView view = new BsProductInvestView();
			if(todayView != null) {
				view.setTodayInvest7(todayView.getTodayInvest7()==null?0d:todayView.getTodayInvest7());
				view.setTodayInvest30(todayView.getTodayInvest30()==null?0d:todayView.getTodayInvest30());
				view.setTodayInvest90(todayView.getTodayInvest90()==null?0d:todayView.getTodayInvest90());
				view.setTodayInvest180(todayView.getTodayInvest180()==null?0d:todayView.getTodayInvest180());
				view.setTodayInvest270(todayView.getTodayInvest270()==null?0:todayView.getTodayInvest270());
				view.setTodayInvest365(todayView.getTodayInvest365()==null?0d:todayView.getTodayInvest365());
			}
			else {
				view.setTodayInvest7(0d);
				view.setTodayInvest30(0d);
				view.setTodayInvest90(0d);
				view.setTodayInvest180(0d);
				view.setTodayInvest270(0d);
				view.setTodayInvest365(0d);
			}
			if(totalView != null) {
				view.setTotalInvest7(totalView.getTotalInvest7()==null?0d:totalView.getTotalInvest7());
				view.setTotalInvest30(totalView.getTotalInvest30()==null?0d:totalView.getTotalInvest30());
				view.setTotalInvest90(totalView.getTotalInvest90()==null?0d:totalView.getTotalInvest90());
				view.setTotalInvest180(totalView.getTotalInvest180()==null?0d:totalView.getTotalInvest180());
				view.setTotalInvest270(totalView.getTotalInvest270()==null?0:totalView.getTotalInvest270());
				view.setTotalInvest365(totalView.getTotalInvest365()==null?0d:totalView.getTotalInvest365());
			}
			else {
				view.setTotalInvest7(0d);
				view.setTotalInvest30(0d);
				view.setTotalInvest90(0d);
				view.setTotalInvest180(0d);
				view.setTotalInvest270(0d);
				view.setTotalInvest365(0d);
			}
			view.setDate(startTime);
			viewMapper.insertSelective(view);
		}catch(Exception e) {
			logger.info("=================【产品投资概览】定时任务异常=====================");
			e.printStackTrace();
		}
		logger.info("=================【产品投资概览】定时任务结束=====================");
	}
}
