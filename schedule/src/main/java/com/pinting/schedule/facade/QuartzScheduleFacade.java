package com.pinting.schedule.facade;

import com.pinting.business.dayend.schedule.QuartzOperationService;
import com.pinting.business.model.BsScheduleConfig;
import com.pinting.schedule.hessian.message.*;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by babyshark on 2017/5/11.
 */
@Component("QuartzSchedule")
public class QuartzScheduleFacade {
    @Autowired
    private QuartzOperationService quartzOperationService;
    /**
     * 获取所有计划中的任务列表
     * @param req
     * @param res
     */
    public void queryAllJob(B2SReqMsg_QuartzSchedule_QueryAllJob req,
                                              B2SResMsg_QuartzSchedule_QueryAllJob res){

        List<BsScheduleConfig> jobs = quartzOperationService.queryAllJob(req.getJobGroup(), req.getJobStatus());
        res.setJobs(jobs);
    }

    /**
     * 所有正在运行的job
     * @param req
     * @param res
     * @throws SchedulerException
     */
    public void queryRunningJob(B2SReqMsg_QuartzSchedule_QueryRunningJob req,
                                                  B2SResMsg_QuartzSchedule_QueryRunningJob res) throws SchedulerException{
        List<BsScheduleConfig> jobs = quartzOperationService.queryRunningJob();
        res.setJobs(jobs);
    }

    /**
     * 新增一个job
     * @param req
     * @param res
     */
    public void addJob(B2SReqMsg_QuartzSchedule_AddJob req,
                       B2SResMsg_QuartzSchedule_AddJob res){
        quartzOperationService.addJob(req.getScheduleJob());
    }

    /**
     * 暂停一个job
     * @param req
     * @param res
     */
    public void pauseJob(B2SReqMsg_QuartzSchedule_PauseJob req,
                         B2SResMsg_QuartzSchedule_PauseJob res){
        quartzOperationService.pauseJob(req.getJobId());
    }

    /**
     * 恢复一个job
     * @param req
     * @param res
     */
    public void resumeJob(B2SReqMsg_QuartzSchedule_ResumeJob req,
                          B2SResMsg_QuartzSchedule_ResumeJob res){
        quartzOperationService.resumeJob(req.getJobId());
    }

    /**
     * 删除一个job
     * @param req
     * @param res
     */
    public void deleteJob(B2SReqMsg_QuartzSchedule_DeleteJob req,
                          B2SResMsg_QuartzSchedule_DeleteJob res){
        quartzOperationService.deleteJob(req.getJobId());
    }

    /**
     * 立即执行job
     * @param req
     * @param res
     */
    public void runAJobNow(B2SReqMsg_QuartzSchedule_RunAJobNow req,
                           B2SResMsg_QuartzSchedule_RunAJobNow res){
        quartzOperationService.runAJobNow(req.getJobId());
    }

    /**
     * 更新job时间表达式
     * @param req
     * @param res
     * @throws SchedulerException
     */
    public void updateJobCron(B2SReqMsg_QuartzSchedule_UpdateJobCron req,
                              B2SResMsg_QuartzSchedule_UpdateJobCron res) throws SchedulerException{
        quartzOperationService.updateJobCron(req.getJobId(), req.getCronExpression());
    }
}
