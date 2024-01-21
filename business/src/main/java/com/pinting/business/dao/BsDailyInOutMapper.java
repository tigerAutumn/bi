package com.pinting.business.dao;

import com.pinting.business.model.BsDailyInOut;
import com.pinting.business.model.BsDailyInOutExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsDailyInOutMapper {
    int countByExample(BsDailyInOutExample example);

    int deleteByExample(BsDailyInOutExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsDailyInOut record);

    int insertSelective(BsDailyInOut record);

    List<BsDailyInOut> selectByExample(BsDailyInOutExample example);

    BsDailyInOut selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsDailyInOut record, @Param("example") BsDailyInOutExample example);

    int updateByExample(@Param("record") BsDailyInOut record, @Param("example") BsDailyInOutExample example);

    int updateByPrimaryKeySelective(BsDailyInOut record);

    int updateByPrimaryKey(BsDailyInOut record);
}