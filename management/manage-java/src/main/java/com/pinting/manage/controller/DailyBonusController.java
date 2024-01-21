package com.pinting.manage.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.pinting.business.hessian.manage.message.ReqMsg_BsDailyBonus_ListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_BsDailyBonus_ServiceDetailList;
import com.pinting.business.hessian.manage.message.ResMsg_BsDailyBonus_ListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsDailyBonus_ServiceDetailList;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.StringUtil;
import com.pinting.util.Constants;
/**
 * 奖励金明细查询
 * @author caonengwen
 *
 */
@Controller
public class DailyBonusController {

	@Autowired
	@Qualifier("dispatchService")
	private HessianService hessianService;
	/**
	 * 奖励金明细查询
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/dailyBonus/index")
	public String bsDailyBonusIit(ReqMsg_BsDailyBonus_ListQuery reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		String pageNum = reqMsg.getPageNum();
		String numPerPage = reqMsg.getNumPerPage();
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
			reqMsg.setPageNum(pageNum);
			reqMsg.setNumPerPage(numPerPage);
		}
		ResMsg_BsDailyBonus_ListQuery resp = (ResMsg_BsDailyBonus_ListQuery) hessianService
				.handleMsg(reqMsg);
		model.put("bsDailyBonusList", resp.getBsDailyBonuss());
		model.put("allBonus", resp.getAllBonus() == null ? 0 : resp.getAllBonus());
		model.put("pageNum", resp.getPageNum());
		model.put("numPerPage", resp.getNumPerPage());
		model.put("totalRows", resp.getTotalRows());
		model.put("name", reqMsg.getName());
		model.put("byName", reqMsg.getByName());
		model.put("mobile", reqMsg.getMobile());
		model.put("byMobile", reqMsg.getByMobile());
		return "dailyBonus/index";
	}
	
	
	@RequestMapping("/customerService/dailyBonus")
	public String customerServiceBsDailyBonus(ReqMsg_BsDailyBonus_ListQuery reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		String pageNum = reqMsg.getPageNum();
		String numPerPage = reqMsg.getNumPerPage();
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
			reqMsg.setPageNum(pageNum);
			reqMsg.setNumPerPage(numPerPage);
		}
		if (reqMsg.getQueryFlag() != null && "QUERY".equals(reqMsg.getQueryFlag())) {
			ResMsg_BsDailyBonus_ListQuery resp = (ResMsg_BsDailyBonus_ListQuery) hessianService
					.handleMsg(reqMsg);
			model.put("bsDailyBonusList", resp.getBsDailyBonuss());
			model.put("allBonus", resp.getAllBonus() == null ? 0 : resp.getAllBonus());
			model.put("pageNum", resp.getPageNum());
			model.put("numPerPage", resp.getNumPerPage());
			model.put("totalRows", resp.getTotalRows());
			model.put("byName", reqMsg.getByName());
			model.put("byMobile", reqMsg.getByMobile());
			model.put("recommendName", reqMsg.getRecommendName());
			model.put("recommendMobile", reqMsg.getRecommendMobile());
		}
		return "dailyBonus/customerService_index";
	}
	
	@RequestMapping("/customerService/dailyBonus/subId")
	public String customerServiceBsDailyBonussubId(ReqMsg_BsDailyBonus_ServiceDetailList reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		
		ResMsg_BsDailyBonus_ServiceDetailList resp = (ResMsg_BsDailyBonus_ServiceDetailList) hessianService
				.handleMsg(reqMsg);
		model.put("bsDailyBonusList", resp.getBsDailyBonuss());
		model.put("allBonus", resp.getAllBonus() == null ? 0 : resp.getAllBonus());
		return "dailyBonus/customerService_detail";
	}
}






