package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;

/**
 * Created by cyb on 2017/12/12.
 */
public class ResMsg_Activity_ChrismasEveLuckyDraw extends ResMsg {

    private static final long serialVersionUID = 2356063458477974384L;

    private List<HashMap<String, Object>> list;

    private Integer leftTimes;

    public Integer getLeftTimes() {
        return leftTimes;
    }

    public void setLeftTimes(Integer leftTimes) {
        this.leftTimes = leftTimes;
    }

    public List<HashMap<String, Object>> getList() {
        return list;
    }

    public void setList(List<HashMap<String, Object>> list) {
        this.list = list;
    }
}
