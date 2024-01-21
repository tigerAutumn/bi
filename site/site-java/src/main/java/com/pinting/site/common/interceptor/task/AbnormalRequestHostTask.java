package com.pinting.site.common.interceptor.task;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.hessian.site.message.ReqMsg_SMS_SendMobiles;
import com.pinting.business.hessian.site.message.ReqMsg_System_AddSensitiveJnl;
import com.pinting.business.hessian.site.message.ResMsg_SMS_SendMobiles;
import com.pinting.business.hessian.site.message.ResMsg_System_AddSensitiveJnl;
import com.pinting.core.util.ConstantUtil;
import com.pinting.site.common.interceptor.AbnormalRequestInterceptor;
import com.pinting.site.common.interceptor.bean.AbnormalRequestAlarmInfo;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;
/**
 * 定时 清除 该时间段所有访问IP名单，对于当天未警示的告警信息 进行手机信息告警，并保存告警信息于异动告警表
 * @Project: site-java
 * @Title: AbnormalRequestTask.java
 * @author dingpf
 * @date 2015-2-2 下午3:19:57
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class AbnormalRequestHostTask {
	private Logger log = LoggerFactory.getLogger(AbnormalRequestHostTask.class);
	@Autowired
	private CommunicateBusiness siteService;
	
	protected void execute()  {  
		//该时间段 所有访问ip名单
		HashMap<String, Integer> hostMap = AbnormalRequestInterceptor.hostMap;
		//log.debug("================该时间段所有访问IP 清除前  记录名单：" + hostMap + "================");
		hostMap.clear();
		
		//该时间段 待告警ip名单
		HashMap<String, AbnormalRequestAlarmInfo> alarmHostMap = AbnormalRequestInterceptor.alarmHostMap;
		//当天 已告警 ip 列表
		List<String> alarmedHostList = AbnormalRequestInterceptor.alarmedHostList;
		
		Collection<AbnormalRequestAlarmInfo> alarmHosts = alarmHostMap.values();
		if(alarmHosts != null && alarmHosts.size() >0 ){
			StringBuffer message = new StringBuffer("警告：IP");
			for (AbnormalRequestAlarmInfo alarm : alarmHosts) {
				if(!alarmedHostList.contains(alarm.getIp())){
					message.append("[").append(alarm.getIp()).append("]");
					//插入异动告警表
					addSensitiveJnl(alarm);
				}
			}
			//message.append("，访问过于频繁！请务必警惕！详情请登录微网站管理平台进行查看！");该短信模板已改
			// 发送告警短信
			if(message.indexOf("[") != -1){
				boolean isSendMobileSucc = sendEmergencyMobile(message.toString());
				if(isSendMobileSucc){
					String subMsg = message.substring(message.indexOf("[")+1, message.lastIndexOf("]"));
					String[] ips = subMsg.split("\\]\\[");
					if(ips != null && ips.length > 0){
						for (String ip : ips) {
							AbnormalRequestInterceptor.alarmedHostList.add(ip);
						}
					}
				}
			}
		}
		
		log.debug("================该时间段待告警IP 清除前  记录名单：" + hostMap + "================");
		alarmHostMap.clear();
    }
	
	/**
	 * 异动告警表信息插入
	 * 
	 * @param alarm
	 *            告警信息
	 * @return
	 */
	protected boolean addSensitiveJnl(AbnormalRequestAlarmInfo alarm) {
		ReqMsg_System_AddSensitiveJnl reqMsg = new ReqMsg_System_AddSensitiveJnl();
		reqMsg.setInfo(alarm.getInfo());
		reqMsg.setIp(alarm.getIp());
		reqMsg.setOpType(alarm.getOpType());
		reqMsg.setTerminal(alarm.getTerminal());
		reqMsg.setTime(alarm.getTime());
		reqMsg.setUserId(alarm.getUserId().isEmpty() ? null : Integer.valueOf(alarm
				.getUserId()));
		reqMsg.setUserName(alarm.getUserName());
		ResMsg_System_AddSensitiveJnl resMsg = (ResMsg_System_AddSensitiveJnl) siteService
				.handleMsg(reqMsg);
		if (ConstantUtil.BSRESCODE_FAIL.equals(resMsg.getResCode())) {
			log.error("================警告：IP【" + alarm.getIp() + "】告警信息【"
					+ alarm.getInfo() + "】保存数据库失败！！！================");
			return false;
		}
		return true;
	}

	/**
	 * 内部手机告警信息发送
	 * 
	 * @param message
	 *            告警信息文本
	 * @return
	 */
	protected boolean sendEmergencyMobile(String message) {
		ReqMsg_SMS_SendMobiles reqMsg = new ReqMsg_SMS_SendMobiles();
		message = "IP访问过于频繁";
		reqMsg.setMessage(message);
		reqMsg.setMobileType(Constants.MOBILE_TYPE_EMERGENCY);
		ResMsg_SMS_SendMobiles resMsg = (ResMsg_SMS_SendMobiles) siteService
				.handleMsg(reqMsg);
		if (ConstantUtil.BSRESCODE_FAIL.equals(resMsg.getResCode())) {
			log.error("================警告：内部手机【" + resMsg.getFailedMobiles()
					+ "】告警信息【" + message + "】发送失败！！！================");
			return false;
		}
		return true;
	}

}
