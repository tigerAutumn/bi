package com.pinting.business.dayend.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.model.vo.ProductInformVO;
import com.pinting.business.service.site.BsProductInformService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;

/**
 * 周周乐活动-短信提醒
 * @author bianyatian
 * @2017-8-3 下午2:36:49
 */
@Service
public class WeekhayActivityRemindTask {

	private Logger log = LoggerFactory.getLogger(WeekhayActivityRemindTask.class);
	@Autowired
	private BsProductInformService bsProductInformService;
	@Autowired
    private SMSServiceClient smsServiceClient;
	
	public void execute() {
		/**
		 * 校验当前时间是否是周四:9:55-10:00,13:55-14:00,19:55-20:00
		 * 根据时间，查询对应的需要通知的列表
		 * 发送对应文案的短信
		 */
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Integer week = cal.get(Calendar.DAY_OF_WEEK)-1;
		if(week == Constants.WEEKHAY_WEEK_4){
			
			Date now = new Date();
			String nowDateStr = DateUtil.formatDateTime(now, DateUtil.DATE_FORMAT); //当前日期
			
			Date wednesday_start = DateUtil.addDays(DateUtil.parseDateTime(nowDateStr + " 00:00:00"),-1);
			Date start_time_1 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_9_55_00);
			Date end_time_1 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_10_00_00);
			Date start_time_2 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_13_55_00);
			Date end_time_2 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_14_00_00);	
			Date start_time_3 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_19_55_00);
			Date end_time_3 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_20_00_00);	
			
			if(now.compareTo(start_time_1)>=0 && now.compareTo(end_time_1) <0){
				//当前时间在第一段开始前，查询列表时间为小于第一段开始时间，大于等于周三0点（起始时间）
				List<ProductInformVO> list = bsProductInformService.getListByTime(wednesday_start, start_time_1, Constants.REMIND_TYPE_WEEKACTIVITY);
				if(CollectionUtils.isNotEmpty(list)){
					for (ProductInformVO productInformVO : list) {
						try {
							smsServiceClient.sendTemplateMessage(productInformVO.getMobile(), TemplateKey.WEEKHAY_ACTIVITY_REMIND_10CLOCK);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}else if(now.compareTo(start_time_2)>=0 && now.compareTo(end_time_2) <0){
				//当前时间在第二段开始前，查询列表时间为小于第二段开始时间，大于等于周三0点（起始时间）
				List<ProductInformVO> list = bsProductInformService.getListByTime(wednesday_start, start_time_2, Constants.REMIND_TYPE_WEEKACTIVITY);
				if(CollectionUtils.isNotEmpty(list)){
					for (ProductInformVO productInformVO : list) {
						try {
							smsServiceClient.sendTemplateMessage(productInformVO.getMobile(), TemplateKey.WEEKHAY_ACTIVITY_REMIND_14CLOCK);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}else if(now.compareTo(start_time_3)>=0 && now.compareTo(end_time_3) <0){
				//当前时间在第三段开始前，查询列表时间为小于第三段开始时间，大于等于周三0点（起始时间）
				List<ProductInformVO> list = bsProductInformService.getListByTime(wednesday_start, start_time_3, Constants.REMIND_TYPE_WEEKACTIVITY);
				if(CollectionUtils.isNotEmpty(list)){
					for (ProductInformVO productInformVO : list) {
						try {
							smsServiceClient.sendTemplateMessage(productInformVO.getMobile(), TemplateKey.WEEKHAY_ACTIVITY_REMIND_20CLOCK);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
