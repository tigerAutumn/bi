package com.pinting.business.dao;

import com.pinting.business.model.BsUserBalanceDailyRecord;
import com.pinting.business.model.BsUserBalanceDailyRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserBalanceDailyRecordMapper {
    int countByExample(BsUserBalanceDailyRecordExample example);

    int deleteByExample(BsUserBalanceDailyRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserBalanceDailyRecord record);

    int insertSelective(BsUserBalanceDailyRecord record);

    List<BsUserBalanceDailyRecord> selectByExample(BsUserBalanceDailyRecordExample example);

    BsUserBalanceDailyRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserBalanceDailyRecord record, @Param("example") BsUserBalanceDailyRecordExample example);

    int updateByExample(@Param("record") BsUserBalanceDailyRecord record, @Param("example") BsUserBalanceDailyRecordExample example);

    int updateByPrimaryKeySelective(BsUserBalanceDailyRecord record);

    int updateByPrimaryKey(BsUserBalanceDailyRecord record);
}