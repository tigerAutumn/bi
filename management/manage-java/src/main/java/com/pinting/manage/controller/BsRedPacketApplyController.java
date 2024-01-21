package com.pinting.manage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_BsRedPacketApply_BsRedPacketApplyBudgetReview;
import com.pinting.business.hessian.manage.message.ReqMsg_BsRedPacketApply_BsRedPacketApplyCheckList;
import com.pinting.business.hessian.manage.message.ReqMsg_BsRedPacketApply_BsRedPacketApplyList;
import com.pinting.business.hessian.manage.message.ReqMsg_BsRedPacketApply_BsRedPacketApplyModify;
import com.pinting.business.hessian.manage.message.ReqMsg_BsRedPacketApply_BsRedPacketApplyRefuse;
import com.pinting.business.hessian.manage.message.ReqMsg_BsRedPacketApply_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_BudgetStat;
import com.pinting.business.hessian.manage.message.ResMsg_BsRedPacketApply_BsRedPacketApplyBudgetReview;
import com.pinting.business.hessian.manage.message.ResMsg_BsRedPacketApply_BsRedPacketApplyCheckList;
import com.pinting.business.hessian.manage.message.ResMsg_BsRedPacketApply_BsRedPacketApplyList;
import com.pinting.business.hessian.manage.message.ResMsg_BsRedPacketApply_BsRedPacketApplyModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsRedPacketApply_BsRedPacketApplyRefuse;
import com.pinting.business.hessian.manage.message.ResMsg_BsRedPacketApply_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_BudgetStat;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;

@Controller
public class BsRedPacketApplyController {
	
	@Autowired
	@Qualifier("dispatchService")
	public HessianService redPacketApplyService;
	
