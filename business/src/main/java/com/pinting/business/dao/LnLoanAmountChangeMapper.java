package com.pinting.business.dao;

import com.pinting.business.model.LnLoanAmountChange;
import com.pinting.business.model.LnLoanAmountChangeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnLoanAmountChangeMapper {
    int countByExample(LnLoanAmountChangeExample example);

    int deleteByExample(LnLoanAmountChangeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnLoanAmountChange record);

    int insertSelective(LnLoanAmountChange record);

    List<LnLoanAmountChange> selectByExample(LnLoanAmountChangeExample example);

    LnLoanAmountChange selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnLoanAmountChange record, @Param("example") LnLoanAmountChangeExample example);

    int updateByExample(@Param("record") LnLoanAmountChange record, @Param("example") LnLoanAmountChangeExample example);

    int updateByPrimaryKeySelective(LnLoanAmountChange record);

    int updateByPrimaryKey(LnLoanAmountChange record);

    /**
     * 根据根据借款ID查询,如果存在取create_time最早的变动前金额before_amount
     * @param relationId 借贷关系编号
     * @return
     */
    LnLoanAmountChange selectAmountByRelationId(@Param("relationId") int relationId);
}