package com.pinting.business.dao;

import com.pinting.business.model.BsDict;
import com.pinting.business.model.BsDictExample;
import com.pinting.business.model.BsDictKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsDictMapper {
    int countByExample(BsDictExample example);

    int deleteByExample(BsDictExample example);

    int deleteByPrimaryKey(BsDictKey key);

    int insert(BsDict record);

    int insertSelective(BsDict record);

    List<BsDict> selectByExample(BsDictExample example);

    BsDict selectByPrimaryKey(BsDictKey key);

    int updateByExampleSelective(@Param("record") BsDict record, @Param("example") BsDictExample example);

    int updateByExample(@Param("record") BsDict record, @Param("example") BsDictExample example);

    int updateByPrimaryKeySelective(BsDict record);

    int updateByPrimaryKey(BsDict record);
}