package com.pinting.business.dao;

import com.pinting.business.model.BsPCA;
import com.pinting.business.model.BsPCAExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsPCAMapper {
    int countByExample(BsPCAExample example);

    int deleteByExample(BsPCAExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsPCA record);

    int insertSelective(BsPCA record);

    List<BsPCA> selectByExample(BsPCAExample example);

    BsPCA selectByPrimaryKey(Integer id);

    List<BsPCA> selectProvinces();

    int updateByExampleSelective(@Param("record") BsPCA record, @Param("example") BsPCAExample example);

    int updateByExample(@Param("record") BsPCA record, @Param("example") BsPCAExample example);

    int updateByPrimaryKeySelective(BsPCA record);

    int updateByPrimaryKey(BsPCA record);
}