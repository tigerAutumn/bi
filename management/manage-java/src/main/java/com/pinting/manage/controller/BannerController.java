package com.pinting.manage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.core.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_Banner_BannerAddOrUpdate;
import com.pinting.business.hessian.manage.message.ReqMsg_Banner_BannerCount;
import com.pinting.business.hessian.manage.message.ReqMsg_Banner_BannerList;
import com.pinting.business.hessian.manage.message.ReqMsg_Banner_PictureAddOrUpdate;
import com.pinting.business.hessian.manage.message.ReqMsg_Banner_PictureCount;
import com.pinting.business.hessian.manage.message.ReqMsg_Banner_PictureList;
import com.pinting.business.hessian.manage.message.ReqMsg_Banner_SelectBannerConfigById;
import com.pinting.business.hessian.manage.message.ReqMsg_Banner_WebsitePictureInfo;
import com.pinting.business.hessian.manage.message.ResMsg_Banner_BannerAddOrUpdate;
import com.pinting.business.hessian.manage.message.ResMsg_Banner_BannerCount;
import com.pinting.business.hessian.manage.message.ResMsg_Banner_BannerList;
import com.pinting.business.hessian.manage.message.ResMsg_Banner_PictureAddOrUpdate;
import com.pinting.business.hessian.manage.message.ResMsg_Banner_PictureCount;
import com.pinting.business.hessian.manage.message.ResMsg_Banner_PictureList;
import com.pinting.business.hessian.manage.message.ResMsg_Banner_SelectBannerConfigById;
import com.pinting.business.hessian.manage.message.ResMsg_Banner_WebsitePictureInfo;
import com.pinting.business.util.Constants;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.GlobEnv;
import com.pinting.util.ReturnDWZAjax;
/**
 * banner管理台
 * @author bianyatian
 * @2016-3-11 下午6:08:43
 */
@Controller
public class BannerController {
	
	@Autowired
	@Qualifier("dispatchService")
	public HessianService bannerService;	
	
