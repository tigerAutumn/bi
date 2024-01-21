package com.pinting.business.dao;

import com.pinting.business.model.BizMallPointsIncome;
import com.pinting.business.model.BizMallPointsIncomeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BizMallPointsIncomeMapper {
    int countByExample(BizMallPointsIncomeExample example);

    int deleteByExample(BizMallPointsIncomeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BizMallPointsIncome record);

    int insertSelective(BizMallPointsIncome record);

    List<BizMallPointsIncome> selectByExample(BizMallPointsIncomeExample example);

    BizMallPointsIncome selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BizMallPointsIncome record, @Param("example") BizMallPointsIncomeExample example);

    int updateByExample(@Param("record") BizMallPointsIncome record, @Param("example") BizMallPointsIncomeExample example);

    int updateByPrimaryKeySelective(BizMallPointsIncome record);

    int updateByPrimaryKey(BizMallPointsIncome record);
}