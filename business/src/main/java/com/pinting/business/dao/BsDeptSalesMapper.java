package com.pinting.business.dao;

import com.pinting.business.model.BsDeptSales;
import com.pinting.business.model.BsDeptSalesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsDeptSalesMapper {
    int countByExample(BsDeptSalesExample example);

    int deleteByExample(BsDeptSalesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsDeptSales record);

    int insertSelective(BsDeptSales record);

    List<BsDeptSales> selectByExample(BsDeptSalesExample example);

    BsDeptSales selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsDeptSales record, @Param("example") BsDeptSalesExample example);

    int updateByExample(@Param("record") BsDeptSales record, @Param("example") BsDeptSalesExample example);

    int updateByPrimaryKeySelective(BsDeptSales record);

    int updateByPrimaryKey(BsDeptSales record);
}