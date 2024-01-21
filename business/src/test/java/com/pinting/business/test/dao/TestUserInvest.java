package com.pinting.business.test.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.dao.BsProductInvestViewMapper;
import com.pinting.business.model.BsBatchBuy;
import com.pinting.business.model.BsProductInvestView;
import com.pinting.business.model.BsUserInvestView;
import com.pinting.business.model.BsUserInvestViewExample;
import com.pinting.business.model.BsUserRegistView;
import com.pinting.business.model.BsUserRegistViewExample;
import com.pinting.business.service.manage.BsSubAccountService;
import com.pinting.business.service.manage.BsUserRegisterService;
import com.pinting.business.service.manage.MUserInvestViewService;
import com.pinting.business.test.TestBase;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.hessian.message.pay19.G2BReqMsg_Pay19Pay4Another_Pay4AnotherResultNotice;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;

public class TestUserInvest extends TestBase{
	private Logger log = LoggerFactory.getLogger(TestUserInvest.class);
	@Autowired
	private BsSubAccountService bsSubAccountService;
	@Autowired
	private MUserInvestViewService userInvestViewService;
	@Autowired
	private BsUserRegisterService bsUserRegisterService;
	@Autowired
	private BsProductInvestViewMapper viewMapper;
	@Test
	public void returnNotice(){
		
		G2BReqMsg_Pay19Pay4Another_Pay4AnotherResultNotice req = new G2BReqMsg_Pay19Pay4Another_Pay4AnotherResultNotice();
		req.setMxOrderId("92142411874400010713");
		req.setFinishTime(new Date());
		req.setOrderStatus(OrderStatus.FAIL.getCode());
		req.setRetCode("99999");
		req.setErrorMsg("我错啦~");
		//userBatchReceiveMoneyService.userReceiveMoneyNotice(req);
		
		
		G2BReqMsg_Pay19Pay4Another_Pay4AnotherResultNotice req1 = new G2BReqMsg_Pay19Pay4Another_Pay4AnotherResultNotice();
		/*req1.setMxOrderId("92141db2503a00018090");
		req1.setFinishTime(new Date());
		req1.setOrderStatus(OrderStatus.SUCCESS.getCode());
		req1.setRetCode("00000");
		req1.setErrorMsg("");*/
		
		for (int i=0; i<=13; i++) {
			switch (i) {
			case 0:
				req1.setMxOrderId("9214241187e700010713");
				break;
			case 1:
				req1.setMxOrderId("92142411885800010713");
				break;
			case 2:
				req1.setMxOrderId("9214241188c500010840");
				break;
			case 3:
				req1.setMxOrderId("92142411893700010840");
				break;
			case 4:
				req1.setMxOrderId("9214241189b000010840");
				break;
			case 5:
				req1.setMxOrderId("921424118a2100010840");
				break;
			case 6:
				req1.setMxOrderId("921424118a8600010840");
				break;
			case 7:
				req1.setMxOrderId("921424118aef00010840");
				break;
			case 8:
				
				break;
			case 9:
				req1.setMxOrderId("921424118b5800010840");
				break;
			case 10:
				req1.setMxOrderId("921424118bc200010840");
				break;
			case 11:
				req1.setMxOrderId("921424118c2c00010840");
				break;
			case 12:
				req1.setMxOrderId("921424118c9800010840");
				break;
			case 13:
				req1.setMxOrderId("921424119c3800010755");
				break;
			default:
				break;
			}
			req1.setFinishTime(new Date());
			req1.setOrderStatus(OrderStatus.SUCCESS.getCode());
			req1.setRetCode("00000");
			req1.setErrorMsg("");
			
			//userBatchReceiveMoneyService.userReceiveMoneyNotice(req1);
		}
		
		
		
		
		
		/*List<BsBatchBuy> receiveBatchs = new ArrayList<BsBatchBuy>();
		BsBatchBuy buy = new BsBatchBuy();
		buy.setId(16);
		buy.setSendBatchId("151202000000016");
		buy.setAmount(445100d);
		receiveBatchs.add(buy);
		
		userBatchReceiveMoneyService.userBatchReceiveMoney(receiveBatchs);*/
		
	}
	
	
	/*@Test
	public void testStatisticsUserInvest() {
		for (int i = 1; i <= 381; i++) {
			statisticUserInvestJob(i);
			
			ststicRegisterJob(i);
			
			productInvestViewJob(i);
		}
	}*/
	
