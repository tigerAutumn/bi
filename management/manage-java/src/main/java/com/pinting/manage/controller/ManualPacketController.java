package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_AutoPacket_GetPolicyType;
import com.pinting.business.hessian.manage.message.ReqMsg_AutoPacket_Init;
import com.pinting.business.hessian.manage.message.ReqMsg_MUserOperate_AgentNameQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MUserOperate_AgentQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MUserOperate_UserOperateQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_ManualPacket_ManualPacketAdd;
import com.pinting.business.hessian.manage.message.ResMsg_AutoPacket_GetPolicyType;
import com.pinting.business.hessian.manage.message.ResMsg_AutoPacket_Init;
import com.pinting.business.hessian.manage.message.ResMsg_MUserOperate_AgentNameQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MUserOperate_AgentQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MUserOperate_UserOperateQuery;
import com.pinting.business.hessian.manage.message.ResMsg_ManualPacket_ManualPacketAdd;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;

/**
 * 
 * @author yanwl
 * @version $Id: ManualPacketController.java, v 0.1 2016-3-2 下午16:20:41 yanwl Exp $
 */
@Controller
@RequestMapping("/manualPacket")
public class ManualPacketController {

    @Autowired
    @Qualifier("dispatchService")
    private HessianService manageService;
    @Autowired
    private BsUserService bsUserService;
    
