package com.pinting.business.dayend.schedule;

import com.pinting.business.model.BsScheduleConfig;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 若一个方法一次执行不完下次轮转时则等待该方法执行完后才执行下一次操作
 * Created by babyshark on 2017/5/11.
 */
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrent implements Job{
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        BsScheduleConfig scheduleJob = (BsScheduleConfig) context.getMergedJobDataMap().get(QuartzInitListener.JOB_DATA_MAP_KEY);
        TaskUtils.invokMethod(scheduleJob);
    }
}
