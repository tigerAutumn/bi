package com.pinting.business.dao;

import com.pinting.business.model.MUser;
import com.pinting.business.model.MUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MUserMapper {
    int countByExample(MUserExample example);

    int deleteByExample(MUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MUser record);

    int insertSelective(MUser record);

    List<MUser> selectByExample(MUserExample example);
    List<MUser> selectMUserByExample(MUser mUser);
    List<MUser> selectMUserListByExample(MUser mUser);
    MUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MUser record, @Param("example") MUserExample example);

    int updateByExample(@Param("record") MUser record, @Param("example") MUserExample example);

    int updateByPrimaryKeySelective(MUser record);

    int updateByPrimaryKey(MUser record);
    
    //红包数据统计———申请人列表
    List<MUser> selectApplicantListStatistics();
    
    int updateAgentIdByPrimaryKey(@Param("id") Integer id, @Param("agentId") Integer agentId);
    
}