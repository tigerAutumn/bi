package com.pinting.business.dao;

import com.pinting.business.model.LnLoanApplyRecord;
import com.pinting.business.model.LnLoanApplyRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnLoanApplyRecordMapper {
    int countByExample(LnLoanApplyRecordExample example);

    int deleteByExample(LnLoanApplyRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnLoanApplyRecord record);

    int insertSelective(LnLoanApplyRecord record);

    List<LnLoanApplyRecord> selectByExample(LnLoanApplyRecordExample example);

    LnLoanApplyRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnLoanApplyRecord record, @Param("example") LnLoanApplyRecordExample example);

    int updateByExample(@Param("record") LnLoanApplyRecord record, @Param("example") LnLoanApplyRecordExample example);

    int updateByPrimaryKeySelective(LnLoanApplyRecord record);

    int updateByPrimaryKey(LnLoanApplyRecord record);
}