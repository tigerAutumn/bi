package com.pinting.mall.dao;

import com.pinting.mall.model.MallBsSubAccount;
import com.pinting.mall.model.MallBsSubAccountExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MallBsSubAccountMapper {
    int countByExample(MallBsSubAccountExample example);

    int deleteByExample(MallBsSubAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MallBsSubAccount record);

    int insertSelective(MallBsSubAccount record);

    List<MallBsSubAccount> selectByExample(MallBsSubAccountExample example);

    MallBsSubAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallBsSubAccount record, @Param("example") MallBsSubAccountExample example);

    int updateByExample(@Param("record") MallBsSubAccount record, @Param("example") MallBsSubAccountExample example);

    int updateByPrimaryKeySelective(MallBsSubAccount record);

    int updateByPrimaryKey(MallBsSubAccount record);
    
    Double sumYearInvestByUserId(@Param("userId") Integer userId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}