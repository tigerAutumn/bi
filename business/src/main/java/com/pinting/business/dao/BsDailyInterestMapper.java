package com.pinting.business.dao;

import com.pinting.business.model.BsDailyInterest;
import com.pinting.business.model.BsDailyInterestExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BsDailyInterestMapper {
    int countByExample(BsDailyInterestExample example);

    int deleteByExample(BsDailyInterestExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsDailyInterest record);

    int insertSelective(BsDailyInterest record);

    List<BsDailyInterest> selectByExample(BsDailyInterestExample example);
    
    List<BsDailyInterest> selectByExamplePage(Map<String, Object> data);

    BsDailyInterest selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsDailyInterest record, @Param("example") BsDailyInterestExample example);

    int updateByExample(@Param("record") BsDailyInterest record, @Param("example") BsDailyInterestExample example);

    int updateByPrimaryKeySelective(BsDailyInterest record);

    int updateByPrimaryKey(BsDailyInterest record);

	Double findZanInvestTotalByUserId(@Param("userId") Integer userId);
}