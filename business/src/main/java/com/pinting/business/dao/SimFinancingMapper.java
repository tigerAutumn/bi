package com.pinting.business.dao;

import com.pinting.business.model.SimFinancing;
import com.pinting.business.model.SimFinancingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SimFinancingMapper {
    int countByExample(SimFinancingExample example);

    int deleteByExample(SimFinancingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SimFinancing record);

    int insertSelective(SimFinancing record);

    List<SimFinancing> selectByExample(SimFinancingExample example);

    SimFinancing selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SimFinancing record, @Param("example") SimFinancingExample example);

    int updateByExample(@Param("record") SimFinancing record, @Param("example") SimFinancingExample example);

    int updateByPrimaryKeySelective(SimFinancing record);

    int updateByPrimaryKey(SimFinancing record);
}