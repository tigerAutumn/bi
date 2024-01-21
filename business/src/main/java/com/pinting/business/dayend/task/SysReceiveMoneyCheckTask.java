package com.pinting.business.dayend.task;

import com.pinting.business.dao.BsBatchBuyMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.BsSysReceiveMoneyMapper;
import com.pinting.business.enums.ThirdSysChannelEnum;
import com.pinting.business.model.*;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 当日系统回款检查告警
 * @Project: business
 * @Title: SysReceiveMoneyCheckTask.java
 * @author dingpf
 * @date 2016-3-8 下午3:35:30
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class SysReceiveMoneyCheckTask {
	private Logger log = LoggerFactory.getLogger(SysReceiveMoneyCheckTask.class);
	@Autowired
	private SpecialJnlService specialJnlService;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsBatchBuyMapper bsBatchBuyMapper;
	@Autowired
	private BsSysReceiveMoneyMapper bsSysReceiveMoneyMapper;
	@Autowired
	private SMSServiceClient smsServiceClient;
	
	/**
	 * 任务执行
	 * 1、查询当日1.0需回款产品，并判断是否已回款，未回款，则告警
	 * 2、查询当日2.0和3.0需回款产品（包括一次性回款和结息回款），未回款或金额不匹配，则告警
	 */
	public void execute() {
		// 定时任务{当日系统回款检查告警}
		log.info("=========定时任务{当日系统回款检查告警}开始=========");
		Date settleDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
		try {
			/*1、查询当日1.0需回款产品，并判断是否已回款，未回款，则告警*/
			BsSubAccountExample oldAccountExample = new BsSubAccountExample();
			Date oldSeparateDate = DateUtil.parseDate("2015-12-01");
			List<Integer> statuss = new ArrayList<Integer>();  
			statuss.add(Constants.SUBACCOUNT_STATUS_OPEN);
			statuss.add(Constants.SUBACCOUNT_STATUS_CLOSE);
			oldAccountExample.createCriteria().andProductTypeEqualTo("REG")
				.andInterestBeginDateLessThanOrEqualTo(oldSeparateDate)
				.andLastFinishInterestDateEqualTo(settleDate)
				.andStatusNotIn(statuss);
			//当日1.0需回款产品
			List<BsSubAccount> oldAccounts = bsSubAccountMapper.selectByExample(oldAccountExample);
			//根据1.0需回款产品列表，分别判断是否已回款，未回款，则告警
			if(oldAccounts!=null && oldAccounts.size()>0){
				for (BsSubAccount bsSubAccount : oldAccounts) {
					Integer status = bsSubAccount.getStatus();
					if(Constants.SUBACCOUNT_STATUS_SETTLE.compareTo(status) != 0 ){
						log.error("=========定时任务{当日系统回款检查告警}1.0产品户编号["+bsSubAccount.getId()+"]尚未回款，请检查=========");
						//告警
						specialJnlService.warn4Fail(bsSubAccount.getBalance(),
								"定时任务{当日系统回款检查告警}1.0产品户编号["+bsSubAccount.getId()+"]尚未回款，请检查",
								null,"1.0理财产品尚未回款",true);
					}
				}
			}else{
				log.info("=========定时任务{当日系统回款检查告警}无1.0产品回款，无需校验=========");
			}
		} catch (Exception e) {
			log.info("=========定时任务{当日系统回款检查告警}1.0产品回款检查失败=========", e);
		}
		
		/*2、查询当日2.0需回款产品（一次性回款），未回款或金额不匹配，则告警*/
		checkSysReceiveMoney4New("2.0", settleDate);
		
		/*3、查询当日3.0需回款产品（包括一次性回款和结息回款），未回款或金额不匹配，则告警*/
		checkSysReceiveMoney4New("3.0", settleDate);
		
	}
	
	private void checkSysReceiveMoney4New(String newType, Date settleDate){
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("currTime", settleDate);
			List<Map<String, Object>> newAccounts = new ArrayList<Map<String, Object>>();
			if("2.0".equals(newType)){
				newAccounts = bsBatchBuyMapper.selectCurrDaySysAwaitingReturn2(paramMap);
			}else if("3.0".equals(newType)){
				newAccounts = bsBatchBuyMapper.selectCurrDaySysAwaitingReturn3(paramMap);
			}
			
			if(newAccounts!=null && newAccounts.size()>0){
				for (Map<String, Object> map : newAccounts) {
					Double principal = (Double) map.get("amount");
					Double interest = (Double) map.get("dafy_return_interest");
					Double amount = MoneyUtil.defaultRound(MoneyUtil.add(principal, interest)).doubleValue();
					String productOrderNo = (String) map.get("send_batch_id");
					String propertySymbol = (String) map.get("property_symbol");
					String channelName = ThirdSysChannelEnum.getEnumByCode(propertySymbol) != null 
							? ThirdSysChannelEnum.getEnumByCode(propertySymbol).getName() : "";
					Date expectTime = (Date) map.get("expect_time");
					BsSysReceiveMoneyExample sysReceiveMoneyExample = new BsSysReceiveMoneyExample();
					sysReceiveMoneyExample.createCriteria().andProductOrderNoEqualTo(productOrderNo)
						.andStatusEqualTo(Constants.RETURN_NOTICE_DEAL_STATUS_SUCCESS)
						.andCreateTimeBetween(settleDate, DateUtil.addDays(settleDate, 1));
					List<BsSysReceiveMoney> sysReceiveMoneys = bsSysReceiveMoneyMapper.selectByExample(sysReceiveMoneyExample);
					if(sysReceiveMoneys!=null && sysReceiveMoneys.size()>0){
						Double returnAmount = sysReceiveMoneys.get(0).getAmount();
						String returnType = sysReceiveMoneys.get(0).getType();
						if(Constants.RETURN_NOTICE_TYPE_INTEREST.equals(returnType)){
							amount = MoneyUtil.defaultRound(interest).doubleValue();
						}
						if(MoneyUtil.defaultRound(MoneyUtil.subtract(returnAmount, amount)).compareTo(new BigDecimal(0))==0){
							log.info("=========定时任务{"+channelName+"当日系统回款检查告警}"+newType+"产品系统购买单号["+productOrderNo+"]回款正常=========");
						}else{
							log.error("=========定时任务{"+channelName+"当日系统回款检查告警}"+newType+"产品系统购买单号["+productOrderNo+"]回款金额异常，差额["
									+MoneyUtil.defaultRound(MoneyUtil.subtract(returnAmount, amount))+"]，请检查=========");
							//告警
							specialJnlService.warnFinance4Fail(principal,
									"定时任务{"+channelName+"当日系统回款检查告警}"+newType+"产品系统购买单号["+productOrderNo+"]回款金额异常，差额["
									+MoneyUtil.defaultRound(MoneyUtil.subtract(returnAmount, amount))+"]，请检查",
									productOrderNo, newType+channelName+"理财回款金额异常",true);
						}
					}else{
						log.error("=========定时任务{"+channelName+"当日系统回款检查告警}"+newType+"产品系统购买单号["+productOrderNo+"]尚未回款，请检查=========");
						//告警
						String errorMsg = "利息尚未回款";

						if(settleDate.compareTo(expectTime) >= 0){
							errorMsg = "理财产品尚未回款";
						}
						specialJnlService.warnFinance4Fail(principal,
								"定时任务{"+channelName+"当日系统回款检查告警}"+newType+"产品系统购买单号["+productOrderNo+"]尚未回款，请检查",
								productOrderNo, newType+channelName+errorMsg,true);
					}
				}
			}else{
				log.info("=========定时任务{当日系统回款检查告警}无"+newType+"产品回款，无需校验=========");
			}
		} catch (Exception e) {
			log.info("=========定时任务{当日系统回款检查告警}"+newType+"产品回款检查失败=========", e);
		}
	}
}
