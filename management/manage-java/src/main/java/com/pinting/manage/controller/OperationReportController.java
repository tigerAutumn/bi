package com.pinting.manage.controller;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_BsSysNews_BsSysNewsModify;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSysNews_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_BsSysNews_BsSysNewsModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsSysNews_SelectByPrimaryKey;
import com.pinting.business.model.BsOperationReport;
import com.pinting.business.model.vo.BsOperationReportVO;
import com.pinting.business.model.vo.UserRechanageStatisticsVO;
import com.pinting.business.service.manage.OperationReportService;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.GlobEnv;
import com.pinting.util.ReturnDWZAjax;

@Controller
public class OperationReportController {
	@Autowired
	private OperationReportService operationReportService;
	
	/**
	 * 运营报告管理列表页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
    @RequestMapping("/operationReport/reportIndex")
    public String reportIndex(BsOperationReportVO req, HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
    	req.setStart(0);
		List<BsOperationReportVO> reportList = operationReportService.queryOperationReportList(req.getStart(), req.getNumPerPage());
		Integer count = operationReportService.queryOperationReportCount();

		model.put("req", req);
		model.put("count", count);
		model.put("reportList", reportList);

        return "/operationReport/report_index";
    }
	
    /**
     * 运营报告新增页面
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/operationReport/reportAdd")
    public String reportAdd(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
    	
    	return "/operationReport/report_add";
    }
    
    
    
    /**
     * 新增运营报告保存
     * @param request
     * @param response
     * @param model
     * @return
     */
	@RequestMapping("/operationReport/reportSave")
	public @ResponseBody Map<Object, Object> reportSave(BsOperationReport report,HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		if (!StringUtil.isNotBlank(report.getIsSugguest())){
			report.setIsSugguest("FALSE");
		}	
		if (!StringUtil.isNotBlank(report.getReportName()) || !StringUtil.isNotBlank(report.getDisplayTime())
				|| !StringUtil.isNotBlank(report.getImgUrl())|| !StringUtil.isNotBlank(report.getStorageAddress())	) {
			return ReturnDWZAjax.fail("请完整填写信息！");
		}
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		report.setOpUserId(Integer.valueOf(mUserId));
		report.setShowTerminal("PC");
		report.setCreateTime(new Date());
		report.setUpdateTime(new Date());
		int num = operationReportService.addOperationReport(report);
		if (num > 0) {
			return ReturnDWZAjax.success("新增成功！");
		}else {
			return ReturnDWZAjax.fail("新增失败！");
		}
	}
	
	
	
	/**
	 * 新增运营报告推荐/取消推荐
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/operationReport/reportSugguest")
	public @ResponseBody Map<Object, Object> reportSugguest(Integer id,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		if (id == null || "".equals(String.valueOf(id))) {
			return ReturnDWZAjax.fail("参数有误！");
		}
		BsOperationReport report = operationReportService.queryOperationReportById(id);
		if (report == null) {
			return ReturnDWZAjax.fail("找不到运营报告！");
		}
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		report.setOpUserId(Integer.valueOf(mUserId));
		if ("TRUE".equals(report.getIsSugguest())) {
			report.setIsSugguest("FALSE");
		}else if ("FALSE".equals(report.getIsSugguest())) {
			report.setIsSugguest("TRUE");
		}
		report.setUpdateTime(new Date());
		int num = operationReportService.updateOperationReport(report);
		if (num>0) {
			return ReturnDWZAjax.success("操作成功！");
		}else {
			return ReturnDWZAjax.fail("操作失败！");
		}
	}

	/**
	 * 删除运营报告
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/operationReport/reportDelete")
	public @ResponseBody Map<Object, Object> reportDelete(Integer id,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		if (id == null || "".equals(String.valueOf(id))) {
			return ReturnDWZAjax.fail("参数有误！");
		}
		BsOperationReport report = operationReportService.queryOperationReportById(id);

		if (report == null) {
			return ReturnDWZAjax.fail("找不到运营报告！");
		}
		int num = operationReportService.deleteOperationReportById(id);
		if (num>0) {
			
			String[] filePath = report.getStorageAddress().split("/resources/upload/report"); 
			String[] imgpath = report.getImgUrl().split("/resources/upload/report"); 
			File file = new File(GlobEnv.get("operation.report.upload") + filePath[1]);
			file.delete();
			
			File img = new File(GlobEnv.get("operation.report.upload") + imgpath[1]);
			img.delete();
			
			return ReturnDWZAjax.success("删除成功！");
		}else {
			return ReturnDWZAjax.fail("删除失败！");
		}
	}
	
	
    /**
     * 运营报告编辑页面
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/operationReport/reportDetail")
    public String reportDetail(Integer id,HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
    	BsOperationReport report = operationReportService.queryOperationReportById(id);
    	model.put("report", report);
    	return "/operationReport/report_detail";
    }
    
    /**
     * 更新运营报告保存
     * @param request
     * @param response
     * @param model
     * @return
     */
	@RequestMapping("/operationReport/reportUpdate")
	public @ResponseBody Map<Object, Object> reportUpdate(BsOperationReport report,HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		if (!StringUtil.isNotBlank(report.getIsSugguest())){
			report.setIsSugguest("FALSE");
		}	
		if (!StringUtil.isNotBlank(report.getReportName()) || !StringUtil.isNotBlank(report.getDisplayTime())
				|| !StringUtil.isNotBlank(report.getImgUrl())|| !StringUtil.isNotBlank(report.getStorageAddress())	) {
			return ReturnDWZAjax.fail("请完整填写信息！");
		}
		
		if (report.getId() == null || "".equals(String.valueOf(report.getId()))) {
			return ReturnDWZAjax.fail("参数有误！");
		}
		BsOperationReport operationReport = operationReportService.queryOperationReportById(report.getId());
		if (operationReport == null) {
			return ReturnDWZAjax.fail("找不到运营报告！");
		}
		
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		
		report.setOpUserId(Integer.valueOf(mUserId));
		report.setUpdateTime(new Date());
		
		int num = operationReportService.updateOperationReport(report);
		if (num > 0) {
			return ReturnDWZAjax.success("编辑成功！");
		}else {
			return ReturnDWZAjax.fail("编辑失败！");
		}
	}
}
