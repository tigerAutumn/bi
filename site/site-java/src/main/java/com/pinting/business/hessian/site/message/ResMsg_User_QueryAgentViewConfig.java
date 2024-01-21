package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2016/10/9
 * Description:
 */
public class ResMsg_User_QueryAgentViewConfig extends ResMsg {

    private static final long serialVersionUID = 8040109186701916575L;

    private List<HashMap<String, Object>> configList;

    public List<HashMap<String, Object>> getConfigList() {
        return configList;
    }

    public void setConfigList(List<HashMap<String, Object>> configList) {
        this.configList = configList;
    }
}
