package com.pinting.site.qblogin.controller;

import com.pinting.business.hessian.site.message.*;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.interceptor.PreURLInterceptor;
import com.pinting.site.common.utils.IpUtil;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.qblogin.enums.TargetJumpEnum;
import com.pinting.util.AESEncryptUtil;
import com.pinting.util.Constants;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
public class UserTrustLoginController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(UserTrustLoginController.class);
    private static String aesKey = "De8sM4FZvRCRia4V5wJudw==";

    static {
        try {
            if (Constants.SITE_SERVER_MODE_PROD.equals(GlobEnvUtil.get("server.mode"))) {
                aesKey = "S+7eBj9MPw+F7wzRIuENCg==";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private CommunicateBusiness siteService;

    @RequestMapping("micro2.0/user/trust_login/{channel}")
    public String loginInit(HttpServletRequest request, HttpServletResponse response,
                            @PathVariable String channel, Map<String, Object> model) {
        //ip白名单
         String ip = IpUtil.getClientIp(request);
        //参数解析
        String jumpUrl = "";
        String backUrl = "";

        logger.info("信任免渠道编号：channel=" + channel);
        switch (channel){
            case Constants.AGENT_VIEW_ID_QB: //钱报渠道
                backUrl = request.getParameter("backurl");
                String tokenAES = request.getParameter("token");
                logger.info("信任免登请求IP地址：ip=" + ip);
                logger.info("信任免登入参：[backUrl=" + backUrl + "][token=" + tokenAES + "]");
                tokenAES = AESEncryptUtil.restoreSymbol(tokenAES);
                logger.info("信任免登入参：[tokenAES=" + tokenAES + "]");
                String token = "";
                try {
                    token = URLDecoder.decode(AESEncryptUtil.decryptByAESAndBase64(aesKey, tokenAES), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    logger.error("信任免登token解析异常", e);
                    return "/micro2.0/channel/channel_error";
                }
                logger.info("信任免登token解析：[token=" + token + "]");
                JSONObject tokenObj = JSONObject.fromObject(token);
                String mobile = tokenObj.getString("mobile");
                String target = tokenObj.getString("target");
                
                String subChannel = tokenObj.get("channel")==null ?"":tokenObj.getString("channel");
                String productCode =  tokenObj.get("product_code")==null ?"":tokenObj.getString("product_code");
                //信任免登（自动注册）
                ReqMsg_User_TrustLogin trustLogin = new ReqMsg_User_TrustLogin();
                trustLogin.setAgentCode(Constants.AGENT_VIEW_ID_QB);
                trustLogin.setIp(ip);
                trustLogin.setMobile(mobile);
                trustLogin.setSubChannel(subChannel);
                ResMsg_User_TrustLogin resTrustLogin = (ResMsg_User_TrustLogin) siteService.handleMsg(trustLogin);
                if(ConstantUtil.BSRESCODE_SUCCESS.equals(resTrustLogin.getResCode())) {
                    //cookie登记
                    ReqMsg_ActiveUserRecord_AddRecord ActiveUserReq = new ReqMsg_ActiveUserRecord_AddRecord();
                    ActiveUserReq.setUserId(resTrustLogin.getUserId());
                    ActiveUserReq.setSrcUrl("micro2.0/user/trust_login/"+channel);
                    ActiveUserReq.setUserId(resTrustLogin.getUserId());
                    ActiveUserReq.setTerminalType("H5");
                    siteService.handleMsg(ActiveUserReq);//活跃用户统计记为H5
                    userCookieManage(request, response, resTrustLogin, "qianbao");
                    logger.info("信任免登成功，准备跳转至["+target+"]");
                    if(TargetJumpEnum.PRODUCT_DETAIL.getCode().equals(target)){
                    	
                        //产品详情页跳转
                        if(StringUtil.isEmpty(productCode)){
                            logger.info("信任免登异常[缺少产品编码]");
                            return "/micro2.0/channel/channel_error";
                        }
                        logger.info("信任免登跳转产品详情页面");
                        //产品详情查询(此刻可直接查询产品信息并跳转页面，或只forward请求并另写一个产品详情请求)
                        //jumpUrl = "/qianbao178/app/product_detail";
                        model.put("id", productCode);
                        model.put("userId", resTrustLogin.getUserId());
                        model.put("from", Constants.FROM_178_APP);
                        return "forward:/micro2.0/regular/index";
                    }else if (TargetJumpEnum.FUND_IN.getCode().equals(target)) {
                        //产品充值页面跳转
                    	logger.info("信任免登跳转充值页面");
                        model.put("from", Constants.FROM_178_APP);
                        return "forward:/micro2.0/topUp/top_up";
					}else if (TargetJumpEnum.FUND_OUT.getCode().equals(target)) {
                    	//产品提现页面跳转            
                    	model.put("wfrom", Constants.FROM_178_APP);
                    	return "forward:/micro2.0/withdraw/withdraw_deposit";
					}	
                	logger.info("信任免登未匹配到目标跳转页面");
                } else{
                    //信任免登异常
                    logger.info("信任免登异常["+resTrustLogin.getResMsg()+"]");
                    return "/micro2.0/channel/channel_error";
                }
                break;
            default:
                break;
        }
        //信任免登
        if(StringUtil.isEmpty(jumpUrl)){
            logger.info("信任免登异常[通道不支持]");
            return "/micro2.0/channel/channel_error";
        }
        model.put("backUrl", backUrl);
        return jumpUrl;
    }


    /**
     * 存微网cookie
     * @param request
     * @param response
     * @param resLogin
     * @param pageChannel
     */
    private void userCookieManage(HttpServletRequest request, HttpServletResponse response, ResMsg_User_TrustLogin resLogin, String pageChannel) {
        CookieManager manager = new CookieManager(request);

        manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(),
                String.valueOf(resLogin.getUserId()), true);
        manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_TYPE.getName(),
                resLogin.getUserType(), true);
        manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_MOBILE_NUM.getName(),
                resLogin.getMobile(), true);
        manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_AGENT_ID.getName(), pageChannel, true);
        manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_178_BACK_URL.getName(), request.getParameter("backurl"), true);
        // 实际的用户渠道ID
        if(resLogin.getAgentId() != null) {
            manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_REAL_AGENT_ID.getName(), String.valueOf(resLogin.getAgentId()), true);
        }
        //判断是否是钱报相关的agentid，是则存cookie
        if(null != resLogin.getAgentId()){
            List<Integer> agentIdList = PreURLInterceptor.agentIdList;
            if(CollectionUtils.isNotEmpty(agentIdList)){
                for (Integer agentId : agentIdList) {
                    if(resLogin.getAgentId() == agentId){
                        manager.setValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), agentId.toString() , true);
                        manager.save(response, CookieEnums._VIEW.getName(), null, "/", -1, true);
                    }
                }
            }
        }
        manager.save(response, CookieEnums._SITE.getName(), null, "/", 5*365 * 24 * 3600, true);
    }

}
