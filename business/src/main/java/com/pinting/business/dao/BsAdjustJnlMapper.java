package com.pinting.business.dao;

import com.pinting.business.model.BsAdjustJnl;
import com.pinting.business.model.BsAdjustJnlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsAdjustJnlMapper {
    int countByExample(BsAdjustJnlExample example);

    int deleteByExample(BsAdjustJnlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsAdjustJnl record);

    int insertSelective(BsAdjustJnl record);

    List<BsAdjustJnl> selectByExample(BsAdjustJnlExample example);

    BsAdjustJnl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsAdjustJnl record, @Param("example") BsAdjustJnlExample example);

    int updateByExample(@Param("record") BsAdjustJnl record, @Param("example") BsAdjustJnlExample example);

    int updateByPrimaryKeySelective(BsAdjustJnl record);

    int updateByPrimaryKey(BsAdjustJnl record);
}