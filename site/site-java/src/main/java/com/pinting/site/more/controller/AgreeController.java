package com.pinting.site.more.controller;

import com.pinting.business.hessian.site.message.*;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.exception.PTException;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.SpringBeanUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.more.enums.AgreeTypeEnum;
import com.pinting.site.more.enums.AgreeVersionServiceEnum;
import com.pinting.site.more.service.AgreeCompensateService;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;
import com.pinting.util.DESUtil;
import com.pinting.util.WeChatShareUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 协议
 *
 * @author bianyatian
 */
@Controller
public class AgreeController extends BaseController {
    @Autowired
    private WeChatUtil weChatUtil;
    @Autowired
    private CommunicateBusiness siteService;

    /**
     * 账户服务协议
     *
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/account")
    public String account(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        String link = GlobEnv.getWebURL("/micro2.0/index");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if (StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
            if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                link += "&agentViewFlag=" + viewAgentFlagCookie;
            }
        }
        // 分享
        Map<String, String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        //app来源
        String type = request.getParameter("type");
        if ("ios".equals(type) || "android".equals(type)) {
            return channel + "/agreement/account_app";
        }
        return channel + "/agreement/account";
    }

    /**
     * 投资协议 出借咨询与服务协议
     *
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/financial")
    public String financial(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
                            Map<String, Object> model) {

        //购买页面需要的参数
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String rate = request.getParameter("rate");
        String term = request.getParameter("term");
        String minInvestAmount = request.getParameter("minInvestAmount");
        String balance = request.getParameter("balance");
        String regMobile = request.getParameter("regMobile");
        String alreadyUse = request.getParameter("alreadyUse");
        String token = request.getParameter("token");
        String redPacketId = request.getParameter("redPacketId");
        String useFlag = request.getParameter("useFlag");
        String amount_pre = request.getParameter("amount_pre");
        String proLimitAmout = request.getParameter("proLimitAmout");
        String maxSingleInvestAmount = request.getParameter("maxSingleInvestAmount");
        String isSupportRedPacket = request.getParameter("isSupportRedPacket");
        String isBindCard = request.getParameter("isBindCard");
        String agentViewFlag = request.getParameter("agentViewFlag");
        //购买页面需要的参数，返回到页面
        model.put("id", id);
        model.put("name", name);
        model.put("rate", rate);
        model.put("term", term);
        model.put("minInvestAmount", minInvestAmount);
        model.put("balance", balance);
        model.put("regMobile", regMobile);
        model.put("alreadyUse", alreadyUse);
        model.put("redPacketId", redPacketId);
        model.put("useFlag", useFlag);
        model.put("amount_pre", amount_pre);
        model.put("proLimitAmout", proLimitAmout);
        model.put("maxSingleInvestAmount", maxSingleInvestAmount);
        model.put("isSupportRedPacket", isSupportRedPacket);
        model.put("isBindCard", isBindCard);
        model.put("from", request.getParameter("from"));
        
        //产品---目标债权类别
        String propertyType = request.getParameter("propertyType");
        String propertySymbol = request.getParameter("propertySymbol");
        model.put("propertySymbol", propertySymbol);
        String link = GlobEnv.getWebURL("/micro2.0/index");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if (StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
            if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                link += "&agentViewFlag=" + viewAgentFlagCookie;
            }
        }
        // 分享
        Map<String, String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);

        model.put("productId", id);
        //app来源
        String type = request.getParameter("type");
        if ("ios".equals(type) || "android".equals(type)) {
            //查询本产品信息
            if (!"".equals(request.getParameter("productId")) && request.getParameter("productId") != null) {
                ReqMsg_Product_InfoQuery infoReq = new ReqMsg_Product_InfoQuery();
                infoReq.setId(Integer.valueOf(request.getParameter("productId")));
                ResMsg_Product_InfoQuery infoRes = (ResMsg_Product_InfoQuery) siteService.handleMsg(infoReq);
                propertySymbol = infoRes.getPropertySymbol();
            }
            if (Constants.PRODUCT_PROPERTY_TYPE_CASH_LOOP.equals(propertyType)) {
                //现金循环贷---新协议
                if (Constants.PROPERTY_SYMBOL_7_DAI.equals(propertySymbol)) {
                    return channel + "/agreement/financial_app_new_7dai";
                } else if(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(propertySymbol) ||
                        Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(propertySymbol) ||
                        Constants.PROPERTY_SYMBOL_FREE.equals(propertySymbol)){ //APP存管产品对应的授权委托书模板(云贷/7贷)
                    return channel + "/agreement/lend_service_template_app";
                } else if(Constants.PROPERTY_SYMBOL_ZSD.equals(propertySymbol)){ //APP赞时贷产品对应的借款咨询服务协议
                    return channel + "/agreement/zsd_loanService_template_app";
                } else {
                    return channel + "/agreement/financial_app_new";
                }
            } else if (Constants.PRODUCT_PROPERTY_TYPE_CONSUME.equals(propertyType)) {
                return channel + "/agreement/financial_app";
            }
        }

        if (Constants.PRODUCT_PROPERTY_TYPE_CASH_LOOP.equals(propertyType)) {
            //现金循环贷---新协议
            if (Constants.PROPERTY_SYMBOL_7_DAI.equals(propertySymbol)) {
                return channel + "/agreement/financial_new_7dai";
            } else if(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(propertySymbol) ||
                    Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(propertySymbol) ||
                    Constants.PROPERTY_SYMBOL_FREE.equals(propertySymbol)) { //H5存管产品授权委托书模板(云贷/7贷)
                return channel + "/agreement/lend_service_template";
            } else if(Constants.PROPERTY_SYMBOL_ZSD.equals(propertySymbol)) { //H5赞时贷产品对应的借款咨询服务协议
                return channel + "/agreement/zsd_loanService_template";
            } else {
                return channel + "/agreement/financial_new";
            }

        } else {
            return channel + "/agreement/financial";
        }
    }

    /**
     * 注册服务协议
     *
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/register")
    public String register(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        String link = GlobEnv.getWebURL("/micro2.0/index");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if (StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
            if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                link += "&agentViewFlag=" + viewAgentFlagCookie;
            }
        }
        // 分享
        Map<String, String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        //app来源
        String type = request.getParameter("type");
        if ("ios".equals(type) || "android".equals(type)) {
            return channel + "/agreement/register_app";
        }
        return channel + "/agreement/register";
    }

    /**
     * 支付协议 H5 APP
     *
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/pay")
    public String more(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        String link = GlobEnv.getWebURL("/micro2.0/index");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if (StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
            if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                link += "&agentViewFlag=" + viewAgentFlagCookie;
            }
        }
        // 分享
        Map<String, String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);

        //授权委托书
        ReqMsg_User_InfoQuery userReq = new ReqMsg_User_InfoQuery();
        CookieManager manangr = new CookieManager(request);
        String userIdH5 = manangr.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        if(StringUtil.isNotEmpty(userIdH5)) {
            userReq.setUserId(Integer.parseInt(userIdH5));
            ResMsg_User_InfoQuery userRes = (ResMsg_User_InfoQuery) siteService.handleMsg(userReq);
            model.put("userName", userRes.getUserName()); //委托人
            model.put("userMobile", userRes.getMobile()); //用户名
            model.put("idCard", userRes.getIdCard()); //身份证号
            model.put("signDate", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日")); //签署日期 系统时间
        }

        //app来源
        String type = request.getParameter("type");
        if ("android".equals(type)) {
            String userId = "";
            try {
                 userId = request.getParameter(URLDecoder.decode("userId","utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            DESUtil des = new DESUtil("cfgubijn");
            String user_id  = des.decryptStr(userId);
            if(StringUtil.isNotEmpty(user_id)) {
                userReq.setUserId(Integer.parseInt(user_id));
                ResMsg_User_InfoQuery userRes = (ResMsg_User_InfoQuery) siteService.handleMsg(userReq);
                model.put("userName", userRes.getUserName());
                model.put("userMobile", userRes.getMobile());
                model.put("idCard", userRes.getIdCard());
                model.put("signDate", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
            }
            return channel + "/agreement/pay_app_new";
        }else if("ios".equals(type)) {
            String userId = request.getParameter("userId");
            if(StringUtil.isNotEmpty(userId)) {
                userReq.setUserId(Integer.parseInt(userId));
                ResMsg_User_InfoQuery userRes = (ResMsg_User_InfoQuery) siteService.handleMsg(userReq);
                model.put("userName", userRes.getUserName());
                model.put("userMobile", userRes.getMobile());
                model.put("idCard", userRes.getIdCard());
                model.put("signDate", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
            }
            return channel + "/agreement/pay_app_new";
        }
        return channel + "/agreement/pay_new";
    }

    /**
     * 支付协议PC 授权委托书-数据获取
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/agreement/pay")
    public Map<String, Object> auditWithdrawDetailInfo(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        ReqMsg_User_InfoQuery userReq = new ReqMsg_User_InfoQuery();
        CookieManager manangr = new CookieManager(request);
        String userId = manangr.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        if(StringUtil.isNotEmpty(userId)) {
            userReq.setUserId(Integer.parseInt(userId));
            ResMsg_User_InfoQuery userRes = (ResMsg_User_InfoQuery) siteService.handleMsg(userReq);
            model.put("userName", userRes.getUserName()); //委托人
            model.put("userNameOther", userRes.getUserName());
            model.put("userMobile", userRes.getMobile()); //用户名
            model.put("idCard", userRes.getIdCard()); //身份证号
            model.put("signDate", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日")); //签署日期 系统时间
        }
        return model;
    }

    /**
     * 自动出借计划协议  PC
     *
     * @param channel
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/entrustAgreement")
    public String entrustAgreementInit(@PathVariable String channel, ReqMsg_User_InfoQuery req,
                                       HttpServletRequest request, HttpServletResponse response,
                                       Map<String, Object> model) {
        //判断协议是否展示
        model.put("DISPLAYPROTOCOL", "DISPLAYPROTOCOL");
        try {
            CookieManager cookieManager = new CookieManager(request);
            String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_USER_ID.getName(), true);

            String subAccountId = request.getParameter("subAccountId");
            String productId = request.getParameter("productId");

            ReqMsg_Match_CheckUserIdLoanRelationId reqRelation = new ReqMsg_Match_CheckUserIdLoanRelationId();
            reqRelation.setUserId(Integer.parseInt(userId));

            boolean flag = checkUserIdSubAccountId(Integer.parseInt(userId), Integer.parseInt(subAccountId));
            if (!flag) {
                String url = channel + "/regular/position_entrust_error";
                return url;
            } else {
                req.setUserId(Integer.parseInt(userId));
                // 1、查询用户信息
                ResMsg_User_InfoQuery resMsg = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
                model.put("userMobile", StringUtil.left(resMsg.getMobile(), 3) + "****" + StringUtil.right(resMsg.getMobile(), 4));
                //身份证号码：18位的隐藏掉中间10位，如111111**********11；15位隐藏中间8位，如111111********1
                model.put("userIdCard", resMsg.getIdCard().length() == 18 ? StringUtil.left(resMsg.getIdCard(), 6) + "**********" + StringUtil.right(resMsg.getIdCard(), 2) : StringUtil.left(resMsg.getIdCard(), 6) + "********" + StringUtil.right(resMsg.getIdCard(), 1));
                model.put("userRealName", resMsg.getUserName());

                // 2、根据subAccountId查询站岗户金额
                ReqMsg_Account_SubAccountById reqAccount = new ReqMsg_Account_SubAccountById();
                try {
                    reqAccount.setId(Integer.parseInt(subAccountId));
                } catch (Exception e) {
                    String url = channel + "/agreement/product_not_self_error";
                    if ("gen2.0".equals(channel) || "gen178".equals(channel)) {
                        url = channel + "/regular/product_not_self_error";
                    }
                    return url;
                }
                ResMsg_Account_SubAccountById resAccount = (ResMsg_Account_SubAccountById) siteService.handleMsg(reqAccount);
                //a)计划名称
                model.put("projectTitle", resAccount.getProductName());
                //b)预期年化收益率
                model.put("rateOfReturn", resAccount.getProductRate());
                //c)期初待出借本金数额：委托金额
                model.put("principalAmount", resAccount.getOpenBalance());
                //d)加入时间：委托交易成功时间，精确到日
                if (resAccount.getOpenTime() != null) {
                    model.put("joinTime", DateUtil.formatDateTime(resAccount.getOpenTime(), "yyyy年MM月dd日"));
                } else {
                    model.put("joinTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
                }
                //协议编号
                model.put("agreementNumber", resAccount.getId());
                //签订时间
                if (resAccount.getOpenTime() != null) {
                    model.put("signingTime", DateUtil.formatDateTime(resAccount.getOpenTime(), "yyyy年MM月dd日"));
                } else {
                    model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
                }

                // 3、查询产品信息
                ReqMsg_Product_InfoQuery reqMsg = new ReqMsg_Product_InfoQuery();
                /*if(null == productId){
                    String url = channel + "/agreement/product_not_self_error";
					if("gen2.0".equals(channel) || "gen178".equals(channel)) {
						url = channel + "/regular/product_not_self_error";
					}
					return url;
				}*/
                reqMsg.setId(Integer.parseInt(productId));
                ResMsg_Product_InfoQuery resProduct = (ResMsg_Product_InfoQuery) siteService.handleMsg(reqMsg);
                // 产品限额
                model.put("maxTotalAmount", resProduct.getMaxTotalAmount());
                //投资期限转换
                int term = resProduct.getTrem();
                //e)冻结期：计划期限
                model.put("lockupPeriod", term);
                //f)出借范围：计划期限
                model.put("loanRange", term);
                //g)还款方式：等额本息
                model.put("repayment", "等额本息");
                //h)期末预期收益数额： 年化金额 等额本息计算
                ReqMsg_Match_GetExpectedRevenueAmount reqRevenueAmount = new ReqMsg_Match_GetExpectedRevenueAmount();
                reqRevenueAmount.setAccountId(Integer.parseInt(subAccountId));
                reqRevenueAmount.setProductId(Integer.parseInt(productId));
                ResMsg_Match_GetExpectedRevenueAmount resRevenueAmount = (ResMsg_Match_GetExpectedRevenueAmount) siteService.handleMsg(reqRevenueAmount);
                model.put("expectedReturn", resRevenueAmount.getIncomeAmount());

