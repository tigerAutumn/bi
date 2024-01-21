package com.pinting.site.fund.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_Fund_CheckIn;
import com.pinting.business.hessian.site.message.ReqMsg_Fund_ListQuery;
import com.pinting.business.hessian.site.message.ReqMsg_Fund_NetValueListQuery;
import com.pinting.business.hessian.site.message.ResMsg_Fund_CheckIn;
import com.pinting.business.hessian.site.message.ResMsg_Fund_ListQuery;
import com.pinting.business.hessian.site.message.ResMsg_Fund_NetValueListQuery;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;

@Controller
public class FundController extends BaseController{
	
	@Autowired
	private CommunicateBusiness fundService;
	@Autowired
	private WeChatUtil weChatUtil;
	
	@RequestMapping("{channel}/fund/index")
	public String fundInit(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response,HashMap<Object,Object> model) {
		String user = request.getParameter("user");  //当点击用户分享链接过来时会有此参数
		if(user !=null && !"".equals(user)){
			user = user.substring(0, user.length()-36);
			CookieManager cookieManager = new CookieManager(request);
			String recommendId = cookieManager.getValue(CookieEnums._SITE.getName(),CookieEnums._SITE_RECOMMEND_ID.getName(), true);
			if(recommendId == null || "".equals(recommendId)){ //说明并没有存在推荐人，同时下面将推荐人的id号存入cookie当中
				cookieManager.setValue(CookieEnums._SITE.getName(),CookieEnums._SITE_RECOMMEND_ID.getName(),user, true);
				cookieManager.save(response, CookieEnums._SITE.getName(), null, "/", 5*365*24*3600, true);
			}
		}
		
		Map<String,String> sign = weChatUtil.createAuth(request);
		
		model.put("weichat", sign);
		
		
		return channel + "/fund/fund_index";
	}
	
	@RequestMapping("{channel}/fund/appoint")
	public String fundAppoint(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response) {
		
		return channel + "/fund/fund_appoint";
	}
	
	@RequestMapping("{channel}/fund/appointSubmit")
	public @ResponseBody HashMap<String,Object> fundAppointSubmit(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response,ReqMsg_Fund_CheckIn regMsg) {
		HashMap<String,Object> dataMap = new HashMap<String,Object>();
		try{
			
			ResMsg_Fund_CheckIn resMsg = (ResMsg_Fund_CheckIn) fundService.handleMsg(regMsg);
			if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())){
				successRes(dataMap, resMsg);
			}else{
				errorRes(dataMap, resMsg);
			}
		} catch (Exception e) {
			errorRes(dataMap, e);
			log.error("=========================基金预约失败==========================");
			e.printStackTrace();
		}
		
		
		return dataMap;
	}
	
	@RequestMapping("{channel}/fund/value")
	public String fundValue(@PathVariable String channel, HttpServletRequest request,HttpServletResponse response,ReqMsg_Fund_ListQuery reqMsg,Map<String,Object> model){

		reqMsg.setPageIndex(0);
		reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
		ResMsg_Fund_ListQuery resMsg = (ResMsg_Fund_ListQuery) fundService.handleMsg(reqMsg);
		model.put("totalCount", resMsg.getTotal());
		model.put("pageIndex", 0);
		model.put("pageSize",Constants.EXCHANGE_PAGE_SIZE);
		model.put("fundValue", resMsg.getValueList());
		return channel + "/fund/fund_value";
	}
	
	@RequestMapping("{channel}/fund/valueContent")
	public String fundValueContent(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response,ReqMsg_Fund_ListQuery reqMsg,Map<String,Object> model) {
		try {
			reqMsg.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
			ResMsg_Fund_ListQuery resMsg = (ResMsg_Fund_ListQuery) fundService.handleMsg(reqMsg);
			
			model.put("fundValue", resMsg.getValueList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return channel + "/fund/fund_valueContent";
	}
	
	@RequestMapping("{channel}/fund/value/detail")
	public String fundValueDetail(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response,ReqMsg_Fund_NetValueListQuery reqMsg,Map<String,Object> model) {
		
		ResMsg_Fund_NetValueListQuery resMsg = (ResMsg_Fund_NetValueListQuery) fundService.handleMsg(reqMsg);
		
		List<HashMap<String, Object>> dataList = resMsg.getValue();
		
		//确定每个数值的百分比
		//获得最大的数字
		double max = 0;
		if(dataList!= null && dataList.size()>0){
			for (HashMap<String, Object> map : dataList) {
				Double tmp = (Double)map.get("net");
				max = max > tmp? max : tmp;
			}
			//确定比率
			for (HashMap<String, Object> map : dataList) {
				Double tmp = (Double)map.get("net");
				Double pecent = tmp*100/max;
				if(pecent < 10){
					pecent = 10.0;
				}
				
				map.put("percent", pecent);
			}
		}
		
		model.put("result", dataList);
		
		return channel + "/fund/fund_value_detail";
	}
}
