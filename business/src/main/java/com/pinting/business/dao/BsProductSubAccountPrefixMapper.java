package com.pinting.business.dao;

import com.pinting.business.model.BsProductSubAccountPrefixExample;
import com.pinting.business.model.BsProductSubAccountPrefixKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsProductSubAccountPrefixMapper {
    int countByExample(BsProductSubAccountPrefixExample example);

    int deleteByExample(BsProductSubAccountPrefixExample example);

    int deleteByPrimaryKey(BsProductSubAccountPrefixKey key);

    int insert(BsProductSubAccountPrefixKey record);

    int insertSelective(BsProductSubAccountPrefixKey record);

    List<BsProductSubAccountPrefixKey> selectByExample(BsProductSubAccountPrefixExample example);

    int updateByExampleSelective(@Param("record") BsProductSubAccountPrefixKey record, @Param("example") BsProductSubAccountPrefixExample example);

    int updateByExample(@Param("record") BsProductSubAccountPrefixKey record, @Param("example") BsProductSubAccountPrefixExample example);
}