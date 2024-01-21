/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.dao.BsRedPacketCheckMapper;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_AutoRedPocketDisable;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_AutoRedPocketReview;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_FindRedPacketInfoGrid;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_GrantManagermentGrid;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_ManualRedPocketReview;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_RedPacketCheckGrid;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_RedPacketCheckRefuse;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_RedPacketCheckPass;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_RedPacketStatistics;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_AutoRedPocketDisable;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_AutoRedPocketReview;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_FindRedPacketInfoGrid;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_GrantManagermentGrid;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_ManualRedPocketReview;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_RedPacketCheckGrid;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_RedPacketCheckPass;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_RedPacketCheckRefuse;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_RedPacketStatistics;
import com.pinting.business.model.BsTermProductCode;
import com.pinting.business.model.BsTermProductCodeExample;
import com.pinting.business.service.manage.AgentService;
import com.pinting.business.service.manage.BsRedPacketService;
import com.pinting.business.service.manage.MTermProductCodeService;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.ReturnDWZAjax;
import com.pinting.util.Util;

/**
 * 
 * @author HuanXiong
 * @version $Id: RedPacketController.java, v 0.1 2016-3-1 上午10:20:41 HuanXiong Exp $
 */
@Controller
@RequestMapping("/redPacket")
public class RedPacketController {

    @Autowired
    @Qualifier("dispatchService")
    private HessianService dispatchService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private BsRedPacketService bsRedPacketService;
    @Autowired
	private MTermProductCodeService termProductCodeService;
    @Autowired
    private BsRedPacketCheckMapper bsRedPacketCheckMapper;
    
