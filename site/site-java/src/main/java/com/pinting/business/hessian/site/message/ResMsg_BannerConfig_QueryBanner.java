package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;

/**
 * Created by cyb on 2017/10/25.
 */
public class ResMsg_BannerConfig_QueryBanner extends ResMsg {

    private static final long serialVersionUID = -7274186264837121022L;

    private List<HashMap<String, Object>> result;

    public List<HashMap<String, Object>> getResult() {
        return result;
    }

    public void setResult(List<HashMap<String, Object>> result) {
        this.result = result;
    }
}
