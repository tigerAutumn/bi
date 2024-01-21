package com.pinting.business.dao;

import com.pinting.business.model.BsDailySettlementFile;
import com.pinting.business.model.BsDailySettlementFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsDailySettlementFileMapper {
    int countByExample(BsDailySettlementFileExample example);

    int deleteByExample(BsDailySettlementFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsDailySettlementFile record);

    int insertSelective(BsDailySettlementFile record);

    List<BsDailySettlementFile> selectByExample(BsDailySettlementFileExample example);

    BsDailySettlementFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsDailySettlementFile record, @Param("example") BsDailySettlementFileExample example);

    int updateByExample(@Param("record") BsDailySettlementFile record, @Param("example") BsDailySettlementFileExample example);

    int updateByPrimaryKeySelective(BsDailySettlementFile record);

    int updateByPrimaryKey(BsDailySettlementFile record);
}