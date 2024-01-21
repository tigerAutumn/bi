package com.pinting.business.dao;

import com.pinting.business.model.BsActivityTeam;
import com.pinting.business.model.BsActivityTeamExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsActivityTeamMapper {
    int countByExample(BsActivityTeamExample example);

    int deleteByExample(BsActivityTeamExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsActivityTeam record);

    int insertSelective(BsActivityTeam record);

    List<BsActivityTeam> selectByExample(BsActivityTeamExample example);

    BsActivityTeam selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsActivityTeam record, @Param("example") BsActivityTeamExample example);

    int updateByExample(@Param("record") BsActivityTeam record, @Param("example") BsActivityTeamExample example);

    int updateByPrimaryKeySelective(BsActivityTeam record);

    int updateByPrimaryKey(BsActivityTeam record);

    List<BsActivityTeam> selectLatestTeamNumber();
}