package com.pinting.site.extraproduct.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_Product_InfoQuery;
import com.pinting.business.hessian.site.message.ReqMsg_SysConfig_QuerySysConfig;
import com.pinting.business.hessian.site.message.ReqMsg_User_CheckNewUser;
import com.pinting.business.hessian.site.message.ResMsg_Product_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_SysConfig_QuerySysConfig;
import com.pinting.business.hessian.site.message.ResMsg_User_CheckNewUser;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;

@Controller
@Scope("prototype")
public class ExtraProductController extends BaseController {
	
	@Autowired
	private CommunicateBusiness siteService;
	@Autowired
    private WeChatUtil weChatUtil;
	
	/**
	 * 额外产品列表查询
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/extraProduct/index")
	public String extraProductInit(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response, Map<String,Object> model){
	    if("micro2.0".equals(channel)) {
	        // 分享
	        String link = GlobEnv.getWebURL("/micro2.0/extraProduct/index");
	        Map<String,String> sign = weChatUtil.createAuth(request);
	        sign.put("link", link);
	        model.put("weichat", sign);
	        model.put("source", "318_activity");
	    }
	    
		List<ResMsg_Product_InfoQuery> pros = new ArrayList<>();
		
		ReqMsg_Product_InfoQuery req = new ReqMsg_Product_InfoQuery();
		req.setId(Constants.PRODUCT_ID_NEWER_1_MONTH);
		ResMsg_Product_InfoQuery res1 = (ResMsg_Product_InfoQuery) siteService.handleMsg(req);
		//投资期限转换
		int term = res1.getTrem();
		int term4Day;
		if(term < 0){
			term4Day = Math.abs(term);
		}else if(term == 12){
			term4Day = 365;
		}else{
			term4Day = term*30;
		}
		res1.setTrem(term4Day);
		pros.add(res1);
		
		req.setId(Constants.PRODUCT_ID_ADD_RATE_1_YEAR);
		ResMsg_Product_InfoQuery res2 = (ResMsg_Product_InfoQuery) siteService.handleMsg(req);
		//投资期限转换
		int term2 = res2.getTrem();
		int term4Day2;
		if(term2 < 0){
			term4Day2 = Math.abs(term2);
		}else if(term2 == 12){
			term4Day2 = 365;
		}else{
			term4Day2 = term2*30;
		}
		res2.setTrem(term4Day2);
		pros.add(res2);
		model.put("pros", pros);
		
		return channel + "/extra_product/extra_product_index";
	}
	
	@RequestMapping("{channel}/extraProduct/balanceQuery")
	public @ResponseBody HashMap<String,Object> balanceQuery(HttpServletRequest request,HttpServletResponse response, String proId){
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		
		ReqMsg_Product_InfoQuery req = new ReqMsg_Product_InfoQuery();
		req.setId(Integer.valueOf(proId));
		ResMsg_Product_InfoQuery res = (ResMsg_Product_InfoQuery) siteService.handleMsg(req);
		
		result.put("productBalance", MoneyUtil.format(MoneyUtil.subtract(res.getMaxTotalAmount(), res.getCurrTotalAmount())));
		
		return result;
	}
	
	@RequestMapping("{channel}/extraProduct/isNewUser")
	public @ResponseBody HashMap<String,Object> isNewUser(HttpServletRequest request,HttpServletResponse response, String proId){
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		CookieManager manangr = new CookieManager(request);
		String userId = manangr.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		ReqMsg_User_CheckNewUser checkNewUser = new ReqMsg_User_CheckNewUser();
		checkNewUser.setUserId(Integer.valueOf(userId));
		ResMsg_User_CheckNewUser checkRes = (ResMsg_User_CheckNewUser) siteService.handleMsg(checkNewUser);
		//非新用户
		if(checkRes.getIsNewUser()){
			result.put("isNewUser", "true");
		}else{
			result.put("isNewUser", "false");
		}
		
		return result;
	}
	
	@RequestMapping("micro2.0/extraProduct/buyForward")
	public String buyForward(String pId, String t, String p,
			HttpServletRequest request, HttpServletResponse response, Map<String,Object> model){
		//非法请求
		if(StringUtil.isEmpty(pId) 
				|| (Constants.PRODUCT_ID_NEWER_1_MONTH != Integer.valueOf(pId)
				&& Constants.PRODUCT_ID_ADD_RATE_1_YEAR != Integer.valueOf(pId))){
			
			return "micro2.0/extra_product/extra_product_illegal";
		}
		//判断活动是否下架
		ReqMsg_SysConfig_QuerySysConfig confReq = new ReqMsg_SysConfig_QuerySysConfig();
		confReq.setKey("CHECK_NEWUSER_END_TIME");
		ResMsg_SysConfig_QuerySysConfig confRes = (ResMsg_SysConfig_QuerySysConfig) siteService.handleMsg(confReq);
		String endTime = confRes.getConfValue();
		Date now = new Date();
		if(now.compareTo(DateUtil.parseDateTime(endTime)) > 0){
			return "micro2.0/extra_product/extra_product_error";
		}
		
  		//新手标需判断用户是否是新用户
  		if(Constants.PRODUCT_ID_NEWER_1_MONTH == Integer.valueOf(pId)){
  			CookieManager manangr = new CookieManager(request);
  			String userId = manangr.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
  			ReqMsg_User_CheckNewUser checkNewUser = new ReqMsg_User_CheckNewUser();
  			checkNewUser.setUserId(Integer.valueOf(userId));
  			ResMsg_User_CheckNewUser checkRes = (ResMsg_User_CheckNewUser) siteService.handleMsg(checkNewUser);
  			//非新用户，不能进入购买录入页
  			if(!checkRes.getIsNewUser()){
  				if("extra".equals(p)){
  					return "forward:/micro2.0/extraProduct/index?e=notNewUser";
  				}else{
  					return "forward:/micro2.0/index?e=notNewUser";
  				}
  			}
  		}
  		//查询本产品信息
  		ReqMsg_Product_InfoQuery req = new ReqMsg_Product_InfoQuery();
  		req.setId(Integer.valueOf(pId));
  		ResMsg_Product_InfoQuery infoRes = (ResMsg_Product_InfoQuery) siteService.handleMsg(req);
  		//投资期限转换
  		int term = infoRes.getTrem();
  		int term4Day;
  		if(term < 0){
  			term4Day = Math.abs(term);
  		}else if(term == 12){
  			term4Day = 365;
  		}else{
  			term4Day = term*30;
  		}
  		infoRes.setTrem(term4Day);
  		StringBuffer params = new StringBuffer();
  		try {
			params.append("?rate=" + infoRes.getRate())
			.append("&id=" + infoRes.getId())
			.append("&term=" + infoRes.getTrem())
			.append("&name=" + URLEncoder.encode(infoRes.getProductName(), "UTF-8"))
			.append("&minInvestAmount=" + infoRes.getMinInvestAmount());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if("buy".equals(t)){
			return "forward:/micro2.0/regular/bind" + params;
		}else{
			return "forward:/micro2.0/regular/index" + params;
		}
  		
	}
	
	@RequestMapping("gen2.0/extraProduct/buyForward")
	public String buyForwardGen(String pId, HttpServletRequest request, HttpServletResponse response, Map<String,Object> model){
		//非法请求
		if(StringUtil.isEmpty(pId) 
				|| (Constants.PRODUCT_ID_NEWER_1_MONTH != Integer.valueOf(pId)
				&& Constants.PRODUCT_ID_ADD_RATE_1_YEAR != Integer.valueOf(pId))){
			
			return "gen2.0/extra_product/extra_channel_error";
		}
		
		//判断活动是否下架
		ReqMsg_SysConfig_QuerySysConfig confReq = new ReqMsg_SysConfig_QuerySysConfig();
		confReq.setKey("CHECK_NEWUSER_END_TIME");
		ResMsg_SysConfig_QuerySysConfig confRes = (ResMsg_SysConfig_QuerySysConfig) siteService.handleMsg(confReq);
		String endTime = confRes.getConfValue();
		Date now = new Date();
		if(now.compareTo(DateUtil.parseDateTime(endTime)) > 0){
			model.put("isOff", true);
			return "gen2.0/regular/error";
		}
		
  		//新手标需判断用户是否是新用户
  		if(Constants.PRODUCT_ID_NEWER_1_MONTH == Integer.valueOf(pId)){
  			CookieManager manangr = new CookieManager(request);
  			String userId = manangr.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
  			ReqMsg_User_CheckNewUser checkNewUser = new ReqMsg_User_CheckNewUser();
  			checkNewUser.setUserId(Integer.valueOf(userId));
  			ResMsg_User_CheckNewUser checkRes = (ResMsg_User_CheckNewUser) siteService.handleMsg(checkNewUser);
  			//非新用户，不能进入购买录入页
  			if(!checkRes.getIsNewUser()){
  				return "forward:/gen2.0/extraProduct/index?e=notNewUser";
  			}
  		}
  		//查询本产品信息
  		ReqMsg_Product_InfoQuery req = new ReqMsg_Product_InfoQuery();
  		req.setId(Integer.valueOf(pId));
  		ResMsg_Product_InfoQuery infoRes = (ResMsg_Product_InfoQuery) siteService.handleMsg(req);
  		//投资期限转换
  		int term = infoRes.getTrem();
  		int term4Day;
  		if(term < 0){
  			term4Day = Math.abs(term);
  		}else if(term == 12){
  			term4Day = 365;
  		}else{
  			term4Day = term*30;
  		}
  		infoRes.setTrem(term4Day);
  		StringBuffer params = new StringBuffer();
  		params.append("?rate=" + infoRes.getRate())
  		.append("&id=" + infoRes.getId())
  		.append("&term=" + infoRes.getTrem())
  		.append("&name=" + infoRes.getProductName())
  		.append("&minInvestAmount=" + infoRes.getMinInvestAmount());
  		
  		return "forward:/gen2.0/regular/index" + params;
  		
	}
	
	
	/**
	 * 判断用户是否登录和是否是新用户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/extraProduct/loginUserCheck")
	public @ResponseBody HashMap<String,Object> loginUserCheck(HttpServletRequest request,HttpServletResponse response){
		
		
		HashMap<String,Object> result = new HashMap<String,Object>();
		
		CookieManager manangr = new CookieManager(request);
		String userId = manangr.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		if(StringUtil.isNotBlank(userId)){
			result.put("isLogin", "true");
			ReqMsg_User_CheckNewUser checkNewUser = new ReqMsg_User_CheckNewUser();
			checkNewUser.setUserId(Integer.valueOf(userId));
			ResMsg_User_CheckNewUser checkRes = (ResMsg_User_CheckNewUser) siteService.handleMsg(checkNewUser);
			//非新用户
			if(checkRes.getIsNewUser()){
				result.put("isNewUser", "true");
			}else{
				result.put("isNewUser", "false");
			}
		}else{
			result.put("isLogin", "false");
		}
		return result;
	}
}
