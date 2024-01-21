package com.pinting.mall.dao;

import com.pinting.mall.model.MallPointsIncome;
import com.pinting.mall.model.MallPointsIncomeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MallPointsIncomeMapper {
    int countByExample(MallPointsIncomeExample example);

    int deleteByExample(MallPointsIncomeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MallPointsIncome record);

    int insertSelective(MallPointsIncome record);

    List<MallPointsIncome> selectByExample(MallPointsIncomeExample example);

    MallPointsIncome selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallPointsIncome record, @Param("example") MallPointsIncomeExample example);

    int updateByExample(@Param("record") MallPointsIncome record, @Param("example") MallPointsIncomeExample example);

    int updateByPrimaryKeySelective(MallPointsIncome record);

    int updateByPrimaryKey(MallPointsIncome record);
    
    MallPointsIncome selectPointsIncomeByIdForLock(@Param("pIncomeId") int pIncomeId);
}