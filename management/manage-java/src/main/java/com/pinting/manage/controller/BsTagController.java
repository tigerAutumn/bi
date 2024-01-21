package com.pinting.manage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_Tag_BsTagListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Tag_BsTagDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_Tag_BsTagList;
import com.pinting.business.hessian.manage.message.ReqMsg_Tag_BsTagModify;
import com.pinting.business.hessian.manage.message.ReqMsg_Tag_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_Tag_BsTagListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Tag_BsTagDelete;
import com.pinting.business.hessian.manage.message.ResMsg_Tag_BsTagList;
import com.pinting.business.hessian.manage.message.ResMsg_Tag_BsTagModify;
import com.pinting.business.hessian.manage.message.ResMsg_Tag_SelectByPrimaryKey;
import com.pinting.business.service.manage.BsTagService;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;

/**
 * 
 * @author shh
 *
 */
@Controller
public class BsTagController {
	
	@Autowired
	@Qualifier("dispatchService")
	public HessianService tagService;
	
	@Autowired
	private BsTagService service;
	
	private Logger log = LoggerFactory.getLogger(BsTagController.class);
	
	
	/**
	 * 标签列表
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsTag/index")
	public String tagInit(ReqMsg_Tag_BsTagList req, HttpServletRequest request, Map<String, Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_50_NUMPERPAGE;
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
		} 
		ResMsg_Tag_BsTagList res = (ResMsg_Tag_BsTagList) tagService.handleMsg(req);
		model.put("req", req);
		model.put("pageNum", res.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("tagList", res.getValueList());
		return "/bsTag/tag_index";
	}
	
	/**
	 * 进入添加/编辑页面
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsTag/detail")
	public String detail(ReqMsg_Tag_SelectByPrimaryKey req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		if (req.getId() != null && req.getId() != 0) {
			ResMsg_Tag_SelectByPrimaryKey res = (ResMsg_Tag_SelectByPrimaryKey) tagService.handleMsg(req);
			model.put("bsTag", res);
		}
		return "/bsTag/tag_detail";
	}
	
	/**
	 * 添加/修改
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("bsTag/save")
	public @ResponseBody Map<Object, Object> save(ReqMsg_Tag_BsTagModify req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		req.setCreator(Integer.parseInt(mUserId));
		ResMsg_Tag_BsTagModify res = (ResMsg_Tag_BsTagModify) tagService.handleMsg(req);
		if (res.getFlag() == 1) {
			return ReturnDWZAjax.success("新增成功！");
		} else if (res.getFlag() == 2) {
			return ReturnDWZAjax.success("修改成功！");
		} else if (res.getFlag() == 3) {
			return ReturnDWZAjax.toAjaxString("301", "标签名称已存在");
		} else {
			return ReturnDWZAjax.fail("操作失败！");
		}
	}
	
	/**
	 * 删除
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsTag/delete")
	public @ResponseBody Map<Object, Object> delete(ReqMsg_Tag_BsTagDelete req, 
			HttpServletRequest request, Map<String, Object> model) {
		ResMsg_Tag_BsTagDelete res = (ResMsg_Tag_BsTagDelete) tagService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			return ReturnDWZAjax.success("删除成功！");
		} else {
			return ReturnDWZAjax.fail("删除失败，请重试！");
		}
	}
	
	/**
	 * 所有标签名字
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("bsTag/allTagIndex")
	public String allAgent(ReqMsg_Tag_BsTagListQuery req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {
		ResMsg_Tag_BsTagListQuery res = (ResMsg_Tag_BsTagListQuery)tagService.handleMsg(req);
		model.put("tags", res.getTagList());
		// 标签总数
		model.put("tagTotalRows", res.getTagList().size());
		return  "bsTag/all_tag_index";
	}
	
	/**
	 * 所有标签总数
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsTag/count")
	@ResponseBody
	public Map<String, Object> count(ReqMsg_Tag_BsTagDelete req, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		int count = service.findCountTag(null);
		result.put("count", count);
		return result;
	}

}
