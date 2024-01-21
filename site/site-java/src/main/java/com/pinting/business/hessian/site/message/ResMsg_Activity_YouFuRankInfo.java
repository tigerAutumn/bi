package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;

/**
 * Created by cyb on 2017/10/17.
 */
public class ResMsg_Activity_YouFuRankInfo extends ResMsg {

    private static final long serialVersionUID = 655379822218096643L;

    private List<HashMap<String, Object>> rankList;

    public List<HashMap<String, Object>> getRankList() {
        return rankList;
    }

    public void setRankList(List<HashMap<String, Object>> rankList) {
        this.rankList = rankList;
    }
}
