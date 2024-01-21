package com.pinting.mall.dao;

import com.pinting.mall.model.MallBsSysConfig;
import com.pinting.mall.model.MallBsSysConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MallBsSysConfigMapper {
    int countByExample(MallBsSysConfigExample example);

    int deleteByExample(MallBsSysConfigExample example);

    int deleteByPrimaryKey(String confKey);

    int insert(MallBsSysConfig record);

    int insertSelective(MallBsSysConfig record);

    List<MallBsSysConfig> selectByExample(MallBsSysConfigExample example);

    MallBsSysConfig selectByPrimaryKey(String confKey);

    int updateByExampleSelective(@Param("record") MallBsSysConfig record, @Param("example") MallBsSysConfigExample example);

    int updateByExample(@Param("record") MallBsSysConfig record, @Param("example") MallBsSysConfigExample example);

    int updateByPrimaryKeySelective(MallBsSysConfig record);

    int updateByPrimaryKey(MallBsSysConfig record);
}