package com.pinting.mall.dao;

import com.pinting.mall.model.MallUserSign;
import com.pinting.mall.model.MallUserSignExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MallUserSignMapper {
    int countByExample(MallUserSignExample example);

    int deleteByExample(MallUserSignExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MallUserSign record);

    int insertSelective(MallUserSign record);

    List<MallUserSign> selectByExample(MallUserSignExample example);

    MallUserSign selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallUserSign record, @Param("example") MallUserSignExample example);

    int updateByExample(@Param("record") MallUserSign record, @Param("example") MallUserSignExample example);

    int updateByPrimaryKeySelective(MallUserSign record);

    int updateByPrimaryKey(MallUserSign record);
}