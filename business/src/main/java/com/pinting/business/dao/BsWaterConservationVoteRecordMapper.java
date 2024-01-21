package com.pinting.business.dao;

import com.pinting.business.model.BsWaterConservationVoteRecord;
import com.pinting.business.model.BsWaterConservationVoteRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsWaterConservationVoteRecordMapper {
    int countByExample(BsWaterConservationVoteRecordExample example);

    int deleteByExample(BsWaterConservationVoteRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsWaterConservationVoteRecord record);

    int insertSelective(BsWaterConservationVoteRecord record);

    List<BsWaterConservationVoteRecord> selectByExample(BsWaterConservationVoteRecordExample example);

    BsWaterConservationVoteRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsWaterConservationVoteRecord record, @Param("example") BsWaterConservationVoteRecordExample example);

    int updateByExample(@Param("record") BsWaterConservationVoteRecord record, @Param("example") BsWaterConservationVoteRecordExample example);

    int updateByPrimaryKeySelective(BsWaterConservationVoteRecord record);

    int updateByPrimaryKey(BsWaterConservationVoteRecord record);
}