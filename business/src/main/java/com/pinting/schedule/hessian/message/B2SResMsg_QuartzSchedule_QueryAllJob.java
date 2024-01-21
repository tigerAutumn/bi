package com.pinting.schedule.hessian.message;

import com.pinting.business.model.BsScheduleConfig;
import com.pinting.core.hessian.msg.ResMsg;

import java.util.List;

/**
 * Created by babyshark on 2017/5/12.
 */
public class B2SResMsg_QuartzSchedule_QueryAllJob extends ResMsg {


    private static final long serialVersionUID = -4433783016307758063L;

    private List<BsScheduleConfig> jobs;

    public List<BsScheduleConfig> getJobs() {
        return jobs;
    }

    public void setJobs(List<BsScheduleConfig> jobs) {
        this.jobs = jobs;
    }
}
