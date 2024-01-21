package com.pinting.gateway.in.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinting.business.dao.DafyUserExtMapper;
import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.DafyUserExt;
import com.pinting.business.model.DafyUserExtExample;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.in.service.DafyUserService;
import com.pinting.gateway.in.util.DafyInConstant;
import com.pinting.gateway.in.util.MethodRole;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;

@Service
public class DafyUserServiceImpl implements DafyUserService {
	@Autowired
	private DafyUserExtMapper dafyDafyUserExtMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private BankCardService bankCardService;
	@Autowired
	private SMSServiceClient smsServiceClient;
	
	@Override
	public DafyUserExt findDafyUserByCustomerId(String customerId) {
		DafyUserExtExample example = new DafyUserExtExample();
		example.createCriteria().andDafyUserIdEqualTo(customerId);
		List<DafyUserExt> list = dafyDafyUserExtMapper.selectByExample(example);
		
		return list.size()>0 ? list.get(0) : null;
	}

	@Override
	public boolean addDafyUser(DafyUserExt dafyUserExt) {
		return  dafyDafyUserExtMapper.insertSelective(dafyUserExt) == 1;
	}

	@Override
	@Transactional
	public void bindCardResultInform(String customerId, String result, String resultMsg) {
		/**
		 * 1.判断绑卡是否成功
		 * 2.如果成功：
		 * 	1）达飞用户扩展信息表，修改银行卡绑定状态为已绑定
		 * 	2）用户信息表，修改实名认证+绑定银行卡状态为以实名认证，已绑卡（达飞把实名认证和绑卡做在一起）
		 *  3）银行卡表状态改变 成已绑卡
		 * 3.如果失败：
		 * 	1）达飞用户扩展信息表，修改银行卡绑定状态为失败
		 */
		if(DafyInConstant.DAFY_RESULT_NOTICE_SUCCESS.equals(result)){//绑卡成功
			DafyUserExtExample example = new DafyUserExtExample();
			example.createCriteria().andDafyUserIdEqualTo(customerId);
			List<DafyUserExt> userList = dafyDafyUserExtMapper.selectByExample(example);
			DafyUserExt userExt = userList.get(0);
			//判断绑卡是否成功
			if(userExt.getStatus() == DafyInConstant.DAFY_USER_STATUS_BINDED){
				return;
			}
			//达飞用户扩展表
			DafyUserExt tempUserExt = new DafyUserExt();
			tempUserExt.setId(userExt.getId());
			tempUserExt.setStatus(DafyInConstant.DAFY_USER_STATUS_BINDED);
			tempUserExt.setBindFailReson("银行卡绑定成功");
			tempUserExt.setDafyBindCardTime(new Date());
			dafyDafyUserExtMapper.updateByPrimaryKeySelective(tempUserExt);
			//银行卡表状态改变 成已绑卡
			BsBankCard bankcard = new BsBankCard();
			bankcard.setUserId(userExt.getUserId());
			bankcard.setCardNo(userExt.getBankCard());
			bankcard.setStatus(Constants.BANK_CARD_NORMAL);
			bankcard.setIsFirst(Constants.IS_FIRST_BANK_YES);
			bankcard.setBindTime(new Date());
			bankCardService.modifyBankCardByUserIdAndCardNo(bankcard);
			
			//用户信息表
			BsUser user = userService.findUserById(userExt.getUserId());
			user.setIsBindBank(Constants.IS_BIND_BANK_YES);
			userService.updateBsUser(user);
			//短信通知用户绑定成功，可进行购买
			/*smsGateService.sendMessage(user.getMobile(), 
					new StringBuffer("尊敬的币港湾用户，您已成功绑定银行卡，可进行理财产品购买操作。").toString());*/
			smsServiceClient.sendTemplateMessage(user.getMobile(), TemplateKey.BIND_BANK_CARD_SUC); 
			
		}else{//绑卡失败
			//达飞用户扩展表
			DafyUserExtExample example = new DafyUserExtExample();
			example.createCriteria().andDafyUserIdEqualTo(customerId);
			List<DafyUserExt> userList = dafyDafyUserExtMapper.selectByExample(example);
			DafyUserExt userExt = userList.get(0);
			userExt.setStatus(DafyInConstant.DAFY_USER_STATUS_BIND_FAIL);
			userExt.setBindFailReson(resultMsg);
			dafyDafyUserExtMapper.updateByPrimaryKey(userExt);
			//银行卡表状态改变 成已绑卡
			BsBankCard bankcard = new BsBankCard();
			bankcard.setUserId(userExt.getUserId());
			bankcard.setCardNo(userExt.getBankCard());
			bankcard.setStatus(Constants.BANK_CARD_BINDFAIL);
			bankCardService.modifyBankCardByUserIdAndCardNo(bankcard);
			//短信通知用户绑卡失败
			//用户信息表
			BsUser user = userService.findUserById(userExt.getUserId());
			/*smsGateService.sendMessage(user.getMobile(), 
					new StringBuffer("尊敬的").append(user.getNick())
					.append("用户，抱歉，您的银行卡绑定失败，原因【"+ resultMsg + "】。").toString());*/
			smsServiceClient.sendTemplateMessage(user.getMobile(), TemplateKey.BIND_BANK_CARD_FALL,resultMsg); 
		}
		
	}

	@Override
	@MethodRole("APP")
	public DafyUserExt findDafyUserByUserId(Integer userId) {
		DafyUserExtExample example = new DafyUserExtExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<DafyUserExt> list = dafyDafyUserExtMapper.selectByExample(example);
		
		return list.size()==1 ? list.get(0):null;
	}

	@Override
	public boolean modifyDafyUser(DafyUserExt dafyUserExt) {
		return dafyDafyUserExtMapper.updateByPrimaryKeySelective(dafyUserExt) == 1;
	}

	@Override
	public int countCardNum() {
		DafyUserExtExample example = new DafyUserExtExample();
		example.createCriteria().andStatusEqualTo(Constants.DAFY_BINDING_STAUTS_NORMAL);
		return dafyDafyUserExtMapper.countByExample(example);
	}

	@Override
	public int countDayCardNum() {
		DafyUserExtExample example = new DafyUserExtExample();
		example.createCriteria().andStatusEqualTo(Constants.DAFY_BINDING_STAUTS_NORMAL).andDafyBindCardTimeBetween(DateUtil.addDays(new Date(), -1), new Date());
		return dafyDafyUserExtMapper.countByExample(example);
	}

	@Override
	public boolean addOrModifyDafyUser(DafyUserExt dafyUserExt) {
		DafyUserExtExample example = new DafyUserExtExample();
		example.createCriteria().andUserIdEqualTo(dafyUserExt.getUserId());
		if(dafyDafyUserExtMapper.updateByExampleSelective(dafyUserExt, example) == 0){
			dafyDafyUserExtMapper.insertSelective(dafyUserExt);
		}
		return  true;
	}

}
