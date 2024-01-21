package com.pinting.business.dayend.task;

import com.pinting.business.dao.BsChannelCheckMapper;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * 每日早上10点（配置）有其他渠道的资金，但还未做转账（判断条件是bs_channel_check如果没有当天的记录），则提醒财务渠道转账短信（边）  
 * @author bianyatian
 * @2016-1-7 下午5:00:44
 */
@Deprecated
public class ChannelTransferTask {
	private Logger log = LoggerFactory.getLogger(ChannelTransferTask.class);
	@Autowired
    private BsPayOrdersMapper bsPayOrdersMapper;
	@Autowired
	private BsSysConfigService bsSysConfigService;
	@Autowired
	private BsChannelCheckMapper bsChannelCheckMapper;
	@Autowired
	private SMSServiceClient smsServiceClient;
	
	/**
	 * 1.判断前一日非达飞、非19pay订单的充值和购买金额总额是否大于0
	 * 2.判断时间是否已到配置的提现时间
	 * 3.判断bs_channel_check是否有当日的记录
	 * 4.发送短信
	 */
	protected void execute() {
		log.info("=================【提醒财务渠道转账】定时任务开始=====================");
		try {
			// 1.判断前一日非达飞、非19pay订单的充值和购买金额总额是否大于0
			// 1.1 获取可提现金额(非达飞、非19pay交易金额：buyAmount昨日购买，rechargeAmount昨日充值 )
	        Map<String, Object> map = bsPayOrdersMapper.selectFinanceWithdrawAmount(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -1)), DateUtil.formatYYYYMMDD(new Date()), null);
	        if(map != null){
	        	Double buyAmount = (Double)map.get("buyAmount") == null ? 0d : (Double)map.get("buyAmount");
		        Double rechargeAmount = (Double)map.get("rechargeAmount") == null ? 0d : (Double)map.get("rechargeAmount");
		        Double amount = MoneyUtil.add(buyAmount, rechargeAmount).doubleValue();
		        log.info("=================前一日非达飞、非19pay订单的充值和购买金额总额:"+amount+"=====================");
		        if( amount.compareTo(0d) > 0 ){
		        	/* //2.判断时间是否已到配置的提现时间
			        BsSysConfig bsSysConfigCheckTime = bsSysConfigService.findKey(Constants.CHANNEL_CHECK_TIME);
			        Date checkTime = DateUtil.parseDateTime(bsSysConfigCheckTime.getConfValue());
			        
			        log.info("=================当前时间："+nowTime+",配置提醒时间："+checkTime+"=====================");
			        if(nowTime.getTime()>= checkTime.getTime()){*/
		        	
			        //3.判断bs_channel_check是否有当日的记录
		        	Date nowTime  = new Date();
			    	String startDay = DateUtil.formatYYYYMMDD(nowTime)+" 00:00:00";
			    	String endDay = DateUtil.formatYYYYMMDD(nowTime)+" 23:59:59";
			        Integer count = bsChannelCheckMapper.countByStatusTime(null, startDay, endDay);
			        log.info("=================bs_channel_check当日记录的条数："+count+"=====================");
			        count = count == null ? 0: count;
			        if(count == 0){
			        		//4.发送短信
			        	BsSysConfig bsSysConfigMobile = bsSysConfigService.findKey(Constants.FINANCE_MOBILE);
			      	    String mobile = bsSysConfigMobile.getConfValue();
			      	    smsServiceClient.sendTemplateMessage(mobile, TemplateKey.COMMON_EMERGENCY, "财务提现未处理提醒");
			        	//}
			        }
		        }
	        }

		} catch (Exception e) {
			log.error("=================【提醒财务渠道转账】定时任务异常=====================");
		}
		log.info("=================【提醒财务渠道转账】定时任务结束=====================");
	}

}
