package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;

/**
 * Author:      cyb
 * Date:        2017/6/7
 * Description: 活动基本数据响应信息
 */
public class ResMsg_Activity_BaseData extends ResMsg {

    private static final long serialVersionUID = -3962393749175227202L;

    /* yyyy-MM-dd HH:mm:ss */
    private String startTime;

    /* yyyy-MM-dd HH:mm:ss */
    private String endTime;

    /* 是否开始 */
    private String isStart;

    /* 扩展信息 */
    private HashMap<String, Object> extendInfo;

    public HashMap<String, Object> getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(HashMap<String, Object> extendInfo) {
        this.extendInfo = extendInfo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIsStart() {
        return isStart;
    }

    public void setIsStart(String isStart) {
        this.isStart = isStart;
    }
}
