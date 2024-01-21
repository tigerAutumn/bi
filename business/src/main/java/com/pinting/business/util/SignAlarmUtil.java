package com.pinting.business.util;

import java.util.Date;
import javax.annotation.PostConstruct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.service.site.SMSService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.StringUtil;
/**
 * 
 * @author Dragon & cat
 *
 */
@Component
public class SignAlarmUtil {
	
	private static Logger logger = LoggerFactory.getLogger(SignAlarmUtil.class);  
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
    private  static  SignAlarmUtil signAlarmUtil; 
	@Autowired
	private  SMSService smsService;
    @Autowired
    private  SpecialJnlService specialJnlService;
	
	//失败次数记录()
	private static  Integer failCount = 0;
	//记录失败第一次的时间
	private static Date firstTime = null;
	//产生失败记录最高次数(必须大于1)
	//private static Integer failMaxCount = 10;
	private static Integer failMaxCount = 5;
	//达到最高失败次数相差时间阈值（单位：秒）
	//private static Integer failMaxTimeSec = 60;
	private static Integer failMaxTimeSec = 60*3;
	
	//记录连续失败
	private static Integer serialCount = 0;
	//连续失败触发告警最高次数
	//private static Integer maxSerialFailCount = 5;
	private static Integer maxSerialFailCount = 3;
	
	//上次告警时间
	private static Date lastAlarmTime = null;
	//距离上次告警相差时间大于此时间方可进行再次告警
	//private static Integer maxAlarmDiffTime = 60 * 2;
	private static Integer maxAlarmDiffTime = 30;
	
	@PostConstruct 
    public void init() { 
		signAlarmUtil = this; 
		signAlarmUtil.smsService = this.smsService; 
		signAlarmUtil.specialJnlService = this.specialJnlService;
    } 

	public void setSmsService(SMSService smsService) {
		this.smsService = smsService;
	}

	public void setSpecialJnlService(SpecialJnlService specialJnlService) {
		this.specialJnlService = specialJnlService;
	}

	/**
	 * 1、状态为成功，记录相应记录
	 * 
	 * 2、失败分为两类失败
	 *   2.1  固定时间段内失败次数
	 * 
	 * 	 2.2 连续失败次数
	 * 
	 * 3、告警    检查今天内是否存在过告警，如果今天已经进行过告警，那么看上次告警到现在是否超过两个小时
	 * 
	 * @param type  签章类型（此字段暂时没用，传null表示针对所有签章）
	 * @param result 是否签章成功
	 */
	public  synchronized  static void alarm(boolean result) {
		
		
		Long len = jsClientDaoSupport.llen("sign_alarm_map");
		
		if (len != null && len > 0) {
			
        	String signAlarmItem = jsClientDaoSupport.lpop("sign_alarm_map");
        	if(StringUtil.isEmpty(signAlarmItem)){
        		logger.warn("redis数据为空");
        		return;
        	}
        	JSONObject json = JSON.parseObject(signAlarmItem);
        	
        	
        	logger.info(">>>正在处理redis数据" + json.toJSONString() + "<<<");

        	failCount = json.getInteger("failCount");
        	firstTime = json.getDate("firstTime");
        	lastAlarmTime = json.getDate("lastAlarmTime");
        	serialCount = json.getInteger("serialCount");
		}
		
		logger.info("此次签章结果："+result);
		String startFirstTime = firstTime == null ? "-" : DateUtil.format(firstTime, "yyyy-MM-dd HH:mm:ss");
		logger.info("S失败次数为"+failCount+";此时第一次失败的时间为：" + startFirstTime);
		logger.info("S已经连续失败"+serialCount+"次");
		String startLastAlarmTime = lastAlarmTime == null ? "-" : DateUtil.format(lastAlarmTime, "yyyy-MM-dd HH:mm:ss");
		logger.info("S上次告警时间为："+startLastAlarmTime );
		//状态是失败
		if (!result) {
			//产生固定失败次数所需时间比率判断
			failCount = failCount + 1;
			if (failCount == 1) {
				firstTime = new Date();
			}
			
			if (failCount >= failMaxCount) {
				Date currentTime = new Date();
				int c =  (int)((currentTime.getTime() - firstTime.getTime()) /  1000);
				
				if (c <= failMaxTimeSec) {
					if (lastAlarmTime == null) {
						lastAlarmTime = new Date();
						
						signAlarmUtil.specialJnlService.warn4Fail(0.0, "签章在"+c+"秒内失败"+failCount+"次告警",
			                    null,"签章高频失败告警",false);
					}else {
						int alarmC =  (int)((currentTime.getTime() - lastAlarmTime.getTime()) /  1000);
						if (alarmC > maxAlarmDiffTime) {
							lastAlarmTime = new Date();
							signAlarmUtil.specialJnlService.warn4Fail(0.0, "签章在"+c+"秒内失败"+failCount+"次告警",
				                    null,"签章高频失败告警",false);
						}
					}
				}
				
				//回到初始状态
				failCount = 0;
				firstTime = new Date();
			}
			
			serialCount = serialCount +1;
			
			if (serialCount >= maxSerialFailCount ) {
				Date currentTime = new Date();
				serialCount = 0;
				if (lastAlarmTime == null) {
					lastAlarmTime = new Date();
					//告警
					signAlarmUtil.specialJnlService.warn4Fail(0.0, "签章连续失败"+maxSerialFailCount+"告警",
		                    null,"签章连续失败告警",false);
					
				}else {
					int alarmC =  (int)((currentTime.getTime() - lastAlarmTime.getTime()) /  1000);
					if (alarmC > maxAlarmDiffTime) {
						lastAlarmTime = new Date();
						signAlarmUtil.specialJnlService.warn4Fail(0.0, "签章连续失败"+maxSerialFailCount+"告警",
			                    null,"签章连续失败告警",false);
					}
				}
			}
			
		}else{
			//产生固定失败次数所需时间比率(成功时候不进行任何操作)
			
			//连续失败次数记录
			//若状态为成功那么连续记录回复初始状态
			serialCount = 0;
		}
		
		

		
		logger.info("-----------------------");
		String endFirstTime = firstTime == null ? "-" : DateUtil.format(firstTime, "yyyy-MM-dd HH:mm:ss");
		logger.info("E失败次数为"+failCount+";此时第一次失败的时间为："+endFirstTime);
		logger.info("E已经连续失败"+serialCount+"次");
		String endLastAlarmTime = lastAlarmTime == null ? "" : DateUtil.format(lastAlarmTime, "yyyy-MM-dd HH:mm:ss");
		logger.info("E上次告警时间为："+endLastAlarmTime );
		
		JSONObject signAlarmObject = new JSONObject();
		signAlarmObject.put("failCount", String.valueOf(failCount));
		signAlarmObject.put("firstTime", firstTime == null ? null : firstTime);
		signAlarmObject.put("serialCount", String.valueOf(serialCount));
		signAlarmObject.put("lastAlarmTime", lastAlarmTime == null ? null : lastAlarmTime);
		jsClientDaoSupport.rpush("sign_alarm_map", signAlarmObject.toString());
	}
	

}
