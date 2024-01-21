package com.pinting.business.dayend.task;

import com.pinting.business.dao.BsChannelCheckMapper;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
/**
 * 财务提现2小时后未确认，发送短信
 * @author bianyatian
 * @2016-1-7 下午7:30:45
 */
@Deprecated
public class ChannelConfirmedTask {
	private Logger log = LoggerFactory.getLogger(ChannelConfirmedTask.class);
	@Autowired
	private BsChannelCheckMapper bsChannelCheckMapper;
	@Autowired
	private BsSysConfigService bsSysConfigService;
	@Autowired
	private SMSServiceClient smsServiceClient;
	
	//判断bs_channel_check中当天的记录中，status的值如果为TRANSFERED （钱已到账）的时候，并且create_time和当前时间差为2小时以上时，触发一条短信（只发一条）。
	protected void execute() {
		log.info("=================【财务提现2小时后未确认】定时任务开始=====================");
		try {
			//1.判断bs_channel_check是否有当日的记录
			Date nowTime  = new Date();
			Date end = new Date(nowTime.getTime() - 3500*2000);//当前时间两小时前
    		String startDay = DateUtil.formatYYYYMMDD(nowTime) + " 00:00:00";
    		String endDay = DateUtil.format(end);
        	Integer count = bsChannelCheckMapper.countByStatusTime(Constants.CHANNEL_CHECK_STATUS_TRANSFERED, startDay, endDay);
        	if(count>0){
        		//2.发送短信
        		BsSysConfig bsSysConfigMobile = bsSysConfigService.findKey(Constants.FINANCE_MOBILE);
      	        String mobile = bsSysConfigMobile.getConfValue();
      	        smsServiceClient.sendTemplateMessage(mobile, TemplateKey.COMMON_EMERGENCY, "财务提现2小时后未确认");
        	}
		} catch (Exception e) {
			log.info("=================【财务提现2小时后未确认】定时任务异常=====================");
		}
		log.info("=================【财务提现2小时后未确认】定时任务结束=====================");
	}
	
}
