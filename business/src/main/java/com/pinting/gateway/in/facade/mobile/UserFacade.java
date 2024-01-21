package com.pinting.gateway.in.facade.mobile;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.manage.BonusGrantPlanService;
import com.pinting.business.service.manage.BsSalesService;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.site.*;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.gateway.in.service.DafyUserService;
import com.pinting.gateway.in.util.InterfaceVersion;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component("appUser")
/**
 * 注册用户管理
 * @Project: business
 * @Title: UserFacade.java
 * @author Linkin
 * @date 2015-2-9 上午10:39:39
 * @Copyright: 2015 bigangwan.com Inc. All rights reserved.
 */
public class UserFacade {
    @Autowired
    private UserService       userService;
    @Autowired
    private SMSService        smsService;
    @Autowired
    private SubAccountService subAccountService;
    @Autowired
    private BankCardService   bankCardService;
    @Autowired
    private DafyUserService   dafyUserService;
    @Autowired
    private BsSysConfigService bsSysConfigService;
    @Autowired
    private InvestService     investService;
    @Autowired
    private UserTransDetailService    userTransDetailService;
    @Autowired
    private BsSalesService    bsSalesService;
    @Autowired
    private RedPacketService redPacketService;
    @Autowired
    private	HFBankDepSiteService hfBankDepSiteService;
    @Autowired
    private QuestionnaireService questionnaireService;
    @Autowired
    private BonusGrantPlanService bonusGrantPlanService;
    @Autowired
    private AssetsService assetsService;
    @Autowired
    private TicketInterestService ticketInterestService;

    @InterfaceVersion("Login/1.0.1")
    public void login_1_0_1(ReqMsg_User_Login req, ResMsg_User_Login res) {
        boolean isValidUser = userService.isValidMobileOrNick(req.getNick());
        if (!isValidUser) {
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }
        BsUser iUser = userService.findUserByNick(req.getNick());
        if (iUser == null) {
            iUser = userService.findUserByMobile(req.getNick());
        }
        if(null != iUser) {
            if(null != iUser.getAgentId()) {
                res.setRealAgentId(iUser.getAgentId());
            }
        }
        /**
         * 判断用户表的锁住时间不为空时，当计时≤15min时，不管密码对错，提示文案：密码错误次数过多，请×分钟后重试
         */
        //理财用户连续登录失败次数限制
        BsSysConfig sys = bsSysConfigService.findKey(Constants.USER_LOGIN_ALWAYS_FAIL_TIME_LIMIT);
        Integer failLimit = sys != null ? Integer.valueOf(sys.getConfValue()) : 5;
        //连续错误次数超限时，锁住时间
        BsSysConfig sysLock = bsSysConfigService.findKey(Constants.USER_LOGIN_LOCK_TIME);
        Integer lockMinute = sysLock != null ? Integer.valueOf(sysLock.getConfValue()) : 15;
        if(iUser.getLoginLockTime() != null){
        	Integer diffSeconds = DateUtil.getDiffeSeconds(new Date(), iUser.getLoginLockTime());//已锁住秒数
        	if(diffSeconds < lockMinute*60){
        		throw new PTMessageException(PTMessageEnum.USER_LOGIN_FAIL_LOCKEDE_AFTER,"，请"+(int)Math.ceil(MoneyUtil.divide(lockMinute*60 - diffSeconds, 60).doubleValue())+"分钟后重试");
        	}else if(iUser.getLoginAlwaysFailTimes() != null && iUser.getLoginAlwaysFailTimes() >= failLimit){
        		//已解锁，连续失败次数置为0
        		BsUser userTemp = new BsUser();
        		userTemp.setId(iUser.getId());
        		userTemp.setLoginAlwaysFailTimes(0);
        		userService.updateBsUser(userTemp);
        		iUser = userService.findUserById(iUser.getId());
        	}
        }

        // 失败时间为空，证明从没有输错过密码
        if(null == iUser.getLoginFailTime()) {
            // 密码输入错误，更新失败次数和时间
            if (iUser.getPassword() != null
                    && !iUser.getPassword().equals(
                    MD5Util.encryptMD5(req.getPassword() + MD5Util.getEncryptkey()))) {
                BsUser iUser3 = new BsUser();
                iUser3.setId(iUser.getId());
                iUser3.setLoginFailTimes(1);
                iUser3.setLoginFailTime(new Date());
                userService.updateBsUser(iUser3);
                userLoginAlwaysFail(iUser, failLimit, lockMinute);
                throw new PTMessageException(PTMessageEnum.USER_PASS_ERROR);
            }
            // 登录成功
            else {
                BsUser iUser2 = new BsUser();
                iUser2.setId(iUser.getId());
                iUser2.setLoginTime(new Date());
                iUser2.setLoginFailTimes(0);
                iUser2.setLoginAlwaysFailTimes(0);
                userService.updateBsUser(iUser2);
                res.setNick(iUser.getNick());
                res.setMobile(iUser.getMobile());
                res.setUserId(iUser.getId());
                res.setUserType(iUser.getUserType());
                // 钱报
                if(null != iUser.getAgentId() && iUser.getAgentId().equals(15)) {
                    res.setAgentId("qianbao");
                } else {
                    res.setAgentId("");
                }
            }
        }
        // 失败时间不为空，证明输错过密码
        else {
            // 在同一天输错过
            if(DateUtil.isSameDay(iUser.getLoginFailTime(), new Date())){
                // 超过十次
                if(null!=iUser.getLoginFailTimes() && iUser.getLoginFailTimes() > 10){
                    throw new PTMessageException(PTMessageEnum.USER_LOGIN_FAIL_OVER_MUCH);
                }
                // 没有超过十次
                else {
                    // 密码输入错误，更新失败次数和时间
                    if (iUser.getPassword() != null
                            && !iUser.getPassword().equals(
                            MD5Util.encryptMD5(req.getPassword() + MD5Util.getEncryptkey()))) {
                        BsUser iUser3 = new BsUser();
                        iUser3.setId(iUser.getId());
                        iUser3.setLoginFailTimes(iUser.getLoginFailTimes() == null?1:iUser.getLoginFailTimes() + 1);
                        iUser3.setLoginFailTime(new Date());
                        userService.updateBsUser(iUser3);
                        userLoginAlwaysFail(iUser, failLimit, lockMinute);
                        throw new PTMessageException(PTMessageEnum.USER_PASS_ERROR);
                    }
                    // 密码输入成功
                    else {
                        BsUser iUser2 = new BsUser();
                        iUser2.setId(iUser.getId());
                        iUser2.setLoginTime(new Date());
                        iUser2.setLoginFailTimes(0);
                        iUser2.setLoginAlwaysFailTimes(0);
                        userService.updateBsUser(iUser2);
                        res.setNick(iUser.getNick());
                        res.setMobile(iUser.getMobile());
                        res.setUserId(iUser.getId());
                        res.setUserType(iUser.getUserType());
                        // 钱报
                        if(null != iUser.getAgentId() && iUser.getAgentId().equals(15)) {
                            res.setAgentId("qianbao");
                        } else {
                            res.setAgentId("");
                        }
                    }
                }
            }
            // 同一天没有输错过
            else {
                // 密码输入错误，更新失败次数和时间
                if (iUser.getPassword() != null
                        && !iUser.getPassword().equals(
                        MD5Util.encryptMD5(req.getPassword() + MD5Util.getEncryptkey()))) {
                    BsUser iUser3 = new BsUser();
                    iUser3.setId(iUser.getId());
                    iUser3.setLoginFailTimes(1);
                    iUser3.setLoginFailTime(new Date());
                    userService.updateBsUser(iUser3);
                    userLoginAlwaysFail(iUser, failLimit, lockMinute);
                    throw new PTMessageException(PTMessageEnum.USER_PASS_ERROR);
                }
                // 密码输入成功
                else {
                    BsUser iUser2 = new BsUser();
                    iUser2.setId(iUser.getId());
                    iUser2.setLoginTime(new Date());
                    iUser2.setLoginFailTimes(0);
                    iUser2.setLoginAlwaysFailTimes(0);
                    userService.updateBsUser(iUser2);
                    res.setNick(iUser.getNick());
                    res.setMobile(iUser.getMobile());
                    res.setUserId(iUser.getId());
                    res.setUserType(iUser.getUserType());
                    // 钱报
                    if(null != iUser.getAgentId() && iUser.getAgentId().equals(15)) {
                        res.setAgentId("qianbao");
                    } else {
                        res.setAgentId("");
                    }
                }
            }
        }
    }
    