	private void statisticUserInvestJob(int i) {
		try {
			log.info("==================定时任务用户投资统计开始====================");
			
			//查询条件
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("todayTime",DateUtil.addDays(new Date(), -i));
			//统计投资用户、投资金额、投资笔数、年化金额
			Map<String,Object> subAccountCountMap = bsSubAccountService.statisticSubAccount(params);
			//年化金额
			Double tAmount1 = (Double)subAccountCountMap.get("t1MonthAmount");
			Double tAmount2 = (Double)subAccountCountMap.get("t3MonthAmount");
			Double tAmount3 = (Double)subAccountCountMap.get("t6MonthAmount");
			Double tAmount4 = (Double)subAccountCountMap.get("t1YearAmount");
			Double tAnnualAmount = tAmount1/365*30+tAmount2/365*90+tAmount3/365*180+tAmount4;
			//新增年化金额
			Double newAmount1 = (Double)subAccountCountMap.get("new1MonthAmount");
			Double newAmount2 = (Double)subAccountCountMap.get("new3MonthAmount");
			Double newAmount3 = (Double)subAccountCountMap.get("new6MonthAmount");
			Double newAmount4 = (Double)subAccountCountMap.get("new1YearAmount");
			Double newAnnualAmount = newAmount1/365*30+newAmount2/365*90+newAmount3/365*180+newAmount4;
			
			
			Date d = DateUtil.parseDate(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -(i+1))));
			BsUserInvestViewExample example = new BsUserInvestViewExample();
			example.createCriteria().andDateEqualTo(d);
			BsUserInvestView userInvest = userInvestViewService.findUserInvestByDate(example);
			if(userInvest != null) {
				//更新用户投资信息
				userInvest.setTodayAnnualAmount(newAnnualAmount);
				userInvest.setTotalAnnualAmount(tAnnualAmount);
				userInvest.setTodayInvestAmount((Double)subAccountCountMap.get("newInvestAmount"));
				userInvest.setTotalInvestAmount((Double)subAccountCountMap.get("tInvestAmount"));
				userInvest.setTodayInvestNum(((Long)subAccountCountMap.get("newDealCount")).intValue());
				userInvest.setTotalInvestNum(((Long)subAccountCountMap.get("tDealCount")).intValue());
				userInvest.setTodayUserNum(((Long)subAccountCountMap.get("newInvestUserCount")).intValue());
				userInvest.setTotalUserNum(((Long)subAccountCountMap.get("tInvestUserCount")).intValue());
				userInvestViewService.updateUserInvest(userInvest);
			}else {
				//新增用户投资信息
				userInvest = new BsUserInvestView();
				userInvest.setDate(d);
				userInvest.setTodayAnnualAmount(newAnnualAmount);
				userInvest.setTotalAnnualAmount(tAnnualAmount);
				userInvest.setTodayInvestAmount((Double)subAccountCountMap.get("newInvestAmount"));
				userInvest.setTotalInvestAmount((Double)subAccountCountMap.get("tInvestAmount"));
				userInvest.setTodayInvestNum(((Long)subAccountCountMap.get("newDealCount")).intValue());
				userInvest.setTotalInvestNum(((Long)subAccountCountMap.get("tDealCount")).intValue());
				userInvest.setTodayUserNum(((Long)subAccountCountMap.get("newInvestUserCount")).intValue());
				userInvest.setTotalUserNum(((Long)subAccountCountMap.get("tInvestUserCount")).intValue());
				userInvestViewService.saveUserInvest(userInvest);
			}
		} catch (Exception e) {
			log.error("==================定时任务用户投资统计失败====================", e);
		}
	}
	
	private void ststicRegisterJob(int i) {
		try {
			log.info("==================定时任务昨日注册情况统计开始====================");
			
			String startDate = DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -(i+1)));
			String endDate = DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -i));
			Date d = DateUtil.parseDate(startDate);
			BsUserRegistViewExample example = new BsUserRegistViewExample();
			example.createCriteria().andRegistDateEqualTo(d);
			BsUserRegistView view = bsUserRegisterService.selectByExample(example);
			if(view == null){
				log.info("=================="+startDate+"数据未存，进行新增==================");
				BsUserRegistView record = bsUserRegisterService.selectByTime(startDate, endDate);
				record.setRegistDate(DateUtil.parseDateTime(startDate+" 00:00:00"));
				bsUserRegisterService.addRegisterView(record);
			}else{
				log.info("=================="+startDate+"数据已存在，进行修改==================");
				BsUserRegistView record = bsUserRegisterService.selectByTime(startDate, endDate);
				record.setRegistDate(DateUtil.parseDateTime(startDate+" 00:00:00"));
				record.setId(view.getId());
				bsUserRegisterService.updateView(record);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("==================定时任务昨日注册情况统计失败====================", e);
		}
		
	}
	
	private void productInvestViewJob(int i) {
		log.info("=================【产品投资概览】定时任务开始=====================");
		try {
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(now);
			calendar.add(Calendar.DAY_OF_MONTH, -(i+1));
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date startTime = calendar.getTime();
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			Date endTime = calendar.getTime();
			
			//起息日为今天(意味着购买是发生在昨天)
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String interestBeginDate = format.format(calendar.getTime());
			
			BsProductInvestView todayView = viewMapper.selectTodayProductInvest(startTime, endTime, interestBeginDate);
			BsProductInvestView totalView = viewMapper.selectTotalProductInvest(interestBeginDate);
			
			BsProductInvestView view = new BsProductInvestView();
			if(todayView != null) {
				view.setTodayInvest30(todayView.getTodayInvest30()==null?0d:todayView.getTodayInvest30());
				view.setTodayInvest90(todayView.getTodayInvest90()==null?0d:todayView.getTodayInvest90());
				view.setTodayInvest180(todayView.getTodayInvest180()==null?0d:todayView.getTodayInvest180());
				view.setTodayInvest365(todayView.getTodayInvest365()==null?0d:todayView.getTodayInvest365());
			}
			else {
				view.setTodayInvest30(0d);
				view.setTodayInvest90(0d);
				view.setTodayInvest180(0d);
				view.setTodayInvest365(0d);
			}
			if(totalView != null) {
				view.setTotalInvest30(totalView.getTotalInvest30()==null?0d:totalView.getTotalInvest30());
				view.setTotalInvest90(totalView.getTotalInvest90()==null?0d:totalView.getTotalInvest90());
				view.setTotalInvest180(totalView.getTotalInvest180()==null?0d:totalView.getTotalInvest180());
				view.setTotalInvest365(totalView.getTotalInvest365()==null?0d:totalView.getTotalInvest365());
			}
			else {
				view.setTotalInvest30(0d);
				view.setTotalInvest90(0d);
				view.setTotalInvest180(0d);
				view.setTotalInvest365(0d);
			}
			view.setDate(startTime);
			viewMapper.insertSelective(view);
		}catch(Exception e) {
			log.info("=================【产品投资概览】定时任务异常=====================");
			e.printStackTrace();
		}
		log.info("=================【产品投资概览】定时任务结束=====================");
	}
}