	@RequestMapping("/bsRedPacketApply/index")
	public String BsRedPacketApplyInit(ReqMsg_BsRedPacketApply_BsRedPacketApplyList req, HttpServletRequest request, Map<String, Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		String orderDirection = request.getParameter("orderDirection");
		String orderField = request.getParameter("orderField");
		if (orderDirection != null && orderField != null && !"".equals(orderDirection) && !"".equals(orderField)) {
			req.setOrderDirection(orderDirection);
			req.setOrderField(orderField);
			model.put("orderDirection", orderDirection);
			model.put("orderField", orderField);
		} else {
			req.setOrderDirection("desc");
			req.setOrderField("create_time");
			model.put("orderDirection", req.getOrderDirection());
			model.put("orderField", req.getOrderField());
		}
		ResMsg_BsRedPacketApply_BsRedPacketApplyList res = (ResMsg_BsRedPacketApply_BsRedPacketApplyList) redPacketApplyService.handleMsg(req);
		model.put("req", req);
		model.put("pageNum", res.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("redPacketApplyList", res.getValueList());
		model.put("creatorList", res.getCreatorList());
		return "/bsRedPacketApply/index";
	}
	
	/**
	 * 进入红包申请页面
	 * @param req
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsRedPacketApply/add_detail")
	public String addDetail(ReqMsg_BsRedPacketApply_SelectByPrimaryKey req, HttpServletResponse response, Map<String, Object> model) {
		if(req.getId() != null && req.getId() != 0) {
			ResMsg_BsRedPacketApply_SelectByPrimaryKey res = (ResMsg_BsRedPacketApply_SelectByPrimaryKey) redPacketApplyService.handleMsg(req);
			model.put("bsRedPacketApply", res);
		}
		return "/bsRedPacketApply/add_detail";
	}
	
	/**
	 * 添加/申请
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsRedPacketApply/save")
	public @ResponseBody Map<Object, Object> save(ReqMsg_BsRedPacketApply_BsRedPacketApplyModify req, HttpServletRequest request, Map<String, Object> model) {
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		req.setCreator(Integer.parseInt(mUserId));
		ResMsg_BsRedPacketApply_BsRedPacketApplyModify res = (ResMsg_BsRedPacketApply_BsRedPacketApplyModify) redPacketApplyService.handleMsg(req);
		if (res.getFlag() != null && res.getFlag() != 0) {
			if(res.getFlag() == 1) {
				return ReturnDWZAjax.success("添加成功！");
			} else if (res.getFlag() == 2) {
				return ReturnDWZAjax.toAjaxString("301", "红包名称已存在");
			} else {
				return ReturnDWZAjax.fail("操作失败！");
			}
		} else {
			return ReturnDWZAjax.fail("操作失败！");
		}
		
	}
	
	/**
	 * 红包审核列表
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsRedPacketApply/indexCheck")
	public String BsRedPacketApplyCheck(ReqMsg_BsRedPacketApply_BsRedPacketApplyCheckList req, HttpServletRequest request, Map<String, Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		String orderDirection = request.getParameter("orderDirection");
		String orderField = request.getParameter("orderField");
		if (orderDirection != null && orderField != null && !"".equals(orderDirection) && !"".equals(orderField)) {
			req.setOrderDirection(orderDirection);
			req.setOrderField(orderField);
			model.put("orderDirection", orderDirection);
			model.put("orderField", orderField);
		} else {
			req.setOrderDirection("desc");
			req.setOrderField("create_time");
			model.put("orderDirection", req.getOrderDirection());
			model.put("orderField", req.getOrderField());
		}
		ResMsg_BsRedPacketApply_BsRedPacketApplyCheckList res = (ResMsg_BsRedPacketApply_BsRedPacketApplyCheckList) redPacketApplyService.handleMsg(req);
		model.put("req", req);
		model.put("pageNum", res.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("redPacketApplyList", res.getValueList());
		model.put("creatorList", res.getCreatorList());
		
		ResMsg_RedPacket_BudgetStat redPaktRes = (ResMsg_RedPacket_BudgetStat) redPacketApplyService.handleMsg(new ReqMsg_RedPacket_BudgetStat());
		model.put("expiryAmount",redPaktRes.getExpiryAmount());
    	model.put("totalBudgetAmount",redPaktRes.getTotalBudgetAmount());
    	model.put("usedBudgetAmount",redPaktRes.getUsedBudgetAmount());
    	model.put("usedRedPaktAmount",redPaktRes.getUsedRedPaktAmount());
    	model.put("unUsedRedPaktAmount",redPaktRes.getUnUsedRedPaktAmount());
		
		return "/bsRedPacketApply/index_check";
	}
	
	/**
	 * 红包审核操作pass
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsRedPacketApply/updatePass")
	public @ResponseBody Map<Object, Object> budgetReview(ReqMsg_BsRedPacketApply_BsRedPacketApplyBudgetReview req, HttpServletRequest request, Map<String, Object> model) {
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		req.setChecker(Integer.valueOf(mUserId));
		ResMsg_BsRedPacketApply_BsRedPacketApplyBudgetReview res = (ResMsg_BsRedPacketApply_BsRedPacketApplyBudgetReview) redPacketApplyService.handleMsg(req);
		if (res.getFlag() == 1) {
			return ReturnDWZAjax.success("操作成功！");
		} else if (res.getFlag() == 2) {
			return ReturnDWZAjax.fail("操作失败！");
		} else {
			return ReturnDWZAjax.fail("操作失败！");
		}
	}
	
	@RequestMapping("/bsRedPacketApply/updateRefuse")
	public @ResponseBody Map<Object, Object> budgetReviewRefuse(ReqMsg_BsRedPacketApply_BsRedPacketApplyRefuse req, HttpServletRequest request, Map<String, Object> model) {
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		req.setChecker(Integer.valueOf(mUserId));
		ResMsg_BsRedPacketApply_BsRedPacketApplyRefuse res = (ResMsg_BsRedPacketApply_BsRedPacketApplyRefuse) redPacketApplyService.handleMsg(req);
		if (res.getFlag() == 1) {
			return ReturnDWZAjax.success("操作成功！");
		} else if (res.getFlag() == 2) {
			return ReturnDWZAjax.fail("操作失败！");
		} else {
			return ReturnDWZAjax.fail("操作失败！");
		}
	}

}
