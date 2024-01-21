package com.pinting.business.dao;

import com.pinting.business.model.LnPayOrdersJnl;
import com.pinting.business.model.LnPayOrdersJnlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnPayOrdersJnlMapper {
    int countByExample(LnPayOrdersJnlExample example);

    int deleteByExample(LnPayOrdersJnlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnPayOrdersJnl record);

    int insertSelective(LnPayOrdersJnl record);

    List<LnPayOrdersJnl> selectByExample(LnPayOrdersJnlExample example);

    LnPayOrdersJnl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnPayOrdersJnl record, @Param("example") LnPayOrdersJnlExample example);

    int updateByExample(@Param("record") LnPayOrdersJnl record, @Param("example") LnPayOrdersJnlExample example);

    int updateByPrimaryKeySelective(LnPayOrdersJnl record);

    int updateByPrimaryKey(LnPayOrdersJnl record);
}