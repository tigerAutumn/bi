package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cyb on 2017/12/12.
 */
public class ResMsg_Activity_YearEndQueryList extends ResMsg {

    private static final long serialVersionUID = -8399662392950400347L;
    /* 活动是否开始 */
    private String isStart;

    private String startTime;

    private String endTime;

    private Map<String, Integer> drawnTimes;

    private List<HashMap<String, Object>> drawnList;

    private List<HashMap<String, Object>> list;

    public List<HashMap<String, Object>> getDrawnList() {
        return drawnList;
    }

    public void setDrawnList(List<HashMap<String, Object>> drawnList) {
        this.drawnList = drawnList;
    }

    public String getIsStart() {
        return isStart;
    }

    public void setIsStart(String isStart) {
        this.isStart = isStart;
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

    public Map<String, Integer> getDrawnTimes() {
        return drawnTimes;
    }

    public void setDrawnTimes(Map<String, Integer> drawnTimes) {
        this.drawnTimes = drawnTimes;
    }

    public List<HashMap<String, Object>> getList() {
        return list;
    }

    public void setList(List<HashMap<String, Object>> list) {
        this.list = list;
    }
}
