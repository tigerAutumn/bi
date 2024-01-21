package com.pinting.business.dao;

import com.pinting.business.model.LnLoanWhite;
import com.pinting.business.model.LnLoanWhiteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnLoanWhiteMapper {
    long countByExample(LnLoanWhiteExample example);

    int deleteByExample(LnLoanWhiteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnLoanWhite record);

    int insertSelective(LnLoanWhite record);

    List<LnLoanWhite> selectByExample(LnLoanWhiteExample example);

    LnLoanWhite selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnLoanWhite record, @Param("example") LnLoanWhiteExample example);

    int updateByExample(@Param("record") LnLoanWhite record, @Param("example") LnLoanWhiteExample example);

    int updateByPrimaryKeySelective(LnLoanWhite record);

    int updateByPrimaryKey(LnLoanWhite record);
}