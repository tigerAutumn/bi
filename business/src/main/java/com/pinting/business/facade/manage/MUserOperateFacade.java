package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_MUserOperate_AgentNameQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MUserOperate_AgentQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MUserOperate_AppMessageQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MUserOperate_UserOperateQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MUserOperate_Index;
import com.pinting.business.hessian.manage.message.ResMsg_MUserOperate_AgentNameQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MUserOperate_AgentQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MUserOperate_AppMessageQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MUserOperate_UserOperateQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MUserOperate_Index;
import com.pinting.business.model.BsAgent;
import com.pinting.business.model.BsAppMessage;
import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.vo.MUserOperateVO;
import com.pinting.business.service.manage.AgentService;
import com.pinting.business.service.manage.BsBankCardService;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.service.manage.MAppNoticeService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.StringUtil;

/**
 * 
 * @Project: business
 * @Title: MUserOperateFacade.java
 * @author yanwl
 * @date 2016-02-26 下午13:34:07
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("MUserOperate")
public class MUserOperateFacade{
	private Logger log = LoggerFactory.getLogger(MUserOperateFacade.class);
	
	@Autowired
	private BsBankCardService bankCardService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private BsUserService bsUserService;
	@Autowired
	private MAppNoticeService mAppNoticeService;
	
	public void index(ReqMsg_MUserOperate_Index reqMsg,ResMsg_MUserOperate_Index resMsg) {
		List<BsBankCard> list = bankCardService.findBankCards();
		resMsg.setBankList(BeanUtils.classToArrayList(list));
	}
	
	public void agentQuery(ReqMsg_MUserOperate_AgentQuery reqMsg,ResMsg_MUserOperate_AgentQuery resMsg) {
		List<BsAgent> list = agentService.findAllAgentList();
		resMsg.setAgentList(BeanUtils.classToArrayList(list));
	}
	
	public void userOperateQuery(ReqMsg_MUserOperate_UserOperateQuery reqMsg,ResMsg_MUserOperate_UserOperateQuery resMsg) {
		Integer pageNum = reqMsg.getPageNum();
		Integer numPerPage = reqMsg.getNumPerPage();
		
		MUserOperateVO recode = new MUserOperateVO();
		recode.setPageNum(pageNum);
		recode.setNumPerPage(numPerPage);
		if(reqMsg.getBankId() != null && reqMsg.getBankId() != 0) {
			recode.setBankId(reqMsg.getBankId());
		}
		if(reqMsg.getsLastBuyTime() != null) {
			recode.setsLastBuyTime(reqMsg.getsLastBuyTime());
		}
		if(reqMsg.geteLastBuyTime() != null) {
			recode.seteLastBuyTime(reqMsg.geteLastBuyTime());
		}
		if(reqMsg.getsFirstBuyTime() != null) {
			recode.setsFirstBuyTime(reqMsg.getsFirstBuyTime());
		}
		if(reqMsg.geteFirstBuyTime() != null) {
			recode.seteFirstBuyTime(reqMsg.geteFirstBuyTime());
		}
		if(reqMsg.getsBuyTimes() != null ) {
			recode.setsBuyTimes(String.valueOf(reqMsg.getsBuyTimes()));
		}
		if(reqMsg.geteBuyTimes() != null ) {
			recode.seteBuyTimes(String.valueOf(reqMsg.geteBuyTimes()));
		}
		if(StringUtil.isNotEmpty(reqMsg.getsInvestMoney())) {
			recode.setsInvestMoney(String.valueOf(reqMsg.getsInvestMoney()));
		}
		if(StringUtil.isNotEmpty(reqMsg.geteInvestMoney())) {
			recode.seteInvestMoney(String.valueOf(reqMsg.geteInvestMoney()));
		}
		if(StringUtil.isNotEmpty(reqMsg.getsInvestTotalMoney())) {
			recode.setsInvestTotalMoney(String.valueOf(reqMsg.getsInvestTotalMoney()));
		}
		if(StringUtil.isNotEmpty(reqMsg.geteInvestTotalMoney())) {
			recode.seteInvestTotalMoney(String.valueOf(reqMsg.geteInvestTotalMoney()));
		}
		if(StringUtil.isNotEmpty(reqMsg.getsTotalBonus())) {
			recode.setsTotalBonus(String.valueOf(reqMsg.getsTotalBonus()));
		}
		if(StringUtil.isNotEmpty(reqMsg.geteTotalBonus())) {
			recode.seteTotalBonus(String.valueOf(reqMsg.geteTotalBonus()));
		}
		if(reqMsg.getsRegistTime() != null) {
			recode.setsRegistTime(reqMsg.getsRegistTime());
		}
		if(reqMsg.geteRegistTime() != null) {
			recode.seteRegistTime(reqMsg.geteRegistTime());
		}
		if(StringUtil.isNotEmpty(reqMsg.getAgentIds())) {
			String[] agentIds = reqMsg.getAgentIds().split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				recode.setAgentIds(ids);
			}
		}
		if(StringUtil.isNotEmpty(reqMsg.getNonAgentId())) {
			recode.setNonAgentId(reqMsg.getNonAgentId());
		}
		int receiveNum = 0;
		if(StringUtil.isNotEmpty(reqMsg.getUserIds())) {
			String trimStr = StringUtil.trimStr(reqMsg.getUserIds());
			String[] ids = trimStr.replace("，", ",").split(",");
			if(ids.length > 0) {
				List<Object> userIds = new ArrayList<Object>();
				for (String str : ids) {
					if(StringUtil.isNotEmpty(str.trim())) {
						receiveNum++;
						try {
							userIds.add(Integer.valueOf(str.trim()));
						} catch (Exception e) {
							log.error("此用户Id："+str+"不符合规范");
						}
					}
				}
				//如果没有符合条件的id值，默认设置为0
				if(userIds.size() == 0) {
					userIds.add(0);
				}
				recode.setUserIds(userIds);
			}
		}
		resMsg.setReceiveNum(receiveNum);
		
		int totalRows = bsUserService.countUserOperate(recode);
		if(totalRows > 0) {
			List<MUserOperateVO> list = bsUserService.findUserOperatePage(recode);
			resMsg.setUserOperateList(BeanUtils.classToArrayList(list));
		}
		resMsg.setTotalRows(totalRows);
	}
	
	public void appMessageQuery(ReqMsg_MUserOperate_AppMessageQuery reqMsg,ResMsg_MUserOperate_AppMessageQuery resMsg) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("isSend", 2);
		if(StringUtil.isNotEmpty(reqMsg.getName())) {
			map.put("name", reqMsg.getName());
		}
		List<BsAppMessage> list = mAppNoticeService.findNoticeByMap(map);
		resMsg.setAppMessageList(BeanUtils.classToArrayList(list));
		if(list != null && list.size() > 0) {
			resMsg.setPushChar(list.get(0).getPushChar());
		}
	}
	
	public void agentNameQuery(ReqMsg_MUserOperate_AgentNameQuery reqMsg,ResMsg_MUserOperate_AgentNameQuery resMsg) {
		resMsg.setAgentNames(agentService.findAllAgentName(reqMsg.getAgentIds()));
	}
}
