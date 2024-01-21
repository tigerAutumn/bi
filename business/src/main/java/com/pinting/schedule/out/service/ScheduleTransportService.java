package com.pinting.schedule.out.service;

import com.pinting.schedule.hessian.message.*;

public interface ScheduleTransportService {
	/**
	 * 理财审核通过，注册定时任务
	 * @param req
	 */
	public B2SResMsg_ProductPlanSchedule_Regist4AuthPass regist4AuthPass(B2SReqMsg_ProductPlanSchedule_Regist4AuthPass req);
	
	/**
	 * 手动发布理财，重置定时任务
	 * @param req
	 */
	public B2SResMsg_ProductPlanSchedule_Regist4Publish regist4Publish(B2SReqMsg_ProductPlanSchedule_Regist4Publish req);
	
	/**
	 * 删除理财定时任务
	 * @param req
	 */
	public B2SResMsg_ProductPlanSchedule_RegistDelete registDelete(B2SReqMsg_ProductPlanSchedule_RegistDelete req);

	/**
	 * 获取所有计划中的任务列表
	 * @param req
	 */
	public B2SResMsg_QuartzSchedule_QueryAllJob queryAllJob(B2SReqMsg_QuartzSchedule_QueryAllJob req);

	/**
	 * 所有正在运行的job
	 * @param req
	 */
	public B2SResMsg_QuartzSchedule_QueryRunningJob queryRunningJob(B2SReqMsg_QuartzSchedule_QueryRunningJob req);

	/**
	 * 新增一个job
	 * @param req
	 */
	public B2SResMsg_QuartzSchedule_AddJob addJob(B2SReqMsg_QuartzSchedule_AddJob req);

	/**
	 * 暂停一个job
	 * @param req
	 */
	public B2SResMsg_QuartzSchedule_PauseJob pauseJob(B2SReqMsg_QuartzSchedule_PauseJob req);

	/**
	 * 恢复一个job
	 * @param req
	 */
	public B2SResMsg_QuartzSchedule_ResumeJob resumeJob(B2SReqMsg_QuartzSchedule_ResumeJob req);

	/**
	 * 删除一个job
	 * @param req
	 */
	public B2SResMsg_QuartzSchedule_DeleteJob deleteJob(B2SReqMsg_QuartzSchedule_DeleteJob req);

	/**
	 * 立即执行job
	 * @param req
	 */
	public B2SResMsg_QuartzSchedule_RunAJobNow runAJobNow(B2SReqMsg_QuartzSchedule_RunAJobNow req);

	/**
	 * 更新job时间表达式
	 * @param req
	 */
	public B2SResMsg_QuartzSchedule_UpdateJobCron updateJobCron(B2SReqMsg_QuartzSchedule_UpdateJobCron req);
}
