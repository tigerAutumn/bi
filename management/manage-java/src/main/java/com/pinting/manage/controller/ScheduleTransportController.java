package com.pinting.manage.controller;

import com.pinting.business.enums.ScheduleJobEnums;
import com.pinting.business.hessian.manage.message.*;
import com.pinting.business.model.BsScheduleConfig;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.schedule.hessian.message.*;
import com.pinting.schedule.out.service.ScheduleTransportService;
import com.pinting.util.ReturnDWZAjax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 定时任务管理
 * @author bianyatian
 * @2017-5-12 下午5:17:58
 */

@Controller
public class ScheduleTransportController {

	@Autowired
	public ScheduleTransportService scheduleTransportService;
	@Autowired
	private HessianService manageService;
	
	/**
	 * 根据传入条件获取列表
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping("/scheduleTransport/index")
	public String bannerIndex(HashMap<String,Object> model,B2SReqMsg_QuartzSchedule_QueryAllJob req,
			HttpServletRequest request, HttpServletResponse response){
		if(req == null){
			req.setJobGroup(null);
			req.setJobStatus(null);
		}
		B2SResMsg_QuartzSchedule_QueryAllJob res = scheduleTransportService.queryAllJob(req);
		model.put("list", res.getJobs());
		model.put("req", req);
		model.put("totalRows", res.getJobs().size());
		return "/scheduleTransport/index";
	}
	
	
	/**
	 * 根据传入条件获取列表
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping("/scheduleTransport/allRunning")
	public String allRunning(HashMap<String,Object> model,B2SReqMsg_QuartzSchedule_QueryRunningJob req,
			HttpServletRequest request, HttpServletResponse response){
		B2SResMsg_QuartzSchedule_QueryRunningJob res = scheduleTransportService.queryRunningJob(req);
		model.put("list", res.getJobs());
		model.put("req", req);
		model.put("totalRows", res.getJobs().size());
		return "/scheduleTransport/allRunning";
	}
	
	
	/**
	 * 跳转至新增页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/scheduleTransport/addPage")
	public String addPage(HttpServletRequest request, HttpServletResponse response){
		
		return "/scheduleTransport/addPage";
	}
	
	/**
	 * 新增
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/scheduleTransport/add")
	public @ResponseBody Map<Object,Object> add(BsScheduleConfig scheduleJob,
			HttpServletRequest request, HttpServletResponse response,HashMap<String,Object> model){
		try {
			if(ScheduleJobEnums.JOB_GROUP_SCHEDULE.getCode().equals(scheduleJob.getJobGroup())){
				B2SReqMsg_QuartzSchedule_AddJob req = new B2SReqMsg_QuartzSchedule_AddJob();
				req.setScheduleJob(scheduleJob);
				B2SResMsg_QuartzSchedule_AddJob res = scheduleTransportService.addJob(req);
				if("000000".equals(res.getResCode())){
					return ReturnDWZAjax.success("添加成功！");
				}else{
					return ReturnDWZAjax.fail(res.getResMsg());
				}
			}else if(ScheduleJobEnums.JOB_GROUP_BUSINESS.getCode().equals(scheduleJob.getJobGroup())){
				ReqMsg_MQuartzSchedule_AddJob req = new ReqMsg_MQuartzSchedule_AddJob();
				req.setScheduleJob(scheduleJob);
				ResMsg_MQuartzSchedule_AddJob res = (ResMsg_MQuartzSchedule_AddJob)manageService.handleMsg(req);
				if("000000".equals(res.getResCode())){
					return ReturnDWZAjax.success("添加成功！");
				}else{
					return ReturnDWZAjax.fail(res.getResMsg());
				}
			}else {
				return ReturnDWZAjax.fail("添加失败，暂不支持此组别");
			}
		} catch (Exception e) {
			return ReturnDWZAjax.fail("添加失败，原因不详！");
		}
	}
	
	
	/**
	 * 跳转至修改页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/scheduleTransport/updatePage")
	public String updatePage(HttpServletRequest request, HttpServletResponse response,HashMap<String,Object> model){
		
		model.put("id", request.getParameter("id"));
		model.put("jobGroup", request.getParameter("jobGroup"));
		model.put("cronExpression", request.getParameter("cronExpression"));
		model.put("description", request.getParameter("description"));
		return "/scheduleTransport/updatePage";
	}
	
	
	/**
	 * 修改
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/scheduleTransport/update")
	public @ResponseBody Map<Object,Object> update(B2SReqMsg_QuartzSchedule_UpdateJobCron req, String jobGroup,
			HttpServletRequest request, HttpServletResponse response,HashMap<String,Object> model){
		try {
			if(ScheduleJobEnums.JOB_GROUP_SCHEDULE.getCode().equals(jobGroup)){
				B2SResMsg_QuartzSchedule_UpdateJobCron res = scheduleTransportService.updateJobCron(req);
				if("000000".equals(res.getResCode())){
					return ReturnDWZAjax.success("修改成功！");
				}else{
					return ReturnDWZAjax.fail(res.getResMsg());
				}
			}else if(ScheduleJobEnums.JOB_GROUP_BUSINESS.getCode().equals(jobGroup)){
				ReqMsg_MQuartzSchedule_UpdateJobCron bizReq = new ReqMsg_MQuartzSchedule_UpdateJobCron();
				bizReq.setCronExpression(req.getCronExpression());
				bizReq.setJobId(req.getJobId());
				ResMsg_MQuartzSchedule_UpdateJobCron res = (ResMsg_MQuartzSchedule_UpdateJobCron)manageService.handleMsg(bizReq);
				if("000000".equals(res.getResCode())){
					return ReturnDWZAjax.success("修改成功！");
				}else{
					return ReturnDWZAjax.fail(res.getResMsg());
				}
			}else {
				return ReturnDWZAjax.fail("暂不支持此组别");
			}
		} catch (Exception e) {
			return ReturnDWZAjax.fail("修改失败，原因不详！");
		}
	}
	
	
	/**
	 * 立即执行
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/scheduleTransport/doNow")
	public @ResponseBody Map<Object,Object> doNow(
			HttpServletRequest request, HttpServletResponse response,HashMap<String,Object> model){
		try {
			if(ScheduleJobEnums.JOB_GROUP_SCHEDULE.getCode().equals(request.getParameter("jobGroup"))){
				B2SReqMsg_QuartzSchedule_RunAJobNow req = new B2SReqMsg_QuartzSchedule_RunAJobNow();
				req.setJobId(Integer.parseInt(request.getParameter("id")));
				B2SResMsg_QuartzSchedule_RunAJobNow res = scheduleTransportService.runAJobNow(req);
				if("000000".equals(res.getResCode())){
					return ReturnDWZAjax.success("立即执行一次成功！");
				}else{
					return ReturnDWZAjax.fail(res.getResMsg());
				}
			}else if(ScheduleJobEnums.JOB_GROUP_BUSINESS.getCode().equals(request.getParameter("jobGroup"))){
				ReqMsg_MQuartzSchedule_RunAJobNow bizReq = new ReqMsg_MQuartzSchedule_RunAJobNow();
				bizReq.setJobId(Integer.parseInt(request.getParameter("id")));
				ResMsg_MQuartzSchedule_RunAJobNow res = (ResMsg_MQuartzSchedule_RunAJobNow)manageService.handleMsg(bizReq);
				if("000000".equals(res.getResCode())){
					return ReturnDWZAjax.success("立即执行一次成功！");
				}else{
					return ReturnDWZAjax.fail(res.getResMsg());
				}
			}else {
				return ReturnDWZAjax.fail("暂不支持此组别");
			}
		} catch (Exception e) {
			return ReturnDWZAjax.fail("立即执行一次失败，原因不详！");
		}
	}
	
	
	/**
	 * 启用
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/scheduleTransport/runing")
	public @ResponseBody Map<Object,Object> runing(
			HttpServletRequest request, HttpServletResponse response,HashMap<String,Object> model){
		try {
			if(ScheduleJobEnums.JOB_GROUP_SCHEDULE.getCode().equals(request.getParameter("jobGroup"))){
				B2SReqMsg_QuartzSchedule_ResumeJob req = new B2SReqMsg_QuartzSchedule_ResumeJob();
				req.setJobId(Integer.parseInt(request.getParameter("id")));
				B2SResMsg_QuartzSchedule_ResumeJob res = scheduleTransportService.resumeJob(req);
				if("000000".equals(res.getResCode())){
					return ReturnDWZAjax.success("启用成功！");
				}else{
					return ReturnDWZAjax.fail(res.getResMsg());
				}
			}else if(ScheduleJobEnums.JOB_GROUP_BUSINESS.getCode().equals(request.getParameter("jobGroup"))){
				ReqMsg_MQuartzSchedule_ResumeJob bizReq = new ReqMsg_MQuartzSchedule_ResumeJob();
				bizReq.setJobId(Integer.parseInt(request.getParameter("id")));
				ResMsg_MQuartzSchedule_ResumeJob res = (ResMsg_MQuartzSchedule_ResumeJob)manageService.handleMsg(bizReq);
				if("000000".equals(res.getResCode())){
					return ReturnDWZAjax.success("启用成功！");
				}else{
					return ReturnDWZAjax.fail(res.getResMsg());
				}
			}else {
				return ReturnDWZAjax.fail("暂不支持此组别");
			}
		} catch (Exception e) {
			return ReturnDWZAjax.fail("启用失败，原因不详！");
		}
	}
	
	
	/**
	 * 停止
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/scheduleTransport/stop")
	public @ResponseBody Map<Object,Object> stop(
			HttpServletRequest request, HttpServletResponse response,HashMap<String,Object> model){
		try {
			if(ScheduleJobEnums.JOB_GROUP_SCHEDULE.getCode().equals(request.getParameter("jobGroup"))){
				B2SReqMsg_QuartzSchedule_PauseJob req = new B2SReqMsg_QuartzSchedule_PauseJob();
				req.setJobId(Integer.parseInt(request.getParameter("id")));
				B2SResMsg_QuartzSchedule_PauseJob res = scheduleTransportService.pauseJob(req);
				if("000000".equals(res.getResCode())){
					return ReturnDWZAjax.success("停止成功！");
				}else{
					return ReturnDWZAjax.fail(res.getResMsg());
				}
			}else if(ScheduleJobEnums.JOB_GROUP_BUSINESS.getCode().equals(request.getParameter("jobGroup"))){
				ReqMsg_MQuartzSchedule_PauseJob bizReq = new ReqMsg_MQuartzSchedule_PauseJob();
				bizReq.setJobId(Integer.parseInt(request.getParameter("id")));
				ResMsg_MQuartzSchedule_PauseJob res = (ResMsg_MQuartzSchedule_PauseJob)manageService.handleMsg(bizReq);
				if("000000".equals(res.getResCode())){
					return ReturnDWZAjax.success("停止成功！");
				}else{
					return ReturnDWZAjax.fail(res.getResMsg());
				}
			}else {
				return ReturnDWZAjax.fail("暂不支持此组别");
			}
		} catch (Exception e) {
			return ReturnDWZAjax.fail("停止失败，原因不详！");
		}
	}
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/scheduleTransport/delete")
	public @ResponseBody Map<Object,Object> delete(
			HttpServletRequest request, HttpServletResponse response,HashMap<String,Object> model){
		try {
			if(ScheduleJobEnums.JOB_GROUP_SCHEDULE.getCode().equals(request.getParameter("jobGroup"))){
				B2SReqMsg_QuartzSchedule_DeleteJob req = new B2SReqMsg_QuartzSchedule_DeleteJob();
				req.setJobId(Integer.parseInt(request.getParameter("id")));
				B2SResMsg_QuartzSchedule_DeleteJob res = scheduleTransportService.deleteJob(req);
				if("000000".equals(res.getResCode())){
					return ReturnDWZAjax.success("删除成功！");
				}else{
					return ReturnDWZAjax.fail(res.getResMsg());
				}
			}else if(ScheduleJobEnums.JOB_GROUP_BUSINESS.getCode().equals(request.getParameter("jobGroup"))){
				ReqMsg_MQuartzSchedule_DeleteJob bizReq = new ReqMsg_MQuartzSchedule_DeleteJob();
				bizReq.setJobId(Integer.parseInt(request.getParameter("id")));
				ResMsg_MQuartzSchedule_DeleteJob res = (ResMsg_MQuartzSchedule_DeleteJob)manageService.handleMsg(bizReq);
				if("000000".equals(res.getResCode())){
					return ReturnDWZAjax.success("删除成功！");
				}else{
					return ReturnDWZAjax.fail(res.getResMsg());
				}
			}else {
				return ReturnDWZAjax.fail("暂不支持此组别");
			}
		} catch (Exception e) {
			return ReturnDWZAjax.fail("删除失败，原因不详！");
		}
	}
}
