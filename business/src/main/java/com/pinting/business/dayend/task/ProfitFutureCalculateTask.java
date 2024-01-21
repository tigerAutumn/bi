package com.pinting.business.dayend.task;

import com.pinting.business.dao.BsBatchBuyMapper;
import com.pinting.business.model.BsCashSchedule30;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.service.manage.MStatisticsService;
import com.pinting.business.service.site.SMSService;
import com.pinting.core.util.DateUtil;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日终统计未来30天内收益
 * @Project: business
 * @Title: ProfitFutureCalculateTask.java
 * @author Huang MengJian
 * @date 2015-3-9 下午1:28:49
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class ProfitFutureCalculateTask {

	
	private Logger log = LoggerFactory.getLogger(ProfitFutureCalculateTask.class);
	@Autowired
	private MStatisticsService statisticsService;
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private BsSpecialJnlService bsSpecialJnlService;
	@Autowired
	private SMSService smsService;
	@Autowired
	private BsBatchBuyMapper bsBatchBuyMapper;
	/**
	 * 任务执行
	 */
	public void execute() {
		
		//查询数据
		ArrayList<BsCashSchedule30> listValue = getProfitDate();
		if(listValue !=null && listValue.size() != 0){
			//插入数据
			insertBsCashSchedule30(listValue);
		}else{
			log.info("==================日终【计算未来一年内收益统计】未来365天内无任何收益====================");
			log.info("==================日终【计算未来一年内收益统计】结束====================");
		}
		
	}
	
	
	public ArrayList<BsCashSchedule30> getProfitDate(){
		Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
		Date future30 = DateUtil.addDays(today, 365);
		ArrayList<BsCashSchedule30> list = new ArrayList<BsCashSchedule30>();
		ArrayList<BsCashSchedule30> valueList = statisticsService.selectBsCashScheduleList(today,future30);
		if(CollectionUtils.isNotEmpty(valueList)){
			for (BsCashSchedule30 bsCashSchedule30 : valueList) {
				Date after30Days = DateUtil.addDays(new Date(), 365);
				if(bsCashSchedule30.getCashDate().compareTo(after30Days) >0){
					list.add(bsCashSchedule30);
				}else{
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("currTime", bsCashSchedule30.getCashDate());
					Double yun = bsBatchBuyMapper.selectOneDaySysAwaitingReturnYunDai(paramMap);
					bsCashSchedule30.setYunDaiAmount(yun == null ? 0d : yun);
					Double qi = bsBatchBuyMapper.selectOneDaySysAwaitingReturn7Dai(paramMap);
					bsCashSchedule30.setQiDaiAmount(qi == null ? 0d : qi);
					list.add(bsCashSchedule30);
				}
				
			}
		}
		return valueList;
	}
	/**
	 * 1、删除数据，当天以及当天以后的数据删除掉
	 * 2、计算未来一年内收益统计，插入数据
	 * @param valueList
	 */
	public void insertBsCashSchedule30(final ArrayList<BsCashSchedule30> valueList){
		try {
			boolean isSucc = transactionTemplate
					.execute(new TransactionCallback<Boolean>() {
						@Override
						public Boolean doInTransaction(TransactionStatus ts) {
							try {
								
								//开始清空数据表,删除当天以及当天以后的数据
								boolean key = statisticsService.deleteBsCashSchedule();
								if(!key){
									return false;
								}
								//插入新的数据
								statisticsService.insertCashScheduleList(valueList);
								
							} catch (Exception e) {
								ts.setRollbackOnly();
								log.error(
										"==================日终【计算未来一年内收益统计】更新数据库失败====================",
										e);
								return false;
							}
							return true;
						}
	
					});
			if (!isSucc) {
				throw new Exception("日终【计算未来一年内收益统计】失败");
			}
	} catch (Exception e) {
		// 由于跑批失败，此处需记录失败信息 到 特殊交易流水表
		log.error("==================日终【计算未来一年内收益统计】失败，【特殊交易流水表】新增失败记录====================", e);
		String type = "日终【计算未来一年内收益统计】";
		String detail = "【" + DateUtil.format(new Date()) + "】日终：计算未来一年内收益统计跑批失败";
		bsSpecialJnlService.addSpecialJnl(type, detail);
		smsService.sendSysManagerMobiles("计算未来一年内收益统计跑批",true);
	}
	log.info("==================日终【计算未来一年内收益统计】结束====================");
	}
}
