package com.pinting.business.dao;

import com.pinting.business.model.BsSubAccountPairJnl;
import com.pinting.business.model.BsSubAccountPairJnlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsSubAccountPairJnlMapper {
    int countByExample(BsSubAccountPairJnlExample example);

    int deleteByExample(BsSubAccountPairJnlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsSubAccountPairJnl record);

    int insertSelective(BsSubAccountPairJnl record);

    List<BsSubAccountPairJnl> selectByExample(BsSubAccountPairJnlExample example);

    BsSubAccountPairJnl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsSubAccountPairJnl record, @Param("example") BsSubAccountPairJnlExample example);

    int updateByExample(@Param("record") BsSubAccountPairJnl record, @Param("example") BsSubAccountPairJnlExample example);

    int updateByPrimaryKeySelective(BsSubAccountPairJnl record);

    int updateByPrimaryKey(BsSubAccountPairJnl record);
}