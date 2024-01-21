package com.pinting.business.dao;

import com.pinting.business.model.LnBad;
import com.pinting.business.model.LnBadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnBadMapper {
    long countByExample(LnBadExample example);

    int deleteByExample(LnBadExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnBad record);

    int insertSelective(LnBad record);

    List<LnBad> selectByExample(LnBadExample example);

    LnBad selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnBad record, @Param("example") LnBadExample example);

    int updateByExample(@Param("record") LnBad record, @Param("example") LnBadExample example);

    int updateByPrimaryKeySelective(LnBad record);

    int updateByPrimaryKey(LnBad record);
}