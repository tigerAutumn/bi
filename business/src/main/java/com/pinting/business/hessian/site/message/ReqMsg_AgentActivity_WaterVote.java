package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/4/5
 * Description:
 */
public class ReqMsg_AgentActivity_WaterVote extends ReqMsg {
    private static final long serialVersionUID = -3523552805458314209L;

    private Integer voteUserId;
    private Integer signUpId;

    public Integer getVoteUserId() {
        return voteUserId;
    }

    public void setVoteUserId(Integer voteUserId) {
        this.voteUserId = voteUserId;
    }

    public Integer getSignUpId() {
        return signUpId;
    }

    public void setSignUpId(Integer signUpId) {
        this.signUpId = signUpId;
    }
}
