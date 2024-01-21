
package com.pinting.manage.controller;

import java.awt.Color;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.core.cookie.CookieManager;
import com.pinting.manage.enums.CookieEnums;



/**
 * 验证码
 */
@Controller
public class CaptchaController {
	
	private Logger LOG = LoggerFactory.getLogger(CaptchaController.class);
	
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
	public void captchaCode(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		
		rwf.setMaxLength(4);
		rwf.setMinLength(4);
		
		Captcha captcha = ccs.getCaptcha();
		
		CookieManager cookie = new CookieManager(request);
		cookie.setValue(CookieEnums._MANAGE_CODE_GROUP.getName(), CookieEnums._MANAGE_CODE.getName(), captcha.getChallenge(), false);
		cookie.save(response, CookieEnums._MANAGE_CODE_GROUP.getName(), false);
		
		try {
			ImageIO.write(captcha.getImage(), "png", response.getOutputStream());
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}
}