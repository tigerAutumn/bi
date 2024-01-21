package com.pinting.business.facade.manage;

import com.pinting.business.dayend.schedule.QuartzOperationService;
import com.pinting.business.hessian.manage.message.*;
import com.pinting.business.model.BsScheduleConfig;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by babyshark on 2017/5/11.
 */
@Component("MQuartzSchedule")
public class MQuartzScheduleFacade {
    @Autowired
    private QuartzOperationService quartzOperationService;
    /**
     * 获取所有计划中的任务列表
     * @param req
     * @param res
     */
    public void queryAllJob(ReqMsg_MQuartzSchedule_QueryAllJob req,
                            ResMsg_MQuartzSchedule_QueryAllJob res){

        List<BsScheduleConfig> jobs = quartzOperationService.queryAllJob(req.getJobGroup(), req.getJobStatus());
        res.setJobs(jobs);
    }

    /**
     * 所有正在运行的job
     * @param req
     * @param res
     * @throws SchedulerException
     */
    public void queryRunningJob(ReqMsg_MQuartzSchedule_QueryRunningJob req,
                                ResMsg_MQuartzSchedule_QueryRunningJob res) throws SchedulerException{
        List<BsScheduleConfig> jobs = quartzOperationService.queryRunningJob();
        res.setJobs(jobs);
    }

    /**
     * 新增一个job
     * @param req
     * @param res
     */
    public void addJob(ReqMsg_MQuartzSchedule_AddJob req,
                       ResMsg_MQuartzSchedule_AddJob res){
        quartzOperationService.addJob(req.getScheduleJob());
    }

    /**
     * 暂停一个job
     * @param req
     * @param res
     */
    public void pauseJob(ReqMsg_MQuartzSchedule_PauseJob req,
                         ResMsg_MQuartzSchedule_PauseJob res){
        quartzOperationService.pauseJob(req.getJobId());
    }

    /**
     * 恢复一个job
     * @param req
     * @param res
     */
    public void resumeJob(ReqMsg_MQuartzSchedule_ResumeJob req,
                          ResMsg_MQuartzSchedule_ResumeJob res){
        quartzOperationService.resumeJob(req.getJobId());
    }

    /**
     * 删除一个job
     * @param req
     * @param res
     */
    public void deleteJob(ReqMsg_MQuartzSchedule_DeleteJob req,
                          ResMsg_MQuartzSchedule_DeleteJob res){
        quartzOperationService.deleteJob(req.getJobId());
    }

    /**
     * 立即执行job
     * @param req
     * @param res
     */
    public void runAJobNow(ReqMsg_MQuartzSchedule_RunAJobNow req,
                           ResMsg_MQuartzSchedule_RunAJobNow res){
        quartzOperationService.runAJobNow(req.getJobId());
    }

    /**
     * 更新job时间表达式
     * @param req
     * @param res
     * @throws SchedulerException
     */
    public void updateJobCron(ReqMsg_MQuartzSchedule_UpdateJobCron req,
                              ResMsg_MQuartzSchedule_UpdateJobCron res) throws SchedulerException{
        quartzOperationService.updateJobCron(req.getJobId(), req.getCronExpression());
    }
}
