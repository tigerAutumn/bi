package com.pinting.schedule.dayend.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.service.site.RedPacketService;
import com.pinting.core.util.DateUtil;

/**
 * 生日福利发放红包
 * @author SHENGUOPING
 * @date  2018年8月1日 下午4:56:03
 */
@Service
public class BirthdayBenefitSendTask {

	private Logger log = LoggerFactory.getLogger(BirthdayBenefitSendTask.class);
    
	@Autowired
	private RedPacketService redPacketService;
	
	/**
	 * 获取定时当日的月天，转化为4位数字，例如0521，筛选出身份证号为对应数字的用户
	 */
	public void execute() {
		log.info(">>>>>start【生日福利红包】发放定时任务 start<<<<<");
		String dateTime = DateUtil.formatDateTime(new Date(), "MMdd");
		redPacketService.happyBirthday(dateTime);
		log.info(">>>>>end【生日福利红包】发放定时任务 end<<<<<");
	}
	
}
