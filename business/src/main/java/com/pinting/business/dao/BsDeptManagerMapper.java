package com.pinting.business.dao;

import com.pinting.business.model.BsDeptManager;
import com.pinting.business.model.BsDeptManagerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsDeptManagerMapper {
    int countByExample(BsDeptManagerExample example);

    int deleteByExample(BsDeptManagerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsDeptManager record);

    int insertSelective(BsDeptManager record);

    List<BsDeptManager> selectByExample(BsDeptManagerExample example);

    BsDeptManager selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsDeptManager record, @Param("example") BsDeptManagerExample example);

    int updateByExample(@Param("record") BsDeptManager record, @Param("example") BsDeptManagerExample example);

    int updateByPrimaryKeySelective(BsDeptManager record);

    int updateByPrimaryKey(BsDeptManager record);
}