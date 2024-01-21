package com.pinting.business.dao;

import com.pinting.business.model.DafyUserExt;
import com.pinting.business.model.DafyUserExtExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DafyUserExtMapper {
    int countByExample(DafyUserExtExample example);

    int deleteByExample(DafyUserExtExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DafyUserExt record);

    int insertSelective(DafyUserExt record);

    List<DafyUserExt> selectByExample(DafyUserExtExample example);

    DafyUserExt selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DafyUserExt record, @Param("example") DafyUserExtExample example);

    int updateByExample(@Param("record") DafyUserExt record, @Param("example") DafyUserExtExample example);

    int updateByPrimaryKeySelective(DafyUserExt record);

    int updateByPrimaryKey(DafyUserExt record);
}