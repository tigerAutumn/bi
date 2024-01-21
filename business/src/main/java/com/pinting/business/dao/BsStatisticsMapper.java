package com.pinting.business.dao;

import com.pinting.business.model.BsStatistics;
import com.pinting.business.model.BsStatisticsExample;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsStatisticsMapper {
    int countByExample(BsStatisticsExample example);

    int deleteByExample(BsStatisticsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsStatistics record);

    int insertSelective(BsStatistics record);

    List<BsStatistics> selectByExample(BsStatisticsExample example);

    BsStatistics selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsStatistics record, @Param("example") BsStatisticsExample example);

    int updateByExample(@Param("record") BsStatistics record, @Param("example") BsStatisticsExample example);

    int updateByPrimaryKeySelective(BsStatistics record);

    int updateByPrimaryKey(BsStatistics record);

	void insertValueList(ArrayList<BsStatistics> valueList);
}