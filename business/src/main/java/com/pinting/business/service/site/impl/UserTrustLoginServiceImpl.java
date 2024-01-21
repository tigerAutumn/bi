package com.pinting.business.service.site.impl;

import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.ReqMsg_User_TrustLogin;
import com.pinting.business.model.AutoRedPacketParams;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.BsUser;
import com.pinting.business.service.site.RedPacketService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.service.site.UserTrustLoginService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by babyshark on 2017/7/29.
 */
@Service
public class UserTrustLoginServiceImpl implements UserTrustLoginService {
    @Autowired
    private BsSysConfigMapper bsSysConfigMapper;
    @Autowired
    private UserService userService;
    @Autowired
	private SMSServiceClient smsServiceClient;
    @Autowired
    private RedPacketService redPacketService;

    private Logger logger = LoggerFactory.getLogger(UserTrustLoginService.class);

    @Override
    public BsUser trustLogin(ReqMsg_User_TrustLogin req) {
        //ip白名单校验、允许渠道校验
//        BsSysConfig ipConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.TRUST_LOGIN_IP);
//        String[] ips = ipConfig.getConfValue().split(",");
//        if(!ArrayUtils.contains(ips, req.getIp())){
//            throw new PTMessageException(PTMessageEnum.ILLEGAL_REQUEST);
//        }
        BsSysConfig agentConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.TRUST_LOGIN_AGENT);
        String[] agents = agentConfig.getConfValue().split(",");
        if(!ArrayUtils.contains(agents, req.getAgentCode())){
            throw new PTMessageException(PTMessageEnum.AGENT_ERROR);
        }
        BsUser user = userService.findUserByMobile(req.getMobile());
        //用户存在则信任登录(用户为非对应渠道时，不改变用户属性)
        if(user != null){
            BsUser userTemp = new BsUser();
            userTemp.setId(user.getId());
            userTemp.setLoginTime(new Date());
            userTemp.setLoginFailTimes(0);
            if (!StringUtil.isEmpty(req.getSubChannel())) {
            	userTemp.setCreateChannel(Integer.valueOf(req.getSubChannel()));
			}
            userService.updateBsUser(userTemp);
            BsUser resUser = new BsUser();
            resUser.setId(user.getId());
            resUser.setUserType(user.getUserType());
            resUser.setAgentId(user.getAgentId());
            resUser.setMobile(user.getMobile());
            return resUser;
        }
        //用户不存在则自动注册
        BsUser userRegist = new BsUser();
        userRegist.setUserType(Constants.USER_TYPE_NORMAL);
        userRegist.setAgentId(Integer.valueOf(req.getAgentCode()));
        int password = (int)((Math.random()*9+1)*100000);
        userRegist.setPassword(MD5Util.encryptMD5( password + MD5Util.getEncryptkey()));
        userRegist.setMobile(req.getMobile());
        userRegist.setStatus(Constants.USER_STATUS_1);
        userRegist.setIsBindBank(Constants.IS_BIND_BANK_NO);
        userRegist.setIsBindName(Constants.IS_BIND_NAME_NO);
        userRegist.setCurrentInterest(0.0);
        userRegist.setCurrentBonus(0.0);
        userRegist.setTotalInterest(0.0);
        userRegist.setTotalBonus(0.0);
        userRegist.setCanWithdraw(0.0);
        userRegist.setTotalTrans(0);
        userRegist.setRelation(-1);
        userRegist.setRegisterTime(new Date());
        userRegist.setReturnPath(Constants.RETURN_PATH_BALANCE);
        userRegist.setRegTerminal(null);
        if (!StringUtil.isEmpty(req.getSubChannel())) {
        	userRegist.setCreateChannel(Integer.valueOf(req.getSubChannel()));
        }
        boolean isCreateUser = userService.registerUser(userRegist,null,null);
        if (!isCreateUser) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
        }
        if(Constants.AGENT_QIANBAO_ID == Integer.valueOf(req.getAgentCode())){
        	 String link = GlobEnvUtil.get("wechat.server.web")+"/micro2.0/qb_index";
        	smsServiceClient.sendTemplateMessage(req.getMobile(),
					TemplateKey.QB_APP_REGIST,String.valueOf(password),link);
        }
        BsUser resUser = new BsUser();
        resUser.setId(userRegist.getId());
        resUser.setUserType(userRegist.getUserType());
        resUser.setAgentId(userRegist.getAgentId());
        resUser.setMobile(userRegist.getMobile());

        // 嵌入红包发送代码
        try {
            AutoRedPacketParams params = new AutoRedPacketParams();
            params.setUserId(resUser.getId());
            params.setTriggerType(Constants.AUTO_RED_PACKET_TIGGER_TYPE_NEW_USER);
            redPacketService.autoRedPacketSend(params);
        } catch (PTMessageException e) {
            logger.info("钱报APP渠道注册用户发送注册红包失败：{}", e.getMessage());
        }
        return resUser;
    }
}
