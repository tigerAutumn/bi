package com.pinting.business.dao;

import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsSubAccountPair;
import com.pinting.business.model.BsSubAccountPairExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BsSubAccountPairMapper {
    int countByExample(BsSubAccountPairExample example);

    int deleteByExample(BsSubAccountPairExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsSubAccountPair record);

    int insertSelective(BsSubAccountPair record);

    List<BsSubAccountPair> selectByExample(BsSubAccountPairExample example);

    BsSubAccountPair selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsSubAccountPair record, @Param("example") BsSubAccountPairExample example);

    int updateByExample(@Param("record") BsSubAccountPair record, @Param("example") BsSubAccountPairExample example);

    int updateByPrimaryKeySelective(BsSubAccountPair record);

    int updateByPrimaryKey(BsSubAccountPair record);

    /**
     * 根据借贷关系编号获得对应产品补差子账户
     * @param loanRelationId
     * @return
     */
    BsSubAccount selectDiffActByLoanRelationId(Integer loanRelationId);
}