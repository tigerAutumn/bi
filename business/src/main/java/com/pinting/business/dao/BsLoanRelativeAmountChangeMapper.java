package com.pinting.business.dao;

import com.pinting.business.model.BsLoanRelativeAmountChange;
import com.pinting.business.model.BsLoanRelativeAmountChangeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsLoanRelativeAmountChangeMapper {
    int countByExample(BsLoanRelativeAmountChangeExample example);

    int deleteByExample(BsLoanRelativeAmountChangeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsLoanRelativeAmountChange record);

    int insertSelective(BsLoanRelativeAmountChange record);

    List<BsLoanRelativeAmountChange> selectByExample(BsLoanRelativeAmountChangeExample example);

    BsLoanRelativeAmountChange selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsLoanRelativeAmountChange record, @Param("example") BsLoanRelativeAmountChangeExample example);

    int updateByExample(@Param("record") BsLoanRelativeAmountChange record, @Param("example") BsLoanRelativeAmountChangeExample example);

    int updateByPrimaryKeySelective(BsLoanRelativeAmountChange record);

    int updateByPrimaryKey(BsLoanRelativeAmountChange record);
}