package com.pinting.business.dayend.task;

import com.pinting.business.dao.BsSmsPlatformsConfigMapper;
import com.pinting.business.dao.BsSmsRecordJnlMapper;
import com.pinting.business.enums.SmsPlatformsCodeEnum;
import com.pinting.business.model.BsSmsPlatformsConfig;
import com.pinting.business.model.BsSmsPlatformsConfigExample;
import com.pinting.business.model.BsSmsRecordJnl;
import com.pinting.business.service.common.SmsPlatformsDealService;
import com.pinting.business.service.manage.BsSmsRecordJnlService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.EMaySmsUtils.EMaySmsUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看发送状态并记录
 * @author bianyatian
 * @2016-3-7 下午4:11:44
 */
@Service
public class SendSmCheckTask {
	private Logger log = LoggerFactory.getLogger(SendSmCheckTask.class);
	
	@Autowired
	private BsSmsPlatformsConfigMapper bsSmsPlatformsConfigMapper;
	@Autowired
	private SmsPlatformsDealService smsPlatformsSendService; 
	@Autowired
	private BsSmsRecordJnlMapper bsSmsRecordJnlMapper;
	@Autowired
	private SpecialJnlService specialJnlService;
	
	public void execute() {
		noticeCheck();
		
		
		
	}

	private void noticeCheck() {
		//查询短信平台配置表按照优先级正序排序，且平台编码和优先级不为空
		BsSmsPlatformsConfigExample priorityExample = new BsSmsPlatformsConfigExample();
		priorityExample.createCriteria().andPlatformsCodeIsNotNull().andPriorityIsNotNull();
		priorityExample.setOrderByClause("priority asc");
		List<BsSmsPlatformsConfig> priorityList = bsSmsPlatformsConfigMapper.selectByExample(priorityExample);
		
		if(CollectionUtils.isNotEmpty(priorityList)){
			int sendTimes=0;//调用平台的次数
			for (BsSmsPlatformsConfig bsSmsPlatformsConfig : priorityList) {
				if(bsSmsPlatformsConfig.getPlatformsCode().equals(SmsPlatformsCodeEnum.EMay.getCode())){
					try {
						//调用亿美平台短信状态查询
						smsPlatformsSendService.emayCheck();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				if(sendTimes == 0){
					/**
					 * 优先级最高的:
					 * 1）查询bs_sms_record_jnl发送时间在60分钟内且有状态的条数在20条以上，成功率低于80%时告警
					 * 2）查询bs_sms_record_jnl最近有状态的100条短信数据，成功率低于80%时告警
					 */
					Double succLimitRate=0.8;
					Double succRateByMinute = bsSmsRecordJnlMapper.succRateByMinute(bsSmsPlatformsConfig.getId(), 60, 20);
					Double succRateByNum = bsSmsRecordJnlMapper.succRateByNum(bsSmsPlatformsConfig.getId(), 100);
					if(succRateByMinute >0 && succRateByMinute.compareTo(succLimitRate) < 0){
						//告警
						specialJnlService.warn4Fail(null, bsSmsPlatformsConfig.getPlatformsName()+"短信平台60分钟内成功率为："+succRateByMinute, null, "短信平台60m内成功率："+succRateByMinute, true);
					}
					
					if(succRateByNum >0 && succRateByNum.compareTo(succLimitRate) < 0){
						//告警
						specialJnlService.warn4Fail(null, bsSmsPlatformsConfig.getPlatformsName()+"短信平台当日近100条成功率为："+succRateByMinute, null, "短信平台当日近100条成功率："+succRateByNum, true);
					}
					
				}
				sendTimes++;
			}
			
		}
		
	}
}
