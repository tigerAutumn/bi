package com.pinting.gateway.out.service.impl;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.dao.BsSmsRecordJnlMapper;
import com.pinting.business.dayend.task.SendSmTask;
import com.pinting.business.enums.SmsSignEnum;
import com.pinting.business.model.BsSmsRecordJnl;
import com.pinting.business.model.vo.SmsMapVO;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.MarketingSMSUtils;
import com.pinting.business.util.SMSUtils;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;

@Service("smsServiceClient")
public class SMSServiceClientImpl implements SMSServiceClient {

	private Logger log = LoggerFactory.getLogger(SMSServiceClientImpl.class);
	@Autowired
	private BsSmsRecordJnlMapper bsSmsRecordJnlMapper;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private SpecialJnlService specialJnlService;
	
	@Override
	public boolean sendTemplateMessage(String mobile, TemplateKey templateKey,
			String... messages) {
		mobile = mobile.replaceAll("，", ",");
		String[] mobileArr = mobile.split(",");
		if(mobileArr.length >0){
			for(String phone : mobileArr){
				//BsSmsRecord bsSmsRecord = bsSmsRecordService.selectByMobile(mobile);
				Map<String, LinkedList<SmsMapVO>> map = SendSmTask.map;
				LinkedList<SmsMapVO> list = map.get(phone);
				String message = String.format(templateKey.getTemplate(), messages).toString();
				//判断手机号长度和是否为1开头
				if(StringUtil.isNotBlank(phone) && phone.length()== 11 && phone.startsWith("1")){
					if(map.get(phone) != null){
						list = map.get(phone);
						SmsMapVO vo = new SmsMapVO();
						vo.setMessage(message);
						vo.setMessageType(templateKey.getMessageType());
						list.addLast(vo);
					}else{
						list = new LinkedList<SmsMapVO>();
						SmsMapVO vo = new SmsMapVO();
						vo.setMessage(message);
						vo.setMessageType(templateKey.getMessageType());
						list.addLast(vo);
					}
				}
				
				map.put(phone, list);
			}
		}
		
		return true;
	}

