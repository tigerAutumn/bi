package com.pinting.business.dao;

import com.pinting.business.model.BsDafyCustomerFixRecord;
import com.pinting.business.model.BsDafyCustomerFixRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsDafyCustomerFixRecordMapper {
    int countByExample(BsDafyCustomerFixRecordExample example);

    int deleteByExample(BsDafyCustomerFixRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsDafyCustomerFixRecord record);

    int insertSelective(BsDafyCustomerFixRecord record);

    List<BsDafyCustomerFixRecord> selectByExample(BsDafyCustomerFixRecordExample example);

    BsDafyCustomerFixRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsDafyCustomerFixRecord record, @Param("example") BsDafyCustomerFixRecordExample example);

    int updateByExample(@Param("record") BsDafyCustomerFixRecord record, @Param("example") BsDafyCustomerFixRecordExample example);

    int updateByPrimaryKeySelective(BsDafyCustomerFixRecord record);

    int updateByPrimaryKey(BsDafyCustomerFixRecord record);
}