	/**
	 * 进入手动发放红包页面
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(ReqMsg_MUserOperate_AgentQuery req,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		
		ResMsg_MUserOperate_AgentQuery resAgent = (ResMsg_MUserOperate_AgentQuery)manageService.handleMsg(req);
		ReqMsg_MUserOperate_UserOperateQuery reqUserOperate = new ReqMsg_MUserOperate_UserOperateQuery();
		reqUserOperate.setNonAgentId("0");
		ArrayList<HashMap<String, Object>> list = resAgent.getAgentList();
		String agentIds = "";
		for (HashMap<String, Object> hashMap : list) {
			if(hashMap.get("id") != null) {
				agentIds += hashMap.get("id") + ",";
			}
		}
		reqUserOperate.setAgentIds(agentIds);
		model.put("req", reqUserOperate);
		model.put("totalRows", 0);
		model.put("agentTotal", list.size());
		return "manualPacket/manual_red_pocket_index";
	}
	
	/**
	 * 用户信息查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/userSearch")
	public String userSearch(ReqMsg_MUserOperate_UserOperateQuery req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {
		if(req.getPageNum() <= 0 || req.getNumPerPage() <= 0) {
			req.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
			req.setNumPerPage(Constants.MANAGE_DEFAULT_NUMPERPAGE);
		}
		//ResMsg_MUserOperate_UserOperateQuery res = (ResMsg_MUserOperate_UserOperateQuery)manageService.handleMsg(req);
		ResMsg_MUserOperate_UserOperateQuery res = bsUserService.findUserOperatePageManualPacket(req);
		ResMsg_MUserOperate_AgentQuery resAgent = (ResMsg_MUserOperate_AgentQuery)manageService.handleMsg(new ReqMsg_MUserOperate_AgentQuery());
		ArrayList<HashMap<String, Object>> list = resAgent.getAgentList();
		
		model.put("req", req);
		model.put("totalRows", res.getTotalRows());
		model.put("operateList", res.getUserOperateList());
		model.put("agentTotal", list.size());
		return  "manualPacket/manual_red_pocket_index";
	}
	
	/**
	 * 所有用户渠道
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/all_agent")
	public String allAgent(ReqMsg_MUserOperate_AgentQuery req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {
		
		ResMsg_MUserOperate_AgentQuery res = (ResMsg_MUserOperate_AgentQuery)manageService.handleMsg(req);
		model.put("agents", res.getAgentList());
		return  "manualPacket/agent_index";
	}
	
	/**
	 * 红包发放页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/redPacketSend")
	public String redPacketSend(ReqMsg_MUserOperate_UserOperateQuery req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {
		
		ResMsg_MUserOperate_UserOperateQuery res = bsUserService.countUserOperatePageManualPacket(req);
		ResMsg_AutoPacket_Init resp = (ResMsg_AutoPacket_Init) manageService.handleMsg(new ReqMsg_AutoPacket_Init());
		ReqMsg_AutoPacket_GetPolicyType policyReq = new ReqMsg_AutoPacket_GetPolicyType();
		
		ResMsg_AutoPacket_GetPolicyType policyRes = (ResMsg_AutoPacket_GetPolicyType)manageService.handleMsg(policyReq);
		if(policyRes.getCheckList().size()>0){
			model.put("policyType", policyRes.getCheckList().get(0).getPolicyType());
		}
		model.put("totalRows", res.getTotalRows());
		model.put("packetList", resp.getPacketList());
		return  "manualPacket/red_packet_send";
	}
	
	/**
	 * 根据userid红包发放页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/userIdRedPacketSend")
	public String userIdRedPacketSend(ReqMsg_MUserOperate_UserOperateQuery req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {
		
		//ResMsg_MUserOperate_UserOperateQuery res = (ResMsg_MUserOperate_UserOperateQuery)manageService.handleMsg(req);
		ResMsg_AutoPacket_Init resp = (ResMsg_AutoPacket_Init) manageService.handleMsg(new ReqMsg_AutoPacket_Init());
		ReqMsg_AutoPacket_GetPolicyType policyReq = new ReqMsg_AutoPacket_GetPolicyType();
		
		ResMsg_AutoPacket_GetPolicyType policyRes = (ResMsg_AutoPacket_GetPolicyType)manageService.handleMsg(policyReq);
		if(policyRes.getCheckList().size()>0){
			model.put("policyType", policyRes.getCheckList().get(0).getPolicyType());
		}
		model.put("totalRows", request.getParameter("userCount"));
//		model.put("totalRows", res.getTotalRows());
		model.put("packetList", resp.getPacketList());
		return  "manualPacket/user_id_red_packet_send";
	}
	
	/**
	 * 检测当前选中人数是否和查询人数一致
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkUserCount")
	@ResponseBody
	public boolean checkUserCount(ReqMsg_MUserOperate_UserOperateQuery req, HttpServletRequest request,
			HttpServletResponse response) {
		ResMsg_MUserOperate_UserOperateQuery res = (ResMsg_MUserOperate_UserOperateQuery)manageService.handleMsg(req);
		int userCount = Integer.valueOf(request.getParameter("userCount"));
		int totalRows = res.getTotalRows();
		if(userCount != totalRows) {
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * 检测输入的人数是否和查询人数一致
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/checkUserIdCount")
	@ResponseBody
	public Map<String, Object> checkUserIdCount(ReqMsg_MUserOperate_UserOperateQuery req, HttpServletRequest request,
			HttpServletResponse response) {
		ResMsg_MUserOperate_UserOperateQuery res = (ResMsg_MUserOperate_UserOperateQuery)manageService.handleMsg(req);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("receiveNum", res.getReceiveNum());
		map.put("realityNum", res.getTotalRows());
		return map;
	}
	
	/**
	 * 进入userId手动发放红包页面
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("userId/index")
	public String userIdIndex(ReqMsg_MUserOperate_UserOperateQuery req,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		model.put("req", req);
		model.put("totalRows", 0);
		return "manualPacket/user_id_red_pocket_index";
	}
	
	/**
	 * userid用户信息查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("userId/userSearch")
	public String userIdSearch(ReqMsg_MUserOperate_UserOperateQuery req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {
		if(req.getPageNum() <= 0 || req.getNumPerPage() <= 0) {
			req.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
			req.setNumPerPage(Constants.MANAGE_DEFAULT_NUMPERPAGE);
		}
		ResMsg_MUserOperate_UserOperateQuery res = (ResMsg_MUserOperate_UserOperateQuery)manageService.handleMsg(req);
		
		model.put("req", req);
		model.put("totalRows", res.getTotalRows());
		model.put("receiveNum", res.getReceiveNum());
		model.put("operateList", res.getUserOperateList());
		return  "manualPacket/user_id_red_pocket_index";
	}
	
	
	/**
	 * 手动红包发放
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sendRedPacket")
	@ResponseBody
	public void sendRedPacket(final ReqMsg_MUserOperate_UserOperateQuery reqUserOperate, final ReqMsg_ManualPacket_ManualPacketAdd reqManual ,HttpServletRequest request,
			HttpServletResponse response) {
		CookieManager cookie = new CookieManager(request);
		final String userId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		
		new Thread(new Runnable(){
			public void run(){
				reqUserOperate.setPageNum(1);
				reqUserOperate.setNumPerPage(Integer.MAX_VALUE);
				ResMsg_MUserOperate_UserOperateQuery res = bsUserService.findUserOperatePageManualPacket(reqUserOperate);
				List<HashMap<String, Object>>  userList = res.getUserOperateList();
				if(userList.size() > 0) {
					List<Integer> userIdList = new ArrayList<Integer>();
					StringBuffer userIds = new StringBuffer("");
					for (HashMap<String, Object> hashMap : userList) {
						userIdList.add(Integer.valueOf(hashMap.get("userId").toString()));
						userIds.append(hashMap.get("userId")+",");
					}
					reqManual.setUserIdList(userIdList);
					reqManual.setTotal(res.getTotalRows());
					reqManual.setApplicant(Integer.valueOf(userId));
					
					StringBuffer manualConditions = new StringBuffer("");
					if(StringUtil.isNotEmpty(reqUserOperate.getUserIds())) {
						manualConditions.append("用户ID："+userIds.toString().substring(0, userIds.toString().length()-1));
					}else {
						manualConditions.append("最后购买时间：");
						if(reqUserOperate.getsLastBuyTime() != null) {
							manualConditions.append(DateUtil.formatYYYYMMDD(reqUserOperate.getsLastBuyTime())+" 至  ");
						}else {
							manualConditions.append("- 至 ");
						}
						if(reqUserOperate.geteLastBuyTime() != null) {
							manualConditions.append(DateUtil.formatYYYYMMDD(reqUserOperate.geteLastBuyTime())+"；");
						}else {
							manualConditions.append("-；");
						}
						manualConditions.append("首次购买时间：");
						if(reqUserOperate.getsFirstBuyTime() != null) {
							manualConditions.append(DateUtil.formatYYYYMMDD(reqUserOperate.getsFirstBuyTime())+" 至  ");
						}else {
							manualConditions.append("- 至 ");
						}
						if(reqUserOperate.geteFirstBuyTime() != null) {
							manualConditions.append(DateUtil.formatYYYYMMDD(reqUserOperate.geteFirstBuyTime())+"；");
						}else {
							manualConditions.append("-；");
						}
						manualConditions.append("注册时间：");
						if(reqUserOperate.getsRegistTime() != null) {
							manualConditions.append(DateUtil.formatYYYYMMDD(reqUserOperate.getsRegistTime())+" 至  ");
						}else {
							manualConditions.append("- 至 ");
						}
						if(reqUserOperate.geteRegistTime() != null) {
							manualConditions.append(DateUtil.formatYYYYMMDD(reqUserOperate.geteRegistTime())+"；");
						}else {
							manualConditions.append("-；");
						}
						manualConditions.append("购买次数：");
						if(reqUserOperate.getsBuyTimes()!= null) {
							manualConditions.append(reqUserOperate.getsBuyTimes()+" 至  ");
						}else {
							manualConditions.append("- 至 ");
						}
						if(reqUserOperate.geteBuyTimes() != null) {
							manualConditions.append(reqUserOperate.geteBuyTimes()+"；");
						}else {
							manualConditions.append("-；");
						}
						manualConditions.append("在投金额：");
						if(StringUtil.isNotEmpty(reqUserOperate.getsInvestMoney())) {
							manualConditions.append(reqUserOperate.getsInvestMoney()+" 至  ");
						}else {
							manualConditions.append("- 至 ");
						}
						if(StringUtil.isNotEmpty(reqUserOperate.geteInvestMoney())) {
							manualConditions.append(reqUserOperate.geteInvestMoney()+"；");
						}else {
							manualConditions.append("-；");
						}
						manualConditions.append("总购买额：");
						if(StringUtil.isNotEmpty(reqUserOperate.getsInvestTotalMoney())) {
							manualConditions.append(reqUserOperate.getsInvestTotalMoney()+" 至  ");
						}else {
							manualConditions.append("- 至 ");
						}
						if(StringUtil.isNotEmpty(reqUserOperate.geteInvestTotalMoney())) {
							manualConditions.append(reqUserOperate.geteInvestTotalMoney()+"；");
						}else {
							manualConditions.append("-；");
						}
						manualConditions.append("渠道：");
						if(StringUtil.isNotEmpty(reqUserOperate.getNonAgentId())) {
							manualConditions.append("非渠道用户");
						}
						if(StringUtil.isNotEmpty(reqUserOperate.getAgentIds())) {
							ReqMsg_MUserOperate_AgentNameQuery reqAgent = new ReqMsg_MUserOperate_AgentNameQuery();
							String[] agentIds = reqUserOperate.getAgentIds().split(",");
							if(agentIds.length > 0) {
								List<Integer> ids = new ArrayList<Integer>();
								for (String str : agentIds) {
									if(StringUtil.isNotEmpty(str)) {
										ids.add(Integer.valueOf(str));
									}
								}
								reqAgent.setAgentIds(ids);
							}
							ResMsg_MUserOperate_AgentNameQuery resAgent = (ResMsg_MUserOperate_AgentNameQuery)manageService.handleMsg(reqAgent);
							if(StringUtil.isNotEmpty(reqUserOperate.getNonAgentId())) {
								manualConditions.append(","+resAgent.getAgentNames());
							}else {
								manualConditions.append(resAgent.getAgentNames());
							}
						}
					}
					
					reqManual.setManualConditions(manualConditions.toString());
					manageService.handleMsg(reqManual);
				}
			}
		}).start();
	}
}
