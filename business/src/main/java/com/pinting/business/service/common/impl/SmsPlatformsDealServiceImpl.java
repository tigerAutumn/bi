package com.pinting.business.service.common.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsSmsRecordJnlMapper;
import com.pinting.business.enums.SmsPlatformsCodeEnum;
import com.pinting.business.enums.SmsSignEnum;
import com.pinting.business.model.BsSmsRecord;
import com.pinting.business.model.BsSmsRecordJnl;
import com.pinting.business.service.common.SmsPlatformsDealService;
import com.pinting.business.service.manage.BsSmsRecordJnlService;
import com.pinting.business.service.site.BsSmsRecordService;
import com.pinting.business.util.CL253SmsUtil;
import com.pinting.business.util.Constants;
import com.pinting.business.util.MarketingSMSUtils;
import com.pinting.business.util.EMaySmsUtils.EMaySmsUtil;
import com.pinting.core.util.StringUtil;

@Service
public class SmsPlatformsDealServiceImpl implements SmsPlatformsDealService {

	@Autowired
	private BsSmsRecordService bsSmsRecordService;
	@Autowired
	private BsSmsRecordJnlMapper bsSmsRecordJnlMapper;
	@Autowired
	private BsSmsRecordJnlService bsSmsRecordJnlService;
	
	@Override
	public String emayNoticeSend(String mobile, String message, String addserial, Integer platformsId) {
		String beforeMessage = SmsSignEnum.getSmsSignEnumBySerial(addserial).getSign();
		//"4".equals(addserial) ? "【赞时贷】" : ("3".equals(addserial) ? "【赞分期】" : ("2".equals(addserial) ? "【达飞云贷】" : "【币港湾】"));
		Date sendTime = new Date();
		String smsReturn = EMaySmsUtil.sendSMS(mobile, beforeMessage+message, null, addserial);
		if(StringUtil.isBlank(smsReturn)){
			return null;
		}
		//短信提交到平台成功，记录短信记录表和流水表
		insertSmsRecord(mobile, beforeMessage+message, smsReturn, platformsId, 
				Constants.SMS_TYPE_BUSINESS, sendTime,SmsSignEnum.getSmsSignEnumBySerial(addserial).getPartnerCode());
		
		return smsReturn;
	}

	private void insertSmsRecord(String mobile, String message, String smsReturn, 
			Integer platformsId, String type, Date sendTime, String partnerCode) {
		// 查询该手机最近一次发送的短信信息
		BsSmsRecord bsSmsRecord = bsSmsRecordService.selectByMobile(mobile);
		BsSmsRecord smsRecordNew = new BsSmsRecord();
		// 短信内容不为空，判断表是否存在该用户发送的记录
		if (bsSmsRecord == null) {
			smsRecordNew.setTotalNum(1);
			smsRecordNew.setLastContent(message);
			smsRecordNew.setMobile(mobile);
			smsRecordNew.setLastSendTime(new Date());
			bsSmsRecordService.addSmsRecord(smsRecordNew);
		}else{
			smsRecordNew.setId(bsSmsRecord.getId());
			smsRecordNew.setTotalNum(1);
			smsRecordNew.setLastSendTime(new Date());
			bsSmsRecordService.updateByIncrement(smsRecordNew);
		}
		
		BsSmsRecordJnl smsRecordJnl = new BsSmsRecordJnl();
		smsRecordJnl.setContent(message);
		smsRecordJnl.setMobile(mobile);
		smsRecordJnl.setSendTime(sendTime);
		smsRecordJnl.setType(type);
		smsRecordJnl.setSerialNo(smsReturn);
		smsRecordJnl.setPlatformsId(platformsId);
		smsRecordJnl.setPartnerCode(partnerCode);
		bsSmsRecordJnlMapper.insertSelective(smsRecordJnl);
		
	}

