package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_AutoPacket_AddAutoPacket;
import com.pinting.business.hessian.manage.message.ReqMsg_AutoPacket_GetApply;
import com.pinting.business.hessian.manage.message.ReqMsg_AutoPacket_GetPolicyType;
import com.pinting.business.hessian.manage.message.ReqMsg_AutoPacket_Init;
import com.pinting.business.hessian.manage.message.ResMsg_AutoPacket_AddAutoPacket;
import com.pinting.business.hessian.manage.message.ResMsg_AutoPacket_GetApply;
import com.pinting.business.hessian.manage.message.ResMsg_AutoPacket_GetPolicyType;
import com.pinting.business.hessian.manage.message.ResMsg_AutoPacket_Init;
import com.pinting.business.model.BsAgent;
import com.pinting.business.model.BsAutoRedPacketRule;
import com.pinting.business.model.BsRedPacketApply;
import com.pinting.business.model.BsRedPacketCheck;
import com.pinting.business.model.vo.BsRedPacketApplyVO;
import com.pinting.business.service.manage.AgentService;
import com.pinting.business.service.manage.BsRedPacketApplyService;
import com.pinting.business.service.manage.BsRedPacketCheckService;
import com.pinting.business.service.manage.BsRedPacketService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.MoneyUtil;

/**
 * 自动红包发放相关
 * @author bianyatian
 * @2016-3-1 下午1:51:27
 */
@Component("AutoPacket")
public class AutoPacketFacade {
	
	@Autowired
	private AgentService agentService;
	@Autowired
	private BsRedPacketApplyService bsRedPacketApplyService;
	@Autowired
	private BsRedPacketService bsRedPacketService;
	@Autowired
	private BsRedPacketCheckService bsRedPacketCheckService;
	
	/**
	 * 查询所有渠道列表和可选择的已通过申请的红包申请信息
	 * @param req
	 * @param res
	 */
	public void init(ReqMsg_AutoPacket_Init req,ResMsg_AutoPacket_Init res){
		List<BsAgent> agentList = agentService.findAllAgentList();
		List<BsRedPacketApply> packetList = bsRedPacketApplyService.findCanPutPacket();
		res.setAgentList((ArrayList<BsAgent>) agentList);
		res.setPacketList((ArrayList<BsRedPacketApply>) packetList);
	}
	
	/**
	 * 根据预算来源的applyNo查询信息
	 * @param req
	 * @param res
	 */
	public void getApply(ReqMsg_AutoPacket_GetApply req,ResMsg_AutoPacket_GetApply res){
		BsRedPacketApplyVO apply = bsRedPacketApplyService.selectByApplyNo(req.getApplyNo());
		res.setApplyNo(apply.getApplyNo());
		res.setBudget(apply.getBudget());
		res.setCanPutAmount(apply.getCanPutAmount());
		res.setRpName(apply.getRpName());
	}
	
	public void getPolicyType(ReqMsg_AutoPacket_GetPolicyType req, ResMsg_AutoPacket_GetPolicyType res){
		
		List<BsRedPacketCheck> list = bsRedPacketCheckService.getListByPolicyType(req.getPolicyType());
		res.setCheckList((ArrayList<BsRedPacketCheck>) list);
	}
	
