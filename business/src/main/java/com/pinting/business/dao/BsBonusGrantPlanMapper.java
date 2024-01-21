package com.pinting.business.dao;

import com.pinting.business.model.BsBonusGrantPlan;
import com.pinting.business.model.BsBonusGrantPlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsBonusGrantPlanMapper {
    int countByExample(BsBonusGrantPlanExample example);

    int deleteByExample(BsBonusGrantPlanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsBonusGrantPlan record);

    int insertSelective(BsBonusGrantPlan record);

    List<BsBonusGrantPlan> selectByExample(BsBonusGrantPlanExample example);

    BsBonusGrantPlan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsBonusGrantPlan record, @Param("example") BsBonusGrantPlanExample example);

    int updateByExample(@Param("record") BsBonusGrantPlan record, @Param("example") BsBonusGrantPlanExample example);

    int updateByPrimaryKeySelective(BsBonusGrantPlan record);

    int updateByPrimaryKey(BsBonusGrantPlan record);
    
    Double sumBonusAmount(@Param("userId") Integer userId);
    
    /**
     * 查询当天定时发放且已到账的发放计划数据
     * @author bianyatian
     * @return
     */
    List<BsBonusGrantPlan> getNeedNoticeList();
    

}