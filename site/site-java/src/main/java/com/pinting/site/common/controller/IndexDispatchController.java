package com.pinting.site.common.controller;

import com.pinting.business.hessian.site.message.*;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.AddressUtil;
import com.pinting.util.Constants;
import com.pinting.util.WeChatShareUtil;
import org.apache.velocity.tools.generic.NumberTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class IndexDispatchController {
    private Logger              log = LoggerFactory.getLogger(IndexDispatchController.class);
    @Autowired
    private CommunicateBusiness homeService;
    @Autowired
    private WeChatUtil          weChatUtil;

    private final String CHANNEL_MICRO = "micro2.0";
    private final String CHANNEL_GEN = "gen2.0";
    private final String CHANNEL_GEN_178 = "gen178";
    private final String TERMINAL_H5 = "h5";
    private final String TERMINAL_PC = "pc";


    /**
     * pc端和手机端调转
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/index")
    public String loginInit(HttpServletRequest request, HttpServletResponse response) {
        /*if(GlobEnvUtil.get("server.web").contains(request.getHeader("Host"))){//如果是m.bigangwan.com,则访问微网站
        	return "forward:/micro2.0/index";
        }*/
        String userAgent = request.getHeader("User-Agent");
        log.info("访问设备UserAgent：【" + userAgent + "】");

        return "forward:/dispather";
    }

    @RequestMapping("/dispather")
    public String dispatcher(HttpServletRequest request, HttpServletResponse response) {

        Pattern mobileAgents = Pattern.compile(
            "iphone|android|phone|ndroid|mobile|wap|netfront|java|opera mobi|"
                    + "opera mini|ucweb|windows ce|symbian|series|webos|sony|blackberry|dopod|"
                    + "nokia|samsung|palmsource|xda|pieplus|meizu|midp|cldc|motorola|foma|"
                    + "docomo|up.browser|up.link|blazer|helio|hosin|huawei|novarra|coolpad|webos|"
                    + "techfaith|palmsource|alcatel|amoi|ktouch|nexian|ericsson|philips|sagem|"
                    + "wellcom|bunjalloo|maui|smartphone|iemobile|spice|bird|zte-|longcos|"
                    + "pantech|gionee|portalmmm|jig browser|hiptop|benq|haier|^lct|320x320|"
                    + "240x320|176x220|w3c |acs-|alav|alca|amoi|audi|avan|benq|bird|blac|"
                    + "blaz|brew|cell|cldc|cmd-|dang|doco|eric|hipt|inno|ipaq|java|jigs|"
                    + "kddi|keji|leno|lg-c|lg-d|lg-g|lge-|maui|maxo|midp|mits|mmef|mobi|"
                    + "mot-|moto|mwbp|nec-|newt|noki|oper|palm|pana|pant|phil|play|port|"
                    + "prox|qwap|sage|sams|sany|sch-|sec-|send|seri|sgh-|shar|sie-|siem|"
                    + "smal|smar|sony|sph-|symb|t-mo|teli|tim-|tosh|tsm-|upg1|upsi|vk-v|"
                    + "voda|wap-|wapa|wapi|wapp|wapr|webc|winw|winw|xda|xda-|Googlebot-Mobile",
            Pattern.CASE_INSENSITIVE);
        //暂时只列出部分，可添加
        Pattern pcAgents = Pattern.compile("Macintosh", Pattern.CASE_INSENSITIVE);

        String userAgent = request.getHeader("User-Agent");
        userAgent = StringUtil.isBlank(userAgent) ? "iphone" : userAgent;//避免user agent为null的情况
        log.info("访问设备UserAgent：【" + userAgent + "】");
        Matcher mobileMatcher = mobileAgents.matcher(userAgent);
        Matcher pcMatcher = pcAgents.matcher(userAgent);
        if (mobileMatcher.find() && !pcMatcher.find()) {
            return "forward:/micro2.0/index";
        } else {
            return "forward:/gen2.0/index";
        }

    }

    /**
     * 首页
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/index")
    public String loginInit(@PathVariable String channel, HttpServletRequest request,
                            HttpServletResponse response, ReqMsg_Home_InfoQuery regHome,
                            Map<String, Object> model) {
        log.info("【首页开始】");
        model.put("now", new Date());
        model.put("tomorrow", DateUtil.addDays(new Date(), 1));
        CookieManager manager = new CookieManager(request);
        String qianbao = request.getParameter("qianbao");
        String userIdStr = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
        // 分享
        String link = GlobEnv.getWebURL("/micro2.0/index");
        WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);

        String user = request.getParameter("user"); //当点击用户分享链接过来时会有此参数
        if (user != null && !"".equals(user) && user.length() >= 36) {//UUID长度至少36
            user = user.substring(0, user.length() - 36);
            // 用户ID不为空，存入cookie
            if (StringUtil.isNotBlank(user)) {
                String recommend = com.pinting.util.Util.generateInvitationCode(Integer
                    .parseInt(user));
                CookieManager cookieManager = new CookieManager(request);
                String recommendId = cookieManager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_RECOMMEND_ID.getName(), true);
                if (recommendId == null || "".equals(recommendId) || !recommend.equals(recommendId)) { //说明并没有存在推荐人，同时下面将推荐人的id号存入cookie当中
                    cookieManager.setValue(CookieEnums._SITE.getName(),
                        CookieEnums._SITE_RECOMMEND_ID.getName(), user, true);
                    cookieManager.save(response, CookieEnums._SITE.getName(), null, "/",
                        5 * 365 * 24 * 3600, true);
                }
            }
        }

        Map<String, Object> channelTypeResult = channelType(channel, request);
        String productShowTerminal = (String) channelTypeResult.get(KEY_PRODUCT_SHOW_TERMINAL);
        String bannerChannel = (String) channelTypeResult.get(KEY_BANNER_CHANNEL);
        String receiverType = (String) channelTypeResult.get(KEY_RECEIVER_TYPE);
        Integer agentId = channelTypeResult.get(KEY_AGENT_ID) == null ? null: (Integer) channelTypeResult.get(KEY_AGENT_ID);
        regHome.setTerminal(channel);
        regHome.setProductShowTerminal(productShowTerminal);
        // 首页信息
        regHome.setUserType("NORMAL");
        if(!StringUtil.isBlank(userIdStr)) {
            regHome.setUserId(Integer.parseInt(userIdStr));
        }

        ResMsg_Home_InfoQuery resHome = (ResMsg_Home_InfoQuery) homeService.handleMsg(regHome);
        //最大和最小的年化收益率
        model.put("minRate", resHome.getMinRate());
        model.put("maxRate", resHome.getMaxRate());
        model.put("currDateTime", new Date());
        model.put("investNum", resHome.getInvestNum());
        model.put("currTotalAmount", resHome.getCurrTotalAmount());
        model.put("totalIncome", resHome.getTotalIncome());
        model.put("limit", resHome.getLimit());
        model.put("ceiling", resHome.getCeiling());
        model.put("totalRedPacketSubtract", resHome.getTotalRedPacketSubtract());
        model.put("totalReqUser", resHome.getTotalRegUser());
        model.put("reportList", resHome.getReportList());
        model.put("totalInvestAmount", resHome.getTotalInvestAmount());

        // 首页公告|动态|新闻
        indexNews(channel, model, receiverType, StringUtil.isBlank(userIdStr) ? null : Integer.parseInt(userIdStr));
        // 获取首页banner
        ReqMsg_BannerConfig_getList bannerReq = new ReqMsg_BannerConfig_getList();
        bannerReq.setbChannel(bannerChannel);
        ResMsg_BannerConfig_getList bannerRes = (ResMsg_BannerConfig_getList) homeService.handleMsg(bannerReq);
        model.put("bannerList", bannerRes);
        
        //记录用户登录的IP地址对应的位置信息
        String ip = manager.getValue(CookieEnums._USER.getName(),
                CookieEnums._USER_IP.getName(), true);
        if (StringUtil.isBlank(ip) && !StringUtil.isBlank(userIdStr)) {
        	ip = AddressUtil.getIp(request);
        	String address = "";
        	try {
    			address = AddressUtil.getAddresses("ip=" + ip, "utf-8");
    		} catch (UnsupportedEncodingException e) {
    			e.printStackTrace();
    		}
        	//用户id和地址都存在时，发起通信入redis
        	if (!StringUtil.isBlank(address)) {
        		ReqMsg_User_AddUserAddress addressReq = new ReqMsg_User_AddUserAddress();
        		addressReq.setUserId(Integer.parseInt(userIdStr));
        		addressReq.setAddress(address);
        		
        		ResMsg_User_AddUserAddress res = (ResMsg_User_AddUserAddress)homeService.handleMsg(addressReq);
        		if ("000000".equals(res.getResCode())) {
        			//将用户IP存入cookie
        			CookieManager ipManager = new CookieManager(request);
        			ipManager.setValue(CookieEnums._USER.getName(),
        					CookieEnums._USER_IP.getName(), ip, true);
        			ipManager.save(response, CookieEnums._USER.getName(), null, "/",
                            -1, true);
        		}
			}
        }

        if (CHANNEL_MICRO.equals(channel)) {
            // 活动中心红点
            ReqMsg_News_IsRead isReadReq = new ReqMsg_News_IsRead();
            isReadReq.setUserId(StringUtil.isNotBlank(userIdStr) ? Integer.parseInt(userIdStr) : null);
            ResMsg_News_IsRead res = (ResMsg_News_IsRead) homeService.handleMsg(isReadReq);
            model.put("isRead", res.getIsRead());

            // H5钱报
            if (StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                // 产品列表
                productListOfQianbao(request, channel, model, null);
                ReqMsg_User_AgentQuery agentReqMsg = new ReqMsg_User_AgentQuery();
                agentReqMsg.setAgentId(agentId);
                model.put(Constants.USER_AGENT_QIANBAO, Constants.USER_AGENT_QIANBAO);
                homeService.handleMsg(agentReqMsg);
                model.put("userId", userIdStr);

                log.info("【首页结束】");
                return "qianbao178/home/home_index";
            } else {
                // H5产品
                //判断是否有限时活动标，是则取该活动标
                //否则判断是否有新手标，并且判断是否是新手，若是则取该新手标
                //否则取普通标
                List<HashMap<String, Object>> prods = resHome.getRecommendProductList();
                HashMap<String, Object> prodActivity = prods.get(0);
                HashMap<String, Object> prodNewBuyer = prods.get(1);
                HashMap<String, Object> prodNormal = prods.get(2);
                List<HashMap<String, Object>> resultProds = new ArrayList<>();
                if(prodActivity != null){
                    Double amount = (Double) prodActivity.get("maxSingleInvestAmount");
                    prodActivity.put("maxSingleInvestAmount", formatNumber(amount));
                    resultProds.add(prodActivity);
                    model.put("recommendProductList", resultProds);
                } else if(prodNewBuyer != null){
                    ReqMsg_PayOrders_GetSuccessPayOrders reqOrder = new ReqMsg_PayOrders_GetSuccessPayOrders();
                    reqOrder.setUserId(StringUtil.isNotBlank(userIdStr) ? Integer.parseInt(userIdStr) : null);
                    ResMsg_PayOrders_GetSuccessPayOrders resOrder = (ResMsg_PayOrders_GetSuccessPayOrders) homeService.handleMsg(reqOrder);
                    if(resOrder.getBuyType() == null){
                        Double amount = (Double) prodNewBuyer.get("maxSingleInvestAmount");
                        prodNewBuyer.put("maxSingleInvestAmount", formatNumber(amount));
                        resultProds.add(prodNewBuyer);
                        model.put("recommendProductList", resultProds);
                    } else {
                        Double amount = (Double) prodNormal.get("maxSingleInvestAmount");
                        prodNormal.put("maxSingleInvestAmount", formatNumber(amount));
                        resultProds.add(prodNormal);
                        model.put("recommendProductList", resultProds);
                    }
                } else {
                    Double amount = (Double) prodNormal.get("maxSingleInvestAmount");
                    prodNormal.put("maxSingleInvestAmount", formatNumber(amount));
                    resultProds.add(prodNormal);
                    model.put("recommendProductList", resultProds);
                }
            }
        } else if (CHANNEL_GEN.equals(channel)) {
            // 新手推荐理财计划开始
            productListOfPC(request, channel, model, resHome);
            // 新手推荐理财计划结束
        } else if (CHANNEL_GEN_178.equals(channel)) {
            // 钱报产品列表
            productListOfQianbao(request, channel, model, resHome);
            model.put("userId", userIdStr);

            // 浏览量添加
            ReqMsg_User_AgentQuery agentReqMsg = new ReqMsg_User_AgentQuery();            
            agentReqMsg.setAgentId(agentId);
            homeService.handleMsg(agentReqMsg);
        }

        log.info("【首页结束】");
        return channel + "/home/home_index";
    }

    @RequestMapping("{channel}/qb_index")
    public String qbIndex(@PathVariable String channel) {
        if(Constants.CHANNEL_MICRO.equals(channel)) {
            return "redirect:/" + channel + "/index?qianbao=qianbao&agentViewFlag=15";
        } else if(Constants.CHANNEL_GEN_178.equals(channel)) {
            return "redirect:/" + channel + "/index?agentViewFlag=15";
        }
        return "redirect:/" + channel + "/index?qianbao=qianbao&agentViewFlag=15";
    }

    /**
     * 秦皇岛首页入口
     * @param channel
     * @param dispatchAgentId
     * @return
     */
    @RequestMapping("{channel}/qhd_index/{dispatchAgentId}")
    public String qhdIndex(@PathVariable String channel, @PathVariable String dispatchAgentId) {
        if(TERMINAL_H5.equals(channel)) {
            return "forward:/" + Constants.CHANNEL_MICRO + "/index?qianbao=qianbao&agentViewFlag="+dispatchAgentId;
        } else if(TERMINAL_PC.equals(channel)) {
            return "forward:/" + Constants.CHANNEL_GEN_178 + "/index?agentViewFlag="+dispatchAgentId;
        }
        return "forward:/" + channel + "/index?qianbao=qianbao&agentViewFlag="+dispatchAgentId;
    }

    /**
     * 分页显示178产品
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/homeIndexPage")
    public String homeIndexPage(@PathVariable String channel, HttpServletRequest request,
                                HttpServletResponse response, Map<String, Object> model) {
        productListOfQianbao(request, channel, model, null);

        return "qianbao178/home/home_index_page";
    }

    /**
     * 产品列表PC
     * @param request
     * @param channel
     * @param model
     * @param resHome
     */
    private void productListOfPC(HttpServletRequest request, String channel,
                                 Map<String, Object> model, ResMsg_Home_InfoQuery resHome) {
        HashMap<String, Object> noviceRecommendedProduct = resHome.getNoviceRecommendedProduct();
        if(null != noviceRecommendedProduct)
            this.tiaozheng(noviceRecommendedProduct);

        List<HashMap<String, Object>> recommendProductList = resHome.getRecommendProductList();
        if(!CollectionUtils.isEmpty(recommendProductList)) {
            for (HashMap<String, Object> product : recommendProductList) {
                this.tiaozheng(product);
            }
        }
        // 新手推荐产品
        model.put("recommendProduct", noviceRecommendedProduct);
        // 理财计划列表
        model.put("recommendProductList", recommendProductList);
    }

    private void tiaozheng(HashMap<String, Object> product) {
        product.put("rate", this.formatNumer(Double.valueOf((String)product.get("rate"))));

        String startTime = (String) product.get("startTime");
        Integer status = (Integer) product.get("status");
        Double minInvestAmount = (Double) product.get("minInvestAmount");
        NumberTool numberTool = new NumberTool();
        product.put("minInvestAmountWan", numberTool.format("0", minInvestAmount / 10000d));
        Date now = new Date();
        // 未发放已发布
        if (Constants.PRODUCT_STATUS_PUBLISH_YES.equals(status)) {
            product.put("flag", "countdown");
            int day = DateUtil.getDiffeDay(DateUtil.parseDateTime(startTime), now);
            int hour = DateUtil.getDiffeHour(
                    DateUtil.addDays(DateUtil.parseDateTime(startTime), -day), now);
            int minute = DateUtil.getDiffeMinute(DateUtil.addHours(
                    DateUtil.addDays(DateUtil.parseDateTime(startTime), -day), -hour), now);
            int seconds = DateUtil
                    .getDiffeSeconds(
                            DateUtil.addMinutes(
                                    DateUtil.addHours(
                                            DateUtil.addDays(DateUtil.parseDateTime(startTime), -day), -hour),
                                    -minute), now);
            product.put("day", day);
            product.put("hour", hour);
            product.put("minute", minute);
            product.put("seconds", seconds);
            product.put("progress", 0);
        }
        // 已经手动结束
        if (Constants.PRODUCT_STATUS_FINISH.equals(status)) {
            product.put("flag", "finish");
            product.put("progress", 100);
        }
        // 进行中
        if (Constants.PRODUCT_STATUS_OPENING.equals(status)) {
            product.put("flag", "buy");
            Double maxTotalAmount = (Double) product.get("maxTotalAmount");
            Double currTotalAmount = (Double) product.get("currTotalAmount");
            Double a =  currTotalAmount / maxTotalAmount * 100d;
            if(a > 0 && a < 1) {
                product.put("progress", 1);
            } else if(a > 99 && a < 100) {
                product.put("progress", 99);
            } else {
                product.put("progress", a);
            }
        }
    }

    /**
     * 钱报的产品列表
     */
    private void productListOfQianbao(HttpServletRequest request, String channel,
                                      Map<String, Object> model, ResMsg_Home_InfoQuery resHome) {
        model.put("now", new Date());
        model.put("tomorrow", DateUtil.addDays(new Date(), 1));

        Map<String, Object> channelTypeResult = channelType(channel, request);
        String productShowTerminal = (String) channelTypeResult.get(KEY_PRODUCT_SHOW_TERMINAL);
        if (CHANNEL_GEN_178.equals(channel)) {
            List<HashMap<String, Object>> recommendProductList = resHome.getRecommendProductList();
            if(!CollectionUtils.isEmpty(recommendProductList)) {
                for (HashMap<String, Object> product : recommendProductList) {
                    this.tiaozheng(product);
                }
            }
            // 理财计划列表
            model.put("recommendProductList", recommendProductList);
        } else {
            ReqMsg_Product_ListQuery reqMsg = new ReqMsg_Product_ListQuery();
            reqMsg.setUserType("NORMAL");
            reqMsg.setStatus(null);
            reqMsg.setTerm(null);
            reqMsg
                .setPageNum(Integer.parseInt(StringUtil.isBlank(request.getParameter("page")) ? "1"
                    : request.getParameter("page")));
            reqMsg.setProductShowTerminal(productShowTerminal);
            reqMsg.setNumPerPage(10);
            model.put("userType", "NORMAL");
            ResMsg_Product_ListQuery resMsg = (ResMsg_Product_ListQuery) homeService
                .handleMsg(reqMsg);
            List<Map<String, Object>> dataList = resMsg.getProductLst();
            // 产品列表数据调整便于前端显示开始
            for (Map<String, Object> product : dataList) {
                String startTime = (String) product.get("startTime");
                Integer status = (Integer) product.get("status");
                Double minInvestAmount = (Double) product.get("minInvestAmount");
                NumberTool numberTool = new NumberTool();
                product.put("minInvestAmountWan", numberTool.format("0", minInvestAmount / 10000d));
                Date now = new Date();
                // 未发放已发布
                if (Constants.PRODUCT_STATUS_PUBLISH_YES.equals(status)) {
                    product.put("flag", "countdown");
                    // 同一天
                    if (DateUtil.isSameDay(DateUtil.parseDateTime(startTime), now)) {
                        product.put("isSameDay", "yes");
                    }
                    // 不同天
                    else {
                        product.put("isSameDay", "no");
                    }
                    product.put("progress", 0);
                }
                // 已经手动结束
                if (Constants.PRODUCT_STATUS_FINISH.equals(status)) {
                    product.put("flag", "finish");
                    product.put("progress", 100);
                }
                // 进行中
                if (Constants.PRODUCT_STATUS_OPENING.equals(status)) {
                    product.put("flag", "buy");
                    Double maxTotalAmount = (Double) product.get("maxTotalAmount");
                    Double currTotalAmount = (Double) product.get("currTotalAmount");
                    Double a = currTotalAmount / maxTotalAmount * 100d;
                    product.put("progress", a);
                }
            }

            // 产品列表数据调整便于前端显示结束
            reqMsg.setTotolRows(resMsg.getCount());
            model.put("item", dataList);
            model.put("totalCount", reqMsg.getTotalPages());
            model.put("page", reqMsg.getPageNum());
        }
    }

    /**
     * 问题、故事、产品、联系我们、免责声明、隐私声明
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/service/{flag}")
    public String serviceInit(@PathVariable String channel, @PathVariable String flag,
                              HttpServletRequest request, HttpServletResponse response,
                              ReqMsg_Home_InfoQuery regHome, Map<String, Object> model) {

        loginInit(channel, request, response, regHome, model);
        return channel + "/service/" + flag;
    }

    /**
     * 模态框跳转
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/alert")
    public String alertInit(HttpServletRequest request, HttpServletResponse response) {

        return "/alert";
    }

    /**
     * 模态框单按钮跳转
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/alerts")
    public String alertsInit(HttpServletRequest request, HttpServletResponse response) {

        return "/alerts";
    }

    /**
     * 带关闭图标弹出框
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/alertClose")
    public String alertCloseInit(HttpServletRequest request, HttpServletResponse response) {

        return "/alert_close";
    }

    /**
     *  省份联动
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/province/index")
    public @ResponseBody
    HashMap<String, Object> provinceIndex(HttpServletRequest request, HttpServletResponse response,
                                          ReqMsg_PCA_InfoQuery req) {

        HashMap<String, Object> result = new HashMap<String, Object>();

        ResMsg_PCA_InfoQuery res = (ResMsg_PCA_InfoQuery) homeService.handleMsg(req);

        result.put("city", res.getProvinceList());

        return result;
    }

    @RequestMapping("{channel}/get_nick_cookie")
    public @ResponseBody
    HashMap<String, Object> getNickCookie(@PathVariable String channel, HttpServletRequest request,
                                          HttpServletResponse response) {

        HashMap<String, Object> result = new HashMap<String, Object>();
        CookieManager manager = new CookieManager(request);
        //查询userId,如不为空，查询手机号并返回，若为空，则返回空
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        String mobile = "";
        if (StringUtil.isNotEmpty(userId)) {
            mobile = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_MOBILE_NUM.getName(), true);
        }
        String nick = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_NAME.getName(), true);

        if (StringUtil.isNotEmpty(mobile) && mobile.length() == 11) {
            mobile = mobile.substring(0, 3) + "****" + mobile.substring(7);
        }
        result.put("userNick", mobile);
        ArrayList<String> allowDomains = new ArrayList<String>();
        return result;
    }

    /**
     * 首页新闻|公告|动态
     * @param model
     */
    private void indexNews(String channel, Map<String, Object> model, String receiverType, Integer userId) {
        if (!Constants.NEWS_RECEIVER_TYPE_BGW.equals(receiverType)) {
            ReqMsg_News_CurrentNews currentNotice = new ReqMsg_News_CurrentNews();
            // 最新公告-显示4条记录
            currentNotice.setReceiverType(receiverType);
            currentNotice.setType(Constants.NEWS_TYPE_NOTICE);
            currentNotice.setShowPage(4);
            currentNotice.setUserId(userId);
            ResMsg_News_CurrentNews noticeRes = (ResMsg_News_CurrentNews) homeService
                .handleMsg(currentNotice);
            model.put("hasNews", noticeRes.isHasNews());
            if (noticeRes.isHasNews()) {
                model.put("news", noticeRes.getNews());
            } else {
                model.put("news", "");
            }
            if(!CHANNEL_MICRO.equals(channel)) {
                // 获取最新3条公司动态
                ReqMsg_News_CurrentNews currentCompanyDynamics = new ReqMsg_News_CurrentNews();
                currentCompanyDynamics.setReceiverType(receiverType);
                currentCompanyDynamics.setType(Constants.NEWS_TYPE_COMPANY_DYNAMIC);
                currentCompanyDynamics.setShowPage(3);
                ResMsg_News_CurrentNews companyDynamicsRes = (ResMsg_News_CurrentNews) homeService
                        .handleMsg(currentCompanyDynamics);
                model.put("companyDynamicsNews", companyDynamicsRes.getNews());
                model.put("hasCDNews", companyDynamicsRes.isHasNews());

                // 获取最新7条媒体报道
                ReqMsg_News_CurrentNews currentMediaCoverage = new ReqMsg_News_CurrentNews();
                currentMediaCoverage.setReceiverType(receiverType);
                currentMediaCoverage.setType(Constants.NEWS_TYPE_NEWS);
                currentMediaCoverage.setShowPage(3);
                ResMsg_News_CurrentNews mediaCoverageRes = (ResMsg_News_CurrentNews) homeService
                        .handleMsg(currentMediaCoverage);
                model.put("mediaCoverageNews", mediaCoverageRes.getNews());
                model.put("hasMCNews", mediaCoverageRes.isHasNews());

                // 查询湾粉活动
                ReqMsg_News_CurrentNews wfansActivityReq = new ReqMsg_News_CurrentNews();
                wfansActivityReq.setReceiverType(receiverType);
                wfansActivityReq.setType(Constants.NEWS_TYPE_WFANS_ACTIVITY);
                wfansActivityReq.setShowPage(3);
                ResMsg_News_CurrentNews wfansActivityRes = (ResMsg_News_CurrentNews) homeService
                        .handleMsg(wfansActivityReq);
                if (wfansActivityRes.isHasNews()) {
                    model.put("wfansActivityList", wfansActivityRes.getNews());
                }
            }
        } else {
            ReqMsg_News_CurrentNews currentNotice = new ReqMsg_News_CurrentNews();
            // 最新公告-显示4条记录
            currentNotice.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW);
            currentNotice.setType(Constants.NEWS_TYPE_NOTICE);
            currentNotice.setShowPage(4);
            currentNotice.setUserId(userId);
            ResMsg_News_CurrentNews noticeRes = (ResMsg_News_CurrentNews) homeService
                    .handleMsg(currentNotice);
            model.put("hasNews", noticeRes.isHasNews());
            if (noticeRes.isHasNews()) {
                model.put("news", noticeRes.getNews());
            } else {
                model.put("news", "");
            }
            if(!CHANNEL_MICRO.equals(channel)) {
                // H5，不查询公司动态、媒体报道、湾粉活动
                // 获取最新3条公司动态
                ReqMsg_News_CurrentNews currentCompanyDynamics = new ReqMsg_News_CurrentNews();
                currentCompanyDynamics.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW);
                currentCompanyDynamics.setType(Constants.NEWS_TYPE_COMPANY_DYNAMIC);
                currentCompanyDynamics.setShowPage(3);
                ResMsg_News_CurrentNews companyDynamicsRes = (ResMsg_News_CurrentNews) homeService
                        .handleMsg(currentCompanyDynamics);
                model.put("companyDynamicsNews", companyDynamicsRes.getNews());
                model.put("hasCDNews", companyDynamicsRes.isHasNews());

                // 获取最新7条媒体报道
                ReqMsg_News_CurrentNews currentMediaCoverage = new ReqMsg_News_CurrentNews();
                currentMediaCoverage.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW);
                currentMediaCoverage.setType(Constants.NEWS_TYPE_NEWS);
                currentMediaCoverage.setShowPage(3);
                ResMsg_News_CurrentNews mediaCoverageRes = (ResMsg_News_CurrentNews) homeService
                        .handleMsg(currentMediaCoverage);
                model.put("mediaCoverageNews", mediaCoverageRes.getNews());
                model.put("hasMCNews", mediaCoverageRes.isHasNews());

                // 查询湾粉活动
                ReqMsg_News_CurrentNews wfansActivityReq = new ReqMsg_News_CurrentNews();
                wfansActivityReq.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW);
                wfansActivityReq.setType(Constants.NEWS_TYPE_WFANS_ACTIVITY);
                wfansActivityReq.setShowPage(3);
                ResMsg_News_CurrentNews wfansActivityRes = (ResMsg_News_CurrentNews) homeService
                        .handleMsg(wfansActivityReq);
                if (wfansActivityRes.isHasNews()) {
                    model.put("wfansActivityList", wfansActivityRes.getNews());
                }
            }
        }
    }
    
    private String formatNumer(Double number) {
        double a = number;
        int integer = (int)a;
        if(a == integer) {
            return integer+".0";
        } else {
            return String.valueOf(a);
        }
    }

    private String formatNumber(Double amount) {
        if(amount != null) {
            double a = MoneyUtil.divide(amount, 10000d).doubleValue();
            int integer = (int)a;
            if(a == integer) {
               return String.valueOf(integer);
            } else {
                return String.valueOf(a);
            }
        }
        return null;
    }

    private final String KEY_AGENT_ID = "KEY_AGENT_ID";
    private final String KEY_BANNER_CHANNEL = "KEY_BANNER_CHANNEL";
    private final String KEY_RECEIVER_TYPE = "KEY_RECEIVER_TYPE";
    private final String KEY_PRODUCT_SHOW_TERMINAL = "KEY_PRODUCT_SHOW_TERMINAL";

    private Map<String, Object> channelType(String channel, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        CookieManager manager = new CookieManager(request);
        String agentViewflag = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
        String qianbao = request.getParameter(Constants.USER_AGENT_QIANBAO);
        //钱报用户公告提示信息//获取banner // 浏览量添加
        if(CHANNEL_MICRO.equals(channel) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            if(StringUtil.isNotBlank(agentViewflag)) {
                //判断钱报or柯桥OR海宁OR瑞安
                if(Constants.AGENT_VIEW_ID_KQ.equals(agentViewflag)) {
                    result.put(KEY_AGENT_ID, Constants.AGENT_KQ_ID);
                    result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_MICROKQ);
                    result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGWKQ);
                    result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_H5_KQ);
                } else if (Constants.AGENT_VIEW_ID_HN.equals(agentViewflag)) {
                    result.put(KEY_AGENT_ID, Constants.AGENT_HAINING_ID);
                    result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_MICROHN);
                    result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGWHN);
                    result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_H5_HN);
                } else if (Constants.AGENT_VIEW_ID_RUIAN.equals(agentViewflag)) {
                    result.put(KEY_AGENT_ID, Constants.AGENT_RUIAN_ID);
                    result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_MICRORUIAN);
                    result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGWRUIAN);
                    result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_H5_RUIAN);
                } else if (Constants.AGENT_VIEW_ID_QB.equals(agentViewflag)) {
                    result.put(KEY_AGENT_ID, Constants.AGENT_QIANBAO_ID);
                    result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_MICRO178);
                    result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGW178);
                    result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_H5_178);
                } else if(Constants.AGENT_VIEW_ID_QD.equals(agentViewflag)) {
                    // 七店
                    result.put(KEY_AGENT_ID, Constants.AGENT_ID_QD);
                    result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_MICRO_QD);
                    result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGWQD);
                    result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_H5_QD);
                } else if(Constants.AGENT_VIEW_ID_QHD_JT.equals(agentViewflag)) {
                    // 秦皇岛交通广播
                    result.put(KEY_AGENT_ID, Constants.AGENT_ID_QHD_JT);
                    result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_MICROQHDJT);
                    result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGWQHDJT);
                    result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_H5_QHD_JT);
                }else if(Constants.AGENT_VIEW_ID_QHD_XW.equals(agentViewflag)) {
                    // 秦皇岛新闻891
                    result.put(KEY_AGENT_ID, Constants.AGENT_ID_QHD_XW);
                    result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_MICROQHDXW);
                    result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGWQHDXW);
                    result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_H5_QHD_XW);
                }else if(Constants.AGENT_VIEW_ID_QHD_TV.equals(agentViewflag)) {
                    // 秦皇岛电视台今日报道
                    result.put(KEY_AGENT_ID, Constants.AGENT_ID_QHD_TV);
                    result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_MICROQHDTV);
                    result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGWQHDTV);
                    result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_H5_QHD_TV);
                }else if(Constants.AGENT_VIEW_ID_QHD_ST.equals(agentViewflag)) {
                    // 视听之友
                    result.put(KEY_AGENT_ID, Constants.AGENT_ID_QHD_ST);
                    result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_MICROQHDST);
                    result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGWQHDST);
                    result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_H5_QHD_ST);
                }else if(Constants.AGENT_VIEW_ID_QHD_SJC.equals(agentViewflag)) {
                    // 1038私家车广播
                    result.put(KEY_AGENT_ID, Constants.AGENT_ID_QHD_SJC);
                    result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_MICROQHDSJC);
                    result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGWQHDSJC);
                    result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_H5_QHD_SJC);
                }else {
                    result.put(KEY_AGENT_ID, Constants.AGENT_QIANBAO_ID);
                    result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_MICRO178);
                    result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGW178);
                    result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_H5_178);
                }
            } else {
                result.put(KEY_AGENT_ID, Constants.AGENT_QIANBAO_ID);
                result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_MICRO178);
                result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGW178);
                result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_H5_178);
            }
        } else if(CHANNEL_GEN_178.equals(channel)) {
            if(StringUtil.isNotBlank(agentViewflag)) {
                if(Constants.AGENT_VIEW_ID_KQ.equals(agentViewflag)) {
                    result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_PC_KQ);
                    result.put(KEY_AGENT_ID, Constants.AGENT_KQ_ID);
                    result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_GENKQ);
                    result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGWKQ);
                } else if (Constants.AGENT_VIEW_ID_HN.equals(agentViewflag)) {
                    result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_PC_HN);
                    result.put(KEY_AGENT_ID, Constants.AGENT_HAINING_ID);
                    result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_GENHN);
                    result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGWHN);
                } else if (Constants.AGENT_VIEW_ID_RUIAN.equals(agentViewflag)) {
                    result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_PC_RUIAN);
                    result.put(KEY_AGENT_ID, Constants.AGENT_RUIAN_ID);
                    result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_GENRUIAN);
                    result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGWRUIAN);
                } else if (Constants.AGENT_VIEW_ID_QHD_ST.equals(agentViewflag)) {
                    // 视听之友
                    result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_PC_QHD_ST);
                    result.put(KEY_AGENT_ID, Constants.AGENT_ID_QHD_ST);
                    result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_GENQHDST);
                    result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGWQHDST);
                }else {
                    result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_PC_178);
                    result.put(KEY_AGENT_ID, Constants.AGENT_QIANBAO_ID);
                    result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_GEN178);
                    result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGW178);
                }
            } else {
                result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_PC_178);
                result.put(KEY_AGENT_ID, Constants.AGENT_QIANBAO_ID);
                result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_GEN178);
                result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGW178);
            }
        } else if(CHANNEL_MICRO.equals(channel)) {
            // 非钱报、七店渠道系列 H5
            result.put(KEY_AGENT_ID, null);
            result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_MICRO);
            result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGW);
            result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_H5);
        } else {
            // 非钱报、七店渠道系列 PC
            result.put(KEY_AGENT_ID, null);
            result.put(KEY_BANNER_CHANNEL, Constants.BANNER_CHANNEL_GEN);
            result.put(KEY_RECEIVER_TYPE, Constants.NEWS_RECEIVER_TYPE_BGW);
            result.put(KEY_PRODUCT_SHOW_TERMINAL, Constants.PRODUCT_SHOW_TERMINAL_PC);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(StringUtil.substringBefore("asdad-1234", "-"));
    }
}
