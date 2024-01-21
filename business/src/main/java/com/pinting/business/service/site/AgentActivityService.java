package com.pinting.business.service.site;

import com.pinting.business.model.BsWaterConservationSignUp;
import com.pinting.business.model.vo.ActivityBaseVO;
import com.pinting.business.model.vo.ActivityWaterSignUpVO;
import com.pinting.business.model.vo.WaterVotePageInfoVO;

import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/3/22
 * Description:
 */
public interface AgentActivityService {

    /**
     * 报名页信息
     * @param userId
     * @return
     */
    ActivityBaseVO signUpPageInfo(Integer userId);

    /**
     * 报名
     * @param req
     * @return
     */
    ActivityBaseVO waterSignUp(ActivityWaterSignUpVO req);

    /**
     * 查询报名列表
     * @return
     */
    List<BsWaterConservationSignUp> queryWaterSignUpList(ActivityWaterSignUpVO req);

    /**
     * 统计报名个数
     * @param req
     * @return
     */
    int countWaterSignUpList(ActivityWaterSignUpVO req);

    /**
     * 审核
     * @param req
     */
    void checkWater(BsWaterConservationSignUp req);

    /**
     * 投票
     * @param voteUserId    投票用户ID
     * @param signUpId      被投票报名信息ID
     * @return
     */
    ActivityBaseVO waterVote(Integer voteUserId, Integer signUpId);

    /**
     * 节水活动投票页面信息
     * @param userId        投票用户ID
     * @param signUpNo      被投票人的编号
     * @param pageNum       当前页码
     * @param numPerPage    每页显示条数
     * @return
     */
    WaterVotePageInfoVO waterVotePageInfo(Integer userId, Integer signUpNo, Integer pageNum, Integer numPerPage);
}
