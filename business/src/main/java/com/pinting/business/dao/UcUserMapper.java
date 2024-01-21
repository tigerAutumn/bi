package com.pinting.business.dao;

import com.pinting.business.model.UcUser;
import com.pinting.business.model.UcUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcUserMapper {
    long countByExample(UcUserExample example);

    int deleteByExample(UcUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UcUser record);

    int insertSelective(UcUser record);

    List<UcUser> selectByExample(UcUserExample example);

    UcUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UcUser record, @Param("example") UcUserExample example);

    int updateByExample(@Param("record") UcUser record, @Param("example") UcUserExample example);

    int updateByPrimaryKeySelective(UcUser record);

    int updateByPrimaryKey(UcUser record);
}