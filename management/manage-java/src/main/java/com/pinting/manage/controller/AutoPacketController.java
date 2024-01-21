package com.pinting.manage.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_AutoPacket_AddAutoPacket;
import com.pinting.business.hessian.manage.message.ReqMsg_AutoPacket_GetApply;
import com.pinting.business.hessian.manage.message.ReqMsg_AutoPacket_GetPolicyType;
import com.pinting.business.hessian.manage.message.ReqMsg_AutoPacket_Init;
import com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_ChannelWithdraw;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_GetNewAutoRedPacket;
import com.pinting.business.hessian.manage.message.ResMsg_AutoPacket_AddAutoPacket;
import com.pinting.business.hessian.manage.message.ResMsg_AutoPacket_GetApply;
import com.pinting.business.hessian.manage.message.ResMsg_AutoPacket_GetPolicyType;
import com.pinting.business.hessian.manage.message.ResMsg_AutoPacket_Init;
import com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_ChannelWithdraw;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_GetNewAutoRedPacket;
import com.pinting.business.model.BsAutoRedPacketRule;
import com.pinting.business.model.BsRedPacketCheck;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.DateUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;

/**
 * 自动红包发放
 * @author bianyatian
 * @2016-3-1 上午11:13:05
 */
@Controller
public class AutoPacketController {

	
	@Autowired
	@Qualifier("dispatchService")
	private HessianService hessianService;
	
	/**
	 * 手动红包新增初始化
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/autoPacket/index")
	public String index(ReqMsg_AutoPacket_Init reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		ResMsg_AutoPacket_Init resp = (ResMsg_AutoPacket_Init) hessianService
					.handleMsg(reqMsg);
		ReqMsg_AutoPacket_GetPolicyType policyReq = new ReqMsg_AutoPacket_GetPolicyType();
		
		ResMsg_AutoPacket_GetPolicyType policyRes = (ResMsg_AutoPacket_GetPolicyType)hessianService.handleMsg(policyReq);
		if(policyRes.getCheckList().size()>0){
			model.put("policyType", policyRes.getCheckList().get(0).getPolicyType());
		}
		model.put("time", DateUtil.formatYYYYMMDD(new Date()));
		model.put("packetList", resp.getPacketList());
		ReqMsg_RedPacket_GetNewAutoRedPacket req = new ReqMsg_RedPacket_GetNewAutoRedPacket();
		ResMsg_RedPacket_GetNewAutoRedPacket res = (ResMsg_RedPacket_GetNewAutoRedPacket)hessianService.handleMsg(req);
		
		model.put("autoPocket", res.getAutoPocketMap());
		model.put("allAgents", resp.getAgentList());
		model.put("agentList", res.getAgentList());
		model.put("notifyList", res.getNotifyList());
		model.put("termList", res.getTermList());
		
		
		return "autoPacket/index2";
	}
	
	@RequestMapping("/autoPacket/agent")
	public String getAgent(ReqMsg_AutoPacket_Init reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		ResMsg_AutoPacket_Init resp = (ResMsg_AutoPacket_Init) hessianService
					.handleMsg(reqMsg);
		model.put("agentList", resp.getAgentList());
		return "autoPacket/agentList";
	}
	
	/**
	 * 根据预算来源的applyNo查询信息
	 * @param request
	 * @param req
	 * @return
	 */
	@ResponseBody
    @RequestMapping("/autoPacket/getByApplyNo")
    public Map<String, Object> channelWithdraw(HttpServletRequest request, ReqMsg_AutoPacket_GetApply req){
        Map<String, Object> map = new HashMap<String, Object>();
        ResMsg_AutoPacket_GetApply res = (ResMsg_AutoPacket_GetApply)hessianService.handleMsg(req);
        map.put("rpName", res.getRpName());
        map.put("applyNo", res.getApplyNo());
        map.put("canPutAmount", res.getCanPutAmount());
        return map;
    }
    
	@ResponseBody
	@RequestMapping("/autoPacket/getCheckByPolicy")
	public Map<String, Object> getCheckByPolicy(HttpServletRequest request,String policyType){
		Map<String, Object> map = new HashMap<String, Object>();
		ReqMsg_AutoPacket_GetPolicyType policyReq = new ReqMsg_AutoPacket_GetPolicyType();
		policyReq.setPolicyType(policyType);
		ResMsg_AutoPacket_GetPolicyType policyRes = (ResMsg_AutoPacket_GetPolicyType)hessianService.handleMsg(policyReq);
		map.put("checkPolicy", policyRes.getCheckList());
		return map;
	}
	
	@RequestMapping("/autoPacket/save")
	@ResponseBody
	public  Map<Object,Object> save(ReqMsg_AutoPacket_AddAutoPacket req,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model,String distributeTimeStart,
			String distributeTimeEnd,String useTimeStart,String useTimeEnd) {
		try {
			CookieManager cookie = new CookieManager(request);
			String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
					CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
			if(StringUtils.isBlank(mUserId)){
				return ReturnDWZAjax.fail("未获取到登录者信息");
			}else{
				req.setApplicant(Integer.parseInt(mUserId));
			}
			req.setUseTimeStart(DateUtil.parseDateTime(useTimeStart));
			req.setUseTimeEnd(DateUtil.parseDateTime(useTimeEnd));
			req.setDistributeTimeEnd(DateUtil.parseDateTime(distributeTimeEnd));
			req.setDistributeTimeStart(DateUtil.parseDateTime(distributeTimeStart));
			ResMsg_AutoPacket_AddAutoPacket res = (ResMsg_AutoPacket_AddAutoPacket)hessianService.handleMsg(req);
			if(StringUtils.isNotBlank(res.getErrorMsg())){
				return ReturnDWZAjax.fail(res.getErrorMsg());
			}else{
				return ReturnDWZAjax.success("添加成功！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return ReturnDWZAjax.fail("添加失败！");
		}
	}
	
}
