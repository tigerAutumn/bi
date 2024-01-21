package com.pinting.site.home.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_Home_BuyOrders;
import com.pinting.business.hessian.site.message.ReqMsg_Home_InfoQuery;
import com.pinting.business.hessian.site.message.ReqMsg_Home_NoviceStandardPlan;
import com.pinting.business.hessian.site.message.ReqMsg_Home_ServerUsableCheck;
import com.pinting.business.hessian.site.message.ResMsg_Home_BuyOrders;
import com.pinting.business.hessian.site.message.ResMsg_Home_NoviceStandardPlan;
import com.pinting.business.hessian.site.message.ResMsg_Home_ServerUsableCheck;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.exception.PTException;
import com.pinting.core.util.ConstantUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;

@Controller
public class HomeController extends BaseController{
	@Autowired
	private CommunicateBusiness homeService;
//	@RequestMapping("{channel}/home/index")
//	public String loginInit(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,ReqMsg_Home_InfoQuery regHome,Map<String,Object> model){
//		
//		String user = request.getParameter("user");  //当点击用户分享链接过来时会有此参数
//		if(user !=null && !"".equals(user)){
//			user = user.substring(0, user.length()-36);
//			CookieManager cookieManager = new CookieManager(request);
//			String recommendId = cookieManager.getValue(CookieEnums._SITE.getName(),CookieEnums._SITE_RECOMMEND_ID.getName(), true);
//			if(recommendId == null || "".equals(recommendId)){ //说明并没有存在推荐人，同时下面将推荐人的id号存入cookie当中
//				cookieManager.setValue(CookieEnums._SITE.getName(),CookieEnums._SITE_RECOMMEND_ID.getName(),user, true);
//				cookieManager.save(response, CookieEnums._SITE.getName(), null, "/", 600, true);
//			}
//		}
//		
//		ResMsg_Home_InfoQuery resHome = (ResMsg_Home_InfoQuery) homeService.handleMsg(regHome);
//		
//		ArrayList<HashMap<String, Object>> list = resHome.getProductList();
//			
//		for (HashMap<String, Object> hashMap : list) {
//			
//			model.put((String)hashMap.get("typeName"), hashMap);
//		}
//		
//		Map<String,String> sign = WeiChat.creatSign(request);
//		
//		model.put("weichat", sign);
//		return channel + "/home/home_index";
//	}
	@RequestMapping("{channel}/home/notice/index")
	public String notice(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,ReqMsg_Home_InfoQuery regHome,Map<String,Object> model){
		return channel + "/home/notice";
	}
	
	@RequestMapping("{channel}/home/intro/index")
	public String intro(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,ReqMsg_Home_InfoQuery regHome,Map<String,Object> model){
		return channel + "/home/intro";
	}
	@RequestMapping("{channel}/home/attention")
	public String attentionInit(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,ReqMsg_Home_InfoQuery regHome,Map<String,Object> model){
		return channel + "/home/attention";
	}
	
	@RequestMapping("{channel}/home/buyOrder")
	public String buyOrderInfo(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,ReqMsg_Home_BuyOrders req,HashMap<String, Object> model){
		ResMsg_Home_BuyOrders resMsg = (ResMsg_Home_BuyOrders) homeService.handleMsg(req);
		
		model.put("orders", resMsg.getUserBuyOrdersList());
		
		return channel + "/home/orderList";
	}
	
	@RequestMapping("{channel}/home/safe")
	public String safe(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,ReqMsg_Home_InfoQuery regHome,Map<String,Object> model){
		return channel + "/safe/safe";
	}
	
	@RequestMapping("{channel}/home/video")
	public String video(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String,Object> model){
		return channel + "/home/video";
	}
	
	/**
     * 首页新手标跳转页面
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("gen2.0/home/noviceStandard")
	public String aboutBiGangWan(HttpServletRequest request, HttpServletResponse response,Map<String,Object> model){
    	
    	CookieManager cookie = new CookieManager(request);
		String user_id = cookie.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		if(user_id != null && !"0".equals(user_id)) {
			model.put("user_id", user_id);
		}else{
			model.put("user_id", null);
		}
    	
    	ReqMsg_Home_NoviceStandardPlan planReq = new ReqMsg_Home_NoviceStandardPlan();
    	planReq.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_PC);
    	ResMsg_Home_NoviceStandardPlan res = (ResMsg_Home_NoviceStandardPlan)homeService.handleMsg(planReq);
    	HashMap<String, Object> noviceStandardPlanProduct = res.getNoviceStandardProduct();
    	if(noviceStandardPlanProduct != null) {
    		model.put("id", noviceStandardPlanProduct.get("id"));
    		model.put("rate", noviceStandardPlanProduct.get("rate"));
    		model.put("totalRedPacketSubtract", res.getTotalRedPacketSubtract());
    	}
	    return "gen2.0/home/novice_standard";
	}
    
    /**
     * 服务可用测试（用户ng调用url测试服务是否可用）
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/common/serverUsableCheck")
	@ResponseBody
	public Map<String, Object> serverUsableCheck(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ResMsg_Home_ServerUsableCheck resp = (ResMsg_Home_ServerUsableCheck) homeService.handleMsg(new ReqMsg_Home_ServerUsableCheck());
			if(!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
				throw new PTException("【服务可用测试】服务响应异常...");
			}else{
				result.put("resCode", ConstantUtil.BSRESCODE_SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	/**
	 * 电信业务经营许可证页面
	 * @param channel
	 * @param request
	 * @param response
     * @return
     */
	@RequestMapping("{channel}/home/businessLicense")
	public String businessLicenseInit(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String,Object> model){
		String url = "";
		model.put("channel", channel);
		if("gen2.0".equals(channel)) {
			url = "/gen2.0/home/business_license";
		}else if("gen178".equals(channel)) {
			url = "/gen178/home/business_license";
		}
		return url;
	}


}
