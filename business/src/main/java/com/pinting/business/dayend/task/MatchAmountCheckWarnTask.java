package com.pinting.business.dayend.task;

import com.pinting.business.model.vo.BsMatchWarnVO;
import com.pinting.business.service.site.BsMatchService;
import com.pinting.business.service.site.SMSService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 某日匹配总金额和某日购买总金额不同警告
 * 某笔投资匹配金额平均值超过1万告警
 * @author bianyatian
 * @2016-5-6 下午4:02:29
 */
@Service
public class MatchAmountCheckWarnTask {
	private Logger log = LoggerFactory.getLogger(MatchAmountCheckWarnTask.class);
	
	@Autowired
	private BsMatchService bsMatchService;
	@Autowired
	private SpecialJnlService specialJnlService;
	@Autowired
	private SMSService smsService;
	
	public void execute() {
		//某日匹配总金额和某日购买总金额不同告警
		try {
			sumAmountCheckTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//某笔投资匹配金额平均值超过1万告警
		//avgAmountCheckTask();
	}


	private void avgAmountCheckTask() {
		log.info("==================【某笔投资匹配金额平均值超过1万告警】开始=======================");
		Double limitAmount = 10000.0;
		List<BsMatchWarnVO> list =  bsMatchService.getAvgAmountWarn(limitAmount);
		boolean flag = false;
		for (BsMatchWarnVO bsMatchWarnVO : list) {
			//告警
			/*specialJnlService.warn4FailNoSMS(null, "定时任务{债权匹配平均匹配金额超过"+limitAmount+"}平均值："
			+bsMatchWarnVO.getAvgAmount()+"；子账户id："+bsMatchWarnVO.getSubAccountId(), 
					null, "债权匹配平均匹配金额超固定值");*/
			log.info("==================【匹配金额告警】定时任务{债权匹配平均匹配金额超过"+limitAmount+"}平均值："
			+bsMatchWarnVO.getAvgAmount()+"；子账户id："+bsMatchWarnVO.getSubAccountId()+"=======================");
			flag = true;
		}
		if(flag){
			smsService.sendSysManagerMobiles("债权匹配平均匹配金额超固定值",true); //发送短信接口
		}
		
	}


	private void sumAmountCheckTask() {
		
		log.info("==================【某日匹配总金额和某日购买总金额不同告警】开始=======================");
		List<BsMatchWarnVO> list = bsMatchService.getMatchDiffBatchBuyAmount();
		boolean flag = false;
		double yunDaiAmount = 0;
		double qiDaiAmount = 0;
		for (BsMatchWarnVO bsMatchWarnVO : list) {
			//告警
			/*specialJnlService.warn4FailNoSMS(null, "定时任务{债权日匹配金额和日购买金额不等}匹配金额："
			+bsMatchWarnVO.getMatchAmount()+"；系统购买金额："+bsMatchWarnVO.getBatchBuyAmount()
			+"；购买时间："+DateUtil.formatYYYYMMDD(bsMatchWarnVO.getBuyTime()), 
					null, "债权日差"+ MoneyUtil.subtract(bsMatchWarnVO.getBatchBuyAmount(), bsMatchWarnVO.getMatchAmount()));*/
			
			log.info("==================【匹配金额告警】定时任务{债权日匹配金额和日购买金额不等}"+
					bsMatchWarnVO.getPropertySymbol()+"匹配金额："+bsMatchWarnVO.getMatchAmount()+
					"；系统购买金额："+bsMatchWarnVO.getBatchBuyAmount()
			+"；购买时间："+DateUtil.formatYYYYMMDD(bsMatchWarnVO.getBuyTime())+"=======================");
			if(Constants.PROPERTY_SYMBOL_YUN_DAI.equals(bsMatchWarnVO.getPropertySymbol())){
				yunDaiAmount = MoneyUtil.add(yunDaiAmount,
						MoneyUtil.subtract(bsMatchWarnVO.getBatchBuyAmount(), bsMatchWarnVO.getMatchAmount()).doubleValue()
						).doubleValue();
			}else if(Constants.PROPERTY_SYMBOL_7_DAI.equals(bsMatchWarnVO.getPropertySymbol())){
				qiDaiAmount = MoneyUtil.add(qiDaiAmount, MoneyUtil.subtract(bsMatchWarnVO.getBatchBuyAmount(), bsMatchWarnVO.getMatchAmount()).doubleValue()
						).doubleValue();
			}

			flag = true;
		}
		if(flag){
			smsService.sendProductOperatorMobiles("Y"+MoneyUtil.round(yunDaiAmount/10000)+"w:Q"
					+ MoneyUtil.round(qiDaiAmount/10000)+"w",true); //发送短信接口
		}
	}
}
