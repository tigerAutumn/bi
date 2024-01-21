package com.pinting.business.dao;

import com.pinting.business.model.LnAccount;
import com.pinting.business.model.LnAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnAccountMapper {
    int countByExample(LnAccountExample example);

    int deleteByExample(LnAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnAccount record);

    int insertSelective(LnAccount record);

    List<LnAccount> selectByExample(LnAccountExample example);

    LnAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnAccount record, @Param("example") LnAccountExample example);

    int updateByExample(@Param("record") LnAccount record, @Param("example") LnAccountExample example);

    int updateByPrimaryKeySelective(LnAccount record);

    int updateByPrimaryKey(LnAccount record);
}