	/**
	 * 新增自动红包
	 * @param req
	 * @param res
	 * 1.检查红包名称，选择预算来源，选择红包类型，红包使用条件等是否为空
	 * 2.获取预算金额并审核
	 */
	public void addAutoPacket(ReqMsg_AutoPacket_AddAutoPacket req,ResMsg_AutoPacket_AddAutoPacket res){
		
		//1.检查红包名称，选择预算来源，选择红包类型，红包使用条件等是否为空
		if(StringUtils.isBlank(req.getSerialName())){
			res.setErrorMsg("红包名称不能为空！");
			return;
		}
		if(StringUtils.isBlank(req.getPolicyType())){
			res.setErrorMsg("红包策略不能为空！");
			return;
		}
		if(StringUtils.isBlank(req.getApplyNo())){
			res.setErrorMsg("请选择预算来源！");
			return;
		}
		if(StringUtils.isBlank(req.getUseCondition())){
			res.setErrorMsg("请选择红包类型！");
			return;
		}
		if(req.getFull() == null){
			res.setErrorMsg("红包使用条件不能为空！");
			return;
		}
		if(req.getFull() >= 100000000){
			res.setErrorMsg("红包使用条件金额不能大于等于1亿！");
			return;
		}
		if(req.getFull() <= 0 ){
			res.setErrorMsg("红包使用条件金额必须大于0！");
			return;
		}
		if(req.getFull() > 100000){
			Double t = req.getFull()%10000;
			if(t>0){
				res.setErrorMsg("红包使用条件金额大于10万时不能存在万位以下！");
				return;
			}
		}
		if(req.getSubtract() == null){
			res.setErrorMsg("红包金额不能为空！");
			return;
		}
		if(req.getSubtract() <= 0 ){
			res.setErrorMsg("红包金额必须大于0！");
			return;
		}
		if(MoneyUtil.subtract(req.getFull(), req.getSubtract()).doubleValue() < 0 ){
			res.setErrorMsg("红包金额不能大于使用条件金额！");
			return;
		}
		if(req.getTotal() == null){
			res.setErrorMsg("发放数量不能为空！");
			return;
		}
		if(req.getTotal() > 100000000){
			res.setErrorMsg("发放数量不能大于1亿！");
			return;
		}
		if(req.getTotal() <= 0 ){
			res.setErrorMsg("发放数量必须大于0！");
			return;
		}
		
		if(req.getTriggerType() == null){
			res.setErrorMsg("请选择触发条件！");
			return;
		}
		
		if(req.getDistributeTimeEnd() == null || req.getDistributeTimeStart() == null){
			res.setErrorMsg("触发时间不能为空！");
			return;
		}
		Date now = new Date();
		if(now.compareTo(req.getDistributeTimeStart()) > 0){
			res.setErrorMsg("触发开始时间不能早于当前时间！");
			return;
		}
		if(req.getDistributeTimeStart().compareTo(req.getDistributeTimeEnd()) > 0){
			res.setErrorMsg("触发时间填写不正确！");
			return;
		}
		
		if(req.getTriggerType() != null && 
				Constants.AUTO_RED_PACKET_TIGGER_TYPE_BUY_FULL.equals(req.getTriggerType())){
		    //触发条件--满额
		    req.setTriggerInviteNum(null);
			if(req.getTriggerAmountStart() == null || req.getTriggerAmountEnd() == null){
				res.setErrorMsg("触发额度不能为空！");
				return;
			}
			if(req.getTriggerAmountStart() < 0  || req.getTriggerAmountEnd() < 0){
				res.setErrorMsg("触发额度不能小于0！");
				return;
			}
			if(req.getTriggerAmountStart() > req.getTriggerAmountEnd() ){
				res.setErrorMsg("触发额度填写不正确！");
				return;
			}
		}
		if(req.getTriggerType() != null && 
				Constants.AUTO_RED_PACKET_TIGGER_TYPE_INVITE_FULL.equals(req.getTriggerType())){
		    
		    //触发条件--邀请满额
            req.setTriggerAmountStart(null);
            req.setTriggerAmountEnd(null);
			if(req.getTriggerInviteNum() == null || req.getTriggerInviteNum() == 0){
				res.setErrorMsg("邀请人数不能为空！");
				return;
			}
		}
		
		if(req.getValidTermType() == null){
			res.setErrorMsg("请选有效期类型！");
			return;
		}
		if(req.getValidTermType() != null && 
				Constants.AUTO_RED_PACKET_VALID_TERM_TYPE_FIXED.equals(req.getValidTermType())){
		    //有效期类型--固定时间
		    req.setAvailableDays(null);
		    
			if(req.getUseTimeStart() == null || req.getUseTimeEnd() == null){
				res.setErrorMsg("有效期时间不能为空！");
				return;
			}
			if(req.getUseTimeStart().compareTo(req.getUseTimeEnd()) > 0){
				res.setErrorMsg("有效期时间填写不正确！");
				return;
			}
			if(req.getUseTimeEnd().compareTo(req.getDistributeTimeEnd()) <0 ){
				res.setErrorMsg("有效期结束时间不能早于触发结束时间！");
				return;
			}
		}
		if(req.getValidTermType() != null 
				&& Constants.AUTO_RED_PACKET_VALID_TERM_TYPE_AFTER_RECEIVE.equals(req.getValidTermType())){
		    //有效期类型--发放后生效
            req.setUseTimeStart(null);
            req.setUseTimeEnd(null);
			if(req.getAvailableDays() == null){
				res.setErrorMsg("有效期时间填写不正确！");
				return;
			}
		}
		
		if(StringUtils.isBlank(req.getAgentIds())){
			res.setErrorMsg("请选择发放渠道！");
			return;
		}
		
		if(StringUtils.isBlank(req.getTermLimit())){
			res.setErrorMsg("请选择限用标的！");
			return;
		}
		
		//2.获取预算金额并审核;预算剩余金额-红包金额*红包个数
		BsRedPacketApplyVO apply = bsRedPacketApplyService.selectByApplyNo(req.getApplyNo());
		Double canPutAmount = apply.getCanPutAmount();
		Double amount = MoneyUtil.subtract(canPutAmount, MoneyUtil.multiply(req.getSubtract(),req.getTotal()).doubleValue()).doubleValue();
		if(amount < 0 ){
			res.setErrorMsg("发放总金额不能超过预算金额！");
			return;
		}
		
		try {
			String serialNo = Util.getSerialNo();
			BsRedPacketCheck check = new BsRedPacketCheck();
			check.setAmount(req.getSubtract());
			check.setApplyNo(req.getApplyNo());
			check.setFull(req.getFull());
			String notifyChannel = req.getNotifyChannel();
			if(StringUtils.isNotBlank(notifyChannel)){
				notifyChannel = notifyChannel.substring(0,notifyChannel.length()-1);
				check.setNotifyChannel(notifyChannel);//通知管道
			}
			check.setSerialName(req.getSerialName().trim());
			check.setPolicyType(req.getPolicyType().trim());
			check.setSubtract(req.getSubtract());
			check.setTotal(req.getTotal());
			check.setUseCondition(req.getUseCondition());//使用条件类型
			check.setUseTimeStart(req.getUseTimeStart());
			check.setUseTimeEnd(req.getUseTimeEnd());
			check.setSerialNo(serialNo);
			check.setApplicant(req.getApplicant());
			if(StringUtils.isNotBlank(req.getTermLimit())){
				check.setTermLimit(req.getTermLimit().substring(0,req.getTermLimit().length()-1));
			}
			check.setNote(req.getNote().trim());
			
			
			BsAutoRedPacketRule rule = new BsAutoRedPacketRule();
			String agentIds = req.getAgentIds();
			//存在-1
			if(agentIds.indexOf("-1") >=0 ){
				agentIds = "-1";
			}else if(agentIds.endsWith(",")){
				agentIds = agentIds.substring(0,agentIds.length()-1);
			}
			rule.setAgentIds(agentIds);
			rule.setAvailableDays(req.getAvailableDays());
			rule.setDistributeTimeEnd(req.getDistributeTimeEnd());
			rule.setDistributeTimeStart(req.getDistributeTimeStart());
			rule.setTriggerAmountEnd(req.getTriggerAmountEnd());
			rule.setTriggerAmountStart(req.getTriggerAmountStart());
			rule.setTriggerInviteNum(req.getTriggerInviteNum());
			rule.setTriggerType(req.getTriggerType());
			rule.setValidTermType(req.getValidTermType());
			rule.setSerialNo(serialNo);
			
			
			bsRedPacketService.addAutoPacketCheck(check);
			bsRedPacketService.addAutoPacketRule(rule);
		} catch (Exception e) {
			res.setErrorMsg("添加失败！");
			e.printStackTrace();
			return;
		}
		
		
	}

}