	/**
	 * 根据传入条件获取列表
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping("/banner/banner_index")
	public String bannerIndex(HashMap<String,Object> model,ReqMsg_Banner_BannerList req,
			HttpServletRequest request, HttpServletResponse response, String newChannel,String cc){
		if(StringUtils.isNotBlank(cc)){
			newChannel = cc;
		}else{
			if(StringUtils.isBlank(newChannel)){
				newChannel= Constants.BANNER_CHANNEL_MICRO; //币港湾微信
			}	
		}
		//APP启动页新增按钮是否显示
		if(Constants.BANNER_CHANNEL_APP_START.equals(newChannel)) {
			ReqMsg_Banner_BannerCount reqCount = new ReqMsg_Banner_BannerCount();
			reqCount.setChannel(newChannel);
			ResMsg_Banner_BannerCount resCount = (ResMsg_Banner_BannerCount)bannerService.handleMsg(reqCount);
			if(resCount.getCount() >= 1) {
				model.put("addButton", Constants.BANNER_ADD_BUTTON_HIDDEN);
			}
		}

		req.setChannel(newChannel);
		ResMsg_Banner_BannerList res = (ResMsg_Banner_BannerList)bannerService.handleMsg(req);
		model.put("record", res);
		model.put("list", res.getValueList());
		model.put("newChannel", newChannel);
		model.put("req", req);
		String mUrl = GlobEnv.get("gen.web");
		model.put("mUrl", mUrl);
		return "/banner/banner_index";
	}
	
	/**
	 * 新增或修改，需要获取当前展示渠道下已有的有效banner总条数
	 * @param model
	 * @param type
	 * @param newChannel
	 * @param bannerId
	 * @param request
	 * @param response
     * @return
     */
	@RequestMapping("/banner/detail")
	public String detail(HashMap<String,Object> model, String type,String newChannel,
			String bannerId, HttpServletRequest request, HttpServletResponse response){
		try {
			if(StringUtils.isBlank(newChannel)){
				newChannel = Constants.BANNER_CHANNEL_MICRO; //币港湾微信
			}
			//获取当前总条数
			ReqMsg_Banner_BannerCount req = new ReqMsg_Banner_BannerCount();
			req.setChannel(newChannel);
			ResMsg_Banner_BannerCount res = (ResMsg_Banner_BannerCount)bannerService.handleMsg(req);
			
			model.put("type", type);
			
			if("add".equals(type)){
				model.put("count", res.getCount()+1);
				model.put("newChannel", newChannel);
			}else{
				//根据id查询
				ReqMsg_Banner_SelectBannerConfigById reqBanner = new ReqMsg_Banner_SelectBannerConfigById();
				reqBanner.setBannerId(Integer.parseInt(bannerId));
				ResMsg_Banner_SelectBannerConfigById resBanner = (ResMsg_Banner_SelectBannerConfigById)bannerService.handleMsg(reqBanner);
				
				if((Constants.BANNER_CHANNEL_APP.equals(request.getParameter("newChannel")) && StringUtils.isNotBlank(resBanner.getUrl()) ||
						(Constants.BANNER_CHANNEL_APP_START.equals(request.getParameter("newChannel")) && StringUtils.isNotBlank(resBanner.getUrl())))){
					if(StringUtils.startsWith(resBanner.getUrl(), "APP")){
						//banner类型是APP且url以app开头，则pushType=APP，appPage为字符串第四位开始
						model.put("pushType", "APP");
						model.put("appPage", resBanner.getUrl().substring(4));
					}else{
						model.put("pushType", "url");
						model.put("appPage", resBanner.getUrl().trim());
					}
				}
				model.put("banner", resBanner);
				if(resBanner.getPriority() != null) {
					if(resBanner.getPriority() == -1){
						model.put("count", res.getCount()+1);
					}else{
						model.put("count", res.getCount());
					}
				}
				model.put("newChannel", resBanner.getChannel());
				String mUrl = GlobEnv.get("gen.web");
				model.put("mUrl", mUrl);
				model.put("activityType", resBanner.getActivityType());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/banner/banner_detail";
	}
	
	@RequestMapping("/banner/addOrUpdate")
	public @ResponseBody Map<Object,Object> addOrUpdate(ReqMsg_Banner_BannerAddOrUpdate req,
			HttpServletRequest request, HttpServletResponse response,HashMap<String,Object> model,
			String pushType, String appPage){
		try {
			CookieManager cookie = new CookieManager(request);
			String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
					CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
			if(StringUtils.isBlank(mUserId)){
				return ReturnDWZAjax.fail("未获取到登录者信息");
			}else{
				req.setmUserId(Integer.parseInt(mUserId));
			}
			req.setChannel(request.getParameter("newChannel"));
			if(Constants.BANNER_CHANNEL_APP.equals(request.getParameter("newChannel")) || Constants.BANNER_CHANNEL_APP_START.equals(request.getParameter("newChannel"))){
				//如新增和修改的banner类型为APP或者APP_START，则判断跳转类型
				if("APP".equals(pushType)){
					req.setUrl(pushType+":"+appPage);
				}else{
					req.setUrl(appPage);
				}
			}

			//APP启动页删除标记位
			req.setDeleteFlag(request.getParameter("deleteFlag"));
			//活动类型
			String activity_type = request.getParameter("activity_type");
			if(StringUtils.isNotEmpty(activity_type)) {
				req.setActivityType(activity_type);
			}else {
				req.setActivityType(null);
			}
			ResMsg_Banner_BannerAddOrUpdate res = (ResMsg_Banner_BannerAddOrUpdate)bannerService.handleMsg(req);
			
			if("999".equals(res.getRetCode())){
				return ReturnDWZAjax.fail(res.getRetMsg());
			}else{
				model.put("channel", req.getChannel());
				return ReturnDWZAjax.success("添加成功！");
			}
			
		} catch (Exception e) {
			return ReturnDWZAjax.fail("添加失败，原因不详！");
		}
	}
		
	/**
	 * 根据传入条件获取列表
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping("/banner/website_picture")
	public String websitePictureIndex(HashMap<String,Object> model, ReqMsg_Banner_PictureList req,
			HttpServletRequest request, HttpServletResponse response, String newShowPageLabel, String cc){
		if(StringUtils.isNotBlank(cc)){
			newShowPageLabel = cc;
		}else{
			if(StringUtils.isBlank(newShowPageLabel)){
				newShowPageLabel = Constants.BANNER_SHOWPAGE_LABEL_INVEST; 
			}	
		}
		req.setShowPageLabel(newShowPageLabel);
		ResMsg_Banner_PictureList res = (ResMsg_Banner_PictureList)bannerService.handleMsg(req);
		model.put("record", res);
		model.put("list", res.getValueList());
		model.put("newShowPageLabel", newShowPageLabel);
		model.put("req", req);
		String mUrl = GlobEnv.get("gen.web");
		model.put("mUrl", mUrl);
		return "/banner/website_picture";
	}
	
	/**
	 * 新增或修改，需要获取当前展示渠道下已有的有效banner总条数
	 * @param model
	 * @param type
	 * @param newChannel
	 * @param bannerId
	 * @param request
	 * @param response
     * @return
     */
	@RequestMapping("/banner/website_picture_detail")
	public String websitePictureDetail(HashMap<String,Object> model, String type, String newChannel, String newShowPageLabel,
			 HttpServletRequest request, HttpServletResponse response){
		String bannerId = request.getParameter("bannerId");
		try {
			if(StringUtils.isBlank(newChannel)){
				newChannel = Constants.BANNER_CHANNEL_GEN;
			}
			//获取当前总条数
			ReqMsg_Banner_PictureCount req = new ReqMsg_Banner_PictureCount();
			req.setChannel(newChannel);
			ResMsg_Banner_PictureCount res = (ResMsg_Banner_PictureCount)bannerService.handleMsg(req);
			model.put("type", type);
			model.put("showPageLabel", newShowPageLabel);
			model.put("newChannel", newChannel);
			
			if("add".equals(type)){
				model.put("count", res.getCount()+1);
				model.put("newChannel", newChannel);
			}else{
				//根据id查询
				ReqMsg_Banner_WebsitePictureInfo reqBanner = new ReqMsg_Banner_WebsitePictureInfo();
				reqBanner.setBannerId(Integer.parseInt(bannerId));
				ResMsg_Banner_WebsitePictureInfo resBanner = (ResMsg_Banner_WebsitePictureInfo)bannerService.handleMsg(reqBanner);
				
				model.put("banner", resBanner);
				if(resBanner.getPriority() != null) {
					if(resBanner.getPriority() == -1){
						model.put("count", res.getCount()+1);
					}else{
						model.put("count", res.getCount());
					}
				}
				model.put("newChannel", resBanner.getChannel());
				String mUrl = GlobEnv.get("gen.web");
				model.put("mUrl", mUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/banner/website_picture_detail";
	}
	
	@RequestMapping("/banner/website/addOrUpdate")
	public @ResponseBody Map<Object,Object> websiteAddOrUpdate(ReqMsg_Banner_PictureAddOrUpdate req,
			HttpServletRequest request, HttpServletResponse response,HashMap<String,Object> model, String appPage){
		try {
			CookieManager cookie = new CookieManager(request);
			String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
					CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
			if(StringUtils.isBlank(mUserId)){
				return ReturnDWZAjax.fail("未获取到登录者信息");
			}else{
				req.setmUserId(Integer.parseInt(mUserId));
			}
			req.setDeleteFlag(request.getParameter("deleteFlag"));

			ResMsg_Banner_PictureAddOrUpdate res = (ResMsg_Banner_PictureAddOrUpdate)bannerService.handleMsg(req);
			
			if("999".equals(res.getRetCode())){
				return ReturnDWZAjax.fail(res.getRetMsg());
			}else{
				model.put("channel", req.getChannel());
				return ReturnDWZAjax.success("操作成功！");
			}
		} catch (Exception e) {
			return ReturnDWZAjax.fail("添加失败，原因不详！");
		}
	}
		
}
