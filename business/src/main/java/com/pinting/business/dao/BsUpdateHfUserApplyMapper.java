package com.pinting.business.dao;

import com.pinting.business.model.BsUpdateHfUserApply;
import com.pinting.business.model.BsUpdateHfUserApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUpdateHfUserApplyMapper {
    int countByExample(BsUpdateHfUserApplyExample example);

    int deleteByExample(BsUpdateHfUserApplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUpdateHfUserApply record);

    int insertSelective(BsUpdateHfUserApply record);

    List<BsUpdateHfUserApply> selectByExample(BsUpdateHfUserApplyExample example);

    BsUpdateHfUserApply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUpdateHfUserApply record, @Param("example") BsUpdateHfUserApplyExample example);

    int updateByExample(@Param("record") BsUpdateHfUserApply record, @Param("example") BsUpdateHfUserApplyExample example);

    int updateByPrimaryKeySelective(BsUpdateHfUserApply record);

    int updateByPrimaryKey(BsUpdateHfUserApply record);
}