	@Override
	public void emayCheck() {
		List<BsSmsRecordJnl> list =  new ArrayList<BsSmsRecordJnl>();
		//list.addAll(SMSUtils.checkSmsSend());//梦网返回
		list.addAll(EMaySmsUtil.checkSmsSend());//亿美返回
		while(list.size() != 0){
			for (BsSmsRecordJnl bsSmsRecordJnl : list) {
				if(StringUtils.isNotBlank(bsSmsRecordJnl.getMobile()) && 
						StringUtils.isNotBlank(bsSmsRecordJnl.getSerialNo())){
					BsSmsRecordJnl record = bsSmsRecordJnlService.selectByMobileSerNo(bsSmsRecordJnl.getMobile(), bsSmsRecordJnl.getSerialNo(),null);
					if(record == null){
						bsSmsRecordJnlService.insertJnl(bsSmsRecordJnl);
					}else{
						bsSmsRecordJnl.setId(record.getId());
						bsSmsRecordJnlService.updateJnl(bsSmsRecordJnl);
					}
				}
			}
			list =  EMaySmsUtil.checkSmsSend();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String chuanglan253Send(String mobile, String message,
			String addserial, Integer platformsId) {
		String beforeMessage = SmsSignEnum.getSmsSignEnumBySerial(addserial).getSign(); 
				//"4".equals(addserial) ? "【赞时贷】" : ("3".equals(addserial) ? "【赞分期】" : ("2".equals(addserial) ? "【达飞云贷】" : "【币港湾】"));
		Date sendTime = new Date();
		String smsReturn = CL253SmsUtil.sendSMS(mobile,  beforeMessage+message);
		//短信提交到平台成功，记录短信记录表和流水表
		insertSmsRecord(mobile, beforeMessage+message, smsReturn, platformsId,
				Constants.SMS_TYPE_BUSINESS, sendTime,SmsSignEnum.getSmsSignEnumBySerial(addserial).getPartnerCode());
				
		return smsReturn;
	}

	@Override
	public void chuanglan253Check(String mobile, String msgid, String status,
			Date time) {
		BsSmsRecordJnl record = bsSmsRecordJnlService.selectByMobileSerNo(mobile, msgid,SmsPlatformsCodeEnum.CL253);
		BsSmsRecordJnl jnlTemp = new BsSmsRecordJnl();
		jnlTemp.setArriveTime(time);
		jnlTemp.setMobile(mobile);
		jnlTemp.setSerialNo(msgid);
		jnlTemp.setStatusCode(0);
		jnlTemp.setStatusMsg(status);
		if(record == null){
			bsSmsRecordJnlService.insertJnl(jnlTemp);
		}else{
			jnlTemp.setId(record.getId());
			bsSmsRecordJnlService.updateJnl(jnlTemp);
		}
		
	}

	@Override
	public String emayMarketingSend(String mobile, String message,
			Integer platformsId) {
		Date sendTime = new Date();
		String smsReturn = EMaySmsUtil.sendSMSMarketing(mobile, "【币港湾】"+message);
		if(StringUtil.isBlank(smsReturn)){
			return null;
		}
		//短信提交到平台成功，记录短信记录表和流水表
		insertSmsRecord(mobile, "【币港湾】"+message, smsReturn, platformsId, Constants.SMS_TYPE_WELINK_MARKET, sendTime,
				SmsSignEnum.BGW.getPartnerCode());
		
		return smsReturn;
	}

	@Override
	public String dreamMarketingSend(String mobile, String message,
			Integer platformsId) {
		Date sendTime = new Date();
		String smsReturn = MarketingSMSUtils.sendSMS(mobile, "【币港湾】"+message);
		if(StringUtil.isBlank(smsReturn)){
			return null;
		}
		//短信提交到平台成功，记录短信记录表和流水表
		insertSmsRecord(mobile, "【币港湾】"+message, smsReturn, platformsId, Constants.SMS_TYPE_WELINK_MARKET, sendTime,
				SmsSignEnum.BGW.getPartnerCode());
		
		return smsReturn;
	}

}
