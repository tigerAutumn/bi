package com.pinting.business.dao;

import com.pinting.business.model.BsUserSignSeal;
import com.pinting.business.model.BsUserSignSealExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserSignSealMapper {
    int countByExample(BsUserSignSealExample example);

    int deleteByExample(BsUserSignSealExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserSignSeal record);

    int insertSelective(BsUserSignSeal record);

    List<BsUserSignSeal> selectByExample(BsUserSignSealExample example);

    BsUserSignSeal selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserSignSeal record, @Param("example") BsUserSignSealExample example);

    int updateByExample(@Param("record") BsUserSignSeal record, @Param("example") BsUserSignSealExample example);

    int updateByPrimaryKeySelective(BsUserSignSeal record);

    int updateByPrimaryKey(BsUserSignSeal record);
}