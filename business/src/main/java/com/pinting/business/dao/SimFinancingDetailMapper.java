package com.pinting.business.dao;

import com.pinting.business.model.SimFinancingDetail;
import com.pinting.business.model.SimFinancingDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SimFinancingDetailMapper {
    int countByExample(SimFinancingDetailExample example);

    int deleteByExample(SimFinancingDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SimFinancingDetail record);

    int insertSelective(SimFinancingDetail record);

    List<SimFinancingDetail> selectByExample(SimFinancingDetailExample example);

    SimFinancingDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SimFinancingDetail record, @Param("example") SimFinancingDetailExample example);

    int updateByExample(@Param("record") SimFinancingDetail record, @Param("example") SimFinancingDetailExample example);

    int updateByPrimaryKeySelective(SimFinancingDetail record);

    int updateByPrimaryKey(SimFinancingDetail record);
}