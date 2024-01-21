package com.pinting.business.facade.site;

import com.pinting.business.dao.BsBankCardMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BankCardVO;
import com.pinting.business.model.vo.BsQuestionnaireVo;
import com.pinting.business.model.vo.BsSubAccountVO;
import com.pinting.business.model.vo.BsUserAssetVO;
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
import com.pinting.gateway.hessian.message.xicai.B2GReqMsg_Xicai_BindUserCallBack;
import com.pinting.gateway.hessian.message.xicai.B2GReqMsg_Xicai_PushP2P;
import com.pinting.gateway.hessian.message.xicai.B2GResMsg_Xicai_PushP2P;
import com.pinting.gateway.in.service.DafyUserService;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.out.service.XicaiTransportService;
import com.pinting.gateway.smsEnums.TemplateKey;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("User")
/**
 * 注册用户管理
 * @Project: business
 * @Title: UserFacade.java
 * @author Linkin
 * @date 2015-2-9 上午10:39:39
 * @Copyright: 2015 bigangwan.com Inc. All rights reserved.
 */
public class UserFacade {
	
	private static Logger logger = LoggerFactory.getLogger(UserFacade.class);
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
    private InvestService     investService;
    @Autowired
    private UserTransDetailService    userTransDetailService;
    @Autowired
    private BsSalesService    bsSalesService;
    @Autowired
    private RedPacketService redPacketService;
    @Autowired
    private BsSysConfigService bsSysConfigService;
	@Autowired
	private SMSServiceClient smsServiceClient;
	@Autowired
	private  XicaiTransportService xicaiTransportService;
	@Autowired
	private BsAgentViewConfigService bsAgentViewConfigService;
	@Autowired
	private AssetsService assetsService;
	@Autowired
	private QuestionnaireService questionnaireService;
    @Autowired
    private BonusGrantPlanService bonusGrantPlanService;
    @Autowired
    private UserTrustLoginService userTrustLoginService;
    @Autowired
    private UcUserService ucUserService;
    @Autowired
    private BsBankCardMapper bsBankCardMapper;
    @Autowired
    private AgreementVersionService agreementVersionService;
    @Autowired
    private TicketInterestService ticketInterestService;
    
