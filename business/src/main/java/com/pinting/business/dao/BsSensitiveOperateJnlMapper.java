package com.pinting.business.dao;

import com.pinting.business.model.BsSensitiveOperateJnl;
import com.pinting.business.model.BsSensitiveOperateJnlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsSensitiveOperateJnlMapper {
    int countByExample(BsSensitiveOperateJnlExample example);

    int deleteByExample(BsSensitiveOperateJnlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsSensitiveOperateJnl record);

    int insertSelective(BsSensitiveOperateJnl record);

    List<BsSensitiveOperateJnl> selectByExample(BsSensitiveOperateJnlExample example);
    List<BsSensitiveOperateJnl> selectByPage(BsSensitiveOperateJnl bsSensitiveOperateJnl);
    BsSensitiveOperateJnl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsSensitiveOperateJnl record, @Param("example") BsSensitiveOperateJnlExample example);

    int updateByExample(@Param("record") BsSensitiveOperateJnl record, @Param("example") BsSensitiveOperateJnlExample example);

    int updateByPrimaryKeySelective(BsSensitiveOperateJnl record);

    int updateByPrimaryKey(BsSensitiveOperateJnl record);
}