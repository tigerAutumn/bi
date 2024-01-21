package com.pinting.business.dao;

import com.pinting.business.model.BsUserSales;
import com.pinting.business.model.BsUserSalesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserSalesMapper {
    int countByExample(BsUserSalesExample example);

    int deleteByExample(BsUserSalesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserSales record);

    int insertSelective(BsUserSales record);

    List<BsUserSales> selectByExample(BsUserSalesExample example);

    BsUserSales selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserSales record, @Param("example") BsUserSalesExample example);

    int updateByExample(@Param("record") BsUserSales record, @Param("example") BsUserSalesExample example);

    int updateByPrimaryKeySelective(BsUserSales record);

    int updateByPrimaryKey(BsUserSales record);
    
    BsUserSales selectSales(BsUserSales record);
}