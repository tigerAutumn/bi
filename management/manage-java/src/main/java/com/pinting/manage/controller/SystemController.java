package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.dao.BsScheduleConfigMapper;
import com.pinting.business.enums.ScheduleJobEnums;
import com.pinting.business.enums.TimeTypeEnum;
import com.pinting.business.enums.WxMuserStatusEnum;
import com.pinting.business.hessian.manage.message.ReqMsg_MQuartzSchedule_PauseJob;
import com.pinting.business.hessian.manage.message.ReqMsg_MQuartzSchedule_ResumeJob;
import com.pinting.business.hessian.manage.message.ReqMsg_MSystem_SysConfigQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MSystem_SysConfigUpdate;
import com.pinting.business.hessian.manage.message.ReqMsg_MSystem_SysConfigsQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MSystem_status;
import com.pinting.business.hessian.manage.message.ReqMsg_MSystem_statusUpdate;
import com.pinting.business.hessian.manage.message.ResMsg_MQuartzSchedule_PauseJob;
import com.pinting.business.hessian.manage.message.ResMsg_MQuartzSchedule_ResumeJob;
import com.pinting.business.hessian.manage.message.ResMsg_MSystem_SysConfigQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MSystem_SysConfigUpdate;
import com.pinting.business.hessian.manage.message.ResMsg_MSystem_SysConfigsQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MSystem_status;
import com.pinting.business.hessian.manage.message.ResMsg_MSystem_statusUpdate;
import com.pinting.business.model.BsPayLimit;
import com.pinting.business.model.BsScheduleConfig;
import com.pinting.business.model.BsScheduleConfigExample;
import com.pinting.business.model.BsSysStatus;
import com.pinting.business.model.vo.MUpdateWxUserInfoResVO;
import com.pinting.business.model.vo.MUserOpRecordVO;
import com.pinting.business.model.vo.ManageResVO;
import com.pinting.business.model.vo.SysOperationalDataVO;
import com.pinting.business.model.vo.SysPayLimitVO;
import com.pinting.business.service.manage.MSysStatusService;
import com.pinting.business.service.manage.MUserOpRecordService;
import com.pinting.business.service.manage.MWxUserService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.schedule.hessian.message.B2SReqMsg_QuartzSchedule_PauseJob;
import com.pinting.schedule.hessian.message.B2SReqMsg_QuartzSchedule_ResumeJob;
import com.pinting.schedule.out.service.ScheduleTransportService;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;

