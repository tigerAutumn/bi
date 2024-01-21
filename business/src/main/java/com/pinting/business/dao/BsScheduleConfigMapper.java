package com.pinting.business.dao;

import com.pinting.business.model.BsScheduleConfig;
import com.pinting.business.model.BsScheduleConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsScheduleConfigMapper {
    long countByExample(BsScheduleConfigExample example);

    int deleteByExample(BsScheduleConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsScheduleConfig record);

    int insertSelective(BsScheduleConfig record);

    List<BsScheduleConfig> selectByExample(BsScheduleConfigExample example);

    BsScheduleConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsScheduleConfig record, @Param("example") BsScheduleConfigExample example);

    int updateByExample(@Param("record") BsScheduleConfig record, @Param("example") BsScheduleConfigExample example);

    int updateByPrimaryKeySelective(BsScheduleConfig record);

    int updateByPrimaryKey(BsScheduleConfig record);
}