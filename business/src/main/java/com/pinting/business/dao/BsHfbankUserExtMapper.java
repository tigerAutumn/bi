package com.pinting.business.dao;

import com.pinting.business.model.BsHfbankUserExt;
import com.pinting.business.model.BsHfbankUserExtExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsHfbankUserExtMapper {
    long countByExample(BsHfbankUserExtExample example);

    int deleteByExample(BsHfbankUserExtExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsHfbankUserExt record);

    int insertSelective(BsHfbankUserExt record);

    List<BsHfbankUserExt> selectByExample(BsHfbankUserExtExample example);

    BsHfbankUserExt selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsHfbankUserExt record, @Param("example") BsHfbankUserExtExample example);

    int updateByExample(@Param("record") BsHfbankUserExt record, @Param("example") BsHfbankUserExtExample example);

    int updateByPrimaryKeySelective(BsHfbankUserExt record);

    int updateByPrimaryKey(BsHfbankUserExt record);
}