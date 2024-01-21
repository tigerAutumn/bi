package com.pinting.business.dao;

import com.pinting.business.model.BsHfbankBalanceCheckRecord;
import com.pinting.business.model.BsHfbankBalanceCheckRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsHfbankBalanceCheckRecordMapper {
    int countByExample(BsHfbankBalanceCheckRecordExample example);

    int deleteByExample(BsHfbankBalanceCheckRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsHfbankBalanceCheckRecord record);

    int insertSelective(BsHfbankBalanceCheckRecord record);

    List<BsHfbankBalanceCheckRecord> selectByExample(BsHfbankBalanceCheckRecordExample example);

    BsHfbankBalanceCheckRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsHfbankBalanceCheckRecord record, @Param("example") BsHfbankBalanceCheckRecordExample example);

    int updateByExample(@Param("record") BsHfbankBalanceCheckRecord record, @Param("example") BsHfbankBalanceCheckRecordExample example);

    int updateByPrimaryKeySelective(BsHfbankBalanceCheckRecord record);

    int updateByPrimaryKey(BsHfbankBalanceCheckRecord record);
}