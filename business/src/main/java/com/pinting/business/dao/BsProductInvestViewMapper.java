package com.pinting.business.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsProductInvestView;
import com.pinting.business.model.BsProductInvestViewExample;

public interface BsProductInvestViewMapper {
    int countByExample(BsProductInvestViewExample example);

    int deleteByExample(BsProductInvestViewExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsProductInvestView record);

    int insertSelective(BsProductInvestView record);

    List<BsProductInvestView> selectByExample(BsProductInvestViewExample example);

    BsProductInvestView selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsProductInvestView record, @Param("example") BsProductInvestViewExample example);

    int updateByExample(@Param("record") BsProductInvestView record, @Param("example") BsProductInvestViewExample example);

    int updateByPrimaryKeySelective(BsProductInvestView record);

    int updateByPrimaryKey(BsProductInvestView record);
    
    BsProductInvestView selectTodayProductInvest(@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("interestBeginDate")String interestBeginDate);
    
    BsProductInvestView selectTotalProductInvest(@Param("interestBeginDate")String interestBeginDate);
}