    /**
     * 红包查询
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/redPacketInfoIndex")
    public String redPacketInfoIndex(ReqMsg_RedPacket_FindRedPacketInfoGrid req, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) { 
        String distributeType = req.getDistributeType();
        String triggerType = req.getTriggerType();
        if("MANUAL".equals(req.getDistributeType())){
            req.setDistributeType("MANUAL");
            req.setTriggerType("");
        } else if("AUTO_REGISTER".equals(req.getDistributeType())) {
            req.setDistributeType("AUTO");
            req.setTriggerType("NEW_USER");
        } else if("AUTO_RECOMMEND".equals(req.getDistributeType())) {
            req.setDistributeType("AUTO");
            req.setTriggerType("INVITE_FULL");
        } else if("AUTO_FULL".equals(req.getDistributeType())) {
            req.setDistributeType("AUTO");
            req.setTriggerType("BUY_FULL");
        } else if("AUTO_318SHAKE".equals(req.getDistributeType())) {
            req.setDistributeType("AUTO");
            req.setTriggerType("318_GAME_OLD_USER");
        } else if("AUTO_EXCHANGE_4MALL".equals(req.getDistributeType())) {
            req.setDistributeType("AUTO");
            req.setTriggerType("EXCHANGE_4MALL");
        }else {
            req.setDistributeType("");
            req.setTriggerType("");
        }
        if (StringUtil.isNotEmpty(req.getQueryFlag()) && "QUERY".equals(req.getQueryFlag())) { // 红包查询默认不查询
        	ResMsg_RedPacket_FindRedPacketInfoGrid res = (ResMsg_RedPacket_FindRedPacketInfoGrid) dispatchService.handleMsg(req);
            model.put("count", res.getCount());
            model.put("dataGrid", res.getDataGrid());
            req.setDistributeType(distributeType);
            req.setTriggerType(triggerType);
            model.put("req", req);
        }
        int agentTotal = agentService.findAgentsTotalRows();
        model.put("agentTotal", agentTotal);
        return "redPacket/red_packet_info_index";
    }

	private String changeTermLimit(String termLimit) {
		StringBuffer buffer = new StringBuffer();
		if(StringUtil.isNotBlank(termLimit)) {
			String[] limits = termLimit.split(",");
			for(String limit : limits) {
				if("1".equals(limit)) {
					buffer.append("30,");
				} else if("3".equals(limit)) {
					buffer.append("90,");
				} else if("6".equals(limit)) {
					buffer.append("180,");
				} else if("12".equals(limit)) {
					buffer.append("365,");
				}
			}
			buffer.delete(buffer.length() - 1, buffer.length());
		}
		return buffer.toString();
	}


	@RequestMapping("/redPacketGrantManagementIndex")
    public String redPacketGrantManagementIndex(ReqMsg_RedPacket_GrantManagermentGrid req, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) { 
    	ResMsg_RedPacket_GrantManagermentGrid res = (ResMsg_RedPacket_GrantManagermentGrid) dispatchService.handleMsg(req);
    	ArrayList<HashMap<String, Object>> list = res.getDataGrid();
    	int checkCount = 0;
    	if(list != null) {
    		for (HashMap<String, Object> hashMap : list) {
				if("PASS".equals(hashMap.get("checkStatus").toString()) 
						&& "MANUAL".equals(hashMap.get("distributeType").toString())
						&& !"FINISHED".equals(hashMap.get("msgStatus")== null ? "" : hashMap.get("msgStatus").toString())) {
					checkCount++;
				}
				hashMap.put("termLimit", changeTermLimit((String)(hashMap.get("termLimit") == null ? hashMap.get("termlimit") : hashMap.get("termLimit"))));
			}
    	}
        model.put("count", res.getCount());
        model.put("dataGrid", list);
        model.put("budgetBalance", res.getBudgetBalance());
        model.put("req", req);
        model.put("checkCount", checkCount);
        return "redPacket/grantManagement/index";
    }
    
    
    @RequestMapping("/autoRedPocketDisable")
	public @ResponseBody Map<Object, Object> autoRedPocketDisable(ReqMsg_RedPacket_AutoRedPocketDisable req,String  pubilsh,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		ResMsg_RedPacket_AutoRedPocketDisable res = (ResMsg_RedPacket_AutoRedPocketDisable) dispatchService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			return ReturnDWZAjax.success("修改成功！");
		} else {
			return ReturnDWZAjax.fail("操作失败！");
		}
    }
    
    
    
	/**
	 * 红包发放管理--进入查看页面(自动发放红包)
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/autoRedPocketReview")
	public String autoRedPocketReview(ReqMsg_RedPacket_AutoRedPocketReview req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		if(req.getId() != null && req.getId() != 0) {
			ResMsg_RedPacket_AutoRedPocketReview res = (ResMsg_RedPacket_AutoRedPocketReview) dispatchService.handleMsg(req);
			res.getAutoPocketMap().put("termLimit", changeTermLimit((String)(res.getAutoPocketMap().get("termLimit") == null ? res.getAutoPocketMap().get("termlimit") : res.getAutoPocketMap().get("termLimit"))));
			model.put("autoRedPocket", res.getAutoPocketMap());
			model.put("notifyChannel", res.getNotifyList());
		}
		return "redPacket/grantManagement/auto_red_pocket_review";
	}
	
	
	
	/**
	 * 红包发放管理--进入查看页面(手动发放红包)
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/manualRedPocketReview")
	public String manualRedPocketReview(ReqMsg_RedPacket_ManualRedPocketReview req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		if(req.getId() != null && req.getId() != 0) {
			ResMsg_RedPacket_ManualRedPocketReview res = (ResMsg_RedPacket_ManualRedPocketReview) dispatchService.handleMsg(req);
			model.put("count", res.getCount());
	        model.put("dataGrid", res.getDataGrid());
			res.getManualPocketMap().put("termLimit", changeTermLimit((String)(res.getManualPocketMap().get("termLimit") == null ? res.getManualPocketMap().get("termlimit") : res.getManualPocketMap().get("termLimit"))));
			res.getManualPocketMap().put("termlimit", res.getManualPocketMap().get("termLimit"));
	        model.put("manualPocketMap",res.getManualPocketMap());
	        model.put("notifyChannel", res.getNotifyList());
	        model.put("queryHistoryList", res.getQueryHistoryList());
	        model.put("req", req);
		}
		return "redPacket/grantManagement/manual_red_pocket_review";
	}
	
	
	
    
    @RequestMapping("/redPacketCheckIndex")
    public String redPacketCheckIndex(ReqMsg_RedPacket_RedPacketCheckGrid req, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) { 
        request.getSession().setAttribute("token", UUID.randomUUID().toString());
        String serverToken = (String) request.getSession().getAttribute("token");
        
    	ResMsg_RedPacket_RedPacketCheckGrid res = (ResMsg_RedPacket_RedPacketCheckGrid) dispatchService.handleMsg(req);
        model.put("count", res.getCount());
        if(res.getCount() >0 && res.getDataGrid()!=null){
        	for(HashMap<String, Object> hashMap: res.getDataGrid()) {
    			hashMap.put("termLimit", changeTermLimit((String)(hashMap.get("termLimit") == null ? hashMap.get("termlimit") : hashMap.get("termLimit"))));
    		}
        }
		
		model.put("dataGrid", res.getDataGrid());
        model.put("budgetBalance", res.getBudgetBalance());
        model.put("serverToken", serverToken);
        model.put("req", req);
        
        //查询期限产品编码列表
  		BsTermProductCodeExample example = new BsTermProductCodeExample();
  		example.setOrderByClause("term asc");
  		List<BsTermProductCode> codes = termProductCodeService.findBsTermProductCodes(example);
  		model.put("codes", codes);
        
        return "redPacket/redPacketCheck/check_index";
    }
    
    
	/**
	 * 进入查看页面(自动发放红包)
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/autoRedPocketCheckReview")
	public String autoRedPocketCheckReview(ReqMsg_RedPacket_AutoRedPocketReview req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		if(req.getId() != null && req.getId() != 0) {
			ResMsg_RedPacket_AutoRedPocketReview res = (ResMsg_RedPacket_AutoRedPocketReview) dispatchService.handleMsg(req);

			res.getAutoPocketMap().put("termLimit", changeTermLimit((String)(res.getAutoPocketMap().get("termLimit") == null ? res.getAutoPocketMap().get("termlimit") : res.getAutoPocketMap().get("termLimit"))));
			model.put("autoRedPocket", res.getAutoPocketMap());
			model.put("notifyChannel", res.getNotifyList());
		}
		return "redPacket/redPacketCheck/auto_red_pocket_review";
	}
	
	
	
	/**
	 * 进入查看页面(手动发放红包)
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/manualRedPocketCheckReview")
	public String manualRedPocketCheckReview(ReqMsg_RedPacket_ManualRedPocketReview req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		if(req.getId() != null && req.getId() != 0) {
			ResMsg_RedPacket_ManualRedPocketReview res = (ResMsg_RedPacket_ManualRedPocketReview) dispatchService.handleMsg(req);
			model.put("count", res.getCount());
	        model.put("dataGrid", res.getDataGrid());
			res.getManualPocketMap().put("termLimit", changeTermLimit((String)(res.getManualPocketMap().get("termLimit") == null ? res.getManualPocketMap().get("termlimit") : res.getManualPocketMap().get("termLimit"))));
			res.getManualPocketMap().put("termlimit", res.getManualPocketMap().get("termLimit"));
			model.put("manualPocketMap",res.getManualPocketMap());
	        model.put("notifyChannel", res.getNotifyList());
	        model.put("queryHistoryList", res.getQueryHistoryList());
	        model.put("req", req);
		}
		return "redPacket/redPacketCheck/manual_red_pocket_review";
	}
	
	
	
	//红包发放审核通过操作
    @RequestMapping("/redPacketCheckPass")
	public @ResponseBody Map<Object, Object> redPacketCheckPass(ReqMsg_RedPacket_RedPacketCheckPass req,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        //防重复提交
    	if(Util.isRepeatSubmit(request)){
        	return ReturnDWZAjax.fail("请勿重复提交！");
        }
    	
    	CookieManager cookie = new CookieManager(request);
		String userId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		req.setMuserId(Integer.parseInt(userId));
    	ResMsg_RedPacket_RedPacketCheckPass res = (ResMsg_RedPacket_RedPacketCheckPass) dispatchService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			return ReturnDWZAjax.success("通过操作成功！");
		} else {
			return ReturnDWZAjax.fail("通过操作失败！");
		}
    }
    
	//红包发放审核拒绝操作
    @RequestMapping("/redPacketCheckRefuse")
	public @ResponseBody Map<Object, Object> redPacketCheckRefuse(ReqMsg_RedPacket_RedPacketCheckRefuse req,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		CookieManager cookie = new CookieManager(request);
		String userId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		req.setMuserId(Integer.parseInt(userId));
    	ResMsg_RedPacket_RedPacketCheckRefuse res = (ResMsg_RedPacket_RedPacketCheckRefuse) dispatchService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			return ReturnDWZAjax.success("拒绝操作成功！");
		} else {
			return ReturnDWZAjax.fail("拒绝操作失败！");
		}
    }
    
    
    @RequestMapping("/redPacketStatisticsIndex")
    public String redPacketStatisticsIndex(ReqMsg_RedPacket_RedPacketStatistics req, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) { 
    	ResMsg_RedPacket_RedPacketStatistics res = (ResMsg_RedPacket_RedPacketStatistics) dispatchService.handleMsg(req);
        model.put("dataGrid", res.getDataGrid());
    	model.put("count", res.getCount());
        model.put("budgetTotal", res.getBudgetTotal());
        model.put("budgetAmount", res.getBudgetAmount());
        model.put("rpNameList", res.getRpNameGrid());
        model.put("applicantList", res.getApplicantGrid());
        model.put("req", req);
        return "redPacket/statistics/index";
    }
    
    //手动红包发放
    @RequestMapping("/manualRedPacketSend")
    @ResponseBody
	public void manualRedPacketSend(HttpServletRequest request, HttpServletResponse response) {
		final String ids = request.getParameter("ids");
		//线程控制发放手动红包
		new Thread(new Runnable() {
			@Override
			public void run() {
				bsRedPacketService.manualRedPacketSend(ids);
			}
		}).start();
    }
    
    
    
    //根据批次号查询年化交易额
    @RequestMapping("/queryAmountYear")
    @ResponseBody
	public Map<String, Object> queryAmountYear(HttpServletRequest request, HttpServletResponse response) {
    	Map<String, Object> model = new HashMap<>();
    	String serialNo = request.getParameter("serialNo");
		Double buyYearAmount =  bsRedPacketCheckMapper.queryAmountYear(serialNo);
		Double buyAmount =  bsRedPacketCheckMapper.queryBuyAmount(serialNo);
		NumberTool tool = new NumberTool();
		model.put("buyYearAmount", buyYearAmount==null?0.00:tool.format("0.00", buyYearAmount));
		model.put("buyAmount", buyAmount==null?0.00:tool.format("0.00", buyAmount));
		model.put("resCode", 200);
		model.put("message", "success");
		return model;
    }
}
