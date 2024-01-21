package com.pinting.business.dayend.schedule;

import com.pinting.business.dao.BsScheduleConfigMapper;
import com.pinting.business.enums.ScheduleJobEnums;
import com.pinting.business.model.BsScheduleConfig;
import com.pinting.business.model.BsScheduleConfigExample;
import com.pinting.core.util.GlobEnvUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Spring初始化完毕后加载定时
 * Created by babyshark on 2017/5/11.
 */
@Component("quartzInitListener")
public class QuartzInitListener implements ApplicationListener<ContextRefreshedEvent>{
    private static Logger log = LoggerFactory.getLogger(QuartzInitListener.class);
    public final static String JOB_DATA_MAP_KEY = "scheduleJob";
    public final static String JOB_GROUP = GlobEnvUtil.get("quartz.group");
    @Autowired
    private BsScheduleConfigMapper bsScheduleConfigMapper;
    @Autowired
    private QuartzOperationService quartzOperationService;

    private void init(){
        log.info("[quartzInitListener] init start");
        //获得job列表
        BsScheduleConfigExample scheduleConfigExample = new BsScheduleConfigExample();
        scheduleConfigExample.createCriteria().andJobGroupEqualTo(JOB_GROUP)
                .andJobStatusEqualTo(ScheduleJobEnums.JOB_STATUS_RUNNING.getCode());
        List<BsScheduleConfig> scheduleJobs = bsScheduleConfigMapper.selectByExample(scheduleConfigExample);
        if(!CollectionUtils.isEmpty(scheduleJobs)){
            log.info("[quartzInitListener] init size:" + scheduleJobs.size());
            for (BsScheduleConfig job : scheduleJobs) {
                log.info("[quartzInitListener] init jobName:"+job.getJobName());
                quartzOperationService.scheduleJob(job);
            }
        }
        log.info("[quartzInitListener] init end");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent applicationEvent) {
        init();
    }
}