    //判断密码失败情况下，是否连续失败等校验
  	private void userLoginAlwaysFail(BsUser user, Integer failLimit, Integer lockMinute) {
  		/**
           * 1，连续5次失败
           * M=第几次，N=总次数， 当M=N-1时，提示文案：密码多次错误，您还可尝试1次；
           * 当M=N时，提示文案：密码错误次数过多，请×分钟后重试； 账号锁定15min不予登录（15min可配置）
           */
  		
          BsUser userTemp = new BsUser();
          userTemp.setId(user.getId());
          userTemp.setLoginAlwaysFailTimes(user.getLoginAlwaysFailTimes() == null?1:user.getLoginAlwaysFailTimes()+1);        
          if(userTemp.getLoginAlwaysFailTimes() == failLimit-1){
          	userService.updateBsUser(userTemp);
          	throw new PTMessageException(PTMessageEnum.USER_LOGIN_FAIL_LAST);
          }else if(userTemp.getLoginAlwaysFailTimes() == failLimit){
          	userTemp.setLoginLockTime(new Date());
          	userService.updateBsUser(userTemp);
          	throw new PTMessageException(PTMessageEnum.USER_LOGIN_FAIL_LOCKEDE,"，请"+lockMinute+"分钟后重试");
          }else{
          	 userService.updateBsUser(userTemp);
          }
  	}

    @InterfaceVersion("Regist/1.0.1")
    public void regist_1_0_1(ReqMsg_User_Regist req, ResMsg_User_Regist res) {
        Integer dafyUserId = null;
        BsUser user = new BsUser();
        user.setUserType(Constants.USER_TYPE_NORMAL);
        //邀请码与渠道Id不能同时存在
        if (req.getRecommendId() != null && !"".equals(req.getRecommendId())
                && req.getAgentId() != null && !"".equals(req.getAgentId())) {
            throw new PTMessageException(PTMessageEnum.ILLEGAL_REQUEST);
        }

        //验证邀请码是否存在
        if (req.getRecommendId() != null && !"".equals(req.getRecommendId())) {
            boolean isValidInvitationCode = userService.isValidCode(Integer.parseInt(req.getRecommendId()));
            if (!isValidInvitationCode) {
                throw new PTMessageException(PTMessageEnum.INVITATIONCODE_NO_EXIT);
            }
        }else{
            //销售人员邀请码验证
            if(StringUtils.isNotBlank(req.getInviteCode())){
                BsSales sales = new BsSales();
                sales.setInviteCode(req.getInviteCode());
                sales.setStatus(1);
                sales = bsSalesService.selectBsSales(sales);
                if (sales == null) {
                    throw new PTMessageException(PTMessageEnum.INVITATIONCODE_NO_EXIT);
                }
            }else if (StringUtils.isNotBlank(req.getManagerInviteCode())) {
                //boolean isValidInvitationCode =  userService.isValidCodeMobile(req.getManagerInviteCode());
                //根据电话(邀请码)查询达飞业务员信息是否存在
                Map<String,Object> map = userService.isExistClientManager(req.getManagerInviteCode());
                boolean isValidInvitationCode =  (boolean)map.get("result");
                dafyUserId = (Integer)map.get("dafyUserId");
                if (!isValidInvitationCode) {
                    throw new PTMessageException(PTMessageEnum.INVITATIONCODE_NO_EXIT);
                }
                req.setInviteCode(req.getManagerInviteCode());
            }
        }


        //校验渠道码是否存在 并返回渠道信息
        if (req.getAgentId() != null && !"".equals(req.getAgentId())) {
            BsAgent agent = userService.findAgentByCode(req.getAgentId());
            if (agent == null) {
                throw new PTMessageException(PTMessageEnum.USER_AGENT_ERROR);
            }
            user.setAgentId(agent.getId());

        }

        // 是否钱报入口
        if("qianbao".equals(req.getQianbao())) {
            BsAgent agent = userService.findAgentById(15);
            if(null == agent) {
                throw new PTMessageException(PTMessageEnum.QIANBAO_AGENT_ERROR);
            }
            user.setAgentId(15);    // 钱报Id
            res.setQianbaoFlag(user.getAgentId());
        }

        //手机验证是否存在
        boolean isValidUser = userService.isValidMobile(req.getMobile());
        if (isValidUser) {
            throw new PTMessageException(PTMessageEnum.MOBILE_IS_EXIT);
        }
        //验证昵称是否存在
        /*isValidUser = userService.isValidNick(req.getNick());
        if (isValidUser) {
            throw new PTMessageException(PTMessageEnum.NICK_IS_EXIT);
        }*/



        //短信校验
        boolean IsValidate = smsService.validateIdentity(req.getMobile(), req.getMobileCode(), true);
        if (!IsValidate) {
            throw new PTMessageException(PTMessageEnum.MOBILE_CODE_WRONG_ERROR);
        }
        user.setNick(req.getNick());
        user.setPassword(MD5Util.encryptMD5(req.getPassword() + MD5Util.getEncryptkey()));
        user.setMobile(req.getMobile());
        user.setStatus(1);
        user.setIsBindBank(2);
        user.setIsBindName(2);
        user.setCurrentInterest(0.0);
        user.setCurrentBonus(0.0);
        user.setTotalInterest(0.0);
        user.setTotalBonus(0.0);
        user.setCanWithdraw(0.0);
        user.setTotalTrans(0);
        user.setRelation(-1);
        user.setRegTerminal(req.getRegTerminal());
        if(StringUtils.isNotBlank(req.getRecommendId())){
            user.setRecommendId(Integer.parseInt(req.getRecommendId()));
        }
        user.setRegisterTime(new Date());
        user.setReturnPath(2);

        boolean isCreateUser = userService.registerUser(user,req.getInviteCode(),dafyUserId);
        if (!isCreateUser) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
        }

