package com.pinting.business.hessian.manage.message;

import com.pinting.business.model.BsScheduleConfig;
import com.pinting.core.hessian.msg.ResMsg;

import java.util.List;

/**
 * Created by babyshark on 2017/5/12.
 */
public class ResMsg_MQuartzSchedule_QueryRunningJob extends ResMsg {

    private static final long serialVersionUID = 4407398754482376260L;
    private List<BsScheduleConfig> jobs;

    public List<BsScheduleConfig> getJobs() {
        return jobs;
    }

    public void setJobs(List<BsScheduleConfig> jobs) {
        this.jobs = jobs;
    }
}
