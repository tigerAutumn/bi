package com.pinting.business.service.site.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.dao.BsAuthMapper;
import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsAuth;
import com.pinting.business.model.BsAuthExample;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.service.site.SMSService;
import com.pinting.business.util.Constants;
import com.pinting.gateway.in.util.MethodRole;
import com.pinting.gateway.out.service.SMSGateService;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
/**
 * @Project: business
 * @Title: SMSServiceImpl.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:48:02
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class SMSServiceImpl implements SMSService{
	@Autowired
	private SMSGateService smsService;
	@Autowired
	private BsAuthMapper bsAuthMapper;
	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
	@Autowired
	private SMSServiceClient smsServiceClient;
	
	@Override
	@MethodRole("APP")
	public String sendIdentify(String mobile)  {
		String mobileCode=Constants.SEND_CODE_ERROR;
		BsAuthExample bsAuthExample = new BsAuthExample();
		bsAuthExample.createCriteria().andMobileEqualTo(mobile);
		BsAuth bsAuth=null;
		if(bsAuthMapper.selectByExample(bsAuthExample).size()>0){//数据库是否有返回数据
			bsAuth=bsAuthMapper.selectByExample(bsAuthExample).get(0);
		}
		if(bsAuth!=null){//该手机已经发送过验证码-数据库里有数据
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(bsAuth.getMobileTime());//验证码发送时间

			//验证发送时间间隔秒数
			int intervalTime = (int) ((new Date().getTime() - bsAuth
					.getMobileTime().getTime()) / 1000);
			//验证发送时间间隔天数
			int  intervalDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH);

			//如果验证码间隔一天且间隔秒数大于有效时间（120秒）——防止一天的最后一分钟发送验证码
			if((intervalDay>0 || intervalTime>Constants.CODE_EXIST_TIME_MAX ) && intervalTime>Constants.CODE_EXIST_TIME){
				bsAuth.setMobileTimes(0);
			}
			if(bsAuth.getMobileTimes()<15){//该手机发送次数小于1五次，可以发送
				//mobileCode=smsService.sendIdentify(mobile);
				mobileCode = smsServiceClient.sendTokenMessage(mobile);
				bsAuth.setMobile(mobile);
				bsAuth.setMobileCode(mobileCode);
				bsAuth.setMobileTime(new Date());
				bsAuth.setMobileTimes(bsAuth.getMobileTimes()+1);
				bsAuth.setMobileCodeUseTimes(0);
				bsAuthMapper.updateByPrimaryKey(bsAuth);
			}else{//该手机发送次数大于1五次，不可以发送验证码
				throw new PTMessageException(PTMessageEnum.MOBILE_SEND_MAX_ERROR);
			}
		}else{//该手机未发送过验证码-数据库里无数据
			//mobileCode=smsService.sendIdentify(mobile);
			mobileCode = smsServiceClient.sendTokenMessage(mobile);
			bsAuth=new BsAuth();
			bsAuth.setMobile(mobile);
			bsAuth.setMobileCode(mobileCode);
			bsAuth.setMobileTime(new Date());
			bsAuth.setMobileTimes(1);
			bsAuth.setMobileCodeUseTimes(0);
			bsAuthMapper.insertSelective(bsAuth);
		}
		//如果mobileCode为null,则发送失败，返回错误信息
		return mobileCode==null ? Constants.SEND_CODE_ERROR : mobileCode;
	}

	@Override
	@MethodRole("APP")
	public boolean validateIdentity(String mobile, String mobileCode) {
		return validateIdentity(mobile, mobileCode, true);
	}

	@Override
	public boolean validateIdentityExpire(String mobile, String mobileCode, int expireSecond) {
		return validateIdentity(mobile, mobileCode, true, expireSecond);
	}

	private boolean validateIdentity(String mobile, String mobileCode, Boolean isDelete, int expireSecond) {
		BsAuthExample bsAuthExample = new BsAuthExample();
		bsAuthExample.createCriteria().andMobileEqualTo(mobile);
		BsAuth bsAuth=null;
		if(bsAuthMapper.selectByExample(bsAuthExample).size()>0){//数据库是否有返回数据
			bsAuth=bsAuthMapper.selectByExample(bsAuthExample).get(0);
		}
		if (bsAuth != null) {// 已经发送验证码
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(bsAuth.getMobileTime());//验证码发送时间

			//验证发送时间间隔秒数
			int intervalTime = (int) ((new Date().getTime() - bsAuth
					.getMobileTime().getTime()) / 1000);
			//验证发送时间间隔天数
			int  intervalDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH);

			if(Constants.SEND_CODE_VERIFY_OR_EXPIRED.equals(bsAuth.getMobileCode())){//验证码已经失效或者验证码未发送
				throw new PTMessageException(PTMessageEnum.MOBILE_NOT_SEND_ERROR);
			}
			if (bsAuth.getMobileCodeUseTimes() >= 5) {// 验证次数大于五次
				throw new PTMessageException(PTMessageEnum.MOBILE_CODE_USER_TIMES_MAX_ERROR);
			}
			if (new Date().getTime() - bsAuth.getMobileTime().getTime() < 50) {// 防止机器暴力破解
				throw new PTMessageException(PTMessageEnum.MOBILE_CODE_TOO_FAST_ERROR);
			}

			if (intervalTime > expireSecond) {// 验证码发送超过 expireSecond 秒，过期
				//如果验证码间隔一天则未发送验证码
				if(intervalDay>0 || intervalTime>Constants.CODE_EXIST_TIME_MAX ){
					throw new PTMessageException(PTMessageEnum.MOBILE_NOT_SEND_ERROR);
				}

				bsAuth.setMobileCode(Constants.SEND_CODE_VERIFY_OR_EXPIRED);//设置验证码错误信息
				bsAuthMapper.updateByPrimaryKey(bsAuth);
				throw new PTMessageException(PTMessageEnum.MOBILE_SEND_DATE_ERROR);
			}
			bsAuth.setMobileCodeUseTimes(bsAuth.getMobileCodeUseTimes() + 1);// 验证次数++
			bsAuthMapper.updateByPrimaryKey(bsAuth);
			if (!bsAuth.getMobileCode().equals(mobileCode)) {// 验证码不正确
				throw new PTMessageException(PTMessageEnum.MOBILE_CODE_WRONG_ERROR);
			}
			if(isDelete){//设置验证码失效
				bsAuth.setMobileCode(Constants.SEND_CODE_VERIFY_OR_EXPIRED);
				bsAuthMapper.updateByPrimaryKey(bsAuth);
			}
			return true;
		} else {//未发送验证码
			throw new PTMessageException(PTMessageEnum.MOBILE_NOT_SEND_ERROR);
		}
	}
	/**
	 * 一、在已经发送过验证码的情况下
		 * 1、判断验证码是否失效
		 * 2、判断验证码被验证次数是否>5次
		 * 3、判断两次验证时间间隔是否过短，目的防止机器暴力破解
		 * 4、判断验证码是否过期，如果过期，设置为错误信息
		 	*4.1、判断验证码发送时间是否间隔>一天，如果是则未发送验证码 	
		 * 5、修改验证次数++
		 * 6、判断验证码是否正确
	 */
	@Override
	@MethodRole("APP")
	public boolean validateIdentity(String mobile, String mobileCode,Boolean isDelete)  {
		BsAuthExample bsAuthExample = new BsAuthExample();
		bsAuthExample.createCriteria().andMobileEqualTo(mobile);
		BsAuth bsAuth=null;
		if(bsAuthMapper.selectByExample(bsAuthExample).size()>0){//数据库是否有返回数据
			bsAuth=bsAuthMapper.selectByExample(bsAuthExample).get(0);
		}
		if (bsAuth != null) {// 已经发送验证码
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(bsAuth.getMobileTime());//验证码发送时间
			
			//验证发送时间间隔秒数
			int intervalTime = (int) ((new Date().getTime() - bsAuth
					.getMobileTime().getTime()) / 1000);
			//验证发送时间间隔天数
			int  intervalDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH);
			
			if(Constants.SEND_CODE_VERIFY_OR_EXPIRED.equals(bsAuth.getMobileCode())){//验证码已经失效或者验证码未发送
				throw new PTMessageException(PTMessageEnum.MOBILE_NOT_SEND_ERROR);
			}
			if (bsAuth.getMobileCodeUseTimes() >= 5) {// 验证次数大于五次
				throw new PTMessageException(PTMessageEnum.MOBILE_CODE_USER_TIMES_MAX_ERROR);
			}
			if (new Date().getTime() - bsAuth.getMobileTime().getTime() < 50) {// 防止机器暴力破解
				throw new PTMessageException(PTMessageEnum.MOBILE_CODE_TOO_FAST_ERROR);
			}

			if (intervalTime > Constants.CODE_EXIST_TIME) {// 验证码发送超过300秒，过期
				//如果验证码间隔一天则未发送验证码
				if(intervalDay>0 || intervalTime>Constants.CODE_EXIST_TIME_MAX ){
					throw new PTMessageException(PTMessageEnum.MOBILE_NOT_SEND_ERROR);
				}

				bsAuth.setMobileCode(Constants.SEND_CODE_VERIFY_OR_EXPIRED);//设置验证码错误信息
				bsAuthMapper.updateByPrimaryKey(bsAuth);
				throw new PTMessageException(PTMessageEnum.MOBILE_SEND_DATE_ERROR);
			}
			bsAuth.setMobileCodeUseTimes(bsAuth.getMobileCodeUseTimes() + 1);// 验证次数++
			bsAuthMapper.updateByPrimaryKey(bsAuth);
			if (!bsAuth.getMobileCode().equals(mobileCode)) {// 验证码不正确
				throw new PTMessageException(PTMessageEnum.MOBILE_CODE_WRONG_ERROR);
			}
			if(isDelete){//设置验证码失效
				bsAuth.setMobileCode(Constants.SEND_CODE_VERIFY_OR_EXPIRED);
				bsAuthMapper.updateByPrimaryKey(bsAuth);
			}
			return true;
		} else {//未发送验证码
			throw new PTMessageException(PTMessageEnum.MOBILE_NOT_SEND_ERROR);
		}
	}

	@Override
	@Deprecated
	public Boolean sendMessage(String mobile, String message) {
		return smsService.sendMessage(mobile, message);
	}

	@Override
	@Deprecated
	public String sendMobiles(String mobiles, String message) {
		String res = "";
		String[] mobileArr = mobiles.split(",");
		for (String mobile : mobileArr) {
			if(!smsService.sendMessage(mobile, message)){
				res += mobile + ",";
			}
		}
		return res;
	}

	/**
	 * isTsak-true，为定时任务异常失败
	 */
	@Override
	public String sendSysManagerMobiles(String message,boolean isTsak) {
		String res = "";
		BsSysConfig bsSysConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.EMERGENCY_MOBILE);
		String phone = bsSysConfig.getConfValue().replaceAll("，", ",");
		String[] mobileArr = phone.split(",");
		for (String mobile : mobileArr) {
			/*if(!smsService.sendMessage(mobile, message)){
				res += mobile + ",";
			}*/
			if(isTsak){
				;
				if(!smsServiceClient.sendTemplateMessage(mobile, TemplateKey.TASK_EMERGENCY, message)){
					res += mobile + ",";
				}
			}else{
				if(!smsServiceClient.sendTemplateMessage(mobile, TemplateKey.COMMON_EMERGENCY, message)){
					res += mobile + ",";
				}
			}
			
		}
		return res;
	}

	/**
	 * isTsak-true，为定时任务异常失败
	 */
	@Override
	public String sendProductOperatorMobiles(String message,boolean isTsak) {
		String res = "";
		BsSysConfig bsSysConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.PRODUCT_OPERATOR_MOBILE);
		String phone = bsSysConfig.getConfValue().replaceAll("，", ",");
		String[] mobileArr = phone.split(",");
		for (String mobile : mobileArr) {
			/*if(!smsService.sendMessage(mobile, message)){
				res += mobile + ",";
			}*/
			if(isTsak){
				;
				if(!smsServiceClient.sendTemplateMessage(mobile, TemplateKey.TASK_EMERGENCY, message)){
					res += mobile + ",";
				}
			}else{
				if(!smsServiceClient.sendTemplateMessage(mobile, TemplateKey.COMMON_EMERGENCY, message)){
					res += mobile + ",";
				}
			}

		}
		return res;
	}

	@Override
	public String sendFinanceMobiles(String message,boolean isTsak) {
		String res = "";
		BsSysConfig bsSysConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.FINANCE_MOBILE);
		String phone = bsSysConfig.getConfValue().replaceAll("，", ",");
		String[] mobileArr = phone.split(",");
		for (String mobile : mobileArr) {
			/*if(!smsService.sendMessage(mobile, message)){
				res += mobile + ",";
			}*/
			if(isTsak){
				;
				if(!smsServiceClient.sendTemplateMessage(mobile, TemplateKey.TASK_EMERGENCY, message)){
					res += mobile + ",";
				}
			}else{
				if(!smsServiceClient.sendTemplateMessage(mobile, TemplateKey.COMMON_EMERGENCY, message)){
					res += mobile + ",";
				}
			}

		}
		return res;
	}

	/**
	 * 发送告警短信
	 * isTsak-true，为定时任务异常失败
	 */
	@Override
	public String sendWarnMobiles(String message,boolean isTsak, String...warnMobiles) {

		Set<String> moblieSet = new HashSet<>();

		for(int i=0; i < warnMobiles.length; i++) {
			BsSysConfig bsSysConfig = bsSysConfigMapper.selectByPrimaryKey(warnMobiles[i]);
			String phone = bsSysConfig.getConfValue().replaceAll("，", ",");
			String[] mobileArr = phone.split(",");
			for (String moblie : mobileArr) {
				moblieSet.add(moblie);
			}
		}

		String res = "";
		for (String mobile : moblieSet) {
			if(isTsak) {
				if(!smsServiceClient.sendTemplateMessage(mobile, TemplateKey.TASK_EMERGENCY, message)) {
					res += mobile + ",";
				}
			} else {
				if(!smsServiceClient.sendTemplateMessage(mobile, TemplateKey.COMMON_EMERGENCY, message)) {
					res += mobile + ",";
				}
			}
		}
		return res;
	}

	@Override
	public Integer interval(String mobile) {
		BsAuthExample bsAuthExample = new BsAuthExample();
		bsAuthExample.createCriteria().andMobileEqualTo(mobile);
		List<BsAuth> bsAuths = bsAuthMapper.selectByExample(bsAuthExample);
		
		if (!CollectionUtils.isEmpty(bsAuths)) {
			
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(bsAuths.get(0).getMobileTime());//验证码发送时间
			//验证码发送间隔秒数
			int intervalTime = (int) ((new Date().getTime() - bsAuths.get(0)
					.getMobileTime().getTime()) / 1000);
			int  intervalDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH);
			
			//如果验证码间隔一天且间隔秒数大于有效时间（60秒）
			if((intervalDay>0 || intervalTime>Constants.CODE_EXIST_TIME_MAX ) && intervalTime >= Constants.CODE_RESEND_TIME){
				return -1;
			}else {
				return Constants.CODE_RESEND_TIME - intervalTime ;
			}
		}
		return -1;
	}

	@Override
	public String sendRepayPreToken(String mobile) {
		String mobileCode=Constants.SEND_CODE_ERROR;
		BsAuthExample bsAuthExample = new BsAuthExample();
		bsAuthExample.createCriteria().andMobileEqualTo(mobile);
		BsAuth bsAuth=null;
		if(bsAuthMapper.selectByExample(bsAuthExample).size()>0){//数据库是否有返回数据
			bsAuth=bsAuthMapper.selectByExample(bsAuthExample).get(0);
		}
		if(bsAuth!=null){//该手机已经发送过验证码-数据库里有数据
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(bsAuth.getMobileTime());//验证码发送时间
			
			//验证发送时间间隔秒数
			int intervalTime = (int) ((new Date().getTime() - bsAuth
					.getMobileTime().getTime()) / 1000);
			//验证发送时间间隔天数
			int  intervalDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH);
			
			//如果验证码间隔一天且间隔秒数大于有效时间（120秒）——防止一天的最后一分钟发送验证码
			if((intervalDay>0 || intervalTime>Constants.CODE_EXIST_TIME_MAX ) && intervalTime>Constants.CODE_EXIST_TIME){
				bsAuth.setMobileTimes(0);
			}
			if(bsAuth.getMobileTimes()<15){//该手机发送次数小于1五次，可以发送
				//mobileCode=smsService.sendIdentify(mobile);
//				mobileCode = smsServiceClient.sendYundaiSelfRepayToken(mobile);
				mobileCode = smsServiceClient.sendYundaiSelfRepayRepeatToken(mobile);
				bsAuth.setMobile(mobile);
				bsAuth.setMobileCode(mobileCode);
				bsAuth.setMobileTime(new Date());
				bsAuth.setMobileTimes(bsAuth.getMobileTimes()+1);
				bsAuth.setMobileCodeUseTimes(0);
				bsAuthMapper.updateByPrimaryKey(bsAuth);
			}else{//该手机发送次数大于1五次，不可以发送验证码
				throw new PTMessageException(PTMessageEnum.MOBILE_SEND_MAX_ERROR);
			}
		}else{//该手机未发送过验证码-数据库里无数据
			//mobileCode=smsService.sendIdentify(mobile);
//			mobileCode = smsServiceClient.sendYundaiSelfRepayToken(mobile);
			mobileCode = smsServiceClient.sendYundaiSelfRepayRepeatToken(mobile);
			bsAuth=new BsAuth();
			bsAuth.setMobile(mobile);
			bsAuth.setMobileCode(mobileCode);
			bsAuth.setMobileTime(new Date());
			bsAuth.setMobileTimes(1);
			bsAuth.setMobileCodeUseTimes(0);
			bsAuthMapper.insertSelective(bsAuth);
		}
		//如果mobileCode为null,则发送失败，返回错误信息
		return mobileCode==null ? Constants.SEND_CODE_ERROR : mobileCode;
	}

	@Override
	public String sendBindCardPreToken(String mobile) {

		String mobileCode=Constants.SEND_CODE_ERROR;
		BsAuthExample bsAuthExample = new BsAuthExample();
		bsAuthExample.createCriteria().andMobileEqualTo(mobile);
		BsAuth bsAuth=null;
		if(bsAuthMapper.selectByExample(bsAuthExample).size()>0){//数据库是否有返回数据
			bsAuth=bsAuthMapper.selectByExample(bsAuthExample).get(0);
		}
		if(bsAuth!=null){//该手机已经发送过验证码-数据库里有数据
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(bsAuth.getMobileTime());//验证码发送时间
			
			//验证发送时间间隔秒数
			int intervalTime = (int) ((new Date().getTime() - bsAuth
					.getMobileTime().getTime()) / 1000);
			//验证发送时间间隔天数
			int  intervalDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH);
			
			//如果验证码间隔一天且间隔秒数大于有效时间（120秒）——防止一天的最后一分钟发送验证码
			if((intervalDay>0 || intervalTime>Constants.CODE_EXIST_TIME_MAX ) && intervalTime>Constants.CODE_EXIST_TIME){
				bsAuth.setMobileTimes(0);
			}
			if(bsAuth.getMobileTimes()<15){//该手机发送次数小于1五次，可以发送
				//mobileCode=smsService.sendIdentify(mobile);
				mobileCode = smsServiceClient.sendYundaiSelfBindCardToken(mobile);
				bsAuth.setMobile(mobile);
				bsAuth.setMobileCode(mobileCode);
				bsAuth.setMobileTime(new Date());
				bsAuth.setMobileTimes(bsAuth.getMobileTimes()+1);
				bsAuth.setMobileCodeUseTimes(0);
				bsAuthMapper.updateByPrimaryKey(bsAuth);
			}else{//该手机发送次数大于1五次，不可以发送验证码
				throw new PTMessageException(PTMessageEnum.MOBILE_SEND_MAX_ERROR);
			}
		}else{//该手机未发送过验证码-数据库里无数据
			//mobileCode=smsService.sendIdentify(mobile);
			mobileCode = smsServiceClient.sendYundaiSelfBindCardToken(mobile);
			bsAuth=new BsAuth();
			bsAuth.setMobile(mobile);
			bsAuth.setMobileCode(mobileCode);
			bsAuth.setMobileTime(new Date());
			bsAuth.setMobileTimes(1);
			bsAuth.setMobileCodeUseTimes(0);
			bsAuthMapper.insertSelective(bsAuth);
		}
		//如果mobileCode为null,则发送失败，返回错误信息
		return mobileCode==null ? Constants.SEND_CODE_ERROR : mobileCode;
	
	}

	@Override
	public String sendBindCardPreToken(String mobile, String addserial) {

		String mobileCode=Constants.SEND_CODE_ERROR;
		BsAuthExample bsAuthExample = new BsAuthExample();
		bsAuthExample.createCriteria().andMobileEqualTo(mobile);
		BsAuth bsAuth=null;
		if(bsAuthMapper.selectByExample(bsAuthExample).size()>0){//数据库是否有返回数据
			bsAuth=bsAuthMapper.selectByExample(bsAuthExample).get(0);
		}
		if(bsAuth!=null){//该手机已经发送过验证码-数据库里有数据
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(bsAuth.getMobileTime());//验证码发送时间

			//验证发送时间间隔秒数
			int intervalTime = (int) ((new Date().getTime() - bsAuth
					.getMobileTime().getTime()) / 1000);
			//验证发送时间间隔天数
			int  intervalDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH);

			//如果验证码间隔一天且间隔秒数大于有效时间（120秒）——防止一天的最后一分钟发送验证码
			if((intervalDay>0 || intervalTime>Constants.CODE_EXIST_TIME_MAX ) && intervalTime>Constants.CODE_EXIST_TIME){
				bsAuth.setMobileTimes(0);
			}
			if(bsAuth.getMobileTimes()<15){//该手机发送次数小于1五次，可以发送
				//mobileCode=smsService.sendIdentify(mobile);
				mobileCode = smsServiceClient.sendYundaiSelfBindCardToken(mobile, addserial);
				bsAuth.setMobile(mobile);
				bsAuth.setMobileCode(mobileCode);
				bsAuth.setMobileTime(new Date());
				bsAuth.setMobileTimes(bsAuth.getMobileTimes()+1);
				bsAuth.setMobileCodeUseTimes(0);
				bsAuthMapper.updateByPrimaryKey(bsAuth);
			}else{//该手机发送次数大于1五次，不可以发送验证码
				throw new PTMessageException(PTMessageEnum.MOBILE_SEND_MAX_ERROR);
			}
		}else{//该手机未发送过验证码-数据库里无数据
			//mobileCode=smsService.sendIdentify(mobile);
			mobileCode = smsServiceClient.sendYundaiSelfBindCardToken(mobile, addserial);
			bsAuth=new BsAuth();
			bsAuth.setMobile(mobile);
			bsAuth.setMobileCode(mobileCode);
			bsAuth.setMobileTime(new Date());
			bsAuth.setMobileTimes(1);
			bsAuth.setMobileCodeUseTimes(0);
			bsAuthMapper.insertSelective(bsAuth);
		}
		//如果mobileCode为null,则发送失败，返回错误信息
		return mobileCode==null ? Constants.SEND_CODE_ERROR : mobileCode;
	}

	@Override
	public String sendZanRepayPreToken(String mobile) {
		String mobileCode=Constants.SEND_CODE_ERROR;
		BsAuthExample bsAuthExample = new BsAuthExample();
		bsAuthExample.createCriteria().andMobileEqualTo(mobile);
		BsAuth bsAuth=null;
		if(bsAuthMapper.selectByExample(bsAuthExample).size()>0){//数据库是否有返回数据
			bsAuth=bsAuthMapper.selectByExample(bsAuthExample).get(0);
		}
		if(bsAuth!=null){//该手机已经发送过验证码-数据库里有数据
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(bsAuth.getMobileTime());//验证码发送时间

			//验证发送时间间隔秒数
			int intervalTime = (int) ((new Date().getTime() - bsAuth
					.getMobileTime().getTime()) / 1000);
			//验证发送时间间隔天数
			int  intervalDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH);

			//如果验证码间隔一天且间隔秒数大于有效时间（120秒）——防止一天的最后一分钟发送验证码
			if((intervalDay>0 || intervalTime>Constants.CODE_EXIST_TIME_MAX ) && intervalTime>Constants.CODE_EXIST_TIME){
				bsAuth.setMobileTimes(0);
			}
			if(bsAuth.getMobileTimes()<15){//该手机发送次数小于1五次，可以发送
				//mobileCode=smsService.sendIdentify(mobile);
				mobileCode = smsServiceClient.sendZanRepayToken(mobile);
				bsAuth.setMobile(mobile);
				bsAuth.setMobileCode(mobileCode);
				bsAuth.setMobileTime(new Date());
				bsAuth.setMobileTimes(bsAuth.getMobileTimes()+1);
				bsAuth.setMobileCodeUseTimes(0);
				bsAuthMapper.updateByPrimaryKey(bsAuth);
			}else{//该手机发送次数大于1五次，不可以发送验证码
				throw new PTMessageException(PTMessageEnum.MOBILE_SEND_MAX_ERROR);
			}
		}else{//该手机未发送过验证码-数据库里无数据
			//mobileCode=smsService.sendIdentify(mobile);
			mobileCode = smsServiceClient.sendZanRepayToken(mobile);
			bsAuth=new BsAuth();
			bsAuth.setMobile(mobile);
			bsAuth.setMobileCode(mobileCode);
			bsAuth.setMobileTime(new Date());
			bsAuth.setMobileTimes(1);
			bsAuth.setMobileCodeUseTimes(0);
			bsAuthMapper.insertSelective(bsAuth);
		}
		//如果mobileCode为null,则发送失败，返回错误信息
		return mobileCode==null ? Constants.SEND_CODE_ERROR : mobileCode;
	}

	@Override
	public String sendZanBindCardPreToken(String mobile) {

		String mobileCode=Constants.SEND_CODE_ERROR;
		BsAuthExample bsAuthExample = new BsAuthExample();
		bsAuthExample.createCriteria().andMobileEqualTo(mobile);
		BsAuth bsAuth=null;
		if(bsAuthMapper.selectByExample(bsAuthExample).size()>0){//数据库是否有返回数据
			bsAuth=bsAuthMapper.selectByExample(bsAuthExample).get(0);
		}
		if(bsAuth!=null){//该手机已经发送过验证码-数据库里有数据
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(bsAuth.getMobileTime());//验证码发送时间

			//验证发送时间间隔秒数
			int intervalTime = (int) ((new Date().getTime() - bsAuth
					.getMobileTime().getTime()) / 1000);
			//验证发送时间间隔天数
			int  intervalDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH);

			//如果验证码间隔一天且间隔秒数大于有效时间（120秒）——防止一天的最后一分钟发送验证码
			if((intervalDay>0 || intervalTime>Constants.CODE_EXIST_TIME_MAX ) && intervalTime>Constants.CODE_EXIST_TIME){
				bsAuth.setMobileTimes(0);
			}
			if(bsAuth.getMobileTimes()<15){//该手机发送次数小于1五次，可以发送
				//mobileCode=smsService.sendIdentify(mobile);
				mobileCode = smsServiceClient.sendZanBindCardToken(mobile);
				bsAuth.setMobile(mobile);
				bsAuth.setMobileCode(mobileCode);
				bsAuth.setMobileTime(new Date());
				bsAuth.setMobileTimes(bsAuth.getMobileTimes()+1);
				bsAuth.setMobileCodeUseTimes(0);
				bsAuthMapper.updateByPrimaryKey(bsAuth);
			}else{//该手机发送次数大于1五次，不可以发送验证码
				throw new PTMessageException(PTMessageEnum.MOBILE_SEND_MAX_ERROR);
			}
		}else{//该手机未发送过验证码-数据库里无数据
			//mobileCode=smsService.sendIdentify(mobile);
			mobileCode = smsServiceClient.sendZanBindCardToken(mobile);
			bsAuth=new BsAuth();
			bsAuth.setMobile(mobile);
			bsAuth.setMobileCode(mobileCode);
			bsAuth.setMobileTime(new Date());
			bsAuth.setMobileTimes(1);
			bsAuth.setMobileCodeUseTimes(0);
			bsAuthMapper.insertSelective(bsAuth);
		}
		//如果mobileCode为null,则发送失败，返回错误信息
		return mobileCode==null ? Constants.SEND_CODE_ERROR : mobileCode;

	}

	@Override
	public String sendRepayPreToken(String mobile, String partnerCode) {
		String mobileCode = Constants.SEND_CODE_ERROR;
		BsAuthExample bsAuthExample = new BsAuthExample();
		bsAuthExample.createCriteria().andMobileEqualTo(mobile);
		BsAuth bsAuth = null;
		if(bsAuthMapper.selectByExample(bsAuthExample).size()>0){//数据库是否有返回数据
			bsAuth=bsAuthMapper.selectByExample(bsAuthExample).get(0);
		}
		if(bsAuth != null){
			//该手机已经发送过验证码-数据库里有数据
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(bsAuth.getMobileTime());//验证码发送时间
			//验证发送时间间隔秒数
			int intervalTime = (int) ((new Date().getTime() - bsAuth
					.getMobileTime().getTime()) / 1000);
			//验证发送时间间隔天数
			int  intervalDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH);
			
			//如果验证码间隔一天且间隔秒数大于有效时间（120秒）——防止一天的最后一分钟发送验证码
			if((intervalDay>0 || intervalTime>Constants.CODE_EXIST_TIME_MAX ) && intervalTime>Constants.CODE_EXIST_TIME){
				bsAuth.setMobileTimes(0);
			}
			if(bsAuth.getMobileTimes()<15){
				//该手机发送次数小于五次，可以发送
				if (PartnerEnum.ZSD.getCode().equals(partnerCode)) {					
					mobileCode = smsServiceClient.sendRepayRepeatToken(mobile, PartnerEnum.ZSD.getCode());
				}
				bsAuth.setMobile(mobile);
				bsAuth.setMobileCode(mobileCode);
				bsAuth.setMobileTime(new Date());
				bsAuth.setMobileTimes(bsAuth.getMobileTimes()+1);
				bsAuth.setMobileCodeUseTimes(0);
				bsAuthMapper.updateByPrimaryKey(bsAuth);
			}else{
				//该手机发送次数大于五次，不可以发送验证码
				throw new PTMessageException(PTMessageEnum.MOBILE_SEND_MAX_ERROR);
			}
		}else{
			//该手机未发送过验证码-数据库里无数据
			if (PartnerEnum.ZSD.getCode().equals(partnerCode)) {
				mobileCode = smsServiceClient.sendRepayRepeatToken(mobile, PartnerEnum.ZSD.getCode());
			}
			bsAuth = new BsAuth();
			bsAuth.setMobile(mobile);
			bsAuth.setMobileCode(mobileCode);
			bsAuth.setMobileTime(new Date());
			bsAuth.setMobileTimes(1);
			bsAuth.setMobileCodeUseTimes(0);
			bsAuthMapper.insertSelective(bsAuth);
		}
		//如果mobileCode为null,则发送失败，返回错误信息
		return mobileCode==null ? Constants.SEND_CODE_ERROR : mobileCode;
	}

	@Override
	public String sendYunHeadFeeIdentify(String mobile, String propertySymbol, Double amount, String userName) {
		String mobileCode=Constants.SEND_CODE_ERROR;
		BsAuthExample bsAuthExample = new BsAuthExample();
		bsAuthExample.createCriteria().andMobileEqualTo(mobile);
		BsAuth bsAuth=null;
		if(bsAuthMapper.selectByExample(bsAuthExample).size()>0){//数据库是否有返回数据
			bsAuth=bsAuthMapper.selectByExample(bsAuthExample).get(0);
		}
		if(bsAuth!=null){//该手机已经发送过验证码-数据库里有数据
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(bsAuth.getMobileTime());//验证码发送时间

			//验证发送时间间隔秒数
			int intervalTime = (int) ((new Date().getTime() - bsAuth
					.getMobileTime().getTime()) / 1000);
			//验证发送时间间隔天数
			int intervalDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH);

			//如果验证码间隔一天且间隔秒数大于有效时间（120秒）——防止一天的最后一分钟发送验证码
			if((intervalDay>0 || intervalTime>Constants.CODE_EXIST_TIME_MAX ) && intervalTime>Constants.CODE_EXIST_TIME){
				bsAuth.setMobileTimes(0);
			}
			if(bsAuth.getMobileTimes()<15){//该手机发送次数小于15次，可以发送
				//mobileCode=smsService.sendIdentify(mobile);
				mobileCode = smsServiceClient.sendYunHeadFeeTokenMessage(mobile, propertySymbol, amount, userName);
				bsAuth.setMobile(mobile);
				bsAuth.setMobileCode(mobileCode);
				bsAuth.setMobileTime(new Date());
				bsAuth.setMobileTimes(bsAuth.getMobileTimes()+1);
				bsAuth.setMobileCodeUseTimes(0);
				bsAuthMapper.updateByPrimaryKey(bsAuth);
			}else{//该手机发送次数大于15次，不可以发送验证码
				throw new PTMessageException(PTMessageEnum.MOBILE_SEND_MAX_ERROR);
			}
		}else{//该手机未发送过验证码-数据库里无数据
			//mobileCode=smsService.sendIdentify(mobile);
			mobileCode = smsServiceClient.sendYunHeadFeeTokenMessage(mobile, propertySymbol, amount, userName);
			bsAuth=new BsAuth();
			bsAuth.setMobile(mobile);
			bsAuth.setMobileCode(mobileCode);
			bsAuth.setMobileTime(new Date());
			bsAuth.setMobileTimes(1);
			bsAuth.setMobileCodeUseTimes(0);
			bsAuthMapper.insertSelective(bsAuth);
		}
		//如果mobileCode为null,则发送失败，返回错误信息
		return mobileCode==null ? Constants.SEND_CODE_ERROR : mobileCode;
	}
	
}
