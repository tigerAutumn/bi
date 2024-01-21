/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.site.activity.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_Shake_DrawRedPacket;
import com.pinting.business.hessian.site.message.ReqMsg_Shake_GetWinUserNumber;
import com.pinting.business.hessian.site.message.ResMsg_Shake_DrawRedPacket;
import com.pinting.business.hessian.site.message.ResMsg_Shake_GetWinUserNumber;
import com.pinting.core.base.BaseController;
import com.pinting.core.util.ConstantUtil;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;

/**
 * 摇一摇
 * @author HuanXiong
 * @version $Id: ShakeController.java, v 0.1 2016-3-7 下午2:40:28 HuanXiong Exp $
 */
@Controller
@RequestMapping("/micro2.0/shake")
public class ShakeController extends BaseController {
    
    @Autowired
    private CommunicateBusiness communicateBusiness;
    @Autowired
    private WeChatUtil weChatUtil;
    
    static Map<String, Object> signTitles = new HashMap<String, Object>();
    static Map<String, Object> signIntroductions = new HashMap<String, Object>();
    static {
        signTitles.put("1", "广布施，结善缘");
        signTitles.put("2", "惜光阴，贵于惜黄金");
        signTitles.put("3", "身心灵，常照拂");
        signTitles.put("4", "亲君子，远小人");
        signTitles.put("5", "守正   出奇");
        signTitles.put("6", "不骄奢   不宴起");
        signTitles.put("7", "黄金本无种，出自勤俭家");
        signTitles.put("8", "聚财莫过于寡欲");
        signTitles.put("9", "积善之家必有余庆");
        signTitles.put("10", "我以礼往，人以财来");
        signTitles.put("11", "书中自有黄金屋");
        signTitles.put("12", "家以和字兴，商以和为贵");
        
        signIntroductions.put("1", "勿恶语伤人，猴年必有贵人相助");
        signIntroductions.put("2", "静时常读好书，闲时与高人座谈，财运自亨通");
        signIntroductions.put("3", "健康乃本，快乐是源，笑口常开，财运自然来");
        signIntroductions.put("4", "信义君子多交际，酒色之徒少往来。“正”字值千金");
        signIntroductions.put("5", "谨慎驶得万年船，胆大方能运包天，猴年横财就手");
        signIntroductions.put("6", "勤敬二字心中留，何方偏财不就手？金猴舞正欢");
        signIntroductions.put("7", "食何必珍馐，衣无需锦绣。欲求富贵，勤俭为要");
        signIntroductions.put("8", "窝头白菜，寡欲步行，问心无愧，人间财神");
        signIntroductions.put("9", "欲得善果，先植善因。财品即人品，2016德至财来");
        signIntroductions.put("10", "不妄言，不毁谤，有礼者必不孤，猴年贵人指点财富涨");
        signIntroductions.put("11", "读书声出金石，人生乐事。腹有诗书，财运在远方");
        signIntroductions.put("12", "不以敛财为财，以均为财。和气生财非诳语");
    }
    
    /**
     * 引导页
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/guide")
    public String guide(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        // 分享
        String link = GlobEnv.getWebURL("/micro2.0/shake/guide");
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        model.put("source", "shake");
        model.put("shareTitle", "财神爷送我一个好运秘籍，一试就灵！一秒钟进账318元！");
        model.put("shareContent", "摇一摇抽取转运神签，让自己的好运越来越旺！摇得神签后更可获得318元大红包，机不可失！");
        return "/micro2.0/shake/shake_guide";
    }
    
    /**
     * 摇一摇首页
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/shake")
    public String shake(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        // 分享
        String link = GlobEnv.getWebURL("/micro2.0/shake/guide");
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        model.put("source", "shake");
        model.put("shareTitle", "财神爷送我一个好运秘籍，一试就灵！一秒钟进账318元！");
        model.put("shareContent", "摇一摇抽取转运神签，让自己的好运越来越旺！摇得神签后更可获得318元大红包，机不可失！");
        //request.getSession().setAttribute("token", UUID.randomUUID().toString());
        ReqMsg_Shake_GetWinUserNumber req = new ReqMsg_Shake_GetWinUserNumber();
        ResMsg_Shake_GetWinUserNumber res = (ResMsg_Shake_GetWinUserNumber) communicateBusiness.handleMsg(req);
        model.put("count", res.getCount());
        return "/micro2.0/shake/shake_index";
    }
    
    /**
     * 进入输入手机的页面
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/shakeSuccess")
    public String shakeSuccess(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        model.put("sign", request.getParameter("sign"));
        model.put("signTitle", signTitles.get(request.getParameter("sign")));
        model.put("signIntroduction", signIntroductions.get(request.getParameter("sign")));
        
        model.put("shareTitle", "财神爷送我的转运神签是「"+signTitles.get(request.getParameter("sign"))+"」你也来摇一摇你的转运神签吧！");
        model.put("shareContent", "摇一摇抽取转运神签，让自己的好运越来越旺！摇得神签后更可获得318元大红包，机不可失！");
        // 分享
        String link = GlobEnv.getWebURL("/micro2.0/shake/guide");
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        model.put("source", "shake");
        return "/micro2.0/shake/shake_succ_mobile";
    }
    
    /**
     * 领取红包
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/mobileSubmit")
    public Map<String, Object> mobileSubmit(ReqMsg_Shake_DrawRedPacket reqMsg, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        ResMsg_Shake_DrawRedPacket resMsg = (ResMsg_Shake_DrawRedPacket) communicateBusiness.handleMsg(reqMsg);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())) {
            result.put("isNewUser", resMsg.getIsNewUser());
            result.put("resCode", "000");
            result.put("resMsg", resMsg.getResMsg());
        } else {
            result.put("resCode", resMsg.getResCode());
            result.put("resMsg", resMsg.getResMsg());
        }
        return result;
    }
    
    
}
