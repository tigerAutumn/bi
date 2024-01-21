package com.pinting.business.dao;

import com.pinting.business.model.BsActivityJoinTeam;
import com.pinting.business.model.BsActivityJoinTeamExample;
import java.util.List;

import com.pinting.business.model.BsActivityTeam;
import org.apache.ibatis.annotations.Param;

public interface BsActivityJoinTeamMapper {
    int countByExample(BsActivityJoinTeamExample example);

    int deleteByExample(BsActivityJoinTeamExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsActivityJoinTeam record);

    int insertSelective(BsActivityJoinTeam record);

    List<BsActivityJoinTeam> selectByExample(BsActivityJoinTeamExample example);

    BsActivityJoinTeam selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsActivityJoinTeam record, @Param("example") BsActivityJoinTeamExample example);

    int updateByExample(@Param("record") BsActivityJoinTeam record, @Param("example") BsActivityJoinTeamExample example);

    int updateByPrimaryKeySelective(BsActivityJoinTeam record);

    int updateByPrimaryKey(BsActivityJoinTeam record);

}