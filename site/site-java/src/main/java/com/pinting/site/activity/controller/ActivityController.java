package com.pinting.site.activity.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.hessian.site.message.*;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.util.DESUtil;
import com.pinting.util.MatrixToImageWriter;
import com.pinting.util.WeChatShareUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;

/**
 * 活动Controller
 * @author HuanXiong
 * @version 2016-5-4 下午4:07:56
 */
@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ActivityController {

    private final static String CHANNEL_APP = "app";
    private final static String CHANNEL_H5 = "micro2.0";
    private final static String CHANNEL_PC = "gen2.0";
    private final static String CHANNEL_PC_178 = "gen178";
    private final static String CLIENT_IOS = "ios";
    private final static String CLIENT_ANDROID = "android";


    @Autowired
    private CommunicateBusiness communicateBusiness;
    @Autowired
    private WeChatUtil weChatUtil;

    private static Logger logger = LoggerFactory.getLogger(ActivityController.class);

    /**
     * 指定渠道活动入口
     * @return
     */
    @RequestMapping("/{channel}/activity/{flag}/{agentId}")
    public String agentActivity(@PathVariable String channel, @PathVariable String flag, @PathVariable String agentId, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        String url = agentActivityBusiness(request, response, model, flag, agentId, channel);
        return url;
    }

    /**
     * 普通活动入口
     * @return
     */
    @RequestMapping("/{channel}/activity/{flag}")
    public String motherDayIndex(@PathVariable String channel, @PathVariable String flag, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        String url = activityBusiness(request, response, model, flag, channel);
        return url;
    }

    /**
     * 指定渠道活动首页实际业务
     * @return
     */
    private String agentActivityBusiness(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, String flag, String agentId, String channel) {
        String returnPage = "";
        if("girl".equals(flag)) {
            //2017年女神节活动
            returnPage = girl2017(request, response, model, flag, agentId, channel);
        }
        return returnPage;
    }

    /**
     * 普通活动首页实际业务
     */
    private String activityBusiness(HttpServletRequest request, HttpServletResponse response,
                                  Map<String, Object> model, String flag, String channel) {
        String returnPage = "";
		if("2018_new_year".equals(flag)) {
            return newYear2018(request, response, model, flag, channel);
        }
		if("2018_lucky_draw".equals(flag)) {
            return luckyDraw2018(request, response, model, flag, channel);
        }        if("2017_christmas_cj9bb90face4de00000000002147483647".equals(flag)) {
            return christmasEve(request, response, model, "2017_christmas", channel);
        }
        if("thanksgiving".equals(flag)){
            return thanksGiving(request, response, model, flag, channel);
        }
        if("youfu".equals(flag)) {
            returnPage = youfuShare(request, response, model, flag, channel);
        }
        if("answer_team".equals(flag)) {
            returnPage = answerTeamInfo(request, response, model, flag, channel);
        }
        if("anniversary_entry".equals(flag)) {
            // 2017年周年庆-入口
            returnPage = anniversary(request, response, model, flag, channel);
        }
        if("lantern".equals(flag)) {
            //2017年元宵活动-元宵喜乐会
            returnPage = lanternFestival(request, response, model, flag, channel);
        }
        if("528".equals(flag)) {
            // 528 活动
            returnPage = activity528(request, response, model, flag, channel);
        }
        if("518".equals(flag)) {
            // 518 “我要发”理财活动
            returnPage = financeDay518(request, response, model, flag, channel);
        }
        if("mother_day_index".equals(flag)) {
            // 母亲节活动
            returnPage = motherDayActivity(request, response, model, flag, channel);
        }
        
        if("pk".equals(flag)) {
            // 12月13日活动--投资PK瓜分两万现金
            returnPage = playerKillingActivity(request, response, model, flag, channel);
        }
        
        if("doubleZero".equals(flag)) {
            // 2016年双旦活动
            returnPage = doubleZeroActivity(request, response, model, flag, channel);
        }
        
        if("treble_gift".equals(flag)){
        	//2017年1月份三重好礼活动
        	returnPage = trebleGift(request, response, model, flag, channel);
        }
        
        if("spring_index".equals(flag)){
        	//2017年踏春活动
        	returnPage = springIndex(request, response, model, flag, channel);
        }
        
        if("spring_index_app".equals(flag)){
        	//2017年踏春活动
        	returnPage = springIndexApp(request, response, model, flag, channel);
        }

        if("2017_618".equals(flag)){
            //2017年618活动
            returnPage = activity2017_618(request, response, model, flag, channel);
        }
        return returnPage;
    }

    /**
     * 公共方法-查询活动是否开始
     */
    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/common/check_before_activity")
    public Map<String, Object> isStart(ReqMsg_Activity_DuringActivity req, @PathVariable String channel, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        ResMsg_Activity_DuringActivity res = (ResMsg_Activity_DuringActivity) communicateBusiness.handleMsg(req);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            result.put("code", res.getResCode());
            result.put("isStart", res.getIsDuringActivity());

            CookieManager cookieManager = new CookieManager(request);
            String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            if(!StringUtils.isEmpty(userIdStr)) {
                result.put("isLogin", "yes");
            } else {
                result.put("isLogin", "no");
            }
        } else {
            result.put("code", res.getResCode());
            result.put("message", res.getResMsg());
        }
        return result;
    }


    //====================================== 2018 新年活动 start ===================================
    private String newYear2018(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, String flag, String channel) {
        String userIdStr = "";
        Integer userId = null;
        if(!CHANNEL_APP.equals(channel)) {
            CookieManager cookieManager = new CookieManager(request);
            userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            userId = StringUtil.isNotBlank(userIdStr) ? Integer.parseInt(userIdStr) : null;

            if(CHANNEL_H5.equals(channel)) {
                String link = GlobEnv.getWebURL("/micro2.0/activity/2018_new_year");
                String shareTitle = "红红火火闹新春，喜喜洋洋分奖金";
                String shareContent = "领个红包过好年，十万奖金来瓜分";
                String logo = GlobEnv.getWebURL("/resources/micro/images/bgw.jpg");
                WeChatShareUtil.share(channel, link, shareTitle, shareContent, logo, true, request, model, weChatUtil);
            }
        } else {
            userIdStr = request.getParameter("userId");
            if(StringUtil.isNotBlank(userIdStr)) {
                userId = Integer.parseInt(new DESUtil("cfgubijn").decryptStr(userIdStr));
                model.put("userIdStr", userIdStr);
                model.put("userId", userId);
            }
            model.put("client", request.getParameter("client"));
        }

        ReqMsg_Activity_NewYear2018First firstReq = new ReqMsg_Activity_NewYear2018First();
        firstReq.setUserId(userId);
        ResMsg_Activity_NewYear2018First firstRes = (ResMsg_Activity_NewYear2018First) communicateBusiness.handleMsg(firstReq);

        ReqMsg_Activity_NewYear2018Second secondReq = new ReqMsg_Activity_NewYear2018Second();
        secondReq.setUserId(userId);
        ResMsg_Activity_NewYear2018Second secondRes = (ResMsg_Activity_NewYear2018Second) communicateBusiness.handleMsg(secondReq);

        ReqMsg_Activity_NewYear2018Third thirdReq = new ReqMsg_Activity_NewYear2018Third();
        thirdReq.setUserId(userId);
        ResMsg_Activity_NewYear2018Third thirdRes = (ResMsg_Activity_NewYear2018Third) communicateBusiness.handleMsg(thirdReq);

        Date now = new Date();
        model.put("year", DateUtil.formatDateTime(now, "yyyy"));
        model.put("month", DateUtil.formatDateTime(now, "MM"));
        model.put("day", DateUtil.formatDateTime(now, "dd"));
        model.put("hour", DateUtil.formatDateTime(now, "HH"));
        model.put("minute", DateUtil.formatDateTime(now, "mm"));
        model.put("second", DateUtil.formatDateTime(now, "ss"));
        model.put("firstRes", firstRes);
        model.put("secondRes", secondRes);
        model.put("thirdRes", thirdRes);
        return channel + "/activity/2018/redlucky/redlucky";
    }

    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/2018/new_year/get_red_packet", method = RequestMethod.POST)
    public Map<String, Object> newYear2018GetRedPacket(HttpServletRequest request, @PathVariable String channel, ReqMsg_Activity_NewYear2018GetRedPacket req) {
        Map<String, Object> model = new HashMap<>();
        if(!CHANNEL_APP.equals(channel)) {
            CookieManager cookieManager = new CookieManager(request);
            String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            if(StringUtil.isNotBlank(userIdStr)) {
                req.setUserId(Integer.parseInt(userIdStr));
            }
        }
        ResMsg_Activity_NewYear2018GetRedPacket res = (ResMsg_Activity_NewYear2018GetRedPacket) communicateBusiness.handleMsg(req);
        model.put("result", res);
        return model;
    }
    //====================================== 2018 新年活动 end ===================================

    //====================================== 2018 年会抽奖活动 start ===================================
    private String luckyDraw2018(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, String flag, String channel) {
        String url = "gen2.0/activity/2018/year_end/lucky_draw";
        ReqMsg_Activity_YearEndQueryList req = new ReqMsg_Activity_YearEndQueryList();
        ResMsg_Activity_YearEndQueryList res = (ResMsg_Activity_YearEndQueryList) communicateBusiness.handleMsg(req);
        model.put("result", res);
        return url;
    }

    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/2018/year_end/lucky_draw", method = RequestMethod.POST)
    public Map<String, Object> christmas_lucky_draw(HttpServletRequest request, @PathVariable String channel, ReqMsg_Activity_YearEndLuckyDraw req) {
        Map<String, Object> model = new HashMap<>();
        ResMsg_Activity_YearEndLuckyDraw res = (ResMsg_Activity_YearEndLuckyDraw) communicateBusiness.handleMsg(req);
        model.put("result", res);
        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/2018/year_end/make_up", method = RequestMethod.POST)
    public Map<String, Object> christmas_make_up(HttpServletRequest request, @PathVariable String channel, ReqMsg_Activity_YearEndMakeupDraw req) {
        Map<String, Object> model = new HashMap<>();
        ResMsg_Activity_YearEndMakeupDraw res = (ResMsg_Activity_YearEndMakeupDraw) communicateBusiness.handleMsg(req);
        model.put("result", res);
        return model;
    }
    //====================================== 2018 年会抽奖活动 end ===================================

    //====================================== 2017 平安夜抽奖活动 start ===================================

    private String christmasEve(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, String flag, String channel) {
        String url = "gen2.0/activity/2017/christmas/wanfen_meeting_2017";
        ReqMsg_Activity_ChrismasQueryList req = new ReqMsg_Activity_ChrismasQueryList();
        ResMsg_Activity_ChrismasQueryList res = (ResMsg_Activity_ChrismasQueryList) communicateBusiness.handleMsg(req);
        model.put("result", res);
        return url;
    }

    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/christmas/lucky_draw", method = RequestMethod.POST)
    public Map<String, Object> christmas_lucky_draw(HttpServletRequest request, @PathVariable String channel, ReqMsg_Activity_ChrismasEveLuckyDraw req) {
        Map<String, Object> model = new HashMap<>();
        ResMsg_Activity_ChrismasEveLuckyDraw res = (ResMsg_Activity_ChrismasEveLuckyDraw) communicateBusiness.handleMsg(req);
        model.put("result", res);
        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/christmas/make_up", method = RequestMethod.POST)
    public Map<String, Object> christmas_make_up(HttpServletRequest request, @PathVariable String channel, ReqMsg_Activity_ChrismasEveMakeupDraw req) {
        Map<String, Object> model = new HashMap<>();
        ResMsg_Activity_ChrismasEveMakeupDraw res = (ResMsg_Activity_ChrismasEveMakeupDraw) communicateBusiness.handleMsg(req);
        model.put("result", res);
        return model;
    }
    //====================================== 2017 平安夜抽奖活动 end ===================================

    //====================================== 2017 感恩节活动 start ===================================

    private String thanksGiving(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, String flag, String channel) {
        String url = channel + "/activity/2017/thanksgiving/" + flag;
        Integer userId = null;
        if(CHANNEL_H5.equals(channel)) {
            String link = GlobEnv.getWebURL("/micro2.0/activity/thanksgiving");
            String shareTitle = "跟着我，三步走，冬日让钱包变暖的小秘诀！";
            String shareContent = "红包、现金和好礼，哪个不能打动你？";
            String logo = GlobEnv.getWebURL("/resources/micro/images/bgw.jpg");
            WeChatShareUtil.share(channel, link, shareTitle, shareContent, logo, true, request, model, weChatUtil);
        } else if(CHANNEL_APP.equals(channel)) {
            String client = request.getParameter("client");
            String userIdStr = null;
            if(CLIENT_IOS.equals(client)) {
                userIdStr = request.getParameter("?userId");
                if(StringUtil.isBlank(userIdStr)) {
                    userIdStr = request.getParameter("userId");
                }
            } else {
                userIdStr = request.getParameter("userId");
            }
            model.put("client", client);
            if(StringUtil.isNotBlank(userIdStr)) {
                userId = Integer.parseInt(new DESUtil("cfgubijn").decryptStr(userIdStr));
                model.put("userId", userId);
            }
        } else if(CHANNEL_PC.equals(channel)) {
            CookieManager cookieManager = new CookieManager(request);
            String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            if(!StringUtils.isEmpty(userIdStr)) {
                userId = Integer.parseInt(userIdStr);
            }

            String link = GlobEnv.getWebURL("/micro2.0/activity/thanksgiving");
            String picName = "thanksgiving_" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss");
            String webPath = request.getSession().getServletContext().getRealPath("/");
            String logoPath = webPath + "/resources/micro2.0/images/qrcode_logo.png";
            MatrixToImageWriter.createMatrixImage(link, picName, logoPath, true);
            String qrImage = GlobEnv.get("wxQRcode.url.prefix") ;
            if(qrImage.charAt(qrImage.length() - 1) != '/'){
                qrImage = qrImage + "/";
            }
            qrImage += picName + ".png";
            model.put("qrImage", qrImage);

            // 活动二
            ReqMsg_Activity_GiftNumber giftNumberReq = new ReqMsg_Activity_GiftNumber();
            giftNumberReq.setUserId(userId);
            ResMsg_Activity_GiftNumber giftNumberRes = (ResMsg_Activity_GiftNumber) communicateBusiness.handleMsg(giftNumberReq);
            List<HashMap<String, Object>> giftNumber = giftNumberRes.getGiftNumber();
            model.put("giftNumber", giftNumber);

            // 活动三
            ReqMsg_Activity_LuckyNumber luckyNumberReq = new ReqMsg_Activity_LuckyNumber();
            luckyNumberReq.setUserId(userId);
            ResMsg_Activity_LuckyNumber luckyNumberRes = (ResMsg_Activity_LuckyNumber) communicateBusiness.handleMsg(luckyNumberReq);
            List<HashMap<String, Object>> luckyNumber = luckyNumberRes.getLuckyNumber();
            List<HashMap<String, Object>> luckyNumberList = luckyNumberRes.getLuckyNumberList();
            model.put("luckyNumber", luckyNumber);
            model.put("luckyNumberList", luckyNumberList);
            model.put("isLogin", luckyNumberRes.getIsLogin());
            model.put("waitPublish", luckyNumberRes.getWaitPublish());
            model.put("isStartThird", luckyNumberRes.getIsStart());
        }
        return url;
    }

    @RequestMapping(value = "/{channel}/activity/thanksgiving/first", method = RequestMethod.GET)
    public String thanksgivingFirst(HttpServletRequest request, @PathVariable String channel, Map<String, Object> model) {
        String url = channel + "/activity/2017/thanksgiving/first";
        ReqMsg_Activity_BaseData req = new ReqMsg_Activity_BaseData();
        req.setActivityType("thanksgiving1");
        ResMsg_Activity_BaseData res = (ResMsg_Activity_BaseData) communicateBusiness.handleMsg(req);
        model.put("isStart", res.getIsStart());
        model.put("startTime", DateUtil.formatDateTime(DateUtil.parse(res.getStartTime(), "yyyy-MM-dd HH:mm:ss"), "yyyy年MM月dd日"));
        model.put("endTime", DateUtil.formatDateTime(DateUtil.parse(res.getEndTime(), "yyyy-MM-dd HH:mm:ss"), "yyyy年MM月dd日"));

        Integer userId = null;

        if(CHANNEL_APP.equals(channel)) {
            String userIdStr = request.getParameter("userId");
            model.put("userId", userIdStr);
            model.put("isLogin", StringUtil.isBlank(userIdStr) ? "no" : "yes");
            model.put("client", request.getParameter("client"));
            if(!StringUtil.isBlank(userIdStr)) {
                userId = Integer.parseInt(userIdStr);
            }

            String link = GlobEnv.getWebURL("/micro2.0/activity/thanksgiving");
            String picName = "thanksgiving_" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss");
            String webPath = request.getSession().getServletContext().getRealPath("/");
            String logoPath = webPath + "/resources/micro2.0/images/qrcode_logo.png";
            MatrixToImageWriter.createMatrixImage(link, picName, logoPath, true);
            String qrImage = GlobEnv.get("wxQRcode.url.prefix") ;
            if(qrImage.charAt(qrImage.length() - 1) != '/'){
                qrImage = qrImage + "/";
            }
            qrImage += picName + ".png";
            model.put("qrImage", qrImage);
        } else if (CHANNEL_H5.equals(channel)) {
            CookieManager cookieManager = new CookieManager(request);
            String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            if(!StringUtils.isEmpty(userIdStr)) {
                userId = Integer.parseInt(userIdStr);
            }
            model.put("isLogin", userId == null ? "no" : "yes");
            model.put("thanksgivingConstant", "thanksgiving");

            String link = GlobEnv.getWebURL("/micro2.0/activity/thanksgiving");
            String shareTitle = "跟着我，三步走，冬日让钱包变暖的小秘诀！";
            String shareContent = "红包、现金和好礼，哪个不能打动你？";
            String logo = GlobEnv.getWebURL("/resources/micro/images/bgw.jpg");
            WeChatShareUtil.share(channel, link, shareTitle, shareContent, logo, true, request, model, weChatUtil);
        }
        ReqMsg_Activity_SharePageInfo shareReq = new ReqMsg_Activity_SharePageInfo();
        shareReq.setUserId(userId);
        ResMsg_Activity_SharePageInfo shareRes = (ResMsg_Activity_SharePageInfo) communicateBusiness.handleMsg(shareReq);

        model.put("isJoined", shareRes.getIsJoined());
        return url;
    }

    @RequestMapping(value = "/{channel}/activity/thanksgiving/second", method = RequestMethod.GET)
    public String thanksgivingSecond(HttpServletRequest request, @PathVariable String channel, Map<String, Object> model) {
        ReqMsg_Activity_BaseData req = new ReqMsg_Activity_BaseData();
        req.setActivityType("thanksgiving2");
        ResMsg_Activity_BaseData res = (ResMsg_Activity_BaseData) communicateBusiness.handleMsg(req);
        model.put("isStart", res.getIsStart());
        model.put("startTime", DateUtil.formatDateTime(DateUtil.parse(res.getStartTime(), "yyyy-MM-dd HH:mm:ss"), "yyyy年MM月dd日"));
        model.put("endTime", DateUtil.formatDateTime(DateUtil.parse(res.getEndTime(), "yyyy-MM-dd HH:mm:ss"), "yyyy年MM月dd日"));

        Integer userId = null;
        // 活动二
        if(CHANNEL_APP.equals(channel)) {
            String userIdStr = request.getParameter("userId");
            model.put("userId", userIdStr);
            model.put("isLogin", StringUtil.isBlank(userIdStr) ? "no" : "yes");
            userId = StringUtil.isBlank(userIdStr) ? null : Integer.parseInt(userIdStr);
            model.put("client", request.getParameter("client"));
        } else if (CHANNEL_H5.equals(channel)) {
            String link = GlobEnv.getWebURL("/micro2.0/activity/thanksgiving");
            String shareTitle = "跟着我，三步走，冬日让钱包变暖的小秘诀！";
            String shareContent = "红包、现金和好礼，哪个不能打动你？";
            String logo = GlobEnv.getWebURL("/resources/micro/images/bgw.jpg");
            WeChatShareUtil.share(channel, link, shareTitle, shareContent, logo, true, request, model, weChatUtil);

            CookieManager cookieManager = new CookieManager(request);
            String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            if(!StringUtils.isEmpty(userIdStr)) {
                userId = Integer.parseInt(userIdStr);
            }
        }

        ReqMsg_Activity_GiftNumber giftNumberReq = new ReqMsg_Activity_GiftNumber();
        giftNumberReq.setUserId(userId);
        ResMsg_Activity_GiftNumber giftNumberRes = (ResMsg_Activity_GiftNumber) communicateBusiness.handleMsg(giftNumberReq);
        List<HashMap<String, Object>> giftNumber = giftNumberRes.getGiftNumber();
        model.put("giftNumber", giftNumber);
        String url = channel + "/activity/2017/thanksgiving/second";
        return url;
    }

    @RequestMapping(value = "/{channel}/activity/thanksgiving/third", method = RequestMethod.GET)
    public String thanksgivingThird(HttpServletRequest request, @PathVariable String channel, Map<String, Object> model) {
        ReqMsg_Activity_BaseData req = new ReqMsg_Activity_BaseData();
        req.setActivityType("thanksgiving3");
        ResMsg_Activity_BaseData res = (ResMsg_Activity_BaseData) communicateBusiness.handleMsg(req);
        model.put("isStart", res.getIsStart());
        model.put("startTime", DateUtil.formatDateTime(DateUtil.parse(res.getStartTime(), "yyyy-MM-dd HH:mm:ss"), "yyyy年MM月dd日"));
        model.put("endTime", DateUtil.formatDateTime(DateUtil.parse(res.getEndTime(), "yyyy-MM-dd HH:mm:ss"), "yyyy年MM月dd日"));

        // 活动三
        Integer userId = null;
        if(CHANNEL_APP.equals(channel)) {
            String userIdStr = request.getParameter("userId");
            model.put("userId", userIdStr);
            model.put("isLogin", StringUtil.isBlank(userIdStr) ? "no" : "yes");
            userId = StringUtil.isBlank(userIdStr) ? null : Integer.parseInt(userIdStr);
            model.put("client", request.getParameter("client"));
        } else {
            CookieManager cookieManager = new CookieManager(request);
            String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            if(!StringUtils.isEmpty(userIdStr)) {
                userId = Integer.parseInt(userIdStr);
            }
            String link = GlobEnv.getWebURL("/micro2.0/activity/thanksgiving");
            String shareTitle = "跟着我，三步走，冬日让钱包变暖的小秘诀！";
            String shareContent = "红包、现金和好礼，哪个不能打动你？";
            String logo = GlobEnv.getWebURL("/resources/micro/images/bgw.jpg");
            WeChatShareUtil.share(channel, link, shareTitle, shareContent, logo, true, request, model, weChatUtil);
        }

        ReqMsg_Activity_LuckyNumber luckyNumberReq = new ReqMsg_Activity_LuckyNumber();
        luckyNumberReq.setUserId(userId);
        ResMsg_Activity_LuckyNumber luckyNumberRes = (ResMsg_Activity_LuckyNumber) communicateBusiness.handleMsg(luckyNumberReq);
        List<HashMap<String, Object>> luckyNumber = luckyNumberRes.getLuckyNumber();
        List<HashMap<String, Object>> luckyNumberList = luckyNumberRes.getLuckyNumberList();
        model.put("luckyNumber", luckyNumber);
        model.put("luckyNumberList", luckyNumberList);
        model.put("isLogin", luckyNumberRes.getIsLogin());
        model.put("isStartThird", luckyNumberRes.getIsStart());
        model.put("waitPublish", luckyNumberRes.getWaitPublish());

        String url = channel + "/activity/2017/thanksgiving/third";
        return url;
    }

    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/thanksgiving/exchange", method = RequestMethod.POST)
    public Map<String, Object> thanksgivingExchange(ReqMsg_Activity_ExchangeGift req, HttpServletRequest request, @PathVariable String channel) {
        Map<String, Object> result = new HashMap<>();

        Integer userId = null;
        if(CHANNEL_APP.equals(channel)) {
            userId = req.getUserId();
        } else {
            CookieManager cookieManager = new CookieManager(request);
            String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            if(!StringUtils.isEmpty(userIdStr)) {
                userId = Integer.parseInt(userIdStr);
            }
        }

        req.setUserId(userId);
        ResMsg_Activity_ExchangeGift res = (ResMsg_Activity_ExchangeGift) communicateBusiness.handleMsg(req);
        result.put("luckyDrawId", res.getLuckyDrawId());
        result.put("haveAddress", res.isHaveAddress() ? "yes" : "no");
        result.put("code", res.getResCode());
        result.put("message", res.getResMsg());
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/thanksgiving/add_address", method = RequestMethod.POST)
    public Map<String, Object> thanksgivingAddAddress(ReqMsg_Activity_AddAddress req, HttpServletRequest request, @PathVariable String channel) {
        Map<String, Object> result = new HashMap<>();

        Integer userId = null;
        if(CHANNEL_APP.equals(channel)) {
            userId = req.getUserId();
        } else {
            CookieManager cookieManager = new CookieManager(request);
            String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            if(!StringUtils.isEmpty(userIdStr)) {
                userId = Integer.parseInt(userIdStr);
            }
        }
        req.setUserId(userId);
        ResMsg_Activity_AddAddress res = (ResMsg_Activity_AddAddress) communicateBusiness.handleMsg(req);
        result.put("code", res.getResCode());
        result.put("message", res.getResMsg());
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/thanksgiving/share", method = RequestMethod.POST)
    public Map<String, Object> thanksgivingShare(ReqMsg_Activity_Share req, HttpServletRequest request, @PathVariable String channel) {
        logger.info("感恩节活动分享，回调保存当前记录");
        Map<String, Object> result = new HashMap<>();

        Integer userId = null;
        CookieManager cookieManager = new CookieManager(request);
        String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        if(!StringUtils.isEmpty(userIdStr)) {
            userId = Integer.parseInt(userIdStr);
        }
        req.setUserId(userId);
        ResMsg_Activity_Share res = (ResMsg_Activity_Share) communicateBusiness.handleMsg(req);

        result.put("code", res.getResCode());
        result.put("message", res.getResMsg());
        logger.info("感恩节活动分享，回调保存当前记录返回结果：{}", result);
        return result;
    }
    //====================================== 2017 感恩节活动 end ===================================

    //====================================== 2017 友富同享邀请活动 start ===================================
    private String youfuShare(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, String flag, String channel) {
        String url = channel + "/activity/2017/youfu/" + flag;
        Integer userId = null;
        if(CHANNEL_H5.equals(channel)) {
            String link = GlobEnv.getWebURL("/micro2.0/activity/youfu");
            String shareTitle = "友富同享，携手共夺800元现金！";
            String shareContent = "同时享最高1%奖励金，错过不再有！";
            String logo = GlobEnv.getWebURL("/resources/micro/images/bgw.jpg");
            WeChatShareUtil.share(channel, link, shareTitle, shareContent, logo, true, request, model, weChatUtil);
        }
        if(CHANNEL_APP.equals(channel)) {
            // app进入
            String client = request.getParameter("client");
            String userIdStr = null;
            if(CLIENT_IOS.equals(client)) {
                userIdStr = request.getParameter("?userId");
                if(StringUtil.isBlank(userIdStr)) {
                    userIdStr = request.getParameter("userId");
                }
            } else {
                userIdStr = request.getParameter("userId");
            }
            model.put("client", client);
            if(StringUtil.isNotBlank(userIdStr)) {
                userId = Integer.parseInt(new DESUtil("cfgubijn").decryptStr(userIdStr));
                model.put("userId", userIdStr);
                CookieManager manager = new CookieManager(request);
                manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(),
                        String.valueOf(userId), true);
                manager.save(response, CookieEnums._SITE.getName(), null, "/", 5*365 * 24 * 3600, true);
            }
        } else {
            CookieManager cookieManager = new CookieManager(request);
            String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            if(!StringUtils.isEmpty(userIdStr)) {
                userId = Integer.parseInt(userIdStr);
            }
        }
        ReqMsg_Activity_BaseData req = new ReqMsg_Activity_BaseData();
        req.setActivityType(flag);
        ResMsg_Activity_BaseData res = (ResMsg_Activity_BaseData) communicateBusiness.handleMsg(req);
        model.put("isStart", res.getIsStart());
        model.put("startTime", DateUtil.formatDateTime(DateUtil.parse(res.getStartTime(), "yyyy-MM-dd HH:mm:ss"), "yyyy年MM月dd日"));
        model.put("endTime", DateUtil.formatDateTime(DateUtil.parse(res.getEndTime(), "yyyy-MM-dd HH:mm:ss"), "yyyy年MM月dd日"));

        ReqMsg_Activity_YouFuRankInfo rankReq = new ReqMsg_Activity_YouFuRankInfo();
        ResMsg_Activity_YouFuRankInfo rankRes = (ResMsg_Activity_YouFuRankInfo) communicateBusiness.handleMsg(rankReq);
        model.put("rankList", rankRes.getRankList());

        if(null != userId) {
            model.put("isLogin", "yes");
            ReqMsg_Activity_YouFuSelfInfo selfReq = new ReqMsg_Activity_YouFuSelfInfo();
            selfReq.setUserId(userId);
            ResMsg_Activity_YouFuSelfInfo selfRes = (ResMsg_Activity_YouFuSelfInfo) communicateBusiness.handleMsg(selfReq);
            model.put("result", selfRes);
        } else {
            model.put("isLogin", "no");
        }
        return url;
    }

    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/youfu/self", method = RequestMethod.POST)
    public Map<String, Object> youfuSelf(ReqMsg_Activity_YouFuSelfInfo req, HttpServletRequest request, @PathVariable String channel) {
        Integer userId = null;
        if(CHANNEL_APP.equals(channel)) {
            // app进入
            String userIdStr = request.getParameter("userIdStr");
            if(StringUtil.isNotBlank(userIdStr)) {
                userId = Integer.parseInt(new DESUtil("cfgubijn").decryptStr(userIdStr));
            }
        } else {
            CookieManager cookieManager = new CookieManager(request);
            String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            if(!StringUtils.isEmpty(userIdStr)) {
                userId = Integer.parseInt(userIdStr);
            }
        }

        Map<String, Object> result = new HashMap<>();
        req.setUserId(userId);
        ResMsg_Activity_YouFuSelfInfo res = (ResMsg_Activity_YouFuSelfInfo) communicateBusiness.handleMsg(req);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            result.put("code", ConstantUtil.BSRESCODE_SUCCESS);
            result.put("result", res);
        } else {
            result.put("code", res.getResCode());
            result.put("message", StringUtils.isEmpty(res.getResMsg()) ? "请稍后再试" : res.getResMsg());
        }
        return result;
    }
    //====================================== 2017 友富同享邀请活动 end ===================================


    //====================================== 2017存管答题抱团活动 start ===================================

    /**
     * 答题抱团活动页
     */
    private String answerTeamInfo(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, String flag, String channel) {
        Integer userId = null;
        if(CHANNEL_APP.equals(channel)) {
            // app进入
            String client = request.getParameter("client");
            String userIdStr = null;
            if(CLIENT_IOS.equals(client)) {
                userIdStr = request.getParameter("?userId");
                if(StringUtil.isBlank(userIdStr)) {
                    userIdStr = request.getParameter("userId");
                }
            } else {
                userIdStr = request.getParameter("userId");
            }
            model.put("client", client);
            if(StringUtil.isNotBlank(userIdStr)) {
                userId = Integer.parseInt(new DESUtil("cfgubijn").decryptStr(userIdStr));
                model.put("userId", userIdStr);
            }
        } else {
            CookieManager cookieManager = new CookieManager(request);
            String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            if(!StringUtils.isEmpty(userIdStr)) {
                userId = Integer.parseInt(userIdStr);
            }
        }
        // 答题抱团活动二维码
        String link = GlobEnv.getWebURL("/micro2.0/activity/answer_team");
        String webPath = request.getSession().getServletContext().getRealPath("/");
        String logoPath = webPath + "/resources/micro2.0/images/qrcode_logo.png";
        MatrixToImageWriter.createMatrixImage(link, "answer_team_share", logoPath, true);
        String matrix = GlobEnv.get("wxQRcode.url.prefix") ;
        if(matrix.charAt(matrix.length() - 1) != '/'){
            matrix = matrix + "/";
        }
        matrix += "answer_team_share.png";
        model.put("matrix", matrix);

        // 点亮活动二维码
        String lightLink = GlobEnv.getWebURL("/micro2.0/activity/lightUpIndex");
        MatrixToImageWriter.createMatrixImage(lightLink, "light_share", logoPath, true);
        String lightQR = GlobEnv.get("wxQRcode.url.prefix") ;
        if(lightQR.charAt(lightQR.length() - 1) != '/'){
            lightQR = lightQR + "/";
        }
        lightQR += "light_share.png";
        model.put("lightQR", lightQR);

        // 周年庆普通分享文案、图片
        String shareTitle = "参与答题参团，心仪红包，等你来领！";
        String shareContent = "庆贺存管上线，争领千元红包！参与答题、抱团即可获得！难度超低！奖励超大！";
        String logo = GlobEnv.getWebURL("/resources/micro2.0/images/bgw_logo.png");
        WeChatShareUtil.share(channel, link, shareTitle, shareContent, logo, true, request, model, weChatUtil);
        ReqMsg_Activity_DepActivityInfo req = new ReqMsg_Activity_DepActivityInfo();
        req.setUserId(userId);
        ResMsg_Activity_DepActivityInfo res = (ResMsg_Activity_DepActivityInfo) communicateBusiness.handleMsg(req);
        model.put("result", res);
        return channel + "/activity/answer_team/answer_team";
    }

    /**
     * 参与抱团
     */
    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/answer_team/join_team", method = RequestMethod.POST)
    public Map<String, Object> joinTeam(ReqMsg_Activity_JoinTeam req, HttpServletRequest request, @PathVariable String channel) {
        Integer userId = null;
        if(CHANNEL_APP.equals(channel)) {
            // app进入
            String userIdStr = request.getParameter("userIdStr");
            if(StringUtil.isNotBlank(userIdStr)) {
                userId = Integer.parseInt(new DESUtil("cfgubijn").decryptStr(userIdStr));
            }
        } else {
            CookieManager cookieManager = new CookieManager(request);
            String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            if(!StringUtils.isEmpty(userIdStr)) {
                userId = Integer.parseInt(userIdStr);
            }
        }

        Map<String, Object> result = new HashMap<>();
        req.setUserId(userId);
        ResMsg_Activity_JoinTeam res = (ResMsg_Activity_JoinTeam) communicateBusiness.handleMsg(req);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            result.put("code", ConstantUtil.BSRESCODE_SUCCESS);
            result.put("result", res);
        } else {
            result.put("code", res.getResCode());
            result.put("message", StringUtils.isEmpty(res.getResMsg()) ? "请稍后再试" : res.getResMsg());
        }
        return result;
    }

    /**
     * 参与答题
     */
    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/answer_team/answer", method = RequestMethod.POST)
    public Map<String, Object> answer(ReqMsg_Activity_Answer req, HttpServletRequest request, @PathVariable String channel) {
        Integer userId = null;
        if(CHANNEL_APP.equals(channel)) {
            // app进入
            String userIdStr = request.getParameter("userIdStr");
            if(StringUtil.isNotBlank(userIdStr)) {
                userId = Integer.parseInt(new DESUtil("cfgubijn").decryptStr(userIdStr));
            }
        } else {
            CookieManager cookieManager = new CookieManager(request);
            String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            if(!StringUtils.isEmpty(userIdStr)) {
                userId = Integer.parseInt(userIdStr);
            }
        }

        Map<String, Object> result = new HashMap<>();
        req.setUserId(userId);
        ResMsg_Activity_Answer res = (ResMsg_Activity_Answer) communicateBusiness.handleMsg(req);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            result.put("code", ConstantUtil.BSRESCODE_SUCCESS);
            result.put("result", res);
        } else {
            result.put("code", res.getResCode());
            result.put("message", StringUtils.isEmpty(res.getResMsg()) ? "请稍后再试" : res.getResMsg());
        }
        return result;
    }
    //====================================== 2017存管答题抱团活动 end ===================================


    //====================================== 2017周年庆活动 start ===================================
    /**
     * 周年庆-入口页面
     */
    private String anniversary(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, String flag, String channel) {
        CookieManager cookieManager = new CookieManager(request);
        String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        Integer userId = null;
        if(!StringUtils.isEmpty(userIdStr)) {
            userId = Integer.parseInt(userIdStr);
        }

        // PC访问路径就是第一期
        if(Constants.CHANNEL_GEN.equals(channel)) {
            // PC 入口页面就是第一期页面
            // 1. 入口页面信息
            ReqMsg_Activity_AnniversaryEntryPageInfo req = new ReqMsg_Activity_AnniversaryEntryPageInfo();
            req.setUserId(userId);
            ResMsg_Activity_AnniversaryEntryPageInfo res = (ResMsg_Activity_AnniversaryEntryPageInfo) communicateBusiness.handleMsg(req);
            model.put("entryInfo", res);

            // 2. 第一期活动信息
            ReqMsg_Activity_AnniversaryFirstHomePageInfo firstPageInfoReq = new ReqMsg_Activity_AnniversaryFirstHomePageInfo();
            firstPageInfoReq.setUserId(userId);
            ResMsg_Activity_AnniversaryFirstHomePageInfo firstPageInfoRes = (ResMsg_Activity_AnniversaryFirstHomePageInfo) communicateBusiness.handleMsg(firstPageInfoReq);
            model.put("firstPageInfo", firstPageInfoRes);

            if(null != userId) {
                Map<String,String> sign = weChatUtil.createAuth(request);
                String link = GlobEnv.getWebURL("/micro2.0/activity/anniversary_share?userId=" + userId + "&menu=yes");
                sign.put("link", link);
                String webPath = request.getSession().getServletContext().getRealPath("/");
                String logoPath = webPath + "/resources/micro2.0/images/qrcode_logo.png";
                MatrixToImageWriter.createMatrixImage(link, userIdStr + "_anniversary", logoPath, true);
                String matrix = GlobEnv.get("wxQRcode.url.prefix") ;
                if(matrix.charAt(matrix.length() - 1) != '/'){
                    matrix = matrix + "/";
                }
                matrix += userId + "_anniversary.png";
                model.put("matrix", matrix);
            } else {
                model.put("matrix", "");
            }
            return channel + "/activity/anniversary/anniversary_first";
        } else if(Constants.CHANNEL_MICRO.equals(channel)) {
            // H5 入口页面是单独的页面
            // 1. 分享
            anniversarySimpleShare(channel, request, model);

            // 2. 入口页面信息
            ReqMsg_Activity_AnniversaryEntryPageInfo req = new ReqMsg_Activity_AnniversaryEntryPageInfo();
            req.setUserId(userId);
            ResMsg_Activity_AnniversaryEntryPageInfo res = (ResMsg_Activity_AnniversaryEntryPageInfo) communicateBusiness.handleMsg(req);
            model.put("result", res);
            return channel + "/activity/anniversary/" + flag;
        }
        return channel + "/activity/anniversary/" + flag;
    }

    /**
     * 周年庆-第一期活动页面
     */
    @RequestMapping(value = "/{channel}/activity/anniversary_first")
    public String anniversaryFirst(@PathVariable  String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        CookieManager cookieManager = new CookieManager(request);
        String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        Integer userId = null;
        if(!StringUtils.isEmpty(userIdStr)) {
            userId = Integer.parseInt(userIdStr);
        }

        if(Constants.CHANNEL_MICRO.equals(channel)) {
            // 1. 邀请分享
            anniversaryRecommendShare(channel, request, model);

            // 2. 第一期活动信息
            ReqMsg_Activity_AnniversaryFirstHomePageInfo firstPageInfoReq = new ReqMsg_Activity_AnniversaryFirstHomePageInfo();
            firstPageInfoReq.setUserId(userId);
            ResMsg_Activity_AnniversaryFirstHomePageInfo firstPageInfoRes = (ResMsg_Activity_AnniversaryFirstHomePageInfo) communicateBusiness.handleMsg(firstPageInfoReq);
            model.put("firstPageInfo", firstPageInfoRes);
        } else if(Constants.CHANNEL_GEN.equals(channel)) {
            this.anniversary(request, response, model, "anniversary_first", channel);
        }
        return channel + "/activity/anniversary/anniversary_first";
    }

    /**
     * 周年庆-第一期分享页面
     */
    @RequestMapping(value = "/micro2.0/activity/anniversary_share")
    public String anniversaryShare(HttpServletRequest request, Map<String, Object> model) {
        model.put("isSelf", "no");
        CookieManager cookieManager = new CookieManager(request);
        String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        Integer shareUserId = null;

        String shareUserIdStr = request.getParameter("userId");
        if(!StringUtils.isEmpty(shareUserIdStr)) {
            model.put("shareUserId", shareUserIdStr);
            shareUserId = Integer.parseInt(shareUserIdStr);
            if(shareUserIdStr.equals(userIdStr)) {
                model.put("isSelf", "yes");
            }

            String user = shareUserId + UUID.randomUUID().toString();
            model.put("user", user);
        }
        // 1. 分享
        if(!StringUtils.isEmpty(userIdStr) && userIdStr.equals(shareUserIdStr)) {
            anniversaryRecommendShare("micro2.0", request, model);
        } else {
            anniversarySimpleShare("micro2.0", request, model);
        }

        // 2. 分享页面信息
        String opennId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_OPEN_ID.getName(), true);
        ReqMsg_Activity_AnniversarySharePageInfo req = new ReqMsg_Activity_AnniversarySharePageInfo();
        req.setShareUserId(shareUserId);
        req.setOpenId(opennId);
        req.setPageNum(1);
        req.setNumPerPage(Integer.MAX_VALUE);
        ResMsg_Activity_AnniversarySharePageInfo res = (ResMsg_Activity_AnniversarySharePageInfo) communicateBusiness.handleMsg(req);
        model.put("req", req);
        model.put("result", res);
        return  "micro2.0/activity/anniversary/anniversary_share";
    }

    /**
     * 分页查询助力好友列表
     */
    @ResponseBody
    @RequestMapping(value = "/micro2.0/activity/anniversary_friend/list", method = RequestMethod.GET)
    public Map<String, Object> anniversaryFriendList(ReqMsg_Activity_AnniversarySharePageInfo req) {
        req.setNumPerPage(10);
        Map<String, Object> result = new HashMap<>();
        ResMsg_Activity_AnniversarySharePageInfo res = (ResMsg_Activity_AnniversarySharePageInfo) communicateBusiness.handleMsg(req);
        result.put("result", res);
        return result;
    }

    /**
     * 好友点击助力
     */
    @ResponseBody
    @RequestMapping(value = "/micro2.0/activity/anniversary/help", method = RequestMethod.POST)
    public Map<String, Object> anniversaryhHelp(ReqMsg_Activity_AnniversaryHelpFriend req, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        CookieManager cookieManager = new CookieManager(request);
        String wxNick = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_WX_NICK.getName(), true);
        String openId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_OPEN_ID.getName(), true);
        String imgUrl = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_WX_HEAD_IMG_URL.getName(), true);
        req.setWxHeadImg(imgUrl);
        req.setOpenId(openId);
        req.setWxNick(wxNick);
        ResMsg_Activity_AnniversaryHelpFriend res = (ResMsg_Activity_AnniversaryHelpFriend) communicateBusiness.handleMsg(req);
        result.put("result", res);
        return result;
    }


    /**
     * 周年庆-第二期活动页面
     */
    @RequestMapping(value = "/{channel}/activity/anniversary_second")
    public String anniversarySecond(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        CookieManager cookieManager = new CookieManager(request);
        String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        Integer userId = null;
        if(!StringUtils.isEmpty(userIdStr)) {
            userId = Integer.parseInt(userIdStr);
        }
        if(Constants.CHANNEL_MICRO.equals(channel)) {
            // 1. 普通分享
            anniversarySimpleShare(channel, request, model);
        } else if(Constants.CHANNEL_GEN.equals(channel)) {
            // 1. 入口页面信息
            ReqMsg_Activity_AnniversaryEntryPageInfo req = new ReqMsg_Activity_AnniversaryEntryPageInfo();
            req.setUserId(userId);
            ResMsg_Activity_AnniversaryEntryPageInfo res = (ResMsg_Activity_AnniversaryEntryPageInfo) communicateBusiness.handleMsg(req);
            model.put("entryInfo", res);
        }
        // 2. 第二期活动信息
        ReqMsg_Activity_AnniversarySecondHomePageInfo secondPageInfoReq = new ReqMsg_Activity_AnniversarySecondHomePageInfo();
        secondPageInfoReq.setUserId(userId);
        ResMsg_Activity_AnniversarySecondHomePageInfo secondPageInfo = (ResMsg_Activity_AnniversarySecondHomePageInfo) communicateBusiness.handleMsg(secondPageInfoReq);
        model.put("secondPageInfo", secondPageInfo);
        List<HashMap<String, Object>> userList = secondPageInfo.getInvestUserList();
        if(CollectionUtils.isEmpty(userList)) {
            userList = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("rank", i + 1);
                map.put("userName", "----");
                map.put("amount", "----");
                userList.add(map);
            }
            secondPageInfo.setInvestUserList(userList);
        } else {
            for (int i = userList.size(); i < 15; i++) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("rank", i + 1);
                map.put("userName", "----");
                map.put("amount", "----");
                userList.add(map);
            }
        }
        return channel + "/activity/anniversary/anniversary_second";
    }

    /**
     * 添加祝福语
     */
    @ResponseBody
    @RequestMapping(value = "{channel}/activity/anniversary_benison")
    public Map<String, Object> saveBenison(@PathVariable String channel, HttpServletRequest request, ReqMsg_Activity_AnniversaryBenison req) {
        Map<String, Object> result = new HashMap<>();
        CookieManager cookieManager = new CookieManager(request);
        String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        Integer userId = null;
        if(!StringUtils.isEmpty(userIdStr)) {
            userId = Integer.parseInt(userIdStr);
        }
        req.setUserId(userId);
        if(!StringUtils.isEmpty(req.getContent())) {
            ResMsg_Activity_AnniversaryBenison res = (ResMsg_Activity_AnniversaryBenison) communicateBusiness.handleMsg(req);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                result.put("code", res.getResCode());
                result.put("result", res);
            } else {
                result.put("code", res.getResCode());
                result.put("message", res.getResMsg());
            }
        } else {
            result.put("code", "params_error");
            result.put("message", "亲，请填写祝福语");
        }
        return result;
    }

    /**
     * 周年庆-第三期活动页面
     */
    @RequestMapping(value = "/{channel}/activity/anniversary_third")
    public String anniversaryThird(@PathVariable  String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        CookieManager cookieManager = new CookieManager(request);
        String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        Integer userId = null;
        if(!StringUtils.isEmpty(userIdStr)) {
            userId = Integer.parseInt(userIdStr);
        }
        if(Constants.CHANNEL_MICRO.equals(channel)) {
            // 1. 普通分享
            anniversarySimpleShare(channel, request, model);
        } else if(Constants.CHANNEL_GEN.equals(channel)) {
            // 1. 入口页面信息
            ReqMsg_Activity_AnniversaryEntryPageInfo req = new ReqMsg_Activity_AnniversaryEntryPageInfo();
            req.setUserId(userId);
            ResMsg_Activity_AnniversaryEntryPageInfo res = (ResMsg_Activity_AnniversaryEntryPageInfo) communicateBusiness.handleMsg(req);
            model.put("entryInfo", res);
        }
        // 2. 第三期活动信息
        ReqMsg_Activity_AnniversaryThirdHomePageInfo thirdPageInfoReq = new ReqMsg_Activity_AnniversaryThirdHomePageInfo();
        thirdPageInfoReq.setUserId(userId);
        ResMsg_Activity_AnniversaryThirdHomePageInfo thirdPageInfo = (ResMsg_Activity_AnniversaryThirdHomePageInfo) communicateBusiness.handleMsg(thirdPageInfoReq);
        List<HashMap<String, Object>> todayInvestUserList = thirdPageInfo.getTodayInvestUserList();
        if(CollectionUtils.isEmpty(todayInvestUserList)) {
            todayInvestUserList = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("rank", i);
                map.put("userName", "----");
                map.put("amount", "----");
                todayInvestUserList.add(map);
            }
            thirdPageInfo.setTodayInvestUserList(todayInvestUserList);
        } else {
            for (int i = todayInvestUserList.size(); i < 10; i++) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("rank", i + 1);
                map.put("userName", "----");
                map.put("amount", "----");
                todayInvestUserList.add(map);
            }
        }
        model.put("thirdPageInfo", thirdPageInfo);

        return channel + "/activity/anniversary/anniversary_third";
    }

    /**
     * 第三期-查看我已瓜分到的奖金
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/anniversary/user_award", method = RequestMethod.POST)
    public Map<String, Object> anniversarySelfAward(@PathVariable  String channel, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        CookieManager cookieManager = new CookieManager(request);
        String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        Integer userId = null;
        if(!StringUtils.isEmpty(userIdStr)) {
            userId = Integer.parseInt(userIdStr);
        }
        ReqMsg_Activity_AnniversaryThirdUserInvestList req = new ReqMsg_Activity_AnniversaryThirdUserInvestList();
        req.setUserId(userId);
        ResMsg_Activity_AnniversaryThirdUserInvestList res = (ResMsg_Activity_AnniversaryThirdUserInvestList) communicateBusiness.handleMsg(req);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            result.put("code", res.getResCode());
            result.put("isStart", res.getIsStart());
            result.put("isLogin", res.getIsLogin());
            result.put("list", res.getUserAwardList());
        } else {
            result.put("code", res.getResCode());
            result.put("message", res.getResMsg());
        }
        return result;
    }

    /**
     * 第三期-查看获奖名单
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/anniversary/winner_list", method = RequestMethod.POST)
    public Map<String, Object> anniversaryWinnerList(@PathVariable  String channel, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        ReqMsg_Activity_AnniversaryThirdWinnerList req = new ReqMsg_Activity_AnniversaryThirdWinnerList();
        ResMsg_Activity_AnniversaryThirdWinnerList res = (ResMsg_Activity_AnniversaryThirdWinnerList) communicateBusiness.handleMsg(req);
        result.put("res", res);
        return result;
    }

    /**
     * 第三期-抽奖
     */
    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/anniversary/lucky_draw", method = RequestMethod.POST)
    public Map<String, Object> anniversaryLuckuyDraw(@PathVariable String channel, ReqMsg_Activity_AnniversaryLuckyDraw req, HttpServletRequest request) {
        CookieManager cookieManager = new CookieManager(request);
        String userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        Integer userId = null;
        if(!StringUtils.isEmpty(userIdStr)) {
            userId = Integer.parseInt(userIdStr);
        }
        req.setUserId(userId);
        Map<String, Object> result = new HashMap<>();
        ResMsg_Activity_AnniversaryLuckyDraw res = (ResMsg_Activity_AnniversaryLuckyDraw) communicateBusiness.handleMsg(req);
        result.put("result", res);
        return result;
    }

    private void anniversarySimpleShare(String channel, HttpServletRequest request, Map<String, Object> model) {
        // 周年庆普通分享文案、图片
        String link = GlobEnv.getWebURL("/micro2.0/activity/anniversary_entry");
        String shareTitle = "史上最强活动来袭！Ta用这个狠狠赚了一笔！";
        String shareContent = "币港湾周年庆，活动6重，奖励不止6重。全场加息，还可送祝福免费参与抽奖。活动期间投资，获得更多收益。这个时间投资，绝对值！";
        String logo = GlobEnv.getWebURL("/resources/micro2.0/images/share/anniversary.jpg");
        WeChatShareUtil.share(channel, link, shareTitle, shareContent, logo, true, request, model, weChatUtil);
    }

    private void anniversaryRecommendShare(String channel, HttpServletRequest request, Map<String, Object> model) {
        // 周年庆邀请分享文案、图片
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        String link = GlobEnv.getWebURL("/micro2.0/activity/anniversary_share?userId=") + userId + "&menu=yes";
        String title = "您的好友邀您助力并领取318元红包！";
        String content = "币港湾周年庆，活动6重，奖励不止6重。全场加息，还可送祝福免费参与抽奖。活动期间投资，获得更多收益。这个时间投资，绝对值！";
        String logo = GlobEnv.getWebURL("/resources/micro2.0/images/share/anniversary.jpg");
        WeChatShareUtil.share(channel, link, title, content, logo, true, request, model, weChatUtil);
    }
    //====================================== 2017周年庆活动 end ===================================

    //====================================== 2017女神节活动 start ===================================

    /**
     * 女神节活动首页信息
     * @return
     */
    private String girl2017(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, String flag, String agentId, String channel) {

        String agentViewFlag = request.getParameter("agentViewFlag");
        if(!StringUtils.isEmpty(agentId)) {
            if(!agentId.equals(agentViewFlag)) {
                String pre = "gen178".equals(channel) ? "gen2.0" : channel;
                return "redirect:/" + pre  +"/index";
            }
        } else {
            String pre = "gen178".equals(channel) ? "gen2.0" : channel;
            return "redirect:/" + pre  +"/index";
        }
        if(!("15".equals(agentId) || "36".equals(agentId) || "46".equals(agentId) || "47".equals(agentId))) {
            String pre = "gen178".equals(channel) ? "gen2.0" : channel;
            return "redirect:/" + pre  +"/index";
        }

        // 分享文案、图片
        String link = GlobEnv.getWebURL("micro2.0/activity/girl/") + agentId + "?qianbao=qianbao&agentViewFlag=" + agentId;
        String shareTitle = "喜迎女神节，鲜花送给您~";
        String shareContent = "您已成功领取风信子花球，好活动就请分享微信好友、朋友圈";
        String logo = GlobEnv.getWebURL("/resources/micro2.0/images/share/girl_share.png");
        WeChatShareUtil.share(channel, link, shareTitle, shareContent, logo, true, request, model, weChatUtil);

        ReqMsg_Activity_ActivityForGirl2017PageInfo req = new ReqMsg_Activity_ActivityForGirl2017PageInfo();
        ResMsg_Activity_ActivityForGirl2017PageInfo res = (ResMsg_Activity_ActivityForGirl2017PageInfo) communicateBusiness.handleMsg(req);
        model.put("agentId", agentId);
        model.put("startTime", res.getStartTime());
        model.put("isStart", res.getIsStart());
        model.put("num", res.getNum());
        return channel + "/activity/girl2017/" + flag;
    }

    /**
     * 女神节活动首页信息
     * @return
     */
    @RequestMapping(value = "/micro2.0/activity/girl/receive_address/{agentId}")
    public String girl2017(HttpServletRequest request, Map<String, Object> model, @PathVariable String agentId) {
        // 分享文案、图片
        String link = GlobEnv.getWebURL("micro2.0/activity/girl/") + agentId + "?qianbao=qianbao&agentViewFlag=" + agentId;
        String shareTitle = "喜迎女神节，鲜花送给您~";
        String shareContent = "您已成功领取风信子花球，好活动就请分享微信好友、朋友圈";
        String logo = GlobEnv.getWebURL("/resources/micro2.0/images/share/girl_share.png");
        WeChatShareUtil.share("micro2.0", link, shareTitle, shareContent, logo, true, request, model, weChatUtil);

        ReqMsg_Activity_ActivityForGirl2017PageInfo req = new ReqMsg_Activity_ActivityForGirl2017PageInfo();
        ResMsg_Activity_ActivityForGirl2017PageInfo res = (ResMsg_Activity_ActivityForGirl2017PageInfo) communicateBusiness.handleMsg(req);
        model.put("agentId", agentId);
        model.put("startTime", res.getStartTime());
        model.put("isStart", res.getIsStart());
        model.put("num", res.getNum());
        return "micro2.0/activity/girl2017/receive_address";
    }

    /**
     * 女神节活动-校验是否有领取资格
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/girl/check")
    public Map<String, Object> checkGirl(HttpServletRequest request, ReqMsg_Activity_CheckForGirl2017Receive req, @PathVariable String channel) {
        Map<String, Object> result = new HashMap<>();
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        if(!StringUtils.isEmpty(userId)) {
            req.setUserId(Integer.parseInt(userId));
        }
        ResMsg_Activity_CheckForGirl2017Receive res = (ResMsg_Activity_CheckForGirl2017Receive) communicateBusiness.handleMsg(req);
        result.put("res", res);
        return result;
    }

    /**
     * 女神节活动-领取奖品
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/girl/receive")
    public Map<String, Object> girlReceive(HttpServletRequest request, ReqMsg_Activity_ActivityForGirl2017Receive req, @PathVariable String channel) {
        Map<String, Object> result = new HashMap<>();
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        if(!StringUtils.isEmpty(userId)) {
            req.setUserId(Integer.parseInt(userId));
        }
        ResMsg_Activity_ActivityForGirl2017Receive res = (ResMsg_Activity_ActivityForGirl2017Receive) communicateBusiness.handleMsg(req);
        result.put("res", res);
        return result;
    }
    //====================================== 2017女神节活动 end ===================================

    //====================================== 2017元宵喜乐会 start ===================================

    /**
     * 元宵喜乐会首页信息
     * @return
     */
    private String lanternFestival(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, String flag, String channel) {
        // 分享文案、图片
        String link = GlobEnv.getWebURL("micro2.0/activity/lantern");
        String shareTitle = "鸡年撞大运！我又领到了一波福利，你也来试试手气吧！";
        String shareContent = "欢天喜地闹元宵，红包、奖励金大放送！";
        String logo = GlobEnv.getWebURL("/resources/micro2.0/images/share/yuanxiao.jpg");
        WeChatShareUtil.share("micro2.0", link, shareTitle, shareContent, logo, true, request, model, weChatUtil);

        ReqMsg_Activity_LuckyDrawPageInfo req = new ReqMsg_Activity_LuckyDrawPageInfo();
        ResMsg_Activity_LuckyDrawPageInfo res = (ResMsg_Activity_LuckyDrawPageInfo) communicateBusiness.handleMsg(req);
        model.put("startTime", res.getStartTime());
        model.put("endTime", res.getEndTime());
        model.put("shakeNum", res.getShakeNum());
        String amount = String.valueOf(res.getAmount().intValue());
        model.put("amount", CollectionUtils.arrayToList(amount.toCharArray()));
        String investNum = String.valueOf(res.getInvestNum());
        model.put("investNum", CollectionUtils.arrayToList(investNum.toCharArray()));
        String nextLuckInvestNum = String.valueOf(res.getNextLuckInvestNum());
        model.put("nextLuckInvestNum", CollectionUtils.arrayToList(nextLuckInvestNum.toCharArray()));

        // 获奖列表
        ReqMsg_Activity_LuckyDrawList reqList = new ReqMsg_Activity_LuckyDrawList();
        reqList.setNumPerPage(20);
        reqList.setPageNum(1);
        ResMsg_Activity_LuckyDrawList resLuckyList = (ResMsg_Activity_LuckyDrawList) communicateBusiness.handleMsg(reqList);
        model.put("res", resLuckyList);
        model.put("req", reqList);

        // 活动是否开始
        ReqMsg_Activity_DuringActivity reqIsStartShake = new ReqMsg_Activity_DuringActivity();
        reqIsStartShake.setActivityType("shake");
        ResMsg_Activity_DuringActivity resIsStartShake = (ResMsg_Activity_DuringActivity) communicateBusiness.handleMsg(reqIsStartShake);
        model.put("shakeIsStart", resIsStartShake.getIsDuringActivity());

        ReqMsg_Activity_DuringActivity reqIsStartBuy = new ReqMsg_Activity_DuringActivity();
        reqIsStartShake.setActivityType("buy");
        ResMsg_Activity_DuringActivity resIsStartBuy = (ResMsg_Activity_DuringActivity) communicateBusiness.handleMsg(reqIsStartBuy);
        model.put("buyIsStart", resIsStartBuy.getIsDuringActivity());

        return channel + "/activity/lantern2017/" + flag;
    }

    /**
     * 元宵喜乐会-中奖列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/lantern/list")
    public Map<String, Object> getLanternList(ReqMsg_Activity_LuckyDrawList req, @PathVariable String channel) {
        Map<String, Object> result = new HashMap<>();
        req.setNumPerPage(20);
        ResMsg_Activity_LuckyDrawList res = (ResMsg_Activity_LuckyDrawList) communicateBusiness.handleMsg(req);
        result.put("res", res);
        result.put("req", req);
        return result;
    }

    /**
     * 元宵喜乐会-摇一摇
     * @return
     */
    @RequestMapping(value = "/{channel}/activity/lantern/shake")
    public String shake(@PathVariable String channel, HttpServletRequest request, Map<String, Object> model) {
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        if(StringUtil.isBlank(userId)) {
            return "redirect:/" + channel + "/activity/lantern";
        }
        // 分享文案、图片
        String link = GlobEnv.getWebURL("micro2.0/activity/lantern");
        String shareTitle = "鸡年撞大运！我又领到了一波福利，你也来试试手气吧！";
        String shareContent = "欢天喜地闹元宵，红包、奖励金大放送！";
        String logo = GlobEnv.getWebURL("/resources/micro2.0/images/share/yuanxiao.jpg");
        WeChatShareUtil.share("micro2.0", link, shareTitle, shareContent, logo, true, request, model, weChatUtil);

        return channel + "/activity/lantern2017/shake";
    }

    /**
     * 元宵喜乐会-抽奖兑奖
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/lantern/lucky_draw")
    public Map<String, Object> luckyDraw(ReqMsg_Activity_LuckyDrawResult req, HttpServletRequest request, @PathVariable String channel) {
        Map<String, Object> result = new HashMap<>();

        //用户是否登录
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        if(!StringUtil.isBlank(userId)) {
            result.put("login","yes");
            req.setUserId(Integer.parseInt(userId));
            ResMsg_Activity_LuckyDrawResult res = (ResMsg_Activity_LuckyDrawResult) communicateBusiness.handleMsg(req);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                result.put("resCode", ConstantUtil.BSRESCODE_SUCCESS);
                result.put("res", res);
            } else {
                result.put("resCode", res.getResCode());
                result.put("resMsg", StringUtils.isEmpty(res.getResMsg()) ? "人气大爆发，请稍后再试" : res.getResMsg());
            }
        } else {
            result.put("login","no");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/lantern/in_activity")
    public Map<String, Object> inActivity(ReqMsg_Activity_DuringActivity req, HttpServletRequest request, @PathVariable String channel) {
        Map<String, Object> result = new HashMap<>();
        ResMsg_Activity_DuringActivity res = (ResMsg_Activity_DuringActivity) communicateBusiness.handleMsg(req);

        result.put("isStart", res.getIsDuringActivity());
        return result;
    }

    //====================================== 2017元宵喜乐会 end ===================================

    //======================================三重礼  start===================================
    /**
     * 【三重好礼】
     * @param request
     * @param response
     * @param model
     * @param flag
     * @param channel
     * @return
     */
	private String trebleGift(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model,
			String flag, String channel) {
		ReqMsg_ActivityLuckyDraw_GetTrebleGiftInvestList reqList = new ReqMsg_ActivityLuckyDraw_GetTrebleGiftInvestList();
		
		//用户是否登录
		CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        if(!StringUtil.isBlank(userId)){
        	model.put("userLogin","is_login");
        	reqList.setUserId(Integer.parseInt(userId));
        }else{
        	model.put("userLogin","no_login");
        }
		
		//查询二重好礼活动时间
        ReqMsg_ActivityLuckyDraw_ActivityTime reqTime = new ReqMsg_ActivityLuckyDraw_ActivityTime();
        reqTime.setActivityId(Constants.ACTIVITY_TREBLEGIFT_2);
        reqTime.setFormat("yyyy-MM-dd HH:mm:ss");
        
        ResMsg_ActivityLuckyDraw_ActivityTime resTime = (ResMsg_ActivityLuckyDraw_ActivityTime)communicateBusiness.handleMsg(reqTime);
		
        model.put("secondActivityIsStart", resTime.getIsStart());
        if("ing".equals(resTime.getIsStart())){
        	//活动进行中，查询当日排行及往日排行
        	reqList.setActivityId(Constants.ACTIVITY_TREBLEGIFT_2);
        	ResMsg_ActivityLuckyDraw_GetTrebleGiftInvestList resList = (ResMsg_ActivityLuckyDraw_GetTrebleGiftInvestList)communicateBusiness.handleMsg(reqList);
        	model.put("todayRankingList", resList.getTodayList());
        	model.put("todayRankingSize", resList.getTodayList()==null?0:resList.getTodayList().size());
        	model.put("hisRankingList", resList.getHistoryList());
        	model.put("hisRankingSize", resList.getHistoryList()==null?0:resList.getHistoryList().size());
        	model.put("userInvest", resList.getBuyAmount());
        	
        }else if("end".equals(resTime.getIsStart())){
        	reqList.setActivityId(Constants.ACTIVITY_TREBLEGIFT_2);
        	ResMsg_ActivityLuckyDraw_GetTrebleGiftInvestList resList = (ResMsg_ActivityLuckyDraw_GetTrebleGiftInvestList)communicateBusiness.handleMsg(reqList);
        	model.put("hisRankingList", resList.getHistoryList());
        	model.put("hisRankingSize", resList.getHistoryList()==null?0:resList.getHistoryList().size());
        }
        
        //查询三重好礼活动时间
        ReqMsg_ActivityLuckyDraw_ActivityTime reqTime1 = new ReqMsg_ActivityLuckyDraw_ActivityTime();
        reqTime1.setActivityId(Constants.ACTIVITY_TREBLEGIFT_3);
        reqTime1.setFormat("yyyy-MM-dd HH:mm:ss");
        
        ResMsg_ActivityLuckyDraw_ActivityTime resTime1 = (ResMsg_ActivityLuckyDraw_ActivityTime)communicateBusiness.handleMsg(reqTime1);
		
        model.put("thirdActivityIsStart", resTime1.getIsStart());
        
        if("micro2.0".equals(channel)) {
        	//分享文案、图片
        	String link = GlobEnv.getWebURL("micro2.0/activity/treble_gift");
            String shareTitle = "新年投资三重奖，投安心的平台，获更高的收益";
            String shareContent = "币港湾为您提供一个能让资金稳健增值的家园。币港湾是由杭商资产管理有限公司发起的国资系的互联网金融服务平台，“币”即钱币，“港湾”寓意安全、稳固。平台对接海量优质小微债权，拥有完善的风控体系，充分保障投资者的资金安全，是您实现财富增值的理想家园。";
            String logo = GlobEnv.getWebURL("/resources/micro2.0/images/share/treble_gift_show.jpg");
            WeChatShareUtil.share("micro2.0", link, shareTitle, shareContent, logo, true, request, model, weChatUtil);
        }
        
        return channel + "/activity/" + flag;
	}
	
	/**
	 * 三重好礼-判断三重礼活动是否需要显示砸蛋
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/{channel}/activity/treble_gift/thirdcheck")
	public @ResponseBody HashMap<String, Object> trebleGiftThirdcheck(
		HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		
		//查询三重好礼活动时间
        ReqMsg_ActivityLuckyDraw_ActivityTime reqTime = new ReqMsg_ActivityLuckyDraw_ActivityTime();
        reqTime.setActivityId(Constants.ACTIVITY_TREBLEGIFT_3);
        reqTime.setFormat("yyyy-MM-dd HH:mm:ss");
        
        ResMsg_ActivityLuckyDraw_ActivityTime resTime = (ResMsg_ActivityLuckyDraw_ActivityTime)communicateBusiness.handleMsg(reqTime);
        
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        if("ing".equals(resTime.getIsStart())){
        	if(!StringUtil.isBlank(userId)){
        		// 2. 是否拆过161红包
                ReqMsg_ActivityLuckyDraw_JudgeUserDrawed161Packet req = new ReqMsg_ActivityLuckyDraw_JudgeUserDrawed161Packet();
                req.setUserId(Integer.parseInt(userId));
                req.setRedPacketName("新春红包");
                ResMsg_ActivityLuckyDraw_JudgeUserDrawed161Packet res =(ResMsg_ActivityLuckyDraw_JudgeUserDrawed161Packet) communicateBusiness.handleMsg(req);
                if(res.isDrawed161Packet()){
                	//拆过红包，不显示
                	dataMap.put("isShowEgg", false);
                }else{
                	dataMap.put("isShowEgg", true);
                }
        	}else{
        		//活动开始，未登录
        		dataMap.put("isShowEgg", true);
        	}
        	
           
        }else{
        	dataMap.put("isShowEgg", false);
        }
        
		return dataMap;
	}
	
	/**
     * 三重好礼-点击拆红包
     * @param channel
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/treble_gift/openRedPacket", method = RequestMethod.POST)
    public Map<String, Object> trebleGiftOpenRedPacket(@PathVariable String channel, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        if(StringUtil.isBlank(userId)) {
            // 未登录
            result.put("resCode", "not_login");
            result.put("resMsg", "用户未登录");
        } else {
            // 已登录
            // 获得168红包
        	ReqMsg_ActivityLuckyDraw_TrebleGiftOpenPacket req = new ReqMsg_ActivityLuckyDraw_TrebleGiftOpenPacket();
            req.setUserId(Integer.parseInt(userId));
            req.setActivityId(Constants.ACTIVITY_TREBLEGIFT_3);
            req.setRedPacketName("新春红包");
            ResMsg_ActivityLuckyDraw_TrebleGiftOpenPacket res =(ResMsg_ActivityLuckyDraw_TrebleGiftOpenPacket) communicateBusiness.handleMsg(req);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                result.put("resCode", ConstantUtil.RESCODE_SUCCESS);
                result.put("res", res);
            } else {
                result.put("resCode", res.getResCode());
                result.put("resMsg", StringUtils.isEmpty(res.getResMsg()) ? "拆红包失败，请重新尝试" : res.getResMsg());
            }
        }
        return result;
    }
    
/*    *//**
     * 三重好礼-历史排行
     * @param channel
     * @param request
     * @return
     *//*
    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/treble_gift/historyRankingList", method = RequestMethod.POST)
    public Map<String, Object> trebleGiftHistory(@PathVariable String channel, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        //查询二重好礼活动时间
        ReqMsg_ActivityLuckyDraw_ActivityTime reqTime = new ReqMsg_ActivityLuckyDraw_ActivityTime();
        reqTime.setActivityId(Constants.ACTIVITY_TREBLEGIFT_2);
        reqTime.setFormat("yyyy-MM-dd HH:mm:ss");
        
        ResMsg_ActivityLuckyDraw_ActivityTime resTime = (ResMsg_ActivityLuckyDraw_ActivityTime)communicateBusiness.handleMsg(reqTime);
		
        result.put("secondActivityIsStart", resTime.getIsStart());
        if("ing".equals(resTime.getIsStart())){
        	//活动进行中，查询当日排行及往日排行
        	ReqMsg_ActivityLuckyDraw_GetTrebleGiftInvestList reqList = new ReqMsg_ActivityLuckyDraw_GetTrebleGiftInvestList();
        	reqList.setActivityId(Constants.ACTIVITY_TREBLEGIFT_2);
        	ResMsg_ActivityLuckyDraw_GetTrebleGiftInvestList resList = (ResMsg_ActivityLuckyDraw_GetTrebleGiftInvestList)communicateBusiness.handleMsg(reqList);
        	result.put("hisRankingList", resList.getHistoryList());
        	result.put("hisRankingLSize", resList.getHistoryList()==null?0:resList.getHistoryList().size());
        	
        }
        return result;
    }*/
    //======================================三重礼  end===================================
    
	/**
     * 【528活动】
     * @param request
     * @param response
     * @param model
     * @param flag
     * @param channel
     * @return
     */
    private String activity528(HttpServletRequest request, HttpServletResponse response,
                               Map<String, Object> model, String flag, String channel) {
        ReqMsg_Shake_GetWinUserNumber req = new ReqMsg_Shake_GetWinUserNumber();
        req.setActivityFlag("activity_528");
        if("micro2.0".equals(channel)) {
            req.setTerminal(Constants.PRODUCT_SHOW_TERMINAL_H5);
            // 分享
            String link = GlobEnv.getWebURL("/micro2.0/activity/" + flag);
            Map<String,String> sign = weChatUtil.createAuth(request);
            sign.put("link", link);
            model.put("weichat", sign);
            model.put("source", "activity_528");
        } else if("gen2.0".equals(channel)) {
            req.setTerminal(Constants.PRODUCT_SHOW_TERMINAL_PC);
        }
        ResMsg_Shake_GetWinUserNumber res = (ResMsg_Shake_GetWinUserNumber) communicateBusiness.handleMsg(req);
        model.put("product", res.getProduct());
        return channel + "/activity/" + flag;
    }

    /**
     * 【518“我要发”理财活动】
     * @param request
     * @param response
     * @param model
     * @param flag
     * @param channel
     * @return
     */
    private String financeDay518(HttpServletRequest request, HttpServletResponse response,
                                 Map<String, Object> model, String flag, String channel) {
        // 分享
        String link = GlobEnv.getWebURL("/micro2.0/activity/" + flag);
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        model.put("source", "finance_day_518");
        
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_Shake_GetWinUserNumber req = new ReqMsg_Shake_GetWinUserNumber();
        req.setActivityFlag("finance_day_518");
        if(!StringUtil.isBlank(userId)) {
            req.setUserId(Integer.parseInt(userId));
        }
        ResMsg_Shake_GetWinUserNumber res = (ResMsg_Shake_GetWinUserNumber) communicateBusiness.handleMsg(req);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            model.put("count", res.getChanceCount());
            return channel + "/activity/" + flag;
        } else if("971006".equals(res.getResCode())) {
            return channel + "/activity/" + flag + "_end";
        }
        return channel + "/activity/" + flag;
    }
    
    /**
     * 【518“我要发”理财活动】领取红包
     * @param reqMsg
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/micro2.0/activity/518/draw_red_packet")
    public Map<String, Object> financeDay518DrawRedPacket(ReqMsg_Shake_DrawRedPacket reqMsg, HttpServletRequest request, HttpServletResponse response) {
        // 数据校验
        Map<String, Object> result = new HashMap<String, Object>();
        //request.getSession().setAttribute("redirectUrl", "/micro2.0/activity/518");
        CookieManager cookieManager = new CookieManager(request);
        cookieManager.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_REDIRECT_URL.getName(), "/micro2.0/activity/518", true);
        cookieManager.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        if(StringUtil.isBlank(userId)) {
            result.put("resCode", "9100049");
            result.put("resMsg", "您还未登录");
            return result;
        }
        // 数据校验
        
        reqMsg.setUserId(Integer.parseInt(userId));
        reqMsg.setActivityFlag("finance_day_518");
        ResMsg_Shake_DrawRedPacket resMsg = (ResMsg_Shake_DrawRedPacket) communicateBusiness.handleMsg(reqMsg);
        ReqMsg_Shake_GetWinUserNumber req = new ReqMsg_Shake_GetWinUserNumber();
        if(!StringUtil.isBlank(userId)) {
            req.setUserId(Integer.parseInt(userId));
        }
        req.setActivityFlag("finance_day_518");
        ResMsg_Shake_GetWinUserNumber res = (ResMsg_Shake_GetWinUserNumber) communicateBusiness.handleMsg(req);
        Integer count = res.getChanceCount();
        result.put("count", count);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
            result.put("resCode", "000");
            result.put("resMsg", resMsg.getResMsg());
        } else {
            result.put("resCode", resMsg.getResCode());
            result.put("resMsg", resMsg.getResMsg());
        }
        return result;
    }
    

    /**
     * 【母亲节活动】
     * @param request
     * @param response
     * @param model
     * @param flag
     * @param channel
     * @return
     */
    private String motherDayActivity(HttpServletRequest request, HttpServletResponse response,
                                     Map<String, Object> model, String flag, String channel) {
        if("micro2.0".equals(channel)) {
            // 分享
            String link = GlobEnv.getWebURL("/micro2.0/activity/" + flag);
            Map<String,String> sign = weChatUtil.createAuth(request);
            sign.put("link", link);
            sign.put("share_title", "【点击领取】币港湾理财节红包！");
            sign.put("share_content", "币港湾理财节，百万红包供你选，想要多少选多少！");
            sign.put("share_logo", GlobEnv.getWebURL("/resources/micro2.0/images/share/718_logo.jpg"));
            model.put("weichat", sign);
            model.put("source", "all");
            
            CookieManager cookieManager = new CookieManager(request);
            String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            ReqMsg_Shake_GetWinUserNumber req = new ReqMsg_Shake_GetWinUserNumber();
            if(!StringUtil.isBlank(userId)) {
                req.setUserId(Integer.parseInt(userId));
            }
            ResMsg_Shake_GetWinUserNumber res = (ResMsg_Shake_GetWinUserNumber) communicateBusiness.handleMsg(req);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                model.put("count", res.getChanceCount());
                return channel + "/activity/" + flag;
            } else if("971006".equals(res.getResCode())) {
                model.put("count", res.getChanceCount());
                return channel + "/activity/" + flag + "_end";
            }
        }
        return channel + "/activity/" + flag;
    }

    /**
     * 【母亲节活动】领取红包
     * @param reqMsg
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/micro2.0/activity/motherday/draw_red_packet")
    public Map<String, Object> motherDayActivityDrawRedPacket(ReqMsg_Shake_DrawRedPacket reqMsg, HttpServletRequest request, HttpServletResponse response) {
        // 数据校验
        Map<String, Object> result = new HashMap<String, Object>();
        //request.getSession().setAttribute("redirectUrl", "/micro2.0/activity/mother_day_index");
        CookieManager cookieManager = new CookieManager(request);
        cookieManager.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_REDIRECT_URL.getName(), "/micro2.0/activity/mother_day_index", true);
        cookieManager.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        if(StringUtil.isBlank(userId)) {
            result.put("resCode", "9100049");
            result.put("resMsg", "您还未登录");
            return result;
        }
        // 数据校验
        
        reqMsg.setUserId(Integer.parseInt(userId));
        ResMsg_Shake_DrawRedPacket resMsg = (ResMsg_Shake_DrawRedPacket) communicateBusiness.handleMsg(reqMsg);
        ReqMsg_Shake_GetWinUserNumber req = new ReqMsg_Shake_GetWinUserNumber();
        if(!StringUtil.isBlank(userId)) {
            req.setUserId(Integer.parseInt(userId));
        }
        ResMsg_Shake_GetWinUserNumber res = (ResMsg_Shake_GetWinUserNumber) communicateBusiness.handleMsg(req);
        Integer count = res.getChanceCount();
        result.put("count", count);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
            result.put("resCode", "000");
            result.put("resMsg", resMsg.getResMsg());
        } else {
            result.put("resCode", resMsg.getResCode());
            result.put("resMsg", resMsg.getResMsg());
        }
        return result;
    }























    // ==================== 2016-10-29 开始的双十一活动 ====================================
    // 1. 进入双十一活动页{
    // 判断是否登录
    // 是否拆过161红包接口   统计用户是否领取 双11专享红包
    // 获奖名单 + 剩余抽奖次数 + 活动起始时间 + 活动是否开始或结束（现有,需要检查是否修改）
    // }

    // 2. 点击拆红包{
    // 获得161红包以及免费抽奖机会接口（奖品已经定了，bs_activity_lucky_draw存库）
    // }

    // 3. 点击抽奖
    // 抽中更新 bs_activity_lucky_draw 表的兑奖状态
    // ==================== 2016-10-29 开始的双十一活动 ====================================

    /**
     * 进入双十一活动页
     * @param channel
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/{channel}/activity/1111", method = RequestMethod.GET)
    public String double11(@PathVariable String channel, Map<String, Object> model, HttpServletRequest request) {
        String link = GlobEnv.getWebURL("/micro2.0/index");
        String shareTitle = "双十一我给自己定个小目标：送他个几十台iPhone 7 Plus！";
        String shareContent = "不送点真福利总觉得亏待了你！";
        String logo = GlobEnv.getWebURL("/resources/micro2.0/images/share/double11.png");
        WeChatShareUtil.share(channel, link, shareTitle, shareContent, logo, true, request, model, weChatUtil);

        // 1. 判断是否登录
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        if(StringUtil.isBlank(userId)) {
            // 未登录
            return "redirect:/" + channel + "/user/login/index?burl=" + "/" + channel + "/activity/1111";
        } else {
            // 已登录
            // 2. 是否拆过161红包
            ReqMsg_ActivityLuckyDraw_JudgeUserDrawed161Packet req = new ReqMsg_ActivityLuckyDraw_JudgeUserDrawed161Packet();
            req.setUserId(Integer.parseInt(userId));
            ResMsg_ActivityLuckyDraw_JudgeUserDrawed161Packet res =(ResMsg_ActivityLuckyDraw_JudgeUserDrawed161Packet) communicateBusiness.handleMsg(req);
            model.put("isDrawed161Packet", res.isDrawed161Packet());
            // 3. 获奖名单 + 剩余抽奖次数 + 活动起始时间 + 活动是否开始或结束（现有,需要检查是否修改）
            ReqMsg_ActivityLuckyDraw_ActivityIndex indexActivityReq = new ReqMsg_ActivityLuckyDraw_ActivityIndex();
            indexActivityReq.setUserId(Integer.parseInt(userId));
            indexActivityReq.setActivityId(Constants.ACTIVITY_ID_DOUBLE11);
            ResMsg_ActivityLuckyDraw_ActivityIndex indexActivityRes = (ResMsg_ActivityLuckyDraw_ActivityIndex) communicateBusiness.handleMsg(indexActivityReq);
            model.put("res", indexActivityRes);

            if(Constants.CHANNEL_GEN.equals(channel)) {
                ReqMsg_ActivityLuckyDraw_Get618UserLuckyList getUserLuckyListReq = new ReqMsg_ActivityLuckyDraw_Get618UserLuckyList();
                getUserLuckyListReq.setStartPage(0);
                getUserLuckyListReq.setPageSize(Integer.MAX_VALUE);
                getUserLuckyListReq.setUserId(Integer.parseInt(userId));
                getUserLuckyListReq.setActivityId(Constants.ACTIVITY_ID_DOUBLE11);
                ResMsg_ActivityLuckyDraw_Get618UserLuckyList resU = (ResMsg_ActivityLuckyDraw_Get618UserLuckyList)communicateBusiness.handleMsg(getUserLuckyListReq);

                model.put("pageIndex", 0);
                model.put("totalCount", resU.getTotal());
                model.put("userLuckyList", resU.getLuckyList());
            }
        }
        return channel + "/activity/activity1111/1111_index";
    }

    @RequestMapping(value = "/{channel}/activity/1111/myAwards", method = RequestMethod.GET)
    public String myAwards(@PathVariable String channel, Map<String, Object> model, HttpServletRequest request) {
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_ActivityLuckyDraw_Get618UserLuckyList req = new ReqMsg_ActivityLuckyDraw_Get618UserLuckyList();
        if(org.apache.commons.lang.StringUtils.isNotBlank(userId)){
            req.setStartPage(0);
            req.setPageSize(Constants.EXCHANGE_PAGE_SIZE_LONG);
            req.setUserId(Integer.parseInt(userId));
            req.setActivityId(Constants.ACTIVITY_ID_DOUBLE11);
            ResMsg_ActivityLuckyDraw_Get618UserLuckyList resU = (ResMsg_ActivityLuckyDraw_Get618UserLuckyList)communicateBusiness.handleMsg(req);

            model.put("pageIndex", 0);
            model.put("totalCount", resU.getTotal());
            model.put("userLuckyList", resU.getLuckyList());
        }
        return channel+"/activity/activity618/618_user";
    }

    /**
     * 用户个人抽奖列表--加载更多
     * @param channel
     * @param request
     * @param model
     * @param pageIndex
     * @param reqU
     * @return
     */
    @RequestMapping("/{channel}/activity/1111/myAwardsMore")
    private String myAwardsMore(@PathVariable String channel, HttpServletRequest request, Map<String, Object> model, Integer pageIndex,
                                                   ReqMsg_ActivityLuckyDraw_Get618UserLuckyList reqU){
        try {
            CookieManager cookieManager = new CookieManager(request);
            String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);

            if(org.apache.commons.lang.StringUtils.isNotBlank(userId)){
                reqU.setStartPage(pageIndex);
                reqU.setPageSize(Constants.EXCHANGE_PAGE_SIZE_LONG);
                reqU.setUserId(Integer.parseInt(userId));
                reqU.setActivityId(Constants.ACTIVITY_ID_DOUBLE11);
                ResMsg_ActivityLuckyDraw_Get618UserLuckyList resU = (ResMsg_ActivityLuckyDraw_Get618UserLuckyList)communicateBusiness.handleMsg(reqU);

                model.put("pageIndex", reqU.getStartPage());
                model.put("totalCount", resU.getTotal());
                model.put("userLuckyList", resU.getLuckyList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channel+"/activity/activity618/618_user_content";
    }

    /**
     *
     * @param channel
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/1111/myAwardList", method = RequestMethod.GET)
    public Map<String, Object> myAwardList(@PathVariable String channel, HttpServletRequest request) {
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_ActivityLuckyDraw_Get618UserLuckyList req = new ReqMsg_ActivityLuckyDraw_Get618UserLuckyList();
        Map<String, Object> model = new HashMap<>();
        if(org.apache.commons.lang.StringUtils.isNotBlank(userId)){
            req.setStartPage(0);
            req.setPageSize(Integer.MAX_VALUE);
            req.setUserId(Integer.parseInt(userId));
            req.setActivityId(Constants.ACTIVITY_ID_DOUBLE11);
            ResMsg_ActivityLuckyDraw_Get618UserLuckyList resU = (ResMsg_ActivityLuckyDraw_Get618UserLuckyList)communicateBusiness.handleMsg(req);

            model.put("resCode", "000000");
            model.put("pageIndex", 0);
            model.put("totalCount", resU.getTotal());
            if(null != resU.getLuckyList()){
                for (Map<String, Object> map: resU.getLuckyList()) {
                    if (map.get("userDrawTime") instanceof Date) {
                        Date date = (Date) map.get("userDrawTime");
                        map.put("userDrawTime", DateUtil.formatDateTime(date, "yyyy-MM-dd HH:mm:ss"));
                    }
                }
            }
            model.put("userLuckyList", resU.getLuckyList());
        } else {
            model.put("resCode", "not_login");
            model.put("resMsg", "用户未登录");
        }
        return model;
    }

    /**
     * 点击拆红包
     * @param channel
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/1111/openRedPacket", method = RequestMethod.POST)
    public Map<String, Object> openRedPacket(@PathVariable String channel, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        if(StringUtil.isBlank(userId)) {
            // 未登录
            result.put("resCode", "not_login");
            result.put("resMsg", "用户未登录");
        } else {
            // 已登录
            // 获得161红包以及免费抽奖机会接口（奖品已经定了，bs_activity_lucky_draw存库）
            ReqMsg_ActivityLuckyDraw_OpenPacket req = new ReqMsg_ActivityLuckyDraw_OpenPacket();
            req.setUserId(Integer.parseInt(userId));
            ResMsg_ActivityLuckyDraw_OpenPacket res =(ResMsg_ActivityLuckyDraw_OpenPacket) communicateBusiness.handleMsg(req);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                result.put("resCode", ConstantUtil.BSRESCODE_SUCCESS);
                result.put("res", res);
            } else {
                result.put("resCode", res.getResCode());
                result.put("resMsg", StringUtils.isEmpty(res.getResMsg()) ? "拆红包失败，请重新尝试" : res.getResMsg());
            }
        }
        return result;
    }

    /**
     * 点击抽奖
     * @param channel
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/1111/luckDraw", method = RequestMethod.POST)
    public Map<String, Object> luckDraw(@PathVariable String channel, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        if(StringUtil.isBlank(userId)) {
            // 未登录
            result.put("resCode", "not_login");
            result.put("resMsg", "用户未登录");
        } else {
            // 已登录
            // 抽奖接口 抽中更新 bs_activity_lucky_draw 表的兑奖状态
            ReqMsg_ActivityLuckyDraw_GetActiveAward req = new ReqMsg_ActivityLuckyDraw_GetActiveAward();
            req.setActivityId(Constants.ACTIVITY_ID_DOUBLE11);
            req.setUserId(Integer.parseInt(userId));
            ResMsg_ActivityLuckyDraw_GetActiveAward res =(ResMsg_ActivityLuckyDraw_GetActiveAward) communicateBusiness.handleMsg(req);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                result.put("resCode", ConstantUtil.BSRESCODE_SUCCESS);
                result.put("isStart", res.getIsStart());
                result.put("beforeTimes", res.getBeforeTimes());
                result.put("afterTimes", res.getAfterTimes());
                result.put("awardId", res.getAwardId());
                result.put("awardContent", res.getAwardContent());
            } else {
                result.put("resCode", res.getResCode());
                result.put("resMsg", StringUtils.isEmpty(res.getResMsg()) ? "抽奖失败，请重新尝试" : res.getResMsg());
            }
        }
        return result;
    }




    // ==================== 12月13日活动--投资PK瓜分两万现金 ====================================
    // 1. 进入PK页面{
    // 无需检查是否登录
    // 页面展示两个活动标的的投资情况（投资总额+进度条）
    // 
    // }

    // 2. 点击排行榜{
    // 展示用户投资排行，单个用户多次投资进行合并计算总投资
    // }

    // 3. 点击马上投资{
    //	a.活动已经开始-跳转标的详情页面
    //  b.活动未开始
    //	c.标的已完成
    //	d.活动已结束
    //}
    
    // 4.H5分享文案
    //	
    // ==================== 12月13日活动--投资PK瓜分两万现金 ====================================
    

    /**
     * 【12月13日活动--投资PK瓜分两万现金】
     * @param request
     * @param response
     * @param model
     * @param flag
     * @param channel
     * @return
     */
    private String playerKillingActivity(HttpServletRequest request, HttpServletResponse response,
                                     Map<String, Object> model, String flag, String channel) {
        
    	
    	ReqMsg_PlayerKilling_Index req = new ReqMsg_PlayerKilling_Index();
    	ResMsg_PlayerKilling_Index res = (ResMsg_PlayerKilling_Index) communicateBusiness.handleMsg(req);
    	model.put("investAmountContentment", MoneyUtil.divide(res.getInvestAmountContentment(), 10000));
    	
    	
    	Double a =  res.getInvestAmountContentment() / 15000000 * 100d;
        if(a > 0 && a < 1) {
        	model.put("investAmountContentmentPercent", 0.9);
        	model.put("investAmountContentmentPercentShow", 1);
        } else if(a > 99 && a < 100) {
        	model.put("investAmountContentmentPercent", 99*0.9);
        	model.put("investAmountContentmentPercentShow", 99);
        } else {
        	model.put("investAmountContentmentPercent", MoneyUtil.multiply(MoneyUtil.divide(res.getInvestAmountContentment(), 15000000).doubleValue(), 90));
        	model.put("investAmountContentmentPercentShow", MoneyUtil.multiply(MoneyUtil.divide(res.getInvestAmountContentment(), 15000000).doubleValue(), 100));
        }
    	
    	model.put("investAmountReal", MoneyUtil.divide(res.getInvestAmountReal(),10000));
    	
    	Double b =  res.getInvestAmountReal() / 15000000 * 100d;
        if(b > 0 && b < 1) {
        	model.put("investAmountRealPercent", 0.9);
        	model.put("investAmountRealPercentShow", 1);
        } else if(b > 99 && b < 100) {
        	model.put("investAmountRealPercent", 99*0.9);
        	model.put("investAmountRealPercentShow", 99);
        } else {
        	model.put("investAmountRealPercent", MoneyUtil.multiply(MoneyUtil.divide(res.getInvestAmountReal(), 15000000).doubleValue(), 90));
        	model.put("investAmountRealPercentShow", MoneyUtil.multiply(MoneyUtil.divide(res.getInvestAmountReal(), 15000000).doubleValue(), 100));
        }
    	
    	model.put("productIdReal", res.getProductIdReal());
    	model.put("productIdContentment", res.getProductIdContentment());
    	//判断是否是胜利方
    	model.put("winner", res.getWinner());
 	/*   if (res.getInvestAmountContentment().compareTo(15000000.00) == 0) {
    		model.put("winner", "contentment");
		}else if (res.getInvestAmountReal().compareTo(15000000.00) == 0) {
			model.put("winner", "real");
		}*/
    	//排行榜数据
    	model.put("rankingContentment", res.getRankingContentment());
    	model.put("rankingReal", res.getRankingReal());
    	
    	if("micro2.0".equals(channel)) {
            // 分享
            String link = GlobEnv.getWebURL("/micro2.0/activity/" + flag);
            Map<String,String> sign = weChatUtil.createAuth(request);
            sign.put("link", link);
            sign.put("share_title", "邀请你加入我的战队，向2万现金大奖进发！");
            sign.put("share_content", "好兄弟！有事一起扛，有福一起享！");
            sign.put("share_logo", GlobEnv.getWebURL("/resources/micro2.0/images/qrcode_logo.png"));
            model.put("weichat", sign);
            model.put("source", "all");
            
            /* CookieManager cookieManager = new CookieManager(request);
            String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            ReqMsg_Shake_GetWinUserNumber req = new ReqMsg_Shake_GetWinUserNumber();
            if(!StringUtil.isBlank(userId)) {
                req.setUserId(Integer.parseInt(userId));
            }
            ResMsg_Shake_GetWinUserNumber res = (ResMsg_Shake_GetWinUserNumber) communicateBusiness.handleMsg(req);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                model.put("count", res.getChanceCount());
                return channel + "/activity/" + flag;
            } else if("971006".equals(res.getResCode())) {
                model.put("count", res.getChanceCount());
                return channel + "/activity/" + flag + "_end";
            }*/        
        }
        return channel + "/activity/playerKilling1213/index";
    }

    
    
    /**
     * 查询标的状态
     * @param channel
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/player_killing/product_status", method = RequestMethod.POST)
    public Map<String, Object> playerKillingProductStatus(@PathVariable String channel, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        ReqMsg_PlayerKilling_ActivityStatus req = new ReqMsg_PlayerKilling_ActivityStatus();
        
        ResMsg_PlayerKilling_ActivityStatus res = (ResMsg_PlayerKilling_ActivityStatus) communicateBusiness.handleMsg(req);
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			 result.put("resCode", res.getResCode());
		     result.put("resMsg", res.getResMsg());
		     result.put("statusContentment", res.getStatusContentment());
		     result.put("statusReal", res.getStatusReal());
		}else {
			 result.put("resCode", res.getResCode());
		     result.put("resMsg", res.getResMsg());
		}    
       
        
        return result;
    }
    
    
    
    /**
     * 查询标的排行榜
     * @param channel
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{channel}/activity/player_killing/product_ranking", method = RequestMethod.POST)
    public Map<String, Object> playerKillingProductRanking(@PathVariable String channel, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        ReqMsg_PlayerKilling_Index req = new ReqMsg_PlayerKilling_Index();
    	ResMsg_PlayerKilling_Index res = (ResMsg_PlayerKilling_Index) communicateBusiness.handleMsg(req);  
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			 result.put("resCode", res.getResCode());
		     result.put("resMsg", res.getResMsg());
		     result.put("rankingContentment", res.getRankingContentment());
		     result.put("rankingReal", res.getRankingReal());
		}else {
			 result.put("resCode", res.getResCode());
		     result.put("resMsg", res.getResMsg());
		} 
    	//排行榜数据

        return result;
    }

    // ==================== 12月13日活动--投资PK瓜分两万现金 E====================================


    //========================双旦活动S==============================
    
    
    private String doubleZeroActivity(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model,
			String flag, String channel) {
    	ReqMsg_Product_Active00Product reqMsg = new ReqMsg_Product_Active00Product();
		
		if("gen2.0".equals(channel)){
		reqMsg.setShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_PC);
		}else if("micro2.0".equals(channel)){
			reqMsg.setShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_H5);

	    	if("micro2.0".equals(channel)) {
	            // 分享
	            String link = GlobEnv.getWebURL("/micro2.0/activity/" + flag);
	            Map<String,String> sign = weChatUtil.createAuth(request);
	            sign.put("link", link);
	            sign.put("share_title", "合规安心的港湾，为您献上加息好礼！");
	            sign.put("share_content", "这个12月不剁手，学理财多赚钱！");
	            sign.put("share_logo", GlobEnv.getWebURL("/resources/micro2.0/images/qrcode_logo.png"));
	            model.put("weichat", sign);
	            model.put("source", "all");
	    	}
		}
		
		ResMsg_Product_Active00Product resMsg = (ResMsg_Product_Active00Product) communicateBusiness.handleMsg(reqMsg);
		if(org.apache.commons.lang.StringUtils.isNotBlank(resMsg.getIsEndOrNoStart())){
		model.put("isEndOrNoStart", resMsg.getIsEndOrNoStart());
		}else{
		ArrayList<HashMap<String, Object>> dataList = resMsg.getProductList();
		model.put("productList", dataList);
		}
		Date now = DateUtil.parseDate(DateUtil.format(new Date()));
		//12月25日（圣诞节当天）与12月31日（2016年最后一天）
		Date super_date1 = DateUtil.parseDate("2016-12-25");
		Date super_date2 = DateUtil.parseDate("2016-12-31");
		if(now.compareTo(super_date1)==0 || now.compareTo(super_date2)==0){
		model.put("maxRate", "0.8");
		}else{
		model.put("maxRate", "0.7");
		}
		
		return channel + "/regular/regular_activity00_product_list";
	}

    
  //========================双旦活动E==============================
    
    /**
     * 2017年开年有礼活动
     * @param request
     * @param response
     * @param model
     * @param channel
     * @return
     */
    @RequestMapping(value = "/{channel}/activity/openPolite/index", method = RequestMethod.GET)
    private String openPoliteActivity(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        String url = "";
        if("gen2.0".equals(channel)) {
            url = "/gen2.0/activity/openPolite2017/open_polite_index";
        }else if("micro2.0".equals(channel)) {
            String link = GlobEnv.getWebURL("/micro2.0/index");
            // 钱报的参数
            String qianbao = request.getParameter("qianbao");
            if(com.pinting.core.util.StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
                model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                link += "?qianbao=qianbao";
                CookieManager manager = new CookieManager(request);
                String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
                if(com.pinting.core.util.StringUtil.isNotBlank(viewAgentFlagCookie)){
                    link += "&agentViewFlag="+viewAgentFlagCookie;
                }
            }
            Map<String,String> sign = weChatUtil.createAuth(request);
            sign.put("link", link);
            model.put("weichat", sign);
            url = channel + "/activity/openPolite2017/open_polite_index";
        }
        return url;
    }
    
    
    
    //============================ 踏春活动S ================================
    /**
     * 【踏春活动】
     * @param request
     * @param response
     * @param model
     * @param flag
     * @param channel
     * @return
     */
	private String springIndex(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model,
			String flag, String channel) {
		CookieManager cookieManager = new CookieManager(request);
		
		//查询返回踏春活动时间
        ReqMsg_ActivityLuckyDraw_ActivityTime reqTime = new ReqMsg_ActivityLuckyDraw_ActivityTime();
        reqTime.setActivityId(Constants.ACTIVITY_SPRING);
        reqTime.setFormat("yyyy-MM-dd");
        ResMsg_ActivityLuckyDraw_ActivityTime resTime = (ResMsg_ActivityLuckyDraw_ActivityTime)communicateBusiness.handleMsg(reqTime);
        model.put("springIsStart", resTime.getIsStart());

        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        //判断   用户是否登录
        if(!StringUtil.isBlank(userId)){
        	model.put("userLogin","is_login");
        }else{
        	model.put("userLogin","no_login");
        }
        if(!"noStart".equals(resTime.getIsStart()) && !StringUtil.isBlank(userId)){
        	//活动已开始或已结束 且 用户已登录
        	
        	//查询用户活动时间内投资额,用户邀请列表
        	ReqMsg_Activity_SpringUsersVO springUsersVOReq = new  ReqMsg_Activity_SpringUsersVO();
        	springUsersVOReq.setUserId(Integer.parseInt(userId));
        	ResMsg_Activity_SpringUsersVO springUsersVORes = (ResMsg_Activity_SpringUsersVO)communicateBusiness.handleMsg(springUsersVOReq);
        	model.put("invitedList", springUsersVORes.getUserInvitedList());
        	model.put("invitedListSize", springUsersVORes.getUserInvitedList() == null ?0:springUsersVORes.getUserInvitedList().size());
        	model.put("userInvitedAmount", springUsersVORes.getUserInvestAmount());
        	model.put("invitedAmount",(int)(springUsersVORes.getUserInvestAmount()/10000));
        	model.put("rankingNo", springUsersVORes.getRankingNo());
        }
        if(("gen2.0".equals(channel) || "gen178".equals(channel)) && (!StringUtil.isBlank(userId))) {
        	Map<String,String> sign = weChatUtil.createAuth(request);
            String link = GlobEnv.getWebURL("/micro2.0/activity/spring_index?shareFlag=1&recommendId=" + Integer.parseInt(userId) + "&menu=yes");
            sign.put("link", link);
            String webPath = request.getSession().getServletContext().getRealPath("/");
            String logoPath = webPath + "/resources/micro2.0/images/qrcode_logo.png";
            MatrixToImageWriter.createMatrixImage(link, userId + "_spring", logoPath, true);
            String matrix = GlobEnv.get("wxQRcode.url.prefix") ;
            if(matrix.charAt(matrix.length() - 1) != '/'){
                matrix = matrix + "/";
            }
            matrix += userId + "_spring.png";
            model.put("matrix", matrix);
            
        }
        
        
		//查询推荐产品列表、投资排行榜列表
		ReqMsg_Activity_SpringIndex springIndexReq = new ReqMsg_Activity_SpringIndex();
		if("micro2.0".equals(channel)) {
			// 分享
            String link = GlobEnv.getWebURL("/micro2.0/activity/" + flag+"?shareFlag=1");
			
			//判断是否是分享进入
			String shareFlag = request.getParameter("shareFlag");
			if("1".equals(shareFlag)){
				//分享进入
				model.put("shareFlag", shareFlag);
				String recommendId = request.getParameter("recommendId");
				if(!StringUtil.isBlank(recommendId)){
					//有推荐人，存cookie
					cookieManager.setValue(CookieEnums._SITE.getName(),CookieEnums._SITE_RECOMMEND_ID.getName(),recommendId, true);
					cookieManager.save(response, CookieEnums._SITE.getName(), null, "/", 5*365*24*3600, true);
				}
				
				//判断用户是否登入，未登录则使用之前的推荐人id
				if(!StringUtil.isBlank(userId)){
	            	link = link + "&recommendId=" + Integer.parseInt(userId);
	            }else{
	            	if(!StringUtil.isBlank(recommendId)){
	            		link = link + "&recommendId=" + Integer.parseInt(recommendId);
	            	}
	            }
			}else{
				//不是分享进入
				if(!StringUtil.isBlank(userId)){
	            	link = link + "&recommendId=" + Integer.parseInt(userId);
	            }
			}
			springIndexReq.setTerminal(Constants.PRODUCT_SHOW_TERMINAL_H5);
			
            Map<String,String> sign = weChatUtil.createAuth(request);
            sign.put("link", link);
            sign.put("share_title", "踏春夺宝|最高加息0.6%，奖励金翻倍！");
            sign.put("share_content", "理财新体验，四月就要赚！尽享加息，奖励金翻倍，一键解锁生财成就！");
            sign.put("share_logo", GlobEnv.getWebURL("/resources/micro2.0/images/qrcode_logo.png"));
            model.put("weichat", sign);
            model.put("source", "all");
		}else{
			springIndexReq.setTerminal(Constants.PRODUCT_SHOW_TERMINAL_PC);
		}
		
		ResMsg_Activity_SpringIndex springIndexRes = (ResMsg_Activity_SpringIndex)communicateBusiness.handleMsg(springIndexReq);
		
		model.put("investRankingList", springIndexRes.getInvestRankingList());
    	model.put("investRankingListSize", springIndexRes.getInvestRankingList() == null ? 0:springIndexRes.getInvestRankingList().size());
    	// 产品列表数据调整便于前端显示开始
        productDataChangeForFront(springIndexRes.getProductList());
        
    	model.put("productList", springIndexRes.getProductList());
        
		return channel + "/activity/spring2017/" + flag;
	}
	/**
     * 【踏春活动】--APP
     * @param request
     * @param response
     * @param model
     * @param flag
     * @param channel
     * @return
     */
	private String springIndexApp(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model,
			String flag, String channel) {
		
		String client = request.getParameter("client");
		model.put("client", client);
		String userIdStr = request.getParameter("userId");
		String userId = "";
		//查询返回踏春活动时间
        ReqMsg_ActivityLuckyDraw_ActivityTime reqTime = new ReqMsg_ActivityLuckyDraw_ActivityTime();
        reqTime.setActivityId(Constants.ACTIVITY_SPRING);
        reqTime.setFormat("yyyy-MM-dd");
        ResMsg_ActivityLuckyDraw_ActivityTime resTime = (ResMsg_ActivityLuckyDraw_ActivityTime)communicateBusiness.handleMsg(reqTime);
        model.put("springIsStart", resTime.getIsStart());

        //判断   用户是否登录
        if(!StringUtil.isBlank(userIdStr)){
        	userId = new DESUtil("cfgubijn").decryptStr(userIdStr);
        	model.put("userLogin","is_login");
        	model.put("userId",userId);
        }else{
        	model.put("userLogin","no_login");
        }
        if(!"noStart".equals(resTime.getIsStart()) && !StringUtil.isBlank(userId)){
        	//活动已开始或已结束 且 用户已登录
        	
        	//查询用户活动时间内投资额,用户邀请列表
        	ReqMsg_Activity_SpringUsersVO springUsersVOReq = new  ReqMsg_Activity_SpringUsersVO();
        	springUsersVOReq.setUserId(Integer.parseInt(userId));
        	ResMsg_Activity_SpringUsersVO springUsersVORes = (ResMsg_Activity_SpringUsersVO)communicateBusiness.handleMsg(springUsersVOReq);
        	model.put("invitedList", springUsersVORes.getUserInvitedList());
        	model.put("invitedListSize", springUsersVORes.getUserInvitedList() == null ?0:springUsersVORes.getUserInvitedList().size());
        	model.put("userInvitedAmount", springUsersVORes.getUserInvestAmount());
        	model.put("invitedAmount",(int)(springUsersVORes.getUserInvestAmount()/10000));
        	model.put("rankingNo", springUsersVORes.getRankingNo());
        }
        
        
		//查询推荐产品列表、投资排行榜列表
		ReqMsg_Activity_SpringIndex springIndexReq = new ReqMsg_Activity_SpringIndex();
		springIndexReq.setTerminal(Constants.PRODUCT_SHOW_TERMINAL_APP);
		
		ResMsg_Activity_SpringIndex springIndexRes = (ResMsg_Activity_SpringIndex)communicateBusiness.handleMsg(springIndexReq);
		
		model.put("investRankingList", springIndexRes.getInvestRankingList());
    	model.put("investRankingListSize", springIndexRes.getInvestRankingList() == null ? 0:springIndexRes.getInvestRankingList().size());
    	// 产品列表数据调整便于前端显示开始
        productDataChangeForFront(springIndexRes.getProductList());
        
    	model.put("productList", springIndexRes.getProductList());
        
		return channel + "/activity/spring2017/" + flag;
	}
	
    /**
     * 产品列表数据调整便于前端显示开始
     * @param dataList
     */
	private void productDataChangeForFront(List<HashMap<String, Object>> dataList) {
        if(!org.springframework.util.CollectionUtils.isEmpty(dataList)) {
            for (Map<String, Object> product : dataList) {
                Double amount = (Double)product.get("maxSingleInvestAmount");
                if(amount != null) {
                    double a = MoneyUtil.divide(amount, 10000d).doubleValue();
                    int integer = (int)a;
                    if(a == integer) {
                        product.put("maxSingleInvestAmount", integer);
                    } else {
                        product.put("maxSingleInvestAmount", String.valueOf(a));
                    }
                }

                Date startTime = (Date) product.get("startTime");
                Integer status = (Integer) product.get("status");
                Date now = new Date();
                // 未发放已发布
                if(Constants.PRODUCT_STATUS_PUBLISH_YES.equals(status)) {
                    product.put("flag", "countdown");
                    // 同一天
                    if(DateUtil.isSameDay(startTime, now)) {
                        product.put("isSameDay", "yes");
                    }
                    // 不同天
                    else {
                        product.put("isSameDay", "no");
                    }
                    product.put("progress", 0);
                }
                // 已经手动结束
                if(Constants.PRODUCT_STATUS_FINISH.equals(status)) {
                    product.put("flag", "finish");
                    product.put("progress", 100);
                }
                // 进行中
                if(Constants.PRODUCT_STATUS_OPENING.equals(status)) {
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
        }
    }
    
    
    
    //============================ 踏春活动E ================================

    //============================ 2017_618活动S ================================

    private String activity2017_618(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, String flag, String channel) {
        String url = channel + "/activity/2017/618/" + flag;
        if(CHANNEL_H5.equals(channel)) {
            String link = GlobEnv.getWebURL("/micro2.0/activity/2017_618");
            String shareTitle = "618投资一夏，赚翻天";
            String shareContent = "618不管你购不购物，这份奖励金不拿白不拿";
            String logo = GlobEnv.getWebURL("/resources/micro/images/bgw.jpg");
            WeChatShareUtil.share(channel, link, shareTitle, shareContent, logo, true, request, model, weChatUtil);
        } else if(CHANNEL_APP.equals(channel)) {
            String client = request.getParameter("client");
            model.put("client", client);
        }
        ReqMsg_Activity_BaseData req = new ReqMsg_Activity_BaseData();
        req.setActivityType(flag);
        ResMsg_Activity_BaseData res = (ResMsg_Activity_BaseData) communicateBusiness.handleMsg(req);
        model.put("isStart", res.getIsStart());
        model.put("startTime", DateUtil.parse(res.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
        model.put("endTime", DateUtil.parse(res.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
        return url;
    }

    //============================ 2017_618活动E ================================

    //====================================== 2017微信点亮存管图标瓜分百万红包 start ====================================

    /**
     * 微信点亮存管图标瓜分百万红包-首页信息
     * @return
     */
    @RequestMapping("micro2.0/activity/lightUpIndex")
    public String lightUpDepLogo2017(HttpServletRequest request, Map<String, Object> model) {
        ReqMsg_Activity_ActivityForLightUp2017PageInfo req = new ReqMsg_Activity_ActivityForLightUp2017PageInfo();

        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
        if(StringUtil.isNotBlank(userId)) {
            model.put("userId", userId);
            req.setUserId(Integer.parseInt(userId));
        }else {
            req.setUserId(0);
            model.put("userId", "0");
        }

        ResMsg_Activity_ActivityForLightUp2017PageInfo res = (ResMsg_Activity_ActivityForLightUp2017PageInfo) communicateBusiness.handleMsg(req);
        model.put("startTime", DateUtil.formatDateTime(DateUtil.parseDate(res.getStartTime()), "yyyy年MM月dd日"));
        model.put("endTime", DateUtil.formatDateTime(DateUtil.parseDate(res.getEndTime()), "MM月dd日"));
        model.put("isStart", res.getIsStart());
        model.put("num", res.getNum());
        model.put("isLightUp", res.getIsLightUp());

        // 分享文案、图片
        String link = GlobEnv.getWebURL("micro2.0/activity/lightUpIndex");;
        String shareTitle = "一键点亮+一键分享=瓜分百万红包！";
        String shareContent = "小招数：参与点亮人数越多可瓜分红包金额越大哦~~~";
        String logo = GlobEnv.getWebURL("/resources/micro/images/bgw.jpg");
        WeChatShareUtil.share("micro2.0", link, shareTitle, shareContent, logo, true, request, model, weChatUtil);

        return "micro2.0/activity/lightUp/lightUp_index";
    }

    /**
     * 微信点亮存管图标瓜分百万红包-点亮操作
     * @return
     */
    @ResponseBody
    @RequestMapping("micro2.0/activity/lightUp")
    public Map<String, Object> lightUp(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        ReqMsg_Activity_ActivityLightUpOperate req = new ReqMsg_Activity_ActivityLightUpOperate();
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
        req.setUserId(Integer.parseInt(userId));

        ResMsg_Activity_ActivityLightUpOperate res = (ResMsg_Activity_ActivityLightUpOperate) communicateBusiness.handleMsg(req);
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            result.put("resCode", res.getResCode());
            result.put("resMsg", res.getResMsg());
            result.put("isLightUp", res.getIsLightUp());
            result.put("num", res.getNum());
        }else {
            result.put("resCode", res.getResCode());
            result.put("resMsg", res.getResMsg());
        }
        return result;
    }

    //====================================== 2017微信点亮存管图标瓜分百万红包 end ======================================

}
