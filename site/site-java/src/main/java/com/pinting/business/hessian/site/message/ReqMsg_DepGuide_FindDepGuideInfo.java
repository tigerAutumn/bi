package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/4/12
 * Description:
 */
public class ReqMsg_DepGuide_FindDepGuideInfo extends ReqMsg {

    private static final long serialVersionUID = -1262126429402064026L;

    private Integer userId;

    private Boolean containRisk;

    public Boolean getContainRisk() {
        return containRisk;
    }

    public void setContainRisk(Boolean containRisk) {
        this.containRisk = containRisk;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
