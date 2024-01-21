package com.pinting.schedule.hessian.message;

import com.pinting.business.model.BsScheduleConfig;
import com.pinting.core.hessian.msg.ResMsg;

import java.util.List;

/**
 * Created by babyshark on 2017/5/12.
 */
public class B2SResMsg_QuartzSchedule_QueryRunningJob extends ResMsg {

    private static final long serialVersionUID = 4407398754482376260L;
    private List<BsScheduleConfig> jobs;

    public List<BsScheduleConfig> getJobs() {
        return jobs;
    }

    public void setJobs(List<BsScheduleConfig> jobs) {
        this.jobs = jobs;
    }
}
