package com.pinting.business.dayend.schedule.impl;

import com.pinting.business.dao.BsScheduleConfigMapper;
import com.pinting.business.dayend.schedule.QuartzJobFactory;
import com.pinting.business.dayend.schedule.QuartzJobFactoryDisallowConcurrent;
import com.pinting.business.dayend.schedule.QuartzOperationService;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.ScheduleJobEnums;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsScheduleConfig;
import com.pinting.business.model.BsScheduleConfigExample;
import com.pinting.core.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.pinting.business.dayend.schedule.QuartzInitListener.JOB_DATA_MAP_KEY;

/**
 * Created by babyshark on 2017/5/11.
 */
@Service
public class QuartzOperationServiceImpl implements QuartzOperationService{
    private Logger log = LoggerFactory.getLogger(QuartzOperationServiceImpl.class);
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    private BsScheduleConfigMapper bsScheduleConfigMapper;

    /***
     * 获取所有计划中的任务列表
     *
     * @return
     * @throws SchedulerException
     */
    @Override
    public List<BsScheduleConfig> queryAllJob(String jobGroup, String jobStatus){
        BsScheduleConfigExample scheduleConfigExample = new BsScheduleConfigExample();
        BsScheduleConfigExample.Criteria criteria = scheduleConfigExample.createCriteria();
        if(StringUtil.isNotEmpty(jobGroup)){
            criteria.andJobGroupEqualTo(jobGroup);
        }
        if(StringUtil.isNotEmpty(jobStatus)){
            criteria.andJobStatusEqualTo(jobStatus);
        }
        List<BsScheduleConfig> jobList = bsScheduleConfigMapper.selectByExample(scheduleConfigExample);
        return jobList;
    }

