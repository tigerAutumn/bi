package com.pinting.business.service.site.impl;


import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsAuthMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsAuth;
import com.pinting.business.model.BsAuthExample;
import com.pinting.business.service.site.EmailService;
import com.pinting.business.util.Constants;
import com.pinting.gateway.out.service.EmailGateService;
/**
 * @Project: business
 * @Title: EmailServiceImpl.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:47:47
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	private EmailGateService emailService;
	
	@Autowired
	private BsAuthMapper bsAuthMapper;
	
	@Override
	public String sendIdentify(String email) {
		String emailCode=Constants.SEND_CODE_ERROR;
		BsAuthExample bsAuthExample = new BsAuthExample();
		bsAuthExample.createCriteria().andEmailEqualTo(email);
		BsAuth bsAuth=null;
		if(bsAuthMapper.selectByExample(bsAuthExample).size()>0){//数据库是否有返回数据
			bsAuth=bsAuthMapper.selectByExample(bsAuthExample).get(0);
		}
		if(bsAuth!=null){//该邮箱已经发送过验证码-数据库里有数据
			emailCode=emailService.sendIdentify(email);
			bsAuth.setEmail(email);
			bsAuth.setEmailCode(emailCode);
			bsAuth.setEmailTime(new Date());
			bsAuth.setEmailCodeUseTimes(0);
			bsAuthMapper.updateByPrimaryKey(bsAuth);
		}else{//该邮箱未发送过验证码-数据库里无数据
			emailCode=emailService.sendIdentify(email);
			bsAuth=new BsAuth();
			bsAuth.setEmail(email);
			bsAuth.setEmailCode(emailCode);
			bsAuth.setEmailTime(new Date());
			bsAuth.setEmailCodeUseTimes(0);
			bsAuthMapper.insertSelective(bsAuth);
		}
		//如果emailCode为null,则发送失败，返回错误信息
		return emailCode==null ? Constants.SEND_CODE_ERROR : emailCode;
	}

	@Override
	public boolean validateIdentity(String email, String emailCode) {
		return validateIdentity(email, emailCode, true);
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
	public boolean validateIdentity(String email, String emailCode,Boolean isDelete)  {
		BsAuthExample bsAuthExample = new BsAuthExample();
		bsAuthExample.createCriteria().andEmailEqualTo(email);
		BsAuth bsAuth=null;
		if(bsAuthMapper.selectByExample(bsAuthExample).size()>0){//数据库是否有返回数据
			bsAuth=bsAuthMapper.selectByExample(bsAuthExample).get(0);
		}
		if(bsAuth!=null){//已经发送验证码
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(bsAuth.getEmailTime());//验证码发送时间
			
			//验证发送时间间隔秒数
			int intervalTime = (int) ((new Date().getTime() - bsAuth
					.getEmailTime().getTime()) / 1000);
			//验证发送时间间隔天数
			int  intervalDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH);
			
			if(Constants.SEND_CODE_VERIFY_OR_EXPIRED.equals(bsAuth.getEmailCode())){//验证码已经失效或者验证码未发送
				throw new PTMessageException(PTMessageEnum.EMAIL_NOT_SEND_ERROR);
			}
			if (bsAuth.getEmailCodeUseTimes() >= 5) {// 验证次数大于五次
				throw new PTMessageException(PTMessageEnum.EMAIL_CODE_USER_TIMES_MAX_ERROR);
			}
			if (new Date().getTime() - bsAuth.getEmailTime().getTime() < 50) {// 防止机器暴力破解
				throw new PTMessageException(PTMessageEnum.EMAIL_CODE_TOO_FAST_ERROR);
			}

			if (intervalTime > Constants.CODE_EXIST_TIME) {// 验证码发送超过120秒，过期
				//如果验证码间隔一天则未发送验证码
				if(intervalDay>0 || intervalTime>Constants.CODE_EXIST_TIME_MAX ){
					throw new PTMessageException(PTMessageEnum.EMAIL_NOT_SEND_ERROR);
				}
				bsAuth.setEmailCode(Constants.SEND_CODE_VERIFY_OR_EXPIRED);//设置验证码错误信息
				bsAuthMapper.updateByPrimaryKey(bsAuth);
				throw new PTMessageException(PTMessageEnum.EMAIL_SEND_DATE_ERROR);
			}
			bsAuth.setEmailCodeUseTimes(bsAuth.getEmailCodeUseTimes() + 1);// 验证次数++
			bsAuthMapper.updateByPrimaryKey(bsAuth);
			if(!bsAuth.getEmailCode().equals(emailCode)){//验证码不正确
				throw new PTMessageException(PTMessageEnum.EMAIL_CODE_WRONG_ERROR);
			}
			if(isDelete){//设置验证码失效
				bsAuth.setEmailCode(Constants.SEND_CODE_VERIFY_OR_EXPIRED);
				bsAuthMapper.updateByPrimaryKey(bsAuth);
			}
			return true;
		}else{//未发送验证码
			throw new PTMessageException(PTMessageEnum.EMAIL_NOT_SEND_ERROR);
		}
	}

}
