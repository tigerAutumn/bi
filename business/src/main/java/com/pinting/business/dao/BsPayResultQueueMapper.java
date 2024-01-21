package com.pinting.business.dao;

import com.pinting.business.model.BsPayResultQueue;
import com.pinting.business.model.BsPayResultQueueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsPayResultQueueMapper {
    int countByExample(BsPayResultQueueExample example);

    int deleteByExample(BsPayResultQueueExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsPayResultQueue record);

    int insertSelective(BsPayResultQueue record);

    List<BsPayResultQueue> selectByExample(BsPayResultQueueExample example);

    BsPayResultQueue selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsPayResultQueue record, @Param("example") BsPayResultQueueExample example);

    int updateByExample(@Param("record") BsPayResultQueue record, @Param("example") BsPayResultQueueExample example);

    int updateByPrimaryKeySelective(BsPayResultQueue record);

    int updateByPrimaryKey(BsPayResultQueue record);
}