package com.pinting.business.dao;

import com.pinting.business.model.BsTermProductCode;
import com.pinting.business.model.BsTermProductCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsTermProductCodeMapper {
    int countByExample(BsTermProductCodeExample example);

    int deleteByExample(BsTermProductCodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsTermProductCode record);

    int insertSelective(BsTermProductCode record);

    List<BsTermProductCode> selectByExample(BsTermProductCodeExample example);

    BsTermProductCode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsTermProductCode record, @Param("example") BsTermProductCodeExample example);

    int updateByExample(@Param("record") BsTermProductCode record, @Param("example") BsTermProductCodeExample example);

    int updateByPrimaryKeySelective(BsTermProductCode record);

    int updateByPrimaryKey(BsTermProductCode record);

    List<BsTermProductCode> selectDistinctTerm(BsTermProductCodeExample example);
}