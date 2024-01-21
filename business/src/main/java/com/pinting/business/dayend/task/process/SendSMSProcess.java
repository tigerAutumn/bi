package com.pinting.business.dayend.task.process;

import com.pinting.business.dao.BsSmsPlatformsConfigMapper;
import com.pinting.business.dao.BsSmsRecordJnlMapper;
import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.dayend.task.SendSmTask;
import com.pinting.business.enums.SmsPlatformsCodeEnum;
import com.pinting.business.enums.SmsSignEnum;
import com.pinting.business.model.BsSmsPlatformsConfig;
import com.pinting.business.model.BsSmsPlatformsConfigExample;
import com.pinting.business.model.BsSmsRecord;
import com.pinting.business.model.BsSmsRecordJnl;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.SmsMapVO;
import com.pinting.business.service.common.SmsPlatformsDealService;
import com.pinting.business.service.site.BsSmsRecordService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.SMSUtils;
import com.pinting.business.util.EMaySmsUtils.EMaySmsUtil;
import com.pinting.core.util.StringUtil;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.awt.TrayIcon.MessageType;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 根据短信平台配置表的优先级，选择发送方，若发送失败则优先级往下继续发送
 * @author bianyatian
 * @2017-6-19 下午2:08:01
 */
public class SendSMSProcess implements Runnable {
	private Logger log = LoggerFactory.getLogger(SendSMSProcess.class);
	private BsSmsRecordJnlMapper bsSmsRecordJnlMapper;
	private String message;
	private String mobile;
	private String addserial;
	private Integer messageType; // messageType短信类型：1通知类短信，2营销短信
	private BsSmsPlatformsConfigMapper bsSmsPlatformsConfigMapper;
	private SmsPlatformsDealService smsPlatformsSendService;
	private BsSysConfigMapper bsSysConfigMapper;
	private BsSmsRecordService bsSmsRecordService;

	@Override
	public void run() {
		if (StringUtil.isNotBlank(message)) {
			//查看短信类型，通知或营销
			if(messageType == 1){
				noticeMessage();
			}else{
				marketingMessage();
			}
			
			
			
		}
	}
	
	private void marketingMessage() {
		//查询短信平台配置表按照优先级正序排序，且平台编码和优先级不为空
		BsSmsPlatformsConfigExample priorityExample = new BsSmsPlatformsConfigExample();
		priorityExample.createCriteria().andPlatformsCodeIsNotNull().andPriorityIsNotNull()
		.andPlatformsTypeEqualTo(Constants.MESSAGE_TYPE_MARKETING);
		priorityExample.setOrderByClause("priority asc");
		List<BsSmsPlatformsConfig> priorityList = bsSmsPlatformsConfigMapper.selectByExample(priorityExample);
		
		if(CollectionUtils.isNotEmpty(priorityList)){
			int sendTimes=0;//调用平台的次数
			for (BsSmsPlatformsConfig bsSmsPlatformsConfig : priorityList) {
				if(bsSmsPlatformsConfig.getPlatformsCode().equals(SmsPlatformsCodeEnum.EMay.getCode())){
					//调用亿美平台
					String smsReturn = smsPlatformsSendService.emayMarketingSend(mobile, message, bsSmsPlatformsConfig.getId());
					if(StringUtil.isNotBlank(smsReturn)){
						//发送成功，做处理
						dealMap(mobile);
						break;
					}
				}
				if(bsSmsPlatformsConfig.getPlatformsCode().equals(SmsPlatformsCodeEnum.DREAM56.getCode())){
					//调用56梦网营销平台
					String smsReturn = smsPlatformsSendService.dreamMarketingSend(mobile, message, bsSmsPlatformsConfig.getId());
					if(StringUtil.isNotBlank(smsReturn) || !(smsReturn.startsWith("error"))){
						//发送成功，做处理
						dealMap(mobile);
						break;
					}
						
				}
				sendTimes++;
				//最后失败记录流水
				if(priorityList.size() == sendTimes){
					BsSmsRecordJnl smsRecordJnl = new BsSmsRecordJnl();
					smsRecordJnl.setContent("【币港湾】"+message);
					smsRecordJnl.setMobile(mobile);
					smsRecordJnl.setSendTime(new Date());
					smsRecordJnl.setType(Constants.SMS_TYPE_BUSINESS);
					smsRecordJnl.setSerialNo("发送至平台全部失败");
					smsRecordJnl.setPartnerCode(SmsSignEnum.BGW.getPartnerCode());
					bsSmsRecordJnlMapper.insertSelective(smsRecordJnl);
				}
				
			}
		}
		
	}
	

