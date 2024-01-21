package com.pinting.business.dao;

import com.pinting.business.model.BsAuth;
import com.pinting.business.model.BsAuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsAuthMapper {
    int countByExample(BsAuthExample example);

    int deleteByExample(BsAuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsAuth record);

    int insertSelective(BsAuth record);

    List<BsAuth> selectByExample(BsAuthExample example);

    BsAuth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsAuth record, @Param("example") BsAuthExample example);

    int updateByExample(@Param("record") BsAuth record, @Param("example") BsAuthExample example);

    int updateByPrimaryKeySelective(BsAuth record);

    int updateByPrimaryKey(BsAuth record);
}