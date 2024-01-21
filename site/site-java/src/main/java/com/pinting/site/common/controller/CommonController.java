/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.site.common.controller;

import com.pinting.business.hessian.site.message.ReqMsg_DepGuide_FindDepGuideInfo;
import com.pinting.business.hessian.site.message.ResMsg_DepGuide_FindDepGuideInfo;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;
import org.patchca.color.ColorFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.Captcha;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.text.renderer.BestFitTextRenderer;
import org.patchca.text.renderer.TextRenderer;
import org.patchca.word.RandomWordFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author HuanXiong
 * @version $Id: CommonController.java, v 0.1 2016-4-27 上午10:34:33 HuanXiong Exp $
 */
@Controller
@Scope("prototype")
@RequestMapping("/{channel}/common")
public class CommonController {

    @Autowired
    private CommunicateBusiness siteService;
    private static Logger logger = LoggerFactory.getLogger(CommonController.class);

    private static ConfigurableCaptchaService ccs = null;
    private static ColorFactory cf = null;
    private static RandomFontFactory ff = null;
    private static RandomWordFactory rwf = null;
    private static TextRenderer tr = null;

    /* 验证码外观样式 */
//	private static WobbleRippleFilterFactory wrff = null;
//	private static MarbleRippleFilterFactory mrff = null;
//	private static DoubleRippleFilterFactory drff = null;
    private static CurvesRippleFilterFactory crff = null;

    /**
     * 初始化参数
     */
    static {
        if (ccs == null) ccs = new ConfigurableCaptchaService();
        if (cf == null) cf = new SingleColorFactory(new Color(25, 60, 170));
        if (ff == null) ff = new RandomFontFactory();
        if (rwf == null) rwf = new RandomWordFactory();
        if (tr == null) tr = new BestFitTextRenderer();

		/* 样式 */
//		wrff = new WobbleRippleFilterFactory();// 摆波纹
//		mrff = new MarbleRippleFilterFactory(); // 大理石波纹
//		drff = new DoubleRippleFilterFactory();  //双波纹
        if (crff == null) crff = new CurvesRippleFilterFactory(new SingleColorFactory(new Color(153, 204, 255))); //干扰线波纹

        rwf.setCharacters("0123456789"); // 验证码随机值
        ff.setRandomStyle(false);
        ff.setMaxSize(30);
        ff.setMinSize(30);
        ccs.setTextRenderer(tr);
        ccs.setFontFactory(ff);
        ccs.setWordFactory(rwf);
        ccs.setColorFactory(cf);
        ccs.setWidth(200);
        ccs.setHeight(40);
        ccs.setFilterFactory(crff);
    }

    @RequestMapping("/captcha/code")
    public void captchaCode(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        String code = null;
        try {
            CookieManager cookie = new CookieManager(request);
            String ts = request.getParameter("ts");
            String tsCookie = cookie.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_CODE_TS.getName(), true);
            if(StringUtil.isNotBlank(tsCookie)) {
                if(tsCookie.equals(ts)) {
                    return;
                }
            }
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");
            rwf.setMaxLength(4);
            rwf.setMinLength(4);
            Captcha captcha = ccs.getCaptcha();

            code = captcha.getChallenge();
            cookie.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_CODE.getName(), code, true);
            cookie.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_CODE_TS.getName(), ts, true);
            cookie.save(response, CookieEnums._SITE.getName(), true);
            logger.info("图片验证码：{" + code + "}");
            ImageIO.write(captcha.getImage(), "png", response.getOutputStream());
            response.getOutputStream().close();
        } catch (IOException e) {
            logger.info("图片转换失败，图片验证码：{" + code + "}" + "，错误原因：{" + e.getMessage() + "}");
        }
    }

    @RequestMapping("/success")
    public String test(@PathVariable String channel, HttpServletRequest request, Map<String, Object> model) {
        
        return channel + "/regular/regular_success";
    }

    @ResponseBody
    @RequestMapping("/checkHFBankDepOpened")
    public Map<String, Object> checkHFBankDepOpened(HttpServletRequest request) {
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
        // 存管引导信息
        ReqMsg_DepGuide_FindDepGuideInfo depGuideReq = new ReqMsg_DepGuide_FindDepGuideInfo();
        depGuideReq.setUserId(Integer.parseInt(userId));
        depGuideReq.setContainRisk(true);
        ResMsg_DepGuide_FindDepGuideInfo depGuideRes = (ResMsg_DepGuide_FindDepGuideInfo) siteService.handleMsg(depGuideReq);

        Map<String, Object> result = new HashMap<>();
        result.put("hfDepGuideInfo", depGuideRes);
        return result;
    }

}
