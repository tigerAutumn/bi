package com.pinting.business.dao;

import com.pinting.business.model.BsBatchReturnDetail;
import com.pinting.business.model.BsBatchReturnDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsBatchReturnDetailMapper {
    int countByExample(BsBatchReturnDetailExample example);

    int deleteByExample(BsBatchReturnDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsBatchReturnDetail record);

    int insertSelective(BsBatchReturnDetail record);

    List<BsBatchReturnDetail> selectByExample(BsBatchReturnDetailExample example);

    BsBatchReturnDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsBatchReturnDetail record, @Param("example") BsBatchReturnDetailExample example);

    int updateByExample(@Param("record") BsBatchReturnDetail record, @Param("example") BsBatchReturnDetailExample example);

    int updateByPrimaryKeySelective(BsBatchReturnDetail record);

    int updateByPrimaryKey(BsBatchReturnDetail record);
    
    Double sumReturnedAmountBySubActId(@Param("subAccountId") Integer subAccountId);
}