package com.pinting.business.facade.site;

import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.BsWaterConservationSignUp;
import com.pinting.business.model.vo.ActivityBaseVO;
import com.pinting.business.model.vo.ActivityWaterSignUpVO;
import com.pinting.business.model.vo.WaterVotePageInfoVO;
import com.pinting.business.service.site.ActivityService;
import com.pinting.business.service.site.AgentActivityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author:      cyb
 * Date:        2017/3/23
 * Description: 各其他渠道活动
 */
@Component("AgentActivity")
public class AgentActivityFacade {

    @Autowired
    private AgentActivityService agentActivityService;
    @Autowired
    private ActivityService activityService;

    /**
     * 报名页信息
     * @return
     */
    public void signUpPageInfo(ReqMsg_AgentActivity_SignUpPageInfo req, ResMsg_AgentActivity_SignUpPageInfo res) {
        ActivityBaseVO vo = agentActivityService.signUpPageInfo(req.getUserId());
        BeanUtils.copyProperties(vo, res);
    }

    /**
     * 报名
     * @param req
     * @return
     */
    public void waterSignUp(ReqMsg_AgentActivity_WaterSignUp req, ResMsg_AgentActivity_WaterSignUp res) {
        ActivityWaterSignUpVO vo = new ActivityWaterSignUpVO();
        BeanUtils.copyProperties(req, vo);
        ActivityBaseVO result = agentActivityService.waterSignUp(vo);
        BeanUtils.copyProperties(result, res);
    }

    /**
     * 投票
     * @param req
     * @param res
     */
    public void waterVote(ReqMsg_AgentActivity_WaterVote req, ResMsg_AgentActivity_WaterVote res) {
        ActivityBaseVO result = agentActivityService.waterVote(req.getVoteUserId(), req.getSignUpId());
        BeanUtils.copyProperties(result, res);
    }

    /**
     * 投票页面
     * @param req
     * @param res
     */
    public void waterVotePageInfo(ReqMsg_AgentActivity_WaterVotePageInfo req, ResMsg_AgentActivity_WaterVotePageInfo res) {
        WaterVotePageInfoVO result = agentActivityService.waterVotePageInfo(req.getUserId(), req.getSignUpNo(), req.getPageNum(), req.getNumPerPage());
        BeanUtils.copyProperties(result, res);
        res.setList(com.pinting.business.util.BeanUtils.classToArrayList(result.getList()));
    }
}
