package com.pinting.business.dao;

import com.pinting.business.model.BsUserMessageRead;
import com.pinting.business.model.BsUserMessageReadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserMessageReadMapper {
    int countByExample(BsUserMessageReadExample example);

    int deleteByExample(BsUserMessageReadExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserMessageRead record);

    int insertSelective(BsUserMessageRead record);

    List<BsUserMessageRead> selectByExample(BsUserMessageReadExample example);

    BsUserMessageRead selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserMessageRead record, @Param("example") BsUserMessageReadExample example);

    int updateByExample(@Param("record") BsUserMessageRead record, @Param("example") BsUserMessageReadExample example);

    int updateByPrimaryKeySelective(BsUserMessageRead record);

    int updateByPrimaryKey(BsUserMessageRead record);
}