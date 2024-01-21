package com.pinting.mall.dao;

import com.pinting.mall.model.MallSendCommodity;
import com.pinting.mall.model.MallSendCommodityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MallSendCommodityMapper {
    int countByExample(MallSendCommodityExample example);

    int deleteByExample(MallSendCommodityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MallSendCommodity record);

    int insertSelective(MallSendCommodity record);

    List<MallSendCommodity> selectByExample(MallSendCommodityExample example);

    MallSendCommodity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallSendCommodity record, @Param("example") MallSendCommodityExample example);

    int updateByExample(@Param("record") MallSendCommodity record, @Param("example") MallSendCommodityExample example);

    int updateByPrimaryKeySelective(MallSendCommodity record);

    int updateByPrimaryKey(MallSendCommodity record);
}