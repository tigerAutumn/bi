package com.pinting.business.dayend.schedule;

import com.pinting.business.model.BsScheduleConfig;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 无状态执行任务 （可并发）
 * Created by babyshark on 2017/5/11.
 */
public class QuartzJobFactory implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        BsScheduleConfig scheduleJob = (BsScheduleConfig) context.getMergedJobDataMap().get(QuartzInitListener.JOB_DATA_MAP_KEY);
        TaskUtils.invokMethod(scheduleJob);
    }
}
