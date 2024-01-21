package com.pinting.manage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_BsUserChannel_BsUserChannelDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_BsUserChannel_BsUserChannelList;
import com.pinting.business.hessian.manage.message.ReqMsg_BsUserChannel_BsUserChannelModify;
import com.pinting.business.hessian.manage.message.ReqMsg_BsUserChannel_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_BsUserChannel_BsUserChannelDelete;
import com.pinting.business.hessian.manage.message.ResMsg_BsUserChannel_BsUserChannelList;
import com.pinting.business.hessian.manage.message.ResMsg_BsUserChannel_BsUserChannelModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsUserChannel_SelectByPrimaryKey;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;

/**
 * 用户优先支付渠道维护
 * @author shh
 *
 */
@Controller
public class BsUserChannelController {
	
	@Autowired
	@Qualifier("dispatchService")
	public HessianService userChannelService;
	
	/**
	 * 用户优先支付渠道列表
	 * @param reqMsg
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsUserChannel/index")
	public String channelInit(ReqMsg_BsUserChannel_BsUserChannelList reqMsg, HttpServletRequest request, Map<String, Object> model) {
		Integer pageNum = reqMsg.getPageNum();
		Integer numPerPage = reqMsg.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			reqMsg.setPageNum(pageNum);
			reqMsg.setNumPerPage(numPerPage);
		}
		/*String orderDirection = request.getParameter("orderDirection");
		String orderField = request.getParameter("orderField");
		if (orderDirection != null && orderField != null && !"".equals(orderDirection) && !"".equals(orderField)) {
			reqMsg.setOrderDirection(orderDirection);
			reqMsg.setOrderField(orderField);
			model.put("orderDirection", orderDirection);
			model.put("orderField", orderField);
		} else {
			reqMsg.setOrderDirection("desc");
			reqMsg.setOrderField("create_time");
			model.put("orderDirection", reqMsg.getOrderDirection());
			model.put("orderField", reqMsg.getOrderField());
		}*/
		ResMsg_BsUserChannel_BsUserChannelList resMsg = (ResMsg_BsUserChannel_BsUserChannelList) userChannelService.handleMsg(reqMsg);
		model.put("req", reqMsg);
		model.put("pageNum", resMsg.getPageNum());
		model.put("numPerPage", resMsg.getNumPerPage());
		model.put("totalRows", resMsg.getTotalRows());
		model.put("userChannelList", resMsg.getValueList());
		return "/bsUserChannel/index";
	}
	
	/**
	 * 进入编辑页面
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsUserChannel/detail")
	public String detail(ReqMsg_BsUserChannel_SelectByPrimaryKey req, HttpServletRequest request, Map<String, Object> model) {
		ResMsg_BsUserChannel_SelectByPrimaryKey res = (ResMsg_BsUserChannel_SelectByPrimaryKey) userChannelService.handleMsg(req);
		if (req.getId() != null && req.getId() != 0) {
			model.put("bsUserChannel", res);
		}
		model.put("payCards", res.getPayCardList());
		return "/bsUserChannel/detail";
	}
	
	
	/**
	 * 进入添加页面
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsUserChannel/add_detail")
	public String addDetail(ReqMsg_BsUserChannel_SelectByPrimaryKey req, HttpServletRequest request, Map<String, Object> model) {
		ResMsg_BsUserChannel_SelectByPrimaryKey res = (ResMsg_BsUserChannel_SelectByPrimaryKey) userChannelService.handleMsg(req);
		if (req.getId() != null && req.getId() != 0) {
			model.put("bsUserChannel", res);
		}
		model.put("payCards", res.getPayCardList());
		model.put("userId", req.getUserId());
		model.put("userName", req.getUserName());
		return "/bsUserChannel/add_detail";
	}
	
	/**
	 * 添加/修改
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsUserChannel/save")
	public @ResponseBody Map<Object, Object> save(ReqMsg_BsUserChannel_BsUserChannelModify req, HttpServletRequest request, Map<String, Object> model) {
		ResMsg_BsUserChannel_BsUserChannelModify res = (ResMsg_BsUserChannel_BsUserChannelModify) userChannelService.handleMsg(req);
		if (res.getFlag() == 1) {
			return ReturnDWZAjax.success("新增成功！");
		} else if (res.getFlag() == 2) {
			return ReturnDWZAjax.success("修改成功！");
		} else if (res.getFlag() == 3) {
			return ReturnDWZAjax.toAjaxString("301", "用户优先支付渠道类型已存在");
		} else {
			return ReturnDWZAjax.fail("操作失败！");
		}
	}
	
	/**
	 * 删除
	 * @param req
	 * @param request
	 * @param model
	 */
	@RequestMapping("/bsUserChannel/delete")
	public @ResponseBody Map<Object, Object> delete(ReqMsg_BsUserChannel_BsUserChannelDelete req, HttpServletRequest request, Map<String, Object> model) {
		ResMsg_BsUserChannel_BsUserChannelDelete res = (ResMsg_BsUserChannel_BsUserChannelDelete) userChannelService.handleMsg(req);
		if (res.getFlag() == 1) {
			return ReturnDWZAjax.success("删除成功！");
		} else if (res.getFlag() == 2) {
			return ReturnDWZAjax.success("删除失败！");
		} else {
			return ReturnDWZAjax.fail("操作失败！");
		}
	}

}
