package com.pinting.schedule.hessian.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by babyshark on 2017/5/12.
 */
public class B2SReqMsg_QuartzSchedule_ResumeJob extends ReqMsg {


    private static final long serialVersionUID = 5241475102815792477L;
    private Integer jobId;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }
}
