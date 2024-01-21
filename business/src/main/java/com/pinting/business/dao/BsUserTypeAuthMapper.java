package com.pinting.business.dao;

import com.pinting.business.model.BsUserTypeAuth;
import com.pinting.business.model.BsUserTypeAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserTypeAuthMapper {
    int countByExample(BsUserTypeAuthExample example);

    int deleteByExample(BsUserTypeAuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserTypeAuth record);

    int insertSelective(BsUserTypeAuth record);

    List<BsUserTypeAuth> selectByExample(BsUserTypeAuthExample example);

    BsUserTypeAuth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserTypeAuth record, @Param("example") BsUserTypeAuthExample example);

    int updateByExample(@Param("record") BsUserTypeAuth record, @Param("example") BsUserTypeAuthExample example);

    int updateByPrimaryKeySelective(BsUserTypeAuth record);

    int updateByPrimaryKey(BsUserTypeAuth record);
}