	private void noticeMessage() {
		//查询短信平台配置表按照优先级正序排序，且平台编码和优先级不为空
		BsSmsPlatformsConfigExample priorityExample = new BsSmsPlatformsConfigExample();
		priorityExample.createCriteria().andPlatformsCodeIsNotNull().andPriorityIsNotNull()
		.andPlatformsTypeEqualTo(Constants.MESSAGE_TYPE_NOTICE);
		priorityExample.setOrderByClause("priority asc");
		List<BsSmsPlatformsConfig> priorityList = bsSmsPlatformsConfigMapper.selectByExample(priorityExample);
		String beforeMessage = SmsSignEnum.getSmsSignEnumBySerial(addserial).getSign();
		//"4".equals(addserial) ? "【赞时贷】" : ("3".equals(addserial) ? "【赞分期】" : ("2".equals(addserial) ? "【达飞云贷】" : "【币港湾】"));
		
		
		//短信内容
		String content = beforeMessage+message;
		if(CollectionUtils.isNotEmpty(priorityList) && sendBeforeCheck(mobile,content)){
			int sendTimes=0;//调用平台的次数
			for (BsSmsPlatformsConfig bsSmsPlatformsConfig : priorityList) {
				if(bsSmsPlatformsConfig.getPlatformsCode().equals(SmsPlatformsCodeEnum.EMay.getCode())){
					//调用亿美平台
					String smsReturn = smsPlatformsSendService.emayNoticeSend(mobile, message, addserial, bsSmsPlatformsConfig.getId());
					if(StringUtil.isNotBlank(smsReturn)){
						//发送成功，做处理
						dealMap(mobile);
						break;
					}
					log.info("==========【亿美】短信发送提交失败=================");
				}
				if(bsSmsPlatformsConfig.getPlatformsCode().equals(SmsPlatformsCodeEnum.CL253.getCode())){
					//调用创蓝253平台
					String smsReturn = smsPlatformsSendService.chuanglan253Send(mobile, message, addserial, bsSmsPlatformsConfig.getId());
					if(StringUtil.isNotBlank(smsReturn) && !(smsReturn.startsWith("error"))){
						//发送成功，做处理
						dealMap(mobile);
						break;
					}
					log.info("==========【创蓝】短信发送提交失败=================");	
				}
				sendTimes++;
				//最后失败记录流水
				if(priorityList.size() == sendTimes){
					BsSmsRecordJnl smsRecordJnl = new BsSmsRecordJnl();
					smsRecordJnl.setContent(content);
					smsRecordJnl.setMobile(mobile);
					smsRecordJnl.setSendTime(new Date());
					smsRecordJnl.setType(Constants.SMS_TYPE_BUSINESS);
					smsRecordJnl.setSerialNo("发送至平台全部失败");
					smsRecordJnl.setPartnerCode(SmsSignEnum.getSmsSignEnumBySerial(addserial).getPartnerCode());
					bsSmsRecordJnlMapper.insertSelective(smsRecordJnl);
				}
				
			}
		}
		
	}
	
	
	



	private boolean sendBeforeCheck(String mobile, String content) {
		BsSysConfig bsSysConfig = bsSysConfigMapper
				.selectByPrimaryKey(Constants.SMS_SEND_TIME);
		Integer intervalTime = Integer.valueOf(bsSysConfig != null ? bsSysConfig.getConfValue() : "30");
		// 记录短信最后发送信息
		BsSmsRecord bsSmsRecord = bsSmsRecordService.selectByMobile(mobile);
		// 短信内容不为空，判断表是否存在该用户发送的记录
		if (bsSmsRecord == null) {
			log.info("==========手机号：" + mobile
					+ "不存在短信发送记录，直接发送并存表==========");
			// 未曾发送短信，直接发送，新增短信记录表
			BsSmsRecord smsRecord = new BsSmsRecord();
			smsRecord.setLastContent(content);
			smsRecord.setMobile(mobile);
			smsRecord.setTotalNum(1);
			smsRecord.setLastSendTime(new Date());
			bsSmsRecordService.addSmsRecord(smsRecord);
			return true;
		}else {
			Date now = new Date();
			// 判断现在和上次发送时间是否相差30秒以上，是则发送短信，并修改短信记录表，否则不处理
			if (now.getTime() - bsSmsRecord.getLastSendTime().getTime() > intervalTime * 1000) {
				log.info("==========手机号：" + mobile
						+ "存有记录，时间相差30秒以上，发送短信并修改记录==========");
				BsSmsRecord smsRecord = new BsSmsRecord();
				smsRecord.setId(bsSmsRecord.getId());
				smsRecord.setLastContent(content);
				smsRecord.setTotalNum(1);
				bsSmsRecordService.updateByIncrement(smsRecord);
				return true;
			} else {
				log.info("==========手机号：" + mobile + "时间未到，不做处理==========");
				return false;
			}
		}
	}

	/**
	 * 对map和list做相应处理
	 * 
	 * @param mobile
	 */
	private void dealMap(String mobile) {
		LinkedList<SmsMapVO> list = SendSmTask.map.get(mobile);
		if (list != null) {
			if (list.size() > 1) {
				// list中的值不止1个时，只删除list中的值,覆盖map
				list.remove(0);
				SendSmTask.map.put(mobile, list);
			} else {
				// list中的值只有一个时，删除键值对
				list.remove(0);
				SendSmTask.clearMapList.add(mobile);

			}
		}
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setBsSmsRecordJnlMapper(
			BsSmsRecordJnlMapper bsSmsRecordJnlMapper) {
		this.bsSmsRecordJnlMapper = bsSmsRecordJnlMapper;
	}

	public void setAddserial(String addserial) {
		this.addserial = addserial;
	}

	public void setBsSmsPlatformsConfigMapper(
			BsSmsPlatformsConfigMapper bsSmsPlatformsConfigMapper) {
		this.bsSmsPlatformsConfigMapper = bsSmsPlatformsConfigMapper;
	}

	public void setSmsPlatformsSendService(
			SmsPlatformsDealService smsPlatformsSendService) {
		this.smsPlatformsSendService = smsPlatformsSendService;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public void setBsSysConfigMapper(BsSysConfigMapper bsSysConfigMapper) {
		this.bsSysConfigMapper = bsSysConfigMapper;
	}

	public void setBsSmsRecordService(BsSmsRecordService bsSmsRecordService) {
		this.bsSmsRecordService = bsSmsRecordService;
	}
	
}
