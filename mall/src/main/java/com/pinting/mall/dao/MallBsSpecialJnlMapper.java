package com.pinting.mall.dao;

import com.pinting.mall.model.MallBsSpecialJnl;
import com.pinting.mall.model.MallBsSpecialJnlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MallBsSpecialJnlMapper {
    int countByExample(MallBsSpecialJnlExample example);

    int deleteByExample(MallBsSpecialJnlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MallBsSpecialJnl record);

    int insertSelective(MallBsSpecialJnl record);

    List<MallBsSpecialJnl> selectByExample(MallBsSpecialJnlExample example);

    MallBsSpecialJnl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallBsSpecialJnl record, @Param("example") MallBsSpecialJnlExample example);

    int updateByExample(@Param("record") MallBsSpecialJnl record, @Param("example") MallBsSpecialJnlExample example);

    int updateByPrimaryKeySelective(MallBsSpecialJnl record);

    int updateByPrimaryKey(MallBsSpecialJnl record);
}