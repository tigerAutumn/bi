package com.pinting.business.dao;

import com.pinting.business.model.LnDepositionTarget;
import com.pinting.business.model.LnDepositionTargetExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LnDepositionTargetMapper {
    long countByExample(LnDepositionTargetExample example);

    int deleteByExample(LnDepositionTargetExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnDepositionTarget record);

    int insertSelective(LnDepositionTarget record);

    List<LnDepositionTarget> selectByExample(LnDepositionTargetExample example);

    LnDepositionTarget selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnDepositionTarget record, @Param("example") LnDepositionTargetExample example);

    int updateByExample(@Param("record") LnDepositionTarget record, @Param("example") LnDepositionTargetExample example);

    int updateByPrimaryKeySelective(LnDepositionTarget record);

    int updateByPrimaryKey(LnDepositionTarget record);

    /**
     * 根据借款编号查询存管标的表
     * @param loanId
     * @return
     */
    LnDepositionTarget selectByLoanId(Integer loanId);
}