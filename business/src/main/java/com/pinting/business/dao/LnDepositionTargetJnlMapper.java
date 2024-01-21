package com.pinting.business.dao;

import com.pinting.business.model.LnDepositionTarget;
import com.pinting.business.model.LnDepositionTargetJnl;
import com.pinting.business.model.LnDepositionTargetJnlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnDepositionTargetJnlMapper {
    long countByExample(LnDepositionTargetJnlExample example);

    int deleteByExample(LnDepositionTargetJnlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnDepositionTargetJnl record);

    int insertSelective(LnDepositionTargetJnl record);

    List<LnDepositionTargetJnl> selectByExample(LnDepositionTargetJnlExample example);

    LnDepositionTargetJnl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnDepositionTargetJnl record, @Param("example") LnDepositionTargetJnlExample example);

    int updateByExample(@Param("record") LnDepositionTargetJnl record, @Param("example") LnDepositionTargetJnlExample example);

    int updateByPrimaryKeySelective(LnDepositionTargetJnl record);

    int updateByPrimaryKey(LnDepositionTargetJnl record);

    /**
     * 标的流水查询
     * @param id            标的流水ID
     * @param start         起始标识（初始：0）
     * @param numPerPage    每页显示条数
     * @return
     */
    List<LnDepositionTarget> selectByPage(@Param("id") Integer id, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 标的流水查询
     * @param id            标的流水ID
     * @return
     */
    Integer countByPage(@Param("id") Integer id);
}