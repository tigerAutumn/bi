package com.pinting.business.dao;

import com.pinting.business.model.LnDailyAmount;
import com.pinting.business.model.LnDailyAmountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnDailyAmountMapper {
    int countByExample(LnDailyAmountExample example);

    int deleteByExample(LnDailyAmountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnDailyAmount record);

    int insertSelective(LnDailyAmount record);

    List<LnDailyAmount> selectByExample(LnDailyAmountExample example);

    LnDailyAmount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnDailyAmount record, @Param("example") LnDailyAmountExample example);

    int updateByExample(@Param("record") LnDailyAmount record, @Param("example") LnDailyAmountExample example);

    int updateByPrimaryKeySelective(LnDailyAmount record);

    int updateByPrimaryKey(LnDailyAmount record);

    LnDailyAmount selectLnDailyAmountForIdLock(Integer id);
}