                String url = "";
                if ("gen2.0".equals(channel) || "gen178".equals(channel)) {
                    if(resAccount.isZanAgreementDate()) { //赞分期新旧协议时间区分标志
                        //新协议
                        url = channel + "/regular/agreement_entrust";
                    }else {
                        //老协议
                        url = channel + "/regular/agreement_entrust_old";
                    }
                }
                return url;
            }
        } catch (Exception e) {
            e.printStackTrace();
            String url = channel + "/regular/position_entrust_error";
            return url;
        }

    }

    /**
     * 自动出借计划协议  App、H5 投资购买产品列表
     *
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/entrustAgreementApp")
    public String entrustAgreementInitApp(@PathVariable String channel, ReqMsg_User_InfoQuery req,
                                          HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        //app来源
        String type = request.getParameter("type");
        String userId = request.getParameter("userId");
        String investId = request.getParameter("investId");
        String url = "";
        boolean zanAgreementDate = false;
        if ("ios".equals(type) || "android".equals(type)) {
            model.put("clientType", type);
            // 1、查询用户信息
            req.setUserId(Integer.parseInt(userId));
            ResMsg_User_InfoQuery resMsg = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
            model.put("userMobile", StringUtil.left(resMsg.getMobile(), 3) + "****" + StringUtil.right(resMsg.getMobile(), 4));
            //身份证号码：18位的隐藏掉中间10位，如111111**********11；15位隐藏中间8位，如111111********1
            model.put("userIdCard", resMsg.getIdCard().length() == 18 ? StringUtil.left(resMsg.getIdCard(), 6) + "**********" + StringUtil.right(resMsg.getIdCard(), 2) : StringUtil.left(resMsg.getIdCard(), 6) + "********" + StringUtil.right(resMsg.getIdCard(), 1));
            model.put("userRealName", resMsg.getUserName());

            // 2、根据investId查询站岗户金额
            ReqMsg_Account_SubAccountById reqAccount = new ReqMsg_Account_SubAccountById();
            reqAccount.setId(Integer.parseInt(investId));
            ResMsg_Account_SubAccountById resAccount = (ResMsg_Account_SubAccountById) siteService.handleMsg(reqAccount);
            //a)计划名称
            model.put("projectTitle", resAccount.getProductName());
            //b)预期年化收益率
            model.put("rateOfReturn", resAccount.getProductRate());
            //c)期初待出借本金数额：委托金额
            model.put("principalAmount", resAccount.getOpenBalance());
            //d)加入时间：委托交易成功时间，精确到日
            if (resAccount.getOpenTime() != null) {
                model.put("joinTime", DateUtil.formatDateTime(resAccount.getOpenTime(), "yyyy年MM月dd日"));
            } else {
                model.put("joinTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
            }
            //协议编号
            model.put("agreementNumber", resAccount.getId());
            //签订时间
            if (resAccount.getOpenTime() != null) {
                model.put("signingTime", DateUtil.formatDateTime(resAccount.getOpenTime(), "yyyy年MM月dd日"));
            } else {
                model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
            }
            zanAgreementDate = resAccount.isZanAgreementDate();

            // 3、查询产品信息
            ReqMsg_Product_InfoQuery reqMsg = new ReqMsg_Product_InfoQuery();
            reqMsg.setId(resAccount.getProductId());
            ResMsg_Product_InfoQuery resProduct = (ResMsg_Product_InfoQuery) siteService.handleMsg(reqMsg);
            // 产品限额
            model.put("maxTotalAmount", resProduct.getMaxTotalAmount());
            //投资期限转换
            int term = resProduct.getTrem();
            //e)冻结期：计划期限
            model.put("lockupPeriod", term);
            //f)出借范围：计划期限
            model.put("loanRange", term);
            //g)还款方式：等额本息
            model.put("repayment", "等额本息");
            //h)期末预期收益数额： 年化金额 等额本息计算
            ReqMsg_Match_GetExpectedRevenueAmount reqRevenueAmount = new ReqMsg_Match_GetExpectedRevenueAmount();
            reqRevenueAmount.setAccountId(Integer.parseInt(investId));
            reqRevenueAmount.setProductId(resAccount.getProductId());
            ResMsg_Match_GetExpectedRevenueAmount resRevenueAmount = (ResMsg_Match_GetExpectedRevenueAmount) siteService.handleMsg(reqRevenueAmount);
            model.put("expectedReturn", resRevenueAmount.getIncomeAmount());

            if(zanAgreementDate) { //赞分期新旧协议时间区分标志
                //新协议
                url = channel + "/agreement/agreement_entrust_app";
            }else {
                //老协议
                url = channel + "/agreement/agreement_entrust_app_old";
            }

        } else { //H5 我的投资进入
            String link = GlobEnv.getWebURL("/micro2.0/index");
            // 钱报的参数
            String qianbao = request.getParameter("qianbao");
            if (StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                link += "?qianbao=qianbao";
                CookieManager manager = new CookieManager(request);
                String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
                if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                    link += "&agentViewFlag=" + viewAgentFlagCookie;
                }
            }
            Map<String, String> sign = weChatUtil.createAuth(request);
            sign.put("link", link);
            model.put("weichat", sign);

            CookieManager cookieManager = new CookieManager(request);
            String userIdMicro = cookieManager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_USER_ID.getName(), true);
            if(StringUtil.isNotEmpty(userIdMicro)) {
                req.setUserId(Integer.parseInt(userIdMicro));
            }

            //校验SubAccountId是否属于该用户
            if(StringUtil.isNotEmpty(userIdMicro)) {
                boolean flag = checkUserIdSubAccountId(Integer.parseInt(userIdMicro), Integer.parseInt(investId));
                if (!flag) {
                    model.put("agreementName", "自动出借计划协议");
                    model.put("info", "投资记录");
                    link = GlobEnv.getWebURL("/micro2.0/index");
                    WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                    url = channel + "/agreement/agreement_error_page";
                    return url;
                } else {
                    // 1、查询用户信息
                    ResMsg_User_InfoQuery resMsg = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
                    model.put("userMobile", StringUtil.left(resMsg.getMobile(), 3) + "****" + StringUtil.right(resMsg.getMobile(), 4));
                    //身份证号码：18位的隐藏掉中间10位，如111111**********11；15位隐藏中间8位，如111111********1
                    model.put("userIdCard", resMsg.getIdCard().length() == 18 ? StringUtil.left(resMsg.getIdCard(), 6) + "**********" + StringUtil.right(resMsg.getIdCard(), 2) : StringUtil.left(resMsg.getIdCard(), 6) + "********" + StringUtil.right(resMsg.getIdCard(), 1));
                    model.put("userRealName", resMsg.getUserName());

                    // 2、根据subAccountId查询站岗户金额
                    ReqMsg_Account_SubAccountById reqAccount = new ReqMsg_Account_SubAccountById();
                    reqAccount.setId(Integer.parseInt(investId));
                    ResMsg_Account_SubAccountById resAccount = (ResMsg_Account_SubAccountById) siteService.handleMsg(reqAccount);
                    //a)计划名称
                    model.put("projectTitle", resAccount.getProductName());
                    //b)预期年化收益率
                    model.put("rateOfReturn", resAccount.getProductRate());
                    //c)期初待出借本金数额：委托金额
                    model.put("principalAmount", resAccount.getOpenBalance());
                    //d)加入时间：委托交易成功时间，精确到日
                    if (resAccount.getOpenTime() != null) {
                        model.put("joinTime", DateUtil.formatDateTime(resAccount.getOpenTime(), "yyyy年MM月dd日"));
                    } else {
                        model.put("joinTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
                    }
                    //协议编号
                    model.put("agreementNumber", resAccount.getId());
                    //签订时间
                    if (resAccount.getOpenTime() != null) {
                        model.put("signingTime", DateUtil.formatDateTime(resAccount.getOpenTime(), "yyyy年MM月dd日"));
                    } else {
                        model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
                    }
                    zanAgreementDate = resAccount.isZanAgreementDate();

                    // 3、查询产品信息
                    ReqMsg_Product_InfoQuery reqMsg = new ReqMsg_Product_InfoQuery();
                    reqMsg.setId(resAccount.getProductId());
                    ResMsg_Product_InfoQuery resProduct = (ResMsg_Product_InfoQuery) siteService.handleMsg(reqMsg);
                    // 产品限额
                    model.put("maxTotalAmount", resProduct.getMaxTotalAmount());
                    //投资期限转换
                    int term = resProduct.getTrem();
                    //e)冻结期：计划期限
                    model.put("lockupPeriod", term);
                    //f)出借范围：计划期限
                    model.put("loanRange", term);
                    //g)还款方式：等额本息
                    model.put("repayment", "等额本息");
                    //h)期末预期收益数额： 年化金额 等额本息计算
                    ReqMsg_Match_GetExpectedRevenueAmount reqRevenueAmount = new ReqMsg_Match_GetExpectedRevenueAmount();
                    reqRevenueAmount.setAccountId(Integer.parseInt(investId));
                    reqRevenueAmount.setProductId(resAccount.getProductId());
                    ResMsg_Match_GetExpectedRevenueAmount resRevenueAmount = (ResMsg_Match_GetExpectedRevenueAmount) siteService.handleMsg(reqRevenueAmount);
                    model.put("expectedReturn", resRevenueAmount.getIncomeAmount());

                    if ("micro2.0".equals(channel)) {
                        if(zanAgreementDate) { //赞分期新旧协议时间区分标志
                            //新协议
                            url = channel + "/agreement/agreement_entrust_app";
                        }else {
                            //老协议
                            url = channel + "/agreement/agreement_entrust_app_old";
                        }

                    }
                }
            }else {
                model.put("agreementName", "自动出借计划协议");
                model.put("info", "投资记录");
                link = GlobEnv.getWebURL("/micro2.0/index");
                WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                url = channel + "/agreement/agreement_error_page";
                return url;
            }

        }
        return url;
    }


    /**
     * 自动出借计划协议  H5 / App 产品购买页面
     *
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/entrustBuyAgreementApp")
    public String entrustBuyAgreementInitApp(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
                                             Map<String, Object> model) {

        //购买页面需要的参数
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String rate = request.getParameter("rate");
        String term = request.getParameter("term");
        String minInvestAmount = request.getParameter("minInvestAmount");
        String balance = request.getParameter("balance");
        String regMobile = request.getParameter("regMobile");
        String alreadyUse = request.getParameter("alreadyUse");
        String token = request.getParameter("token");
        String redPacketId = request.getParameter("redPacketId");
        String useFlag = request.getParameter("useFlag");
        String amount_pre = request.getParameter("amount_pre");
        String proLimitAmout = request.getParameter("proLimitAmout");
        String maxSingleInvestAmount = request.getParameter("maxSingleInvestAmount");
        String isSupportRedPacket = request.getParameter("isSupportRedPacket");
        String isBindCard = request.getParameter("isBindCard");
        String propertySymbol = request.getParameter("propertySymbol");
        String agentViewFlag = request.getParameter("agentViewFlag");
        //购买页面需要的参数，返回到页面
        model.put("from", request.getParameter("from"));
        model.put("id", id);
        model.put("name", name);
        model.put("rate", rate);
        model.put("term", term);
        model.put("minInvestAmount", minInvestAmount);
        model.put("balance", balance);
        model.put("regMobile", regMobile);
        model.put("alreadyUse", alreadyUse);
        model.put("redPacketId", redPacketId);
        model.put("useFlag", useFlag);
        model.put("amount_pre", amount_pre);
        model.put("proLimitAmout", proLimitAmout);
        model.put("maxSingleInvestAmount", maxSingleInvestAmount);
        model.put("isSupportRedPacket", isSupportRedPacket);
        model.put("isBindCard", isBindCard);
        model.put("propertySymbol", propertySymbol);

        String link = GlobEnv.getWebURL("/micro2.0/index");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if (StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
            if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                link += "&agentViewFlag=" + viewAgentFlagCookie;
            }
        }
        // 分享
        Map<String, String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);

        //app来源
        String type = request.getParameter("type");
        model.put("clientType", type);
        String url = "";
        if ("ios".equals(type) || "android".equals(type)) {
            url = channel + "/agreement/agreement_entrust_appBuy";
        }else {
            url = channel + "/agreement/agreement_entrust_buy";
        }

        return url;
    }

    /**
     * 借款协议  PC / H5
     *
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param loanRelationId 债权关联关系ID
     * @return
     */
    @RequestMapping("{channel}/agreement/loanAgreement")
    public String loanAgreementInit(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
                                    Map<String, Object> model, String loanRelationId) {
        try {
            CookieManager cookieManager = new CookieManager(request);
            String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_USER_ID.getName(), true);
            ReqMsg_Match_CheckUserIdLoanRelationId reqRelation = new ReqMsg_Match_CheckUserIdLoanRelationId();
            reqRelation.setUserId(Integer.parseInt(userId));
            boolean flag = checkUserIdlLoanRelationId(Integer.parseInt(userId), Integer.parseInt(loanRelationId));
            if (!flag) {
                String url = channel + "/regular/position_loan_error";
                if("micro2.0".equals(channel)) {
                    model.put("agreementName", "借款协议");
                    model.put("info", "借款协议");
                    String link = GlobEnv.getWebURL("/micro2.0/index");
                    WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                    url = channel + "/agreement/agreement_error_page";
                }
                return url;
            } else {
                ReqMsg_Match_GetUserMatchLoanInfo reqLoanInfo = new ReqMsg_Match_GetUserMatchLoanInfo();
                reqLoanInfo.setLoanRelationId(Integer.parseInt(loanRelationId));
                ResMsg_Match_GetUserMatchLoanInfo resLoanInfo = (ResMsg_Match_GetUserMatchLoanInfo) siteService.handleMsg(reqLoanInfo);

                if (resLoanInfo != null && !"".equals(resLoanInfo)) {
                    //协议编号   格式16100000+借款编号
                    model.put("agreementNumber", "16100000" + String.valueOf(resLoanInfo.getLoanId()));
                    //签订时间
                    if (resLoanInfo.getLoanTime() != null) {
                        model.put("signingTime", DateUtil.formatDateTime(resLoanInfo.getLoanTime(), "yyyy年MM月dd日"));
                    } else {
                        model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
                    }
                    model.put("BGWAddress", "杭州市江干区四季青街道西子国际大厦C座2305");
                    model.put("BGWTel", "400-806-1230");
                    model.put("ZANAddress", "杭州市江干区四季青街道西子国际大厦C座2304");
                    model.put("ZANTel", "400-696-5858");

                    //借款人姓名
                    model.put("loanUserName", resLoanInfo.getBorrowerName().length() == 2 ? (StringUtil.left(resLoanInfo.getBorrowerName(), 1) + "*") : (StringUtil.left(resLoanInfo.getBorrowerName(), 1) + "**"));
                    //借款人手机号
                    model.put("loanMobile", StringUtil.left(resLoanInfo.getMobile(), 3) + "****" + StringUtil.right(resLoanInfo.getMobile(), 4));
                    //借款人身份证号 18位的隐藏掉中间10位，如111111**********11；15位隐藏中间8位，如111111********1
                    model.put("loanIdCard", resLoanInfo.getIdCard().length() == 18 ? StringUtil.left(resLoanInfo.getIdCard(), 6) + "**********" + StringUtil.right(resLoanInfo.getIdCard(), 2) : StringUtil.left(resLoanInfo.getIdCard(), 6) + "********" + StringUtil.right(resLoanInfo.getIdCard(), 1));
                    //借款用途
                    model.put("purpose", resLoanInfo.getPurpose());

                    ReqMsg_Match_GetLoanBasicInfo reqLoanBasic = new ReqMsg_Match_GetLoanBasicInfo();
                    reqLoanBasic.setLoanRelationId(Integer.parseInt(loanRelationId));
                    ResMsg_Match_GetLoanBasicInfo resLoanBasic = (ResMsg_Match_GetLoanBasicInfo) siteService.handleMsg(reqLoanBasic);
                    //总期数
                    model.put("period", resLoanBasic.getPeriod());
                    //借款年化利率
                    model.put("regdProductRate", resLoanBasic.getLoanInterestRate());
                    //起始日 取借款时间
                    model.put("interestBeginDate", DateUtil.formatDateTime(resLoanBasic.getLoanTime(), "yyyy年MM月dd日"));
                    //本息
                    model.put("planTotal", resLoanBasic.getNeedRepayMoney4Month());
                    //还款日
                    model.put("planDate", DateUtil.formatDateTime(resLoanBasic.getLnRepayStartDate(), "yyyy年MM月dd日"));
                    //每月还款日
                    model.put("repaymentDay", resLoanBasic.getDay4Month());
                    //结息日期
                    model.put("interestSettlementDate", DateUtil.formatDateTime(resLoanBasic.getLnRepayEndDate(), "yyyy年MM月dd日"));

                    //根据借款ID查询这一笔借款对应的理财人数据
                    ReqMsg_Match_GetUserFinancialManagement reqUser = new ReqMsg_Match_GetUserFinancialManagement();
                    reqUser.setLoanId(resLoanInfo.getLoanId());
                    ResMsg_Match_GetUserFinancialManagement resUser = (ResMsg_Match_GetUserFinancialManagement) siteService.handleMsg(reqUser);
                    model.put("moneymanInfoData", resUser.getUserList());
                    //借款总本金数额
                    model.put("sumTotalAmount", resUser.getSumTotalAmount());
                }

                String url = "";
                if ("gen2.0".equals(channel) || "gen178".equals(channel)) {

                    if(resLoanInfo.isZanAgreementDate()) { //赞分期新旧协议时间区分标志
                        //新协议
                        url = channel + "/regular/loan_agreement";
                    }else {
                        //老协议
                        url = channel + "/regular/loan_agreement_old";
                    }

                } else if ("micro2.0".equals(channel)) {
                    String link = GlobEnv.getWebURL("/micro2.0/index");
                    // 钱报的参数
                    String qianbao = request.getParameter("qianbao");
                    if (StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                        model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                        link += "?qianbao=qianbao";
                        CookieManager manager = new CookieManager(request);
                        String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
                        if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                            link += "&agentViewFlag=" + viewAgentFlagCookie;
                        }
                    }
                    Map<String, String> sign = weChatUtil.createAuth(request);
                    sign.put("link", link);
                    model.put("weichat", sign);

                    if(resLoanInfo.isZanAgreementDate()) { //赞分期新旧协议时间区分标志
                        //新协议
                        url = channel + "/agreement/loan_agreement_app";
                    }else {
                        //老协议
                        url = channel + "/agreement/loan_agreement_app_old";
                    }

                }
                return url;
            }

        } catch (Exception e) {
            e.printStackTrace();
            String url = channel + "/regular/position_loan_error";
            return url;
        }

    }

    /**
     * 借款协议  币港湾APP专享
     *
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param loanRelationId 借贷关系ID
     * @return
     */
    @RequestMapping("{channel}/agreement/loanAgreementBGWAPPInit")
    public String loanAgreementBGWAPPInit(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
                                          Map<String, Object> model, String loanRelationId) {

        ReqMsg_Match_GetUserMatchLoanInfo reqLoanInfo = new ReqMsg_Match_GetUserMatchLoanInfo();
        reqLoanInfo.setLoanRelationId(Integer.parseInt(loanRelationId));
        ResMsg_Match_GetUserMatchLoanInfo resLoanInfo = (ResMsg_Match_GetUserMatchLoanInfo) siteService.handleMsg(reqLoanInfo);
        String url = "";
        if (resLoanInfo != null && !"".equals(resLoanInfo)) {
            //协议编号   格式16100000+借款编号
            model.put("agreementNumber", "16100000" + String.valueOf(resLoanInfo.getLoanId()));
            //签订时间
            if (resLoanInfo.getLoanTime() != null) {
                model.put("signingTime", DateUtil.formatDateTime(resLoanInfo.getLoanTime(), "yyyy年MM月dd日"));
            } else {
                model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
            }
            model.put("BGWAddress", "杭州市江干区四季青街道西子国际大厦C座2305");
            model.put("BGWTel", "400-806-1230");
            model.put("ZANAddress", "杭州市江干区四季青街道西子国际大厦C座2304");
            model.put("ZANTel", "400-696-5858");

            //借款人姓名
            model.put("loanUserName", resLoanInfo.getBorrowerName().length() == 2 ? (StringUtil.left(resLoanInfo.getBorrowerName(), 1) + "*") : (StringUtil.left(resLoanInfo.getBorrowerName(), 1) + "**"));
            //借款人手机号
            model.put("loanMobile", StringUtil.left(resLoanInfo.getMobile(), 3) + "****" + StringUtil.right(resLoanInfo.getMobile(), 4));
            //借款人身份证号 18位的隐藏掉中间10位，如111111**********11；15位隐藏中间8位，如111111********1
            model.put("loanIdCard", resLoanInfo.getIdCard().length() == 18 ? StringUtil.left(resLoanInfo.getIdCard(), 6) + "**********" + StringUtil.right(resLoanInfo.getIdCard(), 2) : StringUtil.left(resLoanInfo.getIdCard(), 6) + "********" + StringUtil.right(resLoanInfo.getIdCard(), 1));
            //借款用途
            model.put("purpose", resLoanInfo.getPurpose());

            ReqMsg_Match_GetLoanBasicInfo reqLoanBasic = new ReqMsg_Match_GetLoanBasicInfo();
            reqLoanBasic.setLoanRelationId(Integer.parseInt(loanRelationId));
            ResMsg_Match_GetLoanBasicInfo resLoanBasic = (ResMsg_Match_GetLoanBasicInfo) siteService.handleMsg(reqLoanBasic);
            //总期数
            model.put("period", resLoanBasic.getPeriod());
            //借款年化利率
            model.put("regdProductRate", resLoanBasic.getLoanInterestRate());
            //起始日 取借款时间
            model.put("interestBeginDate", DateUtil.formatDateTime(resLoanBasic.getLoanTime(), "yyyy年MM月dd日"));
            //本息
            model.put("planTotal", resLoanBasic.getNeedRepayMoney4Month());
            //还款日
            model.put("planDate", DateUtil.formatDateTime(resLoanBasic.getLnRepayStartDate(), "yyyy年MM月dd日"));
            //每月还款日
            model.put("repaymentDay", resLoanBasic.getDay4Month());
            //结息日期
            model.put("interestSettlementDate", DateUtil.formatDateTime(resLoanBasic.getLnRepayEndDate(), "yyyy年MM月dd日"));

            //根据借款ID查询这一笔借款对应的理财人数据
            ReqMsg_Match_GetUserFinancialManagement reqUser = new ReqMsg_Match_GetUserFinancialManagement();
            reqUser.setLoanId(resLoanInfo.getLoanId());
            ResMsg_Match_GetUserFinancialManagement resUser = (ResMsg_Match_GetUserFinancialManagement) siteService.handleMsg(reqUser);
            model.put("moneymanInfoData", resUser.getUserList());
            //借款总本金数额
            model.put("sumTotalAmount", resUser.getSumTotalAmount());

            if(resLoanInfo.isZanAgreementDate()) { //赞分期新旧协议时间区分标志
                //新协议
                url = channel + "/agreement/loan_agreement_client";
            }else {
                //老协议
                url = channel + "/agreement/loan_agreement_client_old";
            }

        }

        return url;

    }

    /**
     * 债权转让协议 PC H5
     *
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param loanRelationId 债权关联关系ID
     * @return
     */
    @RequestMapping("{channel}/agreement/agreementClaims")
    public String agreementClaimsInit(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
                                      Map<String, Object> model, String loanRelationId) {
        boolean flags = false;
        try {
            CookieManager cookieManager = new CookieManager(request);
            String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_USER_ID.getName(), true);
            ReqMsg_Match_CheckUserIdLoanRelationId reqRelation = new ReqMsg_Match_CheckUserIdLoanRelationId();
            reqRelation.setUserId(Integer.parseInt(userId));
            boolean flag = checkUserIdlLoanRelationId(Integer.parseInt(userId), Integer.parseInt(loanRelationId));
            if (!flag) {
                String url = channel + "/regular/position_claims_error";
                if("micro2.0".equals(channel)) {
                    model.put("agreementName", "债权转让协议");
                    model.put("info", "债权转让协议");
                    String link = GlobEnv.getWebURL("/micro2.0/index");
                    WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                    url = channel + "/agreement/agreement_error_page";
                }
                return url;
            } else {
                ReqMsg_Match_GetUserClaimsTransferInfo reqClaims = new ReqMsg_Match_GetUserClaimsTransferInfo();
                reqClaims.setLoanRelationId(Integer.parseInt(loanRelationId));
                ResMsg_Match_GetUserClaimsTransferInfo resClaims = (ResMsg_Match_GetUserClaimsTransferInfo) siteService.handleMsg(reqClaims);
                if (resClaims != null && !"".equals(resClaims)) {
                    //协议编号
                    model.put("agreementNumber", resClaims.getInSubAccountId());
                    //签订时间
                    if (resClaims.getTransferTime() != null) {
                        model.put("signingTime", DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));
                    } else {
                        model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
                    }

                    //借款债权转让记录表 id
                    model.put("creditTransferId", resClaims.getId());
                    //本次转让价格
                    model.put("amount", resClaims.getInAmount());

                    //转让金额大写
                    model.put("capitalAmount", resClaims.getInAmount() == null ? 0 : MoneyUtil.digitUppercase(resClaims.getInAmount()));

                    //本次转让债权价值(受让人出资金额)
                    model.put("inAmount", resClaims.getInAmount());
                    //出让人姓名
                    model.put("outUserName", resClaims.getOutUserName());
                    //受让人姓名
                    model.put("inUserName", resClaims.getInUserName());
                    //出让人手机号（账户）
                    model.put("outUserMobile", resClaims.getOutUserMobile());
                    //受让人手机号（账户）
                    model.put("inUserMobile", resClaims.getInUserMobile());
                    //出让人身份证
                    model.put("outUserIdCardNo", resClaims.getOutUserIdCardNo());
                    //受让人身份证
                    model.put("inUserIdCardNo", resClaims.getInUserIdCardNo());
                    //借款人姓名
                    model.put("borrowUserName", resClaims.getBorrowUserName());
                    //借款人身份证
                    model.put("borrowUserIdCardNo", resClaims.getBorrowUserIdCardNo());
                    //还款起始日
                    model.put("firstRepayDate", DateUtil.formatDateTime(resClaims.getFirstRepayDate(), "yyyy年MM月dd日"));
                    //每月应收本息
                    model.put("repayAmount4Month", resClaims.getRepayAmount4Month());
                    //预计债权年化收益
                    model.put("repayAmount4All", MoneyUtil.subtract(resClaims.getRepayAmount4All(), resClaims.getAmount()));
                    //还款期限（月）
                    model.put("term", resClaims.getTerm());
                    //剩余还款月数
                    model.put("leftTerm", resClaims.getLeftTerm());
                    //首次还款期次
                    model.put("firstTerm", resClaims.getFirstTerm());
                    //转让时间
                    model.put("transferTime", DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));
                    //计划名称
                    model.put("productName", resClaims.getProductName());
                    //本次转让债权价值
                    if(resClaims.getDiscountAmount() == null){
                        flags = true;
                    }else {
                        model.put("creditorAcount", resClaims.getAmount() + resClaims.getDiscountAmount());
                        //转让折让金额
                        model.put("discountAmount", resClaims.getDiscountAmount());
                        //预期收益
                        model.put("expectProfit", resClaims.getExpectProfit());
                        //本次转让价格新
                        model.put("amountNew",resClaims.getAmount());
                        //新转让金额大写
                        model.put("capitalAmountNew", resClaims.getAmount() == null ? 0 : MoneyUtil.digitUppercase(resClaims.getAmount()));
                    }
                }
                String url = "";
                if ("gen2.0".equals(channel) || "gen178".equals(channel)) {
                    if(flags){
                        url = channel + "/regular/agreement_claims";
                    }else {
                        if(resClaims.isZanAgreementDate()) { //赞分期新旧协议时间区分标志
                            //新协议
                            url = channel + "/regular/agreement_claims_discountamount";
                        }else {
                            //老协议
                            url = channel + "/regular/agreement_claims_discountamount_old";
                        }

                    }
                } else if ("micro2.0".equals(channel)) {
                    String link = GlobEnv.getWebURL("/micro2.0/index");
                    // 钱报的参数
                    String qianbao = request.getParameter("qianbao");
                    if (StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                        model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                        link += "?qianbao=qianbao";
                        CookieManager manager = new CookieManager(request);
                        String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
                        if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                            link += "&agentViewFlag=" + viewAgentFlagCookie;
                        }
                    }
                    Map<String, String> sign = weChatUtil.createAuth(request);
                    sign.put("link", link);
                    model.put("weichat", sign);
                    if(flags){
                        url = channel + "/agreement/agreement_claims_app";
                    }else {
                        if(resClaims.isZanAgreementDate()) { //赞分期新旧协议时间区分标志
                            //新协议
                            url = channel + "/agreement/agreement_claims_discountment_app";
                        }else {
                            //老协议
                            url = channel + "/agreement/agreement_claims_discountment_app_old";
                        }
                    }
                }
                return url;
            }

        } catch (Exception e) {
            e.printStackTrace();
            String url = channel + "/regular/position_claims_error";
            if("micro2.0".equals(channel)) {
                model.put("agreementName", "债权转让协议");
                model.put("info", "债权转让协议");
                String link = GlobEnv.getWebURL("/micro2.0/index");
                WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                url = channel + "/agreement/agreement_error_page";
            }
            return url;
        }

    }

    /**
     * 债权转让协议 币港湾APP专享
     *
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param loanRelationId 债权关联关系ID
     * @return
     */
    @RequestMapping("{channel}/agreement/agreementClaimsBGWAPPInit")
    public String agreementClaimsBGWAPPInit(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
                                            Map<String, Object> model, String loanRelationId) {
        boolean flags = false;
        ReqMsg_Match_GetUserClaimsTransferInfo reqClaims = new ReqMsg_Match_GetUserClaimsTransferInfo();
        reqClaims.setLoanRelationId(Integer.parseInt(loanRelationId));
        ResMsg_Match_GetUserClaimsTransferInfo resClaims = (ResMsg_Match_GetUserClaimsTransferInfo) siteService.handleMsg(reqClaims);
        if (resClaims != null && !"".equals(resClaims)) {
            //协议编号
            model.put("agreementNumber", resClaims.getInSubAccountId());
            //签订时间
            if (resClaims.getTransferTime() != null) {
                model.put("signingTime", DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));
            } else {
                model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
            }

            //借款债权转让记录表 id
            model.put("creditTransferId", resClaims.getId());
            //转让金额
            model.put("amount", resClaims.getInAmount());
            //转让金额大写
            model.put("capitalAmount", resClaims.getAmount() == null ? 0 : MoneyUtil.digitUppercase(resClaims.getAmount()));
            //本次转让债权价值(受让人出资金额)
            model.put("inAmount", resClaims.getInAmount());
            //出让人姓名
            model.put("outUserName", resClaims.getOutUserName());
            //受让人姓名
            model.put("inUserName", resClaims.getInUserName());
            //出让人手机号（账户）
            model.put("outUserMobile", resClaims.getOutUserMobile());
            //受让人手机号（账户）
            model.put("inUserMobile", resClaims.getInUserMobile());
            //出让人身份证
            model.put("outUserIdCardNo", resClaims.getOutUserIdCardNo());
            //受让人身份证
            model.put("inUserIdCardNo", resClaims.getInUserIdCardNo());
            //借款人姓名
            model.put("borrowUserName", resClaims.getBorrowUserName());
            //借款人身份证
            model.put("borrowUserIdCardNo", resClaims.getBorrowUserIdCardNo());
            //还款起始日
            model.put("firstRepayDate", DateUtil.formatDateTime(resClaims.getFirstRepayDate(), "yyyy年MM月dd日"));
            //每月应收本息
            model.put("repayAmount4Month", resClaims.getRepayAmount4Month());
            //预计债权年化收益
            model.put("repayAmount4All", MoneyUtil.subtract(resClaims.getRepayAmount4All(), resClaims.getInAmount()));
            //还款期限（月）
            model.put("term", resClaims.getTerm());
            //剩余还款月数
            model.put("leftTerm", resClaims.getLeftTerm());
            //首次还款期次
            model.put("firstTerm", resClaims.getFirstTerm());
            //转让时间
            model.put("transferTime", DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));
            //计划名称
            model.put("productName", resClaims.getProductName());

            //本次转让债权价值
            if(resClaims.getDiscountAmount() == null){
                flags = true;
            }else {
                model.put("creditorAcount", resClaims.getAmount() + resClaims.getDiscountAmount());
                //转让折让金额
                model.put("discountAmount", resClaims.getDiscountAmount());
                //预期收益
                model.put("expectProfit", resClaims.getExpectProfit());
                //本次转让价格新
                model.put("amountNew",resClaims.getAmount());
                //新转让金额大写
                model.put("capitalAmountNew", resClaims.getAmount() == null ? 0 : MoneyUtil.digitUppercase(resClaims.getAmount()));
            }

        }
        String url = "";
        if ("gen2.0".equals(channel) || "gen178".equals(channel)) {
            if(flags){
                url = channel + "/regular/agreement_claims";
            }else {
                if(resClaims.isZanAgreementDate()) { //赞分期新旧协议时间区分标志
                    //新协议
                    url = channel + "/regular/agreement_claims_discountamount";
                }else {
                    //老协议
                    url = channel + "/regular/agreement_claims_discountamount_old";
                }

            }
        } else if ("micro2.0".equals(channel)) {
            String link = GlobEnv.getWebURL("/micro2.0/index");
            // 钱报的参数
            String qianbao = request.getParameter("qianbao");
            if (StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                link += "?qianbao=qianbao";
                CookieManager manager = new CookieManager(request);
                String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
                if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                    link += "&agentViewFlag=" + viewAgentFlagCookie;
                }
            }
            Map<String, String> sign = weChatUtil.createAuth(request);
            sign.put("link", link);
            model.put("weichat", sign);
            if(flags){
                url = channel + "/agreement/agreement_claims_client";
            }else {
                if(resClaims.isZanAgreementDate()) { //赞分期新旧协议时间区分标志
                    //新协议
                    url = channel + "/agreement/agreement_claims_discountamount_client";
                }else {
                    //老协议
                    url = channel + "/agreement/agreement_claims_discountamount_client_old";
                }

            }
        }
        return url;
    }


    //=====================  赞分期APP相关协议  =====================

    /**
     * 1、支付协议 赞分期APP专用
     *
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/paymentAgreementZanApp")
    public String paymentAgreementZanApp(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        //app来源
        String type = request.getParameter("type");
        model.put("type", type);
        if ("ios".equals(type) || "android".equals(type)) {
            return channel + "/agreement/pay_zan_app";
        }
        return channel + "/agreement/pay_zan_app";
    }

    /**
     * 2、借款协议 赞分期APP专用 无数据页面
     *
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/loanAgreementZanAppNoData")
    public String loanAgreementZanAppNoData(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        //app来源
        String type = request.getParameter("type");
        model.put("type", type);
        if ("ios".equals(type) || "android".equals(type)) {
            return channel + "/agreement/loan_agreement_zanAppNoData";
        }
        return channel + "/agreement/loan_agreement_zanAppNoData";
    }

    /**
     * 3、借款协议 赞分期APP专用
     *
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/loanAgreementZanApp")
    public String loanAgreementZanApp(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        //app来源
        String type = request.getParameter("type");
//        model.put("type", type);
        type = StringUtil.isEmpty(type)? "android" : type; // 2017-09-22 存管上线对信邦修改逻辑
        model.put("type",type);
        //合作方借款编号
        String partnerLoanId = request.getParameter("partnerLoanId");
        String url = "";

        ReqMsg_Match_GetZanAppUserLoanInfo reqZanAppLoanInfo = new ReqMsg_Match_GetZanAppUserLoanInfo();
        reqZanAppLoanInfo.setPartnerLoanId(partnerLoanId);
        ResMsg_Match_GetZanAppUserLoanInfo resZanAppLoanInfo = (ResMsg_Match_GetZanAppUserLoanInfo) siteService.handleMsg(reqZanAppLoanInfo);
        if (resZanAppLoanInfo != null && !"".equals(resZanAppLoanInfo)) {
            //协议编号  格式16100000+借款编号
            model.put("zanAgreementNumber", "16100000" + String.valueOf(resZanAppLoanInfo.getId()));
            //签订时间
            model.put("zanSigningTime", DateUtil.formatDateTime(resZanAppLoanInfo.getLoanTime(), "yyyy年MM月dd日"));

            model.put("zanBGWAddress", "杭州市江干区四季青街道西子国际大厦C座2305");
            model.put("zanBGWTel", "400-806-1230");
            model.put("loanZANAddress", "杭州市江干区四季青街道西子国际大厦C座2304");
            model.put("loanZANTel", "400-696-5858");
            //出借人列表
            model.put("dataUserInfo", resZanAppLoanInfo.getDataUserInfo());
            //借款人姓名
            model.put("lnUserName", getBlurName(resZanAppLoanInfo.getLnUserName()));
            //借款人身份证号
            model.put("lnUserIdCard", getBlurIdNo(resZanAppLoanInfo.getLnUserIdCard()));
            //借款人赞分期账户
            model.put("lnUserZANAccount", getBlurMobile(resZanAppLoanInfo.getLnUserZANAccount()));
            //借款时间（协议签署时间）
            model.put("loanTime", DateUtil.formatDateTime(resZanAppLoanInfo.getLoanTime(), "yyyy年MM月dd日"));
            //借款用途
            model.put("purpose", resZanAppLoanInfo.getPurpose());
            //借款本金数额
            model.put("approveAmount", resZanAppLoanInfo.getApproveAmount());
            //借款年化利率
            model.put("lnRate", resZanAppLoanInfo.getLnRate());
            //借款期限
            model.put("period", resZanAppLoanInfo.getPeriod());
            //月偿还本息数额
            model.put("needRepayMoney4Month", resZanAppLoanInfo.getNeedRepayMoney4Month());
            //第一期还款日
            model.put("lnRepayStartDate", DateUtil.formatDateTime(resZanAppLoanInfo.getLnRepayStartDate(), "yyyy年MM月dd日"));
            //最后一期还款日
            model.put("lnRepayEndDate", DateUtil.formatDateTime(resZanAppLoanInfo.getLnRepayEndDate(), "yyyy年MM月dd日"));
            //每月还款日
            model.put("day4Month", resZanAppLoanInfo.getDay4Month());
        }
        if ("ios".equals(type) || "android".equals(type)) {
            if(resZanAppLoanInfo.isZanAgreementDate()) { //赞分期新旧协议时间区分标志
                //新协议
                url = channel + "/agreement/loan_agreement_zanApp";
            }else {
                //老协议
                url = channel + "/agreement/loan_agreement_zanApp_old";
            }
        }

        return url;
    }

    /**
     * 4、借款咨询与服务协议 赞分期APP专用  无数据页面
     *
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/borrowingServicesZanAppNoData")
    public String borrowingServicesZanAppNoData(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        //app来源
        String type = request.getParameter("type");
        model.put("type", type);
        if ("ios".equals(type) || "android".equals(type)) {
            return channel + "/agreement/borrowing_services_zanAppNoData";
        }
        return channel + "/agreement/borrowing_services_zanAppNoData";
    }

    /**
     * 5、借款咨询与服务协议 赞分期APP专用
     *
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/borrowingServicesZanApp")
    public String borrowingServicesZanApp(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        //app来源
        String type = request.getParameter("type");
        model.put("type", type);
        //合作方借款编号
        String loanId = request.getParameter("loanId");

        ReqMsg_Match_GetUserLoanFeeList reqFee = new ReqMsg_Match_GetUserLoanFeeList();
        reqFee.setPartnerLoanId(loanId);
        ResMsg_Match_GetUserLoanFeeList resFee = (ResMsg_Match_GetUserLoanFeeList) siteService.handleMsg(reqFee);
        //监管费
        model.put("loanServiceFee", resFee.getLoanServiceFee());
        //信息服务费
        model.put("loanInfoServiceFee", resFee.getLoanInfoServiceFee());
        //账户管理费
        model.put("loanAccountManageFee", resFee.getLoanAccountManageFee());
        //保费
        model.put("loadPremium", resFee.getLoadPremium());
        //服务费 = 监管费+信息服务费+账户管理费+保费
        Double loadPremium = 0d;
        loadPremium = MoneyUtil.add((MoneyUtil.add(((resFee.getLoanServiceFee() == null ? 0d : resFee.getLoanServiceFee())), ((resFee.getLoanInfoServiceFee() == null ? 0d : resFee.getLoanInfoServiceFee()))).doubleValue()),
                (MoneyUtil.add(((resFee.getLoanAccountManageFee() == null ? 0d : resFee.getLoanAccountManageFee())), ((resFee.getLoadPremium() == null ? 0d : resFee.getLoadPremium()))).doubleValue())).doubleValue();
        model.put("loanServiceCharge", loadPremium);        //借款人姓名
        model.put("userName", resFee.getUserName());
        model.put("loanTime", DateUtil.formatDateTime(resFee.getLoanTime(), "yyyy年MM月dd日"));

        if ("ios".equals(type) || "android".equals(type)) {
            return channel + "/agreement/borrowing_services_zanApp";
        }

        return channel + "/agreement/borrowing_services_zanApp";
    }

    /**
     * 借款咨询与服务协议 -- 用于pdf生成
     *
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/borrowingServicesZan4Pdf")
    public String borrowingServicesZan4Pdf(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        //合作方借款编号
        String loanId = request.getParameter("loanId");

        ReqMsg_Match_GetUserLoanFeeList reqFee = new ReqMsg_Match_GetUserLoanFeeList();
        reqFee.setPartnerLoanId(loanId);
        ResMsg_Match_GetUserLoanFeeList resFee = (ResMsg_Match_GetUserLoanFeeList) siteService.handleMsg(reqFee);
        //监管费
        model.put("loanServiceFee", resFee.getLoanServiceFee());
        //信息服务费
        model.put("loanInfoServiceFee", resFee.getLoanInfoServiceFee());
        //账户管理费
        model.put("loanAccountManageFee", resFee.getLoanAccountManageFee());
        //保费
        model.put("loadPremium", resFee.getLoadPremium());
        //服务费 = 监管费+信息服务费+账户管理费+保费
        Double loadPremium = 0d;
        loadPremium = MoneyUtil.add((MoneyUtil.add(((resFee.getLoanServiceFee() == null ? 0d : resFee.getLoanServiceFee())), ((resFee.getLoanInfoServiceFee() == null ? 0d : resFee.getLoanInfoServiceFee()))).doubleValue()),
                (MoneyUtil.add(((resFee.getLoanAccountManageFee() == null ? 0d : resFee.getLoanAccountManageFee())), ((resFee.getLoadPremium() == null ? 0d : resFee.getLoadPremium()))).doubleValue())).doubleValue();
        model.put("loanServiceCharge", loadPremium);
        //借款人姓名
        model.put("userName", resFee.getUserName());
        model.put("loanTime", DateUtil.formatDateTime(resFee.getLoanTime(), "yyyy年MM月dd日"));

        return channel + "/agreement/borrowing_services_zan_pdf";
    }

    @RequestMapping("{channel}/agreement/loanAgreement4Pdf")
    public String loanAgreement4Pdf(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        //合作方借款编号
        String partnerLoanId = request.getParameter("partnerLoanId");

        ReqMsg_Match_GetZanAppUserLoanInfo reqZanAppLoanInfo = new ReqMsg_Match_GetZanAppUserLoanInfo();
        reqZanAppLoanInfo.setPartnerLoanId(partnerLoanId);
        ResMsg_Match_GetZanAppUserLoanInfo resZanAppLoanInfo = (ResMsg_Match_GetZanAppUserLoanInfo) siteService.handleMsg(reqZanAppLoanInfo);
        if (resZanAppLoanInfo != null && !"".equals(resZanAppLoanInfo)) {
            //协议编号  格式16100000+借款编号
            model.put("agreementNumber", "16100000" + String.valueOf(resZanAppLoanInfo.getId()));
            //签订时间
            model.put("signingTime", DateUtil.formatDateTime(resZanAppLoanInfo.getLoanTime(), "yyyy年MM月dd日"));

            model.put("BGWAddress", "杭州市江干区四季青街道西子国际大厦C座2305");
            model.put("BGWTel", "400-806-1230");
            model.put("ZANAddress", "杭州市江干区四季青街道西子国际大厦C座2304");
            model.put("ZANTel", "400-696-5858");
            //出借人列表
            model.put("dataUserInfo", resZanAppLoanInfo.getDataUserInfo());
            //借款人姓名
            model.put("lnUserName", resZanAppLoanInfo.getLnUserName());
            //借款人身份证号
            model.put("lnIdCard", resZanAppLoanInfo.getLnUserIdCard());
            //借款人赞分期账户
            model.put("lnUserZANAccount", resZanAppLoanInfo.getLnUserZANAccount());
            //借款成功时间
            model.put("loanTime", DateUtil.formatDateTime(resZanAppLoanInfo.getLoanTime(), "yyyy年MM月dd日"));
            //借款用途
            model.put("purpose", resZanAppLoanInfo.getPurpose());
            //借款本金数额
            model.put("approveAmount", resZanAppLoanInfo.getApproveAmount());
            //借款年化利率
            model.put("lnRate", resZanAppLoanInfo.getLnRate());
            //借款期限
            model.put("period", resZanAppLoanInfo.getPeriod());
            //月偿还本息数额
            model.put("needRepayMoney4Month", resZanAppLoanInfo.getNeedRepayMoney4Month());
            //第一期还款日
            model.put("lnRepayStartDate", DateUtil.formatDateTime(resZanAppLoanInfo.getLnRepayStartDate(), "yyyy年MM月dd日"));
            //最后一期还款日
            model.put("lnRepayEndDate", DateUtil.formatDateTime(resZanAppLoanInfo.getLnRepayEndDate(), "yyyy年MM月dd日"));
            //每月还款日
            model.put("day4Month", resZanAppLoanInfo.getDay4Month());
        }

        String url = "";
        if ("gen2.0".equals(channel) || "gen178".equals(channel)) {
            url = channel + "/regular/loan_agreement_pdf";
        } else if ("micro2.0".equals(channel)) {
            String link = GlobEnv.getWebURL("/micro2.0/index");
            // 钱报的参数
            String qianbao = request.getParameter("qianbao");
            if (StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                link += "?qianbao=qianbao";
                CookieManager manager = new CookieManager(request);
                String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
                if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                    link += "&agentViewFlag=" + viewAgentFlagCookie;
                }
            }
            Map<String, String> sign = weChatUtil.createAuth(request);
            sign.put("link", link);
            model.put("weichat", sign);

            if(resZanAppLoanInfo.isZanAgreementDate()) { //赞分期新旧协议时间区分标志
                //新协议
                url = channel + "/regular/loan_agreement_pdf";
            }else {
                //老协议
                url = channel + "/regular/loan_agreement_pdf_old";
            }

        }
        return url;
    }

    //==========================赞分期协议签章相关 start===============================

    /**
     * 赞分期-债权转让协议签章pdf
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param loanRelationId
     * @return
     */
    @RequestMapping("{channel}/agreement/agreementClaimsZan4Pdf")
    public String agreementClaimsZan4Pdf(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
                                      Map<String, Object> model, String loanRelationId) {

        ReqMsg_Match_GetUserClaimsTransferInfoForPdf reqClaims = new ReqMsg_Match_GetUserClaimsTransferInfoForPdf();
        reqClaims.setLoanRelationId(Integer.parseInt(loanRelationId));
        ResMsg_Match_GetUserClaimsTransferInfoForPdf resClaims = (ResMsg_Match_GetUserClaimsTransferInfoForPdf) siteService.handleMsg(reqClaims);
        if (resClaims != null && !"".equals(resClaims)) {
            //协议编号
            model.put("agreementNumber", resClaims.getInSubAccountId());
            //签订时间
            if (resClaims.getTransferTime() != null) {
                model.put("signingTime", DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));
            } else {
                model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
            }

            //借款债权转让记录表 id
            model.put("creditTransferId", resClaims.getId());
            //本次转让价格
            model.put("amount", resClaims.getInAmount());

            //转让金额大写
            model.put("capitalAmount", resClaims.getInAmount() == null ? 0 : MoneyUtil.digitUppercase(resClaims.getInAmount()));

            //本次转让债权价值(受让人出资金额)
            model.put("inAmount", resClaims.getInAmount());
            //出让人姓名
            model.put("outUserName", resClaims.getOutUserName());
            //受让人姓名
            model.put("inUserName", resClaims.getInUserName());
            //出让人手机号（账户）
            model.put("outUserMobile", resClaims.getOutUserMobile());
            //受让人手机号（账户）
            model.put("inUserMobile", resClaims.getInUserMobile());
            //出让人身份证
            model.put("outUserIdCardNo", resClaims.getOutUserIdCardNo());
            //受让人身份证
            model.put("inUserIdCardNo", resClaims.getInUserIdCardNo());
            //借款人姓名
            model.put("borrowUserName", resClaims.getBorrowUserName());
            //借款人身份证
            model.put("borrowUserIdCardNo", resClaims.getBorrowUserIdCardNo());
            //还款起始日
            model.put("firstRepayDate", DateUtil.formatDateTime(resClaims.getFirstRepayDate(), "yyyy年MM月dd日"));
            //每月应收本息
            model.put("repayAmount4Month", resClaims.getRepayAmount4Month());
            //预计债权年化收益
            model.put("repayAmount4All", MoneyUtil.subtract(resClaims.getRepayAmount4All(), resClaims.getAmount()));
            //还款期限（月）
            model.put("term", resClaims.getTerm());
            //剩余还款月数
            model.put("leftTerm", resClaims.getLeftTerm());
            //首次还款期次
            model.put("firstTerm", resClaims.getFirstTerm());
            //转让时间
            model.put("transferTime", DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));
            //计划名称
            model.put("productName", resClaims.getProductName());

            //本次转让债权价值
            model.put("creditorAcount", resClaims.getAmount() + resClaims.getDiscountAmount());
            //转让折让金额
            model.put("discountAmount", resClaims.getDiscountAmount());
            //预期收益
            model.put("expectProfit", resClaims.getExpectProfit());
            //本次转让价格新
            model.put("amountNew",resClaims.getAmount());
            //新转让金额大写
            model.put("capitalAmountNew", resClaims.getAmount() == null ? 0 : MoneyUtil.digitUppercase(resClaims.getAmount()));

        }

        String url = channel + "/agreement/agreement_claims_zan_pdf";

        return url;
    }

    //==========================赞分期协议签章相关 end===============================


    //==========================代偿lateRepay协议相关 start==========================

    // (1) 收款确认函--服务费--发给云贷、7贷
    @RequestMapping("{channel}/agreement/receiptConfirmServiceFee4Pdf")
    public String receiptConfirmServiceFee4Pdf(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        ReqMsg_Match_GetUserClaimsTransferInfoList reqClaims = new ReqMsg_Match_GetUserClaimsTransferInfoList();

        //代偿编号
        String orderNo = request.getParameter("orderNo");
        reqClaims.setOrderNo(orderNo);

        //代偿协议签订时间
        model.put("createTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));

        //代偿成功的时间
        reqClaims.setLateRepayDate(DateUtil.formatYYYYMMDD(new Date()));

        // 合作方编码
        String partnerEnum = request.getParameter("partnerEnum");
        reqClaims.setPartnerEnum(partnerEnum);
        model.put("partnerEnum", partnerEnum);

        ResMsg_Match_GetUserClaimsTransferInfoList resClaimsList = (ResMsg_Match_GetUserClaimsTransferInfoList) siteService.handleMsg(reqClaims);
        if (resClaimsList != null && !"".equals(resClaimsList)) {
            model.put("dataGrid", resClaimsList.getDataGrid());
            //云贷自主放款债权受让人名字
            //model.put("yunDaiSelfUserName", resClaimsList.getYunDaiSelfUserName());
            //云贷自主放款债权受让人身份证号
            //model.put("yunDaiSelfIdCard", resClaimsList.getYunDaiSelfIdCard());
        }
        return channel + "/regular/lateRepay_receiptConfirm_new_pdf";
    }

    // (2) 收款确认函--债转--理财人看（云贷、7贷）
    // 合规化，债转改为担保
    @RequestMapping("{channel}/agreement/letterOfCreditConfirm4Pdf")
    public String letterOfCreditConfirm4Pdf(@PathVariable String channel, HttpServletRequest request, Map<String, Object> model) {
        // 协议类型
        String agreementType = request.getParameter("agreementType");
        if (StringUtils.isBlank(agreementType)) {
            agreementType = AgreeTypeEnum.HF_YUNDAI_RECEIPTCONFIRMAGREEMENT.getCode();
        }

        // 查询协议的版本号管理
        ReqMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionReq = new ReqMsg_AgreementVersion_QueryAgreementVersionInfo();
        agreementVersionReq.setAgreementType(agreementType);
        agreementVersionReq.setAgreementEffectiveStartTime(DateUtil.format(new Date()));
        ResMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionRes =
                (ResMsg_AgreementVersion_QueryAgreementVersionInfo) siteService.handleMsg(agreementVersionReq);

        String serviceName = AgreeVersionServiceEnum.getEnumByServiceName(agreementType, agreementVersionRes.getAgreementVersion());
        AgreeCompensateService agreeCompensateService = (AgreeCompensateService) SpringBeanUtil.getBean(serviceName);
        agreeCompensateService.execute(request, model);

        return channel + "/regular/" + agreementType + "_" + agreementVersionRes.getAgreementVersion() + "_pdf";
    }

    // (3) 债权转让通知书--发给云贷、7贷
    @RequestMapping("{channel}/agreement/lateRepayCreditorNotice4Pdf")
    public String lateRepayCreditorNotice4Pdf(@PathVariable String channel, HttpServletRequest request, Map<String, Object> model) {
        // 协议类型
        String agreementType = request.getParameter("agreementType");
        if (StringUtils.isBlank(agreementType)) {
            agreementType = AgreeTypeEnum.HF_YUNDAI_CREDITORNOTICEAGREEMENT.getCode();
        }

        // 查询协议的版本号管理
        ReqMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionReq = new ReqMsg_AgreementVersion_QueryAgreementVersionInfo();
        agreementVersionReq.setAgreementType(agreementType);
        agreementVersionReq.setAgreementEffectiveStartTime(DateUtil.format(new Date()));
        ResMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionRes =
                (ResMsg_AgreementVersion_QueryAgreementVersionInfo) siteService.handleMsg(agreementVersionReq);

        String serviceName = AgreeVersionServiceEnum.getEnumByServiceName(agreementType, agreementVersionRes.getAgreementVersion());
        AgreeCompensateService agreeCompensateService = (AgreeCompensateService) SpringBeanUtil.getBean(serviceName);
        agreeCompensateService.execute(request, model);

        return channel + "/regular/" + agreementType + "_" + agreementVersionRes.getAgreementVersion() + "_pdf";
    }

    // (4) 债权转让协议--发给云贷、7贷
    @RequestMapping("{channel}/agreement/lateRepayCreditor4Pdf")
    public String lateRepayCreditor4Pdf(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        ReqMsg_Match_GetCompensateTransferInfo reqClaims = new ReqMsg_Match_GetCompensateTransferInfo();
        //借贷关系编号
        String loanRelationId = request.getParameter("loanRelationId");
        reqClaims.setLoanRelationId(Integer.parseInt(loanRelationId));
        //借款编号
        String loanId = request.getParameter("loanId");
        reqClaims.setLoanId(Integer.parseInt(loanId));

        //代偿协议签订时间
        model.put("createTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));

        // 合作方编码
        String partnerEnum = request.getParameter("partnerEnum");
        reqClaims.setPartnerEnum(partnerEnum);
        model.put("partnerEnum", partnerEnum);

        ResMsg_Match_GetCompensateTransferInfo resClaims = (ResMsg_Match_GetCompensateTransferInfo) siteService.handleMsg(reqClaims);
        //币港湾实际出借人
        model.put("userName", resClaims.getUserName());
        //出借人身份证号
        model.put("idCard", resClaims.getIdCard());
        //出借人手机号
        model.put("userMobile", resClaims.getUserMobile());

        //云贷自主放款债权受让人名字
        model.put("yunDaiSelfUserName", resClaims.getYunDaiSelfUserName());
        //云贷自主放款债权受让人身份证号
        model.put("yunDaiSelfIdCard", resClaims.getYunDaiSelfIdCard());
        //云贷自主放款债权受让人手机号码
        model.put("yunDaiSelfMobile", resClaims.getYunDaiSelfMobile());
        //借款人姓名
        model.put("loanUserName", resClaims.getLoanUserName());
        //借款人身份证号
        model.put("loanIdCard", resClaims.getLoanIdCard());
        //借款期限-云贷
        model.put("period", resClaims.getPeriod());
        //借款本金
        model.put("approveAmount", resClaims.getApproveAmount());
        //债权转让金额
        model.put("transferCreditorAmount", resClaims.getTransferCreditorAmount());
        //借款期限-7贷
        model.put("sevenPeriod", resClaims.getSevenPeriod());

        return channel + "/regular/lateRepay_creditor_pdf";
    }

    //==========================代偿lateRepay协议相关 end==========================


    //=====================  存管新增协议 start =====================


    /**
     * 网络交易资金存管账户服务协议
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/hfCustodyAccountServiceInit")
    public String hfCustodyAgreementInit(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
                                         Map<String, Object> model) {
        //app来源
        String client = request.getParameter("client");
        String userId = request.getParameter("userId");
        String url = "";
        if ("ios".equals(client) || "android".equals(client)) {
            model.put("clientType", client);

            if ("micro2.0".equals(channel)) {
                url = channel + "/agreement/hfCustody_account_service_app";
            }
        } else { //微网
            if("micro2.0".equals(channel)) {
                String link = GlobEnv.getWebURL("/micro2.0/index");
                // 钱报的参数
                String qianbao = request.getParameter("qianbao");
                if (StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                    model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                    link += "?qianbao=qianbao";
                    CookieManager manager = new CookieManager(request);
                    String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
                    if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                        link += "&agentViewFlag=" + viewAgentFlagCookie;
                    }
                }
                Map<String, String> sign = weChatUtil.createAuth(request);
                sign.put("link", link);
                model.put("weichat", sign);

                model.put("userName", request.getParameter("userName"));
                model.put("idCard", request.getParameter("idCard"));
                model.put("cardNo", request.getParameter("cardNo"));
                model.put("bankId", request.getParameter("bankId"));
                model.put("mobile", request.getParameter("mobile"));
                model.put("smsCode", request.getParameter("smsCode"));
                model.put("orderNo", request.getParameter("orderNo"));
                model.put("bankName", request.getParameter("bankName"));
                model.put("oneTop", request.getParameter("oneTop"));
                model.put("dayTop", request.getParameter("dayTop"));
                model.put("notice", request.getParameter("notice"));
                model.put("entry", request.getParameter("entry"));
                model.put("productId", request.getParameter("productId"));
                url = channel + "/agreement/hfCustody_account_service";
            }
        }
        return url;
    }

    /**
     * 借款协议（云贷）存管版 PC H5
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param loanRelationId
     * @param subAccountId
     * @return
     */
    @RequestMapping("{channel}/agreement/hfCustodyLoanAgreementInit")
    public String hfCustodyLoanAgreement(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
                                            Map<String, Object> model, String loanRelationId, String subAccountId) {
        try {
            CookieManager cookieManager = new CookieManager(request);
            String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_USER_ID.getName(), true);
            //判断借款协议loanRelationId是否属于当前登录的用户
            ReqMsg_Match_CheckUserIdLoanRelationId reqRelation = new ReqMsg_Match_CheckUserIdLoanRelationId();
            reqRelation.setUserId(Integer.parseInt(userId));
            boolean flag = checkUserIdlLoanRelationId(Integer.parseInt(userId), Integer.parseInt(loanRelationId));
            if(!flag){
                String url = channel + "/regular/position_loan_error";
                if("micro2.0".equals(channel)) {
                    model.put("agreementName", "借款协议");
                    model.put("info", "借款协议");
                    String link = GlobEnv.getWebURL("/micro2.0/index");
                    WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                    url = channel + "/agreement/agreement_error_page";
                }
                return url;
            }else{
                ReqMsg_Match_GetCustodyLoanInfo reqLoanInfo = new ReqMsg_Match_GetCustodyLoanInfo();
                reqLoanInfo.setLnLoanRelationId(Integer.parseInt(loanRelationId));
                reqLoanInfo.setSubAccountId(Integer.parseInt(subAccountId));
                ResMsg_Match_GetCustodyLoanInfo resLoanInfo =  (ResMsg_Match_GetCustodyLoanInfo) siteService.handleMsg(reqLoanInfo);

                if(resLoanInfo != null && !"".equals(resLoanInfo)) {
                    //协议编号  格式17100000+借款编号
                    model.put("agreementNumber", "17100000"+String.valueOf(resLoanInfo.getLoanId()));
                    //签订时间
                    if (resLoanInfo.getLoanDay() != null) {
                        model.put("signingTime", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                    }
                    //1、理财人信息
                    model.put("financialManagementData", resLoanInfo.getFinancialManagementList());
                    //2、出借金额总计
                    model.put("sumTotalAmount", resLoanInfo.getSumTotalAmount());
                    //3、借款信息
                    model.put("loanUserName", resLoanInfo.getLoanUserName());
                    model.put("loanIdCard", resLoanInfo.getLoanIdCard());
                    model.put("loanMobile", resLoanInfo.getLoanMobile());
                    //4、借款基本信息
                    model.put("loanAmount", resLoanInfo.getLoanAmount());
                    model.put("loanTerm", resLoanInfo.getLoanTerm());
                    model.put("loanDay", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                    model.put("loanDueDate", DateUtil.formatDateTime(resLoanInfo.getLoanDueDate(), "yyyy年MM月dd日"));
                    model.put("agreementRate", resLoanInfo.getAgreementRate());
                    //5、债权转让记录
                    model.put("transferDataList", resLoanInfo.getTransferList());
                    //6、存管云贷借款协议新旧版本时间区分标志
                    model.put("yundaiLoanAgreementDateFlag", resLoanInfo.isYundaiLoanAgreementDateFlag());

                }
                String url = "";
                if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                    url = channel + "/regular/hfCustody_loan_agreement";
                }else if("micro2.0".equals(channel)) {
                    String link = GlobEnv.getWebURL("/micro2.0/index");
                    // 钱报的参数
                    String qianbao = request.getParameter("qianbao");
                    if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                        model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                        link += "?qianbao=qianbao";
                        CookieManager manager = new CookieManager(request);
                        String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
                        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
                            link += "&agentViewFlag="+viewAgentFlagCookie;
                        }
                    }
                    Map<String,String> sign = weChatUtil.createAuth(request);
                    sign.put("link", link);
                    model.put("weichat", sign);

                    url = channel + "/agreement/hfCustody_loan_agreement";
                }
                return url;
            }

        } catch (Exception e) {
            e.printStackTrace();
            String url = channel + "/regular/position_loan_error";
            if("micro2.0".equals(channel)) {
                model.put("agreementName", "借款协议");
                model.put("info", "借款协议");
                String link = GlobEnv.getWebURL("/micro2.0/index");
                WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                url = channel + "/agreement/agreement_error_page";
            }
            return url;
        }

    }

    /**
     * 借款协议（云贷）存管版 APP
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/hfCustodyLoanAgreementApp")
    public String hfCustodyLoanAgreementApp(@PathVariable String channel, HttpServletRequest request,
                                           HttpServletResponse response, Map<String, Object> model,
                                           String loanRelationId, String subAccountId) {
        ReqMsg_Match_GetCustodyLoanInfo reqLoanInfo = new ReqMsg_Match_GetCustodyLoanInfo();
        reqLoanInfo.setLnLoanRelationId(Integer.parseInt(loanRelationId));
        reqLoanInfo.setSubAccountId(Integer.parseInt(subAccountId));
        ResMsg_Match_GetCustodyLoanInfo resLoanInfo =  (ResMsg_Match_GetCustodyLoanInfo) siteService.handleMsg(reqLoanInfo);

        if(resLoanInfo != null && !"".equals(resLoanInfo)) {
            //协议编号  格式17100000+借款编号
            model.put("agreementNumber", "17100000"+String.valueOf(resLoanInfo.getLoanId()));
            //签订时间
            if (resLoanInfo.getLoanDay() != null) {
                model.put("signingTime", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
            } else {
                model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
            }
            //1、理财人信息
            model.put("financialManagementData", resLoanInfo.getFinancialManagementList());
            //2、出借金额总计
            model.put("sumTotalAmount", resLoanInfo.getSumTotalAmount());
            //3、借款信息
            model.put("loanUserName", resLoanInfo.getLoanUserName());
            model.put("loanIdCard", resLoanInfo.getLoanIdCard());
            model.put("loanMobile", resLoanInfo.getLoanMobile());
            //4、借款基本信息
            model.put("loanAmount", resLoanInfo.getLoanAmount());
            model.put("loanTerm", resLoanInfo.getLoanTerm());
            model.put("loanDay", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
            model.put("loanDueDate", DateUtil.formatDateTime(resLoanInfo.getLoanDueDate(), "yyyy年MM月dd日"));
            model.put("agreementRate", resLoanInfo.getAgreementRate());
            //5、债权转让记录
            model.put("transferDataList", resLoanInfo.getTransferList());
            //6、存管云贷借款协议新旧版本时间区分标志
            model.put("yundaiLoanAgreementDateFlag", resLoanInfo.isYundaiLoanAgreementDateFlag());
        }
        return channel + "/agreement/hfCustody_loan_agreement_app";
    }


    /**
     * 借款协议（云贷）存管版-版本控制
     * @param channel
     * @param loanAgreementType 云贷存管借款协议类型 (HF_YUN_LOAN_AGREEMENT)
     * @param request
     * @param response
     * @param model
     * @param loanRelationId
     * @param subAccountId
     * @param effectiveTime 协议生效时间
     * @return
     */
    @RequestMapping("{channel}/agreement/hfCustodyLoanVersionAgreement")
    public String hfCustodyLoanAgreementVersion(@PathVariable String channel, String loanAgreementType,
                                                HttpServletRequest request, HttpServletResponse response,
                                                Map<String, Object> model, String loanRelationId,
                                                String subAccountId, String effectiveTime) {
        try {
            CookieManager cookieManager = new CookieManager(request);
            String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_USER_ID.getName(), true);
            //判断借款协议loanRelationId是否属于当前登录的用户
            ReqMsg_Match_CheckUserIdLoanRelationId reqRelation = new ReqMsg_Match_CheckUserIdLoanRelationId();
            reqRelation.setUserId(Integer.parseInt(userId));
            boolean flag = checkUserIdlLoanRelationId(Integer.parseInt(userId), Integer.parseInt(loanRelationId));
            if(!flag){
                String url = channel + "/regular/position_loan_error";
                return url;
            }else{
                String url = "";
                //赞时贷资金迁移协议 查询借款人的资产方
                ReqMsg_Match_CheckLnUserPartnerCode reqPartnerCode = new ReqMsg_Match_CheckLnUserPartnerCode();
                reqPartnerCode.setLoanRelationId(Integer.parseInt(loanRelationId));
                ResMsg_Match_CheckLnUserPartnerCode resPartnerCode = (ResMsg_Match_CheckLnUserPartnerCode)siteService.handleMsg(reqPartnerCode);
                if(Constants.PROPERTY_SYMBOL_ZSD.equals(resPartnerCode.getPartnerCode())) {
                    //赞时贷数据迁移时，已经匹配出去的债权明细的借款协议
                    ReqMsg_Match_GetCustodyLoanInfo reqLoanInfo = new ReqMsg_Match_GetCustodyLoanInfo();
                    reqLoanInfo.setLnLoanRelationId(Integer.parseInt(loanRelationId));
                    reqLoanInfo.setSubAccountId(Integer.parseInt(subAccountId));
                    ResMsg_Match_GetCustodyLoanInfo resLoanInfo =  (ResMsg_Match_GetCustodyLoanInfo) siteService.handleMsg(reqLoanInfo);
                    if(resLoanInfo != null && !"".equals(resLoanInfo)) {
                        //协议编号  格式17200000+借款编号
                        model.put("agreementNumber", "17200000"+String.valueOf(resLoanInfo.getLoanId()));
                        //签订时间
                        if (resLoanInfo.getLoanDay() != null) {
                            model.put("signingTime", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                        }
                        //1、理财人信息
                        model.put("financialManagementData", resLoanInfo.getFinancialManagementList());
                        //2、出借金额总计
                        model.put("sumTotalAmount", resLoanInfo.getSumTotalAmount());
                        //3、借款信息
                        model.put("loanUserName", resLoanInfo.getLoanUserName());
                        model.put("loanIdCard", resLoanInfo.getLoanIdCard());
                        model.put("loanMobile", resLoanInfo.getLoanMobile());
                        //4、借款基本信息
                        model.put("loanAmount", resLoanInfo.getLoanAmount());
                        model.put("loanTerm", resLoanInfo.getLoanTerm()/30);
                        model.put("loanDay", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                        model.put("loanDueDate", DateUtil.formatDateTime(DateUtil.addDays(resLoanInfo.getLoanDay(), (resLoanInfo.getLoanTerm()/30 - 1)), "yyyy年MM月dd日"));
                        model.put("agreementRate", resLoanInfo.getAgreementRate()/365);
                        model.put("purpose", resLoanInfo.getPurpose());//借款用途
                    }
                    if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                        url = channel + "/regular/zsd_loan_agreement";
                    }

                }else{
                    ReqMsg_Match_GetCustodyLoanInfo reqLoanInfo = new ReqMsg_Match_GetCustodyLoanInfo();
                    reqLoanInfo.setLnLoanRelationId(Integer.parseInt(loanRelationId));
                    reqLoanInfo.setSubAccountId(Integer.parseInt(subAccountId));
                    //协议类型
                    reqLoanInfo.setAgreementType(loanAgreementType);
                    ResMsg_Match_GetCustodyLoanInfo resLoanInfo =  (ResMsg_Match_GetCustodyLoanInfo) siteService.handleMsg(reqLoanInfo);

                    if(resLoanInfo != null && !"".equals(resLoanInfo)) {
                        //协议编号  格式17100000+借款编号
                        model.put("agreementNumber", "17100000"+String.valueOf(resLoanInfo.getLoanId()));
                        //签订时间
                        if (resLoanInfo.getLoanDay() != null) {
                            model.put("signingTime", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                        }
                        //1、理财人信息
                        model.put("financialManagementData", resLoanInfo.getFinancialManagementList());
                        //2、出借金额总计
                        model.put("sumTotalAmount", resLoanInfo.getSumTotalAmount());
                        //3、借款信息
                        model.put("loanUserName", resLoanInfo.getLoanUserName());
                        model.put("loanIdCard", resLoanInfo.getLoanIdCard());
                        model.put("loanMobile", resLoanInfo.getLoanMobile());
                        model.put("loanAddress", resLoanInfo.getAddress());
                        model.put("loanEmail", resLoanInfo.getEmail());
                        //4、借款基本信息
                        model.put("loanTerm", resLoanInfo.getLoanTerm());
                        model.put("loanDueDate", DateUtil.formatDateTime(resLoanInfo.getLoanDueDate(), "yyyy年MM月dd日"));
                        model.put("loanAmount", resLoanInfo.getLoanAmount());
                        model.put("loanDay", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                        model.put("agreementRate", resLoanInfo.getAgreementRate());
                        model.put("purpose", resLoanInfo.getPurpose());
                        //5、债权转让记录
                        model.put("transferDataList", resLoanInfo.getTransferList());
                        //6、存管云贷借款协议新旧版本时间区分标志
                        model.put("yundaiLoanAgreementDateFlag", resLoanInfo.isYundaiLoanAgreementDateFlag());

                    }

                    //查询云贷借款协议的版本号
                    ReqMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionReq = new ReqMsg_AgreementVersion_QueryAgreementVersionInfo();
                    agreementVersionReq.setAgreementType(loanAgreementType);
                    agreementVersionReq.setAgreementEffectiveStartTime(effectiveTime);
                    ResMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionRes =
                            (ResMsg_AgreementVersion_QueryAgreementVersionInfo) siteService.handleMsg(agreementVersionReq);

                    if(Constants.AGREEMENT_VERSION_NUMBER_NO_VERSION.equals(agreementVersionRes.getAgreementVersion())) {
                        //云贷存管老协议
                        if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                            url = channel + "/regular/hfCustody_loan_agreement";
                        }
                    }else if(Constants.AGREEMENT_VERSION_NUMBER_1_1.equals(agreementVersionRes.getAgreementVersion())) {
                        //云贷存管新协议
                        if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                            url = channel + "/regular/hfCustody_loan_agreement_"+agreementVersionRes.getAgreementVersion();
                        }
                    }else if(Constants.AGREEMENT_VERSION_NUMBER_1_2.equals(agreementVersionRes.getAgreementVersion())) {
                        //云贷借款服务费率查询
                        ReqMsg_AgreementVersion_QueryLoanServiceRate loanServiceRateReq = new ReqMsg_AgreementVersion_QueryLoanServiceRate();
                        ResMsg_AgreementVersion_QueryLoanServiceRate loanServiceRateRes =
                                (ResMsg_AgreementVersion_QueryLoanServiceRate) siteService.handleMsg(loanServiceRateReq);
                        model.put("LoanServiceRate", loanServiceRateRes.getLoanServiceRate());

                        // 协议编号
                        if(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(resPartnerCode.getPartnerCode())) {
                            model.put("agreementNumberNew", "bgw-jkxy@"+resLoanInfo.getPartnerLoanId());
                        } else {
                            model.put("agreementNumberNew", resLoanInfo.getPartnerLoanId());
                        }

                        model.put("loanDueDateNew", DateUtil.formatDateTime(DateUtil.addDays(resLoanInfo.getLoanDay(),
                                (resLoanInfo.getTheTerm()*30 - 1)), "yyyy年MM月dd日"));

                        //合规二期存管云贷借款协议（协议三方签订）
                        if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                            url = channel + "/regular/hfCustody_loan_agreement_"+agreementVersionRes.getAgreementVersion();
                        }
                    }else if(Constants.AGREEMENT_VERSION_NUMBER_1_3.equals(agreementVersionRes.getAgreementVersion())) {
                        // 合规版借款协议，添加借款人地址和邮箱

                        //云贷借款服务费率查询
                        ReqMsg_AgreementVersion_QueryLoanServiceRate loanServiceRateReq = new ReqMsg_AgreementVersion_QueryLoanServiceRate();
                        ResMsg_AgreementVersion_QueryLoanServiceRate loanServiceRateRes =
                                (ResMsg_AgreementVersion_QueryLoanServiceRate) siteService.handleMsg(loanServiceRateReq);
                        model.put("LoanServiceRate", loanServiceRateRes.getLoanServiceRate());

                        // 协议编号
                        if(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(resPartnerCode.getPartnerCode())) {
                            model.put("agreementNumberNew", "bgw-jkxy@"+resLoanInfo.getPartnerLoanId());
                        }else {
                            model.put("agreementNumberNew", resLoanInfo.getPartnerLoanId());
                        }

                        model.put("loanDueDateNew", DateUtil.formatDateTime(DateUtil.addDays(resLoanInfo.getLoanDay(),
                                (resLoanInfo.getTheTerm()*30 - 1)), "yyyy年MM月dd日"));

                        //合规二期存管云贷借款协议（协议三方签订）
                        if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                            url = channel + "/regular/hfCustody_loan_agreement_"+agreementVersionRes.getAgreementVersion();
                        }
                    }
                }

                return url;

            }

        } catch (Exception e) {
            e.printStackTrace();
            String url = channel + "/regular/position_loan_error";
            if("micro2.0".equals(channel)) {
                model.put("agreementName", "借款协议");
                model.put("info", "借款协议");
                String link = GlobEnv.getWebURL("/micro2.0/index");
                WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                url = channel + "/agreement/agreement_error_page";
            }
            return url;
        }

    }

    /**
     * 债权转让协议（云贷）存管版 PC H5
     * @param channel
     * @param request
     * @param response
     * @param model
     * @param loanRelationId 债权关联关系id
     * @param subAccountId
     * @return
     */
    @RequestMapping("{channel}/agreement/hfCustodyClaimsAgreementInit")
    public String hfCustodyClaimsAgreementInit(@PathVariable String channel, String agreementType,
    									HttpServletRequest request, HttpServletResponse response, Map<String, Object> model,
                                        String loanRelationId, String subAccountId, String effectiveTime) {
        try {
            CookieManager cookieManager = new CookieManager(request);
            String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_USER_ID.getName(), true);
            ReqMsg_Match_CheckUserIdLoanRelationId reqRelation = new ReqMsg_Match_CheckUserIdLoanRelationId();
            reqRelation.setUserId(Integer.parseInt(userId));
            boolean flag = checkUserIdlLoanRelationId(Integer.parseInt(userId), Integer.parseInt(loanRelationId));
            if(!flag){
                String url = channel + "/regular/position_claims_error";
                if("micro2.0".equals(channel)) {
                    model.put("agreementName", "债权转让协议");
                    model.put("info", "债权转让协议");
                    String link = GlobEnv.getWebURL("/micro2.0/index");
                    WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                    url = channel + "/agreement/agreement_error_page";
                }
                return url;
            }else{

                String url = "";
                //赞时贷资金迁移协议 查询借款人的资产方
                ReqMsg_Match_CheckLnUserPartnerCode reqPartnerCode = new ReqMsg_Match_CheckLnUserPartnerCode();
                reqPartnerCode.setLoanRelationId(Integer.parseInt(loanRelationId));
                ResMsg_Match_CheckLnUserPartnerCode resPartnerCode = (ResMsg_Match_CheckLnUserPartnerCode)siteService.handleMsg(reqPartnerCode);
                if(Constants.PROPERTY_SYMBOL_ZSD.equals(resPartnerCode.getPartnerCode())) {
                    //赞时贷数据迁移时，已经匹配出去的债权明细的债权转让协议
                    ReqMsg_Match_GetCustodyClaimsTransferInfo reqClaims = new ReqMsg_Match_GetCustodyClaimsTransferInfo();
                    reqClaims.setLoanRelationId(Integer.parseInt(loanRelationId));
                    reqClaims.setSubAccountId(Integer.parseInt(subAccountId));

                    ResMsg_Match_GetCustodyClaimsTransferInfo resClaims = (ResMsg_Match_GetCustodyClaimsTransferInfo) siteService.handleMsg(reqClaims);
                    if (resClaims != null && !"".equals(resClaims)) {
                        //协议编号 格式26300000+借款编号
                        model.put("agreementNumber", "26300000" + loanRelationId);
                        //签订时间
                        if (resClaims.getTransferTime() != null) {
                            model.put("signingTime", DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));
                        } else {
                            model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
                        }
                        //债转状态
                        model.put("transStatus", resClaims.getTransStatus());
                        //债转标记
                        model.put("transMark", resClaims.getTransMark());

                        //出让人姓名
                        model.put("outUserName", resClaims.getOutUserName());
                        //出让人身份证
                        model.put("outUserIdCardNo", resClaims.getOutUserIdCardNo());
                        //受让人姓名
                        model.put("inUserName", resClaims.getInUserName());
                        //受让人身份证
                        model.put("inUserIdCardNo", resClaims.getInUserIdCardNo());

                        //借款人姓名
                        model.put("borrowUserName", resClaims.getBorrowUserName());
                        //借款人身份证
                        model.put("borrowUserIdCardNo", resClaims.getBorrowUserIdCardNo());
                        //还款期限（天）/ 借款期限
                        model.put("term", resClaims.getTerm() == null ? 0 : resClaims.getTerm());
                        //转让时间
                        model.put("transferTime", resClaims.getTransferTime() == null ? "" : DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));

                        //借款本金
                        model.put("approveAmount", resClaims.getApproveAmount());
                        //借款本金
                        model.put("approveAmount", resClaims.getApproveAmount());
                        //转让债权金额（人民币：元）= 债转本金 + 债转付息
                        model.put("transferCreditorAmount",  resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
                        //转让价格
                        model.put("transferPrice", resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
                    }

                    if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                        url = channel+"/regular/zsd_claims_agreement";
                    }

                }else {

                    ReqMsg_Match_GetCustodyClaimsTransferInfo reqClaims = new ReqMsg_Match_GetCustodyClaimsTransferInfo();
                    reqClaims.setLoanRelationId(Integer.parseInt(loanRelationId));
                    reqClaims.setSubAccountId(Integer.parseInt(subAccountId));

                    ResMsg_Match_GetCustodyClaimsTransferInfo resClaims = (ResMsg_Match_GetCustodyClaimsTransferInfo) siteService.handleMsg(reqClaims);
                    if (resClaims != null && !"".equals(resClaims)) {
                        //协议编号 格式26100000+借款编号
                        model.put("agreementNumber", "26100000" + loanRelationId);
                        //签订时间
                        if (resClaims.getTransferTime() != null) {
                            model.put("signingTime", DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));
                        } else {
                            model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
                        }
                        //债转状态
                        model.put("transStatus", resClaims.getTransStatus());
                        //债转标记
                        model.put("transMark", resClaims.getTransMark());

                        //出让人姓名
                        model.put("outUserName", resClaims.getOutUserName());
                        //出让人身份证
                        model.put("outUserIdCardNo", resClaims.getOutUserIdCardNo());
                        //受让人姓名
                        model.put("inUserName", resClaims.getInUserName());
                        //受让人身份证
                        model.put("inUserIdCardNo", resClaims.getInUserIdCardNo());

                        //借款人姓名
                        model.put("borrowUserName", resClaims.getBorrowUserName());
                        //借款人身份证
                        model.put("borrowUserIdCardNo", resClaims.getBorrowUserIdCardNo());
                        //还款期限（天）
                        model.put("term", resClaims.getTerm() == null ? 0 : resClaims.getTerm()*30);
                        //还款期限（期数）
                        model.put("repayTerm", resClaims.getTerm() == null ? 0 : resClaims.getTerm());
                        //转让时间
                        model.put("transferTime", resClaims.getTransferTime() == null ? "" : DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));

                        //借款本金
                        model.put("approveAmount", resClaims.getApproveAmount());
                        //借款本金
                        model.put("approveAmount", resClaims.getApproveAmount());
                        //转让债权金额（人民币：元）= 债转本金 + 债转付息
                        model.put("transferCreditorAmount",  resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
                        //转让价格
                        model.put("transferPrice", resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
                        //借款产品标识
                        model.put("partnerBusinessFlag", resClaims.getPartnerBusinessFlag());
                    }

                    
                    //查询云贷借款协议的版本号
                    ReqMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionReq = new ReqMsg_AgreementVersion_QueryAgreementVersionInfo();
                    agreementVersionReq.setAgreementType(agreementType);
                    agreementVersionReq.setAgreementEffectiveStartTime(effectiveTime);
                    ResMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionRes =
                            (ResMsg_AgreementVersion_QueryAgreementVersionInfo) siteService.handleMsg(agreementVersionReq);

                    if(Constants.AGREEMENT_VERSION_NUMBER_NO_VERSION.equals(agreementVersionRes.getAgreementVersion())) {
                        //云贷存管老协议
                        if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                        	url = channel+"/regular/hfCustody_claims_agreement";
                        }
                    }else if(Constants.AGREEMENT_VERSION_NUMBER_1_1.equals(agreementVersionRes.getAgreementVersion())) {
                        //云贷存管新协议
                        if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                            url = channel + "/regular/hfCustody_claims_agreement_"+agreementVersionRes.getAgreementVersion();
                        }
                    }else if(Constants.AGREEMENT_VERSION_NUMBER_1_2.equals(agreementVersionRes.getAgreementVersion())) {
                        // 云贷分期等额本息、等本等息借款产品，对应的债转协议，期限由天数改为期数
                        if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                            url = channel + "/regular/hfCustody_claims_agreement_"+agreementVersionRes.getAgreementVersion();
                        }
                    }

                }

                return url;
            }

        } catch (Exception e) {
            e.printStackTrace();
            String url = channel + "/regular/position_claims_error";
            if("micro2.0".equals(channel)) {
                model.put("agreementName", "债权转让协议");
                model.put("info", "债权转让协议");
                String link = GlobEnv.getWebURL("/micro2.0/index");
                WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                url = channel + "/agreement/agreement_error_page";
            }
            return url;
        }

    }

    /**
     * 债权转让协议（云贷）存管新版 PDF
     * @param channel
     * @param request
     * @param model
     * @param loanRelationId 债权关联关系id
     * @return
     */
    @RequestMapping("{channel}/agreement/hfCustodyClaimsAgreementInitPdf")
    public String hfCustodyClaimsAgreementInitPdf(@PathVariable String channel, HttpServletRequest request,
                                           Map<String, Object> model, String loanRelationId) {

        ReqMsg_Match_GetCustodyClaimsTransferInfoPdf reqClaims = new ReqMsg_Match_GetCustodyClaimsTransferInfoPdf();
        reqClaims.setLoanRelationId(Integer.valueOf(loanRelationId));

        ResMsg_Match_GetCustodyClaimsTransferInfoPdf resClaims = (ResMsg_Match_GetCustodyClaimsTransferInfoPdf) siteService.handleMsg(reqClaims);
        if (resClaims != null && !"".equals(resClaims)) {
            // 协议编号 格式26100000+借款编号
            model.put("agreementNumber", "26100000" + loanRelationId);
            //出让人姓名
            model.put("outUserName", resClaims.getOutUserName());
            //出让人身份证
            model.put("outUserIdCardNo", resClaims.getOutUserIdCardNo());
            //受让人姓名
            model.put("inUserName", resClaims.getInUserName());
            //受让人身份证
            model.put("inUserIdCardNo", resClaims.getInUserIdCardNo());

            //借款人姓名
            model.put("borrowUserName", resClaims.getBorrowUserName());
            //借款人身份证
            model.put("borrowUserIdCardNo", resClaims.getBorrowUserIdCardNo());
            //还款期限（天）
            model.put("term", "90天");

            //转让时间
            model.put("transferTime", resClaims.getTransferTime() == null ? "" : DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));

            //借款本金
            model.put("approveAmount", resClaims.getApproveAmount());
            //借款本金
            model.put("approveAmount", resClaims.getApproveAmount());
            //转让债权金额（人民币：元）= 债转本金 + 债转付息
            model.put("transferCreditorAmount",  resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
            //转让价格
            model.put("transferPrice", resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
        }
        return channel + "/regular/lateRepay_creditor_new_pdf";
    }

    /**
     * 云贷分期等额本息、等本等息借款产品，对应的债转协议 PDF
     * @param channel
     * @param request
     * @param model
     * @param loanRelationId 债权关联关系id
     * @return
     */
    @RequestMapping("{channel}/agreement/hfCustodyClaimsAgreementStagingProductPdf")
    public String hfCustodyClaimsAgreementStagingProductPdf(@PathVariable String channel, HttpServletRequest request,
                                                  Map<String, Object> model, String loanRelationId) {

        ReqMsg_Match_GetCustodyClaimsTransferInfoPdf reqClaims = new ReqMsg_Match_GetCustodyClaimsTransferInfoPdf();
        reqClaims.setLoanRelationId(Integer.valueOf(loanRelationId));

        ResMsg_Match_GetCustodyClaimsTransferInfoPdf resClaims = (ResMsg_Match_GetCustodyClaimsTransferInfoPdf) siteService.handleMsg(reqClaims);
        if (resClaims != null && !"".equals(resClaims)) {
            // 协议编号 格式26100000+借款编号
            model.put("agreementNumber", "26100000" + loanRelationId);
            //出让人姓名
            model.put("outUserName", resClaims.getOutUserName());
            //出让人身份证
            model.put("outUserIdCardNo", resClaims.getOutUserIdCardNo());
            //受让人姓名
            model.put("inUserName", resClaims.getInUserName());
            //受让人身份证
            model.put("inUserIdCardNo", resClaims.getInUserIdCardNo());

            //借款人姓名
            model.put("borrowUserName", resClaims.getBorrowUserName());
            //借款人身份证
            model.put("borrowUserIdCardNo", resClaims.getBorrowUserIdCardNo());
            //还款期限（期数）
            model.put("repayTerm", resClaims.getTerm() == null ? 0 : resClaims.getTerm());

            //转让时间
            model.put("transferTime", resClaims.getTransferTime() == null ? "" : DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));

            //借款本金
            model.put("approveAmount", resClaims.getApproveAmount());
            //借款本金
            model.put("approveAmount", resClaims.getApproveAmount());
            //转让债权金额（人民币：元）= 债转本金 + 债转付息
            model.put("transferCreditorAmount",  resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
            //转让价格
            model.put("transferPrice", resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
        }
        return channel + "/regular/lateRepay_creditor_stagingProduct_pdf";
    }

    /**
     * 债权转让协议（云贷）存管版 币港湾APP专享
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/hfCustodyClaimsAgreementApp")
    public String hfCustodyClaimsAgreementInitApp(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
                                                 Map<String, Object> model, String loanRelationId, String subAccountId) {
        ReqMsg_Match_GetCustodyClaimsTransferInfo reqClaims = new ReqMsg_Match_GetCustodyClaimsTransferInfo();
        reqClaims.setLoanRelationId(Integer.parseInt(loanRelationId));
        reqClaims.setSubAccountId(Integer.parseInt(subAccountId));

        ResMsg_Match_GetCustodyClaimsTransferInfo resClaims = (ResMsg_Match_GetCustodyClaimsTransferInfo) siteService.handleMsg(reqClaims);
        if (resClaims != null && !"".equals(resClaims)) {
            //协议编号 格式26100000+借款编号
            model.put("agreementNumber", "26100000" + loanRelationId);
            //签订时间
            if (resClaims.getTransferTime() != null) {
                model.put("signingTime", DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));
            } else {
                model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
            }
            //债转状态
            model.put("transStatus", resClaims.getTransStatus());
            //债转标记
            model.put("transMark", resClaims.getTransMark());

            //出让人姓名
            model.put("outUserName", resClaims.getOutUserName());
            //出让人身份证
            model.put("outUserIdCardNo", resClaims.getOutUserIdCardNo());
            //受让人姓名
            model.put("inUserName", resClaims.getInUserName());
            //受让人身份证
            model.put("inUserIdCardNo", resClaims.getInUserIdCardNo());

            //借款人姓名
            model.put("borrowUserName", resClaims.getBorrowUserName());
            //借款人身份证
            model.put("borrowUserIdCardNo", resClaims.getBorrowUserIdCardNo());
            //还款期限（天）
            model.put("term", resClaims.getTerm() == null ? 0 : resClaims.getTerm()*30);
            //转让时间
            model.put("transferTime", resClaims.getTransferTime() == null ? "" : DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));

            //借款本金
            model.put("approveAmount", resClaims.getApproveAmount());
            //转让债权金额（人民币：元）
            model.put("transferCreditorAmount",  resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
            //转让价格
            model.put("transferPrice", resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
        }
        return channel + "/agreement/hfCustody_claims_agreement_app";
    }

    /**
     * 注册账户服务协议
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/registAccountServiceAgreement")
    public String registAccountServiceAgreementInit(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
                                         Map<String, Object> model) {
        //app来源
        String client = request.getParameter("client");
        String userId = request.getParameter("userId");
        String url = "";
        if ("ios".equals(client) || "android".equals(client)) {
            model.put("clientType", client);

            if ("micro2.0".equals(channel)) {
                url = channel + "/agreement/regist_accountService_agreement_app";
            }
        } else { //微网
            if("micro2.0".equals(channel)) {
                String link = GlobEnv.getWebURL("/micro2.0/index");
                // 钱报的参数
                String qianbao = request.getParameter("qianbao");
                if (StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                    model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                    link += "?qianbao=qianbao";
                    CookieManager manager = new CookieManager(request);
                    String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
                    if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                        link += "&agentViewFlag=" + viewAgentFlagCookie;
                    }
                }
                Map<String, String> sign = weChatUtil.createAuth(request);
                sign.put("link", link);
                model.put("weichat", sign);
                url = channel + "/agreement/regist_accountService_agreement";
            }
        }
        return url;
    }


    //=====================  存管新增协议 end =====================


    //=====================  赞时贷产品新增协议 start =====================


    /**
     * 赞时贷借款协议 PC H5
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/zsdLoanAgreementInit")
    public String zsdLoanAgreementInit(@PathVariable String channel, HttpServletRequest request,
                                       HttpServletResponse response, Map<String, Object> model,
                                       String loanRelationId, String subAccountId) {
        try {
            CookieManager cookieManager = new CookieManager(request);
            String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_USER_ID.getName(), true);
            //判断借款协议loanRelationId是否属于当前登录的用户
            ReqMsg_Match_CheckUserIdLoanRelationId reqRelation = new ReqMsg_Match_CheckUserIdLoanRelationId();
            reqRelation.setUserId(Integer.parseInt(userId));
            boolean flag = checkUserIdlLoanRelationId(Integer.parseInt(userId), Integer.parseInt(loanRelationId));
            if(!flag){
                String url = channel + "/regular/position_loan_error";
                if("micro2.0".equals(channel)) {
                    model.put("agreementName", "借款协议");
                    model.put("info", "借款协议");
                    String link = GlobEnv.getWebURL("/micro2.0/index");
                    WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                    url = channel + "/agreement/agreement_error_page";
                }
                return url;
            }else{
                ReqMsg_Match_GetCustodyLoanInfo reqLoanInfo = new ReqMsg_Match_GetCustodyLoanInfo();
                reqLoanInfo.setLnLoanRelationId(Integer.parseInt(loanRelationId));
                reqLoanInfo.setSubAccountId(Integer.parseInt(subAccountId));
                ResMsg_Match_GetCustodyLoanInfo resLoanInfo =  (ResMsg_Match_GetCustodyLoanInfo) siteService.handleMsg(reqLoanInfo);
                if(resLoanInfo != null && !"".equals(resLoanInfo)) {
                    //协议编号  格式17200000+借款编号
                    model.put("agreementNumber", "17200000"+String.valueOf(resLoanInfo.getLoanId()));
                    //签订时间
                    if (resLoanInfo.getLoanDay() != null) {
                        model.put("signingTime", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                    }
                    //1、理财人信息
                    model.put("financialManagementData", resLoanInfo.getFinancialManagementList());
                    //2、出借金额总计
                    model.put("sumTotalAmount", resLoanInfo.getSumTotalAmount());
                    //3、借款信息
                    model.put("loanUserName", resLoanInfo.getLoanUserName());
                    model.put("loanIdCard", resLoanInfo.getLoanIdCard());
                    model.put("loanMobile", resLoanInfo.getLoanMobile());
                    //4、借款基本信息
                    model.put("loanAmount", resLoanInfo.getLoanAmount());
                    model.put("loanTerm", resLoanInfo.getLoanTerm()/30);
                    model.put("loanDay", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                    model.put("loanDueDate", DateUtil.formatDateTime(DateUtil.addDays(resLoanInfo.getLoanDay(), (resLoanInfo.getLoanTerm()/30 - 1)), "yyyy年MM月dd日"));
                    model.put("agreementRate", resLoanInfo.getAgreementRate()/365);
                    model.put("purpose", resLoanInfo.getPurpose());//借款用途
                }
                String url = "";
                if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                    url = channel + "/regular/zsd_loan_agreement";
                }else if("micro2.0".equals(channel)) {
                    String link = GlobEnv.getWebURL("/micro2.0/index");
                    // 钱报的参数
                    String qianbao = request.getParameter("qianbao");
                    if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                        model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                        link += "?qianbao=qianbao";
                        CookieManager manager = new CookieManager(request);
                        String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
                        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
                            link += "&agentViewFlag="+viewAgentFlagCookie;
                        }
                    }
                    Map<String,String> sign = weChatUtil.createAuth(request);
                    sign.put("link", link);
                    model.put("weichat", sign);
                    url = channel + "/agreement/zsd_loan_agreement";
                }
                return url;
            }

        } catch (Exception e) {
            e.printStackTrace();
            String url = channel + "/regular/position_loan_error";
            if("micro2.0".equals(channel)) {
                model.put("agreementName", "借款协议");
                model.put("info", "借款协议");
                String link = GlobEnv.getWebURL("/micro2.0/index");
                WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                url = channel + "/agreement/agreement_error_page";
            }
            return url;
        }
    }

    /**
     * 赞时贷借款协议 币港湾APP专享
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/zsdLoanAgreementApp")
    public String zsdLoanAgreementApp(@PathVariable String channel, HttpServletRequest request,
                                      HttpServletResponse response, Map<String, Object> model,
                                      String loanRelationId, String subAccountId) {
        ReqMsg_Match_GetCustodyLoanInfo reqLoanInfo = new ReqMsg_Match_GetCustodyLoanInfo();
        reqLoanInfo.setLnLoanRelationId(Integer.parseInt(loanRelationId));
        reqLoanInfo.setSubAccountId(Integer.parseInt(subAccountId));
        ResMsg_Match_GetCustodyLoanInfo resLoanInfo =  (ResMsg_Match_GetCustodyLoanInfo) siteService.handleMsg(reqLoanInfo);

        if(resLoanInfo != null && !"".equals(resLoanInfo)) {
            //协议编号  格式17200000+借款编号
            model.put("agreementNumber", "17200000"+String.valueOf(resLoanInfo.getLoanId()));
            //签订时间
            if (resLoanInfo.getLoanDay() != null) {
                model.put("signingTime", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
            } else {
                model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
            }
            //1、理财人信息
            model.put("financialManagementData", resLoanInfo.getFinancialManagementList());
            //2、出借金额总计
            model.put("sumTotalAmount", resLoanInfo.getSumTotalAmount());
            //3、借款信息
            model.put("loanUserName", resLoanInfo.getLoanUserName());
            model.put("loanIdCard", resLoanInfo.getLoanIdCard());
            model.put("loanMobile", resLoanInfo.getLoanMobile());
            //4、借款基本信息
            model.put("loanAmount", resLoanInfo.getLoanAmount());
            model.put("loanTerm", resLoanInfo.getLoanTerm()/30);
            model.put("loanDay", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
            model.put("loanDueDate", DateUtil.formatDateTime(DateUtil.addDays(resLoanInfo.getLoanDay(), (resLoanInfo.getLoanTerm()/30 - 1)), "yyyy年MM月dd日"));
            model.put("agreementRate", resLoanInfo.getAgreementRate()/365);
            model.put("purpose", resLoanInfo.getPurpose());//借款用途
        }
        return channel + "/agreement/zsd_loan_agreement_app";
    }

    /**
     * 赞时贷债权转让协议 PC H5
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/zsdClaimsAgreementInit")
    public String zsdClaimsAgreementInit(@PathVariable String channel, HttpServletRequest request,
                                         HttpServletResponse response, Map<String, Object> model,
                                         String loanRelationId, String subAccountId) {
        try {
            CookieManager cookieManager = new CookieManager(request);
            String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_USER_ID.getName(), true);
            ReqMsg_Match_CheckUserIdLoanRelationId reqRelation = new ReqMsg_Match_CheckUserIdLoanRelationId();
            reqRelation.setUserId(Integer.parseInt(userId));
            boolean flag = checkUserIdlLoanRelationId(Integer.parseInt(userId), Integer.parseInt(loanRelationId));
            if(!flag){
                String url = channel + "/regular/position_claims_error";
                if("micro2.0".equals(channel)) {
                    model.put("agreementName", "债权转让协议");
                    model.put("info", "债权转让协议");
                    String link = GlobEnv.getWebURL("/micro2.0/index");
                    WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                    url = channel + "/agreement/agreement_error_page";
                }
                return url;
            }else{
                ReqMsg_Match_GetCustodyClaimsTransferInfo reqClaims = new ReqMsg_Match_GetCustodyClaimsTransferInfo();
                reqClaims.setLoanRelationId(Integer.parseInt(loanRelationId));
                reqClaims.setSubAccountId(Integer.parseInt(subAccountId));

                ResMsg_Match_GetCustodyClaimsTransferInfo resClaims = (ResMsg_Match_GetCustodyClaimsTransferInfo) siteService.handleMsg(reqClaims);
                if (resClaims != null && !"".equals(resClaims)) {
                    //协议编号 格式26300000+借款编号
                    model.put("agreementNumber", "26300000" + loanRelationId);
                    //签订时间
                    if (resClaims.getTransferTime() != null) {
                        model.put("signingTime", DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));
                    } else {
                        model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
                    }
                    //债转状态
                    model.put("transStatus", resClaims.getTransStatus());
                    //债转标记
                    model.put("transMark", resClaims.getTransMark());

                    //出让人姓名
                    model.put("outUserName", resClaims.getOutUserName());
                    //出让人身份证
                    model.put("outUserIdCardNo", resClaims.getOutUserIdCardNo());
                    //受让人姓名
                    model.put("inUserName", resClaims.getInUserName());
                    //受让人身份证
                    model.put("inUserIdCardNo", resClaims.getInUserIdCardNo());

                    //借款人姓名
                    model.put("borrowUserName", resClaims.getBorrowUserName());
                    //借款人身份证
                    model.put("borrowUserIdCardNo", resClaims.getBorrowUserIdCardNo());
                    //还款期限（天）/ 借款期限
                    model.put("term", resClaims.getTerm() == null ? 0 : resClaims.getTerm());
                    //转让时间
                    model.put("transferTime", resClaims.getTransferTime() == null ? "" : DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));

                    //借款本金
                    model.put("approveAmount", resClaims.getApproveAmount());
                    //借款本金
                    model.put("approveAmount", resClaims.getApproveAmount());
                    //转让债权金额（人民币：元）= 债转本金 + 债转付息
                    model.put("transferCreditorAmount",  resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
                    //转让价格
                    model.put("transferPrice", resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
                }

                String url = "";
                if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                    url = channel+"/regular/zsd_claims_agreement";
                }else if("micro2.0".equals(channel)) {
                    String link = GlobEnv.getWebURL("/micro2.0/index");
                    // 钱报的参数
                    String qianbao = request.getParameter("qianbao");
                    if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                        model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                        link += "?qianbao=qianbao";
                        CookieManager manager = new CookieManager(request);
                        String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
                        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
                            link += "&agentViewFlag="+viewAgentFlagCookie;
                        }
                    }
                    Map<String,String> sign = weChatUtil.createAuth(request);
                    sign.put("link", link);
                    model.put("weichat", sign);
                    url = channel + "/agreement/zsd_claims_agreement";
                }
                return url;
            }

        } catch (Exception e) {
            e.printStackTrace();
            String url = channel + "/regular/position_claims_error";
            if("micro2.0".equals(channel)) {
                model.put("agreementName", "债权转让协议");
                model.put("info", "债权转让协议");
                String link = GlobEnv.getWebURL("/micro2.0/index");
                WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
                url = channel + "/agreement/agreement_error_page";
            }
            return url;
        }
    }

    /**
     * 赞时贷借款咨询与服务协议 -- 用于pdf生成
     *
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/borrowingServicesZsd4Pdf")
    public String borrowingServicesZsd4Pdf(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        //合作方借款编号
        String loanId = request.getParameter("loanId");

        ReqMsg_Match_GetZsdUserLoanFeeList reqFee = new ReqMsg_Match_GetZsdUserLoanFeeList();
        reqFee.setPartnerLoanId(loanId);
        ResMsg_Match_GetZsdUserLoanFeeList resFee = (ResMsg_Match_GetZsdUserLoanFeeList) siteService.handleMsg(reqFee);
        //平台服务费
        model.put("platformServiceFee", resFee.getPlatformServiceFee()==null? 0d : resFee.getPlatformServiceFee());
        //账户管理费
        model.put("accountManageFee", resFee.getAccountManageFee()==null? 0d : resFee.getAccountManageFee());
        //风控服务费
        model.put("riskManageServiceFee", resFee.getRiskManageServiceFee()==null? 0d : resFee.getRiskManageServiceFee());
        //代收通道费
        model.put("collectionChannelFee", resFee.getCollectionChannelFee()==null? 0d : resFee.getCollectionChannelFee());
        //服务费 = 监管费+信息服务费+账户管理费+保费
        Double loadPremium = 0d;
        loadPremium = MoneyUtil.add((MoneyUtil.add(((resFee.getPlatformServiceFee() == null ? 0d : resFee.getPlatformServiceFee())), ((resFee.getAccountManageFee() == null ? 0d : resFee.getAccountManageFee()))).doubleValue()),
                (MoneyUtil.add(((resFee.getRiskManageServiceFee() == null ? 0d : resFee.getRiskManageServiceFee())), ((resFee.getCollectionChannelFee() == null ? 0d : resFee.getCollectionChannelFee()))).doubleValue())).doubleValue();
        model.put("loanServiceCharge", loadPremium);
        //借款人姓名
        model.put("userName", resFee.getUserName());
        model.put("loanTime", DateUtil.formatDateTime(resFee.getLoanTime(), "yyyy年MM月dd日"));

        return channel + "/agreement/borrowing_services_zsd_pdf";
    }
    
    /**
     * 赞时贷借款协议
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/zsdLoanAgreement4Pdf")
    public String loanAgreement4ZsdPdf(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        //合作方借款编号
        String partnerLoanId = request.getParameter("partnerLoanId");
        
        ReqMsg_Match_GetZsdAppUserLoanInfo zsdAppUserLoanInfo = new ReqMsg_Match_GetZsdAppUserLoanInfo();
        zsdAppUserLoanInfo.setPartnerLoanId(partnerLoanId);
        ResMsg_Match_GetZsdAppUserLoanInfo resZsdAppLoanInfo = (ResMsg_Match_GetZsdAppUserLoanInfo) siteService.handleMsg(zsdAppUserLoanInfo);
        if (resZsdAppLoanInfo != null && !"".equals(resZsdAppLoanInfo)) {
            //协议编号  格式17200000+借款编号
            model.put("agreementNumber", "17200000" + String.valueOf(resZsdAppLoanInfo.getId()));
            //签订时间
            model.put("signingTime", DateUtil.formatDateTime(resZsdAppLoanInfo.getLoanTime(), "yyyy年MM月dd日"));

            model.put("BGWAddress", "杭州市江干区四季青街道西子国际大厦C座2305");
            model.put("BGWTel", "400-806-1230");
            model.put("ZANAddress", "杭州市江干区四季青街道西子国际大厦C座2304");
            model.put("ZANTel", "400-696-5858");
            //出借人列表
            model.put("dataUserInfo", resZsdAppLoanInfo.getDataUserInfo());
            //借款人姓名
            model.put("lnUserName", resZsdAppLoanInfo.getLnUserName());
            //借款人身份证号
            model.put("lnIdCard", resZsdAppLoanInfo.getLnUserIdCard());
            //借款人赞时贷账户
            model.put("lnUserZSDAccount", resZsdAppLoanInfo.getLnUserZsdAccount());
            //借款成功时间
            model.put("loanTime", DateUtil.formatDateTime(resZsdAppLoanInfo.getLoanTime(), "yyyy年MM月dd日"));
            //借款用途
            model.put("purpose", resZsdAppLoanInfo.getPurpose());
            //借款本金数额
            model.put("approveAmount", resZsdAppLoanInfo.getApproveAmount());
            //借款日利率
            model.put("lnRate",  resZsdAppLoanInfo.getAgreementRate()/365);
            //借款期限
            model.put("period", resZsdAppLoanInfo.getPeriod());   
            model.put("loanDay", DateUtil.formatDateTime(resZsdAppLoanInfo.getLoanTime(), "yyyy年MM月dd日"));
            model.put("loanDueDate", DateUtil.formatDateTime(DateUtil.addDays(resZsdAppLoanInfo.getLoanTime(),
                    (resZsdAppLoanInfo.getPeriod() - 1)), "yyyy年MM月dd日"));
        }

        String url = "";
        if ("gen2.0".equals(channel) || "gen178".equals(channel)) {
            url = channel + "/regular/loan_agreement_zsd_pdf";
        } else if ("micro2.0".equals(channel)) {
            String link = GlobEnv.getWebURL("/micro2.0/index");
            // 钱报的参数
            String qianbao = request.getParameter("qianbao");
            if (StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                link += "?qianbao=qianbao";
                CookieManager manager = new CookieManager(request);
                String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
                if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                    link += "&agentViewFlag=" + viewAgentFlagCookie;
                }
            }
            Map<String, String> sign = weChatUtil.createAuth(request);
            sign.put("link", link);
            model.put("weichat", sign);
            url = channel + "/regular/loan_agreement_zsd_pdf";
        }
        return url;
    }
    
    
    /**
     * 赞时贷债权转让协议 币港湾APP专享
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/zsdClaimsAgreementApp")
    public String zsdClaimsAgreementInitApp(@PathVariable String channel, HttpServletRequest request,
                                            HttpServletResponse response, Map<String, Object> model,
                                            String loanRelationId, String subAccountId) {
        ReqMsg_Match_GetCustodyClaimsTransferInfo reqClaims = new ReqMsg_Match_GetCustodyClaimsTransferInfo();
        reqClaims.setLoanRelationId(Integer.parseInt(loanRelationId));
        reqClaims.setSubAccountId(Integer.parseInt(subAccountId));

        ResMsg_Match_GetCustodyClaimsTransferInfo resClaims = (ResMsg_Match_GetCustodyClaimsTransferInfo) siteService.handleMsg(reqClaims);
        if (resClaims != null && !"".equals(resClaims)) {
            //协议编号 格式26300000+借款编号
            model.put("agreementNumber", "26300000" + loanRelationId);
            //签订时间
            if (resClaims.getTransferTime() != null) {
                model.put("signingTime", DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));
            } else {
                model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
            }
            //债转状态
            model.put("transStatus", resClaims.getTransStatus());
            //债转标记
            model.put("transMark", resClaims.getTransMark());

            //出让人姓名
            model.put("outUserName", resClaims.getOutUserName());
            //出让人身份证
            model.put("outUserIdCardNo", resClaims.getOutUserIdCardNo());
            //受让人姓名
            model.put("inUserName", resClaims.getInUserName());
            //受让人身份证
            model.put("inUserIdCardNo", resClaims.getInUserIdCardNo());

            //借款人姓名
            model.put("borrowUserName", resClaims.getBorrowUserName());
            //借款人身份证
            model.put("borrowUserIdCardNo", resClaims.getBorrowUserIdCardNo());
            //还款期限（天）/ 借款期限
            model.put("term", resClaims.getTerm() == null ? 0 : resClaims.getTerm());
            //转让时间
            model.put("transferTime", resClaims.getTransferTime() == null ? "" : DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));

            //借款本金
            model.put("approveAmount", resClaims.getApproveAmount());
            //转让债权金额（人民币：元）
            model.put("transferCreditorAmount",  resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
            //转让价格
            model.put("transferPrice", resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
        }
        return channel + "/agreement/zsd_claims_agreement_app";
    }


    // -------------------- 赞时贷APP start --------------------

    /**
     * （1）、借款协议 赞时贷APP专用
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/zsdAppLoanAgreement")
    public String loanAgreementZanAppZsd(@PathVariable String channel, HttpServletRequest request,
                                         HttpServletResponse response, Map<String, Object> model) {
        //app来源
        String type = request.getParameter("type");
        model.put("type",type);
        //合作方借款编号
        String partnerLoanId = request.getParameter("partnerLoanId");
        String url = "";
        ReqMsg_Match_GetZsdAppUserLoanInfo reqZsdAppLoanInfo = new ReqMsg_Match_GetZsdAppUserLoanInfo();
        reqZsdAppLoanInfo.setPartnerLoanId(partnerLoanId);
        ResMsg_Match_GetZsdAppUserLoanInfo resZsdAppLoanInfo =
                (ResMsg_Match_GetZsdAppUserLoanInfo) siteService.handleMsg(reqZsdAppLoanInfo);
        if (resZsdAppLoanInfo != null && !"".equals(resZsdAppLoanInfo)) {
            //协议编号  格式17200000+借款编号
            model.put("agreementNumber", "17200000"+String.valueOf(resZsdAppLoanInfo.getId()));
            //签订时间
            if (resZsdAppLoanInfo.getLoanTime() != null) {
                model.put("signingTime", DateUtil.formatDateTime(resZsdAppLoanInfo.getLoanTime(), "yyyy年MM月dd日"));
            } else {
                model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
            }
            //1、理财人信息
            model.put("financialManagementData", resZsdAppLoanInfo.getDataUserInfo());
            //2、借款信息
            model.put("loanUserName", resZsdAppLoanInfo.getLnUserName());
            model.put("loanIdCard", resZsdAppLoanInfo.getLnUserIdCard());
            model.put("loanMobile", resZsdAppLoanInfo.getLnUserZsdAccount());
            //3、借款基本信息
            model.put("loanAmount", resZsdAppLoanInfo.getApproveAmount());
            model.put("loanTerm", resZsdAppLoanInfo.getPeriod());
            model.put("loanDay", DateUtil.formatDateTime(resZsdAppLoanInfo.getLoanTime(), "yyyy年MM月dd日"));
            model.put("loanDueDate", DateUtil.formatDateTime(DateUtil.addDays(resZsdAppLoanInfo.getLoanTime(),
                    (resZsdAppLoanInfo.getPeriod() - 1)), "yyyy年MM月dd日"));
            model.put("agreementRate", resZsdAppLoanInfo.getAgreementRate()/365);
            model.put("purpose", resZsdAppLoanInfo.getPurpose());//借款用途
        }
        url = channel + "/agreement/loan_agreement_zsdAppClient";

        return url;
    }

    /**
     * （2）、借款协议 赞时贷APP专用 无数据页面
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/agreement/zsdAppLoanAgreementPreview")
    public String loanAgreementZanAppNoDataZsd(@PathVariable String channel, HttpServletRequest request,
                                               HttpServletResponse response, Map<String, Object> model) {
        //app来源
        String type = request.getParameter("type");
        model.put("type", type);
        return channel + "/agreement/loan_agreement_zsdAppClientPreview";
    }



    /**
     * （3）赞时贷APP 借款咨询与服务协议
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/zsdAppBorrowingServices")
    public String borrowingServicesZanApp_zsd(@PathVariable String channel, HttpServletRequest request,
                                              HttpServletResponse response, Map<String, Object> model) {
        //app来源
        String type = request.getParameter("type");
        model.put("type", type);
        //合作方借款编号
        String partnerLoanId = request.getParameter("partnerLoanId");
        ReqMsg_Match_GetZsdUserLoanFeeList reqFee = new ReqMsg_Match_GetZsdUserLoanFeeList();
        reqFee.setPartnerLoanId(partnerLoanId);
        ResMsg_Match_GetZsdUserLoanFeeList resFee = (ResMsg_Match_GetZsdUserLoanFeeList) siteService.handleMsg(reqFee);
        //协议编号  格式17300000+借款编号
        model.put("agreementNumber", "17300000"+String.valueOf(resFee.getLoanId()));
        //平台服务费
        model.put("platformServiceFee", resFee.getPlatformServiceFee() == null ? 0d : resFee.getPlatformServiceFee());
        //账户管理费/信息认证费
        model.put("accountManageFee", resFee.getAccountManageFee() == null ? 0d : resFee.getAccountManageFee());
        //风控服务费
        model.put("riskManageServiceFee", resFee.getRiskManageServiceFee() == null ? 0d : resFee.getRiskManageServiceFee());
        //代收通道费
        model.put("collectionChannelFee", resFee.getCollectionChannelFee() == null ? 0d : resFee.getCollectionChannelFee());
        //服务费 = 平台服务费+信息认证费+风控服务费+代收通道费
        Double loadPremium = 0d;
        loadPremium = MoneyUtil.add(
                (MoneyUtil.add(((resFee.getPlatformServiceFee() == null ? 0d : resFee.getPlatformServiceFee())),
                        ((resFee.getAccountManageFee() == null ? 0d : resFee.getAccountManageFee()))).doubleValue()),
                (MoneyUtil.add(((resFee.getRiskManageServiceFee() == null ? 0d : resFee.getRiskManageServiceFee())),
                        ((resFee.getCollectionChannelFee() == null ? 0d : resFee.getCollectionChannelFee()))).doubleValue())
        ).doubleValue();
        model.put("loanServiceCharge", loadPremium);
        model.put("userName", resFee.getUserName());
        model.put("loanTime", DateUtil.formatDateTime(resFee.getLoanTime(), "yyyy年MM月dd日"));

        return channel + "/agreement/borrowing_services_zsdApp";
    }

    /**
     * （4）、赞时贷APP 借款咨询与服务协议无数据页面
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/agreement/zsdAppBorrowingServicesPreview")
    public String borrowingServicesZanAppNoDataZsd(@PathVariable String channel, HttpServletRequest request,
                                                   HttpServletResponse response, Map<String, Object> model) {
        //app来源
        String type = request.getParameter("type");
        model.put("type", type);
        return channel + "/agreement/borrowing_services_zsdAppPreview";
    }

    // -------------------- 赞时贷APP end --------------------


    //=====================  赞时贷产品新增协议 end =====================


    //=====================  2017-12-21 理财端协议统一入口 start =====================

    /**
     * 理财端协议入口
     * @param channel
     * @param agreementType 协议类型
     * @param request
     * @param response
     * @param model
     * @param loanRelationId
     * @param subAccountId
     * @param effectiveTime 协议生效时间
     * @return
     */
    @RequestMapping("{channel}/agreement/entrance")
    public String agreementEntranceIndex(@PathVariable String channel, String agreementType,
                                 HttpServletRequest request, HttpServletResponse response,
                                 Map<String, Object> model, String loanRelationId,
                                 String subAccountId, String effectiveTime) {
    	
        try {
            String url = "";
            CookieManager cookieManager = new CookieManager(request);
            String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_USER_ID.getName(), true);
            ReqMsg_Match_CheckUserIdLoanRelationId reqRelation = new ReqMsg_Match_CheckUserIdLoanRelationId();
            reqRelation.setUserId(Integer.parseInt(userId));
            boolean flag = checkUserIdlLoanRelationId(Integer.parseInt(userId), Integer.parseInt(loanRelationId));
            if(!flag){
                if(Constants.AGREEMENT_VERSION_TYPE_HF_7DAI_LOAN.equals(agreementType) ||
                        Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_LOAN.equals(agreementType) ||
                        Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_LOAN.equals(agreementType) ||
                        Constants.AGREEMENT_VERSION_TYPE_HF_7DAI_LOAN_ANYREPAY.equals(agreementType) ||
                        Constants.AGREEMENT_VERSION_TYPE_HF_YUNDAI_INSTALLMENT.equals(agreementType) ||
                        Constants.AGREEMENT_VERSION_TYPE_HF_YUNDAI_PRINCIPAL_INTEREST.equals(agreementType)) {
                    // 借款协议错误页面
                    url = channel + "/regular/position_loan_error";
                }else if(Constants.AGREEMENT_VERSION_TYPE_HF_7DAI_CLAIMS.equals(agreementType) ||
                        Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_CLAIMS.equals(agreementType) ||
                        Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_CLAIMS.equals(agreementType)) {
                    // 债转协议错误页面
                    url = channel + "/regular/position_claims_error";
                }
                return url;
            }else{
                if (StringUtil.isNotBlank(agreementType)) {
                    if(Constants.AGREEMENT_VERSION_TYPE_HF_7DAI_LOAN.equals(agreementType)) {
                        // 1、7贷借款协议-协议数据查询
                        ReqMsg_Match_GetCustodyLoanInfo reqLoanInfo = new ReqMsg_Match_GetCustodyLoanInfo();
                        reqLoanInfo.setLnLoanRelationId(Integer.parseInt(loanRelationId));
                        reqLoanInfo.setSubAccountId(Integer.parseInt(subAccountId));
                        //协议类型
                        reqLoanInfo.setAgreementType(agreementType);
                        ResMsg_Match_GetCustodyLoanInfo resLoanInfo =  (ResMsg_Match_GetCustodyLoanInfo) siteService.handleMsg(reqLoanInfo);
                        if(resLoanInfo != null && !"".equals(resLoanInfo)) {
                            // 协议编号  格式18100000+借款编号
                            model.put("agreementNumber", "18100000"+String.valueOf(resLoanInfo.getLoanId()));
                            // 合规二期存管七贷借款协议（协议三方签订）协议编号
                            model.put("agreementNumberNew", resLoanInfo.getPartnerLoanId());
                            // 签订时间
                            if (resLoanInfo.getLoanDay() != null) {
                                model.put("signingTime", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                            }
                            // a、理财人信息
                            model.put("financialManagementData", resLoanInfo.getFinancialManagementList());
                            // b、出借金额总计
                            model.put("sumTotalAmount", resLoanInfo.getSumTotalAmount());
                            // c、借款信息
                            model.put("loanUserName", resLoanInfo.getLoanUserName());
                            model.put("loanIdCard", resLoanInfo.getLoanIdCard());
                            model.put("loanMobile", resLoanInfo.getLoanMobile());
                            // d、借款基本信息
                            model.put("loanAmount", resLoanInfo.getLoanAmount());
                            model.put("loanTerm", resLoanInfo.getLoanTerm());
                            model.put("loanDay", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                            model.put("loanDueDate", resLoanInfo.getSevenLoanDueDate());
                            model.put("agreementRate", resLoanInfo.getAgreementRate());
                            model.put("purpose", resLoanInfo.getPurpose());//借款用途
                            model.put("theTerm", resLoanInfo.getTheTerm());

                            model.put("loanDueDateNew", DateUtil.formatDateTime(DateUtil.addDays(resLoanInfo.getLoanDay(),
                                    (resLoanInfo.getDayCount() - 1)), "yyyy年MM月dd日"));
                        	}  
                        } else if (Constants.AGREEMENT_VERSION_TYPE_HF_7DAI_LOAN_ANYREPAY.equals(agreementType)) {
                        	
                        	// 7贷随借随还产品借款协议-协议数据查询
                            ReqMsg_Match_GetCustodyLoanInfo reqLoanInfo = new ReqMsg_Match_GetCustodyLoanInfo();
                            reqLoanInfo.setLnLoanRelationId(Integer.parseInt(loanRelationId));
                            reqLoanInfo.setSubAccountId(Integer.parseInt(subAccountId));
                            //协议类型
                            reqLoanInfo.setAgreementType(agreementType);
                            ResMsg_Match_GetCustodyLoanInfo resLoanInfo =  (ResMsg_Match_GetCustodyLoanInfo) siteService.handleMsg(reqLoanInfo);
                            if(resLoanInfo != null && !"".equals(resLoanInfo)) {
                                // 协议编号  格式19100000+借款编号
                                model.put("agreementNumber", "19100000"+String.valueOf(resLoanInfo.getLoanId()));
                                // 合规二期存管七贷借款协议（协议三方签订）协议编号
                                model.put("agreementNumberNew", resLoanInfo.getPartnerLoanId());
                                // 签订时间
                                if (resLoanInfo.getLoanDay() != null) {
                                    model.put("signingTime", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                                }
                                // a、理财人信息
                                model.put("financialManagementData", resLoanInfo.getFinancialManagementList());
                                // b、出借金额总计
                                model.put("sumTotalAmount", resLoanInfo.getSumTotalAmount());
                                // c、借款信息
                                model.put("loanUserName", resLoanInfo.getLoanUserName());
                                model.put("loanIdCard", resLoanInfo.getLoanIdCard());
                                model.put("loanMobile", resLoanInfo.getLoanMobile());
                                // d、借款基本信息
                                model.put("loanAmount", resLoanInfo.getLoanAmount());
                                model.put("loanTerm", resLoanInfo.getLoanTerm());
                                model.put("loanDay", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                                model.put("loanDueDate", resLoanInfo.getSevenLoanDueDate());
                                model.put("agreementRate", resLoanInfo.getAgreementRate());
                                model.put("purpose", resLoanInfo.getPurpose());//借款用途
                                model.put("theTerm", resLoanInfo.getTheTerm());
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(resLoanInfo.getLoanDay());
                                cal.add(Calendar.DAY_OF_YEAR, resLoanInfo.getTheTerm() -1);
                                model.put("loanDueDateNew", DateUtil.formatDateTime(cal.getTime(), "yyyy年MM月dd日"));
                            }
                           
                    } else if(Constants.AGREEMENT_VERSION_TYPE_HF_7DAI_CLAIMS.equals(agreementType)) {
                        // 2、7贷债转协议-协议数据查询
                        ReqMsg_Match_GetCustodyClaimsTransferInfo reqClaims = new ReqMsg_Match_GetCustodyClaimsTransferInfo();
                        reqClaims.setLoanRelationId(Integer.parseInt(loanRelationId));
                        reqClaims.setSubAccountId(Integer.parseInt(subAccountId));

                        ResMsg_Match_GetCustodyClaimsTransferInfo resClaims = (ResMsg_Match_GetCustodyClaimsTransferInfo) siteService.handleMsg(reqClaims);
                        if (resClaims != null && !"".equals(resClaims)) {
                            // 协议编号 格式27100000+ 借贷关系编号
                            model.put("agreementNumber", "27100000" + loanRelationId);
                            // 签订时间
                            if (resClaims.getTransferTime() != null) {
                                model.put("signingTime", DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));
                            } else {
                                model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
                            }
                            // 债转状态
                            model.put("transStatus", resClaims.getTransStatus());
                            // 债转标记
                            model.put("transMark", resClaims.getTransMark());
                            // 出让人姓名
                            model.put("outUserName", resClaims.getOutUserName());
                            // 出让人身份证
                            model.put("outUserIdCardNo", resClaims.getOutUserIdCardNo());
                            // 受让人姓名
                            model.put("inUserName", resClaims.getInUserName());
                            // 受让人身份证
                            model.put("inUserIdCardNo", resClaims.getInUserIdCardNo());

                            // 借款人姓名
                            model.put("borrowUserName", resClaims.getBorrowUserName());
                            // 借款人身份证
                            model.put("borrowUserIdCardNo", resClaims.getBorrowUserIdCardNo());
                            // 还款期限（天）
                            model.put("term", resClaims.getTerm() == null ? 0 : resClaims.getTerm());
                            // 转让时间
                            model.put("transferTime", resClaims.getTransferTime() == null ? "" : DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));
                            // 借款本金
                            model.put("approveAmount", resClaims.getApproveAmount());
                            //借款本金
                            model.put("approveAmount", resClaims.getApproveAmount());
                            //转让债权金额（人民币：元）= 债转本金 + 债转付息
                            model.put("transferCreditorAmount",  resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
                            //转让价格
                            model.put("transferPrice", resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
                            // 借款表业务标识
                            model.put("partnerBusinessFlag", resClaims.getPartnerBusinessFlag()); 
                        }
                    }else if(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_LOAN.equals(agreementType)) {
                        // 3、存量数据七贷存管借款协议-协议数据查询
                        ReqMsg_Match_GetCustodyLoanInfo reqLoanInfo = new ReqMsg_Match_GetCustodyLoanInfo();
                        reqLoanInfo.setLnLoanRelationId(Integer.parseInt(loanRelationId));
                        reqLoanInfo.setSubAccountId(Integer.parseInt(subAccountId));
                        //协议类型
                        reqLoanInfo.setAgreementType(agreementType);
                        ResMsg_Match_GetCustodyLoanInfo resLoanInfo =  (ResMsg_Match_GetCustodyLoanInfo) siteService.handleMsg(reqLoanInfo);
                        if(resLoanInfo != null && !"".equals(resLoanInfo)) {
                            // 协议编号  格式18200000+借款编号
                            model.put("agreementNumber", "18200000"+String.valueOf(resLoanInfo.getLoanId()));
                            // 合规二期存管七贷借款协议（协议三方签订）协议编号
                            model.put("agreementNumberNew", resLoanInfo.getPartnerLoanId());
                            // 签订时间
                            if (resLoanInfo.getLoanDay() != null) {
                                model.put("signingTime", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                            }
                            // a、理财人信息
                            model.put("financialManagementData", resLoanInfo.getFinancialManagementList());
                            // b、出借金额总计
                            model.put("sumTotalAmount", resLoanInfo.getSumTotalAmount());
                            // c、借款信息
                            model.put("loanUserName", resLoanInfo.getLoanUserName());
                            model.put("loanIdCard", resLoanInfo.getLoanIdCard());
                            model.put("loanMobile", resLoanInfo.getLoanMobile());
                            // d、借款基本信息
                            model.put("loanAmount", resLoanInfo.getLoanAmount());
                            model.put("loanTerm", resLoanInfo.getLoanTerm());
                            model.put("loanDay", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                            model.put("loanDueDate", resLoanInfo.getSevenLoanDueDate());
                            model.put("agreementRate", resLoanInfo.getAgreementRate());
                            model.put("purpose", resLoanInfo.getPurpose());//借款用途
                            model.put("theTerm", resLoanInfo.getTheTerm());

                            model.put("loanDueDateNew", DateUtil.formatDateTime(DateUtil.addDays(resLoanInfo.getLoanDay(),
                                    (resLoanInfo.getDayCount() - 1)), "yyyy年MM月dd日"));
                        }
                    }else if(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_CLAIMS.equals(agreementType)) {
                        // 4、存量数据七贷存管债转协议-协议数据查询
                        ReqMsg_Match_GetCustodyClaimsTransferInfo reqClaims = new ReqMsg_Match_GetCustodyClaimsTransferInfo();
                        reqClaims.setLoanRelationId(Integer.parseInt(loanRelationId));
                        reqClaims.setSubAccountId(Integer.parseInt(subAccountId));
                        ResMsg_Match_GetCustodyClaimsTransferInfo resClaims = (ResMsg_Match_GetCustodyClaimsTransferInfo) siteService.handleMsg(reqClaims);
                        if (resClaims != null && !"".equals(resClaims)) {
                            // 协议编号 格式27200000+ 借贷关系编号
                            model.put("agreementNumber", "27200000" + loanRelationId);
                            // 签订时间
                            if (resClaims.getTransferTime() != null) {
                                model.put("signingTime", DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));
                            } else {
                                model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
                            }
                            // 债转状态
                            model.put("transStatus", resClaims.getTransStatus());
                            // 债转标记
                            model.put("transMark", resClaims.getTransMark());
                            // 出让人姓名
                            model.put("outUserName", resClaims.getOutUserName());
                            // 出让人身份证
                            model.put("outUserIdCardNo", resClaims.getOutUserIdCardNo());
                            // 受让人姓名
                            model.put("inUserName", resClaims.getInUserName());
                            // 受让人身份证
                            model.put("inUserIdCardNo", resClaims.getInUserIdCardNo());

                            // 借款人姓名
                            model.put("borrowUserName", resClaims.getBorrowUserName());
                            // 借款人身份证
                            model.put("borrowUserIdCardNo", resClaims.getBorrowUserIdCardNo());
                            // 还款期限（天）
                            model.put("term", resClaims.getTerm() == null ? 0 : resClaims.getTerm());
                            // 转让时间
                            model.put("transferTime", resClaims.getTransferTime() == null ? "" : DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));
                            // 借款本金
                            model.put("approveAmount", resClaims.getApproveAmount());
                            //借款本金
                            model.put("approveAmount", resClaims.getApproveAmount());
                            //转让债权金额（人民币：元）= 债转本金 + 债转付息
                            model.put("transferCreditorAmount",  resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
                            //转让价格
                            model.put("transferPrice", resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
                        }
                    }else if(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_LOAN.equals(agreementType)) {
                        // 5、存量数据云贷存管借款协议-协议数据查询
                        ReqMsg_Match_GetCustodyLoanInfo reqLoanInfo = new ReqMsg_Match_GetCustodyLoanInfo();
                        reqLoanInfo.setLnLoanRelationId(Integer.parseInt(loanRelationId));
                        reqLoanInfo.setSubAccountId(Integer.parseInt(subAccountId));
                        //协议类型
                        reqLoanInfo.setAgreementType(agreementType);
                        ResMsg_Match_GetCustodyLoanInfo resLoanInfo =  (ResMsg_Match_GetCustodyLoanInfo) siteService.handleMsg(reqLoanInfo);

                        if(resLoanInfo != null && !"".equals(resLoanInfo)) {
                            //协议编号  格式18300000+借款编号
                            model.put("agreementNumber", "18300000"+String.valueOf(resLoanInfo.getLoanId()));
                            
                            //合规二期存量数据云贷存管借款协议（协议三方签订）协议编号
                            if( Constants.YUN_DAI_SELF_FIXED_INSTALLMENT.equals(resLoanInfo.getPartnerBusinessFlag()) ) {
                        		model.put("agreementNumberNew", "bgwde-jkxy@"+resLoanInfo.getPartnerLoanId());
                        	} else if( Constants.YUN_DAI_SELF_FIXED_PRINCIPAL_INTEREST.equals(resLoanInfo.getPartnerBusinessFlag()) ) {
                        		model.put("agreementNumberNew", "bgwdb-jkxy@"+resLoanInfo.getPartnerLoanId());
                        	} else if( Constants.YUN_DAI_SELF_REPAY_ANY_TIME.equals(resLoanInfo.getPartnerBusinessFlag()) ) {
                        		model.put("agreementNumberNew", "bgw-jkxy@"+resLoanInfo.getPartnerLoanId());
                        	} else {
                        		model.put("agreementNumberNew", "bgw-jkxy@"+resLoanInfo.getPartnerLoanId());
                        	}
                            
                            //签订时间
                            if (resLoanInfo.getLoanDay() != null) {
                                model.put("signingTime", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                            }
                            //1、理财人信息
                            model.put("financialManagementData", resLoanInfo.getFinancialManagementList());
                            //2、出借金额总计
                            model.put("sumTotalAmount", resLoanInfo.getSumTotalAmount());
                            //3、借款信息
                            model.put("loanUserName", resLoanInfo.getLoanUserName());
                            model.put("loanIdCard", resLoanInfo.getLoanIdCard());
                            model.put("loanMobile", resLoanInfo.getLoanMobile());
                            //4、借款基本信息
                            model.put("loanAmount", resLoanInfo.getLoanAmount());
                            model.put("loanTerm", resLoanInfo.getLoanTerm());
                            model.put("loanDay", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                            model.put("loanDueDate", DateUtil.formatDateTime(resLoanInfo.getLoanDueDate(), "yyyy年MM月dd日"));
                            model.put("agreementRate", resLoanInfo.getAgreementRate());
                            model.put("purpose", resLoanInfo.getPurpose());
                            //5、债权转让记录
                            model.put("transferDataList", resLoanInfo.getTransferList());
                            //6、存管云贷借款协议新旧版本时间区分标志
                            model.put("yundaiLoanAgreementDateFlag", resLoanInfo.isYundaiLoanAgreementDateFlag());
                            model.put("theTerm", resLoanInfo.getTheTerm());

                            model.put("loanDueDateNew", DateUtil.formatDateTime(DateUtil.addDays(resLoanInfo.getLoanDay(),
                                    (resLoanInfo.getTheTerm()*30 - 1)), "yyyy年MM月dd日"));

                        }
                    }else if(Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_CLAIMS.equals(agreementType)) {
                        // 6、存量数据云贷存管债转协议-协议数据查询
                        ReqMsg_Match_GetCustodyClaimsTransferInfo reqClaims = new ReqMsg_Match_GetCustodyClaimsTransferInfo();
                        reqClaims.setLoanRelationId(Integer.parseInt(loanRelationId));
                        reqClaims.setSubAccountId(Integer.parseInt(subAccountId));

                        ResMsg_Match_GetCustodyClaimsTransferInfo resClaims = (ResMsg_Match_GetCustodyClaimsTransferInfo) siteService.handleMsg(reqClaims);
                        if (resClaims != null && !"".equals(resClaims)) {
                            //协议编号 格式27300000+借款编号
                            model.put("agreementNumber", "27300000" + loanRelationId);
                            //签订时间
                            if (resClaims.getTransferTime() != null) {
                                model.put("signingTime", DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));
                            } else {
                                model.put("beginTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));
                            }
                            //债转状态
                            model.put("transStatus", resClaims.getTransStatus());
                            //债转标记
                            model.put("transMark", resClaims.getTransMark());

                            //出让人姓名
                            model.put("outUserName", resClaims.getOutUserName());
                            //出让人身份证
                            model.put("outUserIdCardNo", resClaims.getOutUserIdCardNo());
                            //受让人姓名
                            model.put("inUserName", resClaims.getInUserName());
                            //受让人身份证
                            model.put("inUserIdCardNo", resClaims.getInUserIdCardNo());

                            //借款人姓名
                            model.put("borrowUserName", resClaims.getBorrowUserName());
                            //借款人身份证
                            model.put("borrowUserIdCardNo", resClaims.getBorrowUserIdCardNo());
                            //还款期限（天）
                            model.put("term", resClaims.getTerm() == null ? 0 : resClaims.getTerm()*30);
                            //转让时间
                            model.put("transferTime", resClaims.getTransferTime() == null ? "" : DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));

                            //借款本金
                            model.put("approveAmount", resClaims.getApproveAmount());
                            //借款本金
                            model.put("approveAmount", resClaims.getApproveAmount());
                            //转让债权金额（人民币：元）= 债转本金 + 债转付息
                            model.put("transferCreditorAmount",  resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
                            //转让价格
                            model.put("transferPrice", resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
                        }
                    }else if(Constants.AGREEMENT_VERSION_TYPE_HF_YUNDAI_INSTALLMENT.equals(agreementType)) {
                        // 存管云贷等额本息产品借款协议
                        ReqMsg_Match_GetCustodyLoanInfo reqLoanInfo = new ReqMsg_Match_GetCustodyLoanInfo();
                        reqLoanInfo.setLnLoanRelationId(Integer.parseInt(loanRelationId));
                        reqLoanInfo.setSubAccountId(Integer.parseInt(subAccountId));
                        //协议类型
                        reqLoanInfo.setAgreementType(agreementType);
                        ResMsg_Match_GetCustodyLoanInfo resLoanInfo =  (ResMsg_Match_GetCustodyLoanInfo) siteService.handleMsg(reqLoanInfo);
                        if(resLoanInfo != null && !"".equals(resLoanInfo)) {
                            //协议编号
                            model.put("agreementNumberNew", "bgw-jkxyde@"+resLoanInfo.getPartnerLoanId());
                            //签订时间
                            if (resLoanInfo.getLoanDay() != null) {
                                model.put("signingTime", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                            }
                            //1、理财人信息
                            model.put("financialManagementData", resLoanInfo.getFinancialManagementList());
                            //2、出借金额总计
                            model.put("sumTotalAmount", resLoanInfo.getSumTotalAmount());
                            //3、借款信息
                            model.put("loanUserName", resLoanInfo.getLoanUserName());
                            model.put("loanIdCard", resLoanInfo.getLoanIdCard());
                            model.put("loanMobile", resLoanInfo.getLoanMobile());
                            model.put("loanAddress", resLoanInfo.getAddress());
                            model.put("loanEmail", resLoanInfo.getEmail());
                            //4、借款基本信息
                            model.put("loanTerm", resLoanInfo.getTheTerm());
                            model.put("loanDueDateNew", resLoanInfo.getSevenLoanDueDate());// 借款到期日
                            model.put("loanAmount", resLoanInfo.getLoanAmount());
                            model.put("loanDay", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                            model.put("agreementRate", resLoanInfo.getAgreementRate());
                            model.put("purpose", resLoanInfo.getPurpose());
                            //分期产品借款服务费
                            model.put("fixLoanServiceRate", resLoanInfo.getFixLoanServiceRate());
                        }
                    }else if(Constants.AGREEMENT_VERSION_TYPE_HF_YUNDAI_PRINCIPAL_INTEREST.equals(agreementType)) {
                        // 存管云贷等本等息产品借款协议
                        ReqMsg_Match_GetCustodyLoanInfo reqLoanInfo = new ReqMsg_Match_GetCustodyLoanInfo();
                        reqLoanInfo.setLnLoanRelationId(Integer.parseInt(loanRelationId));
                        reqLoanInfo.setSubAccountId(Integer.parseInt(subAccountId));
                        //协议类型
                        reqLoanInfo.setAgreementType(agreementType);
                        ResMsg_Match_GetCustodyLoanInfo resLoanInfo =  (ResMsg_Match_GetCustodyLoanInfo) siteService.handleMsg(reqLoanInfo);
                        if(resLoanInfo != null && !"".equals(resLoanInfo)) {
                            //协议编号
                            model.put("agreementNumberNew", "bgw-jkxydb@"+resLoanInfo.getPartnerLoanId());
                            //签订时间
                            if (resLoanInfo.getLoanDay() != null) {
                                model.put("signingTime", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                            }
                            //1、理财人信息
                            model.put("financialManagementData", resLoanInfo.getFinancialManagementList());
                            //2、出借金额总计
                            model.put("sumTotalAmount", resLoanInfo.getSumTotalAmount());
                            //3、借款信息
                            model.put("loanUserName", resLoanInfo.getLoanUserName());
                            model.put("loanIdCard", resLoanInfo.getLoanIdCard());
                            model.put("loanMobile", resLoanInfo.getLoanMobile());
                            model.put("loanAddress", resLoanInfo.getAddress());
                            model.put("loanEmail", resLoanInfo.getEmail());
                            //4、借款基本信息
                            model.put("loanTerm", resLoanInfo.getTheTerm());
                            model.put("loanDueDateNew", resLoanInfo.getSevenLoanDueDate()); // 借款到期日
                            model.put("loanAmount", resLoanInfo.getLoanAmount());
                            model.put("loanDay", DateUtil.formatDateTime(resLoanInfo.getLoanDay(), "yyyy年MM月dd日"));
                            model.put("agreementRate", resLoanInfo.getAgreementRate());
                            model.put("purpose", resLoanInfo.getPurpose());
                            //分期产品借款服务费
                            model.put("fixLoanServiceRate", resLoanInfo.getFixLoanServiceRate());
                        }
                    }
                }

                //查询协议的版本号
                ReqMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionReq = new ReqMsg_AgreementVersion_QueryAgreementVersionInfo();
                agreementVersionReq.setAgreementType(agreementType);
                agreementVersionReq.setAgreementEffectiveStartTime(effectiveTime);
                ResMsg_AgreementVersion_QueryAgreementVersionInfo agreementVersionRes =
                        (ResMsg_AgreementVersion_QueryAgreementVersionInfo) siteService.handleMsg(agreementVersionReq);

                if(Constants.AGREEMENT_VERSION_NUMBER_1_1.equals(agreementVersionRes.getAgreementVersion())) {
                    if(Constants.AGREEMENT_VERSION_TYPE_HF_YUNDAI_INSTALLMENT.equals(agreementType) ||
                            Constants.AGREEMENT_VERSION_TYPE_HF_YUNDAI_PRINCIPAL_INTEREST.equals(agreementType)) {
                        //云贷借款服务费率查询
                        ReqMsg_AgreementVersion_QueryLoanServiceRate loanServiceRateReq = new ReqMsg_AgreementVersion_QueryLoanServiceRate();
                        ResMsg_AgreementVersion_QueryLoanServiceRate loanServiceRateRes =
                                (ResMsg_AgreementVersion_QueryLoanServiceRate) siteService.handleMsg(loanServiceRateReq);
                        model.put("loanServiceRate", loanServiceRateRes.getLoanServiceRate());
                    }
                    //云贷存管新协议
                    if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                        url = channel + "/regular/"+agreementType+"_"+agreementVersionRes.getAgreementVersion();
                    }
                    
                    if (Constants.AGREEMENT_VERSION_TYPE_HF_7DAI_LOAN_ANYREPAY.equals(agreementType)) {
                    	//7贷借款服务费率查询
                        ReqMsg_AgreementVersion_QuerySevenLoanServiceRate sevenLoanServiceRateReq = new ReqMsg_AgreementVersion_QuerySevenLoanServiceRate();
                        ResMsg_AgreementVersion_QuerySevenLoanServiceRate sevenLoanServiceRateRes =
                                (ResMsg_AgreementVersion_QuerySevenLoanServiceRate) siteService.handleMsg(sevenLoanServiceRateReq);
                        model.put("LoanServiceRate", sevenLoanServiceRateRes.getSevenLoanServiceRate());
                    }
                } else if (Constants.AGREEMENT_VERSION_NUMBER_1_2.equals(agreementVersionRes.getAgreementVersion())) {

                    if(Constants.AGREEMENT_VERSION_TYPE_HF_7DAI_LOAN.equals(agreementType) ||
                            Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_LOAN.equals(agreementType)) {
                        //7贷借款服务费率查询
                        ReqMsg_AgreementVersion_QuerySevenLoanServiceRate sevenLoanServiceRateReq = new ReqMsg_AgreementVersion_QuerySevenLoanServiceRate();
                        ResMsg_AgreementVersion_QuerySevenLoanServiceRate sevenLoanServiceRateRes =
                                (ResMsg_AgreementVersion_QuerySevenLoanServiceRate) siteService.handleMsg(sevenLoanServiceRateReq);
                        model.put("LoanServiceRate", sevenLoanServiceRateRes.getSevenLoanServiceRate());
                    }else {
                        //云贷借款服务费率查询
                        ReqMsg_AgreementVersion_QueryLoanServiceRate loanServiceRateReq = new ReqMsg_AgreementVersion_QueryLoanServiceRate();
                        ResMsg_AgreementVersion_QueryLoanServiceRate loanServiceRateRes =
                                (ResMsg_AgreementVersion_QueryLoanServiceRate) siteService.handleMsg(loanServiceRateReq);
                        model.put("LoanServiceRate", loanServiceRateRes.getLoanServiceRate());
                    }

                	//合规二期新协议
                    if("gen2.0".equals(channel) || "gen178".equals(channel)) {
                        url = channel + "/regular/"+agreementType+"_"+agreementVersionRes.getAgreementVersion();
                    }
                } else {
                	if(Constants.AGREEMENT_VERSION_TYPE_HF_7DAI_LOAN.equals(agreementType) ||
                            Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_LOAN.equals(agreementType) ||
                            Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_LOAN.equals(agreementType) ||
                            Constants.AGREEMENT_VERSION_TYPE_HF_7DAI_LOAN_ANYREPAY.equals(agreementType) ||
                            Constants.AGREEMENT_VERSION_TYPE_HF_YUNDAI_INSTALLMENT.equals(agreementType) ||
                            Constants.AGREEMENT_VERSION_TYPE_HF_YUNDAI_PRINCIPAL_INTEREST.equals(agreementType)) {

                        url = channel + "/regular/position_loan_error";
                    }else if(Constants.AGREEMENT_VERSION_TYPE_HF_7DAI_CLAIMS.equals(agreementType) ||
                            Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_CLAIMS.equals(agreementType) ||
                            Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_CLAIMS.equals(agreementType)) {
                        //债转协议错误页面
                        url = channel + "/regular/position_claims_error";
                    }
                }
                return url;
            }

        } catch (Exception e) {
            e.printStackTrace();
            String url = "";
            if(Constants.AGREEMENT_VERSION_TYPE_HF_7DAI_LOAN.equals(agreementType) ||
                    Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_LOAN.equals(agreementType) ||
                    Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_LOAN.equals(agreementType) ||
                    Constants.AGREEMENT_VERSION_TYPE_HF_7DAI_LOAN_ANYREPAY.equals(agreementType) ||
                    Constants.AGREEMENT_VERSION_TYPE_HF_YUNDAI_INSTALLMENT.equals(agreementType) ||
                    Constants.AGREEMENT_VERSION_TYPE_HF_YUNDAI_PRINCIPAL_INTEREST.equals(agreementType)) {
                //借款协议错误页面
                url = channel + "/regular/position_loan_error";
            }else if(Constants.AGREEMENT_VERSION_TYPE_HF_7DAI_CLAIMS.equals(agreementType) ||
                    Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_7DAI_CLAIMS.equals(agreementType) ||
                    Constants.AGREEMENT_VERSION_TYPE_STOCK_HF_YUNDAI_CLAIMS.equals(agreementType)) {
                //债转协议错误页面
                url = channel + "/regular/position_claims_error";
            }
            return url;
        }
    }

    /**
     * 债权转让协议（七贷）存管新版 PDF
     * @param channel
     * @param model
     * @param loanRelationId
     * @return
     */
    @RequestMapping("{channel}/agreement/entrancePdf")
    public String entrancePdf(@PathVariable String channel, Map<String, Object> model, String loanRelationId) {

        // 7贷债转协议-协议数据查询
        ReqMsg_Match_GetCustodyClaimsTransferInfoPdf reqClaims = new ReqMsg_Match_GetCustodyClaimsTransferInfoPdf();
        reqClaims.setLoanRelationId(Integer.parseInt(loanRelationId));

        ResMsg_Match_GetCustodyClaimsTransferInfoPdf resClaims = (ResMsg_Match_GetCustodyClaimsTransferInfoPdf) siteService.handleMsg(reqClaims);
        if (resClaims != null && !"".equals(resClaims)) {
            // 协议编号 格式27100000+ 借贷关系编号
            model.put("agreementNumber", "27100000" + loanRelationId);
            // 债转状态
            model.put("transStatus", resClaims.getTransStatus());
            // 债转标记
            model.put("transMark", resClaims.getTransMark());
            // 出让人姓名
            model.put("outUserName", resClaims.getOutUserName());
            // 出让人身份证
            model.put("outUserIdCardNo", resClaims.getOutUserIdCardNo());
            // 受让人姓名
            model.put("inUserName", resClaims.getInUserName());
            // 受让人身份证
            model.put("inUserIdCardNo", resClaims.getInUserIdCardNo());
            // 借款人姓名
            model.put("borrowUserName", resClaims.getBorrowUserName());
            // 借款人身份证
            model.put("borrowUserIdCardNo", resClaims.getBorrowUserIdCardNo());
            // 随借随还产品（天）/ 先息后本产品（期）
            if ("REPAY_ANY_TIME".equals(resClaims.getPartnerBusinessFlag())) {
                model.put("term", resClaims.getTerm() == null ? 0 : resClaims.getTerm() + "日");
            } else {
                model.put("term", resClaims.getTerm() == null ? 0 : resClaims.getTerm() + "期");
            }
            // 转让时间
            model.put("transferTime", resClaims.getTransferTime() == null ? "" : DateUtil.formatDateTime(resClaims.getTransferTime(), "yyyy年MM月dd日"));
            //借款本金
            model.put("approveAmount", resClaims.getApproveAmount());
            //转让债权金额（人民币：元）= 债转本金 + 债转付息
            model.put("transferCreditorAmount",  resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
            //转让价格
            model.put("transferPrice", resClaims.getInAmount() == null ? 0d : resClaims.getInAmount());
        }
        return channel + "/regular/lateRepay_creditor_new_pdf";
    }

    //=====================  2017-12-21 理财端协议统一入口 end =====================


    //===================== 合规改造 start =====================

    /**
     * 合规改造 风险提示页面
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/riskWarningInfo")
    public String riskWarningInfo(@PathVariable String channel, HttpServletRequest request,
                                  HttpServletResponse response, Map<String, Object> model) {
        //购买页面需要的参数
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String rate = request.getParameter("rate");
        String term = request.getParameter("term");
        String minInvestAmount = request.getParameter("minInvestAmount");
        String balance = request.getParameter("balance");
        String regMobile = request.getParameter("regMobile");
        String alreadyUse = request.getParameter("alreadyUse");
        String token = request.getParameter("token");
        String redPacketId = request.getParameter("redPacketId");
        String useFlag = request.getParameter("useFlag");
        String amount_pre = request.getParameter("amount_pre");
        String proLimitAmout = request.getParameter("proLimitAmout");
        String maxSingleInvestAmount = request.getParameter("maxSingleInvestAmount");
        String isSupportRedPacket = request.getParameter("isSupportRedPacket");
        String isBindCard = request.getParameter("isBindCard");
        String propertySymbol = request.getParameter("propertySymbol");
        String agentViewFlag = request.getParameter("agentViewFlag");
        //购买页面需要的参数，返回到页面
        model.put("id", id);
        model.put("name", name);
        model.put("rate", rate);
        model.put("term", term);
        model.put("minInvestAmount", minInvestAmount);
        model.put("balance", balance);
        model.put("regMobile", regMobile);
        model.put("alreadyUse", alreadyUse);
        model.put("redPacketId", redPacketId);
        model.put("useFlag", useFlag);
        model.put("amount_pre", amount_pre);
        model.put("proLimitAmout", proLimitAmout);
        model.put("maxSingleInvestAmount", maxSingleInvestAmount);
        model.put("isSupportRedPacket", isSupportRedPacket);
        model.put("isBindCard", isBindCard);
        model.put("propertySymbol", propertySymbol);
        model.put("from", request.getParameter("from"));
        String link = GlobEnv.getWebURL("/micro2.0/index");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if (StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
            if (StringUtil.isNotBlank(viewAgentFlagCookie)) {
                link += "&agentViewFlag=" + viewAgentFlagCookie;
            }
        }
        // 分享
        Map<String, String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        String url = "";
        //app来源
        String type = request.getParameter("type");
        if ("ios".equals(type) || "android".equals(type)) {
            url =  channel + "/agreement/risk_warning_app";
        }else { // h5风险提示页面
            url =  channel + "/agreement/risk_warning";
        }
        return url;
    }

    //===================== 合规改造 end   =====================

    //===================== 云贷、七贷三方借款协议  start =================

    /**
     * 云贷三方借款协议
     *
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/loanAgreementYunPdf")
    public String loanAgreementYunPdf(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        //合作方借款编号
        String partnerLoanId = request.getParameter("partnerLoanId");

        String loanTime = request.getParameter("loanTime");

        ReqMsg_Match_GetYunUserLoanInfo yunUserLoanInfo = new ReqMsg_Match_GetYunUserLoanInfo();
        yunUserLoanInfo.setPartnerLoanId(partnerLoanId);
        if (StringUtils.isNotBlank(loanTime)) {
            yunUserLoanInfo.setLoanTime(DateUtil.parse(loanTime, DateUtil.DATE_FORMAT + DateUtil.HHMMSS_FORMAT));
        }
        ResMsg_Match_GetYunUserLoanInfo resYunLoanInfo = (ResMsg_Match_GetYunUserLoanInfo) siteService.handleMsg(yunUserLoanInfo);
        if (resYunLoanInfo != null && !"".equals(resYunLoanInfo)) {
        	//协议编号  格式bgw-jkxy@+合作方借款id
        	
        	if( Constants.YUN_DAI_SELF_FIXED_INSTALLMENT.equals(resYunLoanInfo.getPartnerBusinessFlag()) ) {
        		model.put("agreementNumber", "bgwde-jkxy@"+resYunLoanInfo.getPartnerLoanId());
        	} else if( Constants.YUN_DAI_SELF_FIXED_PRINCIPAL_INTEREST.equals(resYunLoanInfo.getPartnerBusinessFlag()) ) {
        		model.put("agreementNumber", "bgwdb-jkxy@"+resYunLoanInfo.getPartnerLoanId());
        	} else if( Constants.YUN_DAI_SELF_REPAY_ANY_TIME.equals(resYunLoanInfo.getPartnerBusinessFlag()) ) {
        		model.put("agreementNumber", "bgw-jkxy@"+resYunLoanInfo.getPartnerLoanId());
        	} else {
        		model.put("agreementNumber", "bgw-jkxy@"+resYunLoanInfo.getPartnerLoanId());
        	}
        	
        	
            //签订时间
            model.put("signingTime", DateUtil.formatDateTime(resYunLoanInfo.getLoanTime(), "yyyy年MM月dd日"));

            model.put("BGWAddress", "杭州市江干区四季青街道西子国际大厦C座2305");
            model.put("BGWTel", "400-806-1230");
            model.put("ZANAddress", "杭州市江干区四季青街道西子国际大厦C座2304");
            model.put("ZANTel", "400-696-5858");
            //出借人列表
            model.put("dataUserInfo", resYunLoanInfo.getDataUserInfo());
            //借款人姓名
            model.put("lnUserName", resYunLoanInfo.getLnUserName());
            //借款人身份证号
            model.put("lnIdCard", resYunLoanInfo.getLnUserIdCard());
            //借款人币港湾账户
            model.put("lnUserBWGAccount", resYunLoanInfo.getLnUserBGWAccount());
            //借款用途
            model.put("purpose", resYunLoanInfo.getPurpose());
            //借款本金数额
            model.put("approveAmount", resYunLoanInfo.getApproveAmount());
            //借款年化利率
            model.put("agreementRate", resYunLoanInfo.getAgreementRate());
            //借款期限
            model.put("period", resYunLoanInfo.getPeriod()); 
            //借款出借日
            model.put("loanDay", DateUtil.formatDateTime(resYunLoanInfo.getLoanTime(), "yyyy年MM月dd日"));
            //借款到期日
            model.put("loanDueDate", DateUtil.formatDateTime(DateUtil.addDays(resYunLoanInfo.getLoanTime(),
                    (resYunLoanInfo.getPeriod()*30 - 1)), "yyyy年MM月dd日"));
            //借款服务费率
            model.put("loanServiceRate", resYunLoanInfo.getLoanServiceRate());
            //借款人居住地址
            model.put("lnUserAddress", resYunLoanInfo.getAddress());
            //借款人电子邮箱
            model.put("lnUserEmail", resYunLoanInfo.getEmail());
        }
        return channel + "/regular/loan_agreement_yun_pdf";
    }


    /**
     * 云贷等额本息FIXED_INSTALLMENT借款产品，对应的pdf页面
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/loanAgreementYunInstallmentPdf")
    public String loanAgreementYunInstallmentPdf(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        //合作方借款编号
        String partnerLoanId = request.getParameter("partnerLoanId");

        String loanTime = request.getParameter("loanTime");

        ReqMsg_Match_GetYunUserLoanInfo yunUserLoanInfo = new ReqMsg_Match_GetYunUserLoanInfo();
        yunUserLoanInfo.setPartnerLoanId(partnerLoanId);
        if (StringUtils.isNotBlank(loanTime)) {
            yunUserLoanInfo.setLoanTime(DateUtil.parse(loanTime, DateUtil.DATE_FORMAT + DateUtil.HHMMSS_FORMAT));
        }
        ResMsg_Match_GetYunUserLoanInfo resYunLoanInfo = (ResMsg_Match_GetYunUserLoanInfo) siteService.handleMsg(yunUserLoanInfo);
        if (resYunLoanInfo != null && !"".equals(resYunLoanInfo)) {
            //协议编号  格式bgw-jkxy@+合作方借款id
            model.put("agreementNumber", "bgw-jkxy@" + String.valueOf(resYunLoanInfo.getPartnerLoanId()));
            //签订时间
            model.put("signingTime", DateUtil.formatDateTime(resYunLoanInfo.getLoanTime(), "yyyy年MM月dd日"));

            model.put("BGWAddress", "杭州市江干区四季青街道西子国际大厦C座2305");
            model.put("BGWTel", "400-806-1230");
            model.put("ZANAddress", "杭州市江干区四季青街道西子国际大厦C座2304");
            model.put("ZANTel", "400-696-5858");
            //出借人列表
            model.put("dataUserInfo", resYunLoanInfo.getDataUserInfo());
            //借款人姓名
            model.put("lnUserName", resYunLoanInfo.getLnUserName());
            //借款人身份证号
            model.put("lnIdCard", resYunLoanInfo.getLnUserIdCard());
            //借款人币港湾账户
            model.put("lnUserBWGAccount", resYunLoanInfo.getLnUserBGWAccount());
            //借款用途
            model.put("purpose", resYunLoanInfo.getPurpose());
            //借款本金数额
            model.put("approveAmount", resYunLoanInfo.getApproveAmount());
            //借款年化利率
            model.put("agreementRate", resYunLoanInfo.getAgreementRate());
            //借款期限
            model.put("period", resYunLoanInfo.getPeriod());
            //借款出借日
            model.put("loanDay", DateUtil.formatDateTime(resYunLoanInfo.getLoanTime(), "yyyy年MM月dd日"));
            //借款到期日
            model.put("loanDueDate", resYunLoanInfo.getLoanYunExpireDate());
            //借款服务费率
            model.put("loanServiceRate", resYunLoanInfo.getFixLoanServiceRate());
            //借款人居住地址
            model.put("lnUserAddress", resYunLoanInfo.getAddress());
            //借款人电子邮箱
            model.put("lnUserEmail", resYunLoanInfo.getEmail());
        }
        return channel + "/regular/loan_agreement_yun_installment_pdf";
    }


    /**
     * 云贷等本等息FIXED_PRINCIPAL_INTEREST借款产品，对应的pdf页面
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/loanAgreementYunPrincipalInterestPdf")
    public String loanAgreementYunPrincipalInterestPdf(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        //合作方借款编号
        String partnerLoanId = request.getParameter("partnerLoanId");

        String loanTime = request.getParameter("loanTime");

        ReqMsg_Match_GetYunUserLoanInfo yunUserLoanInfo = new ReqMsg_Match_GetYunUserLoanInfo();
        yunUserLoanInfo.setPartnerLoanId(partnerLoanId);
        if (StringUtils.isNotBlank(loanTime)) {
            yunUserLoanInfo.setLoanTime(DateUtil.parse(loanTime, DateUtil.DATE_FORMAT + DateUtil.HHMMSS_FORMAT));
        }
        ResMsg_Match_GetYunUserLoanInfo resYunLoanInfo = (ResMsg_Match_GetYunUserLoanInfo) siteService.handleMsg(yunUserLoanInfo);
        if (resYunLoanInfo != null && !"".equals(resYunLoanInfo)) {
            //协议编号  格式bgw-jkxy@+合作方借款id
            model.put("agreementNumber", "bgw-jkxy@" + String.valueOf(resYunLoanInfo.getPartnerLoanId()));
            //签订时间
            model.put("signingTime", DateUtil.formatDateTime(resYunLoanInfo.getLoanTime(), "yyyy年MM月dd日"));

            model.put("BGWAddress", "杭州市江干区四季青街道西子国际大厦C座2305");
            model.put("BGWTel", "400-806-1230");
            model.put("ZANAddress", "杭州市江干区四季青街道西子国际大厦C座2304");
            model.put("ZANTel", "400-696-5858");
            //出借人列表
            model.put("dataUserInfo", resYunLoanInfo.getDataUserInfo());
            //借款人姓名
            model.put("lnUserName", resYunLoanInfo.getLnUserName());
            //借款人身份证号
            model.put("lnIdCard", resYunLoanInfo.getLnUserIdCard());
            //借款人币港湾账户
            model.put("lnUserBWGAccount", resYunLoanInfo.getLnUserBGWAccount());
            //借款用途
            model.put("purpose", resYunLoanInfo.getPurpose());
            //借款本金数额
            model.put("approveAmount", resYunLoanInfo.getApproveAmount());
            //借款年化利率
            model.put("agreementRate", resYunLoanInfo.getAgreementRate());
            //借款期限
            model.put("period", resYunLoanInfo.getPeriod());
            //借款出借日
            model.put("loanDay", DateUtil.formatDateTime(resYunLoanInfo.getLoanTime(), "yyyy年MM月dd日"));
            //借款到期日
            model.put("loanDueDate", resYunLoanInfo.getLoanYunExpireDate());
            //借款服务费率
            model.put("loanServiceRate", resYunLoanInfo.getFixLoanServiceRate());
            //借款人居住地址
            model.put("lnUserAddress", resYunLoanInfo.getAddress());
            //借款人电子邮箱
            model.put("lnUserEmail", resYunLoanInfo.getEmail());
        }
        return channel + "/regular/loan_agreement_yun_principal_interest_pdf";
    }

    /**
     * 七贷三方借款协议
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/loanAgreement7Pdf")
    public String loanAgreement7Pdf(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
    	//合作方借款编号
        String partnerLoanId = request.getParameter("partnerLoanId");
        String loanTime = request.getParameter("loanTime");
        
        ReqMsg_Match_GetSevenUserLoanInfo sevenUserLoanInfo = new ReqMsg_Match_GetSevenUserLoanInfo();
        sevenUserLoanInfo.setPartnerLoanId(partnerLoanId);
        if (StringUtils.isNotBlank(loanTime)) {
            sevenUserLoanInfo.setLoanTime(DateUtil.parse(loanTime, DateUtil.DATE_FORMAT + DateUtil.HHMMSS_FORMAT));
        }

        ResMsg_Match_GetSevenUserLoanInfo resSevenLoanInfo = (ResMsg_Match_GetSevenUserLoanInfo) siteService.handleMsg(sevenUserLoanInfo);
        if (resSevenLoanInfo != null && !"".equals(resSevenLoanInfo)) {

            //协议编号  格式为合作方借款id
            model.put("agreementNumber", String.valueOf(resSevenLoanInfo.getPartnerLoanId()));
            //签订时间
            model.put("signingTime", DateUtil.formatDateTime(resSevenLoanInfo.getLoanTime(), "yyyy年MM月dd日"));

            model.put("BGWAddress", "杭州市江干区四季青街道西子国际大厦C座2305");
            model.put("BGWTel", "400-806-1230");
            model.put("ZANAddress", "杭州市江干区四季青街道西子国际大厦C座2304");
            model.put("ZANTel", "400-696-5858");
            //出借人列表
            model.put("dataUserInfo", resSevenLoanInfo.getDataUserInfo());
            //借款人姓名
            model.put("lnUserName", resSevenLoanInfo.getLnUserName());
            //借款人身份证号
            model.put("lnIdCard", resSevenLoanInfo.getLnUserIdCard());
            //借款人币港湾账户
            model.put("lnUserBWGAccount", resSevenLoanInfo.getLnUserBGWAccount());
            //借款用途
            model.put("purpose", resSevenLoanInfo.getPurpose());
            //借款本金数额
            model.put("approveAmount", resSevenLoanInfo.getApproveAmount());
            //借款年化利率
            model.put("agreementRate", resSevenLoanInfo.getAgreementRate());
            //借款期限
            model.put("period", resSevenLoanInfo.getPeriod());   
            //借款出借日
            model.put("loanDay", DateUtil.formatDateTime(resSevenLoanInfo.getLoanTime(), "yyyy年MM月dd日"));
            //借款到期日
            model.put("loanDueDate", DateUtil.formatDateTime(DateUtil.addDays(resSevenLoanInfo.getLoanTime(),
                    (resSevenLoanInfo.getDayCount() - 1)), "yyyy年MM月dd日"));
            //借款服务费率
            model.put("loanServiceRate", resSevenLoanInfo.getLoanServiceRate());
        }
        return channel + "/regular/loan_agreement_7_pdf";

    }
    
    /**
     * 七贷随借随还产品，三方借款协议
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/loanAgreement7PdfAnyRepay")
    public String loanAgreement7PdfAnyRepay(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
    	//合作方借款编号
        String partnerLoanId = request.getParameter("partnerLoanId");
        String loanTime = request.getParameter("loanTime");
        
        ReqMsg_Match_GetSevenUserLoanInfo sevenUserLoanInfo = new ReqMsg_Match_GetSevenUserLoanInfo();
        sevenUserLoanInfo.setPartnerLoanId(partnerLoanId);
        if (StringUtils.isNotBlank(loanTime)) {
            sevenUserLoanInfo.setLoanTime(DateUtil.parse(loanTime, DateUtil.DATE_FORMAT + DateUtil.HHMMSS_FORMAT));
        }
        
        ResMsg_Match_GetSevenUserLoanInfo resSevenLoanInfo = (ResMsg_Match_GetSevenUserLoanInfo) siteService.handleMsg(sevenUserLoanInfo);
        if (resSevenLoanInfo != null && !"".equals(resSevenLoanInfo)) {
            //协议编号  格式为合作方借款id
            model.put("agreementNumber", String.valueOf(resSevenLoanInfo.getPartnerLoanId()));
            //签订时间
            model.put("signingTime", DateUtil.formatDateTime(resSevenLoanInfo.getLoanTime(), "yyyy年MM月dd日"));

            model.put("BGWAddress", "杭州市江干区四季青街道西子国际大厦C座2305");
            model.put("BGWTel", "400-806-1230");
            model.put("ZANAddress", "杭州市江干区四季青街道西子国际大厦C座2304");
            model.put("ZANTel", "400-696-5858");
            //出借人列表
            model.put("dataUserInfo", resSevenLoanInfo.getDataUserInfo());
            //借款人姓名
            model.put("lnUserName", resSevenLoanInfo.getLnUserName());
            //借款人身份证号
            model.put("lnIdCard", resSevenLoanInfo.getLnUserIdCard());
            //借款人币港湾账户
            model.put("lnUserBWGAccount", resSevenLoanInfo.getLnUserBGWAccount());
            //借款用途
            model.put("purpose", resSevenLoanInfo.getPurpose());
            //借款本金数额
            model.put("approveAmount", resSevenLoanInfo.getApproveAmount());
            //借款年化利率
            model.put("agreementRate", resSevenLoanInfo.getAgreementRate());
            //借款期限
            model.put("period", resSevenLoanInfo.getPeriod());   
            //借款出借日
            model.put("loanDay", DateUtil.formatDateTime(resSevenLoanInfo.getLoanTime(), "yyyy年MM月dd日"));
            //借款到期日
            Calendar cal = Calendar.getInstance();
            cal.setTime(resSevenLoanInfo.getLoanTime());
            cal.add(Calendar.DAY_OF_YEAR, resSevenLoanInfo.getPeriod() -1);
            model.put("loanDueDate", DateUtil.formatDateTime(cal.getTime(), "yyyy年MM月dd日"));
            //借款服务费率
            model.put("loanServiceRate", resSevenLoanInfo.getLoanServiceRate());
        }
        return channel + "/regular/loan_agreement_7_pdf_any_repay";

    }
    
    //===================== 云贷、七贷三方借款协议  end ===================


    //==========================代偿协议重新生成合规前的版本 start==========================

    // (1) 收款确认函--服务费--发给云贷、7贷
    @RequestMapping("{channel}/agreement/renewReceiptConfirmServiceFee4Pdf")
    public String renewReceiptConfirmServiceFee4Pdf(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,Map<String,Object> model) {
        ReqMsg_Match_GetUserClaimsTransferInfoListRenew reqClaims = new ReqMsg_Match_GetUserClaimsTransferInfoListRenew();

        //代偿编号
        String orderNo = request.getParameter("orderNo");
        reqClaims.setOrderNo(orderNo);

        //合作方借款编号
        String loanId = request.getParameter("loanId");
        reqClaims.setLoanId(loanId);

        //代偿协议签订时间
        model.put("createTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));

        //代偿成功的时间
        reqClaims.setLateRepayDate(DateUtil.formatYYYYMMDD(new Date()));

        // 合作方编码
        String partnerEnum = request.getParameter("partnerEnum");
        reqClaims.setPartnerEnum(partnerEnum);
        model.put("partnerEnum", partnerEnum);

        ResMsg_Match_GetUserClaimsTransferInfoListRenew resClaimsList = (ResMsg_Match_GetUserClaimsTransferInfoListRenew) siteService.handleMsg(reqClaims);
        if (resClaimsList != null && !"".equals(resClaimsList)) {
            model.put("dataGrid", resClaimsList.getDataGrid());
            //云贷自主放款债权受让人名字
            model.put("yunDaiSelfUserName", resClaimsList.getYunDaiSelfUserName());
            //云贷自主放款债权受让人身份证号
            model.put("yunDaiSelfIdCard", resClaimsList.getYunDaiSelfIdCard());
        }
        return channel + "/regular/lateRepay_receiptConfirm_pdf";
    }

    // (2) 收款确认函--债转--理财人看（云贷、7贷）
    @RequestMapping("{channel}/agreement/renewLetterOfCreditConfirm4Pdf")
    public String renewLetterOfCreditConfirm4Pdf(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,Map<String,Object> model) {
        ReqMsg_Match_GetUserClaimsTransferInfoListRenew reqClaims = new ReqMsg_Match_GetUserClaimsTransferInfoListRenew();

        //代偿编号
        String orderNo = request.getParameter("orderNo");
        reqClaims.setOrderNo(orderNo);

        //合作方借款编号
        String loanId = request.getParameter("loanId");
        reqClaims.setLoanId(loanId);

        //代偿协议签订时间
        model.put("createTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));

        //代偿成功的时间
        String lateRepayDate = DateUtil.formatYYYYMMDD(new Date());
        reqClaims.setLateRepayDate(lateRepayDate);

        // 合作方编码
        String partnerEnum = request.getParameter("partnerEnum");
        reqClaims.setPartnerEnum(partnerEnum);
        model.put("partnerEnum", partnerEnum);

        ResMsg_Match_GetUserClaimsTransferInfoListRenew resClaimsList = (ResMsg_Match_GetUserClaimsTransferInfoListRenew) siteService.handleMsg(reqClaims);
        if (resClaimsList != null && !"".equals(resClaimsList)) {
            model.put("dataGrid", resClaimsList.getDataGrid());
            //云贷自主放款债权受让人名字
            model.put("yunDaiSelfUserName", resClaimsList.getYunDaiSelfUserName());
            //云贷自主放款债权受让人身份证号
            model.put("yunDaiSelfIdCard", resClaimsList.getYunDaiSelfIdCard());
        }
        return channel + "/regular/letter_creditConfirm_pdf";
    }

    // (3) 债权转让通知书--发给云贷、7贷
    @RequestMapping("{channel}/agreement/renewLateRepayCreditorNotice4Pdf")
    public String renewLateRepayCreditorNotice4Pdf(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,Map<String,Object> model) {
        ReqMsg_Match_GetCompensateUserTransferInfoRenew reqClaims = new ReqMsg_Match_GetCompensateUserTransferInfoRenew();
        //合作方借款编号
        String loanId = request.getParameter("loanId");
        reqClaims.setLoanId(loanId);

        //代偿协议签订时间
        model.put("createTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));

        //代偿成功的时间
        String lateRepayDate = DateUtil.formatYYYYMMDD(new Date());
        reqClaims.setLateRepayDate(lateRepayDate);
        model.put("lateRepayDate", lateRepayDate);

        // 合作方编码
        String partnerEnum = request.getParameter("partnerEnum");
        reqClaims.setPartnerEnum(partnerEnum);
        model.put("partnerEnum", partnerEnum);

        ResMsg_Match_GetCompensateUserTransferInfoRenew resClaimsList = (ResMsg_Match_GetCompensateUserTransferInfoRenew) siteService.handleMsg(reqClaims);

        //云贷自主放款债权受让人名字
        model.put("yunDaiSelfUserName", resClaimsList.getYunDaiSelfUserName());
        //云贷自主放款债权受让人身份证号
        model.put("yunDaiSelfIdCard", resClaimsList.getYunDaiSelfIdCard());
        //借款编号
        model.put("loanAgreementNumber", resClaimsList.getLoanAgreementNumber());
        //借款人姓名/身份证号码
        model.put("loanUserName", resClaimsList.getLoanUserName());
        model.put("loanIdCard", resClaimsList.getLoanIdCard());

        model.put("dataGrid", resClaimsList.getDataGrid());

        return channel + "/regular/lateRepay_creditorNotice_pdf";
    }

    // (4) 债权转让协议--发给云贷、7贷
    @RequestMapping("{channel}/agreement/renewLateRepayCreditor4Pdf")
    public String renewLateRepayCreditor4Pdf(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,Map<String,Object> model) {
        ReqMsg_Match_GetCompensateTransferInfoRenew reqClaims = new ReqMsg_Match_GetCompensateTransferInfoRenew();
        //借贷关系编号
        String loanRelationId = request.getParameter("loanRelationId");
        reqClaims.setLoanRelationId(Integer.parseInt(loanRelationId));
        //借款编号
        String loanId = request.getParameter("loanId");
        reqClaims.setLoanId(Integer.parseInt(loanId));

        //代偿协议签订时间
        model.put("createTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));

        // 合作方编码
        String partnerEnum = request.getParameter("partnerEnum");
        reqClaims.setPartnerEnum(partnerEnum);
        model.put("partnerEnum", partnerEnum);

        ResMsg_Match_GetCompensateTransferInfoRenew resClaims = (ResMsg_Match_GetCompensateTransferInfoRenew) siteService.handleMsg(reqClaims);
        //币港湾实际出借人
        model.put("userName", resClaims.getUserName());
        //出借人身份证号
        model.put("idCard", resClaims.getIdCard());
        //出借人手机号
        model.put("userMobile", resClaims.getUserMobile());

        //云贷自主放款债权受让人名字
        model.put("yunDaiSelfUserName", resClaims.getYunDaiSelfUserName());
        //云贷自主放款债权受让人身份证号
        model.put("yunDaiSelfIdCard", resClaims.getYunDaiSelfIdCard());
        //云贷自主放款债权受让人手机号码
        model.put("yunDaiSelfMobile", resClaims.getYunDaiSelfMobile());
        //借款人姓名
        model.put("loanUserName", resClaims.getLoanUserName());
        //借款人身份证号
        model.put("loanIdCard", resClaims.getLoanIdCard());
        //借款期限-云贷
        model.put("period", resClaims.getPeriod());
        //借款本金
        model.put("approveAmount", resClaims.getApproveAmount());
        //债权转让金额
        model.put("transferCreditorAmount", resClaims.getTransferCreditorAmount());
        //借款期限-7贷
        model.put("sevenPeriod", resClaims.getSevenPeriod());

        return channel + "/regular/lateRepay_creditor_pdf";
    }

    //==========================代偿协议重新生成合规前的版本 end==========================


    /**
     * 《支付协议》和《授权委托书》协议pdf模板
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/agreement/topUpAgreement4Pdf")
    public String topUpAgreement4Pdf(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        //授权委托书
        ReqMsg_User_InfoQuery userReq = new ReqMsg_User_InfoQuery();
        String userId = request.getParameter("userId");
        if(StringUtil.isNotEmpty(userId)) {
            userReq.setUserId(Integer.parseInt(userId));
            ResMsg_User_InfoQuery userRes = (ResMsg_User_InfoQuery) siteService.handleMsg(userReq);
            model.put("userName", userRes.getUserName()); //委托人
            model.put("userMobile", userRes.getMobile()); //用户名
            model.put("idCard", userRes.getIdCard()); //身份证号
            model.put("signDate", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日")); //签署日期 系统时间
        }
        return channel + "/agreement/pay_new_pdf";
    }


    /**
     * 判断借款协议、债权转让的协议loanRelationId是否属于当前登录的用户
     *
     * @param userId
     * @param loanRelationId
     * @return
     */
    private boolean checkUserIdlLoanRelationId(int userId, int loanRelationId) {
        ReqMsg_Match_CheckUserIdLoanRelationId reqLoanRelation = new ReqMsg_Match_CheckUserIdLoanRelationId();
        reqLoanRelation.setUserId(userId);
        reqLoanRelation.setLoanRelationId(loanRelationId);

        ResMsg_Match_CheckUserIdLoanRelationId resLoanRelation = (ResMsg_Match_CheckUserIdLoanRelationId) siteService.handleMsg(reqLoanRelation);
        if (resLoanRelation.getLoanRelationId() == loanRelationId) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 校验SubAccountId是否属于该用户
     *
     * @param userId
     * @param subAccountId
     * @return
     */
    private boolean checkUserIdSubAccountId(int userId, int subAccountId) {
        ReqMsg_Account_CheckUserIdSubAccountId reqAccount = new ReqMsg_Account_CheckUserIdSubAccountId();
        reqAccount.setUserId(userId);
        reqAccount.setSubAccountId(subAccountId);

        ResMsg_Account_CheckUserIdSubAccountId resAccount = (ResMsg_Account_CheckUserIdSubAccountId) siteService.handleMsg(reqAccount);
        if (resAccount.getSubAccountId() == subAccountId) {
            return true;
        } else {
            return false;
        }
    }


    private String getBlurName(String name) {
        String str = "";
        if (StringUtil.isNotBlank(name)) {
            str = name.substring(0, 1);
            for (int i = 1; i < name.length(); i++) {
                str += "*";
            }
        }
        return str;
    }

    /**
     * 手机号模糊处理
     *
     * @param mobile
     * @return
     */
    private String getBlurMobile(String mobile) {
        String str = "";
        if (StringUtil.isNotBlank(mobile)) {
            str = mobile.substring(0, 3);
            str = str + "****" + mobile.substring(mobile.length() - 4);
        }
        return str;
    }


    /**
     * 18位的隐藏掉中间10位，如123456**********12；15位隐藏中间8位，如123456********1
     * 身份证
     *
     * @param idNo
     * @return
     */
    private String getBlurIdNo(String idNo) {
        String str = "";
        if (StringUtil.isNotBlank(idNo)) {
            str = idNo.substring(0, 6);
            if (idNo.length() == 18) {
                str += "**********" + idNo.substring(idNo.length() - 2);
            } else {
                str += "********" + idNo.substring(idNo.length() - 1);
            }
        }

        return str;
    }


}
