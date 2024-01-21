package com.pinting.business.dao;

import com.pinting.business.model.DafyWapBankExt;
import com.pinting.business.model.DafyWapBankExtExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DafyWapBankExtMapper {
    int countByExample(DafyWapBankExtExample example);

    int deleteByExample(DafyWapBankExtExample example);

    int deleteByPrimaryKey(Integer bankId);

    int insert(DafyWapBankExt record);

    int insertSelective(DafyWapBankExt record);

    List<DafyWapBankExt> selectByExample(DafyWapBankExtExample example);

    DafyWapBankExt selectByPrimaryKey(Integer bankId);

    int updateByExampleSelective(@Param("record") DafyWapBankExt record, @Param("example") DafyWapBankExtExample example);

    int updateByExample(@Param("record") DafyWapBankExt record, @Param("example") DafyWapBankExtExample example);

    int updateByPrimaryKeySelective(DafyWapBankExt record);

    int updateByPrimaryKey(DafyWapBankExt record);
}