package com.pinting.business.hessian.manage.message;

import com.pinting.business.model.BsScheduleConfig;
import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by babyshark on 2017/5/12.
 */
public class ReqMsg_MQuartzSchedule_AddJob extends ReqMsg {

    private static final long serialVersionUID = 8549181386708877224L;
    private BsScheduleConfig scheduleJob;

    public BsScheduleConfig getScheduleJob() {
        return scheduleJob;
    }

    public void setScheduleJob(BsScheduleConfig scheduleJob) {
        this.scheduleJob = scheduleJob;
    }
}
