package com.pinting.business.aspect;

import com.pinting.business.accounting.service.impl.process.UpdateUserInfoProcess;
import com.pinting.business.dao.BsBankCardMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.BsBankCardExample;
import com.pinting.business.model.BsUser;
import com.pinting.business.util.Constants;
import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_UpdateUserInfo;
import com.pinting.gateway.out.service.App178TransportService;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 钱报178APP平台接入   - 通知用户信息更新切面
 * Created by zhangbao on 2017/8/2.
 */

@Aspect
@Component
@Order(7)
public class UpdateUserInfoAspect {
    private Logger log = LoggerFactory.getLogger(UpdateUserInfoAspect.class);

    @Autowired
    private App178TransportService app178TransportService;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private BsBankCardMapper bsBankCardMapper;

    //正式绑卡后钱报用户发起用户信息更新通知
    @Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserCardOperateServiceImpl.bindCard(..))")
    public void bindCardPointcut(){}

    @AfterReturning("bindCardPointcut()")
    public void bindCardAfter(JoinPoint call) {
        
        try {
	        	
	    	Object arg = call.getArgs()[2];
	        Integer userId = 0;
	        try{
	             userId = Integer.valueOf((String) arg);
	        }catch (NumberFormatException e){
	            log.info("userId类型转换错误,正式绑卡入参发生变化",e);
	            return;
	        }
	        log.info("通知钱报178APP用户信息切面，userId="+userId);
	        // 判断用户是否存在
	        BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
	        if(!(bsUser != null && bsUser.getStatus() == Constants.USER_STATUS_1)) {
	            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
	        }
	        if (bsUser.getAgentId() != null && bsUser.getAgentId() == Constants.AGENT_QIANBAO_ID) {
	            log.info("钱报用户，进行通知");
	            B2GReqMsg_APP178_UpdateUserInfo req = new B2GReqMsg_APP178_UpdateUserInfo();
	            //会员账号传入手机号
	            req.setUser_account(bsUser.getMobile());
	            //联调时对方开发人员说身份证传0，生产待确认
	            req.setId_kind(Constants.QIANBAO_ID_CARD);
	            req.setId_no(bsUser.getIdCard());
	            BsBankCardExample example = new BsBankCardExample();
	            example.createCriteria().andUserIdEqualTo(bsUser.getId()).andIsFirstEqualTo(Constants.BANK_CARD_USERED)
	                    .andStatusEqualTo(Constants.DAFY_BINDING_STAUTS_NORMAL);
	            List<BsBankCard> bankCards = bsBankCardMapper.selectByExample(example);
	            if(CollectionUtils.isEmpty(bankCards)){
	                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,",银行卡信息不存在");
	            }
	            req.setBank_account(bankCards.get(0).getCardNo());
	
				UpdateUserInfoProcess process = new UpdateUserInfoProcess();
				process.setApp178TransportService(app178TransportService);
				process.setUpdateUserInfo(req);
				new Thread(process).start();
	
	
	        }
        } catch (Exception e) {
            log.error("钱报用户，更新信息失败：{}", e.getMessage());
            e.printStackTrace();
        }
    }
}
