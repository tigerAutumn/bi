package com.pinting.business.dao;

import com.pinting.business.model.BsSysStatus;
import com.pinting.business.model.BsSysStatusExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsSysStatusMapper {
    long countByExample(BsSysStatusExample example);

    int deleteByExample(BsSysStatusExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsSysStatus record);

    int insertSelective(BsSysStatus record);

    List<BsSysStatus> selectByExample(BsSysStatusExample example);

    BsSysStatus selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsSysStatus record, @Param("example") BsSysStatusExample example);

    int updateByExample(@Param("record") BsSysStatus record, @Param("example") BsSysStatusExample example);

    int updateByPrimaryKeySelective(BsSysStatus record);

    int updateByPrimaryKey(BsSysStatus record);
    
    List<BsSysStatus> findBsStatisticsList(@Param("start")Integer start, @Param("numPerPage")Integer numPerPage);
    
    
}