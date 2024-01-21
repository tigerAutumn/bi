package com.pinting.business.dao;

import com.pinting.business.model.BsCheckJnl;
import com.pinting.business.model.BsCheckJnlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsCheckJnlMapper {
    int countByExample(BsCheckJnlExample example);

    int deleteByExample(BsCheckJnlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsCheckJnl record);

    int insertSelective(BsCheckJnl record);

    List<BsCheckJnl> selectByExample(BsCheckJnlExample example);

    BsCheckJnl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsCheckJnl record, @Param("example") BsCheckJnlExample example);

    int updateByExample(@Param("record") BsCheckJnl record, @Param("example") BsCheckJnlExample example);

    int updateByPrimaryKeySelective(BsCheckJnl record);

    int updateByPrimaryKey(BsCheckJnl record);
}