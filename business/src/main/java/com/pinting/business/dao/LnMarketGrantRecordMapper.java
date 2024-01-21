package com.pinting.business.dao;

import com.pinting.business.model.LnMarketGrantRecord;
import com.pinting.business.model.LnMarketGrantRecordExample;
import com.pinting.business.model.vo.MarketingForCheckVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LnMarketGrantRecordMapper {
    long countByExample(LnMarketGrantRecordExample example);

    int deleteByExample(LnMarketGrantRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnMarketGrantRecord record);

    int insertSelective(LnMarketGrantRecord record);

    List<LnMarketGrantRecord> selectByExample(LnMarketGrantRecordExample example);

    LnMarketGrantRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnMarketGrantRecord record, @Param("example") LnMarketGrantRecordExample example);

    int updateByExample(@Param("record") LnMarketGrantRecord record, @Param("example") LnMarketGrantRecordExample example);

    int updateByPrimaryKeySelective(LnMarketGrantRecord record);

    int updateByPrimaryKey(LnMarketGrantRecord record);

    List<MarketingForCheckVO> selectMarketingForCheck();
}