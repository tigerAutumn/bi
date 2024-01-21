package com.pinting.business.dao;

import com.pinting.business.model.DafyBindBankExt;
import com.pinting.business.model.DafyBindBankExtExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DafyBindBankExtMapper {
    int countByExample(DafyBindBankExtExample example);

    int deleteByExample(DafyBindBankExtExample example);

    int deleteByPrimaryKey(Integer bankId);

    int insert(DafyBindBankExt record);

    int insertSelective(DafyBindBankExt record);

    List<DafyBindBankExt> selectByExample(DafyBindBankExtExample example);

    DafyBindBankExt selectByPrimaryKey(Integer bankId);

    int updateByExampleSelective(@Param("record") DafyBindBankExt record, @Param("example") DafyBindBankExtExample example);

    int updateByExample(@Param("record") DafyBindBankExt record, @Param("example") DafyBindBankExtExample example);

    int updateByPrimaryKeySelective(DafyBindBankExt record);

    int updateByPrimaryKey(DafyBindBankExt record);
}