package com.pinting.business.dao;

import com.pinting.business.model.BsLiquidationRecord;
import com.pinting.business.model.BsLiquidationRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsLiquidationRecordMapper {
    long countByExample(BsLiquidationRecordExample example);

    int deleteByExample(BsLiquidationRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsLiquidationRecord record);

    int insertSelective(BsLiquidationRecord record);

    List<BsLiquidationRecord> selectByExample(BsLiquidationRecordExample example);

    BsLiquidationRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsLiquidationRecord record, @Param("example") BsLiquidationRecordExample example);

    int updateByExample(@Param("record") BsLiquidationRecord record, @Param("example") BsLiquidationRecordExample example);

    int updateByPrimaryKeySelective(BsLiquidationRecord record);

    int updateByPrimaryKey(BsLiquidationRecord record);
}