package com.pinting.business.dao;

import com.pinting.business.model.Bs19payCheck;
import com.pinting.business.model.Bs19payCheckExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Bs19payCheckMapper {
    int countByExample(Bs19payCheckExample example);

    int deleteByExample(Bs19payCheckExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Bs19payCheck record);

    int insertSelective(Bs19payCheck record);

    List<Bs19payCheck> selectByExample(Bs19payCheckExample example);

    Bs19payCheck selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Bs19payCheck record, @Param("example") Bs19payCheckExample example);

    int updateByExample(@Param("record") Bs19payCheck record, @Param("example") Bs19payCheckExample example);

    int updateByPrimaryKeySelective(Bs19payCheck record);

    int updateByPrimaryKey(Bs19payCheck record);
}