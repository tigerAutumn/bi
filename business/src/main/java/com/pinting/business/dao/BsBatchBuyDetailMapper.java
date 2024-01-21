package com.pinting.business.dao;

import com.pinting.business.model.BsBatchBuyDetail;
import com.pinting.business.model.BsBatchBuyDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsBatchBuyDetailMapper {
    int countByExample(BsBatchBuyDetailExample example);

    int deleteByExample(BsBatchBuyDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsBatchBuyDetail record);

    int insertSelective(BsBatchBuyDetail record);

    List<BsBatchBuyDetail> selectByExample(BsBatchBuyDetailExample example);

    BsBatchBuyDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsBatchBuyDetail record, @Param("example") BsBatchBuyDetailExample example);

    int updateByExample(@Param("record") BsBatchBuyDetail record, @Param("example") BsBatchBuyDetailExample example);

    int updateByPrimaryKeySelective(BsBatchBuyDetail record);

    int updateByPrimaryKey(BsBatchBuyDetail record);
}