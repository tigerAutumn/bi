package com.pinting.site.common.interceptor.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.site.common.interceptor.AbnormalRequestInterceptor;
/**
 * 定时 清除 当天由于访问频繁引起的 已告警IP 名单
 * @Project: site-java
 * @Title: AbnormalRequestAlarmTask.java
 * @author dingpf
 * @date 2015-2-2 下午8:13:58
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class AbnormalRequestAlarmTask {
	private Logger log = LoggerFactory.getLogger(AbnormalRequestAlarmTask.class);
	protected void execute()  {  
		//当天 已告警 ip 列表
		List<String> alarmedHostList = AbnormalRequestInterceptor.alarmedHostList;
		log.debug("================当天 已告警IP 清除前  记录名单：" + alarmedHostList + "================");
		alarmedHostList.clear();
    }  

}
