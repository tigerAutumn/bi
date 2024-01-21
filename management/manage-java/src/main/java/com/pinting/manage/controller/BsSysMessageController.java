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
import com.pinting.business.hessian.manage.message.ReqMsg_BsSysMessage_BsSysMessageDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSysMessage_BsSysMessageModify;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSysMessage_MessageList;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSysMessage_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_BsErrorCode_BsErrorCodeDelete;
import com.pinting.business.hessian.manage.message.ResMsg_BsErrorCode_BsErrorCodeModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsErrorCode_ErrorCodeList;
import com.pinting.business.hessian.manage.message.ResMsg_BsErrorCode_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_BsSysMessage_BsSysMessageDelete;
import com.pinting.business.hessian.manage.message.ResMsg_BsSysMessage_BsSysMessageModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsSysMessage_MessageList;
import com.pinting.business.hessian.manage.message.ResMsg_BsSysMessage_SelectByPrimaryKey;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;

@Controller
public class BsSysMessageController {
	@Autowired
	@Qualifier("dispatchService")
	public HessianService sysMessageService;
	
	/**
	 * 查询系统公告
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/sysMessage/index")
	public String sysMessageInit(ReqMsg_BsSysMessage_MessageList req, HttpServletRequest request, HashMap<String, Object> model) {
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
		ResMsg_BsSysMessage_MessageList res = (ResMsg_BsSysMessage_MessageList) sysMessageService.handleMsg(req);
		model.put("req", req);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("sysMessageList", res.getValueList());
		return "/sysMessage/index";
	}
	
	
	/**
	 * 进入添加/修改页面
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sysMessage/detail")
	public String detail(ReqMsg_BsSysMessage_SelectByPrimaryKey req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		if(req.getId() != null && req.getId() != 0) {
			ResMsg_BsSysMessage_SelectByPrimaryKey res = (ResMsg_BsSysMessage_SelectByPrimaryKey) sysMessageService.handleMsg(req);
			model.put("bsErrorCode", res);
		}
		return "/sysMessage/detail";
	}
	
	
	
	/**
	 * 添加/修改
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sysMessage/save")
	public @ResponseBody Map<Object, Object> save(ReqMsg_BsSysMessage_BsSysMessageModify req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		ResMsg_BsSysMessage_BsSysMessageModify res = (ResMsg_BsSysMessage_BsSysMessageModify) sysMessageService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			if (req.getId() != null ) {
				return ReturnDWZAjax.success("修改成功！");
			}else {
				return ReturnDWZAjax.success("新增成功！");
			}
			
		} else {
			return ReturnDWZAjax.fail("操作失败！");
		}
	}
	
	@RequestMapping("/sysMessage/delete")
	public @ResponseBody Map<Object, Object> delete(ReqMsg_BsSysMessage_BsSysMessageDelete req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		ResMsg_BsSysMessage_BsSysMessageDelete res = (ResMsg_BsSysMessage_BsSysMessageDelete) sysMessageService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			return ReturnDWZAjax.success("删除成功！");
		} else {
			return ReturnDWZAjax.fail("删除失败，请重试！");
		}
	}
	
}