	@Override
	public String sendTokenMessage(String mobile) {
		 //生成验证码
       /* String token = Util.generateAssignLengthNo(4);*/
        Random random = new Random();
		StringBuffer token = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			token.append(rand);
		}
        return sendTokenMessage(mobile, token.toString());
	}

	@Override
	public String sendTokenMessage(String mobile, String token) {
		if (sendTemplateMessage(mobile, TemplateKey.SMS_TOKEN, token)) {
            return token;
        } else {
            return Constants.SEND_CODE_ERROR;
        }
	}

	@Deprecated
	@Override
	public String sendTokenSound(String mobile, String token) {
		if (StringUtils.isNotBlank(SMSUtils.sendSMS(mobile, token))) {
            return token;
        } else {
            return Constants.SEND_CODE_ERROR;       
        }
	}

	@Override
	public String sendMarketingMessage(List<String> mobiles, String message) {
		log.info("==============【短信群发】开始================");
		
		String msg = ""; //返回内容
		int countF = 0;//失败数
		int countT = 0;//成功数
		
		//短信内容拼接
		String endStr = message.substring(message.length()-1,message.length());//获取发送信息最后一个字
		if(endStr.equals(",") || endStr.equals(".") || endStr.equals(";") || endStr.equals("!")
				|| endStr.equals("?") || endStr.equals("。") || endStr.equals("？") 
				|| endStr.equals("！") || endStr.equals("；")){
			message = message.substring(0,message.length()-1)+"，回复TD退订。";
		}else if(endStr.equals("，")){
			message = message+"回复TD退订。";
		}else{
			message = message+"，回复TD退订。";
		}
		String max = sysConfigService.findConfigByKey(Constants.SMS_MAX_LENGTH).getConfValue();
		int maxSendLength = 1000;
		//短信群发号码个数（最大1000个手机）
		if(StringUtils.isNotBlank(max)){
			maxSendLength = Integer.valueOf(max)>1000 ? 1000:Integer.valueOf(max); 
		}
		
		int mobilesCount = mobiles.size()%maxSendLength==0?mobiles.size()/maxSendLength:mobiles.size()/maxSendLength+1;
		log.info("==============【短信群发】发送内容："+message+"发送手机个数共："+mobiles.size()+"，分"+mobilesCount+"批发送，每次最多发送手机号个数："+maxSendLength+"================");
		String sendMobiles = "";
		for(int i=1;i<=mobilesCount;i++){
			
			int a = 0; //该批次发送短信手机号个数
			if(i == mobilesCount){
				/*当该次发送的短信是本次营销短信最后一次发送*/
				//群发短信手机号
				sendMobiles = StringUtils.join(mobiles.toArray(), ",", maxSendLength*(i-1), mobiles.size());
				a = mobiles.size()-maxSendLength*(i-1);
			}else{
				sendMobiles = StringUtils.join(mobiles.toArray(), ",", maxSendLength*(i-1), maxSendLength*i);
				a = maxSendLength;
			}
			/*群发短信返回码*/
			String str = MarketingSMSUtils.sendSMSSendingBatch(sendMobiles, message, a);
			String [] sm = sendMobiles.split(",");
			if (StringUtils.isNotBlank(str)) {
				for (int j=0;j < sm.length;j++) {
					//记录短信发送流水
					BsSmsRecordJnl smsRecordJnl = new BsSmsRecordJnl();
					smsRecordJnl.setContent("【币港湾】"+message);
					smsRecordJnl.setMobile(sm[j]);
					smsRecordJnl.setSendTime(new Date());
					smsRecordJnl.setType(Constants.SMS_TYPE_MARKET);
					smsRecordJnl.setPartnerCode(SmsSignEnum.BGW.getPartnerCode());
					//短信拆分条数
					int msgCont = message.length()%65 == 0?message.length()/65:message.length()/65+1;
					if(message.length() > 0 && message.length() <= 65){
						msgCont = 1;
					}else if(message.length() > 65 && message.length() <= 129){
						msgCont = 2;
					}else if(message.length() > 129 && message.length() <= 196){
						msgCont = 3;
					}else{
						msgCont = 4;
					}
					String serNo =getSerNo(str,j+1,msgCont); //推算获取serNo
					smsRecordJnl.setSerialNo(serNo);
					bsSmsRecordJnlMapper.insertSelective(smsRecordJnl);
					countT++;
				}
				
	        }else{
	        	msg = msg +","+ sendMobiles ;//记录失败的
				countF = countF +sm.length;
	        }

		}	
		
		int count = countT+countF;
		if(StringUtil.isNotBlank(msg)){
			msg = msg.substring(0,msg.length()-1);
		}
		msg = "共发送:"+count+"条，" +" 成功:"+countT+"条,失败："+countF+"条，失败号码："+msg;
		//msg = "发送失败："+countF+"条，号码是："+msg;
		if(countF == 0){
			msg = "全部发送成功！";
		}else{
			specialJnlService.warn4Fail(null, msg, null, "运营短信发送失败："+countF+"条", false);
			log.info("==============【短信群发】"+msg+"================");
		}
		log.info("==============【短信群发】结束================");
		return msg;
	}

	private String getSerNo(String str, int i, int msgCont) {
		long startSerNo = Long.parseLong(str);
		long s = (startSerNo+(i-1))+(msgCont-1)*17179869184L;
		return String.valueOf(s);
	}

	@Override
	public boolean sendTemplateMessage4YundaiSelf(String mobile,
			String addserial, TemplateKey templateKey, String... messages) {
		mobile = mobile.replaceAll("，", ",");
		String[] mobileArr = mobile.split(",");
		if(mobileArr.length >0){
			for(String phone : mobileArr){
				//BsSmsRecord bsSmsRecord = bsSmsRecordService.selectByMobile(mobile);
				Map<String, LinkedList<SmsMapVO>> map = SendSmTask.map;
				LinkedList<SmsMapVO> list = map.get(phone);
				String message = String.format(templateKey.getTemplate(), messages).toString();
				//判断手机号长度和是否为1开头
				if(StringUtil.isNotBlank(phone) && phone.length()== 11 && phone.startsWith("1")){
					if(map.get(phone) != null){
						list = map.get(phone);
						SmsMapVO vo = new SmsMapVO();
						vo.setMessage(message);
						vo.setAddserial(addserial);
						vo.setMessageType(templateKey.getMessageType());
						list.addLast(vo);
					}else{
						list = new LinkedList<SmsMapVO>();
						SmsMapVO vo = new SmsMapVO();
						vo.setMessage(message);
						vo.setAddserial(addserial);
						vo.setMessageType(templateKey.getMessageType());
						list.addLast(vo);
					}
				}
				
				map.put(phone, list);
			}
		}
		return true;
	}

	@Override
	public String sendYundaiSelfRepayToken(String mobile) {
		//生成验证码
		Random random = new Random();
		StringBuffer token = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			token.append(rand);
		}
		if (sendTemplateMessage4YundaiSelf(mobile, "2",TemplateKey.INCR_REPAY_PRE_SMS_TOKEN, token.toString())) {
            return token.toString();
        } else {
            return Constants.SEND_CODE_ERROR;
        }
	}

	@Override
	public String sendYundaiSelfBindCardToken(String mobile) {
		//生成验证码
		Random random = new Random();
		StringBuffer token = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			token.append(rand);
		}
		if (sendTemplateMessage4YundaiSelf(mobile, "2",TemplateKey.INCR_BIND_CARD_PRE_SMS_TOKEN, token.toString())) {
            return token.toString();
        } else {
            return Constants.SEND_CODE_ERROR;
        }
	}

	@Override
	public String sendYundaiSelfBindCardToken(String mobile, String addserial) {
		//生成验证码
		Random random = new Random();
		StringBuffer token = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			token.append(rand);
		}
		if (sendTemplateMessage4YundaiSelf(mobile, addserial,TemplateKey.INCR_BIND_CARD_PRE_SMS_TOKEN, token.toString())) {
			return token.toString();
		} else {
			return Constants.SEND_CODE_ERROR;
		}
	}

	@Override
	public boolean sendTemplateMessage4Zan(String mobile, String addserial, TemplateKey templateKey, String... messages) {
		mobile = mobile.replaceAll("，", ",");
		String[] mobileArr = mobile.split(",");
		if(mobileArr.length >0){
			for(String phone : mobileArr){
				//BsSmsRecord bsSmsRecord = bsSmsRecordService.selectByMobile(mobile);
				Map<String, LinkedList<SmsMapVO>> map = SendSmTask.map;
				LinkedList<SmsMapVO> list = map.get(phone);
				String message = String.format(templateKey.getTemplate(), messages).toString();
				//判断手机号长度和是否为1开头
				if(StringUtil.isNotBlank(phone) && phone.length()== 11 && phone.startsWith("1")){
					if(map.get(phone) != null){
						list = map.get(phone);
						SmsMapVO vo = new SmsMapVO();
						vo.setMessage(message);
						vo.setAddserial(addserial);
						vo.setMessageType(templateKey.getMessageType());
						list.addLast(vo);
					}else{
						list = new LinkedList<SmsMapVO>();
						SmsMapVO vo = new SmsMapVO();
						vo.setMessage(message);
						vo.setAddserial(addserial);
						vo.setMessageType(templateKey.getMessageType());
						list.addLast(vo);
					}
				}

				map.put(phone, list);
			}
		}
		return true;
	}

	@Override
	public String sendZanRepayToken(String mobile) {
		//生成验证码
		Random random = new Random();
		StringBuffer token = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			token.append(rand);
		}
		if (sendTemplateMessage4Zan(mobile, "3",TemplateKey.ZAN_REPAY_PRE_SMS_TOKEN, token.toString())) {
			return token.toString();
		} else {
			return Constants.SEND_CODE_ERROR;
		}
	}


	@Override
	public String sendZanBindCardToken(String mobile) {
		//生成验证码
		Random random = new Random();
		StringBuffer token = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			token.append(rand);
		}
		if (sendTemplateMessage4Zan(mobile, "3",TemplateKey.ZAN_BIND_CARD_PRE_SMS_TOKEN, token.toString())) {
			return token.toString();
		} else {
			return Constants.SEND_CODE_ERROR;
		}
	}

	@Override
	public String sendYundaiSelfRepayRepeatToken(String mobile) {
		//生成验证码
		Random random = new Random();
		StringBuffer token = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			token.append(rand);
		}
		if (sendTemplateMessage4YundaiSelf(mobile, "2",TemplateKey.INCR_REPAY_PRE_REPEAT_SMS_TOKEN, token.toString())) {
			return token.toString();
		} else {
			return Constants.SEND_CODE_ERROR;
		}
	}

	@Override
	public String sendRepayRepeatToken(String mobile, String partnerCode) {
		//生成验证码
		Random random = new Random();
		StringBuffer token = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			token.append(rand);
		}
		if (PartnerEnum.ZSD.getCode().equals(partnerCode)) {
			// 4为赞时贷
			if (sendTemplateMessage4YundaiSelf(mobile, "4", TemplateKey.ZSD_REPAY_PRE_REPEAT_SMS_TOKEN, token.toString())) {
				return token.toString();
			} 
		}
		return Constants.SEND_CODE_ERROR;
	}

	@Override
	public String sendYunHeadFeeTokenMessage(String mobile, String propertySymbol, Double amount, String userName) {
		//生成验证码
		Random random = new Random();
		StringBuffer token = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			token.append(rand);
		}
		return sendYunHeadFeeTokenMessage(mobile, propertySymbol, String.valueOf(amount), userName.toString(), token.toString());
	}

	@Override
	public String sendYunHeadFeeTokenMessage(String mobile, String propertySymbol, String amount, String userName,  String token) {
		if (sendTemplateMessage(mobile, TemplateKey.MANAGER_YUN_HEADFEE_TRANSFER, propertySymbol, amount, userName, token)) {
			return token;
		} else {
			return Constants.SEND_CODE_ERROR;
		}
	}

}
