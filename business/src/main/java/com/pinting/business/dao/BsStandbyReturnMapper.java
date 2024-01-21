package com.pinting.business.dao;

import com.pinting.business.model.BsStandbyReturn;
import com.pinting.business.model.BsStandbyReturnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsStandbyReturnMapper {
    int countByExample(BsStandbyReturnExample example);

    int deleteByExample(BsStandbyReturnExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsStandbyReturn record);

    int insertSelective(BsStandbyReturn record);

    List<BsStandbyReturn> selectByExample(BsStandbyReturnExample example);

    BsStandbyReturn selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsStandbyReturn record, @Param("example") BsStandbyReturnExample example);

    int updateByExample(@Param("record") BsStandbyReturn record, @Param("example") BsStandbyReturnExample example);

    int updateByPrimaryKeySelective(BsStandbyReturn record);

    int updateByPrimaryKey(BsStandbyReturn record);
}