    public void login(ReqMsg_User_Login req, ResMsg_User_Login res) {
        boolean isValidUser = userService.isValidMobileOrNick(req.getNick());
        if (!isValidUser) {
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }
        BsUser iUser = userService.findUserByNick(req.getNick());
        if (iUser == null) {
            iUser = userService.findUserByMobile(req.getNick());
        }
        // 设置cookie失效时间
        BsSysConfig sysCookie = bsSysConfigService.findKey(Constants.USER_LOGIN_COOKIE_MAX_AGE);
        Integer cookieMaxAge = sysCookie != null ? Integer.valueOf(sysCookie.getConfValue()) : Constants.COOKIE_MAX_AGE_FIVE_DAYS;
        res.setCookieMaxAge(cookieMaxAge);
        logger.info("login method cookieMaxAge：{}", res.getCookieMaxAge());
        
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
        
        res.setRealAgentId(iUser.getAgentId());
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
                if(null != iUser.getAgentId()) {
                	if(bsAgentViewConfigService.isQianbao(iUser.getAgentId())){
                		res.setAgentId("qianbao");
                	}else{
                		res.setAgentId("");
                	}
                	
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
                        if(null != iUser.getAgentId()) {
                        	if(bsAgentViewConfigService.isQianbao(iUser.getAgentId())){
                        		res.setAgentId("qianbao");
                        	}else{
                        		res.setAgentId("");
                        	}
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
                    if(null != iUser.getAgentId()) {
                    	if(bsAgentViewConfigService.isQianbao(iUser.getAgentId())){
                    		res.setAgentId("qianbao");
                    	}else{
                    		res.setAgentId("");
                    	}
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
            if(StringUtils.isNotBlank(req.getInviteCode())) {
                BsSales sales = new BsSales();
                sales.setStatus(1);
                if(Constants.SEVEN_DIAN_DEFAULT_SALES_ID_KEY.equals(req.getInviteCode())) {
                    // 七店入口注册，但未填写七店店主邀请码
                    BsSysConfig config = bsSysConfigService.findKey(Constants.SEVEN_DIAN_DEFAULT_SALES_ID_KEY);
                    sales.setInviteCode(config.getConfValue());
                    req.setInviteCode(config.getConfValue());
                } else {
                    sales.setInviteCode(req.getInviteCode());
                }
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
      

        //校验渠道码是否存在 并返回渠道信息。
        if (req.getAgentId() != null && !"".equals(req.getAgentId())) {
            BsAgent agent = userService.findAgentByCode(req.getAgentId());
            if (agent == null) {
                throw new PTMessageException(PTMessageEnum.USER_AGENT_ERROR);
            }
            user.setAgentId(agent.getId());
            
            // 19-泓淳递推，29-铁道部,28-运营活动砸金蛋,30-希财渠道,31-销售人员推荐, 32-SHAKE_AGENT_ID-318摇一摇渠道ID 
            // 33-LANDING_PAGE_AGENT_ID 318广告落地页渠道
            if(req.getAgentId() != 19 && req.getAgentId() != 29 && req.getAgentId() != 28 
                    && req.getAgentId() != 31 && req.getAgentId() != Constants.SHAKE_AGENT_ID
                    && req.getAgentId() != Constants.LANDING_PAGE_AGENT_ID && req.getAgentId() != Constants.XICAI_AGENT_ID
                    && req.getAgentId() != Constants.MANAGER_AGENT_ID){
            	user.setUserType(Constants.USER_TYPE_PROMOT);
            } else {
            	user.setUserType(Constants.USER_TYPE_NORMAL);
            }
        }
        
        // 是否钱报入口
        if("qianbao".equals(req.getQianbao())) {
        	
        	if(StringUtils.isNotBlank(req.getViewAgentId())){
        		Integer agentId = Integer.parseInt(req.getViewAgentId());
        		//根据agentid判断该agentid是否存在
        		BsAgent agent = userService.findAgentById(agentId);
        		 if(null == agent) {
                     throw new PTMessageException(PTMessageEnum.AGENT_ERROR);
                 }
        		 //判断该agentid是否在AgentViewConfig表中存在
        		 if(bsAgentViewConfigService.isQianbao(agentId)){
        			 user.setAgentId(agentId);    // 柯桥id
                     res.setQianbaoFlag(agentId);
             	}
        		 
        	}
           
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
        boolean IsValidate = smsService
            .validateIdentity(req.getMobile(), req.getMobileCode(), true);
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
        if(StringUtils.isNotBlank(req.getRecommendId())){
        	user.setRecommendId(Integer.parseInt(req.getRecommendId()));	
        }
        user.setRegisterTime(new Date());
        user.setReturnPath(Constants.RETURN_PATH_BALANCE);
        // 获取注册用户来源（H5、PC）
        user.setRegTerminal(req.getRegTerminal());
        // 废弃PROMOT类型，统一更改为NORMAL类型
        user.setUserType(Constants.USER_TYPE_NORMAL);
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
        if (StringUtil.isNotBlank(bsUser.getPayPassword()) && bsUser.getPayPassword().equals(payPassword)) {
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
    public void infoValidation(ReqMsg_User_InfoValidation req, ResMsg_User_InfoValidation res) {
    	
        //手机验证是否存在
        if (StringUtil.isNotEmpty(StringUtil.trim(req.getMobile()))) {
            boolean isValidUser = userService.isValidMobile(req.getMobile());
            
            boolean isValidUcUser = ucUserService.isValidMobile(req.getMobile());
            //uc用户中心
            if(isValidUcUser) {
            	throw new PTMessageException(PTMessageEnum.MOBILE_IS_EXIT);
            }
            //bs理财用户中心
            if (isValidUser) {
                throw new PTMessageException(PTMessageEnum.MOBILE_IS_EXIT);
            } else {
                throw new PTMessageException(PTMessageEnum.MOBILE_NO_EXIT);
            }
        }
        if (req.getNick() != null) {
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

    public void logout(ReqMsg_User_Login req, ResMsg_User_Login res) {
    }

    public void infoQuery(ReqMsg_User_InfoQuery req, ResMsg_User_InfoQuery resp) {
        BsUser bsUser = userService.findUserById(req.getUserId());

        if (bsUser == null) {
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }
        

        //判断是否显示回款路径菜单
        String isShowReturnPath = "no";
        Date registerTime = bsUser.getRegisterTime();
        Date returnPathOverTime = DateUtil.parseDate(Constants.RETURN_PATH_OVER_TIME);
        if (new Date().compareTo(returnPathOverTime) < 0) {
        	isShowReturnPath = "yes";
		}
        resp.setIsShowReturnPath(isShowReturnPath);
        
        
        String h5ReturnPathHide = "no";
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(returnPathOverTime);
        calendar.add(calendar.DATE,15);
        Date h5ReturnPathHideDate =calendar.getTime();
        if (new Date().compareTo(h5ReturnPathHideDate) < 0) {
        	h5ReturnPathHide = "yes";
		}
        resp.setH5ReturnPathHide(h5ReturnPathHide);
        

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
     * 取用户绑卡信息
     * @param req
     */
    public void bankCardInfo(ReqMsg_User_BankCardInfo req, ResMsg_User_BankCardInfo resp) {
    	BsBankCardExample bsBankCardExample = new BsBankCardExample();
    	bsBankCardExample.createCriteria().andUserIdEqualTo(req.getUserId()).andStatusEqualTo(Constants.BANK_CARD_NORMAL);
    	List<BsBankCard> list = bsBankCardMapper.selectByExample(bsBankCardExample);
    	if (CollectionUtils.isNotEmpty(list)) {
    		BsBankCard bsBankCard = list.get(0);
			resp.setUserName(bsBankCard.getCardOwner());
			resp.setIdCard(bsBankCard.getIdCard());
    	} else {    		
    		BsBankCardExample bankCardExample = new BsBankCardExample();
    		bankCardExample.createCriteria().andUserIdEqualTo(req.getUserId()).andStatusEqualTo(Constants.BANK_CARD_UNBIND);
    		bankCardExample.setOrderByClause("id desc");
    		List<BsBankCard> bankCardList = bsBankCardMapper.selectByExample(bankCardExample);
    		if (CollectionUtils.isNotEmpty(bankCardList)) {
    			BsBankCard bsBankCard = bankCardList.get(0);
    			resp.setUserName(bsBankCard.getCardOwner());
    			resp.setIdCard(bsBankCard.getIdCard());
    		} 
    	}
    }
    
    /**
     * 用户资产信息查询
     * @param req
     * @param res
     * @ 
     */
    public void assetInfoQuery(ReqMsg_User_AssetInfoQuery req, ResMsg_User_AssetInfoQuery res) {
        BsUserAssetVO bsUserAsset = userService.findUserAssetByUserId(req.getUserId());
        int investNum = userService.countInTheInvestmentByUserId(req.getUserId());
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

        if (userAsset != null && userAsset.size() > 0) {
            res.setAccountId((Integer) userAsset.get("accountId"));
            //总资产=总余额+当前投资收益=投资本金+结算户可用余额（推荐奖励金）+当前投资收益
            res.setAssetAmount(MoneyUtil.add((Double) userAsset.get("totalBalance")
                               , (Double) userAsset.get("currentInterest")).doubleValue());

            res.setBonus((Double) userAsset.get("currentBonus"));
            res.setEmail((String) userAsset.get("email"));
            res.setIdCard((String) userAsset.get("idCard"));
            res.setInvestEarnings((Double) userAsset.get("currentInterest"));//投资收益	可以用于展示账户概览总资产明细（投资收益）
            res.setIsBindBank((Integer) userAsset.get("isBindBank"));
            res.setIsBindName((Integer) userAsset.get("isBindName"));
            res.setMobile((String) userAsset.get("mobile"));
            res.setNick((String) userAsset.get("nick"));
            BsSubAccount jshAccount = subAccountService.findJSHSubAccountByUserId(req.getUserId());//用户子账户结算户
            BsSubAccount jljAccount = subAccountService.findJLJSubAccountByUserId(req.getUserId());//用户子账户奖励金户 
            Double regularAmount = CalculatorUtil.calculate("a-a-a-a", userAsset.get("totalBalance")==null?0.0:(Double)userAsset.get("totalBalance"), bsSubAccountDepjsh.getBalance(), jshAccount.getBalance(), jljAccount.getBalance());
            res.setRegularAmount(regularAmount);//当前投资本金=总余额-结算户余额-奖励金余额		可以用于展示账户概览总资产明细（投资本金）
//            res.setRegularAmount(userAsset.get("totalBalance")==null?0.0:((Double)userAsset.get("totalBalance") - bsSubAccountDepjsh.getBalance() - jshAccount.getBalance() - jljAccount.getBalance()));//当前投资本金=总余额-结算户余额-奖励金余额
            res.setBalance((Double) jshAccount.getBalance());//结算户余额	可以用于展示账户概览总资产明细（普通账户余额）
            res.setCanWithdraw((Double)jshAccount.getCanWithdraw() );//JSH可提现金额
            res.setAvailableBalance((Double)jshAccount.getAvailableBalance());//JSH可用余额
            res.setFreezeBalance((Double)jshAccount.getFreezeBalance());//JSH冻结金额
            res.setJljBalance((Double)jljAccount.getBalance()); //JLJ余额		可以用于展示账户概览总资产明细（我的奖励）
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
            res.setHavePayPsd(StringUtil.isNotBlank(bsUserAsset.getPayPassword()) ? Constants.YES : Constants.NO);
            // 当日收益
            Double todayInterest = 0d;
            List<BsDailyInterest> dayInterestList = investService.findDailyInterestByUserAndDay(
                req.getUserId(), DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date())));
            if(dayInterestList != null) {
            	for (BsDailyInterest bsDailyInterest : dayInterestList) {
            		bsDailyInterest.setInterest(bsDailyInterest.getInterest() == null ? 0d : bsDailyInterest.getInterest());
                    todayInterest = MoneyUtil.add(todayInterest, bsDailyInterest.getInterest()).doubleValue();
				}
            }
//            // 委托计划当日回款记录利息总和
//            Double todayTotalInterest = lnFinanceRepayScheduleMapper.sumTodayInterestByUserId(req.getUserId());
//            if(null != todayTotalInterest) {
//                todayInterest = MoneyUtil.add(todayInterest, todayTotalInterest).doubleValue();
//            }
            res.setDayInvestEarnings(todayInterest);

            
            
            //判断是否显示回款路径菜单
/*            String isShowReturnPath = "no";
            Date registerTime = bsUserAsset.getRegisterTime();
            Date returnPathStartTime = DateUtil.parseDate(Constants.RETURN_PATH_START_TIME);
            Date returnPathOverTime = DateUtil.parseDate(Constants.RETURN_PATH_OVER_TIME);
            if (registerTime.compareTo(returnPathStartTime) < 0  && new Date().compareTo(returnPathOverTime) < 0) {
            	isShowReturnPath = "yes";
			}
            res.setIsShowReturnPath(isShowReturnPath);*/
            String isShowReturnPath = "no";
            Date returnPathOverTime = DateUtil.parseDate(Constants.RETURN_PATH_OVER_TIME);
            if (new Date().compareTo(returnPathOverTime) < 0) {
            	isShowReturnPath = "yes";
			}
            res.setIsShowReturnPath(isShowReturnPath);
            
            
            String h5ReturnPathHide = "no";
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(returnPathOverTime);
            calendar.add(calendar.DATE,15);
            Date h5ReturnPathHideDate =calendar.getTime();
            if (new Date().compareTo(h5ReturnPathHideDate) < 0) {
            	h5ReturnPathHide = "yes";
			}
            res.setH5ReturnPathHide(h5ReturnPathHide);
            
            //投资列表和七天内产品到期的数目
//            List<BsSubAccountVO> myInvest = subAccountService.selectMyInvestment(req.getUserId(),0, Integer.MAX_VALUE,2);
            List<BsSubAccountVO> myInvest = subAccountService.findMyInvestByUserIdAndType(Constants.PRODUCT_TYPE_REG,req.getUserId(),0, Integer.MAX_VALUE,2);
            int less7Days = 0;
            if(myInvest != null && !myInvest.isEmpty()) {
            	for (BsSubAccountVO bsSubAccountVO : myInvest) {
            		if(bsSubAccountVO.getInvestDay() > 0 && bsSubAccountVO.getInvestDay() <= 7) {
            			less7Days ++;
            		}
            	}
            	ArrayList<HashMap<String, Object>> investList = BeanUtils.classToArrayList(myInvest);
            	for (HashMap<String, Object> hashMap : investList) {
        		    Date startTime = (Date) hashMap.get("startTime");
        		    Date loadMatchShowTime = null;
        		    
        		    loadMatchShowTime = DateUtil.parse(Constants.LOAD_MATCH_SHOW_TIME, "yyyy-MM-dd");
                    
        		    if(com.pinting.business.util.DateUtil.compareTo(startTime, loadMatchShowTime) < 0) {
        		        hashMap.put("loan", "no");
                    } else {
                        hashMap.put("loan", "yes");
                    }
                }
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

            res.setDepBalance(bsSubAccountDepjsh == null ? 0d : bsSubAccountDepjsh.getBalance());  //可以用于展示账户概览总资产明细（存管账户余额）
            res.setDepCanWithdraw(bsSubAccountDepjsh == null ? 0d : bsSubAccountDepjsh.getCanWithdraw());
            res.setDepAvailableBalance(bsSubAccountDepjsh == null ? 0d : bsSubAccountDepjsh.getAvailableBalance());
            res.setDepFreezeBalance(bsSubAccountDepjsh == null ? 0d : bsSubAccountDepjsh.getFreezeBalance());
        } else {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
    }

    /**
     * 风险测评
     * @param req
     * @param res
     */
    public void questionnaireQuery(ReqMsg_User_QuestionnaireQuery req, ResMsg_User_QuestionnaireQuery res) {
    	BsQuestionnaireVo questionnaireVo = questionnaireService.findQuestionnaireByUserId(req.getUserId());
    	if (questionnaireVo != null) {
    		res.setHasQuestionnaire(questionnaireVo.getHasQuestionnaire());
    		res.setIsExpired(questionnaireVo.getIsExpired());
    		res.setAssessType(questionnaireVo.getAssessType());
    	} else {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
    }
    
    /**
     *  风险测评结果
     *  @param req
     *  @param res
     */
    public void questionnaire(ReqMsg_User_Questionnaire req,
    		ResMsg_User_Questionnaire res) {    	
    	String assessResult = questionnaireService.doQuestionnaire(req);
    	if (StringUtil.isNotBlank(assessResult)) {
    		res.setAssessType(assessResult);
    	} else {
    		throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
    	}
    }
    
    /**
     *  风险测评再次
     *  @param req
     *  @param res
     */
    public void questionnaireMoreQuery(ReqMsg_User_QuestionnaireMoreQuery req,
    		ResMsg_User_QuestionnaireMoreQuery res) {
    	BsQuestionnaireVo questionnaireVo = questionnaireService.findQuestionnaireByUserId(req.getUserId());
    	if (questionnaireVo != null) {
    		res.setHasQuestionnaire(questionnaireVo.getHasQuestionnaire());
    		res.setExpireTime(DateUtil.formatYYYYMMDD(questionnaireVo.getExpireTime()));
    		res.setAssessType(questionnaireVo.getAssessType());
    	} else {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
    }
    
    /**
     * 用户反馈 保存
     * @param req
     * @param resp
     */
    public void feedback(ReqMsg_User_Feedback req, ResMsg_User_Feedback resp) {

        BsFeedback bsFeedback = new BsFeedback();
        bsFeedback.setUserId(req.getUserId());
        bsFeedback.setInfo(req.getInfo());
        bsFeedback.setCreateTime(new Date());
        userService.addUserFeedback(bsFeedback);

    }

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

    public void agentQuery(ReqMsg_User_AgentQuery req, ResMsg_User_AgentQuery resp) {
        BsAgent agent = userService.findAgentByCode(req.getAgentId());
        if (agent == null) {
            throw new PTMessageException(PTMessageEnum.USER_AGENT_ERROR);
        } else {
            userService.increaseAgentReadCount(agent.getId());
            resp.setAgentCode(agent.getAgentCode());
            resp.setAgentName(agent.getAgentName());
            resp.setAgentPic(agent.getAgentPic());
            resp.setId(agent.getId());
            resp.setNote(agent.getNote());
            resp.setAgentId(agent.getId());
            resp.setDept(agent.getDept());
        }
    }

    /**
     * 设置交易密码（没有设置过）
     * 
     * @param req
     * @param resp
     */
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
    public void checkPayPassword(ReqMsg_User_CheckPayPassword req, ResMsg_User_CheckPayPassword resp) {
        BsUser bsUser = userService.findUserById(req.getUserId());
        int time = 180 - DateUtil.getDiffeMinute(new Date(), bsUser.getPayFailTime());
        if(time > 0) { // 180分钟之内
            if(bsUser.getPayFailTimes() != null && bsUser.getPayFailTimes() >= 5){ // 失败次数超过5次，提示找回密码
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
     * 我的邀请用户
     * 
     * @param req
     * @param resp
     */
    public void myRecommend(ReqMsg_User_MyRecommend req, ResMsg_User_MyRecommend resp) {
        List<BsUser> bsUsers = userService.findSubUserByUserId(req.getUserId(), req.getPageIndex(), req.getPageSize());
        List<String> mobiles = new ArrayList<String>();
        for (BsUser bsUser : bsUsers) {
            mobiles.add(bsUser.getMobile());
        }
        resp.setMobiles(mobiles);
    }
    /**
     * 验证验证码是否正确   
     * @param req ReqMsg_User_ValidUser
     * @param res ResMsg_User_ValidUser
     */
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
    public void processingOrder(ReqMsg_User_ProcessingOrder req, ResMsg_User_ProcessingOrder res) {
    	 //判断当前用户有多少个处理中的订单
        Integer processingNum = userTransDetailService.processingNum(req.getUserId());
        res.setProcessingNum(processingNum);
    }
    
    /**
     * 校验手机是否已经存在
     * 
     * @param req
     * @param res
     */
    public void checkMobile(ReqMsg_User_CheckMobile req, ResMsg_User_CheckMobile res){
        boolean isValidUser = userService.isValidMobile(req.getMobile());
        if (isValidUser) {
            res.setIsRegister(true);
        } else {
            res.setIsRegister(false);
        }
    }
    /**
     * 校验是否是新用户
     * @param req
     * @param res
     */
    public void checkNewUser(ReqMsg_User_CheckNewUser req, ResMsg_User_CheckNewUser res){
    	BsUser user = userService.findUserById(req.getUserId());
    	BsSysConfig configStart = bsSysConfigService.findKey(Constants.CHECK_NEWUSER_START_TIME);
    	BsSysConfig configEnd = bsSysConfigService.findKey(Constants.CHECK_NEWUSER_END_TIME);
    	Date startTime = DateUtil.parseDateTime(configStart.getConfValue());
    	Date endTime = DateUtil.parseDateTime(configEnd.getConfValue());
    	boolean flag = false;
    	if(startTime.compareTo(user.getRegisterTime()) > 0 && 
    			(user.getFirstBuyTime() == null || startTime.compareTo(user.getFirstBuyTime()) <= 0)){
    		flag = true;
    	}
    	if(startTime.compareTo(user.getRegisterTime()) <= 0 && endTime.compareTo(user.getRegisterTime()) >=0){
    		flag = true;
    	}
    	res.setIsNewUser(flag);
    }
    
    /**
     * 根据手机号码查询用户信息
     * @param req ReqMsg_User_FindUserByMobile
     * @param res ResMsg_User_FindUserByMobile
     */
    public void findUserByMobile(ReqMsg_User_FindUserByMobile req, ResMsg_User_FindUserByMobile res) {
    	 //根据手机号码查询用户信息
        BsUser bsUser  = userService.findUserByMobile(req.getMobile());
        if (bsUser != null) {
/*        	res.setId(bsUser.getId());
        	res.setUserType(bsUser.getUserType());
        	res.setAgentId(bsUser.getAgentId());
        	res.setMobile(bsUser.getMobile());
        	res.setNick(bsUser.getNick());*/
        	org.springframework.beans.BeanUtils.copyProperties(bsUser, res);
		}
       
    }

    
    public void csaiRegister(ReqMsg_User_CsaiRegister req, ResMsg_User_CsaiRegister res) {
        BsUser user = new BsUser();
        user.setUserType(Constants.USER_TYPE_NORMAL);

        //手机验证是否存在
        boolean isValidUser = userService.isValidMobile(req.getMobile());
        if (isValidUser) {
            throw new PTMessageException(PTMessageEnum.MOBILE_IS_EXIT);
        }
        
		 //生成验证码
        Random random = new Random();
		StringBuffer password = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			String rand = String.valueOf(random.nextInt(10));
			password.append(rand);
		}
        user.setAgentId(req.getAgentId());
        user.setNick(null);
        user.setPassword(MD5Util.encryptMD5(password.toString() + MD5Util.getEncryptkey()));
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
        user.setRecommendId(null);
        user.setRegisterTime(new Date());
        user.setReturnPath(2);
        user.setRegTerminal(2);
        //注册账户
        boolean isCreateUser = userService.registerUser(user,null,null);
        if (!isCreateUser) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
        }

        //发送密码短信
        smsServiceClient.sendTemplateMessage(req.getMobile(), TemplateKey.XC_REGISTER_MSG, req.getMobile(), password.toString());
		
        res.setMobile(req.getMobile());
        res.setNick(null);
        res.setUserId(userService.findUserByMobile(req.getMobile()).getId());
        res.setUserType(user.getUserType());
        res.setAgentId(user.getAgentId());
    }
    
    
    public void bindUserCallBack(ReqMsg_User_BindUserCallBack req, ResMsg_User_BindUserCallBack res) {
    	B2GReqMsg_Xicai_BindUserCallBack reqMsg_Xicai_BindUserCallBack = new B2GReqMsg_Xicai_BindUserCallBack();
    	reqMsg_Xicai_BindUserCallBack.setPhone(req.getPhone());
    	reqMsg_Xicai_BindUserCallBack.setName(req.getName());
    	reqMsg_Xicai_BindUserCallBack.setResult(req.getResult());
    	reqMsg_Xicai_BindUserCallBack.setDisplay(req.getDisplay());
    	xicaiTransportService.bindUserCallBack(reqMsg_Xicai_BindUserCallBack);
   }
    

    public void pushP2p(ReqMsg_User_PushP2p req, ResMsg_User_PushP2p res) {
     	B2GReqMsg_Xicai_PushP2P  b2gReqMsg_Xicai_PushP2P = new B2GReqMsg_Xicai_PushP2P();
       	b2gReqMsg_Xicai_PushP2P.setPid(req.getPid());
       	B2GResMsg_Xicai_PushP2P resMsg_Xicai_PushP2P = xicaiTransportService.pushP2P(b2gReqMsg_Xicai_PushP2P);
    }
    
    /**
     * 是否可以购买欧洲杯新手标
     * @param req
     * @param res
     */
    public void checkCanBuyEcupNewUser(ReqMsg_User_CheckCanBuyEcupNewUser req, ResMsg_User_CheckCanBuyEcupNewUser res) {
        boolean isNewUser = userService.checkCanBuyEcupNewUser(req.getUserId(), req.getProductId());
        res.setNewUser(isNewUser);
    }

    /**
     * 产品详情页面查询用户可用余额
     * @param req ReqMsg_User_UserBalanceQuery
     * @param res ResMsg_User_UserBalanceQuery
     */
	public void userBalanceQuery(ReqMsg_User_UserBalanceQuery req,
			ResMsg_User_UserBalanceQuery res) {

		Double balance = userService.userBalanceQuery(req.getUserId());
		res.setAvailableBalance(balance);
        BsSubAccount depJsh = subAccountService.findDEPJSHSubAccountByUserId(Integer.parseInt(req.getUserId()));
        if(depJsh == null) {
            res.setDepBalance(0.0d);
        }else {
            res.setDepBalance(depJsh.getAvailableBalance());
        }
	}

    /**
     * 校验用户是否绑卡
     * @param req   ReqMsg_User_IsBindCard
     * @param res   ResMsg_User_IsBindCard
     */
    public void isBindCard(ReqMsg_User_IsBindCard req, ResMsg_User_IsBindCard res) {
        res.setBindCard(userService.isBindBank(Integer.parseInt(req.getUserId())));
    }

    /**
     * 根据渠道Id查找对应的展示信息
     * @param req
     * @param res
     */
    public void queryAgentViewConfig(ReqMsg_User_QueryAgentViewConfig req, ResMsg_User_QueryAgentViewConfig res) {
        List<BsAgentViewConfig> configList = userService.queryByAgentId(req.getAgentId());
        if(CollectionUtils.isNotEmpty(configList)){
            res.setConfigList(BeanUtils.classToArrayList(configList));
        }

    }
    
    /**
     * 发送密码给用户
     * @param req
     * @param res
     */
    public void sendPassword(ReqMsg_User_SendPassword req, ResMsg_User_SendPassword res){
    	//发送密码短信
        smsServiceClient.sendTemplateMessage(req.getMobile(), TemplateKey.SITE_INIT_PASSWORD, req.getPassword());
    }
    /**
     * 根据用户id或agentId判断是否是钱报系用户
     */
    public void checkIsQianbaos(ReqMsg_User_CheckIsQianbaos req, ResMsg_User_CheckIsQianbaos res){
    	Integer agentId;
    	if(req.getUserId() != null){
    		BsUser user = userService.findUserById(req.getUserId());
    		agentId = user.getAgentId();
    	}else{
    		agentId = req.getAgentId();
    	}
    	
    	//判断该agentid是否在AgentViewConfig表中存在
    	res.setQianbaos(bsAgentViewConfigService.isQianbao(agentId));
    }
    
    
    
    
    /**
     * 用户管理
     * @param req ReqMsg_User_InvestManage
     * @param res ResMsg_User_InvestManage
     */
    public void investManage(ReqMsg_User_InvestManage req, ResMsg_User_InvestManage res) {
    	
    	if (Constants.PRODUCT_RETURN_TYPE_FINISH_RETURN_ALL.equals(req.getReturnType())) {
        	//查询港湾计划持有中和已完成的数量
    		
    		//当前持有金额
    		Double holdAmount = 0d;
    		List<BsSubAccountVO> myInvestHold = subAccountService.bgwMyInvestByUserId(Constants.PRODUCT_TYPE_REG,req.getUserId(),0, Integer.MAX_VALUE,"HOLDING");
    		if(CollectionUtils.isNotEmpty(myInvestHold)) {
    			for (BsSubAccountVO bsSubAccountVO : myInvestHold) {
    				holdAmount = MoneyUtil.add(holdAmount, bsSubAccountVO.getBalance()).doubleValue();
    			}
    		}
    		
    		
            // 当日收益
            Double todayInterest = 0d;
            List<BsDailyInterest> dayInterestList = investService.findDailyInterestByUserAndDay(
                req.getUserId(), DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date())));
            if(dayInterestList != null) {
            	for (BsDailyInterest bsDailyInterest : dayInterestList) {
            		bsDailyInterest.setInterest(bsDailyInterest.getInterest() == null ? 0d : bsDailyInterest.getInterest());
                    todayInterest = MoneyUtil.add(todayInterest, bsDailyInterest.getInterest()).doubleValue();
				}
            }
        	//累计已获得收益
            Double  cumulativeInvestEarnings =investService.findInvestTotalByUserId(req.getUserId());
    		
            Double  cumulativeInvestZanEarnings = 0d; 
            cumulativeInvestZanEarnings = investService.findZanInvestTotalByUserId(req.getUserId());
    		
    		//持有中
        	Integer  countHoldInvestBgw = subAccountService.countMyInvestByStatus(req.getUserId(),"HOLDING");
    		//已完成
        	Integer  countFinishInvestBgw = subAccountService.countMyInvestByStatus(req.getUserId(),"FINISH");
    		
        	
			//港湾计划
        	
        	List<BsSubAccountVO> myInvest = subAccountService.bgwMyInvestByUserId(Constants.PRODUCT_TYPE_REG,req.getUserId(),req.getPageNum(), req.getPageSize(),req.getInvestStatus());

            //查询云贷授权委托书生效时间
            BsAgreementVersion bsAgreementVersionYun =
                    agreementVersionService.queryAgreementVersionDate(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_POWERATTORNEY, Constants.AGREEMENT_VERSION_NUMBER_1_1);
            Date agreementEffectiveStartTimeYun = bsAgreementVersionYun.getAgreementEffectiveStartTime();
            //查询7贷授权委托书生效时间
            BsAgreementVersion bsAgreementVersionSeven =
                    agreementVersionService.queryAgreementVersionDate(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_POWERATTORNEY, Constants.AGREEMENT_VERSION_NUMBER_1_1);
            Date agreementEffectiveStartTimeSeven = bsAgreementVersionSeven.getAgreementEffectiveStartTime();


            if(CollectionUtils.isNotEmpty(myInvest)) {
                ArrayList<HashMap<String, Object>> investList = BeanUtils.classToArrayList(myInvest);
            	for (HashMap<String, Object> hashMap : investList) {
            		//债权关系显示时间，标的开始时间比这个时间早的，不显示标的
            		Date startTime = (Date) hashMap.get("startTime");
        		    Date loadMatchShowTime = null;
        		    loadMatchShowTime = DateUtil.parse(Constants.LOAD_MATCH_SHOW_TIME, "yyyy-MM-dd");
        		    if(com.pinting.business.util.DateUtil.compareTo(startTime, loadMatchShowTime) < 0) {
        		        hashMap.put("loan", "no");
                    } else {
                        hashMap.put("loan", "yes");
                    }
                    //授权委托书只针对存管云贷、7贷产品
                    if(Constants.BS_PRODUCT_PROPERTY_ID_4.equals((Integer) hashMap.get("propertyId"))) {
                        //计划管理页面《出借服务协议》与《授权委托书》协议名字显示判断
                        Date openTime = (Date) hashMap.get("openTime");

                        if(openTime.after(agreementEffectiveStartTimeYun)) {
                            hashMap.put("powerAttorneyFlag", "yes");
                        }else {
                            hashMap.put("powerAttorneyFlag", "no");
                        }
                    }else if(Constants.BS_PRODUCT_PROPERTY_ID_6.equals((Integer) hashMap.get("propertyId"))) {
                        Date openTime = (Date) hashMap.get("openTime");
                        if(openTime.after(agreementEffectiveStartTimeSeven)) {
                            hashMap.put("powerAttorneyFlag", "yes");
                        }else {
                            hashMap.put("powerAttorneyFlag", "no");
                        }
                    }else if(Constants.BS_PRODUCT_PROPERTY_ID_7.equals((Integer) hashMap.get("propertyId"))) {
                        hashMap.put("powerAttorneyFlag", "yes");
                    } else {
                        hashMap.put("powerAttorneyFlag", "null");
                    }
                }
            	res.setInvestList(investList);
            }
        	
        	//列表中条数
        	if ("HOLDING".equals(req.getInvestStatus())) {
				res.setCount(countHoldInvestBgw);
			}else if ("FINISH".equals(req.getInvestStatus())) {
				res.setCount(countFinishInvestBgw);
			}else {
				res.setCount(0);
			}
        	res.setCountTotalInvestBgw(countHoldInvestBgw);
        	res.setCountHoldInvestBgw(countHoldInvestBgw);
        	res.setCountFinishInvestBgw(countFinishInvestBgw);
        	
        	res.setHoldAmount(holdAmount);
        	res.setDayInvestEarnings(todayInterest);
        	
        	
        	
        	if(cumulativeInvestEarnings == null){
        		cumulativeInvestEarnings = 0d;
        	}
        	if (cumulativeInvestZanEarnings == null) {
        		cumulativeInvestZanEarnings = 0d;
			}
        	res.setCumulativeInvestEarnings(MoneyUtil.defaultRound(cumulativeInvestEarnings - cumulativeInvestZanEarnings).doubleValue());

		}else if (Constants.PRODUCT_RETURN_TYPE_AVERAGE_CAPITAL_PLUS_INTEREST.equals(req.getReturnType())) {
			//委托计划（赞分期计划）
	    	//查询赞分期计划持有中、委托中和已完成的数量
			
			//当前持有金额
    		Double holdAmount = 0d;
    		holdAmount  =  assetsService.zanMyInvestCurrentHoldByUserId(req.getUserId());
            
        	//累计已获得收益
            Double  cumulativeInvestEarnings =investService.findZanInvestTotalByUserId(req.getUserId());
    		
    		
    		//持有中
        	Integer  countHoldInvestBgw = subAccountService.countMyInvestZanByStatus(req.getUserId(),"HOLDING");
    		//已完成
        	Integer  countFinishInvestBgw = subAccountService.countMyInvestZanByStatus(req.getUserId(),"FINISH");
        	//委托中
        	Integer  countEntrustingInvestBgw = subAccountService.countMyInvestZanByStatus(req.getUserId(),"ENTRUSTING");
    		
			//赞分期计划
        	List<BsSubAccountVO> myInvest = assetsService.zanMyInvestByUserId(req.getUserId(),req.getPageNum(), req.getPageSize(),req.getInvestStatus());
        	res.setInvestList(BeanUtils.classToArrayList(myInvest));
        	
        	//列表中条数
        	if ("HOLDING".equals(req.getInvestStatus())) {
				res.setCount(countHoldInvestBgw);
			}else if ("FINISH".equals(req.getInvestStatus())) {
				res.setCount(countFinishInvestBgw);
			}else if ("ENTRUSTING".equals(req.getInvestStatus())) {
				res.setCount(countEntrustingInvestBgw);
			}else {
				res.setCount(0);
			}
        	res.setCountTotalInvestBgw(countHoldInvestBgw + countEntrustingInvestBgw);
        	res.setCountHoldInvestBgw(countHoldInvestBgw);
        	res.setCountEntrustInvestBgw(countEntrustingInvestBgw);
        	res.setCountFinishInvestBgw(countFinishInvestBgw);
        	
        	res.setHoldAmount(holdAmount);
        	res.setCumulativeInvestEarnings(cumulativeInvestEarnings == null ? 0 : cumulativeInvestEarnings);
			
		}else {
			throw new PTMessageException(PTMessageEnum.USER_INVEST_INFO_ERROR);
		}
    }

    /**
     * 信任免登
     * @param req
     * @param res
     */
    public void trustLogin(ReqMsg_User_TrustLogin req, ResMsg_User_TrustLogin res) {
        if(StringUtil.isEmpty(req.getIp()) || StringUtil.isEmpty(req.getMobile())
                || StringUtil.isEmpty(req.getAgentCode())){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "入参错误");
        }
        BsUser user = userTrustLoginService.trustLogin(req);
        res.setMobile(user.getMobile());
        res.setAgentId(user.getAgentId());
        res.setUserType(user.getUserType());
        res.setUserId(user.getId());
    }
    
    public void recommendBonus(ReqMsg_User_RecommendBonus req, ResMsg_User_RecommendBonus res) {
    	Double bonusAmount = bonusGrantPlanService.sumBonusAmount(req.getUserId());
    	res.setRecommendAmountTotal(bonusAmount);
    }

    /**
     * 用户最大并发会话数控制
     * @param req
     * @param res
     */
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
	 * 记录用户ip对应的地址信息
	 * 
	 * @param req
	 * @param res
	 */
	public void addUserAddress(ReqMsg_User_AddUserAddress req,
			ResMsg_User_AddUserAddress res) {
		BsUserAddAddress info = new BsUserAddAddress();
		info.setAddress(req.getAddress());
		info.setUserId(req.getUserId());
		userService.userAddAddress(info);
	}
	
	public void confirmTransaction(ReqMsg_User_ConfirmTransaction req, ResMsg_User_ConfirmTransaction res) {
		boolean isConfirmTransaction = userService.userStatusConfirmTransaction(req.getUserId());
		res.setIsConfirmTransaction(isConfirmTransaction);
		BsUser user = userService.findUserById(req.getUserId());
		res.setUserStatus(user==null? "" : String.valueOf(user.getStatus().intValue()));
    }
	
}
