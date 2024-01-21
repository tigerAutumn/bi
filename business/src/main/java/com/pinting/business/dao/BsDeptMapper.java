package com.pinting.business.dao;

import com.pinting.business.model.BsDept;
import com.pinting.business.model.BsDeptExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsDeptMapper {
    int countByExample(BsDeptExample example);

    int deleteByExample(BsDeptExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsDept record);

    int insertSelective(BsDept record);

    List<BsDept> selectByExample(BsDeptExample example);

    BsDept selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsDept record, @Param("example") BsDeptExample example);

    int updateByExample(@Param("record") BsDept record, @Param("example") BsDeptExample example);

    int updateByPrimaryKeySelective(BsDept record);

    int updateByPrimaryKey(BsDept record);
    
    List<BsDept> selectDeptNameOfSales(BsDept record);
}