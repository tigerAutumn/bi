package com.pinting.business.dayend.schedule;

import com.pinting.business.model.BsScheduleConfig;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * Created by babyshark on 2017/5/11.
 */
public interface QuartzOperationService {

    /**
     * 获取所有计划中的任务列表
     * @param jobGroup 可传空
     * @param jobStatus 可传空
     * @return
     */
    public List<BsScheduleConfig> queryAllJob(String jobGroup, String jobStatus);

    /**
     * 所有正在运行的job
     *
     * @return
     * @throws SchedulerException
     */

    public List<BsScheduleConfig> queryRunningJob() throws SchedulerException;

    /**
     * 新增一个job（加载一个job，并持久化操作）
     * @param scheduleJob
     */
    public void addJob(BsScheduleConfig scheduleJob);

    /**
     * 加载一个job，无持久化操作
     * @param job
     * @return
     */
    public boolean scheduleJob(BsScheduleConfig job);

    /**
     * 暂停一个job
     *
     * @param jobId
     */
    public void pauseJob(Integer jobId);

    /**
     * 恢复一个job
     *
     * @param jobId
     */

    public void resumeJob(Integer jobId);

    /**
     * 删除一个job
     *
     * @param jobId
     */
    public void deleteJob(Integer jobId);

    /**
     * 立即执行job
     *
     * @param jobId
     */

    public void runAJobNow(Integer jobId);

    /**
     * 更新job时间表达式
     *
     * @param jobId
     * @param cronExpression
     */
    public void updateJobCron(Integer jobId, String cronExpression) throws SchedulerException ;

}
