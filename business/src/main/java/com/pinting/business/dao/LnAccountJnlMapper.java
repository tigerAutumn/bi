package com.pinting.business.dao;

import com.pinting.business.model.LnAccountJnl;
import com.pinting.business.model.LnAccountJnlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnAccountJnlMapper {
    long countByExample(LnAccountJnlExample example);

    int deleteByExample(LnAccountJnlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnAccountJnl record);

    int insertSelective(LnAccountJnl record);

    List<LnAccountJnl> selectByExample(LnAccountJnlExample example);

    LnAccountJnl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnAccountJnl record, @Param("example") LnAccountJnlExample example);

    int updateByExample(@Param("record") LnAccountJnl record, @Param("example") LnAccountJnlExample example);

    int updateByPrimaryKeySelective(LnAccountJnl record);

    int updateByPrimaryKey(LnAccountJnl record);
}