package com.pinting.business.dao;

import com.pinting.business.model.BsRepayJnl;
import com.pinting.business.model.BsRepayJnlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsRepayJnlMapper {
    int countByExample(BsRepayJnlExample example);

    int deleteByExample(BsRepayJnlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsRepayJnl record);

    int insertSelective(BsRepayJnl record);

    List<BsRepayJnl> selectByExample(BsRepayJnlExample example);

    BsRepayJnl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsRepayJnl record, @Param("example") BsRepayJnlExample example);

    int updateByExample(@Param("record") BsRepayJnl record, @Param("example") BsRepayJnlExample example);

    int updateByPrimaryKeySelective(BsRepayJnl record);

    int updateByPrimaryKey(BsRepayJnl record);
}