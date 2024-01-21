package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by babyshark on 2017/5/12.
 */
public class ReqMsg_MQuartzSchedule_QueryAllJob extends ReqMsg {


    private static final long serialVersionUID = 268164842300780687L;

    private String jobGroup;
    private String jobStatus;

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }
}
