package com.pinting.business.dao;

import com.pinting.business.model.BsActivity318;
import com.pinting.business.model.BsActivity318Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsActivity318Mapper {
    int countByExample(BsActivity318Example example);

    int deleteByExample(BsActivity318Example example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsActivity318 record);

    int insertSelective(BsActivity318 record);

    List<BsActivity318> selectByExample(BsActivity318Example example);

    BsActivity318 selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsActivity318 record, @Param("example") BsActivity318Example example);

    int updateByExample(@Param("record") BsActivity318 record, @Param("example") BsActivity318Example example);

    int updateByPrimaryKeySelective(BsActivity318 record);

    int updateByPrimaryKey(BsActivity318 record);
}