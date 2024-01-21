package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:      cyb
 * Date:        2017/2/28
 * Description:
 */
public class ResMsg_Activity_AnniversaryThirdWinnerList extends ResMsg {

    private static final long serialVersionUID = 5556412575036983681L;

    /* 五重礼获奖名单 */
    private Map<String, List<HashMap<String, Object>>> winnerList;

    public Map<String, List<HashMap<String, Object>>> getWinnerList() {
        return winnerList;
    }

    public void setWinnerList(Map<String, List<HashMap<String, Object>>> winnerList) {
        this.winnerList = winnerList;
    }
}
