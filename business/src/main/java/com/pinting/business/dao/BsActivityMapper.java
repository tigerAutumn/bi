package com.pinting.business.dao;

import com.pinting.business.model.BsActivity;
import com.pinting.business.model.BsActivityExample;
import java.util.List;

import com.pinting.business.model.vo.ActivityYouFuRankInfoVO;
import com.pinting.business.model.vo.ActivityYouFuSelfInfoVO;
import org.apache.ibatis.annotations.Param;

public interface BsActivityMapper {
    int countByExample(BsActivityExample example);

    int deleteByExample(BsActivityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsActivity record);

    int insertSelective(BsActivity record);

    List<BsActivity> selectByExample(BsActivityExample example);

    BsActivity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsActivity record, @Param("example") BsActivityExample example);

    int updateByExample(@Param("record") BsActivity record, @Param("example") BsActivityExample example);

    int updateByPrimaryKeySelective(BsActivity record);

    int updateByPrimaryKey(BsActivity record);

    /**
     * 【友富同享邀请活动】查询金牌排行榜列表（前六）
     * @return
     */
    List<ActivityYouFuRankInfoVO> selectYouFuRankList();

    /**
     * 【友富同享邀请活动】我的战绩
     * @param userId
     * @return
     */
    ActivityYouFuSelfInfoVO selectYouFuSelfInfo(@Param("userId") Integer userId);

    /**
     * 【感恩节活动】活动期间投资金额
     * @param userId    用户ID
     * @param term      期限
     * @return
     */
    Double sumThanksGiving(@Param("userId") Integer userId, @Param("term") Integer term);
}