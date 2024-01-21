package com.pinting.schedule.out.service.impl;

import com.pinting.schedule.hessian.message.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.schedule.out.service.ScheduleTransportService;

@Service
public class ScheduleTransportServiceImpl implements ScheduleTransportService {

	@Autowired
	@Qualifier("bizScheduleService")
	private HessianService bizScheduleService;
	@Override
	public B2SResMsg_ProductPlanSchedule_Regist4AuthPass regist4AuthPass(
			B2SReqMsg_ProductPlanSchedule_Regist4AuthPass req) {
		B2SResMsg_ProductPlanSchedule_Regist4AuthPass res = (B2SResMsg_ProductPlanSchedule_Regist4AuthPass) bizScheduleService.handleMsg(req);
		return res;
	}

	@Override
	public B2SResMsg_ProductPlanSchedule_Regist4Publish regist4Publish(
			B2SReqMsg_ProductPlanSchedule_Regist4Publish req) {
		B2SResMsg_ProductPlanSchedule_Regist4Publish res = (B2SResMsg_ProductPlanSchedule_Regist4Publish) bizScheduleService.handleMsg(req);
		return res;
	}

	@Override
	public B2SResMsg_ProductPlanSchedule_RegistDelete registDelete(
			B2SReqMsg_ProductPlanSchedule_RegistDelete req) {
		B2SResMsg_ProductPlanSchedule_RegistDelete res = (B2SResMsg_ProductPlanSchedule_RegistDelete) bizScheduleService.handleMsg(req);
		return res;
	}

	@Override
	public B2SResMsg_QuartzSchedule_QueryAllJob queryAllJob(B2SReqMsg_QuartzSchedule_QueryAllJob req) {
		return (B2SResMsg_QuartzSchedule_QueryAllJob) bizScheduleService.handleMsg(req);
	}

	@Override
	public B2SResMsg_QuartzSchedule_QueryRunningJob queryRunningJob(B2SReqMsg_QuartzSchedule_QueryRunningJob req) {
		return (B2SResMsg_QuartzSchedule_QueryRunningJob) bizScheduleService.handleMsg(req);
	}

	@Override
	public B2SResMsg_QuartzSchedule_AddJob addJob(B2SReqMsg_QuartzSchedule_AddJob req) {
		return (B2SResMsg_QuartzSchedule_AddJob) bizScheduleService.handleMsg(req);
	}

	@Override
	public B2SResMsg_QuartzSchedule_PauseJob pauseJob(B2SReqMsg_QuartzSchedule_PauseJob req) {
		return (B2SResMsg_QuartzSchedule_PauseJob) bizScheduleService.handleMsg(req);
	}

	@Override
	public B2SResMsg_QuartzSchedule_ResumeJob resumeJob(B2SReqMsg_QuartzSchedule_ResumeJob req) {
		return (B2SResMsg_QuartzSchedule_ResumeJob) bizScheduleService.handleMsg(req);
	}

	@Override
	public B2SResMsg_QuartzSchedule_DeleteJob deleteJob(B2SReqMsg_QuartzSchedule_DeleteJob req) {
		return (B2SResMsg_QuartzSchedule_DeleteJob) bizScheduleService.handleMsg(req);
	}

	@Override
	public B2SResMsg_QuartzSchedule_RunAJobNow runAJobNow(B2SReqMsg_QuartzSchedule_RunAJobNow req) {
		return (B2SResMsg_QuartzSchedule_RunAJobNow) bizScheduleService.handleMsg(req);
	}

	@Override
	public B2SResMsg_QuartzSchedule_UpdateJobCron updateJobCron(B2SReqMsg_QuartzSchedule_UpdateJobCron req) {
		return (B2SResMsg_QuartzSchedule_UpdateJobCron) bizScheduleService.handleMsg(req);
	}

}
