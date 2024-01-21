package com.pinting.manage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_BsErrorCode_BsErrorCodeDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_BsErrorCode_BsErrorCodeModify;
import com.pinting.business.hessian.manage.message.ReqMsg_BsErrorCode_ErrorCodeList;
import com.pinting.business.hessian.manage.message.ReqMsg_BsErrorCode_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_BsErrorCode_BsErrorCodeDelete;
import com.pinting.business.hessian.manage.message.ResMsg_BsErrorCode_BsErrorCodeModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsErrorCode_ErrorCodeList;
import com.pinting.business.hessian.manage.message.ResMsg_BsErrorCode_SelectByPrimaryKey;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;

/**
 * 
 * @author shh
 *
 */
@Controller
public class BsErrorCodeController {
	
	@Autowired
	@Qualifier("dispatchService")
	public HessianService errorCodeService;
	
	/**
	 * 错误信息列表
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsErrorCode/index")
	public String errorCodeInit(ReqMsg_BsErrorCode_ErrorCodeList req, HttpServletRequest request, HashMap<String, Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage(); 
		if(pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		if(request.getParameter("orderDirection") != null && request.getParameter("orderField") != null) {
			req.setOrderDirection(request.getParameter("orderDirection"));
			req.setOrderField(request.getParameter("orderField"));
			model.put("orderDirection", request.getParameter("orderDirection"));
			model.put("orderField", request.getParameter("orderField"));
		} else {
			req.setOrderDirection("desc");
			req.setOrderField("create_time");
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		ResMsg_BsErrorCode_ErrorCodeList res = (ResMsg_BsErrorCode_ErrorCodeList) errorCodeService.handleMsg(req);
		model.put("req", req);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("errorCodeList", res.getValueList());
		return "/bsErrorCode/index";
	}
	
	/**
	 * 进入添加/修改页面
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsErrorCode/detail")
	public String detail(ReqMsg_BsErrorCode_SelectByPrimaryKey req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		if(req.getId() != null && req.getId() != 0) {
			ResMsg_BsErrorCode_SelectByPrimaryKey res = (ResMsg_BsErrorCode_SelectByPrimaryKey) errorCodeService.handleMsg(req);
			model.put("bsErrorCode", res);
		}
		return "/bsErrorCode/detail";
	}
	
	/**
	 * 添加/修改
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsErrorCode/save")
	public @ResponseBody Map<Object, Object> save(ReqMsg_BsErrorCode_BsErrorCodeModify req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		ResMsg_BsErrorCode_BsErrorCodeModify res = (ResMsg_BsErrorCode_BsErrorCodeModify) errorCodeService.handleMsg(req);
		if (res.getFlag() == 1) {
			return ReturnDWZAjax.success("新增成功！");
		} else if (res.getFlag() == 2) {
			return ReturnDWZAjax.success("修改成功！");
		} else if (res.getFlag() == 3) {
			return ReturnDWZAjax.toAjaxString("301", "错误码已存在");
		} else {
			return ReturnDWZAjax.fail("操作失败！");
		}
		
	}
	
	@RequestMapping("/bsErrorCode/delete")
	public @ResponseBody Map<Object, Object> delete(ReqMsg_BsErrorCode_BsErrorCodeDelete req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		ResMsg_BsErrorCode_BsErrorCodeDelete res = (ResMsg_BsErrorCode_BsErrorCodeDelete) errorCodeService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			return ReturnDWZAjax.success("删除成功！");
		} else {
			return ReturnDWZAjax.fail("删除失败，请重试！");
		}
	}
	
	
}
