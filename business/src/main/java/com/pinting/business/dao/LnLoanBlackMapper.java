package com.pinting.business.dao;

import com.pinting.business.model.LnLoanBlack;
import com.pinting.business.model.LnLoanBlackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnLoanBlackMapper {
    int countByExample(LnLoanBlackExample example);

    int deleteByExample(LnLoanBlackExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnLoanBlack record);

    int insertSelective(LnLoanBlack record);

    List<LnLoanBlack> selectByExample(LnLoanBlackExample example);

    LnLoanBlack selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnLoanBlack record, @Param("example") LnLoanBlackExample example);

    int updateByExample(@Param("record") LnLoanBlack record, @Param("example") LnLoanBlackExample example);

    int updateByPrimaryKeySelective(LnLoanBlack record);

    int updateByPrimaryKey(LnLoanBlack record);
}