/**
 * 系统管理
 * 
 * @Project: manage-java
 * @Title: SystemController.java
 * @author dingpf
 * @date 2015-1-29 上午10:36:53
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Controller
public class SystemController {
	@Autowired
	@Qualifier("dispatchService")
	private HessianService manageService;
	@Autowired
	private MUserOpRecordService mUserOpRecordService;
	@Autowired
	private MWxUserService mWxUserService;
	@Autowired
	private SysConfigService sysConfigService;
	
	@RequestMapping("/sys/config/index")
	public String sysConfigIndex(ReqMsg_MSystem_SysConfigsQuery reqMsg,
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

		ResMsg_MSystem_SysConfigsQuery resMsg = (ResMsg_MSystem_SysConfigsQuery) manageService
				.handleMsg(reqMsg);
		ArrayList<HashMap<String, Object>> configList = resMsg.getConfigs();
		model.put("configList", configList);
		// 分页信息
		model.put("pageNum", resMsg.getPageNum());
		model.put("numPerPage", resMsg.getNumPerPage());
		model.put("totalRows", resMsg.getTotalRows());

		return "/system/sysconfig";

	}

	@RequestMapping("/sys/config/detail")
	public String sysConfigDetail(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		
		String updateFlag = request.getParameter("updateFlag");
		if(Constants.SYSCONFIG_UPDATEFLAG_UPDATE.equals(updateFlag)){
			String confKey = request.getParameter("confKey");
			ReqMsg_MSystem_SysConfigQuery req = new ReqMsg_MSystem_SysConfigQuery();
			req.setConfKey(confKey);
			ResMsg_MSystem_SysConfigQuery res = (ResMsg_MSystem_SysConfigQuery) manageService.handleMsg(req);
			model.put("conf", res);
		}
		
		model.put("updateFlag", updateFlag);

		return "/system/sysconf_detail";
	}

	@RequestMapping("/sys/config/update")
	public @ResponseBody Map<Object, Object> sysConfigUpdate(ReqMsg_MSystem_SysConfigUpdate reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		
		ResMsg_MSystem_SysConfigUpdate resMsg = (ResMsg_MSystem_SysConfigUpdate) manageService.handleMsg(reqMsg);
		if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())){
			return ReturnDWZAjax.success("操作成功！");
		}else{
			return ReturnDWZAjax.fail("操作失败！");
		}
		
		
	}


	/**
	 * 查询日志
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sys/userOpRecord/index")
	public String sysUserOpRecordIndex(MUserOpRecordVO record,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {

		int count = mUserOpRecordService.countMList(record);
		if(count>0){
			List<MUserOpRecordVO> list = mUserOpRecordService.getMList(record);
			model.put("recordList", list);
			model.put("totalRows", count);
		}
		model.put("mRecord", record);
		model.put("pageNum", record.getPageNum());
		model.put("numPerPage", record.getNumPerPage());
		
		return "/system/user_op_record_index";

		
	}
	
	
	/************************************运营数据微信用户管理***********************************/
	
	/**
	 * 运营数据微信用户管理列表
	 * @param pagerReq
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sys/operationalData/index")
	public String sysOperationalDataIndex(SysOperationalDataVO req,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}
		int count = mWxUserService.countOperationalDataInfo(req.getNickName());
		if(count > 0){
			List<SysOperationalDataVO> list = mWxUserService.selectOperationalDataInfoList(req.getNickName(), Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
			model.put("pageList", list);
			model.put("totalRows", count);
		}
	    model.put("req", req);
	    model.put("pageNum", pageNum);
	    model.put("numPerPage", numPerPage);
		return "/system/operational_data_index";
	}
	
	
	/**
	 * 运营数据微信用户管理，更新状态
	 * @param mId
	 * @param nick
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sys/operationalData/detail")
	public String sysOperationalDataDetail(String mId, String nick,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		model.put("mId", mId);
		model.put("nick", nick);
		return "/system/operational_data_detail";
	}
	
	/**
     * 运营数据微信用户管理，修改实现
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/sys/operationalData/updateWxUserInfo")
    @ResponseBody
    public Map<Object, Object> updateWxUserInfo(@RequestBody SysOperationalDataVO req, HttpServletRequest request,
                             HttpServletResponse response, Map<String, Object> model) {
    	CookieManager cookie = new CookieManager(request);
		String userId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		req.setOpUserId(StringUtil.isEmpty(userId) ? 1 : Integer.parseInt(userId));
   		if (req != null && req.getId() > 0) {
   			MUpdateWxUserInfoResVO res = mWxUserService.updateWxUserInfo(req);
   			if (StringUtil.isEmpty(res.getReturnMsg())) {
   				return ReturnDWZAjax.success("操作成功！");   				
   			} else {
   				return ReturnDWZAjax.fail(res.getReturnMsg());		
   			}
   		} else {
   			return ReturnDWZAjax.fail("没有要编辑的记录！");
   		}    	
    }
    
	@RequestMapping("/sys/operationalData/updateStatus")
	@ResponseBody
	public Map<Object, Object> updateOperationalDataStatus(@RequestParam String mId,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		CookieManager cookie = new CookieManager(request);
		String userId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		Boolean isSuccess = mWxUserService.updateStatus(WxMuserStatusEnum.WX_MUSER_STATUS_DELETED.getCode(), Integer.parseInt(mId),
				Integer.parseInt(userId));
		if (isSuccess) {
			return ReturnDWZAjax.success("操作成功！");
		} else {
			return ReturnDWZAjax.fail("操作失败！");
		}
	}
	
	/************************************运营数据微信用户管理***********************************/
	
	/************************************宝付代付归集户配置start***********************************/
	@RequestMapping("/sys/payLimit/index")
    public String findList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}
		SysPayLimitVO req = new SysPayLimitVO();
		int count = sysConfigService.countSysPayLimit(req);
		if(count > 0){
			List<SysPayLimitVO> list = sysConfigService.findSysPayLimitList(req, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
			model.put("pageList", list);
			model.put("totalRows", count);
		}
	    model.put("req", req);
	    model.put("pageNum", pageNum);
	    model.put("numPerPage", numPerPage);
		return "/system/pay_limit_index";
    }
	
	/**
	 * 更新宝付代付归集户配置状态
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sys/payLimit/updateStatus")
	@ResponseBody 
    public Map<Object, Object> updateStatus(@RequestParam String ruleId, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		CookieManager cookie = new CookieManager(request);
		String userId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		if (sysConfigService.dateIsPayLimit(Integer.parseInt(ruleId), new Date())) {
			return ReturnDWZAjax.fail("当前时间处于限制时间段内，不允许删除和修改！");
		}
		Boolean isSuccess = sysConfigService.updatePayLimitStatus(com.pinting.business.util.Constants.PAY_LIMIT_DELETE_YES, Integer.parseInt(ruleId), Integer.parseInt(userId));
		if (isSuccess) {
			return ReturnDWZAjax.success("操作成功！");
		} else {
			return ReturnDWZAjax.fail("操作失败！");
		}
    }
	
	/**
     * 进入添加/编辑页面
     * @param ruleId
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/sys/payLimit/detail")
    public String detail(Integer ruleId, HttpServletRequest request, HttpServletResponse response,
                         Map<String, Object> model) {
        if (null != ruleId) {
        	BsPayLimit payLimit = sysConfigService.findPayLimitById(ruleId);
        	model.put("timeStart", payLimit.getTimeStart());
			model.put("timeEnd", payLimit.getTimeEnd());
        	model.put("payLimit", payLimit);
        } 
        return "/system/config_detail";
    }

    /**
     * 添加/修改实现
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/sys/payLimit/operateConfig")
    @ResponseBody
    public Map<Object, Object> save(SysPayLimitVO req, HttpServletRequest request,
                             HttpServletResponse response, Map<String, Object> model) {
    	String operateFlag = request.getParameter("operateFlag");
    	String triggerTimeStart = req.getTimeStart();
    	String triggerTimeEnd = req.getTimeEnd();
    	CookieManager cookie = new CookieManager(request);
		String userId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		req.setmUserId(StringUtil.isEmpty(userId) ? 1 : Integer.parseInt(userId));
    	if (!TimeTypeEnum.DEFAULT.getTimeType().equals(req.getTimeType())) {
    		if (StringUtil.isEmpty(triggerTimeStart) || StringUtil.isEmpty(triggerTimeEnd)) {
        		return ReturnDWZAjax.fail("宝付代付归集户配置触发时间不能为空！");
        	}
        	if (triggerTimeStart.compareTo(triggerTimeEnd) >= 0) {
        		return ReturnDWZAjax.fail("宝付代付归集户配置触发开始时间大于或等于结束时间！");
        	}
    	}
    	if ("add".equals(operateFlag) || "update".equals(operateFlag)) {
    		ManageResVO res = sysConfigService.operatePayLimitConfig(req, operateFlag);
    		if (StringUtil.isEmpty(res.getReturnMsg())) {
				return ReturnDWZAjax.success("操作成功！");   				
			} else {
				return ReturnDWZAjax.fail(res.getReturnMsg());		
			}
    	} else {
    		return ReturnDWZAjax.fail("没有要审核的记录！");
    	}
    }
	/************************************宝付代付归集户配置end***********************************/

	
}