    /**
     * 所有正在运行的job
     *
     * @return
     * @throws SchedulerException
     */
    @Override
    public List<BsScheduleConfig> queryRunningJob() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.anyJobGroup());
        List<BsScheduleConfig> jobList = new ArrayList<>(jobKeys.size());
        if(!CollectionUtils.isEmpty(jobKeys)){
            for (JobKey jobKey : jobKeys) {
                BsScheduleConfig job = new BsScheduleConfig();
                //JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                TriggerKey triggerKey = TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup());
                Trigger trigger = scheduler.getTrigger(triggerKey);
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                BsScheduleConfigExample scheduleConfigExample = new BsScheduleConfigExample();
                scheduleConfigExample.createCriteria().andJobNameEqualTo(jobKey.getName()).andJobGroupEqualTo(jobKey.getGroup());
                List<BsScheduleConfig>  jobs = bsScheduleConfigMapper.selectByExample(scheduleConfigExample);
                job.setDescription(CollectionUtils.isEmpty(jobs)?
                        "Trigger.Key:" + trigger.getKey():jobs.get(0).getDescription());
                job.setId(CollectionUtils.isEmpty(jobs) ? null : jobs.get(0).getId());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
        }
        return jobList;
    }

    @Override
    public void addJob(BsScheduleConfig job) {
        if (job == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        BsScheduleConfigExample example = new BsScheduleConfigExample();
        example.createCriteria().andJobNameEqualTo(job.getJobName()).andJobGroupEqualTo(job.getJobGroup());
        List<BsScheduleConfig> bsScheduleConfigs = bsScheduleConfigMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(bsScheduleConfigs)){
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "此定时已存在");
        }
        if(scheduleJob(job)){
            job.setJobStatus(ScheduleJobEnums.JOB_STATUS_RUNNING.getCode());
            job.setCreateTime(new Date());
            job.setUpdateTime(new Date());
            bsScheduleConfigMapper.insertSelective(job);
        }else {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "加载Job失败，请检查");
        }
    }

    @Override
    public boolean scheduleJob(BsScheduleConfig job){
        if (job == null || !ScheduleJobEnums.JOB_STATUS_RUNNING.getCode().equals(job.getJobStatus())) {
            return false;
        }
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            String jobGroup = StringUtils.isBlank(job.getJobGroup()) ? Scheduler.DEFAULT_GROUP : job.getJobGroup();
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), jobGroup);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 不存在，创建一个
            if (null == trigger) {
                Class clazz = ScheduleJobEnums.JOB_CONCURRENT_YES.equals(job.getIsConcurrent()) ?
                        QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrent.class;
                JobDetail jobDetail = JobBuilder.newJob(clazz)
                        .withIdentity(job.getJobName(), jobGroup)
                        .build();
                jobDetail.getJobDataMap().put(JOB_DATA_MAP_KEY, job);
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(job.getJobName(), jobGroup)
                        .withSchedule(scheduleBuilder)
                        .build();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                // Trigger已存在，那么更新相应的定时设置
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                        .cronSchedule(job.getCronExpression());
                // 按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder()
                        .withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder)
                        .build();
                // 按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (Exception e){
            log.error("[Quartz add job] error:" + job.getJobName(), e);
            return false;
        }
        return true;
    }

    /**
     * 暂停一个job
     *
     * @param jobId
     */
    @Override
    public void pauseJob(Integer jobId) {
        BsScheduleConfig scheduleJob = bsScheduleConfigMapper.selectByPrimaryKey(jobId);
        if(scheduleJob == null){
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
        if(ScheduleJobEnums.JOB_STATUS_DELETE.getCode().equals(scheduleJob.getJobStatus())){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "此定时已删除");
        }
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            log.error("暂停jobId:"+jobId+"异常", e);
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR, e.getMessage());
        }
        BsScheduleConfig jobTemp = new BsScheduleConfig();
        jobTemp.setId(jobId);
        jobTemp.setJobStatus(ScheduleJobEnums.JOB_STATUS_STOP.getCode());
        jobTemp.setUpdateTime(new Date());
        bsScheduleConfigMapper.updateByPrimaryKeySelective(jobTemp);
    }

    /**
     * 恢复一个job
     *
     * @param jobId
     */
    @Override
    public void resumeJob(Integer jobId) {
        BsScheduleConfig scheduleJob = bsScheduleConfigMapper.selectByPrimaryKey(jobId);
        if(scheduleJob == null){
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
        if(!ScheduleJobEnums.JOB_STATUS_STOP.getCode().equals(scheduleJob.getJobStatus())){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "此定时不需恢复");
        }
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            String jobGroup = StringUtils.isBlank(scheduleJob.getJobGroup()) ? Scheduler.DEFAULT_GROUP : scheduleJob.getJobGroup();
            TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), jobGroup);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 不存在，创建一个
            if (null == trigger) {
                Class clazz = ScheduleJobEnums.JOB_CONCURRENT_YES.equals(scheduleJob.getIsConcurrent()) ?
                        QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrent.class;
                JobDetail jobDetail = JobBuilder.newJob(clazz)
                        .withIdentity(scheduleJob.getJobName(), jobGroup)
                        .build();
                jobDetail.getJobDataMap().put(JOB_DATA_MAP_KEY, scheduleJob);
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(scheduleJob.getJobName(), jobGroup)
                        .withSchedule(scheduleBuilder)
                        .build();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                //已存在则恢复job
                JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(),
                        scheduleJob.getJobGroup());
                scheduler.resumeJob(jobKey);
            }
        } catch (SchedulerException e) {
            log.error("恢复jobId:"+jobId+"异常", e);
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR, e.getMessage());
        }
        BsScheduleConfig jobTemp = new BsScheduleConfig();
        jobTemp.setId(jobId);
        jobTemp.setJobStatus(ScheduleJobEnums.JOB_STATUS_RUNNING.getCode());
        jobTemp.setUpdateTime(new Date());
        bsScheduleConfigMapper.updateByPrimaryKeySelective(jobTemp);

    }

    /**
     * 删除一个job
     *
     * @param jobId
     */
    @Override
    public void deleteJob(Integer jobId) {
        BsScheduleConfig scheduleJob = bsScheduleConfigMapper.selectByPrimaryKey(jobId);
        if(scheduleJob == null){
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
        if(ScheduleJobEnums.JOB_STATUS_DELETE.getCode().equals(scheduleJob.getJobStatus())){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "此定时已删除");
        }

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(),
                scheduleJob.getJobGroup());
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            log.error("删除jobId:"+jobId+"异常", e);
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR, e.getMessage());
        }
        BsScheduleConfig jobTemp = new BsScheduleConfig();
        jobTemp.setId(jobId);
        jobTemp.setJobStatus(ScheduleJobEnums.JOB_STATUS_DELETE.getCode());
        jobTemp.setUpdateTime(new Date());
        bsScheduleConfigMapper.updateByPrimaryKeySelective(jobTemp);

    }

    /**
     * 立即执行job
     *
     * @param jobId
     */
    @Override
    public void runAJobNow(Integer jobId) {
        BsScheduleConfig scheduleJob = bsScheduleConfigMapper.selectByPrimaryKey(jobId);
        if(scheduleJob == null){
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
        if(ScheduleJobEnums.JOB_STATUS_DELETE.getCode().equals(scheduleJob.getJobStatus())){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "此定时已删除");
        }
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(
                    scheduleJob.getJobName(), scheduleJob.getJobGroup());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (null == trigger) {
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "请先启用任务以创建触发器！");
            }
            JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(),
                    scheduleJob.getJobGroup());
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            log.error("执行jobId:"+jobId+"异常", e);
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR, e.getMessage());
        }
    }

    /**
     * 更新job时间表达式
     *
     * @param jobId
     * @param cronExpression
     */
    @Override
    public void updateJobCron(Integer jobId, String cronExpression) throws SchedulerException {
        BsScheduleConfig scheduleJob = bsScheduleConfigMapper.selectByPrimaryKey(jobId);
        if(scheduleJob == null){
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
        /*if(ScheduleJobEnums.JOB_STATUS_DELETE.getCode().equals(scheduleJob.getJobStatus())){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "此定时已删除");
        }*/
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        TriggerKey triggerKey = TriggerKey.triggerKey(
                scheduleJob.getJobName(), scheduleJob.getJobGroup());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        // 不存在，创建一个
        if (null == trigger) {
            Class clazz = ScheduleJobEnums.JOB_CONCURRENT_YES.equals(scheduleJob.getIsConcurrent()) ?
                    QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrent.class;
            JobDetail jobDetail = JobBuilder.newJob(clazz)
                    .withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup())
                    .build();
            jobDetail.getJobDataMap().put(JOB_DATA_MAP_KEY, scheduleJob);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup())
                    .withSchedule(scheduleBuilder)
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
                    .cronSchedule(cronExpression);
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder)
                    .build();
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }

        BsScheduleConfig jobTemp = new BsScheduleConfig();
        jobTemp.setId(jobId);
        jobTemp.setCronExpression(cronExpression);
        jobTemp.setJobStatus(ScheduleJobEnums.JOB_STATUS_RUNNING.getCode());
        jobTemp.setUpdateTime(new Date());
        bsScheduleConfigMapper.updateByPrimaryKeySelective(jobTemp);
    }

}
