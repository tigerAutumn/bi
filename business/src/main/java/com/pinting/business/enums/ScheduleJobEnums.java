package com.pinting.business.enums;

/**
 * Created by babyshark on 2017/5/11.
 */
public enum ScheduleJobEnums {

    JOB_GROUP_SCHEDULE("SCHEDULE","组别：SCHEDULE"),
    JOB_GROUP_BUSINESS("BUSINESS","组别：BUSINESS"),
    JOB_GROUP_MANAGE("MANAGE","组别：MANAGE"),

    JOB_STATUS_STOP("STOP","任务状态：停止"),
    JOB_STATUS_RUNNING("RUNNING","任务状态：启动"),
    JOB_STATUS_DELETE("DELETE","任务状态：删除"),

    JOB_CONCURRENT_YES("YES","允许并发"),
    JOB_CONCURRENT_NO("NO","不允许并发"),
    ;

    private String code;

    private String description;


    ScheduleJobEnums(String code, String description){
        this.setCode(code);
        this.setDescription(description);
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

}
