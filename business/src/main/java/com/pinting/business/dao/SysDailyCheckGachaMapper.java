package com.pinting.business.dao;

import com.pinting.business.model.SysDailyCheckGacha;
import com.pinting.business.model.SysDailyCheckGachaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysDailyCheckGachaMapper {
    int countByExample(SysDailyCheckGachaExample example);

    int deleteByExample(SysDailyCheckGachaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysDailyCheckGacha record);

    int insertSelective(SysDailyCheckGacha record);

    List<SysDailyCheckGacha> selectByExample(SysDailyCheckGachaExample example);

    SysDailyCheckGacha selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysDailyCheckGacha record, @Param("example") SysDailyCheckGachaExample example);

    int updateByExample(@Param("record") SysDailyCheckGacha record, @Param("example") SysDailyCheckGachaExample example);

    int updateByPrimaryKeySelective(SysDailyCheckGacha record);

    int updateByPrimaryKey(SysDailyCheckGacha record);
}