        res.setMobile(req.getMobile());
        res.setNick(req.getNick());
        res.setMobileCode(req.getMobileCode());
        res.setUserId(userService.findUserByMobile(req.getMobile()).getId());
        res.setUserType(user.getUserType());
        res.setAgentId(user.getAgentId());

    }




    @InterfaceVersion("Login/1.0.0")
    public void login(ReqMsg_User_Login req, ResMsg_User_Login res) {
        boolean isValidUser = userService.isValidMobileOrNick(req.getNick());
        if (!isValidUser) {
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }
        BsUser iUser = userService.findUserByNick(req.getNick());
        if (iUser == null) {
            iUser = userService.findUserByMobile(req.getNick());
        }
        
        // 失败时间为空，证明从没有输错过密码
        if(null == iUser.getLoginFailTime()) {
            // 密码输入错误，更新失败次数和时间
            if (iUser.getPassword() != null
                && !iUser.getPassword().equals(
                    MD5Util.encryptMD5(req.getPassword() + MD5Util.getEncryptkey()))) {
                BsUser iUser3 = new BsUser();
                iUser3.setId(iUser.getId());
                iUser3.setLoginFailTimes(1);
                iUser3.setLoginFailTime(new Date());
                userService.updateBsUser(iUser3);
                throw new PTMessageException(PTMessageEnum.USER_PASS_ERROR);
            }
            // 登录成功
            else {
                BsUser iUser2 = new BsUser();
                iUser2.setId(iUser.getId());
                iUser2.setLoginTime(new Date());
                iUser2.setLoginFailTimes(0);
                userService.updateBsUser(iUser2);
                res.setNick(iUser.getNick());
                res.setMobile(iUser.getMobile());
                res.setUserId(iUser.getId());
                res.setUserType(iUser.getUserType());
                // 钱报
                if(null != iUser.getAgentId() && iUser.getAgentId().equals(15)) {
                    res.setAgentId("qianbao");
                } else {
                    res.setAgentId("");
                }
            }
        }
        // 失败时间不为空，证明输错过密码
        else {
            // 在同一天输错过
            if(DateUtil.isSameDay(iUser.getLoginFailTime(), new Date())){
                // 超过十次
                if(null!=iUser.getLoginFailTimes() && iUser.getLoginFailTimes() > 10){
                    throw new PTMessageException(PTMessageEnum.USER_LOGIN_FAIL_OVER_MUCH);
                }
                // 没有超过十次
                else {
                    // 密码输入错误，更新失败次数和时间
                    if (iUser.getPassword() != null
                        && !iUser.getPassword().equals(
                            MD5Util.encryptMD5(req.getPassword() + MD5Util.getEncryptkey()))) {
                        BsUser iUser3 = new BsUser();
                        iUser3.setId(iUser.getId());
                        iUser3.setLoginFailTimes(iUser.getLoginFailTimes() == null?1:iUser.getLoginFailTimes() + 1);
                        iUser3.setLoginFailTime(new Date());
                        userService.updateBsUser(iUser3);
                        throw new PTMessageException(PTMessageEnum.USER_PASS_ERROR);
                    }
                    // 密码输入成功
                    else {
                        BsUser iUser2 = new BsUser();
                        iUser2.setId(iUser.getId());
                        iUser2.setLoginTime(new Date());
                        iUser2.setLoginFailTimes(0);
                        userService.updateBsUser(iUser2);
                        res.setNick(iUser.getNick());
                        res.setMobile(iUser.getMobile());
                        res.setUserId(iUser.getId());
                        res.setUserType(iUser.getUserType());
                        // 钱报
                        if(null != iUser.getAgentId() && iUser.getAgentId().equals(15)) {
                            res.setAgentId("qianbao");
                        } else {
                            res.setAgentId("");
                        }
                    }
                }
            }
            // 同一天没有书错过
            else {
                // 密码输入错误，更新失败次数和时间
                if (iUser.getPassword() != null
                    && !iUser.getPassword().equals(
                        MD5Util.encryptMD5(req.getPassword() + MD5Util.getEncryptkey()))) {
                    BsUser iUser3 = new BsUser();
                    iUser3.setId(iUser.getId());
                    iUser3.setLoginFailTimes(1);
                    iUser3.setLoginFailTime(new Date());
                    userService.updateBsUser(iUser3);
                    throw new PTMessageException(PTMessageEnum.USER_PASS_ERROR);
                }
                // 密码输入成功
                else {
                    BsUser iUser2 = new BsUser();
                    iUser2.setId(iUser.getId());
                    iUser2.setLoginTime(new Date());
                    iUser2.setLoginFailTimes(0);
                    userService.updateBsUser(iUser2);
                    res.setNick(iUser.getNick());
                    res.setMobile(iUser.getMobile());
                    res.setUserId(iUser.getId());
                    res.setUserType(iUser.getUserType());
                    // 钱报
                    if(null != iUser.getAgentId() && iUser.getAgentId().equals(15)) {
                        res.setAgentId("qianbao");
                    } else {
                        res.setAgentId("");
                    }
                }
            }
        }
    }

    @InterfaceVersion("Regist/1.0.0")
    public void regist(ReqMsg_User_Regist req, ResMsg_User_Regist res) {
    	Integer dafyUserId = null;
    	BsUser user = new BsUser();
        user.setUserType(Constants.USER_TYPE_NORMAL);
        //邀请码与渠道Id不能同时存在
        if (req.getRecommendId() != null && !"".equals(req.getRecommendId())
            && req.getAgentId() != null && !"".equals(req.getAgentId())) {
            throw new PTMessageException(PTMessageEnum.ILLEGAL_REQUEST);
        }

        //验证邀请码是否存在
        if (req.getRecommendId() != null && !"".equals(req.getRecommendId())) {
            boolean isValidInvitationCode = userService.isValidCode(Integer.parseInt(req.getRecommendId()));
            if (!isValidInvitationCode) {
                throw new PTMessageException(PTMessageEnum.INVITATIONCODE_NO_EXIT);
            }
        }else{
        	//销售人员邀请码验证
            if(StringUtils.isNotBlank(req.getInviteCode())){
                BsSales sales = new BsSales();
                sales.setInviteCode(req.getInviteCode());
                sales.setStatus(1);
                sales = bsSalesService.selectBsSales(sales);
                if (sales == null) {
                    throw new PTMessageException(PTMessageEnum.INVITATIONCODE_NO_EXIT);
                }
            }else if (StringUtils.isNotBlank(req.getManagerInviteCode())) {
            	//boolean isValidInvitationCode =  userService.isValidCodeMobile(req.getManagerInviteCode());
            	//根据电话(邀请码)查询达飞业务员信息是否存在
            	Map<String,Object> map = userService.isExistClientManager(req.getManagerInviteCode());
            	boolean isValidInvitationCode =  (boolean)map.get("result");
            	dafyUserId = (Integer)map.get("dafyUserId");
            	if (!isValidInvitationCode) {
                    throw new PTMessageException(PTMessageEnum.INVITATIONCODE_NO_EXIT);
                }
            	req.setInviteCode(req.getManagerInviteCode());
            }
        }
      

        //校验渠道码是否存在 并返回渠道信息
        if (req.getAgentId() != null && !"".equals(req.getAgentId())) {
            BsAgent agent = userService.findAgentByCode(req.getAgentId());
            if (agent == null) {
                throw new PTMessageException(PTMessageEnum.USER_AGENT_ERROR);
            }
            user.setAgentId(agent.getId());
            
        }
        
        // 是否钱报入口
        if("qianbao".equals(req.getQianbao())) {
            BsAgent agent = userService.findAgentById(15);
            if(null == agent) {
                throw new PTMessageException(PTMessageEnum.QIANBAO_AGENT_ERROR);
            }
            user.setAgentId(15);    // 钱报Id
            res.setQianbaoFlag(user.getAgentId());
        }

        //手机验证是否存在
        boolean isValidUser = userService.isValidMobile(req.getMobile());
        if (isValidUser) {
            throw new PTMessageException(PTMessageEnum.MOBILE_IS_EXIT);
        }
        //验证昵称是否存在
        /*isValidUser = userService.isValidNick(req.getNick());
        if (isValidUser) {
            throw new PTMessageException(PTMessageEnum.NICK_IS_EXIT);
        }*/
        
       

        //短信校验
        boolean IsValidate = smsService.validateIdentity(req.getMobile(), req.getMobileCode(), true);
        if (!IsValidate) {
            throw new PTMessageException(PTMessageEnum.MOBILE_CODE_WRONG_ERROR);
        }
        user.setNick(req.getNick());
        user.setPassword(MD5Util.encryptMD5(req.getPassword() + MD5Util.getEncryptkey()));
        user.setMobile(req.getMobile());
        user.setStatus(1);
        user.setIsBindBank(2);
        user.setIsBindName(2);
        user.setCurrentInterest(0.0);
        user.setCurrentBonus(0.0);
        user.setTotalInterest(0.0);
        user.setTotalBonus(0.0);
        user.setCanWithdraw(0.0);
        user.setTotalTrans(0);
        user.setRelation(-1);
        user.setRegTerminal(req.getRegTerminal());
        if(StringUtils.isNotBlank(req.getRecommendId())){
        	user.setRecommendId(Integer.parseInt(req.getRecommendId()));	
        }
        user.setRegisterTime(new Date());
        user.setReturnPath(2);

        boolean isCreateUser = userService.registerUser(user,req.getInviteCode(),dafyUserId);
        if (!isCreateUser) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
        }

        res.setMobile(req.getMobile());
        res.setNick(req.getNick());
        res.setMobileCode(req.getMobileCode());
        res.setUserId(userService.findUserByMobile(req.getMobile()).getId());
        res.setUserType(user.getUserType());
        
    }

    @InterfaceVersion("FindPassword/1.0.0")
    public void findPassword(ReqMsg_User_FindPassword req, ResMsg_User_FindPassword res) {

        //修改忘记密码
        BsUser user = new BsUser();
        boolean isValidUser = userService.isValidMobile(req.getMobile());
        if (!isValidUser) {
            throw new PTMessageException(PTMessageEnum.MOBILE_NO_EXIT);
        }
        user.setMobile(req.getMobile());
        user.setPassword(MD5Util.encryptMD5(req.getPassword() + MD5Util.getEncryptkey()));
        BsUser user2 = userService.findUserByMobile(req.getMobile());
        if (user2.getPayPassword() != null && user2.getPayPassword().equals(user.getPassword())) {
            throw new PTMessageException(PTMessageEnum.PAY_LOGIN_PASS_SAME_ERROR);
        }
        if (user2.getPassword().equals(user.getPassword())) {
            throw new PTMessageException(PTMessageEnum.PASS_LOGIN_PASS_SAME_ERROR);
        }
        if (!smsService.validateIdentity(req.getMobile(), req.getMobileCode())) {
            throw new PTMessageException(PTMessageEnum.MOBILE_CODE_WRONG_ERROR);
        }
        userService.updateUserPasswordByMobile(user.getPassword(), user.getMobile());

        res.setNick(user2.getNick());
    }

    @InterfaceVersion("FindPayPassword/1.0.0")
    public void findPayPassword(ReqMsg_User_FindPayPassword req, ResMsg_User_FindPayPassword resp) {
        //修改忘记支付密码
        BsUser bsUser = userService.findUserByMobile(req.getMobile());
        if (bsUser == null) {
            throw new PTMessageException(PTMessageEnum.MOBILE_NO_EXIT);
        }
        /*		if(!bsUser.getId().equals(req.getUserId())){
        			throw new PTMessageException(PTMessageEnum.USER_MOBILE_NOT_MATCH);
        		}*/
        String payPassword = MD5Util.encryptMD5(req.getPassword() + MD5Util.getEncryptkey());
        if (bsUser.getPassword().equals(payPassword)) {
            throw new PTMessageException(PTMessageEnum.PASS_LOGIN_PAY_SAME_ERROR);
        }
        if (bsUser.getPayPassword().equals(payPassword)) {
        	throw new PTMessageException(PTMessageEnum.PAY_LOGIN_PAY_SAME_ERROR);
        }
        if (!smsService.validateIdentity(req.getMobile(), req.getMobileCode())) {
            throw new PTMessageException(PTMessageEnum.MOBILE_CODE_WRONG_ERROR);
        }
        bsUser.setPayPassword(payPassword);
        bsUser.setPayFailTimes(0);
        userService.updateBsUser(bsUser);
        resp.setIsValidateSuccess(true);

    }

    @InterfaceVersion("BankListQuery/1.0.0")
    public void bankListQuery(ReqMsg_User_BankListQuery req, ResMsg_User_BankListQuery res) {

        /**
         * 1.判断当前用户是否存在
         * 2.判断当前用户绑卡处于什么状态
         * 	 a.绑卡中
         * 		页面展示当前绑卡的中的银行卡信息，以及之前所有绑卡失败的银行卡信息，此时无论点击哪种银行卡都会提示"当前正在绑定中，无法修改银行卡信息"
         * 	 c.绑卡成功
         * 		页面只展示当前成功的银行卡信息，目前有且只有一张是成功的，此时无法再增加和修改银行卡信息
         *   d.绑卡失败
         *      将所有绑卡失败的银行卡信息展示，用户可以选择重新增加银行卡信息，或者选择其中的一条的银行卡内容进行更新。
         */

        BsUser bsUser = userService.findUserById(req.getUserId());
        if (bsUser == null) {
            //用户不存在
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }

        res.setIdCard(bsUser.getIdCard()); //拿到用户的身份证号，姓名
        res.setUserName(bsUser.getUserName());
        res.setIsBindName(bsUser.getIsBindName()); //是否已经实名认证，如果已经实名认证，在购买时不允许修改
        res.setIsBindBank(bsUser.getIsBindBank());
        res.setIsBinding(2);
        List<BankCardVO> bankCardList = bankCardService.findBankCardVOByStatusAndUserId(
            req.getUserId(), Constants.BANK_CARD_NORMAL);
        res.setBankCards(BeanUtils.classToArrayList(bankCardList));
        /*if (dafyUser == null) { //当前用户没有发起绑卡交易
            res.setIsBinding(0);
        } else if (dafyUser.getStatus() == Constants.DAFY_BINDING_STAUTS_BINDING) { //说明当前用户银行卡正在绑定中
            res.setIsBinding(1);
            //查询所有的银行卡信息，按照状态进行排序
            List<BankCardVO> bankCardList = bankCardService.findBankCardVOByStatusAndUserId(
                req.getUserId(), null);
            //            ArrayList<BsBankCard> bankCardList = bankCardService.findBankCardInfoByUserId(req
            //                .getUserId());
            res.setBankCards(BeanUtils.classToArrayList(bankCardList));
        } else if (dafyUser.getStatus() == Constants.DAFY_BINDING_STAUTS_NORMAL) { //当前银行卡绑定成功
            res.setIsBinding(2);
            List<BankCardVO> bankCardList = bankCardService.findBankCardVOByStatusAndUserId(
                req.getUserId(), Constants.BANK_CARD_NORMAL);
            //            ArrayList<BsBankCard> bankCardList = bankCardService.findBankCardByStatusAndUserId(
            //                Constants.BANK_CARD_NORMAL, req.getUserId());
            res.setBankCards(BeanUtils.classToArrayList(bankCardList));
        } else {
            res.setIsBinding(3);
            List<BankCardVO> bankCardList = bankCardService.findBankCardVOByStatusAndUserId(
                req.getUserId(), null);
            //            ArrayList<BsBankCard> bankCardList = bankCardService.findBankCardInfoByUserId(req
            //                .getUserId());
            res.setBankCards(BeanUtils.classToArrayList(bankCardList));
        }
        */
    }

    //校验手机，邮箱，昵称中的一种，校验请求bean中存在的数据
    @InterfaceVersion("InfoValidation/1.0.0")
    public void infoValidation(ReqMsg_User_InfoValidation req, ResMsg_User_InfoValidation res) {
        //手机验证是否存在
        if (StringUtil.isNotEmpty(req.getMobile())) {
        	boolean isFreezeUser = userService.isFreezeUserByMobile(req.getMobile());
        	if (isFreezeUser) {
        		throw new PTMessageException(PTMessageEnum.USER_STATUS_FROST_ERROR);
        	}
            boolean isValidUser = userService.isValidMobile(req.getMobile());
            if (isValidUser) {
                throw new PTMessageException(PTMessageEnum.MOBILE_IS_EXIT);
            } else {
                throw new PTMessageException(PTMessageEnum.MOBILE_NO_EXIT);
            }
        }
        if (req.getNick() != null) {
        	boolean isFreezeUser = userService.isFreezeUserByNick(req.getNick());
        	if (isFreezeUser) {
        		throw new PTMessageException(PTMessageEnum.USER_STATUS_FROST_ERROR);
        	}
            boolean isValidUser = userService.isValidNick(req.getNick());
            if (isValidUser) {
                throw new PTMessageException(PTMessageEnum.NICK_IS_EXIT);
            }
        }
        if (req.getEmail() != null) {
            if (userService.findUserByEmail(req.getEmail()) != null) {
                throw new PTMessageException(PTMessageEnum.EMAIL_IS_EXIT);
            }
        }
    }
    
    @InterfaceVersion("InfoQuery/1.0.0")
    public void infoQuery(ReqMsg_User_InfoQuery req, ResMsg_User_InfoQuery resp) {
        BsUser bsUser = userService.findUserById(req.getUserId());

        if (bsUser == null) {
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }

        
        //判断是否显示回款路径菜单
/*        String isShowReturnPath = "no";
        Date registerTime = bsUser.getRegisterTime();
        Date returnPathStartTime = DateUtil.parseDate(Constants.RETURN_PATH_START_TIME);
        Date returnPathOverTime = DateUtil.parseDate(Constants.RETURN_PATH_OVER_TIME);
        if (registerTime.compareTo(returnPathStartTime) < 0  && new Date().compareTo(returnPathOverTime) < 0) {
        	isShowReturnPath = "yes";
		}
        resp.setIsShowReturnPath(isShowReturnPath);*/
        String isShowReturnPath = "no";
        Date returnPathOverTime = DateUtil.parseDate(Constants.RETURN_PATH_OVER_TIME);
        if (new Date().compareTo(returnPathOverTime) < 0) {
        	isShowReturnPath = "yes";
		}
        resp.setIsShowReturnPath(isShowReturnPath);
        
        
        if (StringUtils.isBlank(bsUser.getPayPassword())) {
            resp.setExcitPaypassword(2); //当前无支付密码
        } else {
            resp.setExcitPaypassword(1); //当前有支付密码
        }
        resp.setReturnPath(bsUser.getReturnPath());
        resp.setUserName(bsUser.getUserName());
        resp.setNick(bsUser.getNick());
        resp.setMobile(bsUser.getMobile());
        resp.setEmail(bsUser.getEmail());
        resp.setIsBindName(bsUser.getIsBindName());
        resp.setUrgentName(bsUser.getUrgentName());
        resp.setUrgentMobile(bsUser.getUrgentMobile());
        resp.setRelation(bsUser.getRelation());
        resp.setIdCard(bsUser.getIdCard());
        resp.setIsBindBank(bsUser.getIsBindBank());
        resp.setStatus(bsUser.getStatus());
        resp.setUserId(bsUser.getId());
//        DafyUserExt dafyUser = dafyUserService.findDafyUserByUserId(req.getUserId());
//        if (dafyUser != null) {
//            resp.setDafyStatus(dafyUser.getStatus());
//        } else {
//            resp.setDafyStatus(bsUser.getIsBindBank());
//        }

        // 回款卡
        if(null == bsUser.getReturnPath()) {
            bsUser.setReturnPath(2);
        }
        if(Constants.RETURN_PATH_BANKCARD == bsUser.getReturnPath()){
            BsBankCard bankCard = bankCardService.findFirstBankCardByUserId(req.getUserId());
            if (null == bankCard) {
//				userService.changeUserReturnPath(req.getUserId());
            	resp.setBankName("账户余额");
			}else {
				 resp.setBankName(bankCard.getBankName());
		         resp.setCardNo(bankCard.getCardNo());
		         resp.setCardId(bankCard.getId());
			}
           
        } else {
            resp.setBankName("账户余额");
        }
    }

    /**
     * 用户资产信息查询
     * @param req
     * @param res
     * @ 
     */
    @InterfaceVersion("AssetInfoQuery/1.0.0")
    public void assetInfoQuery(ReqMsg_User_AssetInfoQuery req, ResMsg_User_AssetInfoQuery res) {
        BsUserAssetVO bsUserAsset = userService.findUserAssetByUserId(req.getUserId());
        int investNum = userService.countInTheInvestmentByUserId(req.getUserId());
        Double amountTrans = userService.queryAmountTrans(req.getUserId());
        amountTrans = amountTrans == null ? 0 : amountTrans;
        BsSubAccount bsSubAccountDepjsh = subAccountService.findDEPJSHSubAccountByUserId(req.getUserId());
        bsSubAccountDepjsh = bsSubAccountDepjsh == null ? new BsSubAccount() : bsSubAccountDepjsh;
        bsSubAccountDepjsh.setFreezeBalance(bsSubAccountDepjsh.getFreezeBalance() == null ? 0d : bsSubAccountDepjsh.getFreezeBalance());
        bsSubAccountDepjsh.setCanWithdraw(bsSubAccountDepjsh.getCanWithdraw() == null ? 0d : bsSubAccountDepjsh.getCanWithdraw());
        bsSubAccountDepjsh.setBalance(bsSubAccountDepjsh.getBalance() == null ? 0d : bsSubAccountDepjsh.getBalance());
        bsSubAccountDepjsh.setAvailableBalance(bsSubAccountDepjsh.getAvailableBalance() == null ? 0d : bsSubAccountDepjsh.getAvailableBalance());
        bsSubAccountDepjsh.setOpenBalance(bsSubAccountDepjsh.getOpenBalance() == null ? 0d : bsSubAccountDepjsh.getOpenBalance());
        HashMap<String, Object> userAsset = BeanUtils.transBeanMap(bsUserAsset);
        //总资产=总余额+当前投资收益=投资本金+结算户可用余额（推荐奖励金）+当前投资收益 + 债转金额
        if (userAsset != null && userAsset.size() > 0) {
            res.setAccountId((Integer) userAsset.get("accountId"));
            res.setAssetAmount(MoneyUtil.add((Double) userAsset.get("totalBalance")
                               ,MoneyUtil.add((Double) userAsset.get("currentInterest"), amountTrans).doubleValue()).doubleValue());
            res.setBonus((Double) userAsset.get("currentBonus"));
            
            res.setEmail((String) userAsset.get("email"));
            res.setIdCard((String) userAsset.get("idCard"));
            res.setInvestEarnings((Double) userAsset.get("currentInterest"));//投资收益
            res.setIsBindBank((Integer) userAsset.get("isBindBank"));
            res.setIsBindName((Integer) userAsset.get("isBindName"));
            res.setMobile((String) userAsset.get("mobile"));
            res.setNick((String) userAsset.get("nick"));
            BsSubAccount jshAccount = subAccountService.findJSHSubAccountByUserId(req.getUserId());//用户子账户结算户
            BsSubAccount jljAccount = subAccountService.findJLJSubAccountByUserId(req.getUserId());//用户子账户奖励金户
            Double regularAmount = CalculatorUtil.calculate("a-a-a-a", userAsset.get("totalBalance")==null?0.0:(Double)userAsset.get("totalBalance"), bsSubAccountDepjsh.getBalance(), jshAccount.getBalance(), jljAccount.getBalance());
            res.setRegularAmount(regularAmount);//当前投资本金=总余额-结算户余额-奖励金余额
            res.setBalance((Double) jshAccount.getBalance());//结算户余额
            res.setCanWithdraw((Double)jshAccount.getCanWithdraw() );//JSH可提现金额
            res.setAvailableBalance((Double)jshAccount.getAvailableBalance());//JSH可用余额
            res.setFreezeBalance((Double)jshAccount.getFreezeBalance());//JSH冻结金额
            res.setJljBalance((Double)jljAccount.getBalance()); //JLJ余额
            res.setJljCanWithdraw((Double)jljAccount.getCanWithdraw()); //JLJ可提现余额
            res.setStatus((Integer) userAsset.get("status"));
            res.setTotalBonus((Double) userAsset.get("totalBonus"));//累计推荐奖励
            res.setTotalInvestEarnings((Double) userAsset.get("totalInterest"));//累计投资收益
            res.setTotalTransNum((Integer) userAsset.get("totalTrans"));
            res.setUserId((Integer) userAsset.get("id"));
            res.setUserName((String) userAsset.get("userName"));
            res.setDafyStatus((Integer) userAsset.get("dafyStatus"));
//            res.setInvestNum((Integer) userAsset.get("investNum"));
            res.setInvestNum(investNum);
            res.setBankCardCount((Integer) userAsset.get("bankcardCount")); //银行卡张数

            List<BsDailyInterest> dayInterestList = investService.findDailyInterestByUserAndDay(
                req.getUserId(), DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date())));
            
        	Double todayInterest = 0d;
        	if(dayInterestList != null) {
            	for (BsDailyInterest bsDailyInterest : dayInterestList) {
            		bsDailyInterest.setInterest(bsDailyInterest.getInterest() == null ? 0d : bsDailyInterest.getInterest());
                    todayInterest = MoneyUtil.add(todayInterest, bsDailyInterest.getInterest()).doubleValue();
				}
        	}
            
            res.setDayInvestEarnings(todayInterest);
            
            //判断是否显示回款路径菜单
            String isShowReturnPath = "no";
            Date registerTime = bsUserAsset.getRegisterTime();
            Date returnPathOverTime = DateUtil.parseDate(Constants.RETURN_PATH_OVER_TIME);
            if (new Date().compareTo(returnPathOverTime) < 0) {
            	isShowReturnPath = "yes";
			}
            res.setIsShowReturnPath(isShowReturnPath);
            
            
            //投资列表和七天内产品到期的数目
            List<BsSubAccountVO> myInvest = subAccountService.findMyInvestByUserIdAndType(Constants.PRODUCT_TYPE_REG,req.getUserId(),0, Integer.MAX_VALUE,2);
            int less7Days = 0;
            if(myInvest != null && !myInvest.isEmpty()) {
            	for (BsSubAccountVO bsSubAccountVO : myInvest) {
            		if(bsSubAccountVO.getInvestDay() > 0 && bsSubAccountVO.getInvestDay() <= 7) {
            			less7Days ++;
            		}
            	}
            	ArrayList<HashMap<String, Object>> investList = BeanUtils.classToArrayList(myInvest);
            	res.setInvestList(investList);
            }
            res.setLess7Days(less7Days);
            //判断当前用户有多少个处理中的订单
            Integer processingNum = userTransDetailService.processingNumAll((Integer) userAsset.get("id"));
            //判断当前用户有多少个处理中的购买订单
            Integer processingBuyNum = userTransDetailService.processingNum((Integer) userAsset.get("id"));
            
            res.setProcessingNum(processingNum);
            res.setProcessingBuyNum(processingBuyNum);
            
            // 红包数量 
            Integer redPacketNum = redPacketService.getRedPacketNum(req.getUserId(), Constants.RED_PACKET_STATUS_INIT);
            //加息券数量
            Integer interestTicketNum = ticketInterestService.getInterestTicketNum(req.getUserId(), Constants.RED_PACKET_STATUS_INIT);
            res.setRedPacketNum(redPacketNum+interestTicketNum);
        } else {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
    }
    
    
    /**
     * 用户资产信息查询(存管改造修改）
     * @param req
     * @param res
     * 
     */
    @InterfaceVersion("AssetInfoQuery/1.0.1")
    public void assetInfoQuery_1(ReqMsg_User_AssetInfoQuery req, ResMsg_User_AssetInfoQuery res) {
        BsUserAssetVO bsUserAsset = userService.findUserAssetByUserId(req.getUserId());
        ResMsg_Invest_InvestProportion investRes = assetsService.investProportionList(req.getUserId());
        int investNum = (Integer)investRes.getInvestProportionList().get(0).get("investNum") + (Integer)investRes.getInvestProportionList().get(1).get("investNum");
        BsSubAccount bsSubAccountDepjsh = subAccountService.findDEPJSHSubAccountByUserId(req.getUserId());
        bsSubAccountDepjsh = bsSubAccountDepjsh == null ? new BsSubAccount() : bsSubAccountDepjsh;
        bsSubAccountDepjsh.setFreezeBalance(bsSubAccountDepjsh.getFreezeBalance() == null ? 0d : bsSubAccountDepjsh.getFreezeBalance());
        bsSubAccountDepjsh.setCanWithdraw(bsSubAccountDepjsh.getCanWithdraw() == null ? 0d : bsSubAccountDepjsh.getCanWithdraw());
        bsSubAccountDepjsh.setBalance(bsSubAccountDepjsh.getBalance() == null ? 0d : bsSubAccountDepjsh.getBalance());
        bsSubAccountDepjsh.setAvailableBalance(bsSubAccountDepjsh.getAvailableBalance() == null ? 0d : bsSubAccountDepjsh.getAvailableBalance());
        bsSubAccountDepjsh.setOpenBalance(bsSubAccountDepjsh.getOpenBalance() == null ? 0d : bsSubAccountDepjsh.getOpenBalance());
//        Double amountTrans = userService.queryAmountTrans(req.getUserId());
//        amountTrans = amountTrans == null ? 0 : amountTrans;
        HashMap<String, Object> userAsset = BeanUtils.transBeanMap(bsUserAsset);
        //总资产=总余额+当前投资收益=投资本金+结算户可用余额（推荐奖励金）+当前投资收益
        if (userAsset != null && userAsset.size() > 0) {
            res.setAccountId((Integer) userAsset.get("accountId"));
            res.setAssetAmount(MoneyUtil.add((Double) userAsset.get("totalBalance")
                               , (Double) userAsset.get("currentInterest")).doubleValue() );
            res.setBonus((Double) userAsset.get("currentBonus"));
            
            res.setEmail((String) userAsset.get("email"));
            res.setIdCard((String) userAsset.get("idCard"));
            res.setInvestEarnings((Double) userAsset.get("currentInterest"));//投资收益
            res.setIsBindBank((Integer) userAsset.get("isBindBank"));
            res.setIsBindName((Integer) userAsset.get("isBindName"));
            res.setMobile((String) userAsset.get("mobile"));
            res.setNick((String) userAsset.get("nick"));
            BsSubAccount jshAccount = subAccountService.findJSHSubAccountByUserId(req.getUserId());//用户子账户结算户
            BsSubAccount jljAccount = subAccountService.findJLJSubAccountByUserId(req.getUserId());//用户子账户奖励金户
            Double regularAmount = CalculatorUtil.calculate("a-a-a-a", userAsset.get("totalBalance")==null?0.0:(Double)userAsset.get("totalBalance"), bsSubAccountDepjsh.getBalance(), jshAccount.getBalance(), jljAccount.getBalance());
            res.setRegularAmount(regularAmount);//当前投资本金=总余额-结算户余额-奖励金余额
            res.setBalance((Double) jshAccount.getBalance());//结算户余额
            res.setCanWithdraw((Double)jshAccount.getCanWithdraw() );//JSH可提现金额
            res.setAvailableBalance((Double)jshAccount.getAvailableBalance());//JSH可用余额
            res.setFreezeBalance((Double)jshAccount.getFreezeBalance());//JSH冻结金额
            res.setJljBalance((Double)jljAccount.getBalance()); //JLJ余额
            res.setJljCanWithdraw((Double)jljAccount.getCanWithdraw()); //JLJ可提现余额
            res.setStatus((Integer) userAsset.get("status"));
            res.setTotalBonus((Double) userAsset.get("totalBonus"));//累计推荐奖励
            res.setTotalInvestEarnings((Double) userAsset.get("totalInterest"));//累计投资收益
            res.setTotalTransNum((Integer) userAsset.get("totalTrans"));
            res.setUserId((Integer) userAsset.get("id"));
            res.setUserName((String) userAsset.get("userName"));
            res.setDafyStatus((Integer) userAsset.get("dafyStatus"));
//            res.setInvestNum((Integer) userAsset.get("investNum"));
            res.setInvestNum(investNum);
            res.setBankCardCount((Integer) userAsset.get("bankcardCount")); //银行卡张数
            res.setDepFreezeBalance(bsSubAccountDepjsh == null ? 0d : bsSubAccountDepjsh.getFreezeBalance());
            List<BsDailyInterest> dayInterestList = investService.findDailyInterestByUserAndDay(
                req.getUserId(), DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date())));
            
        	Double todayInterest = 0d;
        	if(dayInterestList != null) {
            	for (BsDailyInterest bsDailyInterest : dayInterestList) {
            		bsDailyInterest.setInterest(bsDailyInterest.getInterest() == null ? 0d : bsDailyInterest.getInterest());
                    todayInterest = MoneyUtil.add(todayInterest, bsDailyInterest.getInterest()).doubleValue();
				}
        	}
            
            res.setDayInvestEarnings(todayInterest);
            
            //判断是否显示回款路径菜单
            String isShowReturnPath = "no";
            Date registerTime = bsUserAsset.getRegisterTime();
            Date returnPathOverTime = DateUtil.parseDate(Constants.RETURN_PATH_OVER_TIME);
            if (new Date().compareTo(returnPathOverTime) < 0) {
            	isShowReturnPath = "yes";
			}
            res.setIsShowReturnPath(isShowReturnPath);
            
            
            //投资列表和七天内产品到期的数目
            List<BsSubAccountVO> myInvest = subAccountService.findMyInvestByUserIdAndType(Constants.PRODUCT_TYPE_REG,req.getUserId(),0, Integer.MAX_VALUE,2);
            int less7Days = 0;
            if(myInvest != null && !myInvest.isEmpty()) {
            	for (BsSubAccountVO bsSubAccountVO : myInvest) {
            		if(bsSubAccountVO.getInvestDay() > 0 && bsSubAccountVO.getInvestDay() <= 7) {
            			less7Days ++;
            		}
            	}
            	ArrayList<HashMap<String, Object>> investList = BeanUtils.classToArrayList(myInvest);
            	res.setInvestList(investList);
            }
            res.setLess7Days(less7Days);
            //判断当前用户有多少个处理中的订单
            Integer processingNum = userTransDetailService.processingNumAll((Integer) userAsset.get("id"));
            //判断当前用户有多少个处理中的购买订单
            Integer processingBuyNum = userTransDetailService.processingNum((Integer) userAsset.get("id"));
            
            res.setProcessingNum(processingNum);
            res.setProcessingBuyNum(processingBuyNum);
            
            // 红包数量 
            Integer redPacketNum = redPacketService.getRedPacketNum(req.getUserId(), Constants.RED_PACKET_STATUS_INIT);
            //加息券数量
            Integer interestTicketNum = ticketInterestService.getInterestTicketNum(req.getUserId(), Constants.RED_PACKET_STATUS_INIT);
            res.setRedPacketNum(redPacketNum+interestTicketNum);
            
            
            DepGuideVO depGuideVO = hfBankDepSiteService.findDepGuideInfo(req.getUserId());
            
            //用户当前账户状态(OLD 只有存管前账户   、DEP  只有存管户 、 DOUBLE  双账户并存)
            if (Constants.HFBANK_GUIDE_ACCOUNT_TYPE_DEP.endsWith(depGuideVO.getAccountType())) {
            	res.setDepAccountStatus("DEP");
                res.setDepAvailableBalance(bsSubAccountDepjsh.getAvailableBalance());
                res.setDepBalance(bsSubAccountDepjsh.getBalance());
                res.setDepCanWithdraw(bsSubAccountDepjsh.getCanWithdraw());
                res.setDepFreezeBalance(bsSubAccountDepjsh.getFreezeBalance());
            }else if (Constants.HFBANK_GUIDE_ACCOUNT_TYPE_DOUBLE.endsWith(depGuideVO.getAccountType())) {
				res.setDepAccountStatus("DOUBLE");
                res.setDepAvailableBalance(bsSubAccountDepjsh.getAvailableBalance());
				  res.setDepBalance(bsSubAccountDepjsh.getBalance());
                res.setDepCanWithdraw(bsSubAccountDepjsh.getCanWithdraw());
                res.setDepFreezeBalance(bsSubAccountDepjsh.getFreezeBalance());
			}else {
				res.setDepAccountStatus("OLD");
			}
            

            //页面加载提示类型（OPEN 开通、ACTIVATE 激活、NO 无提示）
            if (Constants.HFBANK_GUIDE_NO_BIND_CARD.endsWith(depGuideVO.getIsOpened()) ||
            		Constants.HFBANK_GUIDE_FAILED_BIND_HF.endsWith(depGuideVO.getIsOpened())) {
				res.setNoticeType("OPEN");
			}else if (Constants.HFBANK_GUIDE_WAIT_ACTIVATE.endsWith(depGuideVO.getIsOpened())) {
				res.setNoticeType("ACTIVATE");
			}else {
				res.setNoticeType("NO");
			}
           
            // 风险测评
            res.setRiskStatus(assetsService.riskStatus(req.getUserId()));
            
        } else {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
    }

    /**
     * 用户反馈 保存
     * @param req
     * @param resp
     */
    @InterfaceVersion("Feedback/1.0.0")
    public void feedback(ReqMsg_User_Feedback req, ResMsg_User_Feedback resp) {

        BsFeedback bsFeedback = new BsFeedback();
        bsFeedback.setUserId(req.getUserId());
        bsFeedback.setInfo(req.getInfo());
        bsFeedback.setCreateTime(new Date());
        userService.addUserFeedback(bsFeedback);

    }

    @InterfaceVersion("BsSubUserListQuery/1.0.0")
    public void bsSubUserListQuery(ReqMsg_User_BsSubUserListQuery req,
                                   ResMsg_User_BsSubUserListQuery resp) {
        List<BsUser> bsUserList = userService.findSubUserByUserId(req.getUserId(),
            req.getPageIndex(), req.getPageSize());
        if (bsUserList != null && bsUserList.size() > 0) {
            resp.setBsUserList(BeanUtils.classToArrayList(bsUserList));
        }
        int pageSize = req.getPageSize();
        resp.setTotalCount((int) Math.ceil((double) userService.countSubAccountByUserId(req
            .getUserId()) / pageSize));
        resp.setPageIndex(req.getPageIndex());

    }

    /**
     * 设置交易密码（没有设置过）
     * 
     * @param req
     * @param resp
     */
    @InterfaceVersion("SetUpTraderPassword/1.0.0")
    public void setUpTraderPassword(ReqMsg_User_SetUpTraderPassword req,
                                    ResMsg_User_SetUpTraderPassword resp) {
        BsUser bsUser = userService.findUserById(req.getUserId());
        if (bsUser == null) {
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }
        String payPassword = MD5Util.encryptMD5(req.getPayPassword() + MD5Util.getEncryptkey());
        // 支付密码不能和登录密码一样
        if (StringUtils.isNotBlank(bsUser.getPassword())) {
            if (bsUser.getPassword().equals(payPassword)) {
                throw new PTMessageException(PTMessageEnum.PASS_LOGIN_PAY_SAME_ERROR);
            }
        }
        bsUser.setPayPassword(payPassword);
        userService.updateBsUser(bsUser);
    }
    
    /**
     * 校验交易密码是否相同
     * 
     * @param req
     * @param resp
     */
    @InterfaceVersion("CheckPayPassword/1.0.0")
    public void checkPayPassword(ReqMsg_User_CheckPayPassword req, ResMsg_User_CheckPayPassword resp) {
        BsUser bsUser = userService.findUserById(req.getUserId());
        int time = 180 - DateUtil.getDiffeMinute(new Date(), bsUser.getPayFailTime());
        if(time > 0) { // 180分钟之内
            if(bsUser.getPayFailTimes() != null && bsUser.getPayFailTimes() >= 5) { // 失败次数超过5次，提示找回密码
                resp.setToastMsg("交易密码输入错误次数过多，请" + time + "分钟后再试，或尝试找回密码");
                resp.setFailNums(bsUser.getPayFailTimes()+1);
                resp.setTruePayPassword(false);
                userService.updateBsUser(bsUser);
                throw new PTMessageException(PTMessageEnum.USER_PAY_FAIL_OVER_MUCH);
            }
        } else {    // 180分钟之后，清零
            bsUser.setPayFailTimes(0);
            userService.updateBsUser(bsUser);
        }
        // 失败低于等于5次
        String payPassword = MD5Util.encryptMD5(req.getPayPassword() + MD5Util.getEncryptkey());
        if(payPassword.equals(bsUser.getPayPassword())){
            resp.setTruePayPassword(true);
            resp.setFailNums(bsUser.getPayFailTimes());
            // 失败次数清零
            bsUser.setPayFailTimes(0);
            userService.updateBsUser(bsUser);
        } else {
            resp.setTruePayPassword(false);
            resp.setFailNums(bsUser.getPayFailTimes()==null?1:bsUser.getPayFailTimes()+1);
            // 更新失败次数
            bsUser.setPayFailTimes(bsUser.getPayFailTimes()==null?1:bsUser.getPayFailTimes()+1);
            bsUser.setPayFailTime(new Date());
            userService.updateBsUser(bsUser);
        }
    }

    /**
     * 验证验证码是否正确   
     * @param req ReqMsg_User_ValidUser
     * @param res ResMsg_User_ValidUser
     */
    @InterfaceVersion("ValidUser/1.0.0")
    public void validUser(ReqMsg_User_ValidUser req, ResMsg_User_ValidUser res) {
    	boolean isFreezeUser = userService.isFreezeUserByMobile(req.getMobile());
    	if (isFreezeUser) {
    		throw new PTMessageException(PTMessageEnum.USER_STATUS_FROST_ERROR);
    	}
        //验证验证码
        boolean isValidUser = userService.isValidMobile(req.getMobile());
        if (!isValidUser) {
            throw new PTMessageException(PTMessageEnum.MOBILE_NO_EXIT);
        }
        if (!smsService.validateIdentity(req.getMobile(), req.getMobileCode())) {
            throw new PTMessageException(PTMessageEnum.MOBILE_CODE_WRONG_ERROR);
        }
        res.setValidUser(true);
    }
    
    
    /**
     * 检查用户当前还有多少正在处理中的购买订单
     * @param req ReqMsg_User_ProcessingOrder
     * @param res ResMsg_User_processingOrder
     */
    @InterfaceVersion("ProcessingOrder/1.0.0")
    public void processingOrder(ReqMsg_User_ProcessingOrder req, ResMsg_User_ProcessingOrder res) {
    	 //判断当前用户有多少个处理中的订单
        Integer processingNum = userTransDetailService.processingNum(req.getUserId());
        res.setProcessingNum(processingNum);
    }
    
    /**
     * 
     * @Title: messageList 
     * @Description: 查询用户消息列表
     * @param req
     * @param res
     * @throws
     */
    @InterfaceVersion("MessageList/1.0.0")
    public void messageList(ReqMsg_User_MessageList req, ResMsg_User_MessageList res) {
    	List<BsAppMessage> list = userService.findMessageList(req.getUserId(), req.getPage());
    	Integer totalPage = userService.findMessageListCount(req.getUserId());
    	List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
    	for(BsAppMessage m : list) {
    		Map<String,Object> map = new HashMap<String,Object>();
    		map.put("id", m.getId());
    		if(m.getPushTime() != null) {
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			map.put("pushTime", sdf.format(m.getPushTime()));
    		}
    		map.put("title", m.getTitle());
    		map.put("pushType", m.getPushType());
    		//推送类型为url
    		if(m.getPushType() == 1) {
    			map.put("url", m.getContent());
    		}
    		//推送类型为app内页
    		if(m.getPushType() == 3) {
    			map.put("appPage", m.getAppPage());
    		}
    		map.put("pushAbstract", m.getPushAbstract());
    		dataList.add(map);
    	}
    	res.setTotalPage(totalPage);
    	res.setDataLst(dataList);
    }
    
    /**
     * 
     * @Title: messageInfo 
     * @Description: 查询消息详情
     * @param req
     * @param res
     * @throws
     */
    @InterfaceVersion("MessageInfo/1.0.0")
    public void messageInfo(ReqMsg_User_MessageInfo req, ResMsg_User_MessageInfo res) {
    	BsAppMessage message = userService.findMessageById(req.getId());
    	if(message.getPushTime() != null) {
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		res.setPushTime(sdf.format(message.getPushTime()));
    	}
    	res.setTitle(message.getTitle());
    	res.setContent(message.getContent());
    }
    
    
    /**
     * 
     * @Title: appVersion 
     * @Description: 更新用户APP版本信息
     * @param req
     * @param res
     * @throws
     */
    @InterfaceVersion("AppVersion/1.0.0")
    public void appVersion(ReqMsg_User_AppVersion req, ResMsg_User_AppVersion res) {
    	userService.updataAppVersionByUserId(req.getUserId(),req.getAppVersion());
    }
    
    
    /**
     * 
     * @Title: activatePageInfo 
     * @Description: 我的资产_获取激活页面数据_存管改造
     * @param req
     * @param res
     * @throws
     */
    @InterfaceVersion("ActivatePageInfo/1.0.0")
    public void activatePageInfo(ReqMsg_User_ActivatePageInfo req, ResMsg_User_ActivatePageInfo res) {
        WaiteActivateInfoVO result = hfBankDepSiteService.waiteActivateInfo(req.getUserId());
    	BsUserActivateVO vo = userService.activatePageInfo(req.getUserId());
    	res.setMobile(result.getMobile());
    	res.setUserName(result.getUserName());
    	res.setIdCard(result.getIdCard());
    	res.setBankCard(result.getCardNo());
    	res.setBankId(vo.getBankId());
		res.setBankName(result.getBankName());
		res.setSmallLogo(vo.getSmallLogo());
		res.setLargeLogo(vo.getLargeLogo());
		res.setDepAccount(vo.getDepAccount());
    }
    
    /**
     * 
     * @Title: activatePageInfo 
     * @Description: 我的资产_激活存管账户
     * @param req
     * @param res
     * @throws
     */
    @InterfaceVersion("ActivateDepAccount/1.0.0")
    public void activateDepAccount(ReqMsg_User_ActivateDepAccount req, ResMsg_User_ActivateDepAccount res) {
    	userService.activateDepAccount(req.getUserId(),req.getMobileCode());
    }
    
    /**
     * 风险测评信息
     * @param req
     * @param resp
     */
    @InterfaceVersion("Questionnaire/1.0.0")
    public void questionnaire(ReqMsg_User_Questionnaire req, ResMsg_User_Questionnaire resp) {
    	BsQuestionnaireVo questionnaireVo = questionnaireService.findQuestionnaireByUserId(req.getUserId());
    	if (questionnaireVo != null) {
    		resp.setHasQuestionnaire(questionnaireVo.getHasQuestionnaire());
    		resp.setIsExpired(questionnaireVo.getIsExpired());
    		resp.setAssessType(questionnaireVo.getAssessType());
    	} else {
            resp.setHasQuestionnaire(Constants.HAS_NOT_QUESTIONNAIRE);
        }
    }
    
    /**
     * 风险测评信息再次打开
     * @param req
     * @param resp
     */
    @InterfaceVersion("QuestionnaireMoreQuery/1.0.0")
    public void questionnaireMoreQuery(ReqMsg_User_QuestionnaireMoreQuery req, ResMsg_User_QuestionnaireMoreQuery resp) {
    	BsQuestionnaireVo questionnaireVo = questionnaireService.findQuestionnaireByUserId(req.getUserId());
    	if (questionnaireVo != null) {
    		resp.setHasQuestionnaire(questionnaireVo.getHasQuestionnaire());
    		resp.setAssessType(questionnaireVo.getAssessType());
    		resp.setExpireTime(DateUtil.formatYYYYMMDD(questionnaireVo.getExpireTime()));
    	} else {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
    }
    
    /**
     * 进行风险测评
     * @param req
     * @param resp
     */
    @InterfaceVersion("SaveQuestionnaire/1.0.0")
    public void saveQuestionnaire(ReqMsg_User_SaveQuestionnaire req, ResMsg_User_SaveQuestionnaire resp) {
    	String assessResult = questionnaireService.saveQuestionnaire(req);
    	if (StringUtil.isNotBlank(assessResult)) {
    		resp.setAssessType(assessResult);
    	} else {
    		throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
    	}
    }
    
    @InterfaceVersion("RecommendAmount/1.0.0")
    public void recommendAmount(ReqMsg_User_RecommendAmount req, ResMsg_User_RecommendAmount resp) {
    	Double bonusAmount = bonusGrantPlanService.sumBonusAmount(req.getUserId());
    	resp.setRecommendAmountTotal(bonusAmount);
    }


    /**
     * 用户最大并发会话数控制
     * @param req
     * @param res
     */
    @InterfaceVersion("UserSessionConnection/1.0.0")
    public void userSessionConnection(ReqMsg_User_UserSessionConnection req, ResMsg_User_UserSessionConnection res) {
        BsUserSessionConnectionInfo info = new BsUserSessionConnectionInfo();
        info.setSessionId(req.getSessionId());
        info.setUserId(req.getUserId());
        info.setDeviceToken(req.getDeviceToken());
        info.setIp(req.getIp());
        info.setTerminal(req.getTerminal());
        userService.userSessionConnection(info, req.getLogout());
    }
    
    /**
     * 记录用户已安装应用列表
     * @param req
     * @param res
     */
    @InterfaceVersion("AddApplication/1.0.0")
    public void addApplication(ReqMsg_User_AddApplication req, ResMsg_User_AddApplication res) {
        BsUserAddApplication info = new BsUserAddApplication();
        info.setUserId(req.getUserId());
        info.setApplications(req.getApplications());
        userService.addApplications(info);
    }
    
    /**
     * app记录用户ip对应的位置信息
     * @param req
     * @param res
     */
    @InterfaceVersion("AppAddUserAddress/1.0.0")
    public void appAddUserAddress(ReqMsg_User_AppAddUserAddress req, ResMsg_User_AppAddUserAddress res) {
    	BsUserAddAddress info = new BsUserAddAddress();
    	info.setUserId(req.getUserId());
    	info.setAddress(req.getAddress());
        userService.userAddAddress